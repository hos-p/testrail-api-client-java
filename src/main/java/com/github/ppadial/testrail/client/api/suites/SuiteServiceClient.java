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

package com.github.ppadial.testrail.client.api.suites;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.projects.InvalidOrUnknownProjectException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRSuite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 * Testrail Client for API: Suites Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-suites">reference-suites</a>
 * @since 0.1.0
 */
public final class SuiteServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance.
   *
   * @param apiClient the api client
   */
  public SuiteServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing test suite.
   *
   * @param suiteId The ID of the test suite
   * @return test suite
   * @since 0.1.0
   */
  public final TRSuite getSuite(final int suiteId)
      throws ApiCallException, TestRailException, InvalidOrUnknownTestSuiteException, NoAccessToProjectException {
    final ApiResponse apiResponse;
    final TRSuite responseObjectModel;

    // Do the query
    apiResponse = get("get_suite/" + suiteId);

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestSuiteException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRSuite.class, choices);
    return responseObjectModel;
  }

  /**
   * Returns a list of test suites for a project.
   *
   * @param projectId The ID of the project
   * @return list of suites
   * @since 0.1.0
   */
  public final List<TRSuite> getSuites(final int projectId)
      throws ApiCallException, TestRailException, InvalidOrUnknownProjectException, NoAccessToProjectException {
    final ApiResponse apiResponse;
    final List<TRSuite> responseObjectModel;

    // Do the query
    apiResponse = get("get_suites/" + projectId);

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRSuite>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Creates a new test suite.
   *
   * @param projectId The ID of the project the test suite should be added to
   * @param name The name of the test suite (required)
   * @param description The description of the test suite
   * @return created test suite
   * @since 0.1.0
   */
  public final TRSuite addSuite(final int projectId, final String name, final String description)
      throws ApiCallException, TestRailException {
    final ApiResponse apiResponse;
    final TRSuite responseObjectModel;

    final Map<String, String> body = new HashMap<String, String>();
    if (name != null && StringUtils.isNotEmpty(name)) {
      body.put("name", name);
    } else {
      throw new TestRailException("Parameter name can't be null or empty");
    }
    if (description != null && StringUtils.isNotEmpty(description)) {
      body.put("description", description);
    }

    // Do the query
    apiResponse = post("add_suite/" + projectId, body);

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRSuite.class, choices);
    return responseObjectModel;
  }
}
