/*
 * Decompiled with CFR 0.152.
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/*
 * Renamed from nY
 */
public class ny_2 {
    private static PrintWriter Qc;
    private static int Qd;
    private static int Qe;
    public static int Qf;
    public static int Qg;
    public static int Qh;
    public static int Qi;
    public static int Qj;
    public static int Qk;
    public static int Ql;
    public static int Qm;
    public static int Qn;
    public static int Qo;
    public static int Qp;
    public static int Qq;
    public static cp_2 Qr;
    private static final ny_2 Qs;
    private String eA = "";
    private boolean Qt = false;

    public static ny_2 sR() {
        return Qs;
    }

    public void setFileName(String string) {
        if (this.Qt) {
            try {
                File file = new File(string);
                this.eA = file.exists() ? string + "1" : string;
                Qc = new PrintWriter(new BufferedWriter(new FileWriter(this.eA)), true);
            }
            catch (IOException iOException) {
                this.Qt = false;
                System.out.println(iOException);
            }
        }
    }

    public void flush() {
        if (Qc != null) {
            Qc.flush();
        }
    }

    public void println(String ... stringArray) {
        if (this.Qt) {
            for (int j = 0; j < stringArray.length; ++j) {
                Qc.print(stringArray[j]);
            }
            Qc.println();
        }
    }

    public void print(String ... stringArray) {
        if (this.Qt) {
            for (int j = 0; j < stringArray.length; ++j) {
                Qc.print(stringArray[j]);
            }
        }
    }

    public void o(byte ... byArray) {
        if (this.Qt) {
            for (int j = 0; j < byArray.length; ++j) {
                Qc.print(byArray[j]);
            }
            Qc.println();
        }
    }

    public void p(byte ... byArray) {
        if (this.Qt) {
            for (int j = 0; j < byArray.length; ++j) {
                Qc.print(byArray[j]);
            }
        }
    }

    public void j(int ... nArray) {
        if (this.Qt) {
            for (int j = 0; j < nArray.length; ++j) {
                Qc.print(nArray[j]);
            }
            Qc.println();
        }
    }

    public void close() {
        if (this.Qt) {
            Qc.flush();
            Qc.close();
            gj_1.a(this.eA, System.currentTimeMillis(), apN.aDK().Ln().getId());
            gj_1.am(this.eA);
        }
    }

    public boolean sS() {
        return this.Qt;
    }

    public void ag(boolean bl2) {
        this.Qt = bl2;
    }

    public static PrintWriter sT() {
        return Qc;
    }

    public void sU() {
        Qd = 0;
        Qe = 0;
    }

    public static int cu(int n2) {
        if (Qd == 0 && n2 != 0) {
            Qd += 4;
        }
        if (n2 != 0) {
            Qe = n2;
        }
        return (Qd += n2) - Qe;
    }

    public static void a(int n2, Iterator iterator) {
        for (int j = 0; j < n2; ++j) {
            cl_1 cl_12 = (cl_1)iterator.next();
            Qr.a(cl_12.Lb(), cl_12.Ld());
        }
    }

    public static String au(long l2) {
        return (String)Qr.t(l2);
    }

    static {
        Qe = 0;
        Qf = 4;
        Qg = 0;
        Qh = 2;
        Qi = 0;
        Qj = 1;
        Qk = 1;
        Ql = 0;
        Qm = 2;
        Qn = 0;
        Qo = 2;
        Qp = 8;
        Qq = 1;
        Qr = new cp_2();
        Qs = new ny_2();
    }
}

