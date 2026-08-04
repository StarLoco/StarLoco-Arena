/*
 * Decompiled with CFR 0.152.
 */
import java.nio.ByteBuffer;

public class lo
extends ael_2 {
    private byte GV;
    private ByteBuffer GW;

    public boolean a(byte[] byArray) {
        if (!this.a(byArray.length, 1, false)) {
            return false;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        this.GV = byteBuffer.get();
        byte[] byArray2 = new byte[byArray.length - 1];
        byteBuffer.get(byArray2);
        this.GW = ByteBuffer.wrap(byArray2);
        return true;
    }

    public int getId() {
        return 106;
    }

    public byte qd() {
        return this.GV;
    }

    public String qe() {
        if (this.GW.remaining() < 2) {
            a.error((Object)("extractStringParameter() impossible d'extraire la taille de la cha\u00eene : " + this.GW.remaining() + " bytes restant dans le buffer (2 requis)"));
            return null;
        }
        int n2 = this.GW.get() & 0xFF;
        byte[] byArray = new byte[n2];
        if (this.GW.remaining() < n2) {
            a.error((Object)("extractStringParameter() impossible d'extraire la cha\u00eene : " + this.GW.remaining() + " bytes restant dans le buffer (" + n2 + " requis)"));
            return null;
        }
        this.GW.get(byArray);
        return aey_0.V(byArray);
    }

    public int qf() {
        if (this.GW.remaining() < 4) {
            a.error((Object)("extractIntParameter() impossible d'extraire l'Integer : " + this.GW.remaining() + " bytes restant dans le buffer (4 requis)"));
            return 0;
        }
        return this.GW.getInt();
    }

    public boolean qg() {
        if (this.GW.remaining() < 1) {
            a.error((Object)("extractBooleanParameter() impossible d'extraire le Boolean : " + this.GW.remaining() + " bytes restant dans le buffer (1 requis)"));
            return false;
        }
        return this.GW.get() != 0;
    }

    public byte qh() {
        if (this.GW.remaining() < 1) {
            a.error((Object)("extractByteParameter() impossible d'extraire le Byte : " + this.GW.remaining() + " bytes restant dans le buffer (1 requis)"));
            return 0;
        }
        return this.GW.get();
    }
}

