/*
 * Copyright 2021 The Document Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package schema2template.example.odf;

import com.sun.msv.grammar.AttributeExp;
import com.sun.msv.grammar.ElementExp;
import com.sun.msv.grammar.Expression;
import com.sun.msv.grammar.Grammar;
import com.sun.msv.grammar.ReferenceExp;
import com.sun.msv.grammar.SimpleNameClass;
import com.sun.msv.grammar.util.ExpressionWalker;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;
import schema2template.model.PuzzlePiece;

/**
 * => JEDES ELEMENT kommt als key in die parentMap und ist eine Map, es bekommt beim Finden eine
 * neue MAP! hat es mehr als eine ChildNode (attr/Elements) hat diese Map ein Array zum Inhalt Wo
 * kommt die hin? -> wenn neue Tiefe, dann in die Tiefenmap -> wenn gleiche Tiefe, dann in
 * existierende Map (ggf. List oder Tiefenmap) => TIEFE entscheidet zu wissen in welcher Map wir das
 * Element einzufügen ist aufgrund von tiefe müssten wir uns die richtige Map bzw. deren Container
 * zur hand bekommen!! NOTE: Tiefe 1 gibt den Inhalt des ersten childs tiefenMap -> gibt inhalt von
 * Map => OBJECT (entweder ARRAY oder MAP) wenn noch MAP schauen ob schon etwas din ist.. falls
 * nicht BRUTAL FORCE=>Method: EXCHANGE MAP to ARRAY with MAP() :-) => wenn man hochgeht die Map
 * herauslöschen! :-)
 */
public class Grammar2Json {

  private Grammar mGrammar;

  // collect all reachable ElementExps and ReferenceExps.
  final Set<Expression> elementNodes = new HashSet<>();
  final Set<Expression> refNodes = new HashSet<>();
  // ElementExps and ReferenceExps who are referenced more than once.
  final Set<Expression> elementHeads = new HashSet<>();
  final Set<Expression> refHeads = new HashSet<>();

  // all paths of one node
  private final List<JSONObject> descendantList = new ArrayList<JSONObject>();
  private final List<String> descendantNameList = new ArrayList<String>();
  private JSONObject jsonGrammar = null;

  public Grammar2Json(Grammar g) {
    mGrammar = g;
  }

  public JSONObject getJSON() {
    try {
      if (jsonGrammar == null) {
        // jsonGrammar = new JSONObject();
        // jsonGrammar.put("root", new JSONObject());
        initialize();
      }
      return jsonGrammar;
    } catch (Throwable e) {
      System.err.println("ERROR: HERE IS THE jsonGrammar\n" + jsonGrammar.toString(4));
      throw new RuntimeException(e);
    }
  }

