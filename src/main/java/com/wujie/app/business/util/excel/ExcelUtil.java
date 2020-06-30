package com.wujie.app.business.util.excel;

import com.wujie.app.business.enums.ErrorEnum;
import com.wujie.app.framework.exception.CustomException;
import com.wujie.app.framework.util.base.impl.BaseServiceImpl;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *************************************************
 *	公用的excel导出
 * @author  MengDaNai                   
 * @version 1.0                
 * @date    2019年3月8日 创建文件                                            
 * @See                            
 *************************************************
 */
public class ExcelUtil extends BaseServiceImpl {

	/**
	 *************************************************
	 *	Excel表格导出
	 *
	 *	例子<BR/>
	 *
	 *	List<List<String>> excelData = new ArrayList<>();<BR/>
     *
	 *  List<String> head = new ArrayList<>();<BR/>
	 *  head.add("第一列");<BR/>
	 *  head.add("第二列");<BR/>
	 *  head.add("第三列");<BR/>
     *
	 *  List<String> data1 = new ArrayList<>();<BR/>
	 *  data1.add("123");<BR/>
	 *  data1.add("234");<BR/>
	 *  data1.add("345");<BR/>
     *
	 *  List<String> data2 = new ArrayList<>();<BR/>
	 *  data2.add("abc");<BR/>
	 *  data2.add("bcd");<BR/>
	 *  data2.add("cde");<BR/>
     *
	 *  excelData.add(head);<BR/>
	 *  excelData.add(data1);<BR/>
	 *  excelData.add(data2);<BR/>
     *
	 *  String sheetName = "测试";<BR/>
	 *  String fileName = "ExcelTest.xls";<BR/>
     *
	 *  ExcelUtil.exportExcel(ThreadParameterUtil.getResponse(), excelData, sheetName, fileName, 15);<BR/>
	 *
	 * @author  MengDaNai
	 * @param response HttpServletResponse对象
	 * @param excelData Excel表格的数据，封装为List<List<String>>
	 * @param sheetName sheet的名字
	 * @param fileName 导出Excel的文件名
	 * @param columnWidth Excel表格的宽度，建议为15
	 * @date    2019年3月8日 创建文件         
	 *************************************************
	 */
    public static void exportExcel(HttpServletResponse response,List<List<String>> excelData, String sheetName, String fileName, int columnWidth) {

        //声明一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        //生成一个表格，设置表格名称
        HSSFSheet sheet = workbook.createSheet(sheetName);

        //设置表格列宽度
        sheet.setDefaultColumnWidth(columnWidth);

        //写入List<List<String>>中的数据
        int rowIndex = 0;
        for(List<String> data : excelData){
            //创建一个row行，然后自增1
            HSSFRow row = sheet.createRow(rowIndex++);

            //遍历添加本行数据
            for (int i = 0; i < data.size(); i++) {
                //创建一个单元格
                HSSFCell cell = row.createCell(i);

                //创建一个内容对象
                HSSFRichTextString text = new HSSFRichTextString(data.get(i));

                //将内容对象的文字内容写入到单元格中
                cell.setCellValue(text);
            }
        }

        //准备将Excel的输出流通过response输出到页面下载
        //八进制输出流
        response.setContentType("application/octet-stream");

        //设置导出Excel的名称
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        
        try {
        	//刷新缓冲
			response.flushBuffer();
			
			// workbook将Excel写入到response的输出流中，供页面下载该Excel文件
			workbook.write(response.getOutputStream());
			
			//关闭workbook
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CustomException(ErrorEnum.GATEWAY_ERROR);
		}

    }
}
