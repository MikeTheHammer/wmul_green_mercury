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
import java.util.Map;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.marshall.wmul.green_mercury.antlr.*;
import edu.marshall.wmul.green_mercury.antlr.GreenMercuryParser.DocumentContext;
import edu.marshall.wmul.green_mercury.elements.*;

public class PreProcessFile {

    private static final Logger logger = LogManager.getLogger(PreProcessFile.class);

    private PreProcessFile() {
        /* This utility class should not be instantiated */
    }
    
    public static String preprocessInput(CharStream inputStream,  Map<String, AnchorElement> anchorsFromAnchorFile, String relativeFilePath) {
        HashMap<String, AnchorElement> anchorsThisTime = new HashMap<>();
        GatherAnchors.gatherAnchors(inputStream, anchorsThisTime, anchorsFromAnchorFile, relativeFilePath);
        inputStream.seek(0);

        GreenMercuryLexer lexer = new GreenMercuryLexer(inputStream);
        CommonTokenStream stream = new CommonTokenStream(lexer);
        GreenMercuryParser parser = new GreenMercuryParser(stream);

        DocumentContext tree = parser.document();

        PreProcessFileListener listener = new PreProcessFileListener(anchorsFromAnchorFile, relativeFilePath);
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(listener, tree);
        return listener.get_output_string();
    }
    
    public static String preprocessInputFromFile(String filename, Map<String, AnchorElement> anchorsFromAnchorFile, String relativeFilePath) throws IOException {
        CharStream inputStream = CharStreams.fromFileName(filename, StandardCharsets.UTF_8);
        return PreProcessFile.preprocessInput(inputStream, anchorsFromAnchorFile, relativeFilePath);
    }

    public static String preprocessInputFromString(String inputString, Map<String, AnchorElement> anchorsFromAnchorFile) {
        CharStream inputStream = CharStreams.fromString(inputString);
        String relativeFilePath = "";
        return PreProcessFile.preprocessInput(inputStream, anchorsFromAnchorFile, relativeFilePath);
    }

    private static void writeOutputTextToFile(File outputFilename, String outputText) throws IOException {
        File parent = outputFilename.getParentFile();
        parent.mkdirs();
        try (FileWriter writer = new FileWriter(outputFilename)) {
            writer.write(outputText);
        } catch (IOException e) {
            logger.error("Unable to write output to output file. {}", outputFilename, e);
            throw e;
        }
    }

    public static void preprocessFilesInBuildFolder(File asciidocSourceFolder, File asciidocBuildFolder, File anchorFile) throws IOException {
        Map<String, AnchorElement> anchorsFromAnchorFile = AnchorFile.loadAnchorsFromAnchorFile(anchorFile);

        Path rootPath = asciidocSourceFolder.toPath();

        List<Path> pathList = new ArrayList<>();

        try (Stream<Path> stream = Files.walk(rootPath)) {
            pathList = stream.map(Path::normalize)
                             .filter(Files::isRegularFile)
                             .filter(path -> path.getFileName().toString().endsWith(".adoc"))
                             .toList();
        }

        for (Path source_file : pathList) {
            String relativeFilePath = rootPath.relativize(source_file).toString();
            String strFilename = source_file.toString();
            String outputString = PreProcessFile.preprocessInputFromFile(strFilename, anchorsFromAnchorFile, relativeFilePath);
            File outputFile = new File(asciidocBuildFolder, relativeFilePath);
            PreProcessFile.writeOutputTextToFile(outputFile, outputString);
        }
    }

}
