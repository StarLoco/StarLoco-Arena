/*    */ package com.ankamagames.baseImpl.graphicalClient.alea;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphicalClient.AbstractGameClientInstance;
/*    */ import com.ankamagames.baseImpl.graphics.alea.display.AleaWorldScene;
/*    */ import javax.media.opengl.GLAutoDrawable;
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
/*    */ public abstract class GameWorldScene
/*    */   extends AleaWorldScene
/*    */ {
/*    */   protected AbstractGameClientInstance m_gameClientInstance;
/*    */   
/*    */   public GameWorldScene(AbstractGameClientInstance gameClientInstance)
/*    */   {
/* 25 */     this.m_gameClientInstance = gameClientInstance;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void init(GLAutoDrawable glAutoDrawable)
/*    */   {
/* 35 */     super.init(glAutoDrawable);
/*    */     
/*    */ 
/* 38 */     this.m_gameClientInstance.onWorldSceneInitialized();
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\alea\GameWorldScene.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */