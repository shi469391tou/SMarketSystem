package com.hopu.web.poi;

import com.hopu.domain.Provider;
import com.hopu.domain.User;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 逐行处理的处理器
 *      实现SheetContentsHandler接口
 */
public class SheetHandler implements SheetContentsHandler {
	private Provider provider;

	//开始解析某一行处理的方法
	//参数：i 行索引
	public void startRow(int i) {
		if(i>=2) {
			provider = new Provider();
		}
	}

	//解析结束某一行处理的方法
	public void endRow(int i) {
		System.out.println(provider);
	}

	//解析每一列触发的方法
	/**
	 *
	 * @param columnName 列名  A11、B2
	 * @param columnValue 当前单元格数据
	 * @param xssfComment 注释（不用）
	 */
	public void cell(String columnName, String columnValue, XSSFComment xssfComment) {
		if(provider == null) {
			return ;
		}
		String prefix = columnName.substring(0,1); //A,B,C,D
		switch (prefix) {
			case "A": {
				provider.setProCode(columnValue);
				break;
			}
			case "B": {
				provider.setProName(columnValue);
				break;
			}
			case "C": {
				provider.setProDesc(columnValue);
				break;
			}
			case "D": {
				provider.setProContact(columnValue);
				break;
			}
			case "E": {
				provider.setProPhone(columnValue);
				break;
			}
			case "F": {
				provider.setProAddress(columnValue);
				break;
			}
			case "G": {
				provider.setProFax(columnValue);
				break;
			}
			case "H": {
				provider.setCreationDate(new Date(columnValue));
				break;
			}
			default:{break;}
		}
	}

	//当前sheet全部解析结束之后触发的方法
	public void endSheet() {
		System.out.println("处理结束");
	}
}
