/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.framework.graphics.engine.entity.Entity;
import java.awt.Insets;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class Zb
extends ug_0
implements ajb_0,
and_0,
ayi {
    protected static Logger a;
    public static final String TAG = "Appearance";
    public static final String cbY = "ScrollContainerAppearance";
    public static final String cbZ = "SliderAppearance";
    public static final String cca = "ScrollBarAppearance";
    public static final String ccb = "TextEditorAppearance";
    public static final String ccc = "WindowAppearance";
    public static final String ccd = "PopupMenuAppearance";
    public static final String cce = "DEFAULT";
    public static final String ccf = "modulation";
    protected vP AC = null;
    protected final ArrayList ccg = new ArrayList();
    protected final ArrayList cch = new ArrayList();
    protected final ArrayList cci = new ArrayList();
    private String ccj = "default";
    protected String cck = "default";
    protected boolean ccl = true;
    private static final acl_0 uG;
    public static final int aHX;
    public static final int gd;

    public static Zb checkOut() {
        Zb zb;
        try {
            zb = (Zb)uG.adr();
            zb.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            zb = new Zb();
            zb.b();
        }
        return zb;
    }

    public void a(na_1 na_12) {
        super.a(na_12);
        if (na_12 instanceof py_1) {
            this.a((py_1)((Object)na_12));
        }
        if (na_12 instanceof ve_2) {
            this.a((ve_2)((Object)na_12));
        }
        if (na_12 instanceof acf_1) {
            this.a((acf_1)((Object)na_12));
        }
    }

    public void anf() {
        Entity entity = this.DD.getEntity();
        int n2 = this.ccg.size();
        for (int j = 0; j < n2; ++j) {
            py_1 py_12 = (py_1)this.ccg.get(j);
            if (!py_12.isEnabled() || py_12.getEntity() == null) continue;
            entity.i(py_12.getEntity());
        }
        this.ccl = false;
    }

    public void a(acf_1 acf_12) {
        this.a(acf_12, true);
    }

    public void a(acf_1 acf_12, boolean bl2) {
        acf_12.setDecoratorAppearance(this);
        if (bl2) {
            this.cci.add(acf_12);
        }
        if (this.DD != null) {
            this.DD.a(acf_12.getTriggerAction(), new ya_1(this, acf_12), false);
        }
        if (this.DD != null) {
            this.DD.setNeedsToResetMeshes();
        }
    }

    protected void a(ve_2 ve_22) {
        ve_22.setDecoratorAppearance(this);
        if (this.AC != null && ve_22 instanceof ayi) {
            ((ayi)((Object)ve_22)).setModulationColor(this.AC);
        }
        if (ve_22.getState() == null) {
            ve_22.setState(this.ccj);
        }
        if ((ve_22.getState().equalsIgnoreCase(cce) || ve_22.getState().equalsIgnoreCase(this.cck)) && this.DD != null) {
            ve_22.setEnabled(true);
            if (ve_22.isDecoratorSwitch()) {
                ve_22.setup(this);
            } else {
                ve_22.setup(this.DD);
            }
        }
        this.cch.add(ve_22);
        if (this.DD != null) {
            this.DD.setNeedsToResetMeshes();
        }
    }

    protected void a(py_1 py_12) {
        py_12.setDecoratorAppearance(this);
        if (py_12.getState() == null) {
            py_12.setState(this.ccj);
        }
        if (this.AC != null && py_12 instanceof ayi) {
            ((ayi)((Object)py_12)).setModulationColor(this.AC);
        }
        if ((py_12.getState().equalsIgnoreCase(cce) || py_12.getState().equalsIgnoreCase(this.cck)) && this.DD != null) {
            py_12.setEnabled(true);
        }
        if (py_12 instanceof pD) {
            this.setBorder(((pD)py_12).getInsets());
        }
        if (this.DD != null) {
            py_12.b(this.DD.aLd, this.bPw, this.bPx, this.bPy);
        }
        this.ccg.add(py_12);
        if (this.DD != null) {
            this.DD.setNeedsToResetMeshes();
        }
    }

    protected void a(pD pD2) {
        this.a((py_1)pD2);
    }

    public void b(py_1 py_12) {
        this.ccg.remove(py_12);
        if (py_12 instanceof pD) {
            this.ani();
        }
        ((na_1)((Object)py_12)).aaa();
    }

    public void ang() {
        for (int j = this.ccg.size() - 1; j >= 0; --j) {
            ((na_1)this.ccg.get(j)).aaa();
        }
        this.ccg.clear();
        this.ani();
    }

    public void c(py_1 py_12) {
        this.ccg.remove(py_12);
        ((na_1)((Object)py_12)).aab();
        if (py_12 instanceof pD) {
            this.ani();
        }
    }

    public void anh() {
        int n2;
        for (n2 = this.ccg.size() - 1; n2 >= 0; --n2) {
            ((na_1)this.ccg.get(n2)).aab();
        }
        this.ccg.clear();
        this.ani();
        for (n2 = this.cch.size() - 1; n2 >= 0; --n2) {
            ((na_1)this.cch.get(n2)).aab();
        }
        this.cch.clear();
    }

    public void Pj() {
        mH mH2;
        int n2;
        for (n2 = this.ccg.size() - 1; n2 >= 0; --n2) {
            mH2 = (py_1)this.ccg.get(n2);
            if (!mH2.isRemovable()) continue;
            ((na_1)((Object)mH2)).aab();
            this.ccg.remove(n2);
        }
        this.ani();
        for (n2 = this.cch.size() - 1; n2 >= 0; --n2) {
            mH2 = (ve_2)this.cch.get(n2);
            if (!mH2.isRemovable()) continue;
            ((na_1)((Object)mH2)).aab();
            this.cch.remove(n2);
        }
    }

    public void setState(String string) {
        this.ccj = string;
    }

    public String getState() {
        return this.ccj;
    }

    public String getCurrentState() {
        return this.cck;
    }

    public String getTag() {
        return TAG;
    }

    public void setEnabled(String string, boolean bl2) {
        this.cck = string;
        for (int j = 0; j < this.ccg.size(); ++j) {
            py_1 py_12 = (py_1)this.ccg.get(j);
            if (py_12.getLabel() == null || !py_12.getLabel().equals(string)) continue;
            py_12.setEnabled(bl2);
        }
        ArrayList arrayList = new ArrayList();
        for (int j = this.cch.size() - 1; j >= 0; --j) {
            ve_2 ve_22 = (ve_2)this.cch.get(j);
            if (ve_22.getLabel() == null || !ve_22.getLabel().equals(string)) continue;
            ve_22.setEnabled(bl2);
            if (arrayList.contains(ve_22.getClass()) || !ve_22.isEnabled()) continue;
            if (ve_22.isDecoratorSwitch()) {
                ve_22.setup(this);
            } else {
                ve_22.setup(this.getWidget());
            }
            arrayList.add(ve_22.getClass());
        }
    }

    public void setWidget(adg_2 adg_22) {
        agx_1 agx_12;
        int n2;
        super.setWidget(adg_22);
        int n3 = this.ccg.size();
        for (n2 = 0; n2 < n3; ++n2) {
            agx_12 = (py_1)this.ccg.get(n2);
            if (!agx_12.getState().equalsIgnoreCase(this.cck) || this.DD == null) continue;
            agx_12.setEnabled(true);
        }
        n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            agx_12 = (ve_2)this.cch.get(n2);
            if (!agx_12.getState().equalsIgnoreCase(this.cck) || this.DD == null) continue;
            agx_12.setEnabled(true);
            if (agx_12.isDecoratorSwitch()) continue;
            agx_12.setup(adg_22);
        }
        n3 = this.cci.size();
        for (n2 = 0; n2 < n3; ++n2) {
            agx_12 = (acf_1)this.cci.get(n2);
            this.a((acf_1)agx_12, false);
        }
        if (this.AC != null && this.DD instanceof ayi) {
            ((ayi)((Object)this.DD)).setModulationColor(this.AC);
        }
        adg_22.setNeedsToResetMeshes();
    }

    public void setNeedsToResetMeshes() {
        if (this.DD != null) {
            this.DD.setNeedsToResetMeshes();
        }
    }

    public void setColor(vP vP2, String string) {
        if (string == null || string.equalsIgnoreCase(ccf)) {
            this.setModulationColor(vP2);
        }
    }

    public void setModulationColor(vP vP2) {
        mH mH2;
        int n2;
        if (this.AC == vP2) {
            return;
        }
        this.AC = vP2;
        if (this.DD instanceof ayi) {
            ((ayi)((Object)this.DD)).setModulationColor(vP2);
        }
        for (n2 = this.ccg.size() - 1; n2 >= 0; --n2) {
            mH2 = (py_1)this.ccg.get(n2);
            if (!(mH2 instanceof ayi)) continue;
            ((ayi)((Object)mH2)).setModulationColor(vP2);
        }
        for (n2 = this.cch.size() - 1; n2 >= 0; --n2) {
            mH2 = (ve_2)this.cch.get(n2);
            if (!(mH2 instanceof ayi)) continue;
            ((ayi)((Object)mH2)).setModulationColor(vP2);
        }
    }

    public vP getModulationColor() {
        return this.AC;
    }

    public void ani() {
        Insets insets = null;
        for (int j = this.ccg.size() - 1; j >= 0; --j) {
            py_1 py_12 = (py_1)this.ccg.get(j);
            if (!(py_12 instanceof pD)) continue;
            insets = ((pD)py_12).getInsets();
            break;
        }
        if (insets == null) {
            insets = new Insets(0, 0, 0, 0);
        }
        this.setBorder(insets);
    }

    public void a(air_1 air_12) {
        super.a(air_12);
        Zb zb = (Zb)air_12;
        if (this.AC != null) {
            zb.setModulationColor(this.AC);
        }
    }

    public void anj() {
        for (mH mH2 : this.ccg) {
            mH2.setEnabled(false);
        }
        for (mH mH2 : this.cch) {
            mH2.setEnabled(false);
        }
    }

    public void validate() {
        if (this.DD != null) {
            for (int j = this.ccg.size() - 1; j >= 0; --j) {
                ((py_1)this.ccg.get(j)).b(this.DD.aLd, this.bPw, this.bPx, this.bPy);
            }
        }
        super.validate();
    }

    public void j() {
        super.j();
        this.ccg.clear();
        this.cch.clear();
        this.cci.clear();
        this.AC = null;
    }

    public void b() {
        super.b();
        this.ccj = cce;
        this.cck = cce;
        this.ccl = true;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == aHX) {
            this.setModulationColor(if_12.eK(string));
        } else if (n2 == gd) {
            this.setState(if_12.eM(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == aHX) {
            this.setModulationColor((vP)object);
        } else if (n2 == gd) {
            this.setState(String.valueOf(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    static {
        ym_0 ym_02;
        a = Logger.getLogger(Zb.class);
        try {
            ym_02 = new ym_0(new yy_1(), 1000);
        }
        catch (Exception exception) {
            ym_02 = new ym_0(new yz_1());
        }
        uG = ym_02;
        aHX = "modulationColor".hashCode();
        gd = "state".hashCode();
    }
}

