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

package com.github.ppadial.testrail.client;

import com.github.ppadial.testrail.client.apiClient.ApiClient;
import java.net.URI;

/**
 * TestRail clients manager class.
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class TestRailClientManager {

  /**
   * Gets a TestRail client with retry and anti-flooding.
   *
   * @param testrailUri Testrail base url (http://your.domain)
   * @param username username
   * @param password password or token
   * @param retries number of retries (between 0 and 20)
   * @param antiFloodMs number of delay between requests
   * @return a TestRail client
   * @since 0.1.0
   */
  public TestRailClient getClient(final URI testrailUri, final String username, final String password,
      final Integer retries, final Long antiFloodMs) {

    // Creates the TestRail Client based on the configuration
    ApiClient apiClient = new ApiClient(testrailUri.toString(), username, password);

    if (antiFloodMs != null) {
      apiClient.enableAntiFlooding(antiFloodMs);
    }

    if (retries != null) {
      apiClient.enableRetryOnFailure(retries);
    }
    return new TestRailClient(apiClient);
  }

  /**
   * Gets a TestRail client with retry enabled.
   *
   * @param testrailUri Testrail base url (http://your.domain)
   * @param username username
   * @param password password or token
   * @param retries number of retries (between 0 and 20)
   * @return a TestRail client with retry enabled
   * @since 0.1.0
   */
  public TestRailClient getClient(final URI testrailUri, final String username, final String password,
      final Integer retries) {
    return getClient(testrailUri, username, password, retries, null);
  }

  /**
   * Gets a TestRail client with anti-flood enabled.
   *
   * @param testrailUri Testrail base url (http://your.domain)
   * @param username username
   * @param password password or token
   * @param antiFloodMs number of delay between requests
   * @return a TestRail client with retry enabled
   * @since 0.1.0
   */
  public TestRailClient getClient(final URI testrailUri, final String username, final String password,
      final Long antiFloodMs) {
    return getClient(testrailUri, username, password, null, antiFloodMs);
  }

  /**
   * Gets a TestRail client without retry and anti-flood.
   *
   * @param testrailUri Testrail base url (http://your.domain)
   * @param username username
   * @param password password or token
   * @return a TestRail client without any enhancement
   * @since 0.1.0
   */
  public TestRailClient getClient(final URI testrailUri, final String username, final String password) {
    return getClient(testrailUri, username, password, null, null);
  }
}

