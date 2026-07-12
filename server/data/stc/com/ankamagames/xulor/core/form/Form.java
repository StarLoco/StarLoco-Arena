/*     */ package com.ankamagames.xulor.core.form;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.ElementMap;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
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
/*     */ public class Form
/*     */   extends XElement
/*     */ {
/*     */   public static final String TAG = "Form";
/*     */   private HashMap<String, Property> m_properties;
/*  31 */   private FormValidateCallBack m_formValidateCallBack = null;
/*     */   
/*     */ 
/*     */ 
/*     */   public Form()
/*     */   {
/*  37 */     this.m_properties = new HashMap();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addProperty(Property property)
/*     */   {
/*  46 */     addProperty(property.getName(), property);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addProperty(String name, Property prop)
/*     */   {
/*  56 */     this.m_properties.put(name, prop);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Property getProperty(String name)
/*     */   {
/*  66 */     return (Property)this.m_properties.get(name);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Collection<Property> getProperties()
/*     */   {
/*  75 */     return this.m_properties.values();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<String> getPropertyNames()
/*     */   {
/*  84 */     return this.m_properties.keySet();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void synchronizeProperties()
/*     */   {
/*  92 */     for (Property property : this.m_properties.values()) {
/*  93 */       property.synchronizeWithLastClient();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/* 101 */     if (this.m_formValidateCallBack != null) {
/* 102 */       Object result = this.m_formValidateCallBack.invokeCallBack();
/* 103 */       return (result != null) && ((result instanceof Boolean)) && (((Boolean)result).booleanValue());
/*     */     }
/* 105 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setValidate(FormValidateCallBack formValidate)
/*     */   {
/* 114 */     this.m_formValidateCallBack = formValidate;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void applyAllAttributes() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildGUI()
/*     */   {
/* 132 */     IElement[] components = getChildren();
/*     */     
/* 134 */     Xulor.getInstance().getEnvironment().openForm(this.m_elementMap.getId() + "." + this.m_id, this);
/* 135 */     IElement[] arrayOfIElement1; int j = (arrayOfIElement1 = components).length; for (int i = 0; i < j; i++) { IElement c = arrayOfIElement1[i];
/* 136 */       c.buildGUI();
/*     */     }
/* 138 */     Xulor.getInstance().getEnvironment().closeForm(this.m_elementMap.getId() + "." + this.m_id);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void buildXML() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void removeSelfFromParent()
/*     */   {
/* 150 */     Environment environment = Xulor.getInstance().getEnvironment();
/* 151 */     environment.removeForm(this.m_id);
/* 152 */     super.removeSelfFromParent();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object getEncapsulatedObject()
/*     */   {
/* 160 */     return null;
/*     */   }
/*     */   
/*     */   public String getTag() {
/* 164 */     return "Form";
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 168 */     Form form = (Form)element;
/* 169 */     form.m_properties = ((HashMap)this.m_properties.clone());
/* 170 */     form.m_formValidateCallBack = this.m_formValidateCallBack;
/* 171 */     super.copyElementData(element);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public IElement cloneElementStructure()
/*     */   {
/* 178 */     Form form = new Form();
/* 179 */     copyElementData(form);
/* 180 */     return form;
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\form\Form.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */