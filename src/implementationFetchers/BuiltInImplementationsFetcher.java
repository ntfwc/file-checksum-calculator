package implementationFetchers;

import java.security.MessageDigest;

import dataDigesters.BuiltInDigester;
import dataDigesters.DataDigester;

public class BuiltInImplementationsFetcher implements ImplementationFetcher{
	private String algorithmName;
	
	public BuiltInImplementationsFetcher(String algorithmName)
	{
		this.algorithmName = algorithmName;
	}

	@Override
	public DataDigester getImplementation() 
	{
		try
		{
			return new BuiltInDigester(MessageDigest.getInstance(algorithmName));
		}
		catch (Exception e)
		{
			throw new RuntimeException(e.getMessage());
		}
	}
	
}
