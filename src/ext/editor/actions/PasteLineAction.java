package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;

/**
 * 行の先頭からペーストする
 * @author tadashi
 */
public class PasteLineAction extends AbstractAction
{
	public void execute(IAction action)
	{
		try
		{
			int offset = getCaretPos();
			StyledText widget = getTextWidget();
			IDocument doc = getDocument();

			TextTransfer transfer = TextTransfer.getInstance();
			Clipboard clipboard = new Clipboard(widget.getDisplay());
			String text = (String)clipboard.getContents(transfer);
			if (text != null && text.length() > 0)
			{
				doc.replace(offset, 0, text);
				setCaretPos( offset + text.length() );
			}
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}
}
