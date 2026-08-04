/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import org.apache.log4j.Logger;

public class aLM
extends Kt {
    protected static final Logger a = Logger.getLogger(aLM.class);
    private oh_0 dWo = null;
    public static final byte dWp = 2;

    public void a(oh_0 oh_02) {
        this.dWo = oh_02;
    }

    private void j(aak_2 aak_22) {
        if (this.dWo != null) {
            this.dWo.a(aak_22);
        }
    }

    public byte[] cd() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1 + 2 * this.bnK.size());
        this.c(byteBuffer);
        return byteBuffer.array();
    }

    public void c(ByteBuffer byteBuffer) {
        byteBuffer.put((byte)this.bnK.size());
        hn_1 hn_12 = this.bnK.GD();
        int n2 = this.bnK.size();
        while (n2-- > 0) {
            hn_12.fK();
            byteBuffer.put(hn_12.kG());
            byteBuffer.put(hn_12.kH());
        }
    }

    public void f(ByteBuffer byteBuffer) {
        this.bnK.clear();
        byte by = byteBuffer.get();
        for (byte by2 = 0; by2 < by; by2 = (byte)(by2 + 1)) {
            byte by3 = byteBuffer.get();
            byte by4 = byteBuffer.get();
            avx_0 avx_02 = avx_0.a(by3);
            if (avx_02 != null) {
                this.bnK.e(by3, by4);
                continue;
            }
            a.error((Object)("erreur ? la d\u00e9s\u00e9rialisation : property inconnue : " + by3));
        }
        this.j(null);
    }

    public boolean a(jW jW2) {
        return false;
    }

    public boolean b(jW jW2) {
        return false;
    }

    public byte a(avx_0 avx_02) {
        byte by = super.g(avx_02);
        this.j(avx_02);
        return by;
    }

    public byte b(avx_0 avx_02) {
        byte by = super.h(avx_02);
        this.j(avx_02);
        return by;
    }

    public void c(avx_0 avx_02) {
        super.i(avx_02);
        this.j(avx_02);
    }

    public void reset() {
        super.reset();
        this.j(null);
    }
}

