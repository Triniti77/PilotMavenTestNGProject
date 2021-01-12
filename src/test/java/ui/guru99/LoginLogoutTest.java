package ui.guru99;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.Assert.assertEqualsNoOrder;
import static org.testng.AssertJUnit.*;

public class LoginLogoutTest extends Guru99Base {
    private final String wrongLogin = "wrongLogin";
    private final String wrongPassword = "wrongPassword";
    private final String positiveAuthorizationAlertText = "You Have Successfully Logged Out!!";
    private final String negativeAuthorizationAlertText = "User or Password is not valid";

    @Test
    public void positiveLoginLogoutTest() {
        doLogin();
        WebElement logoutButton = getLogoutButton();
        doLogout(logoutButton);
    }

    protected void doLogout(WebElement logoutButton) {
        logoutButton.click();
        assertEquals(positiveAuthorizationAlertText, driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();
        assertEquals(getUrl(), driver.getCurrentUrl());
        wait.until(presenceOfElementLocated(By.name("btnLogin")));
    }

    @Test
    public void negativeLoginWrongPasswordTest() {
        doLogin(getLogin(), wrongPassword);
        assertEquals(negativeAuthorizationAlertText, driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();
        assertEquals(getUrl(), driver.getCurrentUrl());
        wait.until(presenceOfElementLocated(By.name("btnLogin")));
    }

    @Test
    public void negativeLoginWrongLoginTest() {
        doLogin(wrongLogin, getPassword());
        assertEquals(negativeAuthorizationAlertText, driver.switchTo().alert().getText());
        driver.switchTo().alert().accept();
        assertEquals(getUrl(), driver.getCurrentUrl());
        wait.until(presenceOfElementLocated(By.name("btnLogin")));
    }

    @Test
    public void positiveFormResetClearsFields() {
        driver.findElement(By.name("uid")).sendKeys(wrongLogin);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(wrongPassword);
        driver.findElement(By.name("btnReset")).click();
        assertEquals(getUrl(), driver.getCurrentUrl());
        assertEquals("", driver.findElement(By.xpath("//input[@name='uid']")).getText());
        assertEquals("", driver.findElement(By.xpath("//input[@name='password']")).getText());
    }

    @Test
    public void positiveStatementContainsUsers() {
        doLogin();
        WebElement statementButton = getStatementButton();
        statementButton.click();
        WebElement selectElement = wait.until(presenceOfElementLocated(By.name("accountno")));
        List<WebElement> users = selectElement.findElements(By.tagName("option"));
        List<String> accounts = new ArrayList<String>();
        for (WebElement user : users) {
            accounts.add(user.getText());
        }
        String[] expected = {"Select Account", "3308", "3309"};
        assertEqualsNoOrder(expected, accounts.toArray(), "All users are present");
    }

    @Test
    public void positiveUser3308HasStatements() {
        goStatementPage();
        WebElement u = findUserStatements("3308");
        assertNotNull("User 3308 is present", u);
        u.click();
        driver.findElement(By.name("AccSubmit")).click();
        WebElement tr = wait.until(presenceOfElementLocated(By.xpath("//table[@class='layout1']//table/tbody/tr[2]")));
        assertNotNull("Has at least 1 transaction statement", tr);
    }

    @Test
    public void positiveUser3309HasStatements() {
        goStatementPage();
        WebElement u = findUserStatements("3309");
        assertNotNull("User 3309 is present", u);
        u.click();
        driver.findElement(By.name("AccSubmit")).click();
        WebElement tr = wait.until(presenceOfElementLocated(By.xpath("//table[@class='layout1']//table/tbody/tr[2]")));
        assertNotNull("Has at least 1 transaction statement", tr);
    }

    public void goStatementPage() {
        doLogin();
        WebElement statementButton = getStatementButton();
        statementButton.click();
    }

    public WebElement findUserStatements(String userName) {
        WebElement selectElement = wait.until(presenceOfElementLocated(By.name("accountno")));
        List<WebElement> users = selectElement.findElements(By.tagName("option"));
        selectElement.click();
        for (WebElement user : users) {
            if (user.getText().equals(userName)) {
                return user;
            };
        }
        return null;
    }


}
