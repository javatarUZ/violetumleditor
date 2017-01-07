package com.horstmann.violet.product.diagram.abstracts.node;

import com.horstmann.violet.product.diagram.abstracts.IColorable;
import com.horstmann.violet.product.diagram.property.text.LineText;

/**
 * Created by Wenaro on 2017-01-07.
 */
public interface INodeName extends INode, IColorable {

    LineText getName();
}