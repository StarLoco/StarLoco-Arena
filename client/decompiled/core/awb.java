/*
 * Decompiled with CFR 0.152.
 */
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class awb
extends nu_1 {
    private static final byte dho = 8;
    private final LinkedList dhp = new LinkedList();
    private final Object dhq = new Object();
    private final ArrayList adq = new ArrayList(5);
    private final ArrayList dhr = new ArrayList(5);
    private abz_1 dhs = null;
    private byte dht = (byte)8;

    public awb(String string) {
        super(string);
    }

    public awb(String string, byte by) {
        super(string, by);
    }

    public Collection uZ() {
        return this.dhp;
    }

    public avE a(auk auk2, boolean bl2, boolean bl3, boolean bl4, long l2) {
        avE avE2 = this.a(auk2, -1L);
        if (avE2 == null) {
            a.debug((Object)("Impossible d'initialiser une source audio : " + auk2.getDescription()));
            return null;
        }
        if (this.a(avE2, true, true, bl4)) {
            return avE2;
        }
        return null;
    }

    public void b(avE avE2) {
        this.a(avE2, true, true, avE2.og());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean a(avE avE2, boolean bl2, boolean bl3, boolean bl4) {
        if (avE2 == null) {
            a.error((Object)"On ne peut pas jouer une source nulle");
            return false;
        }
        avE2.aj(this.getGain());
        avE2.setMute(this.abg());
        avE2.eq(bl4);
        if (this.bAV != -1) {
            avE2.mx(this.bAV);
        }
        if (this.bAW) {
            avE2.er(true);
            avE2.mz(0);
        }
        Object object = this.dhq;
        synchronized (object) {
            if (this.dhp.size() > this.dht) {
                this.bAT.a(avE2);
                return false;
            }
            try {
                avE2.play();
            }
            catch (Exception exception) {
                a.error((Object)"Erreur durant la lecture de la source", (Throwable)exception);
                this.bAT.a(avE2);
                return false;
            }
            this.adq.add(avE2);
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void bM() {
        Object object = this.dhq;
        synchronized (object) {
            int n2 = this.adq.size();
            for (int j = 0; j < n2; ++j) {
                avE avE2 = (avE)this.adq.get(j);
                this.dhp.add(avE2);
            }
            this.adq.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void G(float f) {
        Object object = this.dhq;
        synchronized (object) {
            for (avE avE2 : this.dhp) {
                avE2.aj(this.getGain());
            }
        }
    }

    public void n(float f, float f2) {
    }

    public void o(float f, float f2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c(boolean bl2, boolean bl3) {
        Object object = this.dhq;
        synchronized (object) {
            for (avE avE2 : this.dhp) {
                avE2.setMute(bl3);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void ay(long l2) {
        super.ay(l2);
        Object object = this.dhq;
        synchronized (object) {
            int n2;
            int n3 = 0;
            if (this.dj != null) {
                n3 = this.dj.zU();
            }
            int n4 = this.dhp.size();
            for (n2 = 0; n2 < n4; ++n2) {
                avE avE2 = (avE)this.dhp.get(n2);
                switch (avE2.dW(l2)) {
                    case 1: 
                    case 3: {
                        this.dhr.add(avE2);
                        this.bAT.a(avE2);
                    }
                }
            }
            n2 = this.dhr.size();
            for (int j = 0; j < n2; ++j) {
                this.dhp.remove(this.dhr.get(j));
            }
            if (n2 > 0 && this.dhp.size() == 0 && this.dhs != null) {
                this.dhs.ly();
            }
            this.dhr.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void stop() {
        Object object = this.dhq;
        synchronized (object) {
            int n2 = this.dhp.size();
            for (int j = 0; j < n2; ++j) {
                this.bAT.a((avE)this.dhp.get(j));
            }
            this.dhp.clear();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void c(avE avE2) {
        Object object = this.dhq;
        synchronized (object) {
            boolean bl2 = this.dhp.remove(avE2);
            if (bl2) {
                this.bAT.a(avE2);
            }
        }
    }

    public boolean cL(int n2) {
        if (!super.cL(n2)) {
            return false;
        }
        int n3 = this.dhp.size();
        for (int j = 0; j < n3; ++j) {
            ((avE)this.dhp.get(j)).mx(n2);
        }
        return true;
    }

    public void a(abz_1 abz_12) {
        this.dhs = abz_12;
    }

    public void bl(float f) {
        if (this.dhs != null) {
            this.dhs.bA(f);
        }
    }

    public void bm(float f) {
        if (this.dhs != null) {
            this.dhs.bB(f);
        }
    }

    public void aeG() {
        if (this.dhs != null) {
            this.dhs.aNw();
            this.dhs.aNx();
        }
    }
}

