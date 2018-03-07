package test.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBase;
import objects.LoginObjects;
import utils.ExcelUtils;
import utils.ExcelUtils.ExcelType;

public class Login extends TestBase {

	protected LoginObjects login;

	private String usuario = "";
	private String contrasena = "";
	static ExcelUtils excelDatosLogin;

	/**
	 * 
	 */
	@Test
	public void iniciarSesion() {
		login = new LoginObjects(driver, "");
		try {
			excelDatosLogin = new ExcelUtils("datosLoginEdeq.xlsx", ExcelType.XLSX);
			usuario = excelDatosLogin.getCellData(1, 1);
			contrasena = excelDatosLogin.getCellData(1, 2);
			Assert.assertTrue(login.iniciarSesion(usuario, contrasena));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
