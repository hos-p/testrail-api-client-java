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

package com.github.ppadial.testrail.client.api.results;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.cases.InvalidOrUnknownTestCaseException;
import com.github.ppadial.testrail.client.api.runs.InvalidOrUnknownTestRunException;
import com.github.ppadial.testrail.client.api.tests.InvalidOrUnknownTestException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

/**
 * Testrail Client for TRPlan API Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-plans">reference-plans</a>
 * @since 0.1.0
 */
public final class ResultServiceClient extends TestRailServiceBase {

  public ResultServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Gets the results for a testcase.
   *
   * @param testId The ID of the test
   * @param limit the number of results to get, 0 if you want all of them
   * @return list of results for the test case
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRResult> getResults(final int testId, final Integer limit)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRResult> responseObjectModel;

    String url = "get_results/" + testId;
    if (limit != null && limit > 0) {
      url += "&limit=" + limit.toString();
    }

    // Do the query
    apiResponse = get(url);

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRResult>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * .
   * @return .
   * @throws TestRailException An error in the connection with testrail
   */
  public final List<TRResult> getResultsFilterByStatusId() throws TestRailException {
    throw new NotImplementedException("method not implemented");
  }

  /**
   * Returns a list of test results for a test run. Requires TestRail 4.0 or later.
   *
   * @param runId The ID of the test run (mandatory)
   * @param createdAfter Only return test results created after this date (as UNIX timestamp).
   * @param createdBefore Only return test results created before this date (as UNIX timestamp).
   * @param createdBy A comma-separated list of creators (user IDs) to filter by.
   * @param limit Limit the result to :limit test results. Use :offset to skip records.
   * @param statuses A comma-separated list of status IDs to filter by.
   * @return list of test results
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRResult> getResultsForRun(final int runId, final Long createdAfter, final Long createdBefore,
      final List<Integer> createdBy, final Integer limit, final List<Integer> statuses)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRResult> responseObjectModel;

    // Do the query
    apiResponse = get(
        addLimitFilter(
            addCreatedAfterFilter(
                addCreatedBeforeFilter(
                    addCreatedByFilter(
                        addStatusFilter(
                            "get_results_for_run/" + runId
                            , statuses
                        )
                        , createdBy
                    )
                    , createdBefore
                )
                , createdAfter
            )
            , limit)
    );

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestRunException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRResult>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Returns a list of test results for a test run and case combination. <p>The difference to get_results is that this
   * method expects a test run + test case instead of a test. In TestRail, tests are part of a test run and the test
   * cases are part of the related test suite. So, when you create a new test run, TestRail creates a test for each test
   * case found in the test suite of the run. You can therefore think of a test as an “instance” of a test case which
   * can have test results, comments and a test status. Please also see TestRail's getting started guide for more
   * details about the differences between test cases and tests. </p>
   *
   * @param runId The ID of the test run
   * @param caseId The ID of the test case
   * @param limit the number of results to gets, 0 if you want all of them
   * @return list of test results
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRResult> getResultsForCase(final int runId, final int caseId, final Integer limit)
      throws TestRailException {

    final ApiResponse apiResponse;
    final List<TRResult> responseObjectModel;

    // Do the query
    apiResponse = get(addLimitFilter("get_results_for_case/" + runId + "/" + caseId, limit));

    final Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST,
                new InvalidOrUnknownTestCaseException("invalid or unknown test run or case"));
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRResult>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Adds a new test result, comment or assigns a test. It's recommended to use add_results instead if you plan to add
   * results for multiple tests.
   *
   * @param testId The ID of the test the result should be added to
   * @param statusId The ID of the test status. The built-in system statuses have the following IDs: 1 (Passed) 2
   * (Blocked) 3 (Untested) 4 (Retest) 5 (Failed)
   * @param comment The comment / description for the test result
   * @param version The version or build you tested against
   * @param elapsed The time it took to execute the test, e.g. "30s" or "1m 45s"
   * @param defects A comma-separated list of defects to link to the test result
   * @param assignedToId The ID of a user the test should be assigned to
   * @param customFields Custom fields are supported as well and must be submitted with their system name, prefixed with
   * 'custom_'
   * @return the added test result
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRResult addResult(final int testId, final Integer statusId, final String comment, final String version,
      final Long elapsed, final String defects, final Integer assignedToId, final Map<String, String> customFields)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRResult responseObjectModel;
    final Map<String, String> body = new HashMap<String, String>();
    if (statusId != null) {
      body.put("status_id", statusId.toString());
    }
    if (comment != null && StringUtils.isNotEmpty(comment)) {
      body.put("comment", comment);
    }
    if (version != null && StringUtils.isNotEmpty(version)) {
      body.put("version", version);
    }
    if (elapsed != null) {
      body.put("elapsed", elapsed.toString());
    }
    if (defects != null && StringUtils.isNotEmpty(defects)) {
      body.put("defects", defects);
    }
    if (assignedToId != null) {
      body.put("assignedto_id", assignedToId.toString());
    }
    if (customFields != null) {
      for (Map.Entry<String, String> entry : customFields.entrySet()) {
        body.put(entry.getKey(), entry.getValue());
      }
    }

    // Do the query
    apiResponse = post("add_result/" + testId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add test results or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRResult.class, choices);
    return responseObjectModel;
  }

  /**
   * Adds a new test result, comment or assigns a test (for a test run and case combination). It's recommended to use
   * add_results_for_cases instead if you plan to add results for multiple test cases. The difference to add_result is
   * that this method expects a test run + test case instead of a test. In TestRail, tests are part of a test run and
   * the test cases are part of the related test suite. So, when you create a new test run, TestRail creates a test for
   * each test case found in the test suite of the run. You can therefore think of a test as an “instance” of a test
   * case which can have test results, comments and a test status. Please also see TestRail's getting started guide for
   * more details about the differences between test cases and tests.
   *
   * @param runId The ID of the test run
   * @param caseId The ID of the test case
   * @param statusId The ID of the test status. The built-in system statuses have the following IDs: 1 (Passed) 2
   * (Blocked) 3 (Untested) 4 (Retest) 5 (Failed)
   * @param comment The comment / description for the test result
   * @param version The version or build you tested against
   * @param elapsed The time it took to execute the test, e.g. "30s" or "1m 45s"
   * @param defects A comma-separated list of defects to link to the test result
   * @param assignedToId The ID of a user the test should be assigned to
   * @param customFields Custom fields are supported as well and must be submitted with their system name, prefixed with
   * 'custom_'
   * @return the added test result
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRResult addResultForCase(final int runId, final int caseId, final Integer statusId,
      final String comment,
      final String version, final Long elapsed, final String defects, final Integer assignedToId,
      final Map<String, String> customFields) throws TestRailException {
    final ApiResponse apiResponse;
    final TRResult responseObjectModel;
    final Map<String, String> body = new HashMap<String, String>();

    if (statusId != null) {
      body.put("status_id", statusId.toString());
    }
    if (comment != null && StringUtils.isNotEmpty(comment)) {
      body.put("comment", comment);
    }
    if (version != null && StringUtils.isNotEmpty(version)) {
      body.put("version", version);
    }
    if (elapsed != null) {
      body.put("elapsed", elapsed.toString());
    }
    if (defects != null && StringUtils.isNotEmpty(defects)) {
      body.put("defects", defects);
    }
    if (assignedToId != null) {
      body.put("assignedto_id", assignedToId.toString());
    }
    if (customFields != null) {
      for (Map.Entry<String, String> entry : customFields.entrySet()) {
        body.put(entry.getKey(), entry.getValue());
      }
    }

    // Do the query
    apiResponse = post("add_result_for_case/" + runId + "/" + caseId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownTestException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to add test results or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRResult.class, choices);
    return responseObjectModel;
  }

  /**
   * Adds one or more new test results, comments or assigns one or more tests (using the case IDs). Ideal for test
   * automation to bulk-add multiple test results in one step.
   * @return .
   */
  public final TRResult addResultsForCases() {
    throw new NotImplementedException("Not implemented");
  }

  private final String addLimitFilter(final String currentUrl, final Integer limit) {
    return (limit != null && limit > 0) ?
        currentUrl + "&limit=" + limit.toString() : StringUtils.EMPTY;
  }

  private final String addStatusFilter(final String currentUrl, final List<Integer> statuses) {
    return (statuses != null && !statuses.isEmpty()) ?
        currentUrl + "&status_id=" + StringUtils.join(statuses, ",") : StringUtils.EMPTY;
  }

  private final String addCreatedAfterFilter(final String currentUrl, final Long createdAfter) {
    return (createdAfter != null && createdAfter > 0) ?
        currentUrl + "&created_after=" + createdAfter.toString() : StringUtils.EMPTY;
  }

  private final String addCreatedBeforeFilter(final String currentUrl, final Long createdBefore) {
    return (createdBefore != null && createdBefore > 0) ?
        currentUrl + "&created_before=" + createdBefore.toString() : StringUtils.EMPTY;
  }

  private final String addCreatedByFilter(final String currentUrl, final List<Integer> createdBys) {
    return (createdBys != null && !createdBys.isEmpty()) ?
        currentUrl + "&created_by=" + StringUtils.join(createdBys, ",") : StringUtils.EMPTY;
  }
}
