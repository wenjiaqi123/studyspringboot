package com.gsm.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisUtils {
    @Autowired
    private RedisTemplate<String, Object> rt;

    // ====================通用==========================

    /**
     * 指定缓存失效时间
     *
     * @param key  key
     * @param time 时间（秒）
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                rt.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 key 过期时间
     *
     * @param key key不能为空
     * @return 时间（秒） 返回0表示永久有效
     */
    public long getExpire(String key) {
        Long expire = rt.getExpire(key);
        return expire;
    }

    /**
     * 判断 key 是否存在
     *
     * @param key
     * @return true 存在 false 不存在
     */
    public boolean hasKey(String key) {
        try {
            Boolean hasKey = rt.hasKey(key);
            return hasKey;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除 key
     *
     * @param key 可以传入一个或多个
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                rt.delete(key[0]);
            } else {
                rt.delete(CollectionUtils.arrayToList(key));
            }
        }
    }

    // ====================String=======================

    /**
     * 存储 K V
     *
     * @param key
     * @param value
     * @return true 成功  false 失败
     */
    public boolean set(String key, Object value) {
        try {
            rt.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 存入 KV 并设置 缓存时间（秒）
     *
     * @param key
     * @param value
     * @param time  时间（秒） time <= 0将设置为无限期
     * @return 成功true 失败false
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                rt.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 K 对应的 V 值
     *
     * @param key key
     * @return value
     */
    public Object get(String key) {
        Object o = key == null ? null : rt.opsForValue().get(key);
        return o;
    }

    /**
     * 递增
     *
     * @param key K
     * @param i   要增加几（需要大于0）
     * @return
     */
    public long incr(String key, long i) {
        if (i < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        Long increment = rt.opsForValue().increment(key, i);
        return increment;
    }

    /**
     * 递减
     *
     * @param key K
     * @param i   要减少几（需要小于0）
     * @return
     */
    public long decr(String key, long i) {
        if (i < 0) {
            throw new RuntimeException("递减少因子必须大于0");
        }
        Long decrement = rt.opsForValue().decrement(key, i);
        return decrement;
    }

    // =====================Hash=======================

    /**
     * 向 Hash 表里插入 键K 项item 值V
     *
     * @param key   K
     * @param item  item
     * @param value V
     * @return 成功true 失败false
     */
    public boolean hset(String key, String item, Object value) {
        try {
            rt.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向 Hash 表里插入 键K 项item 值V 并设置过期时间，
     *
     * @param key
     * @param item
     * @param value
     * @param time  如果已经存在过期时间，这里将会替换原有时间
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            rt.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置 K map
     *
     * @param key K
     * @param map 对应多个键值
     * @return 成功true  失败false
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            rt.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置 K map 并设置过期事案件
     *
     * @param key  K
     * @param map  对应多个键值
     * @param time 过期时间
     * @return 成功true  失败false
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            rt.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据 K,field 获取 Hash 值
     *
     * @param key  Key,不能为 null
     * @param item item,不能为 null
     * @return
     */
    public Object hget(String key, String item) {
        Object o = rt.opsForHash().get(key, item);
        return o;
    }

    /**
     * 获取 Hash 所有键值
     *
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        Map<Object, Object> map = rt.opsForHash().entries(key);
        return map;
    }

    /**
     * 删除 Hash 中值
     *
     * @param key  K
     * @param item 可以一个或多个，不能为 null
     */
    public void hdel(String key, Object... item) {
        rt.opsForHash().delete(key, item);
    }

    /**
     * 判断 Hash 中是否有该值
     *
     * @param key  K 不能为 null
     * @param item item 不能为 null
     * @return true存在  false不存在
     */
    public boolean hHasKey(String key, String item) {
        Boolean hasKey = rt.opsForHash().hasKey(key, item);
        return hasKey;
    }

    /**
     * Hash 递增，不过不存在，就会创建一个，并把新增后的值返回
     *
     * @param key
     * @param item
     * @param i    要增加几（大于0）
     * @return
     */
    public double hincr(String key, String item, double i) {
        Double increment = rt.opsForHash().increment(key, item, i);
        return increment;
    }

    /**
     * Hash 递减
     *
     * @param key
     * @param item
     * @param i    要减少几（大于0）
     * @return
     */
    public double hdecr(String key, String item, double i) {
        Double increment = rt.opsForHash().increment(key, item, -i);
        return increment;
    }

    // =====================Set=======================

    /**
     * 将数据 存入 Set
     *
     * @param key    K
     * @param values 可以是一个或多个
     * @return 成功个数
     */
    public long sSet(String key, Object... values) {
        try {
            Long count = rt.opsForSet().add(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将数据 存入 Set ，并设置时间（秒）
     *
     * @param key
     * @param time   时间（秒）
     * @param values
     * @return 成功个数
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = rt.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据 K 获取 Set 中所有的值
     *
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            Set<Object> members = rt.opsForSet().members(key);
            return members;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据 K V 从一个 Set 中查询，该 V 是否存在
     *
     * @param key   K
     * @param value V
     * @return 存在true  不存在false
     */
    public boolean sHasKey(String key, Object value) {
        try {
            Boolean hasKey = rt.opsForSet().isMember(key, value);
            return hasKey;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取 Set 长度
     *
     * @param key
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            Long size = rt.opsForSet().size(key);
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除 Set 中值为 V 的
     *
     * @param key
     * @param values 可以是一个或多个
     * @return 移除的个数
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = rt.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // =====================List=======================

    /**
     * 获取 List 从 start 到 end 的 V 【0 到 -1 代表所有值】
     *
     * @param key
     * @param start
     * @param end
     * @return 0 到 -1 代表所有值
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            List<Object> list = rt.opsForList().range(key, start, end);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 List 长度
     *
     * @param key
     * @return
     */
    public long lGetListSize(String key) {
        try {
            Long size = rt.opsForList().size(key);
            return size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 index 获取 List 中的值
     *
     * @param key
     * @param index 索引 双向数据索引
     *              -4  -3  -2  -1      index < 0 时，-1 表示表尾
     *              0  1   2   3       index > 0 时，0  表示表头
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            Object o = rt.opsForList().index(key, index);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 插入数据，默认右侧
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value) {
        boolean b = lRightSet(key, value);
        return b;
    }

    /**
     * 插入数据，默认右侧，并设置有效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        boolean b = lRightSet(key, value, time);
        return b;
    }

    /**
     * 将 List 值插入 List
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            rt.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将 List 值插入 List，并设置有效时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            rt.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key,time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在 List 右侧插入
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lRightSet(String key, Object value) {
        try {
            rt.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在 List 左侧插入
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lLeftSet(String key, Object value) {
        try {
            rt.opsForList().leftPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在 List 右侧插入，并设置缓存时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lRightSet(String key, Object value, long time) {
        try {
            rt.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 在 List 左侧插入，并设置缓存时间
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lLeftSet(String key, Object value, long time) {
        try {
            rt.opsForList().leftPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改 List 中的值
     * @param key   K
     * @param index 索引
     * @param value V
     * @return
     */
    public boolean lUpdateIndex(String key,long index,Object value){
        try {
            rt.opsForList().set(key,index,value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}