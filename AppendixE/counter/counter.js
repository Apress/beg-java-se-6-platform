// counter.js

var new_counter = function (current_count)
                  {
                     return function (incr)
                     {
                        return current_count += incr;
                     };
                  };
