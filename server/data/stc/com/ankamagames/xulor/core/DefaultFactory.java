/*     */ package com.ankamagames.xulor.core;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
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
/*     */ public class DefaultFactory<T>
/*     */   implements Factory
/*     */ {
/*     */   private ConverterLibrary m_cvtlib;
/*  30 */   private final ArrayList<Method> m_setters = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  35 */   private final ArrayList<Method> m_getters = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  40 */   private final ArrayList<Method> m_prependers = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  45 */   private final ArrayList<Method> m_appenders = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Class m_template;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  55 */   protected Class[] m_parameterPriority = { Object.class, String.class, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE, Long.TYPE, Byte.TYPE, Integer.TYPE };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultFactory(Class template, ConverterLibrary cvtlib)
/*     */   {
/*  67 */     this.m_cvtlib = cvtlib;
/*  68 */     this.m_template = template;
/*  69 */     registerSetters();
/*  70 */     registerGetters();
/*  71 */     registerPrependers();
/*  72 */     registerAppenders();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultFactory(Class template)
/*     */   {
/*  83 */     this(template, ConverterLibrary.getInstance());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected int priority(Class<?> type)
/*     */   {
/*  90 */     for (int i = 0; i < this.m_parameterPriority.length; i++) {
/*  91 */       if (type.isAssignableFrom(this.m_parameterPriority[i]))
/*  92 */         return i;
/*     */     }
/*  94 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void registerGetters()
/*     */   {
/* 104 */     Method[] methods = this.m_template.getMethods();
/* 105 */     for (int i = 0; i < methods.length; i++) {
/* 106 */       String methodeName = methods[i].getName();
/* 107 */       if (methodeName.startsWith("get")) {
/* 108 */         this.m_getters.add(methods[i]);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void registerSetters()
/*     */   {
/* 117 */     registerAccessors(this.m_setters, "set");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void registerPrependers()
/*     */   {
/* 124 */     registerAccessors(this.m_prependers, "prepend");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void registerAppenders()
/*     */   {
/* 131 */     registerAccessors(this.m_appenders, "append");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T newInstance()
/*     */     throws Exception
/*     */   {
/* 141 */     return (T)this.m_template.newInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T newInstance(Object parameter)
/*     */     throws Exception
/*     */   {
/* 151 */     Class pType = parameter.getClass();
/*     */     
/* 153 */     Constructor[] ctors = this.m_template.getConstructors();
/* 154 */     for (int i = 0; i < ctors.length; i++) {
/* 155 */       Class[] paraTypes = ctors[i].getParameterTypes();
/* 156 */       if ((paraTypes.length > 0) && (paraTypes[0].isAssignableFrom(pType))) {
/* 157 */         return (T)ctors[i].newInstance(new Object[] { parameter });
/*     */       }
/*     */     }
/* 160 */     return (T)this.m_template.newInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public T newInstance(Object[] parameter)
/*     */     throws InstantiationException, IllegalAccessException, InvocationTargetException
/*     */   {
/* 170 */     if (parameter != null) {
/* 171 */       Class[] pTypes = new Class[parameter.length];
/* 172 */       Constructor[] constructors = this.m_template.getConstructors();
/* 173 */       Constructor ctor = null;
/*     */       
/*     */ 
/*     */ 
/* 177 */       for (int i = 0; i < pTypes.length; i++) {
/* 178 */         pTypes[i] = parameter[i].getClass();
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 184 */       for (int i = 0; (ctor == null) && (i < constructors.length); i++) {
/* 185 */         Class[] cParams = constructors[i].getParameterTypes();
/*     */         
/* 187 */         if (cParams.length == pTypes.length)
/*     */         {
/* 189 */           ctor = constructors[i];
/* 190 */           for (int j = 0; (ctor != null) && (j < cParams.length); j++) {
/* 191 */             if (cParams[j].equals(Object.class)) {
/* 192 */               if (!cParams[j].equals(pTypes[j])) {
/* 193 */                 ctor = null;
/*     */               }
/*     */             }
/* 196 */             else if (!cParams[j].isAssignableFrom(pTypes[j])) {
/* 197 */               ctor = null;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 207 */       if (ctor != null) {
/* 208 */         return (T)ctor.newInstance(parameter);
/*     */       }
/* 210 */       throw new IllegalArgumentException("Impossible de trouver de constructeur pour les types : " + pTypes);
/*     */     }
/*     */     
/* 213 */     return (T)this.m_template.newInstance();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Class getTemplate()
/*     */   {
/* 223 */     return this.m_template;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method getSetter(Class template)
/*     */   {
/* 232 */     Method method = null;
/* 233 */     Iterator it = this.m_setters.iterator();
/* 234 */     while ((it != null) && (it.hasNext())) {
/* 235 */       Method m = (Method)it.next();
/* 236 */       Class[] paraTypes = m.getParameterTypes();
/* 237 */       if ((paraTypes != null) && (paraTypes.length > 0) && (template.equals(paraTypes[0]))) {
/* 238 */         method = m;
/* 239 */         break;
/*     */       }
/*     */     }
/* 242 */     return method;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method getSetter(String name)
/*     */   {
/* 251 */     return getAccessor(this.m_setters, name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Method getGetter(String name)
/*     */   {
/* 258 */     return getAccessor(this.m_getters, name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessSetter(String name)
/*     */   {
/* 267 */     return guessAccessor(this.m_setters, name, "set");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessSetter(String name, Class type)
/*     */   {
/* 276 */     return guessAccessor(this.m_setters, name, "set", type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessGetter(String name)
/*     */   {
/* 285 */     return guessAccessor(this.m_getters, name, "get");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessGetter(String name, Class type)
/*     */   {
/* 294 */     return guessAccessor(this.m_getters, name, "get", type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessAppender(String name)
/*     */   {
/* 303 */     return guessAccessor(this.m_appenders, name, "append");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessAppender(String name, Class type)
/*     */   {
/* 312 */     return guessAccessor(this.m_appenders, name, "append", type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Method guessPrepender(String name)
/*     */   {
/* 321 */     return guessAccessor(this.m_prependers, name, "prepend");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Method guessPrepender(String name, Class type)
/*     */   {
/* 328 */     return guessAccessor(this.m_prependers, name, "prepend", type);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Method getAccessor(ArrayList<Method> methodsList, String name)
/*     */   {
/* 340 */     Method method = null;
/* 341 */     Iterator it = methodsList.iterator();
/* 342 */     while ((it != null) && (it.hasNext())) {
/* 343 */       Method m = (Method)it.next();
/* 344 */       if (m.getName().equals(name)) {
/* 345 */         method = m;
/* 346 */         break;
/*     */       }
/*     */     }
/* 349 */     return method;
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
/*     */   private Method guessAccessor(ArrayList<Method> methodsList, String name, String accessorId)
/*     */   {
/* 362 */     Method method = null;
/* 363 */     Iterator it = methodsList.iterator();
/* 364 */     name = (accessorId + name).toLowerCase();
/* 365 */     while ((it != null) && (it.hasNext())) {
/* 366 */       Method m = (Method)it.next();
/* 367 */       if (m.getName().toLowerCase().equals(name)) {
/* 368 */         method = m;
/* 369 */         break;
/*     */       }
/*     */     }
/* 372 */     return method;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Method guessAccessor(ArrayList<Method> methodsList, String name, String accessorId, Class type)
/*     */   {
/* 384 */     Method method = null;
/* 385 */     Iterator it = methodsList.iterator();
/* 386 */     name = (accessorId + name).toLowerCase();
/* 387 */     while ((it != null) && (it.hasNext())) {
/* 388 */       Method m = (Method)it.next();
/* 389 */       if (m.getName().toLowerCase().equals(name)) {
/* 390 */         method = m;
/*     */         
/*     */ 
/* 393 */         if ((type == null) || ((type != null) && (m.getParameterTypes().length > 0) && (m.getParameterTypes()[0].isAssignableFrom(type)))) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 399 */     return method;
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
/*     */   private void registerAccessors(ArrayList<Method> methodsList, String accessorId)
/*     */   {
/* 415 */     Method[] methods = this.m_template.getMethods();
/* 416 */     for (int i = 0; i < methods.length; i++) {
/* 417 */       String methodName = methods[i].getName();
/* 418 */       if ((methodName.startsWith(accessorId)) && 
/* 419 */         (methods[i].getParameterTypes().length == 1))
/*     */       {
/*     */ 
/*     */ 
/* 423 */         Class paraType = methods[i].getParameterTypes()[0];
/*     */         
/*     */ 
/* 426 */         Method m = getAccessor(methodsList, methodName);
/* 427 */         if (m != null)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 432 */           Class<?> cm = m.getDeclaringClass();
/* 433 */           Class cmi = methods[i].getDeclaringClass();
/*     */           
/* 435 */           if (cm.equals(cmi)) {
/* 436 */             Class pType = m.getParameterTypes()[0];
/* 437 */             if (priority(pType) < priority(paraType))
/*     */             {
/* 439 */               methodsList.add(methodsList.indexOf(m), methods[i]);
/*     */             }
/* 441 */           } else if (cm.isAssignableFrom(cmi))
/*     */           {
/* 443 */             methodsList.add(methodsList.indexOf(m), methods[i]);
/*     */           }
/*     */         } else {
/* 446 */           methodsList.add(methods[i]);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\DefaultFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */