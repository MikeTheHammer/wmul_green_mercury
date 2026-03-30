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

@Command(subcommands = {
    GatherAnchorsCLI.class,
    PreProcessFileCLI.class
}, mixinStandardHelpOptions = true, version="green-mercury 0.0.3")
public class CLI implements Runnable {
    
     @Option(names = {"--license"}, required = false, description = "Print the license for this program and exit.")
    private boolean license;

    public static void main(String[] args)  {
        int exitCode = new CommandLine(new CLI()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        if (this.license) {
            String licenseText = """
WMUL Green Mercury 
MIT License
Copyright (c) 2026 Mike Stanley

ANTLR4
The BSD License
Copyright (c) 2012 Terence Parr and Sam Harwell
https://www.antlr.org/index.html
https://www.antlr.org/license.html

Picocli
Apache 2.0 License
https://picocli.info/
https://github.com/remkop/picocli/blob/main/LICENSE

Log4J2
Apache 2.0 License
https://logging.apache.org/log4j/2.x/index.html
""";
            System.out.println(licenseText);
        }
    }

}
