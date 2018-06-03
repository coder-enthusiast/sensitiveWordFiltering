package zzq;

import java.util.*;

/**
 * 实现Set敏感词汇转换为DFA树
 */
public class DFA {
    private static final DFA dfa = new DFA();
    private DFA(){

    }

    /**
     * 初始化set
     * @return
     */
    public Set init(){
        Set<String> set = new HashSet<String>();
        set.add("晚上");
        set.add("晚安");
        set.add("你好啊");
        return set;
    }

    public Map dfaMap(Set<String> txtSet){
        Iterator<String> iterator= txtSet.iterator();
        Map map = new HashMap();
        Map nowMap=null;
        Map temMap=null;
        while (iterator.hasNext()) {
            String key=iterator.next();
            temMap=map;
            for (int i = 0; i < key.length(); i++) {
                char k=key.charAt(i);
                Object workMap=map.get(k);
                if(workMap==null){
                    nowMap=new HashMap<>();
                    nowMap.put("isEnd", "0");
                    temMap.put(k, nowMap);
                    temMap=nowMap;
                }else{
                    temMap=(Map) workMap;
                }
                //最后一个字
                if(i==key.length()-1){
                    nowMap.put("isEnd", "1");
                }
            }
        }
        return map;
    }
    public String test(){
        return dfa.dfaMap(dfa.init()).toString();
    }
    public DFA getDFA(){
        return dfa;
    }
    public static void main(String[] args) {
        System.out.println(DFA.dfa.test());

    }
}
