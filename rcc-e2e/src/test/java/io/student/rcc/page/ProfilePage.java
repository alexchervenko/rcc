package io.student.rcc.page;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Condition;
import io.student.rcc.page.component.ProfileFormModal;

public class ProfilePage extends BasePage {

  public ProfilePage() {
    super("/");
  }

  @Override
  public ProfilePage open() {
    super.open();
    return this;
  }

  @Override
  public ProfilePage waitForOpen() {
    $x("//nav[@aria-label='Основная навигация']").shouldBe(Condition.visible);
    return this;
  }

  public void openProfileModal() {
    $x("//header//button[contains(@class, 'btn-icon')]").shouldBe(Condition.visible).click();
  }

  public ProfileFormModal profileFormModal() {
    ProfileFormModal modal = new ProfileFormModal();
    modal.shouldBeVisible();
    return modal;
  }
}
