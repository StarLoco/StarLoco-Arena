/*    */ package com.ankamagames.graphics.effects;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.Mesh;
/*    */ import com.ankamagames.framework.graphics.opengl.base.Scene;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.Effect;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectContext;
/*    */ import com.ankamagames.framework.graphics.opengl.base.effects.EffectRequirement;
/*    */ import javax.media.opengl.GL;
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
/*    */ public class SceneMirrorEffect
/*    */   extends Effect
/*    */ {
/*    */   public static final String NAME = "sceneMiror";
/*    */   private Scene m_scene;
/*    */   
/*    */   public SceneMirrorEffect()
/*    */   {
/* 28 */     this.m_name = "sceneMiror";
/* 29 */     this.m_requirements = new EffectRequirement(6153, 6159, 1);
/*    */   }
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
/*    */   public void preProcess(long realTime, int frameCount) {}
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
/*    */   public int process(Mesh mesh, long realTime, int frameCount)
/*    */   {
/* 56 */     this.m_scene = ((Scene)mesh);
/*    */     
/* 58 */     return 3;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void draw(GL gl, EffectContext context) {}
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public EffectContext getNewContext()
/*    */   {
/* 77 */     return null;
/*    */   }
/*    */   
/*    */   public Scene getScene() {
/* 81 */     return this.m_scene;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\effects\SceneMirrorEffect.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */