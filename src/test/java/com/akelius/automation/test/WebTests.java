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
package com.akelius.automation.test;

import java.util.List;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.akelius.automation.components.ApartmentSearchResultComponent;
import com.akelius.automation.components.ContactFormComponent;
import com.akelius.automation.data.TestData;
import com.akelius.automation.pages.ApartmentPage;
import com.akelius.automation.pages.LandingPage;
import com.akelius.automation.util.Helper;

public class WebTests extends BaseTest {

  private LandingPage landingPage;
  private List<ApartmentSearchResultComponent> apartmentSearchResults;
  private ApartmentPage apartmentPage;

  @Test
  public void changeLanguage() {
    logger.info("STEP 1 - Navigating to the website.");
    landingPage = navigateToLandingPage();

    logger.info("STEP 2 - Changing the language to 'en_UK'...");
    landingPage.changeLanguageToEnUk();

    logger.info("Verify #1: Verify that the language has been changed...");
    new WebDriverWait(driver, TestData.TEN_SECONDS, TestData.HALF_SECOND_IN_MILLI)
        .until(ExpectedConditions.urlContains(TestData.EN_UK_LOCALE));
    Assert.assertTrue(
        driver.getCurrentUrl().contains(TestData.EN_UK_LOCALE),
        "Failed to change country to the UK.");
  }

  @Test(dependsOnMethods = "changeLanguage")
  public void filterByCityAndSize() {
    logger.info("STEP 1 - Filter by city 'Berlin'.");
    landingPage.selectCity(TestData.CITY);

    logger.info(
        String.format(
            "STEP 2 - Filter by apartment size [min = %s, max= %s].",
            TestData.SLIDER_MIN_VALUE, TestData.SLIDER_MAX_VALUE));
    landingPage.selectSize(TestData.SLIDER_MIN_VALUE, TestData.SLIDER_MAX_VALUE);

    logger.info("Verify #1: Verify that the slider values are correct.");
    Assert.assertEquals(
        landingPage.getMinSliderCurrentValue(),
        TestData.SLIDER_MIN_VALUE,
        "Incorrect minimum slider value.");
    Assert.assertEquals(
        landingPage.getMaxSliderCurrentValue(),
        TestData.SLIDER_MAX_VALUE,
        "Incorrect maximum slider value.");

    logger.info("Verify #2: Verify that the displayed number of apartments is correct.");
    int apartmentCountBefore = landingPage.getApartmentCount();
    apartmentSearchResults = landingPage.getAllApartments();
    Assert.assertEquals(
        apartmentSearchResults.size(),
        apartmentCountBefore,
        "Incorrect number of apartments is displayed.");

    logger.info("Verify #3: Verify that each apartment is displayed only once.");
    Assert.assertTrue(landingPage.areApartmentValuesUnique(), "Apartment values are not unique.");

    logger.info("STEP 3 - Show the results by map.");
    landingPage.toggleView();
    logger.info("Verify #4: Verify that the map view is displayed.");
    Assert.assertTrue(landingPage.isMapView(), "Map view is not displayed.");

    logger.info("STEP 4 - Show the results by list again.");
    landingPage.toggleView();
    logger.info(
        "Verify #5: Verify that the list view is displayed and the number of results is the same.");
    Assert.assertEquals(
        landingPage.getApartmentCount(), apartmentCountBefore, "Apartment count has changed.");
  }

  @Test(dependsOnMethods = "filterByCityAndSize")
  public void checkApartmentDetails() {
    logger.info("STEP 1 - Clicking on the first apartment from the search results.");
    String apartmentTitle = apartmentSearchResults.get(0).getTitleText();
    apartmentPage = apartmentSearchResults.get(0).navigateToApartment();

    logger.info("Verify #1: Verify that text writen in the title is the address of the apartment.");
    Assert.assertEquals(
        apartmentPage.getApartmentTitle(), apartmentTitle, "Incorrect apartment title.");

    logger.info("Verify #2: Verify that The 'id' of the apartment matches the id in the URL.");
    Assert.assertEquals(
        apartmentPage.getApartmentId(),
        Helper.extractGroupFromRegex(TestData.APARTMENT_ID_REGEX, driver.getCurrentUrl()),
        "Incorrect apartment ID is displayed on the website.");
  }

  @Test(dependsOnMethods = "checkApartmentDetails")
  public void checkContactFormValidations() {
    logger.info("STEP 1 - Clicking on the 'Contact' button.");
    ContactFormComponent contactForm = apartmentPage.contactApartmentOwner();

    logger.info(
        "Verify #1: Verify that name field shows the proper message when you type nothing in it.");
    Assert.assertEquals(
        contactForm.typeInNameField(null, true),
        TestData.NAME_EMPTY_ERROR_MESSAGE_ENG,
        "Incorrect error message.");
    logger.info(
        "Verify #2: Verify that email field shows the proper message when you type nothing in it.");
    Assert.assertEquals(
        contactForm.typeInEmailField(null, true),
        TestData.EMAIL_EMPTY_ERROR_MESSAGE_ENG,
        "Incorrect error message.");
    logger.info(
        "Verify #3: Verify that phone number field shows the proper message when you type nothing in it.");
    Assert.assertEquals(
        contactForm.typeInPhoneNumberField(null, true),
        TestData.PHONE_NUMBER_EMPTY_ERROR_MESSAGE_ENG,
        "Incorrect error message.");
    logger.info(
        "Verify #4: Verify that message field shows the proper message when you type nothing in it.");
    Assert.assertEquals(
        contactForm.typeInMessageField(null, true),
        TestData.MESSAGE_EMPTY_ERROR_MESSAGE_ENG,
        "Incorrect error message.");
    logger.info(
        "Verify #5: Verify that email field shows the proper message when you type an invalid email in it.");
    Assert.assertEquals(
        contactForm.typeInEmailField(TestData.INVALID_TEXT, true),
        TestData.EMAIL_INVALID_FORMAT_ERROR_MESSAGE_ENG,
        "Incorrect error message.");
    logger.info(
        "Verify #6: Verify that phone number field shows the proper message when you type an invalid phone number in it.");
    Assert.assertEquals(
        contactForm.typeInPhoneNumberField(TestData.INVALID_TEXT, true),
        TestData.PHONE_NUMBER_INVALID_FORMAT_ERROR_MESSAGE_ENG,
        "Incorrect error message.");
  }
}
