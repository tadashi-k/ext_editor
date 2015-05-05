package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.custom.StyledText;

/**
 * 選択領域をコピーして、領域をクリアする
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
