package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.student.rcc.page.component.Header;
import io.student.rcc.page.component.ItemCard;
import io.student.rcc.page.component.SearchField;
import java.util.List;

public abstract class BasePage {

  private final String path;

  protected final Header header = new Header();

  protected BasePage(String path) {
    this.path = path;
  }

  public Header header() {
    return header;
  }

  public SearchField search() {
    return new SearchField($x("//div[input[@type='search']]"));
  }

  public BasePage open() {
    com.codeborne.selenide.Selenide.open(path);
    return waitForOpen();
  }

  public BasePage waitForOpen() {
    $x("//h2").shouldBe(Condition.visible);
    return this;
  }

  public String title() {
    return $x("//h2").shouldBe(Condition.visible).getText();
  }

  public List<ItemCard> cards() {
    ElementsCollection cards = $$("ul.grid > li").shouldBe(CollectionCondition.sizeGreaterThan(0));
    return cards.asFixedIterable().stream().map(ItemCard::new).toList();
  }
}
