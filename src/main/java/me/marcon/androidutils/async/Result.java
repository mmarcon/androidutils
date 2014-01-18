package me.marcon.androidutils.async;

/**
 * User: mmarcon
 * Date: 1/15/14
 * Time: 7:27 AM
 */
public class Result<T> {
    private T result = null;
    private Throwable error = null;

    public Result() {
        this(null, null);
    }

    public Result(T result) {
        this(result, null);
    }

    public Result(T result, Throwable error) {
        this.result = result;
        this.error = error;
    }

    public boolean ok(){
        return error == null;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }
}
