/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from aml
 */
public abstract class aml_1
extends ka_0 {
    ei_2 Uq;
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        String string2 = attributes.getValue("class");
        try {
            this.Uq = (ei_2)dh_2.a(string2, ei_2.class, this.Pb);
            this.Uq.a(this.Pb);
            qq_02.C(this.Uq);
        }
        catch (Exception exception) {
            this.kc = true;
            this.e("Could not create layout of type " + string2 + "].", exception);
        }
    }

    public void a(qq_0 qq_02, String string) {
        Object object;
        if (this.kc) {
            return;
        }
        if (this.Uq instanceof mt_2) {
            this.Uq.start();
        }
        if ((object = qq_02.wa()) != this.Uq) {
            this.ef("The object on the top the of the stack is not the layout pushed earlier.");
        } else {
            qq_02.wb();
            try {
                adr_0 adr_02 = (adr_0)qq_02.wa();
                adr_02.a(this.Uq);
            }
            catch (Exception exception) {
                this.e("Could not set the layout for containing appender.", exception);
            }
        }
    }

    public void a(qq_0 qq_02) {
    }
}

