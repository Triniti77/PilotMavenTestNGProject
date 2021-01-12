package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RozetkaCompareElement extends RozetkaPageBase {
    public RozetkaCompareElement(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public int getCounter() {
        By compare1By = By.cssSelector(".header-actions__button-counter");
        List<WebElement> items = driver.findElements(compare1By);
        if (items.size() < 1) {
            return 0;
        }
        int number = Integer.parseInt(driver.findElement(compare1By).getText());
        return number;
    }

    public void openCompareListSelector() {
        driver.findElement(By.cssSelector(".header-actions__button_type_compare")).click();
        wait.until(presenceOfElementLocated(By.cssSelector(".modal__content")));
    }

    public void goFirstCompareList() {
        driver.findElement(By.cssSelector(".modal__content .comparison-modal__link")).click();
    }
}
