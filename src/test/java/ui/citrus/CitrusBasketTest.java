package ui.citrus;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.citrus.*;
import ui.citrus.steps.CompareSteps;
import ui.citrus.steps.HomeSteps;
import ui.citrus.steps.ProductListSteps;
import ui.citrus.steps.ProductSteps;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static pageObject.utils.Price.extractPrice;

public class CitrusBasketTest {

    private HomeSteps homeSteps;

    String[] prices;
    String[] names;

    private final String productName = "Apple iPhone 11 64GB";

    @BeforeClass
    public void setup() {
        Configuration.startMaximized = true;
        Configuration.baseUrl = "https://www.citrus.ua";

    }

    @BeforeMethod
    public void cleanBasket() {
        open("/");
        clearBrowserLocalStorage();
        refresh();
        prices = new String[3];
        names = new String[3];
        homeSteps = new HomeSteps();
    }

    @Test
    public void basketViaProductPageTest() throws Exception {
        ProductSteps productSteps = homeSteps.clickMenu("Смартфоны", "Apple")
                .clickProduct(productName)
                .savePriceAndBuy();
        basketViaProductPageFinalCheck(productSteps);
    }

    @Step("Final checks with "+productName)
    private void basketViaProductPageFinalCheck(ProductSteps productSteps) {
        ProductPage productPage = productSteps.getPage();
        productPage.getBasketFragment().getBasketWidget().shouldBe(Condition.visible);
        productPage.getBasketFragment().getBasketProductList().shouldHaveSize(1); // TODO find correct selector
        productPage.getBasketFragment().getBasketProductList().get(0).shouldHave(Condition.text(productName));
        productPage.getBasketFragment().getBasketTotal().shouldHave(Condition.text(productSteps.getPrice()));
    }

    @Test
    public void basketViaProductListPageTest() {
        HomeSteps homeSteps = new HomeSteps();
        ProductListSteps productListSteps = homeSteps.searchFor(productName);
        String productPrice = productListSteps.getPage().readProductPrice(productName);
        productListSteps.getPage().addProductToBasket(productName);
        basketViaProductListPageFinalCheck(productListSteps, productPrice);
    }

    @Step("Final checks with "+productName+" and price {productPrice}")
    private void basketViaProductListPageFinalCheck(ProductListSteps productListSteps, String productPrice) {
        productListSteps.getPage().getBasketFragment().getBasketWidget().shouldBe(Condition.visible);
        productListSteps.getPage().getBasketFragment().getBasketTotal().shouldHave(Condition.text(productPrice));
    }


    @Test
    public void basketViaProductPage2Test() {
        ProductListSteps productListSteps = homeSteps.searchFor(productName);
        List<ProductCardFragment> products = productListSteps.getProductList();
        basketViaProductListPageSaveAndCompare(products, 2);
        basketViaProductPage2FinalCheck(productListSteps, 2);
    }

    @Step("Save product names/price")
    private void basketViaProductListPageSaveAndCompare(List<ProductCardFragment> products, int number) {
        prices = new String[number];
        names = new String[number];
        for (int i=0; i<number; i++) {
            ProductCardFragment product = products.get(i);
            prices[i] = product.getPrice();
            names[i] = product.getTitle();
            product.addToCompare();
        }

    }

    @Step("Final checks of {number} products")
    private void basketViaProductPage2FinalCheck(ProductListSteps productListSteps, int number) {
        ProductListPage productListPage = productListSteps.getPage();
        productListPage.getBasketFragment().open().getBasketProductList().shouldHaveSize(number);
        int total = 0;
        for (int i=0; i<number; i++) {
            productListPage.getBasketFragment().getBasketProductList().get(i).shouldHave(Condition.text(names[i]));
            productListPage.getBasketFragment().getBasketPriceList().get(i).shouldHave(new MatchPriceCondition(prices[i]));
            total += extractPrice(prices[i]);
        }
        productListPage.getBasketFragment().getBasketTotal().shouldHave(new MatchPriceCondition(total));
    }

    @Test
    public void basketViaCompareTest() {
        ProductListSteps productListSteps = homeSteps.searchFor(productName);
        List<ProductCardFragment> products = productListSteps.getProductList();
        basketViaProductListPageSaveAndCompare(products, 2);

        CompareSteps compareSteps = new CompareSteps();
        compareSteps.addToBasket(2);
        basketViaCompareFinalCheck(compareSteps, productListSteps);
    }

    @Step("Final checks")
    private void basketViaCompareFinalCheck(CompareSteps compareSteps, ProductListSteps productListSteps) {
        ComparePage comparePage = compareSteps.getPage();
        comparePage.getBasketFragment().open().getBasketProductList().get(0).shouldHave(Condition.text(names[0]));
        comparePage.getBasketFragment().getBasketProductList().get(1).shouldHave(Condition.text(names[1]));
        int total = extractPrice(prices[0]) + extractPrice(prices[1]);
        productListSteps.getPage().getBasketFragment().getBasketTotal().shouldHave(new MatchPriceCondition(total));
    }
}
