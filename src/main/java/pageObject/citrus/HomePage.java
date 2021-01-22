package pageObject.citrus;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {

    SearchFragment searchFragment = new SearchFragment();
    private boolean menuHovered = false;
    private String menuLink;
    private SelenideElement menu;

    public HomePage hoverMenuLine(String menuLineText) {
        SelenideElement menuTitle = $x("//div[contains(@class,'menu--desktop')]//div[contains(@class, 'show')]//span[contains(text(), '"+menuLineText+"')]");
        String baseUrl = Configuration.baseUrl;
        menuLink = menuTitle.parent().attr("href").substring(baseUrl.length()); // ссылка возвращается с https:// а на странице без этого, поэтому надо вырезать начало
        menu = menuTitle.parent().parent();
        menuTitle.hover();
        menuHovered = true;
        return this;
    }

    public HomePage clickOnLinkInMenu(String linkText) throws Exception {
        if (!menuHovered) {
            throw new Exception("Hover menu first");
        }
        String selector = ".//a[contains(@href, 'brand-')]/span[contains(text(), '"+linkText+"')]";
        System.out.println(selector);
        menu.$x(selector).click();
        return this;
    }

    public HomePage waitForPageToLoad() {
        super.waitForPageToLoad();
        return this;
    }

    public HomePage closePopup() {
        super.closePopup();
        return this;
    }

    public SearchFragment getSearchFragment() {
        return searchFragment;
    }
}
