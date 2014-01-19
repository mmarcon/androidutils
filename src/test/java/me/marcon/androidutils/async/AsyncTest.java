package me.marcon.androidutils.async;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: mmarcon
 * Date: 1/18/14
 * Time: 11:00 AM
 */
public class AsyncTest {

    protected Task<Long> task1;
    protected Task<Long> task2;

    private long fakeTimer;

    protected Async async;

    @Before
    public void setup(){

        fakeTimer = System.currentTimeMillis();

        task1 = new Task<Long>() {
            @Override
            public Result<Long> execute() {
                return new Result<Long>(++fakeTimer);
            }
        };

        task2 = new Task<Long>() {
            @Override
            public Result<Long> execute() {
                return new Result<Long>(++fakeTimer);
            }
        };

        async = new Async();
    }

    @Test
    public void parallelTasksTest() {
        long t0 = ++fakeTimer;
        List<Result<Long>> results = async.parallelTasks(task1, task2);
        long t1 = ++fakeTimer;

        assertThat(results.size(), equalTo(2));
        assertTrue(results.get(0).ok());
        assertTrue(results.get(1).ok());
        assertTrue(results.get(0).getResult() > t0);
        assertTrue(results.get(0).getResult() < t1);
    }

//    @Test
//    public void nonBlockingParallelTasksTest() {
//        //Come up with a better test, this isn't really testing anything...
//        final long t0 = ++fakeTimer;
//        async.parallelTasks(new ResultListener<Long>() {
//            @Override
//            public void done(List<Result<Long>> results) {
//                assertThat(results.size(), equalTo(2));
//                assertTrue(results.get(0).ok());
//                assertTrue(results.get(1).ok());
//                assertTrue(results.get(0).getResult() > t0);
//            }
//        }, task1, task2);
//    }

    @Test
    public void seriesTasksTest() {
        long t0 = ++fakeTimer;
        List<Result<Long>> results = async.seriesTasks(task1, task2);
        long t1 = ++fakeTimer;

        assertThat(results.size(), equalTo(2));
        assertTrue(results.get(0).ok());
        assertTrue(results.get(1).ok());
        assertTrue(results.get(0).getResult() > t0);
        assertTrue(results.get(0).getResult() < t1);
        assertTrue(results.get(0).getResult() < results.get(1).getResult());
    }

//    @Test
//    public void nonBlockingSeriesTasksTest() {
//        //Come up with a better test, this isn't really testing anything...
//        final long t0 = ++fakeTimer;
//        async.parallelTasks(new ResultListener<Long>() {
//            @Override
//            public void done(List<Result<Long>> results) {
//                assertThat(results.size(), equalTo(2));
//                assertTrue(results.get(0).ok());
//                assertTrue(results.get(1).ok());
//                assertTrue(results.get(0).getResult() > t0);
//                assertTrue(results.get(0).getResult() < results.get(1).getResult());
//            }
//        }, task1, task2);
//    }
}
