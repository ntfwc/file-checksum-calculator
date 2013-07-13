import javax.swing.SwingWorker;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.io.IOException;

public class FileDigester extends SwingWorker<Void, Void> implements PropertyChangeListener
{
	private static final int PROGRESS_BAR_SIZE = 100;
	
	private GUI gui;
	private JProgressBar progressBar;
	private JTextField outputBox;
	private long fileSize;
	private long progressBlockSize;
	private InputStream fileInputStream;
	private DataDigester dataDigester;
	private Exception exception;
	
	public FileDigester(GUI gui, JProgressBar progressBar, JTextField outputBox)
	{
		this.gui = gui;
		this.progressBar = progressBar;
		this.outputBox = outputBox;
		addPropertyChangeListener(this);
	}
	
	private void initProgress()
	{
		setProgress(0);
		progressBar.setValue(0);
	}
	
	private void recordFileSize(File file)
	{
		this.fileSize = file.length();
		this.progressBlockSize = fileSize/PROGRESS_BAR_SIZE;
	}
	
	private InputStream getInputStreamForFile(File file) throws IOException
	{
		return new BufferedInputStream(new FileInputStream(file));
	}
	
	private void doDigest(File file, String algorithm) throws Exception
	{
		if (algorithm == null)
		{
			throw new Exception("No algorithm selected");
		}
		if (algorithm == "CRC32")
		{
			this.dataDigester = new CRC32DataDigester();
		}
		else
		{
			this.dataDigester = new MessageDataDigester(MessageDigest.getInstance(algorithm));
		}
		this.fileInputStream = getInputStreamForFile(file);
		this.exception = null;
		recordFileSize(file);
		initProgress();
		execute();
	}
	
	private void showErrorMessage(String message)
	{
		JOptionPane.showMessageDialog(gui, message, "error", JOptionPane.ERROR_MESSAGE);
	}
	
	private void showErrorMessage(Exception e)
	{
		showErrorMessage(e.getMessage());
	}
	
	public void digest(File file, String algorithm)
	{
		try
		{
			doDigest(file, algorithm);
		}
		catch (Exception exception)
		{
			showErrorMessage(exception);
			gui.enableInputComponents();
		}
	}
	
	private void updateProgress(long lengthOfDataRead)
	{
		int newProgress = (int) (lengthOfDataRead/progressBlockSize);
		if (getProgress() != newProgress)
		{
			setProgress(newProgress);
		}
	}
	
	private void performDigest() throws IOException
	{
		byte[] buffer = new byte[1024];
		long lengthOfDataRead = 0;
		while (true)
		{
			int numRead = fileInputStream.read(buffer);
			if (numRead == -1)
			{
				break;
			}
			dataDigester.update(buffer, 0, numRead);
			lengthOfDataRead += numRead;
			updateProgress(lengthOfDataRead);
		}
		fileInputStream.close();
	}
	
	@Override
	protected Void doInBackground()
	{
		try
		{
			performDigest();
		}
		catch (IOException exception)
		{
			this.exception = exception;
		}
		return null;
	}
	
	private static void addHexCode(StringBuilder sb, byte b)
	{
		String code = Integer.toHexString(UnsignedConversion.convertToUnsigned(b));
		if (code.length() == 1)
		{
			sb.append('0');
		}
		sb.append(code);
	}
	
	private static String createHexString(byte[] data)
	{
		StringBuilder sb = new StringBuilder();
		for (byte b : data)
		{
			addHexCode(sb, b);
		}
		return sb.toString();
	}
	
	private String getMessageDigestString()
	{
		return createHexString(dataDigester.digest());
	}
	
	@Override
	protected void done()
	{
		if (exception == null)
		{
			outputBox.setText(getMessageDigestString());
		}
		else
		{
			showErrorMessage(exception);
		}
		progressBar.setValue(PROGRESS_BAR_SIZE);
		gui.enableInputComponents();
	}
	
	private void updateProgressBar(PropertyChangeEvent event)
	{
		int progress = (Integer) event.getNewValue();
		progressBar.setValue(progress);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent event)
	{
		if (event.getPropertyName() == "progress")
		{
			updateProgressBar(event);
		}
	}
}
