package io.student.rcc.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.NonNull;

public class ItemCard extends BaseComponent {

  public ItemCard(@NonNull SelenideElement container) {
    super(container);
  }

  @Step("Получить заголовок карточки")
  public String title() {
    SelenideElement title = container.$("img + div, span");
    return title.getText();
  }

  @Step("Получить alt изображения карточки")
  public String imageAlt() {
    return container.$("img[alt]").getAttribute("alt");
  }

  @Step("Открыть карточку")
  public void open() {
    container.$("a").click();
  }
}
