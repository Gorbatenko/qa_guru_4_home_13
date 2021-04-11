package tests;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.junit5.SoftAssertsExtension;
import com.github.javafaker.Faker;
import io.qameta.allure.AllureId;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static io.qameta.allure.SeverityLevel.NORMAL;

@ExtendWith({SoftAssertsExtension.class})
public class QaGuruTest extends BaseTest {
    private final Faker faker = new Faker();

    private String expectedStartNextCohortMonth;

    @Test
    @Tag("mainPage")
    @Severity(NORMAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка общей информации")
    @DisplayName("Наличие всех заявленных инструментов в программе обучения")
    void testCheckAllInstrumentsInEducationProgram() {
        List<String> skills = new ArrayList<>();
        step("Составление списка заявленных инструментов", () -> {
            ElementsCollection skillsList = $$(".skills-box p").shouldHaveSize(18);
            for (SelenideElement skill : skillsList) {
                skills.add(skill.text());
            }
        });

        step("Просмотр программы обучения", () -> {
            $("#myBtn").click();
        });

        step("Поиск заявленных инструментов в программе обучения", () -> {
            for (String skill : skills) {
                $(".lesson-list").shouldHave(text(skill));
            }
        });
    }

    @Test
    @Tag("mainPage")
    @Severity(NORMAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка общей информации")
    @DisplayName("Наличие всех заявленных преподавателей в программе обучения")
    void testCheckAllLectorsInEducationProgram() {
        List<String> trainers = new ArrayList<>();
        trainers.add("Ерошенко");
        trainers.add("Виноградов");
        trainers.add("Орлов");
        trainers.add("Васенков");
        trainers.add("Данилов");

        step("Просмотр программы обучения", () -> {
            $("#myBtn").click();
        });

        step("Поиск заявленных преподавателей в программе обучения", () -> {
            for (String trainer : trainers) {
                $(".lesson-list").shouldHave(text(trainer));
            }
        });
    }

    @Test
    @Tag("mainPage")
    @Severity(CRITICAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка записи на курс")
    @DisplayName("Кнопка 'Записаться' не пропустит без почты.")
    void testUserCantSendApplicationForParticipationWithoutEmail() {
        String firstName = faker.name().firstName();
        String phoneNumber = faker.phoneNumber().subscriberNumber(10);
        String expectedErrorMessage = "Не заполнено поле Введите ваш эл. адрес";

        step("Переход к записи на курс", () -> {
            $x("//header//a[contains(text(),'Начать обучение')]").click();
        });
        step("Заполнение имени " + firstName, () -> {
            $("[placeholder='Введите ваше имя']").val(firstName);
        });
        step("Заполнение телефона " + phoneNumber, () -> {
            $("[placeholder='Введите ваш телефон']").val(phoneNumber);
        });
        step("Отправка заявки", () -> {
            $x("//button[contains(text(),'Записаться')]").click();
        });
        step("Проверка подсказки: " + expectedErrorMessage, () -> {
            $(".error-message").shouldHave(text(expectedErrorMessage));
        });
    }

    @Test
    @Tag("mainPage")
    @Severity(CRITICAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка записи на курс")
    @DisplayName("Кнопка 'Записаться' не пропустит без имени.")
    void testUserCantSendApplicationForParticipationWithoutName() {
        String emailAddress = faker.internet().emailAddress();
        String phoneNumber = faker.phoneNumber().subscriberNumber(10);
        String expectedErrorMessage = "Не заполнено поле Введите ваше имя";

        step("Переход к записи на курс", () -> {
            $x("//header//a[contains(text(),'Начать обучение')]").click();
        });
        step("Заполнение адреса " + emailAddress, () -> {
            $("[placeholder='Введите ваш эл. адрес']").val(emailAddress);
        });
        step("Заполнение телефона " + phoneNumber, () -> {
            $("[placeholder='Введите ваш телефон']").val(phoneNumber);
        });
        step("Отправка заявки", () -> {
            $x("//button[contains(text(),'Записаться')]").click();
        });
        step("Проверка подсказки: " + expectedErrorMessage, () -> {
            $(".error-message").shouldHave(text(expectedErrorMessage));
        });
    }

    @Test
    @Tag("mainPage")
    @Severity(CRITICAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка записи на курс")
    @DisplayName("Кнопка 'Записаться' не пропустит без телефона.")
    void testUserCantSendApplicationForParticipationWithoutPhone() {
        String emailAddress = faker.internet().emailAddress();
        String firstName = faker.name().firstName();
        String expectedErrorMessage = "Не заполнено поле Введите ваш телефон";

        step("Переход к записи на курс", () -> {
            $x("//header//a[contains(text(),'Начать обучение')]").click();
        });
        step("Заполнение адреса " + emailAddress, () -> {
            $("[placeholder='Введите ваш эл. адрес']").val(emailAddress);
        });
        step("Заполнение имени " + firstName, () -> {
            $("[placeholder='Введите ваше имя']").val(firstName);
        });
        step("Отправка заявки", () -> {
            $x("//button[contains(text(),'Записаться')]").click();
        });
        step("Проверка подсказки: " + expectedErrorMessage, () -> {
            $(".error-message").shouldHave(text(expectedErrorMessage));
        });
    }

    @Test
    @Tag("mainPage")
    @Severity(NORMAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка контактов")
    @DisplayName("Ссылка в разделе 'Как оплатить' ведёт в телеграм.")
    void testCheckTelegramLinkOnPaymentQuestions() {
        String expectedLink = "https://t.me/cnokoino";

        step("Раскрыть список 'Как оплатить?' в часто задаваемых вопросах.", () -> {
            $(byText("Как оплатить?")).click();
        });
        step("Проверить, что ссылка " + expectedLink + " ведёт в телеграм", () -> {
            $(byText(expectedLink))
                    .shouldHave(attribute("href", expectedLink));
        });
    }

    @Test
    @Tag("mainPage")
    @Severity(CRITICAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка общей информации")
    @DisplayName("Цена обучения для разных уровней актуальна.")
    void testCheckPrice() {
        step("Переход к прайсам", () -> {
            $(byText("Сколько стоит курс?")).scrollTo();
            sleep(500);
        });
        step("Проверка цены за обучение", () -> {
            $$("#offers h3")
                    .shouldHave(texts(
                            "Первый вариант 15 000 ₽",
                            "Второй вариант 22 500 ₽",
                            "Третий вариант 28 000 ₽"));
        });

    }

    @Test
    @Tag("mainPage")
    @AllureId("2230")
    @Severity(CRITICAL)
    @Owner("GorbatenkoVA")
    @Feature("Проверка общей информации")
    @DisplayName("В описании скидки актуальный месяц.")
    void testCheckMonthAtSale() {
        step("В форме записи на вводное занятие узнать месяц", () -> {
            String textHeading = $("#last_form h2").scrollTo().text();
            sleep(500);
            expectedStartNextCohortMonth = textHeading.substring(textHeading.lastIndexOf(" ") + 1);
        });
        step("Раскрыть список 'Скидки есть?' в часто задаваемых вопросах.", () -> {
            $(byText("Скидки есть?")).click();
        });
        step("Проверить, что для скидки нужно оплатить до '" + expectedStartNextCohortMonth + "'", () -> {
            $(byText("Скидки есть, при оплате до"))
                    .shouldHave(text(expectedStartNextCohortMonth));
        });
    }
}
