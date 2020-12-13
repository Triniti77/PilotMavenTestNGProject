package dataProvides;

import org.testng.annotations.DataProvider;

public class DataProviderSource {
    @DataProvider
    public  Object[][] client1() {
        return new Object[][]{
                {232, "qwerty"},
                {7778, "ytrewe"}};
    }

    @DataProvider
    public  Object[][] client2() {
        return new Object[][]{
                {5, "five"},
                {6, "six"}};
    }

}
