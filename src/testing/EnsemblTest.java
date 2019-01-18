package testing;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Before;
import org.junit.Test;

public class EnsemblTest {
	private byte[] oldTestData;
	private byte[] newTestData;

	@Before
	public void setUp() throws Exception {
		oldTestData = TestParser.readTestFile("Ensembl_old.txt");
		newTestData = TestParser.readTestFile("Ensembl_new.txt");
	}

	@Test
	public void test() {
		assertArrayEquals(oldTestData, newTestData);
	}

}
