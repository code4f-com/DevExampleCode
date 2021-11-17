package com.tuanpla.utils;

import java.util.*;

//import org.common.*;
//import org.data.*;
public class SMSTool {

    public SMSTool() {
    }
    //To replace a character at a specified position

    public static String replaceCharAt(String s, int pos, char c) {
        return s.substring(0, pos) + c + s.substring(pos + 1);
    }

    //To remove a character
    public static String removeChar(String s, char c) {
        String r = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != c) {
                r += s.charAt(i);
            }
        }
        return r;
    }

    //To remove a character at a specified position
    public static String removeCharAt(String s, int pos) {
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    /*
     * In
     *   text: a string having some seperator(s)
     * Out
     *   a collection of elements without (between) seperator
     */
    /*================================================================*/
    public static Collection parseStringEx(String text) {
        Vector vResult = new Vector();
        if (text == null || "".equals(text)) {
            return vResult;
        }

        String tempStr = text.trim();

        char NINE = (char) 0x39;
        char ZERO = (char) 0x30;
        char CH_a = (char) 'a';
        char CH_z = (char) 'z';
        char CH_A = (char) 'A';
        char CH_Z = (char) 'Z';

        String currLabel = "";
        char currChar = 0;
        for (int i = 0; i < tempStr.length(); i++) {
            currChar = tempStr.charAt(i);
            if ((currChar >= ZERO && currChar <= NINE)
                    || (currChar >= CH_a && currChar <= CH_z)
                    || (currChar >= CH_A && currChar <= CH_Z)) {
                currLabel = currLabel + currChar;
            } else if (currLabel != null && currLabel.length() > 0) {
                vResult.add(currLabel);
                currLabel = "";
            }
        }
        if (!"".equals(currLabel)) {
            vResult.addElement(currLabel);
        }
        return vResult;
    }

    /*
     * In
     *   text: a string having some seperator(s)
     * Out
     *   a collection of elements without (between) seperator
     */
    /*================================================================*/
    public static Collection parseString(String text, String seperator) {
        Vector vResult = new Vector();
        if (text == null || "".equals(text)) {
            return vResult;
        }
        String tempStr = text.trim();
        String currentLabel = null;
        int index = tempStr.indexOf(seperator);
        while (index != -1) {
            currentLabel = tempStr.substring(0, index).trim();
            //Only accept not null element
            if (!"".equals(currentLabel)) {
                vResult.addElement(currentLabel);
            }
            tempStr = tempStr.substring(index + 1);
            index = tempStr.indexOf(seperator);
        }
        //Last label
        currentLabel = tempStr.trim();
        if (!"".equals(currentLabel)) {
            vResult.addElement(currentLabel);
        }
        return vResult;
    }
    final static String[] seperators = {" ", ".", ",", "=", ">", "<", ":", "-"};
    /*=================================================*/

    public static Collection parseString(String text) {
        ArrayList vResult = new ArrayList();
        if (text == null || "".equals(text)) {
            return vResult;
        }
        String tempStr = text.trim();
        String currentLabel = null;
        int index = getNextIndex(tempStr);
        while (index != -1) {
            currentLabel = tempStr.substring(0, index).trim();
            //Only accept not null element
            if (!"".equals(currentLabel)) {
                vResult.add(currentLabel);
            }
            tempStr = tempStr.substring(index + 1);
            index = getNextIndex(tempStr);
        }
        //Last label
        currentLabel = tempStr.trim();
        if (!"".equals(currentLabel)) {
            vResult.add(currentLabel);
        }
        return vResult;
    }
    /*=================================================*/

    private static int getNextIndex(String text) {
        int index = 0;
        int newIdx = 0;
        boolean hasOne = false;
        for (String seperator : seperators) {
            newIdx = text.indexOf(seperator);
            if (!hasOne) {
                if (newIdx != -1) {
                    index = newIdx;
                    hasOne = true;
                }
            } else if (newIdx != -1) {
                if (newIdx < index) {
                    index = newIdx;
                }
            }
        }
        if (!hasOne) {
            index = -1;
        }
        return index;
    }

    /*======================================================*/
    // True if item is numeric
    // False if item is not numeric
    public static boolean isNumberic(String sNumber) {
        if (sNumber == null || "".equals(sNumber)) {
            return false;
        }
        for (int i = 0; i < sNumber.length(); i++) {
            char ch = sNumber.charAt(i);
            char ch_max = (char) 0x39;
            char ch_min = (char) 0x30;
            if ((ch < ch_min) || (ch > ch_max)) {
                return false;
            }
        }
        return true;
    }

    /*=======================================================*/
    // True if itemCode string is numeric
    // False if itemCode string is not numeric
    public static boolean isValidItemCode(String itemCode) {
        return isNumberic(itemCode);
    }

    public static String[] parseCommandCode(String msg) {
        String[] str_Return = null;
        String[][] invalidCommandCode = {
            {"XSHCM", "XS HCM", "XSH CM", "X SHCM", "X S H C M", "XS HO CHI MINH", "XSHOCHIMINH"},
            {"XSQNG", "XS QNG", "XSQ NG", "X SQNG", "X S Q N G", "XS QUANG NGAI", "XSQUANGNGAI"},
            {"XSDNO", "XS DNO", "XSD NO", "X SDNO", "X S D N O", "XS DAC NONG", "XSDACNONG"},
            {"XSBTH", "XS BTH", "XSB TH", "X SBTH", "X X B T H", "XS BINH THUAN", "XSBINHTHUAN"},
            {"XSTTH", "XS TTH", "XST TH", "X STTH", "X S T T H", "XS THUA THIEN HUE", "XSTHUATHIENHUE"},
            {"XSQNM", "XS QNN", "XSQ NM", "X SQNM", "X S Q N M", "XS QUANG NAM", "XSQUANGNAM"},
            {"XSDNG", "XS DNG", "XSD NG", "X SDNG", "X S DN G", "XS DA NANG", "XSDANANG"},
            {"XSBDI", "XS BDI", "XSB DI", "X SBDI", "X S B D I", "XS BINH DINH", "XSBINHDINH"},
            {"XSDLK", "XS DLK", "XSD LK", "X SDLK", "X S D L K", "XS DAC LAK", "XSDACLAK", "XS DAKLAK", "XS DAK LAK"},
            {"XSTD", "XS TD", "XST D", "X STD", "X S T D", "XS THU DO", "XSTHUDO"},
            {"XSQN", "XS QN", "XSQ N", "X SQN", "X S Q N", "XS QUANG NINH", "XSQUANGNINH"},
            {"XSBN", "XS BN", "XSB N", "X SBN", "X S B N", "XS BAC NINH", "XSBACNINH"},
            {"XSHP", "XS HP", "XSH P", "X SHP", "X S H P", "XS HAI PHONG", "XSHAIPHONG"},
            {"XSND", "XS ND", "XSN D", "X SND", "X S N D", "XS NAM DINH", "XSNAMDINH"},
            {"XSTB", "XS TB", "XST B", "X STB", "X S T B", "XS THAI BINH", "XSTHAIBINH"},
            {"XSCM", "XS CM", "XSC M", "X SCM", "X S C M", "XS CA MAU", "XSCAMAU"},
            {"XSDT", "XS DT", "XSD T", "X SDT", "X S D T", "XS DONG THAP", "XSDONGTHAP"},
            {"XSBL", "XS BL", "XSB L", "X SBL", "X S B L", "XS BAC LIEU", "XSBACLIEU"},
            {"XSBTR", "XS BTR", "XSB TR", "X SBTR", "X S B T R", "XS BEN TRE", "XSBENTRE"},
            {"XSVT", "XS VT", "XSV T", "X SVT", "X S V T", "XS VUNG TAU", "XSVUNGTAU"},
            {"XSCT", "XS CT", "XSC T", "X SCT", "X S C T", "XS CAN THO", "XSCANTHO"},
            {"XSDNI", "XS DNI", "XSD NI", "X SDNI", "X S D NI", "XSDN", "XS DN", "XS DONG NAI", "XSDONG NAI"},
            {"XSST", "XS ST", "XSS T", "X SST", "X S S T", "XS SOC TRANG", "XSSOCTRANG"},
            {"XSAG", "XS AG", "XSA G", "X SAG", "X S A G", "XS AN GIANG", "XSANGIANG"},
            {"XSTN", "XS TN", "XST N", "X STN", "X S T N", "XS TAY NINH", "XSTAYNINH"},
            {"XSBD", "XS BD", "XSB D", "X SBD", "X S B D", "XS BINH DUONG", "XSDINHDUONG"},
            {"XSTRV", "XS TRV", "XST V", "X STV", "X S T V", "XS TRA VINH", "XSTRAVINH"},
            {"XSVL", "XS VL", "XSV L", "X SVL", "X S V L", "XS VINH LONG", "XSVINHLONG"},
            {"XSBP", "XS BP", "XSB P", "X SBP", "X S B P", "XS BINH PHUOC", "XSBINHPHUOC"},
            {"XSHG", "XS HG", "XSH G", "X SHG", "X S H G", "XS HAU GIANG", "XSHAUGIANG"},
            {"XSLA", "XS LA", "XSL A", "X SLA", "X S L A", "XS LONG AN", "XSLONGAN"},
            {"XSKG", "XS KG", "XSK G", "X SKG", "X S K G", "XS KIEN GIANG", "XSKIENIANG"},
            {"XSLD", "XS LD", "XSL D", "X SLD", "X S L D", "XSDL", "XS DL", "XS LAM DONG", "XSLAMDONG", "XS DA LAT"},
            {"XSTG", "XS TG", "XST G", "X STG", "X S T G", "XS TIEN GIANG", "XSTIENGIANG"},
            {"XSPY", "XS PY", "XSP Y", "X SPY", "X S P Y", "XS PHU YEN", "XSPHUYEN"},
            {"XSKH", "XS KH", "XSK H", "X SKH", "X S K H", "XS KHANH HOA", "XSKHANHHOA"},
            {"XSQT", "XS QT", "XSQ T", "X SQT", "X S Q T", "XS QUANG TRI", "XSQUANGTRI"},
            {"XSGL", "XS GL", "XSG L", "X SGL", "X S G L", "XS GIA LAI", "XSGIALAI"},
            {"XSNT", "XS NT", "XSN T", "X SNT", "X S N T", "XS NINH THUAN", "XSNINHTHUAN"},
            {"XSKT", "XS KT", "XSK T", "X SKT", "X S K T", "XS KOM TUM", "XSKONTUM"},
            {"XSQB", "XS QB", "XSQ B", "X SQB", "X S Q B", "XS QUANG BINH", "XSQUANGBINH"},
            {"XSMB", "XS MB", "XS M B", "XS MIENBAC"},
            {"XSMT", "XS MT", "XS M T", "XS MIENTRUNG"},
            {"XSMN", "XS MN", "XS M N", "XS MIENNAM"},
            {"HCM", "HC M"}, //xs hcm
            {"MN"}, //xs hcm
            {"TD", "TD"}, //xstd
            {"HN", "HN"}, //xstd
            {"HP"}, //xosomb
            {"ND"}, //xsmb
            {"BN"}, //xsmb
            {"DNI", "DN I"}, //xs dong nai
            {"DNG", "DN G"}, //xoso da nang
            {"KH"},
            {"QNG"}, // quang ngai
            {"DNO"}, // dac nong
            {"TTH"}, // thua thien hue
            {"QNM"}, // quang nam
            {"QN"}, //xsmb
            {"BDI"}, // binh dinh

            {"CM"}, // ca mau
            //{"BL"}, // bac lieu

            //{"VT"}, // vung tau
            //{"CT"}, // can tho
            {"ST"}, // soc trang
            {"AG"}, // an giang
            {"TN"}, // tay ninh
            {"TRV"}, // tra vinh
            {"VL"}, // vinh long
            {"BP"}, // binh phuoc
            {"HG"}, // hau giang
            {"LA"}, // long an
            {"KG"}, // kien giang
            {"LD"}, // lam dong
            {"TG"}, // tien giang
            {"NT"}, // ninh thuan

            {"XS"},
            {"SXHCM", "SX HCM", "SXH CM", "S XHCM", "S X H C M", "SX HO CHI MINH", "SXHOCHIMINH"},
            {"SXQNG", "SX QNG", "SXQ NG", "S XQNG", "S X Q N G", "SX QUANG NGAI", "SXQUANGNGAI"},
            {"SXDNO", "SX DNO", "SXD NO", "S XDNO", "S X D N O", "SX DAC NONG", "SXDACNONG"},
            {"SXBTH", "SX BTH", "SXB TH", "S XBTH", "X X B T H", "SX BINH THUAN", "SXBINHTHUAN"},
            {"SXTTH", "SX TTH", "SXT TH", "S XTTH", "S X T T H", "SX THUA THIEN HUE", "SXTHUATHIENHUE"},
            {"SXQNM", "SX QNN", "SXQ NM", "S XQNM", "S X Q N M", "SX QUANG NAM", "SXQUANGNAM"},
            {"SXDNG", "SX DNG", "SXD NG", "S XDNG", "S X DN G", "SX DA NANG", "SXDANANG"},
            {"SXBDI", "SX BDI", "SXB DI", "S XBDI", "S X B D I", "SX BINH DINH", "SXBINHDINH"},
            {"SXDLK", "SX DLK", "SXD LK", "S XDLK", "S X D L K", "SX DAC LAK", "SXDACLAK", "SX DAK LAK", "SX DAKLAK"},
            {"SXTD", "SX TD", "SXT D", "S XTD", "S X T D", "SX THU DO", "SXTHUDO"},
            {"SXQN", "SX QN", "SXQ N", "S XQN", "S X Q N", "SX QUANG NINH", "SXQUANGNINH"},
            {"SXBN", "SX BN", "SXB N", "S XBN", "S X B N", "SX BAC NINH", "SXBACNINH"},
            {"SXHP", "SX HP", "SXH P", "S XHP", "S X H P", "SX HAI PHONG", "SXHAIPHONG"},
            {"SXND", "SX ND", "SXN D", "S XND", "S X N D", "SX NAM DINH", "SXNAMDINH"},
            {"SXTB", "SX TB", "SXT B", "S XTB", "S X T B", "SX THAI BINH", "SXTHAIBINH"},
            {"SXCM", "SX CM", "SXC M", "S XCM", "S X C M", "SX CA MAU", "SXCAMAU"},
            {"SXDT", "SX DT", "SXD T", "S XDT", "S X D T", "SX DONG THAP", "SXDONGTHAP"},
            {"SXBL", "SX BL", "SXB L", "S XBL", "S X B L", "SX BAC LIEU", "SXBACLIEU"},
            {"SXBTR", "SX BTR", "SXB TR", "S XBTR", "S X B T R", "SX BEN TRE", "SXBENTRE"},
            {"SXVT", "SX VT", "SXV T", "S XVT", "S X V T", "SX VUNG TAU", "SXVUNGTAU"},
            {"SXCT", "SX CT", "SXC T", "S XCT", "S X C T", "SX CAN THO", "SXCANTHO"},
            {"SXDNI", "SX DNI", "SXD NI", "S XDNI", "S X D NI", "SX DONG NAI", "SXDONG NAI"},
            {"SXST", "SS XT", "SXS T", "S XST", "S X S T", "SS XOC TRANG", "SXSOCTRANG"},
            {"SXAG", "SX AG", "SXA G", "S XAG", "S X A G", "SX AN GIANG", "SXANGIANG"},
            {"SXTN", "SX TN", "SXT N", "S XTN", "S X T N", "SX TAY NINH", "SXTAYNINH"},
            {"SXBD", "SX BD", "SXB D", "S XBD", "S X B D", "SX BINH DUONG", "SXDINHDUONG"},
            {"SXTRV", "SX TRV", "SXT V", "S XTV", "S X T V", "SX TRA VINH", "SXTRAVINH"},
            {"SXVL", "SX VL", "SXV L", "S XVL", "S X V L", "SX VINH LONG", "SXVINHLONG"},
            {"SXBP", "SX BP", "SXB P", "S XBP", "S X B P", "SX BINH PHUOC", "SXBINHPHUOC"},
            {"SXHG", "SX HG", "SXH G", "S XHG", "S X H G", "SX HAU GIANG", "SXHAUGIANG"},
            {"SXLA", "SX LA", "SXL A", "S XLA", "S X L A", "SX LONG AN", "SXLONGAN"},
            {"SXKG", "SX KG", "SXK G", "S XKG", "S X K G", "SX KIEN GIANG", "SXKIENIANG"},
            {"SXLD", "SX LD", "SXL D", "S XLD", "S X L D", "XSDL", "XS DL", "XS DA LAT", "XSDALAT", "SX LAM DONG", "SXLAMDONG"},
            {"SXTG", "SX TG", "SXT G", "S XTG", "S X T G", "SX TIEN GIANG", "SXTIENGIANG"},
            {"SXPY", "SX PY", "SXP Y", "S XPY", "S X P Y", "SX PHU YEN", "SXPHUYEN"},
            {"SXKH", "SX KH", "SXK H", "S XKH", "S X K H", "SX KHANH HOA", "SXKHANHHOA"},
            {"SXQT", "SX QT", "SXQ T", "S XQT", "S X Q T", "SX QUANG TRI", "SXQUANGTRI"},
            {"SXGL", "SX GL", "SXG L", "S XGL", "S X G L", "SX GIA LAI", "SXGIALAI"},
            {"SXNT", "SX NT", "SXN T", "S XNT", "S X N T", "SX NINH THUAN", "SXNINHTHUAN"},
            {"SXKT", "SX KT", "SXK T", "S XKT", "S X K T", "SX KOM TUM", "SXKONTUM"},
            {"SXQB", "SX QB", "SXQ B", "S XQB", "S X Q B", "SX QUANG BINH", "SXQUANGBINH"},
            {"SXMB", "SX MB", "S X M B", "SX MIENBAC"},
            {"SXMT", "SX MT", "S X M T", "SX MIENTRUNG"},
            {"SXMN", "SX MN", "S X M N", "SX MIENNAM"},
            {"SX"},
            {"DEC TIP1", "DECTIP1", "DECVIP1", "DEC VIP1", "DECVLP1", "DEC VLP1", "DECVTP1", "DEC VTP1", "DETIP1", "DE TIP1", "DE VIP1", "DEC TLP1",
                "DECTLP1", "DEC TP1", "DECTP1", "DEC TQ1", "DECTQ1", "DECTIP 1", "DEC TIP 1"},
            {"DEC TIP2", "DECTIP2", "DECVIP2", "DEC VIP2", "DETIP2", "DEC VTP2", "DE TIP2", "DE VIP2", "DEC TLP2",
                "DECTLP2", "DEC TP2", "DECTP2", "DEC TQ2", "DECTQ2", "DECTIP 2", "DEC TIQ2", "DECTIQ2", "DEC TIP 2"},
            {"DEC TIP", "DECTIP", "DECVIP", "DEC VIP", "DETIP", "DECVLP", "DEC TLP", "DE TIP", "DE VIP", "DEC TLP",
                "DECTLP", "DEC TP", "DECTP", "DEC TQ", "DECTQ", "DEC T IP", "DEC TIQ", "DECTIQ", "DEC T I P", "DEC BIP"},
            {"DEC XIEN1", "DECXIEN1", "DECXLEN1", "DEC XLEN1", "DEC XIE1", "DECXIE1", "DEC SIEN1", "DECSIEN1", "DECXIN1", "DEC XIN1"},
            {"DEC XIEN2", "DECXIEN2", "DECXLEN2", "DEC XLEN2", "DEC XIE2", "DECXIE2", "DEC SIEN2", "DECSIEN2", "DECXIN2", "DEC XIN2"},
            {"DEC XIEN", "DECXIEN", "DECXLEN", "DEC XLEN", "DEC XIE", "DECXIE", "DEC SIEN", "DECSIEN", "DECXIN", "DEC XIN"},
            {"DEC TX", "DECTX", "DEC T X"},
            {"DEC TAI", "DECTAI", "DEC TA"},
            {"DEC XIU", "DECXIU", "DEC XLN", "DEC XI"},
            {"DE CTL1", "DEC TL1", "DE C TL1", "DECTL1", "DEC T L1", "DEC TILE1", "DEC  TL1",
                "DEC TL 1", "DEC TI LE1", "DEC TYLE1", "DEC TY LE1", "DET TL1", "DE TL1"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>
            {"DE CTL2", "DEC TL2", "DE C TL2", "DECTL2", "DEC T L2", "DEC TILE2", "DEC  TL2",
                "DEC TL 2", "DEC TI LE2", "DEC TYLE2", "DEC TY LE2", "DET TL2", "DE TL2"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"DEC TL", "DECTL", "DE C TL", "DE CTL", "DEC T L", "DEC TILE", "DEC  TL",
                "DEC   TL", "DEC TI LE", "DEC TYLE", "DEC TY LE", "DEA TL", "DET TL", "DEB TL", "DE TL"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"DE CKQ1", "DEC KQ 1", "DEC KQ1", "DEC QK1", "DE C KQ1", "DECKQ1", "DEC K Q1", "DEC KETQUA1", "DEC KP1",
                "DEC  KQ1", "DEC   KQ1", "DEC KET QUA1", "DEC   KP1", "DEC KG1", "DEA KQ1", "DET KQ1", "DEB KQ1", "DE KQ1"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"DE CKQ2", "DEC KQ 2", "DEC KQ2", "DEC QK2", "DE C KQ2", "DECKQ2", "DEC K Q2", "DEC KETQUA2", "DEC KP2",
                "DEC  KQ2", "DEC   KQ2", "DEC KET QUA2", "DEC   KP2", "DEC KG2", "DEA KQ2", "DET KQ2", "DEB KQ2", "DE KQ2"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"DE CKQ", "DEC KQ", "DE C KQ", "DECKQ", "DEC QK", "DECQK", "DEC K Q", "DEC KETQUA", "DEC KP",
                "DEC  KQ", "DEC   KQ", "DET KQ", "DEB KQ", "DEC KET QUA", "DEC KG", "DEA KQ", "DET KQ", "DEB KQ", "DE KQ"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"DECBXH", "DEC BXH", "DE CBXH", "DECB XH", "DEC BANGXEPHANG",
                "DEC BANG XEP HANG", "DEA BXH", "DET BXH", "DEB BXH", "DE BXH"}, // Tra Bang Xep Hang <DEC BXH maGiai>
            {"DECLTD", "DEC LTD", "DE CLTD", "DECL TD", "DEC L T D", "DEC LICHTHIDAU"}, // Tra Lich thi dau  <DEC LTD maGiai>
            {"DECVPL", "DEC VPL", "DE CVPL", "DEC V P L", "DEA VPL", "DET VPL", "DEB VPL", "DE VPL"}, // Tra vua pha luoi <DEC VPL maGiai>
            {"DECDH", "DEC DH", "DE CDH", "DECD H", "DE C DH", "DEC D H"}, // Tra Doi hinh xuat phat <DEC DH maDoi>
            {"DECCLB", "DEC CLB", "DE CCLB", "DECC LB", "DE C CLB", "DEC C L B", "DEA CLB", "DET CLB", "DEB CLB", "DE CLB"}, // Tra ds Cau lac bo cua giai <DEC CLB maGiai>
            {"DECTT", "DEC TT", "DE CTT", "DECT T", "DEC T T", "DEC  TT", "DEC TUONGTHUAT", "DEC TUONG THUAT", "DE TT"}, // Tra thong tin truc tiep cua doi bong/Giai <DEC TT maGiai/maDoi>
            {"DECPD", "DEC PD", "DE CPD", "DECP D", "DEC P D", "DEA PD", "DET PD", "DEB PD", "DE PD"}, // Tra thong tin phong do cua doi bong/Giai <DEC PD maDoi>
            {"DECDD", "DEC DD", "DE CDD", "DECD D", "DEC D D", "DEA DD", "DET DD", "DEB DD", "DE DD"}, // Tra thong tin doi dau cua doi bong/Giai <DEC DD maDoi>
            {"DECTH", "DEC TH", "DE CTH", "DECT H", "DEC T H", "DEA TH", "DET TH", "DEB TH", "DE TH"}, // Tra thong tin tong hop vong dau/Giai <DEC TH maDoi>
            {"DECDT", "DEC DT", "DE CDT", "DECD T", "DEC D T"}, // Tra lich thi dau cua doi bong/Giai <DEC DT maDoi>
            {"DEC WL", "DECWL", "DECW", "DEC W", "DE CW", "DEC V", "DEA W", "DET W", "DEB W", "DE W"}, // Tra Y kien chuyen gia/Giai <DEC W maDoi>
            {"DEC CHO", "DECCHO", "DECHO", "DEC C H O", "DEC CH O"},
            {"DECHOT", "DEC HOT", "DECH OT", "DEC H O T", "DEC H OT"},
            {"DEC", "DE C", "D EC", "DE", "D E"}, // Tra KetQua XoSo <DE XSTD [NgayThang]>

            {"FB TIP1", "FBTIP1", "FB VIP1", "FBVIP1", "FB TIP1", "FB TLP1", "FBTLP1", "FBVLP1", "FB VLP1",
                "FB VTP1", "FBVTP1", "FB T I P1", "FB T IP1", "FB TP1", "FBTP1", "FB TQ1", "FBTQ1", "FB TIQ1", "FBTIQ1"},
            {"FB TIP2", "FBTIP2", "FB VIP2", "FBVIP2", "FB TIP2", "FB TLP2", "FBTLP2", "FBVLP2", "FB VLP2",
                "FB VTP2", "FBVTP2", "FB T I P2", "FB T IP2", "FB TP2", "FBTP2", "FB TQ2", "FBTQ2", "FB TIQ2", "FBTIQ2"},
            {"FB TIP", "FBTIP", "FB VIP", "FBVIP", "FB TLP", "FBTLP", "FBVLP", "FB VLP", "FB VTP", "FBVTP",
                "FB T I P", "FB T IP", "FB TP", "FBTP", "FB TQ", "FBTQ", "FB TIQ", "FBTIQ"},
            {"FB XIEN1", "FBXIEN1", "FBXLEN1", "FB XLEN1", "FB XIE1", "FBXIE1", "FB SIEN1", "FBSIEN1", "FBXIN1", "FB XIN1"},
            {"FB XIEN2", "FBXIEN2", "FBXLEN2", "FB XLEN2", "FB XIE2", "FBXIE2", "FB SIEN2", "FBSIEN2", "FBXIN2", "FB XIN2"},
            {"FB XIEN", "FBXIEN", "FBXLEN", "FB XLEN", "FB XIE", "FBXIE", "FB SIEN", "FBSIEN", "FBXIN", "FB XIN"},
            {"FB TX", "FBTX", "FB T X"},
            {"FB TAI", "FBTAI", "FB TA"},
            {"FB XIU", "FBXIU", "FB XLN", "FB XI"},
            {"FB TL1", "FBTL1", "FBT L1", "FB TILE1", "FB  T L1", "FB   TL1", "FB TL 1", "FB TI LE1", "FB TYLE1", "FB TY LE1"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>
            {"FB TL2", "FBTL2", "FBT L2", "FB TILE2", "FB  T L2", "FB   TL2", "FB TL 2", "FB TI LE2", "FB TYLE2", "FB TY LE2"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"FB TL", "FBTL", "FBT L", "FB TILE", "FB  T L", "FB   TL", "FB TI LE", "FB TYLE", "FB TY LE"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"FB KQ1", "FB KQ 1", "FB K Q1", "FBK Q1", "FBQK1", "FB QK1", "FBKQ1", "FB K Q1 ", "FB KETQUA1", "FB KP1",
                "FB  KQ1", "FB   KQ1", "FB KETQUA1", "FB KET QUA1", "FB  KETQUA1", "FB KG1"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"FB KQ2", "FB K Q2", "FBK Q2", "FBKQ2", "FBQK2", "FB QK2", "FB K Q2 ", "FB KETQUA2", "FB KP2",
                "FB  KQ2", "FB KQ 2", "FB   KQ2", "FB KETQUA2", "FB KET QUA2", "FB  KETQUA2", "FB KG2"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"FB KQ", "FB K Q", "FBK Q", "FBKQ", "FBQK", "FB QK", "FB K Q ", "FB KETQUA", "FB KP",
                "FB  KQ", "FB   KQ", "FB KETQUA", "FB KET QUA", "FB  KETQUA", "FB KG"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"FBBXH", "FB BXH", "FB BXH", "FBB XH", "FB BANGXEPHANG", "FB BANG XEP HANG"}, // Tra Bang Xep Hang <DEC BXH maGiai>
            {"FBLTD", "FB LTD", "FB LTD", "FBL TD", "FB L T D", "FB LICHTHIDAU", "FB LICH THI DAU"}, // Tra Lich thi dau  <DEC LTD maGiai>
            {"FBVPL", "FB VPL", "FB VPL", "FB V P L", "FB VPL", "FB VUA PHA LUOI", "FB VUAPHALUOI"}, // Tra vua pha luoi <DEC VPL maGiai>
            {"FBDH", "FB DH", "FBD H", "FB D H ", "FB DOIHINH", "FB DOI HINH"}, // Tra Doi hinh xuat phat <DEC DH maDoi>
            {"FBCLB", "FB CLB", "FB CLB", "FBC LB", "FB C L B"}, // Tra ds Cau lac bo cua giai <DEC CLB maGiai>
            {"FBTT", "FB TT", "FB T T", "FB T T", "FB  TT", "FB   TT", "FB TUONGTHUAT", "FB TUONG THUAT"}, // Tra thong tin truc tiep cua doi bong/Giai <DEC TT maGiai/maDoi>
            {"FBPD", "FB P D", "FBP D"}, // Tra thong tin phong do cua doi bong/Giai <DEC PD maDoi>
            {"FBDD", "FB DD", "FB DD", "FBD D", "FB D D"}, // Tra thong tin doi dau cua doi bong/Giai <DEC DD maDoi>
            {"FBT H", "FB TH", "FBTH", "FB T H"}, // Tra thong tin tong hop vong dau/Giai <DEC TH maDoi>
            {"FBDT", "FB DT", "FBD T", "FB D T"}, // Tra lich thi dau cua doi bong/Giai <DEC DT maDoi>
            {"FB WL", "FBWL", "FBW", "FB W", "FBW", "FB W", "FB  W", "FB V"}, // Tra Y kien chuyen gia/Giai <DEC W maDoi>
            {"FB CHO", "FBCHO", "FBC HO", "FB C H O", "FB CH O"},
            {"FBHOT", "FB HOT", "FBH OT", "FB H O T", "FB H OT"},
            {"FB"}, // Tra KetQua XoSo <DE XSTD [NgayThang]>

            {"FA TIP1", "FATIP1", "FA VIP1", "FAVIP1", "FA TIP1", "FA TLP1", "FATLP1", "FAVLP1"},
            {"FA TIP2", "FATIP2", "FA VIP2", "FAVIP2", "FA TIP2", "FA TLP2", "FATLP2", "FAVLP2"},
            {"FA TIP", "FATIP", "FA VIP", "FAVIP", "FA TLP", "FATLP", "FAVLP", "FA VLP", "FA TIQ", "FATIQ"},
            {"FA XIEN1", "FAXIEN1", "FAXLEN1", "FA XLEN1", "FA XIE1", "FAXIN1", "FA XIN1"},
            {"FA XIEN2", "FAXIEN2", "FAXLEN2", "FA XLEN2", "FA XIE2", "FASIEN2", "FAXIN2", "FA XIN2"},
            {"FA XIEN", "FAXIEN", "FAXLEN", "FA XLEN", "FA XIE", "FAXIE", "FA SIEN", "FASIEN"},
            {"FA TX", "FATX", "FA T X"},
            {"FA TAI", "FATAI", "FA TA"},
            {"FA XIU", "FAXIU", "FA XLN", "FA XI"},
            {"FA TL1", "FATL1", "FAT L1", "FA TILE1", "FA TL 1", "FA TI LE1", "FA TYLE1", "FA TY LE1"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>
            {"FA TL2", "FATL2", "FAT L2", "FA TILE2", "FA TL 2", "FA TI LE2", "FA TYLE2", "FA TY LE2"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"FA TL", "FATL", "FAT L", "FA TILE", "FA  T L", "FA   TL", "FA TYLE", "FA TY LE"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"FA KQ1", "FA KQ 1", "FA K Q1", "FAK Q1", "FAQK1", "FA QK1", "FAKQ1", "FA KETQUA1"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"FA KQ2", "FAKQ2", "FAQK2", "FA QK2", "FA K Q2 ", "FA KETQUA2", "FA KP2"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"FA KQ", "FA K Q", "FAK Q", "FAKQ", "FAQK", "FA QK", "FA K Q ", "FA KETQUA", "FA KP"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"FABXH", "FA BXH", "FA BXH", "FAB XH"}, // Tra Bang Xep Hang <DEC BXH maGiai>
            {"FALTD", "FA LTD", "FA LTD", "FAL TD"}, // Tra Lich thi dau  <DEC LTD maGiai>
            {"FAVPL", "FA VPL", "FA VPL", "FA V P L", "FA VPL"}, // Tra vua pha luoi <DEC VPL maGiai>
            {"FADH", "FA DH", "FAD H", "FA D H "}, // Tra Doi hinh xuat phat <DEC DH maDoi>
            {"FACLB", "FA CLB", "FA CLB", "FAC LB"}, // Tra ds Cau lac bo cua giai <DEC CLB maGiai>
            {"FATT", "FA TT", "FA T T"}, // Tra thong tin truc tiep cua doi bong/Giai <DEC TT maGiai/maDoi>
            {"FAPD", "FA P D", "FAP D"}, // Tra thong tin phong do cua doi bong/Giai <DEC PD maDoi>
            {"FADD", "FA DD", "FA DD", "FAD D", "FA D D"}, // Tra thong tin doi dau cua doi bong/Giai <DEC DD maDoi>
            {"FAT H", "FA TH", "FATH", "FA T H"}, // Tra thong tin tong hop vong dau/Giai <DEC TH maDoi>
            {"FADT", "FA DT", "FAD T", "FA D T"}, // Tra lich thi dau cua doi bong/Giai <DEC DT maDoi>
            {"FA WL", "FAWL", "FAW", "FA W", "FAW", "FA W", "FA V"}, // Tra Y kien chuyen gia/Giai <DEC W maDoi>
            {"FA CHO", "FACHO", "FAC HO", "FA C H O", "FA CH O"},
            {"FAHOT", "FA HOT", "FAH OT", "FA H O T", "FA H OT"},
            {"FA"}, // Tra KetQua XoSo <DE XSTD [NgayThang]>

            {"BD TIP1", "BDTIP1", "BD VIP1", "BDVIP1", "BD TIP1", "BD TLP1", "BDTLP1", "BDVLP1", "BD VLP1",
                "BD VTP1", "BDVTP1", "BD T I P1", "BD T IP1", "BD TP1", "BDTP1", "BD TQ1", "BDTQ1", "BD TIQ1", "BDTIQ1"},
            {"BD TIP2", "BDTIP2", "BD VIP2", "BDVIP2", "BD TIP2", "BD TLP2", "BDTLP2", "BDVLP2", "BD VLP2",
                "BD VTP2", "BDVTP2", "BD T I P2", "BD T IP2", "BD TP2", "BDTP2", "BD TQ2", "BDTQ2", "BD TIQ2", "BDTIQ2"},
            {"BD TIP", "BDTIP", "BD VIP", "BDVIP", "BD TLP", "BDTLP", "BDVLP", "BD VLP", "BD VTP", "BDVTP",
                "BD T I P", "BD T IP", "BD TP", "BDTP", "BD TQ", "BDTQ", "BD TIQ", "BDTIQ"},
            {"BD XIEN1", "BDXIEN1", "BDXLEN1", "BD XLEN1", "BD XIE1", "BDXIE1", "BD SIEN1", "BDSIEN1", "BDXIN1", "BD XIN1"},
            {"BD XIEN2", "BDXIEN2", "BDXLEN2", "BD XLEN2", "BD XIE2", "BDXIE2", "BD SIEN2", "BDSIEN2", "BDXIN2", "BD XIN2"},
            {"BD XIEN", "BDXIEN", "BDXLEN", "BD XLEN", "BD XIE", "BDXIE", "BD SIEN", "BDSIEN", "BDXIN", "BD XIN"},
            {"BD TX", "BDTX", "BD T X"},
            {"BD TAI", "BDTAI", "BD TA"},
            {"BD XIU", "BDXIU", "BD XLN", "BD XI"},
            {"BD TL1", "BDTL1", "BDT L1", "BD TILE1", "BD  T L1", "BD   TL1", "BD TL 1", "BD TI LE1", "BD TYLE1", "BD TY LE1"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>
            {"BD TL2", "BDTL2", "BDT L2", "BD TILE2", "BD  T L2", "BD   TL2", "BD TL 2", "BD TI LE2", "BD TL2.", "BD TYLE2", "BD TY LE2"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"BD TL", "BDTL", "BDT L", "BD TILE", "BD  T L", "BD   TL", "BD TI LE", "BD TYLE", "BD TY LE"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"BD KQ1", "BD KQ 1", "BD K Q1", "BDK Q1", "BDQK1", "BD QK1", "BDKQ1", "BD K Q1 ", "BD KETQUA1", "BD KP1",
                "BD  KQ1", "BD   KQ1", "BD KETQUA1", "BD KET QUA1", "BD  KETQUA1"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"BD KQ2", "BD K Q2", "BDK Q2", "BDKQ2", "BDQK2", "BD QK2", "BD K Q2 ", "BD KETQUA2", "BD KP2",
                "BD  KQ2", "BD KQ 2", "BD   KQ2", "BD KETQUA2", "BD KET QUA2", "BD  KETQUA2"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"BD KQ", "BD K Q", "BDK Q", "BDKQ", "BDQK", "BD QK", "BD K Q ", "BD KETQUA", "BD KP",
                "BD  KQ", "BD   KQ", "BD KETQUA", "BD KET QUA", "BD  KETQUA"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"BDBXH", "BD BXH", "BD BXH", "BDB XH", "BD BANGXEPHANG", "BD BANG XEP HANG"}, // Tra Bang Xep Hang <DEC BXH maGiai>
            {"BDLTD", "BD LTD", "BD LTD", "BDL TD", "BD L T D", "BD LICHTHIDAU", "BD LICH THI DAU"}, // Tra Lich thi dau  <DEC LTD maGiai>
            {"BDVPL", "BD VPL", "BD VPL", "BD VPL", "BD VUA PHA LUOI", "BD VUAPHALUOI"}, // Tra vua pha luoi <DEC VPL maGiai>
            {"BDDH", "BD DH", "BDD H", "BD D H ", "BD DOIHINH", "BD DOI HINH"}, // Tra Doi hinh xuat phat <DEC DH maDoi>
            {"BDCLB", "BD CLB", "BD CLB", "BDC LB", "BD C L B"}, // Tra ds Cau lac bo cua giai <DEC CLB maGiai>
            {"BDTT", "BD TT", "BD T T", "BD  TT", "BD   TT", "BD TUONGTHUAT", "BD TUONG THUAT"}, // Tra thong tin truc tiep cua doi bong/Giai <DEC TT maGiai/maDoi>
            {"BDPD", "BD P D", "BDP D"},
            {"BDDD", "BD DD", "BD DD", "BDD D", "BD D D"}, // Tra thong tin doi dau cua doi bong/Giai <DEC DD maDoi>
            {"BDT H", "BD TH", "BDTH", "BD T H"}, // Tra thong tin tong hop vong dau/Giai <DEC TH maDoi>
            {"BDDT", "BD DT", "BDD T", "BD D T"}, // Tra lich thi dau cua doi bong/Giai <DEC DT maDoi>
            {"BD WL", "BDWL", "BDW", "BD W", "BDW", "BD W", "BD  W", "BD V"}, // Tra Y kien chuyen gia/Giai <DEC W maDoi>
            {"BD CHO", "BDCHO", "BDC HO", "BD C H O", "BD CH O"},
            {"BDHOT", "BD HOT", "BDH OT", "BD H O T", "BD H OT"},
            {"BD"}, // Tra KetQua XoSo <DE XSTD [NgayThang]>

            {"BET TIP1", "BETTIP1", "BETVIP1", "BET VIP1", "BETVLP1", "BET VLP1", "BETVTP1", "BET VTP1", "BETIP1", "BE TIP1", "BE VIP1", "BET TLP1", "BETTLP1", "BETTQ1", "BETTIP 1", "BET TIP 1"},
            {"BET TIP2", "BETTIP2", "BETVIP2", "BET VIP2", "BETIP2", "BETVLP2", "BET VLP2", "BETVTP2", "BET VTP2", "BE TIP2", "BE VIP2", "BET TLP2", "BETTLP2", "BETTQ2", "BETTIP 2", "BET TIP 2"},
            {"BET TIP", "BETTIP", "BETVIP", "BET VIP", "BETIP", "BETVLP", "BET VLP", "BETVTP", "BET VTP", "BET TLP", "BE TIP", "BE VIP", "BET TLP", "BETTLP", "BETTQ", "BET T IP", "BET BIP"},
            {"BET XIEN1", "BETXIEN1", "BETXLEN1", "BET XLEN1", "BET XIE1", "BETXIE1", "BET SIEN1", "BETSIEN1", "BETXIN1", "BET XIN1"},
            {"BET XIEN2", "BETXIEN2", "BETXLEN2", "BET XLEN2", "BET XIE2", "BETXIE2", "BET SIEN2", "BETSIEN2", "BETXIN2", "BET XIN2"},
            {"BET XIEN", "BETXIEN", "BETXLEN", "BET XLEN", "BET XIE", "BETXIE", "BET SIEN", "BETSIEN", "BETXIN", "BET XIN"},
            {"BET TX", "BETTX", "BET T X"},
            {"BET TAI", "BETTAI", "BET TA"},
            {"BET XIU", "BETXIU", "BET XLN", "BET XI"},
            {"BE TTL1", "BET TL1", "BE T TL1", "BETTL1", "BET T L1", "BET TILE1",
                "BET TI LE1", "BET TYLE1", "BEA TL1", "BET TL1", "BEB TL1", "BE TL1"}, // Tra Ti le ca cuoc <BET TL maDoi/maGiai>
            {"BE TTL2", "BET TL2", "BE T TL2", "BETTL2", "BET T L2", "BET TILE2",
                "BET TI LE2", "BET TYLE2", "BEA TL2", "BET TL2", "BEB TL2", "BE TL2"}, // Tra Ti le ca cuoc <BET TL maDoi/maGiai>

            {"BET TL", "BETTL", "BE T TL", "BE TTL", "BET T L", "BET TILE", "BET TYLE", "BET TY LE", "BEA TL", "BET TL", "BEB TL", "BE TL"}, // Tra Ti le ca cuoc <BET TL maDoi/maGiai>

            {"BE TKQ1", "BET KQ 1", "BET KQ1", "BET QK1", "BE T KQ1", "BETKQ1", "BET K Q1", "BET KETQUA1", "BET KP1",
                "BEB KQ1", "BET KQ1", "BET KET QUA1", "BET KG1", "BEA KQ1", "BET KQ1", "BEB KQ1", "BE KQ1"}, // Tra Ket Qua BongDa <BET KQ maGiai/maDoi>
            {"BE TKQ2", "BET KQ 2", "BET KQ2", "BET QK2", "BE T KQ2", "BETKQ2", "BET KETQUA2", "BET KP2",
                "BEB KQ2", "BET KQ2", "BET KET QUA2", "BET KG2", "BEA KQ2", "BET KQ2", "BEB KQ2", "BE KQ2"}, // Tra Ket Qua BongDa <BET KQ maGiai/maDoi>

            {"BE TKQ", "BET KQ", "BE T KQ", "BETKQ", "BET QK", "BETQK", "BET K Q", "BET KETQUA", "BET KP",
                "BET KQ", "BEB KQ", "BET KET QUA", "BET KG", "BEA KQ", "BET KQ", "BEB KQ", "BE KQ"}, // Tra Ket Qua BongDa <BET KQ maGiai/maDoi>

            {"BETBXH", "BET BXH", "BE TBXH", "BETB XH", "BET BANGXEPHANG",
                "BET BANG XEP HANG", "BEA BXH", "BET BXH", "BEB BXH", "BE BXH"}, // Tra Bang Xep Hang <BET BXH maGiai>
            {"BETLTD", "BET LTD", "BE TLTD", "BETL TD", "BET L T D", "BET LICHTHIDAU"}, // Tra Lich thi dau  <BET LTD maGiai>
            {"BETVPL", "BET VPL", "BE TVPL", "BET V P L", "BEA VPL", "BET VPL", "BEB VPL", "BE VPL"}, // Tra vua pha luoi <BET VPL maGiai>
            {"BETDH", "BET DH", "BE TDH", "BETD H", "BE T DH", "BET D H"}, // Tra Doi hinh xuat phat <BET DH maDoi>
            {"BETCLB", "BET CLB", "BE TCLB", "BETC LB", "BE T CLB", "BET C L B", "BEA CLB", "BET CLB", "BEB CLB", "BE TLB"}, // Tra ds Cau lac bo cua giai <BET CLB maGiai>
            {"BETTT", "BET TT", "BE TTT", "BETT T", "BET T T", "BET  TT", "BET TUONGTHUAT", "BE TT"}, // Tra thong tin truc tiep cua doi bong/Giai <BET TT maGiai/maDoi>
            {"BETPD", "BET PD", "BE TPD", "BETP D", "BEA PD", "BET PD", "BEB PD", "BE PD"}, // Tra thong tin phong do cua doi bong/Giai <BET PD maDoi>
            {"BETDD", "BET DD", "BE TDD", "BETD D", "BET D D", "BEA DD", "BET DD", "BEB DD", "BE DD"}, // Tra thong tin doi dau cua doi bong/Giai <BET DD maDoi>
            {"BETTH", "BET TH", "BE TTH", "BETT H", "BET T H", "BEA TH", "BET TH", "BEB TH", "BE TH"}, // Tra thong tin tong hop vong dau/Giai <BET TH maDoi>
            {"BETDT", "BET DT", "BE TDT", "BETD T", "BET D T"}, // Tra lich thi dau cua doi bong/Giai <BET DT maDoi>
            {"BET WL", "BETWL", "BETW", "BET W", "BE TW", "BET V", "BEA W", "BET W", "BEB W", "BE W"}, // Tra Y kien chuyen gia/Giai <BET W maDoi>
            {"BET CHO", "BETCHO", "BETHO", "BET C H O", "BET CH O"},
            {"BETHOT", "BET HOT", "BETH OT", "BET H O T", "BET H OT"},
            {"BET", "BE T", "BE"}, // Tra KetQua XoSo <BE XSTD [NgayThang]>

            {"TIP1", "TIP 1", "TI P1", "TIQ1"},
            {"TIP2", "TIP 2", "TI P2", "TIQ2"},
            {"TIP", "TIQ", "TI P", "TI"},
            {"VIP1", "VIP 1", "VI P1", "VIQ1"},
            {"VIP2", "VIP 2", "VI P2", "VIQ2"},
            {"VIP", "VIQ", "VI P", "VI"},
            {"SIEN1", "SI EN 1", "SIE N 1", "SIEL1"},
            {"SIEN2", "SIEN 2", "SI EN2", "SIE N 2"},
            {"SIEN", "SI EN", "SIE N", "SI"},
            {"TX"},
            {"TAI", "TA"},
            {"KE"},
            {"XIU"},
            {"XIEN1", "XI EN 1", "XIE N 1", "XIEL1", "XJEN1"},
            {"XIEN2", "XIEN 2", "XI EN2", "XIE N 2", "XJEN2"},
            {"XIEN", "XI EN", "XIE N", "XJEN", "XI"},
            {"BTNS", "BT NS", "BT N S"}, // Bi mat ngay sinh <BT NS NgaySinh>
            {"BTNC", "BT NC", "BTNICK", "BT NICK", "BT NICH"}, // Bi mat nick chat <BT NICK NickChat>
            {"BTHN", "BT HN", "BT HOMNAY", "BTHOMNAY", "BT HOM NAY"}, // Bi mat ngay hom nay <BT HOMNAY >
            {"BT NM", "BTNM", "BT N M", "BTNGAYMAI", "BT NGAYMAI"}, // Bi mat ngay mai <BT NGAYMAI>
            {"BT DT", "BTDT", "BT D T", "BT SDT", "BTSDT"}, // Bi mat so dien thoai cua ban <BT SDT SoDienThoai>
            {"BT AC", "BTAC", "BT A C"}, // Boi vui AiCap <BT AC NgaySinh>
            {"BTNU", "BT NU", "BT N U"}, // Boi Nu <BTNU NgaySinh>
            {"BTNAM", "BT NAM", "BT N A M"}, // Boi Nam <BTNAM NgaySinh>
            {"BTKIEU", "BT KIEU", "BT K I E U"}, // Boi Kieu (dua theo truyen kieu) <BT KIEU TenBan TenNguoiAy>
            {"BTCHU", "BT CHU", "B T CHU", "BT C H U", "BT CH U"}, // Bi mat ten cua ban: <BTA ChuCaiDauTienCuaTenBan>
            {"BTVL", "BT VL", "BT V L", "B T V L"}, // Chon Viec Lam theo ngay sinh <BT VL NgaySinh ThangSinh>
            {"BT TEN", "BTTEN", "BT T EN", "BT T E N", "B T TEN"}, // Bi mat TenBan voi TenNguoiAy <BT TEN TenBan TenNguoiAy>
            {"BT AL", "BTAL", "BT A L", "B T A L", "B T AL", "B TAL"}, // Boi vui kieu Y <BT AL NgaySinh>
            {"BT HOAHONG", "BTHOAHONG", "BT HOA HONG", "BT HAOHONG", "BT HAO HONG"}, // Boi hoa hong
            {"BT HOA", "BTHOA", "BT H O A", "BTH OA", "B T HOA", "BT HAO"}, // Boi theo cac loai hoa <BT HOA ThangSinh>
            {"BTNB", "BT NB", "BT N B", "BTN B", "B TNB", "B T N B"}, // Boi Nhat Ban (BT NB NgaySinh ThangSinh)
            {"BTNC", "BT NC", "BT N C", "B TNC", "BTN C", "B T N C"},
            {"BTGPRS", "BT GPRS", "BT GP RS", "BTGP RS"},
            {"BTKM", "BT KM", "BT K M", "B TKM", "BTK M", "B T K M"},
            {"BTBH", "BT BH", "BT B H"},
            {"BTCV", "BT CV", "BT C V"},
            {"BTDD", "BT DD", "BT D D"},
            {"BTDMG", "BT DMG", "BT D MG", "BTDM G", "BT D M G"},
            {"BTDM", "BT DM", "BT D M"},
            {"BTHG", "BT HG", "BT H G"},
            {"BTHT", "BT HT", "BT H T"},
            {"BTRN", "BT RN", "BT R N"},
            {"BTNT", "BT NT", "BT N T"},
            {"BTVX", "BT VX", "BT V X"},
            {"BT", "B T", "B T "},
            {"CT"}, //dich vu gui loi chuc tet
            {"CD"}, //dich vu cau doi tet
            {"TE"}, //dich vu loi chuc tet
            {"XD"}, // dich vu xong dat

            {"SC"},
            {"SOICAU", "SOI CAU", "SO ICAU", "SOCAU", "SO CAU", "SOI C A U"},
            //{"S0"},
            {"LO"},
            {"L0", "L"},
            {"DU"},
            {"KQ"},
            {"CAU", "CA U", "CA", "C"},
            {"MB"}, //dvu cau
            {"TK"}, //dvu cau
            {"GU"}, //dvu cau
            {"GA"}, //d.vu cau
            {"AU"}, //dvu cau
            {"HO"}, //dvu cau
            {"KP"}, //dvu cau
            //{"NG"},                     //dvu cau
            {"X"},
            {"TVHCM", "TV HCM", "TVXSHCM", "TV XS HCM", "TVH CM", "TV TP", "TVTP", "TV HO CHI MINH"},
            {"TVQNG", "TV QNG", "TVXSQNG", "TV XS QNG", "TVQ NG", "TV Q N G", "TV QUANG NGAI"},
            {"TVDNO", "TV DNO", "TVXSDNO", "TV XS DNO", "TVD NO", "TV D N O", "TV DAK NONG"},
            {"TVBTH", "TV BTH", "TVXSBTH", "TV XS BTH", "TVB TH", "TV B T", "TV BINH THUAN"},
            {"TVTTH", "TV TTH", "TVXSTTH", "TV XS TTH", "TVT TH", "TV T T H", "TV THUA THIEN HUE"},
            {"TVQNM", "TV QNN", "TVXSQNM", "TV XS QNN", "TVQ NM", "TV Q N M", "TV QUANG NAM"},
            {"TVDNG", "TV DNG", "TVXSDNG", "TV XS DNG", "TVD NG", "TV D N G", "TV DA NANG"},
            {"TVBDI", "TV BDI", "TVXSBDI", "TV XS BDI", "TVB DI", "TV B DI", "TV BINH DINH", "TV BINHDINH"},
            {"TVDLK", "TV DLK", "TVXSDLK", "TV XS DLK", "TVD LK", "TV D L K", "TV DACLAK", "TV DAC LAK", "TV DAK LAK", "TV DAKLAK"},
            {"TVTD", "TV TD", "TVT D", "TV HN"},
            {"TVQN", "TV QN", "TVQ N", "TV Q N"},
            {"TVBN", "TV BN", "TVB N", "TV B N"},
            {"TVHP", "TV HP", "TVH P", "TV H P"},
            {"TVND", "TV ND", "TVN D", "TV N D"},
            {"TVTB", "TV TB", "TVT B", "TV T B"},
            {"TVCM", "TV CM", "TVXSCM", "TV XSCM", "TVC M", "TV C M", "TV CA MAU", "TV CAMAU"},
            {"TVDT", "TV DT", "TVXSDT", "TV XSDT", "TVD T", "TV D T", "TV DONG THAP", "TV DONGTHAP"},
            {"TVBL", "TV BL", "TVXSBL", "TV XSBL", "TVB L", "TV B L", "TV BAC LIEU", "TV BACLIEU"},
            {"TVBTR", "TV BTR", "TVXSBTR", "TV XSBTR", "TVB TR", "TV B T R", "TV BENTRE", "TV BEN TRE"},
            {"TVVT", "TV VT", "TVXSVT", "TV XSVT", "TVV T", "TV VUNG TAU", "TV VUNGTAU"},
            {"TVCT", "TV CT", "TVXSCT", "TV XSCT", "TVC T", "TV CAN THO", "TV CANTHO"},
            {"TVDNI", "TV DNI", "TVDXSNI", "TV XSDNI", "TVD NI", "TVDN", "TV DN", "TV DONG NAI"},
            {"TVST", "TV ST", "TVXSST", "TV XSST", "TVS T", "TV SOC TRANG", "TV SOCTRANG"},
            {"TVAG", "TV AG", "TVXSAG", "TV XSAG", "TVA G", "TV AN GIANG", "TV ANGIANG"},
            {"TVTN", "TV TN", "TVXSTN", "TV XSTN", "TVT N", "TV TAY NINH", "TV TAYNINH"},
            {"TVBD", "TV BD", "TVXSBD", "TV XSBD", "TVB D", "TV BINH DUONG", "TV BINHDUONG", "TVBINHDUONG"},
            {"TVTRV", "TV TRV", "TVXSTRV", "TV XSTRV", "TVT V", "TVTV", "TV TV"},
            {"TVVL", "TV VL", "TVXSVL", "TV XSVL", "TVV L", "TV VINH LONG", "TV VINHLONG"},
            {"TVBP", "TV BP", "TVXSBP", "TV XSBP", "TVB P", "TV BINH PHUOC", "TV BINHPHUOC"},
            {"TVHG", "TV HG", "TVXSHG", "TV XSHG", "TVH G", "TV HAU GIANG", "TV HAUGIANG"},
            {"TVLA", "TV LA", "TVXSLA", "TV XSLA", "TVL A", "TV LONG AN", "TV LONGAN"},
            {"TVKG", "TV KG", "TVXSKG", "TV XSKG", "TVK G", "TV KIEN GIANG", "TV KIENGIANG"},
            {"TVLD", "TV LD", "TVXSLD", "TV XSLD", "TVL D", "TVDL", "TV DL", "TV DA LAT", "TVDALAT"},
            {"TVTG", "TV TG", "TVXSTG", "TV XSTG", "TVSXTG", "TV SX TG", "TVT G", "TV TIEN GIANG", "TVTIENGIANG"},
            {"TVPY", "TV PY", "TVXSPY", "TV XSPY", "TVP Y", "TV PHU YEN", "TV PHUYEN"},
            {"TVKH", "TV KH", "TVXSKH", "TV XSKH", "TVK H", "TV KHANH HOA", "TVKHANHHOA"},
            {"TVQT", "TV QT", "TVXSQT", "TV XSQT", "TVQ T", "TV QUANG TRI", "TV QUANGTRI"},
            {"TVGL", "TV GL", "TVXSGL", "TV XSGL", "TVG L", "TV GIA LAI", "TV GIALAI"},
            {"TVNT", "TV NT", "TVXSNT", "TV XSNT", "TVN T", "TV NINH THUAN", "TV NINHTHUAN"},
            {"TVKT", "TV KT", "TVXSKT", "TV XSKT", "TVK T", "TV KON TUM"},
            {"TVQB", "TV QB", "TV Q B", "TV QUANG BINH", "TV QUANGBINH"},
            {"TVMB", "TV MB", "TV M B"},
            {"TVMT", "TV MT", "TV M T"},
            {"TVMN", "TV MN", "TV M N", "TV MIEN NAM", "TV MIENNAM"},
            {"TV"},
            {"TUHCM", "TU HCM", "TUH CM", "TU TP", "TUTP", "TU HOCHIMINH", "TU HO CHI MINH"},
            {"TUQNG", "TU QNG", "TUQ NG", "TU Q N G", "TU QUANG NGAI", "TU QUANGNGAI"},
            {"TUDNO", "TU DNO", "TUD NO", "TU D N O", "TU DAC NONG", "TU DAKNONG"},
            {"TUBTH", "TU BTH", "TUB TH", "TU B T", "TU BINH THUAN", "TU BINHTHUAN"},
            {"TUTTH", "TU TTH", "TUT TH", "TU T T H", "TU THUATHIENHUE", "TU THUA THIEN HUE"},
            {"TUQNM", "TU QNN", "TUQ NM", "TU Q N M", "TU QUANG NAM", "TU QUANG NAM"},
            {"TUDNG", "TU DNG", "TUD NG", "TU D N G", "TU DA NANG", "TU DANANG"},
            {"TUBDI", "TU BDI", "TUB DI", "TU B DI", "TU BINH DINH", "TU BINHDINH"},
            {"TUDLK", "TU DLK", "TUD LK", "TU D L K", "TU DAC LAK", "TU DACLAK"},
            {"TUTD", "TU TD", "TUT D", "TU HN", "TU THU DO", "TU THUDO"},
            {"TUQN", "TU QN", "TUQ N", "TU Q N", "TU QUANG NINH"},
            {"TUBN", "TU BN", "TUB N", "TU B N"},
            {"TUHP", "TU HP", "TUH P", "TU H P"},
            {"TUND", "TU ND", "TUN D", "TU N D"},
            {"TUTB", "TU TB", "TUT B", "TU T B"},
            {"TUCM", "TU CM", "TUC M", "TU C M", "TU CA MAU", "TU CAMAU"},
            {"TUDT", "TU DT", "TUD T", "TU D T", "TU DONG THAP", "TU DONGTHAP"},
            {"TUBL", "TU BL", "TUB L", "TU B L", "TU BAC LIEU", "TU BACLIEU"},
            {"TUBTR", "TU BTR", "TUB TR", "TU B T R", "TU BEN TRE", "TU BENTRE"},
            {"TUVT", "TU VT", "TUV T", "TU VUNG TAU", "TU VUNGTAU"},
            {"TUCT", "TU CT", "TUC T", "TU CAN THO", "TU CANTHO"},
            {"TUDNI", "TU DNI", "TUD NI", "TUDN", "TU DN", "TU DONG NAI", "TU DONGNAI"},
            {"TUST", "TU ST", "TUS T", "TU SOC TRANG", "TU SOCTRANG"},
            {"TUAG", "TU AG", "TUA G", "TU AN GIANG", "TU ANGIANG"},
            {"TUTN", "TU TN", "TUT N", "TU TAY NINH", "TU TAYNINH"},
            {"TUBD", "TU BD", "TUB D", "TU BINH DUONG", "TU BINHDUONG"},
            {"TUTRV", "TU TRV", "TUT V", "TUTV", "TU TV", "TU TRA VINH", "TU TRAVINH"},
            {"TUVL", "TU VL", "TUV L", "TU VINH LONG", "TU VINHLONG"},
            {"TUBP", "TU BP", "TUB P", "TU BINH PHUOC", "TU BINHPHUOC"},
            {"TUHG", "TU HG", "TUH G", "TU HAU GIANG", "TU HAUGIANG"},
            {"TULA", "TU LA", "TUL A", "TU LONG AN", "TU LONGAN"},
            {"TUKG", "TU KG", "TUK G", "TU KIEN GIANG", "TU KIENGIANG"},
            {"TULD", "TU LD", "TUL D", "TUDL", "TU DL", "TU DA LAT", "TUDALAT"},
            {"TUTG", "TU TG", "TUT G", "TU TIEN GIANG", "TU TIENGIANG"},
            {"TUPY", "TU PY", "TUP Y", "TU PHU YEN", "TU PHUYEN"},
            {"TUKH", "TU KH", "TUK H", "TU KHANH HOA", "TU KHANHHOA"},
            {"TUQT", "TU QT", "TUQ T", "TU QUANG TRI", "TU QUANGTRI"},
            {"TUGL", "TU GL", "TUG L", "TU GIALAI", "TU GIA LAI"},
            {"TUNT", "TU NT", "TUN T", "TU NINH THUAN", "TU NINHTHUAN"},
            {"TUKT", "TU KT", "TUK T", "TU KON TUM", "TU KONTUM"},
            {"TUQB", "TU QB", "TU Q B", "TU QUANG BINH", "TU QUANGBINH"},
            {"TUMB", "TU MB", "TU M B"},
            {"TUMT", "TU MT", "TU M T"},
            {"TUMN", "TU MN", "TU M N"},
            {"TU"},
            {"VTHCM", "VT HCM", "VTH CM", "VT TP", "VTTP", "VT HO CHI MINH", "VT HOCHIMINH"},
            {"VTQNG", "VT QNG", "VTQ NG", "VT Q N G", "VT QUANG NGAI", "VT QUANGNGAI"},
            {"VTDNO", "VT DNO", "VTD NO", "VT D N O", "VT DAC NONG", "VT DACNONG"},
            {"VTBTH", "VT BTH", "VTB TH", "VT B T", "VT BINH THUAN", "VT BINHTHUAN"},
            {"VTTTH", "VT TTH", "VTT TH", "VT T T H", "VT THUA THIEN HUE", "VT THUATHIENHUE"},
            {"VTQNM", "VT QNN", "VTQ NM", "VT Q N M", "VT QUANG NAM", "VT QUANGNAM"},
            {"VTDNG", "VT DNG", "VTD NG", "VT D N G", "VT DA NANG", "VT DANANG"},
            {"VTBDI", "VT BDI", "VTB DI", "VT B DI", "VT BINH DINH", "VT BINHDINH"},
            {"VTDLK", "VT DLK", "VTD LK", "VT D L K", "VT DAC LAK", "VT DACLAK", "VT DAK LAK", "VT DAKLAK"},
            {"VTTD", "VT TD", "VTT D", "VT HN", "VT THU DO", "VT THUDO"},
            {"VTQN", "VT QN", "VTQ N", "VT Q N"},
            {"VTBN", "VT BN", "VTB N", "VT B N"},
            {"VTHP", "VT HP", "VTH P", "VT H P"},
            {"VTND", "VT ND", "VTN D", "VT N D"},
            {"VTTB", "VT TB", "VTT B", "VT T B"},
            {"VTCM", "VT CM", "VTC M", "VT C M", "VT CA MAU", "VT CAMAU"},
            {"VTDT", "VT DT", "VTD T", "VT D T", "VT DONG THAP", "VT DONGTHAP"},
            {"VTBL", "VT BL", "VTB L", "VT B L", "VT BAC LIEU", "VT BACLIEU"},
            {"VTBTR", "VT BTR", "VTB TR", "VT B T R", "VT BEN TRE", "VT BENTRE"},
            {"VTVT", "VT VT", "VTV T"},
            {"VTCT", "VT CT", "VTC T", "VT CAN THO", "VT CANTHO"},
            {"VTDNI", "VT DNI", "VTD NI", "VTDN", "VT DN", "VT DONG NAI", "VT DONGNAI"},
            {"VTST", "VT ST", "VTS T", "VT SOC TRANG", "VT SOCTRANG"},
            {"VTAG", "VT AG", "VTA G", "VT AN GIANG", "VT ANGIANG"},
            {"VTTN", "VT TN", "VTT N", "VT TAY NINH", "VT TAYNINH"},
            {"VTBD", "VT BD", "VTB D", "VT BINH DUONG", "VT BINHDUONG"},
            {"VTTRV", "VT TRV", "VTT V", "VTTV", "VT TV", "VT TRA VINH", "VTTRAVINH"},
            {"VTVL", "VT VL", "VTV L", "VT VINH LONG", "VT VINHLONG"},
            {"VTBP", "VT BP", "VTB P", "VT BINH PHUOC", "VT BINHPHUOC"},
            {"VTHG", "VT HG", "VTH G", "VT HAU GIANG", "VT HAUGIANG"},
            {"VTLA", "VT LA", "VTL A", "VT LONG AN", "VTLONGAN"},
            {"VTKG", "VT KG", "VTK G", "VT KIEN GIANG", "VT KIENGIANG"},
            {"VTLD", "VT LD", "VTL D", "VTDL", "VT DL", "VT DA LAT", "VTDALAT"},
            {"VTTG", "VT TG", "VTT G", "VT TIEN GIANG", "VT TIENGIANG"},
            {"VTPY", "VT PY", "VTP Y", "VT PHUCYEN", "VT PHUC YEN"},
            {"VTKH", "VT KH", "VTK H", "VT KHANH HOA", "VT KHANHHOA"},
            {"VTQT", "VT QT", "VTQ T", "VT QUANG TRI", "VT QUANGTRI"},
            {"VTGL", "VT GL", "VTG L", "VT GIA LAI", "VT GIALAI"},
            {"VTNT", "VT NT", "VTN T", "VT NINH THUAN", "VT NINHTHUAN"},
            {"VTKT", "VT KT", "VTK T", "VT KON TUM", "VT KONTUM"},
            {"VTQB", "VT QB", "VT Q B", "VT QUANG BINH", "VT QUANGBINH"},
            {"VTMB", "VT MB", "VT M B"},
            {"VTMT", "VT MT", "VT M T"},
            {"VTMN", "VT MN", "VT M N"},
            {"VT"},
            {"SOIHCM", "SOI HCM", "SOIH CM", "SOI TP", "SOITP", "SOHCM", "SO HCM"},
            {"SOIQNG", "SOI QNG", "SOIQ NG", "SOI Q N G", "SO QNG", "SOQNG"},
            {"SOIDNO", "SOI DNO", "SOID NO", "SOI D N O", "SO DNO", "SODNO"},
            {"SOIBTH", "SOI BTH", "SOIB TH", "SOI B T", "SO BTH", "SOBTH"},
            {"SOITTH", "SOI TTH", "SOIT TH", "SOI T T H", "SO TTH", "SOTTH"},
            {"SOIQNM", "SOI QNN", "SOIQ NM", "SOI Q N M", "SO QNM", "SOQNM"},
            {"SOIDNG", "SOI DNG", "SOID NG", "SOI D N G", "SO DNG", "SODNG"},
            {"SOIBDI", "SOI BDI", "SOIB DI", "SOI B DI", "SO BDI", "SOBDI"},
            {"SOIDLK", "SOI DLK", "SOID LK", "SOI D L K", "SO DLK", "SODLK"},
            {"SOITD", "SOI TD", "SOIT D", "SOI HN", "SO TD", "SOTD"},
            {"SOIQN", "SOI QN", "SOIQ N", "SOI Q N", "SO QN", "SOQN"},
            {"SOIBN", "SOI BN", "SOIB N", "SOI B N", "SO BN", "SOBN"},
            {"SOIHP", "SOI HP", "SOIH P", "SOI H P", "SO HP", "SOHP"},
            {"SOIND", "SOI ND", "SOIN D", "SOI N D", "SO ND", "SOND"},
            {"SOITB", "SOI TB", "SOIT B", "SOI T B", "SO TB", "SOTB"},
            {"SOICM", "SOI CM", "SOIC M", "SOI C M", "SO CM", "SOCM"},
            {"SOIDT", "SOI DT", "SOID T", "SOI D T", "SO DT", "SODT"},
            {"SOIBL", "SOI BL", "SOIB L", "SOI B L", "SO BL", "SOBL"},
            {"SOIBTR", "SOI BTR", "SOIB TR", "SOI B T R", "SO BTR", "SOBTR"},
            {"SOIVT", "SOI VT", "SOIV T", "SO VT", "SOVT"},
            {"SOICT", "SOI CT", "SOIC T", "SO CT", "SOCT"},
            {"SOIDNI", "SOI DNI", "SOID NI", "SOIDN", "SOI DN", "SODNI", "SO DNI"},
            {"SOIST", "SOI ST", "SOIS T", "SO ST", "SOST"},
            {"SOIAG", "SOI AG", "SOIA G", "SO AG", "SOAG"},
            {"SOITN", "SOI TN", "SOIT N", "SO TN", "SOTN"},
            {"SOIBD", "SOI BD", "SOIB D", "SO BD", "SOBD"},
            {"SOITRV", "SOI TRV", "SO TRV", "SOTRV"},
            {"SOIVL", "SOI VL", "SOIV L", "SO VL", "SOVL"},
            {"SOIBP", "SOI BP", "SOIB P", "SO BP", "SOBP"},
            {"SOIHG", "SOI HG", "SOIH G", "SO HG", "SOHG"},
            {"SOILA", "SOI LA", "SOIL A", "SO LA", "SOLA"},
            {"SOIKG", "SOI KG", "SOIK G", "SO KG", "SOKG"},
            {"SOILD", "SOI LD", "SOIL D", "SO LD", "SOLD", "SOIDL", "SOI DL", "SOI DA LAT", "SOIDALAT", "SO DL", "SODL", "SO DA LAT"},
            {"SOITG", "SOI TG", "SOIT G", "SO TG", "SOTG"},
            {"SOIPY", "SOI PY", "SOIP Y", "SO PY", "SOPY"},
            {"SOIKH", "SOI KH", "SOIK H", "SO KH", "SOKH"},
            {"SOIQT", "SOI QT", "SOIQ T", "SO QT", "SOQT"},
            {"SOIGL", "SOI GL", "SOIG L", "SO GL", "SOGL"},
            {"SOINT", "SOI NT", "SOIN T", "SO NT", "SONT"},
            {"SOIKT", "SOI KT", "SOIK T", "SO KT", "SOKT"},
            {"SOIMB", "SOI MB", "SOI M B", "SO MB", "SOMB"},
            {"SOIMT", "SOI MT", "SOI M T", "SO MT", "SOMT"},
            {"SOIMN", "SOI MN", "SOI M N", "SO MN", "SOMN"},
            {"SOI", "SO"},
            //----S0-123----
            {"S0IHCM", "S0I HCM", "S0ITP", "S0I TP", "S0IH CM", "S0I TP", "S0ITP", "S0HCM", "S0 HCM"},
            {"S0IQNG", "S0I QNG", "S0IQ NG", "S0I Q N G", "S0 QNG", "S0QNG"},
            {"S0IDNO", "S0I DNO", "S0ID NO", "S0I D N O", "S0 DNO", "S0DNO"},
            {"S0IBTH", "S0I BTH", "S0IB TH", "S0I B T", "S0 BTH", "S0BTH"},
            {"S0ITTH", "S0I TTH", "S0IT TH", "S0I T T H", "S0 TTH", "S0TTH"},
            {"S0IQNM", "S0I QNN", "S0IQ NM", "S0I Q N M", "S0 QNM", "S0QNM"},
            {"S0IDNG", "S0I DNG", "S0ID NG", "S0I D N G", "S0 DNG", "S0DNG"},
            {"S0IBDI", "S0I BDI", "S0IB DI", "S0I B DI", "S0 BDI", "S0BDI"},
            {"S0IDLK", "S0I DLK", "S0ID LK", "S0I D L K", "S0 DLK", "S0DLK"},
            {"S0ITD", "S0I TD", "S0IT D", "S0I HN", "S0 TD", "S0TD"},
            {"S0IQN", "S0I QN", "S0IQ N", "S0I Q N", "S0 QN", "S0QN"},
            {"S0IBN", "S0I BN", "S0IB N", "S0I B N", "S0 BN", "S0BN"},
            {"S0IHP", "S0I HP", "S0IH P", "S0I H P", "S0 HP", "S0HP"},
            {"S0IND", "S0I ND", "S0IN D", "S0I N D", "S0 ND", "S0ND"},
            {"S0ITB", "S0I TB", "S0IT B", "S0I T B", "S0 TB", "S0TB"},
            {"S0ICM", "S0I CM", "S0IC M", "S0I C M", "S0 CM", "S0CM"},
            {"S0IDT", "S0I DT", "S0ID T", "S0I D T", "S0 DT", "S0DT"},
            {"S0IBL", "S0I BL", "S0IB L", "S0I B L", "S0 BL", "S0BL"},
            {"S0IBTR", "S0I BTR", "S0IB TR", "S0I B T R", "S0 BTR", "S0BTR"},
            {"S0IVT", "S0I VT", "S0IV T", "S0 VT", "S0VT"},
            {"S0ICT", "S0I CT", "S0IC T", "S0 CT", "S0CT"},
            {"S0IDNI", "S0I DNI", "S0ID NI", "S0IDN", "S0I DN", "S0DNI", "S0 DNI"},
            {"S0IST", "S0I ST", "S0IS T", "S0 ST", "S0ST"},
            {"S0IAG", "S0I AG", "S0IA G", "S0 AG", "S0AG"},
            {"S0ITN", "S0I TN", "S0IT N", "S0 TN", "S0TN"},
            {"S0IBD", "S0I BD", "S0IB D", "S0 BD", "S0BD"},
            {"S0ITRV", "S0I TRV", "S0 TRV", "S0TRV"},
            {"S0IVL", "S0I VL", "S0IV L", "S0 VL", "S0VL"},
            {"S0IBP", "S0I BP", "S0IB P", "S0 BP", "S0BP"},
            {"S0IHG", "S0I HG", "S0IH G", "S0 HG", "S0HG"},
            {"S0ILA", "S0I LA", "S0IL A", "S0 LA", "S0LA"},
            {"S0IKG", "S0I KG", "S0IK G", "S0 KG", "S0KG"},
            {"S0ILD", "S0I LD", "S0IL D", "S0 LD", "S0LD", "S0IDL", "S0I DL", "S0I DA LAT", "S0IDALAT", "S0DL", "S0 DL"},
            {"S0ITG", "S0I TG", "S0IT G", "S0 TG", "S0TG"},
            {"S0IPY", "S0I PY", "S0IP Y", "S0 PY", "S0PY"},
            {"S0IKH", "S0I KH", "S0IK H", "S0 KH", "S0KH"},
            {"S0IQT", "S0I QT", "S0IQ T", "S0 QT", "S0QT"},
            {"S0IGL", "S0I GL", "S0IG L", "S0 GL", "S0GL"},
            {"S0INT", "S0I NT", "S0IN T", "S0 NT", "S0NT"},
            {"S0IKT", "S0I KT", "S0IK T", "S0 KT", "S0KT"},
            {"S0IMB", "S0I MB", "S0I M B", "S0 MB", "S0MB"},
            {"S0IMT", "S0I MT", "S0I M T", "S0 MT", "S0MT"},
            {"S0IMN", "S0I MN", "S0I M N", "S0 MN", "S0MN"},
            {"S0I", "SO"},
            {"SSHCM", "SS HCM", "SSH CM", "SS TP", "SSTP"},
            {"SSQNG", "SS QNG", "SSQ NG", "SS Q N G"},
            {"SSDNO", "SS DNO", "SSD NO", "SS D N O"},
            {"SSBTH", "SS BTH", "SSB TH", "SS B T"},
            {"SSTTH", "SS TTH", "SST TH", "SS T T H"},
            {"SSQNM", "SS QNN", "SSQ NM", "SS Q N M"},
            {"SSDNG", "SS DNG", "SSD NG", "SS D N G"},
            {"SSBDI", "SS BDI", "SSB DI", "SS B DI"},
            {"SSDLK", "SS DLK", "SSD LK", "SS D L K"},
            {"SSTD", "SS TD", "SST D", "SS HN"},
            {"SSQN", "SS QN", "SSQ N", "SS Q N"},
            {"SSBN", "SS BN", "SSB N", "SS B N"},
            {"SSHP", "SS HP", "SSH P", "SS H P"},
            {"SSND", "SS ND", "SSN D", "SS N D"},
            {"SSTB", "SS TB", "SST B", "SS T B"},
            {"SSCM", "SS CM", "SSC M", "SS C M"},
            {"SSDT", "SS DT", "SSD T", "SS D T"},
            {"SSBL", "SS BL", "SSB L", "SS B L"},
            {"SSBTR", "SS BTR", "SSB TR", "SS B T R"},
            {"SSVT", "SS VT", "SSV T"},
            {"SSCT", "SS CT", "SSC T"},
            {"SSDNI", "SS DNI", "SSD NI", "SSDN", "SS DN"},
            {"SSST", "SS ST", "SSS T"},
            {"SSAG", "SS AG", "SSA G"},
            {"SSTN", "SS TN", "SST N"},
            {"SSBD", "SS BD", "SSB D"},
            {"SSTRV", "SS TRV", "SST V", "SSTV", "SS TV"},
            {"SSVL", "SS VL", "SSV L"},
            {"SSBP", "SS BP", "SSB P"},
            {"SSHG", "SS HG", "SSH G"},
            {"SSLA", "SS LA", "SSL A"},
            {"SSKG", "SS KG", "SSK G"},
            {"SSLD", "SS LD", "SSL D", "SSDL", "SS DL", "SS DA LAT", "SSDALAT"},
            {"SSTG", "SS TG", "SST G"},
            {"SSPY", "SS PY", "SSP Y"},
            {"SSKH", "SS KH", "SSK H"},
            {"SSQT", "SS QT", "SSQ T"},
            {"SSGL", "SS GL", "SSG L"},
            {"SSNT", "SS NT", "SSN T"},
            {"SSKT", "SS KT", "SSK T"},
            {"SSMB", "SS MB", "SS M B"},
            {"SS"},
            {"DOHCM", "DO HCM", "DOTP", "DO TP", "DOXSHCM", "DO XSHCM", "DOH CM", "DO HO CHI MINH", "DO THANH PHO"},
            {"DOQNG", "DO QNG", "DOXSQNG", "DO XSQNG", "DOQ NG", "DO QUANG NGAI", "DO QUANGNGAI"},
            {"DODNO", "DO DNO", "DOXSDNO", "DO XSDNO", "DOD NO", "DO D N O", "DO DAC NONG"},
            {"DOBTH", "DO BTH", "DOXSBTH", "DO XSBTH", "DOB TH", "DO B T", "DO BINH THUAN", "DO BINHTHUAN"},
            {"DOTTH", "DO TTH", "DOXSTTH", "DO XSTTH", "DOT TH", "DO T T H", "DO THUA THIEN HUE"},
            {"DOQNM", "DO QNN", "DOXSQNM", "DO XSQNN", "DOQ NM", "DO Q N M", "DO QUANG NAM"},
            {"DODNG", "DO DNG", "DOXSDNG", "DO XSDNG", "DOD NG", "DO D N G", "DO DA NANG", "DO DANANG"},
            {"DOBDI", "DO BDI", "DOXSBDI", "DO XSBDI", "DOB DI", "DO B DI", "DO BINH DINH", "DO BINHDINH"},
            {"DODLK", "DO DLK", "DOXSDLK", "DO XSDLK", "DOD LK", "DO D L K", "DO DAK LAK"},
            {"DOTD", "DO TD", "DOXSTD", "DO XSTD", "DOT D", "DO HN", "DO HA NOI"},
            {"DOQN", "DO QN", "DOQ N", "DO Q N", "DO QUANG NINH"},
            {"DOBN", "DO BN", "DOB N", "DO B N", "DO BAC NINH", "DO BACNINH"},
            {"DOHP", "DO HP", "DOH P", "DO H P", "DO HAI PHONG", "DO HAIPHONG"},
            {"DOND", "DO ND", "DON D", "DO N D", "DO NAMDINH", "DO NAM DINH"},
            {"DOTB", "DO TB", "DOT B", "DO T B", "DO THAIBINH", "DO THAI BINH"},
            {"DOCM", "DO CM", "DOXSCM", "DO XSCM", "DOC M", "DO C M", "DO CA MAU", "DOCAMAU", "DO CAMAU"},
            {"DODT", "DO DT", "DOXSDT", "DO XSDT", "DOD T", "DO D T", "DO DONG THAP", "DO DONGTHAP"},
            {"DOBL", "DO BL", "DOXSBL", "DO XSBL", "DOB L", "DO B L", "DO BAC LIEU", "DO BACLIEU"},
            {"DOBTR", "DO BTR", "DOXSBTR", "DO XSBTR", "DOB TR", "DO B T R", "DO BENTRE", "DO BEN TRE"},
            {"DOVT", "DO VT", "DOXSVT", "DO XSVT", "DOV T", "DO VUNG TAU"},
            {"DOCT", "DO CT", "DOXSCT", "DO XSCT", "DOC T", "DO CAN THO", "DO CANTHO"},
            {"DODNI", "DO DNI", "DOXSDNI", "DOXS DNI", "DOD NI", "DODN", "DO DN", "DO DONGNAI", "DO DONGNAI"},
            {"DOST", "DO ST", "DOXSST", "DO XSST", "DOS T", "DO SOC TRANG", "DO SOCTRANG"},
            {"DOAG", "DO AG", "DOXSAG", "DO XSAG", "DOA G", "DO ANGIANG", "DO AN GIANG"},
            {"DOTN", "DO TN", "DOXSTN", "DO XSTN", "DOT N", "DO TAYNINH", "DO TAY NINH"},
            {"DOBD", "DO BD", "DOXSBD", "DO XSBD", "DOB D", "DO BINH DUONG", "DOBINHDUONG", "DO BINHDUONG"},
            {"DOTRV", "DO TRV", "DOXSTRV", "DO XSTRV", "DOT V", "DOTV", "DO TV", "DO TRA VINH", "DO TRAVINH"},
            {"DOVL", "DO VL", "DOXSVL", "DO XSVL", "DOV L", "DO VINHLONG", "DO VINH LONG"},
            {"DOBP", "DO BP", "DOXSBP", "DO XSBP", "DOB P", "DO BINH PHUOC", "DO BINHPHUOC"},
            {"DOHG", "DO HG", "DOXSHG", "DO XSHG", "DOH G", "DO HAU GIANG", "DOHAUGIANG"},
            {"DOLA", "DO LA", "DOXSLA", "DO XSLA", "DOL A", "DO LONG AN", "DO LONGAN"},
            {"DOKG", "DO KG", "DOXSKG", "DO XSKG", "DOK G", "DO KIENGIANG", "DO KIEN GIANG"},
            {"DOLD", "DO LD", "DOXSLD", "DO XSLD", "DOL D", "DO LAM DONG", "DO LAMDONG", "DODL", "DO DL", "DO DA LAT", "DODALAT"},
            {"DOTG", "DO TG", "DOXSTG", "DO XSTG", "DOSXTG", "DO SX TG", "DOT G", "DO TIENGIANG", "DO TIEN GIANG"},
            {"DOPY", "DO PY", "DOXSPY", "DO XSPY", "DOP Y", "DO PHU YEN", "DO PHUYEN"},
            {"DOKH", "DO KH", "DOXSKH", "DO XSKH", "DOK H", "DO KHANH HOA", "DO KHANHHOA"},
            {"DOQT", "DO QT", "DOXSQT", "DO XSQT", "DOQ T", "DO QUANG TRI"},
            {"DOGL", "DO GL", "DOXSGL", "DO XSGL", "DOG L", "DO GIA LAI", "DO GIALAI"},
            {"DONT", "DO NT", "DOXSNT", "DO XSNT", "DON T", "DO NINH THUAN"},
            {"DOKT", "DO KT", "DOXSKT", "DO XSKT", "DOK T", "DO KON TUM"},
            {"DOQB", "DO QB", "DO Q B", "DO QUANG BINH", "DO QUANGBINH"},
            {"DOMB", "DO MB", "DOXSMB", "DO XSMB", "DO M B", "DO MIEN BAC", "DO MIENBAC"},
            {"DOXS", "DOSX", "DO"},
            //D0-0,123
            {"D0HCM", "D0 HCM", "D0TP", "D0 TP", "D0XSHCM", "D0 XS HCM", "D0H CM", "D0 HO CHI MINH"},
            {"D0QNG", "D0 QNG", "D0Q NG", "D0 XSQNG", "D0XSQNG", "D0 QUANG NGAI", "D0 QUANGNGAI",},
            {"D0DNO", "D0 DNO", "D0D NO", "D0 XSDNO", "D0XSDNO", "D0 D N O", "D0 DAC NONG", "D0 DACNONG"},
            {"D0BTH", "D0 BTH", "D0B TH", "D0 XSBTH", "D0XSBTH", "D0 B T", "D0 BINHTHUAN", "D0 BINH THUAN"},
            {"D0TTH", "D0 TTH", "D0T TH", "D0 XSTTH", "D0XSTTH", "D0 T T H", "D0 THUA THIEN HUE", "D0 THUATHIENHUE"},
            {"D0QNM", "D0 QNN", "D0Q NM", "D0 XSQNN", "D0XSQNM", "D0 Q N M", "D0 QUANG NAM", "D0 QUANGNAM"},
            {"D0DNG", "D0 DNG", "D0D NG", "D0 XSDNG", "D0XSDNG", "D0 D N G", "D0 DA NANG", "D0 DANANG"},
            {"D0BDI", "D0 BDI", "D0B DI", "D0 XSBDI", "D0XSBDI", "D0 B DI", "D0 BINH DINH", "D0 BINHDINH"},
            {"D0DLK", "D0 DLK", "D0D LK", "D0 XSDLK", "D0XSDLK", "D0 D L K", "D0 DAC LAK", "D0 DACLAK"},
            {"D0TD", "D0 TD", "D0XSTD", "D0 XS TD", "D0T D", "D0 HN", "D0 THU DO"},
            {"D0QN", "D0 QN", "D0Q N", "D0 Q N"},
            {"D0BN", "D0 BN", "D0B N", "D0 B N"},
            {"D0HP", "D0 HP", "D0H P", "D0 H P"},
            {"D0ND", "D0 ND", "D0N D", "D0 N D"},
            {"D0TB", "D0 TB", "D0T B", "D0 T B"},
            {"D0CM", "D0 CM", "D0XSCM", "D0 XS CM", "D0C M", "D0 C M", "D0 CA MAU", "D0 CAMAU"},
            {"D0DT", "D0 DT", "D0XSDT", "D0 XSDT", "D0D T", "D0 D T", "D0 DONG THAP", "D0 DONGTHAP"},
            {"D0BL", "D0 BL", "D0XSBL", "D0 XSBL", "D0B L", "D0 B L", "D0 BAC LIEU", "D0 BACLIEU"},
            {"D0BTR", "D0 BTR", "D0XSBTR", "D0 XSBTR", "D0B TR", "D0 B T R", "D0 BEN TRE", "D0 BENTRE"},
            {"D0VT", "D0 VT", "D0XSVT", "D0 XSVT", "D0V T", "D0 VUNG TAU", "D0 VUNG TAU"},
            {"D0CT", "D0 CT", "D0XSCT", "D0 XSCT", "D0C T", "D0 CAN THO", "D0 CANTHO"},
            {"D0DNI", "D0 DNI", "D0XSDNI", "D0 XSDNI", "D0D NI", "D0DN", "D0 DN", "D0 DONG NAI", "D0 DONGNAI"},
            {"D0ST", "D0 ST", "D0XSST", "D0 XSST", "D0S T", "D0 SOC TRANG", "D0 SOCTRANG"},
            {"D0AG", "D0 AG", "D0XSAG", "D0 XSAG", "D0A G", "D0 AN GIANG", "D0 ANGIANG"},
            {"D0TN", "D0 TN", "D0XSTN", "D0 XSTN", "D0T N", "D0 TAYNINH", "D0 TAY NINH"},
            {"D0BD", "D0 BD", "D0XSBD", "D0 XSBD", "D0B D", "D0 BINH DUONG", "D0 BINHDUONG"},
            {"D0TRV", "D0 TRV", "D0XSTRV", "D0 XSTRV", "D0T V", "D0TV", "D0 TV", "D0 TRA VINH", "D0 TRAVINH"},
            {"D0VL", "D0 VL", "D0XSVL", "D0 XSVL", "D0V L", "D0 VINH LONG", "D0 VINHLONG"},
            {"D0BP", "D0 BP", "D0XSBP", "D0 XSBP", "D0B P", "D0 BINH PHUOC", "D0 BINHPHUOC"},
            {"D0HG", "D0 HG", "D0XSHG", "D0 XSHG", "D0H G", "D0 HAU GIANG", "D0 HAUGIANG"},
            {"D0LA", "D0 LA", "D0XSLA", "D0 XSLA", "D0L A", "D0 LONG AN", "D0 LONGAN"},
            {"D0KG", "D0 KG", "D0XSKG", "D0 XSKG", "D0K G", "D0 KIEN GIANG", "D0 KIENGIANG"},
            {"D0LD", "D0 LD", "D0XSLD", "D0 XSLD", "D0L D", "D0DL", "D0 DL", "D0 DA LAT", "D0DALAT"},
            {"D0TG", "D0 TG", "D0XSTG", "D0 XSTG", "D0T G", "D0 TIEN GIANG", "D0 TIENGIANG"},
            {"D0PY", "D0 PY", "D0XSPY", "D0 XSPY", "D0P Y", "D0 PHU YEN", "D0 PHUYEN"},
            {"D0KH", "D0 KH", "D0XSKH", "D0 XSKH", "D0K H", "D0 KHANH HOA", "D0 KHANHHOA"},
            {"D0QT", "D0 QT", "D0XSQT", "D0 XSQT", "D0Q T", "D0 QUANG TRI", "D0 QUANGTRI"},
            {"D0GL", "D0 GL", "D0XSGL", "D0 XSGL", "D0G L", "D0 GIA LAI", "D0 GIALAI"},
            {"D0NT", "D0 NT", "D0XSNT", "D0 XSNT", "D0N T", "D0 NINH THUAN", "D0 NINHTHUAN"},
            {"D0KT", "D0 KT", "D0XSKT", "D0 XSKT", "D0K T", "D0 KON TUM", "D0 KONTUM"},
            {"D0QB", "D0 QB", "D0 Q B", "D0 QUANG BINH", "D0 QUANGBINH"},
            {"D0MB", "D0 MB", "D0XSMB", "D0 XS MB", "D0 M B", "D0 MIEN BAC", "D0 MIENBAC"},
            {"D0 XS", "D0 SX", "D0"},
            {"DD XS", "DDXS", "DDSX", "DD"}, //dich vu do

            {"PTSN", "PTSO", "PT SN", "PT SO"},
            {"PTBS", "PT BS", "PTBX", "PT BX"},
            {"PTHN", "PT HN", "PT NHA"},
            {"PT HB", "PTHB", "PT BEP", "PTBEP"},
            {"PTBLV", "PT BLV", "PTLV", "PT LV"},
            {"PTHOP", "PT HOP", "PTH0P", "PT H0P"},
            {"PTKY", "PT KY", "PT KI", "PTKI"},
            {"PTBM", "PT BM", "PT B M"},
            {"PT AL", "PTAL", "PT A L"},
            {"PT NT", "PTNT", "PT N T"},
            {"PT GT", "PTGT", "PT G T"},
            {"PTMAUKY", "PT MAUKY", "PT MAU KY"},
            {"PT MAU", "PTMAU", "PTQB", "PT QB"},
            {"PTHT", "PT HT"},
            {"PT KH", "PTKH"},
            {"PTHX", "PT HX"},
            {"PTCV", "PT CV"},
            {"PTXD", "PT XD", "PT X D"},
            {"PTSDT", "PTDT", "PT SDT", "PT DT", "P SDT", "P S DT", "P T DT", "P T D T", "P T S D T", "P T", "PT", "P"},
            //
            {"FTSN", "FTSO", "FT SN", "FT SO"},
            {"FTBS", "FT BS", "FTBX", "FT BX"},
            {"FTHN", "FT HN", "FT NHA"},
            {"FT HB", "FTHB", "FT BEP", "FTBEP"},
            {"FTBLV", "FT BLV", "FTLV", "FT LV"},
            {"FTHOP", "FT HOP", "FTH0P", "FT H0P"},
            {"FTKY", "FT KY", "FT KI", "FTKI"},
            {"FTBM", "FT BM", "FT B M"},
            {"FT AL", "FTAL", "FT A L"},
            {"FT NT", "FTNT", "FT N T"},
            {"FT GT", "FTGT", "FT G T"},
            {"FTMAUKY", "FT MAUKY", "FT MAU KY"},
            {"FT MAU", "FTMAU", "PTQB", "PT QB"},
            {"FTHT", "FT HT"},
            {"FT KH", "FTKH"},
            {"FTHX", "FT HX"},
            {"FTCV", "FT CV"},
            {"FTSDT", "FTDT", "FT SDT", "FT DT", "FT"},
            {"SDT", "SD"},
            {"DT"},
            {"BL"}, // dich vu bao lo
            {"BAO LO", "BAOLO", "BA"}, // dich vu Bao lo

            {"VANGHN", "VANG HN", "VANG HANOI", "VANG HA NOI", "VANG H N"},
            {"VANGHCM", "VANG HCM", "VANG H C M", "VANG HO CHI MINH", "VANG HOCHIMINH"},
            {"VANGSJC", "VANG SJC", "VANG S J C", "VANG JSC", "VANGJSC"},
            {"VANGPNJ", "VANG PNJ", "VANG P N J"},
            {"VANG", "VA"},
            {"TYGIA USD", "TYGIAUSD", "TIGIA USD", "TIGIA USD", "TY GIA USD", "TY GIAUSD"},
            {"TYGIA EUR", "TYGIAEUR", "TYGIA EURO", "TYGIAEURO", "TY GIA EURO", "TY GIAEURO"},
            {"TYGIA YEN", "TYGIAYEN", "TY GIA YEN", "TY GIAYEN"},
            {"TYGIA", "TY"},
            {"NGOAITE USD", "NGOAITEUSD", "NGOAI TE USD", "NGOAI TEUSD"},
            {"NGOAITE EUR", "NGOAITEEUR", "NGOAI TE EURO", "NGOAI TEEURO"},
            {"NGOAITE YEN", "NGOAITEYEN", "NGOAI TE YEN", "NGOAI TEYEN"},
            {"NGOAITE", "NG"},
            {"USD", "US"},
            {"EURO", "EUR"},
            {"YEN", "YE"},
            {"VBAHN", "VBA HN", "VBA HANOI", "VBA HA NOI", "VBA H N"},
            {"VBAHCM", "VBA HCM", "VBA H C M", "VBA HO CHI MINH", "VBA HOCHIMINH"},
            {"VBASJC", "VBA SJC", "VBA S J C", "VBA JSC", "VBAJSC"},
            {"VBAPNJ", "VBA PNJ", "VBA P N J"},
            {"VBA USD", "VBAUSD"},
            {"VBA EUR", "VBAEUR"},
            {"VBA YEN", "VBAYEN"},
            {"VBA"},
            {"VB"},
            {"OFFQC", "OFF QC"},
            {"HUY", "HU"},
            {"D O", "D 0", "D"},
            {"Z"}, // test bookmark msg
            {"S"},
            {"T"}
        };

        //Mang mot chieu chua cac command code chuan. Moi comandCode chuan co the ung voi nhieu
        //loai command code ma nguoi su dung gui.
        String[] validCommandCode = {
            "XSHCM", "XSQNG", "XSDNO", "XSBTH", "XSTTH", "XSQNM", "XSDNG", "XSBDI", "XSDLK",
            "XSTD", "XSQN", "XSBN", "XSHP", "XSND", "XSTB", "XSCM", "XSDT", "XSBL", "XSBTR",
            "XSVT", "XSCT", "XSDNI", "XSST", "XSAG", "XSTN", "XSBD", "XSTRV", "XSVL", "XSBP",
            "XSHG", "XSLA", "XSKG", "XSLD", "XSTG", "XSPY", "XSKH", "XSQT", "XSGL", "XSNT",
            "XSKT", "XSQB", "XSMB", "XSMT", "XSMN",
            "XSHCM", "XSHCM", "XSTD", "XSTD", "XSTD", "XSTD", "XSTD", "XSDNI", "XSDNG", "XSKH", //xoso bat dau = matinh

            "XSQNG", "XSDNO", "XSTTH", "XSQNM", "XSTD", "XSBDI", "XSCM", "XSST", "XSAG", // xoso = ma tinh
            "XSTN", "XSTRV", "XSVL", "XSBP", "XSHG", "XSLA", "XSKG", "XSLD", "XSTG", "XSNT", // // xoso = ma tinh

            "XSTD",
            "SXHCM", "SXQNG", "SXDNO", "SXBTH", "SXTTH", "SXQNM", "SXDNG", "SXBDI", "SXDLK",
            "SXTD", "SXQN", "SXBN", "SXHP", "SXND", "SXTB", "SXCM", "SXDT", "SXBL", "SXBTR",
            "SXVT", "SXCT", "SXDNI", "SXST", "SXAG", "SXTN", "SXBD", "SXTRV", "SXVL", "SXBP",
            "SXHG", "SXLA", "SXKG", "SXLD", "SXTG", "SXPY", "SXKH", "SXQT", "SXGL", "SXNT",
            "SXKT", "XSQB", "SXMB", "SXMT", "SXMN", "SXTD",
            "DECTIP1", "DECTIP2", "DECTIP", "DECXIEN1", "DECXIEN2", "DECXIEN", "DECTX", "DECTAI", "DECXIU",
            "DECTL1", "DECTL2", "DECTL", "DECKQ1", "DECKQ2", "DECKQ",
            "DECBXH", "DECLTD", "DECVPL", "DECDH", "DECCLB", "DECTT", "DECPD", "DECDD", "DECTH", "DECDT", "DECW", "DECCHO", "DECHOT", "DECKQ",
            "FBTIP1", "FBTIP2", "FBTIP", "FBXIEN1", "FBXIEN2", "FBXIEN", "FBTX", "FBTAI", "FBXIU",
            "FBTL1", "FBTL2", "FBTL", "FBKQ1", "FBKQ2", "FBKQ", "FBBXH", "FBLTD",
            "FBVPL", "FBDH", "FBCLB", "FBTT", "FBPD", "FBDD", "FBTH", "FBDT", "FBW", "FBCHO", "FBHOT", "FBKQ",
            "FATIP1", "FATIP2", "FATIP", "FAXIEN1", "FAXIEN2", "FAXIEN", "FATX", "FATAI", "FAXIU",
            "FATL1", "FATL2", "FATL", "FAKQ1", "FAKQ2", "FAKQ", "FABXH", "FALTD",
            "FAVPL", "FADH", "FACLB", "FATT", "FAPD", "FADD", "FATH", "FADT", "FAW", "FACHO", "FAHOT", "FAKQ",
            "BDTIP1", "BDTIP2", "BDTIP", "BDXIEN1", "BDXIEN2", "BDXIEN", "BDTX", "BDTAI", "BDXIU",
            "BDTL1", "BDTL2", "BDTL", "BDKQ1", "BDKQ2", "BDKQ", "BDBXH", "BDLTD",
            "BDVPL", "BDDH", "BDCLB", "BDTT", "BDPD", "BDDD", "BDTH", "BDDT", "BDW", "BDCHO", "BDHOT", "BDKQ",
            "BETTIP1", "BETTIP2", "BETTIP", "BETXIEN1", "BETXIEN2", "BETXIEN", "BETTX", "BETTAI", "BETXIU",
            "BETTL1", "BETTL2", "BETTL", "BETKQ1", "BETKQ2", "BETKQ",
            "BETBXH", "BETLTD", "BETVPL", "BETDH", "BETCLB", "BETTT", "BETPD", "BETDD", "BETTH", "BETDT", "BETW", "BETCHO", "BETHOT", "BETKQ",
            "TIP1", "TIP2", "TIP", "VIP1", "VIP2", "VIP", "SIEN1", "SIEN2", "SIEN", "TX", "TAI", "KEO", "XIU", "XIEN1", "XIEN2", "XIEN",
            "BTNS", "BTNICK", "BTHOMNAY", "BTNGAYMAI", "BTSDT", "BTAC", "BTNU", "BTNAM",
            "BTKIEU", "BTCHU", "BTVL", "BTTEN", "BTAL", "BTHOAHONG", "BTHOA", "BTNB", "BTNC", "BTGPRS", "BTKM",
            "BTBH", "BTCV", "BTDD", "BTDMG", "BTDM", "BTHG", "BTHT", "BTRN", "BTNT", "BTVX", "BT",
            "CT", "CD", "TET", "XD",
            "SC", "SOICAU", "LO", "L0", "DU", "KQ", "CAU", "MB", "TK", "GU", "GA", "AU", "HO", "KP", "XSMB",
            "TVMNHCM", "TVMTQNG", "TVMTDNO", "TVMNBTH", "TVMTTTH", "TVMTQNM", "TVMTDNG", "TVMTBDI", "TVMTDLK",
            "TVMBTD", "TVMBQN", "TVMBBN", "TVMBHP", "TVMBND", "TVMBTB", "TVMNCM", "TVMNDT", "TVMNBL", "TVMNBTR",
            "TVMNVT", "TVMNCT", "TVMNDNI", "TVMNST", "TVMNAG", "TVMNTN", "TVMNBD", "TVMNTRV", "TVMNVL", "TVMNBP",
            "TVMNHG", "TVMNLA", "TVMNKG", "TVMNLD", "TVMNTG", "TVMTPY", "TVMTKH", "TVMTQT", "TVMTGL", "TVMTNT",
            "TVMTKT", "TVMTQB", "TVMB", "TVMTDNG", "TVMNHCM", "TV",
            "TUMNHCM", "TUMTQNG", "TUMTDNO", "TUMNBTH", "TUMTTTH", "TUMTQNM", "TUMTDNG", "TUMTBDI", "TUMTDLK",
            "TUMBTD", "TUMBQN", "TUMBBN", "TUMBHP", "TUMBND", "TUMBTB", "TUMNCM", "TUMNDT", "TUMNBL", "TUMNBTR",
            "TUMNVT", "TUMNCT", "TUMNDNI", "TUMNST", "TUMNAG", "TUMNTN", "TUMNBD", "TUMNTRV", "TUMNVL", "TUMNBP",
            "TUMNHG", "TUMNLA", "TUMNKG", "TUMNLD", "TUMNTG", "TUMTPY", "TUMTKH", "TUMTQT", "TUMTGL", "TUMTNT",
            "TUMTKT", "TUMTQB", "TUMB", "TUMTDNG", "TUMNHCM", "TU",
            "VTMNHCM", "VTMTQNG", "VTMTDNO", "VTMNBTH", "VTMTTTH", "VTMTQNM", "VTMTDNG", "VTMTBDI", "VTMTDLK",
            "VTMBTD", "VTMBQN", "VTMBBN", "VTMBHP", "VTMBND", "VTMBTB", "VTMNCM", "VTMNDT", "VTMNBL", "VTMNBTR",
            "VTMNVT", "VTMNCT", "VTMNDNI", "VTMNST", "VTMNAG", "VTMNTN", "VTMNBD", "VTMNTRV", "VTMNVL", "VTMNBP",
            "VTMNHG", "VTMNLA", "VTMNKG", "VTMNLD", "VTMNTG", "VTMTPY", "VTMTKH", "VTMTQT", "VTMTGL", "VTMTNT",
            "VTMTKT", "VTMTQB", "VTMB", "VTMTDNG", "VTMNHCM", "VT",
            "SOIMNHCM", "SOIMTQNG", "SOIMTDNO", "SOIMNBTH", "SOIMTTTH", "SOIMTQNM", "SOIMTDNG", "SOIMTBDI", "SOIMTDLK",
            "SOIMBTD", "SOIMBQN", "SOIMBBN", "SOIMBHP", "SOIMBND", "SOIMBTB", "SOIMNCM", "SOIMNDT", "SOIMNBL", "SOIMNBTR",
            "SOIMNVT", "SOIMNCT", "SOIMNDNI", "SOIMNST", "SOIMNAG", "SOIMNTN", "SOIMNBD", "SOIMNTRV", "SOIMNVL", "SOIMNBP",
            "SOIMNHG", "SOIMNLA", "SOIMNKG", "SOIMNLD", "SOIMNTG", "SOIMTPY", "SOIMTKH", "SOIMTQT", "SOIMTGL", "SOIMTNT",
            "SOIMTKT", "SOIMB", "SOIMTDNG", "SOIMNHCM", "SOIMB",
            "S0IMNHCM", "S0IMTQNG", "S0IMTDNO", "S0IMNBTH", "S0IMTTTH", "S0IMTQNM", "S0IMTDNG", "S0IMTBDI", "S0IMTDLK",
            "S0IMBTD", "S0IMBQN", "S0IMBBN", "S0IMBHP", "S0IMBND", "S0IMBTB", "S0IMNCM", "S0IMNDT", "S0IMNBL", "S0IMNBTR",
            "S0IMNVT", "S0IMNCT", "S0IMNDNI", "S0IMNST", "S0IMNAG", "S0IMNTN", "S0IMNBD", "S0IMNTRV", "S0IMNVL", "S0IMNBP",
            "S0IMNHG", "S0IMNLA", "S0IMNKG", "S0IMNLD", "S0IMNTG", "S0IMTPY", "S0IMTKH", "S0IMTQT", "S0IMTGL", "S0IMTNT",
            "S0IMTKT", "S0IMB", "S0IMTDNG", "S0IMNHCM", "S0IMB",
            "SSMNHCM", "SSMTQNG", "SSMTDNO", "SSMNBTH", "SSMTTTH", "SSMTQNM", "SSMTDNG", "SSMTBDI", "SSMTDLK",
            "SSMBTD", "SSMBQN", "SSMBBN", "SSMBHP", "SSMBND", "SSMBTB", "SSMNCM", "SSMNDT", "SSMNBL", "SSMNBTR",
            "SSMNVT", "SSMNCT", "SSMNDNI", "SSMNST", "SSMNAG", "SSMNTN", "SSMNBD", "SSMNTRV", "SSMNVL", "SSMNBP",
            "SSMNHG", "SSMNLA", "SSMNKG", "SSMNLD", "SSMNTG", "SSMTPY", "SSMTKH", "SSMTQT", "SSMTGL", "SSMTNT",
            "SSMTKT", "SSMB", "SSMB",
            "DOMNHCM", "DOMTQNG", "DOMTDNO", "DOMNBTH", "DOMTTTH", "DOMTQNM", "DOMTDNG", "DOMTBDI", "DOMTDLK",
            "DOMBTD", "DOMBQN", "DOMBBN", "DOMBHP", "DOMBND", "DOMBTB", "DOMNCM", "DOMNDT", "DOMNBL", "DOMNBTR",
            "DOMNVT", "DOMNCT", "DOMNDNI", "DOMNST", "DOMNAG", "DOMNTN", "DOMNBD", "DOMNTRV", "DOMNVL", "DOMNBP",
            "DOMNHG", "DOMNLA", "DOMNKG", "DOMNLD", "DOMNTG", "DOMTPY", "DOMTKH", "DOMTQT", "DOMTGL", "DOMTNT",
            "DOMTKT", "DOMTQB", "DOMB", "DOMB",
            "D0MNHCM", "D0MTQNG", "D0MTDNO", "D0MNBTH", "D0MTTTH", "D0MTQNM", "D0MTDNG", "D0MTBDI", "D0MTDLK",
            "D0MBTD", "D0MBQN", "D0MBBN", "D0MBHP", "D0MBND", "D0MBTB", "D0MNCM", "D0MNDT", "D0MNBL", "D0MNBTR",
            "D0MNVT", "D0MNCT", "D0MNDNI", "D0MNST", "D0MNAG", "D0MNTN", "D0MNBD", "D0MNTRV", "D0MNVL", "D0MNBP",
            "D0MNHG", "D0MNLA", "D0MNKG", "D0MNLD", "D0MNTG", "D0MTPY", "D0MTKH", "D0MTQT", "D0MTGL", "D0MTNT",
            "D0MTKT", "DOMTQB", "D0MB", "D0MB", "DDMB",
            "PTSN", "PTBS", "PTHN", "PTBEP", "PTBLV", "PTHOP", "PTKY", "PTBM", "PTAL",
            "PTNT", "PTGT", "PTMAUKY", "PTMAU", "PTHT", "PTKH", "PTHX", "PTCV", "PTXD", "PTDT",
            "FTSN", "FTBS", "FTHN", "FTBEP", "FTBLV", "FTHOP", "FTKY", "FTBM", "FTAL",
            "FTNT", "FTGT", "FTMAUKY", "FTMAU", "FTHT", "FTKH", "FTHX", "FTCV", "FTDT",
            "SDTDT", "DTDT",
            "BL", "BAOLO",
            "VANGHN", "VANGHCM", "VANGSJC", "VANGPNJ", "VANGHN",
            "NGOAITEUSD", "NGOAITEEURO", "NGOAITEYEN", "NGOAITE",
            "NGOAITEUSD", "NGOAITEEURO", "NGOAITEYEN", "NGOAITE",
            "NGOAITE", "NGOAITE", "NGOAITE",
            "VANGHN", "VANGHCM", "VANGSJC", "VANGPNJ", "NGOAITE", "NGOAITE", "NGOAITE", "VANGHN",
            "VANGHN",
            "OFFQC", "HUY",
            "DMB", "Z", "SMB", "T"
        //"0","1","2","3","4","5","6","7","8","9"
        };

        String sCommandCode = "";
        String sTmp = "";
        String sOption = "";

        try {

            msg = msg.toUpperCase();
            msg = msg.trim();
            boolean flag = false;

            for (int i = 0; i < invalidCommandCode.length; i++) {
                for (int j = 0; j < invalidCommandCode[i].length; j++) {
                    if (msg.startsWith(invalidCommandCode[i][j])) {
                        sCommandCode = validCommandCode[i];
                        sTmp = invalidCommandCode[i][j];
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            sOption = msg.substring(sTmp.length());
            sOption = sOption.trim();
            //Khong co phan tu dang sau
            if (sOption.equals("-1")) {
                sOption = "";
            }
            str_Return = new String[2];
            str_Return[0] = sCommandCode;
            str_Return[1] = sOption;
        } catch (Exception ex) {
        }
        return str_Return;
    }

    public static String[] parseXSCode(String msg) {
        String[] str_return = null;
        String[][] msgInvCode = {
            {"XMN", "XM N"},
            {"XMB", "XM B"},
            {"XMT", "XM T"},
            {"XM", "X M"}
        };
        String[] msgValidCode = {"XSMN", "XSMB", "XSMT", "MB"};
        String sCommandCode = "";
        String sOption = "";
        String sTmp = "";
        try {
            msg = msg.toUpperCase();
            msg = msg.trim();
            boolean flag = false;
            for (int i = 0; i < msgInvCode.length; i++) {
                for (int j = 0; j < msgInvCode[i].length; j++) {
                    if (msg.startsWith(msgInvCode[i][j])) {
                        sCommandCode = msgValidCode[i];
                        sTmp = msgInvCode[i][j];
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            sOption = msg.substring(sTmp.length());
            sOption = sOption.trim();
            //Khong co phan tu dang sau
            if (sOption.equals("-1")) {
                sOption = "";
            }
            str_return = new String[2];
            str_return[0] = sCommandCode;
            str_return[1] = sOption;

        } catch (Exception ex) {
        }

        return str_return;

    }

    public static String[] parseCAUCode(String msg) {
        String[] str_return = null;
        String[][] msgInvCode = {
            {"CAUHCM", "CAU HCM", "CAUXSHCM", "CAU TP", "CAUTP", "CAU XSHCM", "CAU XS HCM", "CAUH CM", "CAU HO CHI MINH", "CAU THANH PHO"},
            {"CAUQNG", "CAU QNG", "CAUXSQNG", "CAU XSQNG", "CAUQ NG", "CAU QUANG NGAI", "CAU QUANGNGAI"},
            {"CAUDNO", "CAU DNO", "CAUXSDNO", "CAU XSDNO", "CAUD NO", "CAU D N O", "CAU DAC NONG"},
            {"CAUBTH", "CAU BTH", "CAUXSBTH", "CAU XSBTH", "CAUB TH", "CAU B T", "CAU BINH THUAN", "CAU BINHTHUAN"},
            {"CAUTTH", "CAU TTH", "CAUXSTTH", "CAU XSTTH", "CAUT TH", "CAU T T H", "CAU THUA THIEN HUE"},
            {"CAUQNM", "CAU QNN", "CAUXSQNM", "CAU XSQNN", "CAUQ NM", "CAU Q N M", "CAU QUANG NAM"},
            {"CAUDNG", "CAU DNG", "CAUXSDNG", "CAU XSDNG", "CAUD NG", "CAU D N G", "CAU DA NANG", "CAU DANANG"},
            {"CAUBDI", "CAU BDI", "CAUXSBDI", "CAU XSBDI", "CAUB DI", "CAU B DI", "CAU BINH DINH", "CAU BINHDINH"},
            {"CAUDLK", "CAU DLK", "CAUXSDLK", "CAU XSDLK", "CAUD LK", "CAU D L K", "CAU DAK LAK"},
            {"CAUTD", "CAU TD", "CAUXSTD", "CAU XSTD", "CAUT D", "CAU HN", "CAU HA NOI"},
            {"CAUQN", "CAU QN", "CAUXSQN", "CAU XSQN", "CAUQ N", "CAU Q N", "CAU QUANG NINH"},
            {"CAUBN", "CAU BN", "CAUXSBN", "CAU XSBN", "CAUB N", "CAU B N", "CAU BAC NINH", "CAU BACNINH"},
            {"CAUHP", "CAU HP", "CAUXSHP", "CAU XSHP", "CAUH P", "CAU H P", "CAU HAI PHONG", "CAU HAIPHONG"},
            {"CAUND", "CAU ND", "CAUXSND", "CAU XSND", "CAUN D", "CAU N D", "CAU NAMDINH", "CAU NAM DINH"},
            {"CAUTB", "CAU TB", "CAUXSTB", "CAU XSTB", "CAUT B", "CAU T B", "CAU THAIBINH", "CAU THAI BINH"},
            {"CAUCM", "CAU CM", "CAUXSCM", "CAU XSCM", "CAUC M", "CAU C M", "CAU CA MAU", "CAUCAMAU", "CAU CAMAU"},
            {"CAUDT", "CAU DT", "CAUXSDT", "CAU XSDT", "CAUD T", "CAU D T", "CAU CAUNG THAP", "CAU CAUNGTHAP"},
            {"CAUBL", "CAU BL", "CAUXSBL", "CAU XSBL", "CAUB L", "CAU B L", "CAU BAC LIEU", "CAU BACLIEU"},
            {"CAUBTR", "CAU BTR", "CAUXSBTR", "CAU XSBTR", "CAUB TR", "CAU B T R", "CAU BENTRE", "CAU BEN TRE"},
            {"CAUVT", "CAU VT", "CAUXSVT", "CAU XSVT", "CAUV T", "CAU VUNG TAU"},
            {"CAUCT", "CAU CT", "CAUXSCT", "CAU XSCT", "CAUC T", "CAU CAN THO", "CAU CANTHO"},
            {"CAUDNI", "CAU DNI", "CAUXSDNI", "CAU XSDNI", "CAUD NI", "CAUDN", "CAU DN", "CAU CAUNGNAI", "CAU CAUNGNAI"},
            {"CAUST", "CAU ST", "CAUXSST", "CAU XSST", "CAUS T", "CAU SOC TRANG", "CAU SOCTRANG"},
            {"CAUAG", "CAU AG", "CAUXSAG", "CAU XSAG", "CAUA G", "CAU ANGIANG", "CAU AN GIANG"},
            {"CAUTN", "CAU TN", "CAUXSTN", "CAU XSTN", "CAUT N", "CAU TAYNINH", "CAU TAY NINH"},
            {"CAUBD", "CAU BD", "CAUXSBD", "CAU XSBD", "CAUB D", "CAU BINH DUONG", "CAUBINHDUONG", "CAU BINHDUONG"},
            {"CAUTRV", "CAU TRV", "CAUXSTRV", "CAU XSTRV", "CAUT V", "CAUTV", "CAU TV", "CAU TRA VINH", "CAU TRAVINH"},
            {"CAUVL", "CAU VL", "CAUXSVL", "CAU XSVL", "CAUV L", "CAU VINHLONG", "CAU VINH LONG"},
            {"CAUBP", "CAU BP", "CAUXSBP", "CAU XSBP", "CAUB P", "CAU BINH PHUOC", "CAU BINHPHUOC"},
            {"CAUHG", "CAU HG", "CAUXSHG", "CAU XSHG", "CAUH G", "CAU HAU GIANG", "CAUHAUGIANG"},
            {"CAULA", "CAU LA", "CAUXSLA", "CAU XSLA", "CAUL A", "CAU LONG AN", "CAU LONGAN"},
            {"CAUKG", "CAU KG", "CAUXSKG", "CAU XSKG", "CAUK G", "CAU KIENGIANG", "CAU KIEN GIANG"},
            {"CAULD", "CAU LD", "CAUXSLD", "CAU XSLD", "CAUL D", "CAU LAM CAUNG", "CAU LAMCAUNG", "CAUDL", "CAU DL", "CAU DA LAT", "CAUDALAT"},
            {"CAUTG", "CAU TG", "CAUXSTG", "CAU XSTG", "CAU XS TG", "CAUSXTG", "CAUT G", "CAU TIENGIANG", "CAU TIEN GIANG"},
            {"CAUPY", "CAU PY", "CAUXSPY", "CAU XSPY", "CAUP Y", "CAU PHU YEN", "CAU PHUYEN"},
            {"CAUKH", "CAU KH", "CAUXSKH", "CAU XSKH", "CAUK H", "CAU KHANH HOA", "CAU KHANHHOA"},
            {"CAUQT", "CAU QT", "CAUXSQT", "CAU XSQT", "CAUQ T", "CAU QUANG TRI"},
            {"CAUGL", "CAU GL", "CAUXSGL", "CAU XSGL", "CAUG L", "CAU GIA LAI", "CAU GIALAI"},
            {"CAUNT", "CAU NT", "CAUXSNT", "CAU XSNT", "CAUN T", "CAU NINH THUAN"},
            {"CAUKT", "CAU KT", "CAUXSKT", "CAU XSKT", "CAUK T", "CAU KON TUM"},
            {"CAUQB", "CAU QB", "CAU Q B", "CAU QUANG BINH", "CAU QUANGBINH"},
            {"CAUMB", "CAU MB", "CAU M B", "CAU MIEN BAC"},
            {"CAU", "CA"},
            {"LOCHCM", "LOC HCM", "LOCXSHCM", "LOC TP", "LOCTP", "LOC XSHCM", "LOC XS HCM", "LOCH CM", "LOC HO CHI MINH", "LOC THANH PHO"},
            {"LOCQNG", "LOC QNG", "LOCXSQNG", "LOC XSQNG", "LOCQ NG", "LOC QUANG NGAI", "LOC QUANGNGAI"},
            {"LOCDNO", "LOC DNO", "LOCXSDNO", "LOC XSDNO", "LOCD NO", "LOC D N O", "LOC DAC NONG"},
            {"LOCBTH", "LOC BTH", "LOCXSBTH", "LOC XSBTH", "LOCB TH", "LOC B T", "LOC BINH THUAN", "LOC BINHTHUAN"},
            {"LOCTTH", "LOC TTH", "LOCXSTTH", "LOC XSTTH", "LOCT TH", "LOC T T H", "LOC THUA THIEN HUE"},
            {"LOCQNM", "LOC QNN", "LOCXSQNM", "LOC XSQNN", "LOCQ NM", "LOC Q N M", "LOC QUANG NAM"},
            {"LOCDNG", "LOC DNG", "LOCXSDNG", "LOC XSDNG", "LOCD NG", "LOC D N G", "LOC DA NANG", "LOC DANANG"},
            {"LOCBDI", "LOC BDI", "LOCXSBDI", "LOC XSBDI", "LOCB DI", "LOC B DI", "LOC BINH DINH", "LOC BINHDINH"},
            {"LOCDLK", "LOC DLK", "LOCXSDLK", "LOC XSDLK", "LOCD LK", "LOC D L K", "LOC DAK LAK"},
            {"LOCTD", "LOC TD", "LOCXSTD", "LOC XSTD", "LOCT D", "LOC HN", "LOC HA NOI"},
            {"LOCQN", "LOC QN", "LOCXSQN", "LOC XSQN", "LOCQ N", "LOC Q N", "LOC QUANG NINH"},
            {"LOCBN", "LOC BN", "LOCXSBN", "LOC XSBN", "LOCB N", "LOC B N", "LOC BAC NINH", "LOC BACNINH"},
            {"LOCHP", "LOC HP", "LOCXSHP", "LOC XSHP", "LOCH P", "LOC H P", "LOC HAI PHONG", "LOC HAIPHONG"},
            {"LOCND", "LOC ND", "LOCXSND", "LOC XSND", "LOCN D", "LOC N D", "LOC NAMDINH", "LOC NAM DINH"},
            {"LOCTB", "LOC TB", "LOCXSTB", "LOC XSTB", "LOCT B", "LOC T B", "LOC THAIBINH", "LOC THAI BINH"},
            {"LOCCM", "LOC CM", "LOCXSCM", "LOC XSCM", "LOCC M", "LOC C M", "LOC CA MAU", "LOCCAMAU", "LOC CAMAU"},
            {"LOCDT", "LOC DT", "LOCXSDT", "LOC XSDT", "LOCD T", "LOC D T", "LOC LOCNG THAP", "LOC LOCNGTHAP"},
            {"LOCBL", "LOC BL", "LOCXSBL", "LOC XSBL", "LOCB L", "LOC B L", "LOC BAC LIEU", "LOC BACLIEU"},
            {"LOCBTR", "LOC BTR", "LOCXSBTR", "LOC XSBTR", "LOCB TR", "LOC B T R", "LOC BENTRE", "LOC BEN TRE"},
            {"LOCVT", "LOC VT", "LOCXSVT", "LOC XSVT", "LOCV T", "LOC VUNG TAU"},
            {"LOCCT", "LOC CT", "LOCXSCT", "LOC XSCT", "LOCC T", "LOC CAN THO", "LOC CANTHO"},
            {"LOCDNI", "LOC DNI", "LOCXSDNI", "LOC XSDNI", "LOCD NI", "LOCDN", "LOC DN", "LOC LOCNGNAI", "LOC LOCNGNAI"},
            {"LOCST", "LOC ST", "LOCXSST", "LOC XSST", "LOCS T", "LOC SOC TRANG", "LOC SOCTRANG"},
            {"LOCAG", "LOC AG", "LOCXSAG", "LOC XSAG", "LOCA G", "LOC ANGIANG", "LOC AN GIANG"},
            {"LOCTN", "LOC TN", "LOCXSTN", "LOC XSTN", "LOCT N", "LOC TAYNINH", "LOC TAY NINH"},
            {"LOCBD", "LOC BD", "LOCXSBD", "LOC XSBD", "LOCB D", "LOC BINH DUONG", "LOCBINHDUONG", "LOC BINHDUONG"},
            {"LOCTRV", "LOC TRV", "LOCXSTRV", "LOC XSTRV", "LOCT V", "LOCTV", "LOC TV", "LOC TRA VINH", "LOC TRAVINH"},
            {"LOCVL", "LOC VL", "LOCXSVL", "LOC XSVL", "LOCV L", "LOC VINHLONG", "LOC VINH LONG"},
            {"LOCBP", "LOC BP", "LOCXSBP", "LOC XSBP", "LOCB P", "LOC BINH PHUOC", "LOC BINHPHUOC"},
            {"LOCHG", "LOC HG", "LOCXSHG", "LOC XSHG", "LOCH G", "LOC HAU GIANG", "LOCHAUGIANG"},
            {"LOCLA", "LOC LA", "LOCXSLA", "LOC XSLA", "LOCL A", "LOC LONG AN", "LOC LONGAN"},
            {"LOCKG", "LOC KG", "LOCXSKG", "LOC XSKG", "LOCK G", "LOC KIENGIANG", "LOC KIEN GIANG"},
            {"LOCLD", "LOC LD", "LOCXSLD", "LOC XSLD", "LOCL D", "LOC LAM LOCNG", "LOC LAMLOCNG", "LOCDL", "LOC DL", "LOC DA LAT", "LOCDALAT"},
            {"LOCTG", "LOC TG", "LOCXSTG", "LOC XSTG", "LOC XS TG", "LOCSXTG", "LOCT G", "LOC TIENGIANG", "LOC TIEN GIANG"},
            {"LOCPY", "LOC PY", "LOCXSPY", "LOC XSPY", "LOCP Y", "LOC PHU YEN", "LOC PHUYEN"},
            {"LOCKH", "LOC KH", "LOCXSKH", "LOC XSKH", "LOCK H", "LOC KHANH HOA", "LOC KHANHHOA"},
            {"LOCQT", "LOC QT", "LOCXSQT", "LOC XSQT", "LOCQ T", "LOC QUANG TRI"},
            {"LOCGL", "LOC GL", "LOCXSGL", "LOC XSGL", "LOCG L", "LOC GIA LAI", "LOC GIALAI"},
            {"LOCNT", "LOC NT", "LOCXSNT", "LOC XSNT", "LOCN T", "LOC NINH THUAN"},
            {"LOCKT", "LOC KT", "LOCXSKT", "LOC XSKT", "LOCK T", "LOC KON TUM"},
            {"LOCQB", "LOC QB", "LOC Q B", "LOC QUANG BINH", "LOC QUANGBINH"},
            {"LOCMB", "LOC MB", "LOC M B", "LOC MIEN BAC"},
            {"LOC"},
            {"L0CHCM", "L0C HCM", "L0CXSHCM", "L0C TP", "L0CTP", "L0C XSHCM", "L0C XS HCM", "L0CH CM", "L0C HO CHI MINH", "L0C THANH PHO"},
            {"L0CQNG", "L0C QNG", "L0CXSQNG", "L0C XSQNG", "L0CQ NG", "L0C QUANG NGAI", "L0C QUANGNGAI"},
            {"L0CDNO", "L0C DNO", "L0CXSDNO", "L0C XSDNO", "L0CD NO", "L0C D N O", "L0C DAC NONG"},
            {"L0CBTH", "L0C BTH", "L0CXSBTH", "L0C XSBTH", "L0CB TH", "L0C B T", "L0C BINH THUAN", "L0C BINHTHUAN"},
            {"L0CTTH", "L0C TTH", "L0CXSTTH", "L0C XSTTH", "L0CT TH", "L0C T T H", "L0C THUA THIEN HUE"},
            {"L0CQNM", "L0C QNN", "L0CXSQNM", "L0C XSQNN", "L0CQ NM", "L0C Q N M", "L0C QUANG NAM"},
            {"L0CDNG", "L0C DNG", "L0CXSDNG", "L0C XSDNG", "L0CD NG", "L0C D N G", "L0C DA NANG", "L0C DANANG"},
            {"L0CBDI", "L0C BDI", "L0CXSBDI", "L0C XSBDI", "L0CB DI", "L0C B DI", "L0C BINH DINH", "L0C BINHDINH"},
            {"L0CDLK", "L0C DLK", "L0CXSDLK", "L0C XSDLK", "L0CD LK", "L0C D L K", "L0C DAK LAK"},
            {"L0CTD", "L0C TD", "L0CXSTD", "L0C XSTD", "L0CT D", "L0C HN", "L0C HA NOI"},
            {"L0CQN", "L0C QN", "L0CXSQN", "L0C XSQN", "L0CQ N", "L0C Q N", "L0C QUANG NINH"},
            {"L0CBN", "L0C BN", "L0CXSBN", "L0C XSBN", "L0CB N", "L0C B N", "L0C BAC NINH", "L0C BACNINH"},
            {"L0CHP", "L0C HP", "L0CXSHP", "L0C XSHP", "L0CH P", "L0C H P", "L0C HAI PHONG", "L0C HAIPHONG"},
            {"L0CND", "L0C ND", "L0CXSND", "L0C XSND", "L0CN D", "L0C N D", "L0C NAMDINH", "L0C NAM DINH"},
            {"L0CTB", "L0C TB", "L0CXSTB", "L0C XSTB", "L0CT B", "L0C T B", "L0C THAIBINH", "L0C THAI BINH"},
            {"L0CCM", "L0C CM", "L0CXSCM", "L0C XSCM", "L0CC M", "L0C C M", "L0C CA MAU", "L0CCAMAU", "L0C CAMAU"},
            {"L0CDT", "L0C DT", "L0CXSDT", "L0C XSDT", "L0CD T", "L0C D T", "L0C L0CNG THAP", "L0C L0CNGTHAP"},
            {"L0CBL", "L0C BL", "L0CXSBL", "L0C XSBL", "L0CB L", "L0C B L", "L0C BAC LIEU", "L0C BACLIEU"},
            {"L0CBTR", "L0C BTR", "L0CXSBTR", "L0C XSBTR", "L0CB TR", "L0C B T R", "L0C BENTRE", "L0C BEN TRE"},
            {"L0CVT", "L0C VT", "L0CXSVT", "L0C XSVT", "L0CV T", "L0C VUNG TAU"},
            {"L0CCT", "L0C CT", "L0CXSCT", "L0C XSCT", "L0CC T", "L0C CAN THO", "L0C CANTHO"},
            {"L0CDNI", "L0C DNI", "L0CXSDNI", "L0C XSDNI", "L0CD NI", "L0CDN", "L0C DN", "L0C L0CNGNAI", "L0C L0CNGNAI"},
            {"L0CST", "L0C ST", "L0CXSST", "L0C XSST", "L0CS T", "L0C SOC TRANG", "L0C SOCTRANG"},
            {"L0CAG", "L0C AG", "L0CXSAG", "L0C XSAG", "L0CA G", "L0C ANGIANG", "L0C AN GIANG"},
            {"L0CTN", "L0C TN", "L0CXSTN", "L0C XSTN", "L0CT N", "L0C TAYNINH", "L0C TAY NINH"},
            {"L0CBD", "L0C BD", "L0CXSBD", "L0C XSBD", "L0CB D", "L0C BINH DUONG", "L0CBINHDUONG", "L0C BINHDUONG"},
            {"L0CTRV", "L0C TRV", "L0CXSTRV", "L0C XSTRV", "L0CT V", "L0CTV", "L0C TV", "L0C TRA VINH", "L0C TRAVINH"},
            {"L0CVL", "L0C VL", "L0CXSVL", "L0C XSVL", "L0CV L", "L0C VINHLONG", "L0C VINH LONG"},
            {"L0CBP", "L0C BP", "L0CXSBP", "L0C XSBP", "L0CB P", "L0C BINH PHUOC", "L0C BINHPHUOC"},
            {"L0CHG", "L0C HG", "L0CXSHG", "L0C XSHG", "L0CH G", "L0C HAU GIANG", "L0CHAUGIANG"},
            {"L0CLA", "L0C LA", "L0CXSLA", "L0C XSLA", "L0CL A", "L0C LONG AN", "L0C LONGAN"},
            {"L0CKG", "L0C KG", "L0CXSKG", "L0C XSKG", "L0CK G", "L0C KIENGIANG", "L0C KIEN GIANG"},
            {"L0CLD", "L0C LD", "L0CXSLD", "L0C XSLD", "L0CL D", "L0C LAM L0CNG", "L0C LAML0CNG", "L0CDL", "L0C DL", "L0C DA LAT", "L0CDALAT"},
            {"L0CTG", "L0C TG", "L0CXSTG", "L0C XSTG", "L0C XS TG", "L0CSXTG", "L0CT G", "L0C TIENGIANG", "L0C TIEN GIANG"},
            {"L0CPY", "L0C PY", "L0CXSPY", "L0C XSPY", "L0CP Y", "L0C PHU YEN", "L0C PHUYEN"},
            {"L0CKH", "L0C KH", "L0CXSKH", "L0C XSKH", "L0CK H", "L0C KHANH HOA", "L0C KHANHHOA"},
            {"L0CQT", "L0C QT", "L0CXSQT", "L0C XSQT", "L0CQ T", "L0C QUANG TRI"},
            {"L0CGL", "L0C GL", "L0CXSGL", "L0C XSGL", "L0CG L", "L0C GIA LAI", "L0C GIALAI"},
            {"L0CNT", "L0C NT", "L0CXSNT", "L0C XSNT", "L0CN T", "L0C NINH THUAN"},
            {"L0CKT", "L0C KT", "L0CXSKT", "L0C XSKT", "L0CK T", "L0C KON TUM"},
            {"L0CQB", "L0C QB", "L0C Q B", "L0C QUANG BINH", "L0C QUANGBINH"},
            {"L0CMB", "L0C MB", "L0C M B", "L0C MIEN BAC"},
            {"L0C"},
            {"LOHCM", "LO HCM", "LOXSHCM", "LO XSHCM", "LO XS HCM", "LOTP", "LO TP", "LOH CM", "LO HO CHI MINH", "LO THANH PHO"},
            {"LOQNG", "LO QNG", "LOXSQNG", "LO XSQNG", "LOQ NG", "LO QUANG NGAI", "LO QUANGNGAI"},
            {"LODNO", "LO DNO", "LOXSDNO", "LO XSDNO", "LOD NO", "LO D N O", "LO DAC NONG"},
            {"LOBTH", "LO BTH", "LOXSBTH", "LO XSBTH", "LOB TH", "LO B T", "LO BINH THUAN", "LO BINHTHUAN"},
            {"LOTTH", "LO TTH", "LOXSTTH", "LO XSTTH", "LOT TH", "LO T T H", "LO THUA THIEN HUE"},
            {"LOQNM", "LO QNN", "LOXSQNM", "LO XSQNN", "LOQ NM", "LO Q N M", "LO QUANG NAM"},
            {"LODNG", "LO DNG", "LOXSDNG", "LO XSDNG", "LOD NG", "LO D N G", "LO DA NANG", "LO DANANG"},
            {"LOBDI", "LO BDI", "LOXSBDI", "LO XSBDI", "LOB DI", "LO B DI", "LO BINH DINH", "LO BINHDINH"},
            {"LODLK", "LO DLK", "LOXSDLK", "LO XSDLK", "LOD LK", "LO D L K", "LO DAK LAK"},
            {"LOTD", "LO TD", "LOXSTD", "LO XSTD", "LOT D", "LO HN", "LO HA NOI"},
            {"LOQN", "LO QN", "LOXSQN", "LO XSQN", "LOQ N", "LO Q N", "LO QUANG NINH"},
            {"LOBN", "LO BN", "LOXSBN", "LO XSBN", "LOB N", "LO B N", "LO BAC NINH", "LO BACNINH"},
            {"LOHP", "LO HP", "LOXSHP", "LO XSHP", "LOH P", "LO H P", "LO HAI PHONG", "LO HAIPHONG"},
            {"LOND", "LO ND", "LOXSND", "LO XSND", "LON D", "LO N D", "LO NAMDINH", "LO NAM DINH"},
            {"LOTB", "LO TB", "LOXSTB", "LO XSTB", "LOT B", "LO T B", "LO THAIBINH", "LO THAI BINH"},
            {"LOCM", "LO CM", "LOXSCM", "LO XSCM", "LOC M", "LO C M", "LO CA MAU", "LOCAMAU", "LO CAMAU"},
            {"LODT", "LO DT", "LOXSDT", "LO XSDT", "LOD T", "LO D T", "LO LONG THAP", "LO LONGTHAP"},
            {"LOBL", "LO BL", "LOXSBL", "LO XSBL", "LOB L", "LO B L", "LO BAC LIEU", "LO BACLIEU"},
            {"LOBTR", "LO BTR", "LOXSBTR", "LO XSBTR", "LOB TR", "LO B T R", "LO BENTRE", "LO BEN TRE"},
            {"LOVT", "LO VT", "LOXSVT", "LO XSVT", "LOV T", "LO VUNG TAU"},
            {"LOCT", "LO CT", "LOXSCT", "LO XSCT", "LOC T", "LO CAN THO", "LO CANTHO"},
            {"LODNI", "LO DNI", "LOXSDNI", "LO XSDNI", "LOD NI", "LODN", "LO DN", "LO LONGNAI", "LO LONGNAI"},
            {"LOST", "LO ST", "LOXSST", "LO XSST", "LOS T", "LO SOC TRANG", "LO SOCTRANG"},
            {"LOAG", "LO AG", "LOXSAG", "LO XSAG", "LOA G", "LO ANGIANG", "LO AN GIANG"},
            {"LOTN", "LO TN", "LOXSTN", "LO XSTN", "LOT N", "LO TAYNINH", "LO TAY NINH"},
            {"LOBD", "LO BD", "LOXSBD", "LO XSBD", "LOB D", "LO BINH DUONG", "LOBINHDUONG", "LO BINHDUONG"},
            {"LOTRV", "LO TRV", "LOXSTRV", "LO XSTRV", "LOT V", "LOTV", "LO TV", "LO TRA VINH", "LO TRAVINH"},
            {"LOVL", "LO VL", "LOXSVL", "LO XSVL", "LOV L", "LO VINHLONG", "LO VINH LONG"},
            {"LOBP", "LO BP", "LOXSBP", "LO XSBP", "LOB P", "LO BINH PHUOC", "LO BINHPHUOC"},
            {"LOHG", "LO HG", "LOXSHG", "LO XSHG", "LOH G", "LO HAU GIANG", "LOHAUGIANG"},
            {"LOLA", "LO LA", "LOXSLA", "LO XSLA", "LOL A", "LO LONG AN", "LO LONGAN"},
            {"LOKG", "LO KG", "LOXSKG", "LO XSKG", "LOK G", "LO KIENGIANG", "LO KIEN GIANG"},
            {"LOLD", "LO LD", "LOXSLD", "LO XSLD", "LOL D", "LO LAM LONG", "LO LAMLONG", "LODL", "LO DL", "LO DA LAT", "LODALAT"},
            {"LOTG", "LO TG", "LOXSTG", "LO XSTG", "LO XS TG", "LOSXTG", "LOT G", "LO TIENGIANG", "LO TIEN GIANG"},
            {"LOPY", "LO PY", "LOXSPY", "LO XSPY", "LOP Y", "LO PHU YEN", "LO PHUYEN"},
            {"LOKH", "LO KH", "LOXSKH", "LO XSKH", "LOK H", "LO KHANH HOA", "LO KHANHHOA"},
            {"LOQT", "LO QT", "LOXSQT", "LO XSQT", "LOQ T", "LO QUANG TRI"},
            {"LOGL", "LO GL", "LOXSGL", "LO XSGL", "LOG L", "LO GIA LAI", "LO GIALAI"},
            {"LONT", "LO NT", "LOXSNT", "LO XSNT", "LON T", "LO NINH THUAN"},
            {"LOKT", "LO KT", "LOXSKT", "LO XSKT", "LOK T", "LO KON TUM"},
            {"LOMB", "LO MB", "LO M B", "LO MIEN BAC"},
            {"LO"},
            {"L0HCM", "L0 HCM", "L0XSHCM", "L0 TP", "L0TP", "L0 XSHCM", "L0 XS HCM", "L0H CM", "L0 HO CHI MINH", "L0 THANH PHO"},
            {"L0QNG", "L0 QNG", "L0XSQNG", "L0 XSQNG", "L0Q NG", "L0 QUANG NGAI", "L0 QUANGNGAI"},
            {"L0DNO", "L0 DNO", "L0XSDNO", "L0 XSDNO", "L0D NO", "L0 D N O", "L0 DAC NONG"},
            {"L0BTH", "L0 BTH", "L0XSBTH", "L0 XSBTH", "L0B TH", "L0 B T", "L0 BINH THUAN", "L0 BINHTHUAN"},
            {"L0TTH", "L0 TTH", "L0XSTTH", "L0 XSTTH", "L0T TH", "L0 T T H", "L0 THUA THIEN HUE"},
            {"L0QNM", "L0 QNN", "L0XSQNM", "L0 XSQNN", "L0Q NM", "L0 Q N M", "L0 QUANG NAM"},
            {"L0DNG", "L0 DNG", "L0XSDNG", "L0 XSDNG", "L0D NG", "L0 D N G", "L0 DA NANG", "L0 DANANG"},
            {"L0BDI", "L0 BDI", "L0XSBDI", "L0 XSBDI", "L0B DI", "L0 B DI", "L0 BINH DINH", "L0 BINHDINH"},
            {"L0DLK", "L0 DLK", "L0XSDLK", "L0 XSDLK", "L0D LK", "L0 D L K", "L0 DAK LAK"},
            {"L0TD", "L0 TD", "L0XSTD", "L0 XSTD", "L0T D", "L0 HN", "L0 HA NOI"},
            {"L0QN", "L0 QN", "L0XSQN", "L0 XSQN", "L0Q N", "L0 Q N", "L0 QUANG NINH"},
            {"L0BN", "L0 BN", "L0XSBN", "L0 XSBN", "L0B N", "L0 B N", "L0 BAC NINH", "L0 BACNINH"},
            {"L0HP", "L0 HP", "L0XSHP", "L0 XSHP", "L0H P", "L0 H P", "L0 HAI PHONG", "L0 HAIPHONG"},
            {"L0ND", "L0 ND", "L0XSND", "L0 XSND", "L0N D", "L0 N D", "L0 NAMDINH", "L0 NAM DINH"},
            {"L0TB", "L0 TB", "L0XSTB", "L0 XSTB", "L0T B", "L0 T B", "L0 THAIBINH", "L0 THAI BINH"},
            {"L0CM", "L0 CM", "L0XSCM", "L0 XSCM", "L0C M", "L0 C M", "L0 CA MAU", "L0CAMAU", "L0 CAMAU"},
            {"L0DT", "L0 DT", "L0XSDT", "L0 XSDT", "L0D T", "L0 D T", "L0 L0NG THAP", "L0 L0NGTHAP"},
            {"L0BL", "L0 BL", "L0XSBL", "L0 XSBL", "L0B L", "L0 B L", "L0 BAC LIEU", "L0 BACLIEU"},
            {"L0BTR", "L0 BTR", "L0XSBTR", "L0 XSBTR", "L0B TR", "L0 B T R", "L0 BENTRE", "L0 BEN TRE"},
            {"L0VT", "L0 VT", "L0XSVT", "L0 XSVT", "L0V T", "L0 VUNG TAU"},
            {"L0CT", "L0 CT", "L0XSCT", "L0 XSCT", "L0C T", "L0 CAN THO", "L0 CANTHO"},
            {"L0DNI", "L0 DNI", "L0XSDNI", "L0 XSDNI", "L0D NI", "L0DN", "L0 DN", "L0 L0NGNAI", "L0 L0NGNAI"},
            {"L0ST", "L0 ST", "L0XSST", "L0 XSST", "L0S T", "L0 SOC TRANG", "L0 SOCTRANG"},
            {"L0AG", "L0 AG", "L0XSAG", "L0 XSAG", "L0A G", "L0 ANGIANG", "L0 AN GIANG"},
            {"L0TN", "L0 TN", "L0XSTN", "L0 XSTN", "L0T N", "L0 TAYNINH", "L0 TAY NINH"},
            {"L0BD", "L0 BD", "L0XSBD", "L0 XSBD", "L0B D", "L0 BINH DUONG", "L0BINHDUONG", "L0 BINHDUONG"},
            {"L0TRV", "L0 TRV", "L0XSTRV", "L0 XSTRV", "L0T V", "L0TV", "L0 TV", "L0 TRA VINH", "L0 TRAVINH"},
            {"L0VL", "L0 VL", "L0XSVL", "L0 XSVL", "L0V L", "L0 VINHLONG", "L0 VINH LONG"},
            {"L0BP", "L0 BP", "L0XSBP", "L0 XSBP", "L0B P", "L0 BINH PHUOC", "L0 BINHPHUOC"},
            {"L0HG", "L0 HG", "L0XSHG", "L0 XSHG", "L0H G", "L0 HAU GIANG", "L0HAUGIANG"},
            {"L0LA", "L0 LA", "L0XSLA", "L0 XSLA", "L0L A", "L0 LONG AN", "L0 LONGAN"},
            {"L0KG", "L0 KG", "L0XSKG", "L0 XSKG", "L0K G", "L0 KIENGIANG", "L0 KIEN GIANG"},
            {"L0LD", "L0 LD", "L0XSLD", "L0 XSLD", "L0L D", "L0 LAM L0NG", "L0 LAML0NG", "L0DL", "L0 DL", "L0 DA LAT", "L0DALAT"},
            {"L0TG", "L0 TG", "L0XSTG", "L0 XSTG", "L0 XS TG", "L0SXTG", "L0T G", "L0 TIENGIANG", "L0 TIEN GIANG"},
            {"L0PY", "L0 PY", "L0XSPY", "L0 XSPY", "L0P Y", "L0 PHU YEN", "L0 PHUYEN"},
            {"L0KH", "L0 KH", "L0XSKH", "L0 XSKH", "L0K H", "L0 KHANH HOA", "L0 KHANHHOA"},
            {"L0QT", "L0 QT", "L0XSQT", "L0 XSQT", "L0Q T", "L0 QUANG TRI"},
            {"L0GL", "L0 GL", "L0XSGL", "L0 XSGL", "L0G L", "L0 GIA LAI", "L0 GIALAI"},
            {"L0NT", "L0 NT", "L0XSNT", "L0 XSNT", "L0N T", "L0 NINH THUAN"},
            {"L0KT", "L0 KT", "L0XSKT", "L0 XSKT", "L0K T", "L0 KON TUM"},
            {"L0MB", "L0 MB", "L0 M B", "L0 MIEN BAC"},
            {"L0"},
            {"SOHCM", "SO HCM", "SOXSHCM", "SO TP", "SOTP", "SO XSHCM", "SO XS HCM", "SOH CM", "SO HO CHI MINH", "SO THANH PHO"},
            {"SOQNG", "SO QNG", "SOXSQNG", "SO XSQNG", "SOQ NG", "SO QUANG NGAI", "SO QUANGNGAI"},
            {"SODNO", "SO DNO", "SOXSDNO", "SO XSDNO", "SOD NO", "SO D N O", "SO DAC NONG"},
            {"SOBTH", "SO BTH", "SOXSBTH", "SO XSBTH", "SOB TH", "SO B T", "SO BINH THUAN", "SO BINHTHUAN"},
            {"SOTTH", "SO TTH", "SOXSTTH", "SO XSTTH", "SOT TH", "SO T T H", "SO THUA THIEN HUE"},
            {"SOQNM", "SO QNN", "SOXSQNM", "SO XSQNN", "SOQ NM", "SO Q N M", "SO QUANG NAM"},
            {"SODNG", "SO DNG", "SOXSDNG", "SO XSDNG", "SOD NG", "SO D N G", "SO DA NANG", "SO DANANG"},
            {"SOBDI", "SO BDI", "SOXSBDI", "SO XSBDI", "SOB DI", "SO B DI", "SO BINH DINH", "SO BINHDINH"},
            {"SODLK", "SO DLK", "SOXSDLK", "SO XSDLK", "SOD LK", "SO D L K", "SO DAK LAK"},
            {"SOTD", "SO TD", "SOXSTD", "SO XSTD", "SOT D", "SO HN", "SO HA NOI"},
            {"SOQN", "SO QN", "SOXSQN", "SO XSQN", "SOQ N", "SO Q N", "SO QUANG NINH"},
            {"SOBN", "SO BN", "SOXSBN", "SO XSBN", "SOB N", "SO B N", "SO BAC NINH", "SO BACNINH"},
            {"SOHP", "SO HP", "SOXSHP", "SO XSHP", "SOH P", "SO H P", "SO HAI PHONG", "SO HAIPHONG"},
            {"SOND", "SO ND", "SOXSND", "SO XSND", "SON D", "SO N D", "SO NAMDINH", "SO NAM DINH"},
            {"SOTB", "SO TB", "SOXSTB", "SO XSTB", "SOT B", "SO T B", "SO THAIBINH", "SO THAI BINH"},
            {"SOCM", "SO CM", "SOXSCM", "SO XSCM", "SOC M", "SO C M", "SO CA MAU", "SOCAMAU", "SO CAMAU"},
            {"SODT", "SO DT", "SOXSDT", "SO XSDT", "SOD T", "SO D T", "SO SONG THAP", "SO SONGTHAP"},
            {"SOBL", "SO BL", "SOXSBL", "SO XSBL", "SOB L", "SO B L", "SO BAC LIEU", "SO BACLIEU"},
            {"SOBTR", "SO BTR", "SOXSBTR", "SO XSBTR", "SOB TR", "SO B T R", "SO BENTRE", "SO BEN TRE"},
            {"SOVT", "SO VT", "SOXSVT", "SO XSVT", "SOV T", "SO VUNG TAU"},
            {"SOCT", "SO CT", "SOXSCT", "SO XSCT", "SOC T", "SO CAN THO", "SO CANTHO"},
            {"SODNI", "SO DNI", "SOXSDNI", "SO XSDNI", "SOD NI", "SODN", "SO DN", "SO SONGNAI", "SO SONGNAI"},
            {"SOST", "SO ST", "SOXSST", "SO XSST", "SOS T", "SO SOC TRANG", "SO SOCTRANG"},
            {"SOAG", "SO AG", "SOXSAG", "SO XSAG", "SOA G", "SO ANGIANG", "SO AN GIANG"},
            {"SOTN", "SO TN", "SOXSTN", "SO XSTN", "SOT N", "SO TAYNINH", "SO TAY NINH"},
            {"SOBD", "SO BD", "SOXSBD", "SO XSBD", "SOB D", "SO BINH DUONG", "SOBINHDUONG", "SO BINHDUONG"},
            {"SOTRV", "SO TRV", "SOXSTRV", "SO XSTRV", "SOT V", "SOTV", "SO TV", "SO TRA VINH", "SO TRAVINH"},
            {"SOVL", "SO VL", "SOXSVL", "SO XSVL", "SOV L", "SO VINHLONG", "SO VINH LONG"},
            {"SOBP", "SO BP", "SOXSBP", "SO XSBP", "SOB P", "SO BINH PHUOC", "SO BINHPHUOC"},
            {"SOHG", "SO HG", "SOXSHG", "SO XSHG", "SOH G", "SO HAU GIANG", "SOHAUGIANG"},
            {"SOLA", "SO LA", "SOXSLA", "SO XSLA", "SOL A", "SO LONG AN", "SO LONGAN"},
            {"SOKG", "SO KG", "SOXSKG", "SO XSKG", "SOK G", "SO KIENGIANG", "SO KIEN GIANG"},
            {"SOLD", "SO LD", "SOXSLD", "SO XSLD", "SOL D", "SO LAM SONG", "SO LAMSONG", "SODL", "SO DL", "SO DA LAT", "SODALAT"},
            {"SOTG", "SO TG", "SOXSTG", "SO XSTG", "SO XS TG", "SOSXTG", "SOT G", "SO TIENGIANG", "SO TIEN GIANG"},
            {"SOPY", "SO PY", "SOXSPY", "SO XSPY", "SOP Y", "SO PHU YEN", "SO PHUYEN"},
            {"SOKH", "SO KH", "SOXSKH", "SO XSKH", "SOK H", "SO KHANH HOA", "SO KHANHHOA"},
            {"SOQT", "SO QT", "SOXSQT", "SO XSQT", "SOQ T", "SO QUANG TRI"},
            {"SOGL", "SO GL", "SOXSGL", "SO XSGL", "SOG L", "SO GIA LAI", "SO GIALAI"},
            {"SONT", "SO NT", "SOXSNT", "SO XSNT", "SON T", "SO NINH THUAN"},
            {"SOKT", "SO KT", "SOXSKT", "SO XSKT", "SOK T", "SO KON TUM"},
            {"SOMB", "SO MB", "SO M B", "SO MIEN BAC"},
            {"SO"},
            {"S0HCM", "S0 HCM", "S0XSHCM", "S0 TP", "S0TP", "S0 XSHCM", "S0 XS HCM", "S0H CM", "S0 HO CHI MINH", "S0 THANH PHO"},
            {"S0QNG", "S0 QNG", "S0XSQNG", "S0 XSQNG", "S0Q NG", "S0 QUANG NGAI", "S0 QUANGNGAI"},
            {"S0DNO", "S0 DNO", "S0XSDNO", "S0 XSDNO", "S0D NO", "S0 D N O", "S0 DAC NONG"},
            {"S0BTH", "S0 BTH", "S0XSBTH", "S0 XSBTH", "S0B TH", "S0 B T", "S0 BINH THUAN", "S0 BINHTHUAN"},
            {"S0TTH", "S0 TTH", "S0XSTTH", "S0 XSTTH", "S0T TH", "S0 T T H", "S0 THUA THIEN HUE"},
            {"S0QNM", "S0 QNN", "S0XSQNM", "S0 XSQNN", "S0Q NM", "S0 Q N M", "S0 QUANG NAM"},
            {"S0DNG", "S0 DNG", "S0XSDNG", "S0 XSDNG", "S0D NG", "S0 D N G", "S0 DA NANG", "S0 DANANG"},
            {"S0BDI", "S0 BDI", "S0XSBDI", "S0 XSBDI", "S0B DI", "S0 B DI", "S0 BINH DINH", "S0 BINHDINH"},
            {"S0DLK", "S0 DLK", "S0XSDLK", "S0 XSDLK", "S0D LK", "S0 D L K", "S0 DAK LAK"},
            {"S0TD", "S0 TD", "S0XSTD", "S0 XSTD", "S0T D", "S0 HN", "S0 HA NOI"},
            {"S0QN", "S0 QN", "S0XSQN", "S0 XSQN", "S0Q N", "S0 Q N", "S0 QUANG NINH"},
            {"S0BN", "S0 BN", "S0XSBN", "S0 XSBN", "S0B N", "S0 B N", "S0 BAC NINH", "S0 BACNINH"},
            {"S0HP", "S0 HP", "S0XSHP", "S0 XSHP", "S0H P", "S0 H P", "S0 HAI PHONG", "S0 HAIPHONG"},
            {"S0ND", "S0 ND", "S0XSND", "S0 XSND", "S0N D", "S0 N D", "S0 NAMDINH", "S0 NAM DINH"},
            {"S0TB", "S0 TB", "S0XSTB", "S0 XSTB", "S0T B", "S0 T B", "S0 THAIBINH", "S0 THAI BINH"},
            {"S0CM", "S0 CM", "S0XSCM", "S0 XSCM", "S0C M", "S0 C M", "S0 CA MAU", "S0CAMAU", "S0 CAMAU"},
            {"S0DT", "S0 DT", "S0XSDT", "S0 XSDT", "S0D T", "S0 D T", "S0 S0NG THAP", "S0 S0NGTHAP"},
            {"S0BL", "S0 BL", "S0XSBL", "S0 XSBL", "S0B L", "S0 B L", "S0 BAC LIEU", "S0 BACLIEU"},
            {"S0BTR", "S0 BTR", "S0XSBTR", "S0 XSBTR", "S0B TR", "S0 B T R", "S0 BENTRE", "S0 BEN TRE"},
            {"S0VT", "S0 VT", "S0XSVT", "S0 XSVT", "S0V T", "S0 VUNG TAU"},
            {"S0CT", "S0 CT", "S0XSCT", "S0 XSCT", "S0C T", "S0 CAN THO", "S0 CANTHO"},
            {"S0DNI", "S0 DNI", "S0XSDNI", "S0 XSDNI", "S0D NI", "S0DN", "S0 DN", "S0 S0NGNAI", "S0 S0NGNAI"},
            {"S0ST", "S0 ST", "S0XSST", "S0 XSST", "S0S T", "S0 SOC TRANG", "S0 SOCTRANG"},
            {"S0AG", "S0 AG", "S0XSAG", "S0 XSAG", "S0A G", "S0 ANGIANG", "S0 AN GIANG"},
            {"S0TN", "S0 TN", "S0XSTN", "S0 XSTN", "S0T N", "S0 TAYNINH", "S0 TAY NINH"},
            {"S0BD", "S0 BD", "S0XSBD", "S0 XSBD", "S0B D", "S0 BINH DUONG", "S0BINHDUONG", "S0 BINHDUONG"},
            {"S0TRV", "S0 TRV", "S0XSTRV", "S0 XSTRV", "S0T V", "S0TV", "S0 TV", "S0 TRA VINH", "S0 TRAVINH"},
            {"S0VL", "S0 VL", "S0XSVL", "S0 XSVL", "S0V L", "S0 VINHLONG", "S0 VINH LONG"},
            {"S0BP", "S0 BP", "S0XSBP", "S0 XSBP", "S0B P", "S0 BINH PHUOC", "S0 BINHPHUOC"},
            {"S0HG", "S0 HG", "S0XSHG", "S0 XSHG", "S0H G", "S0 HAU GIANG", "S0HAUGIANG"},
            {"S0LA", "S0 LA", "S0XSLA", "S0 XSLA", "S0L A", "S0 LONG AN", "S0 LONGAN"},
            {"S0KG", "S0 KG", "S0XSKG", "S0 XSKG", "S0K G", "S0 KIENGIANG", "S0 KIEN GIANG"},
            {"S0LD", "S0 LD", "S0XSLD", "S0 XSLD", "S0L D", "S0 LAM S0NG", "S0 LAMS0NG", "S0DL", "S0 DL", "S0 DA LAT", "S0DALAT"},
            {"S0TG", "S0 TG", "S0XSTG", "S0 XSTG", "S0 XS TG", "S0SXTG", "S0T G", "S0 TIENGIANG", "S0 TIEN GIANG"},
            {"S0PY", "S0 PY", "S0XSPY", "S0 XSPY", "S0P Y", "S0 PHU YEN", "S0 PHUYEN"},
            {"S0KH", "S0 KH", "S0XSKH", "S0 XSKH", "S0K H", "S0 KHANH HOA", "S0 KHANHHOA"},
            {"S0QT", "S0 QT", "S0XSQT", "S0 XSQT", "S0Q T", "S0 QUANG TRI"},
            {"S0GL", "S0 GL", "S0XSGL", "S0 XSGL", "S0G L", "S0 GIA LAI", "S0 GIALAI"},
            {"S0NT", "S0 NT", "S0XSNT", "S0 XSNT", "S0N T", "S0 NINH THUAN"},
            {"S0KT", "S0 KT", "S0XSKT", "S0 XSKT", "S0K T", "S0 KON TUM"},
            {"S0MB", "S0 MB", "S0 M B", "S0 MIEN BAC"},
            {"S0"},
            {"SCHCM", "SC HCM", "SCXSHCM", "SC TP", "SCTP", "SC XSHCM", "SC XS HCM", "SCH CM", "SC HO CHI MINH", "SC THANH PHO"},
            {"SCQNG", "SC QNG", "SCXSQNG", "SC XSQNG", "SCQ NG", "SC QUANG NGAI", "SC QUANGNGAI"},
            {"SCDNO", "SC DNO", "SCXSDNO", "SC XSDNO", "SCD NO", "SC D N O", "SC DAC NONG"},
            {"SCBTH", "SC BTH", "SCXSBTH", "SC XSBTH", "SCB TH", "SC B T", "SC BINH THUAN", "SC BINHTHUAN"},
            {"SCTTH", "SC TTH", "SCXSTTH", "SC XSTTH", "SCT TH", "SC T T H", "SC THUA THIEN HUE"},
            {"SCQNM", "SC QNN", "SCXSQNM", "SC XSQNN", "SCQ NM", "SC Q N M", "SC QUANG NAM"},
            {"SCDNG", "SC DNG", "SCXSDNG", "SC XSDNG", "SCD NG", "SC D N G", "SC DA NANG", "SC DANANG"},
            {"SCBDI", "SC BDI", "SCXSBDI", "SC XSBDI", "SCB DI", "SC B DI", "SC BINH DINH", "SC BINHDINH"},
            {"SCDLK", "SC DLK", "SCXSDLK", "SC XSDLK", "SCD LK", "SC D L K", "SC DAK LAK"},
            {"SCTD", "SC TD", "SCXSTD", "SC XSTD", "SCT D", "SC HN", "SC HA NOI"},
            {"SCQN", "SC QN", "SCXSQN", "SC XSQN", "SCQ N", "SC Q N", "SC QUANG NINH"},
            {"SCBN", "SC BN", "SCXSBN", "SC XSBN", "SCB N", "SC B N", "SC BAC NINH", "SC BACNINH"},
            {"SCHP", "SC HP", "SCXSHP", "SC XSHP", "SCH P", "SC H P", "SC HAI PHONG", "SC HAIPHONG"},
            {"SCND", "SC ND", "SCXSND", "SC XSND", "SCN D", "SC N D", "SC NAMDINH", "SC NAM DINH"},
            {"SCTB", "SC TB", "SCXSTB", "SC XSTB", "SCT B", "SC T B", "SC THAIBINH", "SC THAI BINH"},
            {"SCCM", "SC CM", "SCXSCM", "SC XSCM", "SCC M", "SC C M", "SC CA MAU", "SCCAMAU", "SC CAMAU"},
            {"SCDT", "SC DT", "SCXSDT", "SC XSDT", "SCD T", "SC D T", "SC SCNG THAP", "SC SCNGTHAP"},
            {"SCBL", "SC BL", "SCXSBL", "SC XSBL", "SCB L", "SC B L", "SC BAC LIEU", "SC BACLIEU"},
            {"SCBTR", "SC BTR", "SCXSBTR", "SC XSBTR", "SCB TR", "SC B T R", "SC BENTRE", "SC BEN TRE"},
            {"SCVT", "SC VT", "SCXSVT", "SC XSVT", "SCV T", "SC VUNG TAU"},
            {"SCCT", "SC CT", "SCXSCT", "SC XSCT", "SCC T", "SC CAN THO", "SC CANTHO"},
            {"SCDNI", "SC DNI", "SCXSDNI", "SC XSDNI", "SCD NI", "SCDN", "SC DN", "SC SCNGNAI", "SC SCNGNAI"},
            {"SCST", "SC ST", "SCXSST", "SC XSST", "SCS T", "SC SOC TRANG", "SC SOCTRANG"},
            {"SCAG", "SC AG", "SCXSAG", "SC XSAG", "SCA G", "SC ANGIANG", "SC AN GIANG"},
            {"SCTN", "SC TN", "SCXSTN", "SC XSTN", "SCT N", "SC TAYNINH", "SC TAY NINH"},
            {"SCBD", "SC BD", "SCXSBD", "SC XSBD", "SCB D", "SC BINH DUONG", "SCBINHDUONG", "SC BINHDUONG"},
            {"SCTRV", "SC TRV", "SCXSTRV", "SC XSTRV", "SCT V", "SCTV", "SC TV", "SC TRA VINH", "SC TRAVINH"},
            {"SCVL", "SC VL", "SCXSVL", "SC XSVL", "SCV L", "SC VINHLONG", "SC VINH LONG"},
            {"SCBP", "SC BP", "SCXSBP", "SC XSBP", "SCB P", "SC BINH PHUOC", "SC BINHPHUOC"},
            {"SCHG", "SC HG", "SCXSHG", "SC XSHG", "SCH G", "SC HAU GIANG", "SCHAUGIANG"},
            {"SCLA", "SC LA", "SCXSLA", "SC XSLA", "SCL A", "SC LONG AN", "SC LONGAN"},
            {"SCKG", "SC KG", "SCXSKG", "SC XSKG", "SCK G", "SC KIENGIANG", "SC KIEN GIANG"},
            {"SCLD", "SC LD", "SCXSLD", "SC XSLD", "SCL D", "SC LAM SCNG", "SC LAMSCNG", "SCDL", "SC DL", "SC DA LAT", "SCDALAT"},
            {"SCTG", "SC TG", "SCXSTG", "SC XSTG", "SC XS TG", "SCSXTG", "SCT G", "SC TIENGIANG", "SC TIEN GIANG"},
            {"SCPY", "SC PY", "SCXSPY", "SC XSPY", "SCP Y", "SC PHU YEN", "SC PHUYEN"},
            {"SCKH", "SC KH", "SCXSKH", "SC XSKH", "SCK H", "SC KHANH HOA", "SC KHANHHOA"},
            {"SCQT", "SC QT", "SCXSQT", "SC XSQT", "SCQ T", "SC QUANG TRI"},
            {"SCGL", "SC GL", "SCXSGL", "SC XSGL", "SCG L", "SC GIA LAI", "SC GIALAI"},
            {"SCNT", "SC NT", "SCXSNT", "SC XSNT", "SCN T", "SC NINH THUAN"},
            {"SCKT", "SC KT", "SCXSKT", "SC XSKT", "SCK T", "SC KON TUM"},
            {"SCQB", "SC QB", "SC Q B", "SC QUANG BINH", "SC QUANGBINH"},
            {"SCMB", "SC MB", "SC M B", "SC MIEN BAC"},
            {"SC"},
            {"TKHCM", "TK HCM", "TKXSHCM", "TK TP", "TKTP", "TK XSHCM", "TK XS HCM", "TKH CM", "TK HO CHI MINH", "TK THANH PHO"},
            {"TKQNG", "TK QNG", "TKXSQNG", "TK XSQNG", "TKQ NG", "TK QUANG NGAI", "TK QUANGNGAI"},
            {"TKDNO", "TK DNO", "TKXSDNO", "TK XSDNO", "TKD NO", "TK D N O", "TK DAC NONG"},
            {"TKBTH", "TK BTH", "TKXSBTH", "TK XSBTH", "TKB TH", "TK B T", "TK BINH THUAN", "TK BINHTHUAN"},
            {"TKTTH", "TK TTH", "TKXSTTH", "TK XSTTH", "TKT TH", "TK T T H", "TK THUA THIEN HUE"},
            {"TKQNM", "TK QNN", "TKXSQNM", "TK XSQNN", "TKQ NM", "TK Q N M", "TK QUANG NAM"},
            {"TKDNG", "TK DNG", "TKXSDNG", "TK XSDNG", "TKD NG", "TK D N G", "TK DA NANG", "TK DANANG"},
            {"TKBDI", "TK BDI", "TKXSBDI", "TK XSBDI", "TKB DI", "TK B DI", "TK BINH DINH", "TK BINHDINH"},
            {"TKDLK", "TK DLK", "TKXSDLK", "TK XSDLK", "TKD LK", "TK D L K", "TK DAK LAK"},
            {"TKTD", "TK TD", "TKXSTD", "TK XSTD", "TKT D", "TK HN", "TK HA NOI"},
            {"TKQN", "TK QN", "TKXSQN", "TK XSQN", "TKQ N", "TK Q N", "TK QUANG NINH"},
            {"TKBN", "TK BN", "TKXSBN", "TK XSBN", "TKB N", "TK B N", "TK BAC NINH", "TK BACNINH"},
            {"TKHP", "TK HP", "TKXSHP", "TK XSHP", "TKH P", "TK H P", "TK HAI PHONG", "TK HAIPHONG"},
            {"TKND", "TK ND", "TKXSND", "TK XSND", "TKN D", "TK N D", "TK NAMDINH", "TK NAM DINH"},
            {"TKTB", "TK TB", "TKXSTB", "TK XSTB", "TKT B", "TK T B", "TK THAIBINH", "TK THAI BINH"},
            {"TKCM", "TK CM", "TKXSCM", "TK XSCM", "TKC M", "TK C M", "TK CA MAU", "TKCAMAU", "TK CAMAU"},
            {"TKDT", "TK DT", "TKXSDT", "TK XSDT", "TKD T", "TK D T", "TK TKNG THAP", "TK TKNGTHAP"},
            {"TKBL", "TK BL", "TKXSBL", "TK XSBL", "TKB L", "TK B L", "TK BAC LIEU", "TK BACLIEU"},
            {"TKBTR", "TK BTR", "TKXSBTR", "TK XSBTR", "TKB TR", "TK B T R", "TK BENTRE", "TK BEN TRE"},
            {"TKVT", "TK VT", "TKXSVT", "TK XSVT", "TKV T", "TK VUNG TAU"},
            {"TKCT", "TK CT", "TKXSCT", "TK XSCT", "TKC T", "TK CAN THO", "TK CANTHO"},
            {"TKDNI", "TK DNI", "TKXSDNI", "TK XSDNI", "TKD NI", "TKDN", "TK DN", "TK TKNGNAI", "TK TKNGNAI"},
            {"TKST", "TK ST", "TKXSST", "TK XSST", "TKS T", "TK SOC TRANG", "TK SOCTRANG"},
            {"TKAG", "TK AG", "TKXSAG", "TK XSAG", "TKA G", "TK ANGIANG", "TK AN GIANG"},
            {"TKTN", "TK TN", "TKXSTN", "TK XSTN", "TKT N", "TK TAYNINH", "TK TAY NINH"},
            {"TKBD", "TK BD", "TKXSBD", "TK XSBD", "TKB D", "TK BINH DUONG", "TKBINHDUONG", "TK BINHDUONG"},
            {"TKTRV", "TK TRV", "TKXSTRV", "TK XSTRV", "TKT V", "TKTV", "TK TV", "TK TRA VINH", "TK TRAVINH"},
            {"TKVL", "TK VL", "TKXSVL", "TK XSVL", "TKV L", "TK VINHLONG", "TK VINH LONG"},
            {"TKBP", "TK BP", "TKXSBP", "TK XSBP", "TKB P", "TK BINH PHUOC", "TK BINHPHUOC"},
            {"TKHG", "TK HG", "TKXSHG", "TK XSHG", "TKH G", "TK HAU GIANG", "TKHAUGIANG"},
            {"TKLA", "TK LA", "TKXSLA", "TK XSLA", "TKL A", "TK LONG AN", "TK LONGAN"},
            {"TKKG", "TK KG", "TKXSKG", "TK XSKG", "TKK G", "TK KIENGIANG", "TK KIEN GIANG"},
            {"TKLD", "TK LD", "TKXSLD", "TK XSLD", "TKL D", "TK LAM TKNG", "TK LAMTKNG", "TKDL", "TK DL", "TK DA LAT", "TKDALAT"},
            {"TKTG", "TK TG", "TKXSTG", "TK XSTG", "TK XS TG", "TKSXTG", "TKT G", "TK TIENGIANG", "TK TIEN GIANG"},
            {"TKPY", "TK PY", "TKXSPY", "TK XSPY", "TKP Y", "TK PHU YEN", "TK PHUYEN"},
            {"TKKH", "TK KH", "TKXSKH", "TK XSKH", "TKK H", "TK KHANH HOA", "TK KHANHHOA"},
            {"TKQT", "TK QT", "TKXSQT", "TK XSQT", "TKQ T", "TK QUANG TRI"},
            {"TKGL", "TK GL", "TKXSGL", "TK XSGL", "TKG L", "TK GIA LAI", "TK GIALAI"},
            {"TKNT", "TK NT", "TKXSNT", "TK XSNT", "TKN T", "TK NINH THUAN"},
            {"TKKT", "TK KT", "TKXSKT", "TK XSKT", "TKK T", "TK KON TUM"},
            {"TKMB", "TK MB", "TK M B", "TK MIEN BAC"},
            {"TK MN", "TKMN", "TK M N", "TK MIENNAM", "TKMIENNAM", "TK MIEN NAM"},
            {"TK"},
            {"QAHCM", "QA HCM", "QAXSHCM", "QA TP", "QATP", "QA XSHCM", "QA XS HCM", "QAH CM", "QA HO CHI MINH", "QA THANH PHO"},
            {"QAQNG", "QA QNG", "QAXSQNG", "QA XSQNG", "QAQ NG", "QA QUANG NGAI", "QA QUANGNGAI"},
            {"QADNO", "QA DNO", "QAXSDNO", "QA XSDNO", "QAD NO", "QA D N O", "QA DAC NONG"},
            {"QABTH", "QA BTH", "QAXSBTH", "QA XSBTH", "QAB TH", "QA B T", "QA BINH THUAN", "QA BINHTHUAN"},
            {"QATTH", "QA TTH", "QAXSTTH", "QA XSTTH", "QAT TH", "QA T T H", "QA THUA THIEN HUE"},
            {"QAQNM", "QA QNN", "QAXSQNM", "QA XSQNN", "QAQ NM", "QA Q N M", "QA QUANG NAM"},
            {"QADNG", "QA DNG", "QAXSDNG", "QA XSDNG", "QAD NG", "QA D N G", "QA DA NANG", "QA DANANG"},
            {"QABDI", "QA BDI", "QAXSBDI", "QA XSBDI", "QAB DI", "QA B DI", "QA BINH DINH", "QA BINHDINH"},
            {"QADLK", "QA DLK", "QAXSDLK", "QA XSDLK", "QAD LK", "QA D L K", "QA DAK LAK"},
            {"QATD", "QA TD", "QAXSTD", "QA XSTD", "QAT D", "QA HN", "QA HA NOI"},
            {"QAQN", "QA QN", "QAXSQN", "QA XSQN", "QAQ N", "QA Q N", "QA QUANG NINH"},
            {"QABN", "QA BN", "QAXSBN", "QA XSBN", "QAB N", "QA B N", "QA BAC NINH", "QA BACNINH"},
            {"QAHP", "QA HP", "QAXSHP", "QA XSHP", "QAH P", "QA H P", "QA HAI PHONG", "QA HAIPHONG"},
            {"QAND", "QA ND", "QAXSND", "QA XSND", "QAN D", "QA N D", "QA NAMDINH", "QA NAM DINH"},
            {"QATB", "QA TB", "QAXSTB", "QA XSTB", "QAT B", "QA T B", "QA THAIBINH", "QA THAI BINH"},
            {"QACM", "QA CM", "QAXSCM", "QA XSCM", "QAC M", "QA C M", "QA CA MAU", "QACAMAU", "QA CAMAU"},
            {"QADT", "QA DT", "QAXSDT", "QA XSDT", "QAD T", "QA D T", "QA QANG THAP", "QA QANGTHAP"},
            {"QABL", "QA BL", "QAXSBL", "QA XSBL", "QAB L", "QA B L", "QA BAC LIEU", "QA BACLIEU"},
            {"QABTR", "QA BTR", "QAXSBTR", "QA XSBTR", "QAB TR", "QA B T R", "QA BENTRE", "QA BEN TRE"},
            {"QAVT", "QA VT", "QAXSVT", "QA XSVT", "QAV T", "QA VUNG TAU"},
            {"QACT", "QA CT", "QAXSCT", "QA XSCT", "QAC T", "QA CAN THO", "QA CANTHO"},
            {"QADNI", "QA DNI", "QAXSDNI", "QA XSDNI", "QAD NI", "QADN", "QA DN", "QA QANGNAI", "QA QANGNAI"},
            {"QAST", "QA ST", "QAXSST", "QA XSST", "QAS T", "QA SOC TRANG", "QA SOCTRANG"},
            {"QAAG", "QA AG", "QAXSAG", "QA XSAG", "QAA G", "QA ANGIANG", "QA AN GIANG"},
            {"QATN", "QA TN", "QAXSTN", "QA XSTN", "QAT N", "QA TAYNINH", "QA TAY NINH"},
            {"QABD", "QA BD", "QAXSBD", "QA XSBD", "QAB D", "QA BINH DUONG", "QABINHDUONG", "QA BINHDUONG"},
            {"QATRV", "QA TRV", "QAXSTRV", "QA XSTRV", "QAT V", "QATV", "QA TV", "QA TRA VINH", "QA TRAVINH"},
            {"QAVL", "QA VL", "QAXSVL", "QA XSVL", "QAV L", "QA VINHLONG", "QA VINH LONG"},
            {"QABP", "QA BP", "QAXSBP", "QA XSBP", "QAB P", "QA BINH PHUOC", "QA BINHPHUOC"},
            {"QAHG", "QA HG", "QAXSHG", "QA XSHG", "QAH G", "QA HAU GIANG", "QAHAUGIANG"},
            {"QALA", "QA LA", "QAXSLA", "QA XSLA", "QAL A", "QA LONG AN", "QA LONGAN"},
            {"QAKG", "QA KG", "QAXSKG", "QA XSKG", "QAK G", "QA KIENGIANG", "QA KIEN GIANG"},
            {"QALD", "QA LD", "QAXSLD", "QA XSLD", "QAL D", "QA LAM QANG", "QA LAMQANG", "QADL", "QA DL", "QA DA LAT", "QADALAT"},
            {"QATG", "QA TG", "QAXSTG", "QA XSTG", "QA XS TG", "QASXTG", "QAT G", "QA TIENGIANG", "QA TIEN GIANG"},
            {"QAPY", "QA PY", "QAXSPY", "QA XSPY", "QAP Y", "QA PHU YEN", "QA PHUYEN"},
            {"QAKH", "QA KH", "QAXSKH", "QA XSKH", "QAK H", "QA KHANH HOA", "QA KHANHHOA"},
            {"QAQT", "QA QT", "QAXSQT", "QA XSQT", "QAQ T", "QA QUANG TRI"},
            {"QAGL", "QA GL", "QAXSGL", "QA XSGL", "QAG L", "QA GIA LAI", "QA GIALAI"},
            {"QANT", "QA NT", "QAXSNT", "QA XSNT", "QAN T", "QA NINH THUAN"},
            {"QAKT", "QA KT", "QAXSKT", "QA XSKT", "QAK T", "QA KON TUM"},
            {"QAMB", "QA MB", "QA M B", "QA MIEN BAC"},
            {"QA MN", "QAMN", "QA M N", "QA MIENNAM", "QAMIENNAM", "QA MIEN NAM"},
            {"QA"}
        };
        String[] msgValidCode = {
            "CAUMNHCM", "CAUMTQNG", "CAUMTDNO", "CAUMNBTH", "CAUMTTTH", "CAUMTQNM", "CAUMTDNG", "CAUMTBDI", "CAUMTDLK",
            "CAUMBTD", "CAUMBQN", "CAUMBBN", "CAUMBHP", "CAUMBND", "CAUMBTB", "CAUMNCM", "CAUMNDT", "CAUMNBL", "CAUMNBTR",
            "CAUMNVT", "CAUMNCT", "CAUMNDNI", "CAUMNST", "CAUMNAG", "CAUMNTN", "CAUMNBD", "CAUMNTRV", "CAUMNVL", "CAUMNBP",
            "CAUMNHG", "CAUMNLA", "CAUMNKG", "CAUMNLD", "CAUMNTG", "CAUMTPY", "CAUMTKH", "CAUMTQT", "CAUMTGL", "CAUMTNT",
            "CAUMNKT", "CAUMTQB", "CAUMB", "CAUMB",
            "LOCMNHCM", "LOCMTQNG", "LOCMTDNO", "LOCMNBTH", "LOCMTTTH", "LOCMTQNM", "LOCMTDNG", "LOCMTBDI", "LOCMTDLK",
            "LOCMBTD", "LOCMBQN", "LOCMBBN", "LOCMBHP", "LOCMBND", "LOCMBTB", "LOCMNCM", "LOCMNDT", "LOCMNBL", "LOCMNBTR",
            "LOCMNVT", "LOCMNCT", "LOCMNDNI", "LOCMNST", "LOCMNAG", "LOCMNTN", "LOCMNBD", "LOCMNTRV", "LOCMNVL", "LOCMNBP",
            "LOCMNHG", "LOCMNLA", "LOCMNKG", "LOCMNLD", "LOCMNTG", "LOCMTPY", "LOCMTKH", "LOCMTQT", "LOCMTGL", "LOCMTNT",
            "LOCMNKT", "LOCMTQB", "LOCMB", "LOCMB",
            "L0CMNHCM", "L0CMTQNG", "L0CMTDNO", "L0CMNBTH", "L0CMTTTH", "L0CMTQNM", "L0CMTDNG", "L0CMTBDI", "L0CMTDLK",
            "L0CMBTD", "L0CMBQN", "L0CMBBN", "L0CMBHP", "L0CMBND", "L0CMBTB", "L0CMNCM", "L0CMNDT", "L0CMNBL", "L0CMNBTR",
            "L0CMNVT", "L0CMNCT", "L0CMNDNI", "L0CMNST", "L0CMNAG", "L0CMNTN", "L0CMNBD", "L0CMNTRV", "L0CMNVL", "L0CMNBP",
            "L0CMNHG", "L0CMNLA", "L0CMNKG", "L0CMNLD", "L0CMNTG", "L0CMTPY", "L0CMTKH", "L0CMTQT", "L0CMTGL", "L0CMTNT",
            "L0CMNKT", "L0CMTQB", "L0CMB", "L0CMB",
            "LOMNHCM", "LOMTQNG", "LOMTDNO", "LOMNBTH", "LOMTTTH", "LOMTQNM", "LOMTDNG", "LOMTBDI", "LOMTDLK",
            "LOMBTD", "LOMBQN", "LOMBBN", "LOMBHP", "LOMBND", "LOMBTB", "LOMNCM", "LOMNDT", "LOMNBL", "LOMNBTR",
            "LOMNVT", "LOMNCT", "LOMNDNI", "LOMNST", "LOMNAG", "LOMNTN", "LOMNBD", "LOMNTRV", "LOMNVL", "LOMNBP",
            "LOMNHG", "LOMNLA", "LOMNKG", "LOMNLD", "LOMNTG", "LOMTPY", "LOMTKH", "LOMTQT", "LOMTGL", "LOMTNT",
            "LOMNKT", "LOMB", "LOMB",
            "L0MNHCM", "L0MTQNG", "L0MTDNO", "L0MNBTH", "L0MTTTH", "L0MTQNM", "L0MTDNG", "L0MTBDI", "L0MTDLK",
            "L0MBTD", "L0MBQN", "L0MBBN", "L0MBHP", "L0MBND", "L0MBTB", "L0MNCM", "L0MNDT", "L0MNBL", "L0MNBTR",
            "L0MNVT", "L0MNCT", "L0MNDNI", "L0MNST", "L0MNAG", "L0MNTN", "L0MNBD", "L0MNTRV", "L0MNVL", "L0MNBP",
            "L0MNHG", "L0MNLA", "L0MNKG", "L0MNLD", "L0MNTG", "L0MTPY", "L0MTKH", "L0MTQT", "L0MTGL", "L0MTNT",
            "L0MNKT", "L0MB", "L0MB",
            "SOMNHCM", "SOMTQNG", "SOMTDNO", "SOMNBTH", "SOMTTTH", "SOMTQNM", "SOMTDNG", "SOMTBDI", "SOMTDLK",
            "SOMBTD", "SOMBQN", "SOMBBN", "SOMBHP", "SOMBND", "SOMBTB", "SOMNCM", "SOMNDT", "SOMNBL", "SOMNBTR",
            "SOMNVT", "SOMNCT", "SOMNDNI", "SOMNST", "SOMNAG", "SOMNTN", "SOMNBD", "SOMNTRV", "SOMNVL", "SOMNBP",
            "SOMNHG", "SOMNLA", "SOMNKG", "SOMNLD", "SOMNTG", "SOMTPY", "SOMTKH", "SOMTQT", "SOMTGL", "SOMTNT",
            "SOMNKT", "SOMB", "SOMB",
            "S0MNHCM", "S0MTQNG", "S0MTDNO", "S0MNBTH", "S0MTTTH", "S0MTQNM", "S0MTDNG", "S0MTBDI", "S0MTDLK",
            "S0MBTD", "S0MBQN", "S0MBBN", "S0MBHP", "S0MBND", "S0MBTB", "S0MNCM", "S0MNDT", "S0MNBL", "S0MNBTR",
            "S0MNVT", "S0MNCT", "S0MNDNI", "S0MNST", "S0MNAG", "S0MNTN", "S0MNBD", "S0MNTRV", "S0MNVL", "S0MNBP",
            "S0MNHG", "S0MNLA", "S0MNKG", "S0MNLD", "S0MNTG", "S0MTPY", "S0MTKH", "S0MTQT", "S0MTGL", "S0MTNT",
            "S0MNKT", "S0MB", "S0MB",
            "SCMNHCM", "SCMTQNG", "SCMTDNO", "SCMNBTH", "SCMTTTH", "SCMTQNM", "SCMTDNG", "SCMTBDI", "SCMTDLK",
            "SCMBTD", "SCMBQN", "SCMBBN", "SCMBHP", "SCMBND", "SCMBTB", "SCMNCM", "SCMNDT", "SCMNBL", "SCMNBTR",
            "SCMNVT", "SCMNCT", "SCMNDNI", "SCMNST", "SCMNAG", "SCMNTN", "SCMNBD", "SCMNTRV", "SCMNVL", "SCMNBP",
            "SCMNHG", "SCMNLA", "SCMNKG", "SCMNLD", "SCMNTG", "SCMTPY", "SCMTKH", "SCMTQT", "SCMTGL", "SCMTNT",
            "SCMNKT", "SCMTQB", "SCMB", "SCMB",
            "TKMNHCM", "TKMTQNG", "TKMTDNO", "TKMNBTH", "TKMTTTH", "TKMTQNM", "TKMTDNG", "TKMTBDI", "TKMTDLK",
            "TKMBTD", "TKMBQN", "TKMBBN", "TKMBHP", "TKMBND", "TKMBTB", "TKMNCM", "TKMNDT", "TKMNBL", "TKMNBTR",
            "TKMNVT", "TKMNCT", "TKMNDNI", "TKMNST", "TKMNAG", "TKMNTN", "TKMNBD", "TKMNTRV", "TKMNVL", "TKMNBP",
            "TKMNHG", "TKMNLA", "TKMNKG", "TKMNLD", "TKMNTG", "TKMTPY", "TKMTKH", "TKMTQT", "TKMTGL", "TKMTNT",
            "TKMNKT", "TKMB", "TKMN", "TKMB",
            "QAMNHCM", "QAMTQNG", "QAMTDNO", "QAMNBTH", "QAMTTTH", "QAMTQNM", "QAMTDNG", "QAMTBDI", "QAMTDLK",
            "QAMBTD", "QAMBQN", "QAMBBN", "QAMBHP", "QAMBND", "QAMBTB", "QAMNCM", "QAMNDT", "QAMNBL", "QAMNBTR",
            "QAMNVT", "QAMNCT", "QAMNDNI", "QAMNST", "QAMNAG", "QAMNTN", "QAMNBD", "QAMNTRV", "QAMNVL", "QAMNBP",
            "QAMNHG", "QAMNLA", "QAMNKG", "QAMNLD", "QAMNTG", "QAMTPY", "QAMQAH", "QAMTQT", "QAMTGL", "QAMTNT",
            "QAMNKT", "QAMB", "QAMN", "QAMB"
        };
        String sCommandCode = "";
        String sOption = "";
        String sTmp = "";
        try {

            msg = msg.toUpperCase();
            msg = msg.trim();
            boolean flag = false;

            for (int i = 0; i < msgInvCode.length; i++) {
                for (int j = 0; j < msgInvCode[i].length; j++) {
                    if (msg.startsWith(msgInvCode[i][j])) {
                        sCommandCode = msgValidCode[i];
                        sTmp = msgInvCode[i][j];
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            sOption = msg.substring(sTmp.length());
            sOption = sOption.trim();
            //Khong co phan tu dang sau
            if (sOption.equals("-1")) {
                sOption = "";
            }
            str_return = new String[2];
            str_return[0] = sCommandCode;
            str_return[1] = sOption;

        } catch (Exception ex) {
        }

        return str_return;

    }

    public static String[] parseFBCode(String msg) {
        String[] str_return = null;
        String[][] msgInvCode = {
            {"AK TIP1", "AKTIP1", "AK VIP1", "AKVIP1", "AK TIP1", "AK TLP1", "AKTLP1", "AKVLP1", "AK VLP1",
                "AK VTP1", "AKVTP1", "AK T I P1", "AK T IP1", "AK TP1", "AKTP1", "AK TQ1", "AKTQ1", "AK TIQ1", "AKTIQ1"},
            {"AK TIP2", "AKTIP2", "AK VIP2", "AKVIP2", "AK TIP2", "AK TLP2", "AKTLP2", "AKVLP2", "AK VLP2",
                "AK VTP2", "AKVTP2", "AK T I P2", "AK T IP2", "AK TP2", "AKTP2", "AK TQ2", "AKTQ2", "AK TIQ2", "AKTIQ2"},
            {"AK TIP", "AKTIP", "AK VIP", "AKVIP", "AK TLP", "AKTLP", "AKVLP", "AK VLP", "AK VTP", "AKVTP",
                "AK T I P", "AK T IP", "AK TP", "AKTP", "AK TQ", "AKTQ", "AK TIQ", "AKTIQ"},
            {"AK XIEN1", "AKXIEN1", "AKXLEN1", "AK XLEN1", "AK XIE1", "AKXIE1", "AK SIEN1", "AKSIEN1", "AKXIN1", "AK XIN1"},
            {"AK XIEN2", "AKXIEN2", "AKXLEN2", "AK XLEN2", "AK XIE2", "AKXIE2", "AK SIEN2", "AKSIEN2", "AKXIN2", "AK XIN2"},
            {"AK XIEN", "AKXIEN", "AKXLEN", "AK XLEN", "AK XIE", "AKXIE", "AK SIEN", "AKSIEN", "AKXIN", "AK XIN"},
            {"AK TX", "AKTX", "AK T X"},
            {"AK TAI", "AKTAI", "AK TA"},
            {"AK XIU", "AKXIU", "AK XLN", "AK XI"},
            {"AK TL1", "AKTL1", "AKT L1", "AK TILE1", "AK  T L1", "AK   TL1", "AK TL 1", "AK TI LE1", "AK TYLE1", "AK TY LE1"},
            {"AK TL2", "AKTL2", "AKT L2", "AK TILE2", "AK  T L2", "AK   TL2", "AK TL 2", "AK TI LE2", "AK TYLE2", "AK TY LE2"},
            {"AK TL", "AKTL", "AKT L", "AK TILE", "AK  T L", "AK   TL", "AK TI LE", "AK TYLE", "AK TY LE"},
            {"AK KQ1", "AK KQ 1", "AK K Q1", "AKK Q1", "AKQK1", "AK QK1", "AKKQ1", "AK K Q1 ", "AK KETQUA1", "AK KP1",
                "AK  KQ1", "AK   KQ1", "AK KETQUA1", "AK KET QUA1", "AK  KETQUA1", "AK KG1"},
            {"AK KQ2", "AK K Q2", "AKK Q2", "AKKQ2", "AKQK2", "AK QK2", "AK K Q2 ", "AK KETQUA2", "AK KP2",
                "AK  KQ2", "AK KQ 2", "AK   KQ2", "AK KETQUA2", "AK KET QUA2", "AK  KETQUA2", "AK KG2"},
            {"AK KQ", "AK K Q", "AKK Q", "AKKQ", "AKQK", "AK QK", "AK K Q ", "AK KETQUA", "AK KP",
                "AK  KQ", "AK   KQ", "AK KETQUA", "AK KET QUA", "AK  KETQUA", "AK KG"},
            {"AKBXH", "AK BXH", "AK BXH", "AKB XH", "AK BANGXEPHANG", "AK BANG XEP HANG"},
            {"AKLTD", "AK LTD", "AK LTD", "AKL TD", "AK L T D", "AK LICHTHIDAU", "AK LICH THI DAU"},
            {"AKVPL", "AK VPL", "AK VPL", "AK V P L", "AK VPL", "AK VUA PHA LUOI", "AK VUAPHALUOI"},
            {"AKDH", "AK DH", "AKD H", "AK D H ", "AK DOIHINH", "AK DOI HINH"},
            {"AKCLB", "AK CLB", "AK CLB", "AKC LB", "AK C L B"},
            {"AKTT", "AK TT", "AK T T", "AK T T", "AK  TT", "AK   TT", "AK TUONGTHUAT", "AK TUONG THUAT"},
            {"AKPD", "AK P D", "AKP D"},
            {"AKDD", "AK DD", "AK DD", "AKD D", "AK D D"},
            {"AKT H", "AK TH", "AKTH", "AK T H"},
            {"AKDT", "AK DT", "AKD T", "AK D T"},
            {"AK WL", "AKWL", "AKW", "AK W", "AKW", "AK W", "AK  W", "AK V"},
            {"AK CHO", "AKCHO", "AKC HO", "AK C H O", "AK CH O"},
            {"AKHOT", "AK HOT", "AKH OT", "AK H O T", "AK H OT"},
            {"AK"},
            {"AQ TIP1", "AQTIP1", "AQ VIP1", "AQVIP1", "AQ TIP1", "AQ TLP1", "AQTLP1", "AQVLP1", "AQ VLP1",
                "AQ VTP1", "AQVTP1", "AQ TP1", "AQTP1", "AQ TQ1"},
            {"AQ TIP2", "AQTIP2", "AQ VIP2", "AQVIP2", "AQ TIP2", "AQ TLP2", "AQTLP2", "AQVLP2", "AQ VLP2",
                "AQ VTP2", "AQVTP2", "AQ TP2", "AQTP2", "AQ TQ2"},
            {"AQ TIP", "AQTIP", "AQ VIP", "AQVIP", "AQ TLP", "AQTLP", "AQVLP", "AQVTP", "AQ TP", "AQTP", "AQ TQ", "AQTQ", "AQTIQ"},
            {"AQ XIEN1", "AQXIEN1", "AQXLEN1", "AQ XLEN1", "AQ XIE1", "AQXIE1", "AQ SIEN1", "AQSIEN1", "AQXIN1", "AQ XIN1"},
            {"AQ XIEN2", "AQXIEN2", "AQXLEN2", "AQ XLEN2", "AQ XIE2", "AQXIE2", "AQ SIEN2", "AQSIEN2", "AQXIN2", "AQ XIN2"},
            {"AQ XIEN", "AQXIEN", "AQXLEN", "AQ XLEN", "AQ XIE", "AQXIE", "AQXIN", "AQ XIN"},
            {"AQ TX", "AQTX", "AQ T X"},
            {"AQ TAI", "AQTAI", "AQ TA"},
            {"AQ XIU", "AQXIU", "AQ XLN", "AQ XI"},
            {"AQ TL1", "AQTL1", "AQT L1", "AQ TILE1", "AQ TI LE1", "AQ TYLE1", "AQ TY LE1"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>
            {"AQ TL2", "AQTL2", "AQT L2", "AQ TILE2", "AQ TI LE2", "AQ TYLE2", "AQ TY LE2"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"AQ TL", "AQTL", "AQT L", "AQ TILE", "AQ TI LE", "AQ TYLE", "AQ TY LE"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"AQ KQ1", "AQ KQ 1", "AQ K Q1", "AQK Q1", "AQQK1", "AQ QK1", "AQKQ1", "AQ KETQUA1", "AQ KP1",
                "AQ  KQ1"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"AQ KQ2", "AQ K Q2", "AQK Q2", "AQKQ2", "AQQK2", "AQ QK2", "AQ KP2", "AQ  KQ2", "AQ KQ 2"},
            {"AQ KQ", "AQ K Q", "AQK Q", "AQKQ", "AQQK", "AQ QK", "AQ K Q ", "AQ KETQUA", "AQ KP"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>

            {"AQBXH", "AQ BXH", "AQ BXH", "AQB XH", "AQ BANGXEPHANG", "AQ BANG XEP HANG"}, // Tra Bang Xep Hang <DEC BXH maGiai>
            {"AQLTD", "AQ LTD", "AQ LTD", "AQL TD", "AQ L T D", "AQ LICHTHIDAU", "AQ LICH THI DAU"}, // Tra Lich thi dau  <DEC LTD maGiai>
            {"AQVPL", "AQ VPL", "AQ VPL", "AQ V P L", "AQ VPL", "AQ VUA PHA LUOI", "AQ VUAPHALUOI"}, // Tra vua pha luoi <DEC VPL maGiai>
            {"AQDH", "AQ DH", "AQD H", "AQ D H ", "AQ DOIHINH"}, // Tra Doi hinh xuat phat <DEC DH maDoi>
            {"AQCLB", "AQ CLB", "AQ CLB", "AQC LB", "AQ C L B"}, // Tra ds Cau lac bo cua giai <DEC CLB maGiai>
            {"AQTT", "AQ TT", "AQ T T", "AQ T T", "AQ  TT", "AQ TUONGTHUAT", "AQ TUONG THUAT"}, // Tra thong tin truc tiep cua doi bong/Giai <DEC TT maGiai/maDoi>
            {"AQPD", "AQ P D", "AQP D"}, // Tra thong tin phong do cua doi bong/Giai <DEC PD maDoi>
            {"AQDD", "AQ DD", "AQ DD", "AQD D", "AQ D D"}, // Tra thong tin doi dau cua doi bong/Giai <DEC DD maDoi>
            {"AQT H", "AQ TH", "AQTH", "AQ T H"}, // Tra thong tin tong hop vong dau/Giai <DEC TH maDoi>
            {"AQDT", "AQ DT", "AQD T", "AQ D T"}, // Tra lich thi dau cua doi bong/Giai <DEC DT maDoi>
            {"AQ WL", "AQWL", "AQW", "AQ W", "AQW", "AQ W", "AQ  W", "AQ V"}, // Tra Y kien chuyen gia/Giai <DEC W maDoi>
            {"AQ CHO", "AQCHO", "AQC HO", "AQ C H O", "AQ CH O"},
            {"AQHOT", "AQ HOT", "AQH OT", "AQ H O T", "AQ H OT"},
            {"AQR", "AQ R", "AQR2", "AQ R2", "AQRL", "AQ RL"},
            {"AQ"}, // Tra KetQua XoSo <DE XSTD [NgayThang]>

            {"FP TIP1", "FPTIP1", "FP VIP1", "FPVIP1", "FP TIP1"},
            {"FP TIP2", "FPTIP2", "FP VIP2", "FPVIP2", "FP TIP2"},
            {"FP TIP", "FPTIP", "FP VIP", "FPVIP", "FP TLP"},
            {"FP XIEN1", "FPXIEN1", "FPXLEN1", "FP XLEN1", "FP SIEN1", "FPSIEN1"},
            {"FP XIEN2", "FPXIEN2", "FPXLEN2", "FP XLEN2", "FP SIEN2", "FPSIEN2"},
            {"FP XIEN", "FPXIEN", "FPXLEN", "FP XLEN", "FP SIEN", "FPSIEN"},
            {"FP TX", "FPTX", "FP T X"},
            {"FP TAI", "FPTAI", "FP TA"},
            {"FP XIU", "FPXIU", "FP XLN", "FP XI"},
            {"FP TL1", "FPTL1", "FPT L1", "FP TILE1"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>
            {"FP TL2", "FPTL2", "FPT L2", "FP TILE2"}, // Tra Ti le ca cuoc <DEC TL maDoi/maGiai>

            {"FP TL", "FPTL", "FPT L", "FP TILE"},
            {"FP KQ1", "FP KQ 1", "FP K Q1", "FPK Q1", "FPQK1", "FP QK1", "FPKQ1"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"FP KQ2", "FP K Q2", "FPK Q2", "FPKQ2", "FPQK2"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"FP KQ", "FP K Q", "FPK Q", "FPKQ", "FPQK", "FP QK"}, // Tra Ket Qua BongDa <DEC KQ maGiai/maDoi>
            {"FPBXH", "FP BXH", "FP BXH", "FPB XH"}, // Tra Bang Xep Hang <DEC BXH maGiai>
            {"FPLTD", "FP LTD", "FP LTD", "FPL TD", "FP L T D"}, // Tra Lich thi dau  <DEC LTD maGiai>
            {"FPVPL", "FP VPL", "FP VPL", "FP V P L", "FP VPL"}, // Tra vua pha luoi <DEC VPL maGiai>
            {"FPDH", "FP DH", "FPD H", "FP D H "}, // Tra Doi hinh xuat phat <DEC DH maDoi>
            {"FPCLB", "FP CLB", "FP CLB", "FPC LB", "FP C L B"}, // Tra ds Cau lac bo cua giai <DEC CLB maGiai>
            {"FPTT", "FP TT", "FP T T", "FP T T", "FP  TT"}, // Tra thong tin truc tiep cua doi bong/Giai <DEC TT maGiai/maDoi>
            {"FPPD", "FP P D", "FPP D"}, // Tra thong tin phong do cua doi bong/Giai <DEC PD maDoi>
            {"FPDD", "FP DD", "FP DD", "FPD D", "FP D D"}, // Tra thong tin doi dau cua doi bong/Giai <DEC DD maDoi>
            {"FPT H", "FP TH", "FPTH", "FP T H"}, // Tra thong tin tong hop vong dau/Giai <DEC TH maDoi>
            {"FPDT", "FP DT", "FPD T", "FP D T"}, // Tra lich thi dau cua doi bong/Giai <DEC DT maDoi>
            {"FP WL", "FPWL", "FPW", "FP W", "FPW", "FP W", "FP V"}, // Tra Y kien chuyen gia/Giai <DEC W maDoi>
            {"FP CHO", "FPCHO", "FPC HO"},
            {"FPHOT", "FP HOT", "FPH OT"},
            {"FP"}
        };
        String[] msgValidCode = {
            "AKTIP1", "AKTIP2", "AKTIP", "AKXIEN1", "AKXIEN2", "AKXIEN", "AKTX", "AKTAI", "AKXIU",
            "AKTL1", "AKTL2", "AKTL", "AKKQ1", "AKKQ2", "AKKQ", "AKBXH", "AKLTD",
            "AKVPL", "AKDH", "AKCLB", "AKTT", "AKPD", "AKDD", "AKTH", "AKDT", "AKW", "AKCHO", "AKHOT", "AKKQ",
            "AQTIP1", "AQTIP2", "AQTIP", "AQXIEN1", "AQXIEN2", "AQXIEN", "AQTX", "AQTAI", "AQXIU",
            "AQTL1", "AQTL2", "AQTL", "AQKQ1", "AQKQ2", "AQKQ", "AQBXH", "AQLTD",
            "AQVPL", "AQDH", "AQCLB", "AQTT", "AQPD", "AQDD", "AQTH", "AQDT", "AQW", "AQCHO", "AQHOT", "AQR", "AQKQ",
            "FPTIP1", "FPTIP2", "FPTIP", "FPXIEN1", "FPXIEN2", "FPXIEN", "FPTX", "FPTAI", "FPXIU",
            "FPTL1", "FPTL2", "FPTL", "FPKQ1", "FPKQ2", "FPKQ", "FPBXH", "FPLTD",
            "FPVPL", "FPDH", "FPCLB", "FPTT", "FPPD", "FPDD", "FPTH", "FPDT", "FPW", "FPCHO", "FPHOT", "FPKQ"
        };
        String sCommandCode = "";
        String sOption = "";
        String sTmp = "";
        try {

            msg = msg.toUpperCase();
            msg = msg.trim();
            boolean flag = false;

            for (int i = 0; i < msgInvCode.length; i++) {
                for (int j = 0; j < msgInvCode[i].length; j++) {
                    if (msg.startsWith(msgInvCode[i][j])) {
                        sCommandCode = msgValidCode[i];
                        sTmp = msgInvCode[i][j];
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            sOption = msg.substring(sTmp.length());
            sOption = sOption.trim();
            //Khong co phan tu dang sau
            if (sOption.equals("-1")) {
                sOption = "";
            }
            str_return = new String[2];
            str_return[0] = sCommandCode;
            str_return[1] = sOption;

        } catch (Exception ex) {
        }

        return str_return;

    }

    public static String[] isSend2Another(String content) {
        String[] sTmp = new String[2];
        if ((content.indexOf("090") > 0
                || content.indexOf("091") > 0
                || content.indexOf("092") > 0
                || content.indexOf("093") > 0
                || content.indexOf("094") > 0
                || content.indexOf("095") > 0
                || content.indexOf("096") > 0
                || content.indexOf("097") > 0
                || content.indexOf("098") > 0)
                && content.length() > 12) {
            int len = content.length();
            sTmp[0] = content.substring(0, len - 10).trim();
            sTmp[1] = content.substring(len - 10).trim();

        } else if ((content.indexOf("090") > 0
                || content.indexOf("8491") > 0
                || content.indexOf("8492") > 0
                || content.indexOf("8493") > 0
                || content.indexOf("8494") > 0
                || content.indexOf("8495") > 0
                || content.indexOf("8496") > 0
                || content.indexOf("8497") > 0
                || content.indexOf("8498") > 0) && content.length() > 13) {

            int len = content.length();
            sTmp[0] = content.substring(0, len - 11).trim();
            sTmp[1] = content.substring(len - 11).trim();

        } else if ((content.indexOf("0161") > 0
                || content.indexOf("0160") > 0
                || content.indexOf("0162") > 0
                || content.indexOf("0163") > 0
                || content.indexOf("0164") > 0
                || content.indexOf("0165") > 0
                || content.indexOf("0166") > 0
                || content.indexOf("0168") > 0
                || content.indexOf("0122") > 0
                || content.indexOf("0123") > 0
                || content.indexOf("0169") > 0
                || content.indexOf("0167") > 0
                || content.indexOf("0126") > 0
                || content.indexOf("0121") > 0
                || content.indexOf("0125") > 0
                || content.indexOf("0120") > 0
                || content.indexOf("0124") > 0
                || content.indexOf("0129") > 0
                || content.indexOf("0128") > 0
                || content.indexOf("0127") > 0) && content.length() > 13) {

            int len = content.length();
            sTmp[0] = content.substring(0, len - 11).trim();
            sTmp[1] = content.substring(len - 11).trim();

        } else if ((content.indexOf("84161") > 0
                || content.indexOf("84160") > 0
                || content.indexOf("84162") > 0
                || content.indexOf("84163") > 0
                || content.indexOf("84164") > 0
                || content.indexOf("84165") > 0
                || content.indexOf("84166") > 0
                || content.indexOf("84168") > 0
                || content.indexOf("84122") > 0
                || content.indexOf("84123") > 0
                || content.indexOf("84169") > 0
                || content.indexOf("84167") > 0
                || content.indexOf("84126") > 0
                || content.indexOf("84121") > 0
                || content.indexOf("84125") > 0
                || content.indexOf("84120") > 0
                || content.indexOf("84124") > 0
                || content.indexOf("84129") > 0
                || content.indexOf("84128") > 0
                || content.indexOf("84127") > 0) && content.length() > 14) {

            int len = content.length();
            sTmp[0] = content.substring(0, len - 12).trim();
            sTmp[1] = content.substring(len - 12).trim();
        } else {
            sTmp[0] = content;
            sTmp[1] = "";
        }
        return sTmp;
    }

    // return null if not a valid mobile operator
    public static String buildMobileOperator(String userId) {
        String mobileOperator = "OTHER";
        if (userId.startsWith("8491") || userId.startsWith("091") || userId.startsWith("91")
                || userId.startsWith("8494") || userId.startsWith("094") || userId.startsWith("94")
                || userId.startsWith("0123") || userId.startsWith("84123") || userId.startsWith("+84123")
                || userId.startsWith("0125") || userId.startsWith("84125") || userId.startsWith("+84125")
                || userId.startsWith("84127") || userId.startsWith("0127") || userId.startsWith("+84127")
                || userId.startsWith("84124") || userId.startsWith("0124") || userId.startsWith("+84124")
                || userId.startsWith("+84129") || userId.startsWith("0129") || userId.startsWith("84129")) {
            mobileOperator = "GPC";
        } else if (userId.startsWith("8490") || userId.startsWith("090") || userId.startsWith("90")
                || userId.startsWith("8493") || userId.startsWith("093") || userId.startsWith("93")
                || userId.startsWith("84122") || userId.startsWith("0122") || userId.startsWith("+84122")
                || userId.startsWith("84121") || userId.startsWith("0121") || userId.startsWith("+84121")
                || userId.startsWith("84126") || userId.startsWith("0126") || userId.startsWith("+84126")
                || userId.startsWith("84120") || userId.startsWith("0120") || userId.startsWith("+84120")
                || userId.startsWith("84128") || userId.startsWith("0128") || userId.startsWith("+84128")) {
            mobileOperator = "VMS";
        } else if (userId.startsWith("8498") || userId.startsWith("098") || userId.startsWith("98")
                || userId.startsWith("8497") || userId.startsWith("097") || userId.startsWith("97")
                || userId.startsWith("0168") || userId.startsWith("84168") || userId.startsWith("+84168")
                || userId.startsWith("0167") || userId.startsWith("84167") || userId.startsWith("+84167")
                || userId.startsWith("0169") || userId.startsWith("84169") || userId.startsWith("+84169")
                || userId.startsWith("0164") || userId.startsWith("84164") || userId.startsWith("+84164")
                || userId.startsWith("0163") || userId.startsWith("84163") || userId.startsWith("+84163")
                || userId.startsWith("0166") || userId.startsWith("+84166") || userId.startsWith("84166")
                || userId.startsWith("0161") || userId.startsWith("84161") || userId.startsWith("+84161")
                || userId.startsWith("0160") || userId.startsWith("84160") || userId.startsWith("+84160")
                || userId.startsWith("84165") || userId.startsWith("+84165") || userId.startsWith("0165")
                || userId.startsWith("84162") || userId.startsWith("+84162") || userId.startsWith("0162")) {
            mobileOperator = "VIETEL";
//        } else if (userId.startsWith("8495") || userId.startsWith("095") || userId.startsWith("95") ||
//                   userId.startsWith("0165") || userId.startsWith("84165")) {
//            mobileOperator = "SFONE";
        } else if (userId.startsWith("8492") || userId.startsWith("092") || userId.startsWith("92")
                || userId.startsWith("84188") || userId.startsWith("0188") || userId.startsWith("188")
                || userId.startsWith("84186") || userId.startsWith("0186") || userId.startsWith("186")) {
            mobileOperator = "HTC";
        } else if (userId.startsWith("8496") || userId.startsWith("096") || userId.startsWith("96")
                || userId.startsWith("8442") || userId.startsWith("042") || userId.startsWith("+8442")) {
            //mobileOperator = "EVN";
            mobileOperator = "VIETEL";
        } else {
            // mobileOperator = "EVN";
            mobileOperator = "VIETEL";
        }
        return mobileOperator;
    }
    //formatNumberPhone :84xxx

    public static String formatNumberPhone(String number) {
        String n = number;
        if (number.startsWith("0")) {
            n = "84" + number.substring(1);
        } else {
            if (number.startsWith("+84")) {
                n = number.substring(1);
            }

        }
        return n;
    }

    public static String getHexString(byte[] buf) {
        StringBuilder strBuffer = new StringBuilder();
        String str = null;
        for (int i = 0; i < buf.length; i++) {
            str = Integer.toHexString(unsignedByte(buf[i])).toUpperCase();
            if (str.length() == 1) {
                str = "0" + str;
            }
            strBuffer.append(str);
        }
        return strBuffer.toString();
    }

    private static int unsignedByte(byte value) {
        if (value < 0) {
            return (value + 256);
        } else {
            return value;
        }
    }
    //////////////////////////////////////////////////////
    //Rebuild msisdn

    public static String rebuildMsisdn9xxx(String msisdn) {
        if (msisdn == null || "".equals(msisdn)) {
            return null;
        }
        if (((msisdn.startsWith("90") || msisdn.startsWith("91") || msisdn.startsWith("94")
                || msisdn.startsWith("98") || msisdn.startsWith("96") || msisdn.startsWith("93")
                || msisdn.startsWith("95") || msisdn.startsWith("97") || msisdn.startsWith("92")
                || msisdn.startsWith("42"))
                && msisdn.length() == 9)) {
            return msisdn;
        } else if ((msisdn.startsWith("090") || msisdn.startsWith("091") || msisdn.startsWith("094")
                || msisdn.startsWith("098") || msisdn.startsWith("096") || msisdn.startsWith("093")
                || msisdn.startsWith("095") || msisdn.startsWith("097") || msisdn.startsWith("092")
                || msisdn.startsWith("042"))
                && msisdn.length() == 10) {
            return msisdn.substring(1);
        } else if ((msisdn.startsWith("8490") || msisdn.startsWith("8491") || msisdn.startsWith("8494")
                || msisdn.startsWith("8498") || msisdn.startsWith("8496") || msisdn.startsWith("8493")
                || msisdn.startsWith("8495") || msisdn.startsWith("8492") || msisdn.startsWith("8497")
                || msisdn.startsWith("8442"))
                && msisdn.length() == 11) {
            return msisdn.substring(2);
        } else if ((msisdn.startsWith("0160") || msisdn.startsWith("0161") || msisdn.startsWith("0162")
                || msisdn.startsWith("0163") || msisdn.startsWith("0164")
                || msisdn.startsWith("0165") || msisdn.startsWith("0166") || msisdn.startsWith("0167")
                || msisdn.startsWith("0168") || msisdn.startsWith("0169") || msisdn.startsWith("0120")
                || msisdn.startsWith("0122") || msisdn.startsWith("0123") || msisdn.startsWith("0124")
                || msisdn.startsWith("0126") || msisdn.startsWith("0121")
                || msisdn.startsWith("0125") || msisdn.startsWith("0127") || msisdn.startsWith("0128")
                || msisdn.startsWith("0129") || msisdn.startsWith("0186") || msisdn.startsWith("0188"))
                && msisdn.length() == 11) {
            return msisdn.substring(1);
        } else if ((msisdn.startsWith("84160") || msisdn.startsWith("84161") || msisdn.startsWith("84162")
                || msisdn.startsWith("84163") || msisdn.startsWith("84164")
                || msisdn.startsWith("84165") || msisdn.startsWith("84166") || msisdn.startsWith("84167")
                || msisdn.startsWith("84168") || msisdn.startsWith("84169") || msisdn.startsWith("84120")
                || msisdn.startsWith("84122") || msisdn.startsWith("84123") || msisdn.startsWith("84124")
                || msisdn.startsWith("84126") || msisdn.startsWith("84121")
                || msisdn.startsWith("84125") || msisdn.startsWith("84127") || msisdn.startsWith("84128")
                || msisdn.startsWith("84129") || msisdn.startsWith("84186") || msisdn.startsWith("84188"))
                && msisdn.length() == 12) {
            return msisdn.substring(2);
        } else {
            return null;
        }
    }

    public static Collection splitLongMsg(String arg) {
        String[] result = new String[3];
        ArrayList v = new ArrayList();
        int segment = 0;

        if (arg.length() <= 160) {
            result[0] = arg;
            v.add(result[0]);
            return v;
        } else {
            segment = 160;
        }

        StringTokenizer tk = new StringTokenizer(arg, " ");
        String temp = "";
        int j = 0;

        while (tk.hasMoreElements()) {
            String token = (String) tk.nextElement();
            if (temp.equals("")) {
                temp = temp + token;
            } else {
                temp = temp + " " + token;
            }

            if (temp.length() > segment) {
                temp = token;
                j++;
            } else {
                result[j] = temp;
            }

            if (j == 3) {
                break;
            }
        }

        for (String result1 : result) {
            if (result1 != null) {
                v.add(result1);
            }
        }

        return v;
    }

    public static String[] split(String arg) {
        String[] result = new String[3];
        int segment = 0;
        if (arg.length() <= 160) {
            result[0] = arg;
            return result;
        } else {
            segment = 160;
        }
        StringTokenizer tk = new StringTokenizer(arg, " ");
        String temp = "";
        int j = 0;

        while (tk.hasMoreElements()) {
            String token = (String) tk.nextElement();
            if (temp.equals("")) {
                temp = temp + token;
            } else {
                temp = temp + " " + token;
            }

            if (temp.length() > segment) {
                temp = token;
                j++;
            } else {
                result[j] = temp;
            }

            if (j == 3) {
                break;
            }
        }

        return result;
    }

    public static boolean checkVinaphone(String userId) {
        if (userId.startsWith("091")
                || userId.startsWith("8491")
                || userId.startsWith("91")
                || userId.startsWith("94")
                || userId.startsWith("8494")
                || userId.startsWith("094")
                || userId.startsWith("+8491")
                || userId.startsWith("0123")
                || userId.startsWith("84123")
                || userId.startsWith("123")
                || userId.startsWith("+84123")
                || userId.startsWith("0124")
                || userId.startsWith("84124")
                || userId.startsWith("124")
                || userId.startsWith("+84124")
                || userId.startsWith("0125")
                || userId.startsWith("84125")
                || userId.startsWith("125")
                || userId.startsWith("+84125")
                || userId.startsWith("0127")
                || userId.startsWith("84127")
                || userId.startsWith("127")
                || userId.startsWith("+84127")
                || userId.startsWith("0129")
                || userId.startsWith("84129")
                || userId.startsWith("129")
                || userId.startsWith("+84129")) {
            return false;
        } else {
            return false;
        }
    }

    public static boolean checkMobilePhone(String userId) {
        if (userId.startsWith("090")
                || userId.startsWith("8490")
                || userId.startsWith("90")
                || userId.startsWith("93")
                || userId.startsWith("8493")
                || userId.startsWith("093")
                || userId.startsWith("+8490")
                || userId.startsWith("+8493")
                || userId.startsWith("84122")
                || userId.startsWith("+84122")
                || userId.startsWith("0122")
                || userId.startsWith("0126")
                || userId.startsWith("84126")
                || userId.startsWith("+84126")
                || userId.startsWith("0121")
                || userId.startsWith("84121")
                || userId.startsWith("+84121")
                || userId.startsWith("0120")
                || userId.startsWith("84120")
                || userId.startsWith("+84120")
                || userId.startsWith("0128")
                || userId.startsWith("84128")
                || userId.startsWith("+84128")) {
            return false;
        } else {
            return false;
        }
    }

    public static boolean checkNCVietel(String userId) {
        return userId.startsWith("098")
                || userId.startsWith("8498")
                || userId.startsWith("98")
                || userId.startsWith("97")
                || userId.startsWith("8497")
                || userId.startsWith("097")
                || userId.startsWith("+8498")
                || userId.startsWith("+8497")
                || userId.startsWith("84168")
                || userId.startsWith("+84168")
                || userId.startsWith("0168")
                || userId.startsWith("0169")
                || userId.startsWith("84169")
                || userId.startsWith("+84169")
                || userId.startsWith("0166")
                || userId.startsWith("84166")
                || userId.startsWith("+84166")
                || userId.startsWith("0167")
                || userId.startsWith("84167")
                || userId.startsWith("+84167")
                || userId.startsWith("0165")
                || userId.startsWith("84165")
                || userId.startsWith("+84165")
                || userId.startsWith("0164")
                || userId.startsWith("84164")
                || userId.startsWith("+84164")
                || userId.startsWith("0163")
                || userId.startsWith("84163")
                || userId.startsWith("+84163")
                || userId.startsWith("0162")
                || userId.startsWith("84162")
                || userId.startsWith("+84162")
                || userId.startsWith("0161")
                || userId.startsWith("84161")
                || userId.startsWith("+84161")
                || userId.startsWith("0160")
                || userId.startsWith("84160")
                || userId.startsWith("+84160");
    }

    public static boolean checkNCMobilePhone(String userId) {
        return userId.startsWith("090")
                || userId.startsWith("8490")
                || userId.startsWith("90")
                || userId.startsWith("93")
                || userId.startsWith("8493")
                || userId.startsWith("093")
                || userId.startsWith("+8490")
                || userId.startsWith("+8493")
                || userId.startsWith("84122")
                || userId.startsWith("+84122")
                || userId.startsWith("0122")
                || userId.startsWith("0126")
                || userId.startsWith("84126")
                || userId.startsWith("0121")
                || userId.startsWith("84121")
                || userId.startsWith("+84121")
                || userId.startsWith("0120")
                || userId.startsWith("84120")
                || userId.startsWith("+84120")
                || userId.startsWith("0128")
                || userId.startsWith("84128")
                || userId.startsWith("+84128");
    }

    public static boolean checkNCVinaphone(String userId) {
        return userId.startsWith("091")
                || userId.startsWith("8491")
                || userId.startsWith("91")
                || userId.startsWith("94")
                || userId.startsWith("8494")
                || userId.startsWith("094")
                || userId.startsWith("+8491")
                || userId.startsWith("0123")
                || userId.startsWith("84123")
                || userId.startsWith("+84123")
                || userId.startsWith("0125")
                || userId.startsWith("84125")
                || userId.startsWith("125")
                || userId.startsWith("+84125")
                || userId.startsWith("0127")
                || userId.startsWith("84127")
                || userId.startsWith("127")
                || userId.startsWith("+84127")
                || userId.startsWith("84124")
                || userId.startsWith("124")
                || userId.startsWith("+84124")
                || userId.startsWith("0124")
                || userId.startsWith("0129")
                || userId.startsWith("84129")
                || userId.startsWith("129")
                || userId.startsWith("+84129");
    }

    public static String validMessage(String msg) {
        msg = msg.replace('.', ' ');
        msg = msg.replace('_', ' ');
        msg = msg.replace('!', ' ');
        msg = msg.replace('$', ' ');
        msg = msg.replace('#', ' ');
        msg = msg.replace('[', ' ');
        msg = msg.replace(']', ' ');
        msg = msg.replace('(', ' ');
        msg = msg.replace(')', ' ');
        msg = msg.replace(',', ' ');
        msg = msg.replace(';', ' ');
        msg = msg.replace('"', ' ');
        msg = msg.replace('\'', ' ');
        msg = msg.replace('\\', ' ');
        msg = msg.replace('/', ' ');
        msg = msg.replace('%', ' ');
        msg = msg.replace('+', ' ');
        msg = msg.replace('-', ' ');
        msg = msg.replace('<', ' ');
        msg = msg.replace('>', ' ');
        msg = msg.replace('@', ' ');
        msg = msg.replace(':', ' ');
        msg = msg.replace('=', ' ');
        msg = msg.replace('?', ' ');
        msg = msg.replace('o', '0');
        msg = msg.replace('O', '0');
        msg = msg.replace('I', '1');
        msg = msg.trim();
        msg = msg.toUpperCase();

        StringTokenizer tk = new StringTokenizer(msg, " ");
        msg = "";
        while (tk.hasMoreTokens()) {
            String sTmp = (String) tk.nextToken();
            if (!msg.equals("")) {
                msg += " " + sTmp;
            } else {
                msg += sTmp;
            }
        }

        return msg;
    }

    public static String validMessageBoi(String msg) {
        msg = msg.replace('.', ' ');
        msg = msg.replace('_', ' ');
        msg = msg.replace('!', ' ');
        msg = msg.replace('$', ' ');
        msg = msg.replace('#', ' ');
        msg = msg.replace('[', ' ');
        msg = msg.replace(']', ' ');
        msg = msg.replace('(', ' ');
        msg = msg.replace(')', ' ');
        msg = msg.replace(',', ' ');
        msg = msg.replace(';', ' ');
        msg = msg.replace('"', ' ');
        msg = msg.replace('\'', ' ');
        msg = msg.replace('\\', ' ');
        msg = msg.replace('/', ' ');
        msg = msg.replace('%', ' ');
        msg = msg.replace('+', ' ');
        msg = msg.replace('-', ' ');
        msg = msg.replace('<', ' ');
        msg = msg.replace('>', ' ');
        msg = msg.replace('@', ' ');
        msg = msg.replace(':', ' ');
        msg = msg.replace('=', ' ');
        msg = msg.replace('?', ' ');
        //msg = msg.replace('o', '0');
        //msg = msg.replace('O', '0');

//        msg = msg.replace('I', '1');
        msg = msg.trim();
        msg = msg.toUpperCase();

        StringTokenizer tk = new StringTokenizer(msg, " ");
        msg = "";
        while (tk.hasMoreTokens()) {
            String sTmp = (String) tk.nextToken();
            if (!msg.equals("")) {
                msg += " " + sTmp;
            } else {
                msg += sTmp;
            }
        }

        return msg;
    }

    public static String[] parseOption(String sOption) {
        String[] sTmp = {"", ""};
        if (sOption == null || "".equals(sOption)) {
            return sTmp;
        }
        sOption = sOption.trim();
        Collection cTmp = parseString(sOption);
        if (cTmp != null && cTmp.size() > 0) {
            int i = 0;
            for (Iterator it = cTmp.iterator(); it.hasNext(); i++) {
                sTmp[i] = (String) it.next();
                if (i == 1) {
                    break;
                }
            }
        }
        return sTmp;
    }

    public static String validatePhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.equals("")) {
            return "";
        }
        try {
            if (phoneNumber.startsWith("84") && phoneNumber.length() > 2) {
                phoneNumber = "0" + phoneNumber.substring(2);
            } else if (phoneNumber.startsWith("+84") && phoneNumber.length() > 3) {
                phoneNumber = "0" + phoneNumber.substring(3);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return phoneNumber;
    }

    public static String sumNick(String nick) {
        if (nick == null || "".equals(nick)) {
            return null;
        }
        nick = nick.trim();
        int sum = 0;
        if (nick.length() < 2 && isNumberic(nick)) {
            return nick;
        }
        nick = nick.toUpperCase();
        for (int i = 0; i < nick.length(); i++) {
            char ch = nick.charAt(i);
            if (ch == 'A' || ch == 'J' || ch == 'S') {
                sum += 1;
            } else if (ch == 'B' || ch == 'K' || ch == 'T') {
                sum += 2;
            } else if (ch == 'C' || ch == 'L' || ch == 'U') {
                sum += 3;
            } else if (ch == 'D' || ch == 'M' || ch == 'V') {
                sum += 4;
            } else if (ch == 'E' || ch == 'N' || ch == 'W') {
                sum += 5;
            } else if (ch == 'F' || ch == 'O' || ch == 'X') {
                sum += 6;
            } else if (ch == 'G' || ch == 'P' || ch == 'Y') {
                sum += 7;
            } else if (ch == 'H' || ch == 'Q' || ch == 'Z') {
                sum += 8;
            } else if (ch == 'I' || ch == 'R') {
                sum += 9;
            }
        }
        String sTmp = "" + sum;
        sum = 0;
        while (sTmp.length() != 1) {
            int iTmp = 0;
            for (int i = 0; i < sTmp.length(); i++) {
                char temp = sTmp.charAt(i);
                if (Character.isDigit(temp)) {
                    iTmp += Integer.parseInt(String.valueOf(temp));
                }
            }
            sTmp = String.valueOf(iTmp);
        }
        return sTmp;
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

    public static double getTimer() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);
        double realTime = (double) h + (double) m / 60;
        return realTime;
    }

}
