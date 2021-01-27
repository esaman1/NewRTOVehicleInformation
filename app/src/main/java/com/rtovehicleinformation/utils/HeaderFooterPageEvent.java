package com.rtovehicleinformation.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper
{
  @Override
  public void onEndPage(final PdfWriter pdfWriter, final Document document) {
    ColumnText.showTextAligned(pdfWriter.getDirectContent(), 1, new Phrase(" "), 110.0f, 30.0f, 0.0f);
    final PdfContentByte directContent = pdfWriter.getDirectContent();
    final StringBuilder sb = new StringBuilder();
    sb.append("");
    sb.append(document.getPageNumber());
    ColumnText.showTextAligned(directContent, 1, new Phrase(sb.toString()), 550.0f, 30.0f, 0.0f);
  }

  @Override
  public void onStartPage(final PdfWriter pdfWriter, final Document document) {
    ColumnText.showTextAligned(pdfWriter.getDirectContent(), 1, new Phrase("                       View Vehicle Details"), 30.0f, 800.0f, 0.0f);
    ColumnText.showTextAligned(pdfWriter.getDirectContent(), 1, new Phrase(""), 550.0f, 800.0f, 0.0f);
  }
}
