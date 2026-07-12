/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup;
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
/*    */ 
/*    */ public class User
/*    */   implements Comparable<User>
/*    */ {
/*    */   private String m_name;
/*    */   private boolean m_online = false;
/*    */   private long m_id;
/*    */   
/*    */   public User(String name, boolean online, long id) {
/* 26 */     this.m_name = name;
/* 27 */     this.m_online = online;
/* 28 */     this.m_id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public User(String name) {
/* 37 */     this(name, false, -1L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 44 */     return this.m_name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setName(String name) {
/* 51 */     this.m_name = name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOnline() {
/* 58 */     return this.m_online;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOnline(boolean online) {
/* 65 */     this.m_online = online;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getId() {
/* 72 */     return this.m_id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setId(long id) {
/* 79 */     this.m_id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(User user) {
/* 84 */     return getName().toLowerCase().compareTo(user.getName().toLowerCase());
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\cha\\userGroup\User.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */