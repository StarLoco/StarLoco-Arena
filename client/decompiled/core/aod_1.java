/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;
import java.util.Iterator;

/*
 * Renamed from aOd
 */
public class aod_1
implements mx_2 {
    private String[] ebB;
    private final bk_2 ebC;

    public aod_1(bk_2 bk_22) {
        this.ebC = bk_22;
    }

    public void d(File file) {
        this.ebB = new String[]{bk_2.r(file.getAbsolutePath())};
    }

    public void setPath(String string) {
        this.ebB = bk_2.a(this.ebC.TP(), string);
    }

    public String[] aXX() {
        return this.ebB;
    }

    public Iterator iterator() {
        return new qf_0(null, this.ebB);
    }

    public boolean dE() {
        return true;
    }

    public int size() {
        return this.ebB == null ? 0 : this.ebB.length;
    }
}

