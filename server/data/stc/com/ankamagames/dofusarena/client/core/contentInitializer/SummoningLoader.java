/*    */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*    */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentDocumentLoader;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.SummoningDefinition;
/*    */ import com.ankamagames.dofusarena.client.core.game.fighter.SummoningManager;
/*    */ import com.ankamagames.framework.fileFormat.document.DocumentContainer;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SummoningLoader
/*    */   extends ContentDocumentLoader
/*    */ {
/* 21 */   private static final SummoningLoader m_instance = new SummoningLoader();
/*    */   
/*    */   public static SummoningLoader getInstance() {
/* 24 */     return m_instance;
/*    */   }
/*    */   
/*    */   public SummoningLoader() {
/* 28 */     setContentDocumentExtension(".dat");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 37 */     return DofusArenaTranslator.getInstance().getString("contentLoader.summoning", new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void init(AbstractGameClientInstance clientInstance)
/*    */     throws Exception
/*    */   {
/* 46 */     open(DofusArenaConfiguration.getInstance().getString("contentSummoningFile"));
/* 47 */     clientInstance.fireContentInitializerDone(this);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void read(DocumentContainer container)
/*    */   {
/* 57 */     if (container == null) {
/* 58 */       return;
/*    */     }
/*    */     
/*    */     try
/*    */     {
/* 63 */       int summoningCount = readInteger();
/*    */       
/* 65 */       for (int i = 0; i < summoningCount; i++)
/*    */       {
/* 67 */         int id = readInteger();
/* 68 */         int baseHp = readInteger();
/* 69 */         int baseAp = readInteger();
/* 70 */         int baseMp = readInteger();
/* 71 */         int gfxId = readInteger();
/* 72 */         int spellId = readInteger();
/*    */         
/* 74 */         SummoningDefinition s = new SummoningDefinition(id, baseHp, baseAp, baseMp, gfxId, spellId);
/*    */         
/* 76 */         SummoningManager.getInstance().addSummoningDefinition(s);
/*    */       }
/*    */     } catch (Exception e) {
/* 79 */       e.printStackTrace();
/*    */     }
/*    */     
/* 82 */     container.notifyOnLoadComplete();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void notifyOnLoadComplete()
/*    */   {
/* 89 */     m_logger.info("Summoning loaded successfully");
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\SummoningLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */