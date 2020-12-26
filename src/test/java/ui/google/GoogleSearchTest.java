package ui.google;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.BaseUITest;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.AssertJUnit.assertTrue;


public class GoogleSearchTest extends BaseUITest {

    @BeforeMethod
    public void startUp() {
        driver.get("https://google.com/ncr");
    }

    @Test
    public void testEnterSearch() {
        driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);

        WebElement stats = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));

        assertTrue(stats.getText().contains("results"));

        WebElement searchBox = driver.findElement(By.xpath("//h3/span"));
        List<WebElement> elements = driver.findElements(By.xpath("//h3/span"));

    }

    @Test
    public void testClickSearch() {
        driver.findElement(By.name("q")).sendKeys("cheese");
        // Не работает без паузы в 2с, даже wait.until
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(presenceOfElementLocated(By.name("btnK")));
        driver.findElement(By.name("btnK")).click();
        WebElement stats = wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));

        assertTrue(stats.getText().contains("results"));
    }

}
