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
