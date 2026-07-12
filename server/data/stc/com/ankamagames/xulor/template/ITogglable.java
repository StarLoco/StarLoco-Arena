package com.ankamagames.xulor.template;

public abstract interface ITogglable
  extends ISelection
{
  public abstract boolean getSelected();
  
  public abstract ITogglable setSelected(boolean paramBoolean);
  
  public abstract Object getValue();
  
  public abstract String getText();
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\ITogglable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */