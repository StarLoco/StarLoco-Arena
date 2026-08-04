/*
 * Decompiled with CFR 0.152.
 */
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

/*
 * Renamed from TA
 */
public class ta_0
extends ee_2 {
    private int aRk;
    private int bOw;
    private byte DA;

    public boolean b(ByteBuffer byteBuffer) {
        boolean bl2 = false;
        try {
            aJt aJt2;
            this.c(byteBuffer.getLong());
            byte by = byteBuffer.get();
            this.aRk = byteBuffer.getInt();
            byte[] byArray = new byte[byteBuffer.get()];
            byteBuffer.get(byArray);
            this.setName(new String(byArray));
            if ((this.getName() == null || this.getName().equalsIgnoreCase("")) && (aJt2 = (aJt)ER.OC().dZ(this.aRk)) != null) {
                this.setName(aJt2.getName());
            }
            byte by2 = byteBuffer.get();
            this.g(by, by2);
            this.hU(((aJt)ER.OC().dZ(this.aRk)).aFw());
            this.aV(byteBuffer.getInt(), byteBuffer.getInt());
            this.PI();
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            a.error((Object)"pas assez de donn\u00e9es pour completer la cr\u00e9ation d'un Fighter");
            return false;
        }
        return !bl2;
    }

    public void hU(int n2) {
        this.bOw = n2;
        this.Of();
    }

    public String Oe() {
        return String.valueOf(this.bOw);
    }

    public void g(byte by, byte by2) {
        this.baJ = xq.ej(by);
        this.zv = by2;
    }

    public void aV(int n2, int n3) {
        Object object = this.baQ.pK();
        while (((aiz_1)object).hasNext()) {
            ((ll_0)object).fK();
            alm_0 alm_02 = (alm_0)((ll_0)object).value();
            alm_02.atS();
        }
        object = (aJt)ER.OC().dZ(this.aRk);
        this.a(Lr.bqx).at(((asw_0)object).aFu());
        this.a(Lr.bqz).at(((asw_0)object).aFv());
        this.a(Lr.bqy).at(((asw_0)object).Vo());
        this.a(Lr.bqA).at(n2);
        this.a(Lr.bqy).aAF();
        this.a(Lr.bqx).set(n3);
        this.a(Lr.bqz).aAF();
        this.a(Lr.bqA).aAF();
        this.a(Lr.bqU).set(0);
        this.a(Lr.bqV).set(0);
        this.a(Lr.brd).set(((asw_0)object).ot());
        this.a(Lr.bre).set(((asw_0)object).ou());
        this.a(Lr.bqW).set(4);
        if (((aJt)object).Qx().length > 0) {
            try {
                for (int j = 0; j < ((aJt)object).Qx().length; ++j) {
                    this.Oh().a(new zd_2((yp_2)je_1.Wa().el(((aJt)object).Qx()[j])));
                }
            }
            catch (Exception exception) {
                a.error((Object)"Erreur lors de l'ajout d'un sort \u00e0 un SummonedFighter :", (Throwable)exception);
            }
        }
        if (((asw_0)object).op()) {
            this.PL().a(avx_0.deA);
        }
        if (((asw_0)object).aFx()) {
            this.PL().a(avx_0.deB);
        }
        if (((asw_0)object).or()) {
            this.PL().a(avx_0.dev);
        }
        if (((asw_0)object).os()) {
            this.PL().a(avx_0.dex);
        }
        if (((asw_0)object).ov()) {
            this.PL().a(avx_0.deD);
        }
        if (((asw_0)object).oy()) {
            this.PL().a(avx_0.deF);
        }
        this.DA = ((asw_0)object).ox();
    }

    public byte ox() {
        return this.DA;
    }
}

