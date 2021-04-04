package pages.guru.qa;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.disappear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginSteps {

    @Step("Ввод почты {email}")
    public void setEmail(String email) {
        $(byName("email")).val(email);
    }

    @Step("Ввод пароля ***")
    public void setPassword(String password) {
        $(byName("password")).val(password);
    }

    @Step("Вход в личный кабинет")
    public void loginToPersonalAccount() {
        $x("//button[contains(text(),'Войти')]").click();
    }

    @Step("Проверка, что кнопка '{buttonName}' исчезла")
    public void checkButtonDisappear(String buttonName) {
        String xpath = String.format("//button[contains(text(),'%s')]", buttonName);
        $x(xpath).shouldBe(disappear);
    }

    @Step("Проверка, что кнопка '{buttonName}' появилась")
    public void checkButtonVisible(String buttonName) {
        String xpath = String.format("//button[contains(text(),'%s')]", buttonName);
        $x(xpath).shouldBe(visible);
    }

    @Step("Проверка нахождения в личном кабинете")
    public void checkPersonalArea() {
        $("body.gc-user-logined").shouldBe(visible);
    }

    @Step("Переход ко входу в личный кабинет")
    public void goEntranceToPersonalAccount() {
        $(".login").click();
    }
}
