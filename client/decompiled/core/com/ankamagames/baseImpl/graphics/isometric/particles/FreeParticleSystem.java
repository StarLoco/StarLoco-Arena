/*
 * Decompiled with CFR 0.152.
 */
package com.ankamagames.baseImpl.graphics.isometric.particles;

import com.ankamagames.baseImpl.graphics.isometric.particles.IsoParticleSystem;
import java.util.ArrayList;

public class FreeParticleSystem
extends IsoParticleSystem
implements Du,
aFy {
    private Du Ie;
    protected static final int aFe = -1;
    private int aFf = -1;
    private int oI = Integer.MIN_VALUE;
    private int oJ = Integer.MIN_VALUE;
    private int oK;
    private ArrayList oL = null;
    private byte aFg;

    public FreeParticleSystem(boolean bl2) {
        super(bl2);
    }

    protected boolean Gd() {
        return akk_0.aVL().d(this.bZE, qo_2.C(this.getId(), this.Gf(), -1));
    }

    public float getX() {
        if (this.Ie != null) {
            return (float)this.Ie.getWorldX();
        }
        return super.getX();
    }

    public float getY() {
        if (this.Ie != null) {
            return (float)this.Ie.getWorldY();
        }
        return super.getY();
    }

    public float id() {
        if (this.Ie != null) {
            return (float)this.Ie.getAltitude();
        }
        return super.id();
    }

    public Du qF() {
        return this.Ie;
    }

    public void a(Du du) {
        this.Ie = du;
    }

    public void a(Du du, int n2) {
        if (n2 == 0) {
            this.a(du);
        } else {
            this.a(new ais_1(du, n2));
        }
    }

    public double getAltitude() {
        return this.id();
    }

    public short gp() {
        return (short)this.getAltitude();
    }

    public int gn() {
        return (int)this.getX();
    }

    public int go() {
        return (int)this.getY();
    }

    public double getWorldX() {
        return this.getX();
    }

    public double getWorldY() {
        return this.getY();
    }

    public void b(double d, double d2) {
        this.r((float)d, (float)d2);
    }

    public void a(double d, double d2, double d3) {
        this.setPosition((float)d, (float)d2, (float)d3);
    }

    public int getScreenX() {
        return this.oI;
    }

    public int getScreenY() {
        return this.oJ;
    }

    public void ai(int n2) {
        if (this.oI == n2) {
            return;
        }
        this.oI = n2;
        this.hD();
    }

    public void aj(int n2) {
        if (this.oJ == n2) {
            return;
        }
        this.oJ = n2;
        this.hD();
    }

    public void ak(int n2) {
        if (this.oK == n2) {
            return;
        }
        this.oK = n2;
        this.hD();
    }

    public int hB() {
        return this.oK;
    }

    public boolean hC() {
        return this.oI != Integer.MIN_VALUE && this.oJ == Integer.MIN_VALUE;
    }

    public void a(fj_0 fj_02) {
        if (this.oL == null) {
            this.oL = new ArrayList();
        }
        this.oL.add(fj_02);
    }

    public void b(fj_0 fj_02) {
        if (this.oL == null) {
            return;
        }
        this.oL.remove(fj_02);
        if (this.oL.size() == 0) {
            this.oL = null;
            this.oJ = Integer.MIN_VALUE;
            this.oI = Integer.MIN_VALUE;
        }
    }

    protected void hD() {
        if (this.oL != null) {
            int n2 = this.oL.size();
            for (int j = 0; j < n2; ++j) {
                ((fj_0)this.oL.get(j)).a(this, this.oI, this.oJ, this.oK);
            }
        }
    }

    public int Ge() {
        if (this.Ie != null && this.Ie instanceof xw_0) {
            return ((xw_0)((Object)this.Ie)).Ge();
        }
        return super.Ge();
    }

    public int Gf() {
        Du du = this.qF();
        if (du instanceof arp_0) {
            return ((arp_0)((Object)du)).aEZ();
        }
        return this.aFf;
    }

    public void eC(int n2) {
        this.aFf = n2;
    }

    public void G(byte by) {
        super.G(by);
        this.aFg = by;
    }

    public float hA() {
        return this.cpB;
    }

    public void a(aba_2 aba_22) {
        super.a(aba_22);
    }

    public boolean Gg() {
        return this.Ie != null || super.Gg();
    }
}

