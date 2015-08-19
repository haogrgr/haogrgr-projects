package com.haogrgr.practice.main;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.haogrgr.practice.thrift.TestModel;
import com.haogrgr.practice.thrift.TestSrv;

public class ClientMain {

	public static void main(String[] args) throws Exception {
		TTransport transport = new TSocket("localhost", 9090);
		transport.open();
		
		TProtocol protocol = new TBinaryProtocol(transport);
		TestSrv.Client client = new TestSrv.Client(protocol);
		
		TestModel findById = client.findById(2);
		System.out.println(findById);
		
		List<TestModel> findByName = client.findByName("haogrgr");
		System.out.println(findByName);

		transport.close();
	}

}
