package edu.marshall.wmul.green_mercury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;
import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;

public class AnchorFileTest {
    
    @Test
    public void test_output_anchors_to_yaml() {
        HashMap<String, AnchorElement> test_anchors = new HashMap<String, AnchorElement>();
        test_anchors.put("magna", new AnchorElement("magna", "sed_rhoncus", "fringilla neque"));
        test_anchors.put("abb", new AnchorElement("abb", "nibh_vel_lacinia", "augue", "Praesent"));

        LinkedHashMap<String, String> inner_map_1 = new LinkedHashMap<String, String>();
        inner_map_1.put("name", "magna");
        inner_map_1.put("relative_file_path", "sed_rhoncus");
        inner_map_1.put("ref_text", "fringilla neque");
        inner_map_1.put("page", "XX");
        HashMap<String, LinkedHashMap<String, String>> outer_map_1 = new HashMap<>();
        outer_map_1.put("anchor", inner_map_1);

        LinkedHashMap<String, String> inner_map_2 = new LinkedHashMap<String, String>();
        inner_map_2.put("name", "abb");
        inner_map_2.put("relative_file_path", "nibh_vel_lacinia");
        inner_map_2.put("ref_text", "augue");
        inner_map_2.put("page", "Praesent");
        HashMap<String, LinkedHashMap<String, String>> outer_map_2 = new HashMap<>();
        outer_map_2.put("anchor", inner_map_2);

        ArrayList<HashMap<String, LinkedHashMap<String, String>>> expected_anchors = new ArrayList<>();
        expected_anchors.add(outer_map_2);
        expected_anchors.add(outer_map_1);
        

        DumpSettings settings = DumpSettings.builder().setDefaultScalarStyle(ScalarStyle.SINGLE_QUOTED).setDefaultFlowStyle(FlowStyle.BLOCK).setWidth(200).build();
        Dump dump = new Dump(settings);
        String expected_results = dump.dumpAllToString(expected_anchors.iterator());

        String results = AnchorFile.convert_anchors_to_yaml(test_anchors);

        assertEquals(expected_results, results);

    }


    @Test
    public void test_load_anchors_from_yaml() {
        HashMap<String, AnchorElement> test_anchors = new HashMap<String, AnchorElement>();
        test_anchors.put("magna", new AnchorElement("magna", "sed_rhoncus", "fringilla neque"));
        test_anchors.put("abb", new AnchorElement("abb", "nibh_vel_lacinia", "augue", "Praesent"));

        String anchors_as_yaml = AnchorFile.convert_anchors_to_yaml(test_anchors);

        HashMap<String, AnchorElement> result_anchors = AnchorFile.convert_yaml_to_anchors(anchors_as_yaml);

        assertEquals(test_anchors, result_anchors);

    }


}
