/*     */ package com.ankamagames.baseImpl.graphicalClient.chat;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.chat.ChatPipe;
/*     */ import com.ankamagames.xulor.property.FieldProvider;
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
/*     */ public class ChatPipeWrapper
/*     */   implements FieldProvider
/*     */ {
/*     */   public static final String NAME_FIELD = "name";
/*     */   public static final String SELECTED_FIELD = "selected";
/*     */   public static final String PIPE_INTERNAL_NAME_FIELD = "pipeInternalName";
/*  21 */   public static final String[] FIELDS = {
/*  22 */     "name", 
/*  23 */     "selected", 
/*  24 */     "pipeInternalName" };
/*     */   
/*     */ 
/*     */   private final ChatPipe m_chatPipe;
/*     */   
/*     */ 
/*     */   private final String m_pipeName;
/*     */   
/*     */   private boolean m_open;
/*     */   
/*     */ 
/*     */   public ChatPipeWrapper(ChatPipe pipe, String pipeName)
/*     */   {
/*  37 */     this.m_chatPipe = pipe;
/*  38 */     this.m_pipeName = pipeName;
/*  39 */     this.m_open = true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ChatPipe getChatPipe()
/*     */   {
/*  48 */     return this.m_chatPipe;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getPipeName()
/*     */   {
/*  57 */     return this.m_pipeName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOpen()
/*     */   {
/*  66 */     return this.m_open;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void setOpen(boolean open)
/*     */   {
/*  73 */     this.m_open = open;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getFields()
/*     */   {
/*  82 */     return FIELDS;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getFieldValue(String fieldName)
/*     */   {
/*  92 */     if (fieldName.equals("name")) {
/*  93 */       return this.m_pipeName;
/*     */     }
/*  95 */     if (fieldName.equals("selected")) {
/*  96 */       return Boolean.valueOf(this.m_open);
/*     */     }
/*  98 */     if (fieldName.equals("pipeInternalName")) {
/*  99 */       return getChatPipe().getInternalName();
/*     */     }
/*     */     
/* 102 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setFieldValue(String fieldName, Object value) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void prependFieldValue(String fieldName, Object value) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void appendFieldValue(String fieldName, Object value) {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFieldSynchronisable(String fieldName)
/*     */   {
/* 138 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\graphicalClient\chat\ChatPipeWrapper.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */