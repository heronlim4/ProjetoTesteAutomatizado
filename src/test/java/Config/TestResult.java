package Config;

public class TestResult {

    private String errorMessage;
    private boolean result;
    private String testName;
    public TestResult (String errorMessage, boolean result, String testName) {

        this.errorMessage = errorMessage;
        this.result = result;
        this.testName = testName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public boolean getResult() {
        return result;
    }
    public void setResult(boolean result) {
        this.result = result;
    }
    public String getTestName() {
        return testName;
    }


}
