package com.fengwenyi.erwin_api_rate_limiter;

/**
 * @author Erwin Feng
 * @since 2019-07-03 17:47
 */
public class TokenBucketExample {

    public static void main(String[] args) {
        final  TokenBucket tokenBucket = new TokenBucket();
        for (int i=0;i< 200;i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            new Thread(tokenBucket::buy).start();
        }
    }

}
