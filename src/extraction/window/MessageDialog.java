package extraction.window;

import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MessageDialog extends JDialog {

	private JLabel messageLabel;

	public MessageDialog(boolean isModel, String title, String message, JFrame frame) {
		this.setLayout(new FlowLayout());
		this.setSize(220, 30);
		this.setTitle(title);
		this.setModal(isModel);// 设置窗体是否是模式的
		// 根据传入的信息对messageLabel进行初始化
		this.messageLabel = new JLabel(message);
		this.add(messageLabel);// 将messageLabel添加入窗体
		// 窗体默认关闭方式为关闭窗口
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(frame);
		this.pack();// 将窗体设置为自动调整大小的
		this.setResizable(false);
		this.setIconImage(new ImageIcon(MainWindow.class.getResource("talk2.jpg")).getImage());
		this.setVisible(true);
	}

}
