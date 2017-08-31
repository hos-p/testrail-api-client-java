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

package com.github.ppadial.testrail.client.api.configurations;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.ppadial.testrail.client.HttpStatusCode;
import com.github.ppadial.testrail.client.TestRailException;
import com.github.ppadial.testrail.client.api.NoAccessToProjectException;
import com.github.ppadial.testrail.client.api.TestRailServiceBase;
import com.github.ppadial.testrail.client.api.plans.InvalidOrUnknownTestPlanException;
import com.github.ppadial.testrail.client.api.projects.InvalidOrUnknownProjectException;
import com.github.ppadial.testrail.client.apiClient.ApiCallException;
import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.apiClient.ApiResponse;
import com.github.ppadial.testrail.client.model.TRConfig;
import com.github.ppadial.testrail.client.model.TRConfigGroup;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.util.Strings;

/**
 * Testrail Client for API: Configurations Endpoint.
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-configurations">reference-configurations</a>
 * @since 0.1.0
 */
public final class ConfigurationServiceClient extends TestRailServiceBase {

  /**
   * Creates a new instance of the plan service client.
   *
   * @param apiClient the api client to use
   */
  public ConfigurationServiceClient(final ApiClient apiClient) {
    super(apiClient);
  }

  /**
   * Returns an existing test plan.
   *
   * @param projectId The ID of the project
   * @return Returns a list of available configurations, grouped by configuration groups (requires TestRail 3.1 or
   * later).
   * @throws TestRailException An error in the connection with testrail
   * @since 0.1.0
   */
  public final List<TRConfigGroup> getConfigs(final int projectId)
      throws TestRailException {
    final ApiResponse apiResponse;
    final List<TRConfigGroup> responseObjectModel;

    // Do the query
    apiResponse = get("get_configs/" + projectId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN, new NoAccessToProjectException());
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, new TypeReference<List<TRConfigGroup>>() {
    }, choices);
    return responseObjectModel;
  }

  /**
   * Creates a new configuration (requires TestRail 5.2 or later).
   *
   * @param configGroup The ID of the configuration group the configuration should be added to
   * @param name The name of the configuration (required)
   * @return created configuration
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRConfig addConfig(final int configGroup, final String name)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRConfig responseObjectModel;

    Map<String, Object> body = new HashMap<String, Object>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    } else {
      // TODO: Change by InvalidArgument of lang3
      throw new TestRailException("Name parameter can't be null or empty");
    }

    // Do the query
    apiResponse = post("add_config/" + configGroup, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to add configurations or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRConfig.class, choices);
    return responseObjectModel;
  }

  /**
   * Creates a new configuration group (requires TestRail 5.2 or later).
   *
   * @param projectId The ID of the project the configuration group should be added to
   * @param name The name of the configuration group (required)
   * @return the created configuration group
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRConfigGroup addConfigGroup(final int projectId, final String name)
      throws TestRailException {
    final ApiResponse apiResponse;
    final TRConfigGroup responseObjectModel;

    Map<String, Object> body = new HashMap<String, Object>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    } else {
      // TODO: Change by InvalidArgument of lang3
      throw new TestRailException("Name parameter can't be null or empty");
    }

    // Do the query
    apiResponse = post("add_config_group/" + projectId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownProjectException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to add configuration groups or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRConfigGroup.class, choices);
    return responseObjectModel;
  }

  /**
   * Updates an existing configuration group (requires TestRail 5.2 or later).
   *
   * @param configGroupId The ID of the configuration group
   * @param name The name of the configuration group
   * @return the updated configuration group
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRConfigGroup updateConfigGroup(final int configGroupId, final String name)
      throws TestRailException {

    final ApiResponse apiResponse;
    final TRConfigGroup responseObjectModel;

    Map<String, String> body = new HashMap<String, String>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    }

    // Do the query
    apiResponse = post("update_config_group/" + configGroupId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownConfigurationGroupException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to modify configuration groups or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRConfigGroup.class, choices);
    return responseObjectModel;
  }

  /**
   * Updates an existing configuration (requires TestRail 5.2 or later).
   *
   * @param configId The ID of the configuration
   * @param name The name of the configuration
   * @return the updated configuration
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final TRConfig updateConfig(final int configId, final String name)
      throws TestRailException {

    final ApiResponse apiResponse;
    final TRConfig responseObjectModel;

    Map<String, String> body = new HashMap<String, String>();
    if (!Strings.isNullOrEmpty(name)) {
      body.put("name", name);
    }

    // Do the query
    apiResponse = post("update_config/" + configId, body);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownConfigurationGroupException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to modify configurations or no access to the project"));
          }
        };

    // Handle response
    responseObjectModel = handleApiResponse(apiResponse, TRConfig.class, choices);
    return responseObjectModel;
  }

  /**
   * Deletes an existing configuration (requires TestRail 5.2 or later).
   * Deleting a configuration cannot be undone. It does not, however, affect closed test plans/runs, or active
   * test plans/runs unless they are updated.
   * @param configId The ID of the configuration
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final void deleteConfig(final int configId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_config/" + configId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownConfigurationException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException("No permissions to delete configurations or no access to the project"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }

  /**
   * Deletes an existing configuration group and its configurations (requires TestRail 5.2 or later).
   * Deleting a configuration group cannot be undone and also permanently deletes all configurations in this
   * group. It does not, however, affect closed test plans/runs, or active test plans/runs unless they are updated.
   * @param configGroupId The ID of the configuration group
   * @since 0.1.0
   * @throws TestRailException An error in the connection with testrail
   */
  public final void deleteConfigGroup(final int configGroupId)
      throws TestRailException {
    final ApiResponse apiResponse;

    // Do the query
    apiResponse = post("delete_config_group/" + configGroupId);

    Map<HttpStatusCode, TestRailException> choices =
        new HashMap<HttpStatusCode, TestRailException>() {
          {
            put(HttpStatusCode.BAD_REQUEST, new InvalidOrUnknownConfigurationGroupException());
            put(HttpStatusCode.FORBIDDEN,
                new NoAccessToProjectException(
                    "No permissions to delete configuration groups or no access to the project"));
          }
        };

    // Handle response
    handleApiResponse(apiResponse, choices);
  }
}
