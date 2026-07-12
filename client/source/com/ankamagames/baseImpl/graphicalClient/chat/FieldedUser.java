/*    */ package com.ankamagames.baseImpl.graphicalClient.chat;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup.User;
/*    */ import com.ankamagames.xulor.property.FieldProvider;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FieldedUser
/*    */   extends User
/*    */   implements FieldProvider
/*    */ {
/*    */   public static final String NAME_FIELD = "name";
/*    */   public static final String ONLINE_FIELD = "online";
/* 20 */   public static final String[] FIELDS = new String[] {
/* 21 */       "name", 
/* 22 */       "online"
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldedUser(String name, boolean online, long id) {
/* 33 */     super(name, online, id);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FieldedUser(String name) {
/* 42 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void appendFieldValue(String fieldName, Object value) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getFieldValue(String fieldName) {
/* 60 */     if (fieldName.equals("name")) {
/* 61 */       return getName();
/*    */     }
/* 63 */     if (fieldName.equals("online")) {
/* 64 */       return Boolean.valueOf(isOnline());
/*    */     }
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getFields() {
/* 75 */     return FIELDS;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isFieldSynchronisable(String fieldName) {
/* 84 */     return false;
/*    */   }
/*    */   
/*    */   public void prependFieldValue(String fieldName, Object value) {}
/*    */   
/*    */   public void setFieldValue(String fieldName, Object value) {}
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\chat\FieldedUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */