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
 * TRMilestone representation in TestRail.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-milestones">Milestones</a>
 * @since 0.1.0
 */
public class TRMilestone {

  /**
   * The date/time when the milestone was marked as completed (as UNIX timestamp).
   */
  @JsonProperty("completed_on")
  public long completedOn;
  /**
   * The description of the milestone.
   */
  @JsonProperty("description")
  public String description;
  /**
   * The due date/time of the milestone (as UNIX timestamp).
   */
  @JsonProperty("due_on")
  public long dueOn;
  /**
   * The unique ID of the milestone.
   */
  @JsonProperty("id")
  public int id;
  /**
   * True if the milestone is marked as completed and false otherwise.
   */
  @JsonProperty("is_completed")
  public Boolean isCompleted;
  /**
   * True if the milestone is marked as started and false otherwise (available since TestRail 5.3).
   */
  @JsonProperty("is_started")
  public Boolean isStarted;
  /**
   * The sub milestones that belong to the milestone (if any); only available with get_milestone (available since
   * TestRail 5.3).
   */
  @JsonProperty("milestones")
  public List<TRMilestone> childMilestones;
  /**
   * The name of the milestone.
   */
  @JsonProperty("name")
  public String name;
  /**
   * The ID of the parent milestone the milestone belongs to (if any) (available since TestRail 5.3).
   */
  @JsonProperty("parent_id")
  public int parentId;
  /**
   * The ID of the project the milestone belongs to.
   */
  @JsonProperty("project_id")
  public int projectId;
  /**
   * The scheduled start date/time of the milestone (as UNIX timestamp) (available since TestRail 5.3).
   */
  @JsonProperty("start_on")
  public long startOn;
  /**
   * The date/time when the milestone was started (as UNIX timestamp) (available since TestRail 5.3).
   */
  @JsonProperty("started_on")
  public long startedOn;
  /**
   * The address/URL of the milestone in the user interface.
   */
  @JsonProperty("url")
  public String url;
}