package com.kwan.base;


import com.kwan.base.config.APPCacheConfig;
import com.kwan.base.util.SPUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mr.Kwan on 2016-5-13.
 */
public class BaseConfiguration {

    /**
     * 缓存数据(列表)
     */
    private final Map<String, Collection<? extends Object>> dataMap = Collections.synchronizedMap(new HashMap<String, Collection<? extends Object>>());

    /**
     * 缓存数据(基本)
     **/
    private final Map<String, Object> basicMap = Collections.synchronizedMap(new HashMap<String, Object>());

    /**
     * 向列表数据缓存中存入列表数据
     *
     * @param key   键
     * @param datas 数据集合
     */
    public void cacheData(String key, Collection<? extends Object> datas) {
        dataMap.put(key, datas);
    }

    /**
     * 从列表数据缓存中取出特定列表数据
     *
     * @param key 键
     */
    public Collection<? extends Object> getData(String key) {
        return dataMap.get(key);
    }

    /**
     * 向基本数据缓存中存入数据
     *
     * @param key   键
     * @param value 值
     */
    public void cacheBasicData(String key, Object value) {

        //Memory缓存
        basicMap.put(key, value);
        //SharePrefrence缓存

        //是否第一次登陆
        if (key.equals(APPCacheConfig.KEY_IS_FIRST_CACHE))
            SPUtil.setIsFirstIn((Boolean) value);
        else if (key.equals(APPCacheConfig.KEY_IS_LOGIN))//当前是否登陆
            SPUtil.setIsLogin((Boolean) value);


    }

    /**
     * 从基本数据缓存中取出数据
     *
     * @param key 键
     * @return 对象
     */
    public Object getBasicData(String key) {

        //Memory中取
        Object temp = basicMap.get(key);
        //SharePrefrence中取
        if (temp == null) {

            if (key.equals(APPCacheConfig.KEY_IS_FIRST_CACHE))
                temp = SPUtil.getIsFirstIn();
            else if (key.equals(APPCacheConfig.KEY_IS_LOGIN))
                temp = SPUtil.getIsLogin();

        }

        return temp;
    }


}
