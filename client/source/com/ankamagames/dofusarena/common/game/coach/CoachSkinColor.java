/*    */ package com.ankamagames.dofusarena.common.game.coach;
/*    */ 
/*    */ import com.ankamagames.framework.graphics.opengl.base.material.Material;
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
/*    */ public enum CoachSkinColor
/*    */ {
/* 17 */   COLOR0(0.35F, 0.0F, 0.0F),
/* 18 */   COLOR1(0.35F, 0.2F, 0.0F),
/* 19 */   COLOR2(0.32F, 0.3F, 0.27F),
/* 20 */   COLOR3(0.55F, 0.4F, 0.27F),
/* 21 */   COLOR4(0.95F, 0.65F, 0.35F),
/* 22 */   COLOR5(1.0F, 0.83F, 0.49F),
/* 23 */   COLOR6(1.0F, 0.81F, 0.55F),
/* 24 */   COLOR7(1.0F, 0.89F, 0.75F),
/* 25 */   COLOR8(0.1F, 0.1F, 0.2F),
/* 26 */   COLOR9(0.2F, 0.1F, 0.2F),
/* 27 */   COLOR10(0.3F, 0.3F, 0.1F),
/* 28 */   COLOR11(0.43F, 0.36F, 0.56F),
/* 29 */   COLOR12(0.5F, 0.6F, 0.5F),
/* 30 */   COLOR13(0.8F, 0.9F, 0.45F),
/* 31 */   COLOR14(0.74F, 0.9F, 1.0F),
/* 32 */   COLOR15(0.8F, 0.8F, 0.8F);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Material m_material;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   CoachSkinColor(float red, float green, float blue) {
/* 44 */     this.m_material = new Material();
/* 45 */     this.m_material.setDiffuse(red * 1.25F, 
/* 46 */         green * 1.25F, 
/* 47 */         blue * 1.25F, 
/* 48 */         1.0F);
/* 49 */     this.m_material.setUseDiffuse(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Material getMaterial() {
/* 56 */     return this.m_material;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static CoachSkinColor getSkinColor(int index) {
/* 64 */     CoachSkinColor[] values = values();
/* 65 */     if (index >= 0 && index < values.length) {
/* 66 */       return values[index];
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\coach\CoachSkinColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */