package pageObject.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.List;

import static com.codeborne.selenide.Selenide.$x;

public class ComparePage extends BasePage {
    BasketFragment basketFragment = new BasketFragment();

    public ComparePage open() {
        $x("//header[@id='header']//div[contains(@class,'user-actions__compare')]").click();
        return this;
    }

    public List<ProductCompareCardFragment> getProductsList() {
        List<ProductCompareCardFragment> list = ProductCompareCardFragment.toList($$("div.catalog-item"));
        return list;
    }

    public ComparePage addProductToBasket(SelenideElement product) {
        String selector = ".//div[@class='itm-footer-desc']//i[contains(@class,'icon-new-citrus-cart')]";
        product.$x(selector).click();
        closeBasketList();
        return this;
    }

    public BasketFragment getBasketFragment() {
        return basketFragment;
    }
}
