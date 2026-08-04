/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from cU
 */
public class cu_1
extends aat_0
implements abe_0 {
    private static final int ke = -2;
    private String kf = "";
    private int kg = -2;
    private char[] kh = null;
    private boolean ki = false;
    private boolean kj = false;
    private boolean kk = false;

    public void E(String string) {
        this.kh = ayM.cs(string).toCharArray();
    }

    public void v(boolean bl2) {
        this.ki = bl2;
    }

    public void w(boolean bl2) {
        this.kj = bl2;
    }

    public void x(boolean bl2) {
        this.kk = bl2;
    }

    public String a(Reader reader) {
        int n2 = -1;
        if (this.kg != -2) {
            n2 = this.kg;
            this.kg = -2;
        } else {
            n2 = reader.read();
        }
        if (n2 == -1) {
            return null;
        }
        boolean bl2 = true;
        this.kf = "";
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        while (n2 != -1) {
            char c = (char)n2;
            boolean bl3 = this.b(c);
            if (bl2) {
                if (bl3) {
                    if (this.ki) {
                        if (stringBuffer.length() == 0) {
                            stringBuffer.append(c);
                            break;
                        }
                        this.kg = n2;
                        break;
                    }
                    stringBuffer2.append(c);
                    bl2 = false;
                } else {
                    stringBuffer.append(c);
                }
            } else if (bl3) {
                stringBuffer2.append(c);
            } else {
                this.kg = n2;
                break;
            }
            n2 = reader.read();
        }
        this.kf = stringBuffer2.toString();
        if (this.kk) {
            stringBuffer.append(this.kf);
        }
        return stringBuffer.toString();
    }

    public String aR() {
        return this.kj || this.kk ? "" : this.kf;
    }

    private boolean b(char c) {
        if (this.kh == null) {
            return Character.isWhitespace(c);
        }
        for (int j = 0; j < this.kh.length; ++j) {
            if (this.kh[j] != c) continue;
            return true;
        }
        return false;
    }
}

