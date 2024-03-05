package br.com.alura.leilao.login;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LoginTest {
    private LoginPage loginPage;
    

    @BeforeEach
    public void setup() {
        this.loginPage = new LoginPage();
        this.loginPage.navigateToLogin();
    }

    @AfterEach
    public void tearDown() {
        this.loginPage.closeBrowser();
    }

    @Test
    void givenEstouNaPaginaLoginWhenInsiroDadosCorretosEOsEnvioThenLogarNaAplicacao() {

        this.loginPage.registerFormLogin("fulano", "pass");
        String loggedUser = this.loginPage.getElementTextById("logged-user");
        assertNotEquals(LoginPage.URL_LOGIN, this.loginPage.getCurrentUrl());
        assertEquals("http://localhost:8080/leiloes", this.loginPage.getCurrentUrl());
        assertEquals("fulano", loggedUser);
    }

    @Test
    void givenEstouNaPaginaLoginWhenInsiroDadosIncorretosEOsEnvioThenMostrarMensagemErro() {

        this.loginPage.registerFormLogin("fulano", "passe");
        String errorMessage = this.loginPage.getElementTextById("invalid-login");
        assertEquals(LoginPage.URL_LOGIN_ERROR, this.loginPage.getCurrentUrl());
        assertEquals("Usuário e senha inválidos.", errorMessage);
        assertNull(this.loginPage.getElementById("logged-user"));
    }

    @Test
    void givenNaoEstouLogadoNaAplicacaoWhenTentoAcessarUmaPaginaRestritaThenDevoSerRedirecionadoParaLogin() {

        this.loginPage.navigateTo("leiloes/2");
        assertFalse(this.loginPage.pageSourceContains("Dados do Leilão"));
        assertEquals(LoginPage.URL_LOGIN, this.loginPage.getCurrentUrl());

    }

}
