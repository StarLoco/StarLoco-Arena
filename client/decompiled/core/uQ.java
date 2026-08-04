/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class uQ {
    private static final Logger a = Logger.getLogger(uQ.class);
    private final ArrayList ari = new ArrayList();
    private int ug;
    private String arj;
    private acd_0 ark = null;
    private boolean arl = true;
    private static final uQ arm = new uQ();

    public static uQ AV() {
        return arm;
    }

    private uQ() {
    }

    public void setPath(String string) {
        try {
            this.arj = vq_2.getURL(string).toString();
            if (!this.arj.endsWith("/")) {
                this.arj = this.arj + "/";
            }
        }
        catch (IOException iOException) {
            a.error((Object)("Invalid path : " + string), (Throwable)iOException);
        }
    }

    public void aH(int n2) {
        this.ug = n2;
    }

    public int AW() {
        return this.ug;
    }

    public adb_0 n(int n2, int n3, int n4) {
        if (this.ark == null || !this.ark.F(n2, n3)) {
            this.ark = this.Y(n2, n3);
        }
        return this.ark == null ? null : this.ark.n(n2, n3, n4);
    }

    public final void b(short s, short s2) {
        acd_0 acd_02 = new acd_0();
        String string = uQ.a(this.arj, this.ug, s, s2);
        acf acf2 = new acf(vq_2.gm(string));
        acd_02.b(acf2);
        assert (hy_2.aO(acd_02.aG) == s && hy_2.aO(acd_02.aH) == s2);
        this.ari.add(acd_02);
        this.arl = true;
    }

    public void clean() {
        this.ark = null;
        this.ari.clear();
    }

    public void a(float f) {
        for (int j = this.ari.size() - 1; j >= 0; --j) {
            ((acd_0)this.ari.get(j)).a(f);
        }
    }

    public void j(short s, short s2) {
        for (int j = 0; j < this.ari.size(); ++j) {
            if (!((acd_0)this.ari.get(j)).s(s, s2)) continue;
            this.ari.remove(j);
            this.ark = null;
            return;
        }
    }

    public boolean AX() {
        return this.arl;
    }

    public void aK(boolean bl2) {
        this.arl = bl2;
    }

    private acd_0 Y(int n2, int n3) {
        for (int j = this.ari.size() - 1; j >= 0; --j) {
            if (!((acd_0)this.ari.get(j)).F(n2, n3)) continue;
            return (acd_0)this.ari.get(j);
        }
        return null;
    }

    private static String a(String string, int n2, short s, short s2) {
        assert (string != null && string.contains("%d") && string.endsWith("/"));
        return String.format(string, n2) + s + '_' + s2;
    }
}

