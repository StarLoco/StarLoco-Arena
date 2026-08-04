/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * Renamed from en
 */
public class en_1
extends es_1 {
    protected akU[] og = null;
    protected final kl_1 oh = new kl_1();
    protected final ut_0 oi;
    protected zc_2 oj;
    protected final boolean ok;

    public en_1(ut_0 ut_02, zc_2 zc_22, short s, boolean bl2, boolean bl3) {
        super(bl2, s);
        if (!bl2 && bl3) {
            throw new IllegalArgumentException("Impossible de cr\u00e9er un inventaire \u00e0 la fois non stackable (donc sans quantit\u00e9) et pour lequel on veut s\u00e9rialiser une quantit\u00e9.");
        }
        this.l(s);
        this.oi = ut_02;
        this.oj = zc_22;
        this.ok = bl3;
    }

    public byte[] cd() {
        int n2 = 10 * this.size();
        qg_0 qg_02 = new qg_0(n2);
        aye aye2 = this.oh.WB();
        while (aye2.hasNext()) {
            aye2.fK();
            short s = aye2.qD();
            akU akU2 = this.og[s];
            if (!akU2.ji()) continue;
            qg_02.S(s);
            qg_02.t(akU2.cd());
            if (!this.ok) continue;
            qg_02.S(akU2.hG());
        }
        return qg_02.toArray();
    }

    public boolean d(byte[] byArray) {
        this.ho();
        if (byArray == null || byArray.length == 0) {
            return true;
        }
        boolean bl2 = false;
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        while (byteBuffer.hasRemaining()) {
            short s = byteBuffer.getShort();
            akU akU2 = this.oi.e(byteBuffer);
            if (akU2 != null) {
                if (this.ok) {
                    akU2.q(byteBuffer.getShort());
                }
                try {
                    if (this.a(akU2, s)) continue;
                    a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un ArrayInventory : impossible d'ajouter l'item " + akU2.jf()));
                    akU2.release();
                    bl2 = true;
                }
                catch (Exception exception) {
                    a.error((Object)("Erreur lors de la d\u00e9s\u00e9rialisation d'un ArrayInventory : impossible d'ajouter l'item " + akU2.jf()), (Throwable)exception);
                    akU2.release();
                    bl2 = true;
                }
                continue;
            }
            if (this.ok) {
                byteBuffer.getShort();
            }
            bl2 = true;
            a.error((Object)"Erreur lors de la d\u00e9s\u00e9rialisation d'un ArrayInventory : impossible d'ajouter l'item");
        }
        return !bl2;
    }

    public boolean l(short s) {
        if (s < this.YM()) {
            a.error((Object)"Can't decrease the size of an ArrayInventory");
            return false;
        }
        if (this.og != null && s == this.YM()) {
            return true;
        }
        super.l(s);
        if (this.og == null) {
            this.og = new akU[s];
        } else {
            akU[] akUArray = new akU[s];
            System.arraycopy(this.og, 0, akUArray, 0, this.og.length);
            this.og = akUArray;
        }
        this.oh.ensureCapacity(s);
        return true;
    }

    public boolean a(akU akU2) {
        int n2;
        short s;
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() <= 0) {
            a.error((Object)("On essaye d'ajouter un item avec une quantit\u00e9 de " + akU2.hG()), (Throwable)new Exception());
            return false;
        }
        if (this.oh.v(akU2.je())) {
            throw new xR("Item with uniqueID " + akU2.je() + " is already present in the inventory");
        }
        if (this.oj != null && this.oj.b(this, akU2) != 0) {
            return false;
        }
        short s2 = -1;
        short s3 = -1;
        for (s = 0; s < this.og.length; s = (short)((short)(s + 1))) {
            if (this.og[s] != null && akU2.e(this.og[s])) {
                int n3 = n2 = akU2.jg() > 1 ? 1 : 0;
                if (s2 != -1 && this.og[s2].hG() < this.og[s].hG()) {
                    n2 = 0;
                }
                if (n2 != 0) {
                    s2 = s;
                }
            }
            if (this.og[s] != null || s3 != -1) continue;
            s3 = s;
        }
        if (this.isFull() && s2 == -1) {
            throw new gg("Cannot add item : maximum size of inventory is reached (" + this.YM() + ")");
        }
        if (s2 >= 0 && akU2.hG() + this.og[s2].hG() > akU2.jg() && s3 == -1) {
            throw new gg("Cannot add item : There is a possible stack, but his maxSize will be reached, and there is no free place for the rest.  MaxSize : " + this.YM());
        }
        s = 0;
        if (s2 != -1) {
            short s4;
            n2 = this.og[s2].jg() - this.og[s2].hG();
            s = (short)(n2 < (s4 = akU2.hG()) ? n2 : (int)s4);
            this.og[s2].w(s);
            this.b(auA.a((mi_2)this, this.og[s2], s2, s));
        }
        if (akU2.hG() - s > 0) {
            akU2.w(-s);
            if (akU2.hG() > 0 && s3 != -1) {
                this.og[s3] = akU2;
                this.oh.h(akU2.je(), s3);
                this.b(auA.b(this, akU2, s3));
            }
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

    public boolean a(akU akU2, short s) {
        if (akU2 == null) {
            a.info((Object)"Impossible d'ajouter un item null");
            return false;
        }
        if (s < 0 || s >= this.bxz) {
            a.info((Object)("Impossible d'ajouter un item : position en dehors des limites : " + s));
            return false;
        }
        if (this.oj != null && this.oj.a((mi_2)this, (uh_1)akU2, s) != 0) {
            a.info((Object)"Position refus\u00e9e par le checker");
            return false;
        }
        if (this.isFull()) {
            throw new gg("Cannot add item : maximum size of inventory is reached (" + this.YM() + ")");
        }
        if (this.oh.v(akU2.je())) {
            throw new xR("Item with uniqueID " + akU2.je() + " is already present in the inventory");
        }
        if (this.og[s] != null) {
            throw new br_1("Cannot add item " + akU2 + " at position " + s + " item " + this.og[s] + "already present");
        }
        this.og[s] = akU2;
        this.oh.h(akU2.je(), s);
        this.b(auA.b(this, akU2, s));
        return true;
    }

    public boolean a(akU akU2, akU akU3) {
        if (akU2 == null || akU3 == null) {
            return false;
        }
        if (akU2 == akU3) {
            return true;
        }
        if (akU2.je() != akU3.je() && this.oh.v(akU3.je())) {
            throw new xR("Item with uniqueID " + akU3.je() + " is already present in the inventory");
        }
        if (!this.e(akU2)) {
            return false;
        }
        if (this.oj != null && this.oj.a((mi_2)this, (uh_1)akU2, akU3) != 0) {
            return false;
        }
        short s = this.oh.bV(akU2.je());
        this.og[s] = null;
        this.b(auA.f(this, akU2, s));
        this.oh.h(akU3.je(), s);
        this.og[s] = akU3;
        this.b(auA.b(this, akU3, s));
        return true;
    }

    public boolean b(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (this.oh.m(akU2.je())) {
            short s = this.oh.bU(akU2.je());
            if (this.og[s] != akU2) {
                a.error((Object)("Probl\u00e8me de logique : table d'index et tableau incoh\u00e9rents. Item attendu \u00e0 la position " + s + " : " + akU2 + " item trouv\u00e9 : " + this.og[s]));
                return false;
            }
            if (this.oj != null && this.oj.a(this, akU2) != 0) {
                return false;
            }
            this.og[s] = null;
            this.oh.bV(akU2.je());
            this.b(auA.f(this, akU2, s));
            return true;
        }
        return false;
    }

    public boolean c(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (this.oh.m(akU2.je())) {
            short s = this.oh.bU(akU2.je());
            if (this.og[s] != akU2) {
                a.error((Object)("Probl\u00e8me de logique : table d'index et tableau incoh\u00e9rents. Item attendu \u00e0 la position " + s + " : " + akU2 + " item trouv\u00e9 : " + this.og[s]));
                return false;
            }
            if (this.oj != null && this.oj.a(this, akU2) != 0) {
                return false;
            }
            this.og[s] = null;
            this.oh.bV(akU2.je());
            this.b(auA.f(this, akU2, s));
            akU2.release();
            return true;
        }
        return false;
    }

    public akU m(short s) {
        akU akU2 = this.og[s];
        if (akU2 == null) {
            return null;
        }
        if (this.oj != null && this.oj.a(this, akU2) != 0) {
            return null;
        }
        this.og[s] = null;
        this.oh.bV(akU2.je());
        this.b(auA.f(this, akU2, s));
        return akU2;
    }

    public boolean n(short s) {
        if (s < 0 || s >= this.bxz) {
            return false;
        }
        akU akU2 = this.og[s];
        if (akU2 == null) {
            return false;
        }
        if (this.oj != null && this.oj.a(this, akU2) != 0) {
            return false;
        }
        this.og[s] = null;
        this.oh.bV(akU2.je());
        this.b(auA.f(this, akU2, s));
        akU2.release();
        return true;
    }

    public short B(long l2) {
        if (!this.oh.v(l2)) {
            return -1;
        }
        return this.oh.bU(l2);
    }

    public short d(akU akU2) {
        if (akU2 == null) {
            return -1;
        }
        return this.B(akU2.je());
    }

    public akU C(long l2) {
        if (this.oh.m(l2)) {
            short s = this.oh.bV(l2);
            if (this.og[s] == null || this.og[s].je() != l2) {
                a.error((Object)("Probl\u00e8me de logique : table d'index et tableau incoh\u00e9rents. Item attendu \u00e0 la position " + s + " : id " + l2 + ". item trouv\u00e9 : " + this.og[s] + (this.og[s] == null ? "" : "(id : " + this.og[s].je() + ")")));
                this.og[s] = null;
                return null;
            }
            akU akU2 = this.og[s];
            if (this.oj != null && this.oj.a(this, akU2) != 0) {
                return null;
            }
            this.og[s] = null;
            this.b(auA.f(this, akU2, s));
            return akU2;
        }
        return null;
    }

    public void aa(int n2) {
        for (akU akU2 : this.og) {
            if (akU2 == null || akU2.jf() != n2) continue;
            this.c(akU2);
        }
    }

    public int l(int n2, int n3) {
        return 0;
    }

    public boolean D(long l2) {
        if (this.oh.m(l2)) {
            short s = this.oh.bV(l2);
            if (this.og[s] == null || this.og[s].je() != l2) {
                a.error((Object)("Probl\u00e8me de logique : table d'index et tableau incoh\u00e9rents. Item attendu \u00e0 la position " + s + " : id " + l2 + ". item trouv\u00e9 : " + this.og[s] + (this.og[s] == null ? "" : "(id : " + this.og[s].je() + ")")));
                this.og[s] = null;
                return false;
            }
            if (this.oj != null && this.oj.a(this, this.og[s]) != 0) {
                return false;
            }
            this.b(auA.f(this, this.og[s], s));
            this.og[s].release();
            this.og[s] = null;
            return true;
        }
        return false;
    }

    public Iterator iterator() {
        return new dl_2(this.og, false);
    }

    public akU[] hl() {
        return this.og;
    }

    public boolean e(akU akU2) {
        return akU2 != null && this.oh.v(akU2.je());
    }

    public boolean E(long l2) {
        return this.oh.v(l2);
    }

    public boolean ab(int n2) {
        for (akU akU2 : this.og) {
            if (akU2 == null || akU2.jf() != n2) continue;
            return true;
        }
        return false;
    }

    public boolean o(short s) {
        return s >= 0 && s < this.bxz && this.og[s] == null;
    }

    public akU p(short s) {
        if (s < 0 || s >= this.bxz) {
            return null;
        }
        return this.og[s];
    }

    public akU F(long l2) {
        if (!this.oh.m(l2)) {
            return null;
        }
        short s = this.oh.bU(l2);
        return this.og[s];
    }

    public akU ac(int n2) {
        for (akU akU2 : this.og) {
            if (akU2 == null || akU2.jf() != n2) continue;
            return akU2;
        }
        return null;
    }

    public short hm() {
        short s = 0;
        for (akU akU2 : this.og) {
            if (akU2 == null) {
                return s;
            }
            s = (short)(s + 1);
        }
        return -1;
    }

    public ArrayList ad(int n2) {
        ArrayList<akU> arrayList = new ArrayList<akU>();
        for (akU akU2 : this.og) {
            if (akU2 == null || akU2.jf() != n2) continue;
            arrayList.add(akU2);
        }
        return arrayList;
    }

    public int size() {
        return this.oh.size();
    }

    public int hn() {
        int n2 = this.size();
        for (int j = this.og.length - 1; j >= 0; --j) {
            this.og[j] = null;
        }
        this.oh.clear();
        if (n2 > 0) {
            this.b(l_0.a(this));
        }
        return n2;
    }

    public int ho() {
        int n2 = this.size();
        for (int j = this.og.length - 1; j >= 0; --j) {
            if (this.og[j] != null) {
                this.og[j].release();
            }
            this.og[j] = null;
        }
        this.oh.clear();
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

    public boolean f(akU akU2) {
        int n2;
        int n3;
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() <= 0) {
            a.error((Object)("On essaye de simuler l'ajout d'un item avec une quantit\u00e9 de " + akU2.hG()), (Throwable)new Exception());
            return false;
        }
        if (this.oh.v(akU2.je())) {
            throw new xR("Simulation : Item with uniqueID " + akU2.je() + " is already present in the inventory");
        }
        if (this.oj != null && this.oj.b(this, akU2) != 0) {
            return false;
        }
        int n4 = -1;
        int n5 = -1;
        for (n3 = 0; n3 < this.og.length; n3 = (int)((short)(n3 + 1))) {
            if (this.og[n3] != null && this.bxB) {
                a.trace((Object)("En position " + n3 + " le sac contient Ref Id : " + this.og[n3].jf()));
                if (!akU2.e(this.og[n3])) continue;
                a.trace((Object)("On a trouv\u00e9 : " + this.og[n3].jf() + " \u00e9gal a " + akU2.jf()));
                n2 = 1;
                if (n4 != -1 && this.og[n4].hG() < this.og[n3].hG()) {
                    n2 = 0;
                }
                if (n2 == 0) continue;
                n4 = n3;
                continue;
            }
            if (n5 != -1) continue;
            n5 = n3;
        }
        if (this.isFull() && n4 == -1) {
            return false;
        }
        if (n4 >= 0 && akU2.hG() + this.og[n4].hG() > akU2.jg() && n5 == -1) {
            return false;
        }
        n3 = 0;
        if (n4 != -1) {
            short s;
            n2 = this.og[n4].jg() - this.og[n4].hG();
            n3 = (short)(n2 < (s = akU2.hG()) ? n2 : (int)s);
            this.og[n4].w((short)n3);
        }
        if (akU2.hG() - n3 <= 0) {
            return true;
        }
        akU2.w((short)(-n3));
        if (akU2.hG() > 0 && n5 != -1) {
            this.og[n5] = akU2;
            this.oh.h(akU2.je(), (short)n5);
            return true;
        }
        return false;
    }

    public boolean b(akU akU2, short s) {
        if (akU2 == null) {
            a.info((Object)"Impossible d'ajouter un item null");
            return false;
        }
        if (s < 0 || s >= this.bxz) {
            a.info((Object)("Impossible d'ajouter un item : position en dehors des limites : " + s));
            return false;
        }
        if (this.oj != null && this.oj.a((mi_2)this, (uh_1)akU2, s) != 0) {
            a.info((Object)"Position refus\u00e9e par le checker");
            return false;
        }
        if (this.isFull()) {
            throw new gg("Cannot add item : maximum size of inventory is reached (" + this.YM() + ")");
        }
        if (this.oh.v(akU2.je())) {
            throw new xR("Item with uniqueID " + akU2.je() + " is already present in the inventory");
        }
        if (this.og[s] != null) {
            throw new br_1("Cannot add item " + akU2 + " at position " + s + " item " + this.og[s] + "already present");
        }
        this.og[s] = akU2;
        this.oh.h(akU2.je(), s);
        return true;
    }

    public boolean g(akU akU2) {
        int n2;
        short s;
        short s2 = -1;
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() <= 0) {
            a.error((Object)("On essaye de simuler l'ajout d'un item avec une quantit\u00e9 de " + akU2.hG()), (Throwable)new Exception());
            return false;
        }
        if (this.oh.v(akU2.je())) {
            throw new xR("Simulation : Item with uniqueID " + akU2.je() + " is already present in the inventory");
        }
        if (this.oj != null && this.oj.b(this, akU2) != 0) {
            return false;
        }
        short s3 = -1;
        short s4 = -1;
        for (s = 0; s < this.og.length; s = (short)((short)(s + 1))) {
            if (this.og[s] != null && akU2.e(this.og[s])) {
                n2 = 1;
                if (s3 != -1 && this.og[s3].hG() < this.og[s].hG()) {
                    n2 = 0;
                }
                if (n2 != 0) {
                    s3 = s;
                }
            }
            if (this.og[s] != null || s4 != -1) continue;
            s4 = s;
        }
        if (this.isFull() && s3 == -1) {
            return false;
        }
        if (s3 >= 0 && akU2.hG() + this.og[s3].hG() > akU2.jg() && s4 == -1) {
            return false;
        }
        s = 0;
        if (s3 != -1) {
            short s5;
            n2 = this.og[s3].jg() - this.og[s3].hG();
            s = (short)(n2 < (s5 = akU2.hG()) ? n2 : (int)s5);
            s2 = this.og[s3].hG();
            this.og[s3].w(s);
        }
        if (akU2.hG() - s <= 0) {
            if (s2 != -1) {
                this.og[s3].q(s2);
                return true;
            }
        } else {
            akU2.w(-s);
            if (akU2.hG() > 0 && s4 != -1) {
                this.og[s4] = akU2;
                this.oh.h(akU2.je(), s4);
                this.og[s4] = null;
                this.oh.bV(akU2.je());
                return true;
            }
        }
        return false;
    }

    public boolean h(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (this.isFull()) {
            a.info((Object)"Inventaire plein");
            return false;
        }
        if (akU2.hG() <= 0) {
            a.error((Object)("On essaye de simuler l'ajout d'un item avec une quantit\u00e9 de " + akU2.hG()), (Throwable)new Exception());
            return false;
        }
        if (this.oh.v(akU2.je())) {
            throw new xR("Simulation : Item with uniqueID " + akU2.je() + " is already present in the inventory");
        }
        if (this.oj != null && this.oj.b(this, akU2) != 0) {
            return false;
        }
        short s = -1;
        for (short s2 = 0; s2 < this.og.length && s == -1; s2 = (short)((short)(s2 + 1))) {
            if (this.og[s2] != null) continue;
            s = s2;
        }
        if (s == -1) {
            a.warn((Object)"L'inventaire n'a pas de places disponibles, on aurait du retourner false plus tot");
            return false;
        }
        this.og[s] = akU2;
        this.oh.h(akU2.je(), s);
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
            this.b(akU3);
        } else {
            akU3.w(s);
        }
        return true;
    }

    public boolean e(long l2, short s) {
        akU akU2 = this.F(l2);
        if (akU2 == null) {
            return false;
        }
        if (akU2.hG() + s <= 0) {
            this.i(akU2);
        } else {
            akU2.w(s);
        }
        return true;
    }

    public ut_0 hq() {
        return this.oi;
    }

    public boolean i(akU akU2) {
        if (akU2 == null) {
            return false;
        }
        if (this.oh.m(akU2.je())) {
            short s = this.oh.bU(akU2.je());
            if (this.og[s] != akU2) {
                a.error((Object)("Probl\u00e8me de logique : table d'index et tableau incoh\u00e9rents. Item attendu \u00e0 la position " + s + " : " + akU2 + " item trouv\u00e9 : " + this.og[s]));
                return false;
            }
            if (this.oj != null && this.oj.a(this, akU2) != 0) {
                return false;
            }
            this.og[s] = null;
            this.oh.bV(akU2.je());
            return true;
        }
        return false;
    }
}

