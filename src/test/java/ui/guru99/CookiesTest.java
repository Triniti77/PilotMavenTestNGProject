package ui.guru99;

import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class CookiesTest extends Guru99Base {
    @Test
    public void handleCookieTest() throws InterruptedException, AWTException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        doLogin();
        Set<Cookie> cookies = driver.manage().getCookies();
        for (Cookie cookie : cookies) {
            System.out.println("Cookie name="+cookie.getName()
                    +" value="+cookie.getValue()
                    +" domain="+cookie.getDomain()
                    +" path="+cookie.getPath()
                    +" expires="+cookie.getExpiry());
        }
        driver.manage().deleteAllCookies();
        cookies = driver.manage().getCookies();
        assertEquals(0, cookies.size(), "All cookies deleted");

        driver.navigate().refresh();
        getLogoutButton(); // wait page load
        for (Cookie cookie : cookies) {
            String name = cookie.getName();
            assertEquals(name, driver.manage().getCookieNamed(name).getName(), "Check cookie \""+name+"\" is set after reload");
        }
    }
}
