/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.Point;
import java.util.ArrayList;

/*
 * Renamed from aHt
 */
public class aht_1
extends adg_2 {
    public static final String TAG = "Container";
    private static final acl_0 uG = new ym_0(new awd_0());
    protected final ArrayList dMc = new ArrayList();
    protected boolean dMd = false;
    protected a_0 dMe;
    protected boolean dMf = false;
    protected boolean dMg = false;
    protected boolean dMh = false;
    private agj_1 dMi = null;
    private agj_1 dMj = null;
    private boolean dMk = false;
    public static final int dMl = "pack".hashCode();

    public void h(adg_2 adg_22) {
        this.b(adg_22, this.dMc.size());
    }

    public boolean b(adg_2 adg_22, int n2) {
        if (n2 < 0 || n2 > this.dMc.size()) {
            a.error((Object)("Tentative d'ajout d'un widget a un parent avec un index invalide (index=" + n2 + ", taille=" + this.dMc.size()));
        } else if (this.dMc.contains(adg_22)) {
            a.error((Object)"Tentative d'ajout d'un widget d\u00e9j\u00e0 contenu");
        } else {
            this.dMc.add(n2, adg_22);
            adg_22.setContainerParent(this);
            if (this.isInWidgetTree()) {
                adg_22.yx();
            }
            this.dMf = true;
            this.setNeedsToPreProcess();
            return true;
        }
        return false;
    }

    public void b(adg_2 adg_22) {
        if (adg_22 != null && this.dMc != null && this.dMc.contains(adg_22)) {
            if (this.isInWidgetTree()) {
                adg_22.EO();
            }
            this.dMc.remove(adg_22);
            adg_22.setContainerParent(null);
            this.dMf = true;
            this.setNeedsToPreProcess();
            adg_22.aaa();
        }
    }

    public void setWidgetOnTop(adg_2 adg_22) {
        if (adg_22 != null && this.dMc != null && this.dMc.contains(adg_22)) {
            this.dMc.remove(adg_22);
            this.h(adg_22);
        }
    }

    public void a(amx_1 amx_12) {
        super.a(amx_12);
        if (amx_12 instanceof a_0) {
            this.setLayoutManager((a_0)amx_12);
        }
    }

    public void c(adg_2 adg_22, int n2) {
        if (this.b(adg_22, n2)) {
            super.a(adg_22, false);
        } else if (adg_22 != null) {
            adg_22.aab();
            a.warn((Object)"On lib\u00e8re le Widget qui n'a pas p\u00fb \u00eatre ajout\u00e9");
        }
    }

    public void aTX() {
        for (adg_2 adg_22 : this.dMc) {
            if (this.isInWidgetTree()) {
                adg_22.EO();
            }
            adg_22.setContainerParent(null);
        }
        this.dMc.clear();
        this.dMf = true;
        this.setNeedsToPreProcess();
    }

    protected void pX() {
        super.pX();
        int n2 = this.dMc.size();
        for (int j = 0; j < n2; ++j) {
            adg_2 adg_22 = (adg_2)this.dMc.get(j);
            Entity entity = adg_22.getEntity();
            if (!adg_22.getVisible() || entity == null) continue;
            this.arC.i(entity);
        }
    }

    public String getTag() {
        return TAG;
    }

    public ArrayList getWidgetChildren() {
        return this.dMc;
    }

    public Zb getAppearance() {
        return this.cLZ;
    }

    public boolean isAppearanceCompatible(Zb zb) {
        return true;
    }

    public void setLayoutManager(a_0 a_02) {
        if (this.dMe != null && !this.dMe.isUnloading()) {
            this.dMe.release();
        }
        this.dMe = a_02;
    }

    public aiD getLayoutManager() {
        return this.dMe;
    }

    public void setVisible(boolean bl2) {
        if (bl2 != this.aQv) {
            this.setNeedsToPreProcess();
            this.setNeedsToMiddleProcess();
        }
        super.setVisible(bl2);
    }

    protected void setParentVisible(boolean bl2) {
        if (bl2 != this.dyd) {
            super.setParentVisible(bl2);
            for (int j = this.dMc.size() - 1; j >= 0; --j) {
                ((adg_2)this.dMc.get(j)).setParentVisible(bl2);
            }
        }
    }

    public agj_1 getMaxSize() {
        if (this.dMe != null) {
            return this.dMe.getContentPreferedSize(this);
        }
        return new agj_1(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    public agj_1 getContentMinSize() {
        if (this.dMe != null) {
            if (this.dMi == null) {
                this.dMi = this.dMe.getContentMinSize(this);
            }
            int n2 = this.aLb == null ? this.dMi.width : Math.max(this.dMi.width, this.aLb.width);
            int n3 = this.aLb == null ? this.dMi.height : Math.max(this.dMi.height, this.aLb.height);
            return new agj_1(n2, n3);
        }
        return super.getContentMinSize();
    }

    public agj_1 getContentPrefSize() {
        if (this.dMe != null) {
            if (this.dMj == null) {
                this.dMj = this.dMe.getContentPreferedSize(this);
            }
            int n2 = this.dxV == null ? this.dMj.width : Math.max(this.dMj.width, this.dxV.width);
            int n3 = this.dxV == null ? this.dMj.height : Math.max(this.dMj.height, this.dxV.height);
            return new agj_1(n2, n3);
        }
        return super.getContentPrefSize();
    }

    public agj_1 getContentGreedySize() {
        if (this.dxR == null) {
            return new agj_1(this.cLZ.getContentWidth(), this.cLZ.getContentHeight());
        }
        agj_1 agj_12 = this.dxR.getContentGreedySize();
        agj_12.width -= this.dxR.getAppearance().getLeftInset() + this.dxR.getAppearance().getRightInset();
        agj_12.height -= this.dxR.getAppearance().getTopInset() + this.dxR.getAppearance().getBottomInset();
        return this.dxR.getLayoutManager().getContentGreedySize(this.dxR, this, agj_12);
    }

    public boolean isRootFocusContainer() {
        return this.dMd;
    }

    public void setRootFocusContainer(boolean bl2) {
        this.dMd = bl2;
    }

    public aht_1 getRootFocusParent() {
        if (this.dMd) {
            return this;
        }
        return super.getRootFocusParent();
    }

    public void setNonBlocking(boolean bl2) {
        this.setNonBlocking(bl2, false);
    }

    public void setNonBlocking(boolean bl2, boolean bl3) {
        super.setNonBlocking(bl2);
        if (bl3) {
            for (adg_2 adg_22 : this.getWidgetChildren()) {
                if (adg_22 instanceof aht_1) {
                    ((aht_1)adg_22).setNonBlocking(bl2, bl3);
                    continue;
                }
                adg_22.setNonBlocking(bl2);
            }
        }
    }

    public boolean getInvalidateOnMinSizeChange() {
        return this.dMh;
    }

    public void setInvalidateOnMinSizeChange(boolean bl2) {
        this.dMh = bl2;
    }

    public void setPack(boolean bl2) {
        this.dMg = bl2;
    }

    public boolean getPack() {
        return this.dMg;
    }

    public adg_2 getWidget(int n2, int n3) {
        if (this.czc || !this.aQv || !this.getAppearance().aY(n2, n3)) {
            return null;
        }
        adg_2 adg_22 = this.dyc ? null : this;
        n2 -= this.getAppearance().getLeftInset();
        n3 -= this.getAppearance().getBottomInset();
        for (int j = 0; j < this.dMc.size(); ++j) {
            adg_2 adg_23 = (adg_2)this.dMc.get(j);
            if (adg_23.isUnloading() || (adg_23 = adg_23.getWidget(n2 - adg_23.dxS.x, n3 - adg_23.dxS.y)) == null) continue;
            adg_22 = adg_23;
        }
        return adg_22;
    }

    public adg_2 getWidget(int n2) {
        try {
            return (adg_2)this.dMc.get(n2);
        }
        catch (Exception exception) {
            return null;
        }
    }

    public adg_2 getNextFocusableWidget() {
        return null;
    }

    public nm_0 getScissor(adg_2 adg_22) {
        Point point = this.getScreenPosition();
        int n2 = point.x + this.cLZ.getLeftInset();
        int n3 = point.y + this.cLZ.getBottomInset();
        int n4 = this.cLZ.getContentWidth();
        int n5 = this.cLZ.getContentHeight();
        if (adg_22 == null) {
            return nm_0.k(n2, n3, n4, n5);
        }
        Point point2 = adg_22.getScreenPosition();
        int n6 = point2.x;
        int n7 = point2.y;
        int n8 = adg_22.getWidth();
        int n9 = adg_22.getHeight();
        nm_0 nm_02 = nm_0.sl();
        if (nm_0.c(n6, n7, n8, n9, n2, n3, n4, n5)) {
            nm_02.b(n6, n7, n8, n9, n2, n3, n4, n5);
        }
        return nm_02;
    }

    public void yx() {
        super.yx();
        for (adg_2 adg_22 : this.dMc) {
            adg_22.yx();
        }
    }

    public void EO() {
        super.EO();
        for (adg_2 adg_22 : this.dMc) {
            adg_22.EO();
        }
    }

    protected void aTY() {
        this.dMj = null;
        this.dMi = null;
        this.dMk = true;
        this.setNeedsToMiddleProcess();
    }

    public void Am() {
        this.aTY();
        if (this.dxR != null) {
            this.dxR.Am();
        }
        if (this.dMg || this.dMh) {
            this.invalidate();
        }
    }

    public void validate() {
        super.validate();
        this.dMk = false;
        if (this.dMg) {
            this.setSizeToPrefSize();
        }
        if (this.dMe != null && this.cLZ != null) {
            this.dMe.a(this);
        }
        this.setNeedsToResetMeshes();
        this.dMf = false;
    }

    public boolean cc(int n2) {
        boolean bl2 = super.cc(n2);
        if (this.dMf) {
            this.Am();
        }
        return bl2;
    }

    public boolean gU(int n2) {
        if (this.aQv && this.dMk) {
            this.invalidate();
        }
        return super.gU(n2);
    }

    public static aht_1 checkOut() {
        aht_1 aht_12;
        try {
            aht_12 = (aht_1)uG.adr();
            aht_12.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            aht_12 = new aht_1();
            aht_12.b();
        }
        return aht_12;
    }

    public void j() {
        this.aTX();
        super.j();
        this.dMi = null;
        this.dMj = null;
        this.dMe = null;
    }

    public void b() {
        super.b();
        this.dMd = false;
        this.dMf = false;
        this.dMg = false;
        this.dMh = false;
        Zb zb = Zb.checkOut();
        zb.setWidget(this);
        this.a(zb);
        ei_1 ei_12 = ei_1.checkOut();
        this.a(ei_12);
        this.dyc = true;
    }

    public void a(air_1 air_12) {
        aht_1 aht_12 = (aht_1)air_12;
        super.a((air_1)aht_12);
        aht_12.dMg = this.dMg;
        aht_12.dMd = this.dMd;
        a_0 a_02 = null;
        if (this.dMe != null) {
            a_02 = this.dMe.c();
        }
        if (a_02 != null) {
            aht_12.setLayoutManager(a_02);
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != dMl) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setPack(Gr.getBoolean(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != dMl) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setPack(Gr.getBoolean(object));
        return true;
    }
}

