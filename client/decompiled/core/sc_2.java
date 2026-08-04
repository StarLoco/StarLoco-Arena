/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from sC
 */
class sc_2
implements apx {
    final /* synthetic */ ArrayList akM;
    final /* synthetic */ add_1 ajf;

    sc_2(add_1 add_12, ArrayList arrayList) {
        this.ajf = add_12;
        this.akM = arrayList;
    }

    public boolean h(na_1 na_12) {
        try {
            if (!na_12.isUnloading() && na_12.getElementMap().getId().equals("MRU")) {
                this.akM.add(na_12);
            }
        }
        catch (Exception exception) {
            add_1.Dm().error((Object)"Exception lev\u00e9e lors du parcours des \u00e9l\u00e9ments charg\u00e9s pour pouvoir fermer les MRU", (Throwable)exception);
        }
        return true;
    }
}

