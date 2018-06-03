package org.redisson.connection.balancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.redisson.api.NodeType;
import org.redisson.connection.ClientConnectionsEntry;

public class ReadingAndWritingSeparationBalancer implements LoadBalancer{

	private final AtomicInteger index = new AtomicInteger(-1);
	
	@Override
	public ClientConnectionsEntry getEntry(List<ClientConnectionsEntry> clientsCopy) {
		if(clientsCopy.size() == 1){
			return clientsCopy.get(0);
		}
		while(true){
			int ind = Math.abs(index.incrementAndGet() % clientsCopy.size());
			ClientConnectionsEntry clientConnectionsEntry = clientsCopy.get(ind);
			if(clientConnectionsEntry.getNodeType() != NodeType.MASTER){
				return clientConnectionsEntry;
			}
		}
	}

}
