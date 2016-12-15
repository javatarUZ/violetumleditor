package com.horstmann.violet.product.diagram.property.text.decorator;

/**
 * This class centers text
 */
public class CenterTextDecorator extends OneLineTextDecorator
{

    public CenterTextDecorator(OneLineText decoratedOneLineString)
    {
        super(decoratedOneLineString);
    }

    /**
     * @see OneLineText#toDisplay()
     */
    @Override
    public String toDisplay() {
        return "<center>" + decoratedOneLineString.toDisplay() + "</center>";
    }
}
