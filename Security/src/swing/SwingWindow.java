package swing;

import security.My_AES;
import security.My_DES;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class SwingWindow {

	private JTextArea writeText;
	private JTextArea showText;
	private JTextField keyCrypt;
	public JTextField textfield=new JTextField(20);
	private JComboBox<String> methods = new JComboBox<>(new MyComboBox());


	public static void main(String[] args) {
		SwingWindow gui = new SwingWindow();
		gui.start();
	}

	public void start() {
		JFrame frame = new JFrame("文件加解密");
		JPanel leftPanel = new JPanel();
		JPanel centerPanel = new JPanel();
		JPanel rightPanel = new JPanel();

		JLabel methLabelLabel = new JLabel("<html><body><p align=\"center\">请选择加密/解密方式：<br><br></p></body></html>");
		JLabel signkey2 = new JLabel("<html><body>密钥：<br><br></p></body></html>");
		JLabel dirtext = new JLabel("<html><body>文件路径：<br><br></p></body></html>");

		//按钮设计
		JButton btn_Files = new JButton("打开文件");
		JButton btn_Encrypt = new JButton("加密");
		JButton btn_Decrypt = new JButton("解密");
		
		//按钮监听
		btn_Encrypt.addActionListener(new EncryptListener());
		btn_Decrypt.addActionListener(new DecryptListener());
		btn_Files.addActionListener(new open_Files());

		//文本区域
		writeText = new JTextArea(20,20);
		showText = new JTextArea(20,20);
		writeText.setLineWrap(true);
		showText.setLineWrap(true);
		keyCrypt = new JTextField(1);
		keyCrypt.setText("");
		textfield=new JTextField(12);

		//面板设置
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(methLabelLabel);
		centerPanel.add(methods);
		centerPanel.add(signkey2);
		centerPanel.add(keyCrypt);
		centerPanel.add(dirtext);
		centerPanel.add(textfield);
		centerPanel.add(btn_Files);
		centerPanel.add(btn_Encrypt);
		centerPanel.add(btn_Decrypt);
		
		//窗体
		frame.getContentPane().add(BorderLayout.WEST, leftPanel);
		frame.getContentPane().add(BorderLayout.EAST, rightPanel);
		frame.getContentPane().add(BorderLayout.CENTER, centerPanel);
		frame.setSize(300, 300);
		frame.setVisible(true);
	}

	class MyComboBox extends AbstractListModel<String> implements ComboBoxModel<String>{
		String selecteditem = "--请选择--";
		String[] methodString = {"DES", "AES"};

		public String getElementAt(int index) {		//根据索引返回值
			return methodString[index];
		}
		public int getSize() {					//返回下拉列表框中项目的数目
			return methodString.length;			
		}
		public Object getSelectedItem() {		//获取下拉列表框中的项目
			return selecteditem;
		}
		public void setSelectedItem(Object anItem) {	//设置下拉列表框中的项目
			selecteditem = (String)anItem;
		}
	}

	public void writeEncodeFiles(String out){
		File f=new File("C:\\Users\\lyl66\\OneDrive\\Desktop\\test\\encode text.txt");
		FileOutputStream fos= null;
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		OutputStreamWriter osw=new OutputStreamWriter(fos);
		BufferedWriter bw=new BufferedWriter(osw);
		try {
			bw.write(out);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		try {
			bw.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void writeDecodeFiles(String out){
		File f=new File("C:\\Users\\lyl66\\OneDrive\\Desktop\\test\\decode text.txt");
		FileOutputStream fos= null;
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
		OutputStreamWriter osw=new OutputStreamWriter(fos);
		BufferedWriter bw=new BufferedWriter(osw);
		try {
			bw.write(out);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
		try {
			bw.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}


	class open_Files extends Component implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser("C:\\Users\\lyl66\\OneDrive\\Desktop\\test");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			//获取选择的结果
			int ret = fc.showOpenDialog(this);
			File dir = null;
			String filepath = null;
			if (ret == JFileChooser.APPROVE_OPTION) {
				//结果为：已经存在的一个目录
				dir = fc.getSelectedFile();
				filepath = dir.getAbsolutePath();
				textfield.setText(dir.getAbsolutePath());
			}
			String message;
			try {
				message = Files.readString(Path.of(filepath));
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
			writeText.setText(message);
		}
	}

	//加密 按钮监听
	class EncryptListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String methodItem;
			methodItem = (String) methods.getSelectedItem();
			if(methodItem == "--请选择--") methodItem = "???";
			if (methodItem.equals("DES")) {
				String in = writeText.getText();
				String key = keyCrypt.getText();
				int length = key.length();
				if(length == 8){
					String out = security.My_DES.encryptDES(in, key);
					showText.setText(null);
					showText.append(out);
					writeEncodeFiles(out);
					JOptionPane.showMessageDialog(null, "加密成功", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "DES密钥只支持8位 ", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}else if (methodItem.equals("AES")) {
				String in = writeText.getText();
				String key = keyCrypt.getText();
				int length = key.length();
				if(length == 16){
					String out = security.My_AES.encryptAES(in, key);
					showText.setText(null);
					showText.append(out);
					writeEncodeFiles(out);
					JOptionPane.showMessageDialog(null, "加密成功", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "AES密钥只支持16位 ", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				showText.setText(null);
				JOptionPane.showMessageDialog(null, "不支持此加密方式 ", "ERROR", JOptionPane.ERROR_MESSAGE);
			}

		}
	}
	
	//解密 按钮监听
	class DecryptListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String methodItem;
			methodItem = (String) methods.getSelectedItem();
			if(methodItem == null) methodItem = "???";
			if (methodItem.equals("DES")) {
				String in = writeText.getText();
				String key = keyCrypt.getText();
				int length = key.length();
				if(length == 8){
					String out = My_DES.dectyptDES(in, key);
					showText.setText(null);
					showText.append(out);
					writeDecodeFiles(out);
					JOptionPane.showMessageDialog(null, "解密成功", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "DES密钥只支持8位 ", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}else if (methodItem.equals("AES")) {
				String in = writeText.getText();
				String key = keyCrypt.getText();
				int length = key.length();
				if(length == 16){
					String out = My_AES.dectyptAES(in, key);
					showText.setText(null);
					showText.append(out);
					writeDecodeFiles(out);
					JOptionPane.showMessageDialog(null, "解密成功", "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(null, "AES密钥只支持16位 ", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				showText.setText(null);
				JOptionPane.showMessageDialog(null, "不支持此加密方式 ", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
