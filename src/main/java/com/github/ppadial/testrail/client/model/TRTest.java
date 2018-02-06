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

package com.github.ppadial.testrail.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a test instance (an instance of a test case)
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class TRTest {

  public int id;
  @JsonProperty("case_id")
  public int caseId;
  @JsonProperty("statusId")
  public int status_id;
  @JsonProperty("run_id")
  public int runId;
  //following property is not present in TestRail by default
  //we need to add it as a custom field
  @JsonProperty("custom_automation_id")
  public String automationId;

}
