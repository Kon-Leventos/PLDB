package testing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AtlasTest.class, BioSqlTest.class, CoppermineTest.class, EnsemblTest.class, MediaWikiTest.class, OpencartTest.class, PhpBbTest.class,
		Typo3Test.class })
public class TotalTest {
}
