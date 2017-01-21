package com.horstmann.violet.product.diagram.common.node;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NoteNodeTest
{

    @Test
    public void testCopy() throws Exception
    {
        NoteNode node = new NoteNode();

        NoteNode copiedNode = (NoteNode) node.copy();

        assertEquals(node.getId(), copiedNode.getId());
        assertEquals(node.getText(), copiedNode.getText());
        assertEquals(node.getBackgroundColor(), copiedNode.getBackgroundColor());
    }

}
