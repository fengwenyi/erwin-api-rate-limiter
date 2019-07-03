package com.fengwenyi.erwin_api_rate_limiter;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Erwin Feng
 * @since 2019-07-03 17:47
 */
public class TokenBucket {
    private AtomicInteger phoneNumbers = new AtomicInteger(0);

    private final static int LIMIT = 100;

    private RateLimiter rateLimiter = RateLimiter.create(10);

    private final  int saleLimit;

    public TokenBucket() {
        this(LIMIT);
    }

    public TokenBucket(int saleLimit) {
        this.saleLimit = saleLimit;
    }

    public int buy() {

        Stopwatch stopwatch = Stopwatch.createStarted();
        boolean success = rateLimiter.tryAcquire(10, TimeUnit.MILLISECONDS);
        if (success) {
            int phoneNum = phoneNumbers.getAndIncrement();
            if (phoneNum>=saleLimit) {
                throw new IllegalStateException("not any phone can be sale,please wait to next time.");
            }
            handleOrder();
            System.out.println(Thread.currentThread()+"user  get the phone "+phoneNum+",ELT:"+stopwatch.stop());

            return phoneNum;
        }else {
            stopwatch.stop();
            throw new RuntimeException("Sorry,occur excepiton when buy phone");
        }
    }

    private void handleOrder() {
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
