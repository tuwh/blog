package com.uncub.blog.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionsUtils {
    public static final <T> Set<T> listToSet(List<T> list){
        if (list == null) return new HashSet<T>();
        Set<T> set = new HashSet<T>();
        for (T t : list){
            set.add(t);
        }
        return set;
    }
}
