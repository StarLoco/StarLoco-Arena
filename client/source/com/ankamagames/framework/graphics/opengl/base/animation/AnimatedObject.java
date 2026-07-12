/*    */ package com.ankamagames.framework.graphics.opengl.base.animation;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.animation.instances.AnimatedObjectControler;
/*    */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
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
/*    */ public abstract class AnimatedObject
/*    */   extends TimedObject
/*    */ {
/*    */   protected boolean m_invalidate = false;
/*    */   
/*    */   public void process(Mesh parentMesh, int deltaTime, int recurs) {
/* 22 */     incTime(deltaTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract Mesh getMesh();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void release();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void addControler(AnimatedObjectControler paramAnimatedObjectControler);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract void removeControler(AnimatedObjectControler paramAnimatedObjectControler);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInvalidate() {
/* 53 */     return this.m_invalidate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void invalidate() {
/* 60 */     this.m_invalidate = true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\opengl\base\animation\AnimatedObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */