/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;
import org.xml.sax.Attributes;

public class vv
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
            case bKW: 
            case bKY: {
                return false;
            }
            case bKZ: 
            case bKX: {
                it_0 it_02 = new it_0(nj_12, rz_02, string);
                this.asS.push(it_02);
                return true;
            }
        }
        this.eg("PropertySetter.computeAggregationType returned " + (Object)((Object)rz_02));
        return false;
    }

    public void a(qq_0 qq_02, String string, Attributes attributes) {
        Object object;
        it_0 it_02 = (it_0)this.asS.peek();
        String string2 = attributes.getValue("class");
        if (dh_2.isEmpty(string2 = qq_02.subst(string2))) {
            object = it_02.aIi;
            string2 = ((nj_1)object).b(it_02.Vc(), it_02.Va());
        }
        if (dh_2.isEmpty(string2)) {
            it_02.kc = true;
            object = "No class name attribute in [" + string + "]";
            this.eg((String)object);
            return;
        }
        try {
            it_02.V(agw_0.a(string2, this.Pb).newInstance());
            if (it_02.Vb() instanceof aaa_1) {
                ((aaa_1)it_02.Vb()).a(this.Pb);
            }
            this.ee("Pushing component [" + string + "] on top of the object stack.");
            qq_02.C(it_02.Vb());
        }
        catch (Exception exception) {
            it_02.kc = true;
            String string3 = "Could not create component [" + string + "] of type [" + string2 + "]";
            this.e(string3, exception);
        }
    }

    public void a(qq_0 qq_02, String string) {
        Object object;
        it_0 it_02 = (it_0)this.asS.pop();
        if (it_02.kc) {
            return;
        }
        nj_1 nj_12 = new nj_1(it_02.Vb());
        nj_12.a(this.Pb);
        if (nj_12.bk("parent") == rz_0.bKX) {
            nj_12.f("parent", it_02.aIi.getObj());
        }
        if (it_02.Vb() instanceof mt_2) {
            ((mt_2)it_02.Vb()).start();
        }
        if ((object = qq_02.wa()) != it_02.Vb()) {
            this.eg("The object on the top the of the stack is not the component pushed earlier.");
        } else {
            qq_02.wb();
            switch (it_02.aIj) {
                case bKX: {
                    it_02.aIi.f(string, it_02.Vb());
                    break;
                }
                case bKZ: {
                    it_02.aIi.e(string, it_02.Vb());
                }
            }
        }
    }
}

