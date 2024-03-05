package br.com.alura.leilao.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import br.com.alura.leilao.leiloes.LeiloesPage;
import br.com.alura.leilao.selenium.basePage.BasePage;

public class LoginPage extends BasePage{
    public static final String URL_LOGIN = "http://localhost:8080/login";
    public static final String URL_LOGIN_ERROR = "http://localhost:8080/login?error";

    

    public LoginPage() {
        super();
    }
    public void navigateToLogin() {
        this.browser.navigate().to(URL_LOGIN);
    }
    
    public void registerFormLogin(String username, String password) {
       this.browser.findElement(By.id("username")).sendKeys(username);
        this.browser.findElement(By.id("password")).sendKeys(password);
    }

    public LeiloesPage efetuarLogin(){
        this.browser.findElement(By.id("login-form")).submit();
        return new LeiloesPage(this.browser);
    }
 

}
