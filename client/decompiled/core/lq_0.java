/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.List;

/*
 * Renamed from lQ
 */
class lq_0 {
    private static final char If = '\\';
    private static final char Fz = '%';
    private static final char Ig = '(';
    private static final char Ih = ')';
    private static final char Ii = '{';
    private static final char Ij = '}';
    private static final int Ik = 0;
    private static final int Il = 1;
    private static final int Im = 2;
    private static final int In = 3;
    final String pattern;
    final int patternLength;
    final ahs_2 Io;
    int state = 0;
    int Ip = 0;

    lq_0(String string) {
        this(string, new afe_0());
    }

    lq_0(String string, ahs_2 ahs_22) {
        if (string == null) {
            throw new NullPointerException("null pattern string not allowed");
        }
        this.pattern = string;
        this.patternLength = string.length();
        this.Io = ahs_22;
    }

    List qH() {
        ArrayList<ln_0> arrayList = new ArrayList<ln_0>();
        StringBuffer stringBuffer = new StringBuffer();
        while (this.Ip < this.patternLength) {
            char c = this.pattern.charAt(this.Ip);
            ++this.Ip;
            block0 : switch (this.state) {
                case 0: {
                    switch (c) {
                        case '\\': {
                            this.a("%()", stringBuffer);
                            break block0;
                        }
                        case '%': {
                            this.a(1000, stringBuffer, arrayList);
                            arrayList.add(ln_0.bqu);
                            this.state = 1;
                            break block0;
                        }
                        case ')': {
                            if (stringBuffer.length() >= 1 && stringBuffer.charAt(stringBuffer.length() - 1) == '\\') {
                                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                                stringBuffer.append(')');
                                break block0;
                            }
                            this.a(1000, stringBuffer, arrayList);
                            arrayList.add(ln_0.bqs);
                            break block0;
                        }
                    }
                    stringBuffer.append(c);
                    break;
                }
                case 1: {
                    if (c == '(') {
                        this.a(1002, stringBuffer, arrayList);
                        arrayList.add(ln_0.bqt);
                        this.state = 0;
                        break;
                    }
                    if (Character.isJavaIdentifierStart(c)) {
                        this.a(1002, stringBuffer, arrayList);
                        this.state = 2;
                        stringBuffer.append(c);
                        break;
                    }
                    stringBuffer.append(c);
                    break;
                }
                case 3: {
                    switch (c) {
                        case '}': {
                            this.a(1006, stringBuffer, arrayList);
                            this.state = 0;
                            break block0;
                        }
                        case '\\': {
                            this.a("%{}", stringBuffer);
                            break block0;
                        }
                    }
                    stringBuffer.append(c);
                    break;
                }
                case 2: {
                    if (c == '{') {
                        this.a(1004, stringBuffer, arrayList);
                        this.state = 3;
                        break;
                    }
                    if (Character.isJavaIdentifierPart(c)) {
                        stringBuffer.append(c);
                        break;
                    }
                    if (c == '%') {
                        this.a(1004, stringBuffer, arrayList);
                        arrayList.add(ln_0.bqu);
                        this.state = 1;
                        break;
                    }
                    this.a(1004, stringBuffer, arrayList);
                    if (c == ')') {
                        arrayList.add(ln_0.bqs);
                    } else if (c == '\\') {
                        if (this.Ip < this.patternLength) {
                            char c2 = this.pattern.charAt(this.Ip++);
                            this.Io.a("%()", stringBuffer, c2, this.Ip);
                        }
                    } else {
                        stringBuffer.append(c);
                    }
                    this.state = 0;
                    break;
                }
            }
        }
        switch (this.state) {
            case 0: {
                this.a(1000, stringBuffer, arrayList);
                break;
            }
            case 2: {
                arrayList.add(new ln_0(1004, stringBuffer.toString()));
                stringBuffer.setLength(0);
                break;
            }
            case 1: 
            case 3: {
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

    private void a(int n2, StringBuffer stringBuffer, List list) {
        if (stringBuffer.length() > 0) {
            list.add(new ln_0(n2, stringBuffer.toString()));
            stringBuffer.setLength(0);
        }
    }
}

