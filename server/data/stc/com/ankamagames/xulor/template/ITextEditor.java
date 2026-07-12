package com.ankamagames.xulor.template;

public abstract interface ITextEditor
  extends IObservable, IFocusable
{
  public abstract void setMultiline(boolean paramBoolean);
  
  public abstract void setPassword(boolean paramBoolean);
  
  public abstract void setFocused(boolean paramBoolean);
  
  public abstract void setText(String paramString);
  
  public abstract String getText();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\ITextEditor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */