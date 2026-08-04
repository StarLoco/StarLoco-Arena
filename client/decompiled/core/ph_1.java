/*
 * Decompiled with CFR 0.152.
 */
import com.sun.opengl.util.texture.TextureCoords;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.glu.GLU;

/*
 * Renamed from ph
 */
public class ph_1 {
    private static int abl = -1;
    private int rp;
    private int abm = 0;
    private int fb;
    private int fc;
    private int abn;
    private int abo;
    private boolean abp;
    private ByteBuffer abq;
    private int abr;
    private int abs;
    private int abt;
    private int abu;
    private int abv;
    private TextureCoords abw;
    protected awL Ff;

    public ph_1(int n2, int n3, int n4, ByteBuffer byteBuffer, int n5, boolean bl2, int n6, int n7, int n8, int n9) {
        this.rp = n2;
        this.abn = n3;
        this.abo = n4;
        this.fb = ph_1.S(n3);
        this.fc = ph_1.S(n4);
        this.abw = new TextureCoords(0.0f, (float)this.abn / (float)this.fb, (float)this.abo / (float)this.fc, 0.0f);
        this.abq = byteBuffer;
        this.abr = n5;
        this.abp = bl2;
        this.abu = n8;
        this.abv = n9;
        this.abs = n6;
        this.abt = n7;
    }

    public ph_1(int n2, int n3, int n4, ByteBuffer byteBuffer, int n5, boolean bl2) {
        this(n2, n3, n4, byteBuffer, n5, bl2, 9729, 9728, 10496, 10496);
    }

    public ph_1(int n2, BufferedImage bufferedImage, int n3, boolean bl2) {
        DataBufferByte dataBufferByte;
        if (bufferedImage.getType() != 7) {
            BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 7);
            bufferedImage2.getGraphics().drawImage(bufferedImage, 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
            dataBufferByte = (DataBufferByte)bufferedImage2.getData().getDataBuffer();
        } else {
            dataBufferByte = (DataBufferByte)bufferedImage.getData().getDataBuffer();
        }
        this.rp = n2;
        this.abn = bufferedImage.getWidth();
        this.abo = bufferedImage.getHeight();
        this.fb = ph_1.S(this.abn);
        this.fc = ph_1.S(this.abo);
        this.abw = new TextureCoords(0.0f, (float)this.abn / (float)this.fb, (float)this.abo / (float)this.fc, 0.0f);
        this.abq = ByteBuffer.wrap(dataBufferByte.getData());
        this.abr = n3;
        this.abp = bl2;
        this.abu = 10496;
        this.abv = 10496;
        this.abs = 9728;
        this.abt = 9728;
    }

    public void initialize() {
        this.a(this.rp, this.abn, this.abo, this.abq, this.abr, this.abp, this.abs, this.abt, this.abu, this.abv);
        this.abq = null;
    }

    private void a(int n2, int n3, int n4, ByteBuffer byteBuffer, int n5, boolean bl2, int n6, int n7, int n8, int n9) {
        int n10;
        int n11;
        GL gL = GLU.getCurrentGL();
        this.rp = n2;
        this.abn = n3;
        this.abo = n4;
        this.fb = ph_1.S(n3);
        this.fc = ph_1.S(n4);
        float f = (float)n3 / (float)this.fb;
        float f2 = (float)n4 / (float)this.fc;
        int[] nArray = new int[1];
        gL.glGenTextures(1, nArray, 0);
        this.abm = nArray[0];
        int n12 = 4 * this.fb;
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(n12 * this.fc);
        ByteBuffer byteBuffer3 = byteBuffer;
        byte[] byArray = new byte[n3 * 4];
        byte[] byArray2 = new byte[(this.fb - n3) * 4];
        if (bl2) {
            n11 = n12 * n4;
            for (n10 = 0; n10 < n4; ++n10) {
                byteBuffer3.get(byArray);
                byteBuffer2.position(n11 -= n12);
                byteBuffer2.put(byArray);
            }
        } else {
            for (n11 = 0; n11 < n4; ++n11) {
                byteBuffer3.get(byArray);
                byteBuffer2.put(byArray);
                byteBuffer2.put(byArray2);
            }
        }
        byteBuffer2.rewind();
        n11 = 0;
        switch (n5) {
            case 6408: 
            case 32993: {
                n11 = 3;
                break;
            }
            case 32768: {
                n11 = 0;
            }
        }
        n10 = this.fc * this.fb;
        this.Ff = new awL(n10, this.fb);
        for (int j = 0; j < n10; ++j) {
            byte by = byteBuffer2.get(j * 4 + n11);
            if (by < 0) {
                by = (byte)(by + 256);
            }
            this.Ff.i(j, by <= 25);
        }
        byteBuffer2.rewind();
        this.bind();
        gL.glTexImage2D(this.rp, 0, 4, this.fb, this.fc, 0, n5, 5121, byteBuffer2);
        gL.glTexParameterf(3553, 10242, n8);
        gL.glTexParameterf(3553, 10243, n9);
        gL.glTexParameterf(3553, 10240, n6);
        gL.glTexParameterf(3553, 10241, n7);
        this.abw = new TextureCoords(0.0f, f2, f, 0.0f);
    }

    public void a(GL gL, ByteBuffer byteBuffer) {
        int n2;
        int n3 = 4 * this.fb;
        ByteBuffer byteBuffer2 = ByteBuffer.allocate(n3 * this.fc);
        ByteBuffer byteBuffer3 = byteBuffer;
        byte[] byArray = new byte[this.abn * 4];
        byte[] byArray2 = new byte[(this.fb - this.abn) * 4];
        for (n2 = 0; n2 < this.abo; ++n2) {
            byteBuffer3.get(byArray);
            byteBuffer2.put(byArray);
            byteBuffer2.put(byArray2);
        }
        byteBuffer2.rewind();
        n2 = 0;
        switch (this.abr) {
            case 6408: 
            case 32993: {
                n2 = 3;
                break;
            }
            case 32768: {
                n2 = 0;
            }
        }
        int n4 = this.fc * this.fb;
        this.Ff = new awL(n4, this.fb);
        for (int j = 0; j < n4; ++j) {
            byte by = byteBuffer2.get(j * 4 + n2);
            if (by < 0) {
                by = (byte)(by + 256);
            }
            this.Ff.i(j, by <= 25);
        }
        byteBuffer2.rewind();
        this.bind();
        gL.glTexImage2D(this.rp, 0, 4, this.fb, this.fc, 0, this.abr, 5121, byteBuffer2);
    }

    public static int S(int n2) {
        --n2;
        n2 |= n2 >> 1;
        n2 |= n2 >> 2;
        n2 |= n2 >> 4;
        n2 |= n2 >> 8;
        n2 |= n2 >> 16;
        return ++n2;
    }

    public void bind() {
        if (this.rp != 3553 || this.abm != abl) {
            GLU.getCurrentGL().glBindTexture(this.rp, this.abm);
        }
    }

    public void enable() {
        if (this.rp == 3553) {
            db_2 db_22 = arX.cQT.iE();
            vo_1 vo_12 = vo_1.aik();
            vo_12.cu(true);
            vo_12.n(db_22);
        } else {
            GLU.getCurrentGL().glEnable(this.rp);
        }
    }

    public void disable() {
        if (this.rp == 3553) {
            db_2 db_22 = arX.cQT.iE();
            vo_1 vo_12 = vo_1.aik();
            vo_12.cu(false);
            vo_12.n(db_22);
        } else {
            GLU.getCurrentGL().glDisable(this.rp);
        }
    }

    public void a(TextureCoords textureCoords) {
        this.abw = textureCoords;
    }

    public TextureCoords getImageTexCoords() {
        return this.abw;
    }

    public int getWidth() {
        return this.fb;
    }

    public int getHeight() {
        return this.fc;
    }

    public int getImageWidth() {
        return this.abn;
    }

    public int getImageHeight() {
        return this.abo;
    }

    public int getTarget() {
        return this.rp;
    }

    public int tW() {
        return this.abr;
    }

    public boolean tX() {
        return this.abp;
    }

    public boolean isInitialized() {
        return this.abm != 0;
    }

    public int tY() {
        return this.abs;
    }

    public int tZ() {
        return this.abt;
    }

    public int ua() {
        return this.abu;
    }

    public int ub() {
        return this.abv;
    }

    public void cE(int n2) {
        this.abm = n2;
    }

    public void l(ByteBuffer byteBuffer) {
        this.abq = byteBuffer;
    }

    public ByteBuffer getData() {
        return this.abq;
    }

    public void dispose() {
        cW.fd().T(this.abm);
        this.rp = 0;
        this.abm = 0;
        this.fb = 0;
        this.fc = 0;
        this.abn = 0;
        this.abo = 0;
        this.abr = 0;
        this.abq = null;
        this.abw = null;
        this.Ff = null;
    }

    public int getTextureObject() {
        return this.abm;
    }

    public long uc() {
        return this.Ff.getSize() * 4;
    }

    public boolean L(int n2, int n3) {
        return this.Ff.ca(n2, n3);
    }
}

