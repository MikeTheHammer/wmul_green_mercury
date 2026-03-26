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
import java.util.Map;

public class AnchorElement implements Comparable<AnchorElement> {
    String name;
    String relativeFilePath;
    String refText;
    String page;

    public AnchorElement(String name, String relativeFilePath, String refText){
        this.name = name;
        this.relativeFilePath = relativeFilePath;
        this.refText = refText;
        this.page = "XX";
    }

    public AnchorElement(String name, String relativeFilePath, String refText, String page){
        this.name = name;
        this.relativeFilePath = relativeFilePath;
        this.refText = refText;
        this.page = page;
    } 

    public String getName() {
        return this.name;
    }

    public String getPage() {
        return this.page;
    }

    public String getRelativeFilePath() {
        return this.relativeFilePath;
    }

    public String getRefText() {
        return this.refText;
    }

    public String getInterpolatedRefText() {
        return this.refText.replace("[page]", this.page);
    }

    public void updatePageNumberFromOtherAnchor(AnchorElement otherAnchor) {
        if (!otherAnchor.getPage().equals("XX")) {
            this.page = otherAnchor.getPage();
        }
    }

    public String getOutputString() {
        StringBuilder outputBuffer = new StringBuilder();
        outputBuffer.append("[#");
        outputBuffer.append(this.name);
        outputBuffer.append(", reftext=\"");
        outputBuffer.append(this.getInterpolatedRefText());
        outputBuffer.append("\"]");
        return outputBuffer.toString();
    }

    public Map<String, LinkedHashMap<String, String>> toMapForYAML(){
        LinkedHashMap<String, String> innerHashmap = new LinkedHashMap<>();
        innerHashmap.put("name", this.name);
        innerHashmap.put("relative_file_path", this.relativeFilePath);
        innerHashmap.put("ref_text", this.refText);
        innerHashmap.put("page", this.page);

        HashMap<String, LinkedHashMap<String, String>> outerHashmap = new HashMap<>();
        outerHashmap.put("anchor", innerHashmap);

        return outerHashmap;
    }

    public static AnchorElement loadFromMap(Map<String, LinkedHashMap<String, String>> inputMap) {
        HashMap<String, String> innerHasmap = inputMap.get("anchor");
        String name = innerHasmap.get("name");
        String relativeFilePath = innerHasmap.get("relative_file_path");
        String refText = innerHasmap.get("ref_text");
        String page = innerHasmap.get("page");

        return new AnchorElement(name, relativeFilePath, refText, page);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        final AnchorElement otherElement = (AnchorElement) other;

        return (
            this.name.equals(otherElement.getName()) && 
            this.relativeFilePath.equals(otherElement.getRelativeFilePath()) &&
            this.refText.equals(otherElement.getRefText()) &&
            this.page.equals(otherElement.getPage())
        );
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public int compareTo(AnchorElement other) {
        int relativeFilePathComparison = this.relativeFilePath.compareTo(other.getRelativeFilePath());
        if (relativeFilePathComparison == 0) {
            return this.refText.compareTo(other.getRefText());
        }
        return relativeFilePathComparison;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Anchor Element:: Name: ");
        sb.append(this.name);
        sb.append(", Relative File Path: ");
        sb.append(this.relativeFilePath);
        sb.append(", Ref_Text: ");
        sb.append(this.refText);
        sb.append(", Page: ");
        sb.append(this.page);
        return sb.toString();
    }
}
