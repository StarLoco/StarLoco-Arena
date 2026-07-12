/*     */ package com.ankamagames.baseImpl.client.proxyclient.base.network.proxy;
/*     */ 
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertiesReaderWriter;
/*     */ import com.ankamagames.framework.fileFormat.properties.PropertyException;
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
/*     */ public class ProxyGroup
/*     */ {
/*     */   private int m_index;
/*     */   private String m_name;
/*     */   private ArrayList<ProxyAddress> m_proxyAddresses;
/*     */   private ArrayList<ProxyAddress> m_usableProxyAddresses;
/*     */   
/*     */   public ProxyGroup(int index, String name) {
/*  30 */     this.m_index = index;
/*  31 */     this.m_name = name;
/*  32 */     this.m_proxyAddresses = new ArrayList<ProxyAddress>();
/*  33 */     this.m_usableProxyAddresses = new ArrayList<ProxyAddress>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIndex() {
/*  40 */     return this.m_index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  47 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<ProxyAddress> getProxyAddresses() {
/*  54 */     return this.m_proxyAddresses;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProxy(ProxyAddress proxyAddress) {
/*  63 */     this.m_proxyAddresses.add(proxyAddress);
/*  64 */     this.m_usableProxyAddresses.add(proxyAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProxyAddress getRandomProxyAddress() {
/*  73 */     if (!this.m_usableProxyAddresses.isEmpty()) {
/*  74 */       int index = (int)Math.floor(Math.random() * this.m_usableProxyAddresses.size());
/*  75 */       return this.m_usableProxyAddresses.remove(index);
/*     */     } 
/*  77 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearRandomIterator() {
/*  84 */     this.m_usableProxyAddresses = new ArrayList<ProxyAddress>();
/*  85 */     for (ProxyAddress proxyAddress : this.m_proxyAddresses) {
/*  86 */       this.m_usableProxyAddresses.add(proxyAddress);
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ArrayList<ProxyGroup> extractProxyGroupsFromProperties(PropertiesReaderWriter properties, String groupsKey, String proxiesKey) {
/* 114 */     ArrayList<ProxyGroup> proxyGroups = new ArrayList<ProxyGroup>();
/*     */ 
/*     */     
/*     */     try {
/* 118 */       ArrayList<String> universNames = properties.getMultiString(groupsKey);
/*     */ 
/*     */       
/* 121 */       ArrayList<String[]> proxiesDefinitions = properties.getMultiStringArray(proxiesKey);
/*     */       
/* 123 */       for (int i = 0; i < universNames.size(); i++) {
/*     */ 
/*     */         
/* 126 */         ProxyGroup univers = new ProxyGroup(i + 1, universNames.get(i));
/*     */ 
/*     */         
/* 129 */         if (i < proxiesDefinitions.size()) {
/* 130 */           String[] proxyAddresses = proxiesDefinitions.get(i);
/* 131 */           for (int j = 0; j < proxyAddresses.length; j++) {
/*     */             
/* 133 */             String proxyAdress = proxyAddresses[j];
/* 134 */             String[] values = proxyAdress.split(":");
/*     */             
/* 136 */             if (values.length == 2) {
/* 137 */               String proxyHost = values[0];
/* 138 */               int proxyPort = Integer.parseInt(values[1]);
/*     */               
/* 140 */               ProxyAddress proxy = new ProxyAddress(proxyHost, proxyPort);
/* 141 */               univers.addProxy(proxy);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 146 */         proxyGroups.add(univers);
/*     */       } 
/* 148 */     } catch (PropertyException e) {
/* 149 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 152 */     return proxyGroups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 162 */     return this.m_name;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\baseImpl\client\proxyclient\base\network\proxy\ProxyGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */