package org.redisson.connection.balancer;

import java.util.List;

import org.redisson.api.NodeType;
import org.redisson.connection.ClientConnectionsEntry;

public class MasterBalancer implements LoadBalancer{

	@Override
	public ClientConnectionsEntry getEntry(List<ClientConnectionsEntry> clientsCopy) {
		ClientConnectionsEntry clientConnectionsEntry = clientsCopy.get(0);
		if(clientConnectionsEntry.getNodeType() == NodeType.MASTER){//默认是第一个master
			return clientConnectionsEntry;
		}
		for (ClientConnectionsEntry entry : clientsCopy) {//clientsCopy 是一个LinkedList ，反编译会将这种循环反编译成迭代器
			if(entry.getNodeType() == NodeType.MASTER){
				return entry;
			}
		}
		return null;
	}

}
