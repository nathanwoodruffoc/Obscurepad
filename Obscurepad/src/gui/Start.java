package gui;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Start {
	
	public static void main(String[] args)
	{
		
		
		
		JFrame window = new MainGUI();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//window.getContentPane().add(new MainGUI());
		//window.pack(); //?
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
