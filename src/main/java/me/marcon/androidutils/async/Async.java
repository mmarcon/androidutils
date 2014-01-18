package me.marcon.androidutils.async;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * User: mmarcon
 * Date: 1/14/14
 * Time: 8:17 AM
 */
public class Async {

    private static final int MAX_TIMEOUT = 2;

    private ExecutorService threadPool;
    private int maxTimeout = MAX_TIMEOUT;

    private static Async staticAsync = null;

    public Async() {
        threadPool = Executors.newCachedThreadPool();
    }

    public void setThreadPool(ThreadPoolExecutor threadPool) {
        this.threadPool = threadPool;
    }

    public int getMaxTimeout() {
        return maxTimeout;
    }

    public void setMaxTimeout(int maxTimeoutInSeconds) {
        this.maxTimeout = maxTimeoutInSeconds;
    }

    public void setKeepAliveTime(long keepAliveTimeInSeconds) {
        ((ThreadPoolExecutor) threadPool).setKeepAliveTime(keepAliveTimeInSeconds, TimeUnit.SECONDS);
    }

    public <T> List<Result<T>> parallelTasks(Task<T>... tasks) {
        List<Future<Result<T>>> futures = new LinkedList<Future<Result<T>>>();
        List<Result<T>> results = new LinkedList<Result<T>>();
        for (Task<T> t : tasks) {
            futures.add(threadPool.submit(t));
        }
        for (Future<Result<T>> f : futures) {
            Result<T> result = null;
            try {
                result = f.get(maxTimeout, TimeUnit.SECONDS);
            } catch (Exception e) {
                f.cancel(true);
                result = new Result<T>();
                result.setError(e);
            }
            results.add(result);
        }
        return results;
    }

    public <T> List<Result<T>> seriesTasks(Task<T>... tasks) {
        List<Result<T>> results = new LinkedList<Result<T>>();
        for (Task<T> t : tasks) {
            Future<Result<T>> future = threadPool.submit(t);
            Result<T> result = null;
            try {
                result = future.get(maxTimeout, TimeUnit.SECONDS);
            } catch (Exception e) {
                future.cancel(true);
                result = new Result<T>();
                result.setError(e);
            }
            results.add(result);
            if (!result.ok()) {
                break;
            }
        }
        return results;
    }

    public <T> void parallelTasks(final ResultListener<T> resultListener, final Task<T>... tasks) {
        Runnable submitter = new Runnable() {
            @Override
            public void run() {
                List<Result<T>> results = parallelTasks(tasks);
                resultListener.done(results);
            }
        };
        threadPool.submit(submitter);
    }

    public <T> void seriesTasks(final ResultListener<T> resultListener, final Task<T>... tasks) {
        Runnable submitter = new Runnable() {
            @Override
            public void run() {
                List<Result<T>> results = seriesTasks(tasks);
                resultListener.done(results);
            }
        };
        threadPool.submit(submitter);
    }

    private static Async getStaticAsync() {
        synchronized (Async.class) {
            if (staticAsync == null) {
                staticAsync = new Async();
            }
        }
        return staticAsync;
    }

    public static <T> void parallel(ResultListener<T> resultListener, Task<T>... tasks) {
        getStaticAsync().parallelTasks(resultListener, tasks);
    }

    public static <T> void series(ResultListener<T> resultListener, Task<T>... tasks) {
        getStaticAsync().seriesTasks(resultListener, tasks);
    }
}
