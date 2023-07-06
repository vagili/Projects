package output;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import view.MyFrame;

public class ExcelWriter implements DocumentWriter{
	
	
	
	MyFrame frame;
	String fileName;
	
	public ExcelWriter(MyFrame frame, String fileName) {
		this.frame = frame;
		this.fileName = fileName;
	}
	
	
	public void save(String s) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
		int rows = 1;
		int columns = 0;
		
		for(String word : s.split(" ")) {
			columns++;
		}
		
		String array[][] = new String[rows][columns];
		int b=0;
		for(String word : s.split(" ")) {
			array[0][b] = word;
			b++;
		}
		
		Row row = sheet.createRow(0);
		
		int columnCount = 0;
		for(int j=0;j<columns;j++) {
			Cell cell = row.createCell(columnCount++);
			if(array[0][j] != null) {
				cell.setCellValue(array[0][j]);
			}else {
				cell.setCellValue(" ");
			}
			
		}
		
		
		try(FileOutputStream output = new FileOutputStream(fileName)){
			workbook.write(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void save() {
		
		System.out.println("Mphka kai stin save");
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Sheet1");
		
		
		int rows=0;
		int columns=0;
		for(String line: frame.getTextArea().getText().split("\n")) {
			
			int j=0;
			for(String word: line.split(" ")) {
				j++;
			}
			if(j>columns) {
				columns = j;
			}
			
			rows++;
			
		}
		
		String array[][] = new String[rows][columns];
		
		int i=0;
		for(String line: frame.getTextArea().getText().split("\n")) {
			int j=0;
			for(String word: line.split(" ")) {
				
				array[i][j]= word;
				j++;
			}
			i++;
			
		}
		
		
		
		
		/*
		for(i=0;i<rows;i++) {
			for(int j=0;j<columns;j++) {
				System.out.println(array[i][j]);
			}
		}
		*/
		
		int rowCount = 0;
		
		for(i=0;i<rows;i++) {
			
			Row row = sheet.createRow(rowCount++);
			
			int columnCount = 0;
			for(int j=0;j<columns;j++) {
				Cell cell = row.createCell(columnCount++);
				if(array[i][j] != null) {
					cell.setCellValue(array[i][j]);
				}else {
					cell.setCellValue(" ");
				}
				
			}
			
			
		}
		
		try(FileOutputStream output = new FileOutputStream(fileName)){
			workbook.write(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}
