/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/*
 * Renamed from aGo
 */
public class ago_2
extends ex_2 {
    private static Logger a = Logger.getLogger(ago_2.class);
    public static final String TAG = "MasterRootContainer";
    private static final ago_2 dIk = new ago_2();
    private adg_2 dIl;
    private adg_2 dIm;
    private MouseEvent dIn = null;
    private abd_1 dIo = null;
    private MouseEvent dIp = null;
    private agj_1 dIq = null;
    private boolean cIY = false;
    private adg_2 dIr;
    private boolean dIs = false;
    private int dIt;
    private abz_2 dIu;
    private boolean dIv = true;
    private boolean dIw = false;
    private ArrayList G = new ArrayList();
    private boolean dIx = false;

    private ago_2() {
    }

    public static ago_2 getInstance() {
        return dIk;
    }

    public void ade() {
        this.a(qe_1.bFo, new sx_2(this), false);
    }

    public void setSize(int n2, int n3, boolean bl2) {
        this.dIq = new agj_1(n2, n3);
        this.setNeedsToPreProcess();
    }

    public ago_2 getMasterRootContainer() {
        return this;
    }

    public String getTag() {
        return TAG;
    }

    public adg_2 getMouseOver() {
        return this.dIl;
    }

    public abd_1 getCurrentMouseEvent() {
        return this.dIo;
    }

    public MouseEvent getCurrentAWTMouseEvent() {
        return this.dIp;
    }

    public abz_2 getPopupContainer() {
        return this.dIu;
    }

    public boolean isResized() {
        return this.cIY;
    }

    public boolean isKeyEventConsumed() {
        return this.dIv;
    }

    public void setKeyEventConsumed(boolean bl2) {
        this.dIv = bl2;
    }

    public void setElementMap(aji_1 aji_12) {
        super.setElementMap(aji_12);
        this.aRJ.setElementMap(aji_12);
    }

    public void setVisible(boolean bl2) {
        boolean bl3 = this.aQv;
        super.setVisible(bl2);
        apw_1.aDr().a(xy_0.bYl);
        if (bl3 != bl2) {
            for (ang_1 ang_12 : this.G) {
                if (bl2) {
                    ang_12.b(this);
                    continue;
                }
                ang_12.c(this);
            }
        }
    }

    public void a(ang_1 ang_12) {
        if (!this.G.contains(ang_12)) {
            this.G.add(ang_12);
        }
    }

    public void b(ang_1 ang_12) {
        this.G.remove(ang_12);
    }

    public void aSm() {
        this.j();
        this.b();
        this.setSize(alj_0.aWw().aWB());
        agV agV2 = add_1.aOG().aON();
        agV2.awH();
        agV2.e(this.arC);
    }

    public boolean isInWidgetTree() {
        return true;
    }

    public boolean isInTree() {
        return true;
    }

    public boolean isDragging() {
        return this.dIr != null;
    }

    public adg_2 getDragged() {
        return this.dIr;
    }

    public int getDragButton() {
        return this.dIt;
    }

    public void setDragged(adg_2 adg_22, int n2) {
        this.dIr = adg_22;
        this.dIt = n2;
    }

    public boolean isShiftPressed() {
        return this.dIw;
    }

    public void r(adg_2 adg_22) {
        if (this.dIr == adg_22) {
            this.dIr = null;
            this.dIt = 0;
        }
        if (this.dIl == adg_22) {
            this.dIl = null;
        }
        if (this.dIm == adg_22) {
            this.dIm = null;
        }
    }

    public void aSn() {
        if (this.dIn != null) {
            this.g(this.dIn);
        }
    }

    public boolean isMovePointMode() {
        return this.dIx;
    }

    public void setMovePointMode(boolean bl2) {
        this.dIx = bl2;
        apw_1.aDr().a(bl2 ? xy_0.bYn : xy_0.bYl);
    }

    public boolean g(MouseEvent mouseEvent) {
        abd_1 abd_12;
        adg_2 adg_22;
        this.dIn = mouseEvent;
        this.dIp = mouseEvent;
        int n2 = this.aLd.height - mouseEvent.getY();
        int n3 = mouseEvent.getX();
        boolean bl2 = false;
        if (!amY.aBW().by(n3, n2)) {
            bl2 = true;
        }
        if (this.dIr == null && bl2) {
            this.dIp = null;
            return true;
        }
        awS.aJG().as(n3, n2);
        adg_2 adg_23 = adg_22 = !bl2 ? this.getWidget(n3, n2) : null;
        if (adg_22 != null) {
            if (adg_22 != this || adg_22 != this.dIl) {
                apw_1.aDr().a(adg_22.getCursorType());
            }
            abd_12 = abd_1.k(mouseEvent);
            abd_12.e(adg_22);
            abd_12.ai(n3);
            abd_12.aj(n2);
            abd_12.a(qe_1.bFt);
            this.dIo = abd_12;
            this.dIp = mouseEvent;
            adg_22.f(abd_12);
        }
        if (this.dIx) {
            apw_1.aDr().a(xy_0.bYn);
        }
        if (adg_22 != this.dIl) {
            adc_1 adc_12;
            if (this.dIl != null) {
                abd_12 = abd_1.k(mouseEvent);
                abd_12.e(this.dIl);
                abd_12.ai(n3);
                abd_12.aj(n2);
                abd_12.a(qe_1.bFy);
                this.dIo = abd_12;
                this.dIl.f(abd_12);
                this.dIl.aPa();
                this.dIo = null;
                adc_12 = adc_1.ata();
                adc_12.e(this.dIl);
                adc_12.a(qe_1.bFF);
                this.dIl.f(adc_12);
            }
            if (adg_22 != null) {
                abd_12 = abd_1.k(mouseEvent);
                abd_12.e(adg_22);
                abd_12.ai(n3);
                abd_12.aj(n2);
                abd_12.a(qe_1.bFx);
                this.dIo = abd_12;
                adg_22.f(abd_12);
                adg_22.aOZ();
                this.dIo = null;
                adc_12 = adc_1.ata();
                adc_12.e(adg_22);
                adc_12.a(qe_1.bFE);
                adg_22.f(adc_12);
            }
            this.dIl = adg_22;
        }
        if (this.dIr != null) {
            this.dIo = abd_1.k(mouseEvent);
            this.dIo.e(this.dIr);
            this.dIo.ai(n3);
            this.dIo.aj(n2);
            this.dIo.a(qe_1.bFv);
            if (!ali_0.aWv().d(this.dIl, n3, n2)) {
                if (this.dIs) {
                    abd_12 = abd_1.f(this.dIo);
                    abd_12.a(qe_1.bFu);
                    this.dIr.f(abd_12);
                    this.dIs = false;
                }
                this.dIr.f(this.dIo);
            } else {
                this.dIo.release();
            }
            this.dIo = null;
        }
        mb_0.Yl().as(mouseEvent.getX(), n2);
        this.dIp = null;
        return this.dIr != null;
    }

    public boolean mousePressed(MouseEvent mouseEvent) {
        adg_2 adg_22;
        int n2 = mouseEvent.getButton();
        if (n2 == 0) {
            return false;
        }
        this.dIp = mouseEvent;
        int n3 = mouseEvent.getX();
        int n4 = this.aLd.height - mouseEvent.getY();
        if (!amY.aBW().by(n3, n4)) {
            this.dIp = null;
            return true;
        }
        this.dIm = adg_22 = this.getWidget(n3, n4);
        if (adg_22 == null) {
            adg_22 = this;
        }
        lb_2.XL().g(adg_22);
        abd_1 abd_12 = abd_1.k(mouseEvent);
        abd_12.e(adg_22);
        abd_12.ai(n3);
        abd_12.aj(n4);
        abd_12.a(qe_1.bFz);
        this.dIo = abd_12;
        if (this.dIr == null && adg_22 != this) {
            this.dIs = true;
            this.dIr = adg_22;
            this.dIt = n2;
            ali_0.aWv().c(adg_22, n3, n4);
        }
        awS.aJG().d(adg_22, abd_12.getButton());
        adg_22.f(abd_12);
        this.dIo = null;
        this.dIp = null;
        return adg_22 != this;
    }

    public boolean c(MouseEvent mouseEvent) {
        boolean bl2;
        int n2 = mouseEvent.getButton();
        if (n2 == 0) {
            return false;
        }
        this.dIp = mouseEvent;
        int n3 = mouseEvent.getX();
        int n4 = this.aLd.height - mouseEvent.getY();
        boolean bl3 = this.dIt == n2;
        boolean bl4 = bl2 = !amY.aBW().by(n3, n4);
        if (bl2 && !bl3) {
            this.dIp = null;
            return true;
        }
        adg_2 adg_22 = this.getWidget(n3, n4);
        if (adg_22 == null) {
            adg_22 = this;
        }
        abd_1 abd_12 = null;
        if (!bl2) {
            abd_12 = abd_1.k(mouseEvent);
            abd_12.e(adg_22);
            abd_12.ai(n3);
            abd_12.aj(n4);
            abd_12.a(qe_1.bFA);
            this.dIo = abd_12;
        }
        if (bl3) {
            abd_1 abd_13 = abd_1.k(mouseEvent);
            abd_13.e(adg_22);
            abd_13.ai(n3);
            abd_13.aj(n4);
            abd_13.a(qe_1.bFw);
            this.dIr.f(abd_13);
            this.dIr = null;
            this.dIt = 0;
            ali_0.aWv().e(adg_22, n3, n4);
        }
        if (!bl2) {
            awS.aJG().a(adg_22, abd_12);
            adg_22.f(abd_12);
            this.dIo = null;
        }
        this.dIp = null;
        return this.dIm != this && (adg_22 != this || bl3);
    }

    public boolean a(MouseWheelEvent mouseWheelEvent) {
        this.dIp = mouseWheelEvent;
        int n2 = mouseWheelEvent.getX();
        int n3 = this.aLd.height - mouseWheelEvent.getY();
        if (!amY.aBW().by(n2, n3)) {
            return true;
        }
        adg_2 adg_22 = this.getWidget(n2, n3);
        if (adg_22 == null) {
            adg_22 = this;
        }
        abd_1 abd_12 = abd_1.k(mouseWheelEvent);
        abd_12.e(adg_22);
        abd_12.ai(n2);
        abd_12.aj(n3);
        abd_12.ni(mouseWheelEvent.getWheelRotation());
        abd_12.a(qe_1.bFD);
        this.dIo = abd_12;
        adg_22.f(abd_12);
        this.dIo = null;
        this.dIp = null;
        return adg_22 != this;
    }

    public boolean b(KeyEvent keyEvent) {
        boolean bl2 = false;
        if (!amY.aBW().isEmpty() && !add_1.aOG().aOM().d(keyEvent)) {
            bl2 = true;
        }
        azq.aLT().keyPressed(keyEvent);
        if (keyEvent.getKeyCode() == 16) {
            this.dIw = true;
        }
        if (keyEvent.getKeyCode() == 17) {
            this.setMovePointMode(true);
        }
        if (keyEvent.getKeyCode() == 9) {
            if ((keyEvent.getModifiersEx() & 0x40) == 64) {
                lb_2.XL().XO();
            } else {
                lb_2.XL().XP();
            }
            return bl2;
        }
        return bl2 |= this.a(keyEvent, qe_1.bFm);
    }

    public boolean c(KeyEvent keyEvent) {
        boolean bl2;
        boolean bl3 = false;
        if (!amY.aBW().isEmpty() && !add_1.aOG().aOM().d(keyEvent)) {
            bl3 = true;
        }
        azq.aLT().keyReleased(keyEvent);
        if (keyEvent.getKeyCode() == 16) {
            this.dIw = false;
        }
        if (keyEvent.getKeyCode() == 17) {
            this.setMovePointMode(false);
        }
        if (bl2 = this.a(keyEvent, qe_1.bFn)) {
            add_1.aOG().aOM().hS(-1);
            this.setMovePointMode(false);
        }
        return bl2 | bl3;
    }

    public boolean a(KeyEvent keyEvent) {
        return this.a(keyEvent, qe_1.bFo);
    }

    private boolean a(KeyEvent keyEvent, qe_1 qe_12) {
        int n2 = keyEvent.getKeyCode();
        adg_2 adg_22 = lb_2.XL().XM();
        if (adg_22 == null) {
            return false;
        }
        aqG aqG2 = aqG.aEf();
        aqG2.e(adg_22);
        aqG2.setKeyChar(keyEvent.getKeyChar());
        aqG2.setKeyCode(n2);
        aqG2.setModifiers(keyEvent.getModifiersEx());
        aqG2.a(qe_12);
        this.dIv = false;
        boolean bl2 = adg_22.f(aqG2);
        return this.dIv || bl2;
    }

    public boolean cc(int n2) {
        if (this.cIY) {
            this.cIY = false;
        }
        boolean bl2 = false;
        if (this.dIq != null) {
            super.setSize(this.dIq.width, this.dIq.height, false);
            this.dIq = null;
            this.cIY = true;
            bl2 = true;
        }
        bl2 |= super.cc(n2);
        return true;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        avz avz2 = (avz)this.arC.aUM().aI(0);
        if (avz2.aIF().getX() != (float)alj_0.aWw().aWx().x || avz2.aIF().getY() != (float)(alj_0.aWw().aWx().y + this.aLd.height)) {
            avz2.e(alj_0.aWw().aWx().x, alj_0.aWw().aWx().y + this.aLd.height, 0.0f);
            this.arC.aUM().b(0, avz2);
        }
        return bl2;
    }

    public void b() {
        super.b();
        this.setTreeDepth(0);
        this.dyc = false;
        this.cIY = false;
        this.dIu = new abz_2();
        this.dIu.b();
        this.aRJ.a(this.dIu, 30000);
        alQ.getInstance().b();
        this.aRJ.a(alQ.getInstance(), 30000);
        this.setScreenPosition(0, 0);
        this.ade();
        this.dyu = true;
    }

    public void j() {
        super.j();
        this.dIr = null;
        this.dIn = null;
        this.dIl = null;
        this.dIo = null;
        this.dIp = null;
        this.dIq = null;
        this.dIu = null;
    }

    protected void pX() {
        super.pX();
    }

    static /* synthetic */ boolean a(ago_2 ago_22, boolean bl2) {
        ago_22.dIv = bl2;
        return ago_22.dIv;
    }
}

