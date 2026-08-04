/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.WeakHashMap;

public class bU
extends OutputStream {
    private static final int hG = 1024;
    private static final int hH = 132;
    private static final int hI = 13;
    private static final int hJ = 10;
    private WeakHashMap hK = new WeakHashMap();
    private UI hL;
    private boolean hM;

    public bU(UI uI, boolean bl2) {
        this.hL = uI;
        this.hM = bl2;
    }

    private vb_2 dV() {
        Thread thread = Thread.currentThread();
        vb_2 vb_22 = (vb_2)this.hK.get(thread);
        if (vb_22 == null) {
            vb_22 = new vb_2(null);
            vb_2.a(vb_22, new ByteArrayOutputStream(132));
            vb_2.a(vb_22, false);
            this.hK.put(thread, vb_22);
        }
        return vb_22;
    }

    private void dW() {
        Thread thread = Thread.currentThread();
        vb_2 vb_22 = (vb_2)this.hK.get(thread);
        try {
            vb_2.a(vb_22).close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        vb_2.a(vb_22, new ByteArrayOutputStream());
        vb_2.a(vb_22, false);
    }

    private void dX() {
        Thread thread = Thread.currentThread();
        this.hK.remove(thread);
    }

    public void write(int n2) {
        byte by = (byte)n2;
        vb_2 vb_22 = this.dV();
        if (by == 10) {
            vb_2.a(vb_22).write(n2);
            this.a(vb_2.a(vb_22));
        } else {
            if (vb_2.b(vb_22)) {
                this.a(vb_2.a(vb_22));
            }
            vb_2.a(vb_22).write(n2);
        }
        vb_2.a(vb_22, by == 13);
        if (!vb_2.b(vb_22) && vb_2.a(vb_22).size() > 1024) {
            this.a(vb_2.a(vb_22));
        }
    }

    protected void a(ByteArrayOutputStream byteArrayOutputStream) {
        String string = byteArrayOutputStream.toString();
        this.hL.n(string, this.hM);
        this.dW();
    }

    protected void b(ByteArrayOutputStream byteArrayOutputStream) {
        String string = byteArrayOutputStream.toString();
        this.hL.o(string, this.hM);
        this.dW();
    }

    public void close() {
        this.flush();
        this.dX();
    }

    public void flush() {
        vb_2 vb_22 = this.dV();
        if (vb_2.a(vb_22).size() > 0) {
            this.b(vb_2.a(vb_22));
        }
    }

    public void write(byte[] byArray, int n2, int n3) {
        int n4;
        int n5 = n4 = n2;
        int n6 = n3;
        vb_2 vb_22 = this.dV();
        while (n6 > 0) {
            while (n6 > 0 && byArray[n4] != 10 && byArray[n4] != 13) {
                ++n4;
                --n6;
            }
            int n7 = n4 - n5;
            if (n7 > 0) {
                vb_2.a(vb_22).write(byArray, n5, n7);
            }
            while (n6 > 0 && (byArray[n4] == 10 || byArray[n4] == 13)) {
                this.write(byArray[n4]);
                ++n4;
                --n6;
            }
            n5 = n4;
        }
    }
}

