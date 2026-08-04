/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from ss
 */
class ss_1
implements apx {
    final /* synthetic */ StringBuilder aje;
    final /* synthetic */ add_1 ajf;

    ss_1(add_1 add_12, StringBuilder stringBuilder) {
        this.ajf = add_12;
        this.aje = stringBuilder;
    }

    public boolean execute(String string) {
        this.aje.append("\t").append(string).append('\n');
        return true;
    }
}

