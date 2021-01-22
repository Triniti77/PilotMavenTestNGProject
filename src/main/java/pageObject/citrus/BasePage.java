package pageObject.citrus;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.text.DecimalFormat;
import java.time.Duration;
import java.util.NoSuchElementException;

import static com.codeborne.selenide.Selenide.$;

public class BasePage {
    private final WebDriverWait wait;
    SelenideElement closePopupButton = Selenide.$("i.el-icon-close");
    boolean popupClosed = false;
    static private Robot robot = null;

    BasePage() {
        wait = new WebDriverWait(WebDriverRunner.getWebDriver(), 15);
    }

    public static void closeBasketList() {
        Selenide.$x("//div[contains(@class,'el-dialog__wrapper')]//button[@aria-label='Close'][@class='el-dialog__headerbtn']").click();
    }

    public BasePage waitForPageToLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("return document.readyState").toString().equals("complete"));
        closePopup();
        return this;
    }

    public BasePage waitForProductListToLoad() {
        //wait.until(webDriver -> !Selenide.$x("//main[contains(@class,'fade-'").exists());
        waitFor(Selenide.$x("//main[contains(@class,'fade-')]"), 6);
        return this;
    }

    static private Robot getRobot() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
        return robot;
    }

    public BasePage closePopup() {
//        randomlyMoveCursor();
        // add waiter 2 seconds
        if (popupClosed) {
            return this;
        }
        if (waitFor(closePopupButton, 1)) {
            closePopupButton.click();
            popupClosed = true;
        }
        return this;
    }

    private void randomlyMoveCursor() {
        Robot rob = getRobot();
        WebDriver driver = WebDriverRunner.getWebDriver();
        Dimension position = driver.manage().window().getSize();
        rob.mouseMove(position.getWidth()/2 + (int)Math.round(Math.random()*500-250), position.getHeight()/2+(int)Math.round(Math.random()*500-250));
    }

    protected boolean waitFor(SelenideElement element, int seconds) {
        FluentWait<WebDriver> wait = new FluentWait<WebDriver>(WebDriverRunner.getWebDriver())
                .withTimeout(Duration.ofSeconds(seconds))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);
        try {
            wait.until(webDriver -> element.isDisplayed());
        } catch(TimeoutException e) {
            return false;
        }
        return true;
    }

    public static String getUrl() {
        return WebDriverRunner.url();
    }

    SelenideElement $x(String xpathSelector) {
        closePopup();
        return Selenide.$x(xpathSelector);
    }

    SelenideElement $(String cssSelector) {
        closePopup();
        return Selenide.$(cssSelector);
    }

    ElementsCollection $$x(String xpathSelector) {
        closePopup();
        return Selenide.$$x(xpathSelector);
    }

    ElementsCollection $$(String cssSelector) {
        closePopup();
        return Selenide.$$(cssSelector);
    }
}
