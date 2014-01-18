package me.marcon.androidutils.async;

import java.util.List;

/**
 * User: mmarcon
 * Date: 1/15/14
 * Time: 7:28 AM
 */
public interface ResultListener<T> {
    public void done(List<Result<T>> results);
}
