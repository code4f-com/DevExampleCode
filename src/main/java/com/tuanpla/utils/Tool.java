/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tuanpla.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author PLATUAN
 */
public class Tool {

    /**
     * Lấy thời gian hiển thị cho tin tức
     *
     * @param newsTime
     * @return
     */
    public static String getDisplayTimeNews(Timestamp newsTime) {
        try {
            Calendar current = Calendar.getInstance();
            Calendar input = Calendar.getInstance();
            if (newsTime == null) {
                newsTime = DateProc.createTimestamp();
            }
            input.setTime(new java.util.Date(newsTime.getTime()));
            if (input.get(Calendar.YEAR) < current.get(Calendar.YEAR)) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (input.get(Calendar.MONTH) < current.get(Calendar.MONTH)) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (input.get(Calendar.DAY_OF_MONTH) < current.get(Calendar.DAY_OF_MONTH)) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY) >= 5) {
                return DateProc.Timestamp2DDMMYYYYHH24Mi(newsTime);
            } else if (current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY) > 0 && current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY) < 5) {
                return (current.get(Calendar.HOUR_OF_DAY) - input.get(Calendar.HOUR_OF_DAY)) + " giờ trước";
            } else {
                return (current.get(Calendar.MINUTE) - input.get(Calendar.MINUTE)) + " phút trước";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Build Partition Cho Tin tức
     *
     * @param date
     * @return
     */
    public static String buildNewsPartitionByDate(String date) {
        //Format date: dd/mm/yyyy
        String p;
        if (date == null || date.equals("")) {
//            Timestamp currTime = new java.sql.Timestamp(System.currentTimeMillis());
            date = DateProc.getDateString(DateProc.createTimestamp());
        }
        String sMonth;
        String sYear;
        int index = date.indexOf("/");
//        sDay = date.substring(0, index);
        String sTmp = date.substring(index + 1);
        index = sTmp.indexOf("/");
        sMonth = sTmp.substring(0, index);
        sYear = sTmp.substring(index + 1);
        String strTemp = "NEWS_P";
        p = strTemp + (sMonth) + sYear;

        return p;
    } //End of method

    /**
     * Build Partition Cho Tin tức
     *
     * @param date
     * @return
     */
    public static String buildNewsImagesPartitionByDate(String date) {
        //Format date: dd/mm/yyyy
        String p = "";
        if (date == null || date.equals("")) {
//            Timestamp currTime = new java.sql.Timestamp(System.currentTimeMillis());
            date = DateProc.getDateString(DateProc.createTimestamp());
        }
        String sMonth;
        String sYear;
        int index = date.indexOf("/");
//        sDay = date.substring(0, index);
        String sTmp = date.substring(index + 1);
        index = sTmp.indexOf("/");
        sMonth = sTmp.substring(0, index);
        sYear = sTmp.substring(index + 1);
        String strTemp = "NEWS_IMAGES_P";
        p = strTemp + (sMonth) + sYear;

        return p;
    } //End of method

    /**
     * Lấy ra mảng giá trị config theo Key
     *
     * @param input
     * @return
     */
    public static String[] getConfigArrString(String input) {
        String[] str = new String[4];
        try {
            str = input.split(",");
        } catch (Exception e) {
        }
        return str;
    }

    /**
     *
     * @param status
     * @return
     */
    public static boolean getBoolean(String status) {
        return status != null && status.equals("1");
    }

    /**
     *
     * @param input
     * @return
     */
    public static String validStringRequest(String input) {
        if (input != null) {
            input = input.trim();
        } else {
            input = "";
        }
        return input;
    }

    /**
     *
     * @param input
     * @return
     */
    public static int string2Integer(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * string2Integer
     *
     * @param input
     * @param defaultVal
     * @return Default return 0 neu String = 0 or notvalid
     */
    public static int string2Integer(String input, int defaultVal) {
        try {
            return Integer.parseInt(input.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * string2Integer
     *
     * @param input
     * @return Default return 0 neu String = 0 or notvalid
     */
    public static long string2Long(String input) {
        try {
            return Long.parseLong(input);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     *
     * @param string
     * @return
     */
    public static String stringToHTMLString(String string) {
        StringBuilder sb = new StringBuilder(string.length());
        // true if last char was blank
        boolean lastWasBlankChar = false;
        int len = string.length();
        char c;

        for (int i = 0; i < len; i++) {
            c = string.charAt(i);
            if (c == ' ') {
                // blank gets extra work,
                // this solves the problem you get if you replace all
                // blanks with &nbsp;, if you do that you loss
                // word breaking
                if (lastWasBlankChar) {
                    lastWasBlankChar = false;
                    sb.append("&nbsp;");
                } else {
                    lastWasBlankChar = true;
                    sb.append(' ');
                }
            } else {
                lastWasBlankChar = false;
                //
                // HTML Special Chars
                if (c == '"') {
                    sb.append("&quot;");
                } else if (c == '&') {
                    sb.append("&amp;");
                } else if (c == '<') {
                    sb.append("&lt;");
                } else if (c == '>') {
                    sb.append("&gt;");
                } else if (c == '\n') // Handle Newline
                {
                    sb.append("&lt;br/&gt;");
                } else {
                    int ci = 0xffff & c;
                    if (ci < 160) // nothing special only 7 Bit
                    {
                        sb.append(c);
                    } else {
                        // Not 7 Bit use the unicode system
                        sb.append("&#");
                        sb.append(Integer.toString(ci));
                        sb.append(';');
                    }
                }
            }
        }
        return sb.toString();
    }

    /**
     * Chuyển có dấu sang không dấu. Nếu = Null trả ra ""
     *
     * @param org
     * @return
     */
    public static String convert2NoSign(String org) {
        if (org == null) {
            org = "";
            return org;
        }
        char arrChar[] = org.toCharArray();
        char result[] = new char[arrChar.length];
        for (int i = 0; i < arrChar.length; i++) {
            switch (arrChar[i]) {
                case '\u00E1':
                case '\u00E0':
                case '\u1EA3':
                case '\u00E3':
                case '\u1EA1':
                case '\u0103':
                case '\u1EAF':
                case '\u1EB1':
                case '\u1EB3':
                case '\u1EB5':
                case '\u1EB7':
                case '\u00E2':
                case '\u1EA5':
                case '\u1EA7':
                case '\u1EA9':
                case '\u1EAB':
                case '\u1EAD':
                case '\u0203':
                case '\u01CE': {
                    result[i] = 'a';
                    break;
                }
                case '\u00E9':
                case '\u00E8':
                case '\u1EBB':
                case '\u1EBD':
                case '\u1EB9':
                case '\u00EA':
                case '\u1EBF':
                case '\u1EC1':
                case '\u1EC3':
                case '\u1EC5':
                case '\u1EC7':
                case '\u0207': {
                    result[i] = 'e';
                    break;
                }
                case '\u00ED':
                case '\u00EC':
                case '\u1EC9':
                case '\u0129':
                case '\u1ECB': {
                    result[i] = 'i';
                    break;
                }
                case '\u00F3':
                case '\u00F2':
                case '\u1ECF':
                case '\u00F5':
                case '\u1ECD':
                case '\u00F4':
                case '\u1ED1':
                case '\u1ED3':
                case '\u1ED5':
                case '\u1ED7':
                case '\u1ED9':
                case '\u01A1':
                case '\u1EDB':
                case '\u1EDD':
                case '\u1EDF':
                case '\u1EE1':
                case '\u1EE3':
                case '\u020F': {
                    result[i] = 'o';
                    break;
                }
                case '\u00FA':
                case '\u00F9':
                case '\u1EE7':
                case '\u0169':
                case '\u1EE5':
                case '\u01B0':
                case '\u1EE9':
                case '\u1EEB':
                case '\u1EED':
                case '\u1EEF':
                case '\u1EF1': {
                    result[i] = 'u';
                    break;
                }
                case '\u00FD':
                case '\u1EF3':
                case '\u1EF7':
                case '\u1EF9':
                case '\u1EF5': {
                    result[i] = 'y';
                    break;
                }
                case '\u0111': {
                    result[i] = 'd';
                    break;
                }
                case '\u00C1':
                case '\u00C0':
                case '\u1EA2':
                case '\u00C3':
                case '\u1EA0':
                case '\u0102':
                case '\u1EAE':
                case '\u1EB0':
                case '\u1EB2':
                case '\u1EB4':
                case '\u1EB6':
                case '\u00C2':
                case '\u1EA4':
                case '\u1EA6':
                case '\u1EA8':
                case '\u1EAA':
                case '\u1EAC':
                case '\u0202':
                case '\u01CD': {
                    result[i] = 'A';
                    break;
                }
                case '\u00C9':
                case '\u00C8':
                case '\u1EBA':
                case '\u1EBC':
                case '\u1EB8':
                case '\u00CA':
                case '\u1EBE':
                case '\u1EC0':
                case '\u1EC2':
                case '\u1EC4':
                case '\u1EC6':
                case '\u0206': {
                    result[i] = 'E';
                    break;
                }
                case '\u00CD':
                case '\u00CC':
                case '\u1EC8':
                case '\u0128':
                case '\u1ECA': {
                    result[i] = 'I';
                    break;
                }
                case '\u00D3':
                case '\u00D2':
                case '\u1ECE':
                case '\u00D5':
                case '\u1ECC':
                case '\u00D4':
                case '\u1ED0':
                case '\u1ED2':
                case '\u1ED4':
                case '\u1ED6':
                case '\u1ED8':
                case '\u01A0':
                case '\u1EDA':
                case '\u1EDC':
                case '\u1EDE':
                case '\u1EE0':
                case '\u1EE2':
                case '\u020E': {
                    result[i] = 'O';
                    break;
                }
                case '\u00DA':
                case '\u00D9':
                case '\u1EE6':
                case '\u0168':
                case '\u1EE4':
                case '\u01AF':
                case '\u1EE8':
                case '\u1EEA':
                case '\u1EEC':
                case '\u1EEE':
                case '\u1EF0': {
                    result[i] = 'U';
                    break;
                }

                case '\u00DD':
                case '\u1EF2':
                case '\u1EF6':
                case '\u1EF8':
                case '\u1EF4': {
                    result[i] = 'Y';
                    break;
                }
                case '\u0110':
                case '\u00D0':
                case '\u0089': {
                    result[i] = 'D';
                    break;
                }
                case (char) 160: {
                    result[i] = ' ';
                    break;
                }
                default:
                    result[i] = arrChar[i];
            }
        }
        return new String(result);
    }

    public static String validStringUserInput(String sStr) {

        if (sStr == null) {
            sStr = "";
        }
        sStr = sStr.replaceAll("&", "&amp;");
//        sStr = sStr.replaceAll("Æ", "&AElig;");
//        sStr = sStr.replaceAll("Ç", "&#199;");
//        sStr = sStr.replaceAll("‡", "&Dagger;");
//        sStr = sStr.replaceAll("Δ", "&Delta;");
//        sStr = sStr.replaceAll("Ω", "&Omega;");
//        sStr = sStr.replaceAll("Γ", "&Icirc;");
//        sStr = sStr.replaceAll("Φ", "&Phi;");
//        sStr = sStr.replaceAll("Œ", "&OElig;");
//        sStr = sStr.replaceAll("Š", "&Scaron;");
//        sStr = sStr.replaceAll("ζ", "&zeta;");
//        sStr = sStr.replaceAll("¥", "&#165;");
//        sStr = sStr.replaceAll("ξ", "&xi;");
//        sStr = sStr.replaceAll("℘", "&weierp;");
//        sStr = sStr.replaceAll("ü", "&uuml;");
//        sStr = sStr.replaceAll("ϒ", "&upsih;");
//        sStr = sStr.replaceAll("↑", "&uarr;");
//        sStr = sStr.replaceAll("⇑", "&uArr;");
//        sStr = sStr.replaceAll("∴", "&there4;");
//        sStr = sStr.replaceAll("τ", "&tau;");
//        sStr = sStr.replaceAll("⊇", "&supe;");
//        sStr = sStr.replaceAll("³", "&sup3;");
//        sStr = sStr.replaceAll("∑", "&sum;");
//        sStr = sStr.replaceAll("♠", "&spades;");
//        sStr = sStr.replaceAll("§", "&#167;");
//        sStr = sStr.replaceAll("⌋", "&rfloor;");
//        sStr = sStr.replaceAll("æ", "&aelig;");
//        sStr = sStr.replaceAll("≈", "&asymp;");
//        sStr = sStr.replaceAll("β", "&beta;");
//        sStr = sStr.replaceAll("ç", "&ccedil;");
//        sStr = sStr.replaceAll("♣", "&clubs;");
//        sStr = sStr.replaceAll("≅", "&cong;");
//        sStr = sStr.replaceAll("©", "&#169;");
//        sStr = sStr.replaceAll("↵", "&crarr;");
//        sStr = sStr.replaceAll("÷", "&divide;");
//        sStr = sStr.replaceAll("∅", "&#216;");
//        sStr = sStr.replaceAll("♥", "&#9829;");
//        sStr = sStr.replaceAll("¿", "&#191;");
//        sStr = sStr.replaceAll("→", "&rarr;");
//        sStr = sStr.replaceAll("√", "&radic;");
//        sStr = sStr.replaceAll("⇒", "&rArr;");
//        sStr = sStr.replaceAll("ψ", "&psi;");
//        sStr = sStr.replaceAll("∝", "&prop;");
//        sStr = sStr.replaceAll("∏", "&prod;");
//        sStr = sStr.replaceAll("£", "&#163;");
//        sStr = sStr.replaceAll("£", "&pound;");
//        sStr = sStr.replaceAll("±", "&plusmn;");
//        sStr = sStr.replaceAll("ϖ", "&piv;");
//        sStr = sStr.replaceAll("φ", "&phi;");
//        sStr = sStr.replaceAll("¶", "&para;");
//        sStr = sStr.replaceAll("⊕", "&oplus;");
//        sStr = sStr.replaceAll("ω", "&omega;");
//        sStr = sStr.replaceAll("ñ", "&ntilde;");
//        sStr = sStr.replaceAll("⊄", "&nsub;");
//        sStr = sStr.replaceAll("∉", "&notin;");
//        sStr = sStr.replaceAll("∋", "&ni;");
//        sStr = sStr.replaceAll("∇", "&nabla;");
//        sStr = sStr.replaceAll("◊", "&loz;");
//        sStr = sStr.replaceAll("∞", "&infin;");
//        sStr = sStr.replaceAll("∀", "&forall;");
//        sStr = sStr.replaceAll("½", "&frac12;");
//        sStr = sStr.replaceAll("î", "&#238;");

//        sStr = sStr.replaceAll("Ö", "o");
//        sStr = sStr.replaceAll("ö", "o");
//        sStr = sStr.replaceAll("ë", "e");
//        sStr = sStr.replaceAll("†", "t");
//        sStr = sStr.replaceAll("ι", "L");
//        sStr = sStr.replaceAll("¢", "&#162;");
//        sStr = sStr.replaceAll("©", "&#169;");
//        sStr = sStr.replaceAll("€", "&#8364;");
//        sStr = sStr.replaceAll("®", "&#174;");
//        sStr = sStr.replaceAll("¤", "&#164;");
//        sStr = sStr.replaceAll("¿", "&#191;");
//        sStr = sStr.replaceAll("×", "&#215;");
//        sStr = sStr.replaceAll("÷", "&#247;");
//        sStr = sStr.replaceAll("±", "&#177;");
//        sStr = sStr.replaceAll("æ", "&#230;");
//        sStr = sStr.replaceAll("¾", "&#164;");
//        sStr = sStr.replaceAll("Ñ", "&#209;");
//        sStr = sStr.replaceAll("Ü", "&#220;");
//        sStr = sStr.replaceAll("Þ", "&#222;");
//        sStr = sStr.replaceAll("ß", "&#223;");
//        sStr = sStr.replaceAll("ä", "&#228;");
//        sStr = sStr.replaceAll("ë", "&#235;");
//        sStr = sStr.replaceAll("ð", "&#240;");
//        sStr = sStr.replaceAll("ñ", "&#241;");
//        sStr = sStr.replaceAll("û", "&#251;");
//        sStr = sStr.replaceAll("ü", "&#252;");
//        sStr = sStr.replaceAll("þ", "&#254;");
//        sStr = sStr.replaceAll("ÿ", "&#255;");
//        sStr = sStr.replaceAll("Ä", "&#196;");
//        sStr = sStr.replaceAll("Î", "&#206;");
//        sStr = sStr.replaceAll("Ï", "&#207;");
//        sStr = sStr.replaceAll("Õ", "&#213;");
//        sStr = sStr.replaceAll("Ö", "&#214;");
//        sStr = sStr.replaceAll("Ü", "&#220;");
//        sStr = sStr.replaceAll("«", "&#171;");
//        sStr = sStr.replaceAll("\"", "&#34;");
//        sStr = sStr.replaceAll("'", "&#39;");
        sStr = sStr.replaceAll("<", "&lt;");
        sStr = sStr.replaceAll(">", "&gt;");
        sStr = sStr.replaceAll("&lt;br&gt;", "<br/>");
        sStr = sStr.replaceAll("&lt;br/&gt;", "<br/>");
        sStr = sStr.replaceAll("&lt;p&gt;", "<p>");
        sStr = sStr.replaceAll("&lt;/p&gt;", "</p>");
        sStr = sStr.replaceAll("&lt;hr&gt;", "<hr/>");
        sStr = sStr.replaceAll("&lt;hr/&gt;", "<hr/>");
        sStr = sStr.replaceAll("\n", "<br />");
        return sStr;
    }

    public static String replaceString(String sStr, String oldStr, String newStr) {
        sStr = (sStr == null ? "" : sStr);
        String strVar = sStr;
        String tmpStr = "";
        String finalStr = "";
        int stpos = 0, endpos = 0, strLen = 0;
        while (true) {
            strLen = strVar.length();
            stpos = 0;
            endpos = strVar.indexOf(oldStr, stpos);
            if (endpos == -1) {
                break;
            }
            tmpStr = strVar.substring(stpos, endpos);
            tmpStr = tmpStr.concat(newStr);
            strVar = strVar.substring(endpos + oldStr.length() > sStr.length() ? endpos : endpos + oldStr.length(), strLen);
            finalStr = finalStr.concat(tmpStr);
            stpos = endpos;
        }
        finalStr = finalStr.concat(strVar);
        return finalStr;
    }

    public static String readFileText(String path) {
        String Content = "";
        String sContent = "";
        try {
            FileInputStream fstream = new FileInputStream(path);
            try (DataInputStream in = new DataInputStream(fstream)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                while ((Content = br.readLine()) != null) {
                    sContent += Content + "\n";
                }
            }
        } catch (Exception e) {
            System.err.println("Tool : Error: ReadFile >> " + e.getMessage());
        }
        return sContent;
    }

    public static boolean writeFileText(String content, String path) {
        boolean flag;
        try (Writer outw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"))) {
            outw2.write(content);
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static String convertTitle(String input) {
        if (input == null) {
            return null;
        }
        input = Tool.convert2NoSign(input);
        input = input.replaceAll(" ", "-");
        input = input.replaceAll("--", "-");
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch == '-')) {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static String subStringByIndex(String str, int index) {
        String strTem = "";
        if (str == null) {
            return "";
        }
        if (str.length() <= index) {
            return str;
        }
        String[] arrstr = str.split(" ");
        if (arrstr != null && arrstr.length > 0) {
            for (String arrstr1 : arrstr) {
                strTem += arrstr1 + " ";
                if (strTem.length() > index) {
                    break;
                }
            }
        }
        strTem += "...";
        return strTem;
    }

    public static String hidenPhone(String phone) {
        String str = "";
        str = phone;
        if (phone == null) {
            return "";
        }
        if (phone.startsWith("84")) {
            str = "0" + phone.substring("84".length());
        }
        if (phone.startsWith("+84")) {
            str = "0" + phone.substring("+84".length());
        }

        str = str.substring(0, str.length() - 7) + "xxx" + str.substring(str.length() - 3);
        return str;
    }

    public static String hidenAllPhone(String allPhone) {
        String strreturn = "";
        if (allPhone == null || allPhone.equals("")) {
            return strreturn;
        } else {
            String[] tem = allPhone.split(",");
            for (int i = 0; i < tem.length; i++) {
                if (tem[i].startsWith("84") || tem[i].startsWith("0")) {
                    strreturn += hidenPhone(tem[i]) + ",";
                } else {
                    strreturn += tem[i] + ",";
                }
            }
            if (strreturn.endsWith(",")) {
                strreturn = strreturn.substring(0, strreturn.length() - 1);
            }
        }
        return strreturn;
    }

    public static String loadUrl(String urlStr) {
        String t = "";
        try {
            URL url = new URL(urlStr);
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setConnectTimeout(20000);
            try (InputStream in = http.getInputStream()) {
                t = convertStreamToString(in);
            }
            http.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static String convertStreamToString(InputStream is) throws IOException {
        /*
         * To convert the InputStream to String we use the
         * Reader.read(char[] buffer) method. We iterate until the
         * Reader return -1 which means there's no more data to
         * read. We use the StringWriter class to produce the string.
         */
        if (is != null) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } finally {
                is.close();
            }
            return writer.toString();
        } else {
            return "";
        }
    }

    public static void Debug(String input) {
        System.out.println("[DevExampleCode]: " + input);
    }

    private static final Random RANDOM = new SecureRandom();

    public static String generateRandomPassword() {
        // Pick from some letters that won't be easily mistaken for each
        // other. So, for example, omit o O and 0, 1 l and L.
        String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789";

        String pw = "";
        for (int i = 0; i < 10; i++) {
            int index = (int) (RANDOM.nextDouble() * letters.length());
            pw += letters.substring(index, index + 1);
        }
        return pw;
    }

    public static String getTitle(String input) {
        if (input == null) {
            return null;
        }
        input = Tool.convert2NoSign(input);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                buffer.append(ch);
            }
        }
        String tmp = buffer.toString();
        tmp = tmp.replaceAll(" ", "-");
        while (tmp.contains("--")) {
            tmp = tmp.replaceAll("--", "-");
        }
        tmp = tmp.toLowerCase();
        return tmp;
    }

    public static String getStringAlt(String input) {
        if (input == null) {
            return null;
        }
        input = Tool.convert2NoSign(input);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || ch == ' ') {
                buffer.append(ch);
            }
        }
        String tmp = buffer.toString();
        return tmp;
    }

    public static String toTitleNoSpace(String input) {
        if (input == null) {
            return null;
        }
        input = Tool.convert2NoSign(input);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if ((ch >= '0' && ch <= '9') || (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
                buffer.append(ch);
            }
        }
        return buffer.toString();
    }

    public static String getTagAscii(String tags) {
        String str = "";
        if (!checkNull(tags)) {
            tags = convert2NoSign(tags);
            StringTokenizer tokenSpace = new StringTokenizer(tags, "[,]");
            while (tokenSpace.hasMoreTokens()) {
                String tempStr = tokenSpace.nextToken();
                tempStr = tempStr.trim();
                tempStr = tempStr.replaceAll(" ", "-");
                str += tempStr + ",";
            }
            if (str.endsWith(",")) {
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    public static boolean checkNull(String input) {
        return input == null || input.equalsIgnoreCase("null") || input.equalsIgnoreCase("");
    }

    public static String getLogMessage(Exception ex) {
        StackTraceElement[] trace = ex.getStackTrace();
        String str = DateProc.createTimestamp() + "||" + ex.getMessage();
        for (StackTraceElement trace1 : trace) {
            str += trace1 + "\n";
        }
        return str;
    }

    public static String generateUUID() {
        UUID idOne = UUID.randomUUID();
        return idOne.toString();
    }

    public static String getCurrentURL(HttpServletRequest request) {
        String currentURL = null;
        if (request.getAttribute("javax.servlet.forward.request_uri") != null) {
            currentURL = (String) request.getAttribute("javax.servlet.forward.request_uri");
        } else {
            currentURL = request.getRequestURI();
        }
        if (currentURL != null && request.getAttribute("javax.servlet.include.query_string") != null) {
            currentURL += "?" + request.getQueryString();
        }
        return currentURL;
    }

    public static String validStringFCK(String content, String title_url) {
        if (content != null) {
            content = content.trim();
            content = content.replaceAll("&nbsp;", " ");
        } else {
            content = "";
        }
        content = FCKProcessImage(content, title_url);
        return content;
    }

    public static String FCKProcessImage(String content, String title_url) {
        if (content == null || content.equals("")) {
            return content;
        }
        String strFirst = "";
        String strLast = "";
        ArrayList<String> listImgTag = new ArrayList<>();
        int posImg = 0;
        try {
            int index = -1;
            while ((index = content.indexOf("<img")) > 0) {
                int lastIndex = -1;
                String striamge = "";
                // Phan truoc the <img
                strFirst = content.substring(0, index);
                // Phan tu the <img tro di
                strLast = content.substring(index);
//                System.out.println("phan con lai= "+strLast);
                lastIndex = strLast.indexOf(">");
                striamge = strLast.substring(0, lastIndex + 1);
//                System.out.println(striamge);
                // Phan con lai sau khi da loai the <img...>
                strLast = strLast.substring(striamge.length());
//                System.out.println(strLast);
                // Duong dan anh lay ra de xu ly
                listImgTag.add(striamge);
                // Tra lai str da lay duoc link anh
                content = strFirst + "##" + posImg + "##" + strLast;
//                System.out.println("str="+str);
//                System.out.println("--------------------------------");
                posImg++;
                index = -1;
            }
            /**
             * < XU LY CAC LINK ANH
             */
            int indexTem = 0;
            ArrayList<String> contentLink = ImageProcessCache(listImgTag, title_url);
            for (String oneLink : contentLink) {
                String imgStr = "<img alt='" + title_url + "' title ='" + title_url + "' class=\"photo\" src=\"" + oneLink + "\" />";
                content = replaceString(content, "##" + indexTem + "##", imgStr);
                indexTem++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public static ArrayList<String> ImageProcessCache(ArrayList<String> listImgTag, String title_url) {
        ArrayList<String> allRealLink = new ArrayList<>();
        try {
            int i = 1;
            for (String oneTag : listImgTag) {
                // Duong dan anh trong the
                oneTag = getUrlFromImageTag(oneTag);
                if (oneTag.contains("/newsreq/")) {
                    allRealLink.add(oneTag);
                } else {
                    String pathSave = "/newsreq/" + FileUtils.getPathfollowYear()
                            + "/" + title_url + "_" + i + ".jpg";
//                    String real_path = MyContext.ROOT_DIR + MyConfig.URL_SEVER_CACHE_IMAGE_PATH + pathSave;
//                    cacheImageRequest(oneTag, real_path, false, MyConfig.WIDTH_IAMGE_IN_CONTENT_NEW);
//                    //---------
//                    String url_by_host = MyConfig.URL_HOST_IMAGE + pathSave;
//                    allRealLink.add(url_by_host);
                    i++;
                }
            }
        } catch (Exception e) {
        }
        return allRealLink;
    }

    public static boolean cacheImageRequest(String urlImage, String realPath, boolean resize, int width_max) {
        boolean flag = false;
        try {
            urlImage = urlImage.replaceAll(" ", "%20");
            URL urlimg = new URL(urlImage);
            URLConnection ucconn_img = urlimg.openConnection();
            //**************
            String extention = "jpg";
            ImageInputStream iis = ImageIO.createImageInputStream(ucconn_img.getInputStream());
            Iterator iter = ImageIO.getImageReaders(iis);
            if (iter.hasNext()) {
                ImageReader reader = (ImageReader) iter.next();
                reader.setInput(iis);
                String formatName = reader.getFormatName();
                // Gan lai Extention by Final Image Type
                extention = formatName;
            }
            if (resize) {
                // Thay doi Kich Thuoc Anh Lay Ve                
                FileUtils.resizeMaxWithWriteImg(urlimg, width_max, realPath, extention);
            } else {
                FileUtils.writeImg(urlimg, realPath, extention);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    private static String getUrlFromImageTag(String imageTag) {
        try {
            imageTag = imageTag.replaceAll(" ", "");
            imageTag = imageTag.substring(imageTag.indexOf("src=\"") + 5);
            imageTag = imageTag.substring(0, imageTag.indexOf("\""));
        } catch (Exception e) {
        }
        return imageTag;
    }

    public static String getLongTimeString() {
        String str = "";
        long time = new Date().getTime();
        str += time;
        return str;
    }
}
