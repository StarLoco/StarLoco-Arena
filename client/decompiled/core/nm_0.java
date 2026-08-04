/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Dimension;
import org.apache.log4j.Logger;

/*
 * Renamed from nm
 */
public class nm_0
implements cn_1 {
    protected static final Logger a = Logger.getLogger(nm_0.class);
    private static final acl_0 uG = new ade_1(new acn_1(), 1000);
    private int aG;
    private int aH;
    private int fb;
    private int fc;

    public static nm_0 sl() {
        return nm_0.k(0, 0, 0, 0);
    }

    public static nm_0 k(int n2, int n3, int n4, int n5) {
        try {
            nm_0 nm_02 = (nm_0)uG.adr();
            nm_02.setBounds(n2, n3, n4, n5);
            return nm_02;
        }
        catch (Exception exception) {
            throw new RuntimeException("Erreur lors d'un checkOut sur un Item : ", exception);
        }
    }

    private nm_0(int n2, int n3, int n4, int n5) {
        this.aG = n2;
        this.aH = n3;
        this.fb = n4;
        this.fc = n5;
    }

    public void release() {
        try {
            uG.af(this);
        }
        catch (Exception exception) {
            a.error((Object)("Exception dans le release de " + this.getClass().toString() + " (normalement impossible)"));
        }
    }

    public void b() {
    }

    public void j() {
        this.aG = 0;
        this.aH = 0;
        this.fb = 0;
        this.fc = 0;
    }

    public nm_0 a(nm_0 nm_02) {
        int n2 = Math.min(this.aG, nm_02.aG);
        int n3 = Math.min(this.aH, nm_02.aH);
        int n4 = Math.max(this.aG + this.fb, nm_02.aG + nm_02.fb);
        int n5 = Math.max(this.aH + this.fc, nm_02.aH + nm_02.fc);
        return nm_0.k(n2, n3, n4 - n2, n5 - n3);
    }

    public void b(nm_0 nm_02) {
        int n2 = Math.min(this.aG, nm_02.aG);
        int n3 = Math.min(this.aH, nm_02.aH);
        int n4 = Math.max(this.aG + this.fb, nm_02.aG + nm_02.fb);
        int n5 = Math.max(this.aH + this.fc, nm_02.aH + nm_02.fc);
        this.setBounds(n2, n3, n4 - n2, n5 - n3);
    }

    public nm_0 c(nm_0 nm_02) {
        nm_0 nm_03 = nm_0.sl();
        nm_03.a(this, nm_02);
        return nm_03;
    }

    public void a(nm_0 nm_02, nm_0 nm_03) {
        int n2 = nm_02.aG;
        int n3 = nm_02.aH;
        int n4 = nm_03.aG;
        int n5 = nm_03.aH;
        long l2 = n2;
        l2 += (long)nm_02.fb;
        long l3 = n3;
        l3 += (long)nm_02.fc;
        long l4 = n4;
        l4 += (long)nm_03.fb;
        long l5 = n5;
        l5 += (long)nm_03.fc;
        if (n2 < n4) {
            n2 = n4;
        }
        if (n3 < n5) {
            n3 = n5;
        }
        if (l2 > l4) {
            l2 = l4;
        }
        if (l3 > l5) {
            l3 = l5;
        }
        l3 -= (long)n3;
        if ((l2 -= (long)n2) < Integer.MIN_VALUE) {
            l2 = Integer.MIN_VALUE;
        }
        if (l3 < Integer.MIN_VALUE) {
            l3 = Integer.MIN_VALUE;
        }
        this.aG = n2;
        this.aH = n3;
        this.fb = (int)l2;
        this.fc = (int)l3;
    }

    public void b(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = n2;
        int n11 = n3;
        long l2 = n10;
        l2 += (long)n4;
        long l3 = n11;
        l3 += (long)n5;
        long l4 = n6;
        l4 += (long)n8;
        long l5 = n7;
        l5 += (long)n9;
        if (n10 < n6) {
            n10 = n6;
        }
        if (n11 < n7) {
            n11 = n7;
        }
        if (l2 > l4) {
            l2 = l4;
        }
        if (l3 > l5) {
            l3 = l5;
        }
        l3 -= (long)n11;
        if ((l2 -= (long)n10) < Integer.MIN_VALUE) {
            l2 = Integer.MIN_VALUE;
        }
        if (l3 < Integer.MIN_VALUE) {
            l3 = Integer.MIN_VALUE;
        }
        this.aG = n10;
        this.aH = n11;
        this.fb = (int)l2;
        this.fc = (int)l3;
    }

    public boolean d(nm_0 nm_02) {
        int n2 = this.fb;
        int n3 = this.fc;
        int n4 = nm_02.fb;
        int n5 = nm_02.fc;
        if (n4 <= 0 || n5 <= 0 || n2 <= 0 || n3 <= 0) {
            return false;
        }
        int n6 = this.aG;
        int n7 = this.aH;
        int n8 = nm_02.aG;
        int n9 = nm_02.aH;
        n5 += n9;
        n2 += n6;
        n3 += n7;
        return !((n4 += n8) >= n8 && n4 <= n6 || n5 >= n9 && n5 <= n7 || n2 >= n6 && n2 <= n8 || n3 >= n7 && n3 <= n9);
    }

    public static boolean c(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9) {
        int n10 = n4;
        int n11 = n5;
        int n12 = n8;
        int n13 = n9;
        if (n12 <= 0 || n13 <= 0 || n10 <= 0 || n11 <= 0) {
            return false;
        }
        n13 += n7;
        n10 += n2;
        n11 += n3;
        return !((n12 += n6) >= n6 && n12 <= n2 || n13 >= n7 && n13 <= n3 || n10 >= n2 && n10 <= n6 || n11 >= n3 && n11 <= n7);
    }

    public void e(nm_0 nm_02) {
        this.aG = nm_02.aG;
        this.aH = nm_02.aH;
        this.fb = nm_02.fb;
        this.fc = nm_02.fc;
    }

    public void setBounds(int n2, int n3, int n4, int n5) {
        this.aG = n2;
        this.aH = n3;
        this.fb = n4;
        this.fc = n5;
    }

    public void setLocation(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
    }

    public void setSize(Dimension dimension) {
        this.setSize(dimension.width, dimension.height);
    }

    public void setSize(int n2, int n3) {
        this.fb = n2;
        this.fc = n3;
    }

    public int getHeight() {
        return this.fc;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getX() {
        return this.aG;
    }

    public int getY() {
        return this.aH;
    }

    public void setHeight(int n2) {
        this.fc = n2;
    }

    public void setWidth(int n2) {
        this.fb = n2;
    }

    public void setX(int n2) {
        this.aG = n2;
    }

    public void setY(int n2) {
        this.aH = n2;
    }

    /* synthetic */ nm_0(int n2, int n3, int n4, int n5, acn_1 acn_12) {
        this(n2, n3, n4, n5);
    }
}

