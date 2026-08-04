/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Renamed from ML
 */
public abstract class ml_0
implements Sz,
aho_0,
nz_0 {
    public static final String ca = "chat.dialogView";
    public static final String cc = "input";
    public static final String bxQ = "history";
    public static final String bxR = "filtersList";
    public static final String bxS = "channelsList";
    public static final String bxT = "currentChannel";
    public static final String bxU = "privateName";
    public static final String bxV = "currentChannelName";
    public static final String[] ce = new String[]{"history", "input", "filtersList", "channelsList", "currentChannel", "privateName", "currentChannelName"};
    private String bxW = "";
    private String bxX = "";
    private int bxY = 100;
    private final lb_0 bxZ = new lb_0();
    private int bya;
    private Xu byb = null;
    private String byc = "";

    public ml_0(int n2) {
        this.bya = n2;
    }

    public void Zk() {
        ArrayList arrayList = new ArrayList();
        Object object = this.bxZ.pK();
        while (((aiz_1)object).hasNext()) {
            ((ll_0)object).fK();
            Xu xu = (Xu)((ll_0)object).value();
            if (!xu.isOpen()) continue;
            this.a(arrayList, xu.akQ());
        }
        Collections.sort(arrayList);
        object = new StringBuilder();
        for (zc_0 zc_02 : arrayList) {
            ((StringBuilder)object).append(this.c(zc_02));
        }
        this.bxX = this.fm(((StringBuilder)object).toString());
        aor_1.aYh().a(this, bxQ);
    }

    public void a(ArrayList arrayList, ua ua2) {
        if (ua2 == null) {
            return;
        }
        if (ua2.zW() != null) {
            for (Object object : ua2.zW()) {
                arrayList.add(object);
            }
        }
        if (ua2.zZ() != null) {
            for (Object object : ua2.zZ().values()) {
                this.a(arrayList, (ua)object);
            }
        }
    }

    protected abstract String c(zc_0 var1);

    public Xu a(ua ua2, aee_1 aee_12) {
        return this.a(ua2, aee_12, null);
    }

    public Xu a(ua ua2, aee_1 aee_12, String string) {
        ua2.a(this);
        Xu xu = (Xu)this.bxZ.get(ua2.getId());
        if (xu != null) {
            if (xu.akS() != aee_12) {
                xu.a(aee_1.dBA);
            }
            if (string != null) {
                xu.setCommand(string);
            }
        } else {
            xu = new Xu(ua2, ua2.getName(), aee_12, string);
        }
        this.bxZ.c(ua2.getId(), xu);
        xu.cC(ua2.Ab());
        this.b(xu);
        return xu;
    }

    protected void gL(int n2) {
        Xu xu = (Xu)this.bxZ.get(n2);
        if (xu == null) {
            return;
        }
        ua ua2 = xu.akQ();
        ua2.b(this);
        for (ua ua3 : ua2.zZ().values()) {
            ua3.b(this);
        }
    }

    public ll_0 Zl() {
        return this.bxZ.pK();
    }

    public Object[] Zm() {
        return this.bxZ.getValues();
    }

    public List Zn() {
        ArrayList<Xu> arrayList = new ArrayList<Xu>();
        ll_0 ll_02 = this.bxZ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            Xu xu = (Xu)ll_02.value();
            if (xu.akS() == aee_1.dBz) continue;
            arrayList.add(xu);
        }
        return arrayList;
    }

    public List getFilters() {
        ArrayList<Xu> arrayList = new ArrayList<Xu>();
        ll_0 ll_02 = this.bxZ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            Xu xu = (Xu)ll_02.value();
            if (!xu.Ab()) continue;
            arrayList.add(xu);
        }
        return arrayList;
    }

    public Xu a(ua ua2) {
        return this.a(ua2, false);
    }

    public Xu a(ua ua2, boolean bl2) {
        ll_0 ll_02 = this.bxZ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            Xu xu = (Xu)ll_02.value();
            if (xu.akQ() != ua2 || xu.akS() == aee_1.dBz) continue;
            this.a(xu);
            return xu;
        }
        return null;
    }

    private void a(Xu xu) {
        if (xu != this.byb && (xu == null || xu.akS() != aee_1.dBz)) {
            this.byb = xu;
            aor_1.aYh().a(this.byb, Xu.ce);
            aor_1.aYh().a(this, bxT);
            aor_1.aYh().a(this, bxV);
        }
    }

    public Xu Zo() {
        return this.byb;
    }

    private void b(Xu xu) {
        aor_1.aYh().a(this, bxR);
        if (xu.akS() != aee_1.dBz) {
            aor_1.aYh().a(this, bxS);
        }
    }

    public void d(zc_0 zc_02) {
        this.b(bxQ, this.c(zc_02));
        aor_1.aYh().a(this, bxQ);
    }

    public void b(ua ua2, aee_1 aee_12) {
        if (!this.Zt()) {
            this.a(ua2, aee_12);
        }
    }

    public String[] getFields() {
        return ce;
    }

    public int Zp() {
        return this.bya;
    }

    public Object getFieldValue(String string) {
        if (string.equals(cc)) {
            return this.bxW;
        }
        if (string.equals(bxQ)) {
            return this.bxX;
        }
        if (string.equals(bxR)) {
            return this.getFilters();
        }
        if (string.equals(bxS)) {
            return this.Zn();
        }
        if (string.equals(bxU)) {
            return this.Zq();
        }
        if (string.equals(bxT)) {
            return this.byb;
        }
        if (string.equals(bxV)) {
            return this.Zq() != null ? this.Zq() : (this.byb != null ? this.byb.akR() : null);
        }
        return null;
    }

    public String Zq() {
        if (this.byc == null) {
            return null;
        }
        int n2 = this.byc.indexOf(32) + 1;
        if (n2 == 0 || n2 > this.byc.length() - 1) {
            return null;
        }
        return this.byc.substring(n2).replaceAll("\"", "");
    }

    public void a(String string, Object object) {
        if (string.equals(cc)) {
            this.bxW = (String)object;
        } else if (string.equals(bxQ)) {
            this.bxX = this.fm((String)object);
        }
    }

    public void c(String string, Object object) {
        if (string.equals(cc)) {
            this.bxW = this.bxW == null ? (String)object : (String)object + this.bxW;
        }
    }

    public void b(String string, Object object) {
        if (string.equals(bxQ)) {
            this.bxX = this.fm(this.bxX + (String)object);
        } else if (string.equals(cc)) {
            this.bxW = this.bxW == null ? (String)object : this.bxW + (String)object;
        }
    }

    private String fm(String string) {
        if (this.bxY == -1) {
            return string;
        }
        String string2 = string;
        String[] stringArray = string2.split("\n");
        int n2 = 0;
        if (stringArray.length > this.bxY) {
            for (int j = 0; j < stringArray.length - this.bxY; ++j) {
                n2 += stringArray[j].length() + 1;
            }
        }
        return string.substring(n2);
    }

    public boolean l(String string) {
        return string.equals(cc);
    }

    public void clean() {
        this.bxX = "";
        this.bxW = "";
        aor_1.aYh().a(this, bxQ, cc);
    }

    public void setPrompt(String string) {
    }

    public void err(String string) {
    }

    public void log(String string) {
    }

    public void trace(String string) {
    }

    public String Zr() {
        return this.byc;
    }

    public void fn(String string) {
        this.byc = string;
        aor_1.aYh().a(this, bxV);
        aor_1.aYh().a(this, bxU);
    }

    public void gM(int n2) {
        this.bya = n2;
    }

    public void Zs() {
        ll_0 ll_02 = this.bxZ.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            ua ua2 = ((Xu)ll_02.value()).akQ();
            ua2.b(this);
            for (ua ua3 : ua2.zZ().values()) {
                ua3.b(this);
            }
        }
        this.bxZ.clear();
    }

    public boolean Zt() {
        return false;
    }
}

