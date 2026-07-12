/*    */ package org.fenggui.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.InvocationTargetException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class InputOnlyStream
/*    */   extends InputOutputStream
/*    */ {
/* 28 */   private ContextHandler contextHandler = null;
/* 29 */   private String resourcePath = null;
/*    */ 
/*    */   
/*    */   public String getResourcePath() {
/* 33 */     return this.resourcePath;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setResourcePath(String resourcePath) {
/* 38 */     this.resourcePath = resourcePath;
/*    */   }
/*    */ 
/*    */   
/*    */   public InputOnlyStream(ContextHandler contextHandler) {
/* 43 */     this.contextHandler = contextHandler;
/*    */   }
/*    */ 
/*    */   
/*    */   public IOStreamSaveable get(String name) {
/* 48 */     return this.contextHandler.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(String name, IOStreamSaveable s) throws NameShadowingException {
/* 53 */     this.contextHandler.add(name, s);
/*    */   }
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
/*    */   public <T extends IOStreamSaveable> T constructObject(Class<T> clazz) throws IOStreamException, IOException {
/*    */     try {
/* 70 */       Constructor<T> constr = 
/* 71 */         clazz.getConstructor(new Class[] { InputOnlyStream.class });
/* 72 */       return constr.newInstance(new Object[] { this });
/*    */     }
/* 74 */     catch (NoSuchMethodException e) {
/*    */       
/* 76 */       addWarning("Used the default constructor for class " + 
/* 77 */           clazz.getName());
/*    */       
/* 79 */       IOStreamSaveable iOStreamSaveable = (IOStreamSaveable)clazz.newInstance();
/* 80 */       iOStreamSaveable.process(this);
/* 81 */       return (T)iOStreamSaveable;
/*    */     
/*    */     }
/* 84 */     catch (IllegalAccessException e) {
/* 85 */       throw new ChildConstructionException(e.getMessage(), e);
/*    */     }
/* 87 */     catch (InstantiationException e) {
/* 88 */       e.printStackTrace();
/* 89 */       throw new ChildConstructionException(e.getMessage(), e);
/*    */     }
/* 91 */     catch (InvocationTargetException e) {
/* 92 */       throw new ChildConstructionException(e.getMessage(), e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInputStream() {
/* 99 */     return true;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\InputOnlyStream.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */