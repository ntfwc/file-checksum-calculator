/*  Copyright (C) 2013 ntfwc<ntfwc@yahoo.com>

    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along
    with this program; if not, write to the Free Software Foundation, Inc.,
    51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.*/

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JFileChooser;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;

public class GUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 578124798121L;
	private static final String ACTION_CHOOSE_FILE = "a";
	
	private JButton digestButton;
	private JComboBox<String> algorithmSelector;
	private JTextField checksumDisplay;
	private JProgressBar progressBar;
	private JLabel currentFileIndicator;
	
	private JFileChooser fileChooser = new JFileChooser();
	private FileDragAndDropHandler fileDragAndDropHandler;
	
	public final DigestImplementationManager digestImplementationManager = new DigestImplementationManager();
	
	private void addDigestButton()
	{
		this.digestButton = new JButton("Choose File");
		digestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		digestButton.setActionCommand(ACTION_CHOOSE_FILE);
		digestButton.addActionListener(this);
		add(digestButton);
	}
	
	private void addAlgorithmSelector()
	{
		String[] selections = digestImplementationManager.getImplementationNames();
		this.algorithmSelector = new JComboBox<String>(selections);
		algorithmSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
		algorithmSelector.setMaximumSize(algorithmSelector.getPreferredSize());
		add(algorithmSelector);
	}
	
	private void addLabel(String text)
	{
		JLabel label = new JLabel(text);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(label);
	}
	
	private void addCurrentFileIndicator()
	{
		this.currentFileIndicator = new JLabel(" ");
		currentFileIndicator.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(currentFileIndicator);
	}
	
	private void addChecksumDisplay()
	{
		this.checksumDisplay = new JTextField();
		checksumDisplay.setEditable(false);
		checksumDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
		checksumDisplay.setPreferredSize(new Dimension(300, checksumDisplay.getPreferredSize().height));
		checksumDisplay.setMaximumSize(new Dimension(1000, checksumDisplay.getPreferredSize().height));
		add(checksumDisplay);
	}
	
	private void addProgressBar()
	{
		this.progressBar = new JProgressBar();
		progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(progressBar);
	}
	
	private void addEmptySpace(int size)
	{
		add(Box.createRigidArea(new Dimension(size, 10)));
	}
	
	private void addComponents()
	{
		addLabel("Message Digest Algorithm:");
		addAlgorithmSelector();
		addLabel("Drag a file here or");
		addDigestButton();
		addEmptySpace(20);
		addCurrentFileIndicator();
		addChecksumDisplay();
		addProgressBar();
	}
	
	public GUI()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.fileDragAndDropHandler = new FileDragAndDropHandler(this);
		setTransferHandler(fileDragAndDropHandler);
		addComponents();
	}
	
	public void enableInputComponents()
	{
		digestButton.setEnabled(true);
		algorithmSelector.setEnabled(true);
		fileDragAndDropHandler.setEnabled(true);
	}
	
	private File askUserForFile()
	{
		if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			return fileChooser.getSelectedFile();
		}
		else
		{
			return null;
		}
	}
	
	private void disableInputComponents()
	{
		digestButton.setEnabled(false);
		algorithmSelector.setEnabled(false);
		fileDragAndDropHandler.setEnabled(false);
	}
	
	private void clearOutputBox()
	{
		checksumDisplay.setText(null);
	}
	
	public void digestFile(File file)
	{
		clearOutputBox();
		disableInputComponents();
		currentFileIndicator.setText("Current file: " + file.toString());
		FileDigester fileDigester = new FileDigester(this, progressBar, checksumDisplay);
		fileDigester.digest(file, (String) algorithmSelector.getSelectedItem());
	}
	
	private void tryToDigestFile()
	{
		File file = askUserForFile();
		if (file != null && file.isFile())
		{
			digestFile(file);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (e.getActionCommand() == ACTION_CHOOSE_FILE)
		{
			tryToDigestFile();
		}
	}
}
