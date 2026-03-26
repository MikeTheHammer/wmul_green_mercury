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

import edu.marshall.wmul.green_mercury.antlr.*;
import edu.marshall.wmul.green_mercury.elements.AnchorElement;
import edu.marshall.wmul.green_mercury.elements.AnchorTag;
import edu.marshall.wmul.green_mercury.exceptions.DuplicateAnchorException;

import java.util.Map;

class GatherAnchorsListener extends GreenMercuryParserBaseListener {
    Map<String, AnchorElement> anchors;
    Map<String, AnchorElement> anchorsFromAnchorFile;
    String relativeFilePath;
    AnchorTag currentAnchor;

    public GatherAnchorsListener(Map<String, AnchorElement> anchors, Map<String, AnchorElement> anchorsFromAnchorFile, String relativeFilePath) {
        this.anchors = anchors;
        this.anchorsFromAnchorFile = anchorsFromAnchorFile;
        this.relativeFilePath = relativeFilePath;
        this.currentAnchor = null;
    }

    @Override 
    public void enterSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tagName = ctx.NAME().toString().toLowerCase();
        if (tagName.equals("anchor")) {
        AnchorTag anchorTag = new AnchorTag(this.relativeFilePath);
                this.currentAnchor = anchorTag;
         
        }
    }

    @Override 
    public void exitSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tagName = ctx.NAME().toString().toLowerCase();
        if (tagName.equals("anchor")) {
            AnchorElement anchorElement = this.currentAnchor.to_element();

            String anchorElementName = anchorElement.getName();

            if (this.anchors.containsKey(anchorElementName)) {
                throw new DuplicateAnchorException("Duplicate Anchors detected. Two anchors with the name " + anchorElementName + " have been detected. The second is in " + this.relativeFilePath + " at " + ctx.start.toString());
            }

            AnchorElement anchorElementFromAnchorFile = this.anchorsFromAnchorFile.getOrDefault(anchorElementName, null);

            if (anchorElementFromAnchorFile != null) {
                anchorElement.updatePageNumberFromOtherAnchor(anchorElementFromAnchorFile);
            }

            this.anchors.put(anchorElementName, anchorElement);
            this.anchorsFromAnchorFile.put(anchorElementName, anchorElement);
            this.currentAnchor = null;
        }
    }

    @Override 
    public void enterAttribute(GreenMercuryParser.AttributeContext ctx) { 
        if (this.currentAnchor != null) {
            AnchorTag element = this.currentAnchor;
            String attributeName = ctx.NAME().toString().toLowerCase();
            String attributeValue = ctx.STRING().toString().replace("\"", "");
            element.add_attribute(attributeName, attributeValue);
        }
    }
}