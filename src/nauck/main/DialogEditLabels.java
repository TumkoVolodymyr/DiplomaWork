/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nauck.main;

/**
 *
 * @author Volodymyr
 */
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.MalformedURLException;
import nauck.data.DataTable;
import nauck.util.Help;
import nauck.util.HelpNotAvailableException;
import symantec.itools.awt.BevelStyle;
import symantec.itools.awt.ButtonBase;
import symantec.itools.awt.ImageButton;
import symantec.itools.awt.WrappingLabel;
import symantec.itools.awt.shape.HorizontalLine;
import symantec.itools.awt.shape.VerticalLine;
import symantec.itools.net.RelativeURL;

public class DialogEditLabels extends Dialog
{
    class SymWindow extends WindowAdapter
    {

        public void windowClosing(WindowEvent windowevent)
        {
            Object obj = windowevent.getSource();
            if(obj == DialogEditLabels.this)
            {
                Dialog1_WindowClosing(windowevent);
            }
        }

        SymWindow()
        {
        }
    }

    class SymAction
        implements ActionListener
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            Object obj = actionevent.getSource();
            if(obj == labelButtonEditLabelsOK)
            {
                labelButtonEditLabelsOK_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonEditLabelsCancel)
            {
                labelButtonEditLabelsCancel_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonDescriptionReset)
            {
                labelButtonDescriptionReset_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonDescriptionClear)
            {
                labelButtonDescriptionClear_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonVarNameReset)
            {
                labelButtonVarNameReset_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonVarNameResetAll)
            {
                labelButtonVarNameResetAll_actionPerformed(actionevent);
                return;
            }
            if(obj == imageButtonSetVarName)
            {
                imageButtonSetVarName_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonEditLabelsSaveAs)
            {
                labelButtonEditLabelsSaveAs_actionPerformed(actionevent);
                return;
            }
            if(obj == labelButtonEditLabelsHelp)
            {
                labelButtonEditLabelsHelp_actionPerformed(actionevent);
            }
        }

        SymAction()
        {
        }
    }

    class SymItem
        implements ItemListener
    {

        public void itemStateChanged(ItemEvent itemevent)
        {
            Object obj = itemevent.getSource();
            if(obj == listVarNames)
            {
                listVarNames_ItemStateChanged(itemevent);
            }
        }

        SymItem()
        {
        }
    }


    Help help;
    String helpAnchor;
    DialogMessage message;
    DataTable dataTable;
    boolean closedWithOK;
    boolean fComponentsAdjusted;
    Button labelButtonEditLabelsOK;
    Button labelButtonEditLabelsCancel;
    Button labelButtonEditLabelsHelp;
    Button labelButtonEditLabelsSaveAs;
    Panel panelDataDescription;
    Label labelDataDescription;
    TextArea textAreaDataDescription;
    Button labelButtonDescriptionReset;
    Button labelButtonDescriptionClear;
    WrappingLabel wrappingLabelDataDescription;
    Panel panelEditVarNames;
    TextField textFieldNewVarName;
    ImageButton imageButtonSetVarName;
    List listVarNames;
    Button labelButtonVarNameResetAll;
    Label labelVarEdit;
    Button labelButtonVarNameReset;
    Label labelDataFile;
    TextField textFieldDataFileName;
    HorizontalLine horizontalLine2;
    VerticalLine verticalLine1;

    public DialogEditLabels(Frame frame, boolean flag)
    {
        super(frame, flag);
        fComponentsAdjusted = false;
        help = null;
        helpAnchor = null;
        message = new DialogMessage((Frame)getParent(), true);
        closedWithOK = false;
        setLayout(null);
        setVisible(false);
        setSize(580, 360);
        setBackground(new Color(0xc0c0c0));
        labelButtonEditLabelsOK = new Button();
        labelButtonEditLabelsOK.setLabel("OK");
        labelButtonEditLabelsOK.setBounds(232, 329, 70, 24);
        labelButtonEditLabelsOK.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonEditLabelsOK);
        labelButtonEditLabelsCancel = new Button();
        labelButtonEditLabelsCancel.setLabel("Cancel");
        labelButtonEditLabelsCancel.setBounds(392, 329, 70, 24);
        labelButtonEditLabelsCancel.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonEditLabelsCancel);
        labelButtonEditLabelsHelp = new Button();
        labelButtonEditLabelsHelp.setLabel("Help");
        labelButtonEditLabelsHelp.setBounds(505, 329, 70, 24);
        labelButtonEditLabelsHelp.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonEditLabelsHelp);
        labelButtonEditLabelsSaveAs = new Button();
        labelButtonEditLabelsSaveAs.setLabel("Save as");
        labelButtonEditLabelsSaveAs.setBounds(312, 329, 70, 24);
        labelButtonEditLabelsSaveAs.setFont(new Font("SansSerif", 0, 14));
        add(labelButtonEditLabelsSaveAs);
        panelDataDescription = new Panel();
        panelDataDescription.setLayout(null);
        panelDataDescription.setBounds(5, 44, 280, 275);
        panelDataDescription.setBackground(new Color(0xffaae8f4));
        add(panelDataDescription);
        labelDataDescription = new Label("Description for the Data File");
        labelDataDescription.setBounds(5, 5, 270, 20);
        labelDataDescription.setFont(new Font("Dialog", 1, 12));
        panelDataDescription.add(labelDataDescription);
        textAreaDataDescription = new TextArea();
        textAreaDataDescription.setBounds(5, 105, 270, 131);
        panelDataDescription.add(textAreaDataDescription);
        labelButtonDescriptionReset = new Button();
        labelButtonDescriptionReset.setLabel("Reset");
        labelButtonDescriptionReset.setBackground(new Color(0xffe2e2e2));
        labelButtonDescriptionReset.setBounds(149, 242, 60, 26);
        labelButtonDescriptionReset.setFont(new Font("Dialog", 0, 12));
        panelDataDescription.add(labelButtonDescriptionReset);
        labelButtonDescriptionClear = new Button();
        labelButtonDescriptionClear.setLabel("Clear");
        labelButtonDescriptionClear.setBackground(new Color(0xffe2e2e2));
        labelButtonDescriptionClear.setBounds(215, 242, 60, 26);
        labelButtonDescriptionClear.setFont(new Font("Dialog", 0, 12));
        panelDataDescription.add(labelButtonDescriptionClear);
        wrappingLabelDataDescription = new WrappingLabel();
        try
        {
            wrappingLabelDataDescription.setText("A description of the data file can be given which will be shown in the project s" +
"pecification window. This might later help to select the data file. You can also" +
" edit or clear the description."
);
        }
        catch(PropertyVetoException _ex) { }
        wrappingLabelDataDescription.setBounds(5, 27, 270, 75);
        panelDataDescription.add(wrappingLabelDataDescription);
        panelEditVarNames = new Panel();
        panelEditVarNames.setLayout(null);
        panelEditVarNames.setBounds(295, 44, 280, 275);
        panelEditVarNames.setBackground(new Color(0xffaae8f4));
        add(panelEditVarNames);
        textFieldNewVarName = new TextField();
        textFieldNewVarName.setBounds(5, 27, 243, 25);
        panelEditVarNames.add(textFieldNewVarName);
        imageButtonSetVarName = new ImageButton();
        try
        {
            imageButtonSetVarName.setImageURL(RelativeURL.getURL("images/enter.gif"));
        }
        catch(MalformedURLException _ex) { }
        catch(PropertyVetoException _ex) { }
        imageButtonSetVarName.setBounds(249, 27, 25, 25);
        panelEditVarNames.add(imageButtonSetVarName);
        listVarNames = new List(4);
        panelEditVarNames.add(listVarNames);
        listVarNames.setBounds(5, 53, 270, 184);
        labelButtonVarNameResetAll = new Button();
        labelButtonVarNameResetAll.setLabel("Reset All");
        labelButtonVarNameResetAll.setBackground(new Color(0xffe2e2e2));
        labelButtonVarNameResetAll.setBounds(215, 242, 60, 26);
        labelButtonVarNameResetAll.setFont(new Font("Dialog", 0, 12));
        panelEditVarNames.add(labelButtonVarNameResetAll);
        labelVarEdit = new Label("Edit Variable Names");
        labelVarEdit.setBounds(5, 5, 270, 20);
        labelVarEdit.setFont(new Font("Dialog", 1, 12));
        panelEditVarNames.add(labelVarEdit);
        labelButtonVarNameReset = new Button();
        labelButtonVarNameReset.setLabel("Reset");
        labelButtonVarNameReset.setBackground(new Color(0xffe2e2e2));
        labelButtonVarNameReset.setBounds(145, 242, 60, 26);
        labelButtonVarNameReset.setFont(new Font("Dialog", 0, 12));
        panelEditVarNames.add(labelButtonVarNameReset);
        labelDataFile = new Label("Data File");
        labelDataFile.setBounds(10, 5, 65, 23);
        labelDataFile.setFont(new Font("Dialog", 1, 12));
        add(labelDataFile);
        textFieldDataFileName = new TextField();
        textFieldDataFileName.setEditable(false);
        textFieldDataFileName.setBounds(79, 5, 313, 25);
        textFieldDataFileName.setForeground(new Color(0));
        textFieldDataFileName.setBackground(new Color(0xffffff));
        add(textFieldDataFileName);
        horizontalLine2 = new HorizontalLine();
        try
        {
            horizontalLine2.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        horizontalLine2.setBounds(6, 28, 570, 2);
        add(horizontalLine2);
        verticalLine1 = new VerticalLine();
        try
        {
            verticalLine1.setBevelStyle(0);
        }
        catch(PropertyVetoException _ex) { }
        verticalLine1.setBounds(289, 35, 2, 290);
        add(verticalLine1);
        setTitle("Edit Labels");
        SymWindow symwindow = new SymWindow();
        addWindowListener(symwindow);
        SymAction symaction = new SymAction();
        labelButtonEditLabelsOK.addActionListener(symaction);
        labelButtonEditLabelsCancel.addActionListener(symaction);
        labelButtonDescriptionReset.addActionListener(symaction);
        labelButtonDescriptionClear.addActionListener(symaction);
        labelButtonVarNameReset.addActionListener(symaction);
        labelButtonVarNameResetAll.addActionListener(symaction);
        SymItem symitem = new SymItem();
        listVarNames.addItemListener(symitem);
        imageButtonSetVarName.addActionListener(symaction);
        labelButtonEditLabelsSaveAs.addActionListener(symaction);
        labelButtonEditLabelsHelp.addActionListener(symaction);
    }

    public void addNotify()
    {
        Dimension dimension = getSize();
        super.addNotify();
        if(fComponentsAdjusted)
        {
            return;
        }
        setSize(insets().left + insets().right + dimension.width, insets().top + insets().bottom + dimension.height);
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            Point point = acomponent[i].getLocation();
            point.translate(insets().left, insets().top);
            acomponent[i].setLocation(point);
        }

        fComponentsAdjusted = true;
    }

    public DialogEditLabels(Frame frame, String s, boolean flag)
    {
        this(frame, flag);
        setTitle(s);
    }

    public void setVisible(boolean flag)
    {
        super.setVisible(flag);
    }

    public synchronized void show()
    {
        Rectangle rectangle = getParent().bounds();
        Rectangle rectangle1 = bounds();
        int i = rectangle.x + (rectangle.width - rectangle1.width) / 2;
        int j = rectangle.y + (rectangle.height - rectangle1.height) / 2;
        if(i < 20)
        {
            i = 20;
        }
        if(j < 20)
        {
            j = 20;
        }
        move(i, j);
        super.show();
    }

    void Dialog1_WindowClosing(WindowEvent windowevent)
    {
        closedWithOK = false;
        setVisible(false);
    }

    void labelButtonDescriptionReset_actionPerformed(ActionEvent actionevent)
    {
        textAreaDataDescription.setText(dataTable.name);
    }

    void labelButtonDescriptionClear_actionPerformed(ActionEvent actionevent)
    {
        textAreaDataDescription.setText("");
    }

    void labelButtonVarNameReset_actionPerformed(ActionEvent actionevent)
    {
        int i = listVarNames.getSelectedIndex();
        if(i > -1)
        {
            listVarNames.replaceItem(dataTable.varNames[i], i);
        }
    }

    void labelButtonVarNameResetAll_actionPerformed(ActionEvent actionevent)
    {
        for(int i = 0; i < dataTable.columns; i++)
        {
            listVarNames.replaceItem(dataTable.varNames[i], i);
        }

    }

    void listVarNames_ItemStateChanged(ItemEvent itemevent)
    {
        textFieldNewVarName.setText(listVarNames.getSelectedItem());
        textFieldNewVarName.selectAll();
        textFieldNewVarName.requestFocus();
    }

    void imageButtonSetVarName_actionPerformed(ActionEvent actionevent)
    {
        int i = listVarNames.getSelectedIndex();
        if(i > -1)
        {
            listVarNames.replaceItem(textFieldNewVarName.getText(), i);
        }
    }

    public void setDataTable(DataTable datatable)
    {
        dataTable = datatable;
        textFieldDataFileName.setText(datatable.filename);
        textAreaDataDescription.setText(datatable.name);
        for(int i = 0; i < datatable.columns; i++)
        {
            listVarNames.add(datatable.varNames[i]);
        }

    }

    void labelButtonEditLabelsOK_actionPerformed(ActionEvent actionevent)
    {
        dataTable.name = textAreaDataDescription.getText();
        for(int i = 0; i < dataTable.columns; i++)
        {
            dataTable.varNames[i] = listVarNames.getItem(i);
        }

        closedWithOK = true;
        setVisible(false);
    }

    void labelButtonEditLabelsCancel_actionPerformed(ActionEvent actionevent)
    {
        closedWithOK = false;
        setVisible(false);
    }

    void labelButtonEditLabelsSaveAs_actionPerformed(ActionEvent actionevent)
    {
        FileDialog filedialog = new FileDialog((Frame)getParent(), "Save Data File", 1);
        filedialog.setFile("*.dat");
        filedialog.setVisible(true);
        String s1 = filedialog.getDirectory();
        String s = filedialog.getFile();
        if(s != null && s1 != null && s != "")
        {
            try
            {
                dataTable.copy(s1 + s);
            }
            catch(IOException ioexception)
            {
                DialogMessage dialogmessage = new DialogMessage((Frame)getParent(), true);
                dialogmessage.showMessage("Write Error!", "The data file could not be saved completely.", "An I/O error occured while trying to save the data. Please make sure that the fi" +
"lename you specified for the data is valid and that the destination drive is acc" +
"essible. "
 + ioexception.getMessage());
            }
        }
        filedialog.dispose();
    }

    public boolean wasClosedWithOK()
    {
        return closedWithOK;
    }

    public void setHelp(Help help1, String s)
    {
        help = help1;
        helpAnchor = s;
    }

    void labelButtonEditLabelsHelp_actionPerformed(ActionEvent actionevent)
    {
        if(help != null)
        {
            try
            {
                help.show(helpAnchor);
            }
            catch(HelpNotAvailableException _ex)
            {
                help = null;
            }
        }
        if(help == null)
        {
            message.showMessage("Attention!", "NEFCLASS-J cannot display help file.", "NEFCLASS-J cannot display the help file. The reason can be that the file is miss" +
"ing, or that no WWW browser could be started. To display the help file manually," +
" please locate the file NefclassJHelp.htmlthat was included in the distribution " +
"and open it in a WWW browser like, for instance, NETSCAPE."
);
        }
    }
}

