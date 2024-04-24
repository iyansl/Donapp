package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class BuscarUsuariosTests {
	
	@Test
    public void testAccesoBuscarUsuarios() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/buscarUsuarios");
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Introduce el nombre de usuario:')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testFiltroBuscarUsuarios() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/buscarUsuarios");
        WebElement busqueda = driver.findElement(By.id("busqueda"));
        busqueda.sendKeys("prueba");
        WebElement boton = driver.findElement(By.id("login-submit"));
        boton.click();
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'prueba')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testAccesoPerfilUsuario() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/usuario/2");
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Valorar usuario')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
