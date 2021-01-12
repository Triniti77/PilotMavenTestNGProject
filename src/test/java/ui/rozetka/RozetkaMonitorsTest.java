package ui.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.BaseUITest;
import ui.rozetka.po.*;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RozetkaMonitorsTest extends RozetkaTestBase {
    static private final int PRODUCTS_COMPARES_NUM = 2;
    static private final double MAX_PRICE = 3000.0;
    private RozetkaMainMenuElement mainMenu;
    private RozetkaCompareElement compare;

    @BeforeMethod
    public void initObjects() {
        this.mainMenu = new RozetkaMainMenuElement(driver, wait);
        this.compare = new RozetkaCompareElement(driver, wait);
    }

    @Test
    public void monitorsTest() throws Exception {
        mainMenu.goMenuItem(null,"Мониторы");

        List<Double> prices = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

//        int pages = 0;

        RozetkaCatalogPage pageCatalog = new RozetkaCatalogPage(driver, wait);
        while (pageCatalog.getCurrentPage() < 10) {

            List<RozetkaCatalogPage.RozetkaCatalogItemElement> goods = pageCatalog.getAllItems();
            RozetkaCatalogPage.RozetkaCatalogItemElement clickItem = null;

            for (RozetkaCatalogPage.RozetkaCatalogItemElement item : goods) {
                double price = item.getPrice();
                if (price < MAX_PRICE) {
                    String goodsId = item.getHash();
                    if (ids.size() != 0 && ids.contains(goodsId)) {
                        continue;
                    }

                    if (prices.size() != 0 && price >= prices.get(0)) {
                        continue;
                    }
                    prices.add(price);
                    ids.add(goodsId);
                    clickItem = item;
                    break;
                }
            }

            if (clickItem != null) {
                clickItem.go();

                RozetkaItemPage itemPage = new RozetkaItemPage(driver, wait);
                itemPage.addToComparison();
                names.add(itemPage.getItemTitle());
                int inComparison = compare.getCounter();

                assertTrue(inComparison == 1 || inComparison == 2, "Should be 1 or 2 elements in compare" );

                if (inComparison < PRODUCTS_COMPARES_NUM) {
                    driver.navigate().back();
                    continue;
                }
            }

            if (compare.getCounter() == PRODUCTS_COMPARES_NUM) {
                break;
            }

            pageCatalog.goNextPage();
        }

        assertEquals(compare.getCounter(), PRODUCTS_COMPARES_NUM, "Selected "+PRODUCTS_COMPARES_NUM+" elements for comparison");

        compare.openCompareListSelector();
        compare.goFirstCompareList();

        RozetkaCompareItemsPage comparePage = new RozetkaCompareItemsPage(driver, wait);
        List<RozetkaCompareItemsPage.RozetkaComparePageItemElement> products = comparePage.getAllItems();

//        wait.until(presenceOfElementLocated(By.cssSelector(".products-grid")));
//        List<WebElement> products = driver.findElements(By.cssSelector(".products-grid .product__body"));

        assertEquals(products.size(), PRODUCTS_COMPARES_NUM,"Products number in comparison should be " + PRODUCTS_COMPARES_NUM);

        for (RozetkaCompareItemsPage.RozetkaComparePageItemElement product : products) {
            String productName = product.getTitle();

            int index = 0;
            for (String name : names) {
                if (name.equals(productName) ) {
                    break;
                }
                index++;
            }
            double productPrice = product.getPrice();

            assertEquals(productPrice, prices.get(index), "Item "+productName+" ("+index+") has the same price");
        }
    }
}
