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

package com.github.ppadial.testrail.client.api.cases;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.projects.InvalidOrUnknownProjectException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRCase;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Testrail Client for API: Cases Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-cases">reference-cases</a>
 * @since 0.1.0
 */
public final class CaseServiceClient extends TestRailServiceBase {

  public CaseServiceClient(ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Adds a test case.
   * @param sectionId the section ID.
   * @param title the title of the test case
   * @param fields the fields
   * @return the created case.
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRCase addCase(int sectionId, String title, Map<String, String> fields)
      throws TestRailException {
    Map<String, String> body = new HashMap<String, String>();
    body.put("title", title);
    if (fields != null) {
      body.putAll(fields);
    }
//    return objectMapper.readValue(
//        apiClient.doPost("add_case/" + sectionId, objectMapper.writeValueAsString(body)),
//        TRCase.class);
    return null;
  }

  /**
   * Returns an existing test case.
   *
   * @param caseId The ID of the test case
   * @return the case
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRCase getCase(final int caseId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRCase responseObjectModel;

    // Do the query
    apiResponse = get("get_case/" + caseId);

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestCaseException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRCase.class, choices);
    return responseObjectModel;
  }

  /**
   * Gets all cases.
   * @param projectId .
   * @param suiteId .
   * @param sectionId .
   * @param filters .
   * @return .
   * @throws TestRailException An error in the connection with testrail
   */
  public final List<TRCase> getCases(final int projectId, final int suiteId, final int sectionId,
      final Map<String, String> filters) throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRCase> responseObjectModel;

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    String url = "get_cases/" + projectId;
    if (suiteId > 0) {
      url += "&suite_id=" + suiteId;
    }
    if (sectionId > 0) {
      url += "&section_id=" + sectionId;
    }
    if (filters != null) {
      for (Map.Entry<String, String> entry : filters.entrySet()) {
        url += "&" + entry.getKey() + "=" + entry.getValue();
      }
    }

    // Do the query
    apiResponse = get(url);

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRCase>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * .
   * @param caseId .
   * @param fields .
   * @return .
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRCase updateCase(final int caseId, final Map<String, Object> fields)
      throws TestRailException {
//    return objectMapper.readValue(apiClient.doPost("update_case/" + caseId,
//        objectMapper.writeValueAsString(fields)), TRCase.class);
    return null;
  }

  /**
   * Deletes an existing test case.
   *
   * @param caseId The ID of the test case
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public void deleteCase(final int caseId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_case/" + caseId);

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestCaseException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to delete test cases or no access to the project"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }
}
