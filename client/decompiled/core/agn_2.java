/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from aGN
 */
public class agn_2
extends ka_0 {
    arN dJI;
    boolean kc = false;

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        this.kc = false;
        ahu_0 ahu_02 = (ahu_0)this.Pb;
        this.dJI = ahu_02.lw("root");
        String string2 = qq_02.subst(attributes.getValue("level"));
        if (!dh_2.isEmpty(string2)) {
            rl_2 rl_22 = rl_2.bH(string2);
            this.ee("Setting level of ROOT logger to " + rl_22);
            this.dJI.b(rl_22);
        }
        qq_02.C(this.dJI);
    }

    public void a(qq_0 qq_02, String string) {
        if (this.kc) {
            return;
        }
        Object object = qq_02.wa();
        if (object != this.dJI) {
            this.ef("The object on the top the of the stack is not the root logger");
            this.ef("It is: " + object);
        } else {
            qq_02.wb();
        }
    }

    public void a(qq_0 qq_02) {
    }
}

