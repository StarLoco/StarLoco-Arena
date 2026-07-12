/*     */ package com.ankamagames.xulor.core.messagebox;
/*     */ 
/*     */ import com.ankamagames.xulor.Xulor;
/*     */ import com.ankamagames.xulor.core.ElementMap;
/*     */ import com.ankamagames.xulor.event.MouseClickEvent;
/*     */ import com.ankamagames.xulor.event.listener.MouseClickListener;
/*     */ import com.ankamagames.xulor.template.IButton;
/*     */ import com.ankamagames.xulor.template.IContainer;
/*     */ import com.ankamagames.xulor.template.IImage;
/*     */ import com.ankamagames.xulor.template.IMessageBox;
/*     */ import com.ankamagames.xulor.template.ITextView;
/*     */ import com.ankamagames.xulor.template.IWindow;
/*     */ import com.ankamagames.xulor.theme.ThemeParser;
/*     */ import com.ankamagames.xulor.util.Pixmap;
/*     */ import com.ankamagames.xulor.util.ThemeTexture;
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
/*     */ public class MessageBoxFormater
/*     */ {
/*     */   private static final String MSG_BOX_WINDOW_ID = "messageBoxWindow";
/*     */   private static final String MSG_BOX_IMAGE_ID = "messageBoxImage";
/*     */   private static final String MSG_BOX_TEXTVIEW_ID = "messageBoxTextView";
/*     */   private static final String MSG_BOX_BUTTONS_CONTAINER_ID = "messageBoxButtonsContainer";
/*     */   private static final String MSG_BOX_BUTTON_ID = "messageBoxButton";
/*     */   
/*     */   public static void format(IMessageBox messageBox, MessageBoxControler controler, String message, String title, int options)
/*     */     throws Exception
/*     */   {
/*  41 */     ElementMap map = messageBox.getElementMap();
/*     */     
/*     */ 
/*  44 */     if (map.containsElement("messageBoxWindow")) {
/*  45 */       IWindow window = (IWindow)map.getElement("messageBoxWindow");
/*  46 */       window.setTitle(title);
/*     */     } else {
/*  48 */       throw new Exception("Aucune Window n'est référencé sous l'id : messageBoxWindow");
/*     */     }
/*     */     
/*     */     IWindow window;
/*  52 */     if (map.containsElement("messageBoxImage")) {
/*  53 */       IImage image = (IImage)map.getElement("messageBoxImage");
/*     */       
/*     */ 
/*  56 */       ThemeParser themeParser = Xulor.getInstance().getThemeParser();
/*  57 */       if (themeParser != null)
/*     */       {
/*  59 */         ThemeTexture texture = null;
/*  60 */         if ((options & 0x20) == 32) {
/*  61 */           texture = themeParser.getTexture("messageBoxInfoIcon");
/*  62 */         } else if ((options & 0x40) == 64) {
/*  63 */           texture = themeParser.getTexture("messageBoxErrorIcon");
/*  64 */         } else if ((options & 0x80) == 128) {
/*  65 */           texture = themeParser.getTexture("messageBoxQuestionIcon");
/*  66 */         } else if ((options & 0x100) == 256) {
/*  67 */           texture = themeParser.getTexture("messageBoxCautionIcon");
/*     */         }
/*     */         
/*  70 */         if (texture != null) {
/*  71 */           image.setPixmap(new Pixmap(texture));
/*     */         }
/*     */       }
/*     */     }
/*     */     else {
/*  76 */       throw new Exception("Aucun Label n'est référencé sous l'id : messageBoxImage");
/*     */     }
/*     */     
/*     */     IImage image;
/*  80 */     if (map.containsElement("messageBoxTextView")) {
/*  81 */       ITextView textView = (ITextView)map.getElement("messageBoxTextView");
/*  82 */       textView.setText(message);
/*     */     } else {
/*  84 */       throw new Exception("Aucun textView n'est référencé sous l'id : messageBoxTextView");
/*     */     }
/*     */     
/*     */     ITextView textView;
/*  88 */     if (map.containsElement("messageBoxButtonsContainer")) {
/*  89 */       IContainer buttonsContainer = (IContainer)map.getElement("messageBoxButtonsContainer");
/*     */       
/*  91 */       IButton button = null;
/*  92 */       if (map.containsElement("messageBoxButton")) {
/*  93 */         button = (IButton)map.getElement("messageBoxButton");
/*     */         
/*     */ 
/*  96 */         buttonsContainer.removeChildren();
/*     */         
/*     */ 
/*  99 */         if ((options & 0x2) == 2) {
/* 100 */           addButton(button, controler, buttonsContainer, 2);
/*     */         }
/* 102 */         if ((options & 0x4) == 4) {
/* 103 */           addButton(button, controler, buttonsContainer, 4);
/*     */         }
/* 105 */         if ((options & 0x8) == 8) {
/* 106 */           addButton(button, controler, buttonsContainer, 8);
/*     */         }
/* 108 */         if ((options & 0x10) == 16) {
/* 109 */           addButton(button, controler, buttonsContainer, 16);
/*     */         }
/*     */       }
/*     */       else {
/* 113 */         throw new Exception("Aucun button n'est référencé sous l'id : messageBoxButton");
/*     */       }
/*     */     }
/*     */     else {
/* 117 */       throw new Exception("Aucun container n'est référencé sous l'id : messageBoxButtonsContainer");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     IContainer buttonsContainer;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private static IButton addButton(IButton modelButton, MessageBoxControler controler, IContainer container, final int type)
/*     */   {
/* 129 */     IButton button = null;
/*     */     
/*     */     try
/*     */     {
/* 133 */       button = (IButton)modelButton.getClass().newInstance();
/* 134 */       button.setText(getTextButton(type));
/* 135 */       button.setThemeElement(modelButton.getThemeElement());
/* 136 */       button.setOnClick(new MouseClickListener()
/*     */       {
/*     */         public void run(MouseClickEvent event) {
/* 139 */           MessageBoxFormater.this.messageBoxClosed(type);
/*     */           
/* 141 */           Xulor.getInstance().unload(MessageBoxFormater.this.getMessageBoxId());
/*     */         }
/*     */         
/*     */ 
/* 145 */       });
/* 146 */       container.add(button);
/*     */     }
/*     */     catch (Exception e) {
/* 149 */       e.printStackTrace();
/*     */     }
/* 151 */     return button;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String getTextButton(int type)
/*     */   {
/* 159 */     switch (type)
/*     */     {
/*     */     case 2: 
/* 162 */       return Xulor.getInstance().getTranslatedString("ok");
/*     */     
/*     */     case 4: 
/* 165 */       return Xulor.getInstance().getTranslatedString("cancel");
/*     */     
/*     */     case 8: 
/* 168 */       return Xulor.getInstance().getTranslatedString("yes");
/*     */     
/*     */     case 16: 
/* 171 */       return Xulor.getInstance().getTranslatedString("no");
/*     */     }
/*     */     
/* 174 */     return "";
/*     */   }
/*     */ }


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\core\messagebox\MessageBoxFormater.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */