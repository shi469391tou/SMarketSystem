package com.hopu.web.controller;

import com.hopu.domain.Provider;
import com.hopu.domain.Result;
import com.hopu.domain.User;
import com.hopu.service.ProvderServcie;
import com.hopu.utils.DownloadUtil;
import com.hopu.web.poi.ExcelParse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/poi")
public class PoiController {
    @Autowired
    private ProvderServcie provderServcie;
    // 百万级数据导出excel
    @RequestMapping("/printPorvider")
    public void printPorvider(HttpServletResponse response) throws IOException {
        // 从数据库拿所有供应商信息
        List<Provider> providerList = provderServcie.findAllProvider();

        // 1、创建工作簿
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        // 2、创建sheet
        Sheet sheet = workbook.createSheet("供应商列表");
        // 3、开始创建行
        int index = 0;
        // 3.1 大标题，涉及到单元格合并
        Row row = sheet.createRow(index++);
        // firstRow：合并的首行索引；lastRow：合并的尾行索引
        // firstCol：合并的首列索引；lastCol：合并的尾列索引
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
        Cell cell = row.createCell(0);
        cell.setCellValue("供应商列表信息");
        cell.setCellStyle(bigTitle(workbook));

        // 3.2 表头
        row = sheet.createRow(index++);
        String[] headerNames = {"供应商编号", "供应商名称", "供应商描述", "联系人", "联系电话", "联系地址", "传真", "创建日期"};
        for (int i = 0; i < headerNames.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(headerNames[i]);
            cell.setCellStyle(text(workbook));
            sheet.setColumnWidth(i, 20 * 256);
        }
        // 3.3 数据,每一个provider对应一行数据
        for (Provider provider : providerList) {
            for (int i = 0; i < 10000; i++) {
                row = sheet.createRow(index++);
                // 编号
                cell = row.createCell(0);
                cell.setCellValue(provider.getProCode());
                // 名称
                cell = row.createCell(1);
                cell.setCellValue(provider.getProName());
                // 描述
                cell = row.createCell(2);
                cell.setCellValue(provider.getProDesc());
                // 联系人
                cell = row.createCell(3);
                cell.setCellValue(provider.getProContact());
                // 电话
                cell = row.createCell(4);
                cell.setCellValue(provider.getProPhone());
                // 地址
                cell = row.createCell(5);
                cell.setCellValue(provider.getProAddress());
                // 传真
                cell = row.createCell(6);
                cell.setCellValue(provider.getProFax());
                // 日期
                cell = row.createCell(7);
                cell.setCellValue(provider.getCreationDate());
            }
        }
        // excel文件下载
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        DownloadUtil.download(byteArrayOutputStream,response,"供应商列表.xlsx");
    }

//    // 模板导出excel数据
//    @RequestMapping("/printPorvider")
//    public void printPorvider(HttpServletResponse response) throws IOException {
//        // 从数据库拿所有供应商信息
//        List<Provider> providerList = provderServcie.findAllProvider();
//
//        // 1、读取模板工作簿
//        XSSFWorkbook workbook = new XSSFWorkbook("D:\\tOUTPRODUCT.xlsx");
//        // 2、读取sheet
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        // 3、开读取建行
//        int index=0;
//        // 3.1 大标题，涉及到单元格合并
//        XSSFRow row = sheet.getRow(index++);
//        XSSFCell cell = row.getCell(0);
//
//        // 3.2 表头
//        row = sheet.getRow(index++);
//        String [] headerNames = {"供应商编号","供应商名称","供应商描述","联系人","联系电话","联系地址","传真","创建日期"};
//        for (int i = 0; i < headerNames.length; i++) {
//            cell = row.getCell(i);
//            cell.setCellValue(headerNames[i]);
//        }
//        // 补充：读取模板中数据行样式，临时封起来，后面创建数据行的时都用上就行
//        row = sheet.getRow(index);
//        CellStyle[] styles=new CellStyle[row.getLastCellNum()];
//        for (int i = 0; i < styles.length; i++) {
//            styles[i]=row.getCell(i).getCellStyle();
//        }
//        // 3.3 数据,每一个provider对应一行数据
//        for (Provider provider : providerList) {
//            row = sheet.createRow(index++);
//            // 编号
//            cell = row.createCell(0);
//            cell.setCellValue(provider.getProCode());
//            cell.setCellStyle( styles[0]);
//            // 名称
//            cell = row.createCell(1);
//            cell.setCellValue(provider.getProName());
//            cell.setCellStyle( styles[1]);
//            // 描述
//            cell = row.createCell(2);
//            cell.setCellValue(provider.getProDesc());
//            cell.setCellStyle( styles[2]);
//            // 联系人
//            cell = row.createCell(3);
//            cell.setCellValue(provider.getProContact());
//            cell.setCellStyle( styles[3]);
//            // 电话
//            cell = row.createCell(4);
//            cell.setCellValue(provider.getProPhone());
//            cell.setCellStyle( styles[4]);
//            // 地址
//            cell = row.createCell(5);
//            cell.setCellValue(provider.getProAddress());
//            cell.setCellStyle( styles[5]);
//            // 传真
//            cell = row.createCell(6);
//            cell.setCellValue(provider.getProFax());
//            cell.setCellStyle( styles[6]);
//            // 日期
//            cell = row.createCell(7);
//            cell.setCellValue(provider.getCreationDate());
//            cell.setCellStyle( styles[7]);
//        }
//
//        // excel文件下载
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        workbook.write(byteArrayOutputStream);
//        DownloadUtil.download(byteArrayOutputStream,response,"供应商列表.xlsx");
//    }

//    // 导出excel数据
//    @RequestMapping("/printPorvider")
//    public void printPorvider(HttpServletResponse response) throws IOException {
//        // 从数据库拿所有供应商信息
//        List<Provider> providerList = provderServcie.findAllProvider();
//
//        // 1、创建工作簿
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        // 2、创建sheet
//        XSSFSheet sheet = workbook.createSheet("供应商列表");
//        // 3、开始创建行
//        int index=0;
//        // 3.1 大标题，涉及到单元格合并
//        XSSFRow row = sheet.createRow(index++);
//        // firstRow：合并的首行索引；lastRow：合并的尾行索引
//        // firstCol：合并的首列索引；lastCol：合并的尾列索引
//        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));
//        XSSFCell cell = row.createCell(0);
//        cell.setCellValue("供应商列表信息");
//        cell.setCellStyle(bigTitle(workbook));
//
//        // 3.2 表头
//        row = sheet.createRow(index++);
//        String [] headerNames = {"供应商编号","供应商名称","供应商描述","联系人","联系电话","联系地址","传真","创建日期"};
//        for (int i = 0; i < headerNames.length; i++) {
//            cell = row.createCell(i);
//            cell.setCellValue(headerNames[i]);
//            cell.setCellStyle(text(workbook));
//            sheet.setColumnWidth(i,20*256);
//        }
//        // 3.3 数据,每一个provider对应一行数据
//        for (Provider provider : providerList) {
//            row = sheet.createRow(index++);
//            // 编号
//            cell = row.createCell(0);
//            cell.setCellValue(provider.getProCode());
//            cell.setCellStyle(text(workbook));
//            // 名称
//            cell = row.createCell(1);
//            cell.setCellValue(provider.getProName());
//            cell.setCellStyle(text(workbook));
//            // 描述
//            cell = row.createCell(2);
//            cell.setCellValue(provider.getProDesc());
//            cell.setCellStyle(text(workbook));
//            // 联系人
//            cell = row.createCell(3);
//            cell.setCellValue(provider.getProContact());
//            cell.setCellStyle(text(workbook));
//            // 电话
//            cell = row.createCell(4);
//            cell.setCellValue(provider.getProPhone());
//            // 地址
//            cell = row.createCell(5);
//            cell.setCellValue(provider.getProAddress());
//            cell.setCellStyle(text(workbook));
//            // 传真
//            cell = row.createCell(6);
//            cell.setCellValue(provider.getProFax());
//            cell.setCellStyle(text(workbook));
//            // 日期
//            cell = row.createCell(7);
//            cell.setCellValue(provider.getCreationDate());
//            cell.setCellStyle(text(workbook));
//        }
//
//        // excel文件下载
//        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
//        workbook.write(byteArrayOutputStream);
//        DownloadUtil.download(byteArrayOutputStream,response,"供应商列表.xlsx");
//    }

