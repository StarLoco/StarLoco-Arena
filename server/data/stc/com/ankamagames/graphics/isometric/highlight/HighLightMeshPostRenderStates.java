/*    */ package com.ankamagames.graphics.isometric.highlight;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.states.GLRenderStates;
/*    */ import javax.media.opengl.GL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighLightMeshPostRenderStates
/*    */   implements GLRenderStates
/*    */ {
/*    */   public void setup(GL gl)
/*    */   {
/* 16 */     gl.glBlendFunc(1, 771);
/* 17 */     gl.glTexEnvf(8960, 34163, 2.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\graphics\isometric\highlight\HighLightMeshPostRenderStates.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */