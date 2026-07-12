/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.chat.userGroup;
/*    */ 
/*    */ import java.util.HashMap;
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
/*    */ public class UserGroup
/*    */ {
/* 16 */   private HashMap<String, User> m_users = new HashMap();
/*    */   
/*    */ 
/*    */ 
/*    */   public HashMap<String, User> getUsers()
/*    */   {
/* 22 */     return this.m_users;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addUser(User user)
/*    */   {
/* 30 */     this.m_users.put(user.getName(), user);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void addUsers(Iterable<User> users)
/*    */   {
/* 39 */     for (User user : users) {
/* 40 */       addUser(user);
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean removeUser(User user)
/*    */   {
/* 50 */     return this.m_users.remove(user.getName()) != null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean removeUser(String userName)
/*    */   {
/* 60 */     if (this.m_users.containsKey(userName)) {
/* 61 */       this.m_users.remove(userName);
/* 62 */       return true;
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public User getUser(String userName)
/*    */   {
/* 72 */     return (User)this.m_users.get(userName);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public String format()
/*    */   {
/* 80 */     StringBuilder s = new StringBuilder("");
/*    */     
/* 82 */     for (User u : getUsers().values()) {
/* 83 */       s.append(" +").append(u.getName()).append(" (");
/* 84 */       if (u.isOnline()) s.append("onLine"); else
/* 85 */         s.append("offLine");
/* 86 */       s.append(")\n");
/*    */     }
/*    */     
/* 89 */     return s.toString();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean contains(String name)
/*    */   {
/* 98 */     return this.m_users.containsKey(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\chat\userGroup\UserGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */