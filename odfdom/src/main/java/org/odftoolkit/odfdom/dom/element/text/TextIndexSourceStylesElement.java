/**
 * **********************************************************************
 *
 * <p>DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * <p>Copyright 2008, 2010 Oracle and/or its affiliates. All rights reserved.
 *
 * <p>Use is subject to license terms.
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0. You can also obtain a copy of the License at
 * http://odftoolkit.org/docs/license.txt
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied.
 *
 * <p>See the License for the specific language governing permissions and limitations under the
 * License.
 *
 * <p>**********************************************************************
 */

/*
 * This file is automatically generated.
 * Don't edit manually.
 */
package org.odftoolkit.odfdom.dom.element.text;

import org.odftoolkit.odfdom.dom.DefaultElementVisitor;
import org.odftoolkit.odfdom.dom.OdfDocumentNamespace;
import org.odftoolkit.odfdom.dom.attribute.text.TextOutlineLevelAttribute;
import org.odftoolkit.odfdom.pkg.ElementVisitor;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;

/** DOM implementation of OpenDocument element {@odf.element text:index-source-styles}. */
public class TextIndexSourceStylesElement extends OdfElement {

  public static final OdfName ELEMENT_NAME =
      OdfName.newName(OdfDocumentNamespace.TEXT, "index-source-styles");

  /**
   * Create the instance of <code>TextIndexSourceStylesElement</code>
   *
   * @param ownerDoc The type is <code>OdfFileDom</code>
   */
  public TextIndexSourceStylesElement(OdfFileDom ownerDoc) {
    super(ownerDoc, ELEMENT_NAME);
  }

  /**
   * Get the element name
   *
   * @return return <code>OdfName</code> the name of element {@odf.element
   *     text:index-source-styles}.
   */
  public OdfName getOdfName() {
    return ELEMENT_NAME;
  }

  /**
   * Receives the value of the ODFDOM attribute representation <code>TextOutlineLevelAttribute
   * </code> , See {@odf.attribute text:outline-level}
   *
   * <p>Attribute is mandatory.
   *
   * @return - the <code>Integer</code> , the value or <code>null</code>, if the attribute is not
   *     set and no default value defined.
   */
  public Integer getTextOutlineLevelAttribute() {
    TextOutlineLevelAttribute attr =
        (TextOutlineLevelAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "outline-level");
    if (attr != null && !attr.getValue().isEmpty()) {
      return attr.intValue();
    }
    return null;
  }

  /**
   * Sets the value of ODFDOM attribute representation <code>TextOutlineLevelAttribute</code> , See
   * {@odf.attribute text:outline-level}
   *
   * @param textOutlineLevelValue The type is <code>Integer</code>
   */
  public void setTextOutlineLevelAttribute(Integer textOutlineLevelValue) {
    TextOutlineLevelAttribute attr = new TextOutlineLevelAttribute((OdfFileDom) this.ownerDocument);
    setOdfAttribute(attr);
    attr.setIntValue(textOutlineLevelValue);
  }

  /**
   * Create child element {@odf.element text:index-source-style}.
   *
   * @param textStyleNameValue the <code>String</code> value of <code>TextStyleNameAttribute</code>,
   *     see {@odf.attribute text:style-name} at specification
   * @return the element {@odf.element text:index-source-style}
   */
  public TextIndexSourceStyleElement newTextIndexSourceStyleElement(String textStyleNameValue) {
    TextIndexSourceStyleElement textIndexSourceStyle =
        ((OdfFileDom) this.ownerDocument).newOdfElement(TextIndexSourceStyleElement.class);
    textIndexSourceStyle.setTextStyleNameAttribute(textStyleNameValue);
    this.appendChild(textIndexSourceStyle);
    return textIndexSourceStyle;
  }

  /**
   * Accept an visitor instance to allow the visitor to do some operations. Refer to visitor design
   * pattern to get a better understanding.
   *
   * @param visitor an instance of DefaultElementVisitor
   */
  @Override
  public void accept(ElementVisitor visitor) {
    if (visitor instanceof DefaultElementVisitor) {
      DefaultElementVisitor defaultVisitor = (DefaultElementVisitor) visitor;
      defaultVisitor.visit(this);
    } else {
      visitor.visit(this);
    }
  }
}
