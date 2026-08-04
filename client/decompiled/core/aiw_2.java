/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.baseImpl.client.proxyclient.base.console.command.NavigateToCommandSetCommand;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;

/*
 * Renamed from aiW
 */
public class aiw_2
extends adb_2 {
    protected static final Logger a = Logger.getLogger(aiw_2.class);
    private aiw_2 czx;
    private ArrayList uA = new ArrayList();

    public aiw_2() {
        this("", "", false);
    }

    public aiw_2(String string, String string2, boolean bl2) {
        super(string, string2, bl2);
    }

    public boolean f(URL uRL) {
        SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser sAXParser = sAXParserFactory.newSAXParser();
            Fm fm = new Fm(this);
            sAXParser.parse(uRL.openStream(), (DefaultHandler)fm);
            return true;
        }
        catch (Exception exception) {
            a.error((Object)"SAX parser error :", (Throwable)exception);
            return false;
        }
    }

    public void y(ArrayList arrayList) {
        for (acb_0 acb_02 : arrayList) {
            this.ayF().a(acb_02);
        }
    }

    public boolean a(String string, byte by) {
        for (adb_2 adb_22 : this.b(string, by)) {
            if (!adb_22.aOF()) continue;
            return true;
        }
        return false;
    }

    public ArrayList getChildren() {
        return this.uA;
    }

    public void a(adb_2 adb_22) {
        this.uA.add(adb_22);
    }

    private void a(aiw_2 aiw_22) {
        this.czx = aiw_22;
    }

    public aiw_2 ayE() {
        return this.czx;
    }

    public boolean isRoot() {
        return this.czx == null;
    }

    public aiw_2 ayF() {
        if (this.isRoot()) {
            return this;
        }
        return this.ayE().ayF();
    }

    public String getPath() {
        StringBuilder stringBuilder = new StringBuilder();
        if (this.czx != null) {
            stringBuilder.append(this.ayE().getPath());
        }
        return stringBuilder.append(this.getName()).append("/").toString();
    }

    public ArrayList b(String string, byte by) {
        ArrayList<adb_2> arrayList = new ArrayList<adb_2>();
        for (adb_2 adb_22 : this.uA) {
            Matcher matcher;
            if (adb_22.BK() > by || !(matcher = adb_22.aOD().matcher(string)).matches()) continue;
            arrayList.add(adb_22);
        }
        return arrayList;
    }

    public ArrayList ayG() {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (adb_2 adb_22 : this.uA) {
            arrayList.add(adb_22.getName());
        }
        return arrayList;
    }

    public MC arn() {
        return new NavigateToCommandSetCommand(this);
    }

    static /* synthetic */ void a(aiw_2 aiw_22, aiw_2 aiw_23) {
        aiw_22.a(aiw_23);
    }
}

