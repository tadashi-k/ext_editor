/*
 * Created on 2004/02/28
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Uodoした結果を選択状態にしない
 * @author tadashi
 */
public class UnselectedUndoAction extends AbstractAction
{
	public void execute(IAction action)
	{
		action = getAction(ITextEditorActionConstants.UNDO);
		action.run();

		// unselectする
		setCaretPos( getCaretPos());
	}
}
