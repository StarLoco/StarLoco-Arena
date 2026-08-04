/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public abstract class YN
extends yt_1
implements ClipboardOwner {
    private static final int cbq = 50;
    protected ov_1 aDc;
    private long cbr = 0L;
    private boolean cbs = false;
    private boolean cbt = false;
    private aFH cbu = null;
    private boolean cbv = false;
    public static final int cbw = "selectable".hashCode();
    public static final int cbx = "selectOnFocus".hashCode();
    public static final int cby = "enableOnlySelectablePartInteraction".hashCode();

    public boolean getSelectable() {
        return this.getTextBuilder().gs();
    }

    public void setSelectable(boolean bl2) {
        this.getTextBuilder().setSelectable(bl2);
        this.setFocusable(bl2);
        if (bl2) {
            this.setCursorType(xy_0.bYo);
        } else {
            this.setCursorType(xy_0.bYl);
        }
    }

    public boolean isSelectOnFocus() {
        return this.cbv;
    }

    public void setSelectOnFocus(boolean bl2) {
        this.cbv = bl2;
    }

    public boolean isEnableOnlySelectablePartInteraction() {
        return this.cbt;
    }

    public void setEnableOnlySelectablePartInteraction(boolean bl2) {
        this.cbt = bl2;
    }

    public adg_2 getWidget(int n2, int n3) {
        this.cbu = null;
        if (this.czc) {
            return null;
        }
        if (!this.aQv || this.dyc || !this.getAppearance().aY(n2, n3) || ago_2.getInstance().isMovePointMode()) {
            return null;
        }
        this.cbu = this.getContentBlockUnderMouse(n2, -n3);
        if (this.cbt && (this.cbu == null || this.cbu.aRY() != nf_2.NN || ((wC)this.cbu).CZ() == null || ((wC)this.cbu).CZ().getId() == null)) {
            return null;
        }
        return this;
    }

    public aFH getBlockUnderMouse() {
        return this.cbu;
    }

    public void j() {
        super.j();
        this.cbu = null;
        ago_2.getInstance().b(qe_1.bFA, this.aDc, false);
    }

    public void b() {
        super.b();
        this.cbt = false;
        this.ade();
    }

    public void selectAll() {
        if (this.getSelectable()) {
            jz jz2 = this.getTextBuilder().Fh();
            if (jz2.isEmpty()) {
                this.getTextBuilder().mq();
            }
            yb_0 yb_02 = jz2.mw();
            this.getTextBuilder().c(yb_02, 0);
            yb_0 yb_03 = jz2.mx();
            this.getTextBuilder().d(yb_03, yb_03.Fj());
        }
    }

    private Point getOrientedMouseCoodinates(abd_1 abd_12) {
        int n2 = 0;
        int n3 = 0;
        switch (this.getTextBuilder().getOrientation()) {
            case cxU: {
                n2 = abd_12.q(this) - this.cLZ.getBottomInset();
                n3 = this.aLd.width - abd_12.p(this) - this.cLZ.getLeftInset() - this.cLZ.getRightInset();
                break;
            }
            case cxW: {
                n2 = abd_12.p(this) - this.cLZ.getLeftInset();
                n3 = abd_12.q(this) - this.cLZ.getBottomInset();
                break;
            }
            case cxV: {
                n2 = this.aLd.height - abd_12.q(this) - this.cLZ.getBottomInset() - this.cLZ.getTopInset();
                n3 = abd_12.p(this) - this.cLZ.getLeftInset();
                break;
            }
            case cxX: {
                n2 = this.aLd.width - abd_12.p(this) - this.cLZ.getLeftInset() - this.cLZ.getRightInset();
                n3 = this.aLd.height - abd_12.q(this) - this.cLZ.getBottomInset() - this.cLZ.getTopInset();
            }
        }
        return new Point(n2, n3);
    }

    protected void ade() {
        this.a(qe_1.bFh, new aJl(this), false);
        this.aDc = new ajk_0(this);
        ago_2.getInstance().a(qe_1.bFA, this.aDc, false);
        this.a(qe_1.bFz, new ajo_0(this), false);
        this.a(qe_1.bFv, new aJn(this), false);
        this.a(qe_1.bFx, new ajx_0(this), false);
        this.a(qe_1.bFt, new aJv(this), false);
        this.a(qe_1.bFy, new aJB(this), false);
        this.a(qe_1.bFn, new ajz_0(this), true);
        this.a(qe_1.bFm, new aJq(this), true);
        this.a(qe_1.bFo, new amf_0(this), true);
    }

    public aFH getContentBlockUnderMouse(abd_1 abd_12) {
        if (abd_12.oF() != this) {
            return null;
        }
        Point point = this.getOrientedMouseCoodinates(abd_12);
        return this.getContentBlockUnderMouse(point.x, -point.y);
    }

    private aFH getContentBlockUnderMouse(int n2, int n3) {
        pf_0 pf_02 = this.getTextBuilder().ai(n2, n3);
        return pf_02 == null ? null : (aFH)pf_02.getFirst();
    }

    protected void a(Vz vz) {
        long l2 = this.cbr = vz.air() ? System.currentTimeMillis() : 0L;
        if (!vz.air()) {
            this.getTextBuilder().Jt();
        }
    }

    protected void a(aFH aFH2, int n2) {
        if (this.cbs) {
            this.selectAll();
            this.cbr = 0L;
            this.cbs = false;
        }
    }

    protected void b(aFH aFH2, int n2) {
        boolean bl2 = this.cbs = System.currentTimeMillis() - this.cbr < 50L && this.cbv;
        if (!this.cbs && this.getSelectable()) {
            this.getTextBuilder().c(aFH2.De(), aFH2.getStartIndex() + n2);
            this.getTextBuilder().d(aFH2.De(), aFH2.getStartIndex() + n2);
        }
    }

    protected void c(aFH aFH2, int n2) {
        this.cbr = 0L;
        this.cbs = false;
        if (this.getSelectable()) {
            this.getTextBuilder().d(aFH2.De(), aFH2.getStartIndex() + n2);
        }
    }

    protected boolean a(aqG aqG2) {
        return false;
    }

    protected boolean b(aqG aqG2) {
        int n2 = aqG2.getModifiers();
        switch (aqG2.getKeyCode()) {
            case 37: {
                if ((n2 & 0x80) == 128 || (n2 & 0x200) == 512 || (n2 & 0x2000) == 8192 || (n2 & 0x100) == 256) {
                    return true;
                }
                this.getTextBuilder().Jv();
                if ((n2 & 0x40) != 64) {
                    this.getTextBuilder().Je();
                }
                ago_2.getInstance().setKeyEventConsumed(true);
                return false;
            }
            case 39: {
                if ((n2 & 0x80) == 128 || (n2 & 0x200) == 512 || (n2 & 0x2000) == 8192 || (n2 & 0x100) == 256) {
                    return true;
                }
                this.getTextBuilder().Ju();
                if ((n2 & 0x40) != 64) {
                    this.getTextBuilder().Je();
                }
                ago_2.getInstance().setKeyEventConsumed(true);
                return false;
            }
            case 36: {
                this.getTextBuilder().Jw();
                if ((aqG2.getModifiers() & 0x40) != 64) {
                    this.getTextBuilder().Je();
                }
                ago_2.getInstance().setKeyEventConsumed(true);
                return false;
            }
            case 35: {
                this.getTextBuilder().Jx();
                if ((aqG2.getModifiers() & 0x40) != 64) {
                    this.getTextBuilder().Je();
                }
                ago_2.getInstance().setKeyEventConsumed(true);
                return false;
            }
            case 67: {
                if ((n2 & 0x80) != 128) break;
                if (!this.getTextBuilder().Fh().mu()) {
                    this.amM();
                }
                ago_2.getInstance().setKeyEventConsumed(true);
                return false;
            }
        }
        return true;
    }

    protected boolean c(aqG aqG2) {
        if ((aqG2.getModifiers() & 0x80) == 128) {
            ago_2.getInstance().setKeyEventConsumed(true);
            ago_2.getInstance().setMovePointMode(false);
            return false;
        }
        return true;
    }

    public void lostOwnership(Clipboard clipboard, Transferable transferable) {
    }

    protected void amM() {
        String string = this.getTextBuilder().Fh().getSelectedText();
        if (string != null && string.length() != 0) {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection stringSelection = new StringSelection(string);
            clipboard.setContents(stringSelection, this);
        }
    }

    public void a(air_1 air_12) {
        YN yN = (YN)air_12;
        super.a((air_1)yN);
        yN.cbv = this.cbv;
        yN.setSelectable(this.getTextBuilder().gs());
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cbw) {
            this.setSelectable(Gr.getBoolean(string));
        } else if (n2 == cbx) {
            this.setSelectOnFocus(Gr.getBoolean(string));
        } else if (n2 == cby) {
            this.setEnableOnlySelectablePartInteraction(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cbw) {
            this.setSelectable(Gr.getBoolean(object));
        } else if (n2 == cbx) {
            this.setSelectOnFocus(Gr.getBoolean(object));
        } else if (n2 == cby) {
            this.setEnableOnlySelectablePartInteraction(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static /* synthetic */ Point a(YN yN, abd_1 abd_12) {
        return yN.getOrientedMouseCoodinates(abd_12);
    }

    static /* synthetic */ aFH a(YN yN) {
        return yN.cbu;
    }
}

