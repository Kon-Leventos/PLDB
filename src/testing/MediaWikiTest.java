package testing;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class MediaWikiTest {
	private byte[] oldTestData;
	private byte[] newTestData;

	@Before
	public void setUp() throws Exception {
		oldTestData = TestParser.readTestFile("mediaWiki_old.txt");
		newTestData = TestParser.readTestFile("mediaWiki_new.txt");
	}

	@Test
	public void test() {
		assertArrayEquals(oldTestData, newTestData);
	}

}
