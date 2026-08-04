/*
 * Decompiled with CFR 0.152.
 */
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.NamingException;

/*
 * Renamed from aKs
 */
public class aks_0
implements ff_1 {
    private final Map dTw = Collections.synchronizedMap(new HashMap());
    private final ahu_0 dTx;
    private static final ThreadLocal dTy = new ThreadLocal();

    public aks_0(ahu_0 ahu_02) {
        this.dTx = ahu_02;
    }

    public ahu_0 OO() {
        return this.dTx;
    }

    public ahu_0 dS(String string) {
        return (ahu_0)this.dTw.remove(string);
    }

    public ahu_0 ON() {
        String string = null;
        Context context = null;
        ahu_0 ahu_02 = (ahu_0)dTy.get();
        if (ahu_02 != null) {
            return ahu_02;
        }
        try {
            context = agb_2.avT();
            string = agb_2.a(context, auz_0.cWw);
        }
        catch (NamingException namingException) {
            // empty catch block
        }
        if (string == null) {
            return this.dTx;
        }
        ahu_0 ahu_03 = (ahu_0)this.dTw.get(string);
        if (ahu_03 == null) {
            ahu_03 = new ahu_0();
            ahu_03.setName(string);
            this.dTw.put(string, ahu_03);
            URL uRL = this.a(context, ahu_03);
            if (uRL != null) {
                this.a(ahu_03, uRL);
            } else {
                try {
                    new aha_2(ahu_03).aUd();
                }
                catch (azG azG2) {
                    // empty catch block
                }
            }
            ape.b(ahu_03);
        }
        return ahu_03;
    }

    private String lB(String string) {
        return "logback-" + string + ".xml";
    }

    private URL a(Context context, ahu_0 ahu_02) {
        Ju ju = ahu_02.ea();
        String string = agb_2.a(context, auz_0.cWv);
        if (string != null) {
            ju.c(new jP("Searching for [" + string + "]", this));
            URL uRL = this.a(ju, string);
            if (uRL == null) {
                String string2 = "The jndi resource [" + string + "] for context [" + ahu_02.getName() + "] does not lead to a valid file";
                ju.c(new apQ(string2, this));
            }
            return uRL;
        }
        String string3 = this.lB(ahu_02.getName());
        return this.a(ju, string3);
    }

    private URL a(Ju ju, String string) {
        ju.c(new jP("Searching for [" + string + "]", this));
        URL uRL = agw_0.c(string, agw_0.aTa());
        if (uRL != null) {
            return uRL;
        }
        return agw_0.ln(string);
    }

    private void a(ahu_0 ahu_02, URL uRL) {
        try {
            aip_1 aip_12 = new aip_1();
            ahu_02.reset();
            aip_12.a(ahu_02);
            aip_12.b(uRL);
        }
        catch (azG azG2) {
            // empty catch block
        }
        ape.b(ahu_02);
    }

    public List OP() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.dTw.keySet());
        return arrayList;
    }

    public ahu_0 dR(String string) {
        return (ahu_0)this.dTw.get(string);
    }

    public int getCount() {
        return this.dTw.size();
    }

    public void g(ahu_0 ahu_02) {
        dTy.set(ahu_02);
    }

    public void aVz() {
        dTy.remove();
    }
}

