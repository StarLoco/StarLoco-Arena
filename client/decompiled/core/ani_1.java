/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;
import org.xml.sax.Attributes;

/*
 * Renamed from aNI
 */
public class ani_1
extends acz_1 {
    Stack asS = new Stack();

    public boolean a(zf_0 zf_02, Attributes attributes, qq_0 qq_02) {
        String string = zf_02.Ga();
        if (qq_02.isEmpty()) {
            return false;
        }
        Object object = qq_02.wa();
        nj_1 nj_12 = new nj_1(object);
        nj_12.a(this.Pb);
        rz_0 rz_02 = nj_12.bk(string);
        switch (rz_02) {
            case bKV: 
            case bKX: 
            case bKZ: {
                return false;
            }
            case bKW: 
            case bKY: {
                AP aP = new AP(nj_12, rz_02, string);
                this.asS.push(aP);
                return true;
            }
        }
        this.eg("PropertySetter.canContainComponent returned " + (Object)((Object)rz_02));
        return false;
    }

    public void a(qq_0 qq_02, String string, Attributes attributes) {
    }

    public void b(qq_0 qq_02, String string) {
        String string2 = qq_02.subst(string);
        AP aP = (AP)this.asS.peek();
        switch (aP.aIj) {
            case bKW: {
                aP.aIi.setProperty(aP.aIk, string2);
                break;
            }
            case bKY: {
                aP.aIi.f(aP.aIk, string2);
            }
        }
    }

    public void a(qq_0 qq_02, String string) {
        this.asS.pop();
    }
}

