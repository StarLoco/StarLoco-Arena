/*    */ package com.ankamagames.framework.script;
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
/*    */ public class LuaScriptParameterDescriptor
/*    */ {
/*    */   private String m_name;
/* 15 */   private LuaScriptParameterType m_type = LuaScriptParameterType.OBJECT;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean m_optional = true;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterDescriptor(String name, LuaScriptParameterType type, boolean optional) {
/* 27 */     this.m_name = name;
/* 28 */     this.m_type = type;
/* 29 */     this.m_optional = optional;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName() {
/* 36 */     return this.m_name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOptional() {
/* 43 */     return this.m_optional;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LuaScriptParameterType getType() {
/* 50 */     return this.m_type;
/*    */   }
/*    */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\LuaScriptParameterDescriptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */