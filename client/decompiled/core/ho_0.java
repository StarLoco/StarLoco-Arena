/*
 * Decompiled with CFR 0.152.
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * Renamed from ho
 */
public class ho_0
extends ug
implements aho_0 {
    public static final int vo = 0;
    public static final String vp = "title";
    public static final String vq = "message";
    public static final String vr = "date";
    public static final String vs = "sender";
    public static final String vt = "cards";
    public static final String vu = "receiver";
    public static final String vv = "receiverId";
    public static final String vw = "read";
    public static final String vx = "hasItems";
    public static final String[] ce = new String[]{"title", "message", "date", "sender", "cards", "receiver", "receiverId", "read", "hasItems"};

    public void aM(int n2) {
        super.aM(n2);
        azs_0.aLV().a((aho_0)this, vt);
    }

    public void aN(int n2) {
        super.aN(n2);
        azs_0.aLV().a((aho_0)this, vt);
    }

    public String[] getFields() {
        return ce;
    }

    public Object getFieldValue(String string) {
        if (string.equals(vp)) {
            if (this.dUA < 0L) {
                return aon_0.aYc().a(32, this.apz, new Object[0]);
            }
            return this.getTitle();
        }
        if (string.equals(vq)) {
            if (this.dUA < 0L) {
                return aon_0.aYc().a(33, this.apz, new Object[0]);
            }
            return this.getMessage();
        }
        if (string.equals(vr)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
            Date date = this.getDate();
            return date != null ? simpleDateFormat.format(date) : null;
        }
        if (string.equals(vt)) {
            no no2 = new no();
            jg_0 jg_02 = this.Ax();
            if (jg_02 != null) {
                jg_02.a(new alh_2(this, no2));
            }
            ArrayList arrayList = new ArrayList();
            no2.a(new alg_2(this, no2, arrayList));
            return arrayList.toArray();
        }
        if (string.equals(vs)) {
            if (this.dUA < 0L) {
                return aon_0.aYc().a(34, this.apz, new Object[0]);
            }
            return this.aWc();
        }
        if (string.equals(vu)) {
            return this.aWe();
        }
        if (string.equals(vv)) {
            return this.aWb();
        }
        if (string.equals(vw)) {
            return this.aWh();
        }
        if (string.equals(vx)) {
            jg_0 jg_03 = this.Ax();
            return jg_03 != null && !jg_03.isEmpty();
        }
        return null;
    }

    public void a(String string, Object object) {
        if (string.equals(vp)) {
            this.setTitle((String)object);
        }
        if (string.equals(vq)) {
            this.setMessage((String)object);
        }
        if (string.equals(vu)) {
            this.lE((String)object);
        }
    }

    public void c(String string, Object object) {
    }

    public void b(String string, Object object) {
    }

    public boolean l(String string) {
        return string.equals(vp) || string.equals(vq) || string.equals(vu);
    }
}

