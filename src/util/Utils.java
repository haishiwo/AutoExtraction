package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import bean.Rule;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 进行抽取的工具类
 * 
 * @throws Exception
 */
public class Utils {

	private static String regex;// 开始行的正则表达式
	private static List<String> endRegexList;
	private static String label;// 要抽取信息的统称，如“侯症简介”
	private static int length;// 要抽取信息名称的长度。如要抽取侯症，即“XXXX候”，则长度为5

	public static String getRegex() {
		return regex;
	}

	public static void setRegex(String regex) {
		Utils.regex = regex;
	}

	public static List<String> getEndRegexList() {
		return endRegexList;
	}

	public static void setEndRegexList(List<String> endRegexList) {
		Utils.endRegexList = endRegexList;
	}

	public static String getLabel() {
		return label;
	}

	public static void setLabel(String label) {
		Utils.label = label;
	}

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		Utils.length = length;
	}

	/**
	 * 判断字符串str是否符合由regex决定的正则表达式，符合返回true，否则false
	 */
	public static boolean match(String str) {
		if (str.matches(Utils.regex)) {
			return true;
		}
		return false;
	}

	/**
	 * endRegexList是一个字符串集合，包含所有的结束行规则正则表达式
	 * 判断字符串str是否符合endRegexList中的正则表达式，符合任何一个即返回true
	 */
	public static boolean matchEnd(String str) {
		// 遍历集合endRegexList
		for (String string : endRegexList) {
			if (str.matches(string)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 根据模板和待分析文本写excel
	 */
	public static boolean writeExcel(List<String> modelsList, List<String> text2013, File excel) {

		WritableWorkbook wwb = null;
		String flagStr="";
		int flagIndex1=0;
		int flagIndex2=0;
		try {
			int index1 = 0;// 抽取开始索引
			int index2 = 0;// 抽取结束索引
			int k = 0;// while循环控制变量
			String str = null;// 初始字符串
			Label label = null;// 表格的单元格类型
			// 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象
			wwb = Workbook.createWorkbook(excel);
			if (wwb != null) {
				// 创建一个可写入的工作表。Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
				WritableSheet ws = wwb.createSheet("sheet1", 0);
				// 遍历包含所有分段的集合text2013，这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
				for (int j = 0; j <= text2013.size(); j++) {
					for (int i = 0; i < modelsList.size(); i++) {// 遍历模板集合
						if (j == 0) {
							if (i == 0) {// 第一行第一列写入抽取信息通称
								label = new Label(i, j, Utils.label);
							} else {// 第一行其余地方写入模板
								label = new Label(i, j, modelsList.get(i));
							}
						} else {
							// 定位两个索引，之间的内容就是需要提取的部分
							index1 = text2013.get(j - 1).indexOf(modelsList.get(i));
							// 如果没有找到开始索引，则当前关键字对应部分置空
							if (index1 == -1) {
								str = "";
							} else {
								// 遍历到最后一个关键字时，由于没有结束索引，则直接从开始索引截取到分段结束
								if (i == modelsList.size() - 1) {
									str = text2013.get(j - 1).substring(index1 + modelsList.get(i).length());
								} else {
									index2 = text2013.get(j - 1).indexOf(modelsList.get(i + 1), index1);
									// 如果没有找到结束索引，则顺延到下一个关键字作为结束索引继续寻找
									while (index2 == -1) {
										k = k + 1;
										// 如果找到最后也没找到字符串下限，则以最后一个字符作为下限
										if ((i + 1 + k) >= modelsList.size()) {
											index2 = text2013.get(j - 1).length();
											break;
										} else {
											index2 = text2013.get(j - 1).indexOf(modelsList.get(i + 1 + k));
										}
									}
									k = 0;
									if (i == 0) {
										str = text2013.get(j - 1)
												.substring(index1 - Utils.length + modelsList.get(0).length(), index2);
									} else {
										flagStr=text2013.get(j - 1);
										flagIndex1=index1 + modelsList.get(i).length();
										flagIndex2=index2;
										str = text2013.get(j - 1).substring(index1 + modelsList.get(i).length(),
												index2);
									}
								}
							}
							label = new Label(i, j, str);// 将抽取内容写入单元格
						}
						try {
							// 将生成的单元格添加到工作表中
							ws.addCell(label);
						} catch (RowsExceededException e) {
							e.printStackTrace();
						} catch (WriteException e) {
							e.printStackTrace();
						}
					}
				}
				try {
					// 从内存中写入文件中
					wwb.write();
					// 关闭资源，释放内存
					wwb.close();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (WriteException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("出错了");
			System.out.println(flagStr);
			System.out.println(flagIndex1);
			System.out.println(flagIndex2);
			return false;
		}
		return true;

	}

	/**
	 * 从整篇文档中抽取出易读取的小节
	 * 
	 * @throws IOException
	 */
	public static List<String> extract(XWPFDocument doc) throws IOException {
		// 通过XWPFDocument类的getParagraphs方法得到所有行的集合（以回车分割）
		XWPFParagraph[] paras = doc.getParagraphs();
		// 新建一个List类型以接收最终分割出的分段
		List<String> strList = new ArrayList<String>();
		// 新建一个String类作为初始字符串
		String str = "";
		int start = 0;// 开始读取的行数
		int flag = 0;// 判定是否是第二次识别
		int index1 = 0;// 开始索引
		int index2 = 0;// 结束索引
		// 遍历XWPFParagraph集合以寻找start
		for (int j = 0; j < paras.length; j++) {
			if (match(paras[j].getText())) {
				start = j;
				break;
			}
		}
		// 遍历XWPFParagraph集合进行分段的生成
		for (int i = start; i < paras.length; i++) {
			if (match(paras[i].getText())) {// 如果符合开始行规则
				if (flag == 0) {// 第一次识别（*）
					index1 = i;
					flag = flag + 1;
					index2 = 0;
				} else {// 第二次识别
					index2 = i;
					// 将两次识别位置之间的内容记录并存储到strList
					for (int j = index1; j < index2; j++) {
						str = str + paras[j].getText();
					}
					strList.add(str);
					index1 = index2;
				}
			} else if (matchEnd(paras[i].getText())) {// 如果符合结束行规则
				if (flag != 0) {// 只在第二次识别时响应
					index2 = i;
					for (int j = index1; j < index2; j++) {
						str = str + paras[j].getText();
					}
					strList.add(str);
					flag = 0;
				}
			}
			str = "";// 一个分段存储结束，将str置空
		}
		// 如果循环从*部分退出，即最后两个分段不连续，则最后一个分段的开头应该是index1而不是index2
		if (index2 == 0) {// 存储最后一个分段
			for (int j = index1; j < paras.length; j++) {
				str = str + paras[j].getText();
			}
		} else {
			for (int j = index2; j < paras.length; j++) {
				str = str + paras[j].getText();
			}
		}
		strList.add(str);
		return strList;
	}

	/**
	 * 获得文件拓展名
	 */
	public static String getExtension(File file) {
		String fileName = file.getName();// 获取文件名
		// 返回文件名最后一个"."及之后的内容
		return fileName.substring(fileName.lastIndexOf("."));
	}

	// /*
	// *
	// 不是从程序编译器手动写入的字符串都要进行转义处理，如手动输入str="\\123"，则识别为str="\123",但若从窗体输入\\123后赋值，
	// * 则识别为str="\\123"
	// */
	// public static String transfer(String str) {
	// String str1 = str.replace("\\\"", "\"");// “ \" ”换成“ " ”
	// String str2 = str1.replace("\\'", "'");// “ \' ”换成“ ' ”
	// return str2.replace("\\\\", "\\");
	// }

	/**
	 * 根据用户输入的数量和内容自动生成正则表达式
	 */
	public static String createRegex(String numMin, String numMax, String textBox, String text, String type) {
		int i;// 记录循环结束值以判断是否有特殊字符
		String regex = "";
		// 在正在表达式里需要转义的特殊字符
		String[] specialList = { "?", "*", "+", ".", "(", ")" };
		if (!textBox.equals("未选择")) {// 当用户选项不等于“未选择”时才进行生成
			if (!text.equals("")) {// 用户选择为“确定内容”
				for (i = 0; i < specialList.length; i++) {
					if (text.equals(specialList[i])) {
						regex += "\\" + text;// 当出现特殊字符时，需要添加“\\”
						break;
					}
				}
				if (i == specialList.length) {// 如果没有出现特殊字符，则直接添加
					regex += text;
				}
			} else {
				switch (type) {// 用户选择为“指定类型”
				case "汉字":// 选择“汉字”
					regex += "[\u4e00-\u9fa5]";
					break;
				case "数字":// 选择“数字”
					regex += "\\d";
					break;
				case "大写字母":// 选择大写字母
					regex += "[A-Z]";
					break;
				case "小写字母":
					regex += "[a-z]";// 选择小写字母
					break;
				default:
					break;

				}
			}

			if (!numMin.equals("") && numMax.equals("")) {// 用户输入为确定数个
				regex += "{" + numMin + "}";
			} else if (!numMin.equals("") && !numMax.equals("")) {// 用户输入不确定个
				regex += "{" + numMin + "," + numMax + "}";
			}
		}
		return regex;
	}

	/**
	 * 向用户展示已输入的内容
	 */
	public static String createTextForShow(String numMin, String numMax, String textBox, String text, String type) {
		String textForShow = "";// 初始TFS(textForShow)
		if (!textBox.equals("未选择")) {// 只有当用户选择不等于“未选择”时才生成
			if (!numMin.equals("") && numMax.equals("")) {// 确定数个，加“个”
				textForShow += numMin + "个";
				// 一个范围，加“到”和“个”
			} else if (!numMin.equals("") && !numMax.equals("")) {
				textForShow += numMin + "到" + numMax + "个";
			} else {// 未输入，默认为1个
				textForShow += "1个";
			}

			if (!text.equals("")) {// 输入为确定内容
				textForShow += text;// 直接添加内容
			} else {// 输入为特定类型
				textForShow += type;// 添加类型名
			}
			textForShow += "；";// 最后添加一个分号
		}

		return textForShow;

	}

	/**
	 * 使用database.xls模拟数据库存储方法
	 */
	public static boolean save(String name, String label, String length, String regex, List<String> endRegexList,
			List<String> modelsList, String regexTFS, String endRegexTFS) {
		try {
			int row = 0;// 列号
			int line = Utils.getFirstEmptyLine();// 行号，从第一个为空的行开始
			// 获得原始文档
			Workbook wb = Workbook.getWorkbook(new File("files\\database.xls"));
			// 创建一个可读写的副本
			WritableWorkbook workbook = Workbook.createWorkbook(new File("files\\database.xls"), wb);
			// 设置日期格式
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = df.format(new Date());// 获得系统当前时间
			if (workbook != null) {
				// 创建一个可写入的工作表
				WritableSheet ws = workbook.getSheet(0);
				// 下面开始写入，这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行，九个参数分九列存储，其中，两个list对应位置存储的是长度，真正的数据按顺序存入后续的列中
				ws.addCell(new Label(row, line, name));// 将生成的单元格添加到工作表中
				ws.addCell(new Label(row + 1, line, time));
				ws.addCell(new Label(row + 2, line, label));
				ws.addCell(new Label(row + 3, line, length));
				ws.addCell(new Label(row + 4, line, regex));
				ws.addCell(new Label(row + 5, line, Integer.toString(endRegexList.size())));
				ws.addCell(new Label(row + 6, line, Integer.toString(modelsList.size())));
				ws.addCell(new Label(row + 7, line, regexTFS));
				ws.addCell(new Label(row + 8, line, endRegexTFS));
				int extraRow = row + 8;// 获得存有值的最后一列列号
				// 将真正的endRegexList内容顺序存入后续的列中
				for (String string : endRegexList) {
					extraRow = extraRow + 1;
					ws.addCell(new Label(extraRow, line, string));
				}
				// 将真正的modelsList内容顺序存入后续的列中
				for (String string : modelsList) {
					extraRow = extraRow + 1;
					ws.addCell(new Label(extraRow, line, string));
				}
				// 从内存中写入文件中
				workbook.write();
				// 关闭资源，释放内存
				workbook.close();

			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 获取第一个为空的行
	 */
	public static int getFirstEmptyLine() {
		int line = 0;// 行号
		try {
			// 下面开始读数据表
			InputStream in = new FileInputStream("files\\database.xls");
			Workbook wb = Workbook.getWorkbook(in);
			Sheet sheet = wb.getSheet(0);// 获取第一个sheet
			int rowNum = sheet.getRows();// 获得总行数
			Cell cell = null;

			do {
				line = line + 1;// 从第二行开始
				if (line < rowNum) {// 行号不能超过总行数
					cell = sheet.getCell(0, line);// 寻找不为空的第一行
				} else {
					break;
				}

			} while (!cell.getContents().equals(""));// 不为空则继续循环

			// 关闭资源，释放内存
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return line;
	}

	/**
	 * 返回显示用的数组，显示名称+创建时间
	 */
	public static String[] getRules() {
		String[] rulesArr = null;
		try {
			// 下面开始读数据表
			InputStream in = new FileInputStream("files\\database.xls");
			Workbook wb = Workbook.getWorkbook(in);
			Sheet sheet = wb.getSheet(0);
			int rowNum = Utils.getFirstEmptyLine();// 总行数
			rulesArr = new String[rowNum - 1];

			for (int i = 1; i < rowNum; i++) {
				String name = sheet.getCell(0, i).getContents();// 规则名称
				String time = sheet.getCell(1, i).getContents();// 创建时间
				rulesArr[i - 1] = "规则名：" + name + "；创建时间：" + time;

			}
			// 关闭资源，释放内存
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rulesArr;
	}

	/**
	 * 根据用户选择提取特定规则
	 */
	public static Rule load(int row) {

		Rule rule = null;// 初始规则类
		try {
			// 读数据表
			InputStream in = new FileInputStream("files\\database.xls");
			Workbook wb = Workbook.getWorkbook(in);
			Sheet sheet = wb.getSheet(0);// 获取第一个sheet
			// 总行数，此处不使用sheet.getRows()，因其返回值包含空行
			int rowNum = Utils.getFirstEmptyLine();

			if (row < rowNum) {
				// 读取选中行的各个列
				String name = sheet.getCell(0, row).getContents();
				String time = sheet.getCell(1, row).getContents();
				String label = sheet.getCell(2, row).getContents();
				String length = sheet.getCell(3, row).getContents();
				String regex = sheet.getCell(4, row).getContents();

				int endRegexSize = Integer.parseInt(sheet.getCell(5, row).getContents());
				int modelsSize = Integer.parseInt(sheet.getCell(6, row).getContents());

				String regexTFS = sheet.getCell(7, row).getContents();
				String endRegexTFS = sheet.getCell(8, row).getContents();

				// 与save方法的过程恰好相反，顺位读取并填充两个集合
				int extraRow = 8;
				List<String> endRegexList = new ArrayList<String>();
				for (int j = 0; j < endRegexSize; j++) {
					extraRow = extraRow + 1;
					endRegexList.add(sheet.getCell(extraRow, row).getContents());
				}

				List<String> modelsList = new ArrayList<String>();
				for (int j = 0; j < modelsSize; j++) {
					extraRow = extraRow + 1;
					modelsList.add(sheet.getCell(extraRow, row).getContents());
				}

				// 根据读取的参数新建一个Rule类
				rule = new Rule(name, time, label, length, regex, endRegexList, modelsList, regexTFS, endRegexTFS);
			}

			// 关闭资源，释放内存
			in.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return rule;

	}

	/**
	 * 删除特定的行，实际上的逻辑是把特定行及其后面的所有行集体上移一行。 如果是最后一行，则直接清空
	 */
	public static boolean delete(int rowNum) {
		try {
			FileInputStream is = new FileInputStream("files\\database.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(is);
			HSSFSheet sheet = workbook.getSheetAt(0);// 获得第一个sheet
			int lastRowNum = sheet.getLastRowNum();// 获得最后一行的索引，为总行数减一
			if (rowNum < lastRowNum) {
				// 如果不是最后一行，则将其后面的所有行集体上移一行，选定行被覆盖
				sheet.shiftRows(rowNum + 1, lastRowNum, -1);
			} else {
				HSSFRow row = sheet.getRow(lastRowNum);
				sheet.removeRow(row);// 如果是最后一行，则直接清空
			}
			FileOutputStream os = new FileOutputStream("files\\database.xls");
			workbook.write(os);// 写回内存
			is.close();// 关闭流
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * 使用database.xls模拟数据库更新方法
	 */
	public static boolean update(int line, String name, String time, String label, String length, String regex,
			List<String> endRegexList, List<String> modelsList, String regexTFS, String endRegexTFS) {
		try {
			int row = 0;// 列号
			// 获得原始文档
			Workbook wb = Workbook.getWorkbook(new File("files\\database.xls"));
			WritableWorkbook workbook = Workbook.createWorkbook(new File("files\\database.xls"), wb); // 创建一个可读写的副本
			if (workbook != null) {
				// 创建一个可写入的工作表
				WritableSheet ws = workbook.getSheet(0);// 获得第一个sheet
				// 下面开始写入，这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行，九个参数分九列存储，其中，两个list对应位置存储的是长度，真正的数据按顺序存入后续的列中
				ws.addCell(new Label(row, line, name));// 将生成的单元格添加到工作表中
				ws.addCell(new Label(row + 1, line, time));// 创建时间仍使用原值不变
				ws.addCell(new Label(row + 2, line, label));
				ws.addCell(new Label(row + 3, line, length));
				ws.addCell(new Label(row + 4, line, regex));
				ws.addCell(new Label(row + 5, line, Integer.toString(endRegexList.size())));
				ws.addCell(new Label(row + 6, line, Integer.toString(modelsList.size())));
				ws.addCell(new Label(row + 7, line, regexTFS));
				ws.addCell(new Label(row + 8, line, endRegexTFS));
				// 这里与save方法完全一致，将两个集合的内容顺位依次存储
				int extraRow = row + 8;
				for (String string : endRegexList) {
					extraRow = extraRow + 1;
					ws.addCell(new Label(extraRow, line, string));
				}
				for (String string : modelsList) {
					extraRow = extraRow + 1;
					ws.addCell(new Label(extraRow, line, string));
				}
				// 从内存中写入文件中
				workbook.write();
				// 关闭资源，释放内存
				workbook.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
