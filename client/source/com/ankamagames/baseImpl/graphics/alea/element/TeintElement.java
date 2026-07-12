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
/*    */   
/*    */   private static final int VALUE_MIN = -16;
/*    */   
/*    */   public TeintElement(int id) {
/* 21 */     super(id);
/* 22 */     setType(3);
/*    */   }
/*    */   
/*    */   public static float[] getTeintModification(WorldElement element, double lightContrast) {
/* 26 */     float[] value = getValue(element);
/* 27 */     value[0] = (float)(value[0] * lightContrast);
/* 28 */     value[1] = (float)(value[1] * lightContrast);
/* 29 */     value[2] = (float)(value[2] * lightContrast);
/* 30 */     return value;
/*    */   }
/*    */   
/*    */   public static float[] getTeint(WorldElement element) {
/* 34 */     return getTeintModification(element, 0.019999999552965164D);
/*    */   }
/*    */   private static float[] getValue(WorldElement element) {
/*    */     int v;
/* 38 */     float[] value = new float[3];
/*    */     
/* 40 */     switch (element.getParams()[0])
/*    */     { case 3:
/* 42 */         value[2] = element.getParams()[1]; value[1] = element.getParams()[1]; value[0] = element.getParams()[1];
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
/*    */         
/* 56 */         return value;case 5: v = element.getParams()[1] | element.getParams()[2] << 8; value[0] = ((v & 0x1F) + -16); value[1] = ((v >> 5 & 0x1F) + -16); value[2] = ((v >> 10 & 0x1F) + -16); return value; }  m_logger.error("Mauvais type de paramètre attendu"); return value;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\TeintElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */