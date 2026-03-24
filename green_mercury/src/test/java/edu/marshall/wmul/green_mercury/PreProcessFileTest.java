package edu.marshall.wmul.green_mercury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;

public class PreProcessFileTest {
    
    @Test
    public void test_preprocessor() {
        String test_string = """
Vivamus a rutrum nisi. Fusce quis volutpat augue. Duis condimentum ipsum ut quam tempor, in 
aliquam justo ultricies. Integer gravida ipsum non ipsum porta finibus. Vestibulum molestie sem eu nibh dictum, vitae 
dictum lorem sodales. 

!!anchor name="varius_dolor" ref_text="Integer eu porta ipsum on Page [page]" /!!
== In enim nisl, rhoncus et egestas a, ullamcorper id nibh

Mauris ut magna sit amet nulla placerat scelerisque at quis diam. Vestibulum ante ipsum primis in faucibus orci luctus 
et ultrices posuere cubilia curae; Proin fringilla a metus sed mollis. !!xref name="finibus_nec" /!! Vestibulum magna 
mauris, rutrum a nulla ultrices, accumsan vulputate ipsum. 

!!anchor name="scelerisque_mauris" ref_text="Nulla ac metus ut quam" /!!
Nullam tincidunt, tellus quis ornare pellentesque, est est tristique eros, vel molestie est tellus at diam.

!!anchor name="finibus_nec" ref_text="Proin rhoncus pulvinar massa on Page [page]" /!!
Proin rhoncus pulvinar massa. Morbi eget ipsum dui. Vestibulum non urna vitae ante porta posuere. Quisque non fringilla 
dui, a dictum ipsum. Cras rhoncus molestie ipsum, sit amet commodo nisi.
""";


        String expected_string = """
Vivamus a rutrum nisi. Fusce quis volutpat augue. Duis condimentum ipsum ut quam tempor, in 
aliquam justo ultricies. Integer gravida ipsum non ipsum porta finibus. Vestibulum molestie sem eu nibh dictum, vitae 
dictum lorem sodales. 

[#varius_dolor, reftext="Integer eu porta ipsum on Page 47"]
== In enim nisl, rhoncus et egestas a, ullamcorper id nibh

Mauris ut magna sit amet nulla placerat scelerisque at quis diam. Vestibulum ante ipsum primis in faucibus orci luctus 
et ultrices posuere cubilia curae; Proin fringilla a metus sed mollis. <<#finibus_nec, Proin rhoncus pulvinar massa on Page 33>> Vestibulum magna 
mauris, rutrum a nulla ultrices, accumsan vulputate ipsum. 

[#scelerisque_mauris, reftext="Nulla ac metus ut quam"]
Nullam tincidunt, tellus quis ornare pellentesque, est est tristique eros, vel molestie est tellus at diam.

[#finibus_nec, reftext="Proin rhoncus pulvinar massa on Page 33"]
Proin rhoncus pulvinar massa. Morbi eget ipsum dui. Vestibulum non urna vitae ante porta posuere. Quisque non fringilla 
dui, a dictum ipsum. Cras rhoncus molestie ipsum, sit amet commodo nisi.
""";

        HashMap<String, AnchorElement> anchors_from_anchor_file = new HashMap<String, AnchorElement> ();
        anchors_from_anchor_file.put("scelerisque_mauris", new AnchorElement("scelerisque_mauris", "", "Nulla ac metus ut quam", "112"));
        anchors_from_anchor_file.put("finibus_nec", new AnchorElement("finibus_nec", "", "Praesent blandit aliquet dolor", "33"));
        anchors_from_anchor_file.put("varius_dolor", new AnchorElement("varius_dolor", "", "Integer eu porta ipsum on Page [page]", "47"));
    
        
        String result = PreProcessFile.preprocess_input_from_string(test_string, anchors_from_anchor_file);
        assertEquals(expected_string, result);
    }

}
