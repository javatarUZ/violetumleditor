package com.horstmann.violet.workspace.editorpart.behavior;

import com.horstmann.violet.workspace.Workspace;
import com.horstmann.violet.workspace.WorkspacePanel;
import com.horstmann.violet.workspace.editorpart.IEditorPart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

public class DragWorkspaceBehavior extends AbstractEditorPartBehavior
{
    private final static double DRAG_RATIO = 0.05;
    private final IEditorPart editorPart;
    private final Workspace workspace;
    private Point2D initialMousePosition;
    private JScrollBar initialHorizontalScrollBar;
    private JScrollBar initialVerticalScrollBar;

    public DragWorkspaceBehavior(final Workspace workspace)
    {
        this.workspace = workspace;
        this.editorPart = workspace.getEditorPart();
        this.initialMousePosition = null;
        this.initialHorizontalScrollBar = null;
        this.initialVerticalScrollBar = null;
        this.initialVerticalScrollBar = null;
    }

    @Override
    public void onMousePressed(final MouseEvent event)
    {
        super.onMouseClicked(event);
        if (SwingUtilities.isMiddleMouseButton(event))
        {
            final WorkspacePanel workspacePanel = this.workspace.getAWTComponent();
            final JScrollPane scrollableEditorPart = workspacePanel.getScrollableEditorPart();
            initialHorizontalScrollBar = scrollableEditorPart.getHorizontalScrollBar();
            initialVerticalScrollBar = scrollableEditorPart.getVerticalScrollBar();
            initialMousePosition = event.getLocationOnScreen();
            final Cursor cursor = new Cursor(Cursor.MOVE_CURSOR);
            scrollableEditorPart.setCursor(cursor);
            scrollableEditorPart.invalidate();
            scrollableEditorPart.repaint();
        }
    }

    @Override
    public void onMouseDragged(final MouseEvent event)
    {
        final WorkspacePanel workspacePanel = this.workspace.getAWTComponent();
        final JScrollPane scrollableEditorPart = workspacePanel.getScrollableEditorPart();
        final JScrollBar verticalScrollBar = scrollableEditorPart.getVerticalScrollBar();
        final JScrollBar horizontalScrollBar = scrollableEditorPart.getHorizontalScrollBar();
        final Point2D newPoint = event.getLocationOnScreen();

        if (SwingUtilities.isMiddleMouseButton(event))
        {
            final int translationX = (int) (DRAG_RATIO / editorPart.getZoomFactor() *
                    (initialMousePosition.getX() - newPoint.getX()));
            final int translationY = (int) (DRAG_RATIO * editorPart.getZoomFactor() *
                    (initialMousePosition.getY() - newPoint.getY()));

            verticalScrollBar.setValue(initialVerticalScrollBar.getValue() + translationY);
            horizontalScrollBar.setValue(initialHorizontalScrollBar.getValue() + translationX);
            this.editorPart.getSwingComponent().invalidate();
            this.editorPart.getSwingComponent().repaint();
        }
    }
}
