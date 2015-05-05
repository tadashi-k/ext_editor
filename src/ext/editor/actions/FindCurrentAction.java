package ext.editor.actions;

import org.eclipse.jface.action.*;
import org.eclipse.jface.text.*;
import org.eclipse.jface.text.source.*;

public class FindCurrentAction extends AbstractAction
{
	private StringMatcher matcher;

	public FindCurrentAction()
	{
		matcher = new StringMatcher();
		matcher.isSeparateUpperCase = false;
		matcher.isWordSearch = true;
	}

	public void execute(IAction action)
	{
		ISourceViewer viewer = getSourceViewer();
		IFindReplaceTarget fTarget = viewer.getFindReplaceTarget();

		int offset = getCaretPos();
		String word = getCurrentWord(offset);

		// System.out.println( "search word[" + word + "]" );

		try
		{
			ITextViewerExtension5 extention = (ITextViewerExtension5)viewer;
			offset = extention.modelOffset2WidgetOffset(offset);
		}
		catch (ClassCastException e)
		{
		}

		fTarget.findAndSelect(offset, word, true, false, true);
	}

	int startOffset ;

	private String getCurrentWord(int offset)
	{
		String text = getDocument().get();

		startOffset = matcher.getWordStart(text, offset);
		if (startOffset == -1)
		{
			beep();
			return null;
		}
		int endOffset = matcher.getWordEnd(text, offset);
		return text.substring(startOffset, endOffset);
	}
}
