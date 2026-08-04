/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Component;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * Renamed from xU
 */
public class xu_0
extends MouseAdapter
implements MouseMotionListener {
    private final JFrame azI;
    private Dimension minimumSize;
    private Component source;
    private boolean azJ = false;
    private boolean azK = false;
    private ArrayList azL = new ArrayList();
    private Point azM;
    private Dimension size;

    public xu_0(JFrame jFrame, Component ... componentArray) {
        this.azI = jFrame;
        for (Component component : componentArray) {
            this.a(component);
        }
    }

    public void setMinimumSize(Dimension dimension) {
        this.minimumSize = dimension;
    }

    public void a(JComponent ... jComponentArray) {
        for (JComponent jComponent : jComponentArray) {
            if (this.azL.contains(jComponent)) continue;
            this.azL.add(jComponent);
        }
    }

    public void a(Component ... componentArray) {
        for (Component component : componentArray) {
            component.removeMouseListener(this);
        }
    }

    public void a(Component component) {
        component.addMouseListener(this);
    }

    public void mousePressed(MouseEvent mouseEvent) {
        this.h(mouseEvent);
    }

    private void h(MouseEvent mouseEvent) {
        if (!this.azI.isResizable()) {
            return;
        }
        if (this.azI.getExtendedState() == 6) {
            return;
        }
        this.source = mouseEvent.getComponent();
        this.source.addMouseMotionListener(this);
        this.azM = new Point(mouseEvent.getPoint());
        Point point = SwingUtilities.convertPoint(this.source, this.azM, this.azI);
        this.azJ = point.x >= this.azI.getWidth() - 10;
        this.azK = point.y >= this.azI.getHeight() - 10;
        SwingUtilities.convertPointToScreen(this.azM, this.source);
        this.size = this.azI.getSize();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.source == null) {
            return;
        }
        Point point = MouseInfo.getPointerInfo().getLocation();
        int n2 = this.size.width;
        int n3 = this.size.height;
        if (this.azJ) {
            n2 += point.x - this.azM.x;
        }
        if (this.azK) {
            n3 += point.y - this.azM.y;
        }
        if (this.minimumSize != null) {
            if (n2 < this.minimumSize.width) {
                n2 = this.minimumSize.width;
            }
            if (n3 < this.minimumSize.height) {
                n3 = this.minimumSize.height;
            }
        }
        this.azI.setSize(n2, n3);
        this.azI.setVisible(true);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.source != null) {
            this.source.removeMouseMotionListener(this);
        }
        this.source = null;
    }
}

