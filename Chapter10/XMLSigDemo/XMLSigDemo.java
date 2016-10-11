// XMLSigDemo.java

import java.io.*;

import java.security.*;

import java.util.*;

import javax.xml.crypto.*;
import javax.xml.crypto.dom.*;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.*;
import javax.xml.crypto.dsig.keyinfo.*;
import javax.xml.crypto.dsig.spec.*;

import javax.xml.parsers.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

public class XMLSigDemo
{
   public static void main (String [] args) throws Exception
   {
      boolean sign = true;

      if (args.length == 1)
          sign = false; // validate instead of sign
      else
      if (args.length != 2)
      {
          System.out.println ("usage: java XMLSigDemo inFile [outFile]");
          return;
      }

      if (sign)
          signDoc (args [0], args [1]);
      else
          validateSig (args [0]);
   }

   static void signDoc (String inFile, String outFile) throws Exception
   {
      // Obtain the default implementation of DocumentBuilderFactory to parse
      // the XML document that is to be signed.

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();

      // Because XML signatures use XML namespaces, the factory is told to be
      // namespace-aware.

      dbf.setNamespaceAware (true);

      // Use the factory to obtain a DocumentBuilder instance, which is used
      // to parse the document identified by inFile.

      Document doc = dbf.newDocumentBuilder ().parse (new File (inFile));

      // Generate a DSA KeyPair with a length of 512 bits. The private key is
      // used to generate the signature.

      KeyPairGenerator kpg = KeyPairGenerator.getInstance ("DSA");
      kpg.initialize (512);
      KeyPair kp = kpg.generateKeyPair ();

      // Create a DOM-specific XMLSignContext. This class contains context
      // information for generating XML Signatures. It is initialized with the
      // private key that will be used to sign the document and the root of
      // the document to be signed.

      DOMSignContext dsc = new DOMSignContext (kp.getPrivate (),
                                               doc.getDocumentElement ());

      // The different parts of the Signature element are assembled into an
      // XMLSignature object. These objects are created and assembled using an
      // XMLSignatureFactory. Because DocumentBuilderFactory was used to parse
      // the XML document into a DOM object tree, a DOM implementation of
      // XMLSignatureFactory is obtained.

      XMLSignatureFactory fac = XMLSignatureFactory.getInstance ("DOM");

      // Create a Reference element to the content to be digested: An empty
      // string URI ("") implies the document root. SHA1 is used as the digest
      // method. A single enveloped Transform is required for an enveloped
      // signature, so that the Signature element and contained elements are
      // not included when calculating the signature.

      Transform xfrm = fac.newTransform (Transform.ENVELOPED,
                                         (TransformParameterSpec) null);
      Reference ref;
      ref = fac.newReference ("",
                              fac.newDigestMethod (DigestMethod.SHA1, null),
                              Collections.singletonList (xfrm), null,
                              "MyRef");

      // Create the SignedInfo object, which is the only object that is
      // signed -- a Reference element's identified data object is digested,
      // and it is the digest value that is part of the SignedInfo object that
      // is included in the signature. The CanonicalizationMethod chosen is
      // inclusive and preserves comments, the SignatureMethod is DSA, and the
      // list of References contains only one Reference.

      CanonicalizationMethod cm;
      cm = fac.newCanonicalizationMethod (CanonicalizationMethod.
                                          INCLUSIVE_WITH_COMMENTS,
                                          (C14NMethodParameterSpec) null);
      SignatureMethod sm;
      sm = fac.newSignatureMethod (SignatureMethod.DSA_SHA1, null);
      SignedInfo si;
      si = fac.newSignedInfo (cm, sm, Collections.singletonList (ref));

      // Create the KeyInfo object, which allows the recipient to find the
      // public key needed to validate the signature.

      KeyInfoFactory kif = fac.getKeyInfoFactory ();
      KeyValue kv = kif.newKeyValue (kp.getPublic ());
      KeyInfo ki = kif.newKeyInfo (Collections.singletonList (kv));

      // Create the XMLSignature object, passing the SignedInfo and KeyInfo
      // values as arguments.

      XMLSignature signature = fac.newXMLSignature (si, ki);

      // Generate the signature.

      signature.sign (dsc);

      System.out.println ("Signature generated!");
      System.out.println ("Outputting to "+outFile);

      // Transform the DOM-based XML content and Signature element into a
      // stream of content that is output to the file identified by outFile.

      TransformerFactory tf = TransformerFactory.newInstance ();
      Transformer trans = tf.newTransformer ();
      trans.transform (new DOMSource (doc),
                       new StreamResult (new FileOutputStream (outFile)));
   }

