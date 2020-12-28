package ui.guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import ui.BaseUITest;

import static org.testng.Assert.assertTrue;

public class DragAndDropTest extends BaseUITest {

    @Test
    public void test() {
        driver.get("http://demo.guru99.com/test/drag_drop.html");

        WebElement from1 = driver.findElement(By.xpath("//*[@id='credit2']/a"));
        WebElement to1 = driver.findElement(By.xpath("//*[@id='bank']/li"));
        WebElement from2 = driver.findElement(By.xpath("//*[@id='credit1']/a"));
        WebElement to2 = driver.findElement(By.xpath("//*[@id='load']/li"));
        WebElement from3 = driver.findElement(By.xpath("//*[@id='fourth']/a"));
        WebElement to3 = driver.findElement(By.xpath("//*[@id='amt7']/li"));
        WebElement from4 = driver.findElement(By.xpath("//*[@id='fourth']/a"));
        WebElement to4 = driver.findElement(By.xpath("//*[@id='amt8']/li"));

        Actions act = new Actions(driver);
        act.dragAndDrop(from1, to1).build().perform();
        act.dragAndDrop(from2, to2).build().perform();
        act.dragAndDrop(from3, to3).build().perform();
        act.dragAndDrop(from4, to4).build().perform();

        assertTrue(driver.findElement(By.xpath("//a[contains(text(),'Perfect')]")).isDisplayed(), "Text 'perfect' was not displayed");
    }
}
