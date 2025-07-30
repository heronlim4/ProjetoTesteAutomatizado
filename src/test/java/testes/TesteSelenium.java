
package testes;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TesteSelenium {

    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");
    String nome = "standard_user";
    String senha = "secret_sauce";
    public WebDriver driver = new FirefoxDriver();
    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));

    @BeforeAll
    static void preparaClasse() {

        WebDriverManager.firefoxdriver().setup(); // Configura driver do Firefox
    }

    @BeforeEach
    void setup() {
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Abrir pagina do Firefox")
    void acessarPagina() {
        driver.get("https://www.saucedemo.com");
    }

    public void login(String user, String pass) throws NoSuchElementException {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField)).sendKeys(user);
            driver.findElement(passwordField).sendKeys(pass);
            driver.findElement(loginButton).click();
        } catch (TimeoutException e) {
            fail("Tempo excedido ao aguardar elemento: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Login invalido no Site")
    void testaLoginInvalido() { // Nome corrigido (Inalido -> Invalido)
        try {
            acessarPagina();
            login("usuario_invalido", "senha_errada");

            // Verifica mensagem de erro
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")
            ));
            assertTrue(errorMessage.isDisplayed());
        } catch (NoSuchElementException | TimeoutException e) {
            fail("Elemento critico nao encontrado: " + e.getMessage());
        } catch (WebDriverException e) {
            fail("Falha no WebDriver: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste que vai falhar")
    void testeFalha() {
        try {
            acessarPagina();
            login(nome, senha);

            // Verifica mensagem de erro
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")
            ));
            assertTrue(errorMessage.isDisplayed());
        } catch (NoSuchElementException | TimeoutException e) {
            fail("Elemento critico nao encontrado: " + e.getMessage());
        } catch (WebDriverException e) {
            fail("Falha no WebDriver: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Login valido no Site")
    void testaLoginValido() {
        try {
            driver.get("https://www.saucedemo.com");
            login(nome, senha);

            // Verifica se est&aacute; na p&aacute;gina de produtos
            WebElement productsTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.className("title")
            ));
            assertEquals("Products", productsTitle.getText());
        } catch (NoSuchElementException e) {
            fail("Elemento nao encontrado: " + e.getMessage());
        } catch (TimeoutException e) {
            fail("Pagina n&atilde;o carregada a tempo: " + e.getMessage());
        } catch (WebDriverException e) {
            fail("Erro no navegador: " + e.getMessage());
        }
    }

    @AfterEach
    void finalizaSelenium() {
        try {
            if (driver != null) {
                //driver.quit();
            }
        } catch (WebDriverException e) {
            System.err.println("Erro ao fechar o driver: " + e.getMessage());
        }
    }

}


