package pd;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import cipherTypes.CipherType;

public class CurrentState {
	private JFrame programGUI;
	private JTextPane mainText;
	
	private String password;
	private CipherType encType;
	private File currentFile;
	
	
	public CurrentState(JFrame programGUI, JTextPane mainText) {
		this.programGUI = programGUI;
		this.mainText = mainText;
		
		this.password = "";
		this.encType = null;
		this.currentFile = null;
	}
	
	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public CipherType getEncType() {
		return encType;
	}

	public void setEncType(CipherType encType) {
		this.encType = encType;
	}

	public File getCurrentFile() {
		return currentFile;
	}
	
	public void setCurrentFile(File file) {
		if (file == null) {
			programGUI.setTitle("Untitled - Obscurepad");
		} else {
			programGUI.setTitle(file.getName() + " - Obscurepad");
		}
		
		this.currentFile = file;
	}
	
	public void updateText(String text) {
		mainText.setText(text);
	}
}
