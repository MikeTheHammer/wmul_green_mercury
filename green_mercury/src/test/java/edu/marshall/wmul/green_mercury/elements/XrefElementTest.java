package edu.marshall.wmul.green_mercury.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class XrefElementTest {
    
    @Test
    void test_output_string() {
        AnchorElement anchor1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "21");
        AnchorElement anchor2 = new AnchorElement("a", "nibh_vel_lacinia", "augue plahah", "Praesent");

        XrefElement xref1 = new XrefElement("magna");
        XrefElement xref2 = new XrefElement("a", "commodo auctor");

        String expected1 = "<<sed_rhoncus#magna, fringilla neque on Page 21>>";
        String expected2 = "<<nibh_vel_lacinia#a, commodo auctor>>";

        String result1 = xref1.getOutputString(anchor1);
        String result2 = xref2.getOutputString(anchor2);

        assertEquals(expected1, result1);
        assertEquals(expected2, result2);

    }
}
