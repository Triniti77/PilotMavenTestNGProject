package ui.citrus;

import com.codeborne.selenide.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.citrus.*;

import static com.codeborne.selenide.Selenide.*;

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
    public void basketViaProductPageTest() {
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
        ElementsCollection products = productListPage.waitForPageToLoad()
            .getProductsList();
        SelenideElement product0 = products.get(0);
        String price0 = productListPage.getProductPrice(product0);
        String name0 = productListPage.getProductName(product0);
//        String productListUrl = WebDriverRunner.url();

        SelenideElement product1 = products.get(1);
        String price1 = productListPage.getProductPrice(product1);
        String name1 = productListPage.getProductName(product1);

        productListPage.addProductToBasket(product0);
        productListPage.addProductToBasket(product1);

        productListPage.getBasketFragment().open().getBasketProductList().shouldHaveSize(2);
        productListPage.getBasketFragment().getBasketProductList().get(0).shouldHave(Condition.text(name0));
        productListPage.getBasketFragment().getBasketProductList().get(1).shouldHave(Condition.text(name1));
//        productListPage.getBasketFragment().getBasketPriceList().get(0).shouldHave(Condition.text(price0));
//        productListPage.getBasketFragment().getBasketPriceList().get(1).shouldHave(Condition.text(price1));

        double total = BasePage.extractPrice(price0) + BasePage.extractPrice(price1);
        productListPage.getBasketFragment().getBasketTotal().shouldHave(Condition.text(String.valueOf((int)total)));
    }

    @Test
    public void basketViaCompareTest() {
        homePage.waitForPageToLoad()
//                .closePopup()
                .getSearchFragment()
                .searchProduct(productName);
        ElementsCollection products = productListPage.waitForPageToLoad()
                .getProductsList();

        SelenideElement product0 = products.get(0);
        String price0 = productListPage.getProductPrice(product0);
        String name0 = productListPage.getProductName(product0);
//        String productListUrl = WebDriverRunner.url();

        SelenideElement product1 = products.get(1);
        String price1 = productListPage.getProductPrice(product1);
        String name1 = productListPage.getProductName(product1);

        productListPage.addProductToCompare(product0);
        productListPage.addProductToCompare(product1);

        products = comparePage.open().getProductsList();
        comparePage.addProductToBasket(products.get(0));
        comparePage.addProductToBasket(products.get(2));

        comparePage.getBasketFragment().open().getBasketProductList().get(0).shouldHave(Condition.text(name0));
        comparePage.getBasketFragment().getBasketProductList().get(1).shouldHave(Condition.text(name1));

        double total = BasePage.extractPrice(price0) + BasePage.extractPrice(price1);
        productListPage.getBasketFragment().getBasketTotal().shouldHave(Condition.text(BasePage.priceToString(total)));
    }
}
