package ui.google;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ui.BaseUITest;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class googleMailTest extends BaseUITest {
    private final String password = "Zwe7ySsv";
    private final String username = "65788907GT";

    private String randomValue;

    @Test
    public void testAccount() {
        driver.get("https://mail.google.com");

        // login form - email input
        By emailInputLocator = By.xpath("//body/div[1]//div[@role='presentation']//form//input[@type='email']");
        wait.until(presenceOfElementLocated(emailInputLocator));

        WebElement emailInput = driver.findElement(emailInputLocator);
        emailInput.sendKeys(username + Keys.ENTER);

        By emailPasswordLocator = By.xpath("//body/div[1]//div[@role='presentation']//form//input[@type='email']");
        wait.until(presenceOfElementLocated(emailPasswordLocator));

        WebElement emailPassword = driver.findElement(emailPasswordLocator);
        emailPassword.sendKeys(password + Keys.ENTER);

        // wait emails list load
        By emailListTableLocator = By.cssSelector("div[role=main] table[role=grid]");
        wait.until(presenceOfElementLocated(emailListTableLocator));

        // click compose
        By composeButtonLocator = By.cssSelector("div.aic");
        driver.findElement(composeButtonLocator).click();

        By composeDialogSelector = By.cssSelector("*[role=dialog]");
        By emailToSelector = By.cssSelector("form>div:nth-child(1)");
        By emailSubjectSelector = By.cssSelector("form>div:nth-child(2)");
        By emailContentSelector = By.cssSelector("form + table div[role=textbox]");
        By emailToInputSelector = By.cssSelector("form>div:nth-child(1) textarea[role=combobox]");
        String email = username + "@gmail.com";

        WebElement dialog = driver.findElement(composeDialogSelector);
        dialog.findElement(emailToSelector).click();
        dialog.findElement(emailToInputSelector).sendKeys(email + Keys.TAB + "");

    }

    protected String randomString(int length) {
        String result = "";
        for (int i=0; i<length; i++) {
            int randomInt = (int)(Math.random()*100);
//            result += String.valueOf()
        }
        return result;
    }
}
