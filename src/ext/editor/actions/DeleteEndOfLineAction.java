package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * �s���܂ō폜����B
 * <p>
 * �폜�����������DeleteLineAction�Ɠ���static�ŕێ�����B
 * YankLine�ŕ��������邱�Ƃ��ł���B
 * 
 * @author tadashi
 */
public class DeleteEndOfLineAction extends AbstractDeleteAction
{
	/**
	 * ���s���[�`���i�Ē�`���Ă���j
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();
		try
		{
			int line = doc.getLineOfOffset(offset);
			int top = doc.getLineOffset(line);
			int end = top + doc.getLineLength(line) - doc.getLineDelimiter(line).length(); 

			setDeletedString(false, doc.get(offset, end - offset));
			doc.replace(offset, end - offset, "");
			setCaretPos(offset);

			/*
			StyledText text = getTextWidget();
			setDeletedString(false, text.getText(offset, end - 1));
			text.replaceTextRange(offset, end-offset, "");
			*/
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}

	}

}
