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
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.*;
import schema2template.model.XMLModel;

/** @author Svante Schubert */
public class Grammar2JsonTest {

  private static final Logger LOG = Logger.getLogger(Grammar2JsonTest.class.getName());

  private static final String GRAMMAR_FILE_ROOT =
      "examples" + File.separator + "eprocurement" + File.separator;

  private static final String ODF13_GRAMMAR =
      "examples"
          + File.separator
          + "odf"
          + File.separator
          + "odf-schemas"
          + File.separator
          + "OpenDocument-v1.3-schema.rng";

  private static final String CII_21A_GRAMMAR =
      GRAMMAR_FILE_ROOT
          + "cii"
          + File.separator
          + "21A"
          + File.separator
          + "uncefact"
          + File.separator
          + "data"
          + File.separator
          + "standard"
          + File.separator
          + "CrossIndustryInvoice_21p1.xsd";

  private static final String CII_16B_GRAMMAR =
      GRAMMAR_FILE_ROOT
          + "cii"
          + File.separator
          + "16B"
          + File.separator
          + "uncefact"
          + File.separator
          + "data"
          + File.separator
          + "standard"
          + File.separator
          + "CrossIndustryInvoice_100pD16B.xsd";

  private static final String UBL_2_1_INVOICE_GRAMMAR =
      GRAMMAR_FILE_ROOT
          + "ubl"
          + File.separator
          + "UBL-2.1"
          + File.separator
          + "xsd"
          + File.separator
          + "maindoc"
          + File.separator
          + "UBL-Invoice-2.1.xsd";

  private static final String UBL_2_1_CREDIT_NOTE_GRAMMAR =
      GRAMMAR_FILE_ROOT
          + "ubl"
          + File.separator
          + "UBL-2.1"
          + File.separator
          + "xsd"
          + File.separator
          + "maindoc"
          + File.separator
          + "UBL-CreditNote-2.1.xsd";

  private static final String UBL_2_2_INVOICE_GRAMMAR = "";
  private static final String UBL_2_2_CREDIT_NOTE_GRAMMAR = "";
  private static final String UBL_2_3_INVOICE_GRAMMAR = "";
  private static final String UBL_2_3_CREDIT_NOTE_GRAMMAR = "";

  private static String TARGET_BASE_DIR =
      "target" + File.separator + "generated-sources" + File.separator + "java" + File.separator;

  private static String TARGET_ODF13 =
      Paths.get(System.getProperty("user.dir"), TARGET_BASE_DIR, "ODF13.json")
          .normalize()
          .toString();

  private static String TARGET_CII_21A =
      Paths.get(System.getProperty("user.dir"), TARGET_BASE_DIR, "CII_21A.json")
          .normalize()
          .toString();

  private static String TARGET_CII_16B =
      Paths.get(System.getProperty("user.dir"), TARGET_BASE_DIR, "CII_16B.json")
          .normalize()
          .toString();

  private static String TARGET_UBL21_CREDIT_NOTE =
      Paths.get(System.getProperty("user.dir"), TARGET_BASE_DIR, "UBL-CreditNote-2.1.json")
          .normalize()
          .toString();

  private static String TARGET_UBL21_INVOICE =
      Paths.get(System.getProperty("user.dir"), TARGET_BASE_DIR, "UBL-Invoice-2.1.json")
          .normalize()
          .toString();

  public Grammar2JsonTest() {}

  @BeforeClass
  public static void setUpClass() {}

  @AfterClass
  public static void tearDownClass() {}

  @Before
  public void setUp() {}

  @After
  public void tearDown() {}

  /** Test Grammar2Json for a certain grammar */
  @Test
  @Ignore
  public void testODF13() throws Exception {
    Grammar g = XMLModel.loadSchema(getAbsolutePathFromClassloader(ODF13_GRAMMAR));
    Grammar2Json instance = new Grammar2Json(g);
    String grammarJSON = instance.getJSON().toString(4);
    // System.out.println("JSON GRAMMAR:\n" + grammarJSON);
    ensureParentFolders(TARGET_ODF13);
    try (PrintWriter out = new PrintWriter(TARGET_ODF13)) {
      out.println(grammarJSON);
    }
  }

  /** Test Grammar2Json for a certain grammar */
  @Test
  @Ignore
  public void testCII_21A() throws Exception {
    Grammar g = XMLModel.loadSchema(getAbsolutePathFromClassloader(CII_21A_GRAMMAR));
    Grammar2Json instance = new Grammar2Json(g);
    String grammarJSON = instance.getJSON().toString(4);
    // System.out.println("JSON GRAMMAR:\n" + grammarJSON);
    ensureParentFolders(TARGET_CII_21A);
    try (PrintWriter out = new PrintWriter(TARGET_CII_21A)) {
      out.println(grammarJSON);
    }
  }

  /** Test Grammar2Json for a certain grammar */
  @Test
  @Ignore
  public void testCII_16B() throws Exception {
    Grammar g = XMLModel.loadSchema(getAbsolutePathFromClassloader(CII_16B_GRAMMAR));
    Grammar2Json instance = new Grammar2Json(g);
    String grammarJSON = instance.getJSON().toString(4);
    // System.out.println("JSON GRAMMAR:\n" + grammarJSON);
    ensureParentFolders(TARGET_CII_16B);
    try (PrintWriter out = new PrintWriter(TARGET_CII_16B)) {
      out.println(grammarJSON);
    }
  }

  /** Test Grammar2Json for a certain grammar */
  @Test
  @Ignore
  public void testUBL12_CreditNote() throws Exception {
    Grammar g = XMLModel.loadSchema(getAbsolutePathFromClassloader(UBL_2_1_CREDIT_NOTE_GRAMMAR));
    Grammar2Json instance = new Grammar2Json(g);
    String grammarJSON = instance.getJSON().toString(4);
    // System.out.println("JSON GRAMMAR:\n" + grammarJSON);
    ensureParentFolders(TARGET_UBL21_CREDIT_NOTE);
    try (PrintWriter out = new PrintWriter(TARGET_UBL21_CREDIT_NOTE)) {
      out.println(grammarJSON);
    }
  }

  /** Test Grammar2Json for a certain grammar */
  @Test
  public void testUBL12_Invoice() throws Exception {
    Grammar g = XMLModel.loadSchema(getAbsolutePathFromClassloader(UBL_2_1_INVOICE_GRAMMAR));
    Grammar2Json instance = new Grammar2Json(g);
    String grammarJSON = instance.getJSON().toString(4);
    // System.out.println("JSON GRAMMAR:\n" + grammarJSON);
    ensureParentFolders(TARGET_UBL21_INVOICE);
    try (PrintWriter out = new PrintWriter(TARGET_UBL21_INVOICE)) {
      out.println(grammarJSON);
    }
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

  private static void ensureParentFolders(String outputFilePath) {
    File newFile = new File(outputFilePath);
    File parent = newFile.getParentFile();
    if (parent != null && !parent.exists()) {
      try {
        parent.mkdirs();
      } catch (Exception e) {
        LOG.log(Level.WARNING, "Could not create parent directory {0}", parent.getAbsolutePath());
      }
    }
  }
}
