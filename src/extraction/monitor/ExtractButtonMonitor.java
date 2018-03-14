package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import extraction.main.Main;
import extraction.window.EndRegexWindow;
import extraction.window.LoadWindow;
import extraction.window.MainWindow;
import extraction.window.MessageDialog;
import extraction.window.ModelsWindow;
import extraction.window.RegexWindow;
import extraction.window.SaveWindow;
import extraction.window.UpdateWindow;
import util.Utils;

public class ExtractButtonMonitor implements ActionListener {
	private MainWindow extractWindow;// 新建监视器时传入一个MainWindow类

	private static String label;// 希望抽取信息的统称
	private static int length;// 希望抽取的信息名称长度
	private static String regex;// 开始行正则表达式

	// 结束行正则表达式，由于有多个，用集合表示
	private static List<String> endRegexList = new ArrayList<String>();

	// 模板，所有关键字的集合
	private static List<String> modelsList = new ArrayList<String>();

	File doc;// 希望进行抽取的文本
	File excel;// 希望存放抽取结果的表格

	public static List<String> getEndRegexList() {
		return endRegexList;
	}

	public static void setEndRegexList(List<String> endRegexList) {
		ExtractButtonMonitor.endRegexList = endRegexList;
	}

	public static String getLabel() {
		return label;
	}

