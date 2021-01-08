package ui.rozetka.po;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RozetkaPageBase {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public RozetkaPageBase(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public double priceToDouble(String priceText) {
        String priceTextClean = priceText.replaceAll("\\D+", "");
        return Double.parseDouble(priceTextClean);
    }

    public void makePause(int msec) {
        try {
            Thread.sleep(msec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
