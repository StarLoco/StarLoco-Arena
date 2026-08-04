/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.alea.display.DisplayedScreenElement;
import com.ankamagames.baseImpl.graphics.alea.display.ScreenElement;
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import com.ankamagames.framework.graphics.engine.opengl.text.GLGeometryBackground;
import java.util.ArrayList;

/*
 * Renamed from ae
 */
public class ae_2
implements aHq,
atG {
    private static final String bE = "highlightLayer";
    private static final float[] bF = new float[]{1.0f, 0.0f, 0.0f, 0.5f};
    private static final float[] bG = new float[]{0.0f, 0.0f, 1.0f, 0.5f};
    private static final ae_2 bH = new ae_2();
    private ry bI = null;
    private final ry bJ = new ry();
    long bK;
    long bL;
    final ro_0 bM = new ro_0();
    final ee_2 bN = new ee_2();
    final rx_2 bO = new rx_2();
    final amz_2 bP = new amz_2(abw_1.e("SansSerif", 0, 12), true, "0");

    public ae_2() {
        this.bM.aM(1.0f);
        this.bM.c(0.0f, 0.0f, 1.0f);
        GLGeometryBackground gLGeometryBackground = new GLGeometryBackground();
        gLGeometryBackground.setColor(0.0f, 0.0f, 1.0f, 0.5f);
        gLGeometryBackground.a(this.bO.wZ(), this.bO.xa());
        gLGeometryBackground.e(this.bO.xc());
        gLGeometryBackground.f(this.bO.xb());
        gLGeometryBackground.e(this.bO.wW(), this.bO.wX(), this.bO.wY(), this.bO.wV());
        this.bP.ap().a(gLGeometryBackground);
        this.bP.ap().setColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static ae_2 az() {
        return bH;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 30001: {
                int n2;
                int n3;
                abu_1 abu_12 = (abu_1)pr_02;
                if (this.bI == null) {
                    return false;
                }
                float f = abu_12.au();
                float f2 = abu_12.av();
                qs_2 qs_22 = DofusArenaClientInstance.yl().YP();
                ArrayList arrayList = qs_22.a(f, f2, 0.0f, ma_0.buh);
                if (arrayList == null || arrayList.size() == 0) {
                    return false;
                }
                DisplayedScreenElement displayedScreenElement = (DisplayedScreenElement)arrayList.get(0);
                ScreenElement screenElement = displayedScreenElement.atV();
                aoq_0 aoq_02 = apN.aDK().aDL().gV();
                if (!aoq_02.F(n3 = screenElement.avV(), n2 = screenElement.avW())) {
                    return false;
                }
                short s = aoq_02.bL(n3, n2);
                if (s == Short.MIN_VALUE) {
                    return false;
                }
                if (this.bJ.k(n3, n2, (int)s)) {
                    return false;
                }
                if (this.bK != this.bL) {
                    wn_2.Dj().b(this.bK, bE);
                }
                this.bJ.l(n3, n2, s);
                float f3 = this.bJ.getX();
                float f4 = this.bJ.getY();
                float f5 = (f3 + (float)this.bI.getX()) / 2.0f;
                float f6 = (f4 + (float)this.bI.getY()) / 2.0f;
                this.bM.z((float)qs_22.i(f3, f4), (float)qs_22.j(f3, f4) + (float)((double)this.bJ.wk() * qs_22.aNA()));
                this.bP.ap().a(new agu_0((float)qs_22.i(f5, f6), (float)qs_22.j(f5, f6), 0.0f));
                this.bP.ap().aj(0, 0);
                this.bP.setText(Integer.toString(this.a(this.bI, this.bJ)));
                DisplayedScreenElement displayedScreenElement2 = aga_0.aSG().a((int)f3, (int)f4, pq_2.abX);
                this.bK = displayedScreenElement2.aua();
                if (this.bK != this.bL) {
                    wn_2.Dj().a(this.bK, bE);
                } else {
                    this.bK = this.bL;
                    this.bJ.g(this.bI);
                }
                float f7 = 0.8f;
                short s2 = (short)((float)this.bN.PE() * 0.8f);
                ry ry2 = new ry(this.bI);
                ry ry3 = new ry(this.bJ);
                ry2.T((short)(ry2.wk() + s2));
                ry3.T((short)(ry3.wk() + s2));
                ahc_2 ahc_22 = ahc_2.axo();
                ahc_22.z(ry2);
                ahc_22.A(ry3);
                ahc_22.a(aoq_02);
                aaR aaR2 = wn_2.Dj().cJ(bE);
                if (ahc_22.axq()) {
                    aaR2.q(bG);
                    this.bM.c(bG[0], bG[1], bG[2]);
                    this.bP.ap().n(bG[0], bG[1], bG[2], bG[3]);
                } else {
                    aaR2.q(bF);
                    this.bM.c(bF[0], bF[1], bF[2]);
                    this.bP.ap().n(bF[0], bF[1], bF[2], bF[3]);
                }
                return false;
            }
            case 30000: {
                ado ado2 = (ado)pr_02;
                if (ado2.aqY() != 1) {
                    return false;
                }
                if (this.bI == null) {
                    short s;
                    float f = ado2.au();
                    float f8 = ado2.av();
                    qs_2 qs_23 = DofusArenaClientInstance.yl().YP();
                    ArrayList arrayList = qs_23.a(f, f8, 0.0f, ma_0.buh);
                    if (arrayList == null || arrayList.size() == 0) {
                        return false;
                    }
                    ScreenElement screenElement = ((DisplayedScreenElement)arrayList.get(0)).atV();
                    int n4 = screenElement.avV();
                    int n5 = screenElement.avW();
                    aoq_0 aoq_03 = apN.aDK().aDL().gV();
                    boolean bl2 = true;
                    if (!aoq_03.F(n4, n5)) {
                        bl2 = false;
                    }
                    if (bl2 &= (s = aoq_03.bL(n4, n5)) != Short.MIN_VALUE) {
                        this.bI = screenElement.avX();
                        this.bJ.g(this.bI);
                        float f9 = this.bI.getX();
                        float f10 = this.bI.getY();
                        try {
                            aaR aaR3 = wn_2.Dj().cH(bE);
                            aaR3.q(bG);
                            DisplayedScreenElement displayedScreenElement = aga_0.aSG().a((int)f9, (int)f10, pq_2.abX);
                            this.bL = displayedScreenElement.aua();
                            wn_2.Dj().a(this.bL, bE);
                        }
                        catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        short s3 = this.bI.wk();
                        this.bM.A((float)qs_23.i(f9, f10), (float)qs_23.j(f9, f10) + (float)((double)s3 * qs_23.aNA()));
                        this.bM.z((float)qs_23.i(f9, f10), (float)qs_23.j(f9, f10) + (float)((double)s3 * qs_23.aNA()));
                        this.bP.setText("0");
                        this.bP.ap().a(new agu_0((float)qs_23.i(f9, f10), (float)qs_23.j(f9, f10), 0.0f));
                        this.bP.ap().aj(0, 0);
                    } else {
                        this.clear();
                    }
                } else {
                    this.clear();
                    apN.aDK().b(ae_2.az());
                    if (apN.aDK().aDL().Zy() != ko_2.bpt) {
                        apN.aDK().a(S.as());
                    }
                    if (!apN.aDK().c(anx_1.aXx())) {
                        ((xu_2)DofusArenaClientInstance.yl().YP()).cD(false);
                    }
                }
                return false;
            }
        }
        return true;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        DofusArenaClientInstance.yl().YP().a(this);
        this.clear();
    }

    public void b(fh_2 fh_22, boolean bl2) {
        DofusArenaClientInstance.yl().YP().b(this);
        this.clear();
    }

    private void clear() {
        this.bM.A(0.0f, 0.0f);
        this.bM.z(0.0f, 0.0f);
        this.bI = null;
        this.bK = 0L;
        this.bL = 0L;
        wn_2.Dj().cI(bE);
    }

    protected ry c(int n2, int n3) {
        boolean bl2 = true;
        if (apN.aDK().c(avu_0.aIB())) {
            bl2 = !avu_0.aIB().aIC();
        }
        return MJ.a(DofusArenaClientInstance.yl().YP(), n2, n3, bl2);
    }

    protected int a(ry ry2, ry ry3) {
        return Math.abs(ry3.getX() - ry2.getX()) + Math.abs(ry3.getY() - ry2.getY());
    }

    public void a(qs_2 qs_22, int n2) {
    }

    public void a(qs_2 qs_22, float f, float f2) {
        if (this.bI == null) {
            return;
        }
        qs_22.b(this.bP.ap(), false);
    }
}

