package dataDigesters;

import gnu.crypto.hash.IMessageDigest;

public class GnuCryptoDigester implements DataDigester {
	private IMessageDigest gnuHashImplementation;
	
	public GnuCryptoDigester(IMessageDigest gnuHashImplementation)
	{
		this.gnuHashImplementation = gnuHashImplementation;
	}
	
	@Override
	public void update(byte[] data, int offset, int len) {
		gnuHashImplementation.update(data, offset, len);
	}

	@Override
	public byte[] digest() {
		return gnuHashImplementation.digest();
	}

}
