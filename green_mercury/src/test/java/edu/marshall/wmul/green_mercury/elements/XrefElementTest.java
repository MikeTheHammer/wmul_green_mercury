package edu.marshall.wmul.green_mercury.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class XrefElementTest {
    
    @Test
    public void test_output_string() {
        AnchorElement anchor_1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "21");
        AnchorElement anchor_2 = new AnchorElement("a", "nibh_vel_lacinia", "augue plahah", "Praesent");

        XrefElement xref_1 = new XrefElement("magna");
        XrefElement xref_2 = new XrefElement("a", "commodo auctor");

        String expected_1 = "<<sed_rhoncus#magna, fringilla neque on Page 21>>";
        String expected_2 = "<<nibh_vel_lacinia#a, commodo auctor>>";

        String result_1 = xref_1.get_output_string(anchor_1);
        String result_2 = xref_2.get_output_string(anchor_2);

        assertEquals(expected_1, result_1);
        assertEquals(expected_2, result_2);

    }
}
