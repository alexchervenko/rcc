package io.student.rcc.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import static com.codeborne.selenide.logevents.SelenideLogger.addListener;

public abstract class BaseWebTest {

  @BeforeAll
  static void configureUi() {
    Configuration.baseUrl = System.getProperty("front.url", "http://localhost:3000");
    Configuration.browserSize = System.getProperty("browser.size", "1920x1080");
    Configuration.timeout = Long.parseLong(System.getProperty("ui.timeout.ms", "10000"));
    Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
    addListener("allure", new AllureSelenide().screenshots(true).savePageSource(true));
  }

  @AfterEach
  void cleanup() {
    Selenide.closeWebDriver();
  }
}
