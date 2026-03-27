package io.student.rcc.page.component;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Condition;

public class ProfileFormModal extends BaseComponent {

  public ProfileFormModal() {
    super($("form.modal-form"));
  }

  public ProfileFormModal setFirstname(String firstname) {
    container.$("input[name='firstname']").shouldBe(Condition.visible).setValue(firstname);
    return this;
  }

  public ProfileFormModal setSurname(String surname) {
    container.$("input[name='surname']").shouldBe(Condition.visible).setValue(surname);
    return this;
  }

  public ProfileFormModal submit() {
    container.$("button[type='submit']").shouldBe(Condition.visible).click();
    return this;
  }

  public ProfileFormModal shouldHaveFirstname(String firstname) {
    container.$("input[name='firstname']").shouldBe(Condition.visible).shouldHave(Condition.value(firstname));
    return this;
  }

  public ProfileFormModal shouldHaveSurname(String surname) {
    container.$("input[name='surname']").shouldBe(Condition.visible).shouldHave(Condition.value(surname));
    return this;
  }
}
