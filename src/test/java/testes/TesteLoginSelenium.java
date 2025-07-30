package testes;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TesteLoginSelenium {
    WebDriver driver;

    @BeforeAll
    static void preparaClasse() {
        WebDriverManager.chromedriver().setup(); // Configura driver do Chrome
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    By usernameField = By.id("user-name");
    By passwordField = By.id("password");
    By loginButton = By.id("login-button");
    String nome = "standard_user";
    String senha = "secret_sauce";


    public void login(String user, String pass) {
        driver.findElement(usernameField).sendKeys(user);
        driver.findElement(passwordField).sendKeys(pass);
        driver.findElement(loginButton).click();
    }

    public void acessarPagina() {
        driver.get("https://www.saucedemo.com");
    }

    @Test
    @DisplayName("Login invalido no Site")
    void testaLoginInvalido() {
        acessarPagina();
    }

    @Test
    @DisplayName("Login valido no Site")
    void testaLoginValido() {
        acessarPagina();

        // 2. Preencher credenciais
        login(nome, senha);

        // 3. Verificar se o login foi bem-sucedido
        WebElement productsTitle = driver.findElement(By.className("title"));
        assertEquals("Products", productsTitle.getText());
    }

    @Test
    @DisplayName("Adicionar item ao carrinho")
    void testaAdicionarCarrinho() {
        acessarPagina();

        // 2. Preencher credenciais
        login(nome, senha);

        // 3. Verificar se o login foi bem-sucedido
        WebElement productsTitle = driver.findElement(By.className("title"));
        assertEquals("Products", productsTitle.getText());

        // Verifica o item a ser adicionado
        WebElement productsName = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Bike Light']"));
        driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Bike Light']"));
        assertEquals("Sauce Labs Bike Light", productsName.getText());


        // 4. Adiciona item ao carrinho
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-bike-light']")).click();

        // 5. Abre o carrinho
        driver.findElement(By.xpath("//div[@id=\'shopping_cart_container\']/a")).click();

        // 6. Verificar item do carrinho
        productsName = driver.findElement(By.xpath("//div[normalize-space()='Sauce Labs Bike Light']"));
        assertEquals("Sauce Labs Bike Light", productsName.getText());
    }

    @AfterEach
    void finalizaSelenium() {
        //driver.quit(); // Fecha o navegador após cada teste
    }
}
