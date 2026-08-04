/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.log4j.Logger
 */
import com.ankamagames.dofusarena.client.DofusArenaClientInstance;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;

public class aOG
implements atG {
    protected static final Logger a = Logger.getLogger(aOG.class);
    private static aOG emF = new aOG();
    private static BufferedImage emG;
    private axq_0 emH;

    public static aOG aYD() {
        return emF;
    }

    public boolean a(pr_0 pr_02) {
        switch (pr_02.getId()) {
            case 16428: {
                apN.aDK().b(this);
                return false;
            }
            case 16389: {
                aEy aEy2 = (aEy)pr_02;
                auY auY2 = aEy2.aPX();
                apN.aDK().b(this);
                add_1.aOG().a(aon_0.aYc().getString("ReportBug.validForm"), 1059L, 102, 1);
                try {
                    String string = "***" + Long.toHexString((long)Math.random()) + "-" + Long.toHexString((long)Math.random()) + "***";
                    StringBuilder stringBuilder = this.lQ(string);
                    this.a(auY2, string, stringBuilder);
                }
                catch (Exception exception) {
                    a.error((Object)("UIReportBugFrame : onMessage - Erreur Connexion URL : " + exception.toString()));
                }
                return false;
            }
        }
        return true;
    }

    private void a(auY auY2, String string, StringBuilder stringBuilder) {
        String string2 = mu_1.rM().getString("bugReportURL");
        string2 = string2 + DofusArenaClientInstance.yl().aod().f(akz_1.cEu);
        string2 = string2 + "/bug-report";
        URL uRL = new URL(string2);
        URLConnection uRLConnection = uRL.openConnection();
        uRLConnection.setDoOutput(true);
        uRLConnection.setDoInput(true);
        uRLConnection.setUseCaches(false);
        uRLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + string);
        DataOutputStream dataOutputStream = new DataOutputStream(uRLConnection.getOutputStream());
        dataOutputStream.writeBytes("--" + string + "\r\nContent-Disposition: form-data; name=\"screenshot\"; filename=\"screenBug.jpg\"\r\nContent-Type: application/octet-stream\r\n\r\n");
        ImageIO.write((RenderedImage)emG, "jpeg", dataOutputStream);
        this.a(auY2, string, stringBuilder, dataOutputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
        String string3 = bufferedReader.readLine();
        if (!"OK".equals(string3)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            if (string3 != null) {
                stringBuilder2.append(string3).append(" \t ");
            }
            while ((string3 = bufferedReader.readLine()) != null) {
                stringBuilder2.append(string3).append(" \t ");
            }
            a.error((Object)("Error while calling BugReport website : " + stringBuilder2.toString()));
        }
    }

    private void a(auY auY2, String string, StringBuilder stringBuilder, DataOutputStream dataOutputStream) {
        dataOutputStream.writeBytes(stringBuilder.toString());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"bug[title]\"\r\n\r\n");
        for (byte by : aey_0.hH(auY2.aHM())) {
            dataOutputStream.write(by);
        }
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"bug[type]\"\r\n\r\n" + auY2.getType());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"bug[seen_comportment]\"\r\n\r\n");
        for (byte by : aey_0.hH(auY2.aHN())) {
            dataOutputStream.write(by);
        }
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"bug[awaited_comportment]\"\r\n\r\n");
        for (byte by : aey_0.hH(auY2.aHO())) {
            dataOutputStream.write(by);
        }
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"bug[way_to_reproduce]\"\r\n\r\n");
        for (byte by : aey_0.hH(auY2.aHP())) {
            dataOutputStream.write(by);
        }
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"config[screen][height]\"\r\n\r\n" + auY2.lb().getHeight());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"config[screen][width]\"\r\n\r\n" + auY2.lb().getWidth());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"config[screen][fullscreen]\"\r\n\r\n" + auY2.aHT());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"config[client_version]\"\r\n\r\n" + auY2.getVersion());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"log\"\r\n\r\n");
        for (byte by : aey_0.hH(auY2.aHX())) {
            dataOutputStream.write(by);
        }
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"replay\"\r\n\r\n");
        for (byte by : aey_0.hH(auY2.aHY())) {
            dataOutputStream.write(by);
        }
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[character][id]\"\r\n\r\n" + auY2.Ke());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[character][name]\"\r\n\r\n" + auY2.uj());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[character][world][x]\"\r\n\r\n" + auY2.aHR());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[character][world][y]\"\r\n\r\n" + auY2.aHS());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[character][world][name]\"\r\n\r\n" + auY2.aHU());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[account][id]\"\r\n\r\n" + auY2.aHV());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[account][name]\"\r\n\r\n" + auY2.aHW());
        dataOutputStream.writeBytes("\r\n--" + string + "\r\nContent-Disposition: form-data; name=\"user[lang]\"\r\n\r\n" + DofusArenaClientInstance.yl().aod().f(akz_1.cEu));
        dataOutputStream.writeBytes("\r\n--" + string + "--\r\n\r\n");
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    private StringBuilder lQ(String string) {
        Object object;
        Object object2;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            object2 = DofusArenaClientInstance.yl().kW();
            object = ((bx_2)object2).cW();
            for (Map.Entry entry : ((HashMap)object).entrySet()) {
                stringBuilder.append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[graphic-device][").append((String)entry.getKey()).append("]\"\r\n\r\n").append((String)entry.getValue());
            }
        }
        catch (Exception exception) {
            a.error((Object)"Impossible d'envoyer la config openGL d'un bug : GL non r\u00e9cup\u00e9rable", (Throwable)exception);
        }
        object2 = ManagementFactory.getOperatingSystemMXBean();
        stringBuilder.append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[OS][arch]\"\r\n\r\n").append(object2.getArch()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[OS][name]\"\r\n\r\n").append(object2.getName()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[OS][version]\"\r\n\r\n").append(object2.getVersion());
        object = ManagementFactory.getMemoryMXBean();
        stringBuilder.append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[memory][total]\"\r\n\r\n").append(Runtime.getRuntime().totalMemory()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[memory][max]\"\r\n\r\n").append(Runtime.getRuntime().maxMemory()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[memory][free]\"\r\n\r\n").append(Runtime.getRuntime().freeMemory());
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long l2 = runtimeMXBean.getUptime();
        if (l2 == 0L) {
            l2 = 1L;
        }
        stringBuilder.append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[VM][uptime]\"\r\n\r\n").append(runtimeMXBean.getUptime()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[VM][name]\"\r\n\r\n").append(runtimeMXBean.getVmName()).append(' ').append(runtimeMXBean.getVmVendor()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[VM][version]\"\r\n\r\n").append(runtimeMXBean.getVmVersion());
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long[] lArray = threadMXBean.getAllThreadIds();
        long l3 = 0L;
        long l4 = 0L;
        for (long l5 : lArray) {
            ThreadInfo threadInfo = threadMXBean.getThreadInfo(l5);
            if (threadInfo == null) continue;
            long l6 = threadMXBean.isThreadCpuTimeSupported() ? threadMXBean.getThreadUserTime(l5) : 0L;
            long l7 = threadMXBean.isThreadCpuTimeSupported() ? threadMXBean.getThreadCpuTime(l5) : -1L;
            l3 += l6;
            l4 += l4;
            stringBuilder.append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[thread-").append(l5).append("][name]\"\r\n\r\n").append(threadInfo.getThreadName()).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[thread-").append(l5).append("][userTime]\"\r\n\r\n").append(l6).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[thread-").append(l5).append("][cpuTime]\"\r\n\r\n").append(l7).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[thread-").append(l5).append("][userPercent]\"\r\n\r\n").append(l6 / (l2 * 10000L)).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[thread-").append(l5).append("][cpuPercent]\"\r\n\r\n").append(l7 / (l2 * 10000L));
        }
        stringBuilder.append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[threads][userTime]\"\r\n\r\n").append(l3).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[threads][cpuTime]\"\r\n\r\n").append(l4).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[threads][userPercent]\"\r\n\r\n").append(l3 / (l2 * 10000L)).append("\r\n--").append(string).append("\r\nContent-Disposition: form-data; name=\"config[threads][cpuPercent]\"\r\n\r\n").append(l4 / (l2 * 10000L));
        return stringBuilder;
    }

    public long getId() {
        return 0L;
    }

    public void c(long l2) {
    }

    public void a(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            this.emH = new ie_2(this);
            add_1.aOG().a(this.emH);
            add_1.aOG().a("reportBugDialog", oh_2.bq("reportBugDialog"), 8450L, (short)30000);
            azs_0.aLV().g("editableBugReport", new auY());
            azs_0.aLV().g("bugTypeSelected", aon_0.aYc().getString("ReportBug.graphismeMapSon"));
            add_1.aOG().l("dofusarena.reportBug", aqv_0.class);
            pg_2 pg_22 = DofusArenaClientInstance.yl().YN().kV();
            try {
                Robot robot = new Robot();
                emG = robot.createScreenCapture(new Rectangle((int)pg_22.getLocation().getX(), (int)pg_22.getLocation().getY(), pg_22.getWidth(), pg_22.getHeight()));
            }
            catch (Exception exception) {
                a.error((Object)("UIReportBugFrame.onMessage - Erreur prise du screenshot : " + exception.toString()));
            }
        }
    }

    public void b(fh_2 fh_22, boolean bl2) {
        if (!bl2) {
            add_1.aOG().b(this.emH);
            add_1.aOG().kO("reportBugDialog");
            azs_0.aLV().kb("editableBugReport");
            azs_0.aLV().kb("bugTypeSelected");
            add_1.aOG().kG("dofusarena.reportBug");
        }
    }
}

