package ui.citrus;

import com.codeborne.selenide.Configuration;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.citrus.HomePage;
import pageObject.citrus.ProductListPage;

import static com.codeborne.selenide.Selenide.*;

public class CitrusFilterTest {
    HomePage homePage;
    ProductListPage productListPage;

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
    }

    @Test
    public void priceFilterTest() {
        homePage.waitForPageToLoad()
                .hoverMenuLine("Смартфоны")
                .clickOnLinkInMenu("Samsung");
        productListPage.getFiltersFragment().setMinPrice(5000).setMaxPrice(7000).applyPriceFilter();
    }
}