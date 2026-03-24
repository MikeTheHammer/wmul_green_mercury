package edu.marshall.wmul.green_mercury;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;

public class GatherAnchorsTest {
    
    @Test
    public void test_gather_anchors_from_string() {
        String test_string = """
        Mauris commodo nec felis non venenatis. Quisque mollis vel magna et ullamcorper. Donec pulvinar, 
turpis eu iaculis congue, diam nisi finibus odio, non feugiat risus metus nec dolor. Sed eget lectus hendrerit ligula 
mollis malesuada vitae vitae libero. Cras facilisis enim quis massa tincidunt malesuada. Etiam bibendum, odio non 
cursus imperdiet, !!anchor name="magna" ref_text="fringilla neque" /!!, bibendum auctor augue lacus non orci. Nunc 
fringilla commodo nulla, at bibendum justo. !!anchor name="abb" ref_text="augue" page="Praesent" /!!  dapibus, quam 
vestibulum fermentum viverra, ligula odio feugiat risus, id posuere dolor magna nec tellus. Curabitur sit amet nunc 
efficitur, rhoncus turpis quis, imperdiet ex. Praesent mattis elit nec risus cursus auctor.
""";

        HashMap<String, AnchorElement> expected_results = new HashMap<String, AnchorElement>();
        expected_results.put("magna", new AnchorElement("magna", "", "fringilla neque"));
        expected_results.put("abb", new AnchorElement("abb", "", "augue", "Praesent"));

        HashMap<String, AnchorElement> anchors = new HashMap<String, AnchorElement>();
        HashMap<String, AnchorElement> anchors_from_anchor_file = new HashMap<String, AnchorElement>();

        GatherAnchors.gather_anchors_from_string(test_string, anchors, anchors_from_anchor_file);

        for (Map.Entry<String, AnchorElement> key_and_element : expected_results.entrySet()){
            String anchor_name = key_and_element.getKey();
            assertTrue(anchors.containsKey(anchor_name));
            AnchorElement expected_element = key_and_element.getValue();
            AnchorElement result_anchor = anchors.get(anchor_name);
            assertEquals(expected_element, result_anchor);

        }

        assertEquals(expected_results.size(), anchors.size());
    }


    @Test
    public void test_duplicate_anchors() {
        String test_string = """
Mauris commodo nec felis non venenatis. Quisque mollis vel magna et ullamcorper. Donec pulvinar, 
turpis eu iaculis congue, diam nisi finibus odio, non feugiat risus metus nec dolor. Sed eget lectus hendrerit ligula 
mollis malesuada vitae vitae libero. Cras facilisis enim quis massa tincidunt malesuada. Etiam bibendum, odio non 
cursus imperdiet, !!anchor name="magna" ref_text="fringilla neque" /!!, bibendum auctor augue lacus non orci. Nunc 
fringilla commodo nulla, at bibendum justo. !!anchor name="a" ref_text="augue" page="Praesent" /!!  dapibus, quam 
vestibulum fermentum viverra, ligula odio feugiat risus, id posuere dolor magna nec tellus. Curabitur sit amet nunc 
efficitur, !!anchor name="a" ref_text="imperdiet ex" page="mattis" /!! rhoncus turpis quis, imperdiet ex. Praesent 
mattis elit nec risus cursus auctor.
""";

        Pattern expected_error_regex = Pattern.compile("Duplicate Anchors detected\\. Two anchors with the name a have been detected. The second is in.*");

        HashMap<String, AnchorElement> anchors = new HashMap<String, AnchorElement>();
        HashMap<String, AnchorElement> anchors_from_anchor_file = new HashMap<String, AnchorElement>();

        Exception result_exception = assertThrows(RuntimeException.class, () -> {
            GatherAnchors.gather_anchors_from_string(test_string, anchors, anchors_from_anchor_file);
        });

        Matcher m = expected_error_regex.matcher(result_exception.getMessage());

        assertTrue(m.matches());
    }


    @Test
    public void test_gather_anchors_from_string_with_anchors_from_anchor_file() {
        String test_string = """
        Mauris commodo nec felis non venenatis. Quisque mollis vel magna et ullamcorper. Donec pulvinar, 
turpis eu iaculis congue, diam nisi finibus odio, non feugiat risus metus nec dolor. Sed eget lectus hendrerit ligula 
mollis malesuada vitae vitae libero. Cras facilisis enim quis massa tincidunt malesuada. Etiam bibendum, odio non 
cursus imperdiet, !!anchor name="magna" ref_text="fringilla neque" /!!, bibendum auctor augue lacus non orci. Nunc 
fringilla commodo nulla, at bibendum justo. !!anchor name="abb" ref_text="augue" /!!  dapibus, quam 
vestibulum fermentum viverra, ligula odio feugiat risus, id posuere dolor magna nec tellus. Curabitur sit amet nunc 
efficitur, rhoncus turpis quis, imperdiet ex. Praesent mattis elit nec risus cursus auctor.
""";

        HashMap<String, AnchorElement> anchors_from_anchor_file = new HashMap<String, AnchorElement>();
        anchors_from_anchor_file.put("magna", new AnchorElement("magna", "", "fringilla neque"));
        anchors_from_anchor_file.put("abb", new AnchorElement("abb", "", "augue on Page [page]", "Praesent"));

        HashMap<String, AnchorElement> expected_results = new HashMap<String, AnchorElement>();
        expected_results.put("magna", new AnchorElement("magna", "", "fringilla neque"));
        expected_results.put("abb", new AnchorElement("abb", "", "augue", "Praesent"));

        HashMap<String, AnchorElement> anchors = new HashMap<String, AnchorElement>();

        GatherAnchors.gather_anchors_from_string(test_string, anchors, anchors_from_anchor_file);

        for (Map.Entry<String, AnchorElement> key_and_element : expected_results.entrySet()){
            String anchor_name = key_and_element.getKey();
            assertTrue(anchors.containsKey(anchor_name));
            AnchorElement expected_element = key_and_element.getValue();
            AnchorElement result_anchor = anchors.get(anchor_name);
            assertEquals(expected_element, result_anchor);

        }

        assertEquals(expected_results.size(), anchors.size());
    }

}
