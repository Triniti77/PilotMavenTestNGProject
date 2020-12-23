package ui.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;
import ui.BaseUITest;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class RozetkaBasketTest extends BaseUITest {
    String searchText = "samsung";

    @Test
    public void basketIconTest() {
        driver.get("https://rozetka.com.ua");
        driver.findElement(By.name("search")).sendKeys(searchText + Keys.ENTER);

        By firstProductBy = By.cssSelector("a.goods-title__picture");
        wait.until(presenceOfElementLocated(firstProductBy));
        driver.findElement(firstProductBy);

        // verify basket is clean
        By buyButtonBy = By.cssSelector("button.buy-button.button.button_with_icon");
        wait.until(presenceOfElementLocated(buyButtonBy));
        assertEquals(driver.findElements(By.cssSelector("a>svg+span")).size(), 0);
        scrollToElement(driver.findElement(buyButtonBy));
        driver.findElement(buyButtonBy).click();
        By createdOrderBy = By.xpath("//div[@class='cart-receipt']//*[contains(@href,'checkout)]");
        wait.until(presenceOfElementLocated(buyButtonBy));
        scrollToElement(driver.findElement(createdOrderBy));
        driver.findElement(createdOrderBy).click();

        driver.navigate().back();

        assertEquals(driver.findElement(By.cssSelector("a>svg+span")).getText(), "1");
    }
}