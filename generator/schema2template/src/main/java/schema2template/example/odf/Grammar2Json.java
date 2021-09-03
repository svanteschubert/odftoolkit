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

import com.sun.msv.grammar.*;
import com.sun.msv.grammar.util.ExpressionWalker;
import java.util.*;
import java.util.logging.Logger;
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

  private static final Logger LOG = Logger.getLogger(Grammar2Json.class.getName());
  private Grammar mGrammar;

  // collect all reachable ElementExps and ReferenceExps.
  final Set<Expression> elementNodes = new HashSet<>();
  final Set<Expression> refNodes = new HashSet<>();
  // ElementExps and ReferenceExps who are referenced more than once.
  final Set<Expression> elementHeads = new HashSet<>();
  final Set<Expression> refHeads = new HashSet<>();

  // all paths of one node
  private final List<JSONObject> parentList = new ArrayList<JSONObject>();
  // private final List<JSONObject> childContainerByDepth = new ArrayList<JSONObject>();
  private JSONObject jsonGrammar = null;

  public Grammar2Json(Grammar g) {
    mGrammar = g;
  }

  public JSONObject getJSON() {
    try {
      if (jsonGrammar == null) {
        jsonGrammar = new JSONObject();
        JSONObject parent = new JSONObject();
        jsonGrammar.put("grammar", parent);
        parentList.add(parent);
        // childContainerByDepth.add(parent);
        initialize();
      }
      return jsonGrammar;
    } catch (Throwable e) {
      System.err.println("ERROR: HERE IS THE jsonGrammar\n" + jsonGrammar.toString(2));
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
              int previousDepth = -1;
              List result = null;

              @Override
              public void onChoice(ChoiceExp exp) {
                onBinExp(exp);
              }

              @Override
              public void onBinExp(BinaryExp exp) {
                exp.exp1.visit(this);
                exp.exp2.visit(this);
              }

              @Override
              public void onElement(ElementExp exp) {
                if (elementNodes.contains(exp)) {
                  elementHeads.add(exp);
                  return; // prevent infinite recursion.
                } else {
                  elementNodes.add(exp);
                  elementDepth++;

                  // DEBUG System.out.println("before evaluating new ElementChildren: parentList: "+
                  // parentList.toString());
                  // DEBUG System.out.println("ElementDepth: " + elementDepth);

                  evaluatePattern(exp);

                  // //DEBUG System.out.println("BEFORE CHECKING ON ELEMENT CONTENT previousDepth is
                  // now
                  // element Depth!");
                  previousDepth = elementDepth;

                  // DEBUG System.out.println("beforeElementChildren: parentList: " +
                  // parentList.toString());
                  // DEBUG System.out.println("beforeElementChildren: parentList.size(): " +
                  // parentList.size());
                  // DEBUG System.out.println("  ");

                  super.onElement(exp);

                  // once we come up an element, it is finished and can be removed from cache!
                  // DEBUG System.out.println("  ");
                  // DEBUG System.out.println("afterelement1: previousDepth: " + previousDepth);
                  // DEBUG System.out.println("afterelement1: ElementDepth: " + elementDepth);
                  // DEBUG System.out.println("afterelement1: parentList.size(): " +
                  // parentList.size());
                  // DEBUG System.out.println("afterelement1: parentList: " +
                  // parentList.toString());
                  // DEBUG System.out.println("  ");

                  // REMOVE LAST, but not after the end of a sibling element, but only after the end
                  // of a child element..
                  // 2 zu 1 -> 4 index liegen vor, der letze #3 muss gelöscht werden!
                  if (previousDepth > elementDepth) {
                    // DEBUG System.out.println("beforeRemoval:" + parentList.toString());
                    // i = 4 ; 3;
                    // DEBUG System.out.println("i = previous + 2 =  " + (previousDepth + 2));
                    // DEBUG System.out.println("end  = elementDepth + 2 =  " + (elementDepth + 2));
                    for (int i = (previousDepth + 1); (elementDepth + 1) < i; i--) {
                      // DEBUG System.out.println("Removing index " + i);
                      parentList.remove(i);
                    }
                    // DEBUG System.out.println("afterRemoval:" + parentList.toString());
                  }
                  previousDepth = elementDepth;
                  // DEBUG System.out.println("afterelement2: previousDepth: " + previousDepth);
                  // DEBUG System.out.println("afterelement2: ElementDepth: " + elementDepth);
                  // DEBUG System.out.println("afterelement2: parentList.size(): " +
                  // parentList.size());
                  // DEBUG System.out.println("afterelement2: parentList: " +
                  // parentList.toString());
                  // DEBUG System.out.println("++++++ENDING ELEMENT");

                  elementDepth--;
                }
              }

              @Override
              public void onAttribute(AttributeExp exp) {
                String attrName = PuzzlePiece.getName(exp);
                // //DEBUG System.out.println("AttributeName: " + attrName);
                addChildNode("@" + attrName, true);
                // Values are not of interest for now
                //                if (exp.exp instanceof ValueExp) {
                //                    // //DEBUG System.out.println("style:family-1" + ((ValueExp)
                // exp.exp).value.toString());
                //                    addChildNode(parent, ((ValueExp) exp.exp).value.toString());
                //                } else if (exp.exp instanceof ChoiceExp) {
                //                    // //DEBUG System.out.println("style:family-A" + ((ValueExp)
                // ((ChoiceExp)
                //                    // exp.exp).exp1).value.toString());
                //                    // //DEBUG System.out.println("style:family-B" + ((ValueExp)
                // ((ChoiceExp)
                //                    // exp.exp).exp2).value.toString());
                //                    addChildNode(parent, ((ValueExp) ((ChoiceExp)
                // exp.exp).exp1).value.toString());
                //                    addChildNode(parent, ((ValueExp) ((ChoiceExp)
                // exp.exp).exp2).value.toString());
                //                } else {
                //                    // //DEBUG System.out.println("NOT FAMILY '" + attrName +
                // "'");
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
                // //DEBUG System.out.println("REF NAME" + refName);
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
                // //DEBUG System.out.println("depth:" + depth);
                if ((exp.getNameClass() instanceof SimpleNameClass)
                    || (exp.getNameClass() instanceof AnyNameClass)
                    || (exp.getNameClass() instanceof ChoiceNameClass)) {
                  String elementName = null;
                  if ((exp.getNameClass() instanceof SimpleNameClass)) {
                    elementName = ((SimpleNameClass) exp.getNameClass()).localName;
                  } else if (exp.getNameClass() instanceof AnyNameClass) {
                    elementName = "*:*";
                  } else if (exp.getNameClass() instanceof ChoiceNameClass) {
                    elementName = "CHOICE_NAME_CLASS";
                  }
                  if (elementName.equals("description")) {
                    // System.err.println("BREAK!!: HERE IS THE jsonGrammar\n" +
                    // jsonGrammar.toString(2));
                    // DEBUG System.out.println("hit");
                  }
                  JSONObject parent = null;
                  // a new depth was reached and the element is the first child element
                  // if there is a new child hierarchy - e.g. depth 1 is first under root element
                  // DEBUG System.out.println("---------------------------");
                  // DEBUG System.out.println("previousDepth: " + previousDepth);
                  // DEBUG System.out.println("ElementDepth: " + elementDepth);
                  // DEBUG System.out.println("parentList.size(): " + parentList.size());
                  // DEBUG System.out.println("ElementName: "+ elementName+ " the next Map is empty:
                  // "+ (elementDepth + 1 == parentList.size())); //  parentList.get(elementDepth +1
                  // ));
                  if (elementDepth > previousDepth) {
                    // get the last element
                    parent = parentList.get(elementDepth);
                    // add the new element to the parent
                    JSONObject newChildNodeBody = new JSONObject();
                    parent.put(elementName, newChildNodeBody);

                    // add the new element as new hierarchy
                    parentList.add(newChildNodeBody);
                    // childContainerByDepth.add(newChildNodeBody);
                  } else { // if this element is a sibling
                    // always one child, either a single element map or the array of children
                    // DEBUG System.out.println("previousDepth: " + previousDepth);
                    // DEBUG System.out.println("ElementDepth: " + elementDepth);
                    // DEBUG System.out.println("new siblingyeah1!" + parentList.size() + " " +
                    // parentList.toString());
                    // this sibling element's map is the new map on this level (the earlier at the
                    // end, have to be removed)
                    parentList.remove(elementDepth + 1);
                    parentList.add(elementDepth + 1, addChildNode(elementName, false));
                    // DEBUG System.out.println("new siblingyeah2!" + parentList.size() + " " +
                    // parentList.toString());
                  }
                } else {
                  NameClass nc = exp.getNameClass();
                  return; // can there be multiple names?
                }
              }

              // either an attribute or newElement will be added to the JSONMap of the parent
              // there is already one child, if this is a

              /**
               * @param childName
               * @param isAttribute
               * @return neu hinzugefügtes element's body
               */
              private JSONObject addChildNode(String childName, boolean isAttribute) {
                JSONObject newChildNodeBody = null;
                if (childName == null || childName.isEmpty()) {
                  System.err.println("ERROR: childName does not exist!");
                }
                JSONObject container = null;
                if (isAttribute) {
                  // attributes are added one deeper as content in XML they are on the same element
                  // depth as parent
                  container = parentList.get(elementDepth + 1);
                } else {
                  // parent of elementDepth is always for new children, therefore one above for
                  // siblings
                  // WHY PROBLEMS HERE?
                  container = parentList.get(elementDepth);
                }
                newChildNodeBody = new JSONObject();
                container.put(childName, newChildNodeBody);
                return newChildNodeBody;
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
