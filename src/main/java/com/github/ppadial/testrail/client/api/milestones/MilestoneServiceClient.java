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

package com.github.ppadial.testrail.client.api.milestones;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRMilestone;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Strings;

/**
 * Testrail Client for API: Milestones Endpoint. Use the following API methods to request details about milestones and
 * to create or modify milestones.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-milestones">reference-milestones</a>
 * @since 0.1.0
 */
public final class MilestoneServiceClient extends TestRailServiceBase {

  /**
   * Creates an intance of the milestone service client.
   *
   * @param apiClient the apiclient to use
   */
  public MilestoneServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing milestone.
   *
   * @param milestoneId The ID of the milestone
   * @return milestone
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRMilestone getMilestone(final int milestoneId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRMilestone responseObjectModel;

    // Do the query
    apiResponse = get("get_milestone/" + milestoneId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownMilestoneException("invalid or unknown milestone"));
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException("No access to the project"));
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRMilestone.class, choices);
    return responseObjectModel;
  }

  /**
   * Returns the list of milestones for a project.
   *
   * @param projectId The ID of the project
   * @return list of milestones of the project
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRMilestone> getMilestones(final int projectId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRMilestone> responseObjectModel;

    // Do the query
    apiResponse = get("get_milestones/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownMilestoneException("invalid or unknown project"));
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException("No access to the project"));
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRMilestone>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Creates a new milestone.
   *
   * @param projectId The ID of the project the milestone should be added to
   * @param name The name of the milestone (required)
   * @param description The description of the milestone
   * @param dueOn The due date of the milestone (as UNIX timestamp)
   * @param parentId The ID of the parent milestone, if any (for sub-milestones) (available since TestRail 5.3)
   * @param startOn The scheduled start date of the milestone (as UNIX timestamp) (available since TestRail 5.3)
   * @return the added milestone
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRMilestone addMilestone(final int projectId, final String name, final String description,
      final Long dueOn, final Integer parentId, final Long startOn) throws TestRailException {

    final ApiResponse apiResponse;
    final TRMilestone responseObjectModel;
    final Map<String, String> body = new HashMap<String, String>();

    if (Strings.isNullOrEmpty(name)) {
      throw new TestRailException("parameter name is null or empty and it's mandatory");
    } else {
      body.put("name", name);
    }
    if (description != null) {
      body.put("description", description);
    }
    if (dueOn != null) {
      body.put("due_on", dueOn.toString());
    }
    if (parentId != null) {
      body.put("parent_id", parentId.toString());
    }
    if (startOn != null) {
      body.put("start_on", startOn.toString());
    }

    // Do the query
    apiResponse = post("add_milestone/" + projectId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownMilestoneException("invalid or unknown project"));
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add milestones or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRMilestone.class, choices);
    return responseObjectModel;
  }

  /**
   * Updates an existing milestone (partial updates are supported, i.e. you can submit and update specific fields
   * only).
   *
   * @param milestoneId The ID of the milestone
   * @param name The name of the milestone (required)
   * @param description The description of the milestone
   * @param dueOn The due date of the milestone (as UNIX timestamp)
   * @param isCompleted True if a milestone is considered completed and false otherwise
   * @param isStarted True if a milestone is considered started and false otherwise
   * @param parentId The ID of the parent milestone, if any (for sub-milestones) (available since TestRail 5.3)
   * @param startOn The scheduled start date of the milestone (as UNIX timestamp) (available since TestRail 5.3)
   * @return the updated milestone
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRMilestone updateMilestone(final int milestoneId, final String name, final String description,
      final Long dueOn, final Boolean isCompleted,
      final Boolean isStarted, final Integer parentId, final Long startOn)
      throws TestRailException {

    final ApiResponse apiResponse;
    final TRMilestone responseObjectModel;
    final Map<String, String> body = new HashMap<String, String>();

    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    }
    if (description != null) {
      body.put("description", description);
    }
    if (dueOn != null) {
      body.put("due_on", dueOn.toString());
    }
    if (isCompleted != null) {
      body.put("is_completed", isCompleted.toString());
    }
    if (isStarted != null) {
      body.put("is_started", isStarted.toString());
    }
    if (parentId != null) {
      body.put("parent_id", parentId.toString());
    }
    if (startOn != null) {
      body.put("start_on", startOn.toString());
    }

    // Do the query
    apiResponse = post("update_milestone/" + milestoneId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownMilestoneException("invalid or unknown milestone"));
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to modify milestones or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRMilestone.class, choices);
    return responseObjectModel;
  }

  /**
   * Deletes an existing milestone.
   *
   * @param milestoneId The ID of the milestone
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final void deleteMilestone(final int milestoneId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = get("delete_milestone/" + milestoneId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownMilestoneException("invalid or unknown milestone"));
          put(HttpStatusCode.FORBIDDEN,
              new NoAccessToProjectException("No permissions to delete milestones or no access to the project"));
        }};

    // Handle response
    handleApiResponse(apiResponse, choices);
  }
}
