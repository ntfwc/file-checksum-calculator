import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dataDigesters.DataDigester;

import implementationFetchers.BuiltInImplementationsFetcher;
import implementationFetchers.CRC32ImplementationFetcher;
import implementationFetchers.ImplementationFetcher;


public class DigestImplementationManager {
	private Map<String,ImplementationFetcher> implementationDictionary = new HashMap<String, ImplementationFetcher>();
	
	private String[] getAvailableBuiltInDigestAlgorithmNames()
	{
		Set<String> algorithmsSet = new HashSet<String>();
		Provider[] providers = Security.getProviders();
		for (Provider provider : providers)
		{
			Set<Provider.Service> services = provider.getServices();
			for (Provider.Service service : services)
			{
				if (service.getType() == "MessageDigest")
				{
					algorithmsSet.add(service.getAlgorithm());
				}
			}
		}
		
		List<String> algorithmsList = new ArrayList<String>();
		algorithmsList.addAll(algorithmsSet);
		Collections.sort(algorithmsList);
		return algorithmsList.toArray(new String[algorithmsList.size()]);
	}
	
	private void populateImplementationDictionary()
	{
		implementationDictionary.put("CRC32", new CRC32ImplementationFetcher());
		
		String[] builtInAlgorithmNames = getAvailableBuiltInDigestAlgorithmNames();
		for (String algorithmName : builtInAlgorithmNames)
		{
			implementationDictionary.put(algorithmName + " (built-in)", new BuiltInImplementationsFetcher(algorithmName));
		}
	}
	
	public DigestImplementationManager()
	{
		populateImplementationDictionary();
	}
	
	public String[] getImplementationNames()
	{
		Set<String> implementationNameSet = implementationDictionary.keySet();
		String[] implementationNames = implementationNameSet.toArray(new String[implementationNameSet.size()]);
		Arrays.sort(implementationNames);
		return implementationNames;
	}
	
	public DataDigester getImplementation(String implementationName)
	{
		if (!implementationDictionary.containsKey(implementationName))
		{
			throw new RuntimeException("Given unknown implementation name");
		}
		
		return implementationDictionary.get(implementationName).getImplementation();
	}
	
	
}
