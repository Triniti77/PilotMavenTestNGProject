package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

public class BaseUITest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void createBrowser() {
        //        System.setProperty("webdriver.gecko.driver", "C:\\Users\\anvla\\.m2\\repository\\webdriver\\geckodriver\\win64\\0.26.0\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver","/usr/bin/chromedriver");
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("start-maximized");
        options.addArguments("--aggressive-cache-discard");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }

    protected void goPageBack() {
        driver.navigate().back();
    }
}
