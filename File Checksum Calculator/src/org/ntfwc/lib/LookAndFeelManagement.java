/*  Copyright (c) 2013 ntfwc<ntfwc@yahoo.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.*/

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
