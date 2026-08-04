/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/*
 * Renamed from aDj
 */
public class adj_2
extends MouseAdapter
implements MouseMotionListener {
    private final JFrame azI;
    private Component source;
    private Point azM;
    private Point dwO;
    private aun[] dwP;

    public adj_2(JFrame jFrame, Component ... componentArray) {
        this.azI = jFrame;
        this.b(componentArray);
    }

    public void a(Component ... componentArray) {
        for (Component component : componentArray) {
            component.removeMouseListener(this);
        }
    }

    public void b(Component ... componentArray) {
        for (Component component : componentArray) {
            component.addMouseListener(this);
        }
    }

    public void mousePressed(MouseEvent mouseEvent) {
        this.l(mouseEvent);
    }

    private void l(MouseEvent mouseEvent) {
        if (this.azI.getExtendedState() == 6) {
            return;
        }
        this.source = mouseEvent.getComponent();
        this.source.addMouseMotionListener(this);
        GraphicsDevice[] graphicsDeviceArray = hs_1.wQ.getScreenDevices();
        this.dwP = new aun[graphicsDeviceArray.length];
        for (int j = 0; j < graphicsDeviceArray.length; ++j) {
            GraphicsDevice graphicsDevice = graphicsDeviceArray[j];
            GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();
            Rectangle rectangle = graphicsConfiguration.getBounds();
            Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(graphicsConfiguration);
            aun aun2 = new aun(this, null);
            aun2.left = (int)rectangle.getMinX();
            aun2.right = (int)rectangle.getMaxX();
            aun2.minY = (int)rectangle.getMinY() + insets.top;
            aun2.cWa = (int)rectangle.getMaxY() - insets.bottom;
            this.dwP[j] = aun2;
        }
        this.azM = new Point(mouseEvent.getPoint());
        SwingUtilities.convertPointToScreen(this.azM, this.source);
        this.dwO = this.azI.getLocation();
    }

    public void mouseDragged(MouseEvent mouseEvent) {
        if (this.source == null || this.dwP == null) {
            return;
        }
        Point point = MouseInfo.getPointerInfo().getLocation();
        int n2 = point.x - this.azM.x;
        int n3 = point.y - this.azM.y;
        int n4 = this.dwO.x + n2;
        int n5 = this.dwO.y + n3;
        for (int j = 0; j < this.dwP.length; ++j) {
            aun aun2 = this.dwP[j];
            if (point.x < aun2.left || point.x >= aun2.right) continue;
            n5 = Math.max(Math.min(n5, aun2.cWa), aun2.minY);
        }
        this.azI.setLocation(n4, n5);
    }

    public void mouseMoved(MouseEvent mouseEvent) {
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        if (this.source != null) {
            this.source.removeMouseMotionListener(this);
            this.source = null;
        }
        this.dwP = null;
    }
}

