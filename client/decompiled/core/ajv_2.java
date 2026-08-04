/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/*
 * Renamed from ajv
 */
public class ajv_2
extends es_1 {
    protected final HashMap cAy;
    protected final ut_0 oi;
    protected zc_2 oj;
    protected final boolean cAz;
    protected final boolean ok;

    public ajv_2(short s, ut_0 ut_02, zc_2 zc_22, boolean bl2, boolean bl3, boolean bl4) {
        super(bl3, s);
        this.cAy = bl2 ? new LinkedHashMap() : new HashMap();
        this.l(s);
        this.oi = ut_02;
        this.oj = zc_22;
        this.cAz = bl2;
        this.ok = bl4;
    }

    public ut_0 hq() {
        return this.oi;
    }

    public byte[] cd() {
        int n2 = 10 * this.size();
        qg_0 qg_02 = new qg_0(n2);
        for (akU akU2 : this) {
            if (!akU2.ji()) continue;
            qg_02.t(akU2.cd());
            if (!this.ok) continue;
            qg_02.S(akU2.hG());
        }
        return qg_02.toArray();
    }

    public boolean d(byte[] byArray) {
        this.ho();
        if (byArray == null) {
            return true;
        }
        boolean bl2 = false;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        while (byteBuffer.hasRemaining()) {
            akU akU2 = this.oi.e(byteBuffer);
            if (akU2 != null) {
                if (this.ok) {
                    akU2.q(byteBuffer.getShort());
                }
                try {
                    if (this.a(akU2)) continue;
                    a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un StackInventory : impossible d'ajouter l'item " + akU2.jf()));
                    akU2.release();
                    bl2 = true;
                }
                catch (Exception exception) {
                    a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un StackInventory : impossible d'ajouter l'item " + akU2.jf()), (Throwable)exception);
                    akU2.release();
                    bl2 = true;
                }
                continue;
            }
            if (this.ok) {
                byteBuffer.getShort();
            }
            a.error((Object)"Erreur lors de la d\u00e9s\u00e9rialisation d'un StackInventory : impossible d'ajouter l'item");
            bl2 = true;
        }
        return !bl2;
    }

    public boolean b(ByteBuffer byteBuffer) {
        this.ho();
        boolean bl2 = false;
        while (byteBuffer.hasRemaining()) {
            akU akU2 = this.oi.e(byteBuffer);
            if (akU2 != null) {
                if (this.ok) {
                    akU2.q(byteBuffer.getShort());
                }
                try {
                    if (this.a(akU2)) continue;
                    a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un StackInventory : impossible d'ajouter l'item " + akU2.jf()));
                    akU2.release();
                    bl2 = true;
                }
                catch (Exception exception) {
                    a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un StackInventory : impossible d'ajouter l'item " + akU2.jf()), (Throwable)exception);
                    akU2.release();
                    bl2 = true;
                }
                continue;
            }
            if (this.ok) {
                byteBuffer.getShort();
            }
            a.error((Object)"Erreur lors de la d\u00e9s\u00e9rialisation d'un StackInventory : impossible d'ajouter l'item");
            bl2 = true;
        }
        return !bl2;
    }

    public boolean f(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() <= 0) {
            a.warn((Object)("Impossile d'ajouter un item avec un quantit\u00e9e de " + akU2.hG()));
            return false;
        }
        if (!this.bxB) {
            if (this.isFull()) {
                return false;
            }
            if (this.cAy.containsKey(akU2.je())) {
                return false;
            }
            if (this.oj != null && this.oj.b(this, akU2) != 0) {
                return false;
            }
            this.cAy.put(akU2.je(), akU2);
            return true;
        }
        int n2 = akU2.hG();
        int n3 = akU2.jf();
        for (akU akU3 : this.cAy.values()) {
            int n4;
            if (!akU2.e(akU3) || (n4 = akU3.jg() - akU3.hG()) <= 0) continue;
            short s = (short)(n2 < n4 ? n2 : n4);
            n2 = (short)(n2 - s);
            akU3.w(s);
            this.b(auA.c(this, akU3, s));
            if (n2 > 0) continue;
            break;
        }
        Object object = akU2;
        while (n2 > 0) {
            int n5 = n2 < akU2.jg() ? n2 : akU2.jg();
            object.q((short)n5);
            n2 = (short)(n2 - n5);
            if (this.oj != null && this.oj.b(this, (uh_1)object) != 0) {
                return false;
            }
            this.cAy.put(object.je(), object);
            if (n2 <= 0) continue;
            object = (akU)object.G(false);
        }
        return true;
    }

    public boolean c(akU akU2, short s) {
        akU akU3 = this.F(akU2.je());
        if (akU3 == null) {
            for (akU akU4 : this.ad(akU2.jf())) {
                if (akU4.hG() <= 1) continue;
                akU3 = akU4;
            }
        }
        if (akU3 == null) {
            return false;
        }
        if (akU3.hG() + s <= 0) {
            this.cAy.remove(akU3.je());
        } else {
            akU3.w(s);
        }
        return true;
    }

    public boolean a(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() <= 0) {
            a.warn((Object)("Impossile d'ajouter un item avec un quantit\u00e9e de " + akU2.hG()));
            return false;
        }
        if (!this.bxB) {
            if (this.isFull()) {
                throw new gg("Cannot add item : maximum size of inventory is reached (" + this.YM() + ")");
            }
            if (this.cAy.containsKey(akU2.je())) {
                throw new xR("Item with uniqueID " + akU2.je() + " is already present in the inventory", akU2, (uh_1)this.cAy.get(akU2.je()));
            }
            if (this.oj != null && this.oj.b(this, akU2) != 0) {
                return false;
            }
            this.cAy.put(akU2.je(), akU2);
            this.b(auA.c(this, akU2));
            return true;
        }
        int n2 = akU2.hG();
        for (akU akU3 : this.cAy.values()) {
            int n3;
            if (!akU2.e(akU3) || (n3 = akU3.jg() - akU3.hG()) <= 0) continue;
            short s = (short)(n2 < n3 ? n2 : n3);
            n2 = (short)(n2 - s);
            akU3.w(s);
            this.b(auA.c(this, akU3, s));
            if (n2 > 0) continue;
            break;
        }
        Object object = akU2;
        while (n2 > 0) {
            int n4 = n2 < akU2.jg() ? n2 : akU2.jg();
            object.q((short)n4);
            n2 = (short)(n2 - n4);
            if (this.oj != null && this.oj.b(this, (uh_1)object) != 0) {
                return false;
            }
            this.cAy.put(object.je(), object);
            this.b(auA.c(this, (uh_1)object));
            if (n2 <= 0) continue;
            object = (akU)object.G(false);
        }
        return true;
    }

    public boolean d(long l2, short s) {
        akU akU2 = this.F(l2);
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() + s <= 0) {
            return this.c(akU2);
        }
        akU2.w(s);
        this.b(auA.c(this, akU2, s));
        return true;
    }

    public short A(long l2) {
        akU akU2 = this.F(l2);
        if (akU2 == null) {
            return 0;
        }
        return akU2.hG();
    }

    public boolean a(akU akU2, akU akU3) {
        if (akU2 == null || akU3 == null) {
            return false;
        }
        if (akU2 == akU3) {
            return true;
        }
        if (akU2.je() != akU3.je() && this.cAy.containsKey(akU3.je())) {
            throw new xR("Item with uniqueID " + akU3.je() + " is already present in the inventory");
        }
        if (this.oj != null && this.oj.a((mi_2)this, (uh_1)akU2, akU3) != 0) {
            return false;
        }
        if (!this.b(akU2)) {
            return false;
        }
        try {
            return this.a(akU3);
        }
        catch (gg gg2) {
            a.error((Object)bl_0.b(gg2));
            return false;
        }
    }

    public boolean b(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (this.oj != null && this.oj.a(this, akU2) != 0) {
            return false;
        }
        if (this.cAy.remove(akU2.je()) == null) {
            return false;
        }
        this.b(auA.d(this, akU2));
        return true;
    }

    public boolean c(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (this.oj != null && this.oj.a(this, akU2) != 0) {
            return false;
        }
        if (this.cAy.remove(akU2.je()) == null) {
            return false;
        }
        this.b(auA.d(this, akU2));
        akU2.release();
        return true;
    }

    public akU C(long l2) {
        akU akU2 = (akU)this.cAy.get(l2);
        if (akU2 == null) {
            return null;
        }
        if (this.oj != null && this.oj.a(this, akU2) != 0) {
            return null;
        }
        this.cAy.remove(l2);
        this.b(auA.d(this, akU2));
        return akU2;
    }

    public boolean D(long l2) {
        akU akU2 = (akU)this.cAy.get(l2);
        if (akU2 == null) {
            return false;
        }
        if (this.oj != null && this.oj.a(this, akU2) != 0) {
            return false;
        }
        this.cAy.remove(l2);
        this.b(auA.d(this, akU2));
        akU2.release();
        return true;
    }

    public void aa(int n2) {
    }

    public int l(int n2, int n3) {
        return 0;
    }

    public Iterator iterator() {
        return this.cAy.values().iterator();
    }

    public boolean e(akU akU2) {
        return akU2 != null && this.cAy.containsKey(akU2.je());
    }

    public boolean E(long l2) {
        return this.cAy.containsKey(l2);
    }

    public boolean ab(int n2) {
        for (akU akU2 : this.cAy.values()) {
            if (akU2.jf() != n2) continue;
            return true;
        }
        return false;
    }

    public akU F(long l2) {
        return (akU)this.cAy.get(l2);
    }

    public akU ac(int n2) {
        for (akU akU2 : this.cAy.values()) {
            if (akU2.jf() != n2) continue;
            return akU2;
        }
        return null;
    }

    public ArrayList ad(int n2) {
        ArrayList<akU> arrayList = new ArrayList<akU>();
        for (akU akU2 : this.cAy.values()) {
            if (akU2.jf() != n2) continue;
            arrayList.add(akU2);
        }
        return arrayList;
    }

    public int size() {
        return this.cAy.size();
    }

    public int Bp() {
        int n2 = 0;
        for (akU akU2 : this.cAy.values()) {
            if (!akU2.ji()) continue;
            n2 += akU2.Bp();
        }
        return n2;
    }

    public int hn() {
        int n2 = this.size();
        this.cAy.clear();
        if (n2 > 0) {
            this.b(l_0.a(this));
        }
        return n2;
    }

    public int ho() {
        int n2 = this.size();
        for (akU akU2 : this.cAy.values()) {
            akU2.release();
        }
        this.cAy.clear();
        if (n2 > 0) {
            this.b(l_0.a(this));
        }
        return n2;
    }

    public zc_2 hp() {
        return this.oj;
    }

    public void a(zc_2 zc_22) {
        this.oj = zc_22;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("StackInventory = ");
        for (akU akU2 : this.cAy.values()) {
            stringBuffer.append(akU2.je()).append(" ");
        }
        return stringBuffer.toString();
    }
}

