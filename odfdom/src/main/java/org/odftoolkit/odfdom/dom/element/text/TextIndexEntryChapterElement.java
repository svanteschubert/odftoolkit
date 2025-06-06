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
import org.odftoolkit.odfdom.dom.attribute.text.TextDisplayAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextOutlineLevelAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextStyleNameAttribute;
import org.odftoolkit.odfdom.dom.element.OdfStylableElement;
import org.odftoolkit.odfdom.dom.style.OdfStyleFamily;
import org.odftoolkit.odfdom.pkg.ElementVisitor;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;

/** DOM implementation of OpenDocument element {@odf.element text:index-entry-chapter}. */
public class TextIndexEntryChapterElement extends OdfStylableElement {

  public static final OdfName ELEMENT_NAME =
      OdfName.newName(OdfDocumentNamespace.TEXT, "index-entry-chapter");

  /**
   * Create the instance of <code>TextIndexEntryChapterElement</code>
   *
   * @param ownerDoc The type is <code>OdfFileDom</code>
   */
  public TextIndexEntryChapterElement(OdfFileDom ownerDoc) {
    super(
        ownerDoc,
        ELEMENT_NAME,
        OdfStyleFamily.Text,
        OdfName.newName(OdfDocumentNamespace.TEXT, "style-name"));
  }

  /**
   * Get the element name
   *
   * @return return <code>OdfName</code> the name of element {@odf.element
   *     text:index-entry-chapter}.
   */
  public OdfName getOdfName() {
    return ELEMENT_NAME;
  }

  /**
   * Receives the value of the ODFDOM attribute representation <code>TextDisplayAttribute</code> ,
   * See {@odf.attribute text:display}
   *
   * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set
   *     and no default value defined.
   */
  public String getTextDisplayAttribute() {
    TextDisplayAttribute attr =
        (TextDisplayAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "display");
    if (attr != null) {
      return String.valueOf(attr.getValue());
    }
    return TextDisplayAttribute.DEFAULT_VALUE;
  }

  /**
   * Sets the value of ODFDOM attribute representation <code>TextDisplayAttribute</code> , See
   * {@odf.attribute text:display}
   *
   * @param textDisplayValue The type is <code>String</code>
   */
  public void setTextDisplayAttribute(String textDisplayValue) {
    TextDisplayAttribute attr = new TextDisplayAttribute((OdfFileDom) this.ownerDocument);
    setOdfAttribute(attr);
    attr.setValue(textDisplayValue);
  }

  /**
   * Receives the value of the ODFDOM attribute representation <code>TextOutlineLevelAttribute
   * </code> , See {@odf.attribute text:outline-level}
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
   * Receives the value of the ODFDOM attribute representation <code>TextStyleNameAttribute</code> ,
   * See {@odf.attribute text:style-name}
   *
   * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set
   *     and no default value defined.
   */
  public String getTextStyleNameAttribute() {
    TextStyleNameAttribute attr =
        (TextStyleNameAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "style-name");
    if (attr != null) {
      return String.valueOf(attr.getValue());
    }
    return null;
  }

  /**
   * Sets the value of ODFDOM attribute representation <code>TextStyleNameAttribute</code> , See
   * {@odf.attribute text:style-name}
   *
   * @param textStyleNameValue The type is <code>String</code>
   */
  public void setTextStyleNameAttribute(String textStyleNameValue) {
    TextStyleNameAttribute attr = new TextStyleNameAttribute((OdfFileDom) this.ownerDocument);
    setOdfAttribute(attr);
    attr.setValue(textStyleNameValue);
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
