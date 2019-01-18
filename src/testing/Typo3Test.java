package testing;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class Typo3Test {
	private byte[] oldTestData;
	private byte[] newTestData;

	@Before
	public void setUp() throws Exception {
		oldTestData = TestParser.readTestFile("typo3_old.txt");
		newTestData = TestParser.readTestFile("typo3_new.txt");
	}

	@Test
	public void test() {
		assertArrayEquals(oldTestData, newTestData);
	}

}
