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
