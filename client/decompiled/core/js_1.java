/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.sun.opengl.util.texture.TextureData;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import org.apache.log4j.Logger;

/*
 * Renamed from js
 */
public class js_1 {
    protected static final Logger a = Logger.getLogger(js_1.class);

    public static BufferedImage a(BufferedImage bufferedImage, int n2) {
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth() + n2 * 2, bufferedImage.getHeight() + n2 * 2, 2);
        Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, n2, n2, null);
        graphics.dispose();
        return bufferedImage2;
    }

    public static void b(BufferedImage bufferedImage) {
        if (bufferedImage != null) {
            for (int j = 0; j < bufferedImage.getHeight(); ++j) {
                int n2 = j + bufferedImage.getMinY();
                for (int i2 = 0; i2 < bufferedImage.getWidth(); ++i2) {
                    int n3 = i2 + bufferedImage.getMinX();
                    int n4 = bufferedImage.getRGB(n3, n2);
                    float f = (float)(n4 >> 24 & 0xFF) / 255.0f;
                    if (f == 0.0f) continue;
                    float f2 = (float)(n4 >> 16 & 0xFF) / 255.0f;
                    float f3 = (float)(n4 >> 8 & 0xFF) / 255.0f;
                    float f4 = (float)(n4 & 0xFF) / 255.0f;
                    f2 = f2 / f < 1.0f ? f2 / f : 1.0f;
                    f3 = f3 / f < 1.0f ? f3 / f : 1.0f;
                    f4 = f4 / f < 1.0f ? f4 / f : 1.0f;
                    n4 = (int)(f * 255.0f) << 24 | (int)(f2 * 255.0f) << 16 | (int)(f3 * 255.0f) << 8 | (int)(f4 * 255.0f);
                    bufferedImage.setRGB(n3, n2, n4);
                }
            }
        }
    }

    public static void c(BufferedImage bufferedImage) {
        if (bufferedImage != null) {
            for (int j = 0; j < bufferedImage.getHeight(); ++j) {
                int n2 = j + bufferedImage.getMinY();
                for (int i2 = 0; i2 < bufferedImage.getWidth(); ++i2) {
                    int n3 = i2 + bufferedImage.getMinX();
                    int n4 = bufferedImage.getRGB(n3, n2);
                    float f = (float)(n4 >> 24 & 0xFF) / 255.0f;
                    float f2 = (float)(n4 >> 16 & 0xFF) / 255.0f * f;
                    float f3 = (float)(n4 >> 8 & 0xFF) / 255.0f * f;
                    float f4 = (float)(n4 & 0xFF) / 255.0f * f;
                    bufferedImage.setRGB(n3, n2, (int)(f * 255.0f) << 24 | (int)(f2 * 255.0f) << 16 | (int)(f3 * 255.0f) << 8 | (int)(f4 * 255.0f));
                }
            }
        }
    }

    public static BufferedImage d(BufferedImage bufferedImage) {
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 2);
        Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }

    public static BufferedImage e(BufferedImage bufferedImage) {
        BufferedImage bufferedImage2 = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), 3);
        Graphics graphics = bufferedImage2.getGraphics();
        graphics.drawImage(bufferedImage, 0, 0, null);
        graphics.dispose();
        return bufferedImage2;
    }

    public static BufferedImage e(InputStream inputStream) {
        try {
            TextureData textureData = TextureIO.newTextureData(inputStream, false, "tga");
            int n2 = textureData.getHeight();
            int n3 = textureData.getWidth();
            int n4 = 4 * n3;
            ByteBuffer byteBuffer = ByteBuffer.allocate(n4 * n2);
            ByteBuffer byteBuffer2 = (ByteBuffer)textureData.getBuffer();
            byte[] byArray = new byte[n3 * 4];
            int n5 = n4 * n2;
            for (int j = 0; j < n2; ++j) {
                byteBuffer2.get(byArray);
                byteBuffer.position(n5 -= n4);
                byteBuffer.put(byArray);
            }
            byteBuffer.rewind();
            return js_1.a(n3, n2, byteBuffer.array(), wq_0.auB);
        }
        catch (IOException iOException) {
            a.error((Object)"Exception", (Throwable)iOException);
            return null;
        }
    }

    public static BufferedImage aC(String string) {
        try {
            FileInputStream fileInputStream = new FileInputStream(string);
            return js_1.e(fileInputStream);
        }
        catch (FileNotFoundException fileNotFoundException) {
            a.error((Object)"Exception", (Throwable)fileNotFoundException);
            return null;
        }
    }

    public static BufferedImage a(BufferedImage bufferedImage, boolean bl2) {
        if (bufferedImage == null) {
            return null;
        }
        int n2 = bufferedImage.getWidth();
        int n3 = bufferedImage.getHeight();
        BufferedImage bufferedImage2 = new BufferedImage(n2, n3, 2);
        bufferedImage2.getGraphics().drawImage(bufferedImage, 0, 0, n2, n3, 0, 0, n2, n3, null);
        if (bl2) {
            AffineTransform affineTransform = AffineTransform.getScaleInstance(1.0, -1.0);
            affineTransform.translate(0.0, -bufferedImage.getHeight(null));
            AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, 1);
            bufferedImage2 = affineTransformOp.filter(bufferedImage2, null);
        }
        return bufferedImage2;
    }

    public static agf_0 b(BufferedImage bufferedImage, int n2) {
        int n3;
        int n4;
        int n5;
        int n6 = bufferedImage.getWidth();
        int n7 = bufferedImage.getHeight();
        int n8 = 0;
        int n9 = n6;
        int n10 = 0;
        int n11 = n7;
        block0: for (n5 = 0; n5 < n11; ++n5) {
            for (n4 = 0; n4 < n9; ++n4) {
                n3 = (bufferedImage.getRGB(n4, n5) & 0xFF000000) >> 24 & 0xFF;
                if (n3 <= n2) continue;
                n9 = n4;
                if (n8 != 0) continue block0;
                n8 = n5;
                continue block0;
            }
        }
        block2: for (n5 = n7 - 1; n5 > n8; --n5) {
            for (n4 = n6 - 1; n4 > n10; --n4) {
                n3 = (bufferedImage.getRGB(n4, n5) & 0xFF000000) >> 24 & 0xFF;
                if (n3 <= n2) continue;
                n10 = n4;
                if (n11 != n7) continue block2;
                n11 = n5;
                continue block2;
            }
        }
        if (n9 == n6 || n8 == n7) {
            return new agf_0(0, 0, 0, 0);
        }
        if (n9 >= n10) {
            return new agf_0(0, n6, 0, n7);
        }
        return new agf_0(n9, n10 + 1, n8, n11 + 1);
    }

    public static BufferedImage a(int n2, int n3, byte[] byArray, wq_0 wq_02) {
        if (n2 == 0 || n3 == 0) {
            return null;
        }
        DataBufferByte dataBufferByte = new DataBufferByte(byArray, n2 * n3);
        int n4 = 4;
        int n5 = 4 * n2;
        WritableRaster writableRaster = Raster.createInterleavedRaster(dataBufferByte, n2, n3, n5, n4, wq_02.CI(), null);
        ColorSpace colorSpace = ColorSpace.getInstance(1000);
        boolean bl2 = true;
        boolean bl3 = false;
        int n6 = 3;
        int n7 = 0;
        ComponentColorModel componentColorModel = new ComponentColorModel(colorSpace, bl2, bl3, n6, n7);
        return new BufferedImage(componentColorModel, writableRaster, bl3, null);
    }

    public static void a(BufferedImage bufferedImage, OutputStream outputStream, float f) {
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpeg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(2);
        imageWriteParam.setCompressionQuality(f);
        imageWriter.setOutput(new MemoryCacheImageOutputStream(outputStream));
        imageWriter.write(null, new IIOImage(bufferedImage, null, null), imageWriteParam);
        imageWriter.dispose();
    }
}

