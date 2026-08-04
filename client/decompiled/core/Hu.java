/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

class Hu
extends JPanel {
    public static final Logger a = Logger.getLogger(Hu.class);
    private final jL beN;
    private final avk_0 beO;

    Hu(avk_0 avk_02, jL jL2, Dimension dimension) {
        super(new FlowLayout(2, 1, 3));
        this.beO = avk_02;
        this.beN = jL2;
        this.setFocusable(false);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setOpaque(true);
        this.setDoubleBuffered(true);
        JButton jButton = this.a(this.beN.nI(), this.beN.nJ(), this.beN.nM());
        this.add(jButton);
        if (this.beO != null) {
            jButton.addActionListener(new sf_2(this));
        }
        JButton jButton2 = this.a(this.beN.nK(), this.beN.nL(), this.beN.nN());
        this.add(jButton2);
        if (this.beO != null) {
            jButton2.addActionListener(new si_2(this));
        }
        JButton jButton3 = this.a(this.beN.nG(), this.beN.nH(), this.beN.nO());
        this.add(jButton3);
        jButton3.addActionListener(new sh_2(this));
        Container container = new Container();
        container.setSize(new Dimension(2, 1));
        container.setMinimumSize(new Dimension(2, 1));
        container.setMaximumSize(new Dimension(2, 1));
        container.setPreferredSize(new Dimension(2, 1));
        this.add(container);
        if (this.beO != null) {
            this.addMouseListener(new sj_2(this));
        }
    }

    private JButton a(ImageIcon imageIcon, ImageIcon imageIcon2, Dimension dimension) {
        JButton jButton = new JButton();
        jButton.setBorder(BorderFactory.createEmptyBorder());
        jButton.setBorderPainted(false);
        jButton.setFocusable(false);
        jButton.setFocusPainted(false);
        jButton.setIcon(imageIcon);
        jButton.setRolloverIcon(imageIcon2);
        jButton.setMinimumSize(dimension);
        jButton.setSize(dimension);
        jButton.setPreferredSize(dimension);
        jButton.setMaximumSize(dimension);
        return jButton;
    }

    protected void paintComponent(Graphics graphics) {
        int n2 = this.beN.nr().getWidth(null);
        int n3 = this.beN.nv().getWidth(null);
        int n4 = this.getHeight();
        int n5 = this.getWidth();
        graphics.drawImage(this.beN.nr(), 0, 0, n2, n4, null);
        graphics.drawImage(this.beN.nv(), n5 - n3, 0, n3, n4, null);
        graphics.drawImage(this.beN.nt(), n2, 0, n5 - n2 - n3, n4, null);
    }

    static /* synthetic */ avk_0 a(Hu hu) {
        return hu.beO;
    }
}

