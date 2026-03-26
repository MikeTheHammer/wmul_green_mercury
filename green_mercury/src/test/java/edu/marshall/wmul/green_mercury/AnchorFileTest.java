package edu.marshall.wmul.green_mercury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;

class AnchorFileTest {
    
    @Test
    void test_output_anchors_to_yaml() {
        HashMap<String, AnchorElement> testAnchors = new HashMap<String, AnchorElement>();
        testAnchors.put("magna", new AnchorElement("magna", "sed_rhoncus", "fringilla neque"));
        testAnchors.put("abb", new AnchorElement("abb", "nibh_vel_lacinia", "augue", "Praesent"));

        LinkedHashMap<String, String> innerMap1 = new LinkedHashMap<String, String>();
        innerMap1.put("name", "magna");
        innerMap1.put("relative_file_path", "sed_rhoncus");
        innerMap1.put("ref_text", "fringilla neque");
        innerMap1.put("page", "XX");
        HashMap<String, LinkedHashMap<String, String>> outerMap1 = new HashMap<>();
        outerMap1.put("anchor", innerMap1);

        LinkedHashMap<String, String> innerMap2 = new LinkedHashMap<String, String>();
        innerMap2.put("name", "abb");
        innerMap2.put("relative_file_path", "nibh_vel_lacinia");
        innerMap2.put("ref_text", "augue");
        innerMap2.put("page", "Praesent");
        HashMap<String, LinkedHashMap<String, String>> outerMap2 = new HashMap<>();
        outerMap2.put("anchor", innerMap2);

        ArrayList<HashMap<String, LinkedHashMap<String, String>>> expectedAnchors = new ArrayList<>();
        expectedAnchors.add(outerMap2);
        expectedAnchors.add(outerMap1);
        

        DumpSettings settings = DumpSettings.builder().setDefaultScalarStyle(ScalarStyle.SINGLE_QUOTED).setDefaultFlowStyle(FlowStyle.BLOCK).setWidth(200).build();
        Dump dump = new Dump(settings);
        String expectedResults = dump.dumpAllToString(expectedAnchors.iterator());

        String results = AnchorFile.convertAnchorsToYAML(testAnchors);

        assertEquals(expectedResults, results);

    }


    @Test
    void test_load_anchors_from_yaml() {
        HashMap<String, AnchorElement> testAnchors = new HashMap<String, AnchorElement>();
        testAnchors.put("magna", new AnchorElement("magna", "sed_rhoncus", "fringilla neque"));
        testAnchors.put("abb", new AnchorElement("abb", "nibh_vel_lacinia", "augue", "Praesent"));

        String anchorsAsYAML = AnchorFile.convertAnchorsToYAML(testAnchors);

        Map<String, AnchorElement> resultAnchors = AnchorFile.convertYAMLToAnchors(anchorsAsYAML);

        assertEquals(testAnchors, resultAnchors);

    }


}
