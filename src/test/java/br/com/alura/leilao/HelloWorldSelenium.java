package br.com.alura.leilao;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWorldSelenium {

    @Test
    public void hello() {
        // System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver.exe");
        ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("src/drivers/chromedriver.exe")).build();
        WebDriver browser = new ChromeDriver(service);
        browser.navigate().to("http://localhost:8080");
    }
}
