/*     */ package com.ankamagames.dofusarena.client.chat;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DofusArenaUserGroupManager
/*     */ {
/*  21 */   private static DofusArenaUserGroupManager m_instance = new DofusArenaUserGroupManager();
/*  22 */   private HashMap<String, DofusArenaUser> m_list = new HashMap<String, DofusArenaUser>();
/*  23 */   private UserFilter m_userFilter = new UserFilter();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DofusArenaUserGroupManager getInstance() {
/*  30 */     return m_instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUser(short type, DofusArenaUser user) {
/*  40 */     if (!this.m_list.containsKey(user.getName().toLowerCase())) {
/*  41 */       user.EnableType(type);
/*  42 */       this.m_list.put(user.getName().toLowerCase(), user);
/*     */     } else {
/*  44 */       DofusArenaUser dau = this.m_list.get(user.getName().toLowerCase());
/*  45 */       dau.EnableType(type);
/*     */     } 
/*  47 */     updateProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addUsers(short type, Iterable<DofusArenaUser> users) {
/*  56 */     for (DofusArenaUser user : users) {
/*  57 */       addUser(type, user);
/*     */     }
/*  59 */     updateProperty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeUser(short type, String userName) {
/*  69 */     if (this.m_list.containsKey(userName.toLowerCase())) {
/*  70 */       DofusArenaUser user = this.m_list.get(userName.toLowerCase());
/*  71 */       user.DisableType(type);
/*  72 */       if (type == DofusArenaUser.FRIEND) {
/*  73 */         user.setOnline(false);
/*     */       }
/*  75 */       if (user.hasNoType()) {
/*  76 */         this.m_list.remove(userName.toLowerCase());
/*     */       }
/*  78 */       updateProperty();
/*  79 */       return true;
/*     */     } 
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<String, DofusArenaUser> getFriendGroup() {
/*  89 */     HashMap<String, DofusArenaUser> friendList = new HashMap<String, DofusArenaUser>();
/*  90 */     for (DofusArenaUser user : this.m_list.values()) {
/*  91 */       if (user.isType(DofusArenaUser.FRIEND)) {
/*  92 */         friendList.put(user.getName().toLowerCase(), user);
/*     */       }
/*     */     } 
/*  95 */     return friendList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashMap<String, DofusArenaUser> getIgnoreGroup() {
/* 102 */     HashMap<String, DofusArenaUser> ignoreList = new HashMap<String, DofusArenaUser>();
/* 103 */     for (DofusArenaUser user : this.m_list.values()) {
/* 104 */       if (user.isType(DofusArenaUser.IGNORE)) {
/* 105 */         ignoreList.put(user.getName().toLowerCase(), user);
/*     */       }
/*     */     } 
/* 108 */     return ignoreList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserFilter getUserFilter() {
/* 116 */     return this.m_userFilter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProperty() {
/* 123 */     ArrayList<DofusArenaUser> property = new ArrayList<DofusArenaUser>();
/* 124 */     for (DofusArenaUser user : this.m_list.values()) {
/* 125 */       if (this.m_userFilter.accept(user)) {
/* 126 */         property.add(user);
/*     */       }
/*     */     } 
/* 129 */     Collections.sort(property);
/* 130 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().setPropertyValue("contact.list", property.toArray());
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\DofusArenaUserGroupManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */