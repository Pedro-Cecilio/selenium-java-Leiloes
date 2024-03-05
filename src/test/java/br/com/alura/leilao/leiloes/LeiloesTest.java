package br.com.alura.leilao.leiloes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.alura.leilao.login.LoginPage;

@SpringBootTest
class LeiloesTest {

    private LeiloesPage leiloesPage;
    private NewLeilaoPage newLeilaoPage;
    private LoginPage loginPage;
    private EditLeilaoPage editLeilaoPage;

    @AfterEach
    void tearDown() {
        this.leiloesPage.closeBrowser();
    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoCorretamenteThenNovoLeilaoDeveSerRegistrado() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();
        String name = "Prateleira";
        String initialValue = "150.50";
        String openingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();

        assertEquals(LeiloesPage.URL_LEILAO, this.newLeilaoPage.getCurrentUrl());
        assertTrue(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertTrue(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));
    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoValorIncorretoThenDeveAparecerMensagemDeErro() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();

        String name = "Prateleira";
        String initialValue = "0";
        String openingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();
        Boolean showedError = this.newLeilaoPage.pageSourceContains("deve ser um valor maior de 0.1");
        this.newLeilaoPage.navigateTo("leiloes");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));

    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoComDataIncorretaThenDeveAparecerMensagemDeErro() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();

        String name = "Prateleira";
        String initialValue = "10";
        String openingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();
        Boolean showedError = this.newLeilaoPage.pageSourceContains("deve ser uma data no formato dd/MM/yyyy");
        this.newLeilaoPage.navigateTo("leiloes");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));
    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoComDataIncorretaThenDeveAparecerMensagemDeErro2() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();

        String name = "Prateleira";
        String initialValue = "10";
        String openingDate = "abc";

        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();
        Boolean showedError = this.newLeilaoPage.pageSourceContains("deve ser uma data no formato dd/MM/yyyy");
        this.newLeilaoPage.navigateTo("leiloes");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));
    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoComDataIncorretaThenDeveAparecerMensagemDeErro3() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();

        String name = "Prateleira";
        String initialValue = "10";
        String openingDate = "123";

        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();
        Boolean showedError = this.newLeilaoPage.pageSourceContains("deve ser uma data no formato dd/MM/yyyy");
        this.newLeilaoPage.navigateTo("leiloes");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));
    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoNomeIncorretoThenDeveAparecerMensagemDeErro() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();

        String name = "";
        String initialValue = "150.2";
        String openingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();
        Boolean showedError1 = this.newLeilaoPage.pageSourceContains("minimo 3 caracteres");
        Boolean showedError2 = this.newLeilaoPage.pageSourceContains("não deve estar em branco");
        this.newLeilaoPage.navigateTo("leiloes");

        assertTrue(showedError1);
        assertTrue(showedError2);
        assertFalse(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));

    }

    @Test
    void givenUsuarioEstaLogadoWhenCriaNovoLeilaoNomeIncorretoThenDeveAparecerMensagemDeErro2() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();

        String name = "ab";
        String initialValue = "150.2";
        String openingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        this.newLeilaoPage.registerFormNewLeilao(name, initialValue, openingDate);
        this.newLeilaoPage.submitForm();
        Boolean showedError1 = this.newLeilaoPage.pageSourceContains("minimo 3 caracteres");
        Boolean showedError2 = this.newLeilaoPage.pageSourceContains("não deve estar em branco");
        this.newLeilaoPage.navigateTo("leiloes");

        assertTrue(showedError1);
        assertFalse(showedError2);
        assertFalse(this.leiloesPage.newLeilaoWasRegisterd(name, openingDate, initialValue, "fulano"));

    }

    @Test
    void givenUsuarioEstaLogadoWhenCriarNovoLeilaoThenNomeDoUsuarioDeveEstarCorreto() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.newLeilaoPage = this.leiloesPage.newLeilao();
        String nomeDoUsuario = this.newLeilaoPage.getElementById("nomeDoUsuario").getAttribute("value");

        assertEquals("fulano", nomeDoUsuario);
    }

    @Test
    void givenUsuarioNaoEstaLogadoWhenTentaAcessarPaginaParaCriarLeilaoThenDeveSerRedirecionadoParaLogin() {
        this.leiloesPage = new LeiloesPage();
        this.leiloesPage.navigateToNewLeiloes();
        assertEquals(LoginPage.URL_LOGIN, this.leiloesPage.getCurrentUrl());
    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaNomeLeilaoCorretamenteThenNovoLeilaoDeveSerRegistrado() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newLeilaoName = "Tablet Incomum";
        this.editLeilaoPage.editLeilaoName(newLeilaoName);
        this.editLeilaoPage.submitForm();
        Boolean edited = this.leiloesPage.leilaoNameWasEditedCorrectly(newLeilaoName, "fulano");

        assertEquals(LeiloesPage.URL_LEILAO, this.editLeilaoPage.getCurrentUrl());
        assertTrue(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertTrue(edited);
    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaNomeLeilaoIncorretamenteThenMostrarMensagemDeErro() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newLeilaoName = "ta";
        this.editLeilaoPage.editLeilaoName(newLeilaoName);
        this.editLeilaoPage.submitForm();
        Boolean showedError1 = this.editLeilaoPage.pageSourceContains("minimo 3 caracteres");
        this.editLeilaoPage.navigateTo("leiloes");
        Boolean edited = this.leiloesPage.leilaoNameWasEditedCorrectly(newLeilaoName, "fulano");

        assertFalse(edited);
        assertFalse(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertTrue(showedError1);

    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaNomeLeilaoIncorretamenteThenMostrarMensagemDeErro2() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newLeilaoName = "";
        this.editLeilaoPage.editLeilaoName(newLeilaoName);
        this.editLeilaoPage.submitForm();
        Boolean showedError2 = this.editLeilaoPage.pageSourceContains("não deve estar em branco");
        this.editLeilaoPage.navigateTo("leiloes");
        Boolean edited = this.leiloesPage.leilaoNameWasEditedCorrectly(newLeilaoName, "fulano");

        assertFalse(edited);
        assertFalse(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertTrue(showedError2);

    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaValorInicialLeilaoCorretamenteThenNovoLeilaoDeveSerRegistrado() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newValorInicial = "3000";
        this.editLeilaoPage.editLeilaoInitialValue(newValorInicial);
        this.editLeilaoPage.submitForm();
        Boolean edited = this.leiloesPage.leilaoInitialValueWasEditedCorrectly(newValorInicial, "fulano");

        assertEquals(LeiloesPage.URL_LEILAO, this.editLeilaoPage.getCurrentUrl());
        assertTrue(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertTrue(edited);
    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaValorInicialLeilaoIncorretamenteThenMostarMensagemDeErro() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newValorInicial = "0.09";
        this.editLeilaoPage.editLeilaoInitialValue(newValorInicial);
        this.editLeilaoPage.submitForm();
        Boolean showedError = this.editLeilaoPage.pageSourceContains("deve ser um valor maior de 0.1");
        this.editLeilaoPage.navigateTo("leiloes");
        Boolean edited = this.leiloesPage.leilaoInitialValueWasEditedCorrectly(newValorInicial, "fulano");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertFalse(edited);
    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaDataAberturaLeilaoCorretamenteThenNovoLeilaoDeveSerRegistrado() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newDataAbertura = "05/03/2024";
        this.editLeilaoPage.editLeilaoOpeningDate(newDataAbertura);
        this.editLeilaoPage.submitForm();
        Boolean edited = this.leiloesPage.leilaoOpeningDateWasEditedCorrectly(newDataAbertura, "fulano");

        assertEquals(LeiloesPage.URL_LEILAO, this.editLeilaoPage.getCurrentUrl());
        assertTrue(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertTrue(edited);
    }

    @Test
    void givenUsuarioEstaLogadoWhenEditaDataAberturaLeilaoIncorretamenteThenMostarMensagemDeErro() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newDataAbertura = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.editLeilaoPage.editLeilaoOpeningDate(newDataAbertura);
        this.editLeilaoPage.submitForm();
        Boolean showedError = this.editLeilaoPage.pageSourceContains("deve ser uma data no formato dd/MM/yyyy");
        this.editLeilaoPage.navigateTo("leiloes");
        Boolean edited = this.leiloesPage.leilaoOpeningDateWasEditedCorrectly(newDataAbertura, "fulano");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertFalse(edited);
    }
    @Test
    void givenUsuarioEstaLogadoWhenEditaDataAberturaLeilaoIncorretamenteThenMostarMensagemDeErro2() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
        this.loginPage.registerFormLogin("fulano", "pass");
        this.leiloesPage = this.loginPage.efetuarLogin();
        this.editLeilaoPage = this.leiloesPage.clickEditFromLastLeilao("fulano");
        String newDataAbertura = "123";
        this.editLeilaoPage.editLeilaoOpeningDate(newDataAbertura);
        this.editLeilaoPage.submitForm();
        Boolean showedError = this.editLeilaoPage.pageSourceContains("deve ser uma data no formato dd/MM/yyyy");
        this.editLeilaoPage.navigateTo("leiloes");
        Boolean edited = this.leiloesPage.leilaoOpeningDateWasEditedCorrectly(newDataAbertura, "fulano");

        assertTrue(showedError);
        assertFalse(this.leiloesPage.pageSourceContains("Leilão salvo com sucesso"));
        assertFalse(edited);
    }
}
