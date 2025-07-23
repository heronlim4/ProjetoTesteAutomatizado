
package testes;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

public class TesteSelenium {
    WebDriver driver;
    Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    String nome = "standart_user";
    String senha = "secret_sauce";
    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");

    @BeforeAll
    static void preparaClasse() {
        WebDriverManager.firefoxdriver().setup(); // Configura driver do Firefox
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
    }

    @Test
    @DisplayName("Abrir pagina do Firefox")
    void abrirPagina() {
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
    @DisplayName("Login inv&aacute;lido no Site")
    void testaLoginInvalido() { // Nome corrigido (Inalido -> Invalido)
        try {
            driver.get("https://www.saucedemo.com");
            login("usuario_invalido", "senha_errada");

            // Verifica mensagem de erro
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")
            ));
            assertTrue(errorMessage.isDisplayed());
        } catch (NoSuchElementException | TimeoutException e) {
            fail("Elemento crítico não encontrado: " + e.getMessage());
        } catch (WebDriverException e) {
            fail("Falha no WebDriver: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Login v&aacute;lido no Site")
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
            fail("Elemento n&atilde;o encontrado: " + e.getMessage());
        } catch (TimeoutException e) {
            fail("P&aacute;gina n&atilde;o carregada a tempo: " + e.getMessage());
        } catch (WebDriverException e) {
            fail("Erro no navegador: " + e.getMessage());
        }
    }

    @AfterEach
    void finalizaSelenium() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (WebDriverException e) {
            System.err.println("Erro ao fechar o driver: " + e.getMessage());
        }
    }

}


