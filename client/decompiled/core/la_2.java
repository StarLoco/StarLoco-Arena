/*
 * Decompiled with CFR 0.152.
 */
import java.io.File;

/*
 * Renamed from la
 */
class la_2 {
    public final int Gc;
    public int size;
    public int Gd;
    public final File ll;
    final /* synthetic */ apl_1 Ge;

    public la_2(apl_1 apl_12, int n2, int n3, int n4, int n5) {
        this.Ge = apl_12;
        this.Gc = n3;
        this.size = n4;
        this.Gd = n5;
        apl_1.a(apl_12).setLength(0);
        String string = apl_1.a(apl_12).append(apl_1.b(apl_12)).append("data.").append(n2).append("_").append(this.Gc).append(".bdat").toString();
        File file = (File)apl_1.c(apl_12).get(string.hashCode());
        if (file != null) {
            this.ll = file;
        } else {
            this.ll = new File(string);
            apl_1.c(apl_12).c(string.hashCode(), this.ll);
        }
    }
}

