package com.seven.util;

import com.seven.core.GlobalException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 最爱吃小鱼
 */
public class Assert {


    /**
     * 判断对象是否为空
     * @param bean
     * @param message
     */
    public static void isNull(String message, Object bean) {
        if (bean == null) {
            throw new GlobalException(message);
        }
    }

    /**
     * 判断参数是否不为空值
     * @param message
     * @param params
     */
    public static void isBlank(String message, Object... params) {
        if (ArrayUtils.isEmpty(params)) return;

        for (Object param : params) {
            if (param == null || StringUtils.isBlank(param.toString())) {
                throw new GlobalException(message);
            }
        }
    }

    /**
     * 判断参数是否在指定的范围内
     * @param message
     * @param value
     * @param scopeValues
     */
    public static void inScope(String message, int value, int... scopeValues) {
        if (ArrayUtils.isEmpty(scopeValues)) return;

        for (int t : scopeValues) {
            if (t == value) {
                return;
            }
        }
        throw new GlobalException(message);
    }

    /**
     * 判断参数是否在指定的范围内
     * @param message
     * @param value
     * @param scopeValues
     */
    public static void inScopeIgnoreCase(String message, String value, String... scopeValues) {
        if (ArrayUtils.isEmpty(scopeValues) || value == null) return;

        for (String scopeValue : scopeValues) {
            if (value.equalsIgnoreCase(scopeValue)) {
                return;
            }
        }
        throw new GlobalException(message);
    }


    /**
     * 检测参数是否正常传递
     *
     * @param bean
     */
    public static void noParams (Object bean) {
        isNull("没有传递任何参数", bean);
    }
}
