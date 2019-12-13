package com.gsm.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

public class StringDemo {
    @Test
    public void test() {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set("wjq", "handsome");

        String wjq = jedis.get("wjq");
        System.out.println("wjq = " + wjq);
    }
}
