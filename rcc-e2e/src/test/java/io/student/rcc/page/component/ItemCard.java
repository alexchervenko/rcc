package io.student.rcc.page.component;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

public class ItemCard extends BaseComponent {

  public ItemCard(SelenideElement container) {
    super(container);
  }

  public String title() {
    SelenideElement title = container.$("img + div, span");
    return title.shouldBe(Condition.visible).getText();
  }

  public String imageAlt() {
    return container.$("img[alt]").shouldBe(Condition.visible).getAttribute("alt");
  }

  public void open() {
    container.$("a").shouldBe(Condition.visible).click();
  }
}
