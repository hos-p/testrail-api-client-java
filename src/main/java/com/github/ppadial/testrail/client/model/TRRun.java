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
import java.util.List;

/**
 * Represents a Test Run in testrail.
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class TRRun {

  /**
   * The ID of the user the entire test run is assigned to.
   */
  @JsonProperty("assignedto_id")
  public Integer assignedToId;
  /**
   * The amount of tests in the test run marked as blocked.
   */
  @JsonProperty("blocked_count")
  public Integer blockedCount;
  /**
   * The date/time when the test run was closed (as UNIX timestamp).
   */
  @JsonProperty("completed_on")
  public Long completedOn;
  /**
   * The configuration of the test run as string (if part of a test plan).
   */
  @JsonProperty("config")
  public String config;
  /**
   * The array of IDs of the configurations of the test run (if part of a test plan).
   */
  @JsonProperty("config_ids")
  public List<Integer> configIds;
  /**
   * The ID of the user who created the test run.
   */
  @JsonProperty("created_by")
  public Integer createdBy;
  /**
   * The date/time when the test run was created (as UNIX timestamp).
   */
  @JsonProperty("created_on")
  public Long createOn;
  /**
   * The description of the test run.
   */
  @JsonProperty("description")
  public String description;
  /**
   * The amount of tests in the test run marked as failed.
   */
  @JsonProperty("failed_count")
  public Integer failedCount;
  /**
   * The unique ID of the test run.
   */
  @JsonProperty("id")
  public Integer id;
  /**
   * True if the test run includes all test cases and false otherwise.
   */
  @JsonProperty("include_all")
  public Boolean includeAll;
  /**
   * True if the test run was closed and false otherwise.
   */
  @JsonProperty("is_completed")
  public Boolean isCompleted;
  /**
   * The ID of the milestone this test run belongs to.
   */
  @JsonProperty("milestone_id")
  public Integer milestoneId;
  /**
   * The ID of the test plan this test run belongs to.
   */
  @JsonProperty("plan_id")
  public Integer planId;
  /**
   * The name of the test run.
   */
  @JsonProperty("name")
  public String name;
  /**
   * The amount of tests in the test run marked as passed.
   */
  @JsonProperty("passed_count")
  public Integer passedCount;
  /**
   * The ID of the project this test run belongs to.
   */
  @JsonProperty("project_id")
  public Integer projectId;
  /**
   * The amount of tests in the test run marked as retest.
   */
  @JsonProperty("retest_count")
  public Integer retestCount;
  /**
   * The ID of the test suite this test run is derived from.
   */
  @JsonProperty("suite_id")
  public Integer suiteId;
  /**
   * The amount of tests in the test run marked as untested.
   */
  @JsonProperty("untested_count")
  public Integer untestedCount;
  /**
   * The address/URL of the test run in the user interface.
   */
  @JsonProperty("url")
  public String url;

}
