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

import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.ParameterException;

public class CLIValidation {
    
    public static void validate_source_folder(File source_folder, CommandSpec spec) {
        if (!source_folder.isDirectory()) {
            throw new ParameterException(spec.commandLine(), "Source Folder must be a directory");
        }
        if (!source_folder.canRead()) {
            throw new ParameterException(spec.commandLine(), "Source Folder must be readable.");
        }
    }

    public static void validate_build_folder(File build_folder, CommandSpec spec) {
        if (!build_folder.isDirectory()) {
            throw new ParameterException(spec.commandLine(), "Build Folder must be a directory");
        }
        if (!build_folder.canWrite()) {
            throw new ParameterException(spec.commandLine(), "Build Folder must be writable.");
        }
    }

    public static void validate_anchor_file_readable(File anchor_file, CommandSpec spec) {
        if (!anchor_file.canRead()) {
            throw new ParameterException(spec.commandLine(), "Anchor File must be readable.");
        }
    }

    public static void validate_anchor_file_writable(File anchor_file, CommandSpec spec) {
        if (anchor_file.exists() && !anchor_file.canWrite()) {
            throw new ParameterException(spec.commandLine(), "Anchor File must be writable.");
        }
    }

}
