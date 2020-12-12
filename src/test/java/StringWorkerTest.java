import dataProvides.DataProviderSource;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;


public class StringWorkerTest {

    StringWorker stringWorker = new StringWorker();
    StringWorker stringWorker2;

    @Test
    public void test1(){
        assertEquals(stringWorker.concatenate("qwerty", "123", "_+"), "qwerty123_+");
        assertNull(stringWorker2);
    }

    @Test(dataProvider = "getData", timeOut = 4000)
    public void instanceDbProvider(int p1, String p2){
        System.out.println("Instance DataProvider Example: Data(" + p1 +", " + p2 + "J");
    }

    @DataProvider
    public  Object[][] getData() {
        return new Object[][]{
                {5, "five"},
                {6, "six"}};
    }

    @Test(dataProvider = "client1", dataProviderClass = DataProviderSource.class)
    public void client1Test(Integer p, String t ){
        System.out.println("Client1 testing: Data(" + p + ")" + t);
    }

    @Test(dataProvider = "client2", dataProviderClass = DataProviderSource.class)
    public void client2Test(Integer p, String t ){
        System.out.println("Client2 testing: Data(" + p + ")" + t);
    }

}
