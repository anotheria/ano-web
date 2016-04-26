package net.anotheria.webutils;


import net.anotheria.webutils.service.XMLUserTest;
import net.anotheria.webutils.servlet.request.HttpServletRequestMockImplTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(value=Suite.class)
@SuiteClasses(value={HttpServletRequestMockImplTest.class, XMLUserTest.class})
public class AnoWebTestSuite {

}
