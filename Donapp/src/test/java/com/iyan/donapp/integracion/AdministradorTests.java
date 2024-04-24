package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AdministradorTests {
	
	@Test
    public void testAccesoDenunciasAdmin() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("admin@email.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("admin@email.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/denuncias");
        
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Lista de Denuncias')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testCerrarDenunciaAdmin() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("admin@email.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("admin@email.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/denuncias");
        
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Cerrar Denuncia')]"));
        assertTrue(element.isDisplayed());
        element.click();
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Se ha cerrado la denuncia correctamente.')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testEliminarUsuario() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("admin@email.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("admin@email.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/buscarUsuarios");
        
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Eliminar')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testEliminarProducto() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("admin@email.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("admin@email.com");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/mercado");
        
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Eliminar producto')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
