/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.util.List;
import org.apache.log4j.Logger;

public abstract class atn
extends te_1
implements ov_1 {
    private static Logger a = Logger.getLogger(atn.class);
    private ke cTN;

    public abstract qe_1 aV();

    public Object g(ke ke2) {
        this.cTN = ke2;
        na_1 na_12 = ke2.oE();
        if (na_12 != null) {
            super.setElementMap(na_12.getElementMap());
        }
        return super.agg();
    }

    protected void a(String[] stringArray, List list, List list2) {
        list.add(this.cTN.getClass());
        list2.add(this.cTN);
        super.a(stringArray, list, list2);
    }

    public boolean a(ke ke2) {
        Object object = this.g(ke2);
        this.cTN = null;
        if (object instanceof Boolean) {
            return (Boolean)object;
        }
        return false;
    }
}

