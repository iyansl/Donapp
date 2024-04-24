package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class ValoracionesTest {
	
	@Test
    public void testValorarUsuarioContenidoVacio() {
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
        
        WebElement boton = driver.findElement(By.xpath("//*[contains(text(), 'Enviar valoración')]"));
        boton.click();
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Escribe un mensaje de al menos 5 caracteres')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testValorarUsuarioContenidoCorrecto() {
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
        WebElement mensaje = driver.findElement(By.id("valoracionUsuario"));
        mensaje.sendKeys("mensaje");
        WebElement boton = driver.findElement(By.xpath("//*[contains(text(), 'Enviar valoración')]"));
        boton.click();
        
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Escribe un mensaje de al menos 5 caracteres')]"));
        assertFalse(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
