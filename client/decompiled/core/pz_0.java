/*
 * Decompiled with CFR 0.152.
 */
import org.xml.sax.Attributes;

/*
 * Renamed from pZ
 */
public class pz_0
extends hd_0 {
    public void b(String string, String string2, String string3, Attributes attributes, KN kN) {
        fy_2 fy_22 = kN.Xd();
        Object object = null;
        if (fy_22 != null) {
            object = fy_22.OV();
        }
        rs_0 rs_02 = new rs_0(string2);
        rs_02.l(kN.TP());
        rs_02.setNamespace(string);
        rs_02.fL(string3);
        rs_02.dE(es_2.s(rs_02.getNamespace(), string2));
        rs_02.cW(string3);
        axc_0 axc_02 = new axc_0(kN.getLocator().getSystemId(), kN.getLocator().getLineNumber(), kN.getLocator().getColumnNumber());
        rs_02.a(axc_02);
        rs_02.a(kN.Xh());
        if (object != null) {
            ((rs_0)object).a(rs_02);
        } else {
            kN.Xh().a(rs_02);
        }
        kN.a(rs_02, attributes);
        fy_2 fy_23 = new fy_2(rs_02, rs_02.LF());
        for (int j = 0; j < attributes.getLength(); ++j) {
            String string4 = attributes.getLocalName(j);
            String string5 = attributes.getURI(j);
            if (string5 != null && !string5.equals("") && !string5.equals(string)) {
                string4 = string5 + ":" + attributes.getQName(j);
            }
            String string6 = attributes.getValue(j);
            if ("ant-type".equals(string4) || "antlib:org.apache.tools.ant".equals(string5) && "ant-type".equals(attributes.getLocalName(j))) {
                kN.TP().l("WARNING: the ant-type mechanism has been deprecated" + ayM.LINE_SEP + "         and" + " will not be available in Ant 1.8.0 or higher", 1);
                string4 = "ant-type";
                int n2 = string6.indexOf(":");
                if (n2 >= 0) {
                    String string7 = string6.substring(0, n2);
                    String string8 = kN.fc(string7);
                    if (string8 == null) {
                        throw new eq_2("Unable to find XML NS prefix \"" + string7 + "\"");
                    }
                    string6 = es_2.s(string8, string6.substring(n2 + 1));
                }
            }
            fy_23.setAttribute(string4, string6);
        }
        if (fy_22 != null) {
            fy_22.b(fy_23);
        }
        kN.e(fy_23);
    }

    public void a(char[] cArray, int n2, int n3, KN kN) {
        fy_2 fy_22 = kN.Xd();
        fy_22.b(cArray, n2, n3);
    }

    public hd_0 a(String string, String string2, String string3, Attributes attributes, KN kN) {
        return amj_2.aBG();
    }

    public void a(String string, String string2, KN kN) {
        kN.Xf();
    }
}

