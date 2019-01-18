package testing;

import static org.junit.Assert.assertArrayEquals;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AtlasTest {
	private byte[] oldTestData;
	private byte[] newTestData;

	@Before
	public void setUp() throws Exception {
		oldTestData = TestParser.readTestFile("Atlas_old.txt");
		newTestData = TestParser.readTestFile("Atlas_new.txt");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMain() {
		assertArrayEquals(oldTestData, newTestData);
	}

}
