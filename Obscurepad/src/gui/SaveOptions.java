package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cipherTypes.CipherType;
import cipherTypes.Plaintext;
import pd.FileIO;
import pd.SHA256;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SaveOptions extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField textField;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		try {
//			SaveOptions dialog = new SaveOptions();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public SaveOptions(JFrame parentFrame, ArrayList<CipherType> cipherTypes, ArrayList<String> cipherModes, String plainText) {
		
		JDialog currentFrame = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parentFrame.setEnabled(true);
			}
		});
		setResizable(false);
		setBounds(100, 100, 396, 196);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		
		
		
		JComboBox<CipherType> comboBox = new JComboBox<CipherType>();
		comboBox.setBounds(109, 11, 135, 22);
		
		for (CipherType c : cipherTypes) {
			comboBox.addItem(c);
		}
		contentPanel.add(comboBox);
		
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(109, 44, 135, 22);
		for (String s : cipherModes) {
			comboBox_1.addItem(s);
		}
		contentPanel.add(comboBox_1);
		
		
		
		
		
		textField = new JPasswordField();
		textField.setBounds(109, 77, 135, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		
		
		
		
		
		JLabel lblEncryptionType = new JLabel("Encryption Type:");
		lblEncryptionType.setBounds(10, 15, 89, 14);
		contentPanel.add(lblEncryptionType);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 80, 89, 14);
		contentPanel.add(lblPassword);
		
		JCheckBox chckbxCachePasswordFor = new JCheckBox("Cache password for this session");
		chckbxCachePasswordFor.setEnabled(false);
		chckbxCachePasswordFor.setBounds(6, 105, 238, 23);
		contentPanel.add(chckbxCachePasswordFor);
		
		JLabel lblStrength = new JLabel("Strength:");
		lblStrength.setBounds(254, 80, 112, 14);
		contentPanel.add(lblStrength);
		
		JLabel lblEncryptionMode = new JLabel("Encryption Mode:");
		lblEncryptionMode.setBounds(10, 47, 103, 14);
		contentPanel.add(lblEncryptionMode);
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser f = new JFileChooser();
						currentFrame.setVisible(false);
						int returnValue = f.showSaveDialog(parentFrame);
						
						if (returnValue == JFileChooser.APPROVE_OPTION) {
							File selectedFile = f.getSelectedFile();
							System.out.println(selectedFile.getAbsolutePath());
							currentFrame.dispose();
							parentFrame.setEnabled(true);
							parentFrame.toFront();
							
							
							if (!comboBox.getSelectedItem().getClass().equals(Plaintext.class)) {
								//generate new iv, key
								byte[] key = SHA256.hash(new String(textField.getPassword()));
								
								// Generate the IV
								SecureRandom secureRandom = new SecureRandom();
								byte[] iv = new byte[16];
								secureRandom.nextBytes(iv);
								
								CipherType type = (CipherType) comboBox.getSelectedItem();
								type.setIv(iv);
								type.setKey(key);
								type.setCipherMode((String) comboBox_1.getSelectedItem());
								
								FileIO.saveFile(selectedFile.getAbsolutePath(), type, plainText);
								
								
							} else {
								//write unencrypted
							}
							
							
						} else {
							currentFrame.setVisible(true);
							parentFrame.toFront();
							currentFrame.toFront();
						}
						
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						currentFrame.dispose();
						parentFrame.setEnabled(true);
						parentFrame.toFront();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
		
		//this.pack();                   //Realize the components.
		//textField.requestFocusInWindow();  //This button will have the initial focus.
		//frame.setVisible(true);         //Display the window.
	}
}
