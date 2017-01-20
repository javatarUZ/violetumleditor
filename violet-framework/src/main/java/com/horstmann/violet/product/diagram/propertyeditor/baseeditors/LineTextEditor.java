package com.horstmann.violet.product.diagram.propertyeditor.baseeditors;

import com.horstmann.violet.product.diagram.property.text.LineText;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyEditorSupport;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A abstract property editor for the LineText type.
 * Adds common actions to text editors like : undo, redo, traversing behaviour.
 **/
abstract class LineTextEditor extends PropertyEditorSupport
{

    @Override
    public boolean supportsCustomEditor()
    {
        return true;
    }

    @Override
    public Component getCustomEditor()
    {
        setSourceEditor();
        final JPanel panel = new JPanel();
        panel.add(getTextEditorComponent());
        return panel;
    }

    private JComponent getTextEditorComponent()
    {
        if (this.textEditorComponent == null)
        {
            this.textEditorComponent = initializeTextEditorComponent();
        }
        return this.textEditorComponent;
    }

    private JComponent initializeTextEditorComponent()
    {
        final JTextComponent textComponent = createTextComponent();
        addTraversalBehaviour(textComponent);
        addUndoRedoBehaviour(textComponent);
        addDocumentListener(textComponent);
        initializeEditText(textComponent);
        return createScrollPanel(textComponent);
    }

    private void addTraversalBehaviour(final JTextComponent textComponent)
    {
        textComponent.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeyStrokes);
        textComponent.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeyStrokes);
    }

    private void addUndoRedoBehaviour(final JTextComponent textComponent)
    {
        undoManager = new UndoManager();
        addUndoableEditListener(textComponent);
        addUndoBehaviour(textComponent);
        addRedoBehaviour(textComponent);
    }

    private void addUndoableEditListener(final JTextComponent textComponent)
    {
        final UndoHandler undoHandler = new UndoHandler();
        final Document document = textComponent.getDocument();
        document.addUndoableEditListener(undoHandler);
    }

    private void addUndoBehaviour(final JTextComponent textComponent)
    {
        undoAction = new UndoAction();
        final KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK);
        textComponent.getInputMap().put(undoKeyStroke, UNDO_ACTION_NAME);
        textComponent.getActionMap().put(UNDO_ACTION_NAME, undoAction);
    }

    private void addRedoBehaviour(final JTextComponent textComponent)
    {
        redoAction = new RedoAction();
        final KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK);
        textComponent.getInputMap().put(redoKeyStroke, REDO_ACTION_NAME);
        textComponent.getActionMap().put(REDO_ACTION_NAME, redoAction);
    }

    private void addDocumentListener(final JTextComponent textComponent)
    {
        textComponent.getDocument().addDocumentListener(new DocumentListener()
        {
            public void insertUpdate(final DocumentEvent e)
            {
                updateEditorText(textComponent);
                firePropertyChange();
            }

            public void removeUpdate(final DocumentEvent e)
            {
                updateEditorText(textComponent);
                firePropertyChange();
            }

            public void changedUpdate(final DocumentEvent e)
            {
            }
        });
    }

    private void initializeEditText(final JTextComponent textComponent)
    {
        final String textToEdit = getSourceEditor().toEdit();
        textComponent.setText(textToEdit);
    }

    private void updateEditorText(final JTextComponent textComponent)
    {
        final String actualText = textComponent.getText();
        getSourceEditor().setText(actualText);
    }

    /**
     * Save each of text insert into history which is contained by undoManager
     */
    private class UndoHandler implements UndoableEditListener
    {

        /**
         * Messaged when the Document has created an edit, the edit is added to
         * undoManager, an instance of UndoManager.
         */
        public void undoableEditHappened(final UndoableEditEvent undoableEditEvent)
        {
            final UndoableEdit undoableEdit = undoableEditEvent.getEdit();
            undoManager.addEdit(undoableEdit);
            undoAction.update();
            redoAction.update();
        }
    }

    /**
     * Defines undo text action which is performed when specified combination of keys is pressed.
     */
    private class UndoAction extends AbstractAction
    {
        UndoAction()
        {
            super();
            setEnabled(false);
        }

        /**
         * Undo text if possible and update redo and undo action status.
         *
         * @param e a undo event
         */
        public void actionPerformed(final ActionEvent e)
        {
            try
            {
                undoManager.undo();
            }
            catch (final CannotUndoException exception)
            {
                final String exceptionMessage = "Could not perform undo operation!";
                LOG.log(Level.SEVERE, exceptionMessage, exception);
            }
            update();
            redoAction.update();
        }

        /**
         * Updates undo action status(enabled/disabled).
         */
        void update()
        {
            if (undoManager.canUndo())
            {
                setEnabled(true);
                return;
            }
                setEnabled(false);
        }
    }

    /**
     * Defines redo text action which is performed when specified combination of keys is pressed.
     */
    private class RedoAction extends AbstractAction
    {
        RedoAction()
        {
            super();
            setEnabled(false);
        }

        /**
         * Redo text if possible and update redo and undo action status.
         *
         * @param e a redo event
         */
        public void actionPerformed(final ActionEvent e)
        {
            try
            {
                undoManager.redo();
            }
            catch (final CannotRedoException exception)
            {
                final String exceptionMessage = "Could not perform redo operation!";
                LOG.log(Level.SEVERE, exceptionMessage, exception);
            }
            update();
            undoAction.update();
        }

        /**
         * Updates redo action status(enabled/disabled).
         */
        void update()
        {
            if (undoManager.canRedo())
            {
                setEnabled(true);
                return;
            }
                setEnabled(false);
        }
    }

    /**
     * Creates scroll panel which contains editor text component.
     *
     * @param textComponent editor text component
     * @return editor text component with scroll panel
     */
    protected JComponent createScrollPanel(final JTextComponent textComponent)
    {
        return textComponent;
    }

    /**
     * Set source of text which will be edited.
     */
    protected abstract void setSourceEditor();

    /**
     * Get source of text which will be edited.
     */
    protected abstract LineText getSourceEditor();

    /**
     * Create text field where edited text is displayed.
     *
     * @return editor text field
     */
    protected abstract JTextComponent createTextComponent();

    private static final Logger LOG = Logger.getLogger(LineTextEditor.class.getName());

    /**
     * Number of characters in one line.
     */
    static final int COLUMNS = 30;

    /**
     * Contains characters which gives behaviour of traversing forward.
     */
    private static final Set<KeyStroke> forwardKeyStrokes = new HashSet<KeyStroke>(1);

    /**
     * Contains characters which gives behaviour of traversing backward.
     */
    private static final Set<KeyStroke> backwardKeyStrokes = new HashSet<KeyStroke>(1);

    /**
     * Used for mapping keyStroke to undo action.
     */
    private static final String UNDO_ACTION_NAME = "Undo";

    /**
     * Used for mapping keyStroke to redo action.
     */
    private static final String REDO_ACTION_NAME = "Redo";

    static
    {
        final KeyStroke tabKey = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
        forwardKeyStrokes.add(tabKey);
        final KeyStroke shiftTabKey = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_MASK);
        backwardKeyStrokes.add(shiftTabKey);
    }

    /**
     * Contains history of operations and manage with them.
     */
    private UndoManager undoManager;

    /**
     * Action of undo text when specified keys are pressed.
     */
    private UndoAction undoAction;

    /**
     * Action of redo text when specified keys are pressed.
     */
    private RedoAction redoAction;

    /**
     * Text field where edited text is displayed
     */
    private JComponent textEditorComponent;

}
