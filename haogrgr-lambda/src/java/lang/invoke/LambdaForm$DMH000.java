//package java.lang.invoke;
//
//final class LambdaForm$DMH000 extends LambdaForm {
//
//	//https://wikis.oracle.com/display/HotSpotInternals/Direct+method+handles
//	static Object invokeStatic_L_L(Object dmh, Object param) throws Throwable{
//		//在该例中dmh为DirectMethodHandle类型,
//		//证明:该类字节码生成下面语句, internalMemberName方法的参数为DirectMethodHandle类型
//		//在该例中,就是Main$$Lambda$1.get$Lambda(String)Function/invokeStatic
//		MemberName memberName = (MemberName)DirectMethodHandle.internalMemberName(dmh);
//		
//		//param为memberName的参数 
//		//证明:断点DirectMethodHandle.internalMemberName,F6,查看方法参数,确实是"hello lambda "
//		//在该例中,就是Main$$Lambda$1.get$Lambda(String name)中的name
//		Object result = MethodHandle.linkToStatic(param, memberName);
//		
//		//结果为memberName所代表方法执行的结果
//		//TODO:证明:
//		//该例中,就是Main$$Lambda$1.get$Lambda(String name)方法返回值
//		return result;
//	}
//	
//}
