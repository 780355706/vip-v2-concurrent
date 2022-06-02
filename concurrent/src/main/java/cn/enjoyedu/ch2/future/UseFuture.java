package cn.enjoyedu.ch2.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


/**
 *类说明：演示Future等的使用
 * 1. 拿到返回值；
 * 2. 取消任务；
 */
public class UseFuture {
	
	
	/*实现Callable接口，允许有返回值*/
	private static class UseCallable implements Callable<Integer>{
		private int sum;
		@Override
		public Integer call() throws Exception {
			System.out.println("Callable子线程开始计算！");
			/**
			 * 如果加了这个sleep方法，在外部线程调用cancel()方法的时候，sleep()方法会抛出异常；
			 * 因为cancel()方法实施中断的本质就是调用interrupt()方法，而sleep()在睡眠的过程中又
			 * 可以捕捉到InterruptedException。所以，即使不对"是否中断"做判断线程也会终止。
			 * 所以，中断线程的方法有2种：1. 调用interrupt()方法标记中断；2. 阻塞式方法抛出中断
			 * 异常。
			 */
			/**
			 * senyang: 不能在此调用sleep()方法，因为在外部调用cancel()方法时，实际上就是调用interrupt()方法，
			 * sleep()方法又可以响应InterruptedException，调用cancel()后会导致线程中断
			 */
//			Thread.sleep(1000);
	        for(int i=0 ;i<5000;i++){
	        	//判断是否中断，即判断是否收到了cancel()的指令
				//和cancel()方法搭配使用
	        	if(Thread.currentThread().isInterrupted()) {
					System.out.println("Callable子线程计算任务中断！");
					return null;
				}
	            sum=sum+i;
				System.out.println("sum="+sum);
	        }  
	        System.out.println("Callable子线程计算结束！结果为: "+sum);  
	        return sum; 
		}
	}
	
	public static void main(String[] args) 
			throws InterruptedException, ExecutionException {
		//构造一个任务
		UseCallable useCallable = new UseCallable();
		//包装任务
		FutureTask<Integer> futureTask = new FutureTask<>(useCallable);
		Random r = new Random();
		//将任务交给Thread实例处理
		new Thread(futureTask).start();

		Thread.sleep(1);
		if(r.nextInt(100)>50){
			System.out.println("Get UseCallable result = "+futureTask.get());
		}else{
			System.out.println("Cancel................. ");
			//和Thread.currentThread().isInterrupted()搭配使用
			futureTask.cancel(true);
		}
	}

}
