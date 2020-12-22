package ui;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseUITest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeClass
    public void createBrowser() {
        //        System.setProperty("webdriver.gecko.driver", "C:\\Users\\anvla\\.m2\\repository\\webdriver\\geckodriver\\win64\\0.26.0\\geckodriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
    }

}
