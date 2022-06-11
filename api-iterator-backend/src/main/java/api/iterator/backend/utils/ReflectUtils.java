package api.iterator.backend.utils;

import java.lang.reflect.Field;

/**
 * Reflect utils implementation
 */
public class ReflectUtils {
    public static void setNotNullProperties(Object sourceObj, Object targetObj) {
        try {
            for (Field sourceField : sourceObj.getClass().getDeclaredFields()) {
                sourceField.setAccessible(true);
                Object value = sourceField.get(sourceObj);
                if (value != null) {
                    Field targetField = targetObj.getClass().getDeclaredField(sourceField.getName());
                    targetField.setAccessible(true);
                    targetField.set(targetObj, value);
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException ex) {
            ex.printStackTrace();
        }
    }
}