  private void initialize() {
    mGrammar
        .getTopLevel()
        .visit(
            new ExpressionWalker() {
              // ExpressionWalker class traverses expressions in depth-first order.
              // So this invocation traverses the all reachable expressions from
              // the top level expression.

              // Whenever visiting elements and RefExps, they are memorized
              // to identify head of islands.
              int elementDepth = -1;
              List result = null;

              @Override
              public void onElement(ElementExp exp) {
                if (elementNodes.contains(exp)) {
                  elementHeads.add(exp);
                  return; // prevent infinite recursion.
                } else {
                  elementNodes.add(exp);
                  elementDepth++;
                  evaluatePattern(exp);
                  super.onElement(exp);
                  // once we come up an element, it is finished and can be removed from cache!
                  descendantList.remove(elementDepth);
                  elementDepth--;
                }
              }

              @Override
              public void onAttribute(AttributeExp exp) {
                String attrName = PuzzlePiece.getName(exp);
                // System.out.println("AttributeName: " + attrName);
                addChildNode("@" + attrName, null);
                // Values are not of interest for now
                //                if (exp.exp instanceof ValueExp) {
                //                    // System.out.println("style:family-1" + ((ValueExp)
                // exp.exp).value.toString());
                //                    addChildNode(parent, ((ValueExp) exp.exp).value.toString());
                //                } else if (exp.exp instanceof ChoiceExp) {
                //                    // System.out.println("style:family-A" + ((ValueExp)
                // ((ChoiceExp)
                //                    // exp.exp).exp1).value.toString());
                //                    // System.out.println("style:family-B" + ((ValueExp)
                // ((ChoiceExp)
                //                    // exp.exp).exp2).value.toString());
                //                    addChildNode(parent, ((ValueExp) ((ChoiceExp)
                // exp.exp).exp1).value.toString());
                //                    addChildNode(parent, ((ValueExp) ((ChoiceExp)
                // exp.exp).exp2).value.toString());
                //                } else {
                //                    // System.out.println("NOT FAMILY '" + attrName + "'");
                //                    /*
                //         * <rng:attribute name="style:family"> <rng:choice>
                //         * <rng:value>graphic</rng:value> <rng:value>presentation</rng:value>
                //         * </rng:choice> </rng:attribute>
                //                     */
                //                }

                super.onAttribute(exp);
              }

              @Override
              public void onRef(ReferenceExp exp) {
                String refName = ((ReferenceExp) exp).name;
                // System.out.println("REF NAME" + refName);
                // we will allow two times nested refs, but than we break
                if (refNodes.contains(exp)) {
                  if (refHeads.contains(exp)) {
                    return; // prevent infinite recursion.
                  } else {
                    // allow the reference to be parsed once again to find our pattern
                    refHeads.add(exp);
                  }
                }
                elementNodes.add(exp);
                super.onRef(exp);
                // first only remove the head
                if (refHeads.contains(exp)) {
                  refHeads.remove(exp);
                } else if (refNodes.contains(exp)) {
                  // second time remove the head
                  refNodes.remove(exp);
                }
              }

              private void evaluatePattern(ElementExp exp) {
                // System.out.println("depth:" + depth);
                if (!(exp.getNameClass() instanceof SimpleNameClass)) {
                  return;
                }
                String elementName = ((SimpleNameClass) exp.getNameClass()).localName;
                if (elementName.equals("CrossIndustryInvoice")) {
                  System.out.println("hit");
                }
                JSONObject newElement = new JSONObject();
                JSONObject parent = null;
                // a new depth was reached an the element is the first child element
                if (elementDepth == 0 && jsonGrammar == null) {
                  // in the very beginning asign first Element to our root element
                  jsonGrammar = new JSONObject();
                  jsonGrammar.put(elementName, newElement);
                  // no container from above is being taken as we are the first
                  // but we add ourselves as it would be a new child hierarchy
                  // descendantList.add(jsonGrammar);
                  descendantList.add(newElement);
                  descendantNameList.add(elementName);
                } else {
                  // if there is a new child hierarchy
                  if (elementDepth == (descendantList.size())) {
                    // get the parent
                    if (elementDepth == 0) {
                      parent = jsonGrammar;
                    } else {
                      parent = descendantList.get(elementDepth - 1);
                    }
                    // add the new element to the parent
                    parent.put(elementName, newElement);
                    // add the new element as new hierarchy
                    descendantList.add(newElement);
                    descendantNameList.add(elementName);
                  } else { // if this element is a sibling
                    // always one child, either a single element map or the array of children
                    /*
                    descendantList.remove(elementDepth);
                    descendantList.add(newElement);
                    descendantNameList.add(elementDepth);
                    descendantNameList.add(elementName);
                    */
                    addChildNode(elementName, newElement);
                  }
                }
                // System.out.println("ELEMENT NameClass: " + exp.getNameClass().toString());
                // //SvanteDebug
              }

              // either an attribute or element will be added to the JSONMap of the parent
              // there is already one child, if this is a
              private void addChildNode(String childName, JSONObject element) {
                if (childName == null || childName.isEmpty()) {
                  System.err.println("ERROR: childName does not exist!");
                }
                String childKey = descendantNameList.get(elementDepth);
                JSONObject parent;
                // get the parent container
                if (elementDepth == 0) {
                  parent = jsonGrammar;
                } else {
                  parent = descendantList.get(elementDepth - 1);
                }
                Object child = parent.get(childKey);
                // already child array simply append
                if (child instanceof JSONArray) {
                  if (element == null) {
                    // as attribute simply add only the attribute name
                    ((JSONArray) child).put(childName);
                  } else { // add the complete element
                    ((JSONArray) child).put(element);
                  }
                } else { // exchange the existing JSONObject child with an JSONArray
                  if (!(child instanceof JSONObject)) {
                    System.err.println("ERRROR: Child is expected to be a JSONObject!");
                  }
                  parent.remove(childKey);
                  JSONArray childContainer = new JSONArray();
                  childContainer.put(child);
                  parent.put(childKey, childContainer);

                  if (element == null) {
                    // as attribute simply add only the attribute name
                    childContainer.put(childName);
                  } else { // add the complete element
                    childContainer.put(element);
                  }
                }
              }
            });
  }

  public static String listAsString(List<String> nodes) { // 2DO:
    JSONArray firstChild = new JSONArray();
    firstChild.put("@foo");
    firstChild.put("Dummy");
    JSONObject root = new JSONObject();
    root.put("my:root", firstChild);
    //
    //    StringBuilder sb = new StringBuilder();
    //    for (String node : nodes) {
    //        //FIXME
    ////      sb.append("@style:family = '").append(node).append("' =");
    ////      sb.append("\n");
    //    }
    //    return sb.toString();
    return root.toString();
  }

  public static String mapAsString(Map<String, List<String>> results) {
    Set<String> families = results.keySet();
    StringBuilder sb = new StringBuilder();
    for (String family : families) {
      sb.append("@style:family = '").append(family).append("' =");
      List<String> propNames = results.get(family);
      for (String propName : propNames) {
        sb.append(" style:").append(propName);
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
