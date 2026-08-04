/*
 * Decompiled with CFR 0.152.
 */
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class UV
extends YN {
    public static final String TAG = "TextEditor";
    protected String abY = null;
    public static final int bRQ = "autoHorizontalScrolled".hashCode();
    public static final int bRR = "maxChars".hashCode();
    public static final int bRS = "maxCharacters".hashCode();
    public static final int bRT = "password".hashCode();
    public static final int bRU = "restrict".hashCode();
    public static final int bRV = "editable".hashCode();
    public static final int bRW = "unicodeRestrict".hashCode();

    public void appendText(String string) {
        if (string == null) {
            string = "";
        }
        if (this.caC != null) {
            this.caC = this.caC + string;
        } else {
            if (this.abY == null) {
                this.abY = "";
            }
            this.abY = this.abY + string;
        }
        this.setNeedsToPreProcess();
    }

    public String getTag() {
        return TAG;
    }

    public void setText(String string) {
        super.setText(string);
        this.abY = null;
    }

    public void setSelectable(boolean bl2) {
        super.setSelectable(true);
    }

    public int getMaxCharacters() {
        return this.getTextBuilder().Fh().getMaxCharacters();
    }

    public void setMaxCharacters(int n2) {
        this.getTextBuilder().Fh().setMaxCharacters(n2);
    }

    public void setMaxChars(int n2) {
        this.setMaxCharacters(n2);
    }

    public int getMaxChars() {
        return this.getMaxCharacters();
    }

    public String getRestrict() {
        return this.getTextBuilder().Fh().getRestrict();
    }

    public void setRestrict(String string) {
        this.getTextBuilder().Fh().setRestrict(string);
    }

    public boolean getUnicodeRestrict() {
        return this.getTextBuilder().Fh().mv();
    }

    public void setUnicodeRestrict(boolean bl2) {
        this.getTextBuilder().Fh().setUnicodeRestrict(bl2);
    }

    public boolean getPassword() {
        return this.getTextBuilder().Fh().mu();
    }

    public void setPassword(boolean bl2) {
        this.getTextBuilder().Fh().setPassword(bl2);
    }

    public boolean getAutoHorizontalScrolled() {
        return this.getTextBuilder().Jb();
    }

    public void setAutoHorizontalScrolled(Boolean bl2) {
        this.getTextBuilder().setAutoHorizontalScrolled(bl2);
    }

    public void setEditable(boolean bl2) {
        this.getTextBuilder().setEditable(bl2);
    }

    public boolean getEditable() {
        return this.getTextBuilder().isEditable();
    }

    public boolean cc(int n2) {
        this.ug();
        return super.cc(n2);
    }

    protected void ug() {
        if (this.abY != null) {
            this.getTextBuilder().aE(this.abY);
            this.abY = null;
        }
    }

    protected void ahX() {
        super.ahX();
        if (lb_2.XL().XM() == this) {
            this.ahY();
        }
    }

    public void yx() {
        super.yx();
        this.setFocusable(true);
    }

    public void b() {
        super.b();
        auL auL2 = auL.checkOut();
        auL2.setWidget(this);
        this.a(auL2);
        this.setTextBuilder(new ch_2(new abn_1()));
        this.getTextBuilder().a(this);
        this.getTextBuilder().setEditable(true);
        this.setCursorType(xy_0.bYo);
        atz_0 atz_02 = new atz_0(this);
        this.caA.a(atz_02);
        this.caA.b(atz_02);
    }

    private void ahY() {
        jz jz2 = this.getTextBuilder().Fh();
        if (jz2.isEmpty()) {
            this.getTextBuilder().mq();
        }
        if (!jz2.mG()) {
            if (this.isSelectOnFocus()) {
                this.selectAll();
            } else {
                yb_0 yb_02 = jz2.mx();
                this.getTextBuilder().d(yb_02, yb_02.Fj());
                this.getTextBuilder().Je();
            }
        }
    }

    protected void a(Vz vz) {
        super.a(vz);
        if (vz.air()) {
            this.ahY();
        }
    }

    protected boolean a(aqG aqG2) {
        int n2 = aqG2.getModifiers();
        if ((n2 & 0x80) == 0 && (n2 & 0x200) == 0 && (n2 & 0x2000) == 0 && (n2 & 0x100) == 0 && !Character.isIdentifierIgnorable(aqG2.getKeyChar())) {
            ago_2.getInstance().setKeyEventConsumed(true);
        }
        return super.a(aqG2);
    }

    protected boolean b(aqG aqG2) {
        int n2;
        if (super.b(aqG2)) {
            switch (aqG2.getKeyCode()) {
                case 127: {
                    ago_2.getInstance().setKeyEventConsumed(true);
                    return false;
                }
                case 86: {
                    if ((aqG2.getModifiers() & 0x80) != 128) break;
                    Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
                    try {
                        String string;
                        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor) && (string = (String)transferable.getTransferData(DataFlavor.stringFlavor)) != null && string.length() != 0) {
                            this.getTextBuilder().dy(string);
                        }
                    }
                    catch (UnsupportedFlavorException unsupportedFlavorException) {
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    ago_2.getInstance().setKeyEventConsumed(true);
                    return false;
                }
                case 88: {
                    if ((aqG2.getModifiers() & 0x80) != 128) break;
                    if (!this.getTextBuilder().Fh().mu()) {
                        this.amM();
                        this.getTextBuilder().dy("");
                    }
                    ago_2.getInstance().setKeyEventConsumed(true);
                    return false;
                }
                case 65: {
                    if ((aqG2.getModifiers() & 0x80) != 128) break;
                    this.selectAll();
                    ago_2.getInstance().setKeyEventConsumed(true);
                    return false;
                }
            }
        }
        if (((n2 = aqG2.getModifiers()) & 0x80) == 0 && (n2 & 0x200) == 0 && (n2 & 0x2000) == 0 && (n2 & 0x100) == 0 && !Character.isIdentifierIgnorable(aqG2.getKeyChar())) {
            ago_2.getInstance().setKeyEventConsumed(true);
        }
        return true;
    }

    protected boolean c(aqG aqG2) {
        if (super.c(aqG2) && this.getTextBuilder().isEditable()) {
            switch (aqG2.getKeyChar()) {
                case '\b': {
                    this.getTextBuilder().mM();
                    return false;
                }
                case '\u007f': {
                    this.getTextBuilder().mN();
                    ago_2.getInstance().setKeyEventConsumed(true);
                    return false;
                }
                case '\t': {
                    return false;
                }
                case '\n': {
                    if (!this.getMultiline()) break;
                }
                default: {
                    this.getTextBuilder().dy(String.valueOf(aqG2.getKeyChar()));
                    return false;
                }
            }
        }
        return true;
    }

    public void a(air_1 air_12) {
        UV uV = (UV)air_12;
        super.a((air_1)uV);
        uV.setMaxCharacters(this.getMaxCharacters());
        uV.setPassword(this.getPassword());
        uV.setAutoHorizontalScrolled(this.getAutoHorizontalScrolled());
        uV.setRestrict(this.getRestrict());
        uV.setUnicodeRestrict(this.getUnicodeRestrict());
        if (this.abY != null) {
            uV.abY = this.abY;
        }
    }

    public boolean setXMLAttribute(int n2, String string, if_1 if_12) {
        if (n2 == bRQ) {
            this.setAutoHorizontalScrolled(Gr.getBoolean(string));
        } else if (n2 == bRS || n2 == bRR) {
            this.setMaxCharacters(Gr.R(string));
        } else if (n2 == bRT) {
            this.setPassword(Gr.getBoolean(string));
        } else if (n2 == bRU) {
            this.setRestrict(if_12.eM(string));
        } else if (n2 == bRV) {
            this.setEditable(Gr.getBoolean(string));
        } else if (n2 == bRW) {
            this.setUnicodeRestrict(Gr.getBoolean(string));
        } else {
            return super.setXMLAttribute(n2, string, if_12);
        }
        return true;
    }

    public boolean setPropertyAttribute(int n2, Object object) {
        if (n2 == bRQ) {
            this.setAutoHorizontalScrolled(Gr.getBoolean(object));
        } else if (n2 == bRS || n2 == bRR) {
            this.setMaxCharacters(Gr.R(object));
        } else if (n2 == bRT) {
            this.setPassword(Gr.getBoolean(object));
        } else if (n2 == bRU) {
            this.setRestrict((String)object);
        } else if (n2 == bRV) {
            this.setEditable(Gr.getBoolean(object));
        } else if (n2 == bRW) {
            this.setUnicodeRestrict(Gr.getBoolean(object));
        } else {
            return super.setPropertyAttribute(n2, object);
        }
        return true;
    }

    public boolean a(int n2, String string, if_1 if_12) {
        if (n2 != caS) {
            return super.a(n2, string, if_12);
        }
        this.appendText(if_12.eM(string));
        return true;
    }

    public boolean d(int n2, Object object) {
        if (n2 != caS) {
            return super.d(n2, object);
        }
        this.appendText(String.valueOf(object));
        return true;
    }
}

