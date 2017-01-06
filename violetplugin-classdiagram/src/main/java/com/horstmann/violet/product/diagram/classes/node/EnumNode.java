package com.horstmann.violet.product.diagram.classes.node;

import com.horstmann.violet.framework.graphics.Separator;
import com.horstmann.violet.framework.graphics.content.*;
import com.horstmann.violet.framework.graphics.shape.ContentInsideRectangle;
import com.horstmann.violet.product.diagram.classes.ClassDiagramConstant;
import com.horstmann.violet.product.diagram.property.text.decorator.LargeSizeDecorator;
import com.horstmann.violet.product.diagram.property.text.decorator.OneLineText;
import com.horstmann.violet.product.diagram.property.text.decorator.PrefixDecorator;
import com.horstmann.violet.product.diagram.common.node.ColorableNode;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.property.text.LineText;
import com.horstmann.violet.product.diagram.property.text.MultiLineText;
import com.horstmann.violet.product.diagram.property.text.SingleLineText;
import java.awt.Color;

public class EnumNode extends ColorableNode
{

    private SingleLineText name;
    private MultiLineText attributes;

    private transient Separator separator;

    private static final int MIN_NAME_HEIGHT = 45;
    private static final int MIN_WIDTH = 100;

	  /**
     * Node with a default size
     */
    public EnumNode()
    {
        super();
        name = new SingleLineText(NAME_CONVERTER);
        attributes = new MultiLineText();
        createContentStructure();
    }

    /**
     * Copy node
     */
    protected EnumNode(final EnumNode node) throws CloneNotSupportedException
    {
        super(node);
        if( node != null ) {
            name = node.name.clone();
            attributes = node.attributes.clone();
            createContentStructure();
        }
    }

    @Override
    protected void beforeReconstruction()
    {
        super.beforeReconstruction();
        name = new SingleLineText();
        attributes = new MultiLineText();
        name.reconstruction(NAME_CONVERTER);
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

        TextContent nameContent = getTextContent();
        VerticalLayout verticalGroupContent = getVerticalLayout(nameContent);

        ContentInsideShape contentInsideShape = new ContentInsideRectangle(verticalGroupContent);
        setBorder(new ContentBorder(contentInsideShape, getBorderColor()));
        setBackground(new ContentBackground(getBorder(), getBackgroundColor()));
        setContent(getBackground());
        setTextColor(super.getTextColor());
    }

    private TextContent getTextContent() {
        TextContent nameContent = new TextContent(name);
        nameContent.setMinHeight(MIN_NAME_HEIGHT);
        nameContent.setMinWidth(MIN_WIDTH);
        return nameContent;
    }

    private VerticalLayout getVerticalLayout(final TextContent nameContent) {
        TextContent attributesContent = new TextContent(attributes);

        VerticalLayout verticalGroupContent = new VerticalLayout();
        verticalGroupContent.add(nameContent);
        verticalGroupContent.add(attributesContent);
        separator = new Separator.LineSeparator(getBorderColor());
        verticalGroupContent.setSeparator(separator);
        return verticalGroupContent;
    }

    @Override
    public void setBorderColor(final Color borderColor)
    {
        if(borderColor != null) {
            separator.setColor(borderColor);
            super.setBorderColor(borderColor);
        }

    }

    @Override
    public void setTextColor(final Color textColor)
    {
        if(textColor != null) {
            name.setTextColor(textColor);
            attributes.setTextColor(textColor);
            super.setTextColor(textColor);
        }
    }

    @Override
    public String getToolTip()
    {
        return ClassDiagramConstant.CLASS_DIAGRAM_RESOURCE.getString("tooltip.enum_node");
    }

    public void setName(final LineText newValue)
    {
        if(newValue != null) {
            name.setText(newValue);
        }
    }

    public LineText getName()
    {
        return name;
    }

    public void setAttributes(final LineText newValue)
    {
        if( newValue != null ) {
            attributes.setText(newValue);
        }
    }

    public MultiLineText getAttributes()
    {
        return attributes;
    }

    private static final LineText.Converter NAME_CONVERTER = new LineText.Converter()
    {
        @Override
        public OneLineText toLineString(final String text)
        {
            return new PrefixDecorator( new LargeSizeDecorator(new OneLineText(text)), "<center>«enumeration»</center>");
        }
    };
}
