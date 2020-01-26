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
package com.akelius.automation.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.akelius.automation.core.PageObject;
import com.akelius.automation.data.Apartment;
import com.akelius.automation.pages.ApartmentPage;

/**
 * There is a limitation with the built-in Page object model in Selenium and we can't use it in this
 * class and have to use a different workaround
 */
public class ApartmentSearchResultComponent extends PageObject {

  protected static final Logger logger = LogManager.getLogger(ApartmentSearchResultComponent.class);
  private String baseComponent;
  private static final String TITLE_ELEMENT_LOCTOR = "//h3";
  private static final String ADDRESS_ELEMENT_LOCATOR = "//div[@class='address']";
  private static final String ROOMS_ELEMENT_LOCATOR = "//div[@class='group']/div[1]";
  private static final String SIZE_ELEMENT_LOCATOR = "//div[@class='group']/div[2]";
  private static final String FLOOR_ELEMENT_LOCATOR =
      "//div[@class='group ng-star-inserted']/div[1]";
  private static final String RENT_ELEMENT_LOCATOR =
      "//div[@class='group ng-star-inserted']/div[2]";
  private static final String AVAILABLE_FROM_ELEMENT_LOCATOR =
      "//div[@class='group available-from']";

  public ApartmentSearchResultComponent(String baseComponent) {
    this.baseComponent = baseComponent;
  }

  public ApartmentPage navigateToApartment() {
    logger.info("Clicking on the apartment...");
    getTitle().click();

    return new ApartmentPage();
  }

  public WebElement getTitle() {
    return driver.findElement(By.xpath(baseComponent + TITLE_ELEMENT_LOCTOR));
  }

  public String getTitleText() {
    return getTitle().getText().trim();
  }

  public String getAddressText() {
    return driver
        .findElement(By.xpath(baseComponent + ADDRESS_ELEMENT_LOCATOR))
        .getText()
        .trim()
        .replace("\n", " ");
  }

  public String getRoomText() {
    return driver
        .findElement(By.xpath(baseComponent + ROOMS_ELEMENT_LOCATOR))
        .getText()
        .trim()
        .split(" ")[1];
  }

  public int getSizeInMeterSquare() {
    return Integer.valueOf(
        driver
            .findElement(By.xpath(baseComponent + SIZE_ELEMENT_LOCATOR))
            .getText()
            .trim()
            .split(" ")[0]);
  }

  public String getFloorText() {
    return driver.findElement(By.xpath(baseComponent + FLOOR_ELEMENT_LOCATOR)).getText().trim();
  }

  public int getRentAmount() {
    return Integer.valueOf(
        driver
            .findElement(By.xpath(baseComponent + RENT_ELEMENT_LOCATOR))
            .getText()
            .trim()
            .split(" ")[1]
            .replace(".", ""));
  }

  public String getAvailableFromDateText() {
    return driver
        .findElement(By.xpath(baseComponent + AVAILABLE_FROM_ELEMENT_LOCATOR))
        .getText()
        .trim()
        .split(" ")[2]
        .replace("\n", "");
  }

  public Apartment getApartmentObject() {
    return new Apartment(
        getTitleText(),
        getAddressText(),
        getRoomText(),
        getSizeInMeterSquare(),
        getFloorText(),
        getRentAmount(),
        getAvailableFromDateText());
  }
}
