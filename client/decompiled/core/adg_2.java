/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import com.ankamagames.framework.graphics.engine.entity.EntityGroup;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

/*
 * Renamed from aDG
 */
public abstract class adg_2
extends na_1
implements aci_0,
and_0 {
    protected Zb cLZ;
    protected Zb dxQ;
    protected aht_1 dxR;
    protected EntityGroup arC;
    protected Point dxS;
    protected Point dxT = new Point(-1, -1);
    public agj_1 aLd;
    protected agj_1 aLb;
    protected boolean dxU = false;
    protected agj_1 dxV;
    protected boolean dxW = false;
    protected agj_1 dxX;
    protected boolean dxY = false;
    protected boolean dxZ = false;
    protected boolean dya = true;
    protected boolean dyb = false;
    protected boolean dyc = false;
    protected boolean aQv = false;
    protected boolean dyd = false;
    protected boolean OD = true;
    protected boolean dye = false;
    protected String[] dyf = new String[5];
    protected boolean dyg = false;
    protected HashMap dyh = null;
    protected String dyi;
    protected String dyj;
    protected dz_2 dyk;
    protected kn_1 dyl;
    protected aNX dym;
    protected ai_2 bTW;
    protected ov_1 dyn;
    protected ov_1 dyo;
    protected xy_0 dyp = xy_0.bYl;
    protected boolean dyq = false;
    protected boolean dyr = false;
    protected boolean dys = true;
    protected Rectangle dyt = null;
    protected boolean dyu = false;
    private boolean dyv = true;
    public static final int cXP = "size".hashCode();
    public static final int dyw = "prefSize".hashCode();
    public static final int dyx = "maxSize".hashCode();
    public static final int dyy = "expandable".hashCode();
    public static final int dyz = "shrinkable".hashCode();
    public static final int dyA = "greedy".hashCode();
    public static final int dyB = "focusable".hashCode();
    public static final int dyC = "focused".hashCode();
    public static final int cMb = "enabled".hashCode();
    public static final int dyD = "visible".hashCode();
    public static final int dyE = "usedInLayout".hashCode();
    public static final int dyF = "usePositionTween".hashCode();
    public static final int dyG = "useResizeTween".hashCode();
    public static final int ars = "x".hashCode();
    public static final int art = "y".hashCode();
    public static final int dyH = "style".hashCode();
    public static final int dyI = "themeElementName".hashCode();
    public static final int dyJ = "themeElementParentType".hashCode();
    public static final int dyK = "nonBlocking".hashCode();
    public static final int dyL = "cursorType".hashCode();
    public static final int dyM = "needsScissor".hashCode();
    public static final int dyN = "userDefinedSize".hashCode();
    public static final int dyO = "userDefinedPosition".hashCode();
    public static final int xm = "onClick".hashCode();
    public static final int xn = "onDoubleClick".hashCode();
    public static final int xo = "onFocusChange".hashCode();
    public static final int xp = "onItemClick".hashCode();
    public static final int xq = "onItemDoubleClick".hashCode();
    public static final int xr = "onItemOut".hashCode();
    public static final int xs = "onItemOver".hashCode();
    public static final int xt = "onKeyPress".hashCode();
    public static final int xu = "onKeyRelease".hashCode();
    public static final int xv = "onKeyType".hashCode();
    public static final int xw = "onListSelectionChange".hashCode();
    public static final int xx = "onMouseDrag".hashCode();
    public static final int xy = "onMouseDragIn".hashCode();
    public static final int xz = "onMouseDragOut".hashCode();
    public static final int xA = "onMouseEnter".hashCode();
    public static final int xB = "onMouseExit".hashCode();
    public static final int xD = "onMousePress".hashCode();
    public static final int xE = "onMouseRelease".hashCode();
    public static final int xF = "onMouseWheel".hashCode();
    public static final int xG = "onSelectionChange".hashCode();
    public static final int xH = "onSliderMove".hashCode();
    public static final int dyP = "onValueChange".hashCode();
    public static final int xI = "onDrag".hashCode();
    public static final int xJ = "onDrop".hashCode();
    public static final int xK = "onDragOut".hashCode();
    public static final int xL = "onDropOut".hashCode();
    public static final int xM = "onDragOver".hashCode();
    public static final int dyQ = "onStick".hashCode();
    public static final int xN = "onPopupDisplay".hashCode();
    public static final int xO = "onPopupHide".hashCode();

    public void f(na_1 na_12) {
        if (na_12 instanceof abz_2) {
            ((abz_2)na_12).setClient(this);
            ago_2.getInstance().getLayeredContainer().a((adg_2)na_12, 30000);
        } else {
            super.f(na_12);
        }
    }

    public void a(na_1 na_12) {
        boolean bl2 = true;
        if (na_12 instanceof awc_0 || na_12 instanceof ase_0) {
            this.cLZ.a(na_12);
            return;
        }
        if (na_12 instanceof dz_2) {
            bl2 &= this.setLayoutData((dz_2)na_12);
        }
        if (na_12 instanceof Zb) {
            bl2 &= this.setAppearance((Zb)na_12);
        }
        if (na_12 instanceof aNX) {
            this.setTooltip((aNX)na_12);
        }
        if (na_12 instanceof ai_2) {
            this.setPopup((ai_2)na_12);
        }
        if (bl2) {
            super.a(na_12);
        }
    }

    public void a(String string, adg_2 adg_22) {
        if (string == null || adg_22 == null) {
            return;
        }
        if (this.dyh == null) {
            this.dyh = new HashMap();
        }
        this.dyh.put(string.toUpperCase(), adg_22);
        this.dyg = true;
        this.setNeedsToPreProcess();
    }

    protected void anf() {
        assert (this.cLZ != null);
        this.cLZ.anf();
        this.pX();
        this.dys = false;
    }

    protected void pX() {
    }

    public aNX getTooltip() {
        return this.dym;
    }

    public void setTooltip(aNX aNX2) {
        if (this.dyn == null) {
            this.dyn = new abr_2(this);
            this.a(qe_1.bFx, this.dyn, false);
        }
        if (this.dyo == null) {
            this.dyo = new aCc(this);
            this.a(qe_1.bFy, this.dyo, false);
        }
        this.dym = aNX2;
    }

    public void aOZ() {
        if (this.dym != null && this.dym.getText() != null && !this.dym.getText().equals("") && add_1.aOG().aOY()) {
            this.dym.s(this);
            add_1.aOG().aOZ();
        }
    }

    public void aPa() {
        if (this.dym != null && add_1.aOG().aOY()) {
            add_1.aOG().aPa();
        }
    }

    public void setPopup(ai_2 ai_22) {
        this.bTW = ai_22;
    }

    public ai_2 getPopup() {
        return this.bTW;
    }

    public Entity getEntity() {
        return this.arC;
    }

    public void setContainerParent(aht_1 aht_12) {
        this.dxR = aht_12;
    }

    public aht_1 getContainer() {
        return this.dxR;
    }

    public agj_1 getMaxSize() {
        return this.dxX;
    }

    public void setMaxSize(agj_1 agj_12) {
        this.dxX = agj_12;
        this.dxY = agj_12 != null;
    }

    public agj_1 getContentMinSize() {
        if (this.aLb != null) {
            return this.aLb;
        }
        return new agj_1(0, 0);
    }

    public agj_1 getMinSize() {
        agj_1 agj_12 = this.getContentMinSize();
        return new agj_1(agj_12.width + this.cLZ.getLeftInset() + this.cLZ.getRightInset(), agj_12.height + this.cLZ.getTopInset() + this.cLZ.getBottomInset());
    }

    public void aPw() {
    }

    public void setMinSize(agj_1 agj_12) {
        this.aLb = agj_12;
        this.dxU = agj_12 != null;
    }

    public agj_1 getGreedySize() {
        agj_1 agj_12 = this.getContentGreedySize();
        return new agj_1(agj_12.width + this.cLZ.getLeftInset() + this.cLZ.getRightInset(), agj_12.height + this.cLZ.getTopInset() + this.cLZ.getBottomInset());
    }

    public agj_1 getContentGreedySize() {
        if (this.dxR == null) {
            return new agj_1(this.cLZ.getContentWidth(), this.cLZ.getContentHeight());
        }
        return new agj_1(this.cLZ.getContentWidth(), this.cLZ.getContentHeight());
    }

    public agj_1 getPrefSize() {
        agj_1 agj_12 = this.getContentPrefSize();
        if (this.dyb) {
            if (this.dxX != null) {
                agj_12.height = Math.min(agj_12.height, this.dxX.height);
                agj_12.width = Math.min(agj_12.width, this.dxX.width);
            } else {
                agj_12.height = 0;
                agj_12.width = 0;
            }
        }
        return new agj_1(agj_12.width + this.cLZ.getLeftInset() + this.cLZ.getRightInset(), agj_12.height + this.cLZ.getTopInset() + this.cLZ.getBottomInset());
    }

    public agj_1 getContentPrefSize() {
        if (this.dxV == null) {
            return this.getContentMinSize();
        }
        agj_1 agj_12 = this.getContentMinSize();
        if (agj_12 == null) {
            return this.dxV;
        }
        int n2 = Math.max(this.dxV.width, agj_12.width);
        int n3 = Math.max(this.dxV.height, agj_12.height);
        return new agj_1(n2, n3);
    }

    public agj_1 getSetPrefSize() {
        if (this.dxW) {
            return this.dxV;
        }
        return null;
    }

    public void setPrefSize(agj_1 agj_12) {
        this.dxV = agj_12;
        this.dxW = agj_12 != null;
    }

    public agj_1 getSize() {
        return this.aLd;
    }

    public void setSize(agj_1 agj_12) {
        this.setSize(agj_12.width, agj_12.height);
    }

    public void setSize(int n2, int n3) {
        this.setSize(n2, n3, false);
    }

    public void setSize(int n2, int n3, boolean bl2) {
        if (!this.dyq || bl2) {
            boolean bl3 = this.aLd.width != n2 | this.aLd.height != n3;
            if (bl3) {
                this.aLd.width = n2;
                this.aLd.height = n3;
                this.invalidate();
                ke ke2 = ke.oI();
                ke2.a(qe_1.bFO);
                ke2.e(this);
                this.f(ke2);
            }
        } else {
            this.q(xg_1.class);
            xg_1 xg_12 = new xg_1(this.getSize(), new agj_1(n2, n3), this, 0, 500, ys.aCq);
            this.a(xg_12);
        }
    }

    public void setSizeToMinSize() {
        this.setSize(this.getMinSize());
    }

    public void setSizeToPrefSize() {
        this.setSize(this.getPrefSize());
    }

    public int getWidth() {
        return this.aLd.width;
    }

    public void setWidth(int n2) {
        this.setSize(n2, this.aLd.height);
    }

    public void setHeight(int n2) {
        this.setSize(this.aLd.width, n2);
    }

    public int getHeight() {
        return this.aLd.height;
    }

    public boolean isNonBlocking() {
        return this.dyc;
    }

    public void setNonBlocking(boolean bl2) {
        this.dyc = bl2;
    }

    public int getX(aht_1 aht_12) {
        if (this.dxS != null) {
            if (this.dxR == null || this.dxR == aht_12) {
                return this.dxS.x + this.cLZ.getLeftInset();
            }
            return this.dxS.x + this.dxR.getX(aht_12) + this.cLZ.getLeftInset();
        }
        return 0;
    }

    public int getY(aht_1 aht_12) {
        if (this.dxS != null) {
            if (this.dxR == null || this.dxR == aht_12) {
                return this.dxS.y + this.cLZ.getBottomInset();
            }
            return this.dxS.y + this.dxR.getY(aht_12) + this.cLZ.getBottomInset();
        }
        return 0;
    }

    public int getScreenX() {
        if (this.dxT.x != -1 && this.dxT.y != -1) {
            return this.dxT.x;
        }
        if (this.dxS != null) {
            if (this.dxR != null && this.dxR.getAppearance() != null) {
                return this.dxS.x + this.dxR.getScreenX() + this.dxR.getAppearance().getLeftInset();
            }
            return this.dxS.x;
        }
        return 0;
    }

    public int getScreenY() {
        if (this.dxT.x != -1 && this.dxT.y != -1) {
            return this.dxT.y;
        }
        if (this.dxS != null) {
            if (this.dxR != null && this.dxR.getAppearance() != null) {
                return this.dxS.y + this.dxR.getScreenY() + this.dxR.getAppearance().getBottomInset();
            }
            return this.dxS.y;
        }
        return 0;
    }

    public Point getScreenPosition() {
        return this.dxT;
    }

    public void setScreenPosition(int n2, int n3) {
        this.dxT.setLocation(n2, n3);
    }

    public Point getPosition() {
        return this.dxS;
    }

    public void setPosition(Point point) {
        this.setPosition(point.x, point.y, false);
    }

    public void setPosition(int n2, int n3) {
        this.setPosition(n2, n3, false);
    }

    public void setPosition(int n2, int n3, boolean bl2) {
        this.setPosition(n2, n3, bl2 ? 0 : 300);
    }

    public void setPosition(int n2, int n3, int n4) {
        if (n4 == 0 || !this.dyr) {
            if (this.dxS == null) {
                this.dxS = new Point(n2, n3);
            } else if (this.dxS.x != n2 || this.dxS.y != n3) {
                this.dxS.x = n2;
                this.dxS.y = n3;
            }
            this.dyv = true;
            this.setNeedsToPostProcess();
        } else {
            ard_0 ard_02 = new ard_0(this.dxS.x, this.dxS.y, n2, n3, this, 0, n4, ys.aCq);
            this.a(ard_02);
        }
    }

    public void setX(int n2) {
        this.setPosition(n2, this.dxS.y, false);
    }

    public int getX() {
        return this.dxS.x;
    }

    public int getDisplayX() {
        return this.getScreenX();
    }

    public void setY(int n2) {
        this.setPosition(this.dxS.x, n2, false);
    }

    public int getY() {
        return this.dxS.y;
    }

    public int getDisplayY() {
        return this.getScreenY();
    }

    public ago_2 getMasterRootContainer() {
        if (this.dxR != null) {
            return this.dxR.getMasterRootContainer();
        }
        return null;
    }

    public nm_0 getComputedScissor() {
        if (this.dyt == null) {
            return null;
        }
        return nm_0.k(this.getScreenX() + this.dyt.x, this.getScreenY() + this.dyt.y, this.dyt.width, this.dyt.height);
    }

    public Rectangle getScissor() {
        return this.dyt;
    }

    public void setScissor(Rectangle rectangle) {
        this.dyt = rectangle;
    }

    public boolean isExpandable() {
        return this.dya;
    }

    public void setExpandable(boolean bl2) {
        this.dya = bl2;
    }

    public boolean isShrinkable() {
        return this.dyb;
    }

    public void setShrinkable(boolean bl2) {
        this.dyb = bl2;
    }

    public boolean getGreedy() {
        return this.dxZ;
    }

    public void setGreedy(boolean bl2) {
        this.dxZ = bl2;
    }

    public void setCursorType(xy_0 xy_02) {
        this.dyp = xy_02;
    }

    public xy_0 getCursorType() {
        return this.dyp;
    }

    public boolean getVisible() {
        return this.aQv;
    }

    public boolean isParentVisible() {
        return this.dyd;
    }

    protected void setParentVisible(boolean bl2) {
        this.dyd = bl2;
    }

    public boolean getUsedInLayout() {
        return this.getVisible();
    }

    public void setVisible(boolean bl2) {
        if (bl2 != this.aQv) {
            this.aQv = bl2;
            if (this.dxR != null) {
                this.dxR.Am();
                this.dxR.setNeedsToResetMeshes();
            }
            this.setParentVisible(bl2 && (this.dxR == null || this.dxR.isParentVisible()));
            this.setNeedsToPostProcess();
        }
    }

    public boolean isVisible() {
        return this.aQv;
    }

    public void setUsedInLayout(boolean bl2) {
        this.setVisible(bl2);
    }

    public boolean getUseResizeTween() {
        return this.dyq;
    }

    public void setUseResizeTween(boolean bl2) {
        this.dyq = bl2;
    }

    public boolean getUsePositionTween() {
        return this.dyr;
    }

    public void setUsePositionTween(boolean bl2) {
        this.dyr = bl2;
    }

    public boolean getEnabled() {
        return this.OD;
    }

    public void setEnabled(boolean bl2) {
        this.OD = bl2;
        nx_2 nx_22 = new nx_2(this, this.OD);
        this.f(nx_22);
    }

    public boolean getFocusable() {
        return this.dye;
    }

    public void setFocusable(boolean bl2) {
        if (bl2 && !this.dye) {
            lb_2.XL().e(this);
        } else if (!bl2 && this.dye) {
            lb_2.XL().f(this);
        }
        this.dye = bl2;
    }

    public void setFocused(boolean bl2) {
        if (lb_2.XL().XM() != this && bl2) {
            lb_2.XL().g(this);
        } else if (lb_2.XL().XM() == this) {
            lb_2.XL().XP();
        }
    }

    public aht_1 getRootFocusParent() {
        if (this.dxR != null) {
            return this.dxR.getRootFocusParent();
        }
        return null;
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc) {
            return null;
        }
        if (this.aQv && !this.dyc && this.getAppearance().aY(n2, n3) && !ago_2.getInstance().isMovePointMode()) {
            return this;
        }
        return null;
    }

    public void setNeedsToResetMeshes() {
        this.dys = true;
        this.setNeedsToPostProcess();
    }

    public boolean getNeedsToResetMeshes() {
        return this.dys;
    }

    public Zb getAppearance() {
        return this.cLZ;
    }

    public boolean setAppearance(Zb zb) {
        boolean bl2 = false;
        if (this.isAppearanceCompatible(zb)) {
            if (this.cLZ != null && this.cLZ != zb) {
                zb.setWidget(this);
                this.k(this.cLZ);
                this.cLZ = zb;
                bl2 = true;
            } else if (this.cLZ == null) {
                this.cLZ = zb;
                bl2 = true;
            }
        } else if (zb != null) {
            zb.aab();
        }
        return bl2;
    }

    public abstract boolean isAppearanceCompatible(Zb var1);

    public void setDecoratorState(String string) {
        if (this.cLZ != null && !this.cLZ.getCurrentState().equalsIgnoreCase(string)) {
            this.cLZ.anj();
            this.cLZ.setEnabled(string, true);
        }
    }

    public void kU(String string) {
        if (string == null) {
            return;
        }
        boolean bl2 = false;
        for (int j = 0; j < this.dyf.length; ++j) {
            if (this.dyf[j] != null) continue;
            this.dyf[j] = string;
            bl2 = true;
            break;
        }
        if (!bl2) {
            String[] stringArray = new String[this.dyf.length + 5];
            this.dyf = stringArray;
            System.arraycopy(this.dyf, 0, stringArray, 0, this.dyf.length);
            this.dyf[j] = string;
        }
        this.dyg = true;
        this.setNeedsToPreProcess();
    }

    public void setStyle(String string, boolean bl2) {
        if (this.dyf == null) {
            return;
        }
        if (bl2 || !string.equals(this.dyf[0])) {
            this.dyf[0] = string;
            if (this.byP) {
                if (this.cLZ != null) {
                    this.cLZ.Pj();
                }
                for (int j = 0; j < this.dyf.length; ++j) {
                    if (this.dyf[j] == null) continue;
                    add_1.aOG().yh().a(this, this.dyf[j]);
                }
                if (this.dxQ != null) {
                    this.dxQ.a((air_1)this.cLZ);
                }
                this.dyg = false;
            }
        }
    }

    public void setStyle(String string) {
        try {
            this.setStyle(string, false);
        }
        catch (Exception exception) {
            a.error((Object)"Exception ", (Throwable)exception);
        }
    }

    public String getThemeElementName() {
        return this.dyi;
    }

    public void setThemeElementName(String string) {
        this.dyi = string;
    }

    public String getThemeElementParentType() {
        return this.dyj;
    }

    public void setThemeElementParentType(String string) {
        this.dyj = string;
    }

    public String getStyle() {
        return this.dyf[0] == null ? "" : this.dyf[0];
    }

    public String[] getStyles() {
        return this.dyf;
    }

    public adg_2 getWidgetByThemeElementName(String string, boolean bl2) {
        if (this.dyh != null) {
            return (adg_2)this.dyh.get(string.toUpperCase());
        }
        return null;
    }

    public adg_2 getWidgetByThemeElementName(String string) {
        return this.getWidgetByThemeElementName(string, false);
    }

    public boolean setLayoutData(dz_2 dz_22) {
        boolean bl2 = false;
        if (this.dyk != null && this.dyk != dz_22) {
            this.k(this.dyk);
            this.dyk = dz_22;
            bl2 = true;
        } else if (this.dyk == null) {
            this.dyk = dz_22;
            bl2 = true;
        }
        return bl2;
    }

    public dz_2 getLayoutData() {
        return this.dyk;
    }

    public kn_1 getDragAndDropParent() {
        return this.dyl;
    }

    public void setDragAndDropParent(kn_1 kn_12) {
        this.dyl = kn_12;
    }

    public void setOnFocusChange(awX awX2) {
        this.a(awX2.aV(), awX2, true);
    }

    public void setOnClick(apc apc2) {
        this.a(apc2.aV(), apc2, true);
    }

    public void setOnDoubleClick(auh_0 auh_02) {
        this.a(auh_02.aV(), auh_02, true);
    }

    public void setOnMouseEnter(gb_0 gb_02) {
        this.a(gb_02.aV(), gb_02, true);
    }

    public void setOnMouseExit(Se se) {
        this.a(se.aV(), se, true);
    }

    public void setOnMousePress(Lw lw) {
        this.a(lw.aV(), lw, true);
    }

    public void setOnMouseRelease(aCb aCb2) {
        this.a(aCb2.aV(), aCb2, true);
    }

    public void setOnMouseWheel(fk_1 fk_12) {
        this.a(fk_12.aV(), fk_12, true);
    }

    public void setOnMouseDrag(Tg tg) {
        this.a(tg.aV(), tg, true);
    }

    public void setOnMouseDragOut(aqz aqz2) {
        this.a(aqz2.aV(), aqz2, true);
    }

    public void setOnMouseDragIn(to_0 to_02) {
        this.a(to_02.aV(), to_02, true);
    }

    public void setOnKeyPress(nh_0 nh_02) {
        this.a(nh_02.aV(), nh_02, true);
    }

    public void setOnKeyRelease(amv_2 amv_22) {
        this.a(amv_22.aV(), amv_22, true);
    }

    public void setOnKeyType(wf_1 wf_12) {
        this.a(wf_12.aV(), wf_12, true);
    }

    public void setOnPopupDisplay(adz_0 adz_02) {
        this.a(adz_02.aV(), adz_02, true);
    }

    public void setOnPopupHide(pf_1 pf_12) {
        this.a(pf_12.aV(), pf_12, true);
    }

    public void setOnSelectionChange(ala_0 ala_02) {
        this.a(ala_02.aV(), ala_02, true);
    }

    public void setOnSliderMove(fu_1 fu_12) {
        this.a(fu_12.aV(), fu_12, true);
    }

    public void setOnStick(Cm cm) {
        this.a(cm.aV(), cm, true);
    }

    public void setOnValueChange(ez_1 ez_12) {
        this.a(ez_12.aV(), ez_12, true);
    }

    public void setOnListSelectionChange(alw_0 alw_02) {
        this.a(alw_02.aV(), alw_02, true);
    }

    public void setOnItemOver(aq_0 aq_02) {
        this.a(aq_02.aV(), aq_02, true);
    }

    public void setOnItemOut(nX nX2) {
        this.a(nX2.aV(), nX2, true);
    }

    public void setOnItemClick(fk fk2) {
        this.a(fk2.aV(), fk2, true);
    }

    public void setOnItemDoubleClick(aBn aBn2) {
        this.a(aBn2.aV(), aBn2, true);
    }

    public void setOnDrag(anb_0 anb_02) {
        this.a(qe_1.bFc, anb_02, false);
    }

    public void setOnDrop(av_2 av_22) {
        this.a(qe_1.bFf, av_22, false);
    }

    public void setOnDropOut(jd_2 jd_22) {
        this.a(qe_1.bFg, jd_22, false);
    }

    public void setOnDragOut(aza_0 aza_02) {
        this.a(qe_1.bFd, aza_02, false);
    }

    public void setOnDragOver(nf_0 nf_02) {
        this.a(qe_1.bFe, nf_02, false);
    }

    public aht_1 getWidgetParentOfType(Class clazz) {
        if (this.dxR == null) {
            return null;
        }
        if (clazz.isAssignableFrom(this.dxR.getClass())) {
            return this.dxR;
        }
        return this.dxR.getWidgetParentOfType(clazz);
    }

    public void setUserDefinedSize(boolean bl2) {
        vf_0 vf_02;
        if (this.byR == null) {
            this.byR = new vf_0(this);
        }
        if ((vf_02 = (vf_0)this.byR).Bt() == bl2) {
            return;
        }
        vf_02.aN(bl2);
        this.Xq();
    }

    public boolean isSizeInitByUserDefinition() {
        return this.byR != null && ((vf_0)this.byR).Bt() && (this.aLd.getWidth() != 0.0 || this.aLd.getHeight() != 0.0);
    }

    public void setUserDefinedPosition(boolean bl2) {
        vf_0 vf_02;
        if (this.byR == null) {
            this.byR = new vf_0(this);
        }
        if ((vf_02 = (vf_0)this.byR).Bs() == bl2) {
            return;
        }
        vf_02.aM(bl2);
        this.Xq();
    }

    public boolean isPositionInitByUserDefinition() {
        return this.byR != null && ((vf_0)this.byR).Bs() && (this.dxS.getX() != 0.0 || this.dxS.getY() != 0.0);
    }

    public boolean f(ke ke2) {
        if (this.OD || !(ke2 instanceof cq_2)) {
            return super.f(ke2);
        }
        ke2.release();
        return false;
    }

    public void invalidate() {
        if (this.cLZ != null) {
            this.cLZ.invalidate();
        }
        super.invalidate();
    }

    public boolean isInWidgetTree() {
        if (this.dxR != null) {
            return this.dxR.isInWidgetTree();
        }
        return false;
    }

    public void yx() {
        Class clazz;
        aht_1 aht_12;
        aLH aLH2;
        if (this.dyi != null && this.dyj != null && (aLH2 = ye_2.amJ().ij(this.dyj)) != null && (aht_12 = this.getWidgetParentOfType(clazz = aLH2.abM())) != null) {
            aht_12.a(this.dyi, this);
        }
    }

    public void EO() {
    }

    public void Aj() {
        super.Aj();
        this.dxQ = (Zb)this.cLZ.aah();
        if (this.dyf[0] == null) {
            this.setStyle("", true);
        } else {
            this.setStyle(this.dyf[0], true);
        }
    }

    public void j() {
        lb_2.XL().f(this);
        super.j();
        if (ago_2.getInstance() != null) {
            ago_2.getInstance().r(this);
        }
        this.arC.a((ub_0)null);
        this.arC.b((ub_0)null);
        this.arC.HF();
        this.arC = null;
        this.aLd = null;
        this.aLb = null;
        this.dxX = null;
        this.dxV = null;
        this.dxS = null;
        this.dyt = null;
        this.dxR = null;
        this.dyl = null;
        if (this.dxQ != null) {
            this.dxQ.j();
            this.dxQ = null;
        }
        this.cLZ = null;
        Arrays.fill(this.dyf, null);
        this.dyi = null;
        this.dyj = null;
        if (this.dyh != null) {
            this.dyh.clear();
            this.dyh = null;
        }
        this.dyk = null;
        if (this.dym != null) {
            String string = this.dym.getText();
            if (string != null && string.equals(add_1.aOG().aON().awF().getText())) {
                add_1.aOG().aPa();
            }
            this.dym = null;
        }
        this.bTW = null;
        this.dyo = null;
        this.dyn = null;
    }

    public void b() {
        super.b();
        this.dya = true;
        this.dyb = false;
        this.dxZ = false;
        this.dyd = false;
        this.OD = true;
        this.dye = false;
        this.dyg = false;
        this.dyu = false;
        this.dyv = true;
        this.dyp = xy_0.bYl;
        this.dyq = false;
        this.dyr = false;
        this.dys = true;
        avz avz2 = new avz();
        avz2.OH();
        this.arC = (EntityGroup)yW.FL().a(EntityGroup.it(), EntityGroup.class);
        this.arC.aUM().a(avz2);
        kb_2 kb_22 = new kb_2(this);
        this.arC.a(kb_22);
        this.arC.b(kb_22);
        this.arC.ki().e(new agu_0(10000.0f, 0.0f, 0.0f));
        this.dxU = false;
        this.dxY = false;
        this.dxW = false;
        this.dxS = new Point(0, 0);
        this.aLd = new agj_1(0, 0);
        this.aQv = true;
        this.dyc = false;
        this.setNeedsToPostProcess();
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.dyg) {
            this.setStyle(this.dyf[0], true);
        }
        return bl2;
    }

    public boolean cb(int n2) {
        boolean bl2 = super.cb(n2);
        if (this.dys && this.arC != null) {
            this.arC.removeAllChildren();
            this.anf();
        }
        if (this.dyv && this.aQv && this.arC != null) {
            avz avz2;
            agu_0 agu_02;
            int n3 = this.dxS.x;
            int n4 = this.dxS.y;
            if (this.dxR != null) {
                n3 += this.dxR.getAppearance().getLeftInset();
                n4 += this.dxR.getAppearance().getBottomInset();
            }
            if ((agu_02 = (avz2 = (avz)this.arC.aUM().aI(0)).aIG()).getX() != (float)n3 || agu_02.getY() != (float)n4) {
                avz2.e(n3, n4, 0.0f);
                this.arC.aUM().b(0, avz2);
            }
            this.dyv = false;
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[").append(this.getClass().getSimpleName()).append("] ");
        if (this.rE != null) {
            stringBuilder.append("id = ").append(this.rE).append(" ");
        }
        if (this.dxS != null) {
            stringBuilder.append("Position <").append(this.dxS.x).append(", ").append(this.dxS.y).append("> ");
        }
        if (this.aLd != null) {
            stringBuilder.append("Taille (").append(this.aLd.width).append(", ").append(this.aLd.height).append(")");
        }
        if (this.rE != null) {
            stringBuilder.append("Id=").append(this.getId());
        }
        if (this.isUnloading()) {
            stringBuilder.append("released");
        }
        return stringBuilder.toString();
    }

    public void a(air_1 air_12) {
        adg_2 adg_22 = (adg_2)air_12;
        super.a((air_1)adg_22);
        adg_22.OD = this.OD;
        adg_22.dya = this.dya;
        adg_22.dyb = this.dyb;
        adg_22.dxZ = this.dxZ;
        adg_22.setFocusable(this.dye);
        if (this.dxX != null) {
            adg_22.dxX = this.dxX.awl();
        }
        adg_22.dxY = this.dxY;
        if (this.dxV != null) {
            adg_22.dxV = this.dxV.awl();
        }
        adg_22.dxW = this.dxW;
        if (this.aLb != null) {
            adg_22.aLb = this.aLb.awl();
        }
        adg_22.dxU = this.dxU;
        adg_22.dyc = this.dyc;
        adg_22.dxS = (Point)this.dxS.clone();
        adg_22.aLd = (agj_1)this.aLd.clone();
        adg_22.dyp = this.dyp;
        System.arraycopy(this.dyf, 0, adg_22.dyf, 0, this.dyf.length);
        adg_22.dyi = this.dyi;
        adg_22.dyj = this.dyj;
        adg_22.aQv = this.aQv;
        Zb zb = adg_22.dxQ = this.dxQ != null ? (Zb)this.dxQ.aah() : null;
        if (this.byR != null) {
            adg_22.setUserDefinedSize(this.isSizeInitByUserDefinition());
            adg_22.setUserDefinedPosition(this.isPositionInitByUserDefinition());
        }
    }

    public final boolean aPx() {
        return this.dyu;
    }

    public void setNeedsScissor(boolean bl2) {
        this.dyu = bl2;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cXP) {
            this.setSize(if_12.eL(string));
        } else if (n2 == dyx) {
            this.setMaxSize(if_12.eL(string));
        } else if (n2 == dyw) {
            this.setPrefSize(if_12.eL(string));
        } else if (n2 == dyy) {
            this.setExpandable(Gr.getBoolean(string));
        } else if (n2 == dyz) {
            this.setShrinkable(Gr.getBoolean(string));
        } else if (n2 == dyA) {
            this.setGreedy(Gr.getBoolean(string));
        } else if (n2 == cMb) {
            this.setEnabled(Gr.getBoolean(string));
        } else if (n2 == dyB) {
            this.setFocusable(Gr.getBoolean(string));
        } else if (n2 == dyC) {
            this.setFocused(Gr.getBoolean(string));
        } else if (n2 == dyD || n2 == dyE) {
            this.setVisible(Gr.getBoolean(string));
        } else if (n2 == dyF) {
            this.setUsePositionTween(Gr.getBoolean(string));
        } else if (n2 == dyG) {
            this.setUseResizeTween(Gr.getBoolean(string));
        } else if (n2 == ars) {
            this.setX(Gr.R(string));
        } else if (n2 == art) {
            this.setY(Gr.R(string));
        } else if (n2 == dyH) {
            this.setStyle(if_12.eM(string));
        } else if (n2 == dyI) {
            this.setThemeElementName(if_12.eM(string));
        } else if (n2 == dyJ) {
            this.setThemeElementParentType(if_12.eM(string));
        } else if (n2 == dyK) {
            this.setNonBlocking(Gr.getBoolean(string));
        } else if (n2 == dyL) {
            this.setCursorType(xy_0.gH(string));
        } else if (n2 == dyM) {
            this.setNeedsScissor(Gr.getBoolean(string));
        } else if (n2 == dyN) {
            this.setUserDefinedSize(Gr.getBoolean(string));
        } else if (n2 == dyO) {
            this.setUserDefinedPosition(Gr.getBoolean(string));
        } else if (n2 == xI) {
            this.setOnDrag((anb_0)if_12.c(anb_0.class, string));
        } else if (n2 == xJ) {
            this.setOnDrop((av_2)if_12.c(av_2.class, string));
        } else if (n2 == xK) {
            this.setOnDragOut((aza_0)if_12.c(aza_0.class, string));
        } else if (n2 == xL) {
            this.setOnDropOut((jd_2)if_12.c(jd_2.class, string));
        } else if (n2 == xM) {
            this.setOnDragOver((nf_0)if_12.c(nf_0.class, string));
        } else if (n2 == xm) {
            this.setOnClick((apc)if_12.c(apc.class, string));
        } else if (n2 == xn) {
            this.setOnDoubleClick((auh_0)if_12.c(auh_0.class, string));
        } else if (n2 == xo) {
            this.setOnFocusChange((awX)if_12.c(awX.class, string));
        } else if (n2 == xp) {
            this.setOnItemClick((fk)if_12.c(fk.class, string));
        } else if (n2 == xq) {
            this.setOnItemDoubleClick((aBn)if_12.c(aBn.class, string));
        } else if (n2 == xr) {
            this.setOnItemOut((nX)if_12.c(nX.class, string));
        } else if (n2 == xs) {
            this.setOnItemOver((aq_0)if_12.c(aq_0.class, string));
        } else if (n2 == xt) {
            this.setOnKeyPress((nh_0)if_12.c(nh_0.class, string));
        } else if (n2 == xu) {
            this.setOnKeyRelease((amv_2)if_12.c(amv_2.class, string));
        } else if (n2 == xv) {
            this.setOnKeyType((wf_1)if_12.c(wf_1.class, string));
        } else if (n2 == xw) {
            this.setOnListSelectionChange((alw_0)if_12.c(alw_0.class, string));
        } else if (n2 == xx) {
            this.setOnMouseDrag((Tg)if_12.c(Tg.class, string));
        } else if (n2 == xy) {
            this.setOnMouseDragIn((to_0)if_12.c(to_0.class, string));
        } else if (n2 == xz) {
            this.setOnMouseDragOut((aqz)if_12.c(aqz.class, string));
        } else if (n2 == xA) {
            this.setOnMouseEnter((gb_0)if_12.c(gb_0.class, string));
        } else if (n2 == xB) {
            this.setOnMouseExit((Se)if_12.c(Se.class, string));
        } else if (n2 == xD) {
            this.setOnMousePress((Lw)if_12.c(Lw.class, string));
        } else if (n2 == xE) {
            this.setOnMouseRelease((aCb)if_12.c(aCb.class, string));
        } else if (n2 == xF) {
            this.setOnMouseWheel((fk_1)if_12.c(fk_1.class, string));
        } else if (n2 == xG) {
            this.setOnSelectionChange((ala_0)if_12.c(ala_0.class, string));
        } else if (n2 == xH) {
            this.setOnSliderMove((fu_1)if_12.c(fu_1.class, string));
        } else if (n2 == dyQ) {
            this.setOnStick((Cm)if_12.c(Cm.class, string));
        } else if (n2 == xN) {
            this.setOnPopupDisplay((adz_0)if_12.c(adz_0.class, string));
        } else if (n2 == xO) {
            this.setOnPopupHide((pf_1)if_12.c(pf_1.class, string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cXP) {
            this.setSize((agj_1)object);
        } else if (n2 == dyx) {
            this.setMaxSize((agj_1)object);
        } else if (n2 == dyw) {
            this.setPrefSize((agj_1)object);
        } else if (n2 == dyy) {
            this.setExpandable(Gr.getBoolean(object));
        } else if (n2 == dyz) {
            this.setShrinkable(Gr.getBoolean(object));
        } else if (n2 == dyA) {
            this.setGreedy(Gr.getBoolean(object));
        } else if (n2 == cMb) {
            this.setEnabled(Gr.getBoolean(object));
        } else if (n2 == dyB) {
            this.setFocusable(Gr.getBoolean(object));
        } else if (n2 == dyC) {
            this.setFocused(Gr.getBoolean(object));
        } else if (n2 == dyD || n2 == dyE) {
            this.setVisible(Gr.getBoolean(object));
        } else if (n2 == dyF) {
            this.setUsePositionTween(Gr.getBoolean(object));
        } else if (n2 == dyG) {
            this.setUseResizeTween(Gr.getBoolean(object));
        } else if (n2 == ars) {
            this.setX(Gr.R(object));
        } else if (n2 == art) {
            this.setY(Gr.R(object));
        } else if (n2 == dyH) {
            this.setStyle((String)object);
        } else if (n2 == dyI) {
            this.setThemeElementName((String)object);
        } else if (n2 == dyJ) {
            this.setThemeElementParentType((String)object);
        } else if (n2 == dyK) {
            this.setNonBlocking(Gr.getBoolean(object));
        } else if (n2 == dyL) {
            this.setCursorType((xy_0)((Object)object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    public air_1 getNewElement(String string, na_1 na_12, Stack stack, afq_1 afq_12) {
        air_1 air_12 = super.getNewElement(string, na_12, stack, afq_12);
        if (air_12 instanceof Zb && this.cLZ != null) {
            air_12.release();
            air_12 = this.getAppearance();
        }
        return air_12;
    }
}

