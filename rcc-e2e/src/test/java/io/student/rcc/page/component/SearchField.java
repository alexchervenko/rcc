package io.student.rcc.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;

public class SearchField extends BaseComponent {

  private final SelenideElement input = container.$("input[type='search']");
  private final SelenideElement searchButton = container.$("button");

  public SearchField(@NonNull SelenideElement container) {
    super(container);
  }

  @Step("Выполнить поиск по Enter: {text}")
  public SearchField search(@NonNull String text) {
    input.setValue(text).pressEnter();
    return this;
  }

  @Step("Выполнить поиск по кнопке: {text}")
  public SearchField searchByButton(@NonNull String text) {
    input.setValue(text);
    searchButton.click();
    return this;
  }

  @Step("Получить текущее значение поиска")
  public String value() {
    return input.getValue();
  }
}
