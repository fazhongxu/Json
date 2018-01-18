package xxl.com.baselibray.db;

/**
 * Created by xxl on 2018/1/17.
 */

public class DBUtil {


    public static String getTableName(Class clazz){
       return clazz.getSimpleName();
    }

    public static String getColumnType(String type){
        String value = null;
        if (type.contains("String")){
            return "text";
        } else if (type.contains("int")) {
            return "integer";
        }else if (type.contains("boolean")){
            return "boolean";
        }else if (type.contains("short")){
            return "short";
        }else if (type.contains("byte")){
            return "byte";
        }else if (type.contains("float")){
            return "float";
        } else if (type.contains("double")){
            return "double";
        }else if (type.contains("long")) {
            return "long";
        }else if (type.contains("char")){
            return "varchar";
        }
        return value;
    }
}
