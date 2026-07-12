/*    */ package com.ankamagames.dofusarena.client.core.contentInitializer;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*    */ import com.ankamagames.baseImpl.graphicalClient.core.contentLoader.ContentInitializer;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaConfiguration;
/*    */ import com.ankamagames.dofusarena.client.core.DofusArenaTranslator;
/*    */ import com.ankamagames.dofusarena.common.game.fight.FightDefinitionManager;
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
/*    */ public class FightDefinitionLoader
/*    */   implements ContentInitializer
/*    */ {
/* 21 */   private static FightDefinitionLoader m_instance = new FightDefinitionLoader();
/*    */   
/*    */ 
/*    */ 
/*    */   public static FightDefinitionLoader getInstance()
/*    */   {
/* 27 */     return m_instance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String getName()
/*    */   {
/* 36 */     return DofusArenaTranslator.getInstance().getString("contentLoader.fightDefinition", new Object[0]);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void init(AbstractGameClientInstance clientInstance)
/*    */     throws Exception
/*    */   {
/* 46 */     FightDefinitionManager.getInstance().initializeDefinitions(DofusArenaConfiguration.getInstance().getString("fightDefinitionsFile"));
/* 47 */     clientInstance.fireContentInitializerDone(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\core\contentInitializer\FightDefinitionLoader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */