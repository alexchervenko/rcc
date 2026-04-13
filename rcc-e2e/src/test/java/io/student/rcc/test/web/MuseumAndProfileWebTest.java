package io.student.rcc.test.web;

import io.student.rcc.page.MuseumPage;
import io.student.rcc.page.ProfilePage;
import io.student.rcc.page.component.MuseumFormModal;
import io.student.rcc.page.component.ProfileFormModal;
import io.student.rcc.test.BaseWebTest;
import io.student.rcc.test.support.AuthSteps;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;

@Tag("web")
public class MuseumAndProfileWebTest extends BaseWebTest {

  @Test
  @DisplayName("Редактирование профиля для нового пользователя")
  void shouldEditProfile() {
    ProfilePage profilePage = AuthSteps.registerAndLoginEmptyUser();

    profilePage.openProfileModal();
    ProfileFormModal profileModal = profilePage.profileFormModal();
    profileModal
        .setFirstname("Иван")
        .setSurname("Петров")
        .submit();

    profilePage.openProfileModal();
    profilePage.profileFormModal()
        .shouldHaveFirstname("Иван")
        .shouldHaveSurname("Петров");
  }

  @Test
  @DisplayName("Создание нового музея")
  void shouldCreateMuseum() {
    MuseumPage museumPage = AuthSteps.registerAndLoginEmptyUser()
        .header()
        .goToMuseums();

    String title = "Museum " + System.currentTimeMillis();

    museumPage.clickAddMuseum();
    museumPage.museumFormModal()
        .setTitle(title)
        .selectFirstCountry()
        .setCity("Москва")
        .uploadPhoto(validImage())
        .setDescription("Описание тестового музея для UI автотеста")
        .submit();

    museumPage.search().search(title);
    museumPage.openMuseumByTitle(title).shouldShowMuseumTitle(title);
  }

  @Test
  @DisplayName("Редактирование музея")
  void shouldEditMuseum() {
    MuseumPage museumPage = AuthSteps.registerAndLoginEmptyUser()
        .header()
        .goToMuseums();

    String title = "Museum " + System.currentTimeMillis();
    String updatedTitle = title + " Updated";

    museumPage.clickAddMuseum();
    museumPage.museumFormModal()
        .setTitle(title)
        .selectFirstCountry()
        .setCity("Казань")
        .uploadPhoto(validImage())
        .setDescription("Описание музея для проверки редактирования")
        .submit();

    museumPage.search().search(title);
    museumPage.openMuseumByTitle(title)
        .clickEditMuseum();

    museumPage.museumFormModal()
        .setTitle(updatedTitle)
        .setCity("Казань")
        .setDescription("Обновленное описание музея для UI автотеста")
        .submit();

    museumPage.shouldShowMuseumTitle(updatedTitle);
  }

  @Test
  @DisplayName("Проверки граничных значений формы музея")
  void shouldValidateMuseumFormBoundaries() {
    MuseumPage museumPage = AuthSteps.registerAndLoginEmptyUser()
        .header()
        .goToMuseums();

    museumPage.clickAddMuseum();
    MuseumFormModal modal = museumPage.museumFormModal();

    modal.setTitle("ab")
        .setCity("ab")
        .setDescription("123456789")
        .uploadPhoto(validImage())
        .selectFirstCountry()
        .submit()
        .shouldHaveError("Название не может быть короче 3 символов")
        .shouldHaveError("Город не может быть короче 3 символов")
        .shouldHaveError("Описание не может быть короче 10 символов");

    modal.setTitle(repeat("a", 256))
        .setCity(repeat("b", 256))
        .setDescription(repeat("c", 2001))
        .submit()
        .shouldHaveError("Название не может быть длиннее 255 символов")
        .shouldHaveError("Город не может быть длиннее 255 символов")
        .shouldHaveError("Описание не может быть длиннее 2000 символов");
  }

  private static File validImage() {
    return new File(System.getProperty("user.dir"), "../rococo.png");
  }

  private static String repeat(String symbol, int count) {
    return symbol.repeat(Math.max(0, count));
  }
}
