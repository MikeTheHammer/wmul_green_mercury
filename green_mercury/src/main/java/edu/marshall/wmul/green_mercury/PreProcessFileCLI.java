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

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Model.CommandSpec;

import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Command(name = "preprocess-file", mixinStandardHelpOptions = true, version="preprocess-file 0.0.2", 
description = "Loads anchors from --anchor_file. Then recursively iterates through all of the asciidoc files inside --asciidoc_build_folder and replaces all of the anchors and xrefs.")
public class PreProcessFileCLI implements Runnable {
    
    @Option(names = {"--asciidoc_source_folder"}, required = true, description = "The folder containing all of the asciidoc files.")
    private File asciidocSourceFolder;

    @Option(names = {"--asciidoc_build_folder"}, required = true, description = "The folder to which the processed asciidoc files will be written.")
    private File asciidocBuildFolder;

    @Option(names = {"--anchor_file"}, required = true, description = "The yaml file from which the anchors will be read.")
    private File anchorFile;

    @Spec CommandSpec spec;

    private static final Logger logger = LogManager.getLogger(PreProcessFileCLI.class);

    public static void main(String[] args)  {
        int exitCode = new CommandLine(new PreProcessFileCLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        CLIValidation.validate_source_folder(asciidocSourceFolder, spec);
        CLIValidation.validate_build_folder(asciidocBuildFolder, spec);
        CLIValidation.validate_anchor_file_readable(anchorFile, spec);
        try{
            PreProcessFile.preprocessFilesInBuildFolder(asciidocSourceFolder, asciidocBuildFolder, anchorFile);
        } catch (IOException e) {
            logger.error(e);
        } 
    }


}
