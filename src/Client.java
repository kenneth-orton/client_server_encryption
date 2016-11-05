/* Kenneth Orton
 * ACO431 Project 1
 * 
 * Sources:
 * https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
 * http://docs.oracle.com/javase/6/docs/api/javax/swing/JFileChooser.html
 * http://www.programcreek.com/2011/03/java-read-a-file-line-by-line-code-example/
 */
import java.awt.EventQueue;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class Client extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String CAESAR = "asm08touu9pvru92pc6id41t2p";
	private static final String DES = "u7iitg0drg639bce3mc1df0p0b";
	private static final String TDES = "9dke8ulivqaunh7ov5uepupn3n";
	private static final String AES = "12kffc2vllel7vbvrdj1osu9e4";
	private static final String AES192 = "qojltkqvah99p6entadcn04ubb";
	private static final String AES256 = "p7kv85op6pef8h3ikgg6lbkt39";
	private JPanel contentPane;
	private File inputFile;
	final static JFrame frame = new JFrame();
	private JTextField txtBrowser;
	
	public static void main(String[] args) throws ParseException {
		IPDialog ipDialog = new IPDialog(frame);
		ipDialog.setVisible(true);
								
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(IPDialog.getIPAddress() == null){
						System.exit(0);
					}
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Client() throws IOException {
		setTitle("Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 410);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		JLabel lblConnectedTo = new JLabel();
		lblConnectedTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnectedTo.setBounds(141, 12, 160, 15);
		lblConnectedTo.setText("Connected to: " + IPDialog.getIPAddress());
		lblConnectedTo.setSize(lblConnectedTo.getPreferredSize());
		
		JRadioButton rdbtnCaesar = new JRadioButton("Caesar Cipher");
		rdbtnCaesar.setBounds(66, 69, 149, 23);
		rdbtnCaesar.setSelected(true);
		
		JRadioButton rdbtnDES = new JRadioButton("DES");
		rdbtnDES.setBounds(66, 96, 146, 23);
		
		JRadioButton rdbtn3DES = new JRadioButton("3-DES");
		rdbtn3DES.setBounds(66, 123, 149, 23);
		
		JRadioButton rdbtnAES128 = new JRadioButton("AES 128");
		rdbtnAES128.setBounds(245, 69, 149, 23);
		
		JRadioButton rdbtnAES192 = new JRadioButton("AES 192");
		rdbtnAES192.setBounds(245, 96, 149, 23);
		
		JRadioButton rdbtnAES256 = new JRadioButton("AES 256");
		rdbtnAES256.setBounds(245, 123, 149, 23);
		
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rdbtnAES256);
		btnGroup.add(rdbtnAES192);
		btnGroup.add(rdbtnAES128);
		btnGroup.add(rdbtnCaesar);
		btnGroup.add(rdbtnDES);
		btnGroup.add(rdbtn3DES);
		
		JLabel lblChooseEncryptionAlgorithm = new JLabel("Choose Encryption Algorithm:");
		lblChooseEncryptionAlgorithm.setHorizontalAlignment(SwingConstants.LEFT);
		lblChooseEncryptionAlgorithm.setBounds(12, 46, 243, 15);
		contentPane.add(lblChooseEncryptionAlgorithm);
		contentPane.add(rdbtnCaesar);
		contentPane.add(rdbtnAES128);
		contentPane.add(rdbtnDES);
		contentPane.add(rdbtnAES192);
		contentPane.add(rdbtn3DES);
		contentPane.add(rdbtnAES256);
		contentPane.add(lblConnectedTo);
				
		JLabel lblSelectFileTo = new JLabel("Select File to Encrypt:");
		lblSelectFileTo.setBounds(12, 165, 161, 15);
		contentPane.add(lblSelectFileTo);
		
		txtBrowser = new JTextField();
		txtBrowser.setEditable(false);
		txtBrowser.setBounds(12, 192, 375, 19);
		contentPane.add(txtBrowser);
		txtBrowser.setColumns(10);
		
		JTextArea txtMessage = new JTextArea();
		txtMessage.setLineWrap(true);
		JScrollPane scroll = new JScrollPane(txtMessage, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scroll);
		scroll.setBounds(12, 265, 409, 74);
			
		JButton btnBrowse = new JButton("...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser browser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT Files", "txt");
				browser.setFileFilter(filter);
				int val = browser.showOpenDialog(Client.this);
				if(val == JFileChooser.APPROVE_OPTION){
					inputFile = browser.getSelectedFile();
					txtBrowser.setText(inputFile.getAbsolutePath());
				}
			}
		});
		btnBrowse.setBounds(393, 192, 28, 19);
		contentPane.add(btnBrowse);
		
		JButton btnSendFile = new JButton("Send");
		btnSendFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!txtBrowser.getText().isEmpty()){
					// switch case for radio button selection
					if(rdbtnCaesar.isSelected()){
						try {
							Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
							BufferedReader message = new BufferedReader(new FileReader(txtBrowser.getText()));
							encryptCaesar(message, socket);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}	
					}else if(rdbtnDES.isSelected()){
						try {
							Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
							BufferedReader message = new BufferedReader(new FileReader(txtBrowser.getText()));
							encryptDES(message, socket);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InvalidKeyException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalBlockSizeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BadPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}else if(rdbtn3DES.isSelected()){
						try {
							Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
							BufferedReader message = new BufferedReader(new FileReader(txtBrowser.getText()));
							encryptTripleDES(message, socket);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InvalidKeyException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalBlockSizeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BadPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}else if(rdbtnAES128.isSelected()){
						try {
							Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
							BufferedReader message = new BufferedReader(new FileReader(txtBrowser.getText()));
							encryptAES128(message, socket);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InvalidKeyException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalBlockSizeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BadPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}					
					}else if(rdbtnAES192.isSelected()){
						try {
							Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
							BufferedReader message = new BufferedReader(new FileReader(txtBrowser.getText()));
							encryptAES192(message, socket);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InvalidKeyException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalBlockSizeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BadPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}else if(rdbtnAES256.isSelected()){
						try {
							Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
							BufferedReader message = new BufferedReader(new FileReader(txtBrowser.getText()));
							encryptAES256(message, socket);
						} catch (UnknownHostException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (InvalidKeyException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchAlgorithmException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (InvalidKeySpecException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (NoSuchPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IllegalBlockSizeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (BadPaddingException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}	
					}
				}else{
					String message = "Please select a plaintext input file.";
					JOptionPane.showMessageDialog(new JFrame(), message, "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnSendFile.setBounds(351, 218, 70, 20);
		contentPane.add(btnSendFile);
				
		JLabel lblWriteMessage = new JLabel("Write Message:");
		lblWriteMessage.setBounds(12, 238, 161, 15);
		contentPane.add(lblWriteMessage);
		
		JButton btnSendMsg = new JButton("Send");
		btnSendMsg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnCaesar.isSelected()){
					try {
						Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
						BufferedReader message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(txtMessage.getText().getBytes())));
						encryptCaesar(message, socket);
					} catch (UnknownHostException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}	
				}else if(rdbtnDES.isSelected()){
					try {
						Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
						BufferedReader message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(txtMessage.getText().getBytes())));
					    encryptDES(message, socket);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalBlockSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(rdbtn3DES.isSelected()){
					try {
						Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
						BufferedReader message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(txtMessage.getText().getBytes())));
					    encryptTripleDES(message, socket);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalBlockSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(rdbtnAES128.isSelected()){
					try {
						Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
						BufferedReader message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(txtMessage.getText().getBytes())));
					    encryptAES128(message, socket);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalBlockSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				}else if(rdbtnAES192.isSelected()){
					try {
						Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
						BufferedReader message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(txtMessage.getText().getBytes())));
					    encryptAES192(message, socket);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalBlockSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(rdbtnAES256.isSelected()){
					try {
						Socket socket = new Socket(IPDialog.getIPAddress(), 6789);
						BufferedReader message = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(txtMessage.getText().getBytes())));
					    encryptAES256(message, socket);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeyException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvalidKeySpecException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalBlockSizeException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (BadPaddingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					};
				}
			}
		});
		btnSendMsg.setBounds(351, 345, 70, 20);
		contentPane.add(btnSendMsg);
							
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void encryptCaesar(BufferedReader message, Socket socket) throws IOException{
		//URL keyfile = this.getClass().getResource("caesarkey.dat");
		CryptographicAlgorithms cipher = new CryptographicAlgorithms(); 
		DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
		String keyfile = "caesarkey.dat";
		//BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
		// tell the server what cipher you are using
		outData.writeBytes(CAESAR + "\n");
				
		String line = null;
		while ((line = message.readLine()) != null) {
			outData.writeBytes(cipher.caesarSubstitution(line, keyfile, 0) + '\n');
			//inData.readLine();
		}
		message.close();
		socket.close();
	}
	
	public void encryptDES(BufferedReader message, Socket socket) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		CryptographicAlgorithms cipher = new CryptographicAlgorithms(); 
		String key = "deskey.dat";
		DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
		//BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// tell the server what cipher you are using
		outData.writeBytes(DES + "\n");
				
		String line = null;
		while ((line = message.readLine()) != null) {
			outData.writeBytes(cipher.DES(line, key, 0) + '\n');
			//inData.readLine();
		}
		message.close();
		socket.close();
	}
	
	public void encryptTripleDES(BufferedReader message, Socket socket) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		CryptographicAlgorithms cipher = new CryptographicAlgorithms(); 
		String key = "3deskey.dat";
		DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
		//BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// tell the server what cipher you are using
		outData.writeBytes(TDES + "\n");
				
		String line = null;
		while ((line = message.readLine()) != null) {
			outData.writeBytes(cipher.TripleDES(line, key, 0) + '\n');
			//inData.readLine();
		}
		message.close();
		socket.close();
	}
	
	public void encryptAES128(BufferedReader message, Socket socket) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		CryptographicAlgorithms cipher = new CryptographicAlgorithms(); 
		String key = "AES128key.dat";
		DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
		//BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// tell the server what cipher you are using
		outData.writeBytes(AES + "\n");
				
		String line = null;
		while ((line = message.readLine()) != null) {
			outData.writeBytes(cipher.AES(line, key, 0) + '\n');
			//inData.readLine();
		}
		message.close();
		socket.close();
	}
	
	public void encryptAES192(BufferedReader message, Socket socket) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		CryptographicAlgorithms cipher = new CryptographicAlgorithms(); 
		String key = "AES192key.dat";
		DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
		//BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// tell the server what cipher you are using
		outData.writeBytes(AES192 + "\n");
				
		String line = null;
		while ((line = message.readLine()) != null) {
			outData.writeBytes(cipher.AES(line, key, 0) + '\n');
			//inData.readLine();
		}
		message.close();
		socket.close();
	}
	
	public void encryptAES256(BufferedReader message, Socket socket) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		CryptographicAlgorithms cipher = new CryptographicAlgorithms(); 
		String key = "AES256key.dat";
		DataOutputStream outData = new DataOutputStream(socket.getOutputStream());
		//BufferedReader inData = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// tell the server what cipher you are using
		outData.writeBytes(AES256 + "\n");
				
		String line = null;
		while ((line = message.readLine()) != null) {
			outData.writeBytes(cipher.AES(line, key, 0) + '\n');
			//inData.readLine();
		}
		message.close();
		socket.close();
	}
}
