/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aon
 */
public class aon_2
extends afB {
    public static final gh_0 cKK;
    public static final gh_0 cKL;
    public static final gh_0 cKM;
    public static final gh_0 cKN;
    protected Qa qJ;
    protected boolean tY = false;
    protected static kf_0 cKO;
    private static final Logger a;
    private pw cKP;
    private ArrayList cKQ;
    private static final boolean DEBUG = false;

    public aon_2() {
        this.cKP = pw.acb;
        this.cKQ = new ArrayList(1);
        this.cKQ.add(cKO);
        cKO.HE();
    }

    public aon_2(pw pw2, kf_0[] kf_0Array) {
        this.cKP = pw2;
        this.cKQ = new ArrayList(kf_0Array.length);
        for (kf_0 kf_02 : kf_0Array) {
            kf_02.HE();
            this.cKQ.add(kf_02);
        }
    }

    public aon_2(pw pw2, kf_0 kf_02) {
        this.cKP = pw2;
        this.cKQ = new ArrayList(1);
        kf_02.HE();
        this.cKQ.add(kf_02);
    }

    public aon_2(aon_2 aon_22) {
        this.cKP = aon_22.cKP;
        int n2 = aon_22.cKQ.size();
        this.cKQ = new ArrayList(n2);
        for (int j = 0; j < n2; ++j) {
            kf_0 kf_02 = new kf_0(aon_22.lB(j));
            this.cKQ.add(kf_02);
        }
    }

    public aon_2(String string) {
        this.iO(string);
    }

    public boolean iN(String string) {
        try {
            this.qJ = qz_0.adf().e(new URL(string));
        }
        catch (MalformedURLException malformedURLException) {
            return this.iO(string);
        }
        return true;
    }

    public boolean iO(String string) {
        aon_2 aon_22 = aon_2.iP(string);
        if (aon_22 == null) {
            return false;
        }
        this.cKP = aon_22.cKP;
        this.cKQ = aon_22.cKQ;
        aon_22.HF();
        return true;
    }

    public boolean a(byte[] byArray, String string) {
        aon_2 aon_22 = aon_2.b(byArray, string);
        if (aon_22 == null) {
            return false;
        }
        this.cKP = aon_22.cKP;
        this.cKQ = aon_22.cKQ;
        aon_22.HF();
        return true;
    }

    public boolean u(String ... stringArray) {
        this.cKQ.remove(0);
        pw pw2 = null;
        int n2 = -1;
        int n3 = -1;
        for (int j = 0; j < stringArray.length; ++j) {
            aon_2 aon_22 = aon_2.iP(stringArray[j]);
            if (aon_22 == null) {
                return false;
            }
            if (pw2 != null && !pw2.equals(aon_22.cKP)) {
                return false;
            }
            pw2 = aon_22.cKP;
            int n4 = aon_22.aCH();
            for (int i2 = 0; i2 < n4; ++i2) {
                kf_0 kf_02 = aon_22.lB(i2);
                if (n2 == -1 && n3 == -1) {
                    n2 = kf_02.getWidth();
                    n3 = kf_02.getHeight();
                } else if (n2 != kf_02.getWidth() || n3 != kf_02.getHeight()) {
                    return false;
                }
                this.b(kf_02);
            }
            aon_22.HF();
        }
        this.cKP = pw2;
        return true;
    }

    public void a(int n2, zi_1 zi_12) {
        ((kf_0)this.cKQ.get(n2)).a(zi_12);
    }

    public void a(int n2, vP vP2, vP vP3) {
        ((kf_0)this.cKQ.get(n2)).a(vP2, vP3);
    }

    public void a(int n2, kf_0 kf_02) {
        ((kf_0)this.cKQ.get(n2)).HF();
        this.cKQ.set(n2, kf_02);
    }

    public final void b(kf_0 kf_02) {
        this.cKQ.add(kf_02);
    }

    public final boolean isEmpty() {
        return this.cKQ == null || this.cKQ.isEmpty() || this.cKQ.get(0) == cKO;
    }

    public kf_0 lB(int n2) {
        assert (n2 < this.cKQ.size());
        return (kf_0)this.cKQ.get(n2);
    }

    public final int aCH() {
        if (this.cKQ == null) {
            return 0;
        }
        return this.cKQ.size();
    }

    public final pw aCI() {
        return this.cKP;
    }

    public final adz_1 lC(int n2) {
        return this.lB(n2).pl();
    }

    public vP K(int n2, int n3, int n4) {
        return this.lB(n4).H(n2, n3);
    }

    public final void a(float f, float f2, gh_0 gh_02) {
        for (kf_0 kf_02 : this.cKQ) {
            kf_02.a(f, f2, gh_02);
        }
    }

    public final void aCJ() {
        for (int j = this.cKQ.size() - 1; j >= 1; --j) {
            kf_0 kf_02 = (kf_0)this.cKQ.get(j);
            kf_0 kf_03 = (kf_0)this.cKQ.get(j - 1);
            if (kf_02.getBitDepth() != 32) {
                this.a(j - 1, kf_02);
                continue;
            }
            kf_03.a(kf_02);
            this.cKQ.remove(j);
            kf_02.HF();
        }
    }

    public static aon_2 iP(String string) {
        int n2 = string.lastIndexOf(46);
        assert (n2 > 0);
        String string2 = string.substring(n2 + 1).toUpperCase();
        cj_2 cj_22 = ait_1.ayz().in(string2);
        if (cj_22 == null) {
            a.error((Object)("No ImageReader registered for file ext (." + string2 + ")"));
            a.error((Object)("Did you forget to call ImageReaderFactory.getInstance().registerReader (\"" + string2 + "\", new " + string2 + "Reader ()); ?"));
            return null;
        }
        aon_2 aon_22 = cj_22.z(string);
        if (aon_22 == null) {
            a.error((Object)("Impossible de lire l'image " + string));
        }
        return aon_22;
    }

    public static aon_2 b(byte[] byArray, String string) {
        assert (byArray != null);
        cj_2 cj_22 = ait_1.ayz().in(string);
        if (cj_22 == null) {
            a.error((Object)("No ImageReader registered for file ext (." + string + ")"));
            a.error((Object)("Did you forget to call ImageReaderFactory.getInstance().registerReader (\"" + string + "\", new " + string + "Reader ()); ?"));
            return null;
        }
        aon_2 aon_22 = cj_22.c(byArray);
        if (aon_22 == null) {
            a.error((Object)"Impossible de lire les donn\u00e9es d'image.");
        }
        return aon_22;
    }

    public static void h(byte[] byArray, int n2) {
        block3: {
            int n3;
            block2: {
                n3 = n2 / 8;
                if (n2 != 16) break block2;
                int n4 = 0;
                while (n4 < byArray.length) {
                    byte by = byArray[n4];
                    byte by2 = byArray[n4 + 1];
                    byArray[n4++] = (byte)(by2 << 3 | by & 7);
                    byArray[n4++] = (byte)(by2 & 0xE0 | by >> 3);
                }
                break block3;
            }
            if (n2 != 24 && n2 != 32) break block3;
            for (int j = 0; j < byArray.length; j += n3) {
                byte by = byArray[j];
                byArray[j] = byArray[j + 2];
                byArray[j + 2] = by;
            }
        }
    }

    public static void i(byte[] byArray, int n2) {
        assert (byArray.length % n2 == 0) : "Unable to flip the image since the buffer length is not a muptiple of line size";
        byte[] byArray2 = new byte[n2];
        int n3 = byArray.length / n2;
        int n4 = byArray.length - n2;
        int n5 = 0;
        for (int j = 0; j < n3 / 2; ++j) {
            System.arraycopy(byArray, n5, byArray2, 0, n2);
            System.arraycopy(byArray, n4, byArray, n5, n2);
            System.arraycopy(byArray2, 0, byArray, n4, n2);
            n4 -= n2;
            n5 += n2;
        }
    }

    public static kf_0 b(byte[] byArray, int n2, int n3, int n4) {
        int n5 = ej_0.aq(n2);
        int n6 = ej_0.aq(n3);
        if (n5 == n2 && n6 == n3) {
            return new kf_0(n5, n6, (short)n4, null, byArray, 0, byArray.length);
        }
        int n7 = n4 / 8;
        byte[] byArray2 = new byte[n5 * n6 * n7];
        int n8 = n2 * n7;
        int n9 = n5 * n7;
        int n10 = byArray.length / n8;
        int n11 = 0;
        int n12 = 0;
        for (int j = 0; j < n10; ++j) {
            System.arraycopy(byArray, n11, byArray2, n12, n8);
            n11 += n8;
            n12 += n9;
        }
        return new kf_0(n5, n6, (short)n4, null, byArray2, 0, byArray2.length);
    }

    public static kf_0 a(kf_0 kf_02, int n2, int n3, int n4, int n5) {
        assert (n2 >= 0 && n4 > n2 && n4 <= kf_02.getHeight());
        assert (n3 >= 0 && n5 > n3 && n5 <= kf_02.getWidth());
        int n6 = kf_02.getBitDepth() / 8;
        int n7 = n5 - n3;
        int n8 = n4 - n2;
        byte[] byArray = new byte[n7 * n8 * n6];
        int n9 = kf_02.getWidth() * n6;
        int n10 = n4 - n2;
        int n11 = n7 * n6;
        byte[] byArray2 = kf_02.getData();
        int n12 = n2 * n9 + n3 * n6;
        int n13 = 0;
        for (int j = 0; j < n10; ++j) {
            System.arraycopy(byArray2, n12, byArray, n13, n11);
            n12 += n9;
            n13 += n11;
        }
        return new kf_0(n7, n8, (short)kf_02.getBitDepth(), null, byArray, 0, byArray.length);
    }

    public static void a(kf_0 kf_02, agf_0 agf_02) {
        agf_02.ow(kf_02.getWidth());
        agf_02.oy(kf_02.getHeight());
        agf_02.ox(0);
        agf_02.oz(0);
        for (int j = 0; j < kf_02.getWidth(); ++j) {
            for (int i2 = 0; i2 < kf_02.getHeight(); ++i2) {
                if (kf_02.G(j, i2) == 0) continue;
                if (j < agf_02.aSQ()) {
                    agf_02.ow(j);
                }
                if (j > agf_02.aSR()) {
                    agf_02.ox(j);
                }
                if (i2 < agf_02.aSS()) {
                    agf_02.oy(i2);
                }
                if (i2 <= agf_02.aST()) continue;
                agf_02.oz(i2);
            }
        }
        if (agf_02.aSS() > agf_02.aST()) {
            agf_02.ow(0);
            agf_02.ox(0);
            agf_02.oy(0);
            agf_02.oz(0);
        }
    }

    public void lD(int n2) {
        for (kf_0 kf_02 : this.cKQ) {
            kf_02.bQ(n2);
        }
    }

    public void aCK() {
        String string = this.qJ.getURL().getFile();
        int n2 = string.lastIndexOf(46);
        assert (n2 > 0);
        String string2 = string.substring(n2 + 1).toUpperCase();
        cj_2 cj_22 = ait_1.ayz().in(string2);
        if (cj_22 == null) {
            a.error((Object)("No ImageReader registered for file ext (." + string2 + ")"));
            a.error((Object)("Did you forget to call ImageReaderFactory.getInstance().registerReader (\"" + string2 + "\", new " + string2 + "Reader ()); ?"));
            return;
        }
        aon_2 aon_22 = cj_22.c(this.qJ.getData());
        if (aon_22 == null) {
            a.error((Object)("Impossible de lire l'image " + string));
            return;
        }
        this.cKP = aon_22.cKP;
        this.cKQ = aon_22.cKQ;
        aon_22.HF();
        this.qJ = null;
        this.tY = true;
    }

    protected void delete() {
        super.delete();
        if (this.cKQ != null) {
            for (kf_0 kf_02 : this.cKQ) {
                kf_02.HF();
            }
            this.cKQ = null;
        }
        this.cKP = null;
    }

    static {
        int n2;
        cKK = new gh_0(new float[]{1.0f});
        cKL = new gh_0(3);
        cKM = new gh_0(3);
        cKN = new gh_0(3);
        float f = 1.5f;
        float f2 = 4.5f;
        float[] fArray = new float[cKL.getSize() * cKL.getSize()];
        int n3 = cKL.getSize() / 2;
        float f3 = 0.0f;
        int n4 = -1;
        for (n2 = -n3; n2 < n3 + 1; ++n2) {
            for (int j = -n3; j < n3 + 1; ++j) {
                float f4 = (float)(0.0707355302630646 * Math.exp((float)(-(n2 * n2 + j * j)) / 4.5f));
                f3 += f4;
                fArray[++n4] = f4;
            }
        }
        n4 = 0;
        while (n4 < fArray.length) {
            int n5 = n4++;
            fArray[n5] = fArray[n5] / f3;
        }
        cKL.m(fArray);
        int n6 = cKM.getSize();
        float[] fArray2 = new float[n6 * n6];
        int n7 = n6 / 2;
        float f5 = 0.0f;
        int n8 = -1;
        for (n4 = -n7; n4 < n7 + 1; ++n4) {
            for (n2 = -n7; n2 < n7 + 1; ++n2) {
                float f6 = n6 + n6 * n4 * n4 + n2 * n2;
                f5 += f6;
                fArray2[++n8] = f6;
            }
        }
        n8 = 0;
        while (n8 < fArray2.length) {
            int n9 = n8++;
            fArray2[n9] = fArray2[n9] / f5;
        }
        cKM.m(fArray2);
        n6 = cKN.getSize();
        fArray2 = new float[n6 * n6];
        n7 = n6 / 2;
        f5 = 0.0f;
        n8 = -1;
        for (n4 = -n7; n4 < n7 + 1; ++n4) {
            for (n2 = -n7; n2 < n7 + 1; ++n2) {
                float f7 = 1.0f;
                f5 += 1.0f;
                fArray2[++n8] = 1.0f;
            }
        }
        n8 = 0;
        while (n8 < fArray2.length) {
            int n10 = n8++;
            fArray2[n10] = fArray2[n10] / f5;
        }
        cKN.m(fArray2);
        a = Logger.getLogger(aon_2.class);
        n6 = 128;
        int n11 = 64;
        byte[] byArray = new byte[32768];
        cKO = new kf_0(128, 64, 32, null, byArray);
        cKO.bQ(255);
    }
}

