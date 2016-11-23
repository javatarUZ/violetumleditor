package com.horstmann.violet.product.diagram.classes.node;

import com.horstmann.violet.framework.graphics.Separator;
import com.horstmann.violet.framework.graphics.content.*;
import com.horstmann.violet.framework.graphics.shape.ContentInsideRectangle;
import com.horstmann.violet.product.diagram.classes.ClassDiagramConstant;
import com.horstmann.violet.product.diagram.property.text.decorator.*;
import com.horstmann.violet.product.diagram.common.node.ColorableNode;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.property.text.LineText;
import com.horstmann.violet.product.diagram.property.text.MultiLineText;
import com.horstmann.violet.product.diagram.property.text.SingleLineText;

import java.awt.*;

/**
 * An enumeration node in a class diagram.
 */
public class EnumNode extends ColorableNode
{
	/**
     * Construct an enumeration node with a default size
     */
    public EnumNode()
    {
        super();
        name = new SingleLineText(NAME_CONVERTER);
        attributes = new MultiLineText();
        methods = new MultiLineText(PROPERTY_CONVERTER);
        createContentStructure();
    }

    protected EnumNode(EnumNode node) throws CloneNotSupportedException
    {
        super(node);
        name = node.name.clone();
        attributes = node.attributes.clone();
        methods = node.methods.clone();
        createContentStructure();
    }

    @Override
    protected void beforeReconstruction()
    {
        super.beforeReconstruction();

        if(null == name)
        {
            name = new SingleLineText();
        }
        if(null == attributes)
        {
            attributes = new MultiLineText();
        }
        if(null == methods)
        {
            methods = new MultiLineText();
        }
        name.reconstruction(NAME_CONVERTER);
        methods.reconstruction(PROPERTY_CONVERTER);
        attributes.reconstruction();
    }

    @Override
    protected INode copy() throws CloneNotSupportedException
    {
        return new EnumNode(this);
    }

    @Override
    protected void createContentStructure()
    {
        name.setText(name.toEdit());

        TextContent nameContent = new TextContent(name);
        nameContent.setMinHeight(MIN_NAME_HEIGHT);
        nameContent.setMinWidth(MIN_WIDTH);
        TextContent attributesContent = new TextContent(attributes);
        TextContent methodsContent = new TextContent(methods);

        VerticalLayout verticalGroupContent = new VerticalLayout();
        verticalGroupContent.add(nameContent);
        verticalGroupContent.add(attributesContent);
        verticalGroupContent.add(methodsContent);
        separator = new Separator.LineSeparator(getBorderColor());
        verticalGroupContent.setSeparator(separator);

        ContentInsideShape contentInsideShape = new ContentInsideRectangle(verticalGroupContent);

        setBorder(new ContentBorder(contentInsideShape, getBorderColor()));
        setBackground(new ContentBackground(getBorder(), getBackgroundColor()));
        setContent(getBackground());

        setTextColor(super.getTextColor());
    }

    @Override
    public void setBorderColor(Color borderColor)
    {
        if(null != separator)
        {
            separator.setColor(borderColor);
        }
        super.setBorderColor(borderColor);
    }

    @Override
    public void setTextColor(Color textColor)
    {
        name.setTextColor(textColor);
        attributes.setTextColor(textColor);
        methods.setTextColor(textColor);
        super.setTextColor(textColor);
    }

    @Override
    public String getToolTip()
    {
        return ClassDiagramConstant.CLASS_DIAGRAM_RESOURCE.getString("tooltip.enum_node");
    }

    /**
     * Sets the name property value.
     * 
     * @param newValue the class name
     */
    public void setName(LineText newValue)
    {
        name.setText(newValue);
    }

    /**
     * Gets the name property value.
     * 
     * @return the class name
     */
    public LineText getName()
    {
        return name;
    }

    /**
     * Sets the attributes property value.
     *
     * @param newValue the attributes of this class
     */
    public void setAttributes(LineText newValue)
    {
        attributes.setText(newValue);
    }

    /**
     * Gets the attributes property value.
     *
     * @return the attributes of this class
     */
    public MultiLineText getAttributes()
    {
        return attributes;
    }

    /**
     * Sets the methods property value.
     *
     * @param newValue the methods of this class
     */
    public void setMethods(LineText newValue)
    {
        methods.setText(newValue);
    }

    /**
     * Gets the methods property value.
     *
     * @return the methods of this class
     */
    public LineText getMethods()
    {
        return methods;
    }

    private SingleLineText name;
    private MultiLineText attributes;
    private MultiLineText methods;

    private transient Separator separator;

    private static final int MIN_NAME_HEIGHT = 45;
    private static final int MIN_WIDTH = 100;
    private static final String STATIC = "<<static>>";

    private static final LineText.Converter NAME_CONVERTER = new LineText.Converter()
    {
        @Override
        public OneLineText toLineString(String text)
        {
            OneLineText lineString;
            lineString = new PrefixDecorator(new LargeSizeDecorator(new OneLineText(text)), "<center>«enumeration»</center>");
            lineString = new CenterTextDecorator(lineString);
            return lineString;
        }
    };

    private static final LineText.Converter PROPERTY_CONVERTER = new LineText.Converter()
    {
        @Override
        public OneLineText toLineString(String text)
        {
            OneLineText lineString = new OneLineText(text);

            if(lineString.contains(STATIC))
            {
                lineString = new UnderlineDecorator(new RemoveSentenceDecorator(lineString, STATIC));
            }

            return lineString;
        }
    };
}
