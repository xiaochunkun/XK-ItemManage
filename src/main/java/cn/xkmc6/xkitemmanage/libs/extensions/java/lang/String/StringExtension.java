package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.String;

import cn.xkmc6.xkitemmanage.internal.meta.AttributeMeta;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

import java.lang.String;

@Extension
public class StringExtension {
    public static void print(@This String key) {
        System.out.println(key);
    }

    public static AttributeMeta.Attributes asAttributes(@This String key){
        return AttributeMeta.Attributes.asAttributes(key);
    }

    public static AttributeMeta.Slot asSlot(@This String key){
        return AttributeMeta.Slot.asSlot(key);
    }
}