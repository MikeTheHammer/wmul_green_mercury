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

package edu.marshall.wmul.green_mercury.elements;

public class XrefElement {
    String _name;
    String _ref_text;

    public XrefElement(String name) {
        this(name, "");
    }

    public XrefElement(String name, String ref_text) {
        this._name = name;
        this._ref_text = ref_text;
    }

    public String get_name() {
        return this._name;
    }

    public String get_output_string(AnchorElement anchor_element) {
        String ref_text = this._ref_text;
        if (ref_text == "") {
            ref_text = anchor_element.get_interpolated_ref_text();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<<");
        sb.append(anchor_element.get_relative_file_path());
        sb.append("#");
        sb.append(anchor_element.get_name());
        sb.append(", ");
        sb.append(ref_text);
        sb.append(">>");

        return sb.toString();
    }
}
