package com.fengwenyi.erwin_api_rate_limiter;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;

/**
 * @author Erwin Feng
 * @since 2019-07-03 17:20
 */
public class MyTests {

    @Test
    public void testRateLimiter() {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(100));
        // 指定每秒放1个令牌
        RateLimiter limiter = RateLimiter.create(5);
        for (int i = 1; i < 50; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            //acquire(int permits)函数主要用于获取permits个令牌，并计算需要等待多长时间，进而挂起等待，并将该值返回
            Double acquire = null;
            if (i == 1) {
                acquire = limiter.acquire(1);
            } else if (i == 2) {
                acquire = limiter.acquire(10);
            } else if (i == 3) {
                acquire = limiter.acquire(2);
            } else if (i == 4) {
                acquire = limiter.acquire(20);
            } else {
                acquire = limiter.acquire(2);
            }
            executorService.submit(new Task("获取令牌成功，获取耗：" + acquire + " 第 " + i + " 个任务执行"));
        }
    }

    class Task implements Runnable {
        String str;
        public Task(String str) {
            this.str = str;
        }
        @Override
        public void run() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            System.out.println(sdf.format(new Date()) + " | " + Thread.currentThread().getName() + str);
        }
    }

    public boolean access(String userId) {

        // 当一个请求 Token进入 access() 方法后，先计算计算该请求的 Token Bucket 的 key；


        /*
        如果这个 Token Bucket 在 Redis 中不存在，那么就新建一个 Token Bucket，
        然后设置该 Bucket 的 Token 数量为最大值减一(去掉了这次请求获取的 Token）。
        在初始化 Token Bucket 的时候将 Token 数量设置为最大值这一点在后面还有讨论；
        * */

        /*
        如果这个 Token Bucket 在 Redis 中存在，
        而且其上一次加入 Token 的时间到现在时间的时间间隔大于 Token Bucket 的 interval，
        那么也将 Bucket 的 Token 值重置为最大值减一；
         */

        /*
        如果 Token Bucket 上次加入 Token 的时间到现在时间的时间间隔没有大于 interval，
        那么就计算这次需要补充的 Token 数量，将补充过后的 Token 数量更新到 Token Bucket 中。
         */

        /*String key = genKey(userId);

        try (Jedis jedis = jedisPool.getResource()) {
            Map<String, String> counter = jedis.hgetAll(key);

            if (counter.size() == 0) {
                TokenBucket tokenBucket = new TokenBucket(System.currentTimeMillis(), limit - 1);
                jedis.hmset(key, tokenBucket.toHash());
                return true;
            } else {
                TokenBucket tokenBucket = TokenBucket.fromHash(counter);

                long lastRefillTime = tokenBucket.getLastRefillTime();
                long refillTime = System.currentTimeMillis();
                long intervalSinceLast = refillTime - lastRefillTime;

                long currentTokensRemaining;
                if (intervalSinceLast > intervalInMills) {
                    currentTokensRemaining = limit;
                } else {
                    long grantedTokens = (long) (intervalSinceLast / intervalPerPermit);
                    System.out.println(grantedTokens);
                    currentTokensRemaining = Math.min(grantedTokens + tokenBucket.getTokensRemaining(), limit);
                }

                tokenBucket.setLastRefillTime(refillTime);
                assert currentTokensRemaining >= 0;
                if (currentTokensRemaining == 0) {
                    tokenBucket.setTokensRemaining(currentTokensRemaining);
                    jedis.hmset(key, tokenBucket.toHash());
                    return false;
                } else {
                    tokenBucket.setTokensRemaining(currentTokensRemaining - 1);
                    jedis.hmset(key, tokenBucket.toHash());
                    return true;
                }
            }
        }*/
        return false;
    }

}
