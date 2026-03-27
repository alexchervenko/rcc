package io.student.rcc.page.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.student.rcc.page.ArtistPage;
import io.student.rcc.page.MuseumPage;
import io.student.rcc.page.PaintingPage;

public class Header extends BaseComponent {

  private final SelenideElement paintingsLink = container.$("a[href='/painting']");
  private final SelenideElement artistsLink = container.$("a[href='/artist']");
  private final SelenideElement museumsLink = container.$("a[href='/museum']");
  private final SelenideElement loginButton = container.$x(".//button[contains(normalize-space(.), 'Войти')]");

  public Header() {
    super($("header"));
  }

  @Step("Перейти на страницу картин")
  public PaintingPage goToPaintings() {
    paintingsLink.shouldBe(Condition.visible).click();
    return new PaintingPage().waitForOpen();
  }

  @Step("Перейти на страницу художников")
  public ArtistPage goToArtists() {
    artistsLink.shouldBe(Condition.visible).click();
    return new ArtistPage().waitForOpen();
  }

  @Step("Перейти на страницу музеев")
  public MuseumPage goToMuseums() {
    museumsLink.shouldBe(Condition.visible).click();
    return new MuseumPage().waitForOpen();
  }

  @Step("Нажать кнопку входа")
  public void clickLogin() {
    loginButton.shouldBe(Condition.visible).click();
  }

  @Step("Проверить, что пользователь авторизован")
  public boolean isAuthorized() {
    SelenideElement profileButton = $x("//header//button[contains(@class, 'btn-icon')]");
    return profileButton.exists() && profileButton.isDisplayed();
  }
}
