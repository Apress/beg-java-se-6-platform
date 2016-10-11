// StubAnnotationProcessor.java

import static javax.lang.model.SourceVersion.*;
import static javax.tools.Diagnostic.Kind.*;

import java.lang.annotation.*;

import java.util.*;

import javax.annotation.processing.*;

import javax.lang.model.element.*;

@SupportedAnnotationTypes("Stub")
@SupportedSourceVersion(RELEASE_6)
public class StubAnnotationProcessor extends AbstractProcessor
{
   // The javac tool invokes this method to process a set of annotation types
   // originating fom the previous round of annotation processing. The method
   // returns a Boolean value indicating whether (true) or not (false) the
   // annotations are claimed. When annotations are claimed, they will not be
   // subsequently processed.

   public boolean process (Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv)
   {
      // If types generated by this round of annotation processing are subject
      // to a subsequent round of annotation processing ...

      if (!roundEnv.processingOver ())
      {
          Set<? extends Element> elements;
          elements = roundEnv.getElementsAnnotatedWith (Stub.class);

          Iterator<? extends Element> it = elements.iterator ();
          while (it.hasNext ())
          {
             Element element = it.next ();
             String kind = element.getKind ().equals (ElementKind.METHOD)
                           ? "Method " : "Constructor ";
             String name = element.toString ();
             processingEnv.getMessager ().
               printMessage (NOTE, kind+name+ " needs to be fully implemented");
          }
      }

      return true; // Claim the annotations.
   }
}