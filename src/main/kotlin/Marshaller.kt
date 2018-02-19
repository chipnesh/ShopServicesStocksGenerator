import java.io.File
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT
import javax.xml.bind.Marshaller.JAXB_FRAGMENT

object Marshaller {
    private val jaxbContext = JAXBContext.newInstance(XmlTemplate::class.java)
    private const val headerProperty = "com.sun.xml.internal.bind.xmlHeaders"
    private const val customHeader = "<?xml version='1.0' encoding='UTF-8'?>"

    fun marshall(template: XmlTemplate, file: File) {
        val jaxbMarshaller = jaxbContext.createMarshaller()
        jaxbMarshaller.setProperty(JAXB_FRAGMENT, true)
        jaxbMarshaller.setProperty(headerProperty, customHeader)
        jaxbMarshaller.setProperty(JAXB_FORMATTED_OUTPUT, true)
        jaxbMarshaller.marshal(template, file)
    }
}