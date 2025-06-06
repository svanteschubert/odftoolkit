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
import org.odftoolkit.odfdom.dom.attribute.text.TextContinueListAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextContinueNumberingAttribute;
import org.odftoolkit.odfdom.dom.attribute.text.TextStyleNameAttribute;
import org.odftoolkit.odfdom.dom.attribute.xml.XmlIdAttribute;
import org.odftoolkit.odfdom.pkg.ElementVisitor;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.odftoolkit.odfdom.pkg.OdfName;

/** DOM implementation of OpenDocument element {@odf.element text:list}. */
public class TextListElement extends OdfElement {

  public static final OdfName ELEMENT_NAME = OdfName.newName(OdfDocumentNamespace.TEXT, "list");

  /**
   * Create the instance of <code>TextListElement</code>
   *
   * @param ownerDoc The type is <code>OdfFileDom</code>
   */
  public TextListElement(OdfFileDom ownerDoc) {
    super(ownerDoc, ELEMENT_NAME);
  }

  /**
   * Get the element name
   *
   * @return return <code>OdfName</code> the name of element {@odf.element text:list}.
   */
  public OdfName getOdfName() {
    return ELEMENT_NAME;
  }

  /**
   * Receives the value of the ODFDOM attribute representation <code>TextContinueListAttribute
   * </code> , See {@odf.attribute text:continue-list}
   *
   * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set
   *     and no default value defined.
   */
  public String getTextContinueListAttribute() {
    TextContinueListAttribute attr =
        (TextContinueListAttribute) getOdfAttribute(OdfDocumentNamespace.TEXT, "continue-list");
    if (attr != null) {
      return String.valueOf(attr.getValue());
    }
    return null;
  }

  /**
   * Sets the value of ODFDOM attribute representation <code>TextContinueListAttribute</code> , See
   * {@odf.attribute text:continue-list}
   *
   * @param textContinueListValue The type is <code>String</code>
   */
  public void setTextContinueListAttribute(String textContinueListValue) {
    TextContinueListAttribute attr = new TextContinueListAttribute((OdfFileDom) this.ownerDocument);
    setOdfAttribute(attr);
    attr.setValue(textContinueListValue);
  }

  /**
   * Receives the value of the ODFDOM attribute representation <code>TextContinueNumberingAttribute
   * </code> , See {@odf.attribute text:continue-numbering}
   *
   * @return - the <code>Boolean</code> , the value or <code>null</code>, if the attribute is not
   *     set and no default value defined.
   */
  public Boolean getTextContinueNumberingAttribute() {
    TextContinueNumberingAttribute attr =
        (TextContinueNumberingAttribute)
            getOdfAttribute(OdfDocumentNamespace.TEXT, "continue-numbering");
    if (attr != null && !attr.getValue().isEmpty()) {
      return attr.booleanValue();
    }
    return null;
  }

  /**
   * Sets the value of ODFDOM attribute representation <code>TextContinueNumberingAttribute</code> ,
   * See {@odf.attribute text:continue-numbering}
   *
   * @param textContinueNumberingValue The type is <code>Boolean</code>
   */
  public void setTextContinueNumberingAttribute(Boolean textContinueNumberingValue) {
    TextContinueNumberingAttribute attr =
        new TextContinueNumberingAttribute((OdfFileDom) this.ownerDocument);
    setOdfAttribute(attr);
    attr.setBooleanValue(textContinueNumberingValue);
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
   * Receives the value of the ODFDOM attribute representation <code>XmlIdAttribute</code> , See
   * {@odf.attribute xml:id}
   *
   * @return - the <code>String</code> , the value or <code>null</code>, if the attribute is not set
   *     and no default value defined.
   */
  public String getXmlIdAttribute() {
    XmlIdAttribute attr = (XmlIdAttribute) getOdfAttribute(OdfDocumentNamespace.XML, "id");
    if (attr != null) {
      return String.valueOf(attr.getValue());
    }
    return null;
  }

  /**
   * Sets the value of ODFDOM attribute representation <code>XmlIdAttribute</code> , See
   * {@odf.attribute xml:id}
   *
   * @param xmlIdValue The type is <code>String</code>
   */
  public void setXmlIdAttribute(String xmlIdValue) {
    XmlIdAttribute attr = new XmlIdAttribute((OdfFileDom) this.ownerDocument);
    setOdfAttribute(attr);
    attr.setValue(xmlIdValue);
  }

  /**
   * Create child element {@odf.element text:list-header}.
   *
   * @return the element {@odf.element text:list-header}
   */
  public TextListHeaderElement newTextListHeaderElement() {
    TextListHeaderElement textListHeader =
        ((OdfFileDom) this.ownerDocument).newOdfElement(TextListHeaderElement.class);
    this.appendChild(textListHeader);
    return textListHeader;
  }

  /**
   * Create child element {@odf.element text:list-item}.
   *
   * @return the element {@odf.element text:list-item}
   */
  public TextListItemElement newTextListItemElement() {
    TextListItemElement textListItem =
        ((OdfFileDom) this.ownerDocument).newOdfElement(TextListItemElement.class);
    this.appendChild(textListItem);
    return textListItem;
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
