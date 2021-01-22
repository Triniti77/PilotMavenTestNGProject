package ui.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pageObject.citrus.*;

import static com.codeborne.selenide.Selenide.*;

public class CitrusFilterTest {
    public static final int MIN_PRICE = 5000;
    public static final int MAX_PRICE = 7000;

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
        homePage = new HomePage();
        productListPage = new ProductListPage();
    }

    @Test
    public void priceFilterTest() throws Exception {
        homePage.waitForPageToLoad()
                .hoverMenuLine("Смартфоны")
                .clickOnLinkInMenu("Samsung");
        productListPage.getFiltersFragment().setMinPrice(MIN_PRICE).setMaxPrice(MAX_PRICE);
        for (ProductCardFragment product : productListPage.getFilteredProductsList()) {
            product.getPriceElement().shouldBe(new MatchPriceBetweenCondition(MIN_PRICE, MAX_PRICE));
        }
    }

    @Test
    public void memoryFilterTest() throws Exception {
        homePage.waitForPageToLoad()
                .hoverMenuLine("Смартфоны")
                .clickOnLinkInMenu("Xiaomi");
        productListPage.getFiltersFragment().setMemorySize("64 Гб").setMemorySize("32 Гб");
        for (ProductCardFragment product : productListPage.getFilteredProductsList()) {
            product.getTitleElement().shouldBe(Condition.or("Memories", Condition.text("64Gb"), Condition.text("32Gb")));
        }
    }

    @Test
    public void bodyMaterialTest() throws Exception {
        homePage.waitForPageToLoad()
                .hoverMenuLine("Смартфоны")
                .clickOnLinkInMenu("OPPO");
        productListPage.getFiltersFragment().setBodyMaterial("Металл");
        for (ProductCardFragment product : productListPage.getFilteredProductsList()) {
            product.intoView().getMaterialElement().shouldBe(Condition.text("Металл"));
        }
    }
}