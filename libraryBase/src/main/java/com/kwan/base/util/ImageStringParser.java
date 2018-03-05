package com.kwan.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImageStringParser {


    private ArrayList<String> mSmileyTexts;
    private Pattern mPattern;
    private HashMap<String, Bitmap> mSmileyToRes;
    private Context mContext;

    public ImageStringParser(Context context, HashMap<String, Bitmap> data) {

        mContext = context;
        mSmileyToRes = data;
        mSmileyTexts = new ArrayList<>();

        Set<String> set = mSmileyToRes.keySet();
        Iterator<String> it = set.iterator();

        while (it.hasNext()) {
            String key = it.next();
            mSmileyTexts.add(key);
        }

        mPattern = buildPattern(mSmileyTexts);

    }

    // 构建正则表达式
    public static Pattern buildPattern(ArrayList<String> mSmileyTexts) {


        StringBuffer patternString = new StringBuffer();
        patternString.append("(");

        for (String s : mSmileyTexts) {
            patternString.append(escapeExprSpecialWord(s));
            patternString.append('|');
        }

        patternString.append(")");
        return Pattern.compile(patternString.toString());
    }


    /**
     * 正则表达式 转义字符
     *
     * @param keyword
     * @return
     */

    public static String escapeExprSpecialWord(String keyword) {

        String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
        for (String key : fbsArr) {
            if (keyword.contains(key)) {
                keyword = keyword.replace(key, "\\" + key);
            }
        }

        return keyword;
    }


    // 根据文本替换成图片
    public SpannableStringBuilder replace(String text) {

        try {

            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            Matcher matcher = mPattern.matcher(text);

            while (matcher.find()) {
                Bitmap bitmap = mSmileyToRes.get(matcher.group());
                builder.setSpan(new ImageSpan(mContext, bitmap,
                        ImageSpan.ALIGN_BOTTOM), matcher.start(), matcher
                        .end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            return builder;
        } catch (Exception e) {
            return null;
        }
    }
}
