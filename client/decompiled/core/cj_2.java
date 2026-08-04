/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.IOException;
import org.apache.log4j.Logger;

/*
 * Renamed from cj
 */
public abstract class cj_2 {
    protected byte[] im;
    protected static Logger a = Logger.getLogger(cj_2.class);

    public final boolean y(String string) {
        string = string.replace('\\', '/');
        try {
            this.im = vq_2.readFile(string);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return false;
        }
        return this.bA();
    }

    public aon_2 z(String string) {
        string = string.replace('\\', '/');
        try {
            return this.c(vq_2.readFile(string));
        }
        catch (IOException iOException) {
            a.error((Object)("Erreur au chargement de l'image " + string), (Throwable)iOException);
            return null;
        }
    }

    public final aon_2 c(byte[] byArray) {
        try {
            return this.a(acf.T(byArray));
        }
        catch (IOException iOException) {
            a.error((Object)"Erreur au chargement de l'image", (Throwable)iOException);
            return null;
        }
    }

    protected abstract aon_2 a(acf var1);

    public final byte[] getData() {
        return this.im;
    }

    public abstract aon_2 bz();

    protected abstract boolean bA();

    protected final byte[] a(int n2, int n3, int n4, int n5) {
        assert (this.im != null);
        int n6 = cj_2.e(n2, n4);
        int n7 = (n6 + 3) / 4 * 4;
        byte[] byArray = new byte[n3 * n6];
        int n8 = n5;
        for (int j = 0; j < byArray.length; j += n6) {
            System.arraycopy(this.im, n8, byArray, j, n6);
            n8 += n7;
        }
        return byArray;
    }

    protected final byte[] a(acf acf2, int n2, int n3, int n4) {
        int n5 = cj_2.e(n2, n4);
        int n6 = (n5 + 3) / 4 * 4;
        int n7 = n6 - n5;
        byte[] byArray = new byte[n3 * n5];
        for (int j = byArray.length - n5; j >= 0; j -= n5) {
            if (acf2.g(byArray, j, n5) != n5) {
                a.error((Object)"read error");
            }
            if (acf2.jD(n7) == n7) continue;
            a.error((Object)"skip error");
        }
        return byArray;
    }

    protected final byte[] b(acf acf2, int n2, int n3, int n4) {
        int n5 = cj_2.e(n2, n4);
        byte[] byArray = new byte[n3 * n5];
        for (int j = byArray.length - n5; j >= 0; j -= n5) {
            if (acf2.g(byArray, j, n5) == n5) continue;
            a.error((Object)"read error");
        }
        return byArray;
    }

    protected final byte[] b(int n2, int n3, int n4, int n5) {
        assert (this.im != null);
        int n6 = cj_2.e(n2, n4);
        int n7 = (n6 + 3) / 4 * 4;
        byte[] byArray = new byte[n3 * n6];
        int n8 = n5 + n7 * (n3 - 1);
        for (int j = 0; j < byArray.length; j += n6) {
            System.arraycopy(this.im, n8, byArray, j, n6);
            n8 -= n7;
        }
        return byArray;
    }

    protected final byte[] c(int n2, int n3, int n4, int n5) {
        assert (this.im != null);
        int n6 = cj_2.e(n2, n4);
        byte[] byArray = new byte[n3 * n6];
        int n7 = n5 + n6 * (n3 - 1);
        for (int j = 0; j < byArray.length; j += n6) {
            System.arraycopy(this.im, n7, byArray, j, n6);
            n7 -= n6;
        }
        return byArray;
    }

    protected static float H(int n2) {
        return (float)n2 / 8.0f;
    }

    protected static int e(int n2, int n3) {
        return (int)((float)n2 * cj_2.H(n3));
    }
}

