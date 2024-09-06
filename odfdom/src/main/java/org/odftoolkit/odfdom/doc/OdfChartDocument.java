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
package org.odftoolkit.odfdom.doc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import org.odftoolkit.odfdom.dom.element.office.OfficeChartElement;
import org.odftoolkit.odfdom.pkg.MediaType;
import org.odftoolkit.odfdom.pkg.OdfPackage;
import org.xml.sax.SAXException;

/**
 * This class represents an empty ODF document , which will be in general embedded in an existing
 * ODF (Spreadsheet) document.
 */
public class OdfChartDocument extends OdfDocument {

  private static final String EMPTY_CHART_DOCUMENT_PATH = "/OdfChartDocument.odc";
  static final Resource EMPTY_CHART_DOCUMENT_RESOURCE = new Resource(EMPTY_CHART_DOCUMENT_PATH);

  /** This enum contains all possible media types of OdfChartDocument documents. */
  public enum OdfMediaType implements MediaType {
    CHART(OdfDocument.OdfMediaType.CHART),
    CHART_TEMPLATE(OdfDocument.OdfMediaType.CHART_TEMPLATE);
    private final OdfDocument.OdfMediaType mMediaType;

    OdfMediaType(OdfDocument.OdfMediaType mediaType) {
      this.mMediaType = mediaType;
    }

    /** @return the mediatype of this document */
    public String getMediaTypeString() {
      return mMediaType.getMediaTypeString();
    }

    /** @return the ODF filesuffix of this document */
    public String getSuffix() {
      return mMediaType.getSuffix();
    }

    /**
     * @param mediaType string defining an ODF document
     * @return the according OdfMediatype encapuslating the given string and the suffix
     */
    public static OdfDocument.OdfMediaType getOdfMediaType(String mediaType) {
      return OdfDocument.OdfMediaType.getOdfMediaType(mediaType);
    }
  }

  /**
   * Creates an empty charts document. * <br>
   * <em>Note: ODF Chart documents are (with OOo 3.0) only used as embedded document and not used
   * stand-alone.</em>
   *
   * @return ODF charts document based on a default template
   * @throws java.lang.Exception - if the document could not be created
   */
  public static OdfChartDocument newChartDocument() throws Exception {
    return (OdfChartDocument)
        OdfDocument.loadTemplate(EMPTY_CHART_DOCUMENT_RESOURCE, OdfDocument.OdfMediaType.CHART);
  }

  /**
   * Creates an empty charts template. * <br>
   * <em>Note: ODF Chart documents are (with OOo 3.0) only used as embedded document and not used
   * stand-alone.</em>
   *
   * @return ODF charts template based on a default template
   * @throws java.lang.Exception - if the template could not be created
   */
  public static OdfChartDocument newChartTemplateDocument() throws Exception {
    OdfChartDocument doc =
        (OdfChartDocument)
            OdfDocument.loadTemplate(
                EMPTY_CHART_DOCUMENT_RESOURCE, OdfDocument.OdfMediaType.CHART_TEMPLATE);
    doc.changeMode(OdfMediaType.CHART_TEMPLATE);
    return doc;
  }

  /**
   * Creates an OdfChartDocument from the OpenDocument provided by a resource Stream.
   *
   * <p>Since an InputStream does not provide the arbitrary (non sequentiell) read access needed by
   * OdfChartDocument, the InputStream is cached. This usually takes more time compared to the other
   * createInternalDocument methods. An advantage of caching is that there are no problems
   * overwriting an input file.
   *
   * <p>If the resource stream is not a ODF chart document, ClassCastException might be thrown.
   *
   * @param inputStream - the InputStream of the ODF chart document.
   * @return the chart document created from the given InputStream
   * @throws java.lang.Exception - if the document could not be created.
   */
  public static OdfChartDocument loadDocument(InputStream inputStream) throws Exception {
    return (OdfChartDocument) OdfDocument.loadDocument(inputStream);
  }

  /**
   * Loads an OdfChartDocument from the provided path.
   *
   * <p>OdfChartDocument relies on the file being available for read access over the whole lifecycle
   * of OdfChartDocument.
   *
   * <p>If the resource stream is not a ODF chart document, ClassCastException might be thrown.
   *
   * @param documentPath - the path from where the document can be loaded
   * @return the chart document from the given path or NULL if the media type is not supported by
   *     ODFDOM.
   * @throws java.lang.Exception - if the document could not be created.
   */
  public static OdfChartDocument loadDocument(String documentPath) throws Exception {
    return (OdfChartDocument) OdfDocument.loadDocument(documentPath);
  }

  /**
   * Creates an OdfChartDocument from the OpenDocument provided by a File.
   *
   * <p>OdfChartDocument relies on the file being available for read access over the whole lifecycle
   * of OdfChartDocument.
   *
   * <p>If the resource stream is not a ODF chart document, ClassCastException might be thrown.
   *
   * @param file - a file representing the ODF chart document.
   * @return the chart document created from the given File
   * @throws java.lang.Exception - if the document could not be created.
   */
  public static OdfChartDocument loadDocument(File file) throws Exception {
    return (OdfChartDocument) OdfDocument.loadDocument(file);
  }

  /**
   * To avoid data duplication a new document is only created, if not already opened. A document is
   * cached by this constructor using the internalpath as key.
   */
  protected OdfChartDocument(
      OdfPackage pkg, String internalPath, OdfChartDocument.OdfMediaType odfMediaType)
      throws SAXException {
    super(pkg, internalPath, odfMediaType.mMediaType);
  }

  /**
   * Get the content root of a chart document.
   *
   * @return content root, representing the office:chart tag
   * @throws org.xml.sax.SAXException when the XML of the content.xml can not be read
   * @throws java.io.IOException when the XML of the content.xml can not be accessed
   */
  @Override
  public OfficeChartElement getContentRoot() throws SAXException, IOException {
    return super.getContentRoot(OfficeChartElement.class);
  }
  /**
   * Changes the document to the given mediatype. This method can only be used to convert a document
   * to a related mediatype, e.g. template.
   *
   * @param mediaType the related ODF mimetype
   */
  public void changeMode(OdfMediaType mediaType) {
    setOdfMediaType(mediaType.mMediaType);
  }
}
