package com.caf.yeb.common.util.bean;

/**
 * @author chenhaohao
 * @version 1.0
 */
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BeanUtils {
    public BeanUtils() {
    }

    public static <T> T toBean(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        } else {
            CopyOptions copyOptions = new CopyOptions();
            copyOptions.setIgnoreError(true);
            return BeanUtil.toBean(source, clazz, copyOptions);
        }
    }

    public static <T, S> List<T> toBeans(List<S> sources, Class<T> clazz) {
        return CollUtil.isEmpty(sources) ? Collections.emptyList() : (List)sources.stream().map((s) -> {
            return toBean(s, clazz);
        }).collect(Collectors.toList());
    }
}