	public static void setLabel(String label) {
		ExtractButtonMonitor.label = label;
	}

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		ExtractButtonMonitor.length = length;
	}

	public static String getRegex() {
		return regex;
	}

	public static void setRegex(String regex) {
		ExtractButtonMonitor.regex = regex;
	}

	public static List<String> getModelsList() {
		return modelsList;
	}

	public static void setModelsList(List<String> modelsList) {
		ExtractButtonMonitor.modelsList = modelsList;
	}

	public ExtractButtonMonitor(MainWindow extractWindow) {
		this.extractWindow = extractWindow;
	}

	public void actionPerformed(ActionEvent e) {
		// 接收在窗体中定义的作用指令值
		int commandId = Integer.parseInt(e.getActionCommand());
		switch (commandId) {// 根据不同的作用指令值进行不同操作
		case 1:// 作用指令值为1时，开始抽取
			if (validate()) {// 验证各个输入值是否符合要求
				// 从传入的MainWindow对象extractWindow中获取label、length、doc和excel的值，其他的变量直接进行静态赋值，这里不再赋值
				label = extractWindow.getLabel().getText();
				length = Integer.parseInt(extractWindow.getLength().getText());
				doc = extractWindow.getDoc().getSelectedFile();
				excel = extractWindow.getExcel().getSelectedFile();

				// 调用抽取的主方法
				Main.autoExtraction(label, length, regex, endRegexList, modelsList, doc, excel);

			}

			break;
		case 2:// 开始行规则的开始按钮
				// 创立并打开一个新的RegexWindow
			RegexWindow reg = new RegexWindow(extractWindow.getRegex(), "main");
			reg.launchRegexWindow();
			break;
		case 3:// 开始行规则的重置按钮
			regex = "";// 将regex置空
			// 将主窗体的regex输入文本框文字重置
			extractWindow.getRegex().setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则");
			extractWindow.getRegex().updateUI();// 更新控件状态
			break;
		case 4:// 结束行规则的开始按钮
				// 创立并打开一个新的EndRegexWindow
			EndRegexWindow ereg = new EndRegexWindow(extractWindow.getEndRegex(), "main");
			ereg.launchEndRegexWindow();
			break;
		case 5:// 结束行规则的重置按钮
			endRegexList.clear();// 清除endRegexList的所有内容
			// 将主窗体的endRegex输入文本框文字重置
			extractWindow.getEndRegex().setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则。可以定义多个规则");
			extractWindow.getEndRegex().updateUI();
			break;
		case 6:// 模板的开始按钮
				// 创立并打开一个新的ModelsWindow
			ModelsWindow modelsWindow = new ModelsWindow(extractWindow.getModels(), "main");
			modelsWindow.launchModelsWindow();
			break;
		case 7:// 模板的重置按钮
			modelsList.clear();// 清除modelsList的所有内容
			// 将主窗体的models输入文本框文字重置
			extractWindow.getModels().setText("通过点击“开始”按钮开始添加新的模板，点击“重置”按钮来重新定义模板");
			extractWindow.getModels().updateUI();
			break;
		case 8:// 修改规则
			UpdateWindow updateWindow = new UpdateWindow();
			updateWindow.launchUpdateWindow();

			break;
		case 9:// 读取规则
			LoadWindow loadWindow = new LoadWindow(extractWindow.getLabel(), extractWindow.getLength(),
					extractWindow.getRegex(), extractWindow.getEndRegex(), extractWindow.getModels());
			loadWindow.launchLoadWindow();

			break;
		case 10:// 存储规则
			if (validateRule()) {
				label = extractWindow.getLabel().getText();
				length = Integer.parseInt(extractWindow.getLength().getText());
				SaveWindow saveWindow = new SaveWindow(label, Integer.toString(length), regex, endRegexList, modelsList,
						extractWindow.getRegex().getText(), extractWindow.getEndRegex().getText());
				saveWindow.launchSaveWindow();
			}
			break;
		case 11:// 退出
			System.exit(0);

			break;
		}
	}

	public boolean validate() {
		// 从extractWindow对象中获取用户从窗体输入的值
		String label = extractWindow.getLabel().getText();
		String length = extractWindow.getLength().getText();
		String regex = ExtractButtonMonitor.regex;
		File doc = extractWindow.getDoc().getSelectedFile();
		File excel = extractWindow.getExcel().getSelectedFile();

		if (label.equals("")) {// 验证label不为空
			new MessageDialog(true, "提示", "请输入希望抽取信息的统称", extractWindow);
			return false;
		} else if (length.equals("")) {// 验证length不为空
			new MessageDialog(true, "提示", "请输入希望抽取信息名长度", extractWindow);
			return false;
		} else if (regex.equals("")) {// 验证regex不为空
			new MessageDialog(true, "提示", "请自定义开始行的规则", extractWindow);
			return false;
		} else if (endRegexList.size() == 0) {// 验证endRegexList不为空
			new MessageDialog(true, "提示", "请自定义结束行的规则", extractWindow);
			return false;
		} else if (modelsList.size() == 0) {// 验证modelsList不为空
			new MessageDialog(true, "提示", "请自定义模板", extractWindow);
			return false;
		} else if (doc == null) {// 验证doc不为空
			new MessageDialog(true, "提示", "请选择需要进行抽取的文本", extractWindow);
			return false;
		} else if (!Utils.getExtension(doc).equals(".docx")) {// 规定文本必须为.docx格式
			new MessageDialog(true, "提示", "文本必须为.docx格式", extractWindow);
			return false;
		} else if (excel == null) {// 验证excel不为空
			new MessageDialog(true, "提示", "请选择用来存放抽取结果的表格", extractWindow);
			return false;
		} else if (!Utils.getExtension(excel).equals(".xls")) {// 规定表格必须为.xls格式
			new MessageDialog(true, "提示", "表格必须为.xls格式", extractWindow);
			return false;
		}
		return true;

	}

	public boolean validateRule() {
		String label = extractWindow.getLabel().getText();
		String length = extractWindow.getLength().getText();
		String regex = ExtractButtonMonitor.regex;

		if (label.equals("")) {
			new MessageDialog(true, "提示", "请输入希望抽取信息的统称", extractWindow);
			return false;
		} else if (length.equals("")) {
			new MessageDialog(true, "提示", "请输入希望抽取信息名长度", extractWindow);
			return false;
		} else if (regex.equals("")) {
			new MessageDialog(true, "提示", "请自定义开始行的规则", extractWindow);
			return false;
		} else if (endRegexList.size() == 0) {
			new MessageDialog(true, "提示", "请自定义结束行的规则", extractWindow);
			return false;
		} else if (modelsList.size() == 0) {
			new MessageDialog(true, "提示", "请自定义模板", extractWindow);
			return false;
		}

		return true;

	}

}