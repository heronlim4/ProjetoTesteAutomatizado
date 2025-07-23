package testes;
import org.ClassesDoSistema.Calculadora;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TesteCalculadora {
    Calculadora calc;

    @BeforeEach
    void PreparaTeste() {
        calc = new Calculadora();
    }

    @Test
    @DisplayName("Teste de Soma Simples")
    void teste_Soma() {
        assertEquals(5, calc.somar(2, 3));
    }

    @Test
    @DisplayName("Teste de Subtracao Simples")
    void teste_Subtrair() {
        assertEquals(2, calc.subtrair(3, 1));
        assertEquals(-1, calc.subtrair(3, 4));
    }

    @Test
    @DisplayName("Teste de Numero Par")
    void teste_Igual() {
        assertTrue(calc.e_par(4));
        assertFalse(calc.e_par(5));
    }

    @Test
    @DisplayName("Verifica Divisão")
    void verifica_Divisao() {
        assertFalse(calc.dividir(12, 6) == 2);
    }

    @Test
    @Disabled("Exemplo de teste desabilitado")
    void TesteDesabilitado() {
        fail("Não deve ser executado");
    }
}
