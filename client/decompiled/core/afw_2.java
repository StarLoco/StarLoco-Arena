/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import org.apache.log4j.Logger;

/*
 * Renamed from aFw
 */
public final class afw_2 {
    protected static final Logger a = Logger.getLogger(afw_2.class);
    public static final byte dHi = 1;
    private boolean dHj = false;
    private byte[] Fe;
    private int fb;
    private int fc;

    public afw_2(int n2, int n3, byte[] byArray) {
        this.fb = n2;
        this.fc = n3;
        this.Fe = byArray;
    }

    public afw_2(BufferedImage bufferedImage, boolean bl2) {
        this.b(bufferedImage, bl2);
    }

    public afw_2(acf acf2) {
        this.r(acf2);
    }

    public BufferedImage aRV() {
        return js_1.a(this.fb, this.fc, this.Fe, wq_0.auA);
    }

    private void b(BufferedImage bufferedImage, boolean bl2) {
        if (bufferedImage != null) {
            this.fb = bufferedImage.getWidth();
            this.fc = bufferedImage.getHeight();
            if (bl2) {
                if (bufferedImage.getType() != 3) {
                    bufferedImage = js_1.e(bufferedImage);
                } else if (bufferedImage.getType() != 2) {
                    bufferedImage = js_1.d(bufferedImage);
                }
            } else if (bufferedImage.getType() != 2) {
                bufferedImage = js_1.d(bufferedImage);
            }
            this.dHj = bufferedImage.isAlphaPremultiplied();
            DataBufferInt dataBufferInt = (DataBufferInt)bufferedImage.getData().getDataBuffer();
            this.Fe = new byte[this.fb * this.fc * 4];
            for (int j = 0; j < dataBufferInt.getSize(); ++j) {
                int n2 = 4 * j;
                int n3 = dataBufferInt.getElem(j);
                this.Fe[n2] = (byte)(n3 >> 16 & 0xFF);
                this.Fe[n2 + 1] = (byte)(n3 >> 8 & 0xFF);
                this.Fe[n2 + 2] = (byte)(n3 & 0xFF);
                this.Fe[n2 + 3] = (byte)(n3 >> 24 & 0xFF);
            }
        } else {
            this.fb = 0;
            this.fc = 0;
            this.Fe = null;
        }
    }

    public byte[] getData() {
        return this.Fe;
    }

    public int getHeight() {
        return this.fc;
    }

    public int getWidth() {
        return this.fb;
    }

    public String toString() {
        return "AlphaBitmapData (" + this.fb + "x" + this.fc + ") @" + Integer.toHexString(super.hashCode());
    }

    public void h(aij_1 aij_12) {
        aij_12.ct((short)1);
        aij_12.fe(this.dHj);
        aij_12.oO(this.fb);
        aij_12.oO(this.fc);
        if (this.Fe != null) {
            aij_12.eC(this.Fe.length);
            aij_12.writeBytes(this.Fe);
        } else {
            aij_12.eC(0L);
        }
    }

    public void r(acf acf2) {
        short s = acf2.aqD();
        if (s != 1) {
            a.error((Object)"Exception", (Throwable)new Exception("Version incorrecte:" + s + " courante:" + 1));
        }
        this.dHj = acf2.aqE();
        this.fb = acf2.readUnsignedShort();
        this.fc = acf2.readUnsignedShort();
        int n2 = (int)acf2.readUnsignedInt();
        if (n2 > 0) {
            this.Fe = acf2.jE(n2);
        }
    }

    public void aRW() {
        if (this.Fe != null && !this.dHj) {
            this.dHj = true;
            for (int j = 0; j < this.Fe.length; j += 4) {
                byte by = this.Fe[j + 3];
                this.Fe[j] = (byte)(this.Fe[j] * by / 255);
                this.Fe[j + 1] = (byte)(this.Fe[j + 1] * by / 255);
                this.Fe[j + 2] = (byte)(this.Fe[j + 2] * by / 255);
            }
        }
    }

    public void aRX() {
        if (this.Fe != null && this.dHj) {
            this.dHj = false;
            for (int j = 0; j < this.Fe.length; j += 4) {
                byte by = this.Fe[j + 3];
                if (by != 0) {
                    this.Fe[j] = (byte)(this.Fe[j] * 255 / by);
                    this.Fe[j + 1] = (byte)(this.Fe[j + 1] * 255 / by);
                    this.Fe[j + 2] = (byte)(this.Fe[j + 2] * 255 / by);
                    continue;
                }
                this.Fe[j] = -1;
                this.Fe[j + 1] = -1;
                this.Fe[j + 2] = -1;
            }
        }
    }

    public boolean isAlphaPremultiplied() {
        return this.dHj;
    }

    public double cf(int n2, int n3) {
        if (n2 >= this.fb || n3 >= this.fc || this.Fe == null) {
            return 0.0;
        }
        byte by = this.Fe[4 * (n2 + n3 * this.fb) + 3];
        return (double)by / 255.0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof afw_2) {
            afw_2 afw_22 = (afw_2)object;
            if (this.getWidth() != afw_22.getWidth() || this.getHeight() != afw_22.getHeight()) {
                return false;
            }
            byte[] byArray = this.getData();
            byte[] byArray2 = afw_22.getData();
            for (int j = 0; j < byArray.length; ++j) {
                if (byArray[j] == byArray2[j]) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        assert (false) : "Pas d'insertion possible en tant que clef dans une HashMap/HashTable";
        return super.hashCode();
    }
}

