/*
 * Decompiled with CFR 0.152.
 */
public class auL
extends Zb {
    public static final String TAG = "TextWidgetAppearance";
    public static final String cWJ = "TextViewAppearance";
    public static final String cWK = "TextEditorAppearance";
    public static final String cWL = "LabelAppearance";
    public static final String cWM = "text";
    private static final acl_0 uG = new ym_0(new avy_0());
    private BT cG = null;
    private af_1 cWN = null;
    private vP CR = null;
    private boolean AT = false;
    private boolean cWO = false;
    private boolean aKQ = false;
    private boolean cWP = false;
    public static final int cJ = "align".hashCode();
    public static final int bBo = "alignment".hashCode();
    public static final int caJ = "justify".hashCode();
    public static final int mh = "font".hashCode();
    public static final int cWQ = "textColor".hashCode();
    public static final int caU = "useHighContrast".hashCode();

    public static auL checkOut() {
        auL auL2;
        try {
            auL2 = (auL)uG.adr();
            auL2.DG = uG;
        }
        catch (Exception exception) {
            a.error((Object)"Probl\u00e8me au borrowObject.");
            auL2 = new auL();
            auL2.b();
        }
        return auL2;
    }

    public void a(na_1 na_12) {
        super.a(na_12);
    }

    public String getTag() {
        return TAG;
    }

    public void setWidget(adg_2 adg_22) {
        if (this.cG != null && adg_22 instanceof aac) {
            ((aac)((Object)adg_22)).setAlign(this.cG);
        }
        if (this.cWN != null && adg_22 instanceof wS) {
            ((wS)((Object)adg_22)).setFont(this.cWN);
        }
        if (this.CR != null && adg_22 instanceof ajb_0) {
            ((ajb_0)((Object)adg_22)).setColor(this.CR, null);
        }
        if (this.cWO && this.DD instanceof yt_1) {
            ((yt_1)this.DD).setUseHighContrast(this.AT);
        }
        if (this.cWP && this.DD instanceof yt_1) {
            ((yt_1)this.DD).setJustify(this.aKQ);
        }
        super.setWidget(adg_22);
    }

    public void setUseHighContrast(boolean bl2) {
        this.AT = bl2;
        this.cWO = true;
        if (this.DD != null && this.DD instanceof yt_1) {
            ((yt_1)this.DD).setUseHighContrast(this.AT);
        }
    }

    public boolean getUseHighContrast() {
        return this.AT;
    }

    public boolean getJustify() {
        return this.aKQ;
    }

    public void setJustify(boolean bl2) {
        this.aKQ = bl2;
        this.cWP = true;
        if (this.DD != null && this.DD instanceof yt_1) {
            ((yt_1)this.DD).setJustify(bl2);
        }
    }

    public void setAlign(BT bT) {
        this.cG = bT;
        if (this.cG != null && this.DD != null && this.DD instanceof aac) {
            ((aac)((Object)this.DD)).setAlign(bT);
        }
    }

    public BT getAlign() {
        return this.cG;
    }

    public void setAlignment(BT bT) {
        this.setAlign(bT);
    }

    public BT getAlignment() {
        ve_2 ve_22;
        int n2;
        int n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ve_22 = (ve_2)this.cch.get(n2);
            if (!(ve_22 instanceof wd_1) || !ve_22.getState().equalsIgnoreCase(this.cck)) continue;
            return ((wd_1)ve_22).getAlignment();
        }
        n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ve_22 = (ve_2)this.cch.get(n2);
            if (!(ve_22 instanceof wd_1) || !ve_22.getState().equalsIgnoreCase("DEFAULT")) continue;
            return ((wd_1)ve_22).getAlignment();
        }
        return this.cG;
    }

    public void setFont(af_1 af_12) {
        this.cWN = af_12;
        if (this.DD != null && this.DD instanceof wS) {
            ((wS)((Object)this.DD)).setFont(af_12);
        }
    }

    public af_1 getFont() {
        ve_2 ve_22;
        int n2;
        int n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ve_22 = (ve_2)this.cch.get(n2);
            if (!(ve_22 instanceof qr) || !ve_22.getState().equalsIgnoreCase(this.cck)) continue;
            return ((qr)ve_22).getRenderer();
        }
        n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ve_22 = (ve_2)this.cch.get(n2);
            if (!(ve_22 instanceof qr) || !ve_22.getState().equalsIgnoreCase("DEFAULT")) continue;
            return ((qr)ve_22).getRenderer();
        }
        return this.cWN;
    }

    public void setColor(vP vP2, String string) {
        if (string == null || cWM.equals(string)) {
            this.setTextColor(vP2);
        } else {
            super.setColor(vP2, string);
        }
    }

    public void setTextColor(vP vP2) {
        if (this.CR == vP2) {
            return;
        }
        this.CR = vP2;
        if (this.DD instanceof ajb_0) {
            ((ajb_0)((Object)this.DD)).setColor(vP2, null);
        }
    }

    public vP getTextColor() {
        ve_2 ve_22;
        int n2;
        int n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ve_22 = (ve_2)this.cch.get(n2);
            if (!(ve_22 instanceof aab_0) || !ve_22.getState().equalsIgnoreCase(this.cck)) continue;
            return ((aab_0)ve_22).getColor();
        }
        n3 = this.cch.size();
        for (n2 = 0; n2 < n3; ++n2) {
            ve_22 = (ve_2)this.cch.get(n2);
            if (!(ve_22 instanceof aab_0) || !ve_22.getState().equalsIgnoreCase("DEFAULT")) continue;
            return ((aab_0)ve_22).getColor();
        }
        return this.CR;
    }

    public void j() {
        super.j();
        this.cG = null;
        this.cWN = null;
        this.CR = null;
    }

    public void b() {
        super.b();
        this.AT = false;
        this.cWO = false;
    }

    public void a(air_1 air_12) {
        auL auL2 = (auL)air_12;
        super.a((air_1)auL2);
        if (this.cG != null) {
            auL2.setAlign(this.cG);
        }
        if (this.cWN != null) {
            auL2.setFont(this.cWN);
        }
        if (this.CR != null) {
            auL2.setTextColor(this.CR);
        }
        if (this.cWO) {
            auL2.setUseHighContrast(this.AT);
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == cJ || n2 == bBo) {
            this.setAlign(BT.dv(string));
        } else if (n2 == caJ) {
            this.setJustify(Gr.getBoolean(string));
        } else if (n2 == mh) {
            this.setFont(if_12.eP(string));
        } else if (n2 == cWQ) {
            this.setTextColor(if_12.eK(string));
        } else if (n2 == caU) {
            this.setUseHighContrast(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == cWQ) {
            this.setTextColor((vP)object);
        } else if (n2 == cJ || n2 == bBo) {
            this.setAlign((BT)((Object)object));
        } else if (n2 == caJ) {
            this.setJustify(Gr.getBoolean(object));
        } else if (n2 == mh) {
            this.setFont((vg_2)object);
        } else if (n2 == caU) {
            this.setUseHighContrast(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }
}

