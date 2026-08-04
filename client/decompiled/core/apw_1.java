/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;

/*
 * Renamed from apw
 */
public class apw_1 {
    private static final apw_1 cMi = new apw_1();
    private final EnumMap cMj = new EnumMap(xy_0.class);
    private xy_0 aI;
    private boolean cMk = false;

    private apw_1() {
    }

    public static apw_1 aDr() {
        return cMi;
    }

    public xy_0 aDs() {
        return this.aI;
    }

    public void a(xy_0 xy_02, boolean bl2) {
        ass_0 ass_02;
        if ((!this.cMk || bl2) && this.aI != xy_02 && (ass_02 = (ass_0)this.cMj.get((Object)xy_02)) != null) {
            this.aDt();
            ass_02.show();
            this.aI = xy_02;
        }
        if (bl2) {
            this.cMk = true;
        }
    }

    public void unlock() {
        this.cMk = false;
        if (this.aI == xy_0.bYl) {
            return;
        }
        this.aDt();
        this.aI = xy_0.bYl;
        ((ass_0)this.cMj.get((Object)this.aI)).show();
    }

    private void aDt() {
        ass_0 ass_02 = (ass_0)this.cMj.get((Object)this.aI);
        if (ass_02 != null) {
            ass_02.hide();
        }
    }

    public void a(xy_0 xy_02) {
        this.a(xy_02, false);
    }

    public void a(xy_0 xy_02, int n2, int n3, BufferedImage bufferedImage) {
        if (xy_02 != null && bufferedImage != null) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension dimension = toolkit.getBestCursorSize(bufferedImage.getWidth(), bufferedImage.getHeight());
            float f = (float)dimension.width / (float)bufferedImage.getWidth();
            float f2 = (float)dimension.height / (float)bufferedImage.getHeight();
            n2 = (int)((float)n2 * f);
            n3 = (int)((float)n3 * f2);
            this.cMj.put(xy_02, new kM(toolkit.createCustomCursor(bufferedImage, new Point(n2, n3), null)));
            if (xy_02.equals((Object)xy_0.bYl)) {
                this.a(xy_02);
            }
        }
    }

    public void b(xy_0 xy_02, int n2, int n3, int n4, ArrayList arrayList) {
        if (arrayList == null) {
            return;
        }
        int n5 = arrayList.size();
        if (xy_02 != null && n5 > 0) {
            if (n5 == 1) {
                this.a(xy_02, n2, n3, (BufferedImage)arrayList.get(0));
                return;
            }
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            BufferedImage bufferedImage = (BufferedImage)arrayList.get(0);
            Dimension dimension = toolkit.getBestCursorSize(bufferedImage.getWidth(), bufferedImage.getHeight());
            float f = (float)dimension.width / (float)bufferedImage.getWidth();
            float f2 = (float)dimension.height / (float)bufferedImage.getHeight();
            n2 = (int)((float)n2 * f);
            n3 = (int)((float)n3 * f2);
            Cursor[] cursorArray = new Cursor[n5];
            for (int j = 0; j < n5; ++j) {
                cursorArray[j] = toolkit.createCustomCursor((Image)arrayList.get(j), new Point(n2, n3), null);
            }
            this.cMj.put(xy_02, new aem_1(cursorArray, n4));
            if (xy_02.equals((Object)xy_0.bYl)) {
                this.a(xy_02);
            }
        }
    }
}

