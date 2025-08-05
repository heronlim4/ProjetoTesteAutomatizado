package Config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.AfterClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;

public abstract class TestConfig {


    protected static String TEST_CASE_NAME = "";
    protected static String mensagem = "";
    protected static String nomeMetodo = "";
    protected static String momentoExecucao = "";
    protected static String verificacaoCorrente = "";
    protected static WebDriver driver;
    protected  static String mensagemErro = "";
    protected  static boolean sucesso = true;
    protected static ArrayList<TestResult> results = new ArrayList<>();


    public static void openBrowser() throws Exception {
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    public void inicioMetodoTeste(String nomeMetodo) {
        momentoExecucao = "";
        TestConfig.nomeMetodo = nomeMetodo + "\n\n";
    }

    protected void checkpoint() {
        if (momentoExecucao.equals("")) {
            return;
        }
        nomeMetodo += "\t" + momentoExecucao + ": PASS\n";
    }

    protected void checkpoint(String momentoExecucao) {
        checkpoint();
        TestConfig.momentoExecucao = momentoExecucao;
        verificacaoCorrente = nomeMetodo.split("\n")[0] + " >> " + momentoExecucao + "\n -> ";
    }

    protected void fimMetodoTeste() {
        checkpoint();
        results.add(new TestResult(null, sucesso, nomeMetodo));
    }

    protected void clicarSelect(By by, String opcao) {
        driver.findElement(by).click();
        (new Select(driver.findElement(by))).selectByVisibleText(opcao);
    }

    @Test
    public abstract void executarTestes() throws Exception;

    @AfterClass
    public static void saveResult() {
        StringBuilder sb = new StringBuilder();
        for (TestResult r : results) {
            if (r.getResult()== sucesso) {
                sb.append(r.getTestName()).append(": PASS \n\n");
            } else {
                sucesso = false;
                sb.append(r.getTestName()).append("\t").append("Erro: ").append(r.getErrorMessage()).append("\n");
                sb.append("\n:FAIL");
            }
        }
    }

}

