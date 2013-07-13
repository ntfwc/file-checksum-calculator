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
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.security.Security;
import java.security.Provider;
import java.io.File;

public class GUI extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 578124798121L;
	private static final String ACTION_CHOOSE_FILE = "a";
	
	private JButton digestButton;
	private JComboBox<String> algorithmSelector;
	private JTextField outputBox;
	private JProgressBar progressBar;
	private JLabel currentFileLabel;
	private JFileChooser fileChooser = new JFileChooser();
	
	private void addDigestButton()
	{
		this.digestButton = new JButton("Choose File");
		digestButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		digestButton.setActionCommand(ACTION_CHOOSE_FILE);
		digestButton.addActionListener(this);
		add(digestButton);
	}
	
	private String[] getAvailableDigestAlgorithms()
	{
		Set<String> algorithmsSet = new HashSet<String>();
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers)
		{
			Set<Provider.Service> services = provider.getServices();
			for (Provider.Service service : services)
			{
				if (service.getType() == "MessageDigest")
				{
					algorithmsSet.add(service.getAlgorithm());
				}
			}
		}
		
		List<String> algorithmsList = new ArrayList<String>();
		algorithmsList.addAll(algorithmsSet);
		Collections.sort(algorithmsList);
		return algorithmsList.toArray(new String[algorithmsList.size()]);
	}
	
	private void addAlgorithmSelector()
	{
		String[] selections = getAvailableDigestAlgorithms();
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
	
	private void addCurrentFileLabel()
	{
		this.currentFileLabel = new JLabel(" ");
		currentFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(currentFileLabel);
	}
	
	private void addOutputBox()
	{
		this.outputBox = new JTextField();
		outputBox.setEditable(false);
		outputBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		outputBox.setPreferredSize(new Dimension(300, outputBox.getPreferredSize().height));
		outputBox.setMaximumSize(new Dimension(1000, outputBox.getPreferredSize().height));
		add(outputBox);
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
		addCurrentFileLabel();
		addOutputBox();
		addProgressBar();
	}
	
	public GUI()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setTransferHandler(new FileDragAndDropHandler(this));
		addComponents();
	}
	
	public void enableInputComponents()
	{
		digestButton.setEnabled(true);
		algorithmSelector.setEnabled(true);
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
	}
	
	private void clearOutputBox()
	{
		outputBox.setText(null);
	}
	
	public void digestFile(File file)
	{
		clearOutputBox();
		disableInputComponents();
		currentFileLabel.setText("Current file: " + file.toString());
		FileDigester fileDigester = new FileDigester(this, progressBar, outputBox);
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
