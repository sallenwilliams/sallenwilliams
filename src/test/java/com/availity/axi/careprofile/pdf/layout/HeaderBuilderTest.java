package com.availity.axi.careprofile.pdf.layout;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.layout.HeaderBuilder;

import com.lowagie.text.Chunk;
import com.lowagie.text.ImgRaw;
import com.lowagie.text.Paragraph;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class HeaderBuilderTest extends TestCase {

  public void testHeaderBuilderPositive() throws Exception {
    HeaderBuilder headerBuilder = new HeaderBuilder();
    Paragraph paragraph = (Paragraph) headerBuilder.build();
    assertNotNull(paragraph);
    assertTrue(paragraph.size() == 3);
    assertTrue(paragraph.get(0) instanceof Chunk);
    assertTrue(paragraph.get(1) instanceof ImgRaw);
  }
}
