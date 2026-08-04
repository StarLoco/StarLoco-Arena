/*
 * Decompiled with CFR 0.152.
 */
import java.util.List;

/*
 * Renamed from hl
 */
public class hl_1
implements adh_1 {
    protected List vk;
    protected long w;
    protected long vl;

    public hl_1(List list, long l2) {
        this.vk = list;
        this.w = l2;
        this.vl = 0L;
        for (awf_0 awf_02 : this.vk) {
            this.vl += awf_02.getDuration();
            awf_02.ee(l2);
        }
    }

    public agv_0 a(long l2) {
        if (l2 <= this.w) {
            return this.u();
        }
        if (l2 >= this.w + this.vl) {
            return this.v();
        }
        for (int j = 0; j < this.vk.size(); ++j) {
            awf_0 awf_02 = (awf_0)this.vk.get(j);
            if (l2 < awf_02.t() || l2 >= awf_02.s()) continue;
            agv_0 agv_02 = awf_02.a(l2);
            agv_02.X(awf_02.u().id());
            return agv_02;
        }
        return this.v();
    }

    public agv_0 b(long l2) {
        long l3 = 75L;
        agv_0 agv_02 = this.a(l2);
        agv_0 agv_03 = new agv_0((int)agv_02.getX(), (int)agv_02.getY(), (int)agv_02.id());
        agv_0 agv_04 = new agv_0(agv_03);
        agv_0 agv_05 = agv_03;
        while (l2 < this.w + this.vl && agv_04.equals(agv_03)) {
            agv_05 = this.a(l2 + 75L);
            agv_04.d((int)agv_05.getX(), (int)agv_05.getY(), (int)agv_05.id());
            l2 += 75L;
        }
        return agv_05;
    }

    public agv_0 u() {
        if (this.vk != null && this.vk.size() > 0) {
            return ((awf_0)this.vk.get(0)).u();
        }
        return null;
    }

    public agv_0 v() {
        if (this.vk != null && this.vk.size() > 0) {
            return ((awf_0)this.vk.get(this.vk.size() - 1)).v();
        }
        return null;
    }

    public long s() {
        return this.w + this.vl;
    }

    public long t() {
        return this.w;
    }
}

