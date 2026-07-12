/*    */ package com.ankamagames.xulor.util;
/*    */ 
/*    */ import com.ankamagames.xulor.template.IElement;
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
/*    */ public class XulorInsert
/*    */   implements XulorLoadUnload
/*    */ {
/* 17 */   public IElement m_element = null;
/* 18 */   public IElement m_parent = null;
/* 19 */   public String m_id = null;
/* 20 */   public long m_options = 0L;
/*    */   
/*    */   public XulorInsert(IElement element, IElement parent, String id, long options) {
/* 23 */     this.m_element = element;
/* 24 */     this.m_parent = parent;
/* 25 */     this.m_id = id;
/* 26 */     this.m_options = options;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\util\XulorInsert.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */