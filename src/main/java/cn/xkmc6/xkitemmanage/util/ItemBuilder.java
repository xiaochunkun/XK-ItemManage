package cn.xkmc6.xkitemmanage.util;

import com.cryptomorin.xseries.SkullUtils;
import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.changeme.nbtapi.NBTItem;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 小坤
 * @date 2022/02/20 11:23
 */
public class ItemBuilder {
    public static class SkullTexture {
        public String textures;
        public UUID uuid;

        SkullTexture(String textures) {
            this(textures, UUID.randomUUID());
        }

        SkullTexture(String textures, UUID uuid) {
            this.textures = textures;
            this.uuid = uuid;
        }
    }


    @Getter
    private Material material;
    @Getter
    @Setter
    private int amount;
    @Getter
    @Setter
    private short damage;
    @Getter
    @Setter
    private String name;
    @Getter
    private final List<String> lore;
    @Getter
    private final List<ItemFlag> flags;
    @Getter
    private final Map<Enchantment, Integer> enchants;
    @Getter
    private final List<Pattern> patterns;
    @Getter
    @Setter
    private Color color;
    @Getter
    private final List<PotionEffect> potions;
    @Getter
    @Setter
    private PotionData potionData;
    @Getter
    @Setter
    private EntityType spawnType;
    @Getter
    @Setter
    private String skullOwner;
    @Getter
    @Setter
    private SkullTexture skullTexture;
    @Getter
    @Setter
    private boolean isUnbreakable;
    @Getter
    private final int customModelData;
    @Getter
    private final ItemMeta originMeta;

    private ItemBuilder(Builder builder) {
        material = builder.material;
        amount = builder.amount;
        damage = builder.damage;
        name = builder.name;
        lore = builder.lore;
        flags = builder.flags;
        patterns = builder.patterns;
        enchants = builder.enchants;
        color = builder.color;
        potions = builder.potions;
        potionData = builder.potionData;
        spawnType = builder.spawnType;
        skullOwner = builder.skullOwner;
        skullTexture = builder.skullTexture;
        isUnbreakable = builder.isUnbreakable;
        customModelData = builder.customModelData;
        originMeta = builder.originMeta;
    }

