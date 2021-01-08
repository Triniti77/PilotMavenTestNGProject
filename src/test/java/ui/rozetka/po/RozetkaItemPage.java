package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static ui.Helpers.scrollToElement;

public class RozetkaItemPage extends RozetkaPageBase {
    @FindBy(css = "button.buy-button.button.button_with_icon")
    WebElement buyButton;

    @FindBy(xpath = "//div[@class='cart-receipt']//*[contains(@href,'checkout')]")
    WebElement orderButton;

    public RozetkaItemPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    public void putCurrentItemToBasket() {
        wait.until(visibilityOf(buyButton));
        scrollToElement(driver, buyButton);
        buyButton.click();
    }

    public void orderCurrentItem() {
        wait.until(visibilityOf(buyButton));
        wait.until(visibilityOf(orderButton));
        scrollToElement(driver, orderButton);
        orderButton.click();
    }

    private void waitPageLoaded() {
        wait.until(presenceOfElementLocated(By.cssSelector(".product-about")));
    }

    public void addToComparison() {
        By compareButtonBy = By.cssSelector("button.compare-button");
        wait.until(elementToBeClickable(compareButtonBy));
        try {
            driver.findElement(compareButtonBy).click();
        } catch (Exception e) {
//            wait.until(presenceOfElementLocated(compareButtonBy));
            makePause(1000);
            driver.findElement(compareButtonBy).click();
        }
    }

    public String getItemTitle() {
        return getTitleElement().getText();
    }

    private WebElement getTitleElement() {
        return wait.until(visibilityOfElementLocated(By.cssSelector("h1.product__title")));
    }
}
