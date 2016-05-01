package org.hope6537.redis;

import redis.clients.jedis.Jedis;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by hope6537 on 15/10/26.
 */
public class RedisClient {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                while(true){
                    //Jedis jedis = RedisUtil.getJedis();
                    Jedis jedis = new Jedis("127.0.0.1");
                    if (jedis != null) {
                        jedis.publish("hope", String.valueOf(Thread.currentThread() + ":" + System.currentTimeMillis()));
                    }
                    jedis.close();
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        executorService.submit(()-> System.out.println("calling"));
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            executorService.shutdownNow();
        }
    System.exit(0);
    }
}
