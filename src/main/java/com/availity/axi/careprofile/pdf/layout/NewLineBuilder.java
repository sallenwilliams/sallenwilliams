package com.availity.axi.careprofile.pdf.layout;

import java.io.IOException;

import com.availity.axi.careprofile.pdf.layout.Builder;

import com.lowagie.text.Chunk;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class NewLineBuilder implements Builder {

  public Element build() throws DocumentException, IOException {
    return Chunk.NEWLINE;
  }
}
