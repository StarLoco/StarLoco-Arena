/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
package com.ankamagames.framework.graphics.engine.Anm2;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.zip.CRC32;
import org.apache.log4j.Logger;

public final class Anm
extends ams_2 {
    public static final int ql = 0;
    public static final int qm = 1;
    public static final int qn = 2;
    public static final int qo = 3;
    public static final int qp = 4;
    public static final int qq = 5;
    public static final int qr = 6;
    public static final int qs = 7;
    public static final int qt = 8;
    public static final int qu = 9;
    public static final int qv = 10;
    public static final int[] qw = new int[]{1, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    public bs_2 qx;
    public vk_2[] qy;
    public final zm_1 qz;
    public ju_2[] qA;
    public final zm_1 qB;
    public final lb_0 qC;
    public final lb_0 qD;
    public final zm_1 qE;
    String[] qF;
    public final aek_0 qG = new aek_0();
    ArrayList qH;
    String aJ;
    String eA;
    volatile ql_0 qI;
    private Qa qJ;
    private long qK = 0L;
    private static final int qL;
    private static final Logger a;
    private static final CRC32 qM;
    private static final ExecutorService qN;
    private static int qO;
    private Future qP;
    private boolean qQ;

    public Anm() {
        this.qx = new bs_2();
        this.qz = new zm_1();
        this.qB = new zm_1();
        this.qC = new lb_0();
        this.qD = new lb_0();
        this.qE = new zm_1();
        this.qI = ql_0.bHG;
    }

    public boolean ip() {
        return this.qJ != null && this.qJ.acI();
    }

    public final bs_2 iq() {
        return this.qx;
    }

    public final void b(String string, boolean bl2) {
        this.eA = vq_2.getName(string);
        this.aJ = vq_2.getPath(string);
        if (bl2) {
            this.qJ = qz_0.adf().e(new URL(string));
            this.qI = ql_0.bHH;
        } else {
            byte[] byArray = vq_2.readFile(string);
            acf acf2 = acf.T(byArray);
            this.b(acf2);
        }
    }

    public final void b(acf acf2) {
        int n2;
        int n3;
        ArrayList arrayList;
        int n4;
        int n5;
        int n6;
        this.qx = new bs_2();
        this.qx.b(acf2);
        if (this.qx.cJ()) {
            this.qG.b(acf2);
        }
        this.qQ = this.qx.cL();
        int n7 = acf2.readShort() & 0xFFFF;
        this.qy = new vk_2[n7];
        for (n6 = 0; n6 < this.qy.length; ++n6) {
            this.qy[n6] = new vk_2();
            this.qy[n6].b(acf2);
        }
        n6 = acf2.readShort() & 0xFFFF;
        this.qz.ensureCapacity(n6);
        for (n5 = 0; n5 < n6; ++n5) {
            ana_1 ana_12 = new ana_1();
            ana_12.b(acf2);
            this.qz.b(ana_12.fL, ana_12);
        }
        n5 = acf2.readShort() & 0xFFFF;
        this.qA = new ju_2[n5];
        this.qB.ensureCapacity(this.qA.length);
        this.qC.ensureCapacity(this.qA.length);
        this.qD.ensureCapacity(this.qA.length);
        boolean bl2 = this.qG.auG();
        for (n4 = 0; n4 < this.qA.length; ++n4) {
            this.qA[n4] = new ju_2();
            this.qA[n4].b(acf2);
            String string = this.qA[n4].getName();
            if (bl2 && this.ag(string)) {
                this.qA[n4] = null;
                continue;
            }
            this.qB.b(this.qA[n4].fL, this.qA[n4]);
            assert (this.qA[n4].CX == 0 || !this.qC.contains(this.qA[n4].CX));
            this.qC.c(this.qA[n4].CX, this.qA[n4]);
            if (this.qA[n4].CY == 0) continue;
            arrayList = (ArrayList)this.qD.get(this.qA[n4].CY);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.qD.c(this.qA[n4].CY, arrayList);
            }
            arrayList.add(this.qA[n4]);
        }
        n4 = acf2.readShort() & 0xFFFF;
        this.qE.ensureCapacity(n4);
        for (n3 = 0; n3 < n4; ++n3) {
            arrayList = new hn_2();
            ((hn_2)((Object)arrayList)).b(acf2);
            this.qE.b(((hn_2)((Object)arrayList)).fL, arrayList);
        }
        n3 = acf2.readShort() & 0xFFFF;
        this.qF = new String[n3];
        for (n2 = 0; n2 < this.qF.length; ++n2) {
            this.qF[n2] = acf2.readString();
        }
        this.qI = ql_0.bHJ;
        acf2.close();
        if (this.qH != null) {
            for (n2 = 0; n2 < this.qH.size(); ++n2) {
                ((acj_1)this.qH.get(n2)).aqK();
            }
            this.qH = null;
        }
    }

    private boolean ag(String string) {
        if (string == null) {
            return false;
        }
        if (!string.startsWith("_Anim", 1)) {
            return false;
        }
        int n2 = aey_0.hK(string) ? 32 : (int)string.charAt(0);
        return n2 == 51 || n2 == 52 || n2 == 55;
    }

    public long L(long l2) {
        switch (this.qI) {
            case bHH: {
                if (!this.qJ.is()) break;
                byte[] byArray = this.qJ.getData();
                this.qJ = null;
                this.qI = ql_0.bHI;
                this.qP = qN.submit(new wz_0(this, byArray));
                break;
            }
            case bHI: {
                try {
                    if (this.qP.get() == null) break;
                    this.qI = ql_0.bHJ;
                }
                catch (Exception exception) {
                    a.error((Object)"Exception raised : ", (Throwable)exception);
                }
            }
            case bHJ: {
                this.iv();
                this.qI = ql_0.bHK;
            }
            case bHK: {
                boolean bl2 = true;
                for (int j = 0; j < this.qy.length; ++j) {
                    ef_1 ef_12 = cx_0.JY().bt(this.a(this.qy[j]));
                    if (ef_12 == null || !ef_12.isEmpty()) continue;
                    bl2 = false;
                    break;
                }
                if (!bl2) break;
                this.qI = ql_0.bHL;
            }
        }
        return l2;
    }

    public final void b(ArrayList arrayList) {
        for (int j = 0; j < this.qy.length; ++j) {
            arrayList.add(this.qy[j].m_name);
        }
    }

    public final ju_2[] ir() {
        return this.qA;
    }

    public final ArrayList av(int n2) {
        return (ArrayList)this.qD.get(n2);
    }

    public final ju_2 t(short s) {
        return (ju_2)this.qB.an(s);
    }

    public final ju_2 aw(int n2) {
        return (ju_2)this.qC.get(n2);
    }

    public final ana_1 u(short s) {
        return (ana_1)this.qz.an(s);
    }

    public final hn_2 v(short s) {
        return (hn_2)this.qE.an(s);
    }

    public final String getFileName() {
        return this.eA;
    }

    public final boolean is() {
        return this.qI == ql_0.bHL;
    }

    public final boolean cL() {
        return this.qQ;
    }

    public static int it() {
        return qL;
    }

    protected void af() {
    }

    protected void ag() {
        this.qI = ql_0.bHG;
        if (this.qx != null && this.qy != null) {
            for (int j = 0; j < this.qy.length; ++j) {
                ef_1 ef_12 = cx_0.JY().bt(this.a(this.qy[j]));
                if (ef_12 == null) continue;
                ef_12.HF();
            }
        }
        this.qy = null;
        this.qF = null;
        this.qG.clear();
        this.aJ = null;
        this.qK = 0L;
        this.eA = null;
        this.qx = null;
        this.qJ = null;
        this.qz.clear();
        this.qA = null;
        this.qB.clear();
        this.qC.clear();
        this.qD.clear();
        this.qE.clear();
    }

    protected String iu() {
        if (this.qx.cI()) {
            return this.aJ + "/Atlas/";
        }
        return this.aJ + "/Textures/";
    }

    public long a(vk_2 vk_22) {
        if (this.qK == 0L) {
            qM.reset();
            qM.update(this.aJ.getBytes());
            this.qK = 0xBB00BB0000000000L | qM.getValue() << 32;
        }
        return this.qK | (long)vk_22.asw & 0xFFFFFFFFL;
    }

    protected String b(vk_2 vk_22) {
        return this.iu() + vk_22.m_name + ".tgam";
    }

    protected void iv() {
        db_2 db_22 = arX.cQT.iE();
        cx_0 cx_02 = cx_0.JY();
        for (int j = 0; j < this.qy.length; ++j) {
            String string = this.b(this.qy[j]);
            xw_1 xw_12 = xw_1.EB();
            ef_1 ef_12 = cx_02.a(db_22, this.a(this.qy[j]), string, xw_12.EC(), xw_12.EE());
            ef_12.HE();
        }
    }

    public void releaseTexture() {
        if (this.qy == null) {
            return;
        }
        cx_0 cx_02 = cx_0.JY();
        for (int j = 0; j < this.qy.length; ++j) {
            ef_1 ef_12 = cx_02.bu(this.a(this.qy[j]));
            ef_12.HF();
        }
        this.qy = null;
    }

    public void a(acj_1 acj_12) {
        if (this.qH == null) {
            this.qH = new ArrayList(2);
        }
        this.qH.add(acj_12);
    }

    public static int ah(String string) {
        int n2 = ej_0.Z(string);
        switch (n2) {
            case -119798240: {
                return 1;
            }
            case 1361332134: {
                return 2;
            }
            case -1544385936: {
                return 3;
            }
            case -945926284: {
                return 4;
            }
            case -678076573: {
                return 5;
            }
            case 354586003: {
                return 6;
            }
            case -1943282647: {
                return 7;
            }
            case -268996155: {
                return 8;
            }
            case -977132391: {
                return 9;
            }
        }
        a.warn((Object)("part inconnue pour la coloration: " + string));
        return 0;
    }

    public static /* synthetic */ int iw() {
        return qO++;
    }

    public static /* synthetic */ Logger dT() {
        return a;
    }

    static {
        qN = Executors.newCachedThreadPool(new WY());
        qL = Anm.L(Anm.class);
        a = Logger.getLogger(Anm.class);
        qM = new CRC32();
    }
}

