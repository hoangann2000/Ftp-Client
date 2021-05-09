package ftp;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GuiMain extends JFrame implements ActionListener, MouseListener {
	
public static void main(String[] args) {
	GuiMain g = new GuiMain(); 
	g.giaoDien();
		new Thread() {
			public void run() {
				while (true) {
					try {
						DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
						// tạo 1 đối tượng có định dạng thời gian dd-mm-yyyy HH:mm:ss
						Date date = new Date(); // Lấy thời gian hệ thống
						String stringDate = dateFormat.format(date);// Định dạng thời gian theo trên
						GuiMain.lbThoiGian.setText(stringDate);
						sleep(1000);
					} catch (InterruptedException e) {
					}
				}
			};
		}.start();
		
	}

public GuiMain() {
//	GuiMain g = new GuiMain(); 
//	g.giaoDien();
}
	private JTextField username, port, hostName;
	private static JTextField path;
	private JPasswordField password;
	private JButton btnConnect, btnDisconnect, btnDownload, btnUpload, btnDeleteFile, btnAddFolde, btnRefresh,
			btnRename, btnBack;
	private static JTable tableShow;
	private JScrollPane jsc, jSc;
	private JLabel lbPath, lbHostName, lbUsername, lbPassword, lbPort;
	private static JLabel lbThoiGian;
	private JSeparator jse;
	private JPopupMenu menu;
	private JPanel pnHeader, pnLeft, pnServerReplay, pnTable;
	private static JTextArea statusConnect = new JTextArea();
	private JFrame f;
	static boolean checkConnect;
	private String link = null;
	private String getSizeFile = "";
	public boolean kiemTraFolder = false;

	public static ControllerFtpClientsPI ftpClient = new ControllerFtpClientsPI();
	private static ControllerFtpClientTranfer clientTranfer = new ControllerFtpClientTranfer(ftpClient);
	static GuiDowloadFile dow = new GuiDowloadFile();
	static GuiUploadFile up = new GuiUploadFile();
	static GuiNewFolder newFolder = new GuiNewFolder();
	static GuiRenameFile renameFile = new GuiRenameFile();

	/*-------------------------------------Giao dien---------------------------------------*/
	public void giaoDien(){
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception evt) {
	    	evt.printStackTrace();
	    }
		f = new JFrame();
		f.getContentPane().setLayout(null);
		f.setTitle("Hoàng Lê Thiện An");
		//f.setContentPane(new JLabel(new ImageIcon("./image/4291.jpg")));
		pnHeader = new JPanel();
		pnHeader.setBounds(50, 0, 715, 50);
		pnHeader.setBackground(SystemColor.activeCaption);
		pnHeader.setLayout(null);
		pnLeft = new JPanel();
		pnLeft.setLayout(null);
		pnLeft.setBounds(0, 0, 50, 600);
		pnLeft.setBackground(new Color(0,18,50));
		pnServerReplay = new JPanel();
		pnServerReplay.setLayout(null);
		pnServerReplay.setBounds(50, 50, 715, 200);
		pnServerReplay.setBackground(new Color(204,204,204));
		pnTable = new JPanel();
		pnTable.setLayout(null);
		pnTable.setBounds(50, 250, 715, 350);
		pnTable.setBackground(new Color(32, 47, 90));
		lbHostName =  new JLabel("Ftp");
		lbHostName.setBounds(10, 15, 35, 25);
		lbHostName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbHostName.setForeground(new Color(47,79,79));
		
		hostName = new JTextField("127.0.0.1");
		hostName.setForeground(new Color(47,79,79));
		hostName.setFont(new java.awt.Font("Century Gothic", 0, 12));
		hostName.setBounds(43,16, 101, 25);
		hostName.setBackground(Color.WHITE);
		
		lbUsername =  new JLabel("Name");
		lbUsername.setBounds(156, 15, 57, 25);
		lbUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbUsername.setForeground(new Color(47,79,79));
		
		username = new JTextField("");
		username.setBounds(207,16, 120, 25);
		username.setFont(new java.awt.Font("Century Gothic", 0, 12));
		username.setForeground(new Color(47,79,79));
		username.setBackground(Color.WHITE);
		
		lbPassword =  new JLabel("Password");
		lbPassword.setBounds(339, 15, 73, 25);
		lbPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbPassword.setForeground(new Color(47,79,79));
		
		password = new JPasswordField("");
		password.setBounds(412,16, 101, 25);
		password.setFont(new java.awt.Font("Century Gothic", 0, 12));
		password.setForeground(new Color(47,79,79));
		password.setBackground(Color.WHITE);
		
		lbPort =  new JLabel("Port\r\n");
		lbPort.setBounds(525, 15, 45, 25);
		lbPort.setFont(new Font("Tahoma", Font.BOLD, 14));
		lbPort.setForeground(new Color(47,79,79));
		
		port = new JTextField("21");
		port.setBounds(580,15, 50, 25);
		port.setForeground(new Color(47,79,79));
		port.setFont(new java.awt.Font("Century Gothic", 0, 12));
		port.setBackground(Color.WHITE);
		
		jse = new JSeparator();
		jse.setOrientation(javax.swing.SwingConstants.VERTICAL);
		jse.setBounds(650, 0, 5, 50);
		
		/*--------------------------------Button Connection------------------------------------*/
		btnConnect = new JButton(new ImageIcon("./image/network.png"));
		btnConnect.setBounds(660,3, 45, 45);
		
		/*--------------------------------Button Disconnect------------------------------------*/
		btnDisconnect = new JButton(new ImageIcon("./image/gtk-disconnect.png"));
		btnDisconnect.setBounds(660, 3, 45, 45);
		
		/*--------------------------------Button Download------------------------------------*/
		btnDownload = new JButton(new ImageIcon("./image/download02.png"));
		btnDownload.setPressedIcon(new ImageIcon("./image/download01.png"));
		btnDownload.setRolloverIcon(new ImageIcon("./image/download01.png"));
		btnDownload.setBounds(0, 160, 50, 50);
		btnDownload.setBorderPainted(false); 
		btnDownload.setContentAreaFilled(false);
		/*--------------------------------Button Upload------------------------------------*/
		btnUpload = new JButton(new ImageIcon("./image/upload02.png"));
		btnUpload.setPressedIcon(new ImageIcon("./image/upload01.png"));
		btnUpload.setRolloverIcon(new ImageIcon("./image/upload01.png"));
		btnUpload.setBounds(0, 210, 50, 50);
		btnUpload.setBorderPainted(false); 
		btnUpload.setContentAreaFilled(false);
		
		/*--------------------------------Button AddFolder------------------------------------*/
		btnAddFolde = new JButton(new ImageIcon("./image/add02.png"));
		btnAddFolde.setPressedIcon(new ImageIcon("./image/add01.png"));
		btnAddFolde.setRolloverIcon(new ImageIcon("./image/add01.png"));
		btnAddFolde.setBounds(0, 60, 50, 50);
		btnAddFolde.setBorderPainted(false); 
		btnAddFolde.setContentAreaFilled(false);
		
		/*--------------------------------Button Delete------------------------------------*/
		btnDeleteFile = new JButton(new ImageIcon("./image/delete02.png"));
		btnDeleteFile.setPressedIcon(new ImageIcon("./image/detele01.png"));
		btnDeleteFile.setRolloverIcon(new ImageIcon("./image/detele01.png"));
		btnDeleteFile.setBounds(0, 110, 50, 50);
		btnDeleteFile.setBorderPainted(false); 
		btnDeleteFile.setContentAreaFilled(false);
		
		/*--------------------------------Button Refresh------------------------------------*/
		btnRename = new JButton(new ImageIcon("./image/remane02.png"));
		btnRename.setPressedIcon(new ImageIcon("./image/remane01.png"));
		btnRename.setRolloverIcon(new ImageIcon("./image/remane01.png"));
		btnRename.setBounds(0, 260, 50, 50);
		btnRename.setBorderPainted(false);
		btnRename.setContentAreaFilled(false);
		
		/*--------------------------------Button Refresh------------------------------------*/
		btnRefresh = new JButton(new ImageIcon("./image/refresh01.png"));
		btnRefresh.setPressedIcon(new ImageIcon("./image/refresh02.png"));
		btnRefresh.setRolloverIcon(new ImageIcon("./image/refresh02.png"));
		btnRefresh.setBounds(0, 310, 50, 50);
		btnRefresh.setBorderPainted(false);
		btnRefresh.setContentAreaFilled(false);
		
		/*--------------------------------Button Back------------------------------------*/
		btnBack = new JButton(new ImageIcon("./image/left-arrow01.png"));
		btnBack.setPressedIcon(new ImageIcon("./image/left-arrow.png"));
		btnBack.setRolloverIcon(new ImageIcon("./image/left-arrow.png"));
		btnBack.setBounds(5, 5, 16, 16);
		btnBack.setBorderPainted(false);
		btnBack.setContentAreaFilled(false);
		
		
		
		/*--------------------------------Panel add Item-----------------------------------*/
		pnHeader.add(lbHostName);
		pnHeader.add(hostName);
		pnHeader.add(lbUsername);
		pnHeader.add(username);
		pnHeader.add(lbPassword);
		pnHeader.add(password);
		pnHeader.add(lbPort);
		pnHeader.add(port);
		pnHeader.add(jse);
		pnHeader.add(btnConnect);
		
		pnHeader.add(btnDisconnect);
		pnLeft.add(btnRename);
		pnLeft.add(btnRefresh);
		pnLeft.add(btnDownload);
		pnLeft.add(btnAddFolde);
		pnLeft.add(btnUpload);
		pnLeft.add(btnDeleteFile);
		
		/*--------------------------------Status------------------------------------*/
		jSc =  new JScrollPane();
		jSc.setBounds(5, 13, 705, 182);
		jSc.setBackground(Color.WHITE);
		jSc.setBorder(javax.swing.BorderFactory.createTitledBorder("SERVER REPLY"));
		jSc.setViewportView(statusConnect);
		statusConnect.setEditable(false);
		statusConnect.setBackground(new Color(36,47,65));
		statusConnect.setForeground(new Color(204, 204, 204));
		statusConnect.setFont(new java.awt.Font("Consolas", 0, 12));
		
		/*--------------------------------Path------------------------------------*/
        lbPath = new JLabel("PATH: ");
        lbPath.setBounds(5, 300, 50, 20);
        lbPath.setFont(new java.awt.Font("Century Gothic", 0, 12));
        lbPath.setForeground(new java.awt.Color(204, 204, 204));
        
        path = new JTextField();
        path.setBounds(50,300,260,20);
        path.setFont(new java.awt.Font("Consolar", 0, 12));
        path.setEditable(false);
        path.setForeground(new Color(204, 204, 204));
        path.setBackground(new Color(32, 47, 90));
		
		/*--------------------------------Table------------------------------------*/
		tableShow = new JTable(){
			// Set icon cho table
			 public Class getColumnClass(int column){
	                return getValueAt(0, column).getClass();
	            }
		};
		tableShow.setRowHeight(35);
		tableShow.setFont(new Font("Consoles", 0, 12));
		tableShow.setShowVerticalLines(false);
		tableShow.setModel(new DefaultTableModel( new Object [][] {},new String [] {"Icon","Tên file","SLTM","Kích thước (byte)","Ngày"}){
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		});
		
		tableShow.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableShow.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableShow.getColumnModel().getColumn(1).setPreferredWidth(350);
		tableShow.getColumnModel().getColumn(2).setPreferredWidth(80);
		tableShow.getColumnModel().getColumn(3).setPreferredWidth(100);
		tableShow.getColumnModel().getColumn(4).setPreferredWidth(120);
	    jsc =  new JScrollPane();
	    jsc.setBackground(Color.WHITE);
	    jsc.setBorder(javax.swing.BorderFactory.createTitledBorder("FILE HOST"));
	    jsc.setForeground(new Color(204, 204, 204));
		jsc.setBounds(5, 25, 705, 270);
		jsc.setViewportView(tableShow);
		
		
		/*----------------------------------------------------------------------------------------------*/
		
		JMenuItem add = new JMenuItem("Thêm thư mục");
		add.setIcon(new ImageIcon("./image/add.png"));
		JMenuItem delete = new JMenuItem("Delete");
		delete.setIcon(new ImageIcon("./image/dustbin.png"));
		JMenuItem download = new JMenuItem("Tải xuống");
		download.setIcon(new ImageIcon("./image/download.png"));
		JMenuItem upload = new JMenuItem("Tải lên");
		upload.setIcon(new ImageIcon("./image/upload.png"));
		JMenuItem refresh = new JMenuItem("Refresh");
		refresh.setIcon(new ImageIcon("./image/loading.png"));
		JMenuItem rename = new JMenuItem("Rename");
		rename.setIcon(new ImageIcon("./image/rename.png"));
		menu = new JPopupMenu();
		menu.add(upload);
		menu.add(download);
		menu.add(new JSeparator());
		menu.add(refresh);
		menu.add(add);
		menu.add(delete);
		menu.add(rename);
		/*-----------------------------------------------------------------------------------------------*/
		lbThoiGian = new JLabel();
		lbThoiGian.setBounds(580, 300, 200, 20);
		lbThoiGian.setFont(new java.awt.Font("Century Gothic", 0, 13));
		lbThoiGian.setForeground(new Color(204, 204, 204));
		/*-----------------------------------------------------------------------------------------------*/
		JPanel pnRight = new JPanel();
		pnRight.setBounds(765, 0, 5, 600);
		pnRight.setBackground(new Color(0,18,50));
		
		JLabel lbLogo = new JLabel(new ImageIcon("./image/vku.png"));
		lbLogo.setBounds(0, 0, 50, 50);
		JLabel lbHinhNen = new JLabel(new ImageIcon("./image/An.jpg"));
		lbHinhNen.setBounds(0, 0, 715,350);
//		JLabel lbHinhNen1 = new JLabel(new ImageIcon("./image/connect2.png"));
//		lbHinhNen1.setBackground(SystemColor.activeCaption);
//		lbHinhNen1.setBounds(0, 0, 715,200);
			
		pnLeft.add(lbLogo);
		pnTable.add(lbThoiGian);
		pnServerReplay.add(jSc);
//		pnServerReplay.add(lbHinhNen1);
		pnTable.add(btnBack);
		pnTable.add(jsc);
		pnTable.add(path);
		pnTable.add(lbPath);
		pnTable.add(lbHinhNen);
		
		f.getContentPane().add(pnRight);
		f.getContentPane().add(pnLeft);
		f.getContentPane().add(pnHeader);
		f.getContentPane().add(pnTable);
		f.getContentPane().add(pnServerReplay);
		
		f.setSize(778,620);
		f.setVisible(true);
		
		btnConnect.addActionListener(this);
		btnDisconnect.addActionListener(this);
		tableShow.addMouseListener(this);
		btnDownload.addActionListener(this);
		btnAddFolde.addActionListener(this);
		btnUpload.addActionListener(this);
		btnDeleteFile.addActionListener(this);
		btnRefresh.addActionListener(this);
		btnRename.addActionListener(this);
		btnBack.addActionListener(this);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				newFolder.giaoDien();
				// tạo mới folder thì truyền path
				newFolder.setPathNewFolder(path.getText());
				if(kiemTraFolder){
					newFolder.setPathNewFolder(link);
				}
			}
		});
		delete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n= JOptionPane.showConfirmDialog(delete,"Bạn có chắc chắn muốn xóa file này?","Xóa file",JOptionPane.YES_NO_OPTION);
				deleteFileOrFolder(n);
			}
		});
		refresh.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				deleteAllRow();
				showDataWithTable(path.getText());
			}
		});
		download.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				dow.giaoDien();
			}
		});
		// Chua cap nhap lai bang
		upload.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				up.giaoDien();
			}
		});
		rename.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				renameFile.giaoDien();
				renameFile.setPathRenameFile(link);
			}
		});
		setVisableDisconnection();
	}

	public void setVisableConnection() {
		username.setEnabled(false);
		port.setEnabled(false);
		hostName.setEnabled(false);
		password.setEnabled(false);
		btnConnect.setVisible(false);

		path.setVisible(true);
		lbPath.setVisible(true);
		jsc.setVisible(true);
		btnDisconnect.setVisible(true);
		btnDownload.setVisible(true);
		btnAddFolde.setVisible(true);
		btnUpload.setVisible(true);
		btnDeleteFile.setVisible(true);
		btnRefresh.setVisible(true);
		btnRename.setVisible(true);
		btnBack.setVisible(true);
	}

	public void setVisableDisconnection() {
		;
		username.setEnabled(true);
		port.setEnabled(true);
		hostName.setEnabled(true);
		password.setEnabled(true);
		btnConnect.setVisible(true);

		path.setVisible(false);
		lbPath.setVisible(false);
		jsc.setVisible(false);
		btnDisconnect.setVisible(false);
		btnDownload.setVisible(false);
		btnAddFolde.setVisible(false);
		btnUpload.setVisible(false);
		btnDeleteFile.setVisible(false);
		btnRefresh.setVisible(false);
		btnRename.setVisible(false);
		btnBack.setVisible(false);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		/* --------------------------- Connect ------------------------------ */
		if (e.getSource() == btnConnect) {
			try {
				statusConnect.setText(null);
				connectHost();
			} catch (NumberFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/* --------------------------- Disconnect ------------------------------ */
		if (e.getSource() == btnDisconnect) {
			try {
				disconnectHost();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		/* --------------------------- Download ------------------------------ */
		if (e.getSource() == btnDownload) {
			dow.giaoDien();
		}

		/* --------------------------- Upload ------------------------------ */
		// Chua cap nhap lai bang
		if (e.getSource() == btnUpload) {
			up.giaoDien();
		}
		/* --------------------------- Refresh ------------------------------ */
		if (e.getSource() == btnRefresh) {
			deleteAllRow();
			showDataWithTable(path.getText());
		}
		/* --------------------------- Delete ------------------------------ */
		if (e.getSource() == btnDeleteFile) {
			int n = JOptionPane.showConfirmDialog(btnDeleteFile, "Bạn có chắc chắn muốn xóa file này ?",
					"Xóa file", JOptionPane.YES_NO_OPTION);
			deleteFileOrFolder(n);
		}
		/* --------------------------- New Folder ------------------------------ */
		if (e.getSource() == btnAddFolde) {
			newFolder.giaoDien();
			newFolder.setPathNewFolder(path.getText());
			if (kiemTraFolder) {
				newFolder.setPathNewFolder(link);
			}
		}
		if (e.getSource() == btnRename) {
			renameFile.giaoDien();
			renameFile.setPathRenameFile(link);
		}

		if (e.getSource() == btnBack) {
			link = path.getText();
			int n = link.lastIndexOf('/');
			link = link.substring(0, n);
			if (link.equals("")) {
				link = "/";
			}
			path.setText(link);
			deleteAllRow();
			showDataWithTable(path.getText());

		}
		//Check đăng nhập
		
		if (checkConnect) {
			btnConnect.setEnabled(false);
			btnDisconnect.setEnabled(true);
			setVisableConnection();
		} else {
			btnConnect.setEnabled(true);
			btnDisconnect.setEnabled(false);
			setVisableDisconnection();
		}
	}

	// connect host
	public void connectHost() throws NumberFormatException, UnknownHostException, IOException {
		if (ftpClient.connect(hostName.getText().toString().trim(), Integer.parseInt(port.getText().toString().trim()),
				username.getText().toString().trim(), password.getText().toString().trim())) {
			checkConnect = true;
			link = ftpClient.getCurrentDirectory();
			path.setText(link);
			System.out.println(link);
			showDataWithTable(link);
		} else {
			setStatusConnect("Đăng nhập thất bại!");
			checkConnect = false;
		}

	}

	//Disconnect
	public void disconnectHost() throws IOException {
		if (ftpClient.disconnect()) {
			statusConnect.setText("Đã ngắt kết nối");
			DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
			deleteAllRow();
			checkConnect = false;
		} else {
			statusConnect.setText("Không thể ngắt kết nối!");
			checkConnect = true;
		}
	}

	// Delete File hoặc Folder
	private void deleteFileOrFolder(int n) {
		String[] arrayPathFilesDele = getArrayPathInJTable();
		if (n == 0) {
			for (int i = 0; i < arrayPathFilesDele.length; i++) {
				if (kiemTraFolder) {
					if (ftpClient.deleteFolder(arrayPathFilesDele[i])) {
						deleteAllRow();
						/* int a =path.getText().lastIndexOf('/'); */
						showDataWithTable(path.getText());
					} else {
						JOptionPane.showMessageDialog(f, "Không thể xóa file", "Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					if (ftpClient.deleteFile(arrayPathFilesDele[i])) {
						deleteAllRow();
						showDataWithTable(path.getText());
					} else {
						JOptionPane.showMessageDialog(f, "không thể xóa file", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		}
	}

	//lấy giá trị của nhiều hàng được chọn trong JTable
	public String[] getArrayPathInJTable() {
		String[] arrayPath = null;
		DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
		int[] selectRowIndex = tableShow.getSelectedRows();
		arrayPath = new String[selectRowIndex.length];
		for (int i = 0; i < selectRowIndex.length; i++) {
			arrayPath[i] = path.getText() + "/" + model.getValueAt(selectRowIndex[i], 1).toString();
		}
		return arrayPath;
	}

	//Xóa dữ liệu trong Table
	public static void deleteAllRow() {
		DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
		for (int i = model.getRowCount() - 1; i >= 0; i--) {
			model.removeRow(i);
		}
	}

	//Show dữ liệu ra Tablie
	public static void showDataWithTable(String link) {
		DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
		// Truyền link đường dẫn vào để lấy danh sách các thư mục trong Host
		ArrayList<FileHost> arrList = clientTranfer.listFile(link);
		Object rowData[] = new Object[5];
		for (int i = 0; i < arrList.size(); i++) {
			rowData[0] = arrList.get(i).getIcon();
			rowData[1] = arrList.get(i).getFileName();
			rowData[2] = arrList.get(i).getSLTM();
			rowData[3] = arrList.get(i).getFileSize();
			rowData[4] = arrList.get(i).getDate();
			model.addRow(rowData);
		}
	}

	

	//Sự kiện click vào table
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		//Double click
		if (e.getClickCount() == 2) {
			getLink(1);
		}
		//Click chuot phai
		if (SwingUtilities.isRightMouseButton(e)) {
			menu.show(e.getComponent(), e.getX(), e.getY());
			getLink(0);
		}
	}

	//Get Link khi double click or click chuột phải
	public void getLink(int i) {
		DefaultTableModel model = (DefaultTableModel) tableShow.getModel();
		int selectRowIndex = tableShow.getSelectedRow();
		if (!model.getValueAt(selectRowIndex, 3).toString().equals("")) {
			link = path.getText() + "/" + model.getValueAt(selectRowIndex, 1).toString();
			kiemTraFolder = false;
			getSizeFile = model.getValueAt(selectRowIndex, 3).toString();
		} else {
			kiemTraFolder = true;
			link = path.getText() + "/" + model.getValueAt(selectRowIndex, 1).toString();
			String cut[] = link.split("/");
			if (cut[cut.length - 1].equals("..")) {
				link = "";
				for (int j = 0; j < cut.length - 2; j++) {
					link += "/" + cut[j];
				}
			} else if (cut[cut.length - 1].equals(".")) {
				link = "/";
			}
			link = link.replace("//", "/");
//			 If i == 1 thì với cập nhập lại bảng tức là Double click thì với cập nhập lại table. Tức là click vào thư mục
			if (i == 1) {
				path.setText(link);
				link = path.getText();
				deleteAllRow();
				showDataWithTable(link);
				tableShow.repaint();
			}

		}
		// truyền đường dẫn và size cho lớp dow
		dow.setSize(getSizeFile);
		dow.setURL(link);

		// Truyền luôn đường dẫn và kích thước file cho lớp Up
		up.setURL(path.getText());
		if (kiemTraFolder) {
			up.setURL(link);
		}
		newFolder.setPathNewFolder(path.getText());
		if (kiemTraFolder) {
			newFolder.setPathNewFolder(link);
		}
		// Tạo mới folder thì truyền link
		renameFile.setPathRenameFile(link);
		System.out.println(link);
		System.out.println(path.getText());
	}

	//Cập nhập trạng thái trong Text Area
	public static void setStatusConnect(String status) {
		statusConnect.append(status + "\n");
	}

	// Truyền cho lớp Dowload.java... để dowloand 1 file
	public static void dowloadFile(FileDowUp fileDowUp) {
		clientTranfer.dowloadFile(fileDowUp);
	}

	// Truyền cho lớp Upload.java... để uploand 1 file
	public static void uploadFile(FileDowUp fileDowUp) {
		clientTranfer.uploadFile(fileDowUp);
	}

	// Truyền cho lớp GuiNewFolder.java... để tạo 1 folder mới
	public static boolean makeFolder(String name) {
		return ftpClient.makeFolder(name);

	}

	// Truyền cho lớp GuiNewFolder.java... để tạo 1 folder mới
	public static boolean renameFile(String oldName, String newName) {
		return ftpClient.renameFile(oldName, newName);

	}

	// Update lai path sau khi update file
	public static void updatePath(String paths) {
		path.setText(paths);
	}

	public static void setTienTrinhDow(int i) {
		dow.setTienTrinhDow(i);
	}

	public static void setValueProcessUp(int i) {
		up.setValueProcessUp(i);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
