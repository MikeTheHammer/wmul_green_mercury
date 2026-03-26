package edu.marshall.wmul.green_mercury.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

class AnchorElementTest {
    
    @Test
    void test_equals() {
        AnchorElement element1 = new AnchorElement("magna", "", "fringilla neque");
        AnchorElement test1 = new AnchorElement("magna", "", "fringilla neque");
        AnchorElement element2 = new AnchorElement("abb", "", "augue", "Praesent");
        AnchorElement test2 = new AnchorElement("abb", "", "augue", "Praesent");

        assertEquals(element1, test1);
        assertEquals(element2, test2);
    }


    @Test
    void test_anchor_from_hashmap() {
        LinkedHashMap<String, String> innerMap1 = new LinkedHashMap<String, String>();
        innerMap1.put("name", "magna");
        innerMap1.put("ref_text", "fringilla neque");
        innerMap1.put("page", "XX");
        innerMap1.put("relative_file_path", "sed_rhoncus");

        HashMap<String, LinkedHashMap<String, String>> outerMap1 = new HashMap<>();
        outerMap1.put("anchor", innerMap1);

        LinkedHashMap<String, String> innerMap2 = new LinkedHashMap<String, String>();
        innerMap2.put("name", "a");
        innerMap2.put("ref_text", "augue");
        innerMap2.put("page", "Praesent");
        innerMap2.put("relative_file_path", "nibh_vel_lacinia");
        HashMap<String, LinkedHashMap<String, String>> outerMap2 = new HashMap<>();
        outerMap2.put("anchor", innerMap2);

        AnchorElement expected1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque", "XX");
        AnchorElement expected2 = new AnchorElement("a", "nibh_vel_lacinia", "augue", "Praesent");

        AnchorElement result1 = AnchorElement.loadFromMap(outerMap1);
        AnchorElement result2 = AnchorElement.loadFromMap(outerMap2);

        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }

    @Test
    void test_to_hashmap_for_yaml() {
        LinkedHashMap<String, String> innerMap1 = new LinkedHashMap<String, String>();
        innerMap1.put("name", "magna");
        innerMap1.put("ref_text", "fringilla neque");
        innerMap1.put("page", "XX");
        innerMap1.put("relative_file_path", "sed_rhoncus");

        HashMap<String, LinkedHashMap<String, String>> outerMap1 = new HashMap<>();
        outerMap1.put("anchor", innerMap1);

        LinkedHashMap<String, String> innerMap2 = new LinkedHashMap<String, String>();
        innerMap2.put("name", "a");
        innerMap2.put("ref_text", "augue");
        innerMap2.put("page", "Praesent");
        innerMap2.put("relative_file_path", "nibh_vel_lacinia");
        HashMap<String, LinkedHashMap<String, String>> outerMap2 = new HashMap<>();
        outerMap2.put("anchor", innerMap2);

        AnchorElement anchor1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque", "XX");
        AnchorElement anchor2 = new AnchorElement("a", "nibh_vel_lacinia", "augue", "Praesent");

        Map<String, LinkedHashMap<String, String>> result1 = anchor1.toMapForYAML();
        Map<String, LinkedHashMap<String, String>> result2 = anchor2.toMapForYAML();

        assertEquals(outerMap1, result1);
        assertEquals(outerMap2, result2);
    }

    @Test
    void test_output_string() {
        AnchorElement anchor1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "21");
        AnchorElement anchor2 = new AnchorElement("a", "nibh_vel_lacinia", "augue plahah", "Praesent");

        String expected1 = "[#magna, reftext=\"fringilla neque on Page 21\"]";
        String expected2 = "[#a, reftext=\"augue plahah\"]";

        String result1 = anchor1.getOutputString();
        String result2 = anchor2.getOutputString();

        assertEquals(expected1, result1);
        assertEquals(expected2, result2);
    }

    @Test
    void test_update_page_number_from_anchor_file() {
        AnchorElement anchor1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "XX");
        
        AnchorElement updated = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "21");
        
        anchor1.updatePageNumberFromOtherAnchor(updated);

        assertEquals("21", anchor1.getPage());

    }

}
