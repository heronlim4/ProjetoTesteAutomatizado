package testes;

import Config.TestConfig;
import Config.TestResult;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class TestClass extends TestConfig {

    @Override
    public void executarTestes() {
        TEST_CASE_NAME = "";

        int QUANTIDADE_TENTATIVAS = 5;

        boolean sucesso = false;
        String mensagemErro = "";
        int passadoEm = 0;
        for (int i = 0; i < QUANTIDADE_TENTATIVAS; i++) {
            try {
                switch (passadoEm) {
                    case 0: openBrowser(); passadoEm++;
                    case 1: login(); passadoEm++;
                    case 2: addToCart(); passadoEm++;
                }
                sucesso = true;
                break;
            } catch (Exception e) {
                mensagemErro = e.getMessage();
            }
        }
        if (!sucesso) {
            results.add(new TestResult(mensagemErro, TestConfig.sucesso, nomeMetodo));
            Assert.fail(verificacaoCorrente + mensagemErro);
        }
        for(TestResult resultados: results) {
            if (resultados.getResult() == true) {
                System.out.println("TESTE CONCLUIDO CORRETAMENTE");
            }
            else {
                System.out.println("EXECUÇÃO DO TESTE FALHOU");
            }
            System.out.println(resultados.getTestName());
            if (resultados.getErrorMessage() != null) {
                System.out.println(resultados.getErrorMessage());
            }
        }
    }

    public void login() throws Exception {
        inicioMetodoTeste("método 1");
        {
            checkpoint("Insere nome e senha");
            driver.findElement(By.id("user-name")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys(password);
        }
        {
            checkpoint("Clica no botão de login");
            driver.findElement(By.id("login-button")).click();
        }
        {
            checkpoint("Verifica se o login foi efeutado corretamente");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='title'][contains(.,'Products')]")));
        }
        fimMetodoTeste();
    }

    public void addToCart() throws Exception {
        inicioMetodoTeste("método 2");
        String productName = "Sauce Labs Backpack";
        {
            checkpoint("Seleciona item");
            driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Backpack']")).click();
        }
        {
            checkpoint("Verifica as informações do item");
            assert driver.findElement(By.xpath("//div[@class='inventory_details_name large_size']")).getText().equals(productName);
        }
        {
            checkpoint("Adiciona item ao carrinho");
            driver.findElement(By.id("add-to-cart")).click();
        }
        {
            checkpoint("Verifica se o botão foi alterado");
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.id("add-to-cart"))));
            driver.findElement(By.id("remove"));
        }
        fimMetodoTeste();
    }
}
