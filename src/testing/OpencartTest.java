package testing;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class OpencartTest {
	private byte[] oldTestData;
	private byte[] newTestData;

	@Before
	public void setUp() throws Exception {
		oldTestData = TestParser.readTestFile("Opencart_old.txt");
		newTestData = TestParser.readTestFile("Opencart_new.txt");
	}

	@Test
	public void test() {
		assertArrayEquals(oldTestData, newTestData);
	}

}
