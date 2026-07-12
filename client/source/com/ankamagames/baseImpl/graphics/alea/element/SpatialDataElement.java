/*    */ package com.ankamagames.baseImpl.graphics.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.BasicElementProperties;
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.SpatialDataElementProperties;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SpatialDataElement
/*    */   extends BasicElement
/*    */ {
/*    */   public SpatialDataElement(int id) {
/* 23 */     super(id);
/* 24 */     setType(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SpatialDataElementProperties getStateProperties(int state) {
/* 34 */     if (this.m_properties.contains(state)) {
/* 35 */       return (SpatialDataElementProperties)this.m_properties.get(state);
/*    */     }
/* 37 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected BasicElementProperties readStateProperties(ByteBuffer buffer) {
/* 45 */     SpatialDataElementProperties properties = new SpatialDataElementProperties();
/* 46 */     properties.read(buffer);
/* 47 */     return (BasicElementProperties)properties;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\SpatialDataElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */