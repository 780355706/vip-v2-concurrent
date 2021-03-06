package cn.enjoyedu.vo;

import cn.enjoyedu.framework.vo.ITaskProcesser;
import cn.enjoyedu.framework.vo.TaskResult;
import cn.enjoyedu.framework.vo.TaskResultType;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Random;

/**
 *类说明：一个实际任务类，将数值加上一个随机数，并休眠随机时间
 */
@Component//加这个标签，而不去new一个任务的原因是在这个操作中可能会操作数据库，如果我们想管理事务，那么这个Bean必须要让spring来管理，才能实现声明式事务管理。这种情况下taskExecute方法上面就可以使用@Transactional注解
public class OrderTask implements ITaskProcesser<Integer,Integer> {
	public final static String JOB_NAME = "订单处理";

	@Override
	public TaskResult<Integer> taskExecute(Integer data) {
		Random r = new Random();
		int flag = r.nextInt(500);
		SleepTools.ms(flag);
		if(flag<=300) {//正常处理的情况
			Integer returnValue = data.intValue()+flag;
			return new TaskResult<Integer>(TaskResultType.Success,returnValue);
		}else if(flag>301&&flag<=400) {//处理失败的情况
			return new TaskResult<Integer>(TaskResultType.Failure,-1,"Failure");
		}else {//发生异常的情况
			try {
				throw new RuntimeException("异常发生了！！");
			} catch (Exception e) {
				return new TaskResult<Integer>(TaskResultType.Exception,
						-1,e.getMessage());
			}
		}
	}

}
