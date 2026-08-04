/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/*
 * Renamed from iL
 */
class il_1
extends aea_0 {
    private HashMap yK = new HashMap();
    private HashMap yL = new HashMap();
    private HashMap yM = new HashMap();
    final /* synthetic */ axw co;

    il_1(axw axw2) {
        this.co = axw2;
    }

    public void c(ByteBuffer byteBuffer) {
        Object object;
        Object object2;
        byteBuffer.put((byte)this.co.djD.size());
        akz_0 akz_02 = this.co.djD.eI();
        int n2 = this.co.djD.size();
        for (int j = 0; j < n2; ++j) {
            akz_02.fK();
            object2 = (cl_1)akz_02.value();
            byteBuffer.putLong(object2.Lb());
            object = (byte[])this.yL.get(object2);
            if (object != null) {
                byteBuffer.putShort((short)((byte[])object).length);
                byteBuffer.put((byte[])object);
                continue;
            }
            byteBuffer.putShort((short)0);
            a.error((Object)("Impossible de s\u00e9rialiser le match d'id " + this.co.aW + " : Controller s\u00e9rialis\u00e9 \u00e9gal \u00e0 null."));
        }
        byteBuffer.put((byte)this.co.djC.size());
        for (yg_0 yg_02 : this.co.djC) {
            object2 = (byte[])this.yK.get(yg_02);
            if (object2 != null) {
                byteBuffer.putShort((short)((Object)object2).length);
                byteBuffer.put((byte[])object2);
            } else {
                byteBuffer.putShort((short)0);
                a.error((Object)("Impossible de s\u00e9rialiser le match d'id " + this.co.aW + " : Equipe s\u00e9rialis\u00e9e \u00e9gale \u00e0 null."));
            }
            byteBuffer.put((byte)yg_02.amr());
            object = yg_02.amp();
            while (object.hasNext()) {
                alp_0 alp_02 = (alp_0)object.next();
                byteBuffer.putLong(alp_02.getId());
                byte[] byArray = (byte[])this.yM.get(alp_02);
                if (byArray != null) {
                    byteBuffer.putShort((short)byArray.length);
                    byteBuffer.put(byArray);
                } else {
                    byteBuffer.putShort((short)0);
                    a.error((Object)("Impossible de s\u00e9rialiser le match d'id " + this.co.aW + " : Fighter s\u00e9rialis\u00e9 \u00e9gal \u00e0 null."));
                }
                byteBuffer.put(this.co.djG.contains(alp_02) ? (byte)1 : 0);
                if (alp_02.PR()) {
                    byteBuffer.put((byte)1);
                    continue;
                }
                if (alp_02.PT()) {
                    byteBuffer.put((byte)2);
                    continue;
                }
                byteBuffer.put((byte)0);
            }
        }
        this.yK.clear();
        this.yL.clear();
        this.yM.clear();
    }

    public void f(ByteBuffer byteBuffer) {
        Object object;
        Object object2;
        this.co.djG.clear();
        this.co.djE.clear();
        this.co.djF.clear();
        this.co.djD.clear();
        byte by = byteBuffer.get();
        for (byte by2 = 0; by2 < by; by2 = (byte)(by2 + 1)) {
            long l2 = byteBuffer.getLong();
            object2 = new byte[byteBuffer.getShort()];
            byteBuffer.get((byte[])object2);
            object = this.co.ef(l2);
            if (object == null) {
                object = this.co.cb(l2);
            }
            if (object == null) {
                a.error((Object)("Impossible de d\u00e9s\u00e9rialiser le match d'id " + this.co.aW + " : Controller \u00e9gal \u00e0 null."));
                continue;
            }
            object.D((byte[])object2);
            this.co.djD.a(object.Lb(), object);
        }
        ArrayList<Object> arrayList = new ArrayList<Object>();
        byte by3 = byteBuffer.get();
        for (byte by4 = 0; by4 < by3; by4 = (byte)(by4 + 1)) {
            object2 = this.co.asD();
            object = new byte[byteBuffer.getShort()];
            byteBuffer.get((byte[])object);
            ((yg_0)object2).R((byte[])object);
            arrayList.add(object2);
            byte by5 = byteBuffer.get();
            block6: for (byte by6 = 0; by6 < by5; by6 = (byte)(by6 + 1)) {
                long l3 = byteBuffer.getLong();
                alp_0 alp_02 = this.co.eg(l3);
                byte[] byArray = new byte[byteBuffer.getShort()];
                byteBuffer.get(byArray);
                if (alp_02 == null) {
                    alp_02 = this.co.cd(l3);
                }
                if (alp_02 != null) {
                    alp_02.H(byArray);
                } else {
                    a.error((Object)("Impossible de d\u00e9s\u00e9rialiser le match d'id " + this.co.aW + " : Fighter \u00e9gal \u00e0 null."));
                }
                if (alp_02 != null) {
                    alp_02.a((yg_0)null);
                    ((yg_0)object2).j(alp_02);
                }
                if (byteBuffer.get() != 0 && alp_02 != null) {
                    if (this.co.r(alp_02)) {
                        a.error((Object)("Impossible d'ajouter le combattant d'id " + alp_02.getId() + " dans le match d'id " + this.co.aW + " : D\u00e9j\u00e0 pr\u00e9sent."), (Throwable)new Exception());
                    }
                    this.co.djG.add(alp_02);
                }
                switch (byteBuffer.get()) {
                    case 0: {
                        if (alp_02 == null) continue block6;
                        this.co.djE.a(alp_02.getId(), alp_02);
                        continue block6;
                    }
                    case 1: {
                        if (alp_02 == null) continue block6;
                        this.co.djF.a(alp_02.getId(), alp_02);
                    }
                }
            }
        }
        Iterator iterator = this.co.djC.iterator();
        while (iterator.hasNext()) {
            object2 = (yg_0)iterator.next();
            ((yg_0)object2).release();
        }
        this.co.djC.clear();
        this.co.djC.addAll(arrayList);
    }

    public int lF() {
        Object object;
        Object object2;
        this.yK.clear();
        this.yL.clear();
        this.yM.clear();
        int n2 = 1;
        akz_0 akz_02 = this.co.djD.eI();
        int n3 = this.co.djD.size();
        for (int j = 0; j < n3; ++j) {
            akz_02.fK();
            object2 = (cl_1)akz_02.value();
            object = object2.Lh();
            n2 += 10 + ((byte[])object).length;
            this.yL.put(object2, object);
        }
        ++n2;
        for (yg_0 yg_02 : this.co.djC) {
            object2 = yg_02.ams();
            this.yK.put(yg_02, object2);
            n2 += 2 + ((Object)object2).length;
            ++n2;
            object = yg_02.amp();
            while (object.hasNext()) {
                alp_0 alp_02 = (alp_0)object.next();
                byte[] byArray = alp_02.PU();
                this.yM.put(alp_02, byArray);
                n2 += 10 + byArray.length + 1 + 1;
            }
        }
        a.info((Object)("La taille s\u00e9rialis\u00e9e du match d'id " + this.co.aW + " est \u00e9gale \u00e0 " + n2 + "."));
        return n2;
    }
}

