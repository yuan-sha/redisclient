package com.ys;

import redis.clients.jedis.Jedis;

public class CheckCode {
    public static void main(String[] args) {
        // 获取用户的电话号码
        String phoneNo = "3852149569";

        // 获取提交的验证码
        String formCode = "205574";

        // 判断用户提交的验证码是否正确

        String codeKey = phoneNo + ":code";
        Jedis jedis = null;

        try {
            jedis = new Jedis("localhost", 6379);
            // 获取redis中保存的验证码 电话号码：code
            String redisCode = jedis.get(codeKey);
            if ( redisCode == null ) {
                // 验证不成功
                System.out.println("fail1");
            } else {
                // 对获取结果进行校验
                if (redisCode.equals(formCode)) {
                    // 校验成功
                    System.out.println("success");
                } else {
                    // 校验失败
                    System.out.println("fail2");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }
}
