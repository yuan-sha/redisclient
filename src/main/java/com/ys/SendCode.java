package com.ys;

import redis.clients.jedis.Jedis;

import java.util.Random;

public class SendCode {
    public static void main(String[] args) {
        String phoneNo = "3852149569";

        // 获取指定的电话号码发送的验证码次数
        Jedis jedis = null;

        try {
            jedis = new Jedis("localhost",6379);

            // key设计
            // rowkey设计
            String countKey = phoneNo + ":count";
            String codeKey  = phoneNo + ":code";
            String cnt = jedis.get(countKey);
            if ( cnt == null ) {
                // 没有发送过验证码
                jedis.setex(countKey, 60*60*24,"1");

                // 生成验证码
                StringBuilder code = new StringBuilder();
                for ( int i = 0; i < 6; i++ ) {
                    code.append( new Random().nextInt(10) );
                }

                // 在缓存数据库中增加验证码
                jedis.setex(codeKey, 60 * 2, code.toString());

                // 累加验证码发送次数
                //jedis.incr(countKey);

                // 返回成功的消息

            } else {
                if ( Integer.valueOf(cnt) < 3 ) {
                    // 发送验证码

                    // 生成验证码
                    StringBuilder code = new StringBuilder();
                    for ( int i = 0; i < 6; i++ ) {
                        code.append( new Random().nextInt(10) );
                    }

                    // 在缓存数据库中增加验证码
                    jedis.setex(codeKey, 60 * 2, code.toString());

                    // 累加验证码发送次数
                    jedis.incr(countKey);

                    // 返回成功的消息

                } else {
                    // 次数超出了限制
                    // 返回消息

                }
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        }

    }
}