   @SuppressWarnings ("unchecked")
   static void validateSig (String inFile) throws Exception
   {
      // Obtain the default implementation of DocumentBuilderFactory to parse
      // the XML document that contains the signature.

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance ();

      // Because XML signatures use XML namespaces, the factory is told to be
      // namespace-aware.

      dbf.setNamespaceAware (true);

      // Use the factory to obtain a DocumentBuilder instance, which is used
      // to parse the document identified by inFile.

      Document doc = dbf.newDocumentBuilder ().parse (new File (inFile));

      // Return a list of all Signature element nodes in the DOM object tree.
      // There must be at least one Signature element -- the signDoc() method
      // results in exactly one Signature element.

      NodeList nl = doc.getElementsByTagNameNS (XMLSignature.XMLNS,
                                                "Signature");
      if (nl.getLength () == 0)
          throw new Exception ("Missing Signature element");

      // Create a DOM-specific XMLValidateContext. This class contains context
      // information for validating XML Signatures. It is initialized with the
      // public key that will be used to validate the document, and a
      // reference to the Signature element to be validated. The public key
      // will be obtained by invoking keyValueKeySelector's select() method
      // (behind the scenes).

      DOMValidateContext dvc;
      dvc = new DOMValidateContext (new KeyValueKeySelector (), nl.item (0));

      // The different parts of the Signature element are unmarshalled into an
      // XMLSignature object. The Signature element is unmarshalled using an
      // XMLSignatureFactory. Because DocumentBuilderFactory was used to parse
      // the XML document (containing the Signature element) into a DOM object
      // tree, a DOM implementation of XMLSignatureFactory is obtained.

      XMLSignatureFactory fac = XMLSignatureFactory.getInstance ("DOM");

      // Unmarshal the XML Signature from the DOM tree.

      XMLSignature signature = fac.unmarshalXMLSignature (dvc);

      // Validate the XML Signature.

      boolean coreValidity = signature.validate (dvc);
      if (coreValidity)
      {
          System.out.println ("Signature is valid!");
          return;
      }

      System.out.println ("Signature is invalid!");

      // Identify the cause or causes of failure.

      System.out.println ("Checking Reference digest for validity...");

      List<Reference> refs;
      refs = (List<Reference>) signature.getSignedInfo ().getReferences ();
      for (Reference r: refs)
           System.out.println ("  Reference '"+r.getId ()+"' digest is "+
                               (r.validate (dvc) ? "" : "not ")+"valid");

      System.out.println ("Checking SignatureValue element for validity...");

      System.out.println ("  SignatureValue element's value is "+
                          (signature.getSignatureValue ().validate (dvc)
                          ? "" : "not ")+"valid");
   }

   private static class KeyValueKeySelector extends KeySelector
   {
      // Search the Signature element's KeyInfo element's KeyValue elements
      // for the public key that will be used for validation. No determination
      // is made if the key can be trusted.

      public KeySelectorResult select (KeyInfo keyInfo,
                                       KeySelector.Purpose purpose,
                                       AlgorithmMethod method,
                                       XMLCryptoContext context)
         throws KeySelectorException
      {
         if (keyInfo == null)
             throw new KeySelectorException ("Null KeyInfo object!");

         SignatureMethod sm = (SignatureMethod) method;
         List list = keyInfo.getContent ();

         for (int i = 0; i < list.size (); i++)
         {
              XMLStructure xmlStructure = (XMLStructure) list.get (i);
              if (xmlStructure instanceof KeyValue)
              {
                  PublicKey pk = null;
                  try
                  {
                      pk = ((KeyValue) xmlStructure).getPublicKey ();
                  }
                  catch (KeyException ke)
                  {
                      throw new KeySelectorException (ke);
                  }

                  // Make sure algorithm is compatible with signature method.

                  if (algEquals (sm.getAlgorithm (), pk.getAlgorithm ()))
                  {
                      final PublicKey pk2 = pk;
                      return new KeySelectorResult ()
                                 {
                                     public Key getKey ()
                                     {
                                        return pk2;
                                     }
                                 };
                  }
              }
         }

         throw new KeySelectorException ("No KeyValue element found!");
      }
   }

   static boolean algEquals (String algURI, String algName)
   {
      if (algName.equalsIgnoreCase ("DSA") &&
          algURI.equalsIgnoreCase (SignatureMethod.DSA_SHA1))
          return true;
    
      if (algName.equalsIgnoreCase ("RSA") &&
          algURI.equalsIgnoreCase (SignatureMethod.RSA_SHA1))
          return true;
    
      return false;
   }
}
