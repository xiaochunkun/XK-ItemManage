package cn.xkmc6.xkitemmanage.internal.meta;

import de.tr7zw.changeme.nbtapi.NBTCompoundList;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTList;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import lombok.Getter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @author 小坤
 * @date 2022/02/06 13:11
 */
@MetaKey(key = "attribute")
public class AttributeMeta extends Meta {

    private final Map<Slot, Map<Attributes, Object>> attribute = new HashMap<>();

    public AttributeMeta(ConfigurationSection root) {
        super(root);
        attribute.clear();
        root.getKeys(false).forEach(slot -> {
            ConfigurationSection section = root.getConfigurationSection(slot);
            if (section == null) return;
            Map<Attributes, Object> map = new HashMap<>();
            section.getKeys(false).forEach(att -> {
                map.put(att.asAttributes(), section.get(att));
            });
            attribute.put(slot.asSlot(), map);
        });
    }

    public ItemStack build(ItemStack itemStack) {
        NBTItem nbtItem = new NBTItem(itemStack);
        NBTCompoundList attList = nbtItem.getCompoundList("AttributeModifiers");
        NBTListCompound att = attList.addCompound();
        attribute.forEach((slot, attributesStringMap) -> attributesStringMap.forEach((k, v) -> {
            if (v instanceof String) {
                if (((String) v).endsWith("%")) {
                    att.setDouble("Amount", NumberConversions.toDouble(((String) v).substring(0, ((String) v).length() - 1)) / 100.0);
                    att.setInteger("Operation", 1);
                } else {
                    att.setDouble("Amount", NumberConversions.toDouble(v));
                    att.setInteger("Operation", 0);
                }
                UUID uuid = UUID.randomUUID();
                att.setString("AttributeName", k.attributeName);
                att.setString("Name", k.attributeName);
                att.setLong("UUIDLeast", uuid.getLeastSignificantBits());
                att.setLong("UUIDMost", uuid.getMostSignificantBits());
                if (!slot.equals(Slot.ALL)) {
                    att.setString("Slot", slot.slot);
                }
            }
        }));
        return nbtItem.getItem();
    }

    public enum Attributes {
        /**
         * 从上倒下 攻击力 攻速 最大生命 移动速度 击退距离 护甲 护甲韧性 AI追踪距离 幸运
         */
        ATTACK_DAMAGE("generic.attackDamage"),
        ATTACK_SPEED("generic.attackSpeed"),
        MAX_HEALTH("generic.maxHealth"),
        MOVEMENT_SPEED("generic.movementSpeed"),
        KNOCKBACK_RESISTANCE("generic.knockbackResistance"),
        ARMOR("generic.armor"),
        ARMOR_TOUGHNESS("generic.armorToughness"),
        AI_FOLLOW_RANGE("generic.followRange"),
        LUCK("generic.luck");

        private final String attributeName;

        Attributes(String attributeName) {
            this.attributeName = attributeName;
        }

        public String getAttributeName() {
            return attributeName;
        }

        @Override
        public String toString() {
            return attributeName;
        }

        public static Attributes asAttributes(String str) {
            switch (str) {
                case "damage":
                case "attackDamage":
                    return ATTACK_DAMAGE;
                case "attackSpeed":
                case "damageSpeed":
                    return ATTACK_SPEED;
                case "health":
                case "maxHealth":
                    return MAX_HEALTH;
                case "speed":
                case "movementSpeed":
                    return MOVEMENT_SPEED;
                case "knockback":
                case "knockbackResistance":
                    return KNOCKBACK_RESISTANCE;
                case "armor":
                    return ARMOR;
                case "toughness":
                case "armorToughness":
                    return ARMOR_TOUGHNESS;
                case "follow":
                case "followRange":
                    return AI_FOLLOW_RANGE;
                case "luck":
                    return LUCK;
                default:
                    return null;
            }
        }
    }

    public enum Slot {
        /**
         * 全部字面意思 从上到下 头 胸甲 腿 脚 主手 副手
         */
        HEAD("head", 1),
        CHEST("chest", 2),
        LEGS("legs", 3),
        FEET("feet", 4),
        MAIN_HAND("mainhand", 0),
        OFF_HAND("offhand", -1),
        ALL("all", 999);

        private final String slot;
        private final int slotNum;

        Slot(String slot, int slotNum) {
            this.slot = slot;
            this.slotNum = slotNum;
        }

        public int getSlotNum() {
            return slotNum;
        }

        public String getSlot() {
            return slot;
        }

        @Override
        public String toString() {
            return "Slot{" +
                    "slot='" + slot + '\'' +
                    ", slotNum=" + slotNum +
                    '}';
        }

        public static Slot asSlot(String str) {
            switch (str) {
                case "head":
                    return HEAD;
                case "chest":
                    return CHEST;
                case "legs":
                    return LEGS;
                case "feet":
                    return FEET;
                case "mainhand":
                    return MAIN_HAND;
                case "offhand":
                    return OFF_HAND;
                default:
                    return ALL;
            }
        }

        public static Slot asSlot(int slotNum) {
            switch (slotNum) {
                case -1:
                case 5:
                    return OFF_HAND;
                case 0:
                    return MAIN_HAND;
                case 1:
                    return HEAD;
                case 2:
                    return CHEST;
                case 3:
                    return LEGS;
                case 4:
                    return FEET;
                default:
                    return ALL;
            }
        }
    }

}
