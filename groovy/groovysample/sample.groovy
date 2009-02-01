bldr = new groovy.xml.MarkupBuilder()

bldr.html {
    body {
        h1('foo')
        h1('foo')
  }

}


def state = ["Texas", "Florida", "Tennessee"]
println state.getClass()
def add = state.&add
add "Kansas"
println state
state[4] = "California"
println state[4]
state.each {println it}
println state.find { it == "Texas"}
println state.find { it == "Arkansas"}

lst = ["Programming", "In", "Groovy"]
println lst.join ('|')
println lst.reverse()

