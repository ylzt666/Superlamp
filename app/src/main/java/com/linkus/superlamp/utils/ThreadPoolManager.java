package com.linkus.superlamp.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolManager {

	private ExecutorService service;
	
	private static final ThreadPoolManager manager = new ThreadPoolManager();
	
	private ThreadPoolManager() {
		int num = Runtime.getRuntime().availableProcessors();//获取当前系统的CPU 数目;
		service = Executors.newFixedThreadPool(num*4);
	}
	/*Executors.newCachedThreadPool()
	 * 缓存型池子，先查看池中有没有以前建立的线程，如果有，就reuse.如果没有，就建一个新的线程加入池中
	-缓存型池子通常用于执行一些生存期很短的异步型任务
	*/
	
	public static ThreadPoolManager getInstance() {
		return manager;
	}
	
	public void addTask(Runnable runnable) {
		service.execute(runnable);
	}
	
}
