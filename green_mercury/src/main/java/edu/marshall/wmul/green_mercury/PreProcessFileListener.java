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

import java.util.HashMap;
import java.util.Map;

import edu.marshall.wmul.green_mercury.antlr.*;
import edu.marshall.wmul.green_mercury.elements.AnchorElement;
import edu.marshall.wmul.green_mercury.elements.AnchorTag;
import edu.marshall.wmul.green_mercury.elements.XrefElement;
import edu.marshall.wmul.green_mercury.elements.XrefTag;
import edu.marshall.wmul.green_mercury.exceptions.UnknownTagException;
import edu.marshall.wmul.green_mercury.exceptions.UnmatchedXrefException;

public class PreProcessFileListener extends GreenMercuryParserBaseListener {
    StringBuilder textBuffer;
    Map<String, AnchorElement> anchors;
    String relativeFilePath;
    AnchorTag currentAnchor;
    XrefTag currentXref;

    public PreProcessFileListener(Map<String, AnchorElement> anchors, String relativeFilePath) {
        this.textBuffer = new StringBuilder();
        this.anchors = anchors;
        this.relativeFilePath = relativeFilePath;
        this.currentAnchor = null;
        this.currentXref = null;
    }

    @Override 
    public void enterSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tagName = ctx.NAME().toString().toLowerCase();
        switch (tagName) {
            case "anchor":
                AnchorTag anchorTag = new AnchorTag(this.relativeFilePath);
                this.currentAnchor = anchorTag;
                break;
            case "xref":
                XrefTag xrefTag = new XrefTag();
                this.currentXref = xrefTag;
                break;
            default:
                throw new UnknownTagException("Unknown tag detected " + tagName);
        }
    }

    @Override 
    public void exitSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tagName = ctx.NAME().toString().toLowerCase();
        AnchorElement anchorElement;
        switch (tagName) {
            case "anchor":
                anchorElement = this.currentAnchor.to_element();

                String anchorElementName = anchorElement.getName();
                if (this.anchors.containsKey(anchorElementName)) {
                    AnchorElement anchorElementFromAnchorFile = this.anchors.get(anchorElementName);
                    anchorElement.updatePageNumberFromOtherAnchor(anchorElementFromAnchorFile);
                }

                this.textBuffer.append(anchorElement.getOutputString());
                this.currentAnchor = null;
                break;
            case "xref":
                XrefElement xrefElement = this.currentXref.to_element();
                String xrefElementName = xrefElement.getName();
                if (!this.anchors.containsKey(xrefElementName)) {
                    throw new UnmatchedXrefException("Xref with no matching Anchor. Xref named " + xrefElementName + " was found in " + this.relativeFilePath + " at " + ctx.start.toString());
                }
                anchorElement = this.anchors.get(xrefElementName);
                this.textBuffer.append(xrefElement.getOutputString(anchorElement));
                this.currentXref = null;
                break;
            default:
                break;
        }
    }

    @Override 
    public void enterAttribute(GreenMercuryParser.AttributeContext ctx) { 
        if (this.currentAnchor != null) {
            AnchorTag anchorTag = this.currentAnchor;
            String attributeName = ctx.NAME().toString().toLowerCase();
            String attributeValue = ctx.STRING().toString().replace("\"", "");
            anchorTag.add_attribute(attributeName, attributeValue);
        }
        if (this.currentXref != null) {
            XrefTag xrefTag = this.currentXref;
            String attributeName = ctx.NAME().toString().toLowerCase();
            String attributeValue = ctx.STRING().toString().replace("\"", "");
            xrefTag.add_attribute(attributeName, attributeValue);
        }
    }

    @Override 
    public void enterChardata(GreenMercuryParser.ChardataContext ctx) { 
        this.textBuffer.append(ctx.getText());
    }

    public String get_output_string() {
        return this.textBuffer.toString();
    }
}
