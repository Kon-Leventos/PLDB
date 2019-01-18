package testing;

import static org.junit.Assert.assertArrayEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PhpBbTest {
	private byte[] oldTestData;
	private byte[] newTestData;

	@Before
	public void setUp() throws Exception {
		oldTestData = TestParser.readTestFile("phpBB_old.txt");
		newTestData = TestParser.readTestFile("phpBB_new.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertArrayEquals(oldTestData, newTestData);
	}

}
