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

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.akelius.automation.data.TestData;
import com.akelius.automation.pages.LandingPage;

public class WebTests extends BaseTest {

  @Test
  public void changeLanguage() {
    logger.info("STEP 1 - Navigating to the website.");
    LandingPage landingPage = navigateToLandingPage();

    logger.info("STEP 2 - Changing the language to 'en_UK'...");
    landingPage.changeLanguageToEnUk();

    logger.info("Verify that the language has been changed...");
    new WebDriverWait(driver, TestData.TEN_SECONDS, TestData.HALF_SECOND_IN_MILLI)
        .until(ExpectedConditions.urlContains(TestData.EN_UK_LOCALE));
    Assert.assertTrue(
        driver.getCurrentUrl().contains(TestData.EN_UK_LOCALE),
        "Failed to change country to the UK.");
  }
}
