package testes;

import Config.TestConfig;
import Config.TestResult;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

        }
        {
            checkpoint("Verifica se o login foi efeutado corretamente");
        }
        fimMetodoTeste();
    }

    public void addToCart() throws Exception {
        inicioMetodoTeste("método 2");
        {
            checkpoint("Entra no módulo");
        }
        {
            checkpoint("Entra na funcionalidade");
        }
        fimMetodoTeste();
    }
}
