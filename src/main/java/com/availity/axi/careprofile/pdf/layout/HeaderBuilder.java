package com.availity.axi.careprofile.pdf.layout;

import java.io.IOException;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class HeaderBuilder implements Builder {
  private static final String HEADER_TEXT = "Availity¨ CareProfileª Summary Report";
  private static final String IMAGE_PATH = "src/main/resources/logo.gif";

  public Element build() throws DocumentException, IOException {
    Chunk header = new Chunk(HEADER_TEXT, new Font(Font.HELVETICA, 15, Font.BOLD));
    Paragraph paragraph = new Paragraph();
    paragraph.add(header);
    Image headerImage = Image.getInstance(IMAGE_PATH);
    headerImage.setAlignment(Image.RIGHT);
    headerImage.scalePercent(60);
    paragraph.add(headerImage);
    paragraph.add(Chunk.NEWLINE);
    return paragraph;
  }
}
