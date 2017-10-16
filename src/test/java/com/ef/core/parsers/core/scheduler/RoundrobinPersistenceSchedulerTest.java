package com.ef.core.parsers.core.scheduler;

import com.ef.core.schedulers.RoundrobinPersistenceScheduler;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by mohamed on 16/10/17.
 */
@RunWith(JUnit4.class)
public class RoundrobinPersistenceSchedulerTest {

    @Test
    public void initTest() throws InterruptedException {
        RoundrobinPersistenceScheduler scheduler = new RoundrobinPersistenceScheduler(1);
        Assert.assertEquals(scheduler.getThreadHashMap().size(), 1);
        scheduler.setFinishSignal();
    }

}
