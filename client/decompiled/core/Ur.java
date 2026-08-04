/*
 * Decompiled with CFR 0.152.
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.Stack;

public class Ur
extends aNZ {
    public static final String TAG = "Form";
    private HashMap bPO = new HashMap();
    private ag_0 bPP = null;
    public static final int bPQ = "validate".hashCode();

    public void b(afl_0 afl_02) {
        this.a(afl_02.getName(), afl_02);
    }

    public void a(String string, afl_0 afl_02) {
        this.bPO.put(string, afl_02);
    }

    public String getTag() {
        return TAG;
    }

    public afl_0 getProperty(String string) {
        return (afl_0)this.bPO.get(string);
    }

    public Collection getProperties() {
        return this.bPO.values();
    }

    public Set getPropertyNames() {
        return this.bPO.keySet();
    }

    public void agN() {
        for (afl_0 afl_02 : this.bPO.values()) {
            afl_02.avs();
        }
    }

    public boolean isValid() {
        if (this.bPP != null) {
            Object object = this.bPP.agg();
            return object != null && object instanceof Boolean && (Boolean)object != false;
        }
        return true;
    }

    public void setValidate(ag_0 ag_02) {
        this.bPP = ag_02;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Form : ");
        for (afl_0 afl_02 : this.bPO.values()) {
            stringBuilder.append("\n\t").append(afl_02.toString());
        }
        return stringBuilder.toString();
    }

    public void j() {
        this.blb.azj().a(this);
        super.j();
    }

    public void a(air_1 air_12) {
        Ur ur = (Ur)air_12;
        super.a((air_1)ur);
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != bPQ) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setValidate((ag_0)if_12.c(ag_0.class, string));
        return true;
    }

    public void a(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
        super.a(k_02, na_12, stack, afq_12);
        String string = null;
        k_0 k_03 = k_02.f("id");
        if (k_03 != null) {
            string = k_03.getStringValue();
        } else {
            a.warn((Object)"Attention : l'id du formulaire est nulle.");
        }
        afq_12.a(((aji_1)stack.peek()).getId() + "." + string, this);
    }

    public void b(k_0 k_02, na_1 na_12, Stack stack, afq_1 afq_12) {
        super.b(k_02, na_12, stack, afq_12);
        afq_12.lj(((aji_1)stack.peek()).getId() + "." + k_02.f("id").getStringValue());
    }
}

