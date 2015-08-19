package com.haogrgr.zookeeper.main;

import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class Main {

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 5000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
		
		zk.create("/haogrgr", "haogrgr".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		zk.create("/haogrgr/down", "haogrgr_down".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		zk.create("/haogrgr/open", "haogrgr_open".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		List<String> children = zk.getChildren("/haogrgr", true);
		System.out.println(children);
		
		Stat stat = new Stat();
		byte[] data = zk.getData("/haogrgr/down", true, stat);
		System.out.println(new String(data));
		
		zk.setData("/haogrgr/down", "haogrgr_modify".getBytes(), -1);
		System.out.println(new String(zk.getData("/haogrgr/down", true, stat)));
		
		zk.delete("/haogrgr/open", -1);
		System.out.println(zk.getChildren("/haogrgr", true));
		
		zk.delete("/haogrgr/down", -1);
		zk.delete("/haogrgr", -1);
		
		zk.close();
	}

}
