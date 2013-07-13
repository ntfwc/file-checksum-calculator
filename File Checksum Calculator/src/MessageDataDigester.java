import java.security.MessageDigest;


public class MessageDataDigester implements DataDigester {
	private final MessageDigest messageDigest;
	
	public MessageDataDigester(MessageDigest messageDigest)
	{
		this.messageDigest = messageDigest;
	}
	
	@Override
	public void update(byte[] data, int offset, int len) {
		messageDigest.update(data, offset, len);
	}

	@Override
	public byte[] digest() {
		return messageDigest.digest();
	}

}
