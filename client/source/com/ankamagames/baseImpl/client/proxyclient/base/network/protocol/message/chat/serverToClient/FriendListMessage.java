/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient;
/*     */ 
/*     */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*     */ import com.ankamagames.framework.kernel.utils.StringUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FriendListMessage
/*     */   extends InputOnlyProxyMessage
/*     */ {
/*     */   public class FriendInformation
/*     */   {
/*     */     private String m_name;
/*     */     private boolean m_notify;
/*     */     private final long m_userId;
/*     */     
/*     */     public FriendInformation(String name, boolean notify, long userId) {
/*  35 */       this.m_name = name;
/*  36 */       this.m_notify = notify;
/*  37 */       this.m_userId = userId;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getName() {
/*  44 */       return this.m_name;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean getNotify() {
/*  51 */       return this.m_notify;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public long getUserId() {
/*  59 */       return this.m_userId;
/*     */     }
/*     */   }
/*     */   
/*  63 */   private final ArrayList<FriendInformation> m_friendInformationList = new ArrayList<FriendInformation>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean decode(byte[] rawDatas) {
/*  72 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/*     */     
/*  74 */     byte tailleListe = bb.get();
/*  75 */     this.m_friendInformationList.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  80 */     for (int i = 0; i < tailleListe; i++) {
/*  81 */       byte tailleNom = bb.get();
/*  82 */       byte[] userName = new byte[tailleNom & 0xFF];
/*  83 */       bb.get(userName);
/*     */       
/*  85 */       this.m_friendInformationList.add(new FriendInformation(StringUtils.fromUTF8(userName), (bb.get() == 1), bb.getLong()));
/*     */     } 
/*     */     
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getId() {
/*  97 */     return 3144;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCheckIn() {
/* 107 */     super.onCheckIn();
/* 108 */     this.m_friendInformationList.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterable<FriendInformation> getFriendInformationList() {
/* 115 */     return this.m_friendInformationList;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\FriendListMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */