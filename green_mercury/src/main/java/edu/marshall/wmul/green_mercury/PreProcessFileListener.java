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

import edu.marshall.wmul.green_mercury.antlr.*;
import edu.marshall.wmul.green_mercury.elements.AnchorElement;
import edu.marshall.wmul.green_mercury.elements.AnchorTag;
import edu.marshall.wmul.green_mercury.elements.XrefElement;
import edu.marshall.wmul.green_mercury.elements.XrefTag;

public class PreProcessFileListener extends GreenMercuryParserBaseListener {
    StringBuilder _text_buffer;
    HashMap<String, AnchorElement> _anchors;
    String _relative_file_path;
    AnchorTag _current_anchor;
    XrefTag _current_xref;

    public PreProcessFileListener(HashMap<String, AnchorElement> anchors, String relative_file_path) {
        this._text_buffer = new StringBuilder();
        this._anchors = anchors;
        this._relative_file_path = relative_file_path;
        this._current_anchor = null;
        this._current_xref = null;
    }

    @Override 
    public void enterSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tag_name = ctx.NAME().toString().toLowerCase();
        switch (tag_name) {
            case "anchor":
                AnchorTag anchor_tag = new AnchorTag(this._relative_file_path);
                this._current_anchor = anchor_tag;
                break;
            case "xref":
                XrefTag xref_tag = new XrefTag();
                this._current_xref = xref_tag;
                break;
            default:
                throw new RuntimeException("Unknown tag detected " + tag_name);
        }
    }

    @Override 
    public void exitSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tag_name = ctx.NAME().toString().toLowerCase();
        AnchorElement anchor_element;
        switch (tag_name) {
            case "anchor":
                anchor_element = this._current_anchor.to_element();

                String anchor_element_name = anchor_element.get_name();
                if (this._anchors.containsKey(anchor_element_name)) {
                    AnchorElement anchor_element_from_anchor_file = this._anchors.get(anchor_element_name);
                    anchor_element.update_page_number_from_anchor_file(anchor_element_from_anchor_file);
                }

                this._text_buffer.append(anchor_element.get_output_string());
                this._current_anchor = null;
                break;
            case "xref":
                XrefElement xref_element = this._current_xref.to_element();
                String xref_element_name = xref_element.get_name();
                if (!this._anchors.containsKey(xref_element_name)) {
                    throw new RuntimeException("Xref with no matching Anchor. Xref named " + xref_element_name + " was found in " + this._relative_file_path + " at " + ctx.start.toString());
                }
                anchor_element = this._anchors.get(xref_element_name);
                this._text_buffer.append(xref_element.get_output_string(anchor_element));
                this._current_xref = null;
                break;
            default:
                break;
        }
    }

    @Override 
    public void enterAttribute(GreenMercuryParser.AttributeContext ctx) { 
        if (this._current_anchor != null) {
            AnchorTag anchor_tag = this._current_anchor;
            String attribute_name = ctx.NAME().toString().toLowerCase();
            String attribute_value = ctx.STRING().toString().replace("\"", "");
            anchor_tag.add_attribute(attribute_name, attribute_value);
        }
        if (this._current_xref != null) {
            XrefTag xref_tag = this._current_xref;
            String attribute_name = ctx.NAME().toString().toLowerCase();
            String attribute_value = ctx.STRING().toString().replace("\"", "");
            xref_tag.add_attribute(attribute_name, attribute_value);
        }
    }

    @Override 
    public void enterChardata(GreenMercuryParser.ChardataContext ctx) { 
        this._text_buffer.append(ctx.getText());
    }

    public String get_output_string() {
        return this._text_buffer.toString();
    }
}
