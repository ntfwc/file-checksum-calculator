package implementationFetchers;

import gnu.crypto.hash.IMessageDigest;
import gnu.crypto.hash.MD2;
import gnu.crypto.hash.MD4;
import gnu.crypto.hash.MD5;
import gnu.crypto.hash.RipeMD128;
import gnu.crypto.hash.RipeMD160;
import gnu.crypto.hash.Sha160;
import gnu.crypto.hash.Sha256;
import gnu.crypto.hash.Sha384;
import gnu.crypto.hash.Sha512;
import gnu.crypto.hash.Tiger;
import gnu.crypto.hash.Whirlpool;
import dataDigesters.DataDigester;
import dataDigesters.GnuCryptoDigester;

public class GnuCryptoImplementationFetcher implements ImplementationFetcher {
	private String algorithmName;
	public static String[] algorithmNames = {
			"MD2",
			"MD4",
			"MD5",
			"RIPEMD-128",
			"RIPEMD-160",
			"SHA-160",
			"SHA-256",
			"SHA-384",
			"SHA-512",
			"Tiger",
			"Whirlpool"
	};
	
	public GnuCryptoImplementationFetcher(String algorithmName)
	{
		this.algorithmName = algorithmName;
	}
	
	public IMessageDigest getGnuHashImplementation()
	{
		if (algorithmName == "MD2")
		{
			return new MD2();
		}
		else if (algorithmName == "MD4")
		{
			return new MD4();
		}
		else if (algorithmName == "MD5")
		{
			return new MD5();
		}
		else if (algorithmName == "RIPEMD-128")
		{
			return new RipeMD128();
		}
		else if (algorithmName == "RIPEMD-160")
		{
			return new RipeMD160();
		}
		else if (algorithmName == "SHA-160")
		{
			return new Sha160();
		}
		else if (algorithmName == "SHA-256")
		{
			return new Sha256();
		}
		else if (algorithmName == "SHA-384")
		{
			return new Sha384();
		}
		else if (algorithmName == "SHA-512")
		{
			return new Sha512();
		}
		else if (algorithmName == "Tiger")
		{
			return new Tiger();
		}
		else if (algorithmName == "Whirlpool")
		{
			return new Whirlpool();
		}
		else
		{
			throw new RuntimeException("Requested unknown Gnu Crypto hash algorithm");
		}
	}
	
	@Override
	public DataDigester getImplementation() {
		return new GnuCryptoDigester(getGnuHashImplementation());
	}

}
