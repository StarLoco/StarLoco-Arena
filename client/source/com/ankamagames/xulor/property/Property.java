/*     */ package com.ankamagames.xulor.property;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Factory;
/*     */ import com.ankamagames.xulor.core.renderer.ResultProviderParent;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.util.MethodUtil;
/*     */ import com.ankamagames.xulor.util.PrimitiveConverter;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
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
/*     */ public class Property
/*     */   implements ResultProviderParent
/*     */ {
/*  27 */   private static Logger m_logger = Logger.getLogger(Property.class);
/*     */   
/*     */   private String m_name;
/*  30 */   private Object m_value = null;
/*  31 */   private final ArrayList<PropertyClient> m_clients = new ArrayList<PropertyClient>();
/*     */   
/*  33 */   private ConcurrentLinkedQueue<AsynchronousPropertyChange> m_asynchronousPropertyChanges = new ConcurrentLinkedQueue<AsynchronousPropertyChange>();
/*  34 */   private ArrayList<PropertyClient> m_modifiedClients = new ArrayList<PropertyClient>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Property(String name) {
/*  42 */     this.m_name = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  49 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getValue() {
/*  56 */     return this.m_value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString() {
/*  63 */     return PrimitiveConverter.getString(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean() {
/*  70 */     return PrimitiveConverter.getBoolean(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInt() {
/*  77 */     return PrimitiveConverter.getInteger(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble() {
/*  84 */     return PrimitiveConverter.getDouble(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloat() {
/*  91 */     return PrimitiveConverter.getFloat(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong() {
/*  98 */     return PrimitiveConverter.getLong(this.m_value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 106 */     return (this.m_value == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Object getValue(String fieldName) {
/* 113 */     if (this.m_value instanceof FieldProvider && fieldName != null) {
/* 114 */       FieldProvider provider = (FieldProvider)this.m_value;
/* 115 */       return provider.getFieldValue(fieldName);
/*     */     } 
/* 117 */     return this.m_value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object getFieldObjectValue(String fieldName) {
/* 125 */     return getValue(fieldName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFieldStringValue(String fieldName) {
/* 132 */     return PrimitiveConverter.getString(getValue(fieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getFieldBooleanValue(String fieldName) {
/* 139 */     return PrimitiveConverter.getBoolean(getValue(fieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFieldIntValue(String fieldName) {
/* 146 */     return PrimitiveConverter.getInteger(getValue(fieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFieldLongValue(String fieldName) {
/* 153 */     return PrimitiveConverter.getLong(getValue(fieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getFieldDoubleValue(String fieldName) {
/* 160 */     return PrimitiveConverter.getDouble(getValue(fieldName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFieldFloatValue(String fieldName) {
/* 167 */     return PrimitiveConverter.getFloat(getValue(fieldName));
/*     */   }
/*     */   
/*     */   public boolean needProcess() {
/* 171 */     return !this.m_asynchronousPropertyChanges.isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProcess() {
/* 176 */     AsynchronousPropertyChange change = this.m_asynchronousPropertyChanges.poll();
/* 177 */     while (change != null) {
/* 178 */       if (PropertySetMethod.SET.equals(change.m_method)) {
/* 179 */         setToCastValueChecked(change.m_client, change.m_value);
/* 180 */       } else if (PropertySetMethod.PREPEND.equals(change.m_method)) {
/* 181 */         prependToCastValueChecked(change.m_client, change.m_value);
/* 182 */       } else if (PropertySetMethod.APPEND.equals(change.m_method)) {
/* 183 */         appendToCastValueChecked(change.m_client, change.m_value);
/*     */       } 
/* 185 */       if (change.m_client.hasLayoutOnChange()) {
/* 186 */         if (change.m_client.getElement() == null) {
/* 187 */           m_logger.error("Le client que l'on veut layouter a un Element null");
/* 188 */         } else if (change.m_client.getElement().getElementMap() == null) {
/* 189 */           m_logger.error("L'Element à Layouter a une elementMap nulle");
/*     */         } else {
/* 191 */           Xulor.getInstance().addToElementsNeedingLayout(change.m_client.getElement().getElementMap().getId());
/*     */         } 
/*     */       }
/* 194 */       change = this.m_asynchronousPropertyChanges.poll();
/*     */     } 
/* 196 */     this.m_modifiedClients.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyClient(PropertyClient pc) {
/* 204 */     addPropertyClient(pc, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPropertyClient(PropertyClient pc, boolean applyValueAtOnce) {
/* 213 */     synchronized (this.m_clients) {
/* 214 */       this.m_clients.add(pc);
/*     */     } 
/* 216 */     pc.getElement().addProperty(this);
/*     */     
/* 218 */     if (pc.getResultProvider() != null) {
/* 219 */       pc.getResultProvider().setResultProviderParent(this);
/*     */     }
/* 221 */     if (this.m_value != null) {
/* 222 */       Object value = null;
/* 223 */       if (this.m_value instanceof FieldProvider && pc.getFieldName() != null) {
/* 224 */         value = ((FieldProvider)this.m_value).getFieldValue(pc.getFieldName());
/*     */       } else {
/* 226 */         value = this.m_value;
/*     */       } 
/* 228 */       if (applyValueAtOnce) {
/* 229 */         if (pc.getResultProvider() != null) {
/* 230 */           value = pc.getResultProvider().getResult(value);
/*     */         }
/* 232 */         setToCastValueChecked(pc, value);
/*     */       } else {
/* 234 */         setToCastValue(pc, value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePropertyClient(IElement element) {
/* 244 */     if (element == null) {
/*     */       return;
/*     */     }
/* 247 */     synchronized (this.m_clients) {
/* 248 */       ArrayList<PropertyClient> toRemove = new ArrayList<PropertyClient>();
/* 249 */       for (PropertyClient pc : this.m_clients) {
/* 250 */         if (pc.getElement().equals(element)) {
/* 251 */           toRemove.add(pc);
/*     */         }
/*     */       } 
/* 254 */       this.m_clients.removeAll(toRemove);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setToCastValue(PropertyClient pc, Object value) {
/* 260 */     if (this.m_modifiedClients.contains(pc)) {
/* 261 */       for (AsynchronousPropertyChange apc : this.m_asynchronousPropertyChanges) {
/* 262 */         if (apc.m_client == pc && apc.m_method != null && apc.m_method.equals(PropertySetMethod.SET)) {
/* 263 */           if (apc.m_client.getResultProvider() != null) {
/* 264 */             apc.m_value = apc.m_client.getResultProvider().getResult(value); break;
/*     */           } 
/* 266 */           apc.m_value = value;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 272 */       this.m_asynchronousPropertyChanges.offer(new AsynchronousPropertyChange(pc, value, PropertySetMethod.SET));
/* 273 */       this.m_modifiedClients.add(pc);
/*     */     } 
/* 275 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().addToProcessList(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void appendToCastValue(PropertyClient pc, Object value) {
/* 280 */     this.m_asynchronousPropertyChanges.offer(new AsynchronousPropertyChange(pc, value, PropertySetMethod.APPEND));
/* 281 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().addToProcessList(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void prependToCastValue(PropertyClient pc, Object value) {
/* 286 */     this.m_asynchronousPropertyChanges.offer(new AsynchronousPropertyChange(pc, value, PropertySetMethod.PREPEND));
/* 287 */     Xulor.getInstance().getEnvironment().getPropertiesProvider().addToProcessList(this);
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
/*     */   private void setToCastValueChecked(PropertyClient pc, Object value) {
/* 301 */     Method m = pc.getFactory().guessSetter(pc.getAttribute(), (value == null) ? null : value.getClass());
/* 302 */     if (m != null) {
/* 303 */       invokeMethodAccessor(m, pc, value);
/*     */     } else {
/* 305 */       m_logger.error("Impossible de trouver la méthode set" + pc.getAttribute() + " avec la classe " + ((value == null) ? null : value.getClass()));
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
/*     */   private void prependToCastValueChecked(PropertyClient pc, Object value) {
/* 320 */     Method m = pc.getFactory().guessPrepender(pc.getAttribute(), (value == null) ? null : value.getClass());
/* 321 */     if (m != null) {
/* 322 */       invokeMethodAccessor(m, pc, value);
/*     */     } else {
/* 324 */       m_logger.error("Impossible de trouver la méthode prepend" + pc.getAttribute() + " avec la classe " + ((value == null) ? null : value.getClass()));
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
/*     */   private void appendToCastValueChecked(PropertyClient pc, Object value) {
/* 339 */     Method m = pc.getFactory().guessAppender(pc.getAttribute(), (value == null) ? null : value.getClass());
/* 340 */     if (m != null) {
/* 341 */       invokeMethodAccessor(m, pc, value);
/*     */     } else {
/* 343 */       m_logger.error("Impossible de trouver la méthode append" + pc.getAttribute() + " dans " + pc.getElement() + " avec la classe " + ((value == null) ? null : value.getClass()));
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
/*     */   protected void invokeMethodAccessor(Method method, PropertyClient pc, Object value) {
/*     */     try {
/* 360 */       if ((method.getParameterTypes()).length == 0) {
/*     */         return;
/*     */       }
/* 363 */       MethodUtil.castInvoke(method, pc.getElement(), new Object[] { value });
/* 364 */     } catch (IllegalArgumentException e) {
/* 365 */       m_logger.error("Exception illegalArgument : " + e);
/* 366 */     } catch (Exception e) {
/* 367 */       m_logger.error("Erreur lors du InvokeMethodAccessor - Method=" + ((method == null) ? "null" : method.getName()) + " - PropertyClient = " + pc + " - Value = " + value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object value) {
/* 377 */     this.m_value = value;
/* 378 */     synchronized (this.m_clients) {
/* 379 */       for (PropertyClient client : this.m_clients) {
/* 380 */         String fieldName = client.getFieldName();
/* 381 */         if (value instanceof FieldProvider && fieldName != null) {
/* 382 */           FieldProvider fieldedPropertyProvider = (FieldProvider)value;
/* 383 */           Object fieldValue = fieldedPropertyProvider.getFieldValue(fieldName);
/* 384 */           setToCastValue(client, fieldValue); continue;
/*     */         } 
/* 386 */         setToCastValue(client, value);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependValue(Object value) {
/* 396 */     synchronized (this.m_clients) {
/* 397 */       for (PropertyClient client : this.m_clients) {
/* 398 */         prependToCastValue(client, value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendValue(Object value) {
/* 407 */     synchronized (this.m_clients) {
/* 408 */       for (PropertyClient client : this.m_clients) {
/* 409 */         appendToCastValue(client, value);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFieldValue(String field, Object value) {
/* 421 */     if (this.m_value instanceof FieldProvider && field != null) {
/* 422 */       FieldProvider fieldedPropertyProvider = (FieldProvider)this.m_value;
/* 423 */       fieldedPropertyProvider.setFieldValue(field, value);
/* 424 */       synchronized (this.m_clients) {
/* 425 */         for (PropertyClient client : this.m_clients) {
/* 426 */           if (client.getFieldName().equals(field)) {
/* 427 */             setToCastValue(client, value);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fireFieldValueChanged(String field) {
/* 439 */     if (this.m_value != null && this.m_value instanceof FieldProvider) {
/* 440 */       FieldProvider fieldedPropertyProvider = (FieldProvider)this.m_value;
/* 441 */       synchronized (this.m_clients) {
/* 442 */         PropertyClient[] clients = new PropertyClient[this.m_clients.size()];
/* 443 */         this.m_clients.toArray(clients); byte b; int i; PropertyClient[] arrayOfPropertyClient1;
/* 444 */         for (i = (arrayOfPropertyClient1 = clients).length, b = 0; b < i; ) { PropertyClient client = arrayOfPropertyClient1[b];
/* 445 */           String fieldName = client.getFieldName();
/* 446 */           if (fieldName != null && fieldName.equals(field)) {
/* 447 */             setToCastValue(client, fieldedPropertyProvider.getFieldValue(field));
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void firePropertyChanged() {
/* 456 */     if (this.m_value instanceof FieldProvider) {
/* 457 */       FieldProvider fp = (FieldProvider)this.m_value;
/* 458 */       for (PropertyClient client : this.m_clients) {
/* 459 */         String fieldName = client.getFieldName();
/* 460 */         setToCastValue(client, fp.getFieldValue(fieldName));
/*     */       } 
/*     */     } else {
/* 463 */       for (PropertyClient client : this.m_clients) {
/* 464 */         setToCastValue(client, this.m_value);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void fireResultProviderChanged() {
/* 470 */     firePropertyChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void prependFieldValue(String field, Object value) {
/* 480 */     if (this.m_value != null && this.m_value instanceof FieldProvider) {
/* 481 */       FieldProvider fieldedPropertyProvider = (FieldProvider)this.m_value;
/* 482 */       fieldedPropertyProvider.prependFieldValue(field, value);
/* 483 */       synchronized (this.m_clients) {
/* 484 */         for (PropertyClient client : this.m_clients) {
/* 485 */           if (client.getFieldName().equals(field)) {
/* 486 */             prependToCastValue(client, value);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendFieldValue(String field, Object value) {
/* 500 */     if (this.m_value != null && this.m_value instanceof FieldProvider) {
/* 501 */       FieldProvider fieldedPropertyProvider = (FieldProvider)this.m_value;
/* 502 */       fieldedPropertyProvider.appendFieldValue(field, value);
/* 503 */       synchronized (this.m_clients) {
/* 504 */         for (PropertyClient client : this.m_clients) {
/* 505 */           if (client.getFieldName().equals(field)) {
/* 506 */             appendToCastValue(client, value);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void synchronizeWithLastClient() {
/* 514 */     if (this.m_value != null && this.m_value instanceof FieldProvider) {
/* 515 */       fieldProviderSynchronize();
/*     */     } else {
/* 517 */       singleValueSynchronize();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fieldProviderSynchronize() {
/* 527 */     if (this.m_value != null) {
/* 528 */       FieldProvider fieldProvider = (FieldProvider)this.m_value;
/*     */       
/* 530 */       String[] fields = fieldProvider.getFields(); byte b; int i; String[] arrayOfString1;
/* 531 */       for (i = (arrayOfString1 = fields).length, b = 0; b < i; ) { String field = arrayOfString1[b];
/* 532 */         if (field != null) {
/*     */ 
/*     */           
/* 535 */           Object fieldValue = null;
/* 536 */           if (!fieldProvider.isFieldSynchronisable(field))
/*     */           {
/*     */             
/* 539 */             fieldValue = fieldProvider.getFieldValue(field);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 544 */           synchronized (this.m_clients) {
/* 545 */             for (int j = this.m_clients.size() - 1; j >= 0; j--) {
/* 546 */               PropertyClient client = this.m_clients.get(j);
/* 547 */               String fieldName = client.getFieldName();
/* 548 */               if (fieldName != null && fieldName.equals(field))
/*     */               {
/* 550 */                 if (fieldValue == null) {
/* 551 */                   Method method = client.getFactory().guessGetter(client.getAttribute(), (fieldValue == null) ? null : fieldValue.getClass());
/*     */ 
/*     */                   
/*     */                   try {
/* 555 */                     fieldValue = method.invoke(client.getElement(), new Object[0]);
/*     */ 
/*     */ 
/*     */                     
/* 559 */                     fieldProvider.setFieldValue(field, fieldValue);
/* 560 */                   } catch (Exception e) {
/* 561 */                     if (client.getElement() == null) {
/* 562 */                       m_logger.error("[fieldProviderSynchronize] PropertyClient avec un element null : field = " + client.getFieldName());
/* 563 */                     } else if (method == null) {
/* 564 */                       m_logger.error("[fieldProviderSynchronize] La méthode " + client.getElement().getClass().getName() + ".get" + client.getAttribute() + "() n'existe pas, impossible de la charger");
/*     */                     } else {
/* 566 */                       e.printStackTrace();
/*     */                     } 
/*     */                   } 
/*     */                 } else {
/*     */                   
/* 571 */                   setToCastValue(client, fieldValue);
/*     */                 } 
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void singleValueSynchronize() {
/*     */     PropertyClient propertyClient;
/* 587 */     synchronized (this.m_clients) {
/* 588 */       if (this.m_clients.size() != 0) {
/* 589 */         propertyClient = this.m_clients.get(this.m_clients.size() - 1);
/*     */       } else {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 595 */     Factory factory = propertyClient.getFactory();
/*     */     
/* 597 */     Method method = factory.guessGetter(propertyClient.getAttribute(), (this.m_value == null) ? null : this.m_value.getClass());
/*     */ 
/*     */     
/*     */     try {
/* 601 */       this.m_value = method.invoke(propertyClient.getElement(), new Object[0]);
/*     */ 
/*     */       
/* 604 */       synchronized (this.m_clients) {
/* 605 */         for (int i = 0; i < this.m_clients.size() - 1; i++) {
/* 606 */           PropertyClient pc = this.m_clients.get(i);
/* 607 */           setToCastValue(pc, this.m_value);
/*     */         }
/*     */       
/*     */       } 
/* 611 */     } catch (Exception e) {
/* 612 */       m_logger.error("Impossible de synchroniser la propriété " + this.m_name + " avec " + propertyClient.getElement().getClass().getName() + ", l'attribut " + propertyClient.getAttribute() + 
/* 613 */           " est incompatible !");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCompatible(Class<?> clazz, Factory factory) {
/* 623 */     Class<?> compatibleClass = getCompatibleClass(factory);
/* 624 */     return (compatibleClass != null && compatibleClass.isAssignableFrom(clazz));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<?> getCompatibleClass(Factory factory) {
/* 631 */     Method method = factory.guessSetter(this.m_name, (this.m_value == null) ? null : this.m_value.getClass());
/* 632 */     if (method != null) {
/* 633 */       return method.getDeclaringClass();
/*     */     }
/* 635 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 645 */     return "Property name=" + this.m_name + " value=" + this.m_value;
/*     */   }
/*     */   
/* 648 */   public enum PropertySetMethod { SET, APPEND, PREPEND; }
/*     */   
/*     */   protected class AsynchronousPropertyChange {
/*     */     PropertyClient m_client;
/*     */     Object m_value;
/*     */     Property.PropertySetMethod m_method;
/*     */     
/*     */     public AsynchronousPropertyChange(PropertyClient client, Object value, Property.PropertySetMethod method) {
/* 656 */       this.m_client = client;
/* 657 */       if (this.m_client.getResultProvider() != null) {
/* 658 */         this.m_value = this.m_client.getResultProvider().getResult(value);
/*     */       } else {
/* 660 */         this.m_value = value;
/*     */       } 
/* 662 */       this.m_method = method;
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\property\Property.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */