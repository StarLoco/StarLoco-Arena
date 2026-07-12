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
/*    */ public enum CoachHairColor
/*    */ {
/* 17 */   COLOR0(0.2F, 0.2F, 0.2F), 
/* 18 */   COLOR1(0.4F, 0.21F, 0.1F), 
/* 19 */   COLOR2(0.7F, 0.34F, 0.0F), 
/* 20 */   COLOR3(1.0F, 0.47F, 0.0F), 
/* 21 */   COLOR4(1.0F, 0.7F, 0.4F), 
/* 22 */   COLOR5(1.0F, 0.73F, 0.23F), 
/* 23 */   COLOR6(1.0F, 0.23F, 0.35F), 
/* 24 */   COLOR7(1.0F, 0.2F, 0.2F), 
/* 25 */   COLOR8(0.35F, 0.36F, 0.0F), 
/* 26 */   COLOR9(0.83F, 0.87F, 0.1F), 
/* 27 */   COLOR10(0.5F, 1.0F, 0.5F), 
/* 28 */   COLOR11(0.8F, 0.8F, 1.0F), 
/* 29 */   COLOR12(0.47F, 0.56F, 1.0F), 
/* 30 */   COLOR13(0.2F, 0.2F, 0.4F), 
/* 31 */   COLOR14(0.29F, 0.47F, 0.41F), 
/* 32 */   COLOR15(1.0F, 1.0F, 0.75F);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private Material m_material;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   private CoachHairColor(float red, float green, float blue)
/*    */   {
/* 44 */     this.m_material = new Material();
/* 45 */     this.m_material.setDiffuse(red * 1.25F, 
/* 46 */       green * 1.25F, 
/* 47 */       blue * 1.25F, 
/* 48 */       1.0F);
/* 49 */     this.m_material.setUseDiffuse(true);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Material getMaterial()
/*    */   {
/* 56 */     return this.m_material;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static CoachHairColor getHairColor(int index)
/*    */   {
/* 64 */     CoachHairColor[] values = values();
/* 65 */     if ((index >= 0) && (index < values.length)) {
/* 66 */       return values[index];
/*    */     }
/* 68 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\common\game\coach\CoachHairColor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */