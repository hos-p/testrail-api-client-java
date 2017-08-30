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

package com.github.ppadial.testrail.client.api.runs;

import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRRun;
import java.util.HashMap;
import java.util.Map;

/**
 * Testrail Client for TRPlan API Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-plans">reference-plans</a>
 * @since 0.1.0
 */
public final class RunServiceClient extends TestRailServiceBase {

  public RunServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing test run. Please see get_tests for the list of included tests in this run.
   *
   * @param runId The ID of the test run
   * @return test run
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRRun getRun(final int runId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRRun responseObjectModel;

    // Do the query
    apiResponse = get("get_run/" + runId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestRunException());
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRRun.class, choices);
    return responseObjectModel;
  }
}
