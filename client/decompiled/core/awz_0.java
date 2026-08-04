/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from awz
 */
public class awz_0
extends aNZ {
    public static final String TAG = "RadioGroup";
    private ArrayList dhW = new ArrayList();
    private String ayx;
    public static final int dL = "value".hashCode();

    public String getTag() {
        return TAG;
    }

    public String getValue() {
        return this.ayx;
    }

    public void setValue(String string) {
        this.ayx = string;
        for (dl_1 dl_12 : this.dhW) {
            if (dl_12.getValue() == null || !dl_12.getValue().equalsIgnoreCase(string)) continue;
            dl_12.setSelected(true);
            break;
        }
    }

    public ArrayList getRadioButtonList() {
        return this.dhW;
    }

    public void a(dl_1 dl_12) {
        if (!this.dhW.contains(dl_12)) {
            this.dhW.add(dl_12);
        }
    }

    public void b(dl_1 dl_12) {
        this.dhW.remove(dl_12);
    }

    public void setRadioButtonList(ArrayList arrayList) {
        this.dhW = arrayList;
    }

    public void setElementMap(aji_1 aji_12) {
        super.setElementMap(aji_12);
        if (this.rE != null) {
            this.blb.a(this.rE, this);
        }
    }

    public void setId(String string) {
        if (this.blb != null && this.blb.is(this.rE)) {
            this.blb.it(this.rE);
        }
        super.setId(string);
        if (this.blb != null) {
            this.blb.a(this.rE, this);
        }
    }

    public void j() {
        super.j();
        this.dhW.clear();
    }

    public void a(air_1 air_12) {
        awz_0 awz_02 = (awz_0)air_12;
        super.a((air_1)awz_02);
        awz_02.ayx = this.ayx;
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != dL) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setValue(if_12.eM(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 != dL) {
            return super.setPropertyAttribute(n2, object);
        }
        this.setValue(String.valueOf(object));
        return true;
    }
}

