package ui.rozetka.po;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RozetkaMainMenuElement extends RozetkaPageBase {
    private static Map<String, String> menuItems = Map.ofEntries(
            Map.entry("Мониторы", "monitors")
    );

    public RozetkaMainMenuElement(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void goMenuItem(String itemCategory, String itemKey) throws Exception {
        String href = menuItems.get(itemKey);
        if (href == null) {
            throw new Exception("Unknown item key " + itemKey);
        }
        openMainMenu();
        if (itemCategory != null) {
            selectMenuCategory(itemCategory);
        }

        List<WebElement> items = driver.findElements(By.xpath("//*[@class='menu__main-cats-inner']//*[@class='menu__link'][contains(@href,'/"+href+"/')]"));
        if (items.size() < 1) {
            throw new Exception("Cannot find " + itemKey + " menu item");
        }
        items.get(0).click();
    }

    private void selectMenuCategory(String itemCategory) {
    }

    public void openMainMenu() {
        driver.findElement(By.cssSelector(".menu-toggler")).click();
    }
}
