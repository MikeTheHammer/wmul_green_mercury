// Generated from GreenMercuryParser.g4 by ANTLR 4.13.2

    package edu.marshall.wmul.green_mercury.antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GreenMercuryParser}.
 */
public interface GreenMercuryParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#document}.
	 * @param ctx the parse tree
	 */
	void enterDocument(GreenMercuryParser.DocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#document}.
	 * @param ctx the parse tree
	 */
	void exitDocument(GreenMercuryParser.DocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#content}.
	 * @param ctx the parse tree
	 */
	void enterContent(GreenMercuryParser.ContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#content}.
	 * @param ctx the parse tree
	 */
	void exitContent(GreenMercuryParser.ContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#element}.
	 * @param ctx the parse tree
	 */
	void enterElement(GreenMercuryParser.ElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#element}.
	 * @param ctx the parse tree
	 */
	void exitElement(GreenMercuryParser.ElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#tag}.
	 * @param ctx the parse tree
	 */
	void enterTag(GreenMercuryParser.TagContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#tag}.
	 * @param ctx the parse tree
	 */
	void exitTag(GreenMercuryParser.TagContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#close_tag}.
	 * @param ctx the parse tree
	 */
	void enterClose_tag(GreenMercuryParser.Close_tagContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#close_tag}.
	 * @param ctx the parse tree
	 */
	void exitClose_tag(GreenMercuryParser.Close_tagContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#self_closing_tag}.
	 * @param ctx the parse tree
	 */
	void enterSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#self_closing_tag}.
	 * @param ctx the parse tree
	 */
	void exitSelf_closing_tag(GreenMercuryParser.Self_closing_tagContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(GreenMercuryParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(GreenMercuryParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GreenMercuryParser#chardata}.
	 * @param ctx the parse tree
	 */
	void enterChardata(GreenMercuryParser.ChardataContext ctx);
	/**
	 * Exit a parse tree produced by {@link GreenMercuryParser#chardata}.
	 * @param ctx the parse tree
	 */
	void exitChardata(GreenMercuryParser.ChardataContext ctx);
}