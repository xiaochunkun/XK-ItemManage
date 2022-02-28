package cn.xkmc6.xkitemmanage.libs.extensions.java.util.List;

import javafx.util.Pair;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/27 21:37
 */
@Extension
public class ListExtension {
    public static <E> void helloWorld(@This List<E> list) {
        System.out.println("hello world!");
    }

    public static <E> List<Pair<String,Boolean>> asLoreKeys(@This List<String> list){
        List<Pair<String,Boolean>> keys = new ArrayList<>();
        list.forEach(key -> keys.addAll(key.asLoreKey()));
        return keys;
    }
}
