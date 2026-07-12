/*     */ package com.ankamagames.framework.kernel;
/*     */ 
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.interfaces.RSAPrivateKey;
/*     */ import javax.crypto.Cipher;
/*     */ import org.apache.log4j.Logger;
/*     */ import sun.misc.BASE64Encoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ServerCipher
/*     */ {
/*  34 */   public static final Logger m_logger = Logger.getLogger(ServerCipher.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Certificate m_cert;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RSAPrivateKey m_pkey;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cipher m_encoder;
/*     */ 
/*     */ 
/*     */   
/*     */   protected Cipher m_decoder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ServerCipher(String keyStoreFileName, String storeType, String alias, String password) throws Exception {
/*     */     try {
/*  59 */       m_logger.info("Chargement des clefs de cryptage");
/*  60 */       KeyStore ks = KeyStore.getInstance(storeType);
/*     */       
/*  62 */       FileInputStream file = new FileInputStream(keyStoreFileName);
/*  63 */       ks.load(file, password.toCharArray());
/*  64 */       file.close();
/*     */       
/*  66 */       this.m_cert = ks.getCertificate(alias);
/*  67 */       this.m_pkey = (RSAPrivateKey)ks.getKey(alias, password.toCharArray());
/*     */       
/*  69 */       m_logger.info("Création des 'cipher's");
/*     */ 
/*     */ 
/*     */       
/*  73 */       this.m_decoder = Cipher.getInstance("RSA");
/*  74 */       this.m_encoder = Cipher.getInstance("RSA");
/*     */       
/*  76 */       m_logger.info("Initialisation des 'cipher's");
/*     */       
/*  78 */       this.m_decoder.init(2, this.m_pkey);
/*  79 */       this.m_encoder.init(1, this.m_cert);
/*     */     }
/*  81 */     catch (Exception ex) {
/*  82 */       ex.printStackTrace();
/*  83 */       System.err.println("Oublié de rajouter security.provider.7=org.bouncycastle.jce.provider.BouncyCastleProvider dans le fichier lib/security/java.security ?");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] crypt(byte[] source) {
/*  94 */     if (this.m_encoder != null) {
/*     */       try {
/*  96 */         return this.m_encoder.doFinal(source);
/*  97 */       } catch (Exception e) {
/*  98 */         m_logger.error("Impossible de crypter les données, raison : " + e.getMessage());
/*  99 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 103 */     m_logger.error("Impossible de crypter les données, raison : encoder = null");
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] decrypt(byte[] source) {
/* 114 */     if (this.m_decoder != null) {
/*     */       try {
/* 116 */         return this.m_decoder.doFinal(source);
/* 117 */       } catch (Exception e) {
/* 118 */         m_logger.error("Impossible de décrypter les données, raison : " + e.getMessage());
/* 119 */         return null;
/*     */       } 
/*     */     }
/*     */     
/* 123 */     m_logger.error("Impossible de décrypter les données, raison : decoder = null");
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportPrivateKey(String fileName) {
/* 134 */     if (this.m_pkey != null) {
/* 135 */       BASE64Encoder b64 = new BASE64Encoder();
/* 136 */       String key64 = b64.encode(this.m_pkey.getEncoded());
/*     */       
/*     */       try {
/* 139 */         m_logger.info("Export de la clef privée vers " + fileName);
/* 140 */         FileOutputStream pfile = new FileOutputStream(fileName);
/* 141 */         PrintStream prn = new PrintStream(pfile);
/*     */         
/* 143 */         prn.println("-----BEGIN PRIVATE KEY-----");
/* 144 */         prn.println(key64);
/* 145 */         prn.println("-----END PRIVATE KEY-----");
/*     */         
/* 147 */         pfile.close();
/* 148 */         prn.close();
/*     */       }
/* 150 */       catch (IOException e) {
/* 151 */         m_logger.error("Impossible d'expoter la clef privée : " + e.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\ServerCipher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */