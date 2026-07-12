package com.ankamagames.xulor.template;

public interface ITextEditor extends IObservable, IFocusable {
  void setMultiline(boolean paramBoolean);
  
  void setPassword(boolean paramBoolean);
  
  void setFocused(boolean paramBoolean);
  
  void setText(String paramString);
  
  String getText();
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\ITextEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */