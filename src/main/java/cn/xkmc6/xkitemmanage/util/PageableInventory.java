package cn.xkmc6.xkitemmanage.util;

import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * 可分页的Inventory
 * <p>
 * 我强烈建议你可以通过继承该类的方式进行编写
 * 如果直接通过 {@link InventoryHolder} 进行判断, <b>也许会出现<b/>监听至其他的Gui
 * <p/>
 *
 * <p>
 * 使用方法:
 * <code>
 * // PageableInventory pageInv = new PageableInventory(容器大小, 容器名字, 物品列表, 初始页数, 每页所显示的数量);
 * PageableInventory pageInv = new PageableInventory(54, "&aTest", 物品列表, 1, 45);
 * player.openInventory(pageInv.getInventory());
 * </code>
 * <p/>
 *
 * @author 小坤
 * @date 2022/03/06 20:31
 */
public class PageableInventory implements InventoryHolder {
    private final Inventory inventory;
    private final List<ItemStack> items;
    private final int itemsPerPage;
    private int page;
    private int maxPage;

    public PageableInventory(int size, String title, List<ItemStack> items) {
        this(size, title, items, 1, 45);
    }

    public PageableInventory(int size, String title, ItemStack... itemStacks) {
        this(size, title, Lists.newArrayList(itemStacks), 1, 45);
    }

    /**
     * 可分页的Inventory
     *
     * @param size         容器大小
     * @param title        容器标题
     * @param items        所有容器内的物品
     * @param page         初始页数, 默认为 1
     * @param itemsPerPage 每页显示数量, 默认为 45
     */
    public PageableInventory(int size, String title, List<ItemStack> items, int page, int itemsPerPage) {
        this.page = page;
        this.inventory = Bukkit.createInventory(this, size, String.format(title.replaceAll("&", "§"), page));
        this.items = items;
        this.itemsPerPage = itemsPerPage;
        build();
    }

    public PageableInventory(Inventory inventory, List<ItemStack> items, int page, int itemsPerPage) {
        this.inventory = inventory;
        this.items = items;
        this.page = page;
        this.itemsPerPage = itemsPerPage;
        build();
    }

    private void build() {
        maxPage = (items.size() / itemsPerPage) + 1;
        if (!items.isEmpty()) {
            getItemsByPage(page).stream().filter(items -> !isAir(items)).forEach(inventory::addItem);
        }
    }

    public List<ItemStack> getItemsByPage(int page) {
        if (page <= 0) {
            return getItemsByPage(1);
        }
        List<ItemStack> subList;
        int pageSize = itemsPerPage;
        int totalCount = items.size();
        if (pageSize >= totalCount) {
            subList = items;
        } else {
            int fromIndex = Math.min(pageSize * (page - 1), totalCount);
            int endIndex = Math.min(pageSize * page, totalCount);
            subList = items.subList(fromIndex, endIndex);
        }
        return subList;
    }

    public void refresh() {
        if (!isAir(inventory.getItem(0))) {
            for (int i = 0; i < itemsPerPage; i++) {
                if (isAir(inventory.getItem(i))) break;
                inventory.setItem(i, new ItemStack(Material.AIR));
            }
        }

        if (!items.isEmpty()) {
            for (ItemStack itemStack : getItemsByPage(page)) {
                if (!isAir(itemStack)) {
                    inventory.addItem(itemStack);
                }
            }
        }
    }

    public void nextPage() {
        if (page >= maxPage) {
            page = maxPage;
            return;
        }
        this.page += 1;
        refresh();
    }

    public void prevPage() {
        if (page <= 1) {
            page = 1;
            return;
        }
        this.page -= 1;
        refresh();
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
            return;
        }
        if (page > maxPage) {
            this.page = maxPage;
            return;
        }
        this.page = page;
        refresh();
    }

    public int getItemsPerPage() {
        return itemsPerPage;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    private boolean isAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType().equals(Material.AIR);
    }
}
