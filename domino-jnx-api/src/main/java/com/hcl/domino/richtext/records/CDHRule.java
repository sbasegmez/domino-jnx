package com.hcl.domino.richtext.records;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import com.hcl.domino.data.StandardColors;
import com.hcl.domino.misc.DominoEnumUtil;
import com.hcl.domino.misc.INumberEnum;
import com.hcl.domino.richtext.RichTextConstants;
import com.hcl.domino.richtext.annotation.StructureDefinition;
import com.hcl.domino.richtext.annotation.StructureGetter;
import com.hcl.domino.richtext.annotation.StructureMember;
import com.hcl.domino.richtext.annotation.StructureSetter;
import com.hcl.domino.richtext.structures.WSIG;

@StructureDefinition(name = "CDHRule", members = {
    @StructureMember(name = "Header", type = WSIG.class),
    @StructureMember(name = "Flags", type = CDHRule.Flag.class, bitfield = true),
    @StructureMember(name = "Width", type = short.class, unsigned = true),
    @StructureMember(name = "Height", type = short.class, unsigned = true),
    @StructureMember(name = "Color", type = short.class, unsigned = true),
    @StructureMember(name = "GradientColor", type = short.class, unsigned = true)
})
public interface CDHRule extends RichTextRecord<WSIG>{
  enum Flag implements INumberEnum<Integer> {

    HRULE_FLAG_USECOLOR(RichTextConstants.HRULE_FLAG_USECOLOR),
    HRULE_FLAG_USEGRADIENT(RichTextConstants.HRULE_FLAG_USEGRADIENT),
    HRULE_FLAG_FITTOWINDOW(RichTextConstants.HRULE_FLAG_FITTOWINDOW),
    HRULE_FLAG_NOSHADOW(RichTextConstants.HRULE_FLAG_NOSHADOW);
    private final int value;
    private Flag(int value) {
      this.value = value;
    }

    @Override
    public long getLongValue() {
      return value;
    }

    @Override
    public Integer getValue() {
      return value;
    }
  }
  
  @StructureGetter("Header")
  @Override
  WSIG getHeader();
  
  @StructureGetter("Width")
  int getWidth();
  
  @StructureSetter("Width")
  CDHRule setWidth(int width);
  
  @StructureGetter("Height")
  int getHeight();
 
  @StructureSetter("Height")
  CDHRule setHeight(int height);
  
  @StructureGetter("Color")
  int getColorRaw();
  
  default Optional<StandardColors> getColor() {
    return DominoEnumUtil.valueOf(StandardColors.class, getColorRaw());
  }
  
  @StructureSetter("Color")
  CDHRule setColorRaw(int colorRaw);
  
  default CDHRule setColor(StandardColors color) {
	  return setColorRaw(color.getValue());
  }
  
  @StructureGetter("GradientColor")
  int getGradientColorRaw();
  
  default Optional<StandardColors> getGradientColor() {
    return DominoEnumUtil.valueOf(StandardColors.class, getGradientColorRaw());
  }
  
  @StructureSetter("GradientColor")
  CDHRule setGradientColorRaw(int gradientColorRaw);
  
  default CDHRule setGradientColor(StandardColors gradientColor) {
	  return setGradientColorRaw(gradientColor.getValue());
  }
  
  @StructureGetter("Flags")
  Set<Flag> getFlags();
  
  @StructureSetter("Flags")
  CDHRule setFlags(Collection<Flag> flags);
}
