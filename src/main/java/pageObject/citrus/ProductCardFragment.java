package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.util.LinkedList;
import java.util.List;

public class ProductCardFragment {
    SelenideElement element;

    ProductCardFragment(SelenideElement selenideElement) {
        element = selenideElement;
    }

    public SelenideElement getPriceElement() {
        return element.$x(".//div[@class='product-card__prices']//div[@class='prices__price']/span[@class='price']");
    }

    static List<ProductCardFragment> toList(ElementsCollection col) {
        LinkedList<ProductCardFragment> list = new LinkedList<>();
        for (SelenideElement item : col) {
            list.add(new ProductCardFragment(item));
        }
        return list;
    }

    public SelenideElement getTitleElement() {
        return element.$x(".//div[@class='product-card__name']/a");
    }

    public SelenideElement getMaterialElement() {
        return element.hover().$x(".//div[@class='properties__body']/ul/li/span[contains(text(),'Материалы корпуса')]/following-sibling::span[@class='item__value']");
    }

    public ProductCardFragment intoView() {
        element.scrollIntoView(true);
        return this;
    }

    public String getPrice() {
        return getPriceElement().getText();
    }

    public String getTitle() {
        return getTitleElement().attr("title");
    }

    public ProductCardFragment addToCompare() {
//        String selector = ".//div[@class='itm-footer-desc']//i[contains(@class,'icon-comparison2')]";
        element.hover();
        String selector = ".//button[@class='product-card__to-compare']/span";
        element.$x(selector).click();
        // подождать пока станет зеленая иконка
        String selector2 = ".//button[contains(@class,'product-card__to-compare')][contains(@class,'compare-active')]";
//        String selector2 = ".//button[@class='product-card__to-compare']//li[contains(@class,'compare-active')]";
        element.$x(selector2).hover();
        return this;
    }
}
