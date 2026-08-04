/*
 * Decompiled with CFR 0.152.
 */
import java.util.Map;
import org.xml.sax.Attributes;

/*
 * Renamed from Ym
 */
public abstract class ym_2
extends ka_0 {
    ayx cau;
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        this.cau = null;
        String string2 = attributes.getValue("class");
        if (dh_2.isEmpty(string2)) {
            string2 = this.zL();
            this.ef("Assuming default evaluator class [" + string2 + "]");
        }
        if (dh_2.isEmpty(string2)) {
            string2 = this.zL();
            this.kc = true;
            this.eg("Mandatory \"class\" attribute not set for <evaluator>");
            return;
        }
        String string3 = attributes.getValue("name");
        if (dh_2.isEmpty(string3)) {
            this.kc = true;
            this.eg("Mandatory \"name\" attribute not set for <evaluator>");
            return;
        }
        try {
            this.cau = (ayx)dh_2.a(string2, ayx.class, this.Pb);
            this.cau.a(this.Pb);
            this.cau.setName(string3);
            qq_02.C(this.cau);
            this.ee("Adding evaluator named [" + string3 + "] to the object stack");
        }
        catch (Exception exception) {
            this.kc = true;
            this.e("Could not create evaluator of type " + string2 + "].", exception);
        }
    }

    protected abstract String zL();

    public void a(qq_0 qq_02, String string) {
        Object object;
        if (this.kc) {
            return;
        }
        if (this.cau instanceof mt_2) {
            this.cau.start();
            this.ee("Starting evaluator named [" + this.cau.getName() + "]");
        }
        if ((object = qq_02.wa()) != this.cau) {
            this.ef("The object on the top the of the stack is not the evaluator pushed earlier.");
        } else {
            qq_02.wb();
            try {
                Map map = (Map)this.Pb.getObject("EVALUATOR_MAP");
                map.put(this.cau.getName(), this.cau);
            }
            catch (Exception exception) {
                this.e("Could not set evaluator named [" + this.cau + "].", exception);
            }
        }
    }

    public void a(qq_0 qq_02) {
    }
}

