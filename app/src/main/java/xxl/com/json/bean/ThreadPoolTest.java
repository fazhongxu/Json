package xxl.com.json.bean;

import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by xxl on 2017/12/4.
 */

public class ThreadPoolTest {

    private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue<>(128);//容量

    private static ThreadPoolExecutor threadPoolExecutor;

    static {
        /**
         * @param corePoolSize the number of threads to keep in the pool, even
         *        if they are idle, unless {@code allowCoreThreadTimeOut} is set
         *                     核心线程数
         * @param maximumPoolSize the maximum number of threads to allow in the
         *        pool
         *                        最大线程数
         * @param keepAliveTime when the number of threads is greater than
         *        the core, this is the maximum time that excess idle threads
         *        will wait for new tasks before terminating.
         *                      空闲线程存活时间
         * @param unit the time unit for the {@code keepAliveTime} argument
         *            存活时间的单位
         * @param workQueue the queue to use for holding tasks before they are
         *        executed.  This queue will hold only the {@code Runnable}
         *        tasks submitted by the {@code execute} method.
         *                  线程队列
         */
        //设置为非守护线程
        threadPoolExecutor = new ThreadPoolExecutor(4,
                10, 60, TimeUnit.SECONDS, sPoolWorkQueue, new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                Thread thread = new Thread();
                thread.setDaemon(true);//设置为非守护线程
                return thread;
            }
        });
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            final int finalI = i;
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        System.out.println(finalI +"下载完成"+Thread.currentThread().getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
