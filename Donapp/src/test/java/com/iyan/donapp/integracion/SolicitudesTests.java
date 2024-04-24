package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SolicitudesTests {
	
	@Test
    public void testSolicitarProductoVerSolicitud() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("iyancm@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("iyancm@gmail.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/producto/1");
        WebElement boton = driver.findElement(By.className("custom-button"));
        boton.click();
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Solicitud enviada al dueño del producto')]"));
        assertTrue(element1.isDisplayed());
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/solicitudes");
        WebElement element2 = driver.findElement(By.xpath("//*[contains(text(), 'prueba')]"));
        assertTrue(element2.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testVerSolicitudesRecibidas() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("iyancm@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("iyancm@gmail.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/solicitudes");
        WebElement recibidas = driver.findElement(By.id("recibidas-tab"));
        recibidas.click();
        WebElement element2 = driver.findElement(By.xpath("//*[contains(text(), 'No hay solicitudes recibidas')]"));
        assertTrue(element2.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
