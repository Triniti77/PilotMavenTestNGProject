package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RozetkaBasketIconElement extends RozetkaPageBase {
    By headButtonBasketBy = By.cssSelector("a.header-actions__button_type_basket");

    public RozetkaBasketIconElement(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public int itemsInBasket() {
        WebElement basketIcon = waitBasketIconLoaded();
        List<WebElement> ordersIcon = basketIcon.findElements(By.cssSelector("svg+span"));
        return ordersIcon.size() < 1 ? 0 : Integer.parseInt(ordersIcon.get(0).getText());
    }

    private WebElement waitBasketIconLoaded() {
        return wait.until(presenceOfElementLocated(headButtonBasketBy));
    }
}
