package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class HomeTests {

	@Test
    public void testPantallaPrincipal() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/");
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'En DONAPP estamos comprometidos')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
}
