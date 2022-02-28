package cn.xkmc6.xkitemmanage.manage;

import cn.xkmc6.xkitemmanage.internal.meta.*;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 小坤
 * @date 2022/02/27 10:13
 */
public class ClassManage {
    @Getter
    private final Map<String,Class<? extends Meta>> classes = new HashMap<>();

    public void registerClass(){
        classes.put("attribute", MetaAttribute.class);
        classes.put("color", MetaColor.class);
        classes.put("data", MetaData.class);
        classes.put("enchantment", MetaEnchantment.class);
        classes.put("itemflag", MetaItemFlag.class);
        classes.put("potion",MetaPotion.class);
        classes.put("shiny",MetaShiny.class);
    }
}
