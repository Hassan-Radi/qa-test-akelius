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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.akelius.automation.components.ApartmentSearchResultComponent;
import com.akelius.automation.components.ChooseLanguageComponent;
import com.akelius.automation.core.PageObject;
import com.akelius.automation.data.Apartment;
import com.akelius.automation.data.TestData;
import com.akelius.automation.util.Helper;

public class LandingPage extends PageObject {

  protected static final Logger logger = LogManager.getLogger(LandingPage.class);
  private static final String APARTMENT_SEARCH_ITEM_BASE_LOCATOR = "//app-unit-item";

  public LandingPage() {
    /** Make sure that the page loaded successfully before interacting with it */
    wait.until(ExpectedConditions.visibilityOf(changeLanguageButton));
  }

  @FindBy(xpath = "//button[@aria-label='Language']")
  private WebElement changeLanguageButton;

  @FindBy(id = "mat-select-1")
  private WebElement cityDropDown;

  @FindBy(xpath = "//span[@class='mat-option-text']")
  private List<WebElement> dropDownOptions;

  @FindBy(xpath = "//span[contains(@class,'slider-pointer-min')]")
  private WebElement minSizeSlider;

  @FindBy(xpath = "//span[contains(@class,'slider-pointer-max')]")
  private WebElement maxSizeSlider;

  @FindBy(xpath = "//h2")
  private WebElement totalApartmentCountText;

  @FindBy(xpath = APARTMENT_SEARCH_ITEM_BASE_LOCATOR)
  private List<WebElement> apartmentsList;

  @FindBy(xpath = "//div[@aria-label='List']")
  private WebElement listViewLink;

  @FindBy(xpath = "//div[@aria-label='Map']")
  private WebElement mapViewLink;

  @FindBy(xpath = "//app-unit-map")
  private WebElement mapElement;

  /**
   * Changes the language of the website to use En_UK
   *
   * @return An object of the LandingPage
   */
  public LandingPage changeLanguageToEnUk() {
    logger.info("Clicking on the change language button...");
    changeLanguageButton.click();

    ChooseLanguageComponent chooseLanguage = new ChooseLanguageComponent();
    chooseLanguage.clickOnUk();

    return new LandingPage();
  }

  /**
   * Selects a city from the city DropDown and returns an instance to the current class for
   * chaining.
   *
   * @param cityName The city name to look for.
   * @return An instance to the current class.
   */
  public LandingPage selectCity(String cityName) {
    cityDropDown.click();

    /** Cycle through all the DropDown options and click on the one with the text. */
    dropDownOptions
        .stream()
        .filter(it -> it.getText().trim().equalsIgnoreCase(cityName))
        .collect(Collectors.toList())
        .get(0)
        .click();

    return this;
  }

  /**
   * Filters the apartments by minimum and maximum apartment size.
   *
   * @param min
   * @param max
   * @return
   */
  public LandingPage selectSize(int min, int max) {
    driver
        .navigate()
        .to(
            driver.getCurrentUrl()
                + String.format(TestData.APARTMENT_SIZE_SLIDER_PARTIAL_URL, min, max));

    // wait until the page reloads
    Helper.waitForPageLoadingToComplete();

    return this;
  }

  /** @return An integer representing the current value of the minimum slider */
  public int getMinSliderCurrentValue() {
    return Integer.valueOf(minSizeSlider.getAttribute(TestData.SLIDER_VALUE_NOW_ATTRIBUTE));
  }

  /** @return An integer representing the current value of the maximum slider */
  public int getMaxSliderCurrentValue() {
    return Integer.valueOf(maxSizeSlider.getAttribute(TestData.SLIDER_VALUE_NOW_ATTRIBUTE));
  }

  /** @return An integer representing the number of apartments displayed. */
  public int getApartmentCount() {
    return Integer.valueOf(totalApartmentCountText.getText().split(" ")[1]);
  }

  /** @return A list of all the apartment search results returned by the website */
  public List<ApartmentSearchResultComponent> getAllApartments() {
    List<ApartmentSearchResultComponent> apartments = new ArrayList<>();

    for (int i = 0; i < apartmentsList.size(); i++) {
      apartments.add(
          new ApartmentSearchResultComponent(
              String.format("%s[%s]", APARTMENT_SEARCH_ITEM_BASE_LOCATOR, i + 1)));
    }
    return apartments;
  }

  /**
   * Checks whether the displayed apartment search results don't contain any duplicates by creating
   * a HashMap and storing the hash for each apartment as key.
   *
   * @return True if the values are unique, otherwise false
   */
  public boolean areApartmentValuesUnique() {
    Map<Integer, Apartment> distinctValues = new HashMap<>();
    List<ApartmentSearchResultComponent> apartments = getAllApartments();

    for (ApartmentSearchResultComponent apartmentComponent : apartments) {
      Apartment apartmentDataObject = apartmentComponent.getApartmentObject();
      distinctValues.put(apartmentDataObject.hashCode(), apartmentDataObject);
    }

    return distinctValues.size() < apartments.size() ? false : true;
  }

  /**
   * Toggles between list view and map view for the search results
   *
   * @return An object representing the LandingPage
   */
  public LandingPage toggleView() {
    if (Boolean.parseBoolean(listViewLink.getAttribute(TestData.TAB_VIEW_SELECTED_ATTRIBUTE))) {
      // switch to map view
      mapViewLink.click();
      Helper.waitForPageLoadingToComplete();
      return new LandingPage();
    } else {
      // switch to list view
      listViewLink.click();
      Helper.waitForPageLoadingToComplete();
      return new LandingPage();
    }
  }

  /** @return True is the map is displayed, false otherwise */
  public boolean isMapView() {
    try {
      return mapElement.isDisplayed();
    } catch (NoSuchElementException ex) {
      return false;
    }
  }
}
