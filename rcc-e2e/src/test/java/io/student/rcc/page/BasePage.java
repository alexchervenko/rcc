package io.student.rcc.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import io.student.rcc.page.component.Header;
import io.student.rcc.page.component.ItemCard;
import io.student.rcc.page.component.SearchField;
import lombok.NonNull;

import java.util.List;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public abstract class BasePage {

  private final String path;

  protected final Header header = new Header();

  protected BasePage(@NonNull String path) {
    this.path = path;
  }

  @Step("Получить хедер")
  public Header header() {
    return header;
  }

  @Step("Получить поле поиска")
  public SearchField search() {
    return new SearchField($("div:has(input[type='search'])"));
  }

  @Step("Открыть страницу")
  public BasePage open() {
    com.codeborne.selenide.Selenide.open(path);
    return waitForOpen();
  }

  @Step("Дождаться открытия страницы")
  public BasePage waitForOpen() {
    $("h2").shouldBe(Condition.visible);
    return this;
  }

  @Step("Получить заголовок страницы")
  public String title() {
    return $("h2").getText();
  }

  @Step("Получить карточки на странице")
  public List<ItemCard> cards() {
    ElementsCollection cards = $$("ul.grid > li").shouldBe(CollectionCondition.sizeGreaterThan(0));
    return cards.asFixedIterable().stream().map(ItemCard::new).toList();
  }
}
