package implementationFetchers;

import dataDigesters.CRC32DataDigester;
import dataDigesters.DataDigester;

public class CRC32ImplementationFetcher implements ImplementationFetcher{

	@Override
	public DataDigester getImplementation() {
		return new CRC32DataDigester();
	}
	
}
