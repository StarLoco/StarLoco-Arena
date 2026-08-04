/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;

/*
 * Renamed from afe
 */
public class afe_1
extends azc_0 {
    public static final String TAG = "AnimatedImage";
    private ArrayList cqk = new ArrayList();
    private long cql = 1000L;
    private int ajD;
    private Runnable cqm;
    public static final int cqn = "delay".hashCode();

    public void a(na_1 na_12) {
        if (na_12 instanceof ur_1) {
            this.a((ur_1)na_12);
            return;
        }
        super.a(na_12);
    }

    private void a(ur_1 ur_12) {
        if (!this.cqk.contains(ur_12)) {
            this.cqk.add(ur_12);
        }
        if (this.cqk.size() == 1) {
            this.setPixmap(ur_12);
        } else if (this.cqk.size() == 2) {
            this.auP();
        }
    }

    private void auP() {
        this.cqm = new xr_2(this);
        ip_2.Un().a(this.cqm, this.cql, -1);
    }

    public void setDelay(long l2) {
        this.cql = l2;
    }

    public String getTag() {
        return TAG;
    }

    public void j() {
        ip_2.Un().b(this.cqm);
        this.cqk.clear();
        super.j();
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 != cqn) {
            return super.setXMLAttribute(n2, string, if_12);
        }
        this.setDelay(Gr.getLong(string));
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        return super.setPropertyAttribute(n2, object);
    }

    static /* synthetic */ int a(afe_1 afe_12) {
        return afe_12.ajD;
    }

    static /* synthetic */ ArrayList b(afe_1 afe_12) {
        return afe_12.cqk;
    }

    static /* synthetic */ int a(afe_1 afe_12, int n2) {
        afe_12.ajD = n2;
        return afe_12.ajD;
    }
}

