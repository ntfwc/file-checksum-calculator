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
public final class UnsignedConversion
{
	public static final short MAX_UNSIGNED_BYTE = 255;
	public static final int MAX_UNSIGNED_SHORT = 65535;
	public static final long MAX_UNSIGNED_INT = 4294967295L;
	
	public static short convertToUnsigned(byte num)
	{
		return (short) (num & 0xFF);
	}
	
	public static byte convertToSigned(short num)
	{
		return (byte) ((num & 0x7F) - (num & 0x80));
	}
	
	public static int convertToUnsigned(short num)
	{
		return num & 0xFFFF;
	}
	
	public static short convertToSigned(int num)
	{
		return (short) ((num & 0x7FFF) - (num & 0x8000));
	}
	
	public static long convertToUnsigned(int num)
	{
		return num & 0xFFFFFFFFL;
	}
	
	public static int convertToSigned(long num)
	{
		return (int) ((num & 0x7FFFFFFF) - (num & 0x80000000));
	}
}
