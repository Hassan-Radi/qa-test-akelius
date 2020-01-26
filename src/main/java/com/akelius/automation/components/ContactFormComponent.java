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
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.akelius.automation.core.PageObject;

public class ContactFormComponent extends PageObject {

  protected static final Logger logger = LogManager.getLogger(ContactFormComponent.class);
  public static final String BASE_LOCATOR = "//mat-dialog-container";

  public ContactFormComponent() {
    /** Make sure that the page loaded successfully before interacting with it */
    wait.until(ExpectedConditions.visibilityOf(nameTextBox));
  }

  @FindBy(xpath = BASE_LOCATOR + "//input[@name='name']")
  private WebElement nameTextBox;

  @FindBy(xpath = BASE_LOCATOR + "//input[@name='email']")
  private WebElement emailTextBox;

  @FindBy(xpath = BASE_LOCATOR + "//input[@name='phone']")
  private WebElement phoneNumberTextBox;

  @FindBy(xpath = BASE_LOCATOR + "//textarea[@name='message']")
  private WebElement messageTextArea;

  @FindBy(xpath = BASE_LOCATOR + "//input[@name='name']//ancestor::mat-form-field//mat-error")
  private WebElement nameErrorText;

  @FindBy(xpath = BASE_LOCATOR + "//input[@name='email']//ancestor::mat-form-field//mat-error")
  private WebElement emailErrorText;

  @FindBy(xpath = BASE_LOCATOR + "//input[@name='phone']//ancestor::mat-form-field//mat-error")
  private WebElement phoneNumberErrorText;

  @FindBy(xpath = BASE_LOCATOR + "//textarea[@name='message']//ancestor::mat-form-field//mat-error")
  private WebElement messageErrorText;

  /**
   * Types something in the name TextBox and return an error message or a null value (according to
   * the value of the expectError field).
   *
   * @param text The text to type in the field.
   * @return A string representation of the error message for this field, null otherwise
   */
  public String typeInNameField(String text, boolean expectError) {
    if (text != null) {
      logger.info(String.format("Typing [%s] in the name field...", text));
      nameTextBox.click();
      nameTextBox.sendKeys(text);
    } else {
      logger.info(
          "Not typing anything in the name field. Sending a 'Tab' action to trigger the field validation...");
      nameTextBox.click();
      nameTextBox.sendKeys(Keys.TAB);
    }

    return expectError ? nameErrorText.getText().trim() : null;
  }

  /**
   * Types something in the email TextBox and return an error message or a null value (according to
   * the value of the expectError field).
   *
   * @param text The text to type in the field.
   * @return A string representation of the error message for this field, null otherwise
   */
  public String typeInEmailField(String text, boolean expectError) {
    if (text != null) {
      logger.info(String.format("Typing [%s] in the email field...", text));
      emailTextBox.click();
      emailTextBox.sendKeys(text);
    } else {
      logger.info(
          "Not typing anything in the email field. Sending a 'Tab' action to trigger the field validation...");
      emailTextBox.click();
      emailTextBox.sendKeys(Keys.TAB);
    }

    return expectError ? emailErrorText.getText().trim() : null;
  }

  /**
   * Types something in the phone number TextBox and return an error message or a null value
   * (according to the value of the expectError field).
   *
   * @param text The text to type in the field.
   * @return A string representation of the error message for this field, null otherwise
   */
  public String typeInPhoneNumberField(String text, boolean expectError) {
    if (text != null) {
      logger.info(String.format("Typing [%s] in the phone number field...", text));
      phoneNumberTextBox.click();
      phoneNumberTextBox.sendKeys(text);
    } else {
      logger.info(
          "Not typing anything in the phone number field. Sending a 'Tab' action to trigger the field validation...");
      phoneNumberTextBox.click();
      phoneNumberTextBox.sendKeys(Keys.TAB);
    }

    return expectError ? phoneNumberErrorText.getText().trim() : null;
  }

  /**
   * Types something in the message TextArea and return an error message or a null value (according
   * to the value of the expectError field).
   *
   * @param text The text to type in the field.
   * @return A string representation of the error message for this field, null otherwise
   */
  public String typeInMessageField(String text, boolean expectError) {
    if (text != null) {
      logger.info(String.format("Typing [%s] in the message field...", text));
      messageTextArea.click();
      messageTextArea.sendKeys(text);
    } else {
      logger.info(
          "Not typing anything in the message field. Sending a 'Tab' action to trigger the field validation...");
      messageTextArea.click();
      messageTextArea.sendKeys(Keys.TAB);
    }

    return expectError ? messageErrorText.getText().trim() : null;
  }
}
