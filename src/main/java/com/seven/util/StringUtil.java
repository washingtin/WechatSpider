package com.seven.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author guost
 * @Date 2020/7/9 上午11:03
 * @Version 1.0.0
 */
public class StringUtil {

    /**
     *
     * @param orgStr
     * @return
     */
    public static String encodeEmoji(String orgStr)
    {
        if (StringUtils.isEmpty(orgStr))
        {
            return orgStr;
        }
        String temp = orgStr;
        Pattern pattern = Pattern.compile("[^\u0000-\uffff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(orgStr);
        while (matcher.find())
        {
            StringBuilder sb = new StringBuilder("[em:");
            String mStr = matcher.group();
            for (int i = 0; i < mStr.length(); i++)
            {
                int emoji = mStr.charAt(i);
                if (i < mStr.length() - 1)
                {
                    sb.append(emoji).append('-');
                }
                else
                {
                    sb.append(emoji).append(']');
                }
            }
            temp = temp.replaceAll(mStr, sb.toString());
        }
        return temp;
    }

    public static String decodeEmoji(String orgStr)
    {
        if (StringUtils.isEmpty(orgStr))
        {
            return orgStr;
        }
        String temp = orgStr;
        Pattern pattern = Pattern.compile("\\[em:[\\d\\-]+\\]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(orgStr);
        while (matcher.find())
        {
            StringBuilder sb = new StringBuilder("\\[em:");
            StringBuilder emojiSb = new StringBuilder();
            String mStr = matcher.group();
            String[] emojis = mStr.substring(4, mStr.length() - 1).split("-");
            for (int i = 0; i < emojis.length; i++)
            {
                int emoji = Integer.parseInt(emojis[i]);
                emojiSb.append((char)emoji);
                if (i < emojis.length - 1)
                {
                    sb.append(emoji).append("\\-");
                }
                else
                {
                    sb.append(emoji).append("\\]");
                }
            }
            temp = temp.replaceAll(sb.toString(), emojiSb.toString());
        }
        return temp;
    }
}
