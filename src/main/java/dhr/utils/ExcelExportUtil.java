package dhr.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExportUtil {
	// 创建文件头信息
	private static Workbook createHeader(String fileName, List<Map<String,Object>> items) {
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet(fileName);
		Row row = sheet.createRow(0);
		
		
		
		CellStyle style = wb.createCellStyle();
		
		   style.setBorderBottom(CellStyle.BORDER_THIN);  
		   style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
		   style.setBorderLeft(CellStyle.BORDER_THIN);  
		   style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
		   style.setBorderRight(CellStyle.BORDER_THIN);  
		   style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
		   style.setBorderTop(CellStyle.BORDER_THIN);  
		   style.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
		   style.setAlignment(CellStyle.ALIGN_LEFT);
		   style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		for(int i = 0; i < items.size(); i++) {
			sheet.autoSizeColumn(i);
			Cell cell = row.createCell(i);
			String header = (String) items.get(i).get("FIELDDESC");
			int length = header.getBytes().length*256;
			sheet.setColumnWidth(i, length);
			cell.setCellValue(header);
			cell.setCellStyle(style);
		}
		
		return wb;
	}
	
	
	// 写入文件内容
	public static Workbook exportExcel(HttpServletResponse response,String path, String fileName, List<Map<String,Object>> items, List<Map<String,Object>> list) {
		// 创建文件头信息
		Workbook wb = createHeader(fileName, items);
		Sheet sheet = wb.getSheetAt(0);
		
		CellStyle style = wb.createCellStyle();
		
		   style.setBorderBottom(CellStyle.BORDER_THIN);  
		   style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
		   style.setBorderLeft(CellStyle.BORDER_THIN);  
		   style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
		   style.setBorderRight(CellStyle.BORDER_THIN);  
		   style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
		   style.setBorderTop(CellStyle.BORDER_THIN);  
		   style.setTopBorderColor(IndexedColors.BLACK.getIndex()); 
		   style.setAlignment(CellStyle.ALIGN_LEFT);
		   style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		   
		try {
			// 写入行
			for(int i = 0; i < list.size(); i++) {
				Row row = sheet.createRow(i + 1);
				// 写入列
				Map<String,Object> rowMap = list.get(i);
				for(int j = 0; j < items.size(); j++) {
					Cell cell = row.createCell(j);
					Map<String,Object> map = items.get(j);
					String fieldId = (String) map.get("FIELDID");
					int fieldType = (int) map.get("FIELDTYPE");
					if(2==fieldType){
						BigDecimal value = (BigDecimal)rowMap.get(fieldId);
						System.out.println("cell value : " + value);
						if(value==null){
							cell.setCellValue(0);
							cell.setCellStyle(style);
						}else{
							cell.setCellValue(value.doubleValue());
							cell.setCellStyle(style);
						}
					}else if(1==fieldType){
						String value = (String) rowMap.get(fieldId);
						if(value!=null){
							cell.setCellValue(value);
						}
						cell.setCellStyle(style);
					}else if(3==fieldType){
						Date value = (Date) rowMap.get(fieldId);
						if(value!=null){
							cell.setCellValue(value);
						}
						cell.setCellStyle(style);
					}else if(5==fieldType){
						Integer value = (Integer) rowMap.get(fieldId);
						if(value!=null){
							cell.setCellValue(value);
						}
						cell.setCellStyle(style);
					}else if(6==fieldType){
						Object obj = rowMap.get(fieldId);
						if(obj!=null){
							double value = (double) obj;
							cell.setCellValue(value);
						}
						cell.setCellStyle(style);
					}
					
					else{
						String value = (String) rowMap.get(fieldId);
						cell.setCellValue(value);
						cell.setCellStyle(style);
					}
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		// 导出excel
		writeFile(response, wb, path, fileName);
		return wb;
	}
	
	
	// 导出excel
	private static void writeFile(HttpServletResponse response, Workbook wb, String path, String fileName) {
		FileOutputStream os = null;
		OutputStream fos = null;
		System.out.println("fileName: "+fileName);
			try {
//				os = new FileOutputStream(new File(path, fileName + ".xlsx"));
//				wb.write(os);
				fileName = new String(fileName.getBytes("GBK"), "ISO-8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=" +fileName+".xlsx");
		        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		        response.setHeader("Pragma", "no-cache");
		        response.setHeader("Cache-Control", "no-cache");
		        response.setDateHeader("Expires", 0);
		        OutputStream output = response.getOutputStream();
	            wb.write(output);
	            if(output!=null){
	            	output.close();
	            }
	            
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				
				try {
					if(os != null)
						os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		
		
	}
}
