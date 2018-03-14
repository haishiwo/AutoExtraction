package extraction.main;

import java.io.File;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import extraction.window.MessageDialog;
import util.Utils;

/**
 * POI 读取word 2013 中文字内容
 */
public class Main {
	public static void autoExtraction(String label, int length, String regex, List<String> endRegexList,
			List<String> modelsList, File docFile, File excelFile) {
		// 1，希望抽取信息的统称
		Utils.setLabel(label);

		// 2，希望抽取的信息名称长度
		Utils.setLength(length);

		// 3，开始行正则表达式
		Utils.setRegex(regex);

		// 4，结束行正则表达式
		Utils.setEndRegexList(endRegexList);

		// 5，模板
		// 6，希望进行抽取文本的路径
		// 7，希望存放抽取结果表格的路径
		try {
			// 前面介绍过的读取文本方法
			OPCPackage opcPackage = POIXMLDocument.openPackage(docFile.getPath());
			XWPFDocument doc = new XWPFDocument(opcPackage);
			List<String> strList = null;// 新建一个字符串集合以保存所有分段

			strList = Utils.extract(doc);// 获得所有的分段

			// 根据模板对所有的分段进行提取，存入指定表格，并对存储结果进行提示
			if (Utils.writeExcel(modelsList, strList, excelFile)) {
				new MessageDialog(true, "提示", "自动抽取成功！", null);
			} else {
				new MessageDialog(true, "提示", "自动抽取失败", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
