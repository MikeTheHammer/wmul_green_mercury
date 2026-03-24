/*
MIT License

Copyright (c) 2026 Mike Stanley

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package edu.marshall.wmul.green_mercury;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;


public class AnchorFile {
    
    private static final Logger logger = LogManager.getLogger(GatherAnchorsCLI.class);

    public static String convert_anchors_to_yaml(HashMap<String, AnchorElement> anchors) {
        logger.info("AnchorFile.convert_anchors_to_yaml:: Converting anchors to yaml.");
        ArrayList<AnchorElement> list_of_anchors = new ArrayList<>();
        list_of_anchors.addAll(anchors.values());
        list_of_anchors.sort(null);

        ArrayList<HashMap<String, LinkedHashMap<String, String>>> output_map = new ArrayList<>();
        for (AnchorElement anchor_element : list_of_anchors) {
            output_map.add(anchor_element.to_hashmap_for_yaml());
        }

        DumpSettings settings = DumpSettings.builder().setDefaultScalarStyle(ScalarStyle.SINGLE_QUOTED).setDefaultFlowStyle(FlowStyle.BLOCK).setWidth(200).build();
        Dump dump = new Dump(settings);
        return dump.dumpAllToString(output_map.iterator());
    }

    public static void write_anchors_to_file(HashMap<String, AnchorElement> anchors, File output_filename) throws IOException {
        logger.info("AnchorFile.write_anchors_to_file:: Writing anchors to file " + output_filename);
        String anchors_as_yaml = AnchorFile.convert_anchors_to_yaml(anchors);
        try {
            FileWriter Writer = new FileWriter(output_filename);
            Writer.write(anchors_as_yaml);
            Writer.close();
        } catch (IOException e) {
            logger.error("Unable to write anchors to anchor file.", e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public static HashMap<String, AnchorElement> convert_yaml_to_anchors(String anchors_yaml) {
        logger.info("AnchorFile.convert_yaml_to_anchors:: Converting YAML to Anchors");
        LoadSettings settings = LoadSettings.builder().build();
        Load load = new Load(settings);

        Iterable<Object> anchors_from_yaml = load.loadAllFromString(anchors_yaml);

        HashMap<String, AnchorElement> anchors = new HashMap<>();

        for (Object anchor_object : anchors_from_yaml) {
            HashMap<String, LinkedHashMap<String, String>> anchor_hashmap;
            try {
                anchor_hashmap = (HashMap<String, LinkedHashMap<String, String>>) anchor_object;
            } catch (ClassCastException e) {
                throw new RuntimeException("Problem with anchors file. The anchor " + anchor_object.toString() + " is not a well-formed anchor.");
            }
            AnchorElement anchor = AnchorElement.load_from_hashmap(anchor_hashmap);
            anchors.put(anchor.get_name(), anchor);
        }
        logger.info(anchors);
        return anchors;
    }

    public static HashMap<String, AnchorElement> load_anchors_from_anchor_file(File anchor_file) throws IOException {
        logger.info("AnchorFile.load_anchors_from_anchor_file:: Reading anchors from file " + anchor_file);
        String anchors_as_yaml;
        try {
            FileReader fr = new FileReader(anchor_file);
            anchors_as_yaml = fr.readAllAsString();
            fr.close();
        } catch (IOException e) {
            logger.error("A problem occurred when opening the anchor file", e);
            throw e;
        }
        return AnchorFile.convert_yaml_to_anchors(anchors_as_yaml);
    }

}
