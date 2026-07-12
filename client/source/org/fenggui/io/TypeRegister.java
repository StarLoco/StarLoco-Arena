/*    */ package org.fenggui.io;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TypeRegister
/*    */ {
/* 31 */   private final Map<String, Class<? extends IOStreamSaveable>> namedTypes = new HashMap<String, Class<? extends IOStreamSaveable>>();
/*    */ 
/*    */   
/*    */   public void register(String name, Class<? extends IOStreamSaveable> type) {
/* 35 */     this.namedTypes.put(name, type);
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 39 */     return this.namedTypes.isEmpty();
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
/*    */   public String getName(Class<? extends IOStreamSaveable> clazz) {
/* 51 */     for (String s : this.namedTypes.keySet()) {
/*    */       
/* 53 */       if (clazz.equals(this.namedTypes.get(s))) return s;
/*    */     
/*    */     } 
/*    */     
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<String> getNames() {
/* 62 */     return this.namedTypes.keySet();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends IOStreamSaveable> getType(String name) {
/* 67 */     return this.namedTypes.get(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsType(String name) {
/* 72 */     return this.namedTypes.containsKey(name);
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\io\TypeRegister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */