package uet.hungnh.template;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SampleTest extends BaseTest {
    @Test
    public void test() {
        assertThat(true, is(equalTo(true)));
    }
}
