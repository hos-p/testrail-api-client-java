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

package com.github.ppadial.testrail.client.api.sections;

import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.projects.InvalidOrUnknownProjectException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRSection;
import java.util.HashMap;
import java.util.Map;
import org.assertj.core.util.Strings;

/**
 * Testrail Client for API: Sections Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-sections">reference-sections</a>
 * @since 0.1.0
 */
public final class SectionServiceClient extends TestRailServiceBase {

  public SectionServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Creates a new section.
   *
   * @param projectId The ID of the project
   * @param description The description of the section (added with TestRail 4.0)
   * @param suiteId The ID of the test suite (ignored if the project is operating in single suite mode, required
   * otherwise)
   * @param parentId The ID of the parent section (to build section hierarchies)
   * @param name The name of the section (required)
   * @return created section
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRSection addSection(final int projectId, final String description, final Integer suiteId,
      final Integer parentId, final String name) throws TestRailException {
    final ApiResponse apiResponse;
    final TRSection responseObjectModel;

    Map<String, String> body = new HashMap<String, String>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    } else {
      // TODO: Change to InvalidArgumentException of lang3
      throw new TestRailException("Parameter name can't be null or empty.");
    }
    if (suiteId != null) {
      body.put("suite_id", suiteId.toString());
    }
    if (parentId != null) {
      body.put("parent_id", parentId.toString());
    }
    if (!Strings.isNullOrEmpty(description)) {
      body.put("description", description);
    }

    // Do the query
    apiResponse = get("add_section/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.BAD_REQUEST,
              new InvalidOrUnknownProjectException("Invalid or unknown project or test suite"));
          put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
        }};

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRSection.class, choices);
    return responseObjectModel;
  }

  /**
   * Updates an existing section (partial updates are supported, i.e. you can submit and update specific fields only).
   *
   * @param sectionId The ID of the section
   * @param description The description of the section (added with TestRail 4.0)
   * @param name The name of the section
   * @return the updated section
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRSection updateConfigGroup(final int sectionId, final String description, final String name)
      throws TestRailException {

    final ApiResponse apiResponse;
    final TRSection responseObjectModel;

    Map<String, String> body = new HashMap<String, String>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    }
    if (!Strings.isNullOrEmpty(description)) {
      body.put("description", description);
    }

    // Do the query
    apiResponse = post("update_section/" + sectionId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownSectionException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to modify sections or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRSection.class, choices);
    return responseObjectModel;
  }

  /**
   * Deletes an existing section.
   * Deleting a section cannot be undone and also deletes all related test cases as well as active tests and
   * results, i.e. tests and results that weren't closed (archived) yet.
   * @param sectionId The ID of the section
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final void deleteSection(final int sectionId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_section/" + sectionId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownSectionException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to delete sections or test cases or no access to the project"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }
}
