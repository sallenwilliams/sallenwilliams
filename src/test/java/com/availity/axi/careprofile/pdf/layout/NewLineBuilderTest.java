package com.availity.axi.careprofile.pdf.layout;

import junit.framework.TestCase;

import com.availity.axi.careprofile.pdf.layout.NewLineBuilder;

import com.lowagie.text.Chunk;

/**
 * @author <a mailto:swilliams@availity.com> Scott Williams</a>
 */
public class NewLineBuilderTest extends TestCase {

  public void testNewLineBuilderPositive() throws Exception {
    NewLineBuilder newLineBuilder = new NewLineBuilder();
    Chunk chunk = (Chunk) newLineBuilder.build();
    assertNotNull(chunk);
    assertEquals("\n", chunk.content());
  }
}