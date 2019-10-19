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
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class OpenOptions extends JDialog {

	private final JPanel contentPanel = new JPanel();


	public OpenOptions(MainGUI parentFrame, ArrayList<CipherType> cipherTypes, ArrayList<String> cipherModes, File selectedFile) {
		JDialog currentFrame = this;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				parentFrame.setEnabled(true);
			}
		});
		setResizable(false);
		setTitle("Open Options");
		setBounds(100, 100, 262, 196);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
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
		
		
		// Password Field
		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(109, 77, 135, 20);
		contentPanel.add(passwordField);
		passwordField.setColumns(10);
		
		
		// Cache password checkbox
		JCheckBox chckbxCachePasswordFor = new JCheckBox("Cache password for this session");
		chckbxCachePasswordFor.setEnabled(false);
		chckbxCachePasswordFor.setBounds(6, 105, 238, 23);
		contentPanel.add(chckbxCachePasswordFor);
		
		
		// Cipher Modes comboBox
		JComboBox<String> comboBoxModes = new JComboBox<String>();
		comboBoxModes.setBounds(109, 44, 135, 22);
		for (String s : cipherModes) {
			comboBoxModes.addItem(s);
		}
		contentPanel.add(comboBoxModes);
		
				
		// Cipher types comboBox
		JComboBox<CipherType> comboBoxTypes = new JComboBox<CipherType>();
		comboBoxTypes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBoxTypes.getSelectedItem().getClass().equals(Plaintext.class)) {
					lblEncryptionMode.setVisible(false);
					lblPassword.setVisible(false);
					comboBoxModes.setVisible(false);
					passwordField.setVisible(false);
					chckbxCachePasswordFor.setVisible(false);
				} else {
					lblEncryptionMode.setVisible(true);
					lblPassword.setVisible(true);
					comboBoxModes.setVisible(true);
					passwordField.setVisible(true);
					chckbxCachePasswordFor.setVisible(true);
				}
			}
		});
		comboBoxTypes.setBounds(109, 11, 135, 22);
		
		for (CipherType c : cipherTypes) {
			comboBoxTypes.addItem(c);
		}
		contentPanel.add(comboBoxTypes);
				
		

		
		
		
		
		
		
		
		// OK, Cancel buttons
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//generate key
						byte[] key = SHA256.hash(new String(passwordField.getPassword()));
						CipherType type = (CipherType) comboBoxTypes.getSelectedItem();
						type.setKey(key);
						type.setCipherMode((String) comboBoxModes.getSelectedItem());
						
						String plaintext;
						try {
							plaintext = FileIO.readFile(selectedFile.getAbsolutePath(), type);
							
							if (plaintext == null) {
								JOptionPane.showMessageDialog(currentFrame, 
										"Incorrect password or encryption type.", 
										"Unable to decrypt", 
										JOptionPane.ERROR_MESSAGE);
							} else {
								currentFrame.dispose();
								parentFrame.setEnabled(true);
								parentFrame.toFront();
								
								//update stuff
								parentFrame.updateText(plaintext);
								parentFrame.updateTitle(selectedFile.getName());
								//update cached password if applicable
								//parentFrame.updateCachedPassword(password);
								System.out.println(plaintext);
							}
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(currentFrame, 
									"Error while opening \"" + selectedFile.getName() + "\"", 
									"Error", 
									JOptionPane.ERROR_MESSAGE);
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
		
		
		
	}
}
