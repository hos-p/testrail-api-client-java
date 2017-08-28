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

package com.github.ppadial.testrail.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Test Suite representation in TestRail.
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public final class TRSuite {

  /**
   * The date/time when the test suite was closed (as UNIX timestamp) (added with TestRail 4.0).
   */
  @JsonProperty("completed_on")
  public Long completedOn;
  /**
   * The description of the test suite.
   */
  @JsonProperty("description")
  public String description;
  /**
   * The unique ID of the test suite.
   */
  @JsonProperty("id")
  public int id;
  /**
   * True if the test suite is a baseline test suite and false otherwise (added with TestRail 4.0).
   */
  @JsonProperty("is_baseline")
  public Boolean isBaseline;
  /**
   * True if the test suite is marked as completed/archived and false otherwise (added with TestRail 4.0).
   */
  @JsonProperty("is_completed")
  public Boolean isCompleted;
  /**
   * True if the test suite is a master test suite and false otherwise (added with TestRail 4.0).
   */
  @JsonProperty("is_master")
  public Boolean isMaster;
  /**
   * The name of the test suite.
   */
  @JsonProperty("name")
  public String name;
  /**
   * The ID of the project this test suite belongs to.
   */
  @JsonProperty("project_id")
  public Integer projectId;
  /**
   * The address/URL of the test suite in the user interface.
   */
  @JsonProperty("url")
  public String url;
}
