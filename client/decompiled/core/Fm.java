/*
 * Decompiled with CFR 0.152.
 */
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

class Fm
extends DefaultHandler {
    private static final String aUz = "commandList";
    private static final String aUA = "commandSet";
    private static final String aUB = "command";
    private static final String aUC = "name";
    private static final String aUD = "cmdPattern";
    private static final String aUE = "argsPattern";
    private static final String aUF = "allowNoArg";
    private static final String aUG = "class";
    private static final String kb = "level";
    private static final String aUH = "autoCompletion";
    private Stack aUI = new Stack();

    public Fm(aiw_2 aiw_22) {
        this.aUI.add(aiw_22);
    }

    public void startElement(String string, String string2, String string3, Attributes attributes) {
        if (!string3.equals(aUz)) {
            boolean bl2;
            String string4 = attributes.getValue(aUC);
            String string5 = attributes.getValue(aUD);
            String string6 = attributes.getValue(aUE);
            boolean bl3 = attributes.getValue(aUF) != null && Boolean.parseBoolean(attributes.getValue(aUF));
            String string7 = attributes.getValue(kb);
            boolean bl4 = bl2 = attributes.getValue(aUH) != null && Boolean.parseBoolean(attributes.getValue(aUH));
            if (string5 == null || string5.length() == 0) {
                aiw_2.a.error((Object)("cmdPattern est invalide pour " + string4 + "!"));
            }
            if (string3.equals(aUA)) {
                aiw_2 aiw_22 = new aiw_2(string5, string6, bl3);
                if (string4 != null) {
                    aiw_22.setName(string4);
                }
                if (string7 != null) {
                    aiw_22.ax(Byte.valueOf(string7));
                }
                if (!this.aUI.isEmpty()) {
                    aiw_2 aiw_23 = (aiw_2)this.aUI.lastElement();
                    aiw_2.a(aiw_22, aiw_23);
                    aiw_23.a((adb_2)aiw_22);
                }
                this.aUI.add(aiw_22);
            } else if (string3.equals(aUB)) {
                String string8 = attributes.getValue(aUG);
                try {
                    MC mC = null;
                    try {
                        mC = (MC)this.getClass().getClassLoader().loadClass(string8).newInstance();
                    }
                    catch (InstantiationException instantiationException) {
                        aiw_2.a.error((Object)instantiationException.getMessage());
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        aiw_2.a.error((Object)illegalAccessException.getMessage());
                    }
                    catch (ClassCastException classCastException) {
                        aiw_2.a.error((Object)classCastException.getMessage());
                    }
                    if (!this.aUI.isEmpty()) {
                        acb_0 acb_02 = new acb_0(string5, string6, mC, bl3);
                        if (string4 != null) {
                            acb_02.setName(string4);
                        }
                        if (string7 != null) {
                            acb_02.ax(Byte.valueOf(string7));
                        }
                        acb_02.eL(bl2);
                        ((aiw_2)this.aUI.lastElement()).a(acb_02);
                    }
                }
                catch (ClassNotFoundException classNotFoundException) {
                    aiw_2.a.error((Object)"ClassNotFound", (Throwable)classNotFoundException);
                }
            }
        }
    }

    public void endElement(String string, String string2, String string3) {
        if (string3.equals(aUA)) {
            this.aUI.pop();
        }
    }

    public void endDocument() {
    }
}

