package com.iyan.donapp.integracion;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class PublicarTests {
	
	@Test
    public void testAccesoPublicar() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/publicar");
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Nombre:')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testDatosCorrectos() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/publicar");
        WebElement nombre = driver.findElement(By.id("nombre"));
        nombre.sendKeys("nombre");
        WebElement descripcion = driver.findElement(By.id("descripcion"));
        descripcion.sendKeys("descripcion");
        WebElement descripcionEntrega = driver.findElement(By.id("descripcionEntrega"));
        descripcionEntrega.sendKeys("descripcionEntrega");
        WebElement ubicacion = driver.findElement(By.id("ubicacion"));
        ubicacion.sendKeys("ubicacion");
        WebElement boton = driver.findElement(By.id("botonSubmitPublicar"));
        boton.click();
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Exito al añadir el producto.')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
	@Test
    public void testDatosIncorrectos() {
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
        
        driver.get("https://donapp-25d4421f1d6f.herokuapp.com/publicar");
        WebElement nombre = driver.findElement(By.id("nombre"));
        nombre.sendKeys("n");
        WebElement descripcion = driver.findElement(By.id("descripcion"));
        descripcion.sendKeys("descripcion");
        WebElement descripcionEntrega = driver.findElement(By.id("descripcionEntrega"));
        descripcionEntrega.sendKeys("descripcionEntrega");
        WebElement ubicacion = driver.findElement(By.id("ubicacion"));
        ubicacion.sendKeys("ubicacion");
        WebElement boton = driver.findElement(By.id("botonSubmitPublicar"));
        boton.click();
        WebElement element1 = driver.findElement(By.xpath("//*[contains(text(), 'Los datos deben tener al menos 4 caracteres')]"));
        assertTrue(element1.isDisplayed());
        
        // Cerrar el WebDriver
        driver.quit();
    }
	
}
