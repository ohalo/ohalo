package org.ohalo.app;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.ohalo.app.entity.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        assertTrue(true);
    }

    @SuppressWarnings("unchecked")
    public void testCompare() {
        AppInfo info1 = new AppInfo();
        AppInfo info2 = new AppInfo();
        info1.setAppVersion("1.0.123458");
        info2.setAppVersion("1.0.123457");
        List<AppInfo> apps = new ArrayList<AppInfo>();
        apps.add(info1);
        apps.add(info2);
        Collections.sort(apps, new AppInfo());
        System.out.println(apps.get(apps.size() - 1));


        System.out.println("你好！");

    }
}
