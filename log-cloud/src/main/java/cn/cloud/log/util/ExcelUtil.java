package cn.cloud.log.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * 读取写入excel文件
 * @author Administrator
 *
 */
public class ExcelUtil {
    
	public static void readFile(String filepath){
		
	}
	
	public static Object getCellValue(Row row,int index){
		Object val = "";
        try{
            Cell cell = row.getCell(index);
            if (cell != null){
                if (cell.getCellType() == CellType.NUMERIC){
                    val = cell.getNumericCellValue();
                }else if (cell.getCellType() == CellType.STRING){
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == CellType.FORMULA){
                    val = cell.getCellFormula();
                }else if (cell.getCellType() == CellType.BOOLEAN){
                    val = cell.getBooleanCellValue();
                }else if (cell.getCellType() == CellType.ERROR){
                    val = cell.getErrorCellValue();
                }
            }
        }catch (Exception e) {
            return val;
        }
        return val; 
	}
	
}
