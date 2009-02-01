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

public static main(String[] args) 
  {
	Bike bike = new Bike(manufacturer:"Huffy", model:"8210", hashocks:"true");
	println(bike.toString());
  }

}

//Bike bike = new Bike();
//bike.toString();