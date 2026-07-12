/*    */ package com.ankamagames.baseImpl.graphics.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.GraphicalElementProperties;
/*    */ import gnu.trove.TIntObjectHashMap;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GraphicalElement
/*    */   extends SpatialDataElement
/*    */ {
/*    */   public GraphicalElement(int id)
/*    */   {
/* 19 */     super(id);
/* 20 */     setType(2);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public GraphicalElementProperties getStateProperties(int state)
/*    */   {
/* 30 */     if (this.m_properties.contains(state)) {
/* 31 */       return (GraphicalElementProperties)this.m_properties.get(state);
/*    */     }
/* 33 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected GraphicalElementProperties readStateProperties(ByteBuffer buffer)
/*    */   {
/* 41 */     GraphicalElementProperties properties = new GraphicalElementProperties();
/* 42 */     properties.read(buffer);
/* 43 */     return properties;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\GraphicalElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */