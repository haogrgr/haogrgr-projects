package com.haogrgr.buka.main;

import com.haogrgr.buka.util.AppContextUtil;

public class DaoTest {

	public static void main(String[] args) {
		AppContextUtil.initSpring();

		// CashLogMapper bean = AppContextUtil.getBean(CashLogMapper.class);

		// System.out.println(bean.findById(1));

		// PageInfo<CashLogModel> info = new PageInfo<>(1, 10);
		// info.addParam("orderId", "1");
		// info.addParam("id", "2");
		// List<CashLogModel> findByPage = bean.findByPage(info);
		// Integer findByPageCount = bean.findByPageCount(info);
		// System.out.println(findByPageCount + ":" + findByPage);

		// CashLogModel log = new CashLogModel();
		// log.setId(26);
		// log.setOrderId("update");
		// log.setCreateTime(new Date());
		// bean.update(log);

		// System.out.println(bean.all().size());
		// System.out.println(bean.count());

		// CashLogModel log = new CashLogModel();
		// log.setOrderId("25");
		// log.setAccountId(25);
		// log.setCreateTime(new Date());
		// bean.insert(log);

		// ArrayList<CashLogModel> list = new ArrayList<CashLogModel>();
		// for (int i = 0; i < 25; i++) {
		// CashLogModel log = new CashLogModel();
		// log.setOrderId(""+i);
		// log.setAccountId(i);
		// log.setCreateTime(new Date());
		// list.add(log);
		// }
		// bean.inserts(list);

		// bean.delete(26);

		// bean.deletes(new Integer[]{25,24,23,22,21});

		// List<CashLogModel> load = bean.load(new Integer[]{1,10,20});
		// System.out.println(load);

	}

}
