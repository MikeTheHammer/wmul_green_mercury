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
import java.util.HashMap;
import java.util.LinkedHashMap;

public class AnchorElement implements Comparable<AnchorElement> {
    String _name;
    String _relative_file_path;
    String _ref_text;
    String _page;

    public AnchorElement(String name, String relative_file_path, String ref_text){
        this._name = name;
        this._relative_file_path = relative_file_path;
        this._ref_text = ref_text;
        this._page = "XX";
    }

    public AnchorElement(String name, String relative_file_path, String ref_text, String page){
        this._name = name;
        this._relative_file_path = relative_file_path;
        this._ref_text = ref_text;
        this._page = page;
    } 

    public String get_name() {
        return this._name;
    }

    public String get_page() {
        return this._page;
    }

    public String get_relative_file_path() {
        return this._relative_file_path;
    }

    public String get_raw_ref_text() {
        return this._ref_text;
    }

    public void update_page_number_from_anchor_file(AnchorElement anchor_element_from_anchor_file) {
        if (anchor_element_from_anchor_file.get_page() != "XX") {
            this._page = anchor_element_from_anchor_file.get_page();
        }
    }

    public String get_interpolated_ref_text() {
        return this._ref_text.replace("[page]", this._page);
    }

    public String get_output_string() {
        StringBuilder output_buffer = new StringBuilder();
        output_buffer.append("[#");
        output_buffer.append(this._name);
        output_buffer.append(", reftext=\"");
        output_buffer.append(this.get_interpolated_ref_text());
        output_buffer.append("\"]");
        return output_buffer.toString();
    }

    public HashMap<String, LinkedHashMap<String, String>> to_hashmap_for_yaml(){
        LinkedHashMap<String, String> inner_hashmap = new LinkedHashMap<>();
        inner_hashmap.put("name", this._name);
        inner_hashmap.put("relative_file_path", this._relative_file_path);
        inner_hashmap.put("ref_text", this._ref_text);
        inner_hashmap.put("page", this._page);

        HashMap<String, LinkedHashMap<String, String>> outer_hashmap = new HashMap<>();
        outer_hashmap.put("anchor", inner_hashmap);

        return outer_hashmap;
    }

    public static AnchorElement load_from_hashmap(HashMap<String, LinkedHashMap<String, String>> input_hashmap) {
        HashMap<String, String> inner_hashmap = input_hashmap.get("anchor");
        String name = inner_hashmap.get("name");
        String relative_file_path = inner_hashmap.get("relative_file_path");
        String ref_text = inner_hashmap.get("ref_text");
        String page = inner_hashmap.get("page");

        return new AnchorElement(name, relative_file_path, ref_text, page);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final AnchorElement other_element = (AnchorElement) other;

        return (
            this._name.equals(other_element.get_name()) && 
            this._relative_file_path.equals(other_element.get_relative_file_path()) &&
            this._ref_text.equals(other_element.get_raw_ref_text()) &&
            this._page.equals(other_element.get_page())
        );
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(AnchorElement other) {
        int relative_file_path_comparison = this._relative_file_path.compareTo(other.get_relative_file_path());
        if (relative_file_path_comparison == 0) {
            return this._ref_text.compareTo(other.get_raw_ref_text());
        }
        return relative_file_path_comparison;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Anchor Element:: Name: ");
        sb.append(this._name);
        sb.append(", Relative File Path: ");
        sb.append(this._relative_file_path);
        sb.append(", Ref_Text: ");
        sb.append(this._ref_text);
        sb.append(", Page: ");
        sb.append(this._page);
        return sb.toString();
    }
}
