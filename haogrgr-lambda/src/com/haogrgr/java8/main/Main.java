package com.haogrgr.java8.main;

import java.util.function.Function;

public class Main {

	public static void main(String[] args) throws Throwable {
//		-Djava.lang.invoke.MethodHandle.DUMP_CLASS_FILES=true
//		System.setProperty("jdk.internal.lambda.dumpProxyClasses", ".");
//		Main$$Lambda$1
		String hello = "hello lambda ";
		Function<String, Void> func = (name) -> {
			System.out.println(hello + name);
			return null;
		};
		func.apply("haogrgr");
		
//		String name = "xx";
//		Runnable run = () -> {System.out.println(name);};
	}

}