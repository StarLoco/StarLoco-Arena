package com.ankamagames.xulor.template;

import com.ankamagames.xulor.event.ISliderMovedListener;

public interface ISlider extends IComponent {
  boolean isHorizontal();
  
  void setValue(double paramDouble);
  
  double getValue();
  
  void setSliderSize(double paramDouble);
  
  double getSliderSize();
  
  void setHorizontal(boolean paramBoolean);
  
  void setOnSliderMove(ISliderMovedListener paramISliderMovedListener);
}


/* Location:              E:\Jeux\Ankama\DofusArena2-offi\game\core.jar!\com\ankamagames\xulor\template\ISlider.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */