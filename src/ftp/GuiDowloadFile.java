package ftp;
import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.awt.SystemColor;
import java.awt.Font;

class GuiDowloadFile extends JFrame implements ActionListener{
	public GuiDowloadFile() {
//		GuiDowloadFile d = new GuiDowloadFile(); 
//		d.giaoDien();
	}
	private JTextField txtPathHost =  new JTextField();
	private	JTextField txtBrower;
	private JFileChooser fileChooserComputer;
	private JButton btnDowload,btnBrower,btnCancel;
	private JProgressBar tienTrinh = new JProgressBar(0,100); 
	private JLabel lbThongBao;
	private JFrame f;
	private String URL = ""; 
	private String pathh = null;
	private  JTextField txtSize = new JTextField();
	public void giaoDien(){
		f = new JFrame("Tải xuống");
		f.getContentPane().setLayout(null);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		JLabel lbUrl = new JLabel("URL:");
		lbUrl.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbUrl.setBounds(70,10, 80, 25);
		txtPathHost.setBounds(110, 10, 350, 25);
		
		btnDowload = new JButton("Tải xuống");
		btnDowload.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnDowload.setIcon(new ImageIcon("./image/dowloadfile.png"));
		btnDowload.setBounds(210, 100, 129, 30);
		
		JLabel lbPath= new JLabel("Đường dẫn lưu:");
		lbPath.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbPath.setBounds(12,45, 100, 25);
		txtBrower = new JTextField();
		txtBrower.setBounds(110, 45, 310, 25);
		txtBrower.setEditable(false);
		btnBrower = new JButton("...");
		btnBrower.setBounds(430,45, 30, 25);
		
		btnCancel = new JButton("Hủy bỏ");
		btnCancel.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCancel.setBounds(355, 100, 115, 30);
		btnCancel.setIcon(new ImageIcon("./image/cancel.png"));
		
		lbThongBao = new JLabel();
		lbThongBao.setBounds(200,80,300, 20);
		lbThongBao.setForeground(new Color(178,34,34));
		
		tienTrinh.setBounds(20, 135, 440, 5);
		tienTrinh.setVisible(false);
		tienTrinh.setValue(0);
		tienTrinh.setMaximum(100);
		tienTrinh.setForeground(new Color(52, 168, 83));
		
		JLabel lbSize = new JLabel("Size:");
		lbSize.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbSize.setBounds(70, 80, 80, 20);
		txtSize.setBounds(110, 80, 80, 20);
		txtSize.setEditable(false);
		
		f.getContentPane().add(txtSize);
		f.getContentPane().add(lbSize);
		f.getContentPane().add(tienTrinh);
		f.getContentPane().add(lbThongBao);
		f.getContentPane().add(btnCancel);
		f.getContentPane().add(lbPath);
		f.getContentPane().add(txtBrower);
		f.getContentPane().add(btnBrower);
		f.getContentPane().add(btnDowload);
		f.getContentPane().add(lbUrl);
		f.getContentPane().add(txtPathHost);
		
		btnBrower.addActionListener(this);
		btnCancel.addActionListener(this);
		btnDowload.addActionListener(this);
		f.getContentPane().setBackground(SystemColor.activeCaption);
		f.setSize(500, 180);
		f.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		lbThongBao.setText(null);
		if(e.getSource() == btnBrower){
			fileChooserComputer = new JFileChooser();
			
			//Thiết lập JFileChooser để cho phép người dùng: chỉ lựa chọn file or chỉ thư mục hoặc cả hai
			fileChooserComputer.setFileSelectionMode(1); 
	        if (fileChooserComputer.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
	          txtBrower.setText(fileChooserComputer.getSelectedFile().getAbsolutePath());
	        }
		}
		if(e.getSource() == btnCancel){
			f.setVisible(false);
			f.dispose();
		}
		if(e.getSource()==btnDowload){
			if(txtPathHost.getText().equals("")){
				lbThongBao.setText("Đường dẫn tải file còn trống!");
			}
			else if(txtBrower.getText().equals("")){
				lbThongBao.setText("Bạn chưa chọn đường dẫn để lưu file!");
			}
			else{
					if(GuiMain.checkConnect){
		        		//Cài đặt giá trị ban đầu của JProcegarbar = 0;
		        		// Xử lý đường dẫn khi lưu
						new Thread(){
					        public void run() {
					        	tienTrinh.setValue(0);
					        	URL = txtPathHost.getText();
								String m[] = URL.split("/");
								pathh = txtBrower.getText();
								pathh = pathh.replace("\\", "/");
								pathh +="/"+m[m.length-1];
								System.out.println(pathh);
								System.out.println(txtPathHost.getText());
								System.out.println(URL);
								tienTrinh.setVisible(true);
								GuiMain.dowloadFile(new FileDowUp(URL, pathh, Long.valueOf(txtSize.getText())));
								if(tienTrinh.getValue()==100){
									lbThongBao.setForeground(new Color(58,95,205));
									lbThongBao.setText("Tải xuống hoàn tất!");
									// Sau khi tải xong cần gán pathh = null, Do pathh sử dụng đã += nên có thể dẫn đến đường dẫn bị sai
									pathh = null;
								}
								
					        }
					      }.start();
					}
					else{
						lbThongBao.setText("Bạn chưa đăng nhập!");
					}
			}
		}
	}
	public void setURL(String url){
		txtPathHost.setText(url);
	}
	public void setSize(String size){
		txtSize.setText(size);
	}
	public void setTienTrinhDow(int i){
		tienTrinh.setValue(i);
	}
}
