/**
 * **********************************************************************
 *
 * <p>Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * <p>**********************************************************************
 */
package org.odftoolkit.odfdom.incubator.search;

import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.incubator.doc.office.OdfOfficeMasterStyles;
import org.odftoolkit.odfdom.incubator.doc.text.OdfWhitespaceProcessor;
import org.odftoolkit.odfdom.pkg.OdfElement;
import org.odftoolkit.odfdom.pkg.OdfFileDom;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A derived Navigation class used for navigate the text content it is used to search the document
 * and find the matched text and would return TextSelection instance
 */
public class TextNavigation extends Navigation<TextSelection> {

  private static final Logger LOG = Logger.getLogger(TextNavigation.class.getName());

  private static final String mMatchedElementName = "text:p,text:h";
  private final Pattern mPattern;
  private final OdfTextDocument mTextDocument;
  private TextSelection mCurrentSelectedItem;
  private TextSelection mNextSelectedItem;
  private String mCurrentText;
  private int mCurrentIndex;
  private boolean mbFinishFindInHeaderFooter;
  private SelectionManager mSelectionManager;

  /**
   * Construct TextNavigation with matched condition and navigation scope
   *
   * @param pattern the matched pattern String
   * @param doc the navigation scope
   */
  public TextNavigation(String pattern, OdfTextDocument doc) {
    this(Pattern.compile(pattern), doc);
  }

  /**
   * Construct TextNavigation with matched condition and navigation scope
   *
   * @param pattern the Pattern object to search with
   * @param doc the navigation scope
   */
  public TextNavigation(Pattern pattern, OdfTextDocument doc) {
    this.mPattern = pattern;
    mTextDocument = doc;
    mCurrentSelectedItem = null;
    mbFinishFindInHeaderFooter = false;

    mSelectionManager = doc.getSelectionManager();
    // initialize the Iterator and find the first element...
    mNextSelectedItem = findNextSelection(null);
  }

  /**
   * Returns the selectionManager instance.
   *
   * @return
   */
  public SelectionManager getSelectionManager() {
    return mSelectionManager;
  }

  // the matched text might exist in header/footer
  private TextSelection findInHeaderFooter(TextSelection selected) {
    OdfFileDom styledom = null;
    OdfOfficeMasterStyles masterpage = null;
    OdfElement element = null;

    if (selected != null) {
      int nextIndex = setCurrentTextAndGetIndex(selected);
      if (nextIndex != -1) {
        TextSelection item =
            new TextSelection(
                mCurrentText, selected.getContainerElement(), nextIndex, mSelectionManager);
        return item;
      }
    }
    try {
      styledom = mTextDocument.getStylesDom();
      if (styledom == null) {
        return null;
      }
      NodeList list = styledom.getElementsByTagName("office:master-styles");
      if (list.getLength() > 0) {
        masterpage = (OdfOfficeMasterStyles) list.item(0);
      } else {
        return null;
      }

      if (selected == null) {
        element = (OdfElement) getNextMatchElementInTree(masterpage, masterpage);
      } else {
        element =
            (OdfElement) getNextMatchElementInTree(selected.getContainerElement(), masterpage);
      }

      if (element != null) {
        TextSelection item =
            new TextSelection(mCurrentText, element, mCurrentIndex, mSelectionManager);
        return item;
      } else {
        return null;
      }

    } catch (Exception ex) {
      LOG.log(Level.SEVERE, ex.getMessage(), ex);
    }
    return null;
  }

  /*
   * Finds the next TextSelection which match specified style
   */
  private TextSelection findNextSelection(TextSelection selected) {
    TextSelection result = null;
    if (!mbFinishFindInHeaderFooter) {
      result = findInHeaderFooter(selected);
      if (result != null) {
        mSelectionManager.registerItem(result);
        return result;
      }
      selected = null;
      mbFinishFindInHeaderFooter = true;
    }

    if (selected == null) {
      OdfElement element = null;
      try {
        element = (OdfElement) getNextMatchElement((Node) mTextDocument.getContentRoot());
      } catch (Exception ex) {
        LOG.log(Level.SEVERE, ex.getMessage(), ex);
      }
      if (element != null) {
        result = new TextSelection(mCurrentText, element, mCurrentIndex, mSelectionManager);
        mSelectionManager.registerItem(result);
        return result;
      } else {
        return null;
      }
    }

    OdfElement containerElement = selected.getContainerElement();
    int nextIndex = setCurrentTextAndGetIndex(selected);
    if (nextIndex != -1) {
      result = new TextSelection(mCurrentText, containerElement, nextIndex, mSelectionManager);
      mSelectionManager.registerItem(result);
      return result;
    } else {
      OdfElement element = (OdfElement) getNextMatchElement(containerElement);
      if (element != null) {
        result = new TextSelection(mCurrentText, element, mCurrentIndex, mSelectionManager);
        mSelectionManager.registerItem(result);
        return result;
      } else {
        return null;
      }
    }
  }

  private int setCurrentTextAndGetIndex(TextSelection selected) {
    int index = selected.getIndex();
    OdfWhitespaceProcessor textProcessor = new OdfWhitespaceProcessor();
    String content = textProcessor.getText(selected.getContainerElement());

    int nextIndex = -1;
    Matcher matcher = mPattern.matcher(content);
    // start from the end index of the selected item
    try {
      if (matcher.find(index + selected.getText().length())) {
        // here just consider \n\r\t occupy one char
        nextIndex = matcher.start();
        int eIndex = matcher.end();
        mCurrentText = content.substring(nextIndex, eIndex);
      }
    } catch (IndexOutOfBoundsException e) {
      // can occur in case the text of the selection was manipulated from the client
      return -1;
    }
    return nextIndex;
  }

  /**
   * Returns the next matching element in the document.
   *
   * @return the next element
   * @throws NoSuchElementException if the iteration has no more matching elements
   */
  @Override
  public TextSelection next() {
    if (mNextSelectedItem != null) {
      mCurrentSelectedItem = mNextSelectedItem;
      mNextSelectedItem = findNextSelection(mCurrentSelectedItem);
    } else {
      throw new NoSuchElementException();
    }
    return mCurrentSelectedItem;
  }

  /**
   * Returns {@code true} if the Document has more matching elements. (In other words, returns
   * {@code true} if {@link #next} would return an element rather than throwing an exception.)
   */
  @Override
  public boolean hasNext() {
    return !(mNextSelectedItem == null);
  }

  /*
   * Return the element from the current matching selection.
   * Use next() to navigate to the next element.
   *
   * @return OdfElement of the current item or null if not element exists.
   */
  @Override
  public OdfElement getElement() {
    if (mCurrentSelectedItem != null) {
      return mCurrentSelectedItem.getElement();
    }
    return null;
  }

  /**
   * check if the text content of element match the specified pattern string
   *
   * @param element navigate this element
   * @return true if the text content of this element match this pattern; false if not match
   */
  @Override
  public boolean match(Node element) {
    if (element instanceof OdfElement) {
      if (mMatchedElementName.contains(element.getNodeName())) {
        OdfWhitespaceProcessor textProcessor = new OdfWhitespaceProcessor();
        String content = textProcessor.getText(element);

        Matcher matcher = mPattern.matcher(content);
        if (matcher.find()) {
          // here just consider \n\r\t occupy one char
          mCurrentIndex = matcher.start();
          int eIndex = matcher.end();
          mCurrentText = content.substring(mCurrentIndex, eIndex);
          return true;
        }
      }
    }
    return false;
  }
}
