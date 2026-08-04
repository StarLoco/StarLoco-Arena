/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from qB
 */
public class qb_2
extends ka_0 {
    boolean kc = false;
    pm_1 aeN = null;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        String string2 = attributes.getValue("class");
        if (dh_2.isEmpty(string2)) {
            this.eg("Missing class name for statusListener. Near [" + string + "] line " + this.c(qq_02));
            this.kc = true;
            return;
        }
        try {
            this.aeN = (pm_1)dh_2.a(string2, pm_1.class, this.Pb);
            qq_02.QK().ea().a(this.aeN);
            qq_02.C(this.aeN);
        }
        catch (Exception exception) {
            this.kc = true;
            this.e("Could not create an StatusListener of type [" + string2 + "].", exception);
            throw new vf_1(exception);
        }
    }

    public void a(qq_0 qq_02) {
    }

    public void a(qq_0 qq_02, String string) {
        Object object;
        if (this.kc) {
            return;
        }
        if (this.aeN instanceof mt_2) {
            ((mt_2)((Object)this.aeN)).start();
        }
        if ((object = qq_02.wa()) != this.aeN) {
            this.ef("The object at the of the stack is not the statusListener pushed earlier.");
        } else {
            qq_02.wb();
        }
    }
}

