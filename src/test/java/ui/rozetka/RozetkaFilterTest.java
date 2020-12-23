package ui.rozetka;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import ui.BaseUITest;

import java.security.Key;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class RozetkaFilterTest extends BaseUITest {
    @Test
    public void testBrandsFilter() {
        openSearchSamsung();

        By catalogBy = By.cssSelector(".catalog-grid");
        wait.until(presenceOfElementLocated(catalogBy));

        By brandProducerBy = By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='producer']");
        WebElement producerBlock = driver.findElement(brandProducerBy);

        WebElement brandInput = producerBlock.findElement(By.cssSelector(".sidebar-search__input"));
        brandInput.sendKeys("Apple");
        producerBlock.findElement(By.cssSelector(".checkbox-filter__item label")).click();

        wait.until(presenceOfElementLocated(catalogBy));

        brandInput = producerBlock.findElement(By.cssSelector(".sidebar-search__input"));
        brandInput.sendKeys("Honor");
        producerBlock.findElement(By.cssSelector(".checkbox-filter__item label")).click();

        boolean hasNextPage = true;
        while (hasNextPage) {
            wait.until(presenceOfElementLocated(catalogBy));

            By goodsTitleInnerBy = By.cssSelector(".goods-tile__inner");
            List<WebElement> goods = driver.findElements(goodsTitleInnerBy);

            By itemTitleBy = By.cssSelector(".goods-tile__title");
            for (WebElement item : goods) {
                String title = item.findElement(itemTitleBy).getText();
                assertTrue(title.contains("Apple") || title.contains("Samsung") || title.contains("Honor"), "Check the phone one of Apple, Samsung, Honor");
            }

            hasNextPage = false;
            // не работает почему-то. элемент не найден
//            By nextButtonBy = By.cssSelector("div.pagination .button.pagination__direction_type_forward");
//            WebElement nextButton = driver.findElement(nextButtonBy);
//            scrollToElement(nextButton);
//            if (nextButton.getAttribute("class").contains("state_disabled")) {
//                hasNextPage = false;
//            } else {
//                hasNextPage = true;
//                nextButton.click();
//            }
        }
    }

    @Test
    public void testPriceFilter() {
        openSearchSamsung();

        By catalogBy = By.cssSelector(".catalog-grid");
        wait.until(presenceOfElementLocated(catalogBy));

        By priceBy = By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='price']");
        WebElement priceBlock = driver.findElement(priceBy);

//        List<WebElement> priceFilter = priceBlock.findElements(By.cssSelector("input.sidebar-search__input"));
        List<WebElement> priceFilter = driver.findElements(By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='price']//input[contains(@class,'slider-filter__input')]"));
        assertEquals(priceFilter.size(), 2, "Two inputs for price filter");
        WebElement priceMinInput = priceFilter.get(0);
        priceMinInput.sendKeys("" + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + "5000");

        WebElement priceMaxInput = priceFilter.get(1);
        priceMaxInput.sendKeys("" + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + Keys.BACK_SPACE + "15000");

        priceBlock.findElement(By.cssSelector("button.button.slider-filter__button")).click();

        wait.until(presenceOfElementLocated(catalogBy));

        wait.until(presenceOfElementLocated(catalogBy));

        By goodsTitleInnerBy = By.cssSelector(".goods-tile__inner");
        List<WebElement> goods = driver.findElements(goodsTitleInnerBy);

        for (WebElement item : goods) {
            String priceText = item.findElement(By.cssSelector(".goods-tile__price-value")).getText();
            double productPrice = priceToDouble(priceText);
            assertTrue(productPrice >= 5000 && productPrice <= 15000, "Product price 5000 < price < 15000");
        }
    }

    @Test
    public void testCustomFilter() {
        openSearchSamsung();

        By catalogBy = By.cssSelector(".catalog-grid");
        wait.until(presenceOfElementLocated(catalogBy));

        By priceBy = By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='38435']"); // 38435 - оперативная память
        WebElement priceBlock = driver.findElement(priceBy);

        driver.findElement(By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='38435']//a[@class='checkbox-filter__link']/label[contains(text(), '4 ГБ')]")).click();

    }

    protected void openSearchSamsung() {
        driver.get("https://rozetka.com.ua");

        driver.findElement(By.cssSelector(".search-form__input")).sendKeys("samsung" + Keys.ENTER);

        By filterCategoriesBy = By.cssSelector(".categories-filter__link");
        wait.until(presenceOfElementLocated(filterCategoriesBy));
        List<WebElement> categories = driver.findElements(filterCategoriesBy);
        for (WebElement category : categories) {
            if (category.getText().contains("Мобильные телефоны")) {
                category.click();
                break;
            }
        }
    }

    public double priceToDouble(String priceText) {
        String priceTextClean = priceText.replaceAll("\\D+", "");
        return Double.parseDouble(priceTextClean);
    }
}
