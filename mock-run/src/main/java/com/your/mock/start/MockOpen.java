package com.your.mock.start;

import com.your.common.util.SpringEnvironmentUtils;

/**
 * describe:开关
 *
 * @author zhangzhen
 * @date 2019-12-23 14:12
 */
class MockOpen {

    /**
     * 是否执行MOCK
     *
     * @return
     */
    public static boolean isMock() {
        if (SpringEnvironmentUtils.isTest() || SpringEnvironmentUtils.isDev()) {
            return true;
        }
        return false;
    }

}
