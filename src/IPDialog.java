/* Kenneth Orton
 * ACO431 Project1
 * 
 * Sources:
 * http://stackoverflow.com/questions/11093326/restricting-jtextfield-input-to-integers
 * http://stackoverflow.com/questions/9922543/why-does-inetaddress-isreachable-return-false-when-i-can-ping-the-ip-address
 * http://stackoverflow.com/questions/11316778/action-listener-on-a-radio-button
 */
//import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.text.NumberFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.NumberFormat;
import java.text.ParseException;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.JFormattedTextField;

public class IPDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	private static JFormattedTextField ftxtOct1;
	private static JFormattedTextField ftxtOct2;
	private static JFormattedTextField ftxtOct3;
	private static JFormattedTextField ftxtOct4;
	private static String ipAddr;
	private static final int PORT = 6789;
	private static final int TIMEOUT = 10000;
	
	public IPDialog(Frame parent) throws ParseException {
		super(parent, "Connect", true);
		setTitle("Connection");
		setResizable(false);
		setBounds(100, 100, 242, 160);
		getContentPane().setLayout(null);
		setLocationRelativeTo(parent);
				
		JLabel lblIPAddress = new JLabel("IP Address:");
		lblIPAddress.setBounds(12, 32, 89, 15);
		getContentPane().add(lblIPAddress);
		
		//formats IP address input
		NumberFormat nf = NumberFormat.getIntegerInstance();
		NumberFormatter numFormatter = new NumberFormatter(nf);
		numFormatter.setValueClass(Integer.class);
		numFormatter.setAllowsInvalid(false);
		numFormatter.setMinimum(0);
		numFormatter.setMaximum(255);
		
		ftxtOct1 = new JFormattedTextField(numFormatter);
		ftxtOct1.setBounds(96, 32, 33, 19);
		getContentPane().add(ftxtOct1);
		
		ftxtOct2 = new JFormattedTextField(numFormatter);
		ftxtOct2.setBounds(130, 32, 33, 19);
		getContentPane().add(ftxtOct2);

		ftxtOct3 = new JFormattedTextField(numFormatter);
		ftxtOct3.setBounds(163, 32, 33, 19);
		getContentPane().add(ftxtOct3);
		
		ftxtOct4 = new JFormattedTextField(numFormatter);
		ftxtOct4.setBounds(196, 32, 33, 19);
		getContentPane().add(ftxtOct4);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ipAddr = octetToString(ftxtOct1.getText(), ftxtOct2.getText(), ftxtOct3.getText(), ftxtOct4.getText());
				if(isReachable(ipAddr, PORT, TIMEOUT)){
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(IPDialog.this, "Host Unreachable, Check IP Address", "Error", JOptionPane.ERROR_MESSAGE);
                    // reset fields
                    ftxtOct1.setValue(null);
                	ftxtOct2.setValue(null);
                	ftxtOct3.setValue(null);
                	ftxtOct4.setValue(null);
                	ftxtOct1.requestFocus();
                }
			}
		});
		btnSubmit.setBounds(145, 89, 83, 25);
		getContentPane().add(btnSubmit);
	}
	
	private static String octetToString(String oct1, String oct2, String oct3, String oct4){
		return oct1 + "." + oct2 + "." + oct3 + "." + oct4;
	}
	
	private static boolean isReachable(String addr, int openPort, int timeOutMillis) {
	    // Any Open port on other machine
	    // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
	    try {
	        try (Socket soc = new Socket()) {
	            soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
	        }
	        return true;
	    } catch (IOException ex) {
	    	ipAddr = null;
	        return false;
	    }
	}
	
	public static String getIPAddress(){
		return ipAddr;
	}
}
