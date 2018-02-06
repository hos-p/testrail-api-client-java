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

package com.github.ppadial.testrail.client.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for all test rail client services implementations.
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public abstract class TestRailServiceBase {

  private static final Logger LOG = LoggerFactory.getLogger(TestRailServiceBase.class);

  //underlying api client
  protected ApiClient apiClient;
  //(de)-serializes objects to/from json
  protected ObjectMapper objectMapper;

  /**
   * creates a new instance.
   * @param apiClient apiclient to use
   * @since 0.1.0
   */
  public TestRailServiceBase(ApiClient apiClient) {
    LOG.debug(":: Constructor:: called");
    this.apiClient = apiClient;
    LOG.debug("Initialising ObjectMapper configuration");
    objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    //TODO: should probably remove this
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  /**
   * .
   * @param apiResponse .
   * @param choices .
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  protected void handleApiResponse(final ApiResponse apiResponse, Map<HttpStatusCode, TestRailException> choices)
      throws TestRailException {
    if (!apiResponse.getHttpStatusCode().is2xxSuccessful()) {
      handleApiResponseKoChoices(apiResponse, choices);
    }
  }

  /**
   * .
   * @param apiResponse .
   * @param clazz .
   * @param choices .
   * @param <T> .
   * @return .
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  protected <T> T handleApiResponse(ApiResponse apiResponse, Class<T> clazz,
      Map<HttpStatusCode, TestRailException> choices) throws TestRailException {
    T type = null;

    // if Response is OK, parse and return type
    if (apiResponse.getHttpStatusCode().is2xxSuccessful()) {
      type = handleApiResponseOk(apiResponse, clazz);
    } else { // if Response is Not OK, then parse choices
      handleApiResponseKoChoices(apiResponse, choices);
    }

    return type;
  }

  /**
   * .
   * @param apiResponse .
   * @param typeReference .
   * @param choices .
   * @param <T> .
   * @return .
   * @throws TestRailException An error in the connection with testrail
   */
  protected <T> T handleApiResponse(ApiResponse apiResponse, TypeReference<T> typeReference,
      Map<HttpStatusCode, TestRailException> choices)
      throws TestRailException {
    T type = null;

    // if Response is OK, parse and return type
    if (apiResponse.getHttpStatusCode().is2xxSuccessful()) {
      type = handleApiResponseOk(apiResponse, typeReference);
    } else { // if Response is Not OK, then parse choices
      handleApiResponseKoChoices(apiResponse, choices);
    }

    return type;
  }

  private <T> T handleApiResponseOk(final ApiResponse apiResponse, final Class<T> clazz)
      throws TestRailException {
    final T type;
    try {
      type = objectMapper.readValue(apiResponse.getBody(), clazz);
    } catch (IOException ioException) {
      throw new TestRailException(ioException);
    }
    return type;
  }

  private <T> T handleApiResponseOk(final ApiResponse apiResponse, final TypeReference<T> typeReference)
      throws TestRailException {
    final T type;
    try {
      type = objectMapper.readValue(apiResponse.getBody(), typeReference);
    } catch (IOException ioException) {
      throw new TestRailException(ioException);
    }
    return type;
  }

  private void handleApiResponseKoChoices(final ApiResponse apiResponse,
      final Map<HttpStatusCode, TestRailException> choices) throws TestRailException {
    if (choices == null || choices.isEmpty()) {
      throw new TestRailException("No Choices provided and response was not OK");
    }

    if (choices.containsKey(apiResponse.getHttpStatusCode())) {
      TestRailException testRailException = choices.get(apiResponse.getHttpStatusCode());
      if (testRailException != null) {
        throw (testRailException);
      } else {
        throw new TestRailException("Choice provided but not Exception specified, so raising a generic exception");
      }
    } else {
      throw new TestRailException("Response with code " + apiResponse.getHttpStatusCode().value());
    }
  }

  /**
   * Do a Post Operation on the TestRail service.
   *
   * @param uriSuffix api uri subfix to send POST
   * @return an api response
   * @throws TestRailException An Error during operation
   * @since 0.1.0
   */
  protected ApiResponse post(String uriSuffix) throws TestRailException {
    final ApiResponse apiResponse;
    try {
      apiResponse = apiClient.doPost(uriSuffix, null);
    } catch (ApiCallException apiCallException) {
      throw new TestRailException(apiCallException);
    }
    return apiResponse;
  }

  /**
   * Do a Post Operation on the TestRail service.
   *
   * @param uriSuffix api uri subfix to send POST
   * @param data map with properties to send in the request body
   * @return an api response
   * @throws TestRailException An Error during operation
   * @since 0.1.0
   */
  protected ApiResponse post(String uriSuffix, Map<String, ? extends Object> data) throws TestRailException {
    final ApiResponse apiResponse;
    try {
      apiResponse = apiClient.doPost(uriSuffix, objectMapper.writeValueAsString(data));
    } catch (JsonProcessingException jsonProcessingException) {
      throw new TestRailException(jsonProcessingException);
    } catch (ApiCallException apiCallException) {
      throw new TestRailException(apiCallException);
    }
    return apiResponse;
  }

  /**
   * Do a GET Operation on the TestRail service.
   *
   * @param uriSuffix api uri subfix to send GET
   * @return an api response
   * @throws TestRailException An Error during operation
   * @since 0.1.0
   */
  protected ApiResponse get(String uriSuffix) throws TestRailException {
    final ApiResponse apiResponse;
    try {
      apiResponse = apiClient.doGet(uriSuffix);
    } catch (ApiCallException apiCallException) {
      throw new TestRailException(apiCallException);
    }
    return apiResponse;
  }
}
