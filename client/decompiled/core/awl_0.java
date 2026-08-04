/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from awl
 */
public class awl_0
extends te_1 {
    private kn_1 aeH;
    private kn_1 czv;
    private Object It;
    private Object czw;
    private Object dE;

    public void d(String string, aji_1 aji_12) {
        this.c(string, aji_12);
    }

    public void a(kn_1 kn_12, kn_1 kn_13, Object object) {
        qa_1 qa_12;
        this.dE = object;
        this.aeH = kn_12;
        this.czv = kn_13;
        if (this.aeH != null && (qa_12 = this.aeH.getRenderableParent()) != null) {
            this.It = qa_12.getItemValue();
        }
        if (this.czv != null && (qa_12 = this.czv.getRenderableParent()) != null) {
            this.czw = qa_12.getItemValue();
        }
    }

    protected void a(String[] stringArray, List list, List list2) {
        list.add(kn_1.class);
        list.add(Object.class);
        list.add(kn_1.class);
        list.add(Object.class);
        list.add(Object.class);
        list2.add(this.aeH);
        list2.add(this.It);
        list2.add(this.czv);
        list2.add(this.czw);
        list2.add(this.dE);
        super.a(stringArray, list, list2);
    }

    public void a(awl_0 awl_02) {
        awl_02.d(this.nq, this.blb);
    }

    public awl_0 aJx() {
        awl_0 awl_02 = new awl_0();
        this.a(awl_02);
        return awl_02;
    }

    public Object b(kn_1 kn_12, kn_1 kn_13, Object object) {
        qa_1 qa_12;
        this.dE = object;
        this.aeH = kn_12;
        this.czv = kn_13;
        if (this.aeH != null && (qa_12 = this.aeH.getRenderableParent()) != null) {
            this.It = qa_12.getItemValue();
        }
        if (this.czv != null && (qa_12 = this.czv.getRenderableParent()) != null) {
            this.czw = qa_12.getItemValue();
        }
        return super.agg();
    }
}

