package base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PageBase {

	/**
	 * Crear el driver
	 */
	protected WebDriver driver;
	/**
	 * TÃ­tulo de pÃ¡gina esperado. SerÃ¡ usuado en isPageLoad () para comprobar si
	 * la pÃ¡gina estÃ¡ cargada.
	 */
	protected String pageTitle;

	public PageBase(WebDriver driver, String pageTitle) {
		this.driver = driver;
		this.pageTitle = pageTitle;
		// Inicializar los WebElements
		PageFactory.initElements(driver, this);
	}

	public PageBase() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Comprueba si la pÃ¡gina se carga comparando el tÃ­tulo de pÃ¡gina esperado
	 * con un tÃ­tulo de pÃ¡gina real.
	 **/
	public boolean isPageLoad() {
		return (driver.getTitle().contains(pageTitle));
	}

	/**
	 * Retorna el tÃ­tulo de la pÃ¡gina
	 */
	public String getTitle() {
		return pageTitle;
	}

	/**
	 * EnvÃ­a el texto recibido al elemento que recibe por parÃ¡metro
	 * 
	 * @param element
	 * @param text
	 * @return
	 */
	public boolean sendText(WebElement element, String text) {
		boolean result = true;
		if (isElementPresentAndDisplay(element)) {
			element.sendKeys(text);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Hace click sobre el elemento que recibe por parÃ¡metro
	 * 
	 * @param element
	 * @return
	 */
	public boolean clickButtonLink(WebElement element) {
		boolean result = true;
		if (isElementPresentAndDisplay(element)) {
			element.click();
		} else {

			result = false;

		}
		return result;
	}

	public boolean clickRadioButtonByName(List<WebElement> elements, String value) {
		boolean result = true;
		for (int i = 0; i < elements.size(); i++) {
			String opciones = elements.get(i).getAttribute("value");
			if (opciones.equalsIgnoreCase(value)) {
				elements.get(i).click();
				result = true;
				break;
			}
		}
		return result;
	}

	public boolean clickRadioButtonByNameContains(List<WebElement> elements, String value) {
		boolean result = true;
		for (int i = 0; i < elements.size(); i++) {
			String opciones = elements.get(i).getAttribute("value");
			if (opciones.contains(value)) {
				elements.get(i).click();
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Verifica si el texto esta presente en la pÃ¡gina
	 */
	public boolean isTextPresent(String text) {
		return driver.getPageSource().contains(text);
	}

	/**
	 * Verifica si el elemento esta presente en la pÃ¡gina
	 */
	public boolean isElementPresentAndDisplay(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Selecciona una opciÃ³n del combo box por su texto
	 * 
	 * @param element
	 * @param text
	 * @return
	 */
	public boolean selectDropdownVisibleText(WebElement element, String text) {
		boolean result = true;
		Select listBox;
		if (isElementPresentAndDisplay(element)) {
			listBox = new Select(element);
			listBox.selectByVisibleText(text);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Selecciona una opciÃ³n del combo box por su valor
	 * 
	 * @param element
	 * @param value
	 * @return
	 */
	public boolean selectDropdownValue(WebElement element, String value) {
		boolean result = true;
		Select listBox;
		if (isElementPresentAndDisplay(element)) {
			listBox = new Select(element);
			listBox.selectByValue(value);
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * MÃ©todo que obtiene el texto de un elemento y verifica que sea igual al
	 * esperado
	 * 
	 * @param element
	 * @param text
	 * @return
	 */
	public boolean getTextElement(WebElement element, String text) {
		return element.getText().equalsIgnoreCase(text);
	}

	/**
	 * Metodo que obtiene el el texto de un elemento
	 * @param element, elemento que contiene el texto 
	 * @return el texto del elemento
	 */
	public String getText(WebElement element) {
		return element.getText();
	}
	
	/**
	 * Selecciona una opciÃ³n del combo box por su valor, este mÃ©todo es para
	 * acceder a opciones cuando se manejan multiples capas en el frontend
	 * 
	 * @param combo
	 * @param comboOptions
	 * @param text
	 * @return
	 */
	public boolean selectDropdownAjax(WebElement combo, WebElement comboOptions, String text) {
		boolean result = true;
		if (isElementPresentAndDisplay(combo)) {
			List<WebElement> options = comboOptions.findElements(By.tagName("li"));

			for (WebElement option : options) {
				if (option.getText().equals(text)) {
					option.click(); // click the desired option
					break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * Metodo para esperar un tiempo
	 * 
	 * @param segundos
	 */
	public void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		} catch (Exception e) {
			// Mensaje en caso de que falle
		}
	}

	/**
	 * Metodo para dar una alerta de si un componente esta presente
	 * 
	 * @return
	 */
	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			return true;
		} 
		catch (NoAlertPresentException Ex) {
			return false;
		} 
	} 

	/**
	 * Metodo que selecciona los permisos de la lista de permisos
	 * 
	 * @param numeroId
	 *            cadena que identifica el permiso
	 */
	public void encontrarCheckBox(String numeroId) {
		WebElement checkBox = driver.findElement(By.id(numeroId));
		clickButtonLink(checkBox);
	}
}
