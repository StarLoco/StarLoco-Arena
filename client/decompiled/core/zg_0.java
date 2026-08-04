/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.kernel.core.maths.Matrix44;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;

/*
 * Renamed from Zg
 */
public class zg_0
extends ajz {
    final bx_2 wU;

    public zg_0(bx_2 bx_22) {
        this(96, 192, 1.5f, 0.5f, 0.1f, bx_22);
    }

    public zg_0(int n2, int n3, float f, float f2, float f3, bx_2 bx_22) {
        super(n2, n3, f, f2, f3);
        this.wU = bx_22;
    }

    public void a(ByteArrayOutputStream byteArrayOutputStream, String string) {
        this.a(string, new aKD(this, byteArrayOutputStream));
    }

    public void L(String string, String string2) {
        this.a(string2, new akc_0(this, string));
    }

    public void a(String string, uz_1 uz_12) {
        this.wU.a(new aKy(this, string, uz_12));
    }

    private int l(GL gL) {
        int[] nArray = new int[1];
        gL.glEnable(3553);
        gL.glGenTextures(1, nArray, 0);
        gL.glBindTexture(3553, nArray[0]);
        gL.glTexParameteri(3553, 10241, 9729);
        gL.glTexParameteri(3553, 10240, 9729);
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.abn * this.abo * 4);
        gL.glTexImage2D(3553, 0, 4, this.abn, this.abo, 0, 6408, 5121, byteBuffer);
        return nArray[0];
    }

    private byte[] a(int n2, qp_2 qp_22, String string) {
        GL gL = (GL)qp_22.LV();
        int n3 = this.abn;
        int n4 = this.abo;
        vo_1.aik().reset();
        float[] fArray = new float[4];
        gL.glGetFloatv(3106, fArray, 0);
        gL.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gL.glClear(17408);
        qp_22.adV.nO(0);
        vo_1 vo_12 = vo_1.aik();
        vo_12.cr(true);
        vo_12.a(jq_0.bmI);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        vo_12.a(jq_0.bmH);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        vo_12.a(jq_0.bmG);
        vo_12.n(qp_22);
        gL.glLoadIdentity();
        float f = (float)n3 / 2.0f;
        float f2 = (float)n4 / 2.0f;
        gL.glViewport(0, 0, n3, n4);
        if (f < 1.0f) {
            f = 1.0f;
        }
        if (f2 < 1.0f) {
            f2 = 1.0f;
        }
        gL.glOrtho(-f, f, -f2, f2, 0.0, 65535.0);
        vo_1.aik().reset();
        ahA.axi().axj();
        qp_22.c(Matrix44.bEn);
        float f3 = f * (this.Gv - 0.5f) / this.aaw;
        float f4 = f2 * (this.Gw - 0.5f) / this.aaw;
        this.cAE.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        avz avz2 = new avz();
        avz2.m(this.aaw, this.aaw, 1.0f);
        avz2.e(f3, f4, 0.0f);
        this.cAE.a(ub_0.bPW);
        this.cAE.b(ub_0.bPW);
        this.cAF.a(0, this.cAE, 0);
        vo_1.aik().at(2.0f);
        this.cAE.aUM().a(avz2);
        this.cAE.d(qp_22);
        gL.glFlush();
        vo_1.aik().cu(true);
        gL.glBindTexture(3553, n2);
        gL.glCopyTexImage2D(3553, 0, 6408, 0, 0, n3, n4, 0);
        byte[] byArray = new byte[n3 * n4 * 4];
        ByteBuffer byteBuffer = ByteBuffer.wrap(byArray);
        gL.glGetTexImage(3553, 0, 6408, 5121, byteBuffer);
        BufferedImage bufferedImage = js_1.a(n3, n4, byArray, wq_0.auA);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            BufferedImage bufferedImage2 = js_1.a(bufferedImage, true);
            ImageIO.write((RenderedImage)bufferedImage2, string, byteArrayOutputStream);
        }
        catch (IOException iOException) {
            ajz.Dm().error((Object)"", (Throwable)iOException);
        }
        vo_1.aik().reset();
        gL.glClearColor(fArray[0], fArray[1], fArray[2], fArray[3]);
        return byteArrayOutputStream.toByteArray();
    }

    static /* synthetic */ int a(zg_0 zg_02, GL gL) {
        return zg_02.l(gL);
    }

    static /* synthetic */ byte[] a(zg_0 zg_02, int n2, qp_2 qp_22, String string) {
        return zg_02.a(n2, qp_22, string);
    }
}

