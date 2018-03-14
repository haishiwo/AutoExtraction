package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import bean.Rule;
import extraction.window.LoadWindow;
import extraction.window.MessageDialog;
import util.Utils;

public class LoadButtonMonitor implements ActionListener {
	private LoadWindow loadWindow;

	public LoadButtonMonitor(LoadWindow loadWindow) {
		this.loadWindow = loadWindow;
	}

	public void actionPerformed(ActionEvent e) {
		int commandId = Integer.parseInt(e.getActionCommand());// 获得作用指令值
		switch (commandId) {
		case 1:// 值为1代表“载入规则”按钮
				// 根据RuleBox被选中项的索引获得对应的Rule类
			Rule rule = Utils.load(loadWindow.getRuleBox().getSelectedIndex() + 1);
			// 将规则的各个参数对应赋给主窗体监视器
			ExtractButtonMonitor.setLabel(rule.getLabel());
			ExtractButtonMonitor.setLength(Integer.parseInt(rule.getLength()));
			ExtractButtonMonitor.setRegex(rule.getRegex());
			ExtractButtonMonitor.setEndRegexList(rule.getEndRegexList());
			ExtractButtonMonitor.setModelsList(rule.getModelsList());

			new MessageDialog(true, "提示", "载入成功！", loadWindow);

			// 对主窗体上对应的文本框文本进行更新
			loadWindow.getLabel().setText(rule.getLabel());
			loadWindow.getLength().setText(rule.getLength());
			loadWindow.getRegex().setText(rule.getRegexTFS());
			loadWindow.getEndRegex().setText(rule.getEndRegexTFS());

			// 模板是一个集合，需要把所有项相加后显示在文本框中
			String models = "";
			for (String str : rule.getModelsList()) {
				models += str + "。";

			}
			models = models.substring(0, models.length() - 1);// 去掉最后一位（多余的句号）
			loadWindow.getModels().setText(models);

			// 更新各个控件
			loadWindow.getLabel().updateUI();
			loadWindow.getLength().updateUI();
			loadWindow.getRegex().updateUI();
			loadWindow.getEndRegex().updateUI();
			loadWindow.getModels().updateUI();

			loadWindow.dispose();

			break;
		case 2:// 值为2代表“取消”按钮
			loadWindow.dispose();

			break;
		}
	}

}