package zzq;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
public class SensitivewordFilter {
	@SuppressWarnings("rawtypes")
	public Map sensitiveWordMap = null;
	public static int minMatchTYpe = 1;      //最小匹配规则
	public static int maxMatchType = 2;      //最大匹配规则

	// 构造函数，初始化敏感词库

	public SensitivewordFilter(){
		sensitiveWordMap = DFA.getDFA().dfaMap(DFA.getDFA().init());
	}

	// 判断文字是否包含敏感字符

	public boolean isContaintSensitiveWord(String txt,int matchType){
		boolean flag = false;
		for(int i = 0 ; i < txt.length() ; i++){
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType); //判断是否包含敏感字符
			if(matchFlag > 0){    //大于0存在，返回true
				flag = true;
			}
		}
		return flag;
	}

	// 获取文字中的敏感词

	public Set<String> getSensitiveWord(String txt , int matchType){
		Set<String> sensitiveWordList = new HashSet<String>();
		for(int i = 0 ; i < txt.length() ; i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(txt.substring(i, i+length));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}

		return sensitiveWordList;
	}
	/**
	 * 汉字
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public Set<String> getSensitiveWordChinese(String txt , int matchType,String source){
		Set<String> sensitiveWordList = new HashSet<String>();
		for(int i = 0,j=0 ; i < txt.length(); i++){
			int length = CheckSensitiveWord(txt, i, matchType);    //判断是否包含敏感字符
			if(length > 0){    //存在,加入list中
				sensitiveWordList.add(source.substring(i/3, (i+length)/3));
				i = i + length - 1;    //减1的原因，是因为for会自增
			}
		}

		return sensitiveWordList;
	}

	// 替换敏感字字符

	public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
		String resultTxt = txt;
		Set<String> set = getSensitiveWord(txt, matchType);     //获取所有的敏感词
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	//获取替换字符串

	private String getReplaceChars(String replaceChar,int length){
		String resultReplace = replaceChar;
		for(int i = 1,j=1; i < length ; i++){

			if(j==2){
				resultReplace+=",";
				j=0;
			}else{
				++j;
				resultReplace += replaceChar;
			}

		}
		//System.out.println(resultReplace+"--------");
		return resultReplace;
	}

	// 检查文字中是否包含敏感字符，检查规则如下：<br>

	@SuppressWarnings({ "rawtypes"})
	public int CheckSensitiveWord(String txt,int beginIndex,int matchType){
		boolean  flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
		int matchFlag = 0;     //匹配标识数默认为0
		char word = 0;
		Map nowMap = sensitiveWordMap;
		for(int i = beginIndex; i < txt.length() ; i++){
			word = txt.charAt(i);
			nowMap = (Map) nowMap.get(word);     //获取指定key
			if(nowMap != null){     //存在，则判断是否为最后一个
				matchFlag++;     //找到相应key，匹配标识+1
				if("1".equals(nowMap.get("isEnd"))){       //如果为最后一个匹配规则,结束循环，返回匹配标识数
					flag = true;       //结束标志位为true
					if(SensitivewordFilter.minMatchTYpe == matchType){    //最小规则，直接返回,最大规则还需继续查找
						break;
					}
				}
			}
			else{     //不存在，直接返回
				break;
			}
		}
		if(matchFlag < 2 || !flag){        //长度必须大于等于1，为词
			matchFlag = 0;
		}
		return matchFlag;
	}

	public static void main(String[] args) throws Exception {
		long beginTime = System.currentTimeMillis();
		SensitivewordFilter filter = new SensitivewordFilter();
		Set setset = new HashSet<String>();
		System.out.println("敏感词的数量：" + filter.sensitiveWordMap.size());
		String string = "卧槽，你他妈真，垃-圾！！,操，艹，曹，糙，曹";
		System.out.println("被检测字符串长度：" + string.length());
		String newStr = filter.replaceSensitiveWord(string, 2, "*");
		//String newStr = filter.replaceSensitiveWord(ChineseToChar.test(string), 2, "*");
		String [] ss=newStr.split(",");
		String result="";
		/*for (int i = 0,j=0; i < string.length(); i++) {
			if(ChineseToChar.isChinese(string.charAt(i))){//如果是汉字
				if(ss[j].equals("**")){
					result+="*";
				}else{
					result+=string.charAt(i);
				}
				++j;
			}else{
				result+=string.charAt(i);
			}
		}*/
		long endTime = System.currentTimeMillis();
		//Set<String> set = filter.getSensitiveWordChinese(ChineseToChar.test(string), 1,ChineseToChar.delNotChainse(string));
		Set<String> set = filter.getSensitiveWordChinese(string, 1,string);
		System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
		System.out.println("总共耗时：" + (endTime - beginTime)+"ms");
		System.out.println("原字符串为：\n" + string);
		System.out.println("替换后的字符串为：\n" + result);
		System.out.println("替换后的字符串为：\n" + newStr);
	}
}



