package com.elseforif.servlet.utility;

/**
 * This class contains constants for generating HTML strings.  It forms
 * the very base of a unified system of HTML generation.
 */
public interface HTMLConstants
  {

  public static final String XML_PROLOGUE_ISO_8859_1 = "<?xml version=\"1.0\" "
        + "encoding=\"iso-8859-1\"?>\n\n";
  public static final String XML_PROLOGUE_UTF_8 = "<?xml version=\"1.0\" "
        + "encoding=\"UTF-8\"?>\n\n";
  public static final String DOCTYPE_HTML_4_LOOSE = "<!DOCTYPE HTML PUBLIC "
        + "\"-//W3C//DTD HTML 4.01 Transitional//EN\" "
        + "\"http://www.w3.org/TR/html4/loose.dtd\">";
  public static final String DOCTYPE_HTML_4_STRICT = "<!DOCTYPE HTML PUBLIC "
        + "\"-//W3C//DTD HTML 4.01//EN\" "
        + "\"http://www.w3.org/TR/html4/strict.dtd\">";
  public static final String DOCTYPE_XHTML_STRICT = XML_PROLOGUE_ISO_8859_1
        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" "
        + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">";
  public static final String DOCTYPE_XHTML_TRANSITIONAL = XML_PROLOGUE_ISO_8859_1
        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" "
        + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
  public static final String DOCTYPE_XHTML_FRAMESET = XML_PROLOGUE_ISO_8859_1
        + "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Frameset//EN\" "
        + "\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd\">";
  public static final String DOCTYPE_XHTML_TARGET = XML_PROLOGUE_ISO_8859_1
        + "<!DOCTYPE html PUBLIC \"-//VanCleve//DTD XHTML With Target//EN\" "
        + "\"http://www.vancleve.com/xml/xhtml1-target.dtd\">";

  public static final String ALIGN_CENTER = "align=\"center\"";
  public static final String NL = "\n";
  public static final String B = "<b>";
  public static final String BODY = "<body>";
  public static final String BOTH_END_TAGS = NL + NL + "</body>" + NL + NL + "</html>" + NL;
  public static final String BLOCKQUOTE = "<blockquote>";
  public static final String BR = "<br />";
  public static final String CENTER = "<center>";
  public static final String CODE = "<code>";
  public static final String DIV = "<div>";
  public static final String DIV_CENTER = "<div style=\"text-align: center; line-height: 0px;\">";
  public static final String HR = "<hr />";
  public static final String I = "<i>";
  public static final String LI = "<li>";
  public static final String NBSP = "&nbsp;";
  public static final String OL = "<ol>";
  public static final String P = "<p>";
  public static final String PRE = "<pre>";
  public static final String SCRIPT = "<script type=\"text/javascript\">";
  public static final String SPAN = "<span>";
  public static final String TABLE = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">";
  public static final String TABLE_DEBUG = "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">";
  public static final String TBODY = "<tbody>";
  public static final String TR = "<tr>";
  public static final String TD = "<td>";
  public static final String TD_COLSPAN_2 = "<td colspan=\"2\">";
  public static final String TD_TOP_CENTER = "<td align=\"center\" valign=\"top\">";
  public static final String TD_TOP_RIGHT = "<td align=\"right\" valign=\"top\">";
  public static final String TD_TOP = "<td valign=\"top\">";
  public static final String TD_BOTTOM = "<td valign=\"bottom\">";
  public static final String TD_CENTER = "<td align=\"center\">";
  public static final String TD_CENTER_BOTH = "<td align=\"center\" valign=\"center\">";
  public static final String TD_NOWRAP = "<td style=\"white-space: nowrap;\">";
  public static final String TH = "<th>";
  public static final String UL = "<ul>";
  public static final String END_A = "</a>";
  public static final String END_APPLET = "<!--[if !IE]--></object><!--[endif]--></object>";
  public static final String END_B = "</b>";
  public static final String END_BODY = "</body>";
  public static final String END_HEAD = NL + "</head>" + NL;
  public static final String END_HTML = NL + NL + "</html>";
  public static final String END_BLOCKQUOTE = "</blockquote>";
  public static final String END_CENTER = "</center>";
  public static final String END_CODE = "</code>";
  public static final String END_DIV = "</div>";
  public static final String END_DIV_CENTER = END_DIV;
  public static final String END_FRAMESET = NL + "</frameset>";
  public static final String END_FORM = "</form>";
  public static final String END_I = "</i>";
  public static final String END_LI = "</li>";
  public static final String END_MAP = "</map>";
  public static final String END_OL = "</ol>";
  public static final String END_P = "</p>";
  public static final String END_UL = "</ul>";
  public static final String END_OPTION = "</option>";
  public static final String END_PRE = "</pre>";
  public static final String END_SCRIPT = "</script>";
  public static final String END_SELECT = "</select>";
  public static final String END_SPAN = "</span>";
  public static final String END_TABLE = "</table>";
  public static final String END_TABLE_2 = "</tr></table>";
  public static final String END_TABLE_3 = "</td></tr></table>";
  public static final String END_TBODY = "</tbody>";
  public static final String END_TD = "</td>";
  public static final String END_TH = "</th>";
  public static final String END_TR = "</tr>";
  public static final String END_TEXTAREA = "</textarea>";
  public static final String[] SP = {
        "",
        "&nbsp;",
        "&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;",
        "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" };
  public static final String[] BRNL = {
        "",
        BR + NL,
        BR + NL + BR + NL,
        BR + NL + BR + NL + BR + NL,
        BR + NL + BR + NL + BR + NL + BR + NL,
        BR + NL + BR + NL + BR + NL + BR + NL + BR + NL,
        BR + NL + BR + NL + BR + NL + BR + NL + BR + NL + BR + NL };
  public static String DisplayNull = "--";
  public static float IEToNetscapeTextboxRatio = .5f;
  public static float IEToNetscapeTextareaRatio = .625f;

  }
