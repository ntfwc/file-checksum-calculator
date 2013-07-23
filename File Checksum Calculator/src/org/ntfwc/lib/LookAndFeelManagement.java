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
