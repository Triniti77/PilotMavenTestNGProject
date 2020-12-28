package ui.guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import ui.BaseUITest;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class Guru99Base extends BaseUITest {
    private final String login = "1303";
    private final String password = "Guru99";
    private final String guruUrl = "http://demo.guru99.com/Agile_Project/Agi_V1/index.php";

    @BeforeMethod
    public void startUp() {
        driver.get(guruUrl);
    }

    protected void doLogin(String login, String password) {
        driver.findElement(By.name("uid")).sendKeys(login);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.name("btnLogin")).click();
    }

    protected void doLogin() {
        doLogin(login, password);
    }

    protected String getLogin() {
        return login;
    }

    protected String getPassword() {
        return password;
    }

    protected WebElement getLogoutButton() {
        return wait.until(presenceOfElementLocated(By.xpath("//a[@href='Logout.php']")));
    }

    protected WebElement getStatementButton() {
        return wait.until(presenceOfElementLocated(By.xpath("//a[@href='MiniStatementInput.php']")));
    }

    protected String getUrl() {
        return guruUrl;
    }
}
