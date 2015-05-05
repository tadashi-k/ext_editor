package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * ��s�폜�@�\����������B
 * 
 * @author tadashi
 */
public class DeleteLineAction extends AbstractDeleteAction
{
	/**
	 * ���s���[�`��
	 */
	public void execute(IAction action)
	{
		super.execute(action);

		int offset = getCaretPos();
		IDocument doc = getDocument();
		try
		{
			int line = doc.getLineOfOffset(offset);
			int top = doc.getLineOffset(line);
			int end = top + doc.getLineLength(line);

			addDeletedString(true, doc.get(top, end - top));
			doc.replace(top, end - top, "");

			// �V�������C�������̃J�[�\���ʒu���Z���ꍇ�͍s���ɂ���
			int len = doc.getLineLength(line) - doc.getLineDelimiter(line).length();
			if (offset > top + len)
				offset = top + len;

			setCaretPos(offset);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

}
