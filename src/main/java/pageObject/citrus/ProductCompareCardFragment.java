package pageObject.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.LinkedList;
import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

public class ProductCompareCardFragment {
    private final SelenideElement element;

    ProductCompareCardFragment(SelenideElement el) {
        element = el;
    }

    static List<ProductCompareCardFragment> toList(ElementsCollection col) {
        col = col.exclude(Condition.not(Condition.visible));
        LinkedList<ProductCompareCardFragment> list = new LinkedList<>();
        for (SelenideElement item : col) {
            list.add(new ProductCompareCardFragment(item));
        }
        return list;
    }

    public String getPrice() {
        return getPriceElement().getText();
    }

    public String getTitle() {
        return getTitleElement().attr("title");
    }

    public SelenideElement getTitleElement() {
        return element.$x(".//a[@class='card-product-link']");
    }

    public SelenideElement getPriceElement() {
        return element.$x(".//div[@class='price-itm']//span[@class='price-number']");
    }

    public ProductCompareCardFragment addToBasket() {
        String selector = ".//div[@class='itm-footer-desc']//i[contains(@class,'icon-new-citrus-cart')]";
        element.$x(selector).click();
        // Закрыть окошко с товарами обязательно
        BasePage.closeBasketList();
        return this;
    }
}
