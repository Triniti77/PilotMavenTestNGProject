package pageObject.citrus;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class SearchFragment {
    SelenideElement searchInput = Selenide.$("#search-input");
    SelenideElement searchButtonSubmit = Selenide.$(".global-search__submit");

    public SearchFragment searchProduct(String productName) {
        searchInput.val(productName);
        searchButtonSubmit.click();
        return this;
    }
}
