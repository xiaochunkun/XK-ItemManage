package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.String;

import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.lang.String;

@Extension
public class StringExtension {
    public static void print(@This String key) {
        System.out.println(key);
    }

}