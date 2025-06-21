package com.aivle.agriculture.domain.calculate.utils;

import com.aivle.agriculture.global.exception.CustomException;
import com.aivle.agriculture.global.response.ErrorCode;

import java.util.Map;

public class MapUtils {
    public static <K, V> V getRequired(Map<K, V> map, K key) {
        V value = map.get(key);
        if (value == null) {
            throw new CustomException(ErrorCode.BAD_REQUEST, "Required key '" + key.toString() + "' is missing");
        }
        return value;
    }
}
