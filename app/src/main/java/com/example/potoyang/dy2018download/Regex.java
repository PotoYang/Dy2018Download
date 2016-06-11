package com.example.potoyang.dy2018download;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 71579 on 2016/5/21.
 */

public class Regex {

    public static String getRegexHtml(String content) {

//        String re2 = "<a[^>]+?href=[\"']?([^\"']+)[\"']?[^>]*>([^<]+)</a>";    // Unix Path 1

//        String re2 = "<a\\s*href=\"?([\\w\\W]*?)\"?[\\s]*?[^>]>([\\s\\S]*?)(?=</a>)";
        //提取字符串中的href后面的链接，以下类似
        String re1 = "<a\\s*href='?([^a-c]*?)'";
        String result = "";

        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String group01 = m.group(1);
            result += group01.toString() + "\n";
        }
        return result;
    }

    public static String getRegexTitle(String content) {

        String re1 = "title=\"(.*?)\"+";
        String result = "";

        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String group01 = m.group(1);
            result += group01.toString() + "\n";
        }
        return result;
    }

    public static String getRegexURL(String content) {

        String re1 = "\">ftp://(.*?)(</a>)+";
        String result = "";

        Pattern p = Pattern.compile(re1, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher m = p.matcher(content);
        while (m.find()) {
            String group01 = m.group(1);
            result += group01.toString() + "\n";
        }

        return result;
    }

}
