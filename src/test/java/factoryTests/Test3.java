package factoryTests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

public class Test3 {

    @Factory
    public Object[] getTestClasses1() {
        Object[] tests = new Object[2];
        tests[0] = new Test1();
        tests[1] = new Test2();
        return tests;
    }

    @Factory(dataProvider = "dp")
    public Object[] getTestClasses1(String s) {
        Object[] tests = new Object[2];
        tests[0] = new Test4(s);
        tests[1] = new Test5(s);
        return tests;
    }

    @DataProvider
    public Object[] dp() {
        return new Object[] {"A","B"};
    }



}
