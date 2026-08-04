/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.nio.ByteBuffer;
import java.util.List;
import org.apache.log4j.Logger;
import org.jdom.Element;

/*
 * Renamed from QT
 */
public class qt_0 {
    private static final Logger a = Logger.getLogger(qt_0.class);
    protected final aBp bHV;

    public qt_0() {
        this.bHV = new aBp();
    }

    protected qt_0(aBp aBp2) {
        this.bHV = aBp2;
    }

    public final boolean isEmpty() {
        return this.bHV.isEmpty();
    }

    public void a(qt_0 qt_02) {
        qk qk2 = qt_02.bHV.aNm();
        while (qk2.hasNext()) {
            this.bHV.nk(qk2.next());
        }
    }

    public final void add(int n2, int n3) {
        this.bHV.nk(qt_0.aP(n2, n3));
    }

    public final boolean contains(int n2, int n3) {
        return this.bHV.contains(qt_0.aP(n2, n3));
    }

    public final boolean b(qt_0 qt_02) {
        return this.bHV.G(qt_02.bHV.aNn());
    }

    public final boolean aO(int n2, int n3) {
        return this.bHV.remove(qt_0.aP(n2, n3));
    }

    public aBp adv() {
        return this.bHV;
    }

    public Element adw() {
        Element element = new Element("partitions");
        qk qk2 = this.bHV.aNm();
        while (qk2.hasNext()) {
            int n2 = qk2.next();
            short s = ej_0.an(n2);
            short s2 = ej_0.ao(n2);
            Element element2 = new Element("partition");
            element2.setAttribute("x", String.valueOf(s));
            element2.setAttribute("y", String.valueOf(s2));
            element.addContent(element2);
        }
        return element;
    }

    public void a(Element element) {
        assert (element.getName().equals("partitions"));
        List list = element.getChildren("partition");
        for (int j = 0; j < list.size(); ++j) {
            Element element2 = (Element)list.get(j);
            int n2 = Integer.parseInt(element2.getAttributeValue("x"));
            int n3 = Integer.parseInt(element2.getAttributeValue("y"));
            this.add(n2, n3);
        }
        this.bHV.compact();
    }

    public void g(aij_1 aij_12) {
        int n2 = this.bHV.size();
        aij_12.writeInt(n2);
        qk qk2 = this.bHV.aNm();
        for (int j = 0; j < n2; ++j) {
            aij_12.writeInt(qk2.next());
        }
    }

    public void F(ByteBuffer byteBuffer) {
        int n2 = byteBuffer.getInt();
        for (int j = 0; j < n2; ++j) {
            this.bHV.nk(byteBuffer.getInt());
        }
        this.bHV.compact();
    }

    protected static int aP(int n2, int n3) {
        return ej_0.a((short)n2, (short)n3);
    }
}

