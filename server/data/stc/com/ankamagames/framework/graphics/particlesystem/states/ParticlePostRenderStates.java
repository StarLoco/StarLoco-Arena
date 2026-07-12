/*    */ package com.ankamagames.framework.graphics.particlesystem.states;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ParticlePostRenderStates
/*    */   implements GLRenderStates
/*    */ {
/*    */   public void setup(GL gl)
/*    */   {
/* 18 */     gl.glTexEnvf(8960, 8704, 34160.0F);
/* 19 */     gl.glBlendFunc(1, 771);
/*    */     
/* 21 */     gl.glTexEnvf(8960, 34163, 2.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\graphics\particlesystem\states\ParticlePostRenderStates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */