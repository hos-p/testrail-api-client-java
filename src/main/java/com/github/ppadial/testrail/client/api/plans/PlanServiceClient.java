/*
 * Copyright (c) 2018. Paulino Padial
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.ppadial.testrail.client.api.plans;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.projects.InvalidOrUnknownProjectException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRPlan;
import com.github.ppadial.testrail.client.model.TRPlanEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Strings;

/**
 * Testrail Client for API: Plans Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-plans">reference-plans</a>
 * @since 0.1.0
 */
public final class PlanServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance of the plan service client.
   *
   * @param apiClient the api client to use
   */
  public PlanServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing test plan.
   *
   * @param planId The ID of the test plan
   * @return the test plan and its test run
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRPlan getPlan(final int planId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRPlan responseObjectModel;

    // Do the query
    apiResponse = get("get_plan/" + planId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestPlanException("invalid or unknown plan"));
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add milestones or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRPlan.class, choices);
    return responseObjectModel;
  }

  /**
   * Get all test plans of a project with a filter.
   *
   * @param projectId project identifier
   * @param filters filters to apply
   * @return list of test plans from the specified project that meets the filter
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  // TODO: Change filters by parameters nullable
  public final List<TRPlan> getPlans(final int projectId, final Map<String, String> filters)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRPlan> responseObjectModel;

    String url = "get_plans/" + projectId;
    if (filters != null) {
      for (Map.Entry<String, String> entry : filters.entrySet()) {
        url += "&" + entry.getKey() + "=" + entry.getValue();
      }
    }

    // Do the query
    apiResponse = get("get_plans/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRPlan>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Add a news TRTest TRPlan
   *
   * @param projectId project identifier
   * @param name The name of the test plan (required)
   * @param description the description of the test plan
   * @param milestoneId The ID of the milestone to link to the test plan
   * @param entries An array of objects describing the test runs of the plan
   * @return the created test plan
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRPlan addPlan(final int projectId, final String name, final String description,
      final Integer milestoneId, final List<TRPlanEntry> entries)
      throws TestRailException {

    final ApiResponse apiResponse;
    final TRPlan responseObjectModel;

    Map<String, Object> body = new HashMap<String, Object>();
    body.put("name", name);
    if (milestoneId != null) {
      body.put("milestone_id", String.valueOf(milestoneId));
    }
    if (!Strings.isNullOrEmpty(description)) {
      body.put("description", description);
    }
    if (entries != null) {
      body.put("entries", entries);
    }

    // Do the query
    apiResponse = post("add_plan/" + projectId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add test plans or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRPlan.class, choices);
    return responseObjectModel;
  }

  /**
   * Adds a suite to an existing test plan
   *
   * @param planId The ID of the plan the test runs should be added to
   * @param suiteId The ID of the test suite for the test run(s) (required)
   * @return the test run(s) were created and are returned as part of the response. Please note that test runs in a plan
   * are organized into 'entries' and these have their own IDs (to be used with update_plan_entry and delete_plan_entry,
   * respectively).
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  // TODO: Handle all optional parameters
  // TODO: Create a Generic NoPermissionsException ?
  public final TRPlanEntry addPlanEntry(final int planId, final int suiteId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRPlanEntry responseObjectModel;

    Map<String, String> body = new HashMap<String, String>();
    body.put("suite_id", String.valueOf(suiteId));

    // Do the query
    apiResponse = post("add_plan_entry/" + planId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestPlanException());
          put(HttpStatusCode.FORBIDDEN,
              new NoAccessToProjectException("No permissions to modify test plans or no access to the project"));
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRPlanEntry.class, choices);
    return responseObjectModel;
  }

  /**
   * Updates one or more existing test runs in a plan (partial updates are supported, i.e. you can submit and update
   * specific fields only).
   *
   * @param planId The ID of the test plan
   * @param entryId The ID of the test plan entry (note: not the test run ID)
   * @return the test run(s) were created and are returned as part of the response. Please note that test runs in a plan
   * are organized into 'entries' and these have their own IDs (to be used with update_plan_entry and delete_plan_entry,
   * respectively).
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  // TODO: Add optional parameters
  public final List<TRPlanEntry> updatePlanEntry(final int planId, final int entryId)
      throws TestRailException {

    final ApiResponse apiResponse;
    final List<TRPlanEntry> responseObjectModel;

    Map<String, String> body = new HashMap<String, String>();

    // Do the query
    apiResponse = post("update_plan_entry/" + planId + "/" + entryId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestPlanException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to modify test plans or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRPlanEntry>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Close an existing plan.
   * Closing a test plan cannot be undone.
   *
   * @param planId The ID of the test plan
   * @return the closed plan
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRPlan closePlan(final int planId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRPlan responseObjectModel;

    // Do the query
    apiResponse = post("close_plan/" + planId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestPlanException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to close test plans or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRPlan.class, choices);
    return responseObjectModel;
  }

  /**
   * Deletes an existing test plan.
   * Deleting a test plan cannot be undone and also permanently deletes all test runs and results of the test
   * plan.
   *
   * @param planId The ID of the test plan
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final void deletePlan(final int planId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_plan/" + planId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestPlanException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to delete test plans or no access to the project"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }

  /**
   * Deletes an existing test plan.
   * Deleting a test run from a plan cannot be undone and also permanently deletes all related test results.
   *
   * @param planId The ID of the test plan
   * @param entryId The ID of the test plan entry (note: not the test run ID)
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final void deletePlanEntry(final int planId, final int entryId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_plan_entry/" + planId + "/" + entryId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestPlanException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to modify test plans or no access to the project"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }
}
