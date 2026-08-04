/*
 * Decompiled with CFR 0.152.
 */
class Ma
implements aDN {
    final /* synthetic */ StringBuilder bsS;
    final /* synthetic */ acu_1 bsR;

    Ma(acu_1 acu_12, StringBuilder stringBuilder) {
        this.bsR = acu_12;
        this.bsS = stringBuilder;
    }

    public boolean execute(String string, int n2) {
        this.bsS.append(n2).append(" x ").append(string).append("\n");
        return true;
    }
}

