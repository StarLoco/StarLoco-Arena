/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Enumeration;

/*
 * Renamed from Gl
 */
public class gl_0
extends aen_2 {
    private boolean baI = true;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.lp()) {
            stringBuffer.append("{majorityselect: ");
            stringBuffer.append(super.toString());
            stringBuffer.append("}");
        }
        return stringBuffer.toString();
    }

    public void br(boolean bl2) {
        this.baI = bl2;
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        int n2 = 0;
        int n3 = 0;
        Enumeration enumeration = this.lr();
        while (enumeration.hasMoreElements()) {
            boolean bl2 = ((R)enumeration.nextElement()).a(file, string, file2);
            if (bl2) {
                ++n2;
                continue;
            }
            ++n3;
        }
        if (n2 > n3) {
            return true;
        }
        if (n3 > n2) {
            return false;
        }
        return this.baI;
    }
}

