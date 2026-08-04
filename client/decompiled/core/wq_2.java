/*
 * Decompiled with CFR 0.152.
 */
import java.util.Iterator;

/*
 * Renamed from WQ
 */
public class wq_2
implements Pi {
    private final acy bVJ = new acy();
    private int aW;

    public wq_2(int n2) {
        this.aW = n2;
    }

    public int getId() {
        return this.aW;
    }

    public long iO() {
        return this.aW;
    }

    public void a(xj_0 xj_02) {
        this.bVJ.add(xj_02);
    }

    public void a(xj_0[] xj_0Array) {
        this.bVJ.add(xj_0Array);
    }

    public int iP() {
        return 14;
    }

    public Iterator iterator() {
        return this.bVJ.iterator();
    }

    public acy ajz() {
        return this.bVJ;
    }

    public byte[] cd() {
        return ug_2.EMPTY_BYTE_ARRAY;
    }

    public void b(byte[] byArray) {
    }
}

