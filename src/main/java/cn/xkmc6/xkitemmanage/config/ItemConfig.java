package cn.xkmc6.xkitemmanage.config;

import cn.xkmc6.xkitemmanage.ItemManage;
import manifold.ext.props.rt.api.var;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author 小坤
 * @date 2022/02/06 11:19
 */
public class ItemConfig {

    public void loadConfig() {
        File file = new File(ItemManage.instance.getDataFolder(), "item");
        find(file).forEach(f -> {

        });
    }

    private List<File> find(File file) {
        List<File> list = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(file.listFiles())).forEach(f -> {
            if (f.isDirectory()) {
                find(f);
            }
            if (f.isFile()) {
                list.add(f);
            }
        });
        return list;
    }
}
