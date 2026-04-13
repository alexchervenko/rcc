package io.student.rcc.page.component;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import io.qameta.allure.Step;
import lombok.NonNull;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;

public class MuseumFormModal extends BaseComponent {

  private final ElementsCollection errorMessages = container.$$("span.text-error-400");

  public MuseumFormModal() {
    super($("form.modal-form"));
  }

  @Step("Заполнить название музея: {title}")
  public MuseumFormModal setTitle(@NonNull String title) {
    container.$("input[name='title']").setValue(title);
    return this;
  }

  @Step("Заполнить город музея: {city}")
  public MuseumFormModal setCity(@NonNull String city) {
    container.$("input[name='city']").setValue(city);
    return this;
  }

  @Step("Заполнить описание музея")
  public MuseumFormModal setDescription(@NonNull String description) {
    container.$("textarea[name='description']").setValue(description);
    return this;
  }

  @Step("Загрузить фото музея")
  public MuseumFormModal uploadPhoto(@NonNull File file) {
    container.$("input[name='photo']").uploadFile(file);
    return this;
  }

  @Step("Выбрать первую страну в списке")
  public MuseumFormModal selectFirstCountry() {
    container.$("select[name='countryId']")
        .$$("option")
        .shouldHave(CollectionCondition.sizeGreaterThan(0));
    container.$("select[name='countryId']").selectOption(0);
    return this;
  }

  @Step("Отправить форму музея")
  public MuseumFormModal submit() {
    container.$("button[type='submit']").click();
    return this;
  }

  @Step("Проверить сообщение об ошибке: {errorText}")
  public MuseumFormModal shouldHaveError(@NonNull String errorText) {
    errorMessages.findBy(Condition.exactText(errorText)).shouldBe(Condition.visible);
    return this;
  }
}
