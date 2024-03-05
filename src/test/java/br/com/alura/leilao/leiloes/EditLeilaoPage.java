package br.com.alura.leilao.leiloes;

import org.openqa.selenium.WebDriver;

import br.com.alura.leilao.selenium.basePage.BasePage;

public class EditLeilaoPage extends BasePage{

    public EditLeilaoPage(WebDriver browser){
        super(browser);
    }
    public EditLeilaoPage(){
        super();
    }
    

    public void editLeilaoName(String name){
        this.getElementById("nome").clear();
        this.getElementById("nome").sendKeys(name);
    }
    public void editLeilaoInitialValue(String initialValue){
        this.getElementById("valorInicial").clear();
        this.getElementById("valorInicial").sendKeys(initialValue);
    }
    public void editLeilaoOpeningDate(String openingDate){
        this.getElementById("dataAbertura").clear();
        this.getElementById("dataAbertura").sendKeys(openingDate);
    }

    public void submitForm(){
        this.getElementById("button-submit").click();
    }
}
