/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;

public class jL {
    public static final Logger a = Logger.getLogger(jL.class);
    private static final String BQ = "BorderTopLeft.png";
    private static final String BR = "BorderTop.png";
    private static final String BS = "BorderTopRight.png";
    private static final String BT = "BorderRight.png";
    private static final String BU = "BorderBottomRight.png";
    private static final String BV = "BorderBottom.png";
    private static final String BW = "BorderBottomLeft.png";
    private static final String BX = "BorderLeft.png";
    private static final String BY = "BtnCloseDefault.png";
    private static final String BZ = "BtnCloseOver.png";
    private static final String Ca = "BtnReduceDefault.png";
    private static final String Cb = "BtnReduceOver.png";
    private static final String Cc = "BtnEnlargeDefault.png";
    private static final String Cd = "BtnEnlargeOver.png";
    private final String Ce;
    private final ImageIcon Cf;
    private final Image Cg;
    private final ImageIcon Ch;
    private final Image Ci;
    private final ImageIcon Cj;
    private final Image Ck;
    private final ImageIcon Cl;
    private final Image Cm;
    private final ImageIcon Cn;
    private final Image Co;
    private final ImageIcon Cp;
    private final Image Cq;
    private final ImageIcon Cr;
    private final Image Cs;
    private final ImageIcon Ct;
    private final Image Cu;
    private final ImageIcon Cv;
    private final ImageIcon Cw;
    private final ImageIcon Cx;
    private final ImageIcon Cy;
    private final ImageIcon Cz;
    private final ImageIcon CA;
    private final Insets CB;