    // 百万级数据读取
    @RequestMapping("/importExcel")
    @ResponseBody
    public Result importExcel(@RequestParam("file") MultipartFile file, HttpSession session) throws Exception {
        // 开始解析上传的文件中的Excel进行数据封装，然后添加
//        // 1、读取工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        // 2、获得表单
        XSSFSheet sheet = workbook.getSheetAt(0);
        // 3、获取最后一行
        int lastRowNum = sheet.getLastRowNum();
        // 获取最后一列
        XSSFRow row = sheet.getRow(1);
        short lastCellNum = row.getLastCellNum();

        List<Provider> providerList=new ArrayList<>();
        // 开始解析并封装数据
        for (int i = 2; i <= lastRowNum; i++) {
            // 获取遍历的每一行
            row=sheet.getRow(i);
            // 获取每一列数据
            Object [] excelData = new Object[8];// 临时存储excel的数据 一个数组是一个Provider对象
            for (int j = 0; j < lastCellNum ; j++) {
                XSSFCell cell = row.getCell(j);
                Object cellValue = getCellValue(cell);
                excelData[j]=cellValue;
            }
            // 创建一个Provider对象
            Provider provider = new Provider(excelData);
            User login_user = (User) session.getAttribute("login_user");
            provider.setCreatedBy(login_user.getId());
            providerList.add(provider);
        }
        // 批量添加
        provderServcie.saveList(providerList);

        Result result = new Result(0, "ok");
        return result;
    }

