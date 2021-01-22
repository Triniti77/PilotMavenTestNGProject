package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$$x;

public class ProductPage extends BasePage {

    BasketFragment basketFragment = new BasketFragment();

    SelenideElement productPrice = Selenide.$$x("//div[@class='price']/span/span").first();
    SelenideElement buyButton = Selenide.$$("div.normal button.btn.orange.full").first();

    public String readProductPrice() {
        return productPrice.getText();
    }

    public ProductPage clickBuyButton() {
        buyButton.click();
        return this;
    }

    public BasketFragment getBasketFragment() {
        return basketFragment;
    }
}
