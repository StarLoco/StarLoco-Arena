/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from aNn
 */
class ann_1
implements nm_1 {
    final /* synthetic */ StringBuffer dZk;
    final /* synthetic */ ano_0 bPX;

    ann_1(ano_0 ano_02, StringBuffer stringBuffer) {
        this.bPX = ano_02;
        this.dZk = stringBuffer;
    }

    public boolean i(Object object, Object object2) {
        this.dZk.append(object);
        this.dZk.append("=");
        this.dZk.append(object2);
        this.dZk.append(", ");
        return true;
    }
}

