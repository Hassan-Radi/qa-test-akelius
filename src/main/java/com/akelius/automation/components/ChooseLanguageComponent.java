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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.akelius.automation.core.PageObject;

public class ChooseLanguageComponent extends PageObject {

  protected static final Logger logger = LogManager.getLogger(ChooseLanguageComponent.class);

  public ChooseLanguageComponent() {
    /** Make sure that the page loaded successfully before interacting with it */
    wait.until(ExpectedConditions.visibilityOf(zoomInButton));
  }

  @FindBy(xpath = "//*[name()='g' and @aria-label='Press ENTER to zoom in']")
  private WebElement zoomInButton;

  @FindBy(
      xpath =
          "//*[name()='g' and @aria-labelledby='Vereinigtes Königreich Großbritannien und Nordirland']")
  private WebElement ukMapElement;

  @FindBy(xpath = "//web-language-confirmation-dialog//span")
  private WebElement languageButton;

  public void clickOnUk() {
    logger.info("Clicking on the UK on the map...");
    wait.until(ExpectedConditions.visibilityOf(ukMapElement));
    ukMapElement.click();

    logger.info("Clicking on 'English' from the language list...");
    wait.until(ExpectedConditions.visibilityOf(languageButton));
    languageButton.click();
  }
}
