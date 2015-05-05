package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

/**
 * �L�����b�g�̂��郏�[�h���폜����B
 * <p>
 * @author tadashi
 */
public class DeleteWordAction extends AbstractDeleteAction
{
	private WordSeparator separator ;

	public DeleteWordAction()
	{
		separator = new WordSeparator();
	}

	/**
	 * ���s���[�`��
	 */
	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();
		int end = separator.getNextWord(doc.get(), offset);

		try
		{
			addDeletedString(false, doc.get(offset, end - offset));
			doc.replace(offset, end-offset, "");
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}
