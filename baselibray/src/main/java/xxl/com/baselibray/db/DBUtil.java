package xxl.com.baselibray.db;

/**
 * Created by xxl on 2018/1/17.
 */

public class DBUtil {
    public static Object getType(String type){
        String value = null;
        if (type.contains("String")){
            return "Text";
        } else if (type.contains("int")) {
            return "Integer";
        }else if (type.contains("boolean")){
            return "Boolean";
        }else if (type.contains("short")){
            return "Short";
        }else if (type.contains("byte")){
            return "Byte";
        }else if (type.contains("float")){
            return "Float";
        } else if (type.contains("double")){
            return "Double";
        }else if (type.contains("long")) {
            return "Long";
        }
        return value;
    }
}
