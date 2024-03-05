package br.com.alura.leilao.selenium.basePage;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class BasePage {
    private ChromeDriverService service = new ChromeDriverService.Builder()
            .usingDriverExecutable(new File("src/drivers/chromedriver.exe")).build();;

    private static final String URL_BASE = "http://localhost:8080/";


    protected WebDriver browser;

    public BasePage() {
        this.browser = new ChromeDriver(this.service);
    }
    public BasePage(WebDriver browser) {
        this.browser = browser;
    }

    public String getElementTextById(String id) {
        return this.browser.findElement(By.id(id)).getText();
    }

    public WebElement getElementById(String id) {
        try {
            return this.browser.findElement(By.id(id));

        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentUrl() {
        return this.browser.getCurrentUrl();
    }

    /**
     * Navigates to the specified path relative to the base URL.
     * 
     * @param path the path to navigate to, relative to the base URL
     */
    public void navigateTo(String path) {
        this.browser.navigate().to(URL_BASE + path);
    }

    public Boolean pageSourceContains(String text) {
        return this.browser.getPageSource().contains(text);
    }

    public void closeBrowser(){
        this.browser.quit();
    }


}
