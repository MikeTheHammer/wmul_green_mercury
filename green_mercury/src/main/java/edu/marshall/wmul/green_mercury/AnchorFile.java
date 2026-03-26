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
import java.util.Map;

import org.snakeyaml.engine.v2.api.Dump;
import org.snakeyaml.engine.v2.api.DumpSettings;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.snakeyaml.engine.v2.common.FlowStyle;
import org.snakeyaml.engine.v2.common.ScalarStyle;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import edu.marshall.wmul.green_mercury.elements.AnchorElement;
import edu.marshall.wmul.green_mercury.exceptions.AnchorCastException;


public class AnchorFile {
    
    private static final Logger logger = LogManager.getLogger(AnchorFile.class);

    private AnchorFile() {
        /* This utility class should not be instantiated */
    }

    public static String convertAnchorsToYAML(Map<String, AnchorElement> anchors) {
        logger.info("AnchorFile.convertAnchorsToYAML:: Converting anchors to yaml.");
        ArrayList<AnchorElement> listOfAnchors = new ArrayList<>();
        listOfAnchors.addAll(anchors.values());
        listOfAnchors.sort(null);

        ArrayList<Map<String, LinkedHashMap<String, String>>> outputMap = new ArrayList<>();
        for (AnchorElement anchor_element : listOfAnchors) {
            outputMap.add(anchor_element.toMapForYAML());
        }

        DumpSettings settings = DumpSettings.builder().setDefaultScalarStyle(ScalarStyle.SINGLE_QUOTED).setDefaultFlowStyle(FlowStyle.BLOCK).setWidth(200).build();
        Dump dump = new Dump(settings);
        return dump.dumpAllToString(outputMap.iterator());
    }

    public static void writeAnchorsToFile(Map<String, AnchorElement> anchors, File outputFilename) throws IOException {
        logger.info("AnchorFile.write_anchors_to_file:: Writing anchors to file {}", outputFilename);
        String anchorsAsYAML = AnchorFile.convertAnchorsToYAML(anchors);
        try (FileWriter writer = new FileWriter(outputFilename)) {
            writer.write(anchorsAsYAML);
        } catch (IOException e) {
            logger.error("Unable to write anchors to anchor file.", e);
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, AnchorElement> convertYAMLToAnchors(String anchorsYAML) {
        logger.info("AnchorFile.convert_yaml_to_anchors:: Converting YAML to Anchors");
        LoadSettings settings = LoadSettings.builder().build();
        Load load = new Load(settings);

        Iterable<Object> anchorsFromYAML = load.loadAllFromString(anchorsYAML);

        HashMap<String, AnchorElement> anchors = new HashMap<>();

        for (Object anchor_object : anchorsFromYAML) {
            HashMap<String, LinkedHashMap<String, String>> anchorHashmap;
            try {
                anchorHashmap = (HashMap<String, LinkedHashMap<String, String>>) anchor_object;
            } catch (ClassCastException e) {
                throw new AnchorCastException("Problem with anchors file. The anchor " + anchor_object.toString() + " is not a well-formed anchor.", e);
            }
            AnchorElement anchor = AnchorElement.loadFromMap(anchorHashmap);
            anchors.put(anchor.getName(), anchor);
        }
        logger.info(anchors);
        return anchors;
    }

    public static Map<String, AnchorElement> loadAnchorsFromAnchorFile(File anchorFilename) throws IOException {
        logger.info("AnchorFile.load_anchors_from_anchor_file:: Reading anchors from file {}", anchorFilename);
        String anchorsAsYAML;
        try (FileReader fr = new FileReader(anchorFilename)) {
            anchorsAsYAML = fr.readAllAsString();
        } catch (IOException e) {
            logger.error("A problem occurred when opening the anchor file", e);
            throw e;
        }
        return AnchorFile.convertYAMLToAnchors(anchorsAsYAML);
    }

}
