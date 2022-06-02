package cn.enjoyedu.ch2.tools.semaphore;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 *类说明：演示Semaphore用法，一个数据库连接池的实现
 */
public class DBPoolSemaphore {
	//数据库连接池的初始容量
	private final static int POOL_SIZE = 10;
	//两个指示器，分别表示池子还有可用连接和已用连接
	private final Semaphore useful,useless;
	//存放数据库连接的容器
	private static LinkedList<Connection> pool = new LinkedList<Connection>();
	//初始化池
	static {
        for (int i = 0; i < POOL_SIZE; i++) {
            pool.addLast(SqlConnectImpl.fetchConnection());
        }
	}
	public DBPoolSemaphore() {
		//提供10个许可证，即同一时刻最多有10个线程可以拿到许可证
		this.useful = new Semaphore(10);
		//记录被拿到许可证的线程个数
		this.useless = new Semaphore(0);
	}
	
	/*归还连接*/
	public void returnConnect(Connection connection) throws InterruptedException {
		if(connection!=null) {
			System.out.println("当前有"+useful.getQueueLength()+"个线程等待数据库连接!!"
					+"可用连接数："+useful.availablePermits());
			useless.acquire();//不可用连接数先减一，相当于准备归还连接
			synchronized (pool) {
				pool.addLast(connection);//正确归还连接
			}
			useful.release();//可用连接数再加一，相当于已经归还连接
		}
	}

	/**
	 * 个人见解（senyang）：这样做会有一个问题，如果N个线程同时调用takeConnect()方法，useful.acquire()通过原子操作使useful的信号量减少了N，
	 * 此时时间静止，那么这个时候对于信号量而言，连接已经被取走，而对于连接池而言，连接还没有被取走。这样就造成了信号量和连接池的不同步。
	 * 但是可以换一个角度考虑这个问题，就可以理解这个方法的巧妙之处了，takeConnect()方法中，将useful.acquire()看做是准备工作，useless.release()
	 * 方法看做是工作已经完成，returnConnect方法亦是同理。
	 *  也可以将目光落在方法的最后一行，takeConnect()方法中的useless.release()控制了returnConnect()方法的useless.acquire()；returnConnect()方法中的
	 *  useful.release()限制了takeConnect()方法的useful.acquire()；
	 */

	/*从池子拿连接*/
	public Connection takeConnect() throws InterruptedException {
		useful.acquire();//可用连接数先减一，相当于准备拿走连接
		Connection connection;
		//可能有多个线程来同时获取连接，需要加锁
		synchronized (pool) {
			connection = pool.removeFirst();//正确取走连接
		}
		useless.release();//不可用连接数再加一，相当于已经拿走连接
		return connection;
	}
	
}
