/*    */ package com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.chat.serverToClient;
/*    */ 
/*    */ import com.ankamagames.baseImpl.client.proxyclient.base.network.protocol.message.InputOnlyProxyMessage;
/*    */ import com.ankamagames.framework.kernel.utils.StringUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.ArrayList;
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
/*    */ public class IgnoreListMessage
/*    */   extends InputOnlyProxyMessage
/*    */ {
/* 21 */   private ArrayList<String> m_IgnoreList = new ArrayList();
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean decode(byte[] rawDatas)
/*    */   {
/* 30 */     ByteBuffer bb = ByteBuffer.wrap(rawDatas);
/* 31 */     int m_ignoreListSize = bb.get();
/* 32 */     for (int i = 0; i < m_ignoreListSize; i++)
/*    */     {
/* 34 */       byte[] m_entryName = new byte[bb.get() & 0xFF];
/* 35 */       bb.get(m_entryName);
/*    */       
/* 37 */       String name = StringUtils.fromUTF8(m_entryName);
/* 38 */       this.m_IgnoreList.add(name);
/*    */     }
/*    */     
/* 41 */     return true;
/*    */   }
/*    */   
/*    */   public void main(String[] args) {
/* 45 */     byte[] b = new byte[1];
/* 46 */     b[0] = 18;
/* 47 */     decode(b);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public int getId()
/*    */   {
/* 56 */     return 3146;
/*    */   }
/*    */   
/*    */   public ArrayList<String> getIgnoreList() {
/* 60 */     return this.m_IgnoreList;
/*    */   }
/*    */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\protocol\message\chat\serverToClient\IgnoreListMessage.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */