package com.haogrgr.practice.handle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.haogrgr.practice.thrift.TestModel;
import com.haogrgr.practice.thrift.TestSrv;

public class TestSrvImpl implements TestSrv.Iface {

	private static Map<String, TestModel> dbbyname = new HashMap<String, TestModel>();
	private static Map<Integer, TestModel> dbbyid = new HashMap<Integer, TestModel>();
	static {
		dbbyname.put("haogrgr", new TestModel(1, "haogrgr", 25));
		dbbyname.put("testst", new TestModel(2, "testst", 25));

		dbbyid.put(1, new TestModel(1, "haogrgr", 25));
		dbbyid.put(2, new TestModel(2, "testst", 25));
	}

	@Override
	public TestModel findById(int id) throws TException {
		return dbbyid.get(id);
	}

	@Override
	public List<TestModel> findByName(String name) throws TException {
		return Arrays.asList(dbbyname.get(name));
	}

	@Override
	public int update(TestModel testModel) throws TException {
		TestModel model = dbbyid.get(testModel.getId());
		if (model != null) {
			model.setName(testModel.getName());
			model.setAge(testModel.getAge());
			return 1;
		}
		return 0;
	}

}
