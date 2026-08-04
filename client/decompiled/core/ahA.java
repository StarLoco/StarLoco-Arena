/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.EntitySprite;
import com.ankamagames.framework.graphics.engine.opengl.GLGeometrySprite;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.media.opengl.GL;

public final class ahA {
    public static final boolean cvY = true;
    public awD cvZ;
    public float cwa;
    public float fG;
    public EntitySprite cwb;
    public EntitySprite cwc;
    public QI cwd = new QI("ELAPSED_TIME", mr_0.JN);
    private static final ahA cwe = new ahA();
    private HashMap cwf = new HashMap(16);
    private HashMap cwg = new HashMap(16);
    private lb_0 cwh = new lb_0();
    private lb_0 cwi = new lb_0();
    private lb_0 cwj = new lb_0();
    private ArrayList cwk = new ArrayList();
    private ef_1 cwl;
    private boolean cwm = false;
    private boolean cwn = false;
    public GLGeometrySprite cwo;

    public static ahA axi() {
        return cwe;
    }

    public void a(awD awD2) {
        this.cvZ = awD2;
    }

    public final void j(String string, String string2, String string3) {
        this.cwg.put(string, string2);
        asr_0 asr_02 = this.cvZ.jW(string3);
        asr_02.setName(string);
        this.cwf.put(asr_02.getName(), asr_02);
    }

    public final void a(asr_0 asr_02) {
        this.cwf.put(asr_02.getName(), asr_02);
    }

    public final void ig(String string) {
        this.cwf.remove(string);
    }

    public final asr_0 ih(String string) {
        return (asr_0)this.cwf.get(string);
    }

    public final void axj() {
        for (asr_0 asr_02 : this.cwf.values()) {
            asr_02.reset();
        }
    }

    public final boolean kK(int n2) {
        if (!this.cwm) {
            return false;
        }
        for (asr_0 asr_02 : this.cwf.values()) {
            if (!asr_02.kK(n2)) continue;
            return true;
        }
        return false;
    }

    public final void axk() {
        Set set = this.cwg.entrySet();
        for (Map.Entry entry : set) {
            asr_0 asr_02 = this.ih((String)entry.getKey());
            asr_02.l((String)entry.getKey(), (String)entry.getValue());
        }
        this.cwg = null;
        sz_0.a(null);
        this.cwb.b(this.ih("transform"));
    }

    public final void a(float f) {
        Object object;
        Object object2 = this.cwf.values().iterator();
        while (object2.hasNext()) {
            ((asr_0)object2.next()).aFR();
        }
        this.cwa = f;
        this.fG += f;
        this.cwd.bHy[0] = this.fG;
        object2 = this.cwh.pK();
        while (((aiz_1)object2).hasNext()) {
            ((ll_0)object2).fK();
            object = (avw)((ll_0)object2).value();
            if (!((avw)object).isEnabled() || !((avw)object).aiW()) continue;
            ((avw)object).a(f);
        }
        object = this.cwj.pK();
        while (((aiz_1)object).hasNext()) {
            ((ll_0)object).fK();
            this.cwi.c(((ll_0)object).kR(), ((ll_0)object).value());
        }
        this.cwj.clear();
        object = this.cwi.pK();
        while (((aiz_1)object).hasNext()) {
            ((ll_0)object).fK();
            aBX aBX2 = (aBX)((ll_0)object).value();
            if (aBX2.aiW()) {
                aBX2.a(f);
                continue;
            }
            aBX2.clear();
            ((aiz_1)object).remove();
        }
    }

    public final void dx(boolean bl2) {
        this.cwm = bl2;
    }

    public final boolean axl() {
        return this.cwm;
    }

    public final void a(avw avw2) {
        assert (avw2 != null);
        this.cwh.c(avw2.getID(), avw2);
    }

    public final void b(avw avw2) {
        this.cwh.remove(avw2.getID());
    }

    public final avw kL(int n2) {
        return (avw)this.cwh.get(n2);
    }

    public final void a(aBX aBX2) {
        assert (aBX2 != null);
        this.cwj.c(aBX2.getId(), aBX2);
    }

    public final void b(aBX aBX2) {
        this.cwi.remove(aBX2.getId());
        this.cwj.remove(aBX2.getId());
    }

    public final aBX kM(int n2) {
        aBX aBX2 = (aBX)this.cwi.get(n2);
        if (aBX2 != null) {
            return aBX2;
        }
        return (aBX)this.cwj.get(n2);
    }

