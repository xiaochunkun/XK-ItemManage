package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import lombok.Getter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/06 11:19
 */
public class ItemConfig {
    private final File file = new File(ItemManage.getInstance().getDataFolder(), "item");

    @Getter
    private final List<File> fileList = new ArrayList<>();

    public void loadConfig() {
        if (!file.exists()) {
            file.mkdirs();
            ItemManage.getInstance().saveResource("item/default.yml", true);
            ItemManage.getInstance().saveResource("item/test/test.yml", true);
        }
        fileList.clear();
        find(file);
    }

    private void find(File file) {
        File[] files = file.listFiles();
        if (files != null){
            Arrays.stream(files).forEach(f -> {
                if (f.isDirectory()) {
                    find(f);
                }
                if (f.isFile()) {
                    fileList.add(f);
                }
            });
        }
    }

}
