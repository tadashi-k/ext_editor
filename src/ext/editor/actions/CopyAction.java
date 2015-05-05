package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.custom.StyledText;

/**
 * �I��̈���R�s�[���āA�̈���N���A����
 * @author tadashi
 */
public class CopyAction extends AbstractAction
{
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		StyledText widget = getTextWidget();
		widget.copy();
		setCaretPos(offset);
	}
}
