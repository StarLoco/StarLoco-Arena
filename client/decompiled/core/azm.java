/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Enumeration;

public class azm
extends aen_2 {
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.lp()) {
            stringBuffer.append("{orselect: ");
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
            if (!bl2) continue;
            return true;
        }
        return false;
    }
}

