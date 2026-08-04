/*
 * Decompiled with CFR 0.152.
 */
public enum alj implements rk_0
{
    cEY(1, "Lancer un d\u00e9fi"),
    cEZ(2, "Donne un exploit");

    private short fL;
    private String fM;

    /*
     * WARNING - void declaration
     */
    private alj() {
        void var4_2;
        void var3_1;
        this.fL = var3_1;
        this.fM = var4_2;
    }

    public short tI() {
        return this.fL;
    }

    public String cC() {
        return String.valueOf(this.fL);
    }

    public String cD() {
        return this.fM;
    }

    public String cE() {
        return this.toString();
    }
}

