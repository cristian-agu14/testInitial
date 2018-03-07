package objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import base.PageBase;

/**
 * Unit test for simple App.
 */
public class LoginObjects extends PageBase {

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public LoginObjects(WebDriver driver, String pageTitle) {
		super(driver, pageTitle);
	}

	/**
	 * Metodo que hace la insertar de la informacion para iniciar sesion 
	 * @param user cadena del nombre del usuario
	 * @param pass cadena de la contrasena del usuario
	 * @return true si puede entrar a la pagina de inicio
	 * @return false si no ha podido entar a la pagina
	 */
	public boolean iniciarSesion(String user, String pass) {
		sendText(cajaTextoNameUser, user);
		sendText(cajaTextoPass, pass);
		clickButtonLink(btnIniciarSesion);

		if (isElementPresentAndDisplay(nombreBienvenida)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Elemento web que recibe el usuario para el login
	 */
	@FindBy(how = How.ID, using = "user")
	private WebElement cajaTextoNameUser;

	/**
	 * Elemento web que recibe la contraseña para el login
	 */
	@FindBy(how = How.ID, using = "password")
	private WebElement cajaTextoPass;

	/**
	 * Boton para completar el inicio de sesion
	 */
	@FindBy(how = How.XPATH, using = "/html/body/form/div/div/div[2]/div[2]/input")
	private WebElement btnIniciarSesion;

	@FindBy(how = How.XPATH, using = "/html/body/div[2]/div[2]/div[2]/div[1]/div")
	private WebElement nombreBienvenida;
}
