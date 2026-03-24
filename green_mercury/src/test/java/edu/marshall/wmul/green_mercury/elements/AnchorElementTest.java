package edu.marshall.wmul.green_mercury.elements;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;

import org.junit.jupiter.api.Test;

public class AnchorElementTest {
    
    @Test
    public void test_equals() {
        AnchorElement element_1 = new AnchorElement("magna", "", "fringilla neque");
        AnchorElement test_1 = new AnchorElement("magna", "", "fringilla neque");
        AnchorElement element_2 = new AnchorElement("abb", "", "augue", "Praesent");
        AnchorElement test_2 = new AnchorElement("abb", "", "augue", "Praesent");

        assertEquals(element_1, test_1);
        assertEquals(element_2, test_2);
    }


    @Test
    public void test_anchor_from_hashmap() {
        LinkedHashMap<String, String> inner_map_1 = new LinkedHashMap<String, String>();
        inner_map_1.put("name", "magna");
        inner_map_1.put("ref_text", "fringilla neque");
        inner_map_1.put("page", "XX");
        inner_map_1.put("relative_file_path", "sed_rhoncus");

        HashMap<String, LinkedHashMap<String, String>> outer_map_1 = new HashMap<>();
        outer_map_1.put("anchor", inner_map_1);

        LinkedHashMap<String, String> inner_map_2 = new LinkedHashMap<String, String>();
        inner_map_2.put("name", "a");
        inner_map_2.put("ref_text", "augue");
        inner_map_2.put("page", "Praesent");
        inner_map_2.put("relative_file_path", "nibh_vel_lacinia");
        HashMap<String, LinkedHashMap<String, String>> outer_map_2 = new HashMap<>();
        outer_map_2.put("anchor", inner_map_2);

        AnchorElement expected_1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque", "XX");
        AnchorElement expected_2 = new AnchorElement("a", "nibh_vel_lacinia", "augue", "Praesent");

        AnchorElement result_1 = AnchorElement.load_from_hashmap(outer_map_1);
        AnchorElement result_2 = AnchorElement.load_from_hashmap(outer_map_2);

        
        assertEquals(expected_1, result_1);
        assertEquals(expected_2, result_2);

    }

    @Test
    public void test_to_hashmap_for_yaml() {
        LinkedHashMap<String, String> inner_map_1 = new LinkedHashMap<String, String>();
        inner_map_1.put("name", "magna");
        inner_map_1.put("ref_text", "fringilla neque");
        inner_map_1.put("page", "XX");
        inner_map_1.put("relative_file_path", "sed_rhoncus");

        HashMap<String, LinkedHashMap<String, String>> outer_map_1 = new HashMap<>();
        outer_map_1.put("anchor", inner_map_1);

        LinkedHashMap<String, String> inner_map_2 = new LinkedHashMap<String, String>();
        inner_map_2.put("name", "a");
        inner_map_2.put("ref_text", "augue");
        inner_map_2.put("page", "Praesent");
        inner_map_2.put("relative_file_path", "nibh_vel_lacinia");
        HashMap<String, LinkedHashMap<String, String>> outer_map_2 = new HashMap<>();
        outer_map_2.put("anchor", inner_map_2);

        AnchorElement anchor_1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque", "XX");
        AnchorElement anchor_2 = new AnchorElement("a", "nibh_vel_lacinia", "augue", "Praesent");

        HashMap<String, LinkedHashMap<String, String>> result_1 = anchor_1.to_hashmap_for_yaml();
        HashMap<String, LinkedHashMap<String, String>> result_2 = anchor_2.to_hashmap_for_yaml();

        assertEquals(outer_map_1, result_1);
        assertEquals(outer_map_2, result_2);
    }

    @Test
    public void test_output_string() {
        AnchorElement anchor_1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "21");
        AnchorElement anchor_2 = new AnchorElement("a", "nibh_vel_lacinia", "augue plahah", "Praesent");

        String expected_1 = "[#magna, reftext=\"fringilla neque on Page 21\"]";
        String expected_2 = "[#a, reftext=\"augue plahah\"]";

        String result_1 = anchor_1.get_output_string();
        String result_2 = anchor_2.get_output_string();

        assertEquals(expected_1, result_1);
        assertEquals(expected_2, result_2);
    }

    @Test
    public void test_update_page_number_from_anchor_file() {
        AnchorElement anchor_1 = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "XX");
        
        AnchorElement updated = new AnchorElement("magna", "sed_rhoncus", "fringilla neque on Page [page]", "21");
        
        anchor_1.update_page_number_from_anchor_file(updated);

        assertEquals("21", anchor_1.get_page());

    }

}
