package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestGood() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильев-Иванов Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim());
    }

    @Test
    void shouldTestNamePhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильев Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+56841288746");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim());
    }

    @Test
    void shouldTestBadName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vasilev Vasiy");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+56841288746");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldTestBadNoName() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+56841288746");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldTestBadPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильев Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("89428583321");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }

    @Test
    void shouldTestBadNoPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильев Василий");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Поле обязательно для заполнения", driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim());
    }
    @Test
    void shouldTestCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Васильев Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+56841288746");
        driver.findElement(By.className("button")).click();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid ")).getText());
    }
}
