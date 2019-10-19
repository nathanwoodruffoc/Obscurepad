package gui;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;

import cipherTypes.*;

public class Start {
	
	public static void main(String[] args)
	{
		ArrayList<CipherType> cipherTypes = new ArrayList<CipherType>();
		cipherTypes.add(new AES());
		cipherTypes.add(new DES());
		cipherTypes.add(new Plaintext());
		
		ArrayList<String> cipherModes = new ArrayList<String>();
		cipherModes.add("CBC");
		cipherModes.add("CTR");
		
		
		JFrame window = new MainGUI(cipherTypes, cipherModes);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.getContentPane().add(new MainGUI());
		//window.pack(); //?
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
