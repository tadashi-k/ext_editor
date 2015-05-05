package ext.editor.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.ISourceViewer;


/**
 * テキストバッファ全体を変換するコマンドのベースクラス
 * 
 * @author tadashi
 */
public abstract class AbstractConvertAction extends AbstractAction
{
	public void execute(IAction action)
	{
		try
		{
			int offset = getCaretPos();
			IDocument doc = getDocument();
			int line = doc.getLineOfOffset(offset);
			int pos = offset - doc.getLineOffset(line);

			ISourceViewer viewer = getSourceViewer();
			int topIdx = viewer.getTopIndex();

			int tabWidth = getTextWidget().getTabs();

			String src = doc.get();
			StringBuffer dst = new StringBuffer();
			convert(src, dst, tabWidth);
			doc.set(dst.toString());

			viewer.setTopIndex(topIdx);
			setCaretPos(doc.getLineOffset(line) + pos);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	abstract protected void convert(String src, StringBuffer dst, int tabWidth);

}
