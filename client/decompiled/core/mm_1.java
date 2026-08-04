/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from Mm
 */
public class mm_1 {
    private static final int btm = 0;
    private static final int btn = 1;
    private static final int bto = 2;
    private static final char If = '\\';
    private static final char btp = ',';
    private static final char btq = '\"';
    private static final char btr = '\'';
    final String pattern;
    final int patternLength;
    final ahs_2 Io;
    char quoteChar;
    int Ip = 0;
    int state = 0;

    mm_1(String string) {
        this(string, new afe_0());
    }

    mm_1(String string, ahs_2 ahs_22) {
        this.pattern = string;
        this.patternLength = string.length();
        this.Io = ahs_22;
    }

    List qH() {
        ArrayList<String> arrayList = new ArrayList<String>();
        StringBuffer stringBuffer = new StringBuffer();
        while (this.Ip < this.patternLength) {
            char c = this.pattern.charAt(this.Ip);
            ++this.Ip;
            block0 : switch (this.state) {
                case 0: {
                    switch (c) {
                        case '\t': 
                        case '\n': 
                        case '\r': 
                        case ' ': {
                            break block0;
                        }
                        case '\"': 
                        case '\'': {
                            this.state = 2;
                            this.quoteChar = c;
                            break block0;
                        }
                    }
                    stringBuffer.append(c);
                    this.state = 1;
                    break;
                }
                case 1: {
                    switch (c) {
                        case ',': {
                            arrayList.add(stringBuffer.toString().trim());
                            stringBuffer.setLength(0);
                            this.state = 0;
                            break block0;
                        }
                    }
                    stringBuffer.append(c);
                    break;
                }
                case 2: {
                    if (c == this.quoteChar) {
                        arrayList.add(stringBuffer.toString());
                        stringBuffer.setLength(0);
                        this.state = 0;
                        break;
                    }
                    if (c == '\\') {
                        this.a(String.valueOf(this.quoteChar), stringBuffer);
                        break;
                    }
                    stringBuffer.append(c);
                }
            }
        }
        switch (this.state) {
            case 0: {
                break;
            }
            case 1: {
                arrayList.add(stringBuffer.toString().trim());
                break;
            }
            default: {
                throw new fe("Unexpected end of pattern string");
            }
        }
        return arrayList;
    }

    void a(String string, StringBuffer stringBuffer) {
        if (this.Ip < this.patternLength) {
            char c = this.pattern.charAt(this.Ip++);
            this.Io.a(string, stringBuffer, c, this.Ip);
        }
    }
}

