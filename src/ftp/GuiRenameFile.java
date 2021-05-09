package ftp;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.SystemColor;

public class GuiRenameFile extends JFrame implements ActionListener{
	public GuiRenameFile() {
//		GuiRenameFile r = new GuiRenameFile();
//		r.giaoDien();
	}
	private JTextField txtOldName = new JTextField();
	private JTextField txtNewName;
	private JButton btnOk,btnHuy;
	private JLabel lbThongBao;
	private String oldPath,newPath,link;
	private JFrame f;
	public void giaoDien(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		f = new JFrame("Rename");
		JLabel lbOldName = new JLabel("Old name:");
		lbOldName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbOldName.setBounds(20, 20, 76, 20);
		JLabel lbNewName = new JLabel("New name:");
		lbNewName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbNewName.setBounds(20, 53, 100, 20);
		
		txtOldName = new JTextField();
		txtOldName.setBounds(124, 20, 200, 20);
		txtOldName.enable(true);
		txtNewName = new JTextField();
		txtNewName.setBounds(124, 53, 200, 20);
		
		btnOk = new JButton("OK");
		btnOk.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnOk.setBounds(98, 97, 86, 36);
		btnOk.setIcon(new ImageIcon("./image/ok.png"));
		btnHuy = new JButton("Hủy");
		btnHuy.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnHuy.setBounds(232, 97, 100, 36);
		btnHuy.setIcon(new ImageIcon("./image/cancel.png"));
		lbThongBao = new JLabel();
		lbThongBao.setBounds(20, 130, 200, 20);
		
		f.getContentPane().add(lbThongBao);
		f.getContentPane().add(btnHuy);
		f.getContentPane().add(btnOk);
		f.getContentPane().add(txtNewName);
		f.getContentPane().add(txtOldName);
		f.getContentPane().add(lbOldName);
		f.getContentPane().add(lbNewName);
		f.getContentPane().setLayout(null);
		f.getContentPane().setBackground(SystemColor.activeCaption);
		f.setVisible(true);
		f.setSize(413, 193);
		
		btnHuy.addActionListener(this);
		btnOk.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == btnOk){
			newPath = link+"/"+txtNewName.getText();
			if(GuiMain.renameFile(oldPath, newPath)){
				lbThongBao.setText("Đổi tên thành công!");
				GuiMain.deleteAllRow();
				GuiMain.showDataWithTable(link);
			}
			else{
				JOptionPane.showMessageDialog(f,"Đổi tên thất bại!","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(e.getSource()==btnHuy){
			f.setVisible(false);
            f.dispose();
		}
	}
	public void setPathRenameFile(String path){
		oldPath = path;
		int n = path.lastIndexOf('/');
		link = path.substring(0,n);
		path = path.substring(n+1, path.length());
		txtOldName.setText(path);
	}
}
