package org.ntfwc.lib;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class LookAndFeelManagement {
	private static final String[] alternativeLookAndFeelPreferenceArray = {
			"GTK+",
			"Nimbus",
	};
	
	
	private static String tryToGetLookAndFeel(LookAndFeelInfo[] availableLookAndFeels, String name)
	{
		for (LookAndFeelInfo lookAndFeelInfo : availableLookAndFeels)
		{
			if (lookAndFeelInfo.getName() == name)
			{
				return lookAndFeelInfo.getClassName();
			}
		}
		return null;
	}
	
	private static String tryToGetAlternativeLookAndFeel()
	{
		LookAndFeelInfo[] availableLookAndFeels = UIManager.getInstalledLookAndFeels();
		
		String alternativeLookAndFeel = null;
		for (String preferredAlternative : alternativeLookAndFeelPreferenceArray)
		{
			alternativeLookAndFeel = tryToGetLookAndFeel(availableLookAndFeels, preferredAlternative);
			if (alternativeLookAndFeel != null)
			{
				break;
			}
		}
		
		return alternativeLookAndFeel;
	}
	
	private static String getLookAndFeelClassName()
	{
		String defaultLookAndFeel = UIManager.getSystemLookAndFeelClassName();
		if (defaultLookAndFeel == "javax.swing.plaf.metal.MetalLookAndFeel")
		{
			//This is probably not desired, so lets try to find alternatives
			String alternativeLookAndFeel = tryToGetAlternativeLookAndFeel();
			if (alternativeLookAndFeel != null)
			{
				return alternativeLookAndFeel;
			}
		}
		return defaultLookAndFeel;
	}
	
	public static void setupNativeLookAndFeel()
	{
		try
		{
			UIManager.setLookAndFeel(getLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}
	}
}
