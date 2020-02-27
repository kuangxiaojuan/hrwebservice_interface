package dhr.utils;

public class StringAddUtil {
	
	/**
	 * 注意： 字符串限定是数字和大写字母的组合，如果有其他字母需求，可以对方法做相应的改动
	 * 该方法可以实现字符串加1
	 * @param str
	 * @return
	 */
	public static String StringAdd(String str){
		char[] chars = str.toCharArray();
		
		for(int i=0;i<chars.length;i++){
			
		}
		
		for (int i = chars.length-1; i >= 0; i--) {
			
				chars[i] = charAdd(chars[i]);
				
				if("0".equals(String.valueOf(chars[i]))){
					continue;
				}else{
					break;
				}
			
		}
		
		StringBuilder strBuilder = new StringBuilder();
		for(int i=0 ;i<chars.length;i++){
			strBuilder.append(String.valueOf(chars[i]));
		}
		
		String result = strBuilder.toString();
		
		
		return result;
	} 
	
	public static char charAdd(char c){
		
		char j = (char) ( c+1 );
		int k = j;
		
		if(k>57 && j<=65){
			c =  'A';
		}
		if(k>90){
			c = '0';
		}
		if(k>48 && k <=57 || k>65 && k<=90){
			c = j;
		}
		
		return c; 
	}

}
