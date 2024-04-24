package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PerfilTests {
	
	@Test
    public void testAccesoPerfil() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/miPerfil");
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Usuario:')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testEditarPerfil() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/miPerfil");
        WebElement editButton = driver.findElement(By.className("editar-btn"));
        editButton.click();
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Cambiar')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testEditarBiografia() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/miPerfil");
        WebElement editButton = driver.findElement(By.className("editar-btn"));
        editButton.click();
        WebElement bio = driver.findElement(By.name("descripcion"));
        bio.sendKeys("nueva bio");
        WebElement sendButton = driver.findElement(By.id("botonCambiarDescripcion"));
        sendButton.click();
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'nueva bio')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
}
