package br.com.alura.leilao.leiloes;
import org.openqa.selenium.WebDriver;

import br.com.alura.leilao.selenium.basePage.BasePage;


public class NewLeilaoPage extends BasePage{
    public static final String URL_NEW_LEILAO = "http://localhost:8080/leiloes/new";


    public NewLeilaoPage(WebDriver browser){
        super(browser);
    }
    public NewLeilaoPage(){
        super();
    }
    

    public void registerFormNewLeilao(String name, String initialValue, String openingDate){
        this.getElementById("nome").sendKeys(name);
        this.getElementById("valorInicial").sendKeys(initialValue);
        this.getElementById("dataAbertura").sendKeys(openingDate);
    }

    public void submitForm(){
        this.getElementById("button-submit").click();
    }
}
