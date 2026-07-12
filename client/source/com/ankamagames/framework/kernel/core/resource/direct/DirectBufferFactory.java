/*    */ package com.ankamagames.framework.kernel.core.resource.direct;
/*    */ 
/*    */ import com.sun.opengl.util.BufferUtil;
/*    */ import java.nio.Buffer;
/*    */ import org.apache.commons.pool.KeyedPoolableObjectFactory;
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
/*    */ public class DirectBufferFactory
/*    */   implements KeyedPoolableObjectFactory
/*    */ {
/*    */   public static final int BUFFERTYPE_BYTE = 1;
/*    */   public static final int BUFFERTYPE_SHORT = 2;
/*    */   public static final int BUFFERTYPE_INT = 3;
/*    */   public static final int BUFFERTYPE_FLOAT = 4;
/*    */   public static final int BUFFERTYPE_DOUBLE = 5;
/* 25 */   private static final Object m_mutex = new Object();
/*    */ 
/*    */   
/*    */   public Object makeObject(Object object) throws Exception {
/* 29 */     Integer key = (Integer)object;
/*    */     
/* 31 */     synchronized (m_mutex) {
/* 32 */       Buffer buf = null;
/*    */       
/* 34 */       int type = key.intValue() >> 24 & 0xFF;
/* 35 */       int size = key.intValue() & 0xFFFFFF;
/*    */       
/* 37 */       switch (type) {
/*    */         case 1:
/* 39 */           buf = BufferUtil.newByteBuffer(size);
/*    */           break;
/*    */         case 2:
/* 42 */           buf = BufferUtil.newShortBuffer(size);
/*    */           break;
/*    */         case 3:
/* 45 */           buf = BufferUtil.newIntBuffer(size);
/*    */           break;
/*    */         case 4:
/* 48 */           buf = BufferUtil.newFloatBuffer(size);
/*    */           break;
/*    */         case 5:
/* 51 */           buf = BufferUtil.newDoubleBuffer(size);
/*    */           break;
/*    */       } 
/*    */       
/* 55 */       return buf;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroyObject(Object object, Object object1) throws Exception {}
/*    */   
/*    */   public boolean validateObject(Object object, Object object1) {
/* 63 */     return true;
/*    */   }
/*    */   
/*    */   public void activateObject(Object object, Object object1) throws Exception {}
/*    */   
/*    */   public void passivateObject(Object object, Object object1) throws Exception {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\kernel\core\resource\direct\DirectBufferFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */