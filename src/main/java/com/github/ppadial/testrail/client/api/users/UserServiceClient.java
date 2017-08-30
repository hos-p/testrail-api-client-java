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

package com.github.ppadial.testrail.client.api.users;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Testrail Client for TRPlan API Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-users">reference-users</a>
 * @since 0.1.0
 */
public final class UserServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance of the user api client.
   *
   * @param apiClient the apiclient to use
   */
  public UserServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing user.
   *
   * @param userId The ID of the user
   * @return the user ID
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRUser getUser(final int userId) throws TestRailException {
    final ApiResponse apiResponse;
    final TRUser trUser;

    // Do the query
    apiResponse = get("get_user/" + userId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.NOT_FOUND, new InvalidOrUnknownUserException("invalid or unknown user"));
        }};

    // Handle response
    trUser = handleApiResponse(apiResponse, TRUser.class, choices);

    // return the user if everything is Ok
    return trUser;
  }

  /**
   * Returns an existing user by his/her email address.
   *
   * @param email the email address to get the user for
   * @return the user
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final TRUser getUserByEmail(final String email) throws TestRailException {
    final ApiResponse apiResponse;
    final TRUser trUser;

    // Do the query
    apiResponse = get("get_user_by_email&email=" + email);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {{
          put(HttpStatusCode.NOT_FOUND, new UnknownEmailAddressException("Email not found in your testrail instance"));
          put(HttpStatusCode.BAD_REQUEST,
              new InvalidEmailAddressException("Email is not a valid email (from testrail validation)"));
        }};

    // Handle response
    trUser = handleApiResponse(apiResponse, TRUser.class, choices);

    // return the user if everything is Ok
    return trUser;
  }

  /**
   * Returns a list of users.
   *
   * @return a list of users
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRUser> getUsers() throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRUser> trUser;

    // Do the query
    apiResponse = get("get_users");

    // Handle response
    trUser = handleApiResponse(apiResponse, new TypeReference<List<TRUser>>() {
    }, null);

    // return the user if everything is Ok
    return trUser;
  }
}
