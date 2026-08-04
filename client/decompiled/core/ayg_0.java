/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collections;

/*
 * Renamed from ayg
 */
public class ayg_0
extends ua_1
implements aho_0 {
    public static final String dkJ = "receivedMails";
    public static final String dkK = "sentMails";
    public static final String[] ce = new String[]{"receivedMails", "sentMails"};
    public static ayg_0 dkL = new ayg_0();

    public static ayg_0 aKP() {
        return dkL;
    }

    public void C(ArrayList arrayList) {
        this.a(apN.aDK().Ln().getId(), arrayList);
    }

    public void aKQ() {
        this.cN(apN.aDK().Ln().getId());
    }

    public ArrayList aKR() {
        long l2 = apN.aDK().Ln().getId();
        ArrayList arrayList = this.cO(l2);
        for (aLb aLb2 : arrayList) {
            if ((!aLb2.aWj() || aLb2.aWb() != l2) && (!aLb2.aWi() || aLb2.aWa() != l2)) continue;
            arrayList.remove(aLb2);
        }
        return arrayList;
    }

    public ArrayList aKS() {
        ArrayList<ho_0> arrayList = new ArrayList<ho_0>();
        qa_2 qa_22 = this.cP(apN.aDK().Ln().getId());
        for (int j = 0; j < qa_22.size(); ++j) {
            if (((aLb)this.bPn.t(qa_22.hn(j))).aWj() || ((aLb)this.bPn.t(qa_22.hn(j))).aWb() != apN.aDK().Ln().getId()) continue;
            arrayList.add((ho_0)this.bPn.t(qa_22.hn(j)));
        }
        return arrayList;
    }

    public ArrayList aKT() {
        ArrayList<ho_0> arrayList = new ArrayList<ho_0>();
        qa_2 qa_22 = this.cP(apN.aDK().Ln().getId());
        for (int j = 0; j < qa_22.size(); ++j) {
            if (((aLb)this.bPn.t(qa_22.hn(j))).aWi() || ((aLb)this.bPn.t(qa_22.hn(j))).aWa() != apN.aDK().Ln().getId()) continue;
            arrayList.add((ho_0)this.bPn.t(qa_22.hn(j)));
        }
        return arrayList;
    }

    public void a(ho_0 ho_02) {
        if (ho_02.getTitle() != null && !ho_02.getTitle().equals("") && ho_02.getMessage() != null && !ho_02.getMessage().equals("")) {
            F f = new F();
            ho_02.pi(2);
            ho_02.c(0L);
            ho_02.setDate(null);
            ho_02.eG(apN.aDK().Ln().getId());
            ho_02.lD(apN.aDK().Ln().getName());
            ho_02.setTitle(avQ.jT(ho_02.getTitle()));
            ho_02.setMessage(avQ.jT(ho_02.getMessage()));
            f.a(ho_02);
            apN.aDK().vJ().b(f);
        }
    }

    protected void h(aLb aLb2) {
        super.h(aLb2);
        azs_0.aLV().g("mailbox.mail", (Object)null);
        azs_0.aLV().a((aho_0)this, ce);
    }

    protected void e(aLb aLb2) {
    }

    protected void g(aLb aLb2) {
        super.g(aLb2);
        azs_0.aLV().a((aho_0)this, ce);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(dkJ)) {
            ArrayList arrayList = this.aKS();
            Collections.sort(arrayList, new ael_0(this));
            return arrayList.toArray();
        }
        if (string.equals(dkK)) {
            ArrayList arrayList = this.aKT();
            Collections.sort(arrayList, new aem_0(this));
            return arrayList.toArray();
        }
        return null;
    }

    public void a(String string, Object object) {
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return false;
    }
}

