package br.com.alura.leilao.leiloes;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import br.com.alura.leilao.selenium.basePage.BasePage;

public class LeiloesPage extends BasePage {
    public static final String URL_LEILAO = "http://localhost:8080/leiloes";

    public LeiloesPage(WebDriver browser) {
        super(browser);
    }

    public LeiloesPage() {
        super();
    }

    public void navigateToNewLeiloes() {
        navigateTo("/leiloes/new");
    }

    public NewLeilaoPage newLeilao() {
        this.getElementById("novo_leilao_link").click();
        return new NewLeilaoPage(this.browser);
    }

    public Boolean newLeilaoWasRegisterd(String name, String openingDate, String initalValue, String user) {
        List<WebElement> trElements = this.browser.findElements(By.tagName("tr"));
        if (trElements.size() == 0) {
            return false;
        }
        List<WebElement> tdElements = trElements.get(trElements.size() - 1).findElements(By.tagName("td"));
        Boolean validationName = tdElements.get(0).getText().equals(name);
        Boolean validationDate = tdElements.get(1).getText().equals(openingDate);
        Boolean validationValue = tdElements.get(2).getText().equals(initalValue);
        Boolean validationUser = tdElements.get(3).getText().equals(user);

        return validationName && validationDate && validationValue && validationUser;
    }

    public Boolean leilaoNameWasEditedCorrectly(String newLeilaoName, String user) {
        List<WebElement> tdElements = this.getTheLastLeilaoRegisteredByTheUser(user);
        System.out.println(tdElements.size());
        Boolean validationName = tdElements.get(0).getText().equals(newLeilaoName);

        return validationName;
    }
    public Boolean leilaoOpeningDateWasEditedCorrectly(String newOpeningDate, String user) {
        List<WebElement> tdElements = this.getTheLastLeilaoRegisteredByTheUser(user);

        Boolean validationDate = tdElements.get(1).getText().equals(newOpeningDate);

        return validationDate;
    }
    public Boolean leilaoInitialValueWasEditedCorrectly(String newInitialValue, String user) {
        List<WebElement> tdElements = this.getTheLastLeilaoRegisteredByTheUser(user);
        double oldInitialValue = new Double(tdElements.get(2).getText());
        double newInitialValueDouble = new Double(newInitialValue);
        System.out.println(oldInitialValue);
        System.out.println(newInitialValueDouble);
        
        Boolean validationValue = oldInitialValue == newInitialValueDouble;

        return validationValue;
    }


    public List<WebElement> getLeiloesRegisteredByTheUser(String user) {
        List<WebElement> trElements = this.browser.findElements(By.tagName("tr"));
        if (trElements.isEmpty()) {
            return null;
        }
        List<WebElement> leiloesList = trElements.stream().filter((element) -> {
            List<WebElement> tdElements = element.findElements(By.tagName("td"));
            if(tdElements.isEmpty()){
                return false;
            }
            return tdElements.get(3).getText().equals(user);
        }).collect(Collectors.toList());

        return leiloesList;
    }

    public List<WebElement> getTheLastLeilaoRegisteredByTheUser(List<WebElement> userLeiloesList) {

        if (userLeiloesList.isEmpty()) {
            return null;
        }
        return userLeiloesList.get(userLeiloesList.size() - 1).findElements(By.tagName("td"));
    }
    public List<WebElement> getTheLastLeilaoRegisteredByTheUser(String user) {
        List<WebElement> userLeiloesList = this.getLeiloesRegisteredByTheUser(user);

        if (userLeiloesList.isEmpty()) {
            return null;
        }
        return userLeiloesList.get(userLeiloesList.size() - 1).findElements(By.tagName("td"));
    }

    public EditLeilaoPage clickEditFromLastLeilao(String user) {
        List<WebElement> userLeiloesList = this.getLeiloesRegisteredByTheUser(user);
        if (userLeiloesList.isEmpty()) {
            throw new IllegalArgumentException("Usuário não possui nenhum leilão cadastrado");
        }
        List<WebElement> lastLeilao = this.getTheLastLeilaoRegisteredByTheUser(userLeiloesList);
        WebElement linkEditar = lastLeilao.get(lastLeilao.size() - 1).findElement(By.tagName("a"));
        linkEditar.click();

        return new EditLeilaoPage(this.browser);
    }

}
