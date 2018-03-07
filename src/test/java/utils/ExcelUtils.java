package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import utils.ExcelUtils.ExcelType;

public class ExcelUtils {

	private Sheet sheet;
	private Workbook excelWBook;
	private static final String BASE_PATH = "Resources/datapool/";

	/**
	 * EnumeraciÃ³n que representa los tipos de hoja de cÃ¡culo soportados
	 * 
	 * @author anamariaquinteroleal
	 *
	 */
	public enum ExcelType {
		XLS, XLSX
	}

	/**
	 * Crea una instancia de la clase utilitaria para leer los datos de un archivo
	 * de excel. Se carga por defecto la primera hoja
	 * 
	 * @param path
	 *            Ruta del archivo a cargar
	 * @param tipo
	 *            Tipo de archivo
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ExcelUtils(String path, ExcelType tipo) throws FileNotFoundException, IOException {
		this(path, 0, tipo);
	}

	/**
	 * @param path
	 *            Ruta del archivo a cargar
	 * @param numSheet
	 *            Numero de la hoja a cargar
	 * @param tipo
	 *            Tipo de archivo
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ExcelUtils(String path, int numSheet, ExcelType tipo) throws FileNotFoundException, IOException {
		excelWBook = createWorkBook(new FileInputStream(BASE_PATH + path), tipo);
		loadSheet(numSheet);
	}

	/**
	 * Constructor
	 * 
	 * @param path
	 *            Ruta del archivo a cargar
	 * @param sheetName
	 *            Nombre de la hoja a cargar
	 * @param tipo
	 *            Tipo de archivo
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public ExcelUtils(String path, String sheetName, ExcelType tipo) throws FileNotFoundException, IOException {
		excelWBook = createWorkBook(new FileInputStream(BASE_PATH + path), tipo);
		loadSheet(sheetName);
	}

	/**
	 * Metodo que permite cambiar la hoja del archivo de excel de la cual se estan
	 * extrayendo los datos
	 * 
	 * @param sheetName
	 *            Nombre de la hoja del archivo de excel
	 */
	public void loadSheet(String sheetName) {
		sheet = excelWBook.getSheet(sheetName);
	}

	/**
	 * Metodo que permite cambiar la hoja del archivo de excel de la cual se estan
	 * extrayendo los datos
	 * 
	 * @param numSheet
	 *            NÃºmero de la hoja que se desea cargar
	 */
	public void loadSheet(int numSheet) {
		sheet = excelWBook.getSheetAt(numSheet);
	}

	/**
	 * MÃ©todo que permite obtener la informaciÃ³n de una celda
	 * 
	 * @param rowNum
	 *            nÃºmero de la fila
	 * @param colNum
	 *            nÃºmero de la columna
	 * @param sheetName
	 *            Nombre de la hoja
	 * @return un String con la informaciÃ³n de la celda
	 */
	public String getCellData(int rowNum, int colNum, String sheetName) {
		loadSheet(sheetName);
		return getCellData(rowNum, colNum);
	}

	/**
	 * MÃ©todo que permite obtener la informaciÃ³n de una celda
	 * 
	 * @param rowNum
	 *            nÃºmero de la fila
	 * @param colNum
	 *            nÃºmero de la columna
	 * @param numSheet
	 *            NÃºmero de la hoja
	 * @return un String con la informaciÃ³n de la celda
	 */
	public String getCellData(int rowNum, int colNum, int numSheet) {
		loadSheet(numSheet);
		return getCellData(rowNum, colNum);
	}

	/**
	 * MÃ©todo que permite obtener la informaciÃ³n de una celda
	 * 
	 * @param rowNum
	 *            nÃºmero de la fila
	 * @param colNum
	 *            nÃºmero de la columna
	 * @return un String con la informaciÃ³n de la celda
	 * @throws Exception
	 */
	public String getCellData(int rowNum, int colNum) {
		try {
			Cell cell = sheet.getRow(rowNum).getCell(colNum);
			CellType cellTypeEnum = cell.getCellTypeEnum();
			return getCellStringValue(cell, cellTypeEnum);
		} catch (Exception e) {
			throw new RuntimeException("Error al obtener el dato de la celda " + rowNum + ":" + colNum, e);
		}
	}

	public void setCellData(int rowNum, int colNum, String valor) {
		try {
			Cell cell = sheet.getRow(rowNum).getCell(colNum);
			CellType cellTypeEnum = cell.getCellTypeEnum();
			cell.setCellValue(valor);

		} catch (Exception e) {
			throw new RuntimeException("Error al guardar el dato de la celda " + rowNum + ":" + colNum, e);
		}

	}

	private String getCellStringValue(Cell cell, CellType cellTypeEnum) {
		switch (cellTypeEnum) {
		case NUMERIC:
			return "" + cell.getNumericCellValue();
		case BOOLEAN:
			return "" + cell.getBooleanCellValue();
		case FORMULA:
			return getCellStringValue(cell, cell.getCachedFormulaResultTypeEnum());
		case ERROR:
			return "CELL ERROR: " + cell.getErrorCellValue();
		case BLANK:
		case _NONE:
			return "";
		default:
			return cell.getStringCellValue();
		}
	}

	/**
	 * MÃ©todo que crea la instancia de la clase (dependiendo de su tipo) que
	 * manipularÃ¡ el archivo de excel.
	 * 
	 * @param input
	 *            Flujo de entrada del archivo de excel
	 * @param tipo
	 *            Tipo de archivo
	 * @return un libro de excel
	 * @throws IOException
	 */
	private Workbook createWorkBook(InputStream input, ExcelType tipo) throws IOException {
		switch (tipo) {
		case XLS:
			return new HSSFWorkbook(input);
		case XLSX:
			return new XSSFWorkbook(input);
		default:
			return null;
		}

	}

	
}
