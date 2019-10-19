package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.drjekyll.fontchooser.FontDialog;

import cipherTypes.AES;
import cipherTypes.CipherType;
import cipherTypes.Plaintext;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class MainGUI extends JFrame {

	private JPanel contentPane;
	private JTextPane textPane;

	public void updateTitle(String title) {
		this.setTitle(title + " - Obscurepad");
	}
	
	public void updateText(String text) {
		textPane.setText(text);
	}
	
	public void updateCachedPassword(String password) {
		
	}

	/**
	 * Create the frame.
	 */
	public MainGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		ArrayList<CipherType> cipherTypes = new ArrayList<CipherType>();
		cipherTypes.add(new AES());
		cipherTypes.add(new Plaintext());
		
		ArrayList<String> cipherModes = new ArrayList<String>();
		cipherModes.add("CBC");
		cipherModes.add("CTR");
		
		
		
		
		MainGUI currentFrame = this;
		textPane = new JTextPane();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 662, 464);
		setTitle("Untitled - ObscurePad");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateTitle("Untitled");
				updateText("");
				updateCachedPassword("");
			}
		});
		mntmNew.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open...");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser f = new JFileChooser();
				int returnValue = f.showOpenDialog(currentFrame);
				
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = f.getSelectedFile();
					System.out.println(selectedFile.getAbsolutePath());
		
					OpenOptions s = new OpenOptions(MainGUI.this, cipherTypes, cipherModes, selectedFile);
					s.setLocationRelativeTo(null);
					s.setAutoRequestFocus(true);
					s.setVisible(true);
					currentFrame.setEnabled(false);
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setEnabled(false);
		mntmSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveOptions s = new SaveOptions(currentFrame, cipherTypes, cipherModes, textPane.getText());
				s.setLocationRelativeTo(null);
				s.setAutoRequestFocus(true);
				s.setVisible(true);
				currentFrame.setEnabled(false);
			}
		});
		mnFile.add(mntmSaveAs);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmPageSetup = new JMenuItem("Page Setup...");
		mntmPageSetup.setEnabled(false);
		mnFile.add(mntmPageSetup);
		
		JMenuItem mntmPrint = new JMenuItem("Print...");
		mntmPrint.setEnabled(false);
		mntmPrint.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnFile.add(mntmPrint);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentFrame.dispose();
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mntmUndo.setEnabled(false);
		mntmUndo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmUndo);
		
		JSeparator separator_2 = new JSeparator();
		mnEdit.add(separator_2);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mntmCut.setEnabled(false);
		mntmCut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mntmCopy.setEnabled(false);
		mntmCopy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmCopy);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mntmPaste.setEnabled(false);
		mntmPaste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmPaste);
		
		JMenuItem mntmDelete = new JMenuItem("Delete");
		mntmDelete.setEnabled(false);
		mnEdit.add(mntmDelete);
		
		JSeparator separator_3 = new JSeparator();
		mnEdit.add(separator_3);
		
		JMenuItem mntmFind = new JMenuItem("Find...");
		mntmFind.setEnabled(false);
		mntmFind.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmFind);
		
		JMenuItem mntmFindNext = new JMenuItem("Find Next");
		mntmFindNext.setEnabled(false);
		mnEdit.add(mntmFindNext);
		
		JMenuItem mntmReplace = new JMenuItem("Replace...");
		mntmReplace.setEnabled(false);
		mntmReplace.setAccelerator(KeyStroke.getKeyStroke('H', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmReplace);
		
		JMenuItem mntmGoTo = new JMenuItem("Go To...");
		mntmGoTo.setEnabled(false);
		mntmGoTo.setAccelerator(KeyStroke.getKeyStroke('G', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmGoTo);
		
		JSeparator separator_4 = new JSeparator();
		mnEdit.add(separator_4);
		
		JMenuItem mntmSelectAll = new JMenuItem("Select All");
		mntmSelectAll.setEnabled(false);
		mntmSelectAll.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));
		mnEdit.add(mntmSelectAll);
		
		JMenuItem mntmTimedate = new JMenuItem("Time/Date");
		mntmTimedate.setEnabled(false);
		mnEdit.add(mntmTimedate);
		
		JMenu mnFormat = new JMenu("Format");
		menuBar.add(mnFormat);
		
		JRadioButtonMenuItem rdbtnmntmWordWrap = new JRadioButtonMenuItem("Word Wrap");
		rdbtnmntmWordWrap.setEnabled(false);
		mnFormat.add(rdbtnmntmWordWrap);
		
		JMenuItem mntmFont = new JMenuItem("Font...");
		mntmFont.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FontDialog.showDialog(textPane);
			}
		});
		mnFormat.add(mntmFont);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JRadioButtonMenuItem rdbtnmntmStatusBar = new JRadioButtonMenuItem("Status Bar");
		rdbtnmntmStatusBar.setEnabled(false);
		mnView.add(rdbtnmntmStatusBar);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmViewHelp = new JMenuItem("View Help");
		mntmViewHelp.setEnabled(false);
		mnHelp.add(mntmViewHelp);
		
		JMenuItem mntmAboutObscurepad = new JMenuItem("About ObscurePad");
		mntmAboutObscurepad.setEnabled(false);
		mnHelp.add(mntmAboutObscurepad);
		contentPane = new JPanel();
		//contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("insets 0 0 0 0", "[grow]", "[grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		contentPane.add(scrollPane, "cell 0 0,grow");
		
		
		scrollPane.setViewportView(textPane);
	}

}
