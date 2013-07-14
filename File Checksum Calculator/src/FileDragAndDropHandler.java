import javax.swing.TransferHandler;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.File;

public class FileDragAndDropHandler extends TransferHandler
{
	private static final long serialVersionUID = 9573198412L;
	
	private GUI gui;
	private AtomicBoolean enabled = new AtomicBoolean(true);
	
	public FileDragAndDropHandler(GUI gui)
	{
		this.gui = gui;
	}
	
	public void setEnabled(boolean disabled)
	{
		this.enabled.set(disabled);
	}
	
	private boolean isFileTransfer(TransferHandler.TransferSupport support)
	{
		return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}
	
	@Override
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		return isFileTransfer(support) && enabled.get();
	}
	
	@SuppressWarnings("unchecked")
	private File readTransferable(Transferable transferable)
	{
		List<File> fileList;
		try
		{
			Object transferData = transferable.getTransferData(DataFlavor.javaFileListFlavor);
			fileList = (List<File>) transferData;
		}
		catch (Exception e)
		{
			throw new RuntimeException("Unexpected exception occurred: " + e.getMessage());
		}
		
		File file = fileList.get(0);
		if (file.isFile())
		{
			return file;
		}
		else
		{
			return null;
		}
	}
	
	@Override
	public boolean importData(TransferHandler.TransferSupport support)
	{
		if (support.isDrop() && isFileTransfer(support))
		{
			File file = readTransferable(support.getTransferable());
			if (file != null)
			{
				gui.digestFile(file);
				return true;
			}
		}
		return false;
	}
}
