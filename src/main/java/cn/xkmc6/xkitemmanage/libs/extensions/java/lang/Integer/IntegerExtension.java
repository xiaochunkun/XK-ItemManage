package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.Integer;

import cn.xkmc6.xkitemmanage.internal.meta.AttributeMeta;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;

/**
 * @author 小坤
 * @date 2022/02/21 23:44
 */
@Extension
public class IntegerExtension {
    public static AttributeMeta.Slot asSlot(@This Integer key){
        return AttributeMeta.Slot.asSlot(key);
    }
}
