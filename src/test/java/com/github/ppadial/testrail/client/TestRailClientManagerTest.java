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

package com.github.ppadial.testrail.client;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestRailClientManagerTest {

  private TestRailClientManager testRailClientManager;
  private MocksFactory mocksFactory;
  private TestRailClient testRailClient;

  @BeforeMethod(alwaysRun=true)
  public void injectDoubles() {
    MockitoAnnotations.initMocks(this);
  }

  @BeforeMethod
  public void setUp() throws Exception {
    mocksFactory = new MocksFactory();
  }

  @Test
  public void testGetClient() throws Exception {
    testRailClientManager = new TestRailClientManager();
    testRailClient = testRailClientManager.getClient(new URI("http://a.com"), "a", "a");
    assertThat(testRailClient).isNotNull().isInstanceOf(TestRailClient.class);
  }

  @Test
  public void testGetClient_WithAntiFloodEnabled() throws Exception {
    assertThat(testRailClient).isNotNull().isInstanceOf(TestRailClient.class);
  }

  @Test
  public void testGetClient_WithRetryOnFailureEnabled() throws Exception {
    assertThat(testRailClient).isNotNull().isInstanceOf(TestRailClient.class);
  }

}