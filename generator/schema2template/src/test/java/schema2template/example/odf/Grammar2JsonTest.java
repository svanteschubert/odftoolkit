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

import com.sun.msv.grammar.Grammar;
import java.io.File;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import schema2template.model.XMLModel;

/** @author Svante Schubert */
public class Grammar2JsonTest {

  //  private static final String EXPECTED_CII_16B_RESULT = "";
  //  private static final String EXPECTED_CII_21A_RESULT = "";
  //  private static final String EXPECTED_UBL_1_2_INVOICE_RESULT = "";
  //  private static final String EXPECTED_UBL_1_2_CREDIT_NOTE_RESULT = "";
  //  private static final String EXPECTED_UBL_1_3_INVOICE_RESULT = "";
  //  private static final String EXPECTED_UBL_1_3_CREDIT_NOTE_RESULT = "";

  private static final String GRAMMAR_FILE_ROOT =
      "examples" + File.separator + "eprocurement" + File.separator;
  private static final String CII_21A_GRAMMAR =
      "examples"
          + File.separator
          + "odf"
          + File.separator
          + "odf-schemas"
          + File.separator
          + "OpenDocument-v1.3-schema.rng";

  // private static final String CII_21A_GRAMMAR = GRAMMAR_FILE_ROOT + "cii" + File.separator +
  // "21A" + File.separator + "uncefact" + File.separator + "data" + File.separator + "standard" +
  // File.separator + "CrossIndustryInvoice_21p1.xsd";
  private static final String CII_16B_GRAMMAR = "";
  private static final String UBL_1_2_INVOICE_GRAMMAR = "";
  private static final String UBL_1_2_CREDIT_NOTE_GRAMMAR = "";
  private static final String UBL_1_3_INVOICE_GRAMMAR = "";
  private static final String UBL_1_3_CREDIT_NOTE_GRAMMAR = "";

  public Grammar2JsonTest() {}

  @BeforeClass
  public static void setUpClass() {}

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  /** Test of getProperties method, of class Grammar2Json. */
  @Test
  public void testGetProperties() throws Exception {
    Grammar g = XMLModel.loadSchema(getAbsolutePathFromClassloader(CII_21A_GRAMMAR));
    Grammar2Json instance = new Grammar2Json(g);
    System.out.println("JSON GRAMMAR:\n" + instance.getJSON().toString(4));

    //    if (!EXPECTED_CII_16B_RESULT.equals(result))
    //      fail("The reference and test result differ:\nExpected:\n" + EXPECTED_CII_16B_RESULT +
    // "\nTest:\n" + result);
  }

  private static String getAbsolutePathFromClassloader(String resourcePath) {
    String path = null;
    try {
      path = SchemaToTemplate.class.getClassLoader().getResource(resourcePath).toURI().getPath();
    } catch (URISyntaxException ex) {
      Logger.getLogger(SchemaToTemplate.class.getName()).log(Level.SEVERE, null, ex);
    }
    return path;
  }
}
