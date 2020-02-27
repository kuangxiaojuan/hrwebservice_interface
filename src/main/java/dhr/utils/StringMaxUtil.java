package dhr.utils;

import java.util.List;

public class StringMaxUtil {
	/**
	 * 比较规则：最大位数的字符串最大，如果位数相同，比较对应的ASCII码值
	 * @param list
	 * @return
	 */
	public static String getMaxString(List<String> list){
		String result = null;
		int len = 0;
		for(int i=0;i<list.size();i++){
			int j = list.get(i).length();
			if(j>len){
				len = j;
			}
		}
		
		for(int i=0;i<list.size();i++){
			if(list.get(i).length()<len){
				list.remove(i);
				i--;
			}
		}
		
		result = list.get(0);
		for(int i=0;i<list.size();i++){
			char[] c1 = result.toCharArray();
			char[] c2 = list.get(i).toCharArray();
			for(int j=0;j<len;j++){
				int m = c1[j];
				int n = c2[j];
				if(m==n){
					continue;
				}
				if(m<n){
					result = list.get(i);
					break;
				}
				if(m>n){
					break;
				}
			}
		}
		
		return result;
	}

}
