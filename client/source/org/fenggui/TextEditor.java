/*     */ package org.fenggui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.regex.Pattern;
/*     */ import org.fenggui.event.Event;
/*     */ import org.fenggui.event.FocusEvent;
/*     */ import org.fenggui.event.IDragAndDropListener;
/*     */ import org.fenggui.event.IFocusListener;
/*     */ import org.fenggui.event.IKeyPressedListener;
/*     */ import org.fenggui.event.IKeyReleasedListener;
/*     */ import org.fenggui.event.IKeyTypedListener;
/*     */ import org.fenggui.event.ITextChangedListener;
/*     */ import org.fenggui.event.Key;
/*     */ import org.fenggui.event.KeyPressedEvent;
/*     */ import org.fenggui.event.KeyReleasedEvent;
/*     */ import org.fenggui.event.KeyTypedEvent;
/*     */ import org.fenggui.event.TextChangedEvent;
/*     */ import org.fenggui.event.TextCursorMovedEvent;
/*     */ import org.fenggui.event.mouse.IMouseEnteredListener;
/*     */ import org.fenggui.event.mouse.IMouseExitedListener;
/*     */ import org.fenggui.event.mouse.IMousePressedListener;
/*     */ import org.fenggui.event.mouse.MouseEnteredEvent;
/*     */ import org.fenggui.event.mouse.MouseExitedEvent;
/*     */ import org.fenggui.event.mouse.MousePressedEvent;
/*     */ import org.fenggui.io.IOStreamException;
/*     */ import org.fenggui.io.InputOutputStream;
/*     */ import org.fenggui.render.Binding;
/*     */ import org.fenggui.render.Font;
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
/*     */ public class TextEditor
/*     */   extends ObservableWidget
/*     */   implements ITextWidget
/*     */ {
/*  59 */   private ArrayList<ITextChangedListener> textChangedHook = new ArrayList<ITextChangedListener>();
/*     */   
/*     */   public static final String LABEL_DEFAULT = "default";
/*     */   
/*     */   public static final String LABEL_DISABLED = "disabled";
/*     */   public static final String LABEL_FOCUSED = "focused";
/*  65 */   private int cursorIndex = 0;
/*  66 */   private StringBuilder text = new StringBuilder();
/*  67 */   private TextEditorAppearance appearance = null;
/*  68 */   private Selection selection = new Selection();
/*     */   
/*     */   private boolean multiline = true;
/*     */   
/*     */   private boolean selectOnFocus = false;
/*     */   private boolean inWritingState = false;
/*  74 */   private TextEditorDnDListener dndListener = null;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shiftKeyDown = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean passwordField = false;
/*     */ 
/*     */   
/*  85 */   private int maxCharacters = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   private Pattern restrict = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean unicodeRestrict = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextEditor(boolean multiline) {
/* 105 */     this.multiline = multiline;
/* 106 */     this.appearance = new TextEditorAppearance(this);
/* 107 */     this.dndListener = new TextEditorDnDListener(this);
/* 108 */     resetSelection();
/*     */     
/* 110 */     buildMouseBehavior();
/* 111 */     buildKeyboardBehavior();
/*     */     
/* 113 */     setupTheme(TextEditor.class);
/* 114 */     updateMinSize();
/* 115 */     setTraversable(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextEditor() {
/* 120 */     this(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Selection getSelection() {
/* 125 */     return this.selection;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextEditorAppearance getAppearance() {
/* 130 */     return this.appearance;
/*     */   }
/*     */ 
/*     */   
/*     */   void buildMouseBehavior() {
/* 135 */     addMousePressedListener(new IMousePressedListener()
/*     */         {
/*     */           public void mousePressed(MousePressedEvent mp) {}
/*     */         });
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
/* 160 */     addMouseEnteredListener(new IMouseEnteredListener()
/*     */         {
/*     */           
/*     */           public void mouseEntered(MouseEnteredEvent mouseEnteredEvent)
/*     */           {
/* 165 */             Binding.getInstance().getCursorFactory().getTextCursor().show();
/* 166 */             TextEditor.this.getDisplay().addDndListener(TextEditor.this.dndListener);
/*     */           }
/*     */         });
/*     */     
/* 170 */     addMouseExitedListener(new IMouseExitedListener()
/*     */         {
/*     */           
/*     */           public void mouseExited(MouseExitedEvent mouseExited)
/*     */           {
/* 175 */             Binding.getInstance().getCursorFactory().getDefaultCursor().show();
/* 176 */             if (TextEditor.this.getDisplay() != null) {
/* 177 */               TextEditor.this.getDisplay().removeDndListener(TextEditor.this.dndListener);
/*     */             }
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   void buildKeyboardBehavior() {
/* 186 */     addKeyPressedListener(new IKeyPressedListener()
/*     */         {
/*     */           
/*     */           public void keyPressed(KeyPressedEvent keyPressedEvent)
/*     */           {
/* 191 */             if (TextEditor.this.inWritingState) TextEditor.this.handleKeyPressed(keyPressedEvent);
/*     */           
/*     */           }
/*     */         });
/*     */     
/* 196 */     addKeyTypedListener(new IKeyTypedListener()
/*     */         {
/*     */           
/*     */           public void keyTyped(KeyTypedEvent keyTypedEvent)
/*     */           {
/* 201 */             if (TextEditor.this.inWritingState) TextEditor.this.handleKeyTyped(keyTypedEvent);
/*     */           
/*     */           }
/*     */         });
/*     */     
/* 206 */     addKeyReleasedListener(new IKeyReleasedListener()
/*     */         {
/*     */           
/*     */           public void keyReleased(KeyReleasedEvent e)
/*     */           {
/* 211 */             if (e.getKeyClass() == Key.SHIFT) TextEditor.this.shiftKeyDown = false;
/*     */           
/*     */           }
/*     */         });
/* 215 */     addFocusListener(new IFocusListener()
/*     */         {
/*     */           boolean hadAlreadyFocus = false;
/*     */           
/*     */           public void focusChanged(FocusEvent f) {
/* 220 */             if (f.isFocusLost()) {
/*     */               
/* 222 */               TextEditor.this.inWritingState = false;
/* 223 */               TextEditor.this.selection.startIndex = -1;
/* 224 */               this.hadAlreadyFocus = false;
/*     */ 
/*     */             
/*     */             }
/* 228 */             else if (!this.hadAlreadyFocus) {
/* 229 */               this.hadAlreadyFocus = true;
/* 230 */               TextEditor.this.inWritingState = true;
/* 231 */               if (TextEditor.this.getText() != null) {
/*     */ 
/*     */                 
/* 234 */                 int textLength = TextEditor.this.getText().length();
/* 235 */                 TextEditor.this.setCursorIndex(textLength);
/* 236 */                 if (TextEditor.this.selectOnFocus) {
/* 237 */                   TextEditor.this.selection.startIndex = 0;
/* 238 */                   TextEditor.this.selection.endIndex = textLength;
/*     */                 } 
/*     */               } 
/* 241 */               TextEditor.this.getAppearance().getCursorPainter().resetTimer();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleKeyTyped(KeyTypedEvent e) {
/* 249 */     if (getAppearance().getFont().isCharacterMapped(e.getKey())) {
/*     */       
/* 251 */       if (this.restrict != null && !this.restrict.matcher(Character.toString(e.getKey())).matches()) {
/*     */         return;
/*     */       }
/*     */       
/* 255 */       if (this.selection.startIndex < this.selection.endIndex) {
/*     */         
/* 257 */         removeSelectedText();
/* 258 */         setCursorIndex(this.selection.startIndex);
/* 259 */         resetSelection();
/*     */       } 
/* 261 */       if (this.maxCharacters < 0 || (this.maxCharacters >= 0 && this.text.length() < this.maxCharacters)) {
/*     */         
/* 263 */         insertCharAt(getCursorIndex(), e.getKey());
/* 264 */         setCursorIndex(getCursorIndex() + 1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void handleKeyPressed(KeyPressedEvent e) {
/*     */     boolean alreadyLeft;
/*     */     int rightNewLineIndex, leftNewLineIndex;
/* 273 */     getAppearance().getCursorPainter().resetTimer();
/*     */     
/* 275 */     switch (e.getKeyClass()) {
/*     */       
/*     */       case BACKSPACE:
/* 278 */         if (this.selection.startIndex < this.selection.endIndex) {
/*     */           
/* 280 */           removeSelectedText();
/* 281 */           setCursorIndex(this.selection.startIndex);
/* 282 */           resetSelection(); break;
/*     */         } 
/* 284 */         if (getCursorIndex() > 0) {
/*     */           
/* 286 */           int newCursorIndex = getCursorIndex() - 1;
/* 287 */           deleteCharAt(newCursorIndex);
/* 288 */           setCursorIndex(newCursorIndex);
/*     */         } 
/*     */         break;
/*     */       case ENTER:
/* 292 */         if (this.multiline) {
/*     */           
/* 294 */           if (this.selection.startIndex < this.selection.endIndex) {
/*     */             
/* 296 */             removeSelectedText();
/* 297 */             setCursorIndex(this.selection.startIndex);
/* 298 */             resetSelection();
/*     */           } 
/* 300 */           insertCharAt(getCursorIndex(), '\n');
/* 301 */           setCursorIndex(getCursorIndex() + 1);
/*     */         } 
/* 303 */         resetSelection();
/*     */         break;
/*     */       
/*     */       case SHIFT:
/* 307 */         if (this.selection.state == 0) {
/*     */           
/* 309 */           this.selection.startIndex = this.selection.endIndex = getCursorIndex();
/* 310 */           this.selection.state = 1;
/*     */         } 
/* 312 */         this.shiftKeyDown = true;
/*     */         break;
/*     */       
/*     */       case DELETE:
/* 316 */         if (this.selection.startIndex < this.selection.endIndex) {
/*     */           
/* 318 */           removeSelectedText();
/* 319 */           setCursorIndex(this.selection.startIndex);
/*     */         }
/* 321 */         else if (getCursorIndex() < this.text.length()) {
/*     */           
/* 323 */           deleteCharAt(getCursorIndex());
/*     */         } 
/* 325 */         resetSelection();
/*     */         break;
/*     */       
/*     */       case UP:
/* 329 */         setCursorIndex(seekNearestIndex(getAppearance().getCursorPainter().getX(), 
/* 330 */               getAppearance().getCursorPainter().getY() + 
/* 331 */               (int)(getAppearance().getFont().getHeight() * 1.5F)));
/* 332 */         if (this.shiftKeyDown) { this.selection.upKey(); }
/* 333 */         else { resetSelection(); }
/* 334 */          getDisplay().fireGlobalEventListener((Event)new TextCursorMovedEvent(this, 1, getCursorIndex(), this.shiftKeyDown));
/*     */         break;
/*     */       
/*     */       case RIGHT:
/* 338 */         if (getCursorIndex() < getText().length()) setCursorIndex(getCursorIndex() + 1); 
/* 339 */         if (this.shiftKeyDown) { this.selection.rightKey(); }
/* 340 */         else { resetSelection(); }
/* 341 */          if (getCursorIndex() < this.text.length()) getDisplay().fireGlobalEventListener((Event)new TextCursorMovedEvent(this, 3, getCursorIndex(), this.shiftKeyDown));
/*     */         
/*     */         break;
/*     */       case LEFT:
/* 345 */         alreadyLeft = (getCursorIndex() == 0);
/* 346 */         if (getCursorIndex() > 0) setCursorIndex(getCursorIndex() - 1); 
/* 347 */         if (this.shiftKeyDown) { this.selection.leftKey(); }
/* 348 */         else { resetSelection(); }
/* 349 */          if (!alreadyLeft) getDisplay().fireGlobalEventListener((Event)new TextCursorMovedEvent(this, 2, getCursorIndex(), this.shiftKeyDown));
/*     */         
/*     */         break;
/*     */       case DOWN:
/* 353 */         setCursorIndex(seekNearestIndex(getAppearance().getCursorPainter().getX(), 
/* 354 */               getAppearance().getCursorPainter().getY() - 
/* 355 */               (int)(getAppearance().getFont().getHeight() * 0.5F)));
/* 356 */         if (this.shiftKeyDown) { this.selection.downKey(); }
/* 357 */         else { resetSelection(); }
/* 358 */          getDisplay().fireGlobalEventListener((Event)new TextCursorMovedEvent(this, 0, getCursorIndex(), this.shiftKeyDown));
/*     */         break;
/*     */       
/*     */       case END:
/* 362 */         rightNewLineIndex = this.text.indexOf(10, getCursorIndex());
/* 363 */         if (rightNewLineIndex != -1) {
/*     */           
/* 365 */           setCursorIndex(rightNewLineIndex);
/*     */         }
/*     */         else {
/*     */           
/* 369 */           setCursorIndex(this.text.length());
/*     */         } 
/* 371 */         if (this.shiftKeyDown) { this.selection.rightKey(); }
/* 372 */         else { resetSelection(); }
/* 373 */          getDisplay().fireGlobalEventListener((Event)new TextCursorMovedEvent(this, 0, getCursorIndex(), this.shiftKeyDown));
/*     */         break;
/*     */       
/*     */       case HOME:
/* 377 */         leftNewLineIndex = this.text.substring(0, getCursorIndex()).lastIndexOf(
/* 378 */             10, getCursorIndex());
/* 379 */         if (leftNewLineIndex != -1) {
/*     */           
/* 381 */           setCursorIndex(leftNewLineIndex + 1);
/*     */         }
/*     */         else {
/*     */           
/* 385 */           setCursorIndex(0);
/*     */         } 
/* 387 */         if (this.shiftKeyDown) { this.selection.leftKey(); }
/* 388 */         else { resetSelection(); }
/* 389 */          getDisplay().fireGlobalEventListener((Event)new TextCursorMovedEvent(this, 1, getCursorIndex(), this.shiftKeyDown));
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void removeSelectedText() {
/* 398 */     if (this.selection.startIndex < 0 || this.selection.endIndex > this.text.length())
/* 399 */       return;  deleteText(this.selection.startIndex, this.selection.endIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMultiline() {
/* 407 */     return this.multiline;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMultiline(boolean multiline) {
/* 415 */     this.multiline = multiline;
/* 416 */     processTextChange((String)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelectOnFocus() {
/* 424 */     return this.selectOnFocus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectOnFocus(boolean selectOnFocus) {
/* 433 */     this.selectOnFocus = selectOnFocus;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxCharacters() {
/* 440 */     return this.maxCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxCharacters(int maxCharacters) {
/* 447 */     this.maxCharacters = maxCharacters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRestrict() {
/* 454 */     return this.restrict.pattern();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRestrict(String restrict) {
/* 464 */     if (restrict != null) {
/* 465 */       if (this.unicodeRestrict) {
/* 466 */         this.restrict = Pattern.compile(restrict, 64);
/*     */       } else {
/* 468 */         this.restrict = Pattern.compile(restrict);
/*     */       } 
/*     */     } else {
/* 471 */       this.restrict = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnicodeRestrict() {
/* 479 */     return this.unicodeRestrict;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUnicodeRestrict(boolean unicodeRestrict) {
/* 486 */     this.unicodeRestrict = unicodeRestrict;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCursorIndex() {
/* 491 */     return this.cursorIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCursorIndex(int cursorIndex) {
/* 496 */     if (cursorIndex < 0 || getText().length() == 0) { cursorIndex = 0; }
/* 497 */     else if (cursorIndex > getText().length()) { cursorIndex = getText().length() - 1; }
/* 498 */      this.cursorIndex = cursorIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSelectionLength() {
/* 503 */     return this.selection.startIndex - this.selection.endIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPasswordField() {
/* 511 */     return this.passwordField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPasswordField(boolean passwordField) {
/* 519 */     this.passwordField = passwordField;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 527 */     if (this.text != null) {
/* 528 */       return this.text.toString();
/*     */     }
/* 530 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String text) {
/* 540 */     this.text.delete(0, this.text.length());
/*     */     
/* 542 */     if (this.restrict == null || (this.restrict != null && this.restrict.matcher(text).matches())) {
/*     */ 
/*     */       
/* 545 */       String fittingText = text;
/* 546 */       if (text != null && text.length() != 0) {
/*     */         
/* 548 */         if (this.maxCharacters < 0 || text.length() <= this.maxCharacters) {
/*     */           
/* 550 */           fittingText = text;
/*     */         }
/*     */         else {
/*     */           
/* 554 */           fittingText = text.substring(0, this.maxCharacters);
/*     */         } 
/* 556 */         this.text.append(fittingText);
/* 557 */         this.cursorIndex = fittingText.length();
/*     */       } 
/*     */       
/* 560 */       processTextChange(fittingText);
/*     */     } 
/* 562 */     if ((getAppearance()).useBufferedTextRenderer) {
/* 563 */       getAppearance().getTextRenderer().setText(text);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendText(String text) {
/* 573 */     this.text.append(text);
/* 574 */     processTextChange(text);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextLine(String text) {
/* 585 */     if (this.text.length() == 0) {
/*     */       
/* 587 */       setText(text);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 592 */       StringBuffer newText = (new StringBuffer("\n")).append(text);
/* 593 */       this.text.append(newText);
/* 594 */       processTextChange(newText.toString());
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
/*     */   void deleteText(int start, int end) {
/*     */     try {
/* 609 */       String removed = this.text.substring(start, end);
/* 610 */       this.text.delete(start, end);
/* 611 */       processTextChange(removed);
/*     */     }
/* 613 */     catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {}
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
/*     */   void insertCharAt(int index, char c) {
/*     */     try {
/* 629 */       this.text.insert(index, c);
/* 630 */       processTextChange(String.valueOf(c));
/*     */     }
/* 632 */     catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
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
/*     */   void deleteCharAt(int index) {
/*     */     try {
/* 647 */       char c = this.text.charAt(index);
/* 648 */       this.text.deleteCharAt(index);
/* 649 */       processTextChange(String.valueOf(c));
/*     */     }
/* 651 */     catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetSelection() {
/* 659 */     this.selection.reset();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void process(InputOutputStream stream) throws IOException, IOStreamException {
/* 665 */     super.process(stream);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processTextChange(String txt) {
/* 672 */     setCursorIndex(this.cursorIndex);
/* 673 */     updateMinSize();
/* 674 */     fireTextChangedEvent(txt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMinSize() {
/* 681 */     setMinSize(getAppearance().getMinSizeHint());
/*     */     
/* 683 */     if (getParent() != null && getParent() instanceof ScrollContainer)
/*     */     
/* 685 */     { ((ScrollContainer)getParent()).layout(); }
/*     */     
/* 687 */     else if (getParent() != null) { getParent().updateMinSize(); }
/*     */   
/*     */   }
/*     */   
/*     */   private int seekNearestIndex(int x, int y) {
/* 692 */     String text = getText();
/* 693 */     if (text == null || text.length() == 0) return 0;
/*     */     
/* 695 */     Font font = getAppearance().getFont();
/*     */ 
/*     */     
/* 698 */     int line = (getAppearance().getContentHeight() - y) / font.getHeight();
/*     */ 
/*     */ 
/*     */     
/* 702 */     int i = 0;
/* 703 */     char c = text.charAt(0);
/* 704 */     int lineCounter = 0;
/*     */ 
/*     */     
/* 707 */     while (lineCounter < line && i < text.length()) {
/*     */       
/* 709 */       c = text.charAt(i++);
/* 710 */       if (c == '\n') lineCounter++;
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 717 */     int nearestX = font.getWidth(c);
/* 718 */     while (nearestX < x && i < text.length()) {
/*     */       
/* 720 */       c = text.charAt(i++);
/* 721 */       nearestX += font.getWidth(c);
/*     */ 
/*     */       
/* 724 */       if (c == '\n') {
/*     */         
/* 726 */         i--;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 731 */     if (line == 0) i++;
/*     */ 
/*     */ 
/*     */     
/* 735 */     if (i > text.length()) {
/*     */ 
/*     */       
/* 738 */       i--;
/*     */     }
/* 740 */     else if (nearestX - x > font.getWidth(c) / 2) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 747 */       i--;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 752 */     return i;
/*     */   }
/*     */   
/*     */   private class TextEditorDnDListener
/*     */     implements IDragAndDropListener {
/* 757 */     TextEditor parent = null;
/*     */ 
/*     */     
/*     */     public TextEditorDnDListener(TextEditor parent) {
/* 761 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDndWidget(IWidget w, int displayX, int displayY) {
/* 766 */       return w.equals(this.parent);
/*     */     }
/*     */ 
/*     */     
/*     */     public void select(int displayX, int displayY) {
/* 771 */       TextEditor.this.selection.state = 2;
/* 772 */       TextEditor.this.selection.startIndex = TextEditor.this.selection.endIndex = TextEditor.this
/* 773 */         .seekNearestIndex(displayX - this.parent.getDisplayX(), displayY - this.parent.getDisplayY());
/* 774 */       TextEditor.this.selection.state = 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public void drag(int displayX, int displayY) {
/* 779 */       int index = TextEditor.this.seekNearestIndex(displayX - this.parent.getDisplayX(), displayY - this.parent.getDisplayY());
/* 780 */       updateSelection(index);
/*     */     }
/*     */ 
/*     */     
/*     */     public void drop(int displayX, int displayY, IWidget droppedOn) {
/* 785 */       if (!droppedOn.equals(this.parent))
/* 786 */         return;  int index = TextEditor.this.seekNearestIndex(displayX - this.parent.getDisplayX(), displayY - this.parent.getDisplayY());
/* 787 */       updateSelection(index);
/* 788 */       TextEditor.this.selection.state = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     private void updateSelection(int index) {
/* 793 */       if (TextEditor.this.selection.state == 1) {
/*     */         
/* 795 */         if (index > TextEditor.this.selection.startIndex)
/*     */         {
/* 797 */           TextEditor.this.selection.endIndex = index;
/*     */         }
/*     */         else
/*     */         {
/* 801 */           TextEditor.this.selection.endIndex = TextEditor.this.selection.startIndex;
/* 802 */           TextEditor.this.selection.startIndex = index;
/* 803 */           TextEditor.this.selection.state = 2;
/*     */         
/*     */         }
/*     */       
/*     */       }
/* 808 */       else if (index < TextEditor.this.selection.endIndex) {
/*     */         
/* 810 */         TextEditor.this.selection.startIndex = index;
/*     */       }
/*     */       else {
/*     */         
/* 814 */         TextEditor.this.selection.startIndex = TextEditor.this.selection.endIndex;
/* 815 */         TextEditor.this.selection.endIndex = index;
/* 816 */         TextEditor.this.selection.state = 1;
/*     */       } 
/*     */       
/* 819 */       TextEditor.this.setCursorIndex(index);
/*     */     } }
/*     */   
/*     */   public class Selection { public static final int NO_SELECTION = 0;
/*     */     public static final int AT_END_OF_SELECTION = 1;
/*     */     public static final int AT_START_OF_SELECTION = 2;
/*     */     public int startIndex;
/*     */     public int endIndex;
/*     */     
/*     */     public Selection() {
/* 829 */       this.startIndex = -1;
/* 830 */       this.endIndex = -1;
/* 831 */       this.startX = -1;
/* 832 */       this.startY = -1;
/* 833 */       this.endX = -1;
/* 834 */       this.endY = -1;
/*     */       
/* 836 */       this.state = 0;
/*     */     }
/*     */     public int startX; public int startY; public int endX; public int endY; public int state;
/*     */     public void reset() {
/* 840 */       this.startIndex = this.endIndex = this.startX = this.endX = this.startY = this.endY = -1;
/* 841 */       this.state = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void upKey() {
/* 846 */       if (this.state != 0 && TextEditor.this.getSelectionLength() == 0)
/*     */       {
/* 848 */         this.state = 2;
/*     */       }
/*     */       
/* 851 */       if (this.state == 1) {
/*     */         
/* 853 */         if (TextEditor.this.getCursorIndex() < this.startIndex)
/*     */         {
/* 855 */           this.endIndex = this.startIndex;
/* 856 */           this.startIndex = TextEditor.this.getCursorIndex();
/* 857 */           this.state = 2;
/*     */         }
/*     */         else
/*     */         {
/* 861 */           this.endIndex = TextEditor.this.getCursorIndex();
/*     */         }
/*     */       
/* 864 */       } else if (this.state == 2) {
/*     */         
/* 866 */         this.startIndex = TextEditor.this.getCursorIndex();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void downKey() {
/* 873 */       if (this.state != 0 && TextEditor.this.getSelectionLength() == 0)
/*     */       {
/* 875 */         this.state = 1;
/*     */       }
/*     */       
/* 878 */       if (this.state == 1) {
/*     */         
/* 880 */         this.endIndex = TextEditor.this.getCursorIndex();
/*     */       }
/* 882 */       else if (this.state == 2) {
/*     */         
/* 884 */         if (TextEditor.this.getCursorIndex() > this.endIndex) {
/*     */           
/* 886 */           this.startIndex = this.endIndex;
/* 887 */           this.endIndex = TextEditor.this.getCursorIndex();
/* 888 */           this.state = 1;
/*     */         }
/*     */         else {
/*     */           
/* 892 */           this.startIndex = TextEditor.this.getCursorIndex();
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void leftKey() {
/* 900 */       if (this.state != 0 && TextEditor.this.getSelectionLength() == 0)
/*     */       {
/* 902 */         this.state = 2;
/*     */       }
/*     */       
/* 905 */       if (this.state == 1) {
/*     */         
/* 907 */         this.endIndex = TextEditor.this.getCursorIndex();
/*     */       }
/* 909 */       else if (this.state == 2) {
/*     */         
/* 911 */         this.startIndex = TextEditor.this.getCursorIndex();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void rightKey() {
/* 918 */       if (this.state != 0 && TextEditor.this.getSelectionLength() == 0)
/*     */       {
/* 920 */         this.state = 1;
/*     */       }
/*     */       
/* 923 */       if (this.state == 1) {
/*     */         
/* 925 */         this.endIndex = TextEditor.this.getCursorIndex();
/*     */       }
/* 927 */       else if (this.state == 2) {
/*     */         
/* 929 */         this.startIndex = TextEditor.this.getCursorIndex();
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInWritingState() {
/* 937 */     return this.inWritingState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextChangedListener(ITextChangedListener l) {
/* 946 */     if (!this.textChangedHook.contains(l))
/*     */     {
/* 948 */       this.textChangedHook.add(l);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTextChangedListener(ITextChangedListener l) {
/* 958 */     this.textChangedHook.remove(l);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireTextChangedEvent(String text) {
/* 966 */     TextChangedEvent e = new TextChangedEvent(this, text);
/*     */     
/* 968 */     for (ITextChangedListener l : this.textChangedHook)
/*     */     {
/* 970 */       l.textChanged(e);
/*     */     }
/*     */     
/* 973 */     Display display = getDisplay();
/* 974 */     if (display != null)
/*     */     {
/* 976 */       display.fireGlobalEventListener((Event)e);
/*     */     }
/*     */   }
/*     */ }


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\org\fenggui\TextEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */