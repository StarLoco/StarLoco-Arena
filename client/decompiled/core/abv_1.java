/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from abv
 */
public class abv_1
extends ee_2 {
    public static final String cia = "breedSpells";
    public static final String cib = "weaponCards";
    public static final String cic = "petCards";
    public static final String cid = "cloakCards";
    public static final String cie = "hatCards";
    public static final String cif = "dofusCards";
    public static final String[] ce = new String[]{"breedSpells", "weaponCards", "petCards", "cloakCards", "hatCards", "dofusCards"};
    public static final String[] oT = new String[ce.length + ee_2.ce.length];
    private static final String[] cig;
    private static final String[] alz;

    public void aqf() {
        this.b(this.L().hh(1));
        azs_0.aLV().a((aho_0)this, "actorDirection");
    }

    public void aqg() {
        this.b(this.L().hh(-1));
        azs_0.aLV().a((aho_0)this, "actorDirection");
    }

    public void W(byte by) {
        super.W(by);
        azs_0.aLV().a((aho_0)this, "actorDescriptorLibrary");
        azs_0.aLV().a((aho_0)this, "backgroundUrl");
    }

    public void S(byte by) {
        super.S(by);
        azs_0.aLV().a((aho_0)this, "actorDescriptorLibrary");
    }

    public void c(yp_2 yp_22) {
        try {
            this.Oh().a(yp_22);
            this.PI();
            azs_0.aLV().a((aho_0)this, cig);
        }
        catch (gg gg2) {
        }
        catch (xR xR2) {
            // empty catch block
        }
    }

    public void d(yp_2 yp_22) {
        this.Oh().b(yp_22);
        this.PI();
        azs_0.aLV().a((aho_0)this, cig);
    }

    public void a(ve_0 ve_02, short s) {
        try {
            if (!this.Oi().o(s)) {
                this.Oi().n(s);
            }
            this.Oi().a((akU)ve_02, s);
            this.PI();
            azs_0.aLV().a((aho_0)this, alz);
        }
        catch (gg gg2) {
        }
        catch (xR xR2) {
        }
        catch (br_1 br_12) {
            // empty catch block
        }
    }

    public void e(ve_0 ve_02) {
        this.Oi().b(ve_02);
        this.PI();
        azs_0.aLV().a((aho_0)this, alz);
    }

    public ArrayList a(vi_1 vi_12) {
        ArrayList arrayList = aca_0.aOq().b(vi_12);
        ArrayList arrayList2 = new ArrayList(arrayList.size());
        je_2 je_22 = jk_1.mf().mg();
        jg_0 jg_02 = je_22.nc();
        for (int j = 0; j < arrayList.size(); ++j) {
            if (jg_02.contains(((ve_0)arrayList.get(j)).getId())) continue;
            arrayList2.add(arrayList.get(j));
        }
        return arrayList2;
    }

    public String[] getFields() {
        return oT;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cia)) {
            ArrayList arrayList = je_1.Wa().gu(this.NY().lV());
            ArrayList arrayList2 = new ArrayList(arrayList.size());
            je_2 je_22 = jk_1.mf().mg();
            jg_0 jg_02 = je_22.nb();
            for (int j = 0; j < arrayList.size(); ++j) {
                if (jg_02.contains(((yp_2)arrayList.get(j)).getId()) || ((yp_2)arrayList.get(j)).jd() != null) continue;
                arrayList2.add(arrayList.get(j));
            }
            return arrayList2.toArray();
        }
        if (string.equals(cib)) {
            return this.a(vi_1.bSW).toArray();
        }
        if (string.equals(cic)) {
            return this.a(vi_1.bSX).toArray();
        }
        if (string.equals(cid)) {
            return this.a(vi_1.bSY).toArray();
        }
        if (string.equals(cie)) {
            return this.a(vi_1.bSZ).toArray();
        }
        if (string.equals(cif)) {
            return this.a(vi_1.bTa).toArray();
        }
        return super.getFieldValue(string);
    }

    public void a(String string, Object object) {
        if (string.equals("name") && object instanceof String) {
            this.setName((String)object);
        }
    }

    public boolean l(String string) {
        return string.equals("name");
    }

    static {
        System.arraycopy(ce, 0, oT, 0, ce.length);
        System.arraycopy(ee_2.ce, 0, oT, ce.length, ee_2.ce.length);
        cig = new String[]{"spells", "value"};
        alz = new String[]{"weaponEquipment", "petEquipment", "cloakEquipment", "hatEquipment", "dofusEquipment", "actorDescriptorLibrary", "actorEquipment", "value"};
    }
}

