package ui.rozetka;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ui.BaseUITest;
import ui.rozetka.po.RozetkaBasketIconElement;
import ui.rozetka.po.RozetkaItemPage;
import ui.rozetka.po.RozetkaMainPage;
import ui.rozetka.po.RozetkaSearchResultPage;

import static org.testng.Assert.assertEquals;

public class RozetkaBasketTest extends RozetkaTestBase {
    String searchText = "samsung";
    RozetkaMainPage mainPage;
    RozetkaSearchResultPage searchResultPage;
    RozetkaItemPage itemPage;
    RozetkaBasketIconElement basketElement;

    @BeforeClass
    public void initTestClass() {
        mainPage = new RozetkaMainPage(driver, wait);
        searchResultPage = new RozetkaSearchResultPage(driver, wait);
        itemPage = new RozetkaItemPage(driver, wait);
        basketElement = new RozetkaBasketIconElement(driver, wait);
    }

    @Test
    public void basketIconTest() throws Exception {
        mainPage.searchByKeyword(searchText);
        searchResultPage.enterCatalogItem(0);

        assertEquals(basketElement.itemsInBasket(), 0, "Items in basket should be 0");

        itemPage.putCurrentItemToBasket();
        itemPage.orderCurrentItem();

        goPageBack();

        assertEquals(basketElement.itemsInBasket(), 1, "Items in basket should be 1");
    }
}