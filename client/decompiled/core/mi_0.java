/*
 * Decompiled with CFR 0.152.
 */
/*
 * Renamed from Mi
 */
class mi_0
implements Gk {
    final /* synthetic */ hs_1 btk;

    mi_0(hs_1 hs_12) {
        this.btk = hs_12;
    }

    public void a(bx_2 bx_22, ag ag2) {
        hs_1.a.fatal((Object)("Error while initializing Renderer : " + ag2.toString()));
        if (hs_1.a(this.btk) != null) {
            hs_1.a(this.btk).b(bx_22, ag2.toString());
        }
        System.exit(0);
    }
}

