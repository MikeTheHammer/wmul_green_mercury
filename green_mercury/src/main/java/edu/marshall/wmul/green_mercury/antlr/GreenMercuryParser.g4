// MIT License
// Copyright (c) 2026 Mike Stanley

// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:

// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.

// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

parser grammar GreenMercuryParser;

options {
    tokenVocab = GreenMercuryLexer;
}

@header {
    package edu.marshall.wmul.green_mercury.antlr;
}

document
    : (chardata | element)* EOF
    ;

content
    : chardata* ((element) chardata?)*
    ;

element
    : tag content close_tag
    | self_closing_tag
    ;

tag
    : OPEN NAME attribute* CLOSE
    ;
    
close_tag
    : OPEN SLASHNAME CLOSE
    ;

self_closing_tag
    : OPEN NAME attribute* SLASH_CLOSE
    ;

attribute
    : NAME EQUALS STRING
    ; 

/** ``All text that is not markup constitutes the character data of
 *  the document.''
 */
chardata
    : TEXT+
    | SEA_WS
    ;

