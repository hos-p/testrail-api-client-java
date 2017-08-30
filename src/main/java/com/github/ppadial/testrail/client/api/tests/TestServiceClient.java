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

package com.github.ppadial.testrail.client.api.tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.results.ResultServiceClient;
import com.github.ppadial.testrail.client.api.runs.InvalidOrUnknownTestRunException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRTest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Testrail Client for TRPlan API Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-tests">reference-tests</a>
 * @since 0.1.0
 */
public final class TestServiceClient extends TestRailServiceBase {

  /**
   * Creates a new test service client.
   *
   * @param apiClient the apiclient
   */
  public TestServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing test. use {@link ResultServiceClient#getResults(int, Integer)} instead.
   *
   * @param testId The ID of the test
   * @return an existing test
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRTest getTest(final int testId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRTest trTest;

    // Do the query
    apiResponse = get("get_test/" + testId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestException("invalid or unknown test"));
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    trTest = handleApiResponse(apiResponse, TRTest.class, choices);
    return trTest;
  }

  /**
   * Returns a list of tests for a test run.
   *
   * @param runId The ID of the test run
   * @return list of tests
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRTest> getTests(final int runId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRTest> trTest;

    // Do the query
    apiResponse = get("get_tests/" + runId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestRunException());
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    trTest = handleApiResponse(apiResponse, new TypeReference<List<TRTest>>() {
    }, choices);

    return trTest;
  }

  /**
   * Returns a list of tests with specified statuses for a test run.
   *
   * @param runId The ID of the test run
   * @param statuses list of status identifier
   * @return list of tests with the status specified
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRTest> getTestsFilterByStatus(final int runId, final int... statuses)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRTest> responseObjectModel;

    // TODO: validate status id? how?
    // This line convert an int[] to an string[]
    final String listOfStatus = String.join(",",
        (Arrays.stream(statuses)).sorted().mapToObj(String::valueOf).toArray(String[]::new));
    // Do the query
    apiResponse = get("get_tests/" + runId + "&status_id=" + listOfStatus);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestRunException());
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRTest>>() {
    }, choices);
    return responseObjectModel;
  }
}
