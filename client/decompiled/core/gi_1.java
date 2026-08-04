/*
 * Decompiled with CFR 0.152.
 */
import java.io.Reader;

/*
 * Renamed from Gi
 */
public class gi_1
extends aat_0
implements aDa,
gx_2 {
    private String baH = "";

    public void dU(String string) {
        this.baH = tD.cs(string);
    }

    public String dV(String string) {
        StringBuffer stringBuffer = new StringBuffer(string.length());
        for (int j = 0; j < string.length(); ++j) {
            char c = string.charAt(j);
            if (this.d(c)) continue;
            stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }

    public Reader b(Reader reader) {
        return new ady_1(this, reader);
    }

    private boolean d(char c) {
        for (int j = 0; j < this.baH.length(); ++j) {
            if (this.baH.charAt(j) != c) continue;
            return true;
        }
        return false;
    }

    static boolean a(gi_1 gi_12, char c) {
        return gi_12.d(c);
    }
}

