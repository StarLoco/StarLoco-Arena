/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.baseImpl.graphics.isometric.particles.FreeParticleSystem;

public class Io {
    private static final double bgE = 0.01;
    private static final float bgF = 2.1f;
    private boolean rH = false;
    private Object dE;
    private int aG;
    private int aH;
    private int bgG;
    private int bgH;
    private FreeParticleSystem bgI = null;
    private int oI;
    private int oJ;
    private boolean bgJ = false;
    private awt_0 bgK;
    private awt_0 bgL;
    private awt_0 bgM;
    private awt_0 bgN;
    private awt_0 bgO;
    private SF bgP;
    private acn bgQ;

    public Io(SF sF, Object object, int n2) {
        this.bgP = sF;
        this.dE = object;
        this.bgM = new awt_0();
        this.bgM.w(2.1f);
        this.bgM.v(0.01);
        this.bgN = new awt_0();
        this.bgN.w(2.1f);
        this.bgN.v(0.01);
        this.bgO = new awt_0();
        this.bgO.w(2.1f);
        this.bgO.v(0.01);
        this.bgK = new awt_0();
        this.bgK.w(2.1f);
        this.bgK.v(0.01);
        this.bgL = new awt_0();
        this.bgL.w(2.1f);
        this.bgL.v(0.01);
        this.bgH = n2;
    }

    public Io(SF sF, Object object, int n2, int n3, int n4, int n5, int n6, int n7) {
        this.bgP = sF;
        this.dE = object;
        this.aG = n3;
        this.bgM = new awt_0();
        this.bgM.w(2.1f);
        this.bgM.v(0.01);
        this.bgM.I(n3);
        this.aH = n4;
        this.bgN = new awt_0();
        this.bgN.w(2.1f);
        this.bgN.v(0.01);
        this.bgN.I(n4);
        this.bgG = n5;
        this.bgO = new awt_0();
        this.bgO.w(2.1f);
        this.bgO.v(0.01);
        this.bgO.I(n5);
        this.oI = n6;
        this.bgK = new awt_0();
        this.bgK.w(2.1f);
        this.bgK.v(0.01);
        this.bgK.I(n6);
        this.oJ = n7;
        this.bgL = new awt_0();
        this.bgL.w(2.1f);
        this.bgL.v(0.01);
        this.bgL.I(n7);
        this.bgH = n2;
    }

    public void setScreenPosition(int n2, int n3) {
        this.bgK.I(n2);
        this.bgL.I(n3);
    }

    public void aq(int n2, int n3) {
        this.bgK.K(n2);
        this.bgL.K(n3);
    }

    public int getScreenX() {
        return this.oI;
    }

    public int getScreenY() {
        return this.oJ;
    }

    public int Uh() {
        return this.bgH;
    }

    public void setValue(Object object) {
        this.dE = object;
    }

    public Object getValue() {
        return this.dE;
    }

    public void e(boolean bl2, boolean bl3) {
        if (this.rH && !bl3 && this.bgJ == bl2) {
            return;
        }
        this.rH = true;
        this.bgJ = bl2;
        if (this.bgJ) {
            this.Uj();
            this.bgP.b(this);
        } else {
            this.Uk();
            this.bgP.a(this);
        }
    }

    public void setPosition(int n2, int n3, int n4) {
        this.bgM.I(n2);
        this.bgN.I(n3);
        this.bgO.I(n4);
    }

    public void u(int n2, int n3, int n4) {
        this.bgM.K(n2);
        this.bgN.K(n3);
        this.bgO.K(n4);
    }

    public int getX() {
        return (int)this.bgM.aJI();
    }

    public int getY() {
        return (int)this.bgN.aJI();
    }

    public int Ui() {
        return (int)this.bgO.aJI();
    }

    public void bI(int n2) {
        this.oI = (int)this.bgK.mD(n2);
        this.oJ = (int)this.bgL.mD(n2);
        this.aG = (int)this.bgM.mD(n2);
        this.aH = (int)this.bgN.mD(n2);
        this.bgG = (int)this.bgO.mD(n2);
        if (this.bgI != null) {
            this.bgI.a(this.aG, this.aH, this.bgG);
        }
    }

    private void Uj() {
        if (this.bgI != null) {
            return;
        }
        this.bgI = aiJ.ayv().bw(this.bgH, 0);
        if (this.bgI != null) {
            this.bgI.a(this.aG, this.aH, this.bgG);
            qd_1.uW().b(this.bgI);
        }
    }

    private void Uk() {
        if (this.bgI == null) {
            return;
        }
        qd_1.uW().cK(this.bgI.getId());
        this.bgI = null;
    }

    private void Ul() {
        if (this.bgQ != null) {
            acn acn2 = this.bgQ;
            this.bgQ = null;
            acn2.aqZ();
        }
    }

    public acn Um() {
        return this.bgQ;
    }

    public void a(acn acn2) {
        this.bgQ = acn2;
    }

    public void clear() {
        this.Ul();
        this.bgP.b(this);
        this.Uk();
    }
}

