package com.horstmann.violet.product.diagram.property.text.decorator;

/**
 * This class centers text
 *
 * @author Sebastian Pieniążek <S.Pieniazek94@gmail.com>
 * @date 23.11.2016
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
