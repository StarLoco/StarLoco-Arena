/*     */ package org.keplerproject.luajava;
/*     */ 
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.util.StringTokenizer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LuaObject
/*     */ {
/*     */   protected Integer ref;
/*     */   protected LuaState L;
/*     */   
/*     */   protected LuaObject(LuaState paramLuaState, String paramString) {
/*  68 */     synchronized (paramLuaState) {
/*     */       
/*  70 */       this.L = paramLuaState;
/*  71 */       paramLuaState.getGlobal(paramString);
/*  72 */       registerValue(-1);
/*  73 */       paramLuaState.pop(1);
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
/*     */   protected LuaObject(LuaObject paramLuaObject, String paramString) throws LuaException {
/*  87 */     synchronized (paramLuaObject.getLuaState()) {
/*     */       
/*  89 */       this.L = paramLuaObject.getLuaState();
/*     */       
/*  91 */       if (!paramLuaObject.isTable() && !paramLuaObject.isUserdata())
/*     */       {
/*  93 */         throw new LuaException("Object parent should be a table or userdata .");
/*     */       }
/*     */       
/*  96 */       paramLuaObject.push();
/*  97 */       this.L.pushString(paramString);
/*  98 */       this.L.getTable(-2);
/*  99 */       this.L.remove(-2);
/* 100 */       registerValue(-1);
/* 101 */       this.L.pop(1);
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
/*     */   protected LuaObject(LuaObject paramLuaObject, Number paramNumber) throws LuaException {
/* 117 */     synchronized (paramLuaObject.getLuaState()) {
/*     */       
/* 119 */       this.L = paramLuaObject.getLuaState();
/* 120 */       if (!paramLuaObject.isTable() && !paramLuaObject.isUserdata()) {
/* 121 */         throw new LuaException("Object parent should be a table or userdata .");
/*     */       }
/* 123 */       paramLuaObject.push();
/* 124 */       this.L.pushNumber(paramNumber.doubleValue());
/* 125 */       this.L.getTable(-2);
/* 126 */       this.L.remove(-2);
/* 127 */       registerValue(-1);
/* 128 */       this.L.pop(1);
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
/*     */   protected LuaObject(LuaObject paramLuaObject1, LuaObject paramLuaObject2) throws LuaException {
/* 144 */     if (paramLuaObject1.getLuaState() != paramLuaObject2.getLuaState())
/* 145 */       throw new LuaException("LuaStates must be the same!"); 
/* 146 */     synchronized (paramLuaObject1.getLuaState()) {
/*     */       
/* 148 */       if (!paramLuaObject1.isTable() && !paramLuaObject1.isUserdata()) {
/* 149 */         throw new LuaException("Object parent should be a table or userdata .");
/*     */       }
/* 151 */       this.L = paramLuaObject1.getLuaState();
/*     */       
/* 153 */       paramLuaObject1.push();
/* 154 */       paramLuaObject2.push();
/* 155 */       this.L.getTable(-2);
/* 156 */       this.L.remove(-2);
/* 157 */       registerValue(-1);
/* 158 */       this.L.pop(1);
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
/*     */   protected LuaObject(LuaState paramLuaState, int paramInt) {
/* 171 */     synchronized (paramLuaState) {
/*     */       
/* 173 */       this.L = paramLuaState;
/*     */       
/* 175 */       registerValue(paramInt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LuaState getLuaState() {
/* 184 */     return this.L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerValue(int paramInt) {
/* 195 */     synchronized (this.L) {
/*     */       
/* 197 */       this.L.pushValue(paramInt);
/* 198 */       int i = this.L.Lref(LuaState.LUA_REGISTRYINDEX.intValue());
/* 199 */       this.ref = new Integer(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() {
/*     */     try {
/* 207 */       synchronized (this.L) {
/*     */         
/* 209 */         if (this.L.getCPtrPeer() != 0L) {
/* 210 */           this.L.LunRef(LuaState.LUA_REGISTRYINDEX.intValue(), this.ref.intValue());
/*     */         }
/*     */       } 
/* 213 */     } catch (Exception exception) {
/*     */       
/* 215 */       System.err.println("Unable to release object " + this.ref);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void push() {
/* 224 */     this.L.rawGetI(LuaState.LUA_REGISTRYINDEX.intValue(), this.ref.intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNil() {
/* 229 */     synchronized (this.L) {
/*     */       
/* 231 */       push();
/* 232 */       boolean bool = this.L.isNil(-1);
/* 233 */       this.L.pop(1);
/* 234 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBoolean() {
/* 240 */     synchronized (this.L) {
/*     */       
/* 242 */       push();
/* 243 */       boolean bool = this.L.isBoolean(-1);
/* 244 */       this.L.pop(1);
/* 245 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNumber() {
/* 251 */     synchronized (this.L) {
/*     */       
/* 253 */       push();
/* 254 */       boolean bool = this.L.isNumber(-1);
/* 255 */       this.L.pop(1);
/* 256 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isString() {
/* 262 */     synchronized (this.L) {
/*     */       
/* 264 */       push();
/* 265 */       boolean bool = this.L.isString(-1);
/* 266 */       this.L.pop(1);
/* 267 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFunction() {
/* 273 */     synchronized (this.L) {
/*     */       
/* 275 */       push();
/* 276 */       boolean bool = this.L.isFunction(-1);
/* 277 */       this.L.pop(1);
/* 278 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isJavaObject() {
/* 284 */     synchronized (this.L) {
/*     */       
/* 286 */       push();
/* 287 */       boolean bool = this.L.isObject(-1);
/* 288 */       this.L.pop(1);
/* 289 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isJavaFunction() {
/* 295 */     synchronized (this.L) {
/*     */       
/* 297 */       push();
/* 298 */       boolean bool = this.L.isJavaFunction(-1);
/* 299 */       this.L.pop(1);
/* 300 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTable() {
/* 306 */     synchronized (this.L) {
/*     */       
/* 308 */       push();
/* 309 */       boolean bool = this.L.isTable(-1);
/* 310 */       this.L.pop(1);
/* 311 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUserdata() {
/* 317 */     synchronized (this.L) {
/*     */       
/* 319 */       push();
/* 320 */       boolean bool = this.L.isUserdata(-1);
/* 321 */       this.L.pop(1);
/* 322 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int type() {
/* 328 */     synchronized (this.L) {
/*     */       
/* 330 */       push();
/* 331 */       int i = this.L.type(-1);
/* 332 */       this.L.pop(1);
/* 333 */       return i;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getBoolean() {
/* 339 */     synchronized (this.L) {
/*     */       
/* 341 */       push();
/* 342 */       boolean bool = this.L.toBoolean(-1);
/* 343 */       this.L.pop(1);
/* 344 */       return bool;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getNumber() {
/* 350 */     synchronized (this.L) {
/*     */       
/* 352 */       push();
/* 353 */       double d = this.L.toNumber(-1);
/* 354 */       this.L.pop(1);
/* 355 */       return d;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() {
/* 361 */     synchronized (this.L) {
/*     */       
/* 363 */       push();
/* 364 */       String str = this.L.toString(-1);
/* 365 */       this.L.pop(1);
/* 366 */       return str;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getObject() throws LuaException {
/* 372 */     synchronized (this.L) {
/*     */       
/* 374 */       push();
/* 375 */       Object object = this.L.getObjectFromUserdata(-1);
/* 376 */       this.L.pop(1);
/* 377 */       return object;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LuaObject getField(String paramString) throws LuaException {
/* 387 */     return this.L.getLuaObject(this, paramString);
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
/*     */   public Object[] call(Object[] paramArrayOfObject, int paramInt) throws LuaException {
/* 402 */     synchronized (this.L) {
/*     */       boolean bool;
/* 404 */       if (!isFunction() && !isTable() && !isUserdata()) {
/* 405 */         throw new LuaException("Invalid object. Not a function, table or userdata .");
/*     */       }
/* 407 */       int i = this.L.getTop();
/* 408 */       push();
/*     */       
/* 410 */       if (paramArrayOfObject != null) {
/*     */         
/* 412 */         bool = paramArrayOfObject.length;
/* 413 */         for (byte b = 0; b < bool; b++) {
/*     */           
/* 415 */           Object object = paramArrayOfObject[b];
/* 416 */           this.L.pushObjectValue(object);
/*     */         } 
/*     */       } else {
/*     */         
/* 420 */         bool = false;
/*     */       } 
/* 422 */       int j = this.L.pcall(bool, paramInt, 0);
/*     */       
/* 424 */       if (j != 0) {
/*     */         String str;
/*     */         
/* 427 */         if (this.L.isString(-1)) {
/*     */           
/* 429 */           str = this.L.toString(-1);
/* 430 */           this.L.pop(1);
/*     */         } else {
/*     */           
/* 433 */           str = "";
/*     */         } 
/* 435 */         if (j == LuaState.LUA_ERRRUN.intValue()) {
/*     */           
/* 437 */           str = "Runtime error. " + str;
/*     */         }
/* 439 */         else if (j == LuaState.LUA_ERRMEM.intValue()) {
/*     */           
/* 441 */           str = "Memory allocation error. " + str;
/*     */         }
/* 443 */         else if (j == LuaState.LUA_ERRERR.intValue()) {
/*     */           
/* 445 */           str = "Error while running the error handler function. " + str;
/*     */         }
/*     */         else {
/*     */           
/* 449 */           str = "Lua Error code " + j + ". " + str;
/*     */         } 
/*     */         
/* 452 */         throw new LuaException(str);
/*     */       } 
/*     */       
/* 455 */       if (paramInt == LuaState.LUA_MULTRET.intValue())
/* 456 */         paramInt = this.L.getTop() - i; 
/* 457 */       if (this.L.getTop() - i < paramInt)
/*     */       {
/* 459 */         throw new LuaException("Invalid Number of Results .");
/*     */       }
/*     */       
/* 462 */       Object[] arrayOfObject = new Object[paramInt];
/*     */       
/* 464 */       for (int k = paramInt; k > 0; k--) {
/*     */         
/* 466 */         arrayOfObject[k - 1] = this.L.toJavaObject(-1);
/* 467 */         this.L.pop(1);
/*     */       } 
/* 469 */       return arrayOfObject;
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
/*     */   public Object call(Object[] paramArrayOfObject) throws LuaException {
/* 483 */     return call(paramArrayOfObject, 1)[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 488 */     synchronized (this.L) {
/*     */ 
/*     */ 
/*     */       
/* 492 */       if (isNil())
/* 493 */         return "nil"; 
/* 494 */       if (isBoolean())
/* 495 */         return String.valueOf(getBoolean()); 
/* 496 */       if (isNumber())
/* 497 */         return String.valueOf(getNumber()); 
/* 498 */       if (isString())
/* 499 */         return getString(); 
/* 500 */       if (isFunction())
/* 501 */         return "Lua Function"; 
/* 502 */       if (isJavaObject())
/* 503 */         return getObject().toString(); 
/* 504 */       if (isUserdata())
/* 505 */         return "Userdata"; 
/* 506 */       if (isTable())
/* 507 */         return "Lua Table"; 
/* 508 */       if (isJavaFunction()) {
/* 509 */         return "Java Function";
/*     */       }
/* 511 */       return null;
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
/*     */   public Object createProxy(String paramString) throws ClassNotFoundException, LuaException {
/* 528 */     synchronized (this.L) {
/*     */       
/* 530 */       if (!isTable()) {
/* 531 */         throw new LuaException("Invalid Object. Must be Table.");
/*     */       }
/* 533 */       StringTokenizer stringTokenizer = new StringTokenizer(paramString, ",");
/* 534 */       Class[] arrayOfClass = new Class[stringTokenizer.countTokens()];
/* 535 */       for (byte b = 0; stringTokenizer.hasMoreTokens(); b++) {
/* 536 */         arrayOfClass[b] = Class.forName(stringTokenizer.nextToken());
/*     */       }
/* 538 */       LuaInvocationHandler luaInvocationHandler = new LuaInvocationHandler(this);
/*     */       
/* 540 */       return Proxy.newProxyInstance(getClass().getClassLoader(), arrayOfClass, luaInvocationHandler);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\LuaObject.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */