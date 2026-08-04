/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

/*
 * Renamed from avt
 */
public final class avt_0
extends avg
implements Cloneable {
    private String className;
    private final Vector parameters = new Vector();
    private bk_2 sK;

    public void setClassName(String string) {
        this.className = string;
    }

    public String getClassName() {
        return this.className;
    }

    public void a(vj_0 vj_02) {
        this.parameters.addElement(vj_02);
    }

    public void e(bk_2 bk_22) {
        if (this.aId()) {
            throw this.aIh();
        }
        if (this.sK == null) {
            this.sK = bk_22;
        } else {
            this.sK.b(bk_22);
        }
    }

    public bk_2 jz() {
        if (this.aId()) {
            throw this.aIi();
        }
        if (this.sK == null) {
            this.sK = new bk_2(this.TP());
        }
        return this.sK.dB();
    }

    public bk_2 jC() {
        return this.sK;
    }

    public void d(awq_0 awq_02) {
        if (this.aId()) {
            throw this.aIh();
        }
        this.jz().a(awq_02);
    }

    public vj_0[] aIA() {
        Object[] objectArray = new vj_0[this.parameters.size()];
        this.parameters.copyInto(objectArray);
        return objectArray;
    }

    public void a(awq_0 awq_02) {
        if (!this.parameters.isEmpty() || this.className != null || this.sK != null) {
            throw this.aIh();
        }
        Object object = awq_02.P(this.TP());
        if (object instanceof avt_0) {
            avt_0 avt_02 = (avt_0)object;
            this.setClassName(avt_02.getClassName());
            this.e(avt_02.jC());
            vj_0[] vj_0Array = avt_02.aIA();
            if (vj_0Array != null) {
                for (int j = 0; j < vj_0Array.length; ++j) {
                    this.a(vj_0Array[j]);
                }
            }
        } else {
            String string = awq_02.aJC() + " doesn't refer to a FilterReader";
            throw new eq_2(string);
        }
        super.a(awq_02);
    }
}

