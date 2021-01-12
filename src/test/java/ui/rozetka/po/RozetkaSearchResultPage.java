package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class RozetkaSearchResultPage extends RozetkaPageBase {
    private static Map<String, String> menuItems = Map.ofEntries(
            Map.entry("Мобильные телефоны", "mobile-phones")
    );

    @FindBy(css = "a.goods-tile__picture")
    List<WebElement> items;

    public RozetkaSearchResultPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void enterCatalogItem(int itemNum) throws Exception {
        if (itemNum >= items.size()) {
            throw new Exception("Cannot select catalog Item number over items count on the page");
        }
        items.get(itemNum).click();
    }

    public void goCategory(String categoryName) throws Exception {
        By filterCategoriesBy = By.cssSelector(".categories-filter__link");
        wait.until(presenceOfElementLocated(filterCategoriesBy));

        String href = menuItems.get(categoryName);
        if (href == null) {
            throw new Exception("No such category name "+categoryName);
        }

        List<WebElement> categories = driver.findElements(filterCategoriesBy);
        boolean clicked = false;

        for (WebElement category : categories) {
            String attributeHref = category.getAttribute("href");
            if (attributeHref == null) {
                continue;
            }
            if (attributeHref.contains("/"+href+"/")) {
                category.click();
                clicked = true;
                break;
            }
        }
        if (!clicked) {
            throw new Exception("Category " + categoryName+" not found");
        }
    }
}
