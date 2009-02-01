package com.availity
/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
import java.math.BigDecimal;
class Bike {

  String manufacturer
  String model
  Integer frame
  String serialNo
  Double weight
  String status
  BigDecimal cost

  public void setCost(BigDecimal newCost) {
    cost = newCost.setScale(3, BigDecimal.ROUND_HALF_UP)
  }


  public String toString() {
    return """Bike:
         manufacturer --  ${manufacturer}
         model -- ${model}
         frame -- ${frame}
         serialNo -- ${serialNo}
      """
  }

}

