package ui.citrus.steps;

import io.qameta.allure.Step;
import pageObject.citrus.ProductPage;

public class ProductSteps {
    String productPrice;
    private ProductPage productPage;

    public ProductSteps() {
        productPage = new ProductPage();
    }

    @Step("Click product buy")
    public ProductSteps clickBuy() {
        productPage.clickBuyButton();
        return this;
    }

    @Step("Save product price and click buy")
    public ProductSteps savePriceAndBuy() {
        getPrice();
        productPage.clickBuyButton();
        return new ProductSteps();
    }

    public String getPrice() {
        if (productPrice == null) {
            productPrice = productPage.readProductPrice();
        }
        return productPrice;
    }

    public ProductPage getPage() {
        return productPage;
    }
}
