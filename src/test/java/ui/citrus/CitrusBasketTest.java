package ui.citrus;

import com.codeborne.selenide.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.citrus.*;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;
import static pageObject.utils.Price.extractPrice;

public class CitrusBasketTest {
    HomePage homePage;
    ProductPage productPage;
    ProductListPage productListPage;
    ComparePage comparePage;

    String productName = "Apple iPhone 12 64GB";

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
        homePage = new HomePage();
        productListPage = new ProductListPage();
        productPage = new ProductPage();
        comparePage = new ComparePage();
    }

    @Test
    public void basketViaProductPageTest() throws Exception {
        homePage.waitForPageToLoad()
                .hoverMenuLine("Смартфоны")
                .clickOnLinkInMenu("Apple");

        productListPage.waitForPageToLoad()
                .clickOnProductByName(productName);
        String productPrice = productPage.readProductPrice();
        productPage.clickBuyButton();

        productPage.getBasketFragment().getBasketWidget().shouldBe(Condition.visible);
        productPage.getBasketFragment().getBasketProductList().shouldHaveSize(1); // TODO find correct selector
        productPage.getBasketFragment().getBasketProductList().get(0).shouldHave(Condition.text(productName));
        productPage.getBasketFragment().getBasketTotal().shouldHave(Condition.text(productPrice));
    }

    @Test
    public void basketViaProductListPageTest() {
        homePage.waitForPageToLoad()
//                .closePopup()
                .getSearchFragment()
                .searchProduct(productName);
        productListPage.waitForPageToLoad()
                .closePopup();

        String productPrice = productListPage.readProductPrice(productName);
        productListPage.addProductToBasket(productName);

        productListPage.getBasketFragment().getBasketWidget().shouldBe(Condition.visible);
        productListPage.getBasketFragment().getBasketTotal().shouldHave(Condition.text(productPrice));
    }

    @Test
    public void basketViaProductPage2Test() {
        homePage.waitForPageToLoad()
//                .closePopup()
                .getSearchFragment()
                .searchProduct(productName);

        List<ProductCardFragment> products = productListPage.waitForPageToLoad()
                .getProductsList();

        ProductCardFragment product0 = products.get(0);
        String price0 = product0.getPrice();
        String name0 = product0.getTitle();

        ProductCardFragment product1 = products.get(1);
        String price1 = product1.getPrice();
        String name1 = product1.getTitle();

        product0.addToCompare();
        product1.addToCompare();

        productListPage.getBasketFragment().open().getBasketProductList().shouldHaveSize(2);
        productListPage.getBasketFragment().getBasketProductList().get(0).shouldHave(Condition.text(name0));
        productListPage.getBasketFragment().getBasketProductList().get(1).shouldHave(Condition.text(name1));
        productListPage.getBasketFragment().getBasketPriceList().get(0).shouldHave(new MatchPriceCondition(price0));
        productListPage.getBasketFragment().getBasketPriceList().get(1).shouldHave(new MatchPriceCondition(price1));

        int total = extractPrice(price0) + extractPrice(price1);
        productListPage.getBasketFragment().getBasketTotal().shouldHave(new MatchPriceCondition(total));
    }

    @Test
    public void basketViaCompareTest() {
        homePage.waitForPageToLoad()
//                .closePopup()
                .getSearchFragment()
                .searchProduct(productName);
        List<ProductCardFragment> products = productListPage.waitForPageToLoad()
                .getProductsList();

        ProductCardFragment product0 = products.get(0);
        String price0 = product0.getPrice();
        String name0 = product0.getTitle();

        ProductCardFragment product1 = products.get(1);
        String price1 = product1.getPrice();
        String name1 = product1.getTitle();

        product0.addToCompare();
        product1.addToCompare();

        List<ProductCompareCardFragment> cproducts = comparePage.open().getProductsList();
        cproducts.get(0).addToBasket();
        cproducts.get(0).addToBasket();
//        comparePage.addProductToBasket(products.get(0));
//        comparePage.addProductToBasket(products.get(2));

        comparePage.getBasketFragment().open().getBasketProductList().get(0).shouldHave(Condition.text(name0));
        comparePage.getBasketFragment().getBasketProductList().get(1).shouldHave(Condition.text(name1));

        int total = extractPrice(price0) + extractPrice(price1);
        productListPage.getBasketFragment().getBasketTotal().shouldHave(new MatchPriceCondition(total));
    }
}
