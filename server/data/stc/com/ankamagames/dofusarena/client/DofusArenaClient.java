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
/*    */   public static void main(String[] args)
/*    */   {
/* 33 */     PropertyConfigurator.configure(DofusArenaClient.class.getResource("log4j.properties"));
/*    */     
/* 35 */     Version.display();
/*    */     
/* 37 */     boolean configurationLoaded = false;
/* 38 */     String[] arrayOfString = args;int j = args.length; for (int i = 0; i < j; i++) { String argument = arrayOfString[i];
/*    */       
/* 40 */       if ((argument.length() > 1) && (argument.charAt(0) == '-')) {
/* 41 */         char cmd = argument.charAt(1);
/*    */         
/* 43 */         switch (cmd)
/*    */         {
/*    */ 
/*    */         case 'c': 
/* 47 */           String configFile = argument.substring(2);
/*    */           
/* 49 */           configurationLoaded = DofusArenaConfiguration.getInstance().load(configFile);
/* 50 */           if (!configurationLoaded) {
/* 51 */             DofusArenaClientInstance.getLogger().fatal("Echec du chargement avec le fichier de config " + configFile + ", reprise du fichier par dŽfaut");
/*    */           }
/* 53 */           break;
/*    */         
/*    */ 
/*    */         case 's': 
/* 57 */           DofusArenaConfiguration.getInstance().setStartInOpenGLThread(false);
/* 58 */           break;
/*    */         
/*    */ 
/*    */         default: 
/* 62 */           DofusArenaClientInstance.getLogger().error("argument inconnu : '-" + cmd + "'");
/*    */         }
/*    */         
/*    */       }
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 72 */     if ((configurationLoaded) || (DofusArenaConfiguration.getInstance().load()))
/*    */     {
/* 74 */       DofusArenaClientInstance client = DofusArenaClientInstance.getInstance();
/*    */       try {
/* 76 */         client.initialize();
/*    */ 
/*    */ 
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/*    */ 
/* 83 */         JOptionPane.showMessageDialog(null, e, "Error", 0);
/* 84 */         m_logger.error("Erreur au lancement", e);
/* 85 */         System.exit(0);
/*    */       }
/*    */     } else {
/* 88 */       DofusArenaClientInstance.getLogger().fatal("Echec du chargement de la configuration, DofusArenaConfiguration introuvable");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\DofusArenaClient.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */