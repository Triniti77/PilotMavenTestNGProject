package ui.citrus.steps;

import io.qameta.allure.Step;
import pageObject.citrus.ProductCardFragment;
import pageObject.citrus.ProductListPage;
import ui.citrus.CitrusFilterTest;

import java.util.List;

public class ProductListSteps {

    private final ProductListPage productListPage;

    public ProductListSteps() {
        productListPage = new ProductListPage();
    }

    @Step("Click on {productName} on product list")
    public ProductSteps clickProduct(String productName) {
        productListPage.waitForPageToLoad()
                .clickOnProductByName(productName);
        return new ProductSteps();
    }

    public ProductListPage getPage() {
        return productListPage;
    }

    @Step("Fetch product list")
    public List<ProductCardFragment> getProductList() {
        return productListPage.waitForPageToLoad()
                .getProductsList();
    }
}
