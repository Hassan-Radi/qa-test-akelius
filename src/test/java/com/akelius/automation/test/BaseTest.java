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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.akelius.automation.core.DriverManager;
import com.akelius.automation.data.TestData;
import com.akelius.automation.pages.LandingPage;

/** Base test class for the common setup and driver creation for all the test cases. */
public class BaseTest {

  protected static final Logger logger = LogManager.getLogger(BaseTest.class);
  protected static WebDriver driver = null;

  @BeforeMethod
  public void beforeMethod() {
    logger.info(
        "\n*************************************************************************************\n"
            + "*************************************NEW TEST****************************************\n"
            + "*************************************************************************************");
  }

  @AfterMethod
  public void afterMethod() {
    /** delete all cookies in the session to be able to start the new test with no old data */
    DriverManager.getDriver().manage().deleteAllCookies();
    logger.info("Cleaned all the session cookies to start running a new test.\n\n");
  }

  @BeforeClass
  public static void setUp() {
    driver = DriverManager.getDriver();
  }

  @AfterClass
  public static void tearDown() {
    /**
     * clear after the test is done executing by terminating the running browser instance to prevent
     * memory leaks.
     */
    if (DriverManager.getDriver() != null) {
      DriverManager.getDriver().quit();
      logger.info("Terminating the driver session and killing the browser...");
    }
  }

  /** @return An object of the LandingPage. */
  public LandingPage navigateToLandingPage() {
    driver.navigate().to(TestData.LANDING_PAGE);
    return new LandingPage();
  }
}
