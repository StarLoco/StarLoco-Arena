/*    */ package com.ankamagames.framework.preferences;
/*    */ 
/*    */ import com.ankamagames.framework.kernel.events.EventObject;
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
/*    */ 
/*    */ 
/*    */ public class PreferencePropertyChangeEvent
/*    */   extends EventObject
/*    */ {
/*    */   private final String m_propertyName;
/*    */   private final Object m_oldValue;
/*    */   private final Object m_newValue;
/*    */   
/*    */   public PreferencePropertyChangeEvent(PreferenceStore store, String propertyName, Object oldValue, Object newValue)
/*    */   {
/* 28 */     super(store);
/* 29 */     this.m_propertyName = propertyName;
/* 30 */     this.m_oldValue = oldValue;
/* 31 */     this.m_newValue = newValue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public PreferenceStore getPreferenceStore()
/*    */   {
/* 38 */     return (PreferenceStore)this.m_source;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public String getPropertyName()
/*    */   {
/* 45 */     return this.m_propertyName;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getOldValue()
/*    */   {
/* 52 */     return this.m_oldValue;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public Object getNewValue()
/*    */   {
/* 59 */     return this.m_newValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\preferences\PreferencePropertyChangeEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */