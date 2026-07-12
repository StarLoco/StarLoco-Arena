/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public abstract class TagLibrary
/*     */ {
/*  24 */   private static Logger m_logger = Logger.getLogger(TagLibrary.class);
/*     */   
/*  26 */   protected Map<String, Factory> m_tags = new HashMap<String, Factory>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TagLibrary() {
/*  32 */     registerTags();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void registerTags();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTag(String name, Class template) {
/*  44 */     registerTag(name, new DefaultFactory(template));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerTag(String name, Factory factory) {
/*  51 */     if (!this.m_tags.containsKey(name.toLowerCase())) {
/*  52 */       this.m_tags.put(name.toLowerCase(), factory);
/*     */     } else {
/*  54 */       m_logger.error("le tag (name=" + name + ") est déjà utilisé !");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unregisterTag(String name) {
/*  62 */     return (this.m_tags.remove(name) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map getTagClasses() {
/*  70 */     return this.m_tags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Factory getFactory(String name) {
/*  77 */     return this.m_tags.get(name.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Factory getFactory(Class template) {
/*  84 */     Factory factory = null;
/*  85 */     Iterator<Factory> it = this.m_tags.values().iterator();
/*  86 */     while (it != null && it.hasNext()) {
/*  87 */       Factory f = it.next();
/*  88 */       if (f.getTemplate().equals(template)) {
/*  89 */         factory = f;
/*     */         break;
/*     */       } 
/*     */     } 
/*  93 */     return factory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Method getSetter(Class template, String name) {
/* 101 */     Method method = null;
/* 102 */     Factory factory = getFactory(template.getName());
/* 103 */     if (factory != null) {
/* 104 */       method = factory.getSetter(name);
/*     */     }
/* 106 */     return method;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Method guessSetter(Class template, String name) {
/* 114 */     Method method = null;
/* 115 */     Factory factory = getFactory(template.getName());
/* 116 */     if (factory != null) {
/* 117 */       method = factory.guessSetter(name);
/*     */     }
/* 119 */     return method;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\TagLibrary.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */