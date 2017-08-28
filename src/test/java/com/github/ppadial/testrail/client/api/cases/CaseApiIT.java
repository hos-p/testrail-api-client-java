package com.github.ppadial.testrail.client.api.cases;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.ppadial.testrail.client.apiClient.ApiClient;
import com.github.ppadial.testrail.client.model.TRCase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CaseApiIT {
  private ApiClient apiClient;
  private CaseServiceClient caseServiceClient;

  @BeforeMethod
  public void setUp() throws Exception {
    apiClient = new ApiClient("", "", "");
    caseServiceClient = new CaseServiceClient(apiClient);

  }
  @Test
  public void testGetCase() throws Exception {
    final TRCase trCase = caseServiceClient.getCase(9356);
  }

}