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
import java.io.FileWriter;
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

import edu.marshall.wmul.green_mercury.antlr.*;
import edu.marshall.wmul.green_mercury.antlr.GreenMercuryParser.DocumentContext;
import edu.marshall.wmul.green_mercury.elements.*;

public class PreProcessFile {
    
    public static String preprocess_input(CharStream input_stream,  HashMap<String, AnchorElement> anchors_from_anchor_file, String relative_file_path) {
        HashMap<String, AnchorElement> anchors_this_time = new HashMap<String, AnchorElement>();
        GatherAnchors.gather_anchors(input_stream, anchors_this_time, anchors_from_anchor_file, relative_file_path);
        input_stream.seek(0);

        GreenMercuryLexer lexer = new GreenMercuryLexer(input_stream);
        CommonTokenStream stream = new CommonTokenStream(lexer);
        GreenMercuryParser parser = new GreenMercuryParser(stream);

        DocumentContext tree = parser.document();

        PreProcessFileListener listener = new PreProcessFileListener(anchors_from_anchor_file, relative_file_path);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener.get_output_string();
    }

    
    public static String preprocess_input_from_file(String filename, HashMap<String, AnchorElement> anchors_from_anchor_file, String relative_file_path) throws IOException {
        CharStream input_stream = CharStreams.fromFileName(filename, StandardCharsets.UTF_8);
        return PreProcessFile.preprocess_input(input_stream, anchors_from_anchor_file, relative_file_path);
    }

    public static String preprocess_input_from_string(String input_string, HashMap<String, AnchorElement> anchors_from_anchor_file) {
        CharStream input_stream = CharStreams.fromString(input_string);
        String relative_file_path = "";
        return PreProcessFile.preprocess_input(input_stream, anchors_from_anchor_file, relative_file_path);
    }

    private static void write_output_text_to_file(File output_filename, String output_text) throws IOException {
        try {
            FileWriter Writer = new FileWriter(output_filename);
            Writer.write(output_text);
            Writer.close();
        } catch (IOException e) {
            System.out.println("Unable to write output to output file.");
            e.printStackTrace();
            throw e;
        }
    }

    public static void preprocess_files_in_build_folder(File asciidoc_source_folder, File asciidoc_build_folder, File anchor_file) throws IOException {
        HashMap<String, AnchorElement> anchors_from_anchor_file = AnchorFile.load_anchors_from_anchor_file(anchor_file);

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
            String output_string = PreProcessFile.preprocess_input_from_file(str_filename, anchors_from_anchor_file, relative_file_path);
            File output_file = new File(asciidoc_build_folder, relative_file_path);
            PreProcessFile.write_output_text_to_file(output_file, output_string);
        }
    }


}
