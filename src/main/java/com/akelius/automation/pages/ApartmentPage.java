/**
 * Copyright 2020 Hassan Radi
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in
 * writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * <p>See the License for the specific language governing permissions and limitations under the
 * License.
 */
package com.akelius.automation.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.akelius.automation.core.PageObject;

public class ApartmentPage extends PageObject {

  protected static final Logger logger = LogManager.getLogger(ApartmentPage.class);

  public ApartmentPage() {
    /** Make sure that the page loaded successfully before interacting with it */
    wait.until(ExpectedConditions.visibilityOf(apartmentTitle));
  }

  @FindBy(xpath = "//h1")
  private WebElement apartmentTitle;

  @FindBy(xpath = "//h2")
  private WebElement apartmentId;

  public String getApartmentTitle() {
    return apartmentTitle.getText();
  }

  public String getApartmentId() {
    return apartmentId.getText().split(" ")[1];
  }
}
