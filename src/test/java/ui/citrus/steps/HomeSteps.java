package ui.citrus.steps;

import io.qameta.allure.Step;
import pageObject.citrus.HomePage;
import pageObject.citrus.ProductListPage;

public class HomeSteps {
    private final HomePage homePage;

    public HomeSteps() {
        homePage = new HomePage();
    }

    @Step("Open menu {menuName} -> {submenuName}")
    public ProductListSteps clickMenu(String menuName, String submenuName) throws Exception {
        homePage.waitForPageToLoad()
                .hoverMenuLine(menuName)
                .clickOnLinkInMenu(submenuName);
        return new ProductListSteps();
    }

    public ProductListSteps searchFor(String productName) {
        homePage.waitForPageToLoad()
                .getSearchFragment()
                .searchProduct(productName);
        return new ProductListSteps();
    }
}
