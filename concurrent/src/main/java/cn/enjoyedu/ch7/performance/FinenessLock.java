package cn.enjoyedu.ch7.performance;

import java.util.HashSet;
import java.util.Set;

/**
 * 锁分离
 */
public class FinenessLock {
	
	public final Set<String> users = new HashSet<String>();
	public final Set<String> queries = new HashSet<String>();

	//这四个方法抢的都是同一把锁，即调用方法的FinenessLock实例
	public synchronized void addUser(String u) {
		users.add(u);
	}
	
	public synchronized void addQuery(String q) {
		queries.add(q);
	}
	
	public synchronized void removeUser(String u) {
		users.remove(u);
	}
	
	public synchronized void removeQuery(String q) {
		queries.remove(q);
	}

	//实现锁分离，操作两个集合使用两把锁
	public void addUserDiv(String u) {
		synchronized (users){
			users.add(u);
		}
	}

	public void addQueryDiv(String q) {
		synchronized (queries){
			queries.add(q);
		}
	}

}
