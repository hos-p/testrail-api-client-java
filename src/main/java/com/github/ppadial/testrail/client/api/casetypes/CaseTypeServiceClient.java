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

package com.github.ppadial.testrail.client.api.casetypes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRCaseType;
import java.util.List;

/**
 * Testrail Client for API Case Types Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-case-types">reference-case-types</a>
 * @since 0.1.0
 */
public final class CaseTypeServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance of the priorities api client.
   *
   * @param apiClient the apiclient to use
   */
  public CaseTypeServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns a list of available case types.
   *
   * @return list of available case types
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final List<TRCaseType> getCaseTypes()
      throws TestRailException {

    final ApiResponse apiResponse;
    final List<TRCaseType> responseObjectModel;

    // Do the query
    apiResponse = get("get_case_types");

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRCaseType>>() {
    }, null);
    return responseObjectModel;
  }
}
