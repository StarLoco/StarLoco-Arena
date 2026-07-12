/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.Environment;
/*     */ import com.ankamagames.xulor.core.form.Form;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.core.renderer.ResultProvider;
/*     */ import com.ankamagames.xulor.property.Property;
/*     */ import com.ankamagames.xulor.property.PropertyClient;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IProperty;
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
/*     */ public class XProperty
/*     */   extends XElement
/*     */   implements IProperty
/*     */ {
/*     */   public static final String TAG = "Property";
/*  27 */   private Property m_property = null;
/*     */   
/*     */   private boolean m_local = false;
/*     */   private boolean m_localInit = false;
/*  31 */   private String m_name = null;
/*  32 */   private String m_attribute = null;
/*  33 */   private String m_field = null;
/*     */   private boolean m_layoutOnChange = false;
/*  35 */   private Form[] m_forms = null;
/*  36 */   private ResultProvider m_resultProvider = null;
/*     */   
/*     */   public void add(IElement element) {
/*  39 */     if (element instanceof ResultProvider) {
/*  40 */       this.m_resultProvider = (ResultProvider)element;
/*     */     } else {
/*  42 */       super.add(element);
/*     */     } 
/*     */   }
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
/*     */   public Object getEncapsulatedObject() {
/*  58 */     return this.m_property;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  65 */     if (this.m_parent != null) {
/*  66 */       buildProperty();
/*  67 */       this.m_property.addPropertyClient(new PropertyClient(this.m_parent, Xulor.getInstance().getBinding().getTagLibrary().getFactory(this.m_parent.getTag()), this.m_attribute, this.m_field, this.m_resultProvider, this.m_layoutOnChange), true);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void buildProperty() {
/*  72 */     if (this.m_property == null) {
/*  73 */       Environment env = Xulor.getInstance().getEnvironment();
/*  74 */       this.m_property = env.getPropertiesProvider().getProperty(this.m_name);
/*     */       
/*  76 */       if (this.m_property == null) {
/*  77 */         this.m_property = new Property(this.m_name);
/*  78 */         env.getPropertiesProvider().addProperty(this.m_property);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  88 */       if (this.m_forms != null) {
/*  89 */         byte b; int i; Form[] arrayOfForm; for (i = (arrayOfForm = this.m_forms).length, b = 0; b < i; ) { Form form = arrayOfForm[b];
/*  90 */           form.addProperty(this.m_property); b++; }
/*     */         
/*  92 */         this.m_forms = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public Property getProperty() {
/*  98 */     return this.m_property;
/*     */   }
/*     */   
/*     */   public void addPropertyClient(IElement element) {
/* 102 */     if (this.m_property != null && element != null) {
/* 103 */       this.m_property.addPropertyClient(new PropertyClient(element, Xulor.getInstance().getBinding().getTagLibrary().getFactory(element.getTag()), this.m_attribute, this.m_field, this.m_resultProvider, this.m_layoutOnChange), true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildXML() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/* 120 */     return "Property";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAttribute() {
/* 127 */     return this.m_attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttribute(String attribute) {
/* 134 */     this.m_attribute = attribute;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getField() {
/* 141 */     return this.m_field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(String field) {
/* 148 */     this.m_field = field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setForms(Form[] forms) {
/* 155 */     this.m_forms = forms;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocal() {
/* 162 */     return this.m_local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocal(boolean local) {
/* 169 */     this.m_localInit = true;
/* 170 */     this.m_local = local;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 177 */     return this.m_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/* 184 */     this.m_name = name;
/*     */   }
/*     */   
/*     */   public boolean isLayoutOnChange() {
/* 188 */     return this.m_layoutOnChange;
/*     */   }
/*     */   
/*     */   public void setLayoutOnChange(boolean layoutOnChange) {
/* 192 */     this.m_layoutOnChange = layoutOnChange;
/*     */   }
/*     */   
/*     */   protected void copyElementData(IElement element) {
/* 196 */     XProperty fp = (XProperty)element;
/* 197 */     if (this.m_attribute != null) fp.m_attribute = this.m_attribute; 
/* 198 */     if (this.m_field != null) fp.m_field = this.m_field; 
/* 199 */     if (this.m_localInit) fp.m_local = this.m_local; 
/* 200 */     if (this.m_name != null) fp.m_name = this.m_name; 
/* 201 */     if (this.m_resultProvider != null) fp.m_resultProvider = this.m_resultProvider; 
/* 202 */     if (this.m_forms != null) fp.m_forms = (Form[])this.m_forms.clone(); 
/* 203 */     fp.m_layoutOnChange = this.m_layoutOnChange;
/* 204 */     super.copyElementData(element);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IElement cloneElementStructure() {
/* 211 */     XProperty fp = new XProperty();
/* 212 */     copyElementData((IElement)fp);
/* 213 */     return (IElement)fp;
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */