package tests;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.github.javafaker.Faker;
import config.EnvironmentConfig;
import config.StudentConfig;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.guru.qa.LoginSteps;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.MINOR;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({SoftAssertsExtension.class})
public class LoginTest extends BaseTest{
    private final StudentConfig studentConfig = ConfigFactory.create(StudentConfig.class);
    private final EnvironmentConfig envConfig = ConfigFactory.create(EnvironmentConfig.class);
    private final LoginSteps steps = new LoginSteps();
    private final Faker faker = new Faker();

    @BeforeEach
    void openBaseUrl() {
        step("Переход на страницу " + envConfig.getBaseUrl(), () -> {
            open(envConfig.getBaseUrl());
        });
    }

    @AfterEach
    void afterEach() {
        step("Чистка куков", Selenide::clearBrowserCookies);
    }

    @Test
    @Owner("GorbatenkoVA")
    @Severity(CRITICAL)
    @Story("Проверка авторизационной формы")
    @DisplayName("Логин с неправильной почтой не пропустит в личный кабинет.")
    void testThatUserWithErrorEmailNotLogin() {
        String randomEmail = faker.internet().emailAddress();
        String studentPassword = studentConfig.getStudentPassword();

        steps.goEntranceToPersonalAccount();
        steps.setEmail(randomEmail);
        steps.setPassword(studentPassword);
        steps.loginToPersonalAccount();
        steps.checkButtonDisappear("Войти");
        steps.checkButtonVisible("Неверный пароль");
        step("Ожидание 2 секунды", () -> {
            sleep(2000);
        });
        steps.checkButtonDisappear("Неверный пароль");
        steps.checkButtonVisible("Войти");
    }

    @Test
    @Owner("GorbatenkoVA")
    @Severity(CRITICAL)
    @Story("Проверка авторизационной формы")
    @DisplayName("Логин с неправильным паролем не пропустит в личный кабинет.")
    void testThatUserWithErrorPasswordNotLogin() {
        String studentEmail = studentConfig.getStudentEmail();
        String randomPassword = faker.hacker().abbreviation();

        steps.goEntranceToPersonalAccount();
        steps.setEmail(studentEmail);
        steps.setPassword(randomPassword);
        steps.loginToPersonalAccount();
        steps.checkButtonDisappear("Войти");
        steps.checkButtonVisible("Неверный пароль");
        step("Ожидание 2 секунды", () -> {
            sleep(2000);
        });
        steps.checkButtonDisappear("Неверный пароль");
        steps.checkButtonVisible("Войти");
    }

    @Test
    @Owner("GorbatenkoVA")
    @Severity(CRITICAL)
    @Story("Проверка авторизационной формы")
    @DisplayName("Логин с валидными данными ведёт в личный кабинет.")
    void testCheckThatStudentCanLogin() {
        String studentEmail = studentConfig.getStudentEmail();
        String studentPassword = studentConfig.getStudentPassword();

        steps.goEntranceToPersonalAccount();
        steps.setEmail(studentEmail);
        steps.setPassword(studentPassword);
        steps.loginToPersonalAccount();
        steps.checkPersonalArea();
    }

    @Test
    @Owner("GorbatenkoVA")
    @Severity(MINOR)
    @Story("Проверка редиректа главной страницы")
    @DisplayName("Авторизованного студента переводит в личный кабинет с главной страницы.")
    void testCheckLoginedStudentRedirectToPersonalAccountFromMainPage() {
        String studentEmail = studentConfig.getStudentEmail();
        String studentPassword = studentConfig.getStudentPassword();

        steps.goEntranceToPersonalAccount();
        steps.setEmail(studentEmail);
        steps.setPassword(studentPassword);
        steps.loginToPersonalAccount();
        steps.checkPersonalArea();
        step("Переход на страницу " + envConfig.getBaseUrl(), () -> {
            open(envConfig.getBaseUrl());
        });
        steps.checkPersonalArea();
    }

    @Test
    @Owner("GorbatenkoVA")
    @Severity(MINOR)
    @Story("Проверка редиректа главной страницы")
    @DisplayName("После выхода из личного кабинета главная страница доступна.")
    void testCheckLogoutStudentCanUseMainPage() {
        String studentEmail = studentConfig.getStudentEmail();
        String studentPassword = studentConfig.getStudentPassword();

        steps.goEntranceToPersonalAccount();
        steps.setEmail(studentEmail);
        steps.setPassword(studentPassword);
        steps.loginToPersonalAccount();
        steps.checkPersonalArea();
        step("Открыть профиль", () -> {
            $(".menu-item .menu-item-icon").click();
        });
        step("Выйти из профиля", () -> {
            $(".menu-item-logout").click();
        });
        step("Переход на страницу " + envConfig.getBaseUrl(), () -> {
            open(envConfig.getBaseUrl());
        });
        step("Проверка адреса страницы " + envConfig.getBaseUrl(), () -> {
            assertEquals(envConfig.getBaseUrl(), url());
        });
    }
}
