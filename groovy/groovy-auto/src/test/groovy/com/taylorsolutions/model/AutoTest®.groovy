package com.taylorsolutions.model



/**
 * <A href="mailto:scott@jeeprofessional.com">Scott A. Williams</href>
 */

public class AutoTest extends GroovyTestCase {

  void testSetTires() {
    Auto auto = new Auto()
    auto.setTires(4)
    assertEquals(4,4)

  }


}