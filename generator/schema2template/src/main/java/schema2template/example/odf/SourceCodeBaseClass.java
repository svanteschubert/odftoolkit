/**
 * **********************************************************************
 *
 * <p>DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * <p>Copyright 2009, 2010 Oracle and/or its affiliates. All rights reserved.
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
package schema2template.example.odf;

import java.util.SortedSet;
import java.util.TreeSet;
import schema2template.model.PuzzlePiece;
import schema2template.model.PuzzlePieceSet;
import schema2template.model.QNamed;
import schema2template.model.XMLModel;

/**
 * Encapsulates the name of the java base class
 *
 * <p>Convention: Unique key is the name of the baseClass. So name is used for compareTo(o),
 * equals(o) and hashCode().
 */
public class SourceCodeBaseClass implements Comparable<SourceCodeBaseClass>, QNamed {

  private SortedSet<PuzzlePiece> mSubElements;
  private String mBaseName;
  private OdfModel mOdfModel;

  protected SourceCodeBaseClass(
      OdfModel odfModel, String baseName, SortedSet<PuzzlePiece> subElements) {
    mSubElements = subElements;
    mBaseName = baseName;
    mOdfModel = odfModel;
  }

  public int compareTo(SourceCodeBaseClass o) {
    return mBaseName.compareTo(o.mBaseName);
  }

  public boolean equals(Object o) {
    return (o instanceof SourceCodeBaseClass
        && ((SourceCodeBaseClass) o).mBaseName.equals(mBaseName));
  }

  public int hashCode() {
    return mBaseName.hashCode();
  }

  public String getLocalName() {
    return XMLModel.extractLocalName(mBaseName);
  }

  public String getQName() {
    return mBaseName;
  }

  public String getNamespace() {
    return XMLModel.extractNamespace(mBaseName);
  }

  public String toString() {
    return getQName();
  }

  /**
   * Returns the element Definitions which are subclassing this JavaBaseClass
   *
   * @return subclasses
   */
  public PuzzlePieceSet getElements() {
    return new PuzzlePieceSet(mSubElements);
  }

  /**
   * Returns the attribute Definitions which are shared by all subclasses of this JavaBaseClass
   *
   * @return attributes
   */
  public PuzzlePieceSet getBaseAttributes() {
    SortedSet<PuzzlePiece> attributes =
        new TreeSet<PuzzlePiece>(mSubElements.last().getAttributes());
    for (PuzzlePiece subelement : mSubElements.headSet(mSubElements.last())) {
      attributes.retainAll(subelement.getAttributes());
    }
    return new PuzzlePieceSet(attributes);
  }

  /**
   * Determines whether all subclasses of this JavaBaseClass are stylable or not stylable.
   *
   * @return whether all subclasses are stylable (true) or none (false).
   * @throws RuntimeException if some subclasses are stylable and some are not
   */
  public boolean isStylable() {
    boolean notStylable = false;
    boolean stylable = false;
    for (PuzzlePiece def : getElements()) {
      if (mOdfModel.isStylable(def)) {
        stylable = true;
      } else {
        notStylable = true;
      }
    }
    if (stylable && !notStylable) {
      return true;
    }
    if (notStylable && !stylable) {
      return false;
    }
    throw new RuntimeException(
        "Base Class "
            + getQName()
            + " used for stylable AND not stylable elements. This is not possible.");
  }
}
