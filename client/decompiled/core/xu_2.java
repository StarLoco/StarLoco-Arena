/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

/*
 * Renamed from XU
 */
public class xu_2
extends ayb {
    private boolean bZx = false;
    private boolean bZy = false;
    private boolean bZz = false;
    private final ArrayList EK = new ArrayList();

    public xu_2(mk_1 mk_12) {
        super(mk_12);
        this.a(qi_1.vV());
        this.b(new mp_0[]{aau_0.apB(), gp_2.Sb()});
        this.a(GY.Ss());
    }

    protected void vm() {
        this.dsa = new YR(this, 0.9f, 3.0f);
    }

    public boolean alF() {
        return this.bZx;
    }

    public void cD(boolean bl2) {
        this.bZx = bl2;
    }

    public void ao(boolean bl2) {
        super.ao(bl2);
        this.bq(0.0f);
        this.ex(false);
        azh.aLL().aLM();
        vt_0.aiU().deactivate();
        xx_1.clear();
    }

    public void bI(int n2) {
        super.bI(n2);
        adu_0 adu_02 = apN.aDK().aDL();
        long l2 = pm_0.ur().Tm();
        if (l2 != 0L && System.currentTimeMillis() - l2 > 10000L && (adu_02 == null || adu_02.Zy() == ko_2.bpv)) {
            pm_0.ur().done();
        }
    }

    public boolean b(KeyEvent keyEvent) {
        return false;
    }

    public boolean c(KeyEvent keyEvent) {
        return false;
    }

    public boolean a(FocusEvent focusEvent) {
        return false;
    }

    public boolean b(FocusEvent focusEvent) {
        return false;
    }

    public boolean a(KeyEvent keyEvent) {
        return false;
    }

    public boolean b(MouseEvent mouseEvent) {
        return false;
    }

    public boolean f(MouseEvent mouseEvent) {
        return this.g(mouseEvent);
    }

    public boolean d(MouseEvent mouseEvent) {
        return false;
    }

    public boolean g(MouseEvent mouseEvent) {
        super.g(mouseEvent);
        if (!po_0.abV().abY()) {
            this.be(mouseEvent.getX(), mouseEvent.getY());
        }
        if (this.bZx) {
            abu_1 abu_12 = abu_1.aNs();
            abu_12.jL(mouseEvent.getX());
            abu_12.jM(mouseEvent.getY());
            acu_1.ara().c(abu_12);
        }
        if (this.bZy && this.bZz) {
            YO.amN().i(mouseEvent);
        }
        return false;
    }

    public boolean e(MouseEvent mouseEvent) {
        return false;
    }

    public boolean mousePressed(MouseEvent mouseEvent) {
        int n2 = -1;
        n2 = DofusArenaClientInstance.yl().aod().a(adc_0.clW) ? 1 : 3;
        if (mouseEvent.getButton() == n2) {
            this.bZz = true;
        }
        return false;
    }

    public boolean c(MouseEvent mouseEvent) {
        int n2 = -1;
        n2 = DofusArenaClientInstance.yl().aod().a(adc_0.clW) ? 1 : 3;
        if (mouseEvent.getButton() == n2) {
            this.bZz = false;
        }
        YO.amN().j(mouseEvent);
        ado ado2 = ado.asl();
        ado2.jH(mouseEvent.getButton());
        ado2.jL(mouseEvent.getX());
        ado2.jM(mouseEvent.getY());
        acu_1.ara().c(ado2);
        return true;
    }

    public boolean a(MouseWheelEvent mouseWheelEvent) {
        this.k(this.Ft() - (double)((float)mouseWheelEvent.getWheelRotation() * 0.1f));
        return false;
    }

    public void a(int n2, acy_1 acy_12) {
        try {
            if (mu_1.rM().getBoolean("activateMapVisualEffect")) {
                switch (n2) {
                    case 461: 
                    case 462: 
                    case 463: {
                        acy_12.a(adg_0.aPh().kS("sea"), false);
                        break;
                    }
                    default: {
                        acy_12.a(null, false);
                    }
                }
            }
        }
        catch (aih_2 aih_22) {
            aih_22.printStackTrace();
        }
    }

    public ArrayList bd(int n2, int n3) {
        tp_1 tp_12;
        ArrayList<tp_1> arrayList = new ArrayList<tp_1>();
        float f = this.J(n2);
        float f2 = this.K(n3);
        ArrayList arrayList2 = bd_1.Is().t(f, f2);
        if (arrayList2 != null) {
            arrayList.addAll(arrayList2);
        }
        if ((tp_12 = (tp_1)GY.Ss().u(f, f2)) != null) {
            arrayList.add(tp_12);
        }
        return arrayList;
    }

    public aiu_0 be(int n2, int n3) {
        tp_1 tp_12;
        this.EK.clear();
        float f = this.J(n2);
        float f2 = this.K(n3);
        mT mT2 = (mT)bd_1.Is().u(f, f2);
        if (mT2 != null) {
            this.EK.add(mT2);
        }
        if ((tp_12 = (tp_1)GY.Ss().u(f, f2)) != null) {
            this.EK.add(tp_12);
        }
        aiu_0 aiu_02 = null;
        for (aiu_0 aiu_03 : this.EK) {
            if (aiu_02 != null && aiu_03.aTz() <= aiu_02.aTz()) continue;
            aiu_02 = aiu_03;
        }
        bd_1.Is().a(aiu_02);
        GY.Ss().a(aiu_02);
        if (aiu_02 != null) {
            aiu_02.setSelected(true);
        }
        return aiu_02;
    }

    public boolean alG() {
        return this.bZy;
    }

    public void cE(boolean bl2) {
        this.bZy = bl2;
    }
}

