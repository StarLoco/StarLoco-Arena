/*     */ package com.ankamagames.framework.kernel.core.translator;
/*     */ 
/*     */ import com.ankamagames.framework.kernel.utils.StringFormatter;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.TimeZone;
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
/*     */ public abstract class Translator
/*     */ {
/*  28 */   private static Logger m_logger = Logger.getLogger(Translator.class);
/*     */   
/*     */   private Language m_language;
/*     */   
/*     */   private String m_path;
/*     */   
/*     */   private ResourceBundle m_bundle;
/*     */   
/*     */ 
/*     */   public Translator()
/*     */   {
/*  39 */     this.m_language = getDefaultLanguage();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLanguage(Language language)
/*     */   {
/*  48 */     this.m_language = language;
/*  49 */     reload();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Language getLanguage()
/*     */   {
/*  56 */     return this.m_language;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setPath(String path)
/*     */   {
/*  63 */     this.m_path = path;
/*  64 */     reload();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getString(String key, Object... args)
/*     */   {
/*  72 */     if (this.m_bundle != null) {
/*  73 */       String format = null;
/*     */       try {
/*  75 */         format = this.m_bundle.getString(key);
/*  76 */         if (args.length != 0) {
/*  77 */           return StringFormatter.format(format, args);
/*     */         }
/*  79 */         return format;
/*     */       }
/*     */       catch (IllegalFormatException e2) {
/*  82 */         return format;
/*     */       } catch (MissingResourceException e1) {
/*  84 */         m_logger.warn("Propriété introuvable dans le Translator key=" + key);
/*     */       }
/*     */     }
/*  87 */     return "!" + key + "!";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String formatDate(Date date, String format)
/*     */   {
/*     */     try
/*     */     {
/*  99 */       SimpleDateFormat dateFormat = new SimpleDateFormat(format);
/* 100 */       dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
/* 101 */       return dateFormat.format(date);
/*     */     } catch (Exception e) {
/* 103 */       m_logger.error("Erreur dans formatDate :", e);
/*     */     }
/* 105 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean containsKey(String key)
/*     */   {
/* 113 */     if (this.m_bundle != null) {
/*     */       try {
/* 115 */         this.m_bundle.getString(key);
/* 116 */         return true;
/*     */       }
/*     */       catch (IllegalFormatException localIllegalFormatException) {}catch (MissingResourceException localMissingResourceException) {}
/*     */     }
/*     */     
/* 121 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Language getDefaultLanguage()
/*     */   {
/* 130 */     String sysLanguage = System.getProperty("user.language");
/* 131 */     Language language = Language.getLanguage(sysLanguage);
/* 132 */     if (language == null) {
/* 133 */       return Language.EN;
/*     */     }
/* 135 */     return language;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean reload()
/*     */   {
/* 146 */     if ((this.m_path != null) && (this.m_language != null)) {
/*     */       try {
/* 148 */         this.m_bundle = ResourceBundle.getBundle(this.m_path, this.m_language.getLocale(), getClass().getClassLoader());
/*     */       } catch (Exception e) {
/* 150 */         e.printStackTrace();
/* 151 */         return false;
/*     */       }
/*     */     }
/* 154 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\translator\Translator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */