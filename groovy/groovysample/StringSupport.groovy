def name = "Joe"
def string = "Hello ${name}"
println string

// *nix/Mac OS X
println "ls -al".execute().text

//Windoze
//println "cmd /c dir".execute().text

//System.properties.each{println it}

println System.getenv("JAVA_HOME")
println System.env.each{println it}

println "I like\n " +
"Hamburgers and\n " +
"Hotdogs"

println """I like 
Hamburgers and
Hotdogs"""
println ""

//XML DSL out of the box
// parsing XML with XmlSlurper()
def xml = """<athlete number="27">
               <college>Ohio St.</college>
               <attributes>
                 <weight>220</weight>
                 <height>6ft2in</height>
                 <forty unit="seconds">
                   <value>4.31</value>
                 </forty>
               </attributes>
             </athlete>"""
def athlete = new XmlSlurper().parseText(xml)
println athlete
 
println athlete.@number
println athlete.attributes.weight
println athlete.attributes.forty.@unit

println xml.getClass()
println athlete.getClass()

// building XML
def builtXml = new groovy.xml.MarkupBuilder()
builtXml.athlete(number:27)
{
  college("Ohio St.")
    attributes {
    weight("220")
    height("6ft2in")
    forty(unit:"seconds"){
    value("4.31")
	}
  }
}

println builtXml.getClass()




