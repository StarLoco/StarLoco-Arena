/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from aJP
 */
public class ajp_1
extends sl_2 {
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{dependselector targetdir: ");
        if (this.ahu == null) {
            stringBuffer.append("NOT YET SET");
        } else {
            stringBuffer.append(this.ahu.getName());
        }
        stringBuffer.append(" granularity: ");
        stringBuffer.append(this.bLh);
        if (this.ahw != null) {
            stringBuffer.append(" mapper: ");
            stringBuffer.append(this.ahw.toString());
        } else if (this.ahv != null) {
            stringBuffer.append(" mapper: ");
            stringBuffer.append(this.ahv.toString());
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public boolean i(File file, File file2) {
        boolean bl2 = zr_1.a(file, file2, this.bLh);
        return bl2;
    }
}

