package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.codeborne.selenide.Selenide.$;

public class FiltersFragment {
    private SelenideElement priceWidget;
    private SelenideElement filtersBody = $("div.filters__body");
    private SelenideElement memoryWidget;
    private SelenideElement bodyMaterialWidget;

    FiltersFragment() {
        refresh();
    }

    private void refresh() {
        priceWidget = filtersBody.$("div.filter--price div.roww");
        memoryWidget = filtersBody.$$("div.filter-itm").get(3);
        bodyMaterialWidget = filtersBody.$$("div.filter-itm").get(13);
    }


    public FiltersFragment setMinPrice(int i) {
        priceWidget.$$("input.el-input__inner").get(0).val(String.valueOf(i));
        filtersBody.click();
        waitForFilterApply();
        return this;
    }

    public FiltersFragment setMaxPrice(int i) {
        priceWidget.$$("input.el-input__inner").get(1).val(String.valueOf(i));
        filtersBody.click();
        waitForFilterApply();
        return this;
    }

    private void waitForFilterApply() {
        WebDriverWait wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 15);
        // подождать пока исчезнет белый слой
        wait.until(webDriver -> ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.querySelectorAll('main > section.preloader-ajax-wrap').length < 1"));
    }

    public FiltersFragment setMemorySize(String memorySize) {
        memoryWidget.scrollIntoView(true).$x(".//a[contains(text(), '"+memorySize+"')]").click();
        waitForFilterApply();
        return this;
    }

    public FiltersFragment setBodyMaterial(String material) {
        bodyMaterialWidget.scrollIntoView(true).$x(".//a[contains(text(), '"+material+"')]").click();
        waitForFilterApply();
        return this;
    }
}
