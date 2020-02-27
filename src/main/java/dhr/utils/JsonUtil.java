package dhr.utils;

import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *<p>json工具类</p>
 * @author Administrator
 * @version V1.0
 * @see 相关的类
 */
public class JsonUtil {
	
	private static Gson gson = new Gson();
	private static Gson gsonBulider = new GsonBuilder()  
			  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
			  .create();
	
	/**
	 * 任意对象转换json
	 * @param obj
	 * @return
	 */
	public static String obj2json(Object obj) {
		return gsonBulider.toJson(obj);
	}
	
	/**
	 * json转换对象
	 * @param json
	 * @param cls
	 * @return
	 */
	public static Object json2Obj(String json,Class<?> cls){	
		return gson.fromJson(json,cls);
	}
	
	/**
	 * json转换对象集合
	 * @param jso
	 * @return
	 */
	public static <T> T json2ObjList(String json,TypeToken<T> token){	
		return gson.fromJson(json,token.getType());
	}
	
	 /** 
     * JSON转成list集合 
     *  
     * @param gsonString 
     * @param cls 
     * @return 
     */  
    public static <T> List<T> json2ObjList(String gsonStr, Class<T> cls) {  
        List<T> list = null;  
        if (gson != null) {  
            list = gson.fromJson(gsonStr, new TypeToken<List<T>>() {  
            }.getType());  
        }  
        return list;  
    } 
	
	/**
	 * json转换map
	 * @param jso
	 * @return
	 */
	public static Map<String, Object> json2Map(String json){	
		return gson.fromJson(json,new TypeToken<Map<String, Object>>(){}.getType());
	}
	
	/**
	 * Json转换List<map>
	 * @param json
	 * @return
	 */
	public static List<Map<String, Object>> json2MapList(String json){
		return gson.fromJson(json,new TypeToken<List<Map<String, Object>>>(){}.getType());
	}
	
	/**
	 * Json转换Arrays
	 * @param json
	 * @return
	 */
	public static Object[] json2Arrays(String json){
		
		return gson.fromJson(json,new TypeToken<Object[]>(){}.getType());
	}
	
	public static void main(String[] args) {
		String json = "[{\"id\":\"1000\"}]";
		List<Map<String, Object>> listmap = JsonUtil.json2MapList(json);
		
		System.out.println(JsonUtil.obj2json(listmap));
	}
}