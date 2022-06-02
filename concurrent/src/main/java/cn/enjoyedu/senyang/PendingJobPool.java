package cn.enjoyedu.senyang;

public class PendingJobPool {

    private static class InnerPendingJobPool {
        public static PendingJobPool pool = new PendingJobPool();
    }

    /**
     * 提供一个单实例对象
     * @return
     */
    public PendingJobPool getInstance() {
        return InnerPendingJobPool.pool;
    }

    public void registerJob(String jobName, Integer jobLength) {

    }
}
