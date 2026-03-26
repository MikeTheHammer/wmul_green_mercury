package edu.marshall.wmul.green_mercury;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;
import edu.marshall.wmul.green_mercury.exceptions.DuplicateAnchorException;

class GatherAnchorsTest {
    
    @Test
    void test_gather_anchors_from_string() {
        String testString = """
        Mauris commodo nec felis non venenatis. Quisque mollis vel magna et ullamcorper. Donec pulvinar, 
turpis eu iaculis congue, diam nisi finibus odio, non feugiat risus metus nec dolor. Sed eget lectus hendrerit ligula 
mollis malesuada vitae vitae libero. Cras facilisis enim quis massa tincidunt malesuada. Etiam bibendum, odio non 
cursus imperdiet, !!anchor name="magna" ref_text="fringilla neque" /!!, bibendum auctor augue lacus non orci. Nunc 
fringilla commodo nulla, at bibendum justo. !!anchor name="abb" ref_text="augue" page="Praesent" /!!  dapibus, quam 
vestibulum fermentum viverra, ligula odio feugiat risus, id posuere dolor magna nec tellus. Curabitur sit amet nunc 
efficitur, rhoncus turpis quis, imperdiet ex. Praesent mattis elit nec risus cursus auctor.
""";

        HashMap<String, AnchorElement> expectedResults = new HashMap<String, AnchorElement>();
        expectedResults.put("magna", new AnchorElement("magna", "", "fringilla neque"));
        expectedResults.put("abb", new AnchorElement("abb", "", "augue", "Praesent"));

        HashMap<String, AnchorElement> anchors = new HashMap<String, AnchorElement>();
        HashMap<String, AnchorElement> anchorsFromAnchorFile = new HashMap<String, AnchorElement>();

        GatherAnchors.gatherAnchorsFromString(testString, anchors, anchorsFromAnchorFile);

        for (Map.Entry<String, AnchorElement> key_and_element : expectedResults.entrySet()){
            String anchorName = key_and_element.getKey();
            assertTrue(anchors.containsKey(anchorName));
            AnchorElement expectedElement = key_and_element.getValue();
            AnchorElement resultAnchor = anchors.get(anchorName);
            assertEquals(expectedElement, resultAnchor);

        }

        assertEquals(expectedResults.size(), anchors.size());
    }


    @Test
    void test_duplicate_anchors() {
        String testString = """
Mauris commodo nec felis non venenatis. Quisque mollis vel magna et ullamcorper. Donec pulvinar, 
turpis eu iaculis congue, diam nisi finibus odio, non feugiat risus metus nec dolor. Sed eget lectus hendrerit ligula 
mollis malesuada vitae vitae libero. Cras facilisis enim quis massa tincidunt malesuada. Etiam bibendum, odio non 
cursus imperdiet, !!anchor name="magna" ref_text="fringilla neque" /!!, bibendum auctor augue lacus non orci. Nunc 
fringilla commodo nulla, at bibendum justo. !!anchor name="a" ref_text="augue" page="Praesent" /!!  dapibus, quam 
vestibulum fermentum viverra, ligula odio feugiat risus, id posuere dolor magna nec tellus. Curabitur sit amet nunc 
efficitur, !!anchor name="a" ref_text="imperdiet ex" page="mattis" /!! rhoncus turpis quis, imperdiet ex. Praesent 
mattis elit nec risus cursus auctor.
""";

        Pattern expectedErrorRegex = Pattern.compile("Duplicate Anchors detected\\. Two anchors with the name a have been detected. The second is in.*");

        HashMap<String, AnchorElement> anchors = new HashMap<String, AnchorElement>();
        HashMap<String, AnchorElement> anchorsFromAnchorFile = new HashMap<String, AnchorElement>();

        Exception resultException = assertThrows(DuplicateAnchorException.class, () -> {
            GatherAnchors.gatherAnchorsFromString(testString, anchors, anchorsFromAnchorFile);
        });

        Matcher m = expectedErrorRegex.matcher(resultException.getMessage());

        assertTrue(m.matches());
    }


    @Test
    void test_gather_anchors_from_string_with_anchors_from_anchor_file() {
        String testString = """
        Mauris commodo nec felis non venenatis. Quisque mollis vel magna et ullamcorper. Donec pulvinar, 
turpis eu iaculis congue, diam nisi finibus odio, non feugiat risus metus nec dolor. Sed eget lectus hendrerit ligula 
mollis malesuada vitae vitae libero. Cras facilisis enim quis massa tincidunt malesuada. Etiam bibendum, odio non 
cursus imperdiet, !!anchor name="magna" ref_text="fringilla neque" /!!, bibendum auctor augue lacus non orci. Nunc 
fringilla commodo nulla, at bibendum justo. !!anchor name="abb" ref_text="augue" /!!  dapibus, quam 
vestibulum fermentum viverra, ligula odio feugiat risus, id posuere dolor magna nec tellus. Curabitur sit amet nunc 
efficitur, rhoncus turpis quis, imperdiet ex. Praesent mattis elit nec risus cursus auctor.
""";

        HashMap<String, AnchorElement> anchorsFromAnchorFile = new HashMap<String, AnchorElement>();
        anchorsFromAnchorFile.put("magna", new AnchorElement("magna", "", "fringilla neque"));
        anchorsFromAnchorFile.put("abb", new AnchorElement("abb", "", "augue on Page [page]", "Praesent"));

        HashMap<String, AnchorElement> expectedResults = new HashMap<String, AnchorElement>();
        expectedResults.put("magna", new AnchorElement("magna", "", "fringilla neque"));
        expectedResults.put("abb", new AnchorElement("abb", "", "augue", "Praesent"));

        HashMap<String, AnchorElement> anchors = new HashMap<String, AnchorElement>();

        GatherAnchors.gatherAnchorsFromString(testString, anchors, anchorsFromAnchorFile);

        for (Map.Entry<String, AnchorElement> key_and_element : expectedResults.entrySet()){
            String anchorName = key_and_element.getKey();
            assertTrue(anchors.containsKey(anchorName));
            AnchorElement expectedElement = key_and_element.getValue();
            AnchorElement resultAnchor = anchors.get(anchorName);
            assertEquals(expectedElement, resultAnchor);

        }

        assertEquals(expectedResults.size(), anchors.size());
    }

}