    // 导入Excel数据
//    @RequestMapping("/importExcel")
//    @ResponseBody
//    public Result importExcel(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
//        // 开始解析上传的文件中的Excel进行数据封装，然后添加
//        // 1、读取工作簿
//        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
//        // 2、获得表单
//        XSSFSheet sheet = workbook.getSheetAt(0);
//        // 3、获取最后一行
//        int lastRowNum = sheet.getLastRowNum();
//        // 获取最后一列
//        XSSFRow row = sheet.getRow(1);
//        short lastCellNum = row.getLastCellNum();
//
//        List<Provider> providerList=new ArrayList<>();
//        // 开始解析并封装数据
//        for (int i = 2; i <= lastRowNum; i++) {
//            // 获取遍历的每一行
//            row=sheet.getRow(i);
//            // 获取每一列数据
//            Object [] excelData = new Object[8];// 临时存储excel的数据 一个数组是一个Provider对象
//            for (int j = 0; j < lastCellNum ; j++) {
//                XSSFCell cell = row.getCell(j);
//                Object cellValue = getCellValue(cell);
//                excelData[j]=cellValue;
//            }
//            // 创建一个Provider对象
//            Provider provider = new Provider(excelData);
//            User login_user = (User) session.getAttribute("login_user");
//            provider.setCreatedBy(login_user.getId());
//            providerList.add(provider);
//        }
//        // 批量添加
//        provderServcie.saveList(providerList);
//
//        Result result = new Result(0, "ok");
//        return result;
//    }

    //解析每个单元格的数据
    private static Object getCellValue(Cell cell){
        Object object=null;
        CellType cellType = cell.getCellType();
        switch (cellType){
            case STRING:
                object=cell.getStringCellValue();
                break;
            case BOOLEAN:
                object=cell.getBooleanCellValue();
                break;
            case NUMERIC:
                boolean flag = DateUtil.isCellDateFormatted(cell);
                if(flag){
                    object=cell.getDateCellValue();
                }else {
                    object=cell.getNumericCellValue();
                }
                break;
            default:
                break;
        }
        return object;
    }


    //int firstRow, int lastRow, int firstCol, int lastCol
    //sheet.addMergedRegion(new CellRangeAddress(0 , 0 , 1 , 8));
    //大标题的样式
    public CellStyle bigTitle(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short)16);
        font.setBold(true);//字体加粗
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        return style;
    }

    //小标题的样式
    public CellStyle title(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("黑体");
        font.setFontHeightInPoints((short)12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);				//横向居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线
        return style;
    }

    //文字样式
    public CellStyle text(Workbook wb){
        CellStyle style = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short)10);

        style.setFont(font);

        style.setAlignment(HorizontalAlignment.LEFT);				//横向居左
        style.setVerticalAlignment(VerticalAlignment.CENTER);		//纵向居中
        style.setBorderTop(BorderStyle.THIN);						//上细线
        style.setBorderBottom(BorderStyle.THIN);					//下细线
        style.setBorderLeft(BorderStyle.THIN);						//左细线
        style.setBorderRight(BorderStyle.THIN);						//右细线

        return style;
    }
}
