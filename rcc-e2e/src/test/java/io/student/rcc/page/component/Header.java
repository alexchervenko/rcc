package io.student.rcc.page.component;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.student.rcc.page.ArtistPage;
import io.student.rcc.page.MuseumPage;
import io.student.rcc.page.PaintingPage;

import static com.codeborne.selenide.Selenide.$;

public class Header extends BaseComponent {

    private final SelenideElement paintingsLink = container.$("a[href='/painting']");
    private final SelenideElement artistsLink = container.$("a[href='/artist']");
    private final SelenideElement museumsLink = container.$("a[href='/museum']");
    private final SelenideElement loginButton = container.$("button:contains('Войти')");
    private final SelenideElement profileButton = container.$("button[class*='btn-icon']");

    public Header() {
        super($("header"));
    }

    @Step("Перейти на страницу картин")
    public PaintingPage goToPaintings() {
        paintingsLink.click();
        return new PaintingPage().waitForOpen();
    }

    @Step("Перейти на страницу художников")
    public ArtistPage goToArtists() {
        artistsLink.click();
        return new ArtistPage().waitForOpen();
    }

    @Step("Перейти на страницу музеев")
    public MuseumPage goToMuseums() {
        museumsLink.click();
        return new MuseumPage().waitForOpen();
    }

    @Step("Нажать кнопку входа")
    public void clickLogin() {
        loginButton.click();
    }

    @Step("Проверить, что пользователь авторизован")
    public boolean isAuthorized() {
        return profileButton.exists() && profileButton.isDisplayed();
    }
}
