package pageObject.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class ComparePage extends BasePage {
    BasketFragment basketFragment = new BasketFragment();

    public ComparePage open() {
        $x("//header[@id='header']//div[contains(@class,'user-actions__compare')]").click();
        return this;
    }

    public ElementsCollection getProductsList() {
        ElementsCollection col = $$("div.catalog-item");
        col = col.exclude(Condition.not(Condition.visible));
        return col;
    }

    public ComparePage addProductToBasket(SelenideElement product) {
        String selector = ".//div[@class='itm-footer-desc']//i[contains(@class,'icon-new-citrus-cart')]";
        product.$x(selector).click();
        $x("//div[contains(@class,'el-dialog__wrapper')]//button[@aria-label='Close'][@class='el-dialog__headerbtn']").click();
        return this;
    }

    public BasketFragment getBasketFragment() {
        return basketFragment;
    }
}
