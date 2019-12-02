package com.gsm.util;

/**
 * 随机数
 */
public class RandomNumUtils {
    /**
     * 获得 4 位随机数
     * @return
     */
    public static Integer getRandom4(){
        int max = 9999;
        int min = 1000;
        int i = (int) (Math.random() * (max - min + 1) + min);
        return i;
    }
    /**
     * 获得 6 位随机数
     * @return
     */
    public static Integer getRandom6(){
        int max = 99999;
        int min = 10000;
        int i = (int) (Math.random() * (max - min + 1) + min);
        return i;
    }
}
