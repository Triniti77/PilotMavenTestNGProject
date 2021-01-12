package ui.rozetka;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.rozetka.po.RozetkaCatalogPage;
import ui.rozetka.po.RozetkaMainPage;
import ui.rozetka.po.RozetkaSearchResultPage;

import java.util.List;

import static org.testng.Assert.assertTrue;

public class RozetkaFilterTest extends RozetkaTestBase {
    String searchText = "samsung";
    static private final int lowPrice = 5000;
    static private final int highPrice = 15000;

    private RozetkaMainPage mainPage;

    @BeforeMethod
    public void initObjects() {
        mainPage = new RozetkaMainPage(driver, wait);
    }

    @AfterMethod
    public void cleaning() {
        mainPage = null;
    }

    @Test
    public void testBrandsFilter() throws Exception {
        mainPage.searchByKeyword(searchText);
        RozetkaSearchResultPage searchResultPage = new RozetkaSearchResultPage(driver, wait);
        searchResultPage.goCategory("Мобильные телефоны");

        RozetkaCatalogPage catalogPage = new RozetkaCatalogPage(driver, wait);
        catalogPage.addFilterByProducer("Apple");
        catalogPage.addFilterByProducer("Huawei");

        List<RozetkaCatalogPage.RozetkaCatalogItemElement> goods = catalogPage.getAllItems();

        for (RozetkaCatalogPage.RozetkaCatalogItemElement item : goods) {
            String title = item.getTitle();
            assertTrue(title.contains("Apple") || title.contains("Samsung") || title.contains("Huawei"), "Check the phone one of Apple, Samsung, Huawei");
        }
    }

    @Test
    public void testPriceFilter() throws Exception {
        mainPage.searchByKeyword(searchText);
        RozetkaSearchResultPage searchResultPage = new RozetkaSearchResultPage(driver, wait);
        searchResultPage.goCategory("Мобильные телефоны");

        RozetkaCatalogPage catalogPage = new RozetkaCatalogPage(driver, wait);
        catalogPage.addFilterByPriceLow(lowPrice);
        catalogPage.addFilterByPriceHigh(highPrice);
        catalogPage.applyFilterByPrice();

        List<RozetkaCatalogPage.RozetkaCatalogItemElement> goods = catalogPage.getAllItems();

        for (RozetkaCatalogPage.RozetkaCatalogItemElement item : goods) {
            double productPrice = item.getPrice();
            assertTrue(productPrice >= (double)lowPrice && productPrice <= (double)highPrice, "Product price "+lowPrice+" < price < "+highPrice);
        }
    }

    @Test
    public void testCustomFilter() throws Exception {
        mainPage.searchByKeyword(searchText);
        RozetkaSearchResultPage searchResultPage = new RozetkaSearchResultPage(driver, wait);
        searchResultPage.goCategory("Мобильные телефоны");

        RozetkaCatalogPage catalogPage = new RozetkaCatalogPage(driver, wait);
        catalogPage.addFilterByRam("4 ГБ");
        catalogPage.addFilterByStorage("128 ГБ");

        List<RozetkaCatalogPage.RozetkaCatalogItemElement> goods = catalogPage.getAllItems();

        for (RozetkaCatalogPage.RozetkaCatalogItemElement item : goods) {
            String title = item.getTitle();
            assertTrue(title.contains("4/128GB"), "Check the phone has 4/128GB memory");
        }
    }
}
