public class Calculator {
    int lastValue = 0;
    int operations = 0;

    public int sum(int a, int b){
        operations++;
        lastValue = a+b;
        return lastValue;
    }

    public int multiply(int a, int b){
        operations++;
        lastValue = a*b;
        return lastValue;
    }

    public int divide(int a, int b) throws ArithmeticException {
        operations++;
        if (b == 0) {
            lastValue = 0;
            throw new ArithmeticException("Division by zero");
        }
        lastValue = a/b;
        return lastValue;
    }

    public int lastResult() {
        return lastValue;
    }

    public int totalOperations() {
        return operations;
    }

}
