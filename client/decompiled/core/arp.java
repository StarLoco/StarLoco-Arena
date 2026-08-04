/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;

public class arp {
    private static final Logger a = Logger.getLogger(arp.class);
    private final lb_0 cPE = new lb_0();
    private String eA;
    private static final arp cPF = new arp();

    public static arp aEu() {
        return cPF;
    }

    private arp() {
    }

    public String getFileName() {
        return this.eA;
    }

    public void setFile(String string) {
        this.eA = string;
    }

    public void load() {
        try {
            assert (this.eA != null && !new File(this.eA).isDirectory());
            acf acf2 = new acf(vq_2.gm(this.eA));
            this.b(acf2);
            acf2.close();
        }
        catch (IOException iOException) {
            a.error((Object)"", (Throwable)iOException);
        }
    }

    private void b(acf acf2) {
        if (acf2 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/sound/SoundBank.load must not be null");
        }
        int n2 = acf2.readInt();
        for (int j = 0; j < n2; ++j) {
            ws_0 ws_02 = new ws_0();
            ws_02.b(acf2);
            if (this.cPE.c(ws_02.getId(), ws_02) == null) continue;
            a.error((Object)("SoundBank already loaded : " + ws_02.getId()));
        }
    }

    public void a(aij_1 aij_12) {
        if (aij_12 == null) {
            throw new IllegalArgumentException("Argument 0 for @NotNull parameter of com/ankamagames/baseImpl/graphics/alea/sound/SoundBank.save must not be null");
        }
        ll_0 ll_02 = this.cPE.pK();
        aij_12.writeInt(this.cPE.size());
        while (ll_02.hasNext()) {
            ll_02.fK();
            ((ws_0)ll_02.value()).a(aij_12);
        }
    }

    public final ws_0 a(ws_0 ws_02) {
        assert (!this.cPE.contains(ws_02.getId()));
        return (ws_0)this.cPE.c(ws_02.getId(), ws_02);
    }

    public final ws_0 lS(int n2) {
        return (ws_0)this.cPE.get(n2);
    }

    public final void clear() {
        this.cPE.clear();
    }
}

