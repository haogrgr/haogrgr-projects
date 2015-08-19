package com.haogrgr.practice.main;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.haogrgr.practice.handle.TestSrvImpl;
import com.haogrgr.practice.thrift.TestSrv;

public class SrvMain {

	public static void main(String[] args) throws Exception {
		TServerTransport serverTransport = new TServerSocket(9090);
		
		TestSrv.Processor<TestSrvImpl> processor = new TestSrv.Processor<>(new TestSrvImpl());
		
		Args arg = new TThreadPoolServer.Args(serverTransport).processor(processor);
		TServer server = new TThreadPoolServer(arg);
		
		server.serve();
	}

}
