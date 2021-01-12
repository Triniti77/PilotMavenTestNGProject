package ui.rozetka.po;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEquals;

public class RozetkaCatalogPage extends RozetkaPageBase {
    @FindBy(css = ".catalog-grid")
    WebElement catalog;
    List<RozetkaCatalogItemElement> catalogItems;
    int currentPage = -1;

    public RozetkaCatalogPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void go(int pageNum) {
        wait.until(presenceOfElementLocated(By.cssSelector(".catalog-grid")));
        catalogItems = null;

        if (pageNum == 0) {

        } else {
            WebElement nextPageBtn = driver.findElement(By.cssSelector(".button.pagination__direction_type_forward"));
            if (nextPageBtn.isEnabled()) {
                nextPageBtn.click();
            }
        }
        currentPage = pageNum;
        // go to another page
    }

    public void goNextPage() {

    }

    public int getCurrentPage() {
        return currentPage;
    }

    private void waitForCatalog() {
        wait.until(presenceOfElementLocated(By.cssSelector(".catalog-grid")));
    }

    public List<RozetkaCatalogItemElement> getAllItems() {
        if (getCurrentPage() < 0) {
            go(0);
        }
        waitForCatalog();

        By goodsTitleInnerBy = By.cssSelector(".goods-tile__inner");
        List<WebElement> goods = driver.findElements(goodsTitleInnerBy);
        catalogItems = new ArrayList<>();
        for (WebElement item : goods) {
            catalogItems.add(new RozetkaCatalogItemElement(item));
        }
        return catalogItems;
    }

    public void addFilterByProducer(String producerName) {
        waitForCatalog();
        By brandProducerBy = By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='producer']");
        WebElement producerBlock = driver.findElement(brandProducerBy);

        WebElement brandInput = producerBlock.findElement(By.cssSelector(".sidebar-search__input"));
        brandInput.sendKeys(producerName);
        producerBlock.findElement(By.cssSelector(".checkbox-filter__item label")).click();
    }

    private WebElement getPriceInput(int num) {
        waitForCatalog();
        List<WebElement> priceFilter = driver.findElements(By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='price']//input[contains(@class,'slider-filter__input')]"));
        return  priceFilter.get(num);
    }

    public void addFilterByPriceLow(int price) {
        WebElement priceMinInput = getPriceInput(0);
        priceMinInput.clear();
        priceMinInput.sendKeys(String.valueOf(price));
    }

    public void addFilterByPriceHigh(int price) {
        WebElement priceMaxInput = getPriceInput(1);
        priceMaxInput.clear();
        priceMaxInput.sendKeys(String.valueOf(price));
    }

    public void applyFilterByPrice() {
        By priceBy = By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='price']");
        WebElement priceBlock = driver.findElement(priceBy);
        priceBlock.findElement(By.cssSelector("button.slider-filter__button")).click();
    }

    public void addFilterByRam(String s) {
        waitForCatalog();
        driver.findElement(By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='38435']//a[@class='checkbox-filter__link']/label[contains(text(), '"+s+"')]")).click();
    }

    public void addFilterByStorage(String s) {
        waitForCatalog();
        driver.findElement(By.xpath("//div[contains(@class,'sidebar-block')][@data-filter-name='41404']//a[@class='checkbox-filter__link']/label[contains(text(), '"+s+"')]")).click();
    }

    public class RozetkaCatalogItemElement {
        WebElement element;
        WebElement link;
        By itemTitleBy = By.cssSelector(".goods-tile__title");


        private RozetkaCatalogItemElement(WebElement item) {
            element = item;
            By tilePicture = By.cssSelector("a.goods-tile__picture");
            try {
                link = element.findElement(tilePicture);
            } catch (Exception e) {
//                wait.until(presenceOfElementLocated(tilePicture));
                makePause(1000);
                link = element.findElement(tilePicture);
            }
        }

        public double getPrice() {
            By priceValueBy = By.cssSelector(".goods-tile__price-value");
            wait.until(elementToBeClickable(priceValueBy));
            WebElement priceElement;
            // https://stackoverflow.com/questions/18225997/stale-element-reference-element-is-not-attached-to-the-page-document
            try {
                priceElement = element.findElement(priceValueBy);
            } catch (StaleElementReferenceException e) {
                makePause(1000);
//                priceElement = wait.until(presenceOfElementLocated(priceValueBy));
                priceElement = element.findElement(priceValueBy);
            }
            String priceText = priceElement.getText();
            return priceToDouble(priceText);
        }

        public String getLink() {
            return link.getAttribute("href");
        }

        public String getHash() {
            return getLink().hashCode() + ":" + getPrice();
        }

        public void go() {
            link.click();
        }

        public String getTitle() {
            return element.findElement(itemTitleBy).getText();
        }
    }
}