    public void setMaterial(XMaterial material) {
        this.material = material.parseMaterial();
        if (this.material == null) {
            this.material = Material.STONE;
        }
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void hideAll() {
        flags.addAll(Arrays.asList(ItemFlag.values()));
    }

    public void shiny() {
        flags.add(ItemFlag.HIDE_ENCHANTS);
        enchants.put(Enchantment.LURE, 1);
    }

    public void colored() {
        if (name != null) {
            name = name.replaceAll("&", "§");
        }
        if (lore.size() > 0) {
            List<String> newLore = lore.stream().map(l -> l.replaceAll("&", "§")).collect(Collectors.toList());
            lore.clear();
            lore.addAll(newLore);
        }
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        colored();
        NBTItem item = new NBTItem(itemStack);
        itemStack.setAmount(amount);
        if (damage != 0) {
            itemStack.setDurability(damage);
        }
        ItemMeta itemMeta;
        if (originMeta != null) {
            itemMeta = originMeta;
        } else if (itemStack.getItemMeta() != null) {
            itemMeta = itemStack.getItemMeta();
        } else {
            return itemStack;
        }
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        flags.forEach(itemMeta::addItemFlags);
        if (itemMeta instanceof EnchantmentStorageMeta) {
            enchants.forEach((e, l) -> ((EnchantmentStorageMeta) itemMeta).addStoredEnchant(e, l, true));
        } else {
            enchants.forEach((e, l) -> itemMeta.addEnchant(e, l, true));
        }

        if (itemMeta instanceof LeatherArmorMeta) {
            ((LeatherArmorMeta) itemMeta).setColor(color);
        }

        if (itemMeta instanceof PotionMeta) {
            ((PotionMeta) itemMeta).setColor(color);
            potions.forEach(p -> ((PotionMeta) itemMeta).addCustomEffect(p, true));
            if (potionData != null) {
                ((PotionMeta) itemMeta).setBasePotionData(potionData);
            }
        }

        if (itemMeta instanceof SkullMeta) {
            if (skullOwner != null) {
                ((SkullMeta) itemMeta).setOwner(skullOwner);
            }
            if (skullTexture != null) {
                GameProfile gameProfile = new GameProfile(skullTexture.uuid, null);
                gameProfile.getProperties().put("textures", new Property("textures", skullTexture.textures));
                Field field;
                try {
                    field = itemMeta.getClass().getDeclaredField("profile");
                    field.setAccessible(true);
                    field.set(itemMeta, gameProfile);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException | SecurityException noSuchFieldException) {
                    noSuchFieldException.printStackTrace();
                }
            }
        }

        itemMeta.setUnbreakable(isUnbreakable);

        if (spawnType != null && itemMeta instanceof BannerMeta) {
            patterns.forEach(p -> ((BannerMeta) itemMeta).addPattern(p));
        }

        if (customModelData != -1) {
            itemMeta.setCustomModelData(customModelData);
        }

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static final class Builder {
        private Material material = Material.STONE;
        private int amount = 1;
        private short damage = 0;
        private String name = null;
        private final List<String> lore = new ArrayList<>();

        private final List<ItemFlag> flags = new ArrayList<>();
        private final Map<Enchantment, Integer> enchants = new HashMap<>();
        private final List<Pattern> patterns = new ArrayList<>();
        private Color color = null;
        private final List<PotionEffect> potions = new ArrayList<>();
        private PotionData potionData = null;
        private EntityType spawnType = null;
        private String skullOwner = null;
        private SkullTexture skullTexture = null;
        private boolean isUnbreakable = false;
        private int customModelData = -1;
        private ItemMeta originMeta = null;

        public Builder() {
        }

        public Builder(Material val) {
            material = val;
        }

        public Builder(@NotNull XMaterial val) {
            material = val.parseMaterial();
            if (material == null) {
                material = Material.STONE;
            }
            damage = material.getMaxDurability();
        }

        public Builder(@NotNull ItemStack itemStack) {
            material = itemStack.getType();
            amount = itemStack.getAmount();
            damage = itemStack.getDurability();
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta == null) return;
            originMeta = itemMeta.clone();
            name = itemMeta.getDisplayName();
            if (itemMeta.getLore() != null) {
                lore.addAll(itemMeta.getLore());
            }
            flags.addAll(itemMeta.getItemFlags());
            if (itemMeta instanceof EnchantmentStorageMeta) {
                enchants.putAll(((EnchantmentStorageMeta) itemMeta).getStoredEnchants());
            } else {
                enchants.putAll(itemMeta.getEnchants());
            }
            if (itemMeta instanceof LeatherArmorMeta) {
                color = ((LeatherArmorMeta) itemMeta).getColor();
            }
            if (itemMeta instanceof PotionMeta) {
                color = ((PotionMeta) itemMeta).getColor();
                potions.addAll(((PotionMeta) itemMeta).getCustomEffects());
                potionData = ((PotionMeta) itemMeta).getBasePotionData();
            }
            if (itemMeta instanceof SkullMeta) {
                if (((SkullMeta) itemMeta).hasOwner()) {
                    skullOwner = ((SkullMeta) itemMeta).getOwner();
                }
                skullTexture = XSkull.getSkin(itemMeta);
            }
            customModelData = itemMeta.getCustomModelData();
            isUnbreakable = itemMeta.isUnbreakable();
            if (itemMeta instanceof SpawnEggMeta) {
                spawnType = ((SpawnEggMeta) itemMeta).getSpawnedType();
            }

            if (itemMeta instanceof BannerMeta && ((BannerMeta) itemMeta).getPatterns().size() > 0) {
                patterns.addAll(((BannerMeta) itemMeta).getPatterns());
            }
        }

        public Builder material(Material val) {
            material = val;
            return this;
        }

        public Builder material(@NotNull XMaterial val) {
            material = val.parseMaterial();
            if (material == null) {
                material = Material.STONE;
            }
            return this;
        }

        public Builder amount(int val) {
            amount = val;
            return this;
        }

        public Builder damage(short val) {
            damage = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder lore(String... val) {
            lore.addAll(Arrays.asList(val));
            return this;
        }

        public Builder flag(ItemFlag... flag) {
            flags.addAll(Arrays.asList(flag));
            return this;
        }

        public Builder enchant(Map<Enchantment, Integer> enchant) {
            enchants.putAll(enchant);
            return this;
        }

        public Builder pattern(Pattern... pattern){
            patterns.addAll(Arrays.asList(pattern));
            return this;
        }

        public Builder potion(PotionEffect... potion){
            potions.addAll(Arrays.asList(potion));
            return this;
        }

        public Builder color(Color val) {
            color = val;
            return this;
        }

        public Builder potionData(PotionData val) {
            potionData = val;
            return this;
        }

        public Builder spawnType(EntityType val) {
            spawnType = val;
            return this;
        }

        public Builder skullOwner(String val) {
            skullOwner = val;
            return this;
        }

        public Builder skullTexture(SkullTexture val) {
            skullTexture = val;
            return this;
        }

        public Builder isUnbreakable(boolean val) {
            isUnbreakable = val;
            return this;
        }

        public Builder hideAll() {
            flags.addAll(Arrays.asList(ItemFlag.values()));
            return this;
        }

        public Builder shiny() {
            flags.add(ItemFlag.HIDE_ENCHANTS);
            enchants.put(Enchantment.LURE, 1);
            return this;
        }

        public ItemBuilder build() {
            return new ItemBuilder(this);
        }
    }
}
