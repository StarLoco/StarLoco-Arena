/*     */ package com.ankamagames.dofusarena.common.constants;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Version
/*     */   extends com.ankamagames.baseImpl.common.clientAndServer.utils.Version
/*     */ {
/*     */   public static final byte MAJOR = 2;
/*     */   public static final short MINOR = 4;
/*  32 */   protected static final Logger m_logger = Logger.getLogger(Version.class);
/*     */   
/*  34 */   private static final Version m_version = new Version();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/*  49 */     Date date = new Date();
/*     */     String dateString;
/*  51 */     try { URL u = ClassLoader.getSystemResource("com/ankamagames/dofusarena/common/constants/build.properties");
/*  52 */       Properties props = new Properties();
/*  53 */       InputStream is = u.openStream();
/*  54 */       props.load(is);
/*  55 */       String version = (String)props.get("build.revision");
/*  56 */       String dateString = (String)props.get("build.date");
/*     */       try {
/*  58 */         is.close();
/*     */       } catch (Exception e) {
/*  60 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/*     */       String version;
/*     */       
/*  66 */       BUILD_VERSION = version;
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/*  63 */       version = "-1";
/*  64 */       dateString = "";
/*     */     }
/*     */     try
/*     */     {
/*  68 */       date = new SimpleDateFormat("E MMMM d h:m:s z yyyy", Locale.ENGLISH).parse(dateString);
/*     */     } catch (ParseException e) {
/*  70 */       m_logger.error("BUILD_DATE invalide : " + dateString, e);
/*  71 */       date = new Date();
/*     */     }
/*  73 */     BUILD_DATE = date;
/*  74 */     INTERNAL_VERSION = new byte[3];
/*  75 */     ByteBuffer bb = ByteBuffer.wrap(INTERNAL_VERSION);
/*  76 */     bb.put((byte)2);
/*  77 */     bb.putShort((short)4); }
/*     */   
/*  79 */   public static final String READABLE_VERSION = String.format("%d.%02d (build %s)", new Object[] { Integer.valueOf(2), Integer.valueOf(4), BUILD_VERSION });
/*  80 */   public static final String READABLE_DATED_VERSION = String.format("%d.%02d (build %s %4$tY-%4$tm-%4$td)", new Object[] { Integer.valueOf(2), Integer.valueOf(4), BUILD_VERSION, BUILD_DATE });
/*     */   
/*     */   public static final String BUILD_VERSION;
/*     */   
/*  84 */   public static final void display() { m_logger.info(READABLE_DATED_VERSION); }
/*     */   
/*     */   public static final Date BUILD_DATE;
/*     */   public static final byte[] INTERNAL_VERSION;
/*     */   protected boolean implCheckVersion(byte[] datas) {
/*  89 */     if (datas == null)
/*  90 */       return false;
/*  91 */     if (datas.length < 4)
/*  92 */       return false;
/*  93 */     ByteBuffer bb = ByteBuffer.wrap(datas);
/*  94 */     if (bb.get() != 2)
/*  95 */       return false;
/*  96 */     if (bb.getShort() != 4) {
/*  97 */       return false;
/*     */     }
/*  99 */     return true;
/*     */   }
/*     */   
/*     */   protected byte[] implGetNeededVersion()
/*     */   {
/* 104 */     return INTERNAL_VERSION;
/*     */   }
/*     */   
/*     */   public static String format(byte[] datas) {
/* 108 */     if ((datas == null) || (datas.length != 3))
/* 109 */       return "";
/* 110 */     StringBuilder sb = new StringBuilder();
/* 111 */     ByteBuffer bb = ByteBuffer.wrap(datas);
/* 112 */     int major = bb.get() & 0xFF;
/* 113 */     int minor = bb.getShort() & 0xFFFF;
/* 114 */     sb.append(major).append('.');
/* 115 */     if (minor < 10) sb.append('0');
/* 116 */     sb.append(minor);
/* 117 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\constants\Version.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */