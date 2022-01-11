package com.ys;

import redis.clients.jedis.Jedis;

import java.util.*;

public class TestRedis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);

        String pong = jedis.ping();
        System.out.println(pong);

        Set<String> keys = jedis.keys("*");
        for(Iterator iterator = keys.iterator();iterator.hasNext();){
            String key = (String) iterator.next();
            System.out.println(key);
        }
        System.out.println(jedis.exists("k2"));
        System.out.println(jedis.ttl("key1"));

        System.out.println(jedis.get("k1"));
        jedis.set("k4","k4_Redis");
        System.out.println("----------------------------------------");
        jedis.mset("str1","v1","str2","v2","str3","v3");
        System.out.println(jedis.mget("str1","str2","str3"));
        //list
        List<String> list = jedis.lrange("mylist",0,-1);
        for (String element : list) {
            System.out.println(element);
        }
        //set
        jedis.sadd("orders","jd001");
        jedis.sadd("orders","jd002");
        jedis.sadd("orders","jd003");
        Set<String> set1 = jedis.smembers("orders");
        for (Iterator iterator = set1.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
        jedis.srem("orders","jd002");
        //Hash
        jedis.hset("hash1","userName","lisi");
        System.out.println(jedis.hget("hash1","userName"));
        Map<String,String> map = new HashMap<String,String>();
        map.put("telphone","13810169999");
        map.put("address","atguigu");
        map.put("email","abc@163.com");
        jedis.hmset("hash2",map);
        List<String> result = jedis.hmget("hash2", "telphone","email");
        for (String element : result) {
            System.out.println(element);
        }

        //zet
        jedis.zadd("zset01",60d,"v1");
        jedis.zadd("zset01",70d,"v2");
        jedis.zadd("zset01",80d,"v3");
        jedis.zadd("zset01",90d,"v4");
        Set<String> s1 = jedis.zrange("zset01",0,-1);
        for (Iterator iterator = s1.iterator(); iterator.hasNext();) {
            String string = (String) iterator.next();
            System.out.println(string);
        }
    }
}
