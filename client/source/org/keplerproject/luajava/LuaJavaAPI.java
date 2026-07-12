/*     */ package org.keplerproject.luajava;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public final class LuaJavaAPI
/*     */ {
/*     */   public static int objectIndex(int paramInt, Object paramObject, String paramString) throws LuaException {
/*  55 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/*  57 */     synchronized (luaState) {
/*     */       Class<?> clazz; Object object;
/*  59 */       int i = luaState.getTop();
/*     */       
/*  61 */       Object[] arrayOfObject = new Object[i - 1];
/*     */ 
/*     */ 
/*     */       
/*  65 */       if (paramObject instanceof Class) {
/*     */         
/*  67 */         clazz = (Class)paramObject;
/*     */       }
/*     */       else {
/*     */         
/*  71 */         clazz = paramObject.getClass();
/*     */       } 
/*     */       
/*  74 */       Method[] arrayOfMethod = clazz.getMethods();
/*  75 */       Method method = null;
/*     */ 
/*     */       
/*  78 */       for (byte b = 0; b < arrayOfMethod.length; b++) {
/*     */         
/*  80 */         if (arrayOfMethod[b].getName().equals(paramString)) {
/*     */ 
/*     */ 
/*     */           
/*  84 */           Class[] arrayOfClass = arrayOfMethod[b].getParameterTypes();
/*  85 */           if (arrayOfClass.length == i - 1) {
/*     */ 
/*     */             
/*  88 */             boolean bool = true;
/*     */             
/*  90 */             for (byte b1 = 0; b1 < arrayOfClass.length; b1++) {
/*     */ 
/*     */               
/*     */               try {
/*  94 */                 arrayOfObject[b1] = compareTypes(luaState, arrayOfClass[b1], b1 + 2);
/*     */               }
/*  96 */               catch (Exception exception) {
/*     */                 
/*  98 */                 bool = false;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 103 */             if (bool) {
/*     */               
/* 105 */               method = arrayOfMethod[b];
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 112 */       if (method == null)
/*     */       {
/* 114 */         throw new LuaException("Invalid method call. No such method.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 120 */         if (Modifier.isPublic(method.getModifiers()))
/*     */         {
/* 122 */           method.setAccessible(true);
/*     */         }
/*     */         
/* 125 */         if (paramObject instanceof Class)
/*     */         {
/* 127 */           object = method.invoke((Object)null, arrayOfObject);
/*     */         }
/*     */         else
/*     */         {
/* 131 */           object = method.invoke(paramObject, arrayOfObject);
/*     */         }
/*     */       
/* 134 */       } catch (Exception exception) {
/*     */         
/* 136 */         throw new LuaException(exception);
/*     */       } 
/*     */ 
/*     */       
/* 140 */       if (object == null)
/*     */       {
/* 142 */         return 0;
/*     */       }
/*     */ 
/*     */       
/* 146 */       luaState.pushObjectValue(object);
/*     */       
/* 148 */       return 1;
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
/*     */   public static int classIndex(int paramInt, Class paramClass, String paramString) throws LuaException {
/* 166 */     synchronized (LuaStateFactory.getExistingState(paramInt)) {
/*     */ 
/*     */ 
/*     */       
/* 170 */       int i = checkField(paramInt, paramClass, paramString);
/*     */       
/* 172 */       if (i != 0)
/*     */       {
/* 174 */         return 1;
/*     */       }
/*     */       
/* 177 */       i = checkMethod(paramInt, paramClass, paramString);
/*     */       
/* 179 */       if (i != 0)
/*     */       {
/* 181 */         return 2;
/*     */       }
/*     */       
/* 184 */       return 0;
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
/*     */   public static int javaNewInstance(int paramInt, String paramString) throws LuaException {
/* 199 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/* 201 */     synchronized (luaState) {
/*     */       Class<?> clazz;
/*     */ 
/*     */       
/*     */       try {
/* 206 */         clazz = Class.forName(paramString);
/*     */       }
/* 208 */       catch (ClassNotFoundException classNotFoundException) {
/*     */         
/* 210 */         throw new LuaException(classNotFoundException);
/*     */       } 
/* 212 */       Object object = getObjInstance(luaState, clazz);
/*     */       
/* 214 */       luaState.pushJavaObject(object);
/*     */       
/* 216 */       return 1;
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
/*     */   public static int javaNew(int paramInt, Class paramClass) throws LuaException {
/* 230 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/* 232 */     synchronized (luaState) {
/*     */       
/* 234 */       Object object = getObjInstance(luaState, paramClass);
/*     */       
/* 236 */       luaState.pushJavaObject(object);
/*     */       
/* 238 */       return 1;
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
/*     */   public static int javaLoadLib(int paramInt, String paramString1, String paramString2) throws LuaException {
/* 254 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/* 256 */     synchronized (luaState) {
/*     */       Class<?> clazz;
/*     */ 
/*     */       
/*     */       try {
/* 261 */         clazz = Class.forName(paramString1);
/*     */       }
/* 263 */       catch (ClassNotFoundException classNotFoundException) {
/*     */         
/* 265 */         throw new LuaException(classNotFoundException);
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 270 */         Method method = clazz.getMethod(paramString2, new Class[] { LuaState.class });
/* 271 */         Object object = method.invoke((Object)null, new Object[] { luaState });
/*     */         
/* 273 */         if (object != null && object instanceof Integer)
/*     */         {
/* 275 */           return ((Integer)object).intValue();
/*     */         }
/*     */         
/* 278 */         return 0;
/*     */       }
/* 280 */       catch (Exception exception) {
/*     */         
/* 282 */         throw new LuaException("Error on calling method. Library could not be loaded. " + exception.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object getObjInstance(LuaState paramLuaState, Class paramClass) throws LuaException {
/* 290 */     synchronized (paramLuaState) {
/*     */       
/* 292 */       int i = paramLuaState.getTop();
/*     */       
/* 294 */       Object[] arrayOfObject = new Object[i - 1];
/*     */       
/* 296 */       Constructor[] arrayOfConstructor = (Constructor[])paramClass.getConstructors();
/* 297 */       Constructor<byte> constructor = null;
/*     */       
/*     */       byte b;
/* 300 */       for (b = 0; b < arrayOfConstructor.length; b++) {
/*     */         
/* 302 */         Class[] arrayOfClass = arrayOfConstructor[b].getParameterTypes();
/* 303 */         if (arrayOfClass.length == i - 1) {
/*     */ 
/*     */           
/* 306 */           boolean bool = true;
/*     */           
/* 308 */           for (byte b1 = 0; b1 < arrayOfClass.length; b1++) {
/*     */ 
/*     */             
/*     */             try {
/* 312 */               arrayOfObject[b1] = compareTypes(paramLuaState, arrayOfClass[b1], b1 + 2);
/*     */             }
/* 314 */             catch (Exception exception) {
/*     */               
/* 316 */               bool = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 321 */           if (bool) {
/*     */             
/* 323 */             constructor = arrayOfConstructor[b];
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 330 */       if (constructor == null)
/*     */       {
/* 332 */         throw new LuaException("Invalid method call. No such method.");
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 338 */         b = constructor.newInstance(arrayOfObject);
/*     */       }
/* 340 */       catch (Exception exception) {
/*     */         
/* 342 */         throw new LuaException(exception);
/*     */       } 
/*     */       
/* 345 */       if (b == null)
/*     */       {
/* 347 */         throw new LuaException("Couldn't instantiate java Object");
/*     */       }
/*     */       
/* 350 */       return b;
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
/*     */   public static int checkField(int paramInt, Object paramObject, String paramString) throws LuaException {
/* 365 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/* 367 */     synchronized (luaState) {
/*     */       Class<?> clazz;
/* 369 */       Field field = null;
/*     */ 
/*     */       
/* 372 */       if (paramObject instanceof Class) {
/*     */         
/* 374 */         clazz = (Class)paramObject;
/*     */       }
/*     */       else {
/*     */         
/* 378 */         clazz = paramObject.getClass();
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 383 */         field = clazz.getField(paramString);
/*     */       }
/* 385 */       catch (Exception exception) {
/*     */         
/* 387 */         return 0;
/*     */       } 
/*     */       
/* 390 */       if (field == null)
/*     */       {
/* 392 */         return 0;
/*     */       }
/*     */       
/* 395 */       Object object = null;
/*     */       
/*     */       try {
/* 398 */         object = field.get(paramObject);
/*     */       }
/* 400 */       catch (Exception exception) {
/*     */         
/* 402 */         return 0;
/*     */       } 
/*     */       
/* 405 */       if (paramObject == null)
/*     */       {
/* 407 */         return 0;
/*     */       }
/*     */       
/* 410 */       luaState.pushObjectValue(object);
/*     */       
/* 412 */       return 1;
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
/*     */   public static int checkMethod(int paramInt, Object paramObject, String paramString) {
/* 426 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/* 428 */     synchronized (luaState) {
/*     */       Class<?> clazz;
/*     */ 
/*     */       
/* 432 */       if (paramObject instanceof Class) {
/*     */         
/* 434 */         clazz = (Class)paramObject;
/*     */       }
/*     */       else {
/*     */         
/* 438 */         clazz = paramObject.getClass();
/*     */       } 
/*     */       
/* 441 */       Method[] arrayOfMethod = clazz.getMethods();
/*     */       
/* 443 */       for (byte b = 0; b < arrayOfMethod.length; b++) {
/*     */         
/* 445 */         if (arrayOfMethod[b].getName().equals(paramString)) {
/* 446 */           return 1;
/*     */         }
/*     */       } 
/* 449 */       return 0;
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
/*     */   public static int createProxyObject(int paramInt, String paramString) throws LuaException {
/* 464 */     LuaState luaState = LuaStateFactory.getExistingState(paramInt);
/*     */     
/* 466 */     synchronized (luaState) {
/*     */ 
/*     */       
/*     */       try {
/* 470 */         if (!luaState.isTable(2)) {
/* 471 */           throw new LuaException("Parameter is not a table. Can't create proxy.");
/*     */         }
/*     */         
/* 474 */         LuaObject luaObject = luaState.getLuaObject(2);
/*     */         
/* 476 */         Object object = luaObject.createProxy(paramString);
/* 477 */         luaState.pushJavaObject(object);
/*     */       }
/* 479 */       catch (Exception exception) {
/*     */         
/* 481 */         throw new LuaException(exception);
/*     */       } 
/*     */       
/* 484 */       return 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Object compareTypes(LuaState paramLuaState, Class<boolean> paramClass, int paramInt) throws LuaException {
/* 491 */     boolean bool = true;
/* 492 */     Boolean bool1 = null;
/*     */     
/* 494 */     if (paramLuaState.isBoolean(paramInt)) {
/*     */       
/* 496 */       if (paramClass.isPrimitive()) {
/*     */         
/* 498 */         if (paramClass != boolean.class)
/*     */         {
/* 500 */           bool = false;
/*     */         }
/*     */       }
/* 503 */       else if (!paramClass.isAssignableFrom(Boolean.class)) {
/*     */         
/* 505 */         bool = false;
/*     */       } 
/* 507 */       bool1 = new Boolean(paramLuaState.toBoolean(paramInt));
/*     */     }
/* 509 */     else if (paramLuaState.type(paramInt) == LuaState.LUA_TSTRING.intValue()) {
/*     */       
/* 511 */       if (!paramClass.isAssignableFrom(String.class))
/*     */       {
/* 513 */         bool = false;
/*     */       }
/*     */       else
/*     */       {
/* 517 */         String str = paramLuaState.toString(paramInt);
/*     */       }
/*     */     
/* 520 */     } else if (paramLuaState.isFunction(paramInt)) {
/*     */       
/* 522 */       if (!paramClass.isAssignableFrom(LuaObject.class))
/*     */       {
/* 524 */         bool = false;
/*     */       }
/*     */       else
/*     */       {
/* 528 */         LuaObject luaObject = paramLuaState.getLuaObject(paramInt);
/*     */       }
/*     */     
/* 531 */     } else if (paramLuaState.isTable(paramInt)) {
/*     */       
/* 533 */       if (!paramClass.isAssignableFrom(LuaObject.class))
/*     */       {
/* 535 */         bool = false;
/*     */       }
/*     */       else
/*     */       {
/* 539 */         LuaObject luaObject = paramLuaState.getLuaObject(paramInt);
/*     */       }
/*     */     
/* 542 */     } else if (paramLuaState.type(paramInt) == LuaState.LUA_TNUMBER.intValue()) {
/*     */       
/* 544 */       Double double_ = new Double(paramLuaState.toNumber(paramInt));
/*     */       
/* 546 */       Number number = LuaState.convertLuaNumber(double_, paramClass);
/* 547 */       if (number == null)
/*     */       {
/* 549 */         bool = false;
/*     */       }
/*     */     }
/* 552 */     else if (paramLuaState.isUserdata(paramInt)) {
/*     */       
/* 554 */       if (paramLuaState.isObject(paramInt))
/*     */       {
/* 556 */         Object object = paramLuaState.getObjectFromUserdata(paramInt);
/* 557 */         if (!paramClass.isAssignableFrom(object.getClass()))
/*     */         {
/* 559 */           bool = false;
/*     */         }
/*     */         else
/*     */         {
/* 563 */           Object object1 = object;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 568 */       else if (!paramClass.isAssignableFrom(LuaObject.class))
/*     */       {
/* 570 */         bool = false;
/*     */       }
/*     */       else
/*     */       {
/* 574 */         LuaObject luaObject = paramLuaState.getLuaObject(paramInt);
/*     */       }
/*     */     
/*     */     }
/* 578 */     else if (paramLuaState.isNil(paramInt)) {
/*     */       
/* 580 */       bool1 = null;
/*     */     }
/*     */     else {
/*     */       
/* 584 */       throw new LuaException("Invalid Parameters.");
/*     */     } 
/*     */     
/* 587 */     if (!bool)
/*     */     {
/* 589 */       throw new LuaException("Invalid Parameter.");
/*     */     }
/*     */     
/* 592 */     return bool1;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\keplerproject\luajava\LuaJavaAPI.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */