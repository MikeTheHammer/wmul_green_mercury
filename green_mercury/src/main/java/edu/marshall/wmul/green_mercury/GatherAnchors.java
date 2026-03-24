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
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import edu.marshall.wmul.green_mercury.antlr.GreenMercuryLexer;
import edu.marshall.wmul.green_mercury.antlr.GreenMercuryParser;
import edu.marshall.wmul.green_mercury.antlr.GreenMercuryParser.DocumentContext;
import edu.marshall.wmul.green_mercury.elements.AnchorElement;

public class GatherAnchors {

    public static void gather_anchors(CharStream input_stream, HashMap<String, AnchorElement> anchors, HashMap<String, AnchorElement> anchors_from_anchor_file, String relative_file_path) {
        GreenMercuryLexer lexer = new GreenMercuryLexer(input_stream);
        CommonTokenStream stream = new CommonTokenStream(lexer);
        GreenMercuryParser parser = new GreenMercuryParser(stream);

        DocumentContext tree = parser.document();

        GatherAnchorsListener listener = new GatherAnchorsListener(anchors, anchors_from_anchor_file, relative_file_path);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
    }

    public static void gather_anchors_from_file(String filename, HashMap<String, AnchorElement> anchors, HashMap<String, AnchorElement> anchors_from_anchor_file, String relative_file_path) throws IOException {
        CharStream input_stream = CharStreams.fromFileName(filename, StandardCharsets.UTF_8);
        GatherAnchors.gather_anchors(input_stream, anchors, anchors_from_anchor_file, relative_file_path);
    }

    public static void gather_anchors_from_string(String input_string, HashMap<String, AnchorElement> anchors, HashMap<String, AnchorElement> anchors_from_anchor_file) {
        CharStream input_stream = CharStreams.fromString(input_string);
        String relative_file_path = "";
        GatherAnchors.gather_anchors(input_stream, anchors, anchors_from_anchor_file, relative_file_path);
    }

    public static void gather_anchors_from_src_folder(File asciidoc_source_folder, File anchor_output_file) throws IOException {
        HashMap<String, AnchorElement> anchors = new HashMap<>();
        HashMap<String, AnchorElement> anchors_from_anchor_file;
        if (anchor_output_file.exists()) {
            anchors_from_anchor_file = AnchorFile.load_anchors_from_anchor_file(anchor_output_file);
        } else {
            anchors_from_anchor_file = new HashMap<String, AnchorElement>();
        }

        Path root_path = asciidoc_source_folder.toPath();

        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(root_path)) {
            pathList = stream.map(Path::normalize)
                             .filter(Files::isRegularFile)
                             .filter(path -> path.getFileName().toString().endsWith(".adoc"))
                             .collect(Collectors.toList());
        }

        for (Path source_file : pathList) {
            String relative_file_path = root_path.relativize(source_file).toString();
            String str_filename = source_file.toString();
            GatherAnchors.gather_anchors_from_file(str_filename, anchors, anchors_from_anchor_file, relative_file_path);
        }
        AnchorFile.write_anchors_to_file(anchors, anchor_output_file);
    }

}
