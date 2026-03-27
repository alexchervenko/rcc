package io.student.rcc.page.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import java.io.File;
import lombok.NonNull;

public class MuseumFormModal extends BaseComponent {

  public MuseumFormModal() {
    super($("form.modal-form"));
  }

  @Step("Заполнить название музея: {title}")
  public MuseumFormModal setTitle(@NonNull String title) {
    container.$("input[name='title']").shouldBe(Condition.visible).setValue(title);
    return this;
  }

  @Step("Заполнить город музея: {city}")
  public MuseumFormModal setCity(@NonNull String city) {
    container.$("input[name='city']").shouldBe(Condition.visible).setValue(city);
    return this;
  }

  @Step("Заполнить описание музея")
  public MuseumFormModal setDescription(@NonNull String description) {
    container.$("textarea[name='description']").shouldBe(Condition.visible).setValue(description);
    return this;
  }

  @Step("Загрузить фото музея")
  public MuseumFormModal uploadPhoto(@NonNull File file) {
    container.$("input[name='photo']").shouldBe(Condition.exist).uploadFile(file);
    return this;
  }

  @Step("Выбрать первую страну в списке")
  public MuseumFormModal selectFirstCountry() {
    container.$("select[name='countryId']").shouldBe(Condition.visible)
        .$$("option")
        .shouldHave(CollectionCondition.sizeGreaterThan(0));
    container.$("select[name='countryId']").selectOption(0);
    return this;
  }

  @Step("Отправить форму музея")
  public MuseumFormModal submit() {
    container.$("button[type='submit']").shouldBe(Condition.visible).click();
    return this;
  }

  @Step("Проверить сообщение об ошибке: {errorText}")
  public MuseumFormModal shouldHaveError(@NonNull String errorText) {
    $$("span.text-error-400").findBy(Condition.exactText(errorText)).shouldBe(Condition.visible);
    return this;
  }
}
