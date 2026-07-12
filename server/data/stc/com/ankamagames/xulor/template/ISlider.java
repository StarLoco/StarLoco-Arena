package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.ISliderMovedListener;

public abstract interface ISlider
  extends IComponent
{
  public abstract boolean isHorizontal();
  
  public abstract void setValue(double paramDouble);
  
  public abstract double getValue();
  
  public abstract void setSliderSize(double paramDouble);
  
  public abstract double getSliderSize();
  
  public abstract void setHorizontal(boolean paramBoolean);
  
  public abstract void setOnSliderMove(ISliderMovedListener paramISliderMovedListener);
}


/* Location:              C:\Users\flore\Desktop\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\ISlider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       0.7.1
 */