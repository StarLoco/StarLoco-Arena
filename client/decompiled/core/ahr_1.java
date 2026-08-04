/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 * Renamed from ahr
 */
public class ahr_1 {
    public static final Integer cvt;
    public static final Long cvu;
    private static final boolean DEBUG = false;
    private String bnj;
    private Reader in;
    private int cvv = -1;
    private boolean cvw = false;
    private short cvx;
    private short cvy;
    private aFA cvz = new re_2(this);
    private aFA cvA;
    private short cvB;
    private short cvC;
    private String cvD = null;
    private static final Map cvE;
    private static final Map cvF;
    private aeo_1 aEE = null;

    public ahr_1(String string) {
        this(string, new FileInputStream(string));
    }

    public ahr_1(String string, String string2) {
        this(string, new FileInputStream(string), string2);
    }

    public ahr_1(File file) {
        this(file.getAbsolutePath(), new FileInputStream(file), null);
    }

    public ahr_1(File file, String string) {
        this(file.getAbsolutePath(), new FileInputStream(file), string);
    }

    public ahr_1(String string, InputStream inputStream) {
        this(string, new InputStreamReader(inputStream), 1, 0);
    }

    public ahr_1(String string, InputStream inputStream, String string2) {
        this(string, string2 == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, string2), 1, 0);
    }

    public ahr_1(String string, Reader reader) {
        this(string, reader, 1, 0);
    }

    public ahr_1(String string, Reader reader, short s, short s2) {
        if (string == null && Boolean.getBoolean("org.codehaus.janino.source_debugging.enable")) {
            String string2 = System.getProperty("org.codehaus.janino.source_debugging.dir");
            File file = string2 == null ? null : new File(string2);
            File file2 = File.createTempFile("janino", ".java", file);
            file2.deleteOnExit();
            reader = new iv_2(reader, new FileWriter(file2), true);
            string = file2.getAbsolutePath();
        }
        this.bnj = string;
        this.in = new axP(reader);
        this.cvx = s;
        this.cvy = s2;
        this.axd();
        this.cvz = this.axb();
        this.cvA = null;
    }

    public String getFileName() {
        return this.bnj;
    }

    public void close() {
        this.in.close();
    }

    public aFA awX() {
        aFA aFA2 = this.cvz;
        if (this.cvA != null) {
            this.cvz = this.cvA;
            this.cvA = null;
        } else {
            this.cvz = this.axb();
        }
        return aFA2;
    }

    public aFA awY() {
        return this.cvz;
    }

    public aFA awZ() {
        if (this.cvA == null) {
            this.cvA = this.axb();
        }
        return this.cvA;
    }

    public String axa() {
        String string = this.cvD;
        this.cvD = null;
        return string;
    }

    public lc_0 RR() {
        return this.cvz.aP();
    }

    public static String aA(Object object) {
        if (object instanceof String) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('\"');
            String string = (String)object;
            for (int j = 0; j < string.length(); ++j) {
                char c = string.charAt(j);
                if (c == '\"') {
                    stringBuffer.append("\\\"");
                    continue;
                }
                ahr_1.a(c, stringBuffer);
            }
            stringBuffer.append('\"');
            return stringBuffer.toString();
        }
        if (object instanceof Character) {
            char c = ((Character)object).charValue();
            if (c == '\'') {
                return "'\\''";
            }
            StringBuffer stringBuffer = new StringBuffer("'");
            ahr_1.a(c, stringBuffer);
            return stringBuffer.append('\'').toString();
        }
        if (object instanceof Integer) {
            if (object == cvt) {
                return "2147483648";
            }
            int n2 = (Integer)object;
            return n2 < 0 ? "0x" + Integer.toHexString(n2) : Integer.toString(n2);
        }
        if (object instanceof Long) {
            if (object == cvu) {
                return "9223372036854775808L";
            }
            long l2 = (Long)object;
            return l2 < 0L ? "0x" + Long.toHexString(l2) + 'L' : Long.toString(l2) + 'L';
        }
        if (object instanceof Float) {
            return object.toString() + 'F';
        }
        if (object instanceof Double) {
            return object.toString() + 'D';
        }
        if (object instanceof Boolean) {
            return object.toString();
        }
        if (object instanceof Byte) {
            return "((byte)" + object.toString() + ")";
        }
        if (object instanceof Short) {
            return "((short)" + object.toString() + ")";
        }
        if (object == null) {
            return "null";
        }
        throw new aHY("Unexpected value type \"" + object.getClass().getName() + "\"");
    }

    private static void a(char c, StringBuffer stringBuffer) {
        int n2 = "\b\t\n\f\r\\".indexOf(c);
        if (n2 != -1) {
            stringBuffer.append('\\').append("btnfr\\".charAt(n2));
        } else if (c >= ' ' && c < '\u00ff' && c != '\u007f') {
            stringBuffer.append(c);
        } else {
            stringBuffer.append("\\u");
            String string = Integer.toHexString(0xFFFF & c);
            for (int j = string.length(); j < 4; ++j) {
                stringBuffer.append('0');
            }
            stringBuffer.append(string);
        }
    }

    private aFA axb() {
        if (this.cvD != null) {
            this.a("MDC", "Misplaced doc comment", this.cvz.aP());
            this.cvD = null;
        }
        int n2 = 0;
        StringBuffer stringBuffer = null;
        block13: while (true) {
            switch (n2) {
                case 0: {
                    if (this.cvv == -1) {
                        return new ail_2(this);
                    }
                    if (Character.isWhitespace((char)this.cvv)) break;
                    if (this.cvv != 47) break block13;
                    n2 = 1;
                    break;
                }
                case 1: {
                    if (this.cvv == -1) {
                        return new aev_0(this, "/");
                    }
                    if (this.cvv == 61) {
                        this.axd();
                        return new aev_0(this, "/=");
                    }
                    if (this.cvv == 47) {
                        n2 = 2;
                        break;
                    }
                    if (this.cvv == 42) {
                        n2 = 3;
                        break;
                    }
                    return new aev_0(this, "/");
                }
                case 2: {
                    if (this.cvv == -1) {
                        return new ail_2(this);
                    }
                    if (this.cvv != 13 && this.cvv != 10) break;
                    n2 = 0;
                    break;
                }
                case 3: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in traditional comment", this.RR());
                    }
                    if (this.cvv == 42) {
                        n2 = 4;
                        break;
                    }
                    n2 = 9;
                    break;
                }
                case 4: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in doc comment", this.RR());
                    }
                    if (this.cvv == 47) {
                        n2 = 0;
                        break;
                    }
                    if (this.cvD != null) {
                        this.a("MDC", "Multiple doc comments", new lc_0(this.bnj, this.cvx, this.cvy));
                    }
                    stringBuffer = new StringBuffer();
                    stringBuffer.append((char)this.cvv);
                    n2 = this.cvv == 13 || this.cvv == 10 ? 6 : (this.cvv == 42 ? 8 : 5);
                    break;
                }
                case 5: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in doc comment", this.RR());
                    }
                    if (this.cvv == 42) {
                        n2 = 8;
                        break;
                    }
                    if (this.cvv == 13 || this.cvv == 10) {
                        stringBuffer.append((char)this.cvv);
                        n2 = 6;
                        break;
                    }
                    stringBuffer.append((char)this.cvv);
                    break;
                }
                case 6: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in doc comment", this.RR());
                    }
                    if (this.cvv == 42) {
                        n2 = 7;
                        break;
                    }
                    if (this.cvv == 13 || this.cvv == 10) {
                        stringBuffer.append((char)this.cvv);
                        break;
                    }
                    if (this.cvv == 32 || this.cvv == 9) break;
                    stringBuffer.append((char)this.cvv);
                    n2 = 5;
                    break;
                }
                case 7: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in doc comment", this.RR());
                    }
                    if (this.cvv == 42) break;
                    if (this.cvv == 47) {
                        this.cvD = stringBuffer.toString();
                        n2 = 0;
                        break;
                    }
                    stringBuffer.append((char)this.cvv);
                    n2 = 5;
                    break;
                }
                case 8: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in doc comment", this.RR());
                    }
                    if (this.cvv == 47) {
                        this.cvD = stringBuffer.toString();
                        n2 = 0;
                        break;
                    }
                    if (this.cvv == 42) {
                        stringBuffer.append('*');
                        break;
                    }
                    stringBuffer.append('*');
                    stringBuffer.append((char)this.cvv);
                    n2 = 5;
                    break;
                }
                case 9: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in traditional comment", this.RR());
                    }
                    if (this.cvv != 42) break;
                    n2 = 10;
                    break;
                }
                case 10: {
                    if (this.cvv == -1) {
                        throw new ajy_2("EOF in traditional comment", this.RR());
                    }
                    if (this.cvv == 47) {
                        n2 = 0;
                        break;
                    }
                    if (this.cvv == 42) break;
                    n2 = 9;
                    break;
                }
                default: {
                    throw new aHY(Integer.toString(n2));
                }
            }
            this.axd();
        }
        this.cvB = this.cvx;
        this.cvC = this.cvy;
        if (Character.isJavaIdentifierStart((char)this.cvv)) {
            StringBuffer stringBuffer2 = new StringBuffer();
            stringBuffer2.append((char)this.cvv);
            while (true) {
                this.axd();
                if (this.cvv == -1 || !Character.isJavaIdentifierPart((char)this.cvv)) break;
                stringBuffer2.append((char)this.cvv);
            }
            String string = stringBuffer2.toString();
            if (string.equals("true")) {
                return new nh_1(this, Boolean.TRUE);
            }
            if (string.equals("false")) {
                return new nh_1(this, Boolean.FALSE);
            }
            if (string.equals("null")) {
                return new nh_1(this, (Object)null);
            }
            String string2 = (String)cvE.get(string);
            if (string2 != null) {
                return new ec_2(this, string2);
            }
            return new it_1(this, string);
        }
        if (Character.isDigit((char)this.cvv)) {
            return this.kI(0);
        }
        if (this.cvv == 46) {
            this.axd();
            if (Character.isDigit((char)this.cvv)) {
                return this.kI(2);
            }
            return new aev_0(this, ".");
        }
        if (this.cvv == 34) {
            StringBuffer stringBuffer3 = new StringBuffer("");
            this.axd();
            if (this.cvv == -1) {
                throw new ajy_2("EOF in string literal", this.RR());
            }
            if (this.cvv == 13 || this.cvv == 10) {
                throw new ajy_2("Line break in string literal", this.RR());
            }
            while (this.cvv != 34) {
                stringBuffer3.append(this.axc());
            }
            this.axd();
            return new nh_1(this, stringBuffer3.toString());
        }
        if (this.cvv == 39) {
            this.axd();
            if (this.cvv == 39) {
                throw new ajy_2("Single quote must be backslash-escaped in character literal", this.RR());
            }
            char c = this.axc();
            if (this.cvv != 39) {
                throw new ajy_2("Closing single quote missing", this.RR());
            }
            this.axd();
            return new nh_1(this, new Character(c));
        }
        String string = (String)cvF.get(new String(new char[]{(char)this.cvv}));
        if (string != null) {
            while (true) {
                this.axd();
                String string3 = (String)cvF.get(string + (char)this.cvv);
                if (string3 == null) {
                    return new aev_0(this, string);
                }
                string = string3;
            }
        }
        throw new ajy_2("Invalid character input \"" + (char)this.cvv + "\" (character code " + this.cvv + ")", this.RR());
    }

    private aFA kI(int n2) {
        StringBuffer stringBuffer = n2 == 2 ? new StringBuffer("0.") : new StringBuffer();
        int n3 = n2;
        while (true) {
            switch (n3) {
                case 0: {
                    if (this.cvv == 48) {
                        n3 = 6;
                        break;
                    }
                    stringBuffer.append((char)this.cvv);
                    n3 = 1;
                    break;
                }
                case 1: {
                    if (Character.isDigit((char)this.cvv)) {
                        stringBuffer.append((char)this.cvv);
                        break;
                    }
                    if (this.cvv == 108 || this.cvv == 76) {
                        this.axd();
                        return this.w(stringBuffer.toString(), 10);
                    }
                    if (this.cvv == 102 || this.cvv == 70) {
                        this.axd();
                        return this.ie(stringBuffer.toString());
                    }
                    if (this.cvv == 100 || this.cvv == 68) {
                        this.axd();
                        return this.if(stringBuffer.toString());
                    }
                    if (this.cvv == 46) {
                        stringBuffer.append('.');
                        n3 = 2;
                        break;
                    }
                    if (this.cvv == 69 || this.cvv == 101) {
                        stringBuffer.append('E');
                        n3 = 3;
                        break;
                    }
                    return this.v(stringBuffer.toString(), 10);
                }
                case 2: {
                    if (Character.isDigit((char)this.cvv)) {
                        stringBuffer.append((char)this.cvv);
                        break;
                    }
                    if (this.cvv == 101 || this.cvv == 69) {
                        stringBuffer.append('E');
                        n3 = 3;
                        break;
                    }
                    if (this.cvv == 102 || this.cvv == 70) {
                        this.axd();
                        return this.ie(stringBuffer.toString());
                    }
                    if (this.cvv == 100 || this.cvv == 68) {
                        this.axd();
                        return this.if(stringBuffer.toString());
                    }
                    return this.if(stringBuffer.toString());
                }
                case 3: {
                    if (Character.isDigit((char)this.cvv)) {
                        stringBuffer.append((char)this.cvv);
                        n3 = 5;
                        break;
                    }
                    if (this.cvv == 45 || this.cvv == 43) {
                        stringBuffer.append((char)this.cvv);
                        n3 = 4;
                        break;
                    }
                    throw new ajy_2("Exponent missing after \"E\"", this.RR());
                }
                case 4: {
                    if (Character.isDigit((char)this.cvv)) {
                        stringBuffer.append((char)this.cvv);
                        n3 = 5;
                        break;
                    }
                    throw new ajy_2("Exponent missing after \"E\" and sign", this.RR());
                }
                case 5: {
                    if (Character.isDigit((char)this.cvv)) {
                        stringBuffer.append((char)this.cvv);
                        break;
                    }
                    if (this.cvv == 102 || this.cvv == 70) {
                        this.axd();
                        return this.ie(stringBuffer.toString());
                    }
                    if (this.cvv == 100 || this.cvv == 68) {
                        this.axd();
                        return this.if(stringBuffer.toString());
                    }
                    return this.if(stringBuffer.toString());
                }
                case 6: {
                    if ("01234567".indexOf(this.cvv) != -1) {
                        stringBuffer.append((char)this.cvv);
                        n3 = 7;
                        break;
                    }
                    if (this.cvv == 108 || this.cvv == 76) {
                        this.axd();
                        return this.w("0", 10);
                    }
                    if (this.cvv == 102 || this.cvv == 70) {
                        this.axd();
                        return this.ie("0");
                    }
                    if (this.cvv == 100 || this.cvv == 68) {
                        this.axd();
                        return this.if("0");
                    }
                    if (this.cvv == 46) {
                        stringBuffer.append("0.");
                        n3 = 2;
                        break;
                    }
                    if (this.cvv == 69 || this.cvv == 101) {
                        stringBuffer.append('E');
                        n3 = 3;
                        break;
                    }
                    if (this.cvv == 120 || this.cvv == 88) {
                        n3 = 8;
                        break;
                    }
                    return this.v("0", 10);
                }
                case 7: {
                    if ("01234567".indexOf(this.cvv) != -1) {
                        stringBuffer.append((char)this.cvv);
                        break;
                    }
                    if (this.cvv == 56 || this.cvv == 57) {
                        throw new ajy_2("Digit '" + (char)this.cvv + "' not allowed in octal literal", this.RR());
                    }
                    if (this.cvv == 108 || this.cvv == 76) {
                        this.axd();
                        return this.w(stringBuffer.toString(), 8);
                    }
                    return this.v(stringBuffer.toString(), 8);
                }
                case 8: {
                    if (Character.digit((char)this.cvv, 16) != -1) {
                        stringBuffer.append((char)this.cvv);
                        n3 = 9;
                        break;
                    }
                    throw new ajy_2("Hex digit expected after \"0x\"", this.RR());
                }
                case 9: {
                    if (Character.digit((char)this.cvv, 16) != -1) {
                        stringBuffer.append((char)this.cvv);
                        break;
                    }
                    if (this.cvv == 108 || this.cvv == 76) {
                        this.axd();
                        return this.w(stringBuffer.toString(), 16);
                    }
                    return this.v(stringBuffer.toString(), 16);
                }
                default: {
                    throw new aHY(Integer.toString(n3));
                }
            }
            this.axd();
        }
    }

    private nh_1 v(String string, int n2) {
        int n3;
        switch (n2) {
            case 10: {
                if (string.equals("2147483648")) {
                    return new nh_1(this, cvt);
                }
                try {
                    n3 = Integer.parseInt(string);
                    break;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new ajy_2("Value of decimal integer literal \"" + string + "\" is out of range", this.RR());
                }
            }
            case 8: {
                n3 = 0;
                for (int j = 0; j < string.length(); ++j) {
                    if ((n3 & 0xE0000000) != 0) {
                        throw new ajy_2("Value of octal integer literal \"" + string + "\" is out of range", this.RR());
                    }
                    n3 = (n3 << 3) + Character.digit(string.charAt(j), 8);
                }
                break;
            }
            case 16: {
                n3 = 0;
                for (int j = 0; j < string.length(); ++j) {
                    if ((n3 & 0xF0000000) != 0) {
                        throw new ajy_2("Value of hexadecimal integer literal \"" + string + "\" is out of range", this.RR());
                    }
                    n3 = (n3 << 4) + Character.digit(string.charAt(j), 16);
                }
                break;
            }
            default: {
                throw new aHY("Illegal radix " + n2);
            }
        }
        return new nh_1(this, new Integer(n3));
    }

    private nh_1 w(String string, int n2) {
        long l2;
        switch (n2) {
            case 10: {
                if (string.equals("9223372036854775808")) {
                    return new nh_1(this, cvu);
                }
                try {
                    l2 = Long.parseLong(string);
                    break;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new ajy_2("Value of decimal long literal \"" + string + "\" is out of range", this.RR());
                }
            }
            case 8: {
                l2 = 0L;
                for (int j = 0; j < string.length(); ++j) {
                    if ((l2 & 0xE000000000000000L) != 0L) {
                        throw new ajy_2("Value of octal long literal \"" + string + "\" is out of range", this.RR());
                    }
                    l2 = (l2 << 3) + (long)Character.digit(string.charAt(j), 8);
                }
                break;
            }
            case 16: {
                l2 = 0L;
                for (int j = 0; j < string.length(); ++j) {
                    if ((l2 & 0xF000000000000000L) != 0L) {
                        throw new ajy_2("Value of hexadecimal long literal \"" + string + "\" is out of range", this.RR());
                    }
                    l2 = (l2 << 4) + (long)Character.digit(string.charAt(j), 16);
                }
                break;
            }
            default: {
                throw new aHY("Illegal radix " + n2);
            }
        }
        return new nh_1(this, new Long(l2));
    }

    private nh_1 ie(String string) {
        float f;
        try {
            f = Float.parseFloat(string);
        }
        catch (NumberFormatException numberFormatException) {
            throw new aHY("SNO: parsing float literal \"" + string + "\" throws a \"NumberFormatException\"");
        }
        if (Float.isInfinite(f)) {
            throw new ajy_2("Value of float literal \"" + string + "\" is out of range", this.RR());
        }
        if (Float.isNaN(f)) {
            throw new aHY("SNO: parsing float literal \"" + string + "\" results is NaN");
        }
        if (f == 0.0f) {
            for (int j = 0; j < string.length(); ++j) {
                char c = string.charAt(j);
                if ("123456789".indexOf(c) != -1) {
                    throw new ajy_2("Literal \"" + string + "\" is too small to be represented as a float", this.RR());
                }
                if ("0.".indexOf(c) == -1) break;
            }
        }
        return new nh_1(this, new Float(f));
    }

    private nh_1 if(String string) {
        double d;
        try {
            d = Double.parseDouble(string);
        }
        catch (NumberFormatException numberFormatException) {
            throw new aHY("SNO: parsing double literal \"" + string + "\" throws a \"NumberFormatException\"");
        }
        if (Double.isInfinite(d)) {
            throw new ajy_2("Value of double literal \"" + string + "\" is out of range", this.RR());
        }
        if (Double.isNaN(d)) {
            throw new aHY("SNO: parsing double literal \"" + string + "\" results is NaN");
        }
        if (d == 0.0) {
            for (int j = 0; j < string.length(); ++j) {
                char c = string.charAt(j);
                if ("123456789".indexOf(c) != -1) {
                    throw new ajy_2("Literal \"" + string + "\" is too small to be represented as a double", this.RR());
                }
                if ("0.".indexOf(c) == -1) break;
            }
        }
        return new nh_1(this, new Double(d));
    }

    private char axc() {
        if (this.cvv == -1) {
            throw new ajy_2("EOF in character literal", this.RR());
        }
        if (this.cvv == 13 || this.cvv == 10) {
            throw new ajy_2("Line break in literal not allowed", this.RR());
        }
        if (this.cvv != 92) {
            char c = (char)this.cvv;
            this.axd();
            return c;
        }
        this.axd();
        int n2 = "btnfr".indexOf(this.cvv);
        if (n2 != -1) {
            char c = "\b\t\n\f\r".charAt(n2);
            this.axd();
            return c;
        }
        n2 = "01234567".indexOf(this.cvv);
        if (n2 != -1) {
            int n3 = n2;
            this.axd();
            n2 = "01234567".indexOf(this.cvv);
            if (n2 == -1) {
                return (char)n3;
            }
            n3 = 8 * n3 + n2;
            this.axd();
            n2 = "01234567".indexOf(this.cvv);
            if (n2 == -1) {
                return (char)n3;
            }
            if ((n3 = 8 * n3 + n2) > 255) {
                throw new ajy_2("Invalid octal escape", this.RR());
            }
            this.axd();
            return (char)n3;
        }
        char c = (char)this.cvv;
        this.axd();
        return c;
    }

    private void axd() {
        try {
            this.cvv = this.in.read();
        }
        catch (abr_0 abr_02) {
            throw new ajy_2(abr_02.getMessage(), this.RR(), abr_02);
        }
        if (this.cvv == 13) {
            this.cvx = (short)(this.cvx + 1);
            this.cvy = 0;
            this.cvw = true;
        } else if (this.cvv == 10) {
            if (this.cvw) {
                this.cvw = false;
            } else {
                this.cvx = (short)(this.cvx + 1);
                this.cvy = 0;
            }
        } else {
            this.cvy = (short)(this.cvy + 1);
        }
    }

    public void a(aeo_1 aeo_12) {
        this.aEE = aeo_12;
    }

    private void a(String string, String string2, lc_0 lc_02) {
        if (this.aEE != null) {
            this.aEE.b(string, string2, lc_02);
        }
    }

    static String c(ahr_1 ahr_12) {
        return ahr_12.bnj;
    }

    static short d(ahr_1 ahr_12) {
        return ahr_12.cvB;
    }

    static short e(ahr_1 ahr_12) {
        return ahr_12.cvC;
    }

    static {
        int n2;
        cvt = new Integer(Integer.MIN_VALUE);
        cvu = new Long(Long.MIN_VALUE);
        cvE = new HashMap();
        String[] stringArray = new String[]{"abstract", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"};
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            cvE.put(stringArray[n2], stringArray[n2]);
        }
        cvF = new HashMap();
        stringArray = new String[]{"(", ")", "{", "}", "[", "]", ";", ",", ".", "=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||", "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>="};
        for (n2 = 0; n2 < stringArray.length; ++n2) {
            cvF.put(stringArray[n2], stringArray[n2]);
        }
    }
}