    public jL(String string) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        int n10;
        this.Ce = string;
        this.Cf = this.aG(BQ);
        this.Cg = this.Cf.getImage();
        ImageIcon imageIcon = this.aG(BR);
        if (imageIcon.getImageLoadStatus() != 8) {
            BufferedImage bufferedImage = new BufferedImage(1, 18, 1);
            for (n10 = 0; n10 < bufferedImage.getWidth(); ++n10) {
                n9 = bufferedImage.getHeight() - 1;
                bufferedImage.setRGB(n10, 0, 0);
                bufferedImage.setRGB(n10, n9, 0);
                for (n8 = 1; n8 < n9; ++n8) {
                    bufferedImage.setRGB(n10, n8, 255);
                }
            }
            this.Ch = new ImageIcon(bufferedImage);
        } else {
            this.Ch = imageIcon;
        }
        this.Ci = this.Ch.getImage();
        this.Cj = this.aG(BS);
        this.Ck = this.Cj.getImage();
        this.Cl = this.aG(BX);
        this.Cm = this.Cl.getImage();
        this.Cn = this.aG(BW);
        this.Co = this.Cn.getImage();
        this.Cp = this.aG(BV);
        this.Cq = this.Cp.getImage();
        this.Cr = this.aG(BU);
        this.Cs = this.Cr.getImage();
        this.Ct = this.aG(BT);
        this.Cu = this.Ct.getImage();
        this.Cv = this.aG(BY);
        this.Cw = this.aG(BZ);
        this.Cx = this.aG(Ca);
        this.Cy = this.aG(Cb);
        this.Cz = this.aG(Cc);
        this.CA = this.aG(Cd);
        int n11 = this.Cg.getHeight(null);
        n10 = this.Ck.getHeight(null);
        n9 = this.Ci.getHeight(null);
        n8 = Math.max(Math.max(n11, n10), n9);
        if (n11 != n8) {
            a.warn((Object)("Skin pbm : top left image height not consistent : " + n11 + " != " + n8));
        }
        if (n10 != n8) {
            a.warn((Object)("Skin pbm : top right image height not consistent : " + n10 + " != " + n8));
        }
        if (n9 != n8) {
            a.warn((Object)("Skin pbm : top image height not consistent : " + n9 + " != " + n8));
        }
        if ((n7 = this.Cm.getWidth(null)) != (n6 = Math.max(n7, n5 = this.Co.getWidth(null)))) {
            a.warn((Object)("Skin pbm : left image width not consistent : " + n7 + " != " + n6));
        }
        if (n5 != n6) {
            a.warn((Object)("Skin pbm : bottom left image width not consistent : " + n5 + " != " + n6));
        }
        int n12 = this.Co.getHeight(null);
        int n13 = this.Cq.getHeight(null);
        int n14 = this.Cs.getHeight(null);
        int n15 = Math.max(Math.max(n12, n13), n14);
        if (n12 != n15) {
            a.warn((Object)("Skin pbm : bottom left image height not consistent : " + n12 + " != " + n15));
        }
        if (n13 != n15) {
            a.warn((Object)("Skin pbm : bottom image height not consistent : " + n13 + " != " + n15));
        }
        if (n14 != n15) {
            a.warn((Object)("Skin pbm : bottom right image height not consistent : " + n14 + " != " + n15));
        }
        if ((n4 = this.Cu.getWidth(null)) != (n3 = Math.max(n4, n2 = this.Cs.getWidth(null)))) {
            a.warn((Object)("Skin pbm : right image width not consistent : " + n4 + " != " + n3));
        }
        if (n2 != n3) {
            a.warn((Object)("Skin pbm : bottom right image width not consistent : " + n2 + " != " + n3));
        }
        this.CB = new Insets(n8, n6, n15, n3);
    }

    private ImageIcon aG(String string) {
        ImageIcon imageIcon;
        try {
            URL uRL = new URL(this.Ce + string);
            imageIcon = new ImageIcon(uRL);
        }
        catch (MalformedURLException malformedURLException) {
            imageIcon = new ImageIcon(this.Ce + string);
        }
        if (imageIcon.getImageLoadStatus() != 8) {
            a.error((Object)("Skin image not present : " + this.Ce + string));
        }
        return imageIcon;
    }

    public ImageIcon nq() {
        return this.Cf;
    }

    public Image nr() {
        return this.Cg;
    }

    public ImageIcon ns() {
        return this.Ch;
    }

    public Image nt() {
        return this.Ci;
    }

    public ImageIcon nu() {
        return this.Cj;
    }

    public Image nv() {
        return this.Ck;
    }

    public ImageIcon nw() {
        return this.Cl;
    }

    public Image nx() {
        return this.Cm;
    }

    public ImageIcon ny() {
        return this.Cn;
    }

    public Image nz() {
        return this.Co;
    }

    public ImageIcon nA() {
        return this.Cp;
    }

    public Image nB() {
        return this.Cq;
    }

    public ImageIcon nC() {
        return this.Cr;
    }

    public Image nD() {
        return this.Cs;
    }

    public ImageIcon nE() {
        return this.Ct;
    }

    public Image nF() {
        return this.Cu;
    }

    public ImageIcon nG() {
        return this.Cv;
    }

    public ImageIcon nH() {
        return this.Cw;
    }

    public ImageIcon nI() {
        return this.Cx;
    }

    public ImageIcon nJ() {
        return this.Cy;
    }

    public ImageIcon nK() {
        return this.Cz;
    }

    public ImageIcon nL() {
        return this.CA;
    }

    public Insets getBorderInsets() {
        return this.CB;
    }

    public Dimension nM() {
        Image image = this.Cx.getImage();
        Image image2 = this.Cy.getImage();
        return new Dimension(Math.max(image.getWidth(null), image2.getWidth(null)), Math.max(image.getHeight(null), image2.getHeight(null)));
    }

    public Dimension nN() {
        Image image = this.Cz.getImage();
        Image image2 = this.CA.getImage();
        return new Dimension(Math.max(image.getWidth(null), image2.getWidth(null)), Math.max(image.getHeight(null), image2.getHeight(null)));
    }

    public Dimension nO() {
        Image image = this.Cv.getImage();
        Image image2 = this.Cw.getImage();
        return new Dimension(Math.max(image.getWidth(null), image2.getWidth(null)), Math.max(image.getHeight(null), image2.getHeight(null)));
    }
}

