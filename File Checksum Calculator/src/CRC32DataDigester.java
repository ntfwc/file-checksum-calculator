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

import java.util.zip.CRC32;

import org.ntfwc.lib.UnsignedConversion;


public class CRC32DataDigester implements DataDigester {
	private final CRC32 crcDigester = new CRC32();
	
	@Override
	public void update(byte[] data, int offset, int len) {
		crcDigester.update(data, offset, len);
	}

	private byte[] getIntBytes(int integer)
	{
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (integer >> 24);
		bytes[1] = (byte) (integer >> 16);
		bytes[2] = (byte) (integer >> 8);
		bytes[3] = (byte) integer;
		
		return bytes;
	}
	
	@Override
	public byte[] digest() {
		int digestValue = UnsignedConversion.convertToSigned(crcDigester.getValue());
		return getIntBytes(digestValue);
	}

}
