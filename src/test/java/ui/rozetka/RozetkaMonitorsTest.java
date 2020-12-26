package ui.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ui.BaseUITest;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class RozetkaMonitorsTest extends BaseUITest {
    static private final int PRODUCTS_COMPARES_NUM = 2;
    static private final double MAX_PRICE = 3000.0;

    @Test
    public void monitorsTest() {
        driver.get("https://rozetka.com.ua");

//        Actions actions = new Actions(driver);
//        By menuItems = By.cssSelector(".menu-categories__link");
//        WebElement menuItem = driver.findElements(menuItems).get(0);
//        assertEquals(menuItem.getAttribute("href").substring(23,31), "computer");
//        actions.moveToElement(menuItem).perform();
//        makePause(1000);
//        String hoveredCss = ".menu-categories__item.menu-categories__item_state_hovered";
//        WebElement openedMenu = driver.findElement(By.cssSelector(hoveredCss));
//        WebElement monitors = openedMenu.findElement(By.partialLinkText("Мониторы"));
//        monitors.click();

        driver.findElement(By.cssSelector(".menu-toggler")).click();
        List<WebElement> items = driver.findElements(By.xpath("//*[@class='menu__main-cats-inner']//*[@class='menu__link'][contains(.,'Мониторы')]"));
        assertEquals(items.size(), 1, "Link to monitors found");
        items.get(0).click();

        List<Double> prices = new ArrayList<>();
        List<String> ids = new ArrayList<>();
        List<String> names = new ArrayList<>();

        int pages = 0;
        int inComparison = 0;

        while (pages < 10) {
            wait.until(presenceOfElementLocated(By.cssSelector(".catalog-grid")));

            By goodsTitleInnerBy = By.cssSelector(".goods-tile__inner");
            List<WebElement> goods = driver.findElements(goodsTitleInnerBy);

            WebElement clickItem = null;

            for (WebElement item : goods) {
                By priceValueBy = By.cssSelector(".goods-tile__price-value");
                wait.until(presenceOfElementLocated(priceValueBy));
                WebElement priceElement;
                // https://stackoverflow.com/questions/18225997/stale-element-reference-element-is-not-attached-to-the-page-document
                try {
                    priceElement = item.findElement(priceValueBy);
                } catch (StaleElementReferenceException e) {
                    makePause(1000);
                    priceElement = item.findElement(priceValueBy);
                }
                String priceText = priceElement.getText();
                double price = priceToDouble(priceText);
                if (price < MAX_PRICE) {
                    String goodsId = item.findElement(By.cssSelector("a.goods-tile__picture")).getAttribute("href");
                    if (ids.size() == 0) {
                        ids.add(goodsId);
                    } else if (goodsId.equals(ids.get(0))) {
                        continue;
                    }

                    if (prices.size() == 0) {
                        prices.add(price);
                    } else if (price >= prices.get(0)) {
                        continue;
                    }
                    clickItem = item;
                    break;
                }
            }

            if (clickItem != null) {
                clickItem.click();

                By compareButtonBy = By.cssSelector("button.compare-button");
                wait.until(presenceOfElementLocated(compareButtonBy));
                try {
                    driver.findElement(compareButtonBy).click();
                } catch (StaleElementReferenceException e) {
                    makePause(1000);
                    driver.findElement(compareButtonBy).click();
                }

                wait.until(presenceOfElementLocated(By.cssSelector("h1.product__title")));
                names.add(driver.findElement(By.cssSelector("h1.product__title")).getText());

                By compare1By = By.cssSelector(".header-actions__button-counter");
                wait.until(presenceOfElementLocated(compare1By));
                assertEquals(driver.findElement(compare1By).getText(), prices.size() == 1 ? "1" : "2");
                inComparison++;

                if (inComparison < PRODUCTS_COMPARES_NUM) {
                    driver.navigate().back();
                    wait.until(presenceOfElementLocated(goodsTitleInnerBy));
                    continue;
                }
            }

            if (inComparison == PRODUCTS_COMPARES_NUM) {
                break;
            }
            driver.findElement(By.cssSelector(".button.pagination__direction_type_forward")).click();
            pages++;
        }
        assertEquals(inComparison, PRODUCTS_COMPARES_NUM, "Selected 2 elements for comparison");

        driver.findElement(By.cssSelector(".header-actions__button_type_compare")).click();
        wait.until(presenceOfElementLocated(By.cssSelector(".modal__content")));
        driver.findElement(By.cssSelector(".modal__content .comparison-modal__link")).click();

        wait.until(presenceOfElementLocated(By.cssSelector(".products-grid")));
        List<WebElement> products = driver.findElements(By.cssSelector("..products-grid .product__body"));

        assertEquals(PRODUCTS_COMPARES_NUM, products.size(), "Products number in comparison");

        for (WebElement product : products) {
            String productName = product.findElement(By.cssSelector("a.product__heading")).getText();

            int index = 0;
            for (String name : names) {
                if (name.equals(productName) ) {
                    break;
                }
                index++;
            }

            String oldPrice = null;
            try {
                oldPrice = product.findElement(By.cssSelector(".product__price.product__price--red product__price--old")).getText();
            } catch (NoSuchElementException e) {
            }
            String priceText = product.findElement(By.cssSelector(".product__price.product__price--red")).getText();
            if (oldPrice != null) {
                priceText = priceText.replace(oldPrice, "");
            }
            double productPrice = priceToDouble(priceText);

            assertEquals(productPrice, prices.get(index), "Item "+productName+" ("+index+") has the same price");
        }
    }

    public void makePause(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public double priceToDouble(String priceText) {
        String priceTextClean = priceText.replaceAll("\\D+", "");
        return Double.parseDouble(priceTextClean);
    }
}
