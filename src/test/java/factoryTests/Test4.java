package factoryTests;

import org.testng.annotations.Test;

public class Test4 {

    private String str;

    public Test4(String s) {
        this.str = s;
    }

    @Test
    public void test4() {
        System.out.println("Test4 test method. str = " + str);
    }
}
