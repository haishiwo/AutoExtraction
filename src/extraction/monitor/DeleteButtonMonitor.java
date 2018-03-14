package extraction.monitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import extraction.window.DeleteWindow;
import extraction.window.MessageDialog;
import util.Utils;

public class DeleteButtonMonitor implements ActionListener {
	private DeleteWindow deleteWindow;
	int rowNum;

	public DeleteButtonMonitor(DeleteWindow deleteWindow) {
		this.deleteWindow = deleteWindow;
	}

	public void actionPerformed(ActionEvent e) {
		int commandId = Integer.parseInt(e.getActionCommand());// 获得作用指令值
		switch (commandId) {
		case 1:// 值为1代表“确认删除”按钮
			rowNum = deleteWindow.getRowNum();// 获得从窗体传来的行号
			if (Utils.delete(rowNum + 1)) {// 实现删除方法
				new MessageDialog(true, "提示", "删除成功", deleteWindow);
				// 在对数据库中的数据进行删除操作后，对规则下拉列表的项也进行相应的删除操作
				deleteWindow.getRuleBox().removeItemAt(rowNum);
				deleteWindow.getRuleBox().updateUI();
			} else {
				new MessageDialog(true, "提示", "删除失败", deleteWindow);
			}

			deleteWindow.dispose();

			break;
		case 2:// 取消
				// 关闭当前窗体
			deleteWindow.dispose();

			break;
		}
	}

}