/*     */ package com.ankamagames.framework.script;
/*     */ 
/*     */ import org.apache.log4j.Logger;
/*     */ import org.keplerproject.luajava.JavaFunction;
/*     */ import org.keplerproject.luajava.LuaException;
/*     */ import org.keplerproject.luajava.LuaState;
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
/*     */ public abstract class JavaFunctionEx
/*     */   extends JavaFunction
/*     */ {
/*  20 */   protected static final Logger m_logger = Logger.getLogger(JavaFunctionEx.class);
/*     */   
/*     */   protected static final int INCORRECT_PARAM_COUNT = -1;
/*     */   
/*  24 */   protected int m_returnValueCount = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public JavaFunctionEx(LuaState luaState)
/*     */   {
/*  33 */     super(luaState);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract String getName();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  46 */     return "not yet implemented";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public abstract LuaScriptParameterDescriptor[] getParameterDescriptors();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void register()
/*     */     throws LuaException
/*     */   {
/*  60 */     super.register(getName());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int execute()
/*     */     throws LuaException
/*     */   {
/*  69 */     this.m_returnValueCount = 0;
/*     */     
/*  71 */     int paramCount = checkParam();
/*  72 */     run(paramCount);
/*     */     
/*  74 */     return this.m_returnValueCount;
/*     */   }
/*     */   
/*     */ 
/*     */   protected abstract void run(int paramInt)
/*     */     throws LuaException;
/*     */   
/*     */ 
/*     */   protected int checkParam()
/*     */     throws LuaException
/*     */   {
/*  85 */     LuaScriptParameterDescriptor[] paramDescr = getParameterDescriptors();
/*  86 */     int paramCount = paramDescr != null ? paramDescr.length : 0;
/*     */     
/*  88 */     int minCount = 0;
/*  89 */     int maxCount = 0;
/*  90 */     for (int i = 0; i < paramCount; i++) {
/*  91 */       maxCount++;
/*  92 */       if (!paramDescr[i].isOptional()) {
/*  93 */         minCount++;
/*     */       }
/*  95 */       else if (paramDescr[i].getType() == LuaScriptParameterType.BLOOPS) {
/*  96 */         maxCount = Integer.MAX_VALUE;
/*     */       }
/*     */     }
/*  99 */     if (maxCount < minCount) {
/* 100 */       maxCount = minCount;
/*     */     }
/* 102 */     return checkParamCount(minCount, maxCount);
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
/*     */   private int checkParamCount(int paramCountMin, int paramCountMax)
/*     */     throws LuaException
/*     */   {
/* 118 */     int count = this.L.getTop() - 1;
/* 119 */     if ((count >= paramCountMin) && (count <= paramCountMax))
/* 120 */       return count;
/*     */     String msg;
/*     */     String msg;
/* 123 */     if (paramCountMin == paramCountMax) {
/* 124 */       msg = String.format("(attendu: %d, lu: %d)", new Object[] { Integer.valueOf(paramCountMin), Integer.valueOf(count) });
/*     */     } else { String msg;
/* 126 */       if (paramCountMax == Integer.MAX_VALUE) {
/* 127 */         msg = String.format("(attendu au moins: %d, lu: %d)", new Object[] { Integer.valueOf(paramCountMin), Integer.valueOf(count) });
/*     */       } else {
/* 129 */         msg = String.format("(attendu: %d-%d, lu: %d)", new Object[] { Integer.valueOf(paramCountMin), Integer.valueOf(paramCountMax), Integer.valueOf(count) });
/*     */       }
/*     */     }
/* 132 */     throw new LuaException(getName() + ": nombre de paramètre incorrect " + msg);
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
/*     */   private boolean checkDefinitionParamType(int i, LuaScriptParameterType type)
/*     */     throws LuaException
/*     */   {
/* 148 */     LuaScriptParameterDescriptor[] paramDescr = getParameterDescriptors();
/* 149 */     int paramCount = paramDescr != null ? paramDescr.length : 0;
/* 150 */     if (i < paramCount) {
/* 151 */       LuaScriptParameterType definitionType = paramDescr[i].getType();
/* 152 */       if ((definitionType != LuaScriptParameterType.BLOOPS) && (definitionType != type)) {
/* 153 */         String msg = String.format(getName() + ": mauvais type d'argument #%d: (definition: %s, fonction: %s)", new Object[] { Integer.valueOf(i), definitionType, type });
/* 154 */         throw new LuaException(msg);
/*     */       }
/*     */     }
/* 157 */     return true;
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
/*     */   public Object[] getParams(int begin, int end)
/*     */     throws LuaException
/*     */   {
/* 174 */     Object[] args = (Object[])null;
/* 175 */     if (checkDefinitionParamType(begin, LuaScriptParameterType.BLOOPS)) {
/* 176 */       int count = end - begin;
/* 177 */       args = new Object[count];
/* 178 */       for (int i = 0; i < count; i++) {
/* 179 */         args[i] = this.L.toJavaObject(begin + i + 2);
/*     */       }
/*     */     }
/* 182 */     return args;
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
/*     */   public int getParamInt(int i)
/*     */     throws LuaException
/*     */   {
/* 196 */     if ((checkDefinitionParamType(i, LuaScriptParameterType.INTEGER)) && 
/* 197 */       (!this.L.isNumber(i + 2))) {
/* 198 */       throw new LuaException(getName() + ": Argument #" + i + " n'est pas un entier.(lu:" + this.L.typeName(i + 2) + ")");
/*     */     }
/*     */     
/* 201 */     return this.L.toInteger(i + 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public double getParamDouble(int i)
/*     */     throws LuaException
/*     */   {
/* 214 */     if ((checkDefinitionParamType(i, LuaScriptParameterType.NUMBER)) && 
/* 215 */       (this.L.isNumber(i + 2))) {
/* 216 */       return this.L.toNumber(i + 2);
/*     */     }
/*     */     
/* 219 */     throw new LuaException(getName() + ": Argument #" + i + " n'est pas un decimal.(lu:" + this.L.typeName(i + 2) + ")");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getParamString(int i)
/*     */     throws LuaException
/*     */   {
/* 232 */     if ((checkDefinitionParamType(i, LuaScriptParameterType.STRING)) && 
/* 233 */       (this.L.isString(i + 2))) {
/* 234 */       return this.L.toString(i + 2);
/*     */     }
/*     */     
/* 237 */     throw new LuaException(getName() + ": Argument #" + i + " n'est pas une chaine de caractere." + this.L.typeName(i + 2) + ")");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getParamBool(int i)
/*     */     throws LuaException
/*     */   {
/* 247 */     if ((checkDefinitionParamType(i, LuaScriptParameterType.BOOLEAN)) && 
/* 248 */       (this.L.isBoolean(i + 2))) {
/* 249 */       return this.L.toBoolean(i + 2);
/*     */     }
/*     */     
/* 252 */     throw new LuaException(getName() + ": Argument #" + i + " n'est pas un booleen." + this.L.typeName(i + 2) + ")");
/*     */   }
/*     */   
/*     */   protected void addReturnValue(boolean b) {
/* 256 */     this.L.pushBoolean(b);
/* 257 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addReturnValue(int i) {
/* 261 */     this.L.pushNumber(i);
/* 262 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addReturnValue(JavaFunction jf) throws LuaException {
/* 266 */     this.L.pushJavaFunction(jf);
/* 267 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addReturnValue(Object o) {
/* 271 */     this.L.pushJavaObject(o);
/* 272 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addNilReturnValue() {
/* 276 */     this.L.pushNil();
/* 277 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addReturnValue(double d) {
/* 281 */     this.L.pushNumber(d);
/* 282 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addReturnValue(byte[] b) {
/* 286 */     this.L.pushString(b);
/* 287 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */   protected void addReturnValue(String s) {
/* 291 */     this.L.pushString(s);
/* 292 */     this.m_returnValueCount += 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected LuaScript getScriptObject(LuaState L)
/*     */     throws LuaException
/*     */   {
/* 303 */     L.getGlobal("script");
/* 304 */     LuaScript s = (LuaScript)L.toJavaObject(-1);
/* 305 */     L.pop(1);
/* 306 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\framework\script\JavaFunctionEx.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */