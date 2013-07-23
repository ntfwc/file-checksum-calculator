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
