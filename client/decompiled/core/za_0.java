/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import org.apache.log4j.Logger;

/*
 * Renamed from Za
 */
public class za_0
extends oj_2 {
    private static final Logger a = Logger.getLogger(za_0.class);

    public za_0(int n2, int n3) {
        super(n2, n3);
    }

    public final int abp() {
        return this.getWidth() * this.getHeight();
    }

    public final int au(int n2, int n3) {
        if (n2 < 0 || n2 >= this.getWidth() || n3 < 0 || n3 >= this.getHeight()) {
            return -1;
        }
        return n2 + n3 * this.getWidth();
    }

    public int gZ(int n2) {
        return n2 % this.getWidth();
    }

    public int ha(int n2) {
        return n2 / this.getWidth();
    }

    public final auo_0 av(int n2, int n3) {
        int n4 = (int)Math.floor((float)n2 / (float)this.getWidth());
        int n5 = (int)Math.floor((float)n3 / (float)this.getHeight());
        return new auo_0(n4, n5);
    }

    public int aw(int n2, int n3) {
        return (int)Math.floor((float)n2 / (float)this.getWidth());
    }

    public int ax(int n2, int n3) {
        return (int)Math.floor((float)n3 / (float)this.getHeight());
    }

    public final auo_0 ay(int n2, int n3) {
        return new auo_0(n2 * this.getWidth(), n3 * this.getHeight());
    }

    public int az(int n2, int n3) {
        return n2 * this.getWidth();
    }

    public int aA(int n2, int n3) {
        return n3 * this.getHeight();
    }

    public byte abq() {
        return 105;
    }
}

