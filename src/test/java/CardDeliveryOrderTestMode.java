import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class CardDeliveryOrderTestMode {
    private static String serviceUrl = "http://localhost:9999";
    private static String cityInput = "[data-test-id='city'] input";
    private static String nameInput = "[data-test-id='name'] input";
    private static String dateInput = "[data-test-id='date'] input";
    private static String phoneInput = "[data-test-id='phone'] input";
    private static String agreement = "[data-test-id=agreement]";
    private static String button = ".button";
    private static String invalid = ".input_invalid .input__sub";
    private static LocalDate localDate = LocalDate.now();


    static void datePicker() {
        $(dateInput).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $(dateInput).setValue(Generator.getNewDate());
    }

    static void defaultName() {
        $(nameInput).setValue(Generator.getName());
    }

    static void defaultPhone() {
        $(phoneInput).setValue(Generator.getPhone());
    }

    @Test
    @DisplayName(value = "проверка форм на корректные входные данные")
    void checkVerificationFormWithCorrectInput() {
        open(serviceUrl);
        $(cityInput).setValue(Generator.getCity());
        datePicker();
        defaultName();
        defaultPhone();
        $(agreement).click();
        $(button).click();
        // Проверка заполнения форм и перепланирования даты
        open(serviceUrl);
        $(cityInput).setValue(Generator.getCity());
        datePicker();
        defaultName();
        defaultPhone();
        $(agreement).click();
        $(button).click();
        $("[data-test-id=replan-notification]").waitUntil(Condition.visible, 3000);
        $$("[class=button__text]").find(exactText("Перепланировать")).click();
        $(withText("Успешно!")).waitUntil(Condition.visible, 3000);
    }

    @Test
    @DisplayName(value = "тест на неверно введенную дату")
    void wrongDateSelectionTest() {
        open("http://localhost:9999");
        $(cityInput).setValue(Generator.getCity());
        $(dateInput).sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        $(dateInput).setValue(localDate.format(DateTimeFormatter.ofPattern("dd.MM.YYYY")));
        defaultName();
        defaultPhone();
        $(agreement).click();
        $(button).click();
        $(invalid).shouldHave(text("Заказ на выбранную дату невозможен"));
    }

    @Test
    @DisplayName(value = "тест на неверно введенный город")
    void wrongCitySelectionTest() {
        open(serviceUrl);
        $(cityInput).setValue("Норильск");
        datePicker();
        defaultName();
        defaultPhone();
        $(agreement).click();
        $(button).click();
        $(invalid).shouldHave(text("Доставка в выбранный город недоступна"));
    }

    @Test
    @DisplayName(value = "тест на неверно введенное Имя")
    void wrongNameSelectionTest() {
        open(serviceUrl);
        $(cityInput).setValue(Generator.getCity());
        datePicker();
        $(nameInput).setValue("Ivanov Ivan");
        defaultPhone();
        $(agreement).click();
        $(button).click();
        $(invalid).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    @DisplayName(value = "Проверка на букву Ё")
    void checkOnYo() {
        open(serviceUrl);
        $(cityInput).setValue(Generator.getCity());
        datePicker();
        $(nameInput).setValue("Семёнов Семён");
        defaultPhone();
        $(agreement).click();
        $(button).click();
        $(withText("Успешно!")).shouldBe(Condition.visible);
    }

    @Test
    @DisplayName(value = "тест на неверно введенный номер телефона")
    void wrongPhoneSelectionTest() {
        open(serviceUrl);
        $(cityInput).setValue(Generator.getCity());
        datePicker();
        defaultName();
        $(phoneInput).setValue("+790123456");
        $(agreement).click();
        $(button).click();
        $(invalid).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    @DisplayName(value = "Проверка на пустые поля")
    void checkForEmptyField() {
        open(serviceUrl);
        datePicker();
        defaultName();
        defaultPhone();
        $(agreement).click();
        $(button).click();
        $(invalid).shouldHave(text("Поле обязательно для заполнения"));
    }

    @Test
    @DisplayName(value = "Проверка на отсутствия согласия на условия персональных данных")
    void verificationNotAgreement() {
        open(serviceUrl);
        $(cityInput).setValue(Generator.getCity());
        datePicker();
        defaultName();
        defaultPhone();
        $(button).click();
        $("[class='checkbox__text']").shouldHave(text("Я соглашаюсь с условиями обработки и " +
                "использования моих персональных данных"));
    }
}