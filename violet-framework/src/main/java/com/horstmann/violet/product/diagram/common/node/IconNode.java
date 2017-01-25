package com.horstmann.violet.product.diagram.common.node;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.horstmann.violet.framework.graphics.content.*;
import com.horstmann.violet.framework.graphics.shape.ContentInsideCustomShape;
import com.horstmann.violet.framework.injection.resources.ResourceBundleConstant;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;

/**
 * @author Aleksander Orchowski comodsuda@gmail.com LAST TOUCH : Aleksander Orchowski
 * 
 *         Node with icon inside
 */
public class IconNode extends ColorableNode
{
    ApplicationIcons icon;

    /**
     * Initializes IconNode
     * 
     * @param ApplicationIcons enum state
     */
    public IconNode(ApplicationIcons icon)
    {
        super();
        this.icon = icon;
        createContentStructure();
    }

    @Override
    protected INode copy() throws CloneNotSupportedException
    {
        return new IconNode(icon);
    }

    @Override
    protected void createContentStructure()
    {
        EmptyContent content = new EmptyContent();
        content.setMinHeight(DEFAULT_HEIGHT);
        content.setMinWidth(DEFAULT_WIDTH);

        ContentInsideShape contentInsideShape = new ContentInsideCustomShape(content, new ContentInsideCustomShape.ShapeCreator()
        {
            @Override
            public Shape createShape(double contentWidth, double contentHeight)
            {
                GeneralPath path = new GeneralPath();
                path.moveTo(0, 0);
                path.lineTo(contentWidth, contentHeight);
                path.lineTo(0, contentHeight);
                path.closePath();
                return path;
            }
        });

        setBorder(new ContentBorder(contentInsideShape, getBorderColor()));
        setBackground(new ContentBackground(getBorder(), getBackgroundColor()));
        setContent(getBackground());
    }

    @Override
    public void draw(Graphics2D graphics)
    {
        super.draw(graphics);
        BufferedImage image;
        try
        {
            image = ImageIO.read(IconNode.class.getResourceAsStream(icon.getIconPath()));
            Rectangle2D bounds = getBounds();
            graphics.drawImage(image, Math.round((float) (bounds.getMinX())), Math.round((float) bounds.getY()), null);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getToolTip()
    {
        return ResourceBundleConstant.NODE_AND_EDGE_RESOURCE.getString(icon.getIconTranslationKey());
    }

    @Override
    public boolean addConnection(IEdge edge)
    {
        if (edge.getStartNode() == edge.getEndNode())
        {
            return false;
        }
        return super.addConnection(edge);
    }

    private static int DEFAULT_WIDTH = 128;
    private static int DEFAULT_HEIGHT = 128;
}