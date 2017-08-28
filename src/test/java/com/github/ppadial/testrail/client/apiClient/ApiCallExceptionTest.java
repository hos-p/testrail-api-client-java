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

import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ApiCallExceptionTest {

  @Test
  public void createNewInstance_WithNoArgs() throws Exception {
    Assertions.assertThat(new ApiCallException()).isInstanceOf(ApiCallException.class);
  }

  @Test
  public void createNewInstance_WithThrowable() throws Exception {
    assertThat(new ApiCallException(new Throwable())).isInstanceOf(ApiCallException.class);
  }

  @Test
  public void createNewInstance_WithMessage() throws Exception {
    assertThat(new ApiCallException("an error message")).isInstanceOf(ApiCallException.class);
  }

  @Test
  public void createNewInstance_WithMessageAndThrowable() throws Exception {
    assertThat(new ApiCallException("message", new Throwable())).isInstanceOf(ApiCallException.class);
  }

  @Test
  public void createNewInstance_WithMessageAndException() throws Exception {
    assertThat(new ApiCallException("message", new Exception())).isInstanceOf(ApiCallException.class);
  }

  @Test
  public void createNewInstance_WithMessageAndThrowableAndEnableSuppresionAndWritableStackTrace() throws Exception {
    assertThat(new ApiCallException("message", new Throwable(), true, true)).isInstanceOf(ApiCallException.class);
  }

}