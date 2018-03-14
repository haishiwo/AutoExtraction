package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import bean.Rule;
import extraction.window.DeleteWindow;
import extraction.window.EndRegexWindow;
import extraction.window.MessageDialog;
import extraction.window.ModelsWindow;
import extraction.window.RegexWindow;
import extraction.window.UpdateWindow;
import util.Utils;

public class UpdateButtonMonitor implements ActionListener {
	private UpdateWindow updateWindow;

	private int timesEndRegex = 0;// endRegex执行次数
	private int timesModels = 0;// models执行次数

	private static String regex;

	private static List<String> endRegexList = new ArrayList<String>();

	private static List<String> modelsList = new ArrayList<String>();

	public static String getRegex() {
		return regex;
	}

	public static void setRegex(String regex) {
		UpdateButtonMonitor.regex = regex;
	}

	public static List<String> getEndRegexList() {
		return endRegexList;
	}

	public static void setEndRegexList(List<String> endRegexList) {
		UpdateButtonMonitor.endRegexList = endRegexList;
	}

	public static List<String> getModelsList() {
		return modelsList;
	}

	public static void setModelsList(List<String> modelsList) {
		UpdateButtonMonitor.modelsList = modelsList;
	}

	public UpdateButtonMonitor(UpdateWindow updateWindow) {
		this.updateWindow = updateWindow;
	}

	public void actionPerformed(ActionEvent e) {
		int index = updateWindow.getRuleBox().getSelectedIndex();// 规则下拉列表被选中项的索引
		Rule rule = Utils.load(index + 1);// 读取对应行规则

		if (e.getSource() == updateWindow.getRuleBox()) {// 如果事件源是RuleBox
			// 将从数据库读出规则的各个项分别在窗体对应文本框中显示出来
			updateWindow.getRuleName().setText(rule.getName());
			updateWindow.getLabel().setText(rule.getLabel());
			updateWindow.getLength().setText(rule.getLength());
			updateWindow.getRegex().setText(rule.getRegexTFS());
			updateWindow.getEndRegex().setText(rule.getEndRegexTFS());
			// 同样的道理，模板在加和之后显示出来
			String models = "";
			for (String str : rule.getModelsList()) {
				models += str + "。";// 每个关键字之间用“。”分割
			}
			models = models.substring(0, models.length() - 1);// 去掉最后一位（多余的句号）
			updateWindow.getModels().setText(models);
			// 给变量赋值
			UpdateButtonMonitor.setRegex(rule.getRegex());
			UpdateButtonMonitor.setEndRegexList(rule.getEndRegexList());
			UpdateButtonMonitor.setModelsList(rule.getModelsList());
			// 更新控件
			updateWindow.getRuleName().updateUI();
			updateWindow.getLabel().updateUI();
			updateWindow.getLength().updateUI();
			updateWindow.getRegex().updateUI();
			updateWindow.getEndRegex().updateUI();
			updateWindow.getModels().updateUI();

		} else {
			int commandId = Integer.parseInt(e.getActionCommand());// 获得作用指令值
			switch (commandId) {
			case 1:// 值为1代表“修改”按钮
					// 使用MessageDialog进行提示
				new MessageDialog(true, "提示", "请选择一个规则并在以下对应的文本框中修改相应项，通过点击“保存”按钮提交所做的修改", updateWindow);
				break;
			case 2:// 值为2代表“删除”按钮
					// 新建并打开一个DeleteWindow
				DeleteWindow deleteWindow = new DeleteWindow(index, updateWindow.getRuleBox());
				deleteWindow.launchDeleteWindow();
				break;
			case 3:// 开始行规则的开始按钮
					// 创立并打开一个新的RegexWindow
				RegexWindow reg = new RegexWindow(updateWindow.getRegex(), "update");
				reg.launchRegexWindow();
				break;

			case 4:// 重置
				regex = "";
				updateWindow.getRegex().setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则");
				updateWindow.getRegex().updateUI();

				break;
			case 5:// 开始endRegex
				if (timesEndRegex == 0) {
					updateWindow.getEndRegex().setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则。可以定义多个规则");
					updateWindow.getEndRegex().updateUI();
					timesEndRegex = timesEndRegex + 1;
				}
				EndRegexWindow ereg = new EndRegexWindow(updateWindow.getEndRegex(), "update");
				ereg.launchEndRegexWindow();

				break;

			case 6:// 重置
				endRegexList.clear();
				updateWindow.getEndRegex().setText("通过点击“开始”按钮开始添加新的规则，点击“重置”按钮来重新定义规则。可以定义多个规则");
				updateWindow.getEndRegex().updateUI();

				break;
			case 7:// 开始models
				if (timesModels == 0) {
					updateWindow.getModels().setText("通过点击“开始”按钮开始添加新的模板，点击“重置”按钮来重新定义模板");
					updateWindow.getModels().updateUI();
					timesModels = timesModels + 1;
				}

				ModelsWindow modelsWindow = new ModelsWindow(updateWindow.getModels(), "update");
				modelsWindow.launchModelsWindow();

				break;
			case 8:// 重置
				modelsList.clear();
				updateWindow.getModels().setText("通过点击“开始”按钮开始添加新的模板，点击“重置”按钮来重新定义模板");
				updateWindow.getModels().updateUI();

				break;
			case 9:// 保存按钮事件
				if (validateRule()) {// 非空验证
					// 从窗体获取各个参数值
					String name = updateWindow.getRuleName().getText();
					String label = updateWindow.getLabel().getText();
					String length = updateWindow.getLength().getText();
					String regex = UpdateButtonMonitor.regex;
					List<String> endRegexList = UpdateButtonMonitor.endRegexList;
					List<String> modelsList = UpdateButtonMonitor.modelsList;
					String regexTFS = updateWindow.getRegex().getText();
					String endRegexTFS = updateWindow.getEndRegex().getText();
					// 使用新的参数进行更新操作
					if (Utils.update(index + 1, name, rule.getTime(), label, length, regex, endRegexList, modelsList,
							regexTFS, endRegexTFS)) {
						new MessageDialog(true, "提示", "修改成功！", updateWindow);
						// 在成功进行数据库数据的更新操作之后，对规则下拉列表的项也进行更新
						updateWindow.getRuleBox().removeItemAt(index);
						updateWindow.getRuleBox().insertItemAt(Utils.getRules()[index], index);
						updateWindow.getRuleBox().updateUI();
					} else {
						new MessageDialog(true, "提示", "修改失败", updateWindow);
					}
				}
				timesEndRegex = 0;
				timesModels = 0;
				break;
			case 10:// 取消
				// 关闭当前窗体
				updateWindow.dispose();

				break;
			}
		}

	}

	public boolean validateRule() {
		String label = updateWindow.getLabel().getText();
		String length = updateWindow.getLength().getText();
		String regex = UpdateButtonMonitor.regex;

		if (label.equals("")) {
			new MessageDialog(true, "提示", "请输入希望抽取信息的统称", updateWindow);
			return false;
		} else if (length.equals("")) {
			new MessageDialog(true, "提示", "请输入希望抽取信息名长度", updateWindow);
			return false;
		} else if (regex.equals("")) {
			new MessageDialog(true, "提示", "请自定义开始行的规则", updateWindow);
			return false;
		} else if (endRegexList.size() == 0) {
			new MessageDialog(true, "提示", "请自定义结束行的规则", updateWindow);
			return false;
		} else if (modelsList.size() == 0) {
			new MessageDialog(true, "提示", "请自定义模板", updateWindow);
			return false;
		}

		return true;

	}

}