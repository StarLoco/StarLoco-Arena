/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Rectangle;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aEF
 */
public class aef_2
implements JG {
    private static final Logger a = Logger.getLogger(aef_2.class);
    private static final acl_0 uG = new ym_0(new ais_0());
    private acl_0 DG;
    private final ArrayList dBC = new ArrayList();
    private EM dBD = null;
    private final Rectangle dBE = new Rectangle();
    private int dBF = 0;
    private BP aCS;
    private int avd;

    private aef_2() {
    }

    public static aef_2 aQF() {
        aef_2 aef_22;
        try {
            aef_22 = (aef_2)uG.adr();
            aef_22.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aef_22 = new aef_2();
            aef_22.b();
        }
        return aef_22;
    }

    public void release() {
        try {
            if (this.DG != null) {
                this.DG.af(this);
            } else {
                this.j();
            }
        }
        catch (Exception exception) {
            a.warn((Object)"Probl\u00e8me lors du retour dans un pool", (Throwable)exception);
            this.j();
        }
    }

    public wC h(String string, int n2, int n3) {
        return this.a(string, null, 0, 0, n2, n3);
    }

    public wC a(String string, adv_0 adv_02, int n2, int n3, int n4, int n5) {
        wC wC2 = new wC();
        wC2.a(this);
        wC2.setText(string);
        wC2.c(adv_02);
        wC2.os(n2);
        wC2.setEndIndex(n3);
        wC2.setX(n4);
        wC2.setWidth(n5);
        this.a(wC2);
        return wC2;
    }

    public aNS a(aoz_2 aoz_22, int n2) {
        aNS aNS2 = new aNS();
        aNS2.a(this);
        aNS2.c(aoz_22);
        aNS2.setX(n2);
        this.a(aNS2);
        return aNS2;
    }

    private void a(aFH aFH2) {
        this.dBE.width += aFH2.getWidth();
        this.dBC.add(aFH2);
    }

    public void nx(int n2) {
        aFH aFH2 = (aFH)this.dBC.remove(n2);
        this.dBE.width -= aFH2.getWidth();
    }

    public void aQG() {
        this.dBC.clear();
        this.dBE.width = 0;
        this.aQH();
    }

    public void aQH() {
        this.dBD = null;
    }

    public EM aQI() {
        return this.dBD;
    }

    public void f(int n2, int n3, boolean bl2) {
        this.dBD = new EM();
        this.dBD.a(this);
        this.dBD.setX(n2);
        this.dBD.setWidth(n3);
        this.dBD.bn(bl2);
    }

    public Rectangle getBounds() {
        return this.dBE;
    }

    public int getX() {
        return this.dBE.x;
    }

    public void setX(int n2) {
        this.dBE.x = n2;
    }

    public int getY() {
        return this.dBE.y;
    }

    public void setY(int n2) {
        this.dBE.y = n2;
    }

    public int getHeight() {
        return this.dBE.height;
    }

    public void setHeight(int n2) {
        this.dBE.height = n2;
    }

    public int getWidth() {
        return this.dBE.width;
    }

    public int aQJ() {
        return this.dBF;
    }

    public void ny(int n2) {
        this.dBF = n2;
    }

    public BP Fi() {
        return this.aCS;
    }

    public void a(BP bP) {
        this.aCS = bP;
    }

    public boolean isEmpty() {
        return this.dBC.isEmpty();
    }

    public int Dc() {
        return this.avd;
    }

    public void ec(int n2) {
        this.avd = n2;
    }

    public int aQK() {
        return this.dBC.size();
    }

    public aFH nz(int n2) {
        return (aFH)this.dBC.get(n2);
    }

    public aFH aQL() {
        if (!this.dBC.isEmpty()) {
            return (aFH)this.dBC.get(0);
        }
        return null;
    }

    public aFH aQM() {
        if (!this.dBC.isEmpty()) {
            return (aFH)this.dBC.get(this.dBC.size() - 1);
        }
        return null;
    }

    public void aQN() {
        this.dBE.width = 0;
        for (aFH aFH2 : this.dBC) {
            this.dBE.width += aFH2.getWidth();
        }
    }

    public final ArrayList aQO() {
        return this.dBC;
    }

    public void b() {
        this.dBF = 0;
    }

    public void j() {
        this.dBC.clear();
        this.dBD = null;
        this.dBE.setBounds(0, 0, 0, 0);
        this.aCS = null;
        this.avd = 0;
    }

    /* synthetic */ aef_2(ais_0 ais_02) {
        this();
    }
}

