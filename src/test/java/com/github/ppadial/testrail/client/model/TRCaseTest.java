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

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class TRCaseTest {

  @Test
  public void createNewInstance() throws Exception {
    assertThat(new TRCase()).isNotNull().isInstanceOf(TRCase.class);
  }

  @Test
  public void assignValueToAllMembers() throws Exception {
    TRCase c = new TRCase();
    c.addCustomField("automation_id", "automationid");
    c.id = 1;
    c.milestoneId = 1;
    c.refs = "refs";
    c.sectionId = 1;
    c.suiteId = 1;
    c.title = "title";
    c.typeId = 1;
  }

}