package zzq;

import java.io.IOException;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChineseToChar {
	// 将汉字转换为全拼

	private static HanyuPinyinOutputFormat spellFormat = new HanyuPinyinOutputFormat();
	//初始化信息
	public static void init() throws IOException{
		spellFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		spellFormat.setToneType(HanyuPinyinToneType.WITH_TONE_NUMBER);
		spellFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
	}
	//将汉字转换为拼音
	public static String getPinYin(String src) {
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		String t3 = "";
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i],spellFormat);
					t3 += t2[0];
					t3+=":";
				}
			}
			return t3;
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}
		return t3;
	}
	//将拼音转换为字符组合
	public static String getChar(char src){
		String t4 = "";
		String set = "";

		//String t7 = "";

		String t6 = "";
		if(isChinese(src)){
			t4 = getPinYin(src+"");
			String[] t5=t4.split(":");
			String a[][]= new String[2][23];
			String b[][]= new String[2][36];
			String c[][]= new String[2][5];
			a[0][0]="b";a[0][1]="p";a[0][2]="m";a[0][3]="f";a[0][4]="h";a[0][5]="d";a[0][6]="t";a[0][7]="n";a[0][8]="l";a[0][9]="g";a[0][10]="k";a[0][11]="j";a[0][12]="q";a[0][13]="x";a[0][14]="zh";a[0][15]="z";a[0][16]="ch";a[0][17]="c";a[0][18]="sh";a[0][19]="s";a[0][20]="r";a[0][21]="y";a[0][22]="w";
			a[1][0]="A";a[1][1]="B";a[1][2]="C";a[1][3]="D";a[1][4]="D";a[1][5]="E";a[1][6]="F";a[1][7]="G";a[1][8]="G";a[1][9]="H";a[1][10]="I";a[1][11]="J";a[1][12]="K";a[1][13]="L";a[1][14]="M";a[1][15]="M";a[1][16]="N";a[1][17]="N";a[1][18]="O";a[1][19]="O";a[1][20]="P";a[1][21]="Q";a[1][22]="R";
			b[0][0]="a";b[0][1]="o";b[0][2]="e";b[0][3]="i";b[0][4]="u";b[0][5]="v";b[0][6]="ai";b[0][7]="ei";b[0][8]="ui";b[0][9]="un";b[0][10]="ao";b[0][11]="ou";b[0][12]="iu";b[0][13]="ie";b[0][14]="ve";b[0][15]="ue";b[0][16]="er";b[0][17]="an";b[0][18]="ang";b[0][19]="en";b[0][20]="eng";b[0][21]="in";b[0][22]="ing";b[0][23]="ong";b[0][24]="ia";b[0][25]="ua";b[0][26]="uo";b[0][27]="uai";b[0][28]="uei";b[0][29]="iao";b[0][30]="iou";b[0][31]="ian";b[0][32]="uan";b[0][33]="uang";b[0][34]="iang";b[0][35]="iong";
			b[1][0]="A";b[1][1]="B";b[1][2]="C";b[1][3]="D";b[1][4]="E";b[1][5]="F";b[1][6]="G";b[1][7]="G";b[1][8]="H";b[1][9]="H";b[1][10]="I";b[1][11]="J";b[1][12]="K";b[1][13]="L";b[1][14]="M";b[1][15]="M";b[1][16]="N";b[1][17]="O";b[1][18]="O";b[1][19]="P";b[1][20]="P";b[1][21]="Q";b[1][22]="Q";b[1][23]="R";b[1][24]="S";b[1][25]="T";b[1][26]="U";b[1][27]="V";b[1][28]="W";b[1][29]="X";b[1][30]="Y";b[1][31]="Z";b[1][32]="a";b[1][33]="a";b[1][34]="b";b[1][35]="c";
			c[0][0]="5";c[0][1]="1";c[0][2]="2";c[0][3]="3";c[0][4]="4";
			c[1][0]="A";c[1][1]="B";c[1][2]="C";c[1][3]="D";c[1][4]="E";
			int k=0;//一行中第k个字
			int p=1;//标记韵母开始的下标
			int i=0;
			int l=t5.length;
			while(k<l){
				int len=t5[k].length();
				for (i=0;i<23;i++) {// 判断声母
					if (t5[k].startsWith(a[0][i])==true) {
						t6+=a[1][i];
						if((t5[k].substring(1,2)).equals("h")==true){//zh、ch、sh
							p=2;
						}
						break;
					}
				}
				if (i==23) {
					t6+="S";
					p=0;
				}
				for (int j=0;j<36;j++) {// 判断韵母
					String str=t5[k].substring(p,len-1);
					if ((str.equals(b[0][j]))==true) {
						t6+=b[1][j];
						break;
					}
				}
				/*for(int j=0;j<6;j++){//判断声调
					if (t5[k].endsWith(c[0][j])==true) {
						t6+=c[1][j];
						break;
					}
				}*/
				//t7+=t6;
				set+=t6;
				t6="";
				k++;
			}
			return set+",";
		}/*else{
			set+=src;
		}*/

		return "";
	}
	public static String test(String s){
		String ss="";
		for(int i=0;i<s.toCharArray().length;i++){
			/*System.out.println((s.toCharArray()[i]+""));*/
			ss+=getChar(s.toCharArray()[i]);
		}
		return ss;
	}
	//判断是否是汉字
	public static boolean isChinese(char c) {
		return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
	}
	/**
	 * 去掉文本中的非汉字
	 */
	public static String delNotChainse(String context){
		String reg="[^\u4e00-\u9fa5]";
		Pattern pat = Pattern.compile(reg);
		Matcher mat=pat.matcher(context);

		return mat.replaceAll("");
	}


	public static void main(String[] args) throws IOException {
		init();
		String string = "朱志强";
		for(int i=0;i<string.toCharArray().length;i++){
			/*System.out.println((s.toCharArray()[i]+""));*/
			System.out.print(getChar(string.toCharArray()[i]));
		}
		string = "朱c志-强";
		for(int i=0;i<string.toCharArray().length;i++){
			/*System.out.println((s.toCharArray()[i]+""));*/
			System.out.print(getChar(string.toCharArray()[i]));
		}
	}
}
