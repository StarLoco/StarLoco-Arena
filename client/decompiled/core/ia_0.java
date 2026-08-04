/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from iA
 */
public class ia_0
extends aat_0
implements abe_0 {
    private static final int ke = -2;
    private String yv = "";
    private int kg = -2;
    private boolean kk = false;

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
        this.yv = "";
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl2 = false;
        while (n2 != -1) {
            if (!bl2) {
                if (n2 == 13) {
                    bl2 = true;
                } else {
                    if (n2 == 10) {
                        this.yv = "\n";
                        break;
                    }
                    stringBuffer.append((char)n2);
                }
            } else {
                bl2 = false;
                if (n2 == 10) {
                    this.yv = "\r\n";
                    break;
                }
                this.kg = n2;
                this.yv = "\r";
                break;
            }
            n2 = reader.read();
        }
        if (n2 == -1 && bl2) {
            this.yv = "\r";
        }
        if (this.kk) {
            stringBuffer.append(this.yv);
        }
        return stringBuffer.toString();
    }

    public String aR() {
        if (this.kk) {
            return "";
        }
        return this.yv;
    }
}

