/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.transformer.BatchTransformer;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class mT
extends aiu_0 {
    protected static final Logger a = Logger.getLogger(mT.class);
    protected final ArrayList Ls = new ArrayList();
    private mT Lt;
    private mT Lu;
    private ArrayList Lv;
    private mT Lw;
    private boolean Lx;
    private boolean Ly;
    private boolean Lz;
    private byte LA;
    public static final byte LB = 0;
    public static final byte LC = 1;
    public static final byte LD = 2;
    private static final int LE = 500;
    private static final int LF = 1;
    private static final int LG = 1;

    public mT(long l2) {
        super(l2);
    }

    public mT(long l2, double d, double d2, double d3) {
        super(l2, d, d2, d3);
    }

    public mT(long l2, double d, double d2) {
        super(l2, d, d2);
    }

    public boolean rA() {
        return this.Lz;
    }

    public void b(qc_0 qc_02) {
        assert (qc_02 != null);
        if (this.ak == qc_02) {
            return;
        }
        int n2 = qc_02.getIndex() - this.ak.getIndex();
        this.ak = qc_02;
        this.GF = true;
        if (this.Lt != null) {
            this.Lt.b(this.Lt.L().hg(n2));
        }
        if (this.Lv != null) {
            int n3 = this.Lv.size();
            for (int j = 0; j < n3; ++j) {
                mT mT2 = (mT)this.Lv.get(j);
                if (!mT2.Ly) continue;
                mT2.b(this.ak);
            }
        }
    }

    public void a(double d, double d2, double d3) {
        int n2 = this.gn();
        int n3 = this.go();
        super.a(d, d2, d3);
        int n4 = this.gn();
        int n5 = this.go();
        if (n4 != n2 || n5 != n3) {
            this.g(new int[]{this.gn(), this.go(), (int)Math.round(this.getAltitude())});
        }
        if (this.Lt != null) {
            this.Lt.a(d, d2, d3 + (double)this.ge());
        }
        if (this.Lv != null) {
            int n6 = this.Lv.size();
            for (int j = 0; j < n6; ++j) {
                ((mT)this.Lv.get(j)).a(d, d2, d3 + (double)this.ge());
            }
        }
    }

    protected void b(mT mT2) {
        this.Lt = mT2;
    }

    public mT rB() {
        return this.Lt;
    }

    protected void c(mT mT2) {
        this.Lu = mT2;
    }

    public mT rC() {
        return this.Lu;
    }

    public boolean rD() {
        return this.Lu != null;
    }

    public boolean rE() {
        return this.Lt != null;
    }

    public void d(mT mT2) {
        if (mT2 == this) {
            return;
        }
        mT2.c(this);
        this.b(mT2);
        mT2.aTt();
    }

    public boolean aY(String string) {
        boolean bl2 = super.aY(string);
        if (bl2 && this.Lv != null) {
            int n2 = this.Lv.size();
            for (int j = 0; j < n2; ++j) {
                mT mT2 = (mT)this.Lv.get(j);
                if (!mT2.Lx) continue;
                mT2.aY(string);
            }
        }
        return bl2;
    }

    public boolean a(qs_2 qs_22) {
        if (!super.a(qs_22)) {
            return false;
        }
        if (this.Lu != null) {
            Matrix44 matrix44 = this.Lu.aTF().jR();
            if (matrix44 == null) {
                return false;
            }
            this.a(qs_22, matrix44, this.Lu);
        } else if (this.Lw != null && this.Lw.aTF() != null) {
            Matrix44 matrix44 = this.Lw.aTF().jS();
            if (matrix44 == null) {
                return false;
            }
            this.a(qs_22, matrix44, this.Lw);
        }
        return true;
    }

    public mT rF() {
        if (this.Lt != null) {
            this.ls(null);
            this.Lt.c((mT)null);
            mT mT2 = this.Lt;
            this.b((mT)null);
            mT2.aTt();
            return mT2;
        }
        return null;
    }

    public mT a(boolean bl2, ry ry2) {
        if (this.Lt != null) {
            if (bl2) {
                this.ls(null);
                this.a(ry2);
            }
            this.Lt.c((mT)null);
            mT mT2 = this.Lt;
            this.b((mT)null);
            if (bl2) {
                mT2.aTt();
            }
            return mT2;
        }
        return null;
    }

    private void a(ry ry2) {
        aln_0 aln_02 = new aln_0(this.Lt);
        aln_02.h(new agv_0(ry2.getX(), ry2.getY(), ry2.wk()));
        aln_02.f(new agv_0(this.Lt.gn(), this.Lt.go(), this.Lt.gp() + this.ge()));
        aln_02.dN(500L);
        aln_02.g(new agv_0(0.0f, 0.0f, 1.0f));
        aln_02.i(new agv_0(0.0f, 0.0f, 1.0f));
        ahq_0.awW().b(aln_02);
    }

    public void e(mT mT2) {
        this.a(mT2, false, false, true);
    }

    public void a(mT mT2, boolean bl2, boolean bl3, boolean bl4) {
        assert (this.Lw == null) : "A mobile is already linked to this mobile";
        assert (mT2 != null) : "You can't link a null mobile";
        if (this.Lv == null) {
            this.Lv = new ArrayList();
        }
        this.Lv.add(mT2);
        mT2.Lw = this;
        mT2.Lx = bl2;
        mT2.Lz = bl3;
        mT2.Ly = bl4;
        this.aTt();
        mT2.aTt();
        mT2.rL();
    }

    public final void rG() {
        if (this.Lv == null) {
            return;
        }
        int n2 = this.Lv.size();
        for (int j = 0; j < n2; ++j) {
            mT mT2 = (mT)this.Lv.get(j);
            mT2.Lw = null;
            mT2.aTt();
        }
        this.Lv.clear();
        this.Lv = null;
    }

    public final void f(mT mT2) {
        if (this.Lv == null) {
            return;
        }
        this.Lv.remove(mT2);
        mT2.Lw = null;
        mT2.aTt();
    }

    public ArrayList rH() {
        return this.Lv;
    }

    public mT rI() {
        return this.Lw;
    }

    public float[] aE(int n2) {
        if (this.cAF == null) {
            return null;
        }
        return this.cAF.aE(n2);
    }

    public void b(int n2, float[] fArray) {
        assert (fArray == null || fArray.length == 4);
        if (this.cAF == null) {
            return;
        }
        if (fArray == null) {
            this.cAF.aF(n2);
        }
        this.cAF.a(n2, fArray);
    }

    public final void a(aje aje2) {
        if (aje2 != null && !this.Ls.contains(aje2)) {
            this.Ls.add(aje2);
        }
    }

    public final void b(aje aje2) {
        if (aje2 != null) {
            this.Ls.remove(aje2);
        }
    }

    public final void rJ() {
        this.Ls.clear();
    }

    protected void g(int[] nArray) {
        if (this.Ls != null) {
            aje[] ajeArray;
            for (aje aje2 : ajeArray = this.Ls.toArray(new aje[this.Ls.size()])) {
                aje2.a(this, nArray[arh_0.cQk], nArray[arh_0.cQl], (short)nArray[arh_0.cQm]);
            }
        }
        if (this.rB() != null) {
            this.rB().g(nArray);
        }
    }

    public void setStatus(byte by) {
        this.LA = by;
    }

    public byte rK() {
        return this.LA;
    }

    private void a(qs_2 qs_22, Matrix44 matrix44, mT mT2) {
        Matrix44 matrix442 = Matrix44.acr();
        matrix442.e(mT2.d(qs_22));
        BatchTransformer batchTransformer = this.getEntity().aUM();
        kz_2 kz_22 = new kz_2();
        Matrix44 matrix443 = (Matrix44)yW.FL().a(Matrix44.it(), Matrix44.class);
        matrix443.a(matrix44, matrix442);
        float f = mT2.aTF().getScale();
        float[] fArray = matrix443.Pn();
        fArray[0] = fArray[0] / f;
        fArray[5] = fArray[5] / f;
        kz_22.a(matrix443);
        batchTransformer.b(0, kz_22);
        matrix443.HF();
    }

    public void e(float[] fArray) {
        if (this.Lw != null) {
            this.rL();
        } else {
            super.e(fArray);
            if (this.Lv != null) {
                int n2 = this.Lv.size();
                for (int j = 0; j < n2; ++j) {
                    ((mT)this.Lv.get(j)).rL();
                }
            }
        }
    }

    protected void rL() {
        if (this.Lw == null) {
            return;
        }
        aPb aPb2 = this.Lw.getMaterial();
        this.tJ.G(aPb2.aYK());
        this.dLn.c(this.tJ);
    }
}

