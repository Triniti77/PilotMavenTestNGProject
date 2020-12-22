package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.testng.AssertJUnit.assertTrue;

public class StylusGoogleSearchTest extends BaseUITest {
    private String searchKeyword = "iphone kyiv buy";
//    private String lookupDomain = "stylus.ua";
    private String lookupDomain = "cases.kiev.ua";
    private final int MAX_PAGES = 5;

    @BeforeMethod
    public void startUp() {
        driver.get("https://google.com/ncr");
    }

    protected int lookForSomething(String searchDomain, boolean withAds) {
        int pos = -1;
        if (withAds) {
            pos = getSearchPosition(getAdsElements(), searchDomain);
        }
        if (pos > -1) {
            return pos;
        }
        pos = getSearchPosition(getSearchElements(), searchDomain);
        return pos;
    }

    protected List<WebElement> getAdsElements() {
        return driver.findElements(By.xpath("//*[@id='tads']//*[@data-text-ad]/div/div/a"));
    }

    protected List<WebElement> getSearchElements() {
        return driver.findElements(By.xpath("//*[@id='rso']//*[@class='g']/div[@class='rc']/div/a"));
    }

    protected int getSearchPosition(List<WebElement> list, String searchDomain) {
        int searchPos = 0;
        for(WebElement element : list) {
            String value = element.getAttribute("href");
            if (value.contains("://www."+searchDomain) || value.contains("://"+searchDomain)) {
                return searchPos;
            }
            searchPos++;
        }
        return -1;
    }

    public int lookFor(String query, String lookFor, boolean withAds) {
        driver.findElement(By.name("q")).sendKeys(query + Keys.ENTER);
        wait.until(presenceOfElementLocated(By.cssSelector("#result-stats")));

        System.out.println("Test With Ads");
        int pos = -1;
        int page = 1;
        for (; page <= MAX_PAGES; page++) {
            pos = lookForSomething(lookFor, withAds);
            if (pos > -1) {
                break;
            }
            goToPage(page+1);
        }
        System.out.println("Search query='"+query+"' domain='"+lookFor+"' ("+(withAds?"with ads":"without ads")+") found at position "+pos + " on page "+page);
        return page;
    }

    @Test
    public void lookForStylusWithAds() {
        int page = lookFor(searchKeyword, lookupDomain, true);
        if (page <= MAX_PAGES) {
            System.out.println(lookupDomain + " found on page "+ page + " (with ads)");
        } else {
            System.out.println(lookupDomain + " not found on first "+MAX_PAGES + " pages (with ads)");
        }
        assertTrue("Lookup for domain "+lookupDomain+" without ads on query: "+searchKeyword, page <= MAX_PAGES);
    }

    @Test
    public void lookForStylusWithoutAds() {
        int page = lookFor(searchKeyword, lookupDomain, false);
        if (page <= MAX_PAGES) {
            System.out.println(lookupDomain + " found on page "+ page + "  (without ads)");
        } else {
            System.out.println(lookupDomain + " not found on first "+MAX_PAGES + " pages (without ads)");
        }
        assertTrue("Lookup for domain "+lookupDomain+" without ads on query: "+searchKeyword, page <= MAX_PAGES);
    }

    protected void goToPage(int pageNum) {
        driver.findElement(By.xpath("//*[@id='foot']//a[@aria-label='Page "+pageNum+"']")).click();
    }
}
