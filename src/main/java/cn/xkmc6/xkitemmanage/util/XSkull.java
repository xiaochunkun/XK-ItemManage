package cn.xkmc6.xkitemmanage.util;

import com.cryptomorin.xseries.SkullUtils;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.sun.istack.internal.NotNull;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * @author 小坤
 * @date 2022/02/20 15:21
 */
public class XSkull extends SkullUtils {
    public static ItemBuilder.SkullTexture getSkin(@NotNull ItemMeta skull) {
        SkullMeta meta = (SkullMeta) skull;
        GameProfile profile = null;

        try {
            profile = (GameProfile) PROFILE_GETTER.invoke(meta);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        if (profile != null && !profile.getProperties().get("textures").isEmpty()) {
            for (Property property : profile.getProperties().get("textures")) {
                if (!property.getValue().isEmpty()) {
                    return new ItemBuilder.SkullTexture(property.getValue(), profile.getId());
                }
            }
        }

        return null;
    }
}
