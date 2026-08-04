/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.RenderTreeStencil;
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import com.ankamagames.framework.graphics.engine.fx.FixedPipeline.Water;
import com.ankamagames.framework.graphics.engine.text.EntityText;
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import org.apache.log4j.Logger;

/*
 * Renamed from qs
 */
public abstract class qs_2
extends aba_2
implements ala,
eo_2,
wi_1 {
    private static final Logger a = Logger.getLogger(qs_2.class);
    public static final String aea = "contents/gfx";
    public static final String aeb = "contents/sounds";
    protected String aec = "contents/gfx";
    protected String aed = "contents/sounds";
    private int bk;
    private int bl;
    private static float aee = 1.1f;
    protected static float aef = 1.4f;
    private float aeg = aee;
    private float aeh = aef;
    protected final ArrayList aei = new ArrayList();
    private boolean aej = false;
    private final ry aek = new ry(Integer.MIN_VALUE, Integer.MIN_VALUE, Short.MIN_VALUE);
    private mp_0[] ael = null;
    private static final avz aem = new avz();
    protected final Ir aen;
    private final Water aeo = new Water();
    protected final aga_0 aep;
    protected boolean aeq = false;
    boolean aer = false;
    boolean aes = false;
    protected final vP aet = new vP();
    protected boolean aeu = false;
    private boolean aev;
    private final ArrayList aew = new ArrayList(128);
    private final agu_0 aex = new agu_0();
    private final agu_0 aey = new agu_0();
    private dc_0 aez = null;
    private boolean aeA = false;
    private final hl_0 aeB = new hl_0(null);

    public qs_2() {
        this(new RenderTreeStencil(), aga_0.aSG());
    }

    protected qs_2(Ir ir, aga_0 aga_02) {
        this.a(bd_1.Is());
        this.a(qd_1.uW());
        this.a(wn_2.Dj());
        this.a(ahq_0.awW());
        this.a(wj_2.Df());
        this.aen = ir;
        this.aep = aga_02;
    }

    protected void vm() {
        this.dsa = new aw_0(this, this);
    }

    public final YR vn() {
        return (YR)super.vC();
    }

    public void an(boolean bl2) {
        this.aej = bl2;
    }

    public void b(mp_0[] mp_0Array) {
        this.ael = mp_0Array;
    }

    public void bD(String string) {
        this.aec = string;
        alr_1.aAO().bD(string);
    }

    public void bE(String string) {
        arp.aEu().setFile(string);
        arp.aEu().load();
    }

    public int au() {
        return this.bk;
    }

    public int av() {
        return this.bl;
    }

    public void a(aHq aHq2) {
        this.aei.add(aHq2);
    }

    public void b(aHq aHq2) {
        this.aei.remove(aHq2);
    }

    public void uninitialize() {
        this.ao(true);
        super.uninitialize();
    }

    protected final void vo() {
        a.debug((Object)("addReferences " + this.drZ.size() + " fading=" + this.aev));
        assert (!this.aev || this.drZ.size() == 0);
        this.aev = true;
        for (int j = this.drZ.size() - 1; j >= 0; --j) {
            ((Entity)this.drZ.get(j)).HE();
        }
    }

    protected final void vp() {
        a.debug((Object)("removeReferences " + this.drZ.size() + " fading=" + this.aev));
        if (!this.aev) {
            return;
        }
        this.aev = false;
        for (int j = this.drZ.size() - 1; j >= 0; --j) {
            ((Entity)this.drZ.get(j)).HF();
        }
    }

    public void ao(boolean bl2) {
        this.bk(false);
        this.a(vP.atM);
        if (yb_2.amk().aml()) {
            this.vo();
        } else {
            this.aen.clear();
        }
        this.vq();
        this.aep.clear();
        this.bq();
        this.an(true);
        this.drX.clear();
        this.csb.clear();
        this.aeq = false;
    }

    protected void vq() {
        mg_1.qV().clear();
        bd_1.Is().Iu();
        qd_1.uW().uY();
        wj_2.Df().clear();
        ahn_0.dNL.reset();
        qi_1.vV().clear();
        dt_0.lH.clear();
    }

    public void init(GLAutoDrawable gLAutoDrawable) {
        this.aer = true;
        super.init(gLAutoDrawable);
        this.a(bu_0.cO());
        this.b(aux__0.aHL());
        this.vr();
    }

    public void P(int n2, int n3) {
        if (this.bIz != (float)n2 || this.bIA != (float)n3) {
            super.P(n2, n3);
        }
        arX.cQT.iE().an(n2, n3);
        this.dsa.bT(n2, n3);
        ahA.axi().bs(n2, n3);
    }

    public void vr() {
        if (this.dsa != null) {
            this.aeq = false;
        }
    }

    public void vs() {
        super.vs();
        this.aeq = false;
    }

    private void vt() {
        this.aen.clear();
        int n2 = this.drZ.size();
        for (int j = 0; j < n2; ++j) {
            this.aen.a((Entity)this.drZ.get(j), 0);
        }
        if (this.aen instanceof RenderTreeStencil) {
            amo_2.cGH.c((RenderTreeStencil)this.aen);
        }
    }

    public void bI(int n2) {
        int n3;
        if (!this.cT(n2)) {
            return;
        }
        YR yR = this.vn();
        yR.bI(n2);
        int n4 = (int)Math.floor(yR.oV());
        int n5 = (int)Math.floor(yR.oW());
        xx_1.p(n4, n5, 2);
        int n6 = this.aei.size();
        for (int j = 0; j < n6; ++j) {
            ((aHq)this.aei.get(j)).a(this, n2);
        }
        this.aq(this.aej);
        ado_0.aPH().a(n2);
        this.cS(n2);
        this.cU(n2);
        float f = this.vn().aEK();
        for (n3 = 0; n3 < this.csb.size(); ++n3) {
            Entity entity = (Entity)this.csb.get(n3);
            if (!(entity instanceof EntityText)) continue;
            ((EntityText)entity).c(f, f);
        }
        this.vu();
        this.vt();
        this.vv();
        for (n3 = 0; n3 < this.csb.size(); ++n3) {
            this.aen.c((Entity)this.csb.get(n3));
        }
        this.drX.clear();
        this.csb.clear();
        ahA.axi().a((float)n2 / 1000.0f);
    }

    protected final void vu() {
        int n2;
        this.drY.nl();
        int n3 = this.drX.size();
        assert (n3 < 8191) : "On ne peut pas trier plus de 8191 entit\u00e9s";
        for (n2 = 0; n2 < n3; ++n2) {
            Entity entity = (Entity)this.drX.get(n2);
            if (entity.avb() < 0) continue;
            long l2 = entity.dPx;
            assert (l2 >= 0L && l2 < 0x4000000000000L);
            this.drY.ct((l2 << 13) + (long)n2);
        }
        this.drY.sort();
        this.drZ.clear();
        n2 = this.drY.size();
        for (int j = 0; j < n2; ++j) {
            int n4 = (int)(this.drY.hn(j) & 0x1FFFL);
            assert (n4 >= 0);
            Entity entity = (Entity)this.drX.get(n4);
            this.a(entity);
        }
    }

    protected void a(Entity entity) {
        this.drZ.add(entity);
    }

    protected final void cS(int n2) {
        this.aep.b(this, n2);
        this.bq();
        this.p((float)this.dsa.oV(), (float)this.dsa.oW());
    }

    protected final boolean cT(int n2) {
        if (!this.isInitialized()) {
            this.drZ.clear();
            return false;
        }
        if (this.dsa == null) {
            return false;
        }
        if (yb_2.amk().aml()) {
            return false;
        }
        if (yb_2.amk().amm()) {
            this.dsa.bI(n2);
            this.aq(this.aej);
            return false;
        }
        return true;
    }

    protected void cU(int n2) {
        ahn_0.dNL.c(this, n2);
    }

    private void vv() {
        int n2;
        ams_2 ams_22;
        for (int j = 0; j < this.aew.size(); ++j) {
            ams_22 = (Entity)this.aew.get(j);
            if (ams_22.avb() < 0) continue;
            if (ams_22 instanceof EntityGroup) {
                EntityGroup entityGroup = (EntityGroup)ams_22;
                ArrayList arrayList = entityGroup.aUK();
                for (n2 = 0; n2 < arrayList.size(); ++n2) {
                    Entity entity = (Entity)arrayList.get(n2);
                    entity.oM(-180157682);
                    ((Entity)ams_22).a(ub_0.bPW);
                    ((Entity)ams_22).b(ub_0.bPW);
                }
                continue;
            }
            ((Entity)ams_22).oM(-180157682);
            ((Entity)ams_22).a(ub_0.bPW);
            ((Entity)ams_22).b(ub_0.bPW);
        }
        this.aew.clear();
        db_2 db_22 = arX.cQT.iE();
        this.c(db_22);
        ams_22 = db_22.LU();
        boolean bl2 = !ahA.axi().kK(-98564371);
        int n3 = this.dsa.getScreenWidth();
        n2 = this.dsa.getScreenHeight();
        float f = (float)this.dsa.oZ();
        float f2 = f / (float)n3;
        float f3 = f / (float)n2;
        int n4 = 0;
        int n5 = this.drZ.size();
        for (int j = 0; j < n5; ++j) {
            Entity entity;
            int n6;
            int n7;
            Entity entity2 = (Entity)this.drZ.get(j);
            if ((entity2.dPB & 1) == 0) continue;
            int n8 = entity2.EP;
            int n9 = entity2.EQ;
            int n10 = entity2.EN;
            int n11 = entity2.EO;
            float f4 = entity2.dPy + entity2.dPz;
            float f5 = (float)(entity2.EN + entity2.EP) / 2.0f;
            float f6 = (float)(entity2.EO + entity2.EQ) / 2.0f;
            float f7 = entity2.dPA + entity2.bsF * 0.666f;
            this.aex.d(f5, f6, 0.0f, 1.0f);
            ((Matrix44)ams_22).b(this.aex, this.aey);
            this.aey.Hk *= 2.0f / (float)n3;
            this.aey.Hl *= 2.0f / (float)n2;
            for (n7 = j + 1; n7 < n5; ++n7) {
                Entity entity3 = (Entity)this.drZ.get(n7);
                if ((entity3.dPB & 2) == 0 || entity3.EN >= n8 || entity3.EO >= n9 || entity3.EP <= n10 || entity3.EQ <= n11 || entity3.dPA + entity3.bsF <= f7 || entity3.dPy + entity3.dPz <= f4) continue;
                this.aew.add(entity3);
            }
            n7 = this.aew.size();
            if (n7 - n4 <= 0) continue;
            if (!bl2) {
                float f8 = 2 * (n8 - n10 - 1);
                float f9 = 1.5f * (float)(n9 - n11 - 1);
                float f10 = f8 * f2;
                float f11 = f9 * f3;
                for (n6 = n4; n6 < n7; ++n6) {
                    entity = (Entity)this.aew.get(n6);
                    if (entity instanceof EntityGroup) {
                        EntityGroup entityGroup = (EntityGroup)entity;
                        ArrayList arrayList = entityGroup.aUK();
                        for (int i2 = 0; i2 < arrayList.size(); ++i2) {
                            Entity entity4 = (Entity)arrayList.get(i2);
                            entity4.oM(-360637107);
                            entity4.dPC.bHy[4] = f10;
                            entity4.dPC.bHy[5] = f11;
                            entity4.dPC.bHy[6] = this.aey.Hk;
                            entity4.dPC.bHy[7] = this.aey.Hl;
                        }
                        continue;
                    }
                    entity.oM(-98564371);
                    entity.dPC.bHy[4] = f10;
                    entity.dPC.bHy[5] = f11;
                    entity.dPC.bHy[6] = this.aey.Hk;
                    entity.dPC.bHy[7] = this.aey.Hl;
                }
            } else {
                for (int i3 = n4; i3 < n7; ++i3) {
                    Entity entity5 = (Entity)this.aew.get(i3);
                    entity5.a(agd_0.ctE);
                    entity5.b(alq_1.cFf);
                    if (entity5 instanceof EntityGroup) {
                        EntityGroup entityGroup = (EntityGroup)entity5;
                        ArrayList arrayList = entityGroup.aUK();
                        for (n6 = 0; n6 < arrayList.size(); ++n6) {
                            entity = (Entity)arrayList.get(n6);
                            entity.a(agd_0.ctE);
                            entity.b(alq_1.cFf);
                        }
                        continue;
                    }
                    entity5.a(agd_0.ctE);
                    entity5.b(alq_1.cFf);
                }
            }
            n4 = this.aew.size();
        }
    }

    private void g(GL gL) {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        qp_22.adV.nO(0);
        vo_1 vo_12 = vo_1.aik();
        vo_12.cr(true);
        vo_12.a(jq_0.bmI);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        vo_12.a(jq_0.bmH);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        vo_12.a(jq_0.bmG);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        TV tV = this.Ni();
        int n2 = (int)tV.getX();
        int n3 = (int)tV.getY();
        int n4 = (int)tV.getWidth();
        int n5 = (int)tV.getHeight();
        float f = (float)(tV.getWidth() - tV.getX()) / 2.0f;
        float f2 = (float)(tV.getHeight() - tV.getY()) / 2.0f;
        gL.glViewport(n2, n3, n4, n5);
        if (f < 1.0f) {
            f = 1.0f;
        }
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        gL.glOrtho(-f, f, -f2, f2, 0.0, 65535.0);
        this.c(qp_22);
        vo_1.aik().reset();
        ahA.axi().axj();
    }

    private void c(db_2 db_22) {
        if (!yb_2.amk().aml()) {
            ari_0 ari_02 = this.aNB();
            float f = ari_02.aEK();
            float f2 = (float)(-ari_02.oX()) * f;
            float f3 = (float)(-ari_02.oY()) * f;
            aem.OH();
            if (this.aes) {
                aem.m(-f, f, 1.0f);
                aem.e(-f2, f3, 0.0f);
            } else {
                aem.m(f, f, 1.0f);
                aem.e(f2, f3, 0.0f);
            }
        }
        db_22.c(aem.ki());
    }

    private void endRendering() {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        if (qp_22.LW()) {
            qp_22.cO(0);
        }
        this.drX.clear();
        this.csb.clear();
    }

    private void drawAll() {
        qp_2 qp_22 = (qp_2)arX.cQT.iE();
        this.aeo.a(qp_22);
        this.aen.ar((int)this.Ni().getWidth(), (int)this.Ni().getHeight());
        ahA.axi().a(this.aen, qp_22);
    }

    public void h(GL gL) {
        if (!this.aer) {
            return;
        }
        this.g(gL);
        this.drawAll();
        this.endRendering();
    }

    private void b(YR yR) {
        boolean bl2 = this.c(yR);
        if (this.aeA == bl2) {
            return;
        }
        this.ap(bl2);
        this.aeA = bl2;
    }

    protected void ap(boolean bl2) {
        YR yR = this.vn();
        if (bl2) {
            this.e(yR);
        } else {
            this.d(yR);
        }
    }

    private boolean c(YR yR) {
        int n2 = yR.gn();
        int n3 = yR.go();
        int n4 = 0;
        if (this.aNC() != null) {
            n4 = (int)Math.ceil(this.aNC().getAltitude() + 0.5);
        }
        if (this.aek.k(n2, n3, n4) && this.aep.aSL()) {
            return this.aeA;
        }
        this.aek.l(n2, n3, (short)n4);
        DisplayedScreenElement displayedScreenElement = this.aep.e(n2, n3, n4, pq_2.abX);
        if (displayedScreenElement == null) {
            yR.bf(0, 0);
            return false;
        }
        int n5 = displayedScreenElement.gn();
        int n6 = displayedScreenElement.go();
        int n7 = displayedScreenElement.Ge();
        yR.bf(n7, displayedScreenElement.amZ());
        boolean bl2 = false;
        if (n7 == 0) {
            bl2 = false;
        } else {
            if (this.aez == null || !this.aez.Ls().F(n5, n6)) {
                this.aez = auU.bW(n5, n6);
            }
            if (this.aez == null) {
                a.error((Object)("pas de map topologique aux coordonn\u00e9es " + n5 + " " + n6));
                return this.aeA;
            }
            short s = displayedScreenElement.gp();
            int n8 = this.aez.s(n5, n6, s);
            switch (n8) {
                case 128: {
                    bl2 = true;
                    break;
                }
                case 0: {
                    bl2 = false;
                    break;
                }
                case 192: {
                    bl2 = true;
                    break;
                }
                case 64: {
                    bl2 = false;
                    break;
                }
                default: {
                    a.error((Object)("type de mur inconnu " + n8));
                }
            }
        }
        return bl2;
    }

    public void aq(boolean bl2) {
        YR yR = this.vn();
        if (!this.aeq) {
            this.aeq = true;
        }
        this.aej = false;
        this.b(yR);
        this.aep.b(yR.aEO());
    }

    public abstract void a(int var1, acy_1 var2);

    protected void p(float f, float f2) {
        int n2 = this.aei.size();
        for (int j = 0; j < n2; ++j) {
            ((aHq)this.aei.get(j)).a(this, f, f2);
        }
        this.aep.c(this);
    }

    public ArrayList a(double d, double d2, float f, ma_0 ma_02) {
        return this.a(d, d2, f, ma_02, 0.0f, 0.0f);
    }

    public ArrayList a(double d, double d2, float f, ma_0 ma_02, float f2, float f3) {
        if (this.dsa == null) {
            return null;
        }
        int n2 = (int)(this.J((float)d) + (float)this.dsa.getScreenX() - f2);
        int n3 = (int)(this.K((float)d2) + (float)this.dsa.getScreenY() - f3);
        ArrayList arrayList = new ArrayList();
        this.aep.a(n2, n3, arrayList);
        int n4 = 0;
        while (n4 < arrayList.size()) {
            DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(n4);
            if (!displayedScreenElement.isVisible()) {
                arrayList.remove(n4);
                continue;
            }
            ++n4;
        }
        ma_02.a(arrayList, new ayZ(this, f, n2, n3));
        return arrayList;
    }

    public float H(float f) {
        if (this.aes) {
            return this.bIz / 2.0f - f * this.dsa.aEK();
        }
        return this.bIz / 2.0f + f * this.dsa.aEK();
    }

    public float I(float f) {
        return this.bIA / 2.0f + f * this.dsa.aEK();
    }

    public float J(float f) {
        if (this.aes) {
            return (-f + this.bIz / 2.0f) / this.dsa.aEK();
        }
        return (f - this.bIz / 2.0f) / this.dsa.aEK();
    }

    public float K(float f) {
        return (this.bIA / 2.0f - f) / this.dsa.aEK();
    }

    public ArrayList c(double d, double d2) {
        if (this.dsa == null) {
            return null;
        }
        return bd_1.Is().t(this.J((float)d), this.K((float)d2));
    }

    public int d(double d, double d2) {
        if (this.dsa == null) {
            return 0;
        }
        return bd_1.Is().C(this.J((float)d), this.K((float)d2));
    }

    public float vw() {
        return this.aeg;
    }

    public float vx() {
        return this.aeh;
    }

    public void L(float f) {
        this.aeg = f;
    }

    public void M(float f) {
        this.aeh = f;
    }

    public void ar(boolean bl2) {
        this.aeo.enable(bl2);
    }

    public void d(YR yR) {
        if (yR == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/display/AleaWorldScene.resetToDefaultOutdoorZoomFactor must not be null");
        }
        if (yR.Ft() > (double)this.aeg) {
            yR.k(this.aeg);
        }
    }

    public void e(YR yR) {
        if (yR == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/display/AleaWorldScene.resetToDefaultIndoorZoomFactor must not be null");
        }
        if (yR.Ft() < (double)this.aeh) {
            yR.k(this.aeh);
        }
    }

    public void c(List list) {
        for (String string : list) {
            if (!string.equalsIgnoreCase("playSound") && !string.equalsIgnoreCase("gotoStaticAnimation")) continue;
            Ky.WG().c(string, this.ael, true);
        }
    }

    public abstract boolean b(MouseEvent var1);

    public abstract boolean mousePressed(MouseEvent var1);

    public abstract boolean c(MouseEvent var1);

    public abstract boolean d(MouseEvent var1);

    public abstract boolean e(MouseEvent var1);

    public abstract boolean f(MouseEvent var1);

    public boolean g(MouseEvent mouseEvent) {
        this.bk = mouseEvent.getX();
        this.bl = mouseEvent.getY();
        return false;
    }

    public abstract boolean a(MouseWheelEvent var1);

    public abstract boolean a(KeyEvent var1);

    public abstract boolean b(KeyEvent var1);

    public abstract boolean c(KeyEvent var1);

    public String toString() {
        return "zoom=" + this.dsa.aEK() + ", " + super.toString();
    }

    public long b(int n2, int n3, float f, float f2) {
        DisplayedScreenElement displayedScreenElement = this.aep.e(n2, n3, (int)f, pq_2.abV);
        return qs_2.a(displayedScreenElement, n2, n3, f2);
    }

    public boolean a(xw_0 xw_02, Entity entity, int n2, int n3, float f, float f2) {
        this.aeB.a(this.aep, n2, n3, (int)f);
        entity.dPx = qs_2.a(hl_0.b(this.aeB), n2, n3, f2);
        ajh_2.a(xw_02, hl_0.c(this.aeB));
        return hl_0.c(this.aeB) != null;
    }

    private static long a(DisplayedScreenElement displayedScreenElement, int n2, int n3, float f) {
        if (displayedScreenElement == null) {
            return qs_2.a(n2, n3, f);
        }
        return qs_2.a(n2, n3, (float)displayedScreenElement.atV().cts + f);
    }

    public abstract boolean a(FocusEvent var1);

    public abstract boolean b(FocusEvent var1);

    public void a(vP vP2) {
        if (this.aet.Cf() == vP2.Cf()) {
            return;
        }
        this.aet.set(vP2.Cf());
        this.aeu = true;
    }

    public boolean vy() {
        return this.aep.is();
    }

    public final void vz() {
        this.vp();
        this.aen.clear();
    }

    public final void vA() {
        YR yR = this.vn();
        this.aeA = this.c(yR);
        yR.l(this.aeA ? (double)this.aeh : (double)this.aeg);
    }

    public aga_0 vB() {
        return this.aep;
    }
}

