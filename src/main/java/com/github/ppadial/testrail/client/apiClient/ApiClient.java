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

package com.github.ppadial.testrail.client.apiClient;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Client to talk to TestRail API end points
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class ApiClient {

  private static final Logger LOG = LoggerFactory.getLogger(ApiClient.class);
  private static Long MIN_MS_BETWEEN_RETRIES = Long.valueOf(250) /* 0,25s */;
  private static Long MAX_MS_BETWEEN_RETRIES = Long.valueOf(60000) /* 1m */;
  private static Integer MIN_RETRY_NUM = 1;
  private static Integer MAX_RETRY_NUM = 20;
  private static Long MIN_FLOOD_VALUE = Long.valueOf(1000); /* 1s */
  private static Long MAX_FLOOD_VALUE = Long.valueOf(60000); /* 1m */
  private HttpClient httpClient;
  private String url;
  private String username;
  private String password;
  private boolean retryOnFailureEnabled = false; /*disabled by default*/
  private Integer numOfRetries = MIN_RETRY_NUM;
  private Long millisBetweenRetries = MIN_MS_BETWEEN_RETRIES;
  private boolean antiFloodEnabled = false; /*disabled by default*/
  private Long antiFloodValue = MIN_FLOOD_VALUE;

  /**
   * Creates a new instance of the object.
   *
   * @param url url of the service
   * @param user username login
   * @param password login password or token
   * @since 0.1.0
   */
  public ApiClient(String url, String user, String password) {
    try {
      LOG.debug(":: Constructor method ::");
      this.url = url + "/index.php?/api/v2/";
      this.username = user;
      this.password = password;

      Base64 base64 = new Base64();
      List<Header> headerList = new ArrayList<Header>();
      headerList.add(new BasicHeader("Content-Type", "application/json"));
      headerList.add(new BasicHeader("Authorization",
          "Basic " + base64.encodeToString(
              (this.username + ":" + this.password).getBytes(StandardCharsets.UTF_8))));

      httpClient = HttpClientBuilder.create().setDefaultHeaders(headerList).build();
      LOG.debug("Created API client for {}", url);
    } catch (Exception e) {
      LOG.error(e.getMessage());
      throw new RuntimeException(e);
    }
  }

  /**
   * For testing pourposes constructor.
   * @param httpClient http client to use
   * @since 0.1.0
   */
  public ApiClient(HttpClient httpClient) {
    this.url = url + "/index.php?/api/v2/";
    this.httpClient = httpClient;
  }

  /**
   * Enable the retry-mode per request.
   *
   * @param numOfRetries number of retries before mark a failure (min {@code MIN_RETRY_NUM}, max {@code MAX_RETRY_NUM})
   * @since 0.1.0
   */
  public void enableRetryOnFailure(final Integer numOfRetries) {
    this.retryOnFailureEnabled = true;
    this.numOfRetries =
        (numOfRetries < MIN_RETRY_NUM) ? MIN_RETRY_NUM
            : (numOfRetries > MAX_RETRY_NUM) ? MAX_RETRY_NUM : numOfRetries;
  }

  /**
   * Enable the retry-mode per request.
   *
   * @param numOfRetries number of retries before mark a failure (min {@code MIN_RETRY_NUM}, max {@code MAX_RETRY_NUM})
   * @param millisBetweenRetries number of milliseconds between retries
   * @since 0.1.0
   */
  public void enableRetryOnFailure(final Integer numOfRetries, final Integer millisBetweenRetries) {
    this.retryOnFailureEnabled = true;
    this.numOfRetries =
        (numOfRetries < MIN_RETRY_NUM) ? MIN_RETRY_NUM
            : (numOfRetries > MAX_RETRY_NUM) ? MAX_RETRY_NUM : numOfRetries;
    this.millisBetweenRetries =
        (millisBetweenRetries < MIN_MS_BETWEEN_RETRIES) ? MIN_MS_BETWEEN_RETRIES
            : (millisBetweenRetries > MAX_MS_BETWEEN_RETRIES) ? MAX_MS_BETWEEN_RETRIES : millisBetweenRetries;
  }

  /**
   * Enable the antiflood-mode per request.
   *
   * @param antiFloodValue sleep before each call to the server (min {@code MIN_FLODD_VALUE}, max {@code
   * MAX_FLOOD_VALUE})
   * @since 0.1.0
   */
  public void enableAntiFlooding(final Long antiFloodValue) {
    this.antiFloodEnabled = true;
    this.antiFloodValue =
        (antiFloodValue < MIN_FLOOD_VALUE) ? MIN_FLOOD_VALUE
            : (antiFloodValue > MAX_FLOOD_VALUE) ? MAX_FLOOD_VALUE : antiFloodValue;
  }

  /**
   * Do an HTTP Get call against the TestRail instance.
   *
   * @param uriSuffix suffix url to query
   * @return response object
   * @throws ApiCallException An error during the call to the service
   * @since 0.1.0
   */
  public ApiResponse doGet(String uriSuffix) throws ApiCallException {
    LOG.debug("Invoking {}", uriSuffix);
    final HttpGet httpGet = new HttpGet(url + uriSuffix);
    return doRequest(httpGet);
  }

  /**
   * Do an HTTP Post call against the TestRail instance.
   *
   * @param uriSuffix suffix url to query
   * @param jsonData json request body data
   * @return response object
   * @throws ApiCallException An error during the call to the service
   * @since 0.1.0
   */
  public ApiResponse doPost(String uriSuffix, String jsonData) throws ApiCallException {
    LOG.debug("Invoking {} with jsonData {}", uriSuffix, jsonData);
    final HttpPost httpPost = new HttpPost(url + uriSuffix);
    // If we have json body data to send
    if (!Strings.isNullOrEmpty(jsonData)) {
      final StringEntity reqEntity;
      try {

        reqEntity = new StringEntity(jsonData);
        reqEntity.setContentType("application/json");
      } catch (UnsupportedEncodingException unsupportedEncodingException) {
        throw new ApiCallException(unsupportedEncodingException);
      }
      httpPost.setEntity(reqEntity);
    }
    return doRequest(httpPost);
  }

  /**
   * Do an HTTP Post call against the TestRail instance.
   *
   * @param uriSuffix suffix url to query
   * @return response object
   * @throws ApiCallException An error during the call to the service
   * @since 0.1.0
   */
  public ApiResponse doPost(String uriSuffix) throws ApiCallException {
    LOG.debug("Invoking {} without body", uriSuffix);
    final HttpPost httpPost = new HttpPost(url + uriSuffix);
    return doRequest(httpPost);
  }

  private ApiResponse doRequest(HttpUriRequest httpUriRequest) throws ApiCallException {
    LOG.debug("method called with AntiFlood={} RetryOnFailure={}", antiFloodEnabled, retryOnFailureEnabled);
    ApiResponse apiResponse;
    HttpResponse httpResponse;
    int numOfExecutions = 0;

    try {
      //do {
      httpResponse = httpClient.execute(httpUriRequest);
      apiResponse = ApiResponseMapper.from(httpResponse); // parse
//        numOfExecutions++; // increments the executions

//        // Anti-Flood capability
//        // applies after each request if enabled
//        if (antiFloodEnabled) {
//          LOG.debug("Anti flood enabled, waiting: {} seconds after the call", antiFloodValue / 1000);
//          try {
//            Thread.sleep(antiFloodValue);
//          } catch (InterruptedException antiFloodInterruptedException) {
//            LOG.error("Error while applying antiflood delay before request with message {}",
//                antiFloodInterruptedException.getMessage());
//          }
//        }
//        // End of Anti-Flood capability
//
//        // Check for the response of the request
//        final int statusCode = httpResponse.getStatusLine().getStatusCode();
//        // IF response is OK, get out
//        if (apiResponse.getHttpStatusCode().is2xxSuccessful()) {
//          if (retryOnFailureEnabled) {
//            LOG.debug("the request has been OK after {} executions with {} retries", numOfExecutions,
//                numOfExecutions - 1);
//          }
//          break; // return httpresponse
//        } else { // In case KO...
//          // In case of ERROR, if RetryOnFailure is not enabled, raise an exception
//          if (!retryOnFailureEnabled) {
//            throw new ApiCallException(
//                "Received status code " + apiResponse.getHttpStatusCode().value() + " with content '"
//                    + apiResponse.getBody() + "'");
//          } else {
//            // RetryOnFailure Enabled
//            // in this case if i have consume all the retries, raise an exception
//            if (numOfExecutions > this.numOfRetries) {
//              throw new ApiCallException(
//                  "After " + numOfExecutions + " executions and " + (numOfExecutions - 1) + " retries"
//                      + " the Request still failing with  status code " + apiResponse.getHttpStatusCode().value()
//                      + " and content '" + apiResponse.getBody() + "'");
//            } else {
//              // Little timing between retries
//              try {
//                Thread.sleep(this.millisBetweenRetries);
//              } catch (InterruptedException e) {
//                LOG.error("Error during wait between retries with message {}", e.getMessage());
//              }
//            }
//          }
//        }
//      } while (retryOnFailureEnabled);
    } catch (Exception exception) {
      throw new ApiCallException(exception.getCause());
    }

    return apiResponse;
  }

  /**
   * Builder class for API Client class.
   *
   * @author Paulino Padial
   * @since 0.1.0
   */
  public static class Builder {

    private String testRailUrl;
    private String username;
    private String accessKey;
    private Boolean retryOnFailure = false;
    private Integer numOfRetries;
    private Long millisBetweenRetries;
    private Boolean antiFloodEnabled = false;
    private Long antiFloodValue;

    /**
     * Sets TestRail instance url.
     *
     * @param testRailUrl test rail instance url to connect
     * @return Builder pointer
     * @since 1.2.1
     */
    public Builder testRailInstanceUrl(String testRailUrl) {
      this.testRailUrl = testRailUrl;
      return this;
    }

    /**
     * Sets the credentials to connects to the TestRail instance.
     *
     * @param username the user name as login
     * @param accessKey the access key, can be the password or the API Access Token (recommended)
     * @return Builder pointer
     * @since 1.2.1
     */
    public Builder credentials(String username, String accessKey) {
      this.username = username;
      this.accessKey = accessKey;
      return this;
    }

    /**
     * Enable the retry-mode per request.
     *
     * @param numOfRetries number of retries before mark a failure (min 1, less than 1 set to 1, max 20)
     * @return Builder pointer
     * @since 1.2.1
     */
    public Builder withRetry(final Integer numOfRetries) {
      this.retryOnFailure = true;
      this.numOfRetries = numOfRetries;
      return this;
    }

    /**
     * Enable the retry-mode per request.
     *
     * @param numOfRetries number of retries before mark a failure (min 1, less than 1 set to 1, max 20)
     * @param millisBetweenRetries number of milliseconds to wait before try again (min 250, max 60000)
     * @return Builder pointer
     * @since 1.2.1
     */
    public Builder withRetry(final Integer numOfRetries, final Integer millisBetweenRetries) {
      this.retryOnFailure = true;
      this.numOfRetries = numOfRetries;
      this.millisBetweenRetries = Long.valueOf(millisBetweenRetries);
      return this;
    }

    /**
     * Enable the anti-flood mode per request.
     *
     * @param antiFloodValue time to wait before each new request to avoid saturate the server
     * @return Builder pointer
     * @since 1.2.1
     */
    public Builder withAntiFlood(final Long antiFloodValue) {
      this.antiFloodEnabled = true;
      this.antiFloodValue = antiFloodValue;
      return this;
    }

    /**
     * Build the API Client Object.
     *
     * @return ApiClient object as a result of the Builder pattern
     * @since 1.2.1
     */
    public ApiClient build() {
      LOG.debug("Building an instance of ApiClient");
      ApiClient apiClient = new ApiClient(testRailUrl, username, accessKey);
      if (retryOnFailure) {
        LOG.debug("adding retry support");
        apiClient.enableRetryOnFailure(numOfRetries);
      }
      if (antiFloodEnabled) {
        LOG.debug("adding anti-flooding support");
        apiClient.enableAntiFlooding(antiFloodValue);
      }
      return apiClient;
    }
  }

}