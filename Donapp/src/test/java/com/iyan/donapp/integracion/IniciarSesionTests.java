package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class IniciarSesionTests {

	@Test
    public void testPantallaIniciarSesion() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Email')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testIntroducirDatosCorrectos() {
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
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Descarga nuestra')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testIntroducirPasswordIncorrecta() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("iyancppv@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("passwordincorrecta");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Usuario o password incorrectas.')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testIntroducirEmailIncorrecto() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/iniciarsesion");
        WebElement emailInput = driver.findElement(By.id("username"));
        emailInput.sendKeys("iyancpp");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("password");
        WebElement loginButton = driver.findElement(By.id("login-submit"));
        loginButton.click();
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Usuario o password incorrectas.')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testAccesoRecuperarContra() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/recuperarcontrasena");
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Recuperar')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
