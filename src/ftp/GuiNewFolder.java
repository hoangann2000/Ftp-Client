package ftp;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class GuiNewFolder extends JFrame implements ActionListener{
	public GuiNewFolder() {
//		GuiNewFolder n = new GuiNewFolder();
//		n.giaoDien();
	}
	private JTextField txtPath = new JTextField();
	private JTextField txtFolderName;
	private JButton btnOk,btnHuy;
	private JLabel lbThongBao;
	JFrame f;
	private String link;
	public void giaoDien(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		f = new JFrame("Tạo folder");
		JLabel lbPath = new JLabel("Path:");
		lbPath.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbPath.setBounds(20, 20, 50, 20);
		JLabel lbFolderName = new JLabel("Folder name:");
		lbFolderName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbFolderName.setBounds(20, 50, 100, 20);
		
		txtPath = new JTextField();
		txtPath.setBounds(132, 17, 200, 20);
		txtFolderName = new JTextField();
		txtFolderName.setBounds(132, 50, 200, 20);
		
		btnOk = new JButton("OK");
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnOk.setBounds(132, 97, 94, 37);
		btnOk.setIcon(new ImageIcon("./image/new.png"));
		
		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuy.setBounds(260, 97, 105, 37);
		btnHuy.setIcon(new ImageIcon("./image/cancel.png"));
		lbThongBao = new JLabel();
		lbThongBao.setBounds(20, 130, 200, 20);
		
		f.getContentPane().add(lbThongBao);
		f.getContentPane().add(btnHuy);
		f.getContentPane().add(btnOk);
		f.getContentPane().add(txtFolderName);
		f.getContentPane().add(txtPath);
		f.getContentPane().add(lbPath);
		f.getContentPane().add(lbFolderName);
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(SystemColor.activeCaption);
		f.setVisible(true);
		f.setSize(418, 194);
		
		btnHuy.addActionListener(this);
		btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOk){
			link = txtPath.getText();
			if(txtFolderName.getText().indexOf(' ') == -1){
				if(link.length()==1){
					link = "/"+txtFolderName.getText();
				}
				else{
					link = txtPath.getText() +"/"+txtFolderName.getText();
				}
				if(GuiMain.makeFolder(txtPath.getText() +"/"+txtFolderName.getText())){
					lbThongBao.setText("Tạo mới thành công!");
					GuiMain.deleteAllRow();
					GuiMain.showDataWithTable(txtPath.getText());
				}
				else{
					JOptionPane.showMessageDialog(f,"Không thể tạo mới thư mục! \n Đường dẫn chưa chính xác","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(f,"Không thể tạo mới thư mục! \nTên thuc mục không được chưa khoảng trắng","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource()==btnHuy){
			f.setVisible(false);
			f.dispose();
		}
	}
	public void setPathNewFolder(String path){
		txtPath.setText(path);
	}
}
