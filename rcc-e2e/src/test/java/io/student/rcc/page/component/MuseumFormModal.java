package io.student.rcc.page.component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import java.io.File;

public class MuseumFormModal extends BaseComponent {

  public MuseumFormModal() {
    super($("form.modal-form"));
  }

  public MuseumFormModal setTitle(String title) {
    container.$("input[name='title']").shouldBe(Condition.visible).setValue(title);
    return this;
  }

  public MuseumFormModal setCity(String city) {
    container.$("input[name='city']").shouldBe(Condition.visible).setValue(city);
    return this;
  }

  public MuseumFormModal setDescription(String description) {
    container.$("textarea[name='description']").shouldBe(Condition.visible).setValue(description);
    return this;
  }

  public MuseumFormModal uploadPhoto(File file) {
    container.$("input[name='photo']").shouldBe(Condition.exist).uploadFile(file);
    return this;
  }

  public MuseumFormModal selectFirstCountry() {
    container.$("select[name='countryId']").shouldBe(Condition.visible)
        .$$("option")
        .shouldHave(CollectionCondition.sizeGreaterThan(0));
    container.$("select[name='countryId']").selectOption(0);
    return this;
  }

  public MuseumFormModal submit() {
    container.$("button[type='submit']").shouldBe(Condition.visible).click();
    return this;
  }

  public MuseumFormModal shouldHaveError(String errorText) {
    $$("span.text-error-400").findBy(Condition.exactText(errorText)).shouldBe(Condition.visible);
    return this;
  }
}
