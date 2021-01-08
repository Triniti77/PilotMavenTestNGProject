package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RozetkaMainPage extends RozetkaPageBase {
    @FindBy(name = "search")
    WebElement searchField;

    public RozetkaMainPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }


    public void searchByKeyword(String searchText) {
        searchField.sendKeys(searchText + Keys.ENTER);
    }
}
