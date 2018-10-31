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

import com.github.ppadial.testrail.client.api.cases.CaseServiceClient;
import com.github.ppadial.testrail.client.api.casetypes.CaseTypeServiceClient;
import com.github.ppadial.testrail.client.api.configurations.ConfigurationServiceClient;
import com.github.ppadial.testrail.client.api.milestones.MilestoneServiceClient;
import com.github.ppadial.testrail.client.api.plans.PlanServiceClient;
import com.github.ppadial.testrail.client.api.priorities.PriorityServiceClient;
import com.github.ppadial.testrail.client.api.projects.ProjectServiceClient;
import com.github.ppadial.testrail.client.api.results.ResultServiceClient;
import com.github.ppadial.testrail.client.api.runs.RunServiceClient;
import com.github.ppadial.testrail.client.api.sections.SectionServiceClient;
import com.github.ppadial.testrail.client.api.statuses.StatusServiceClient;
import com.github.ppadial.testrail.client.api.suites.SuiteServiceClient;
import com.github.ppadial.testrail.client.api.templates.TemplateServiceClient;
import com.github.ppadial.testrail.client.api.tests.TestServiceClient;
import com.github.ppadial.testrail.client.api.users.UserServiceClient;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TestRail Client for endpoints described at <a href="http://docs.gurock.com/testrail-Api2/start"> TestRail Api</a>.
 *
 * <p>Contains methods to talk to all the various endpoints for all the different object types within this one class.
 * The method parameters translate to the fields accepted as part of the request URL as well as a map of fields that can
 * be passed as body of the POST requests</p>
 *
 * @author Paulino Padial
 * @since 0.1.0
 */
public class TestRailClient {

  private static final Logger LOG = LoggerFactory.getLogger(TestRailClient.class);

  //underlying Api apiClient
  private ApiClient apiClient;

  /**
   * Creates an instance of the apiClient and setups up required state.
   * @param apiClient the api client to use
   */
  public TestRailClient(ApiClient apiClient) {
    LOG.debug(":: Constructor:: called");
    this.apiClient = apiClient;
  }

  /**
   * Get access to the case Api functions.
   *
   * @return access to case functions catalog
   */
  public CaseServiceClient caseApi() {
    return new CaseServiceClient(apiClient);
  }

  /**
   * Get access to the case types Api functions.
   *
   * @return access to case types functions catalog
   */
  public CaseTypeServiceClient caseTypesApi() {
    return new CaseTypeServiceClient(apiClient);
  }

  /**
   * Get access to the configuration Api functions.
   *
   * @return access to configuration functions catalog
   */
  public ConfigurationServiceClient configurationApi() {
    return new ConfigurationServiceClient(apiClient);
  }

  /**
   * Get access to the milestone Api functions.
   *
   * @return access to milestone functions catalog
   */
  public MilestoneServiceClient milestoneApi() {
    return new MilestoneServiceClient(apiClient);
  }

  /**
   * Get access to the plan Api functions.
   *
   * @return access to plan functions catalog
   */
  public PlanServiceClient planApi() {
    return new PlanServiceClient(apiClient);
  }

  /**
   * Get access to the priority Api functions.
   *
   * @return access to priority functions catalog
   */
  public PriorityServiceClient priorityApi() {
    return new PriorityServiceClient(apiClient);
  }


  /**
   * Get access to the project Api functions.
   *
   * @return access to project functions catalog
   */
  public ProjectServiceClient projectApi() {
    return new ProjectServiceClient(apiClient);
  }

  /**
   * Get access to the result Api functions.
   *
   * @return access to result functions catalog
   */
  public ResultServiceClient resultApi() {
    return new ResultServiceClient(apiClient);
  }

  /**
   * Get access to the run Api functions.
   *
   * @return access to run functions catalog
   */
  public RunServiceClient runApi() {
    return new RunServiceClient(apiClient);
  }

  /**
   * Get access to the section Api functions.
   *
   * @return access to section functions catalog
   */
  public SectionServiceClient sectionApi() {
    return new SectionServiceClient(apiClient);
  }

  /**
   * Get access to the status Api functions.
   *
   * @return access to status functions catalog
   */
  public StatusServiceClient statusApi() {
    return new StatusServiceClient(apiClient);
  }
  
  /**
   * Get access to the suite Api functions.
   *
   * @return access to suite functions catalog
   */
  public SuiteServiceClient suiteApi() {
    return new SuiteServiceClient(apiClient);
  }

  /**
   * Get access to the template Api functions.
   *
   * @return access to template functions catalog
   */
  public TemplateServiceClient templateApi() {
    return new TemplateServiceClient(apiClient);
  }
    
  /**
   * Get access to the test Api functions.
   *
   * @return access to test functions catalog
   */
  public TestServiceClient testApi() {
    return new TestServiceClient(apiClient);
  }

  /**
   * Get access to the user Api functions.
   *
   * @return access to user functions catalog
   */
  public UserServiceClient userApi() {
    return new UserServiceClient(apiClient);
  }
}
