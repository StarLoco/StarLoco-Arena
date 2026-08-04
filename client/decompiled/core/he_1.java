/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.log4j.Logger;

/*
 * Renamed from He
 */
public abstract class he_1
implements JG {
    protected static final Logger a = Logger.getLogger(he_1.class);
    private acl_0 uG;
    private HashMap bds;
    private HashMap bdt = new HashMap();
    private rG bdu;
    private ea_0 bdv;

    protected he_1() {
        this.bds = new HashMap();
    }

    public ea_0 Np() {
        return this.bdv;
    }

    public void b(ea_0 ea_02) {
        this.bdv = ea_02;
    }

    public void release() {
        if (this.uG != null) {
            try {
                this.uG.af(this);
            }
            catch (Exception exception) {
                a.error((Object)"impossible");
            }
        } else {
            a.error((Object)("Double release de " + this.getClass().toString()));
        }
        this.j();
    }

    public void b() {
        this.bdu = null;
        this.bdt.clear();
        this.bds.clear();
    }

    public void j() {
        this.pG();
        this.bdu = null;
    }

    public void a(acl_0 acl_02) {
        this.uG = acl_02;
    }

    public Collection Sy() {
        return this.bds.values();
    }

    public Collection Sz() {
        Collection collection = this.bds.values();
        ArrayList<ack_1> arrayList = new ArrayList<ack_1>();
        for (ack_1 ack_12 : collection) {
            if (!ack_12.aqT()) continue;
            arrayList.add(ack_12);
        }
        return arrayList;
    }

    public ack_1 o(ry ry2) {
        for (ack_1 ack_12 : this.Sz()) {
            if (!ry2.S(ack_12.gn(), ack_12.go())) continue;
            return ack_12;
        }
        return null;
    }

    public int SA() {
        return this.bdt.size();
    }

    public Collection SB() {
        return this.bdt.values();
    }

    public ack_1 bG(long l2) {
        return (ack_1)this.bds.get(l2);
    }

    public ack_1 bH(long l2) {
        return (ack_1)this.bdt.get(l2);
    }

    public void a(rG rG2) {
        this.bdu = rG2;
    }

    public void f(ack_1 ack_12) {
        if (ack_12 == null) {
            return;
        }
        if (!this.bdt.containsKey(ack_12.getId())) {
            assert (!this.bdt.containsValue(ack_12)) : "Trying to insert an effectArea already present, but with a different Id";
            ack_12.a(this.bdu);
            this.bds.put(ack_12.getId(), ack_12);
            this.bdt.put(ack_12.getId(), ack_12);
            ack_12.aqQ();
            if (this.bdu != null) {
                this.bdu.a(ack_12);
            }
        }
    }

    public void g(ack_1 ack_12) {
        if (ack_12 == null) {
            return;
        }
        if (this.bdt.containsKey(ack_12.getId())) {
            this.bdt.remove(ack_12.getId());
            ack_12.aqR();
            if (this.bdu != null) {
                this.bdu.b(ack_12);
            }
        }
    }

    public void h(ack_1 ack_12) {
        if (ack_12 == null) {
            return;
        }
        this.bds.remove(ack_12.getId());
    }

    public void e(kc_2 kc_22) {
        if (kc_22 == null) {
            return;
        }
        ArrayList<ack_1> arrayList = new ArrayList<ack_1>();
        for (ack_1 ack_12 : this.bdt.values()) {
            if (ack_12.Nq() != kc_22) continue;
            arrayList.add(ack_12);
        }
        for (ack_1 ack_12 : arrayList) {
            this.g(ack_12);
        }
    }

    public void f(kc_2 kc_22) {
        if (kc_22 == null) {
            return;
        }
        ArrayList<ack_1> arrayList = new ArrayList<ack_1>();
        for (ack_1 ack_12 : this.bdt.values()) {
            if (ack_12.Nq() != kc_22 || !ack_12.aqW()) continue;
            arrayList.add(ack_12);
        }
        for (ack_1 ack_12 : arrayList) {
            this.g(ack_12);
        }
    }

    public boolean i(ack_1 ack_12) {
        return this.bdt.containsKey(ack_12.getId());
    }

    public void pG() {
        for (ack_1 ack_12 : this.Sy()) {
            ack_12.release();
        }
        this.bdt.clear();
        this.bds.clear();
    }

    public void a(int n2, int n3, short s, int n4, int n5, short s2, kc_2 kc_22) {
        Iterable iterable2;
        ArrayList<Iterable> arrayList = new ArrayList<Iterable>();
        for (Iterable iterable2 : this.bdt.values()) {
            if (!((ack_1)iterable2).x(n2, n3, s)) continue;
            arrayList.add(iterable2);
        }
        ArrayList arrayList2 = new ArrayList();
        iterable2 = new ArrayList();
        ArrayList<ack_1> arrayList3 = new ArrayList<ack_1>();
        for (ack_1 ack_12 : this.bdt.values()) {
            if (ack_12.x(n4, n5, s2)) {
                if (!arrayList.contains(ack_12)) {
                    arrayList2.add(ack_12);
                    continue;
                }
                arrayList3.add(ack_12);
                continue;
            }
            if (!arrayList.contains(ack_12)) continue;
            ((ArrayList)iterable2).add(ack_12);
        }
        Iterator<Object> iterator = arrayList2.iterator();
        while (iterator.hasNext()) {
            ack_1 ack_12;
            ack_12 = (ack_1)iterator.next();
            ack_12.b(10001, (aOf)kc_22);
        }
        for (ack_1 ack_12 : arrayList3) {
            ack_12.b(10008, (aOf)kc_22);
        }
        for (ack_1 ack_12 : iterable2) {
            ack_12.b(10002, (aOf)kc_22);
        }
    }

    public boolean bI(long l2) {
        return this.bdt.containsKey(l2);
    }

    public boolean j(ack_1 ack_12) {
        return this.bdt.containsKey(ack_12.getId());
    }

    public byte[] SC() {
        Object object;
        Object object22;
        int n2 = 2;
        HashMap<ack_1, byte[]> hashMap = new HashMap<ack_1, byte[]>();
        for (Object object22 : this.bdt.values()) {
            object = ((ack_1)object22).aqL().array();
            hashMap.put((ack_1)object22, (byte[])object);
            n2 = (short)(n2 + (10 + ((byte[])object).length));
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(n2);
        byteBuffer.putShort((short)this.bdt.size());
        object22 = this.bdt.values().iterator();
        while (object22.hasNext()) {
            object = (ack_1)object22.next();
            byteBuffer.putLong(((ack_1)object).aqM());
            byte[] byArray = (byte[])hashMap.get(object);
            byteBuffer.putShort((short)byArray.length);
            byteBuffer.put(byArray);
        }
        return byteBuffer.array();
    }

    public void J(byte[] byArray) {
        this.pG();
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        short s = byteBuffer.getShort();
        for (short s2 = 0; s2 < s; s2 = (short)(s2 + 1)) {
            long l2 = byteBuffer.getLong();
            ack_1 ack_12 = this.bJ(l2);
            byte[] byArray2 = new byte[byteBuffer.getShort()];
            byteBuffer.get(byArray2);
            if (ack_12 == null) continue;
            ack_12.I(ByteBuffer.wrap(byArray2));
            this.f(ack_12);
        }
    }

    public abstract ack_1 bJ(long var1);
}

