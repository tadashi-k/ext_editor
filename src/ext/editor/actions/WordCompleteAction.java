package ext.editor.actions;

import java.util.ArrayList;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class WordCompleteAction extends AbstractAction
{
	/**  候補の最大数 */
	private final int MAX_MATCH = 20;

	/** 検索ルーチン */
	private StringMatcher matcher;

	/** 候補リスト */
	Candidates candidates;

	public WordCompleteAction()
	{
		matcher = new StringMatcher();
		matcher.isSeparateUpperCase = false;
		matcher.isWordSearch = true;
		candidates = new Candidates();
	}

	public void execute(IAction action)
	{
		int offset = getCaretPos();
		IDocument doc = getDocument();

		// 候補を見つける
		if (candidates.isContinued(doc, offset))
		{
			candidates.next();
		}
		else
		{
			if (candidates.create(doc, offset) == false)
			{
				beep();
				return;
			}
		}

		// 候補を表示する
		try
		{
			int start = candidates.getStartOffset();
			int end = candidates.getEndOffset();

			doc.replace(start, end - start, candidates.getString());
			offset = start + candidates.getString().length();
			setCaretPos(offset);
		}
		catch (BadLocationException e)
		{
			e.printStackTrace();
		}
	}

	/**  ワード完成のための前方検索をカプセル化する  */
	private class WordSearcher
	{
		//  候補を検索する距離
		static protected final int MAX_DIST = 64 * 1024;

		protected int pos, start;
		protected String text;
		protected StringMatcher matcher;

		public WordSearcher(String t, int p, StringMatcher m)
		{
			text = t;
			start = pos = p;
			matcher = m;
			setup();
		}
		public void setup()
		{
			next();
		}
		public void next()
		{
			if (pos >= 0)
			{
				pos = matcher.getWordEnd(text, pos);
				pos = matcher.searchForward(text, pos);
				if (pos >= 0)
				{
					if (pos - start > MAX_DIST)
						pos = -1;
				}
			}
		}
		public boolean isDone()
		{
			return pos < 0;
		}
		public String getString()
		{
			int end = matcher.getWordEnd(text, pos);
			return text.substring(pos, end);
		}
		public int getDistance()
		{
			if (isDone())
				return MAX_DIST;
			else
			{
				if (pos > start)
					return pos - start;
				else
					return start - pos;
			}
		}
	}

	/**  ワード完成のための後方検索をカプセル化する  */
	private class WordBackwardSearcher extends WordSearcher
	{
		WordBackwardSearcher(String t, int p, StringMatcher m)
		{
			super(t, p, m);
		}
		public void setup()
		{
			pos = this.matcher.getWordStart(text, pos);
			if (pos > 0)
				pos--;
			else
				pos = -1;
			next();
		}
		public void next()
		{
			if (pos >= 0)
			{
				pos = this.matcher.searchBackward(text, pos);
				if (start - pos > MAX_DIST)
					pos = -1;
			}
		}
	}

	private class Candidates
	{
		private IDocument prevDoc;

		/**  候補リスト  */
		private String candidates[];

		/**  候補ポインタ  */
		private int curPtr;

		private int startOffset, endOffset ;

		public boolean create(IDocument doc, int offset)
		{
			if (offset == 0)
				return false;

			String text = doc.get();
			startOffset = matcher.getWordStart(text, offset - 1);
			if (startOffset == -1)
				return false;
			endOffset = matcher.getWordEnd(text, offset - 1);
			String refWord = text.substring(startOffset, offset);
			matcher.setSearchWord(refWord);

			ArrayList<String> list = new ArrayList<String>();
			WordSearcher fsearch = new WordSearcher(text, offset, matcher);
			WordBackwardSearcher bsearch =
				new WordBackwardSearcher(text, offset, matcher);

			while (list.size() < MAX_MATCH)
			{
				if (fsearch.isDone() && bsearch.isDone())
					break;
				WordSearcher csearch;
				if (fsearch.getDistance() < bsearch.getDistance())
					csearch = fsearch;
				else
					csearch = bsearch;
				String word = csearch.getString();
				if (!list.contains(word))
					list.add(word);
				csearch.next();
			}
			if ( list.size() == 0 )
				return false ;

			candidates = list.toArray(new String[0]);
			curPtr = 0;

			prevDoc = doc;

			return true;
		}

		public boolean isContinued(IDocument doc, int offset)
		{
			if (prevDoc != doc)
				return false;

			String text = doc.get();
			startOffset = matcher.getWordStart(text, offset - 1);
			if (text.substring(startOffset, offset).equals(candidates[curPtr]))
			{
				endOffset = startOffset + getString().length() ; 
				return true;
			}
			else
				return false;
		}
		public void next()
		{
			curPtr++;
			if (curPtr >= candidates.length)
			{
				beep();
				curPtr = 0;
			}
		}
		public int getStartOffset()
		{
			return startOffset;
		}
		public int getEndOffset()
		{
			return endOffset ;
		}
		public String getString()
		{
			return candidates[curPtr];
		}
	}
}
