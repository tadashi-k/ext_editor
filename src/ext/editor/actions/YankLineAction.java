
package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * �폜����������𕜊�������B
 * <p>
 * DeleteLine, DeleteEndOfLine�ō폜���������񂪑ΏۂɂȂ�B
 * 
 * @author tadashi
 */
public class YankLineAction extends AbstractAction
{
	/**
	 * ���s���[�`��
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();
		try
		{
			if (DeleteLineAction.isLined())
			{
				int line = doc.getLineOfOffset(offset);
				int top = doc.getLineOffset(line);
				doc.replace(top, 0, DeleteLineAction.getDeletedString());
				setCaretPos(offset);
			}
			else
			{
				doc.replace(offset, 0, DeleteLineAction.getDeletedString());
				int len = DeleteLineAction.getDeletedString().length();
				setCaretPos(offset + len);
			}
			DeleteLineAction.done();
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

}
