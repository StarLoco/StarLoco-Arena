/*
 * Decompiled with CFR 0.152.
 */
import java.util.Hashtable;
import org.xml.sax.Attributes;
import org.xml.sax.SAXParseException;

public class aOH
extends hd_0 {
    public void b(String string, String string2, String string3, Attributes attributes, KN kN) {
        Object object;
        String string4;
        Object object2 = null;
        String string5 = "";
        UI uI = kN.TP();
        id_2 id_22 = new id_2();
        id_22.l(uI);
        id_22.a(new axc_0(kN.getLocator()));
        kN.b(id_22);
        for (int j = 0; j < attributes.getLength(); ++j) {
            String string6 = attributes.getURI(j);
            if (string6 != null && !string6.equals("") && !string6.equals(string)) continue;
            string4 = attributes.getLocalName(j);
            object = attributes.getValue(j);
            if (string4.equals("name")) {
                object2 = object;
                if (!"".equals(object2)) continue;
                throw new eq_2("name attribute must not be empty");
            }
            if (string4.equals("depends")) {
                string5 = object;
                continue;
            }
            if (string4.equals("if")) {
                id_22.w((String)object);
                continue;
            }
            if (string4.equals("unless")) {
                id_22.x((String)object);
                continue;
            }
            if (string4.equals("id")) {
                if (object == null || ((String)object).equals("")) continue;
                kN.TP().o((String)object, id_22);
                continue;
            }
            if (string4.equals("description")) {
                id_22.setDescription((String)object);
                continue;
            }
            throw new SAXParseException("Unexpected attribute \"" + string4 + "\"", kN.getLocator());
        }
        if (object2 == null) {
            throw new SAXParseException("target element appears without a name attribute", kN.getLocator());
        }
        if (kN.Xl().get(object2) != null) {
            throw new eq_2("Duplicate target '" + object2 + "'", id_22.hW());
        }
        Hashtable hashtable = uI.ahn();
        boolean bl2 = false;
        if (hashtable.containsKey(object2)) {
            uI.l("Already defined in main or a previous import, ignore " + (String)object2, 3);
        } else {
            id_22.setName((String)object2);
            kN.Xl().put(object2, id_22);
            uI.b((String)object2, id_22);
            bl2 = true;
        }
        if (string5.length() > 0) {
            id_22.eE(string5);
        }
        if (kN.Xk() && kN.Xc() != null && kN.Xc().length() != 0) {
            string4 = kN.Xc() + "." + (String)object2;
            object = bl2 ? new id_2(id_22) : id_22;
            ((id_2)object).setName(string4);
            kN.Xl().put(string4, object);
            uI.b(string4, (id_2)object);
        }
    }

    public hd_0 a(String string, String string2, String string3, Attributes attributes, KN kN) {
        return amj_2.aBG();
    }

    public void a(String string, String string2, KN kN) {
        kN.c(kN.Xi());
    }
}

