/*
 * Decompiled with CFR 0.152.
 */
import com.ankamagames.framework.graphics.engine.VertexBufferPCT;
import com.ankamagames.framework.graphics.engine.geometry.GeometryMesh;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.awt.image.RescaleOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.FloatBuffer;
import javax.imageio.ImageIO;

/*
 * Renamed from ayv
 */
public class ayv_0
extends ajz {
    private final BufferedImage dmu;

    public ayv_0() {
        this(96, 192, 1.5f, 0.5f, 0.1f);
    }

    public ayv_0(int n2, int n3, float f, float f2, float f3) {
        super(n2, n3, f, f2, f3);
        this.dmu = new BufferedImage(this.abn, this.abo, 2);
    }

    public void a(ByteArrayOutputStream byteArrayOutputStream, String string) {
        this.a((OutputStream)byteArrayOutputStream, string);
    }

    public void L(String string, String string2) {
        try {
            this.a(vq_2.gw(string), string2);
        }
        catch (IOException iOException) {
            ajz.Dm().error((Object)"", (Throwable)iOException);
        }
    }

    public void a(String string, uz_1 uz_12) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (this.azh()) {
            this.a((OutputStream)byteArrayOutputStream, string);
            uz_12.z(byteArrayOutputStream.toByteArray());
        }
    }

    private void a(OutputStream outputStream, String string) {
        this.aLA();
        try {
            AffineTransform affineTransform = new AffineTransform();
            affineTransform.scale(1.0, -1.0);
            affineTransform.translate(0.0, -this.dmu.getHeight(null));
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 1);
            BufferedImage bufferedImage = affineTransformOp.filter(this.dmu, null);
            ImageIO.write((RenderedImage)bufferedImage, string, outputStream);
        }
        catch (IOException iOException) {
            ajz.Dm().error((Object)"Erreur ici", (Throwable)iOException);
        }
    }

    private void aLA() {
        afB afB2;
        Graphics2D graphics2D = this.dmu.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        this.cAF.a(0, this.cAE, 0);
        int n2 = this.cAE.aFz();
        lb_0 lb_02 = new lb_0(n2);
        for (int j = 0; j < n2; ++j) {
            afB2 = (GeometryMesh)this.cAE.ma(j);
            ef_1 ef_12 = this.cAE.ln(j);
            String string = ef_12.getFileName();
            int n3 = ej_0.Z(string);
            if ((ef_12 = (ef_1)lb_02.get(n3)) == null) {
                ef_12 = arX.cQT.iE().a((long)n3, string, true);
                ef_12.bh(false);
                lb_02.c(n3, ef_12);
            }
            this.a((GeometryMesh)afB2, ef_12, graphics2D);
        }
        ll_0 ll_02 = lb_02.pK();
        while (ll_02.hasNext()) {
            ll_02.fK();
            afB2 = (ef_1)ll_02.value();
            while (afB2.avb() >= 0) {
                afB2.HF();
            }
        }
        graphics2D.dispose();
    }

    private void a(GeometryMesh geometryMesh, ef_1 ef_12, Graphics2D graphics2D) {
        kf_0 kf_02 = ef_12.lB(0);
        VertexBufferPCT vertexBufferPCT = geometryMesh.ab();
        int n2 = vertexBufferPCT.fq() / 4;
        for (int j = 0; j < n2; ++j) {
            this.a(graphics2D, j, vertexBufferPCT, kf_02);
        }
    }

    private void a(Graphics2D graphics2D, int n2, VertexBufferPCT vertexBufferPCT, kf_0 kf_02) {
        FloatBuffer floatBuffer = vertexBufferPCT.ys();
        FloatBuffer floatBuffer2 = vertexBufferPCT.yu();
        FloatBuffer floatBuffer3 = vertexBufferPCT.yt();
        int n3 = n2 * 4;
        short s = (short)n3;
        short s2 = (short)(n3 + 1);
        short s3 = (short)(n3 + 2);
        float f = floatBuffer.get(s * 2) * this.aaw + (float)this.abn * this.Gv;
        float f2 = floatBuffer.get(s2 * 2) * this.aaw + (float)this.abn * this.Gv;
        float f3 = floatBuffer.get(s3 * 2) * this.aaw + (float)this.abn * this.Gv;
        float f4 = floatBuffer.get(s * 2 + 1) * this.aaw + (float)this.abo * this.Gw;
        float f5 = floatBuffer.get(s2 * 2 + 1) * this.aaw + (float)this.abo * this.Gw;
        float f6 = floatBuffer.get(s3 * 2 + 1) * this.aaw + (float)this.abo * this.Gw;
        float f7 = floatBuffer2.get(s * 2);
        float f8 = floatBuffer2.get(s3 * 2);
        float f9 = floatBuffer2.get(s * 2 + 1);
        float f10 = floatBuffer2.get(s2 * 2 + 1);
        float f11 = floatBuffer3.get(s * 4) * 1.25f + 0.5f;
        float f12 = floatBuffer3.get(s * 4 + 1) * 1.25f + 0.5f;
        float f13 = floatBuffer3.get(s * 4 + 2) * 1.25f + 0.5f;
        float f14 = floatBuffer3.get(s * 4 + 3);
        agw_1 agw_12 = new agw_1(f - f2, f4 - f5);
        agw_1 agw_13 = new agw_1(f3 - f2, f6 - f5);
        int n4 = Math.round(f9 * (float)kf_02.getHeight());
        int n5 = Math.round(f7 * (float)kf_02.getWidth());
        int n6 = Math.round(f10 * (float)kf_02.getHeight());
        int n7 = Math.round(f8 * (float)kf_02.getWidth());
        kf_0 kf_03 = aon_2.a(kf_02, n6, n5, n4, n7);
        int n8 = kf_03.getWidth();
        int n9 = kf_03.getHeight();
        BufferedImage bufferedImage = js_1.a(n8, n9, kf_03.getData(), wq_0.auA);
        js_1.b(bufferedImage);
        RescaleOp rescaleOp = new RescaleOp(new float[]{f11, f12, f13, f14}, new float[]{0.0f, 0.0f, 0.0f, 0.0f}, null);
        bufferedImage = rescaleOp.filter(bufferedImage, null);
        float f15 = agw_13.Hk / (float)n8;
        float f16 = agw_13.Hl / (float)n8;
        float f17 = agw_12.Hk / (float)n9;
        float f18 = agw_12.Hl / (float)n9;
        AffineTransform affineTransform = new AffineTransform(f15, f16, f17, f18, f2, f5);
        graphics2D.drawImage(bufferedImage, affineTransform, null);
    }
}

