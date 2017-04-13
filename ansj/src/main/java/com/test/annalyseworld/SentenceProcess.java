package com.test.annalyseworld;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/4/13.
 * 正向最大匹配算法
 *1： 左边往右边切sentence.sunString(i,sentence.length) 长度为len的字符串
 * 2：从切出来的字符串判断是否有词语存在字典里，有的话 i=i+词语长度 调回第一步，没有的话截取第一位到倒数第二位的字符串
 * 3：截取出来的字符串做第二步的判断
 */

public class SentenceProcess {
    ArrayList<String> words = new ArrayList();
    String sentence = "计算机语言学课程有意思，但是有时候大家必须有调试问题的耐心";
    int len=4;
    static ArrayList dictionary;
    static{
         dictionary= new ArrayList(Arrays.asList("耐心","问题","计算机", "语言学", "课程", "意思","无聊","有时候","但是"));
    }

    void splitSentence() {
        int i=0;
        for (i=0;i<sentence.length();i++){
            //TODO i+len 做判断
            String subSentence = "";
            if (i+len>sentence.length())
                 subSentence = sentence.substring(i, sentence.length());
            else
                 subSentence = sentence.substring(i, i+len);

            while (subSentence.length()>0){
                if (dictionary.contains(subSentence)) {
                    words.add(subSentence);
                    i = i + subSentence.length()-1;
                    break;
                }
                subSentence= subSentence.substring(0, subSentence.length()-1);

            }

        }

        System.out.println(sentence.charAt(1));
    }

    public static void main(String[] args) {
        SentenceProcess sentenceProcess = new SentenceProcess();
        sentenceProcess.splitSentence();
    }
}
