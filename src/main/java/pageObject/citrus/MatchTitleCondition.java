package pageObject.citrus;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Driver;
import org.openqa.selenium.WebElement;

public class MatchTitleCondition extends Condition {

    private final String title;

    public MatchTitleCondition(String title) {
        super(title);
        this.title = title;
    }

    @Override
    public boolean apply(Driver driver, WebElement webElement) {
        String otherTitle = webElement.getAttribute("title");
        return otherTitle.equals(title);
    }
}
