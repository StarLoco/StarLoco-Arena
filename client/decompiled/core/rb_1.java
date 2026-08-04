/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from rB
 */
public class rb_1
extends hx_1 {
    private File ahu = null;
    private rh_0 ahv = null;
    private yx_2 ahw = null;
    private boolean ahx = true;

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("{presentselector targetdir: ");
        if (this.ahu == null) {
            stringBuffer.append("NOT YET SET");
        } else {
            stringBuffer.append(this.ahu.getName());
        }
        stringBuffer.append(" present: ");
        if (this.ahx) {
            stringBuffer.append("both");
        } else {
            stringBuffer.append("srconly");
        }
        if (this.ahw != null) {
            stringBuffer.append(this.ahw.toString());
        } else if (this.ahv != null) {
            stringBuffer.append(this.ahv.toString());
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }

    public void l(File file) {
        this.ahu = file;
    }

    public rh_0 xe() {
        if (this.ahv != null) {
            throw new eq_2("Cannot define more than one mapper");
        }
        this.ahv = new rh_0(this.TP());
        return this.ahv;
    }

    public void a(xx_0 xx_02) {
        if (xx_02.getIndex() == 0) {
            this.ahx = false;
        }
    }

    public void dQ() {
        if (this.ahu == null) {
            this.eC("The targetdir attribute is required.");
        }
        this.ahw = this.ahv == null ? new sw() : this.ahv.wl();
        if (this.ahw == null) {
            this.eC("Could not set <mapper> element.");
        }
    }

    public boolean a(File file, String string, File file2) {
        this.validate();
        String[] stringArray = this.ahw.bT(string);
        if (stringArray == null) {
            return false;
        }
        if (stringArray.length != 1 || stringArray[0] == null) {
            throw new eq_2("Invalid destination file results for " + this.ahu + " with filename " + string);
        }
        String string2 = stringArray[0];
        File file3 = new File(this.ahu, string2);
        return file3.exists() == this.ahx;
    }
}

