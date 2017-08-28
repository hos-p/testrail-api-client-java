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
import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * Representation of a Project in TestRail
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class TRProject {

  /**
   * The description/announcement of the project.
   */
  @JsonProperty("announcement")
  public String announcement;
  /**
   * The date/time when the project was marked as completed (as UNIX timestamp).
   */
  @JsonProperty("completed_on")
  public Long completedOn;
  /**
   * The unique ID of the project.
   */
  @JsonProperty("id")
  public Integer id;
  /**
   * True if the project is marked as completed and false otherwise.
   */
  @JsonProperty("is_completed")
  public Boolean isCompleted;
  /**
   * The name of the project.
   */
  @JsonProperty("name")
  public String name;
  /**
   * True to show the announcement/description and false otherwise.
   */
  @JsonProperty("show_announcement")
  @JsonRawValue
  public Boolean showAnnouncement;
  /**
   * The suite mode of the project (1 for single suite mode, 2 for single suite + baselines, 3 for multiple suites).
   * (added with TestRail 4.0)
   */
  @JsonProperty("suite_mode")
  public Integer suiteMode;
  /**
   * The address/URL of the project in the user interface.
   */
  @JsonProperty("url")
  public String url;
}
