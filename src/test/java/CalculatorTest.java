import org.testng.annotations.*;

import static org.testng.Assert.*;

public class CalculatorTest {

    Calculator calculator;
    Integer[] results;
    int testCalculations = 0;

    @DataProvider
    public  Object[][] sumData() {
        return new Object[][]{
                {2, 3, 5},
                {0, 3, 3},
                {-5, 7, 2}};
    }

    @BeforeClass
    public void startUp() {
        System.out.println("Init calculator");
        calculator = new Calculator();
    }

    @AfterClass
    public void shutdown() {
        System.out.println("Total calculations: " + calculator.totalOperations());
    }

    @BeforeMethod
    public void beforeTest() {
        testCalculations = calculator.totalOperations();
    }

    @AfterMethod
    public void afterTest() {
        System.out.println("Test has used " + (calculator.totalOperations()-testCalculations) + " calculations");
    }

    @Test(dataProvider = "sumData")
    public void test1Sum(int x1, int x2,int x3) {
        assertEquals(calculator.sum(x1, x2), x3);
    }

    @Test
    public void test1Multiply() {
        int result = calculator.multiply(4,7);
        assertEquals(result, 28);
        assertTrue(result == 28);
        assertFalse(result == 29);
    }

    @Test
    public void test2Multiply() {
        assertEquals(calculator.multiply(4,0), 0);
    }

    @Test
    public void test3Multiply() {
        int result1 = calculator.multiply(8,-1);
        int result2 = calculator.multiply(-5,-5);
        Integer[] resultArray = {result1, result2};
        Integer[] expectedArray = {-8, 25};
        Integer[] expectedArray2 = {25, -8};
//        assertArrayEquals(resultArray, expectedArray);
        assertEquals(resultArray, expectedArray);
        assertEqualsNoOrder(resultArray, expectedArray2, "Expected results are wrong");

        results = expectedArray;
        test3MultiplySubtest(results);
        testMultiplySubtest2(expectedArray2);
    }

    public void test3MultiplySubtest(Integer[] values) {
        assertSame(results, values);
    }

    public void testMultiplySubtest2(Integer[] values) {
        assertNotSame(results, values);
    }

    @Test(expectedExceptions = {ArithmeticException.class})
    public void test4Divide() throws ArithmeticException {
        int result = calculator.divide(100, 0);
    }

    @Test(priority = -10)
    @Parameters({"div1", "div2"})
    public void test5Divide(int a, int b) {
        try {
            int result = calculator.divide(a, b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = {"test5Divide"}, priority = -10)
    @Parameters({"div3"})
    public void test5DivideDepend(int c) {
        int result = calculator.lastResult();
        assertEquals(result, c);
    }
}