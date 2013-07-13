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
