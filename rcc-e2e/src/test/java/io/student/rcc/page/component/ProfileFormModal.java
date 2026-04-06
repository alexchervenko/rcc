package io.student.rcc.page.component;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import lombok.NonNull;

import static com.codeborne.selenide.Selenide.$;

public class ProfileFormModal extends BaseComponent {

  public ProfileFormModal() {
    super($("form.modal-form"));
  }

  @Step("Заполнить имя в профиле: {firstname}")
  public ProfileFormModal setFirstname(@NonNull String firstname) {
    container.$("input[name='firstname']").setValue(firstname);
    return this;
  }

  @Step("Заполнить фамилию в профиле: {surname}")
  public ProfileFormModal setSurname(@NonNull String surname) {
    container.$("input[name='surname']").setValue(surname);
    return this;
  }

  @Step("Сохранить профиль")
  public ProfileFormModal submit() {
    container.$("button[type='submit']").click();
    return this;
  }

  @Step("Проверить имя в профиле: {firstname}")
  public ProfileFormModal shouldHaveFirstname(@NonNull String firstname) {
    container.$("input[name='firstname']").shouldHave(Condition.value(firstname));
    return this;
  }

  @Step("Проверить фамилию в профиле: {surname}")
  public ProfileFormModal shouldHaveSurname(@NonNull String surname) {
    container.$("input[name='surname']").shouldHave(Condition.value(surname));
    return this;
  }
}
