/*     */ package com.ankamagames.xulor.binding.fenggui.template;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.binding.fenggui.FengguiBinding;
/*     */ import com.ankamagames.xulor.core.Converter;
/*     */ import com.ankamagames.xulor.core.impl.XElement;
/*     */ import com.ankamagames.xulor.template.IElement;
/*     */ import com.ankamagames.xulor.template.IRadioButton;
/*     */ import com.ankamagames.xulor.template.IRadioGroup;
/*     */ import java.util.ArrayList;
/*     */ import org.fenggui.RadioButton;
/*     */ import org.fenggui.ToggableGroup;
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
/*     */ public class XRadioGroup
/*     */   extends XElement
/*     */   implements IRadioGroup
/*     */ {
/*     */   public static final String TAG = "RadioGroup";
/*  29 */   private ToggableGroup m_toggableGroup = null;
/*  30 */   private ArrayList<IRadioButton> m_radioButtons = new ArrayList<IRadioButton>();
/*     */   
/*  32 */   private Object m_value = null;
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
/*  46 */     return this.m_toggableGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buildGUI() {
/*  54 */     if (this.m_toggableGroup == null) {
/*  55 */       this.m_toggableGroup = new ToggableGroup();
/*  56 */       Xulor.getInstance().getEnvironment().putRadioGroup(this.m_id, this);
/*  57 */       for (IRadioButton button : this.m_radioButtons)
/*  58 */         ((RadioButton)button.getEncapsulatedObject()).setRadioButtonGroup(this.m_toggableGroup); 
/*     */     }  byte b;
/*     */     int i;
/*     */     IElement[] arrayOfIElement;
/*  62 */     for (i = (arrayOfIElement = getChildren()).length, b = 0; b < i; ) { IElement c = arrayOfIElement[b];
/*  63 */       c.buildGUI();
/*     */       b++; }
/*     */   
/*     */   }
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
/*     */   public IElement cloneElementStructure() {
/*  78 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTag() {
/*  85 */     return "RadioGroup";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(Object value) {
/*  93 */     this.m_value = value;
/*  94 */     if (this.m_toggableGroup != null) {
/*  95 */       Converter conv = FengguiBinding.getInstance().getConverterLibrary().getConverter(value.getClass());
/*  96 */       if (conv != null) {
/*  97 */         for (IRadioButton radio : this.m_radioButtons) {
/*  98 */           Object ret = conv.convert(value.getClass(), radio.getValue());
/*  99 */           if (ret != null && ret.equals(value)) {
/*     */             
/* 101 */             ((XRadioButton)radio).setSelected(true);
/*     */             break;
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
/*     */   public Object getValue() {
/* 114 */     if (this.m_toggableGroup != null) {
/* 115 */       return this.m_toggableGroup.getSelectedValue();
/*     */     }
/* 117 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRadioButton(IRadioButton radioButton) {
/* 125 */     this.m_radioButtons.add(radioButton);
/*     */     
/* 127 */     if (this.m_toggableGroup != null) {
/* 128 */       ((RadioButton)radioButton.getEncapsulatedObject()).setRadioButtonGroup(this.m_toggableGroup);
/*     */     }
/*     */     
/* 131 */     if (this.m_value != null) {
/* 132 */       Converter conv = FengguiBinding.getInstance().getConverterLibrary().getConverter(this.m_value.getClass());
/* 133 */       if (conv != null) {
/* 134 */         Object ret = conv.convert(this.m_value.getClass(), radioButton.getValue());
/* 135 */         if (ret != null && ret.equals(this.m_value))
/*     */         {
/* 137 */           ((XRadioButton)radioButton).setSelected(true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\binding\fenggui\template\XRadioGroup.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */