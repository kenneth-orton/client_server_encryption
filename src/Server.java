/* Kenneth Orton
 * Sources:
 * http://stackoverflow.com/questions/8690351/updating-jtextarea-in-java
 * http://stackoverflow.com/questions/17502210/unable-to-make-the-jtextarea-scrollable
 */
import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.DatatypeConverter;

import javax.swing.JLabel;
import javax.swing.JTextArea;

public class Server extends JFrame {
	private static final long serialVersionUID = 1L;
	private static final String CAESAR = "asm08touu9pvru92pc6id41t2p";
	private static final String DES = "u7iitg0drg639bce3mc1df0p0b";
	private static final String TDES = "9dke8ulivqaunh7ov5uepupn3n";
	private static final String AES = "12kffc2vllel7vbvrdj1osu9e4";
	private static final String AES192 = "qojltkqvah99p6entadcn04ubb";
	private static final String AES256 = "p7kv85op6pef8h3ikgg6lbkt39";
	private static JTextArea txtCipher;
	private static JTextArea txtPlain;
	private JPanel contentPane;
	private JLabel lblDecryptedMessage;

	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("The Server is listening.");
		ServerSocket listenChannel = new ServerSocket(6789);
		try{
			while(true){
				new ReceiverConnection(listenChannel.accept()).start();
			}
		}finally{
			listenChannel.close();
		}
	}
	
	// set up GUI
	public Server() {
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEncryptedMessage = new JLabel("Ciphertext");
		lblEncryptedMessage.setBounds(25, 12, 155, 15);
		contentPane.add(lblEncryptedMessage);
		
		txtCipher = new JTextArea();
		txtCipher.setEditable(false);
		txtCipher.setLineWrap(false);
		JScrollPane scroll = new JScrollPane(txtCipher, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(24, 39, 460, 200);
		contentPane.add(scroll);
		
		lblDecryptedMessage = new JLabel("Plaintext:");
		lblDecryptedMessage.setBounds(25, 250, 155, 15);
		contentPane.add(lblDecryptedMessage);
		
		txtPlain = new JTextArea();
		txtPlain.setEditable(false);
		txtPlain.setLineWrap(false);
		JScrollPane scroll2 = new JScrollPane(txtPlain, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll2.setBounds(25, 270, 460, 200);
		contentPane.add(scroll2);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private static class ReceiverConnection extends Thread {
		private Socket socket;
		private String encryptedMessage = "";
		private String plaintextMessage = "";
	
		public ReceiverConnection(Socket socket){
			this.socket = socket;
		}
	
		public void run(){
			try{
				int selection = 0;
				BufferedReader dataIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				//DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());
							
				// read the incoming encrypted message line by line
				while(true){
					String input = dataIn.readLine();
					
					if(input == null){
						break;
					}else if(input.equals(CAESAR)){
						continue;
					}else if(input.equals(DES)){
						selection = 1;
						continue;
					}else if(input.equals(TDES)){
						selection = 2;
						continue;
					}else if(input.equals(AES)){
						selection = 3;
						continue;
					}else if(input.equals(AES192)){
						selection = 4;
						continue;
					}else if(input.equals(AES256)){
						selection = 5;
						continue;
					}
					cipherExecutionControl(selection, input);
				}
				txtCipher.setText(encryptedMessage);
				txtCipher.setCaretPosition(txtCipher.getDocument().getLength());
				txtPlain.setText(plaintextMessage);
				txtPlain.setCaretPosition(txtPlain.getDocument().getLength());
			}catch(IOException e){
				System.out.println(e);
			} catch (InvalidKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidKeySpecException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				// close the connection channel
				try{
					socket.close();
				}catch(IOException e){
					System.out.println(e);
				}
			}
		}
		// flow control for the selected cipher
		private void cipherExecutionControl(int selection, String message) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
			// decrypt with caesar sub
			if(selection == 0){
				String ciphertext = new String(DatatypeConverter.parseBase64Binary(message), "UTF8");
				String keyfile = "caesarkey.dat";
				encryptedMessage += ciphertext + '\n';	
				CryptographicAlgorithms cipher = new CryptographicAlgorithms();
				String plaintext = new String(DatatypeConverter.parseBase64Binary(cipher.caesarSubstitution(ciphertext, keyfile, 1)));
				plaintextMessage += plaintext + '\n';
			// decrypt with DES
			}else if(selection == 1){ 
				String keyfile = "deskey.dat";
				encryptedMessage += message + '\n';
				CryptographicAlgorithms cipher = new CryptographicAlgorithms();
				String plaintext = new String(DatatypeConverter.parseBase64Binary(cipher.DES(message, keyfile, 1)), "UTF8");
				plaintextMessage += plaintext + '\n';
			// decrypt with 3DES	
			}else if(selection == 2){
				String keyfile = "3deskey.dat";
				encryptedMessage += message + '\n';
				CryptographicAlgorithms cipher = new CryptographicAlgorithms();
				String plaintext = new String(DatatypeConverter.parseBase64Binary(cipher.TripleDES(message, keyfile, 1)), "UTF8");
				plaintextMessage += plaintext + '\n';
			// decrypt with AES
			}else if(selection == 3){
				String keyfile = "AES128key.dat";
				encryptedMessage += message + '\n';
				CryptographicAlgorithms cipher = new CryptographicAlgorithms();
				String plaintext = new String(DatatypeConverter.parseBase64Binary(cipher.AES(message, keyfile, 1)), "UTF8");
				plaintextMessage += plaintext + '\n';
			// decrypt with AES 192
			}else if(selection == 4){
				String keyfile = "AES192key.dat";
				encryptedMessage += message + '\n';
				CryptographicAlgorithms cipher = new CryptographicAlgorithms();
				String plaintext = new String(DatatypeConverter.parseBase64Binary(cipher.AES(message, keyfile, 1)), "UTF8");
				plaintextMessage += plaintext + '\n';
			// decrypt with AES 256
			}else if(selection == 5){
				String keyfile = "AES256key.dat";
				encryptedMessage += message + '\n';
				CryptographicAlgorithms cipher = new CryptographicAlgorithms();
				String plaintext = new String(DatatypeConverter.parseBase64Binary(cipher.AES(message, keyfile, 1)), "UTF8");
				plaintextMessage += plaintext + '\n';
			}
		}
	}
}


