import org.junit.Test;

public class PatternIndexTest {


	@Test(timeout = 100)
	public void test1() throws Throwable {
	
		try {
			PatternIndex.patternIndex("hi!", "");
			org.junit.Assert.fail(
					"Expected exception of type java.lang.StringIndexOutOfBoundsException; message: String index out of range: 0");
		} catch (java.lang.StringIndexOutOfBoundsException e) {
		}
	}

	@Test(timeout = 100)
	public void test2() throws Throwable {
	
		try {
			PatternIndex.patternIndex("", "");
			org.junit.Assert.fail(
					"Expected exception of type java.lang.StringIndexOutOfBoundsException; message: String index out of range: 0");
		} catch (java.lang.StringIndexOutOfBoundsException e) {
		}
	}

	@Test(timeout = 100)
	public void test3() throws Throwable {
	
		int int2 = PatternIndex.patternIndex("", "hi!");
		org.junit.Assert.assertTrue("'" + int2 + "' != '" + (-1) + "'", int2 == (-1));
	}

	@Test(timeout = 100)
	public void test4() throws Throwable {

		int int2 = PatternIndex.patternIndex("hi!", "hi!");
		org.junit.Assert.assertTrue("'" + int2 + "' != '" + 0 + "'", int2 == 0);
	}
}
