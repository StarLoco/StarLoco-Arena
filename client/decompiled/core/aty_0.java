/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aty
 */
public class aty_0
extends aat_0
implements aDa {
    private String cLo;

    public void jA(String string) {
        this.cLo = string;
    }

    public String dV(String string) {
        if (this.cLo == null) {
            throw new eq_2("Missing contains in containsstring");
        }
        if (string.indexOf(this.cLo) > -1) {
            return string;
        }
        return null;
    }
}

