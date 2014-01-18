package me.marcon.androidutils.async;

import java.util.concurrent.Callable;

/**
 * User: mmarcon
 * Date: 1/15/14
 * Time: 7:26 AM
 */
public abstract class Task<T> implements Callable<Result<T>> {

    @Override
    public Result<T> call() throws Exception {
        return execute();
    }

    public abstract Result<T> execute();
}
