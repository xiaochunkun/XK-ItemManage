package cn.xkmc6.xkitemmanage.internal;

import cn.xkmc6.xkitemmanage.ItemManage;
import cn.xkmc6.xkitemmanage.internal.Display;
import cn.xkmc6.xkitemmanage.internal.meta.Meta;
import cn.xkmc6.xkitemmanage.manage.ClassManage;
import com.cryptomorin.xseries.XMaterial;
import javafx.util.Pair;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 小坤
 * @date 2022/02/27 13:09
 */
public class ItemData {
    @Getter
    private String display = null;
    @Getter
    private XMaterial material = XMaterial.AIR;
    @Getter
    private Map<String, String> name = new HashMap<>();
    @Getter
    private Map<String, Object> lore = new HashMap<>();
    @Getter
    private List<Meta> metas = new ArrayList<>();

    public ItemData(ConfigurationSection root) {
        root.getKeys(false).stream().filter(key -> root.get(key) != null).forEach(key -> {
            switch (key) {
                case "display":
                    this.display = root.getString("display", "null");
                    break;
                case "material":
                    this.material = root.getString("material", "air").asXMaterial();
                    break;
                case "name":
                    ConfigurationSection section = root.getConfigurationSection("name");
                    if (section != null) {
                        section.getKeys(false).forEach(k -> {
                            name.put(k, section.getString(k));
                        });
                    }
                    break;
                case "lore":
                    section = root.getConfigurationSection("lore");
                    if (section != null) {
                        section.getKeys(false).forEach(k -> {
                            lore.put(k, section.get(k));
                        });
                    }
                    break;
                case "meta":
                    section = root.getConfigurationSection("meta");
                    if (section != null) {
                        section.getKeys(false).forEach(k -> {
                            ClassManage classManage = ItemManage.getInstance().getClassManage();
                            if (classManage.getClasses().containsKey(k)) {
                                Class<? extends Meta> meta = classManage.getClasses().get(k);
                                try {
                                    Constructor<? extends Meta> constructor = meta.getConstructor(ConfigurationSection.class);
                                    Meta o = constructor.newInstance(root);
                                    metas.add(o);
                                } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    break;
                case "data":
                    ClassManage classManage = ItemManage.getInstance().getClassManage();
                    Class<? extends Meta> meta = classManage.getClasses().get("data");
                    try {
                        Constructor<? extends Meta> constructor = meta.getConstructor(ConfigurationSection.class);
                        Meta o = constructor.newInstance(root);
                        metas.add(o);
                    } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        });
    }

    public ItemStack getItem() {
        ItemStack itemStack = material.parseItem();
        if (itemStack == null) return XMaterial.AIR.parseItem();
        if (display == null) return itemStack;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) return itemStack;
        String disName = (String) display.asDisplay().getDisplay().get("name");
        List<String> nameKeys = disName.asNameKey();
        for (String key : nameKeys) {
            if (name.containsKey(key)) {
                disName = disName.replace("...", "").replace("<" + key + ">", name.get(key));
            }
        }
        itemMeta.setDisplayName(disName.replaceAll("&", "§"));
        List<String> loreName = display.asDisplay().getDisplay().get("lore").castList(String.class);
        List<Pair<String, Boolean>> loreKeys = loreName.asLoreKeys();
        loreName.clear();
        for (Pair<String, Boolean> pair : loreKeys) {
            if (lore.containsKey(pair.getKey())) {
                if (lore.get(pair.getKey()) instanceof String) {
                    loreName.add((String) lore.get(pair.getKey()));
                }
                if (pair.getValue()) {
                    if (lore.get(pair.getKey()) instanceof List) {
                        loreName.addAll(lore.get(pair.getKey()).castList(String.class));
                    }
                } else {
                    if (lore.get(pair.getKey()) instanceof List) {
                        loreName.add(lore.get(pair.getKey()).castList(String.class).get(0));
                    }
                }
            }
        }
        loreName = loreName.stream().map(l -> l.replaceAll("&","§")).collect(Collectors.toList());
        itemMeta.setLore(loreName);
        itemStack.setItemMeta(itemMeta);
        for (Meta meta : metas) {
            itemStack = meta.build(itemStack);
        }
        if (display.asDisplay().getDisplay().containsKey("meta")){
            ConfigurationSection root = (ConfigurationSection) display.asDisplay().getDisplay().get("root");
            ConfigurationSection section = (ConfigurationSection) display.asDisplay().getDisplay().get("meta");
            if (section != null) {
                section.getKeys(false).forEach(k -> {
                    ClassManage classManage = ItemManage.getInstance().getClassManage();
                    if (classManage.getClasses().containsKey(k)) {
                        Class<? extends Meta> meta = classManage.getClasses().get(k);
                        try {
                            Constructor<? extends Meta> constructor = meta.getConstructor(ConfigurationSection.class);
                            Meta o = constructor.newInstance(root);
                            metas.add(o);
                        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        if (display.asDisplay().getDisplay().containsKey("data")){
            ConfigurationSection root = (ConfigurationSection) display.asDisplay().getDisplay().get("root");
            ClassManage classManage = ItemManage.getInstance().getClassManage();
            Class<? extends Meta> meta = classManage.getClasses().get("data");
            try {
                Constructor<? extends Meta> constructor = meta.getConstructor(ConfigurationSection.class);
                Meta o = constructor.newInstance(root);
                metas.add(o);
            } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return itemStack;
    }

    @Override
    public String toString() {
        return "ItemData{" +
                "display=" + display +
                ", material=" + material +
                ", name=" + name +
                ", lore=" + lore +
                ", metas=" + metas +
                '}';
    }
}
