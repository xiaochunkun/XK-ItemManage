package cn.xkmc6.xkitemmanage.libs.extensions.java.lang.Object;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 小坤
 * @date 2022/02/21 11:21
 */
@Extension
public class ObjectExtension {
    public static <T> List<T> castList(@This Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }


}
