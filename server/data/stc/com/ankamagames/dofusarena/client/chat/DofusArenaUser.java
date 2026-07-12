/*     */ package com.ankamagames.dofusarena.client.chat;
/*     */ 
/*     */ import com.ankamagames.baseImpl.graphicalClient.chat.FieldedUser;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DofusArenaUser
/*     */   extends FieldedUser
/*     */   implements FieldProvider
/*     */ {
/*  14 */   public static short NONE = 0;
/*  15 */   public static short FRIEND = 1;
/*  16 */   public static short IGNORE = 2;
/*  17 */   public static short PARTY = 4;
/*  18 */   public static short GUILD = 8;
/*     */   
/*  20 */   private short m_type = 0;
/*  21 */   private boolean m_notify = false;
/*     */   
/*     */   public static final String NOTIFY_FIELD = "notify";
/*     */   
/*     */   public static final String TYPE_FIELD = "type";
/*  26 */   public static final String[] FIELDS = { "notify", "type" };
/*     */   
/*     */ 
/*     */ 
/*  30 */   public static final String[] ALL_FIELDS = new String[FIELDS.length + FieldedUser.FIELDS.length];
/*  31 */   static { System.arraycopy(FIELDS, 0, ALL_FIELDS, 0, FIELDS.length);
/*  32 */     System.arraycopy(FieldedUser.FIELDS, 0, ALL_FIELDS, FIELDS.length, FieldedUser.FIELDS.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DofusArenaUser(String name, boolean online, long id, boolean notify)
/*     */   {
/*  42 */     super(name, online, id);
/*  43 */     this.m_notify = notify;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DofusArenaUser(String name)
/*     */   {
/*  52 */     super(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void EnableType(short type)
/*     */   {
/*  62 */     this.m_type = ((short)(type | this.m_type));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void DisableType(short type)
/*     */   {
/*  71 */     this.m_type = ((short)((type ^ 0xFFFFFFFF) & this.m_type));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isType(short type)
/*     */   {
/*  82 */     return (this.m_type & type) != 0;
/*     */   }
/*     */   
/*     */   public boolean hasNoType() {
/*  86 */     return this.m_type == NONE;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/*  95 */     if (fieldName.equals("notify")) {
/*  96 */       return Boolean.valueOf(isNotify());
/*     */     }
/*  98 */     if (fieldName.equals("type")) {
/*  99 */       return Short.valueOf(this.m_type);
/*     */     }
/* 101 */     return super.getFieldValue(fieldName);
/*     */   }
/*     */   
/*     */   public boolean isNotify() {
/* 105 */     return this.m_notify;
/*     */   }
/*     */   
/*     */   public void setNotify(boolean notify) {
/* 109 */     this.m_notify = notify;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/* 117 */     return ALL_FIELDS;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\dofusarena\client\chat\DofusArenaUser.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */