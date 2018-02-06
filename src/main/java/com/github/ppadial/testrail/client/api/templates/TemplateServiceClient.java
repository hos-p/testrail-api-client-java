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

package com.github.ppadial.testrail.client.api.templates;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.projects.InvalidOrUnknownProjectException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRTemplate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Testrail Client for APIL Template Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-templates">reference-templates</a>
 * @since 0.1.0
 */
public final class TemplateServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance of the template api client.
   *
   * @param apiClient the apiclient to use
   */
  public TemplateServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns a list of available templates (requires Testrail 5.2 or later).
   *
   * @param projectId the ID of the project
   * @return a list of templates
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRTemplate> getTemplates(final int projectId)
      throws TestRailException {

    final ApiResponse apiResponse;
    final List<TRTemplate> responseObjectModel;

    // Do the query
    apiResponse = get("get_templates/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRTemplate>>() {
    }, choices);
    return responseObjectModel;
  }
}
