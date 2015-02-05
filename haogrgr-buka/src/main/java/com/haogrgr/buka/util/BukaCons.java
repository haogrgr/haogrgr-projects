package com.haogrgr.buka.util;

public class BukaCons {
	/**漫画详情**/
	public static final String DETAIL_PARAM = "i={\"f\":\"func_getdetail\",\"mid\":%s}&c=%s&z=1&p=android&v=4&cv=17170469";
	
	/**完结分页**/
	public static final String FINISH_LIST_PARAM = "i={\"f\":\"func_getgroupitems\",\"count\":%s,\"start\":%s,\"fn\":11,\"fp\":\"\"}&c=%s&z=1&p=android&v=4&cv=1717";
	
	/**评论分页**/
	public static final String DISCUSS_LIST_PARAM = "i={\"f\":\"func_getdetail\",\"mid\":%s}&c=%s&z=1&p=android&v=4&cv=17170469";
}
