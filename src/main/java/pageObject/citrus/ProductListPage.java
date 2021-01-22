package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class ProductListPage extends BasePage {
    BasketFragment basketFragment = new BasketFragment();
    FiltersFragment filtersFragment = new FiltersFragment();


    public ProductListPage clickOnProductByName(String productName) {
        $x("//span[contains(text(), '"+productName+"')]").click();
        return this;
    }

    public ProductListPage waitForProductListToLoad() {
        super.waitForProductListToLoad();
        return this;
    }

    public ProductListPage waitForPageToLoad() {
        super.waitForPageToLoad();
//        super.closePopup();
        return this;
    }

    public ProductListPage closePopup() {
//        super.closePopup();
        return this;
    }

    public String readProductPrice(String productName) {
//        super.closePopup();
        String selector = "//h5[contains(text(), '"+productName+"')]/../../..//div[@class='base-price']/span";
        System.out.println(selector);
        return $x(selector).getText();
    }

    public ProductListPage addProductToBasket(String productName) {
//        super.closePopup();
        // /h5[contains(text(), 'Apple iPhone 12 64GB')]/../../..//div[@class='itm-footer-desc']//i[contains(@class,'icon-new-citrus-cart')]
        String selector = "//h5[contains(text(), '"+productName+"')]/../../..//div[@class='itm-footer-desc']//i[contains(@class,'icon-new-citrus-cart')]";
        System.out.println(selector);
        $x(selector).click();
        return this;
    }

    public ProductListPage addProductToBasket(SelenideElement product) {
        String selector = ".//div[@class='itm-footer-desc']//i[contains(@class,'icon-new-citrus-cart')]";
        product.$x(selector).click();
        $x("//div[contains(@class,'el-dialog__wrapper')]//button[@aria-label='Close'][@class='el-dialog__headerbtn']").click();
        return this;
    }

    public BasketFragment getBasketFragment() {
        return basketFragment;
    }

    // Для страниц когда вбиваешь в поиске слово
    public List<ProductCardFragment> getProductsList() {
//        return $$x("//div[contains(@class, 'catalog-item product-card__')]");
        return ProductCardFragment.toList($$x("//div[contains(@class, 'catalog-item product-card__')]"));
    }

    // Для страниц когда кликаешь на бренд и слева есть фильтр
    public List<ProductCardFragment> getFilteredProductsList() {
        return ProductCardFragment.toList($$("div.catalog__items .product-card__overview"));
    }

    public String getProductPrice(SelenideElement product) {
        return product.$x(".//div[@class='price-block']//span[@class='price-number']").getText();
    }

    public String getProductName(SelenideElement product) {
        return product.$x(".//h5").getText();
    }

    public ProductListPage addProductToCompare(SelenideElement product) {
        String selector = ".//div[@class='itm-footer-desc']//i[contains(@class,'icon-comparison2')]";
        product.$x(selector).click();
        // подождать пока станет зеленая иконка
        String selector2 = ".//div[@class='itm-footer-desc']//li[contains(@class,'compare-active')]";
        product.$x(selector2).hover();
        return this;
    }

    public FiltersFragment getFiltersFragment() {
        return filtersFragment;
    }
}
