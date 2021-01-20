package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class FiltersFragment {
    SelenideElement priceWidget;
    SelenideElement filtersBody = $("div.filters__body");

    FiltersFragment() {
        refresh();
    }

    private void refresh() {
        priceWidget = filtersBody.$("div.filter--price div.roww");
    }


    public FiltersFragment setMinPrice(int i) {
        priceWidget.$$("input.el-input__inner").get(0).val(String.valueOf(i));
        return this;
    }

    public FiltersFragment setMaxPrice(int i) {
        priceWidget.$$("input.el-input__inner").get(1).val(String.valueOf(i));
        return this;
    }
}