    public final boolean r(db_2 db_22) {
        if (!db_22.vj() || !this.cwm || this.cwn) {
            return false;
        }
        ll_0 ll_02 = this.cwh.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            avw avw2 = (avw)ll_02.value();
            if (!avw2.aiW() || !avw2.isEnabled()) continue;
            return true;
        }
        return false;
    }

    public final void bs(int n2, int n3) {
        db_2 db_22 = arX.cQT.iE();
        if (!db_22.vj()) {
            return;
        }
        this.axn();
        this.cwl = this.bt(n2, n3);
        if (this.cwl == null) {
            this.axn();
            this.cwn = true;
        }
        this.cwb.setSize(n2, n3);
        this.cwb.x(n3 / 2, -n2 / 2);
        this.cwc.setSize(n2, n3);
        this.cwc.x(n3 / 2, -n2 / 2);
    }

    public final void a(Ir ir, db_2 db_22) {
        if (!this.r(db_22)) {
            ir.a(db_22);
            this.s(db_22);
        } else {
            this.a(ir);
        }
    }

    public final ef_1 bt(int n2, int n3) {
        int n4 = this.cwk.size();
        for (int j = 0; j < n4; ++j) {
            ef_1 ef_12 = (ef_1)this.cwk.get(j);
            kf_0 kf_02 = ef_12.lB(0);
            if (kf_02.getWidth() != n2 || kf_02.getHeight() != n3 || ef_12.MJ() || ef_12 == this.cwl) continue;
            return ef_12;
        }
        return this.bu(n2, n3);
    }

    public final ef_1 axm() {
        return this.cwl;
    }

    private ahA() {
        this.cwb = new EntitySprite();
        this.cwo = new GLGeometrySprite();
        this.cwb.a(this.cwo);
        this.cwb.setMaterial(aPb.enf);
        this.cwb.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.cwb.k(1.0f, 0.0f, 0.0f, 1.0f);
        this.cwc = new EntitySprite();
        this.cwc.a(new GLGeometrySprite());
        this.cwc.setMaterial(aPb.enf);
        this.cwc.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        this.cwc.k(1.0f, 0.0f, 0.0f, 1.0f);
    }

    private void s(db_2 db_22) {
        ll_0 ll_02 = this.cwi.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            aBX aBX2 = (aBX)ll_02.value();
            if (!aBX2.aiW()) continue;
            aBX2.a(db_22);
        }
    }

    private void a(Ir ir) {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        GL gL = (GL)qp_22.LV();
        vo_1 vo_12 = vo_1.aik();
        qp_22.b(this.cwl);
        vo_12.ir(-1);
        gL.glClearStencil(0);
        gL.glClear(17408);
        vo_12.n(qp_22);
        ir.a(qp_22);
        this.s(qp_22);
        Matrix44 matrix44 = qp_22.LU();
        qp_22.c(Matrix44.bEn);
        ll_0 ll_02 = this.cwh.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            avw avw2 = (avw)ll_02.value();
            ef_1 ef_12 = avw2.a(qp_22, gL);
            if (ef_12 == null) continue;
            this.cwl = ef_12;
        }
        qp_22.b((ef_1)null);
        gL.glViewport(0, 0, this.cwl.lB(0).getWidth(), this.cwl.lB(0).getHeight());
        this.cwo.a(air.cya, air.cxZ);
        this.cwb.setTexture(this.cwl);
        this.cwb.oM(587111861);
        this.cwb.at(1.0f);
        this.cwb.aIc = null;
        this.cwb.aId = null;
        this.cwb.a(qp_22);
        this.cwo.a(air.cyd, air.cye);
        qp_22.c(matrix44);
    }

    private ef_1 bu(int n2, int n3) {
        db_2 db_22 = arX.cQT.iE();
        ef_1 ef_12 = db_22.a(yh_0.FD(), n2, n3, false);
        ef_12.e(db_22);
        if (!ef_12.is()) {
            ef_12.HF();
            ef_12.HF();
            return null;
        }
        ef_12.HE();
        this.cwk.add(ef_12);
        return ef_12;
    }

    private void axn() {
        int n2 = this.cwk.size();
        for (int j = 0; j < n2; ++j) {
            ef_1 ef_12 = (ef_1)this.cwk.get(j);
            ef_12.HF();
            ef_12.HF();
        }
        this.cwk.clear();
    }
}

