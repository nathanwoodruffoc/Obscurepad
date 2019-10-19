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
import javax.swing.JOptionPane;
import javax.swing.JCheckBox;
import javax.swing.JPasswordField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OpenOptions extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPasswordField textField;


	public OpenOptions(MainGUI parentFrame, ArrayList<CipherType> cipherTypes, ArrayList<String> cipherModes, File selectedFile) {
		
		JDialog currentFrame = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parentFrame.setEnabled(true);
			}
		});
		setResizable(false);
		setBounds(100, 100, 262, 196);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		
		
		
		// Cipher types comboBox
		JComboBox<CipherType> comboBox = new JComboBox<CipherType>();
		comboBox.setBounds(109, 11, 135, 22);
		
		for (CipherType c : cipherTypes) {
			comboBox.addItem(c);
		}
		contentPanel.add(comboBox);
		
		
		// Cipher Modes comboBox
		JComboBox<String> comboBox_1 = new JComboBox<String>();
		comboBox_1.setBounds(109, 44, 135, 22);
		for (String s : cipherModes) {
			comboBox_1.addItem(s);
		}
		contentPanel.add(comboBox_1);
		
		
		// Password Field
		textField = new JPasswordField();
		textField.setBounds(109, 77, 135, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		
		
		JCheckBox chckbxCachePasswordFor = new JCheckBox("Cache password for this session");
		chckbxCachePasswordFor.setEnabled(false);
		chckbxCachePasswordFor.setBounds(6, 105, 238, 23);
		contentPanel.add(chckbxCachePasswordFor);
		
		
		
		
		
		// OK, Cancel buttons
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (!comboBox.getSelectedItem().getClass().equals(Plaintext.class)) {
							//generate key
							byte[] key = SHA256.hash(new String(textField.getPassword()));
							CipherType type = (CipherType) comboBox.getSelectedItem();
							type.setKey(key);
							type.setCipherMode((String) comboBox_1.getSelectedItem());
							
							String plaintext = FileIO.readFile(selectedFile.getAbsolutePath(), type);
							if (plaintext == null) {
								//disable current frame
								//display wrong password dialog
								//enable current frame

								JOptionPane.showMessageDialog(currentFrame, 
										"Incorrect password or encryption type.", 
										"Unable to decrypt", 
										JOptionPane.ERROR_MESSAGE);
								
							} else {
								// delete current frame
								currentFrame.dispose();
								parentFrame.setEnabled(true);
								parentFrame.toFront();
								
								//update window with unencrypted text
								parentFrame.updateText(plaintext);
								//update window title
								parentFrame.updateTitle(selectedFile.getName());
								//update cached password if applicable
								//parentFrame.updateCachedPassword(password);
								System.out.println(plaintext);
							}
						} else {
							// delete current frame
							currentFrame.dispose();
							parentFrame.setEnabled(true);
							parentFrame.toFront();
							
							//read unencrypted
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
		
		
		// Labels
		JLabel lblEncryptionType = new JLabel("Encryption Type:");
		lblEncryptionType.setBounds(10, 15, 89, 14);
		contentPanel.add(lblEncryptionType);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(10, 80, 89, 14);
		contentPanel.add(lblPassword);
		
		JLabel lblEncryptionMode = new JLabel("Encryption Mode:");
		lblEncryptionMode.setBounds(10, 47, 103, 14);
		contentPanel.add(lblEncryptionMode);
	}
}
