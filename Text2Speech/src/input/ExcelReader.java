package input;

import java.awt.print.Book;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import commands.OpenDocument;

public class ExcelReader implements DocumentReader{

	String excelPath;
	private ArrayList<String> contents;
	private JTextArea text;
	private OpenDocument open;
	
	public ExcelReader(OpenDocument open, String excelPath) {
		
		this.excelPath = excelPath;
		this.open = open;
		text = new JTextArea();
		contents = new ArrayList <String>();
		
	}

	
	public ArrayList<String> read() throws FileNotFoundException, IOException{
		
		File file = new File(excelPath);
		FileInputStream fis = new FileInputStream(file.getAbsolutePath());
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		
		while(iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			
			while(cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				String cellValue;
				switch(cell.getCellType()) {
					case STRING:
						cellValue = cell.getStringCellValue();
						//System.out.print(cellValue);
						contents.add(cellValue);
						contents.add("\t");

						break;
					case BOOLEAN:
						cellValue = String.valueOf(cell.getBooleanCellValue());

						//System.out.println(cellValue);
						contents.add(cellValue);
						contents.add("\t");

						break;
					case NUMERIC:
						cellValue = String.valueOf(cell.getNumericCellValue());

						//System.out.println(cellValue);
					
						contents.add(cellValue);
						contents.add("\t");
						break;

				}
			}
			contents.add("\n");

			System.out.println();
		}
		
		workbook.close();
		fis.close();
		return contents;
	}
	
	
	public JTextArea getTextArea() {
		return text;
	}


}