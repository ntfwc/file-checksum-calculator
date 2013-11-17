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

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dataDigesters.DataDigester;

import implementationFetchers.BuiltInImplementationsFetcher;
import implementationFetchers.CRC32ImplementationFetcher;
import implementationFetchers.GnuCryptoImplementationFetcher;
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
		
		//For some reason my Java implementation gives SHA1 two names, so we will remove one.
		if (algorithmsSet.contains("SHA") && algorithmsSet.contains("SHA1"))
		{
			algorithmsSet.remove("SHA");
		}
		
		return algorithmsSet.toArray(new String[algorithmsSet.size()]);
	}
	
	private void populateImplementationDictionary()
	{
		implementationDictionary.put("CRC32 (built-in)", new CRC32ImplementationFetcher());
		
		String[] builtInAlgorithmNames = getAvailableBuiltInDigestAlgorithmNames();
		for (String algorithmName : builtInAlgorithmNames)
		{
			implementationDictionary.put(algorithmName + " (built-in)", new BuiltInImplementationsFetcher(algorithmName));
		}
		
		String[] gnuCryptoAlgorithmNames = GnuCryptoImplementationFetcher.algorithmNames;
		for (String algorithmName : gnuCryptoAlgorithmNames)
		{
			implementationDictionary.put(algorithmName + " (Gnu Crypto)", new GnuCryptoImplementationFetcher(algorithmName));
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
