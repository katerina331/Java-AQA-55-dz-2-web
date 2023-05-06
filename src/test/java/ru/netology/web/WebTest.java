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
//        System.setProperty("webdriver.chrome.driver","./driver/win/chromedriver.exe");
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
//        driver = new ChromeDriver();
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        driver.findElements(By.className("input__control")).get(0).sendKeys("Василий");
        driver.findElements(By.className("input__control")).get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", driver.findElement(By.className("paragraph")).getText().trim());
    }

    @Test
    void shouldTestNamePhone() {
        driver.findElements(By.className("input__control")).get(0).sendKeys("Васильев Василий");
        driver.findElements(By.className("input__control")).get(1).sendKeys("+56841288746");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", driver.findElement(By.className("paragraph")).getText().trim());
    }

    @Test
    void shouldTestBadName() {
        driver.findElements(By.className("input__control")).get(0).sendKeys("Vasilev Vasiy");
        driver.findElements(By.className("input__control")).get(1).sendKeys("+56841288746");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", driver.findElement(By.className("input__sub")).getText().trim());
    }

    @Test
    void shouldTestBadPhone() {
        driver.findElements(By.className("input__control")).get(0).sendKeys("Васильев Василий");
        driver.findElements(By.className("input__control")).get(1).sendKeys("89428583321");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", driver.findElements(By.className("input__sub")).get(1).getText().trim());
    }

    @Test
    void shouldTestCheckbox() {
        driver.findElements(By.className("input__control")).get(0).sendKeys("Васильев Василий");
        driver.findElements(By.className("input__control")).get(1).sendKeys("+56841288746");
        driver.findElement(By.className("button")).click();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", driver.findElement(By.className("input_invalid")).getText());
    }
}
