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

import java.util.HashMap;

class GatherAnchorsListener extends GreenMercuryParserBaseListener {
    HashMap<String, AnchorElement> _anchors;
    HashMap<String, AnchorElement> _anchors_from_anchor_file;
    String _relative_file_path;
    AnchorTag _current_anchor;

    public GatherAnchorsListener(HashMap<String, AnchorElement> anchors, HashMap<String, AnchorElement> anchors_from_anchor_file, String relative_file_path) {
        this._anchors = anchors;
        this._anchors_from_anchor_file = anchors_from_anchor_file;
        this._relative_file_path = relative_file_path;
        this._current_anchor = null;
    }

    @Override 
    public void enterSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tag_name = ctx.NAME().toString().toLowerCase();
        switch (tag_name) {
            case "anchor":
                AnchorTag anchor_tag = new AnchorTag(this._relative_file_path);
                this._current_anchor = anchor_tag;
                break;
            default:
                break;
        }
    }

    @Override 
    public void exitSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx) { 
        String tag_name = ctx.NAME().toString().toLowerCase();
        switch (tag_name) {
            case "anchor":
                AnchorElement anchor_element = this._current_anchor.to_element();

                String anchor_element_name = anchor_element.get_name();

                if (this._anchors.containsKey(anchor_element_name)) {
                    throw new RuntimeException("Duplicate Anchors detected. Two anchors with the name " + anchor_element_name + " have been detected. The second is in " + this._relative_file_path + " at " + ctx.start.toString());
                }

                AnchorElement anchor_element_from_anchor_file = this._anchors_from_anchor_file.getOrDefault(anchor_element_name, null);

                if (anchor_element_from_anchor_file != null) {
                    anchor_element.update_page_number_from_anchor_file(anchor_element_from_anchor_file);
                }

                this._anchors.put(anchor_element_name, anchor_element);
                this._anchors_from_anchor_file.put(anchor_element_name, anchor_element);
                this._current_anchor = null;
                break;
            default:
                break;
        }
    }

    @Override 
    public void enterAttribute(GreenMercuryParser.AttributeContext ctx) { 
        if (this._current_anchor != null) {
            AnchorTag element = this._current_anchor;
            String attribute_name = ctx.NAME().toString().toLowerCase();
            String attribute_value = ctx.STRING().toString().replace("\"", "");
            element.add_attribute(attribute_name, attribute_value);
        }
    }
}