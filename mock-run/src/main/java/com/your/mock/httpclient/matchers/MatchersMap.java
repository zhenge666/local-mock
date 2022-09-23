package com.your.mock.httpclient.matchers;

import org.hamcrest.Matcher;

import java.util.HashMap;

/**
 * 集合匹配器
 *
 * @author zhangzhen
 * @date 2019-12-18 18:55
 */
public class MatchersMap<K, V> extends HashMap<K, MatchersList<V>> {

    /**
     * 条件匹配
     *
     * @param name
     * @param value
     * @return
     */
    public boolean matches(K name, V value) {
        return this.containsKey(name) && this.get(name).allMatches(value);
    }

    /**
     * 添加条件
     *
     * @param name
     * @param value
     */
    public void put(K name, Matcher<V> value) {
        this.putIfAbsent(name, new MatchersList<>());
        this.get(name).add(value);
    }

}
