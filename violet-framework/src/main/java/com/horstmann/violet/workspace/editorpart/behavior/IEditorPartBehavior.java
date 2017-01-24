package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.product.diagram.abstracts.IColorable;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.workspace.sidebar.graphtools.GraphTool;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;

/**
 * Describes all editors events which can be listen to.
 */
public interface IEditorPartBehavior
{
    
    /**
     * Action performed when mouse is pressed.
     * @param event mouse event.
     */
    public void onMousePressed(MouseEvent event);

    /**
     * Action performed when mouse is dragged (pressed and moved).
     * @param event mouse event.
     */
    public void onMouseDragged(MouseEvent event);

    /**
     * Action performed when mouse is released.
     * @param event mouse event.
     */
    public void onMouseReleased(MouseEvent event);

    /**
     * Action performed when mouse is clicked (pressed and released).
     * @param event mouse event.
     */
    public void onMouseClicked(MouseEvent event);

    /**
     * Action performed when mouse is moved.
     * @param event mouse event.
     */
    public void onMouseMoved(MouseEvent event);

    /**
     * Action performed when mouse wheel is rotated.
     * @param event mouse wheel event.
     */
    public void onMouseWheelMoved(MouseWheelEvent event);

    /**
     * Action performed when specified tool is selected.
     * @param selectedTool selected graph tool.
     */
    public void onToolSelected(GraphTool selectedTool);

    /**
     * Action performed when specified node is selected.
     * @param node selected node.
     */
    public void onNodeSelected(INode node);

    /**
     * Action performed when specified edge is selected.
     * @param edge selected edge.
     */
    public void onEdgeSelected(IEdge edge);

    /**
     * Action performed before editing specified node.
     * @param node edited node.
     */
    public void beforeEditingNode(INode node);

    /**
     * Action performed while editing specified node.
     * @param node edited node.
     */
    public void whileEditingNode(INode node, PropertyChangeEvent event);

    /**
     * Action performed after editing specified node.
     * @param node edited node.
     */
    public void afterEditingNode(INode node);

    /**
     * Action performed before editing specified edge.
     * @param edge edited edge.
     */
    public void beforeEditingEdge(IEdge edge);

    /**
     * Action performed while editing specified edge.
     * @param edge edited edge.
     */
    public void whileEditingEdge(IEdge edge, PropertyChangeEvent event);

    /**
     * Action performed after editing specified edge.
     * @param edge edited edge.
     */
    public void afterEditingEdge(IEdge edge);

    /**
     * Action performed before selected elements are removed.
     */
    public void beforeRemovingSelectedElements();

    /**
     * Action performed after selected elements are removed.
     */
    public void afterRemovingSelectedElements();

    /**
     * Action performed before specified node is added.
     * @param node added node.
     * @param location location of added node.
     */
    public void beforeAddingNodeAtPoint(INode node, Point2D location);

    /**
     * Action performed after specified node is added.
     * @param node added node.
     * @param location location of added node.
     */
    public void afterAddingNodeAtPoint(INode node, Point2D location);

    /**
     * Action performed before specified edge is added.
     * @param edge added edge.
     * @param startPoint start edge point.
     * @param endPoint end edge point.
     */
    public void beforeAddingEdgeAtPoints(IEdge edge, Point2D startPoint, Point2D endPoint);

    /**
     * Action performed after specified edge is added.
     * @param edge added edge.
     * @param startPoint start edge point.
     * @param endPoint end edge point.
     */
    public void afterAddingEdgeAtPoints(IEdge edge, Point2D startPoint, Point2D endPoint);

    /**
     * Action performed before transition points on the edge are changed.
     * @param edge edge which points are changed.
     */
    public void beforeChangingTransitionPointsOnEdge(IEdge edge);

    /**
     * Action performed after transition points on the edge are changed.
     * @param edge edge which points are changed.
     */
    public void afterChangingTransitionPointsOnEdge(IEdge edge);

    /**
     * Action performed before color of element is changed.
     * @param element colored element.
     */
    public void beforeChangingColorOnElement(IColorable element);

    /**
     * Action performed after color of element is changed.
     * @param element colored element.
     */
    public void afterChangingColorOnElement(IColorable element);

    /**
     * Action performed when specified graphic is painted.
     * @param g2 painted graphic.
     */
    public void onPaint(Graphics2D g2);
    
}
