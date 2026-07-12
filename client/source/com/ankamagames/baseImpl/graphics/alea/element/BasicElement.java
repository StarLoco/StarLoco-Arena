/*    */ package com.ankamagames.baseImpl.graphics.alea.element;
/*    */ 
/*    */ import com.ankamagames.baseImpl.graphics.alea.element.properties.BasicElementProperties;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class BasicElement
/*    */ {
/*    */   public static final int ELEMENT_TYPE_BASIC = 0;
/*    */   public static final int ELEMENT_TYPE_SPATIAL_DATA = 1;
/*    */   public static final int ELEMENT_TYPE_GRAPHICAL = 2;
/*    */   public static final int ELEMENT_TYPE_TEINT = 3;
/*    */   public static final int ELEMENT_TYPE_OFFSET = 4;
/*    */   public static final int ELEMENT_TYPE_GROUP = 6;
/*    */   public static final int ELEMENT_TYPE_LEVEL_UNPILED = 8;
/*    */   public static final int ELEMENT_TYPE_PARTICLE = 9;
/*    */   public static final int ELEMENT_TYPE_SHADOW = 10;
/*    */   public static final int ELEMENT_TYPE_SOUND = 11;
/*    */   private int m_id;
/*    */   private int m_type;
/* 33 */   public TIntObjectHashMap<BasicElementProperties> m_properties = null;
/*    */   
/*    */   public BasicElement(int id) {
/* 36 */     this.m_id = id;
/* 37 */     setType(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 44 */     return this.m_id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getType() {
/* 51 */     return this.m_type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void setType(int type) {
/* 58 */     this.m_type = type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BasicElementProperties getStateProperties(int state) {
/* 68 */     return (BasicElementProperties)this.m_properties.get(state);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void read(ByteBuffer buffer) {
/* 77 */     this.m_properties = new TIntObjectHashMap();
/*    */     
/* 79 */     int numState = buffer.get();
/* 80 */     for (int i = 0; i < numState; i++) {
/* 81 */       int state = buffer.get();
/* 82 */       this.m_properties.put(state, readStateProperties(buffer));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected BasicElementProperties readStateProperties(ByteBuffer buffer) {
/* 87 */     BasicElementProperties properties = new BasicElementProperties();
/* 88 */     properties.read(buffer);
/* 89 */     return properties;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphics\alea\element\BasicElement.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */