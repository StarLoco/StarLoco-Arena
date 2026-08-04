/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from on
 */
public class on_2
extends ve_0 {
    public static final String UV = "usable";
    public static final String UW = "smallDescription";
    public static final String[] ce = new String[]{"usable", "smallDescription"};
    public static final String[] oT = new String[ce.length + ve_0.ce.length];
    private ee_2 bN;

    public on_2(ve_0 ve_02) {
        super(ve_02.getId(), ve_02.Vk(), ve_02.Bo(), ve_02.Vo(), ve_02.Vp(), ve_02.AA(), ve_02.Az(), ve_02.iW(), ve_02.Vq(), ve_02.getValue(), ve_02.Vr(), ve_02.Vs(), ve_02.eA(), ve_02.AD());
        for (xj_0 xj_02 : ve_02) {
            this.a(xj_02);
        }
    }

    public ee_2 tG() {
        return this.bN;
    }

    public void b(ee_2 ee_22) {
        this.bN = ee_22;
    }

    public ex_0 tH() {
        return apN.aDK().aDL().a((gn_0)this.bN, (jb_2)this, null);
    }

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals(UV)) {
            return this.tH() == ex_0.aUd;
        }
        if (string.equals(UW)) {
            ex_0 ex_02;
            StringBuilder stringBuilder = new StringBuilder(this.getName());
            if (this.Vo() != 0) {
                stringBuilder.append(" (").append(this.Vo()).append(' ').append(aon_0.aYc().getString("AP")).append(")");
            }
            if ((ex_02 = this.tH()) != ex_0.aUd) {
                stringBuilder.append('\n').append(aon_0.aYc().getString(ex_02.toString()));
            }
            return stringBuilder.toString();
        }
        return super.getFieldValue(string);
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(ve_0.ce, 0, oT, ce.length, ve_0.ce.length);
    }
}

