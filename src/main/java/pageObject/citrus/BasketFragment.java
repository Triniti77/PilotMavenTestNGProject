package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class BasketFragment {
    SelenideElement basketWidget;
    ElementsCollection productNamesInBasket;
    ElementsCollection productPricesInBasket;
    // //div[@class='ctrs-basket-product__code']/preceding-sibling::a
    SelenideElement basketTotalPrice;

    BasketFragment() {
        refresh();
    }

    protected void refresh() {
        basketWidget = Selenide.$("div.el-dialog");
        productNamesInBasket = Selenide.$$("div.el-dialog div.ctrs-basket-item__main-box div.ctrs-basket-product a.ctrs-basket-product__name");
        productPricesInBasket = Selenide.$$("div.el-dialog div.ctrs-basket-item__main-box div.ctrs-basket-item__total-price span.ctrs-main-price");
        basketTotalPrice = Selenide.$("div.el-dialog div.ctrs-basket-footer__price span.ctrs-main-price");
    }

    public SelenideElement getBasketWidget() {
        return basketWidget;
    }

    public ElementsCollection getBasketProductList() {
        return productNamesInBasket;
    }

    public ElementsCollection getBasketPriceList() {
        return productPricesInBasket;
    }

    public SelenideElement getBasketTotal() {
        return basketTotalPrice;
    }

    public BasketFragment open() {
        $x("//header[@id='header']//div[contains(@class,'user-actions__cart')]").click();
        refresh();
        return this;
    }
}
