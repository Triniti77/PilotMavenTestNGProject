package ui.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.jsoup.Connection;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.citrus.*;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class CitrusComparisonTest {
    private HomePage homePage;
    private ProductListPage productListPage;

    String productName = "Apple iPhone 11";
    private ComparePage comparePage;

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
        comparePage = new ComparePage();
    }

    @Test
    public void threeItemsInComparisonTest() throws Exception {
        homePage.waitForPageToLoad()
                .hoverMenuLine("Ноутбуки")
                .clickOnLinkInMenu("Acer");

        List<ProductCardFragment> products = productListPage.waitForProductListToLoad()
                .getFilteredProductsList();

        ProductCardFragment product0 = products.get(0);
        String price0 = product0.getPrice();
        String name0 = product0.getTitle();

        ProductCardFragment product1 = products.get(1);
        String price1 = product1.getPrice();
        String name1 = product1.getTitle();

        product0.addToCompare();
        product1.addToCompare();

        String productListUrl = BasePage.getUrl();

        List<ProductCompareCardFragment> cproducts = comparePage.open().getProductsList();
        cproducts.get(0).getTitleElement().shouldBe(Condition.or("Should be one of the titles", new MatchTitleCondition(name0), new MatchTitleCondition(name1)));
        cproducts.get(0).getPriceElement().shouldBe(Condition.or("Should be one of the prices", new MatchPriceCondition(price0), new MatchPriceCondition(price1)));
        cproducts.get(2).getTitleElement().shouldBe(Condition.or("Should be one of the titles", new MatchTitleCondition(name0), new MatchTitleCondition(name1)));
        cproducts.get(2).getPriceElement().shouldBe(Condition.or("Should be one of the prices", new MatchPriceCondition(price0), new MatchPriceCondition(price1)));

        open(productListUrl);

        products = productListPage.waitForProductListToLoad()
                .getFilteredProductsList();
        products.get(2).addToCompare();
        cproducts = comparePage.open().getProductsList();

        ProductCardFragment product2 = products.get(2);
        String price2 = product2.getPrice();
        String name2 = product2.getTitle();

        cproducts.get(0).getTitleElement().shouldBe(Condition.or("Should be one of the titles",
                new MatchTitleCondition(name0), new MatchTitleCondition(name1), new MatchTitleCondition(name2)));
        cproducts.get(0).getPriceElement().shouldBe(Condition.or("Should be one of the prices",
                new MatchPriceCondition(price0), new MatchPriceCondition(price1), new MatchPriceCondition(price2)));
        cproducts.get(2).getTitleElement().shouldBe(Condition.or("Should be one of the titles",
                new MatchPriceCondition(price0), new MatchPriceCondition(price1), new MatchPriceCondition(price2)));
        cproducts.get(2).getPriceElement().shouldBe(Condition.or("Should be one of the prices",
                new MatchPriceCondition(price0), new MatchPriceCondition(price1), new MatchPriceCondition(price2)));
        cproducts.get(4).getTitleElement().shouldBe(Condition.or("Should be one of the titles",
                new MatchPriceCondition(price0), new MatchPriceCondition(price1), new MatchPriceCondition(price2)));
        cproducts.get(4).getPriceElement().shouldBe(Condition.or("Should be one of the prices",
                new MatchPriceCondition(price0), new MatchPriceCondition(price1), new MatchPriceCondition(price2)));
    }
}
