package com.your.mock.httpclient.matchers;

import org.hamcrest.Matcher;

import java.util.ArrayList;

/**
 * 集合匹配器
 *
 * @author zhangzhen
 * @date 2019-12-18 18:55
 */
public class MatchersList<T> extends ArrayList<Matcher<T>> {

    /**
     * 条件匹配
     *
     * @param value
     * @return
     */
    public boolean allMatches(T value) {
        return this.stream().allMatch(m -> m.matches(value));
    }

}
