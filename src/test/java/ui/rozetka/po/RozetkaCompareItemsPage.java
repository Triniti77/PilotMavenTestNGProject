package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RozetkaCompareItemsPage extends RozetkaPageBase{

    public RozetkaCompareItemsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public List<RozetkaComparePageItemElement> getAllItems() {
        wait.until(presenceOfElementLocated(By.cssSelector(".products-grid")));
        List<WebElement> products = driver.findElements(By.cssSelector(".products-grid .product__body"));
        List<RozetkaComparePageItemElement> compareItems = new ArrayList<>();
        for (WebElement product : products) {
            compareItems.add(new RozetkaComparePageItemElement(product));
        }
        return compareItems;
    }


    public class RozetkaComparePageItemElement {

        private final WebElement element;

        private RozetkaComparePageItemElement(WebElement item) {
            element = item;    
        }

        public String getTitle() {
            return element.findElement(By.cssSelector("a.product__heading")).getText();
        }

        public double getPrice() {
            String oldPrice = null;
            try {
                oldPrice = element.findElement(By.cssSelector(".product__price.product__price--red .product__price--old")).getText();
            } catch (NoSuchElementException e) {
            }
            String priceText = element.findElement(By.cssSelector(".product__price.product__price--red")).getText();
            if (oldPrice != null) {
                priceText = priceText.replace(oldPrice, "");
            }
            return priceToDouble(priceText);

        }
    }
}
