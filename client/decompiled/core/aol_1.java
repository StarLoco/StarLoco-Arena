/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from aoL
 */
public class aol_1 {
    private static aol_1 cLi = new aol_1();
    public static final byte cLj = 0;
    public static final byte cLk = 1;
    public static final byte cLl = 2;

    public ArrayList aJ(byte by) {
        ArrayList<aif_2> arrayList = new ArrayList<aif_2>();
        for (art_0 art_02 : art_0.values()) {
            if (art_0.a(art_02) != 2 && art_0.a(art_02) != by) continue;
            arrayList.add(new aif_2(art_0.b(art_02), art_0.a(art_02), art_0.c(art_02)));
        }
        return arrayList;
    }

    public static aol_1 aCS() {
        return cLi;
    }
}

