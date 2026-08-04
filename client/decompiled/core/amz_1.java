/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import org.apache.log4j.Logger;

/*
 * Renamed from amZ
 */
public class amz_1
extends JComponent {
    public static final Logger a = Logger.getLogger(amz_1.class);
    private final Image cIl;

    public amz_1(Image image) {
        this.setBorder(new EmptyBorder(0, 0, 0, 0));
        this.setOpaque(true);
        this.setDoubleBuffered(true);
        this.cIl = image;
    }

    protected void paintComponent(Graphics graphics) {
        graphics.drawImage(this.cIl, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    protected void paintBorder(Graphics graphics) {
    }
}

