package nauck.fuzzy;

import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.TextField;

public class SpecifyFuzzyPartition extends Panel
{
  public TextField textFieldVarName;
  public TextField textFieldFSNumber;
  public Choice choiceFSType;

  public SpecifyFuzzyPartition(String paramString, int paramInt1, int paramInt2)
  {
    setLayout(null);
    setSize(350, 25);
    this.textFieldVarName = new TextField();
    this.textFieldVarName.setEditable(false);
    this.textFieldVarName.setBounds(0, 0, 200, 25);
    this.textFieldVarName.setText(paramString);
    add(this.textFieldVarName);
    this.textFieldFSNumber = new TextField();
    this.textFieldFSNumber.setBounds(200, 0, 45, 25);
    this.textFieldFSNumber.setText(Integer.toString(paramInt1));
    add(this.textFieldFSNumber);
    this.choiceFSType = new Choice();
    this.choiceFSType.addItem("треугольная");
    this.choiceFSType.addItem("трапецеедальная");
    this.choiceFSType.addItem("колоколообразная");
    this.choiceFSType.addItem("линейная");
    try
    {
      this.choiceFSType.select(paramInt2);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
    }
    add(this.choiceFSType);
    this.choiceFSType.setBounds(245, 0, 105, 25);
    this.choiceFSType.setFont(new Font("Сообщение", 0, 13));
  }

  public int getFSNumber()
  {
    int i;
    try
    {
      i = Integer.parseInt(this.textFieldFSNumber.getText());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 2;
    }
    return i;
  }

  public int getFSType()
  {
    int i = this.choiceFSType.getSelectedIndex();
    return i;
  }

  public void setFSNumber(int paramInt)
  {
    if (paramInt > 0)
      this.textFieldFSNumber.setText(Integer.toString(paramInt));
  }

  public void setFSType(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= this.choiceFSType.getItemCount()))
      this.choiceFSType.select(paramInt);
  }
}

/* Location:           W:\nefcalss\nefclass.jar
 * Qualified Name:     nauck.fuzzy.SpecifyFuzzyPartition
 * JD-Core Version:    0.6.0
 */