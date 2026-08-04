/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class aAN
implements anf {
    protected static final Logger a = Logger.getLogger(aAN.class);
    private DocumentBuilder dpZ;
    private Document dqa;
    private String dqb = null;
    private static final aAN dqc = new aAN();

    public static aAN aMW() {
        return dqc;
    }

    public aAN() {
        try {
            this.dpZ = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (ParserConfigurationException parserConfigurationException) {
            a.error((Object)"Exception", (Throwable)parserConfigurationException);
        }
    }

    public void iJ(String string) {
        try {
            this.q(new BufferedInputStream(new URL(string).openStream()));
        }
        catch (Exception exception) {
            File file = new File(string);
            if (file.exists()) {
                try {
                    this.dqa = this.dpZ.parse(file);
                }
                catch (SAXException sAXException) {
                    this.dpZ = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                    throw sAXException;
                }
                if (this.dqa == null) {
                    throw new Exception("Impossible de parser le document " + string);
                }
            }
            this.dqa = null;
            throw new FileNotFoundException("Fichier non trouv\u00e9 : " + string);
        }
    }

    public void q(InputStream inputStream) {
        this.dqa = this.dpZ.parse(inputStream);
    }

    public boolean iK(String string) {
        this.dqb = string;
        File file = new File(string);
        return file.createNewFile();
    }

    public void close() {
    }

    public void a(aNe aNe2, tf_2 ... tf_2Array) {
        try {
            Node node;
            if (this.dqa == null) {
                a.error((Object)"read() invoqu\u00e9 sur un document non ouvert ( voir : open() )");
                return;
            }
            aNe2.va();
            for (node = this.dqa.getFirstChild(); node != null && node.getNodeType() != 1; node = node.getNextSibling()) {
            }
            PU pU = this.a(node, tf_2Array);
            if (pU != null) {
                aNe2.a(pU);
            }
            aNe2.vb();
        }
        catch (Exception exception) {
            aNe2.bA("Exception : " + exception.getMessage());
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    private String a(String string, tf_2[] tf_2Array) {
        if (tf_2Array != null && tf_2Array.length > 0) {
            for (tf_2 tf_22 : tf_2Array) {
                String string2 = tf_22.fQ(string);
                if (string2 == null) continue;
                return string2;
            }
        }
        return string;
    }

    private PU a(Node node, tf_2[] tf_2Array) {
        if (node == null) {
            return null;
        }
        String string = this.a(node.getNodeName(), tf_2Array);
        String string2 = this.a(node.getNodeValue(), tf_2Array);
        PU pU = new PU(string, string2);
        NamedNodeMap namedNodeMap = node.getAttributes();
        if (namedNodeMap != null) {
            for (int j = 0; j < namedNodeMap.getLength(); ++j) {
                Node node2 = namedNodeMap.item(j);
                string = this.a(node2.getNodeName(), tf_2Array);
                string2 = this.a(node2.getNodeValue(), tf_2Array);
                pU.c(new zo_2(string, string2));
            }
        }
        for (Node node3 = node.getFirstChild(); node3 != null; node3 = node3.getNextSibling()) {
            pU.a(this.a(node3, tf_2Array));
        }
        return pU;
    }

    public void b(aNe aNe2) {
        this.a(aNe2, true);
    }

    public void a(aNe aNe2, boolean bl2) {
        if (aNe2 == null) {
            return;
        }
        aNe2.vc();
        try {
            if (bl2) {
                this.d(aNe2);
            } else {
                this.c(aNe2);
            }
        }
        catch (TransformerException transformerException) {
            a.error((Object)"Probleme pendant la sauvegarde d'un fichier XML.", (Throwable)transformerException);
        }
        aNe2.vd();
    }

    private void c(aNe aNe2) {
        Document document = this.dpZ.newDocument();
        Node node = this.a(aNe2.aXo(), document);
        document.appendChild(node);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource dOMSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(this.dqb));
        transformer.transform(dOMSource, streamResult);
    }

    private Node a(k_0 k_02, Document document) {
        Node node;
        Node node2;
        if (k_02 == null) {
            return null;
        }
        String string = k_02.getName();
        String string2 = k_02.getStringValue();
        if (string.equals("#text")) {
            node2 = document.createTextNode(string2);
        } else if (string.equals("#comment")) {
            node2 = document.createComment(string2);
        } else if (string.equals("#cdata-section")) {
            node2 = document.createCDATASection(string2);
        } else {
            node2 = document.createElement(string);
            for (k_0 k_03 : k_02.al()) {
                node = document.createAttribute(k_03.getName());
                node.setValue(k_03.getStringValue());
                ((Element)node2).setAttributeNode((Attr)node);
            }
        }
        for (k_0 k_03 : k_02.getChildren()) {
            node = this.a(k_03, document);
            node2.appendChild(node);
        }
        return node2;
    }

    private void d(aNe aNe2) {
        PU pU = aNe2.aXo();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(this.dqb));
            this.a(fileOutputStream, "UTF-8");
            if (pU != null) {
                this.a(pU, fileOutputStream, "UTF-8", 0);
            }
            fileOutputStream.close();
        }
        catch (Exception exception) {
            aNe2.bA("Exception : " + exception.getMessage());
            a.error((Object)"Exception", (Throwable)exception);
        }
    }

    private void a(FileOutputStream fileOutputStream, String string) {
        fileOutputStream.write(("<?xml version=\"1.0\" encoding=\"" + string + "\"?>\r\n").getBytes(string));
    }

    private void a(k_0 k_02, FileOutputStream fileOutputStream, String string, int n2) {
        if (k_02 == null) {
            return;
        }
        this.t(k_02);
        String string2 = k_02.getName();
        String string3 = k_02.getStringValue();
        StringBuffer stringBuffer = new StringBuffer();
        for (int j = 0; j < n2; ++j) {
            stringBuffer.append("\t");
        }
        String string4 = stringBuffer.toString();
        if (string2.equals("#text")) {
            fileOutputStream.write(string4.getBytes(string));
            fileOutputStream.write(string3.getBytes(string));
            fileOutputStream.write("\r".getBytes(string));
        } else if (string2.equals("#comment")) {
            fileOutputStream.write(string4.getBytes(string));
            fileOutputStream.write("<!--".getBytes(string));
            fileOutputStream.write(string3.getBytes(string));
            fileOutputStream.write("-->".getBytes(string));
        } else if (string2.equals("#cdata-section")) {
            fileOutputStream.write(string4.getBytes(string));
            fileOutputStream.write("<![CDATA[".getBytes(string));
            fileOutputStream.write(string3.getBytes(string));
            fileOutputStream.write("]]>\n".getBytes(string));
        } else {
            boolean bl2;
            fileOutputStream.write(string4.getBytes(string));
            fileOutputStream.write("<".getBytes(string));
            fileOutputStream.write(string2.getBytes(string));
            this.a(k_02, fileOutputStream, string);
            boolean bl3 = !k_02.getChildren().isEmpty();
            boolean bl4 = bl2 = k_02.getStringValue() != null && !k_02.getStringValue().equals("");
            if (!bl3 && !bl2) {
                fileOutputStream.write("/".getBytes(string));
            }
            if (!bl2) {
                fileOutputStream.write(">\r\n".getBytes(string));
            } else {
                fileOutputStream.write("> ".getBytes(string));
            }
            for (k_0 k_03 : k_02.getChildren()) {
                this.a(k_03, fileOutputStream, string, n2 + 1);
            }
            if (k_02.getStringValue() != null) {
                fileOutputStream.write(k_02.getStringValue().trim().getBytes(string));
            }
            if (bl3) {
                fileOutputStream.write(string4.getBytes(string));
            }
            if (bl3 || bl2) {
                fileOutputStream.write("</".getBytes(string));
                fileOutputStream.write(string2.getBytes(string));
                fileOutputStream.write(">\r\n".getBytes(string));
            }
        }
    }

    private void t(k_0 k_02) {
        String string = k_02.getName();
        if (string.equals("#text")) {
            k_02.b(k_02.getStringValue().replaceAll("[\n\t]", ""));
        }
    }

    private void a(k_0 k_02, FileOutputStream fileOutputStream, String string) {
        if (k_02 == null) {
            return;
        }
        for (k_0 k_03 : k_02.al()) {
            String string2 = k_03.getName();
            String string3 = k_03.getStringValue();
            fileOutputStream.write(" ".getBytes(string));
            fileOutputStream.write(string2.getBytes(string));
            fileOutputStream.write("=\"".getBytes(string));
            fileOutputStream.write(string3 != null ? string3.getBytes(string) : "".getBytes());
            fileOutputStream.write("\"".getBytes(string));
        }
    }

    public aNe aMX() {
        return new aNe();
    }

    public void a(aNe aNe2, String string) {
        if (aNe2 == null) {
            return;
        }
        PU pU = aNe2.aXo();
        aNe2.vc();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(this.dqb));
            this.a(fileOutputStream, "UTF-8");
            fileOutputStream.write(string.getBytes("UTF-8"));
            if (pU != null) {
                this.a(pU, fileOutputStream, "UTF-8", 0);
            }
            fileOutputStream.close();
        }
        catch (Exception exception) {
            aNe2.bA("Exception : " + exception.getMessage());
            a.error((Object)"Exception", (Throwable)exception);
            return;
        }
        aNe2.vd();
    }
}

