// demo.fx

import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

Frame
{
   width: 650
   height: 150
   title: "demo.fx"
   background: lightgoldenrodyellow
   centerOnScreen: true
   content: Canvas
   {
      content: Text
      {
         x: 15
         y: 20
         content: "{msg:<<java.lang.String>>}"
         font: Font { face: VERDANA, style: [ITALIC, BOLD], size: 80 }
         fill: LinearGradient
         {
            x1: 0, y1: 0, x2: 0, y2: 1

            stops: 
            [
               Stop
               {
                  offset: 0
                  color: blue
               },

               Stop
               {
                  offset: 0.5
                  color: dodgerblue
               },

               Stop
               {
                  offset: 1
                  color: blue
               }
            ]
         }

         filter: [MotionBlur { distance: 10.5 }, Glow {amount: 0.15},
                  Noise {monochrome: false, distribution: 0}]
      }
   }
   visible: true
}
