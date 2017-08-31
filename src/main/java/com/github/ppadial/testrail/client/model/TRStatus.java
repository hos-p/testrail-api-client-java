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
 * TRStatus representation in TestRail.
 *
 * <p>Each status has a unique ID, a name (system name) as well as a label (display name). The color related fields
 * specify the different colors used for a status and are RGB colors. The following system statuses are available by
 * default. You can add additional custom statuses under Administration.</p>
 * <p>Customizations in TestRail.</p>
 * <table> <caption>statuses in testrails</caption> <tr>
 * <td>ID</td> <td>Name</td> </tr> <tr> <td>1</td> <td>Passed</td> </tr> <tr> <td>2</td> <td>Blocked</td> </tr> <tr>
 * <td>3</td> <td>Untested</td> </tr> <tr> <td>4</td> <td>Retest</td> </tr> <tr> <td>5</td> <td>Failed</td> </tr>
 * </table>
 *
 * @author Paulino Padial
 * @see <a href="http://docs.gurock.com/testrail-api2/reference-templates">Templates</a>
 * @since 0.1.0
 */
public class TRStatus {

  @JsonProperty("color_bright")
  public Long colorBright;
  @JsonProperty("color_dark")
  public Long colorDark;
  @JsonProperty("color_medium")
  public Long colorMedium;
  @JsonProperty("id")
  public int id;
  @JsonProperty("is_final")
  public Boolean isFinal;
  @JsonProperty("is_system")
  public Boolean isSystem;
  @JsonProperty("is_untested")
  public Boolean isUntested;
  @JsonProperty("label")
  public String label;
  @JsonProperty("name")
  public String name;
}
