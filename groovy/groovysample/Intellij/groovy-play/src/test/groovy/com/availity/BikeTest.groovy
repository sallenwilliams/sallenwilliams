package com.availity

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class BikeTest extends GroovyTestCase {

  void testToString() {
    def bike = new Bike(manufacturer: "Huffy", model:"8210")
    assert bike.manufacturer == "Huffy"
    assert bike.model == "8210"
  }
}