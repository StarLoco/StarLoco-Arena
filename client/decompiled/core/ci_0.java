/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Renamed from Ci
 */
public class ci_0
implements Iterator {
    private List fB = new ArrayList(2);
    private akz_0 aLn = null;
    private int fD = 0;

    public ci_0() {
    }

    public ci_0(akz_0 ... akz_0Array) {
        for (akz_0 akz_02 : akz_0Array) {
            this.fB.add(akz_02);
        }
        if (this.aLn == null && akz_0Array.length > 0) {
            this.aLn = akz_0Array[0];
        }
    }

    public ci_0(akz_0 akz_02) {
        this.fB.add(akz_02);
        this.aLn = akz_02;
    }

    public ci_0(akz_0 akz_02, akz_0 akz_03) {
        this.fB.add(akz_02);
        this.fB.add(akz_03);
        this.aLn = akz_02;
    }

    public void a(akz_0 akz_02) {
        this.fB.add(akz_02);
        if (this.aLn == null) {
            this.aLn = akz_02;
        }
    }

    public boolean hasNext() {
        return this.aLn != null && this.aLn.hasNext();
    }

    public Object next() {
        this.aLn.fK();
        Object object = this.aLn.value();
        if (!this.aLn.hasNext()) {
            while (!this.aLn.hasNext()) {
                ++this.fD;
                if (this.fD >= this.fB.size()) {
                    this.aLn = null;
                    break;
                }
                this.aLn = (akz_0)this.fB.get(this.fD);
            }
        }
        return object;
    }

    public void remove() {
    }
}

