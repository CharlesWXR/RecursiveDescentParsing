import edu.njnu.parser.RecursiveDescentParser;
import org.junit.Test;

public class ParserTest {
	@Test
	public void testParser() throws Exception {
		RecursiveDescentParser parser = new RecursiveDescentParser();
		System.out.println(parser.getResult("2*(2+3*3)"));
	}
}
