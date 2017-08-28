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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.configuration.GlobalConfiguration;
import org.mockito.internal.progress.ThreadSafeMockingProgress;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.api.support.ClassLoaderUtil;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@PrepareForTest({ ApiClient.class, LoggerFactory.class })
@PowerMockIgnore({ "javax.net.ssl.*" })
public class ApiClientTest extends PowerMockTestCase {

  @Mock(answer = Answers.RETURNS_DEEP_STUBS) private HttpClient httpClient;
  @Mock(answer = Answers.RETURNS_DEEP_STUBS) private HttpResponse httpResponse;
  @Mock HttpUriRequest httpUriRequest;

  @BeforeMethod(alwaysRun=true)
  public void injectDoubles() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void createNewInstance() throws Exception {
    final ApiClient apiClient = new ApiClient("http://url.url", "user", "password");
    assertThat(apiClient).isInstanceOf(ApiClient.class);
  }

  @Test (expectedExceptions = RuntimeException.class)
  public void anErrorOnTheConstructor_ShouldLogAndThrowAnException() throws Exception {
    PowerMockito.mockStatic(LoggerFactory.class);
    final Logger logger = Mockito.mock(Logger.class);
    Mockito.doThrow(new RuntimeException()).when(logger).debug("Created API client for {}", "http://force-error.url");
    Mockito.when(LoggerFactory.getLogger(Mockito.any(Class.class))).thenReturn(logger);

    // Action
    new ApiClient("http://force-error.url", "user", "password");
  }

  @Test
  public void invokeHttpPost_WithSuccess_ShouldReturnsResponseString() throws Exception {
    // Given
    final String urlSuffix = "/test";
    final String jsonData = "{}";
    Mockito.when(httpResponse.getStatusLine().getStatusCode())
        .thenReturn(200);
    Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
    final ApiClient apiClient = new ApiClient(httpClient);

//    final String responseString = apiClient.doPost(urlSuffix, jsonData);

//    assertThat(responseString).isNotNull().isEmpty();

  }

  @Test (expectedExceptions = ApiCallException.class)
  public void invokeHttpGet_WithFailure_ShouldThrowAnException() throws Exception {
    // Given
    final String urlSuffix = "/test";
    Mockito.when(httpResponse.getStatusLine().getStatusCode())
        .thenReturn(429);
    Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
    final ApiClient apiClient = new ApiClient(httpClient);

    apiClient.doGet(urlSuffix);
  }

  @Test
  public void invokeHttpGet_WithSuccess_ShouldReturnsResponseString() throws Exception {
    final String urlSuffix = "/test";
    Mockito.when(httpResponse.getStatusLine().getStatusCode())
        .thenReturn(200);
    Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
    final ApiClient apiClient = new ApiClient(httpClient);

//    final String responseString = apiClient.doGet(urlSuffix);
//
//    assertThat(responseString).isNotNull().isEmpty();
  }

  @Test
  public void invokeHttpGet_WithThreadSleepException() throws Exception {
    // Given
    final String urlSuffix = "/test";
    Mockito.when(httpResponse.getStatusLine().getStatusCode())
        .thenReturn(429).thenReturn(200);
    Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
    final ApiClient apiClient = new ApiClient(httpClient);
    apiClient.enableRetryOnFailure(1);
    apiClient.enableAntiFlooding(Long.valueOf(1));
    // We do not want to mock all methods, only specific methods, such as Thread.sleep().
    // Use spy() instead of mockStatic().
    PowerMockito.spy(Thread.class);
    // These two lines are tightly bound.
    PowerMockito.doThrow(new InterruptedException()).when(Thread.class);
    Thread.sleep(Mockito.anyLong());

    // When
//    final String response = apiClient.doGet(urlSuffix);

    // Then
//    assertThat(response).isNotNull().isEmpty();

  }

  @Test
  public void retryOnFailureCapability_WithOneFailureAndOneOK_ShouldReturnsOK() throws Exception {
    // Given
    final String urlSuffix = "/test";
    Mockito.when(httpResponse.getStatusLine().getStatusCode())
        .thenReturn(429).thenReturn(200);
    Mockito.when(httpClient.execute(Mockito.any())).thenReturn(httpResponse);
    final ApiClient apiClient = new ApiClient(httpClient);
    apiClient.enableRetryOnFailure(1);

    // When
//    final String response = apiClient.doGet(urlSuffix);
//
//     Then
//    assertThat(response).isNotNull().isEmpty();
  }

  @Test
  public void retryOnFailureCapability_WithTwoFailures_ShouldReturnsKO() throws Exception {
    // Given
    final String urlSuffix = "/test";
    Mockito.when(httpResponse.getStatusLine().getStatusCode())
        .thenReturn(429).thenReturn(429);
    Mockito.when(httpClient.execute(httpUriRequest)).thenReturn(httpResponse);
    final ApiClient apiClient = new ApiClient(httpClient);
    apiClient.enableRetryOnFailure(1);

    // When
    Throwable thrown = catchThrowable( () -> {apiClient.doGet(urlSuffix);});

    // Then
    assertThat(thrown).isInstanceOf(ApiCallException.class)
        .hasMessageContaining(
            "2 executions and 1 retries");
  }

  @Test
  public void enableRetryOnFaliure_WithNumOfRetries() throws Exception {
    final ApiClient apiClient = new ApiClient(httpClient);
    final Integer numOfRetries = 1;

    apiClient.enableRetryOnFailure(numOfRetries);
    // TODO: We don't have a property to verify the set-ed value!
  }

  @Test
  public void enableRetryOnFaliure_WithNumOfRetriesAndMillisBetweenRetries() throws Exception {
    final ApiClient apiClient = new ApiClient(httpClient);
    final Integer numOfRetries = 1;
    final Integer millisBetweenRetries = 450;

    apiClient.enableRetryOnFailure(numOfRetries, millisBetweenRetries);
    // TODO: We don't have a property to verify the set-ed value!
  }

  @Test
  public void enableAntiFlooding_WithAntiFloodValue() throws Exception {
    final ApiClient apiClient = new ApiClient(httpClient);
    final Long antiFloodValue = Long.valueOf(40000);

    apiClient.enableAntiFlooding(antiFloodValue);
    // TODO: We don't have a property to verify the set-ed value!
  }

  @Test
  public void internalBuilder_ForCoverage() throws Exception {
    ApiClient apiClient = new ApiClient.Builder()
        .credentials("user", "password")
        .testRailInstanceUrl("http://url.url")
        .withRetry(1)
        .withRetry(1, 1)
        .withAntiFlood(Long.valueOf(1))
        .build();
  }

  private static class MockitoStateCleaner implements Runnable {
    public void run() {
      clearMockProgress();
      clearConfiguration();
    }

    private void clearMockProgress() {
      clearThreadLocalIn(ThreadSafeMockingProgress.class);
    }

    private void clearConfiguration() {
      clearThreadLocalIn(GlobalConfiguration.class);
    }

    private void clearThreadLocalIn(Class<?> cls) {
      Whitebox.getInternalState(cls, ThreadLocal.class).set(null);
      final Class<?> clazz = ClassLoaderUtil.loadClass(cls, ClassLoader.getSystemClassLoader());
      Whitebox.getInternalState(clazz, ThreadLocal.class).set(null);
    }
  }
}