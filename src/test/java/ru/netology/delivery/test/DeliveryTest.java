package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {


    @BeforeEach
    void setup() {
        Configuration.headless = true;
//        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {

        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
        // TODO: добавить логику теста в рамках которого будет выполнено планирование и перепланирование встречи.
        // Для заполнения полей формы можно использовать пользователя validUser и строки с датами в переменных
        // firstMeetingDate и secondMeetingDate. Можно также вызывать методы generateCity(locale),
        // generateName(locale), generatePhone(locale) для генерации и получения в тесте соответственно города,
        // имени и номера телефона без создания пользователя в методе generateUser(String locale) в датагенераторе

        $("[data-test-id=\"city\"] input").setValue(validUser.getCity());
        SelenideElement date = $("[data-test-id=date] input");
        date.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        date.setValue(firstMeetingDate);
        $("[data-test-id=\"name\"] input").setValue(validUser.getName());
        $("[data-test-id=\"phone\"] input").setValue(validUser.getPhone());
        $("[data-test-id=\"agreement\"] .checkbox__box").click();
        $(By.className("button__content")).click();
        $("[data-test-id=\"success-notification\"] [class=\"notification__content\"]").shouldBe(Condition.appear)
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + firstMeetingDate));
        date.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        date.setValue(secondMeetingDate);
        $(By.className("button__content")).click();
        $("[data-test-id=\"replan-notification\"] [class=\"notification__title\"]").shouldBe(Condition.appear)
                .shouldHave(Condition.exactText("Необходимо подтверждение"));
        $x("//*[@class=\"button__text\"] [text()=\"Перепланировать\"]").click();

    }
}
