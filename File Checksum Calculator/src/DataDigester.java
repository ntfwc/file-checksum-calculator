
public interface DataDigester {
	public void update(byte[] data, int offset, int len);
	public byte[] digest();
}
