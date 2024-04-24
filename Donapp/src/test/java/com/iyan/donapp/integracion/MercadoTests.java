package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MercadoTests {
	
	@Test
    public void testMercadoNoHayProductos() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("iyancppv@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("iyancppv@gmail.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/mercado");
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'No hay productos que mostrar')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testMercadoHayProductos() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/mercado");
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Camiseta nueva negra')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testFiltrarProductos() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/mercado");
        WebElement filtro = driver.findElement(By.id("busqueda"));
        filtro.sendKeys("Camiseta nueva negra");
        WebElement send = driver.findElement(By.id("botonSendFiltrosMercado"));
        send.click();
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Camiseta nueva negra')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
