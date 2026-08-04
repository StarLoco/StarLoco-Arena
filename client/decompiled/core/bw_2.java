/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Enumeration;

/*
 * Renamed from BW
 */
public class bw_2
extends aen_2 {
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.lp()) {
            stringBuffer.append("{andselect: ");
            stringBuffer.append(super.toString());
            stringBuffer.append("}");
        }
        return stringBuffer.toString();
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        Enumeration enumeration = this.lr();
        while (enumeration.hasMoreElements()) {
            boolean bl2 = ((R)enumeration.nextElement()).a(file, string, file2);
            if (bl2) continue;
            return false;
        }
        return true;
    }
}

