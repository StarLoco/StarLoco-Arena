/*
 * Decompiled with CFR 0.152.
 */
public class Im
extends cf_2 {
    private boolean bgy;

    public void Ko() {
        this.a(new nz_2());
        wA.CT().a(this);
    }

    public void Kq() {
        super.Kq();
        this.ig();
        wA.CT().b(this);
        try {
            this.release();
        }
        catch (Exception exception) {
            a.error((Object)"Exception  lors d'un release d'une Admin Entity, n'arrive pas normalement");
        }
    }

    public void b() {
        super.b();
    }

    public void j() {
        super.j();
        this.bgy = false;
    }

    public boolean Uf() {
        return this.bgy;
    }

    public void bG(boolean bl2) {
        this.bgy = bl2;
    }
}

