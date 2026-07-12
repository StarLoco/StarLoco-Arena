/*    */ package com.ankamagames.dofusarena.client;
/*    */ 
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*    */ import com.ankamagames.dofusarena.common.constants.Version;
/*    */ import javax.swing.JOptionPane;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.PropertyConfigurator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DofusArenaClient
/*    */ {
/* 25 */   private static Logger m_logger = Logger.getLogger(DofusArenaClient.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void main(String[] args) {
/* 33 */     PropertyConfigurator.configure(DofusArenaClient.class.getResource("log4j.properties"));
/*    */     
/* 35 */     Version.display();
/*    */     
/* 37 */     boolean configurationLoaded = false; byte b; int i; String[] arrayOfString;
/* 38 */     for (i = (arrayOfString = args).length, b = 0; b < i; ) { String argument = arrayOfString[b];
/*    */       
/* 40 */       if (argument.length() > 1 && argument.charAt(0) == '-') {
/* 41 */         String configFile; char cmd = argument.charAt(1);
/*    */         
/* 43 */         switch (cmd) {
/*    */ 
/*    */           
/*    */           case 'c':
/* 47 */             configFile = argument.substring(2);
/*    */             
/* 49 */             configurationLoaded = DofusArenaConfiguration.getInstance().load(configFile);
/* 50 */             if (!configurationLoaded) {
/* 51 */               DofusArenaClientInstance.getLogger().fatal("Echec du chargement avec le fichier de config " + configFile + ", reprise du fichier par dŽfaut");
/*    */             }
/*    */             break;
/*    */ 
/*    */           
/*    */           case 's':
/* 57 */             DofusArenaConfiguration.getInstance().setStartInOpenGLThread(false);
/*    */             break;
/*    */ 
/*    */           
/*    */           default:
/* 62 */             DofusArenaClientInstance.getLogger().error("argument inconnu : '-" + cmd + "'");
/*    */             break;
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       } 
/*    */       b++; }
/*    */     
/* 72 */     if (configurationLoaded || DofusArenaConfiguration.getInstance().load()) {
/*    */       
/* 74 */       DofusArenaClientInstance client = DofusArenaClientInstance.getInstance();
/*    */       try {
/* 76 */         client.initialize();
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       }
/* 82 */       catch (Exception e) {
/* 83 */         JOptionPane.showMessageDialog(null, e, "Error", 0);
/* 84 */         m_logger.error("Erreur au lancement", e);
/* 85 */         System.exit(0);
/*    */       } 
/*    */     } else {
/* 88 */       DofusArenaClientInstance.getLogger().fatal("Echec du chargement de la configuration, DofusArenaConfiguration introuvable");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\DofusArenaClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */