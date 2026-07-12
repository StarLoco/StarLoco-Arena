/*    */ package com.ankamagames.baseImpl.graphics.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.worldElement.WorldElement;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TeintElement
/*    */   extends BasicElement
/*    */ {
/* 16 */   private static Logger m_logger = Logger.getLogger(TeintElement.class);
/*    */   private static final int VALUE_MIN = -16;
/*    */   
/*    */   public TeintElement(int id)
/*    */   {
/* 21 */     super(id);
/* 22 */     setType(3);
/*    */   }
/*    */   
/*    */   public static float[] getTeintModification(WorldElement element, double lightContrast) {
/* 26 */     float[] value = getValue(element); int 
/* 27 */       tmp7_6 = 0; float[] tmp7_5 = value;tmp7_5[tmp7_6] = ((float)(tmp7_5[tmp7_6] * lightContrast)); int 
/* 28 */       tmp16_15 = 1; int tmp16_14 = tmp7_6;tmp16_14[tmp16_15] = ((float)(tmp16_14[tmp16_15] * lightContrast)); int 
/* 29 */       tmp25_24 = 2; int tmp25_23 = tmp7_6;tmp25_23[tmp25_24] = ((float)(tmp25_23[tmp25_24] * lightContrast));
/* 30 */     return tmp7_6;
/*    */   }
/*    */   
/*    */   public static float[] getTeint(WorldElement element) {
/* 34 */     return getTeintModification(element, 0.019999999552965164D);
/*    */   }
/*    */   
/*    */   private static float[] getValue(WorldElement element) {
/* 38 */     float[] value = new float[3];
/*    */     
/* 40 */     switch (element.getParams()[0]) {
/*    */     case 3: 
/* 42 */       value[0] = (value[1] = value[2] = element.getParams()[1]);
/* 43 */       break;
/*    */     
/*    */     case 5: 
/* 46 */       int v = element.getParams()[1] | element.getParams()[2] << 8;
/* 47 */       value[0] = ((v & 0x1F) + -16);
/* 48 */       value[1] = ((v >> 5 & 0x1F) + -16);
/* 49 */       value[2] = ((v >> 10 & 0x1F) + -16);
/* 50 */       break;
/*    */     case 4: 
/*    */     default: 
/* 53 */       m_logger.error("Mauvais type de paramètre attendu");
/*    */     }
/*    */     
/* 56 */     return value;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\TeintElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */