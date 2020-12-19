package unitTests.factoryTests;

import org.testng.annotations.Test;

public class Test5 {

    private String str;

    public Test5(String s) {
        this.str = s;
    }

    @Test
    public void test5() {
        System.out.println("Test5 test method. str = " + str);
    }

}
