/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.log4j.Logger;

/*
 * Renamed from Mb
 */
public class mb_0 {
    private static final Logger a = Logger.getLogger(mb_0.class);
    private static final mb_0 bsT = new mb_0();
    private adg_2 DD;
    private boolean bsU = false;
    private BT bsV = BT.aJZ;
    private int bsW = 0;
    private int bsX = 0;
    private int aG = 0;
    private int aH = 0;

    private mb_0() {
    }

    public void setWidget(adg_2 adg_22) {
        this.DD = adg_22;
    }

    public static mb_0 Yl() {
        return bsT;
    }

    public void as(int n2, int n3) {
        this.aG = n2;
        this.aH = n3;
        this.Ym();
    }

    public void Ym() {
        if (this.DD != null) {
            int n2 = this.aG - this.bsV.eL(this.DD.getWidth()) + this.bsW;
            int n3 = this.aH - this.bsV.eM(this.DD.getHeight()) + this.bsX;
            this.DD.setPosition(n2, n3);
        }
    }

    public void a(String string, String string2, int n2, int n3, BT bT) {
        this.hide();
        adg_2 adg_22 = null;
        if (string != null && string2 != null) {
            adg_22 = this.x(string, string2);
        } else if (string != null) {
            adg_22 = this.fi(string);
        } else if (string2 != null) {
            adg_22 = this.x(string, string2);
        } else {
            a.error((Object)"On tent d'afficher des informations de souris non valides");
            return;
        }
        this.setWidget(adg_22);
        this.setXOffset(n2);
        this.setYOffset(n3);
        this.a(bT);
        this.show();
    }

    public void show() {
        if (this.bsU) {
            return;
        }
        eq_0 eq_02 = add_1.aOG().aON().getMasterRootContainer().getLayeredContainer();
        eq_02.a(this.DD, 40000);
        this.bsU = true;
    }

    public void hide() {
        if (this.DD == null || !this.bsU) {
            return;
        }
        aht_1 aht_12 = add_1.aOG().aON().getMasterRootContainer().getLayeredContainer().getContainerFromWidget(this.DD);
        if (aht_12 != null) {
            aht_12.b((na_1)this.DD);
        }
        this.DD = null;
        this.bsU = false;
    }

    public BT Yn() {
        return this.bsV;
    }

    public void a(BT bT) {
        this.bsV = bT;
        this.Ym();
    }

    public int getXOffset() {
        return this.bsW;
    }

    public void setXOffset(int n2) {
        this.bsW = n2;
        this.Ym();
    }

    public int getYOffset() {
        return this.bsX;
    }

    public void setYOffset(int n2) {
        this.bsX = n2;
        this.Ym();
    }

    private adg_2 fi(String string) {
        return this.a(string, null);
    }

    private adg_2 fj(String string) {
        return this.b(string, null);
    }

    private adg_2 x(String string, String string2) {
        return this.a(string, string2, null);
    }

    private adg_2 a(String string, aji_1 aji_12) {
        if (aji_12 == null) {
            aji_12 = this.Yo();
        }
        try {
            azc_0 azc_02 = new azc_0();
            URL uRL = new URL(string);
            akq_1 akq_12 = new akq_1(agx_2.aTc().h(uRL));
            azc_02.b();
            azc_02.setNonBlocking(true);
            azc_02.setExpandable(false);
            azc_02.setPixmap(akq_12);
            azc_02.zs();
            azc_02.setId("image");
            aji_12.a(azc_02.getId(), azc_02);
            azc_02.setElementMap(aji_12);
            azc_02.setSizeToPrefSize();
            return azc_02;
        }
        catch (MalformedURLException malformedURLException) {
            a.warn((Object)("URL malform\u00e9e : \"" + string + "\""));
            return null;
        }
    }

    private adg_2 b(String string, aji_1 aji_12) {
        if (aji_12 == null) {
            aji_12 = this.Yo();
        }
        ps ps2 = new ps();
        ps2.b();
        ps2.setExpandable(false);
        ps2.setNonBlocking(true);
        ps2.setStyle("White18Bordered");
        ps2.setText(string);
        ps2.setSizeToPrefSize();
        ps2.setId("text");
        aji_12.a(ps2.getId(), ps2);
        ps2.setElementMap(aji_12);
        ps2.Aj();
        return ps2;
    }

    private aji_1 Yo() {
        return new aji_1("test", add_1.aOG().azj());
    }

    private adg_2 a(String string, String string2, aji_1 aji_12) {
        if (aji_12 == null) {
            aji_12 = this.Yo();
        }
        ei_1 ei_12 = new ei_1();
        ei_12.b();
        ei_12.setAlign(BT.aJU);
        ei_12.setHorizontal(false);
        aht_1 aht_12 = new aht_1();
        aht_12.b();
        aht_12.setLayoutManager(ei_12);
        if (string2 != null) {
            aht_12.a(this.b(string2, aji_12));
        }
        if (string != null) {
            aht_12.a(this.a(string, aji_12));
        }
        aht_12.setElementMap(aji_12);
        aht_12.setPack(true);
        aht_12.Ak();
        aht_12.Aj();
        return aht_12;
    }

    public void setText(String string) {
        if (this.DD == null) {
            return;
        }
        na_1 na_12 = this.DD.getElementMap().R("text");
        if (na_12 == null) {
            return;
        }
        ((ps)na_12).setText(string);
    }

    public void fk(String string) {
        if (this.DD == null) {
            return;
        }
        na_1 na_12 = this.DD.getElementMap().R("image");
        if (na_12 == null) {
            return;
        }
        try {
            URL uRL = new URL(string);
            akq_1 akq_12 = new akq_1(agx_2.aTc().h(uRL));
            ((azc_0)na_12).setPixmap(akq_12);
        }
        catch (MalformedURLException malformedURLException) {
            a.warn((Object)("URL malform\u00e9e : \"" + string + "\""));
        }
    }

    public boolean xg() {
        return this.DD == null;
    }

    public boolean isVisible() {
        return this.DD != null && this.DD.getVisible();
    }
}

