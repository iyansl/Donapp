package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegistroTests {
	
	@Test
    public void testAccesoRegistro() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/registrarse");
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Nombre de usuario')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testRegistroEmailIncorrecto() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/registrarse");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("iyancm@gmail.com");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("iyancm@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("iyancm@gmail.com");
        WebElement cPasswordInput = driver.findElement(By.id("confirmPassword"));
        cPasswordInput.sendKeys("iyancm@gmail.com");
        WebElement loginButton = driver.findElement(By.className("custom-button"));
        loginButton.click();
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'El email introducido ya existe en el sistema')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testRegistroPasswordIncorrectas() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/registrarse");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("iyanc455@gmail.com");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("iyanc455@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("iyanc@gmail.com");
        WebElement cPasswordInput = driver.findElement(By.id("confirmPassword"));
        cPasswordInput.sendKeys("otrapassword");
        WebElement loginButton = driver.findElement(By.className("custom-button"));
        loginButton.click();
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Las contraseñas no coinciden')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }

    @Test
    public void testRegistroCorrecto() {
        // Configurar el WebDriver
        System.setProperty("webdriver.chrome.driver", "src/test/java/com/iyan/donapp/integracion/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        // Test
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/registrarse");
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("usuarioPrueba3@gmail.com");
        WebElement email = driver.findElement(By.id("email"));
        email.sendKeys("usuarioPrueba3@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password"));
        passwordInput.sendKeys("Iyanc1234");
        WebElement cPasswordInput = driver.findElement(By.id("confirmPassword"));
        cPasswordInput.sendKeys("Iyanc1234");
        WebElement loginButton = driver.findElement(By.className("custom-button"));
        loginButton.click();
        WebElement element = driver.findElement(By.xpath("//*[contains(text(), 'Verifica tu correo electrónico para confirmar tu cuenta (Revisa la carpeta de Spam).')]"));
        assertTrue(element.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
