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
 * Representation of a TestRail Test Result.
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class TRResult {

  /**
   * The ID of the assignee (user) of the test result.
   */
  @JsonProperty("assignedto_id")
  public Integer assignedtoId;
  /**
   * The comment or error message of the test result.
   */
  @JsonProperty("comment")
  public String comment;
  /**
   * The ID of the user who created the test result.
   */
  @JsonProperty("created_by")
  public Integer createdBy;
  /**
   * The date/time when the test result was created (as UNIX timestamp).
   */
  @JsonProperty("created_on")
  public Long createdOn;
  /**
   * A comma-separated list of defects linked to the test result.
   */
  @JsonProperty("defects")
  public String defects;
  /**
   * The amount of time it took to execute the test (e.g. "1m" or "2m 30s").
   */
  @JsonProperty("elapsed")
  public Long elapsed;
  /**
   * The unique ID of the test result.
   */
  @JsonProperty("id")
  public int id;
  /**
   * The status of the test result, e.g. passed or failed, also see get_statuses.
   */
  @JsonProperty("status_id")
  public Integer statusId;
  /**
   * The ID of the test this test result belongs to.
   */
  @JsonProperty("test_id")
  public Integer testId;
  /**
   * The (build) version the test was executed against.
   */
  @JsonProperty("version")
  public String version;
}
