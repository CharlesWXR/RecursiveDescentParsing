package edu.njnu.parser;

import edu.njnu.element.EnumBase;
import edu.njnu.element.OperatorEnum;
import edu.njnu.element.TypeEnum;
import edu.njnu.element.Word;
import edu.njnu.exception.ScannerException;
import edu.njnu.wordscanner.Scanner;

import java.io.*;

public class RecursiveDescentParser {
	private Scanner scanner = new Scanner();
	private Word lastWord = null;

	public Object getResult(String target) throws Exception {
		scanner.appendBuffer(target);
		return E();
	}

	public Object E() throws Exception {
		Object t1 = T();
		Object t2 = E1();
		if (t2 == null)
			return t1;

		if (t1 instanceof Integer && t2 instanceof Integer) {
			return (int)t1 + (int)t2;
		}
		else {
			return (double)t1 + (double)t2;
		}
	}

	public Object T() throws Exception {
		Object t1 = F();
		Object t2 = T1();
		if (t2 == null)
			return t1;

		if (t1 instanceof Integer && t2 instanceof Integer)
			return (int)t1 * (int)t2;
		else
			return (double)t1 * (double)t2;
	}

	public Object E1() throws Exception {
		Word w = null;
		if (this.lastWord != null) {
			w = this.lastWord;
			this.lastWord = null;
		}
		else {
			try {
				w = (Word)this.scanner.getNext();
			} catch (Exception e) {
				return null;
			}
		}

		if (w.getType() == OperatorEnum.Plus) {
			Object t1 = T();
			Object t2 = E1();
			if (t2 == null)
				return t1;

			if (t1 instanceof Integer && t2 instanceof Integer) {
				return (int)t1 + (int)t2;
			}
			else {
				return (double)t1 + (double)t2;
			}
		}
		this.lastWord = w;
		return null;
	}

	public Object T1() throws Exception {
		Word w = null;
		if (this.lastWord != null) {
			w = this.lastWord;
			this.lastWord = null;
		}
		else {
			try {
				w = (Word)this.scanner.getNext();
			} catch (Exception e) {
				return null;
			}
		}

		if (w.getType() == OperatorEnum.Multiply) {
			Object t1 = F();
			Object t2 = T1();
			if (t2 == null)
				return t1;

			if (t1 instanceof Integer && t2 instanceof Integer) {
				return (int)t1 * (int)t2;
			}
			else {
				return (double)t1 * (double)t2;
			}
		}
		this.lastWord = w;
		return null;
	}

	public Object F() throws Exception {
		Word w = (Word)this.scanner.getNext();
		EnumBase type = w.getType();
		if (type == TypeEnum.Int || type == TypeEnum.Double) {
			return w.getContent();
		}
		else if (type == OperatorEnum.LBracket) {
			Object t = E();
			Word next = null;
			if (this.lastWord == null) {
				next = (Word)this.scanner.getNext();
			}
			else {
				next = this.lastWord;
				this.lastWord = null;
			}

			if (next.getType() == OperatorEnum.RBracket) {
				return t;
			}
		}
		throw new ScannerException(ScannerException.NoMatch);
	}

	public static void main(String[] args) {
		try {
			FileInputStream fis = new FileInputStream("F:\\IDEA Workplace\\RecursiveDescentParsing\\src\\main\\java\\edu\\njnu\\Test.txt");
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			String buffer = null;

			Scanner scanner = new Scanner();

			while ((buffer = reader.readLine()) != null) {
				scanner.appendBuffer(buffer + "\n");
			}

			while (!scanner.isEnd()) {
				System.out.println(scanner.getNext());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
