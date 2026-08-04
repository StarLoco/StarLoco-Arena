/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from Sl
 */
public abstract class sl_2
extends hx_1 {
    private static final ga_2 xa = ga_2.Qo();
    protected File ahu = null;
    protected rh_0 ahv = null;
    protected yx_2 ahw = null;
    protected int bLh = (int)xa.Qp();

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
            throw new eq_2("Invalid destination file results for " + this.ahu.getName() + " with filename " + string);
        }
        String string2 = stringArray[0];
        File file3 = new File(this.ahu, string2);
        boolean bl2 = this.i(file2, file3);
        return bl2;
    }

    protected abstract boolean i(File var1, File var2);

    public void hI(int n2) {
        this.bLh = n2;
    }
}

