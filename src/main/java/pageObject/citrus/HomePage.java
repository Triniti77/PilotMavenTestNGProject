package pageObject.citrus;

import static com.codeborne.selenide.Selenide.*;

public class HomePage extends BasePage {

    SearchFragment searchFragment = new SearchFragment();

    public HomePage hoverMenuLine(String menuLineText) {
        $x("//div[contains(@class, 'show')]//span[contains(text(), '"+menuLineText+"')]").hover();
        return this;
    }

    public HomePage clickOnLinkInMenu(String linkText) {
        String selector = "//a[contains(@href, '/smartfony/brand-apple/')]/span[contains(text(), '"+linkText+"')]";
        System.out.println(selector);
        $$x(selector).get(0).click();
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
