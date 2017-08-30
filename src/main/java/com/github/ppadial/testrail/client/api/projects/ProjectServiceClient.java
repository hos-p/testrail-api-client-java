/*
 * Copyright (c) 2017. Paulino Padial
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

package com.github.ppadial.testrail.client.api.projects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.plans.InvalidOrUnknownTestPlanException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRProject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Strings;

/**
 * Testrail Client for API: Projects Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-projects">reference-projects</a>
 * @since 0.1.0
 */
public final class ProjectServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance of the plan service client.
   *
   * @param apiClient the api client to use
   */
  public ProjectServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing project.
   *
   * @param projectId The ID of the project
   * @return project
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRProject getProject(final int projectId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRProject responseObjectModel;

    // Do the query
    apiResponse = get("get_project/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRProject.class, choices);
    return responseObjectModel;
  }

  /**
   * Returns the list of available projects.
   *
   * @return List of available projects
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRProject> getProjects() throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRProject> responseObjectModel;

    // Do the query
    apiResponse = get("get_projects");

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRProject>>() {
    }, null);
    return responseObjectModel;
  }

  /**
   * Returns the list of available projects.
   *
   * @param isCompleted True to return completed projects only. False to return active projects only.
   * @return List of available projects
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRProject> getProjects(final boolean isCompleted) throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRProject> responseObjectModel;

    // Do the query
    apiResponse = get("get_projects&is_completed=" + ((isCompleted) ? "1" : "0"));

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRProject>>() {
    }, null);
    return responseObjectModel;
  }

  /**
   * Creates a new project (admin status required).
   *
   * @param name The name of the project (required)
   * @param announcement The description of the project
   * @param showAnnouncement True if the announcement should be displayed on the project's overview page and false
   * otherwise
   * @param suiteMode The suite mode of the project
   * @return the added project
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRProject addProject(final String name, final String announcement, final boolean showAnnouncement,
      final SuiteMode suiteMode) throws TestRailException {

    final ApiResponse apiResponse;
    final TRProject responseObjectModel;

    Map<String, Object> body = new HashMap<String, Object>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    } else {
      throw new IllegalArgumentException("Name is a required argument and can't be null or empty");
    }
    if (!Strings.isNullOrEmpty(announcement)) {
      body.put("announcement", announcement);
    }
    body.put("show_announcement", showAnnouncement);
    if (suiteMode != null) {
      body.put("suite_mode", suiteMode.getValue());
    }

    // Do the query
    apiResponse = post("add_project", body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add projects (requires admin rights)"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRProject.class, choices);
    return responseObjectModel;
  }

  /**
   * Updates an existing project (admin status required; partial updates are supported, i.e. you can submit and update
   * specific fields only).
   *
   * @param projectId the project ID
   * @param name The name of the project (required)
   * @param announcement The description of the project
   * @param showAnnouncement True if the announcement should be displayed on the project's overview page and false
   * otherwise
   * @param suiteMode The suite mode of the project
   * @param isCompleted Specifies whether a project is considered completed or not
   * @return the updated project
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRProject updateProject(final int projectId, final String name, final String announcement,
      final boolean showAnnouncement, final SuiteMode suiteMode, final boolean isCompleted)
      throws TestRailException {

    final ApiResponse apiResponse;
    final TRProject responseObjectModel;

    Map<String, Object> body = new HashMap<String, Object>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    }
    if (!Strings.isNullOrEmpty(announcement)) {
      body.put("announcement", announcement);
    }
    body.put("show_announcement", showAnnouncement);
    if (suiteMode != null) {
      body.put("suite_mode", suiteMode.toString());
    }
    body.put("is_completed", isCompleted);

    // Do the query
    apiResponse = post("update_project/" + projectId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add projects (requires admin rights)"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRProject.class, choices);
    return responseObjectModel;
  }

  /**
   * Deletes an existing project (admin status required).
   * Deleting a project cannot be undone and also permanently deletes all test suites and cases, test runs and
   * results and everything else that is part of the project.
   * @param projectId The ID of the project
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final void deleteProject(final int projectId) throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_project/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to delete projects (requires admin rights)"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }
}
