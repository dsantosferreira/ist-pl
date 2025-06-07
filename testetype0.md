// 3
let x = 1;
let y = true;
(x+2*x);;

// true
let x = 1;
let y = true;
(x>3 || y);;

/* fails */
let x = 1;
let y = true;
(x>3 && x>y);;

// false
let x = 1;
let y = (x > 2);
(x>3 && y);;

// false
let x = 1;
let y = (x > 2);
(x>5/2 && y);;

// false
let x = 1;
let y = (x > 2);
(2*x>5/2 && y);;

/* fails */
let x = 1;
let y = (x > 2);
-(2*x>5/2 && y);;

// true
let x = 1;
let y = (x > 2);
~(2*x>5/2 && y);;

// 2
let x = 1;
let y = (x > 2);
if (y) { 1 } else { 2 };;

/* fails */
let x = 1;
let y = (x > 2);
if (y) { 1 } else { x > 1 };;

// false
let x = 1;
let y = (x > 2);
if (y) { x ~= 1 } else { y };;

// 10
(let x = 1; (x + 1)) * (let x = 2; (x + 3));;

// 72
let x = 2;
let z = x+2;
let k = (let x = z+2; x*x);
k+k;;

// true 
let y = 1;
let b = (y > 0) && (y <= 20);
let z = (let z = 2*y;  z*z);
b || ~ (z < 0)
;;

// 49
(fn z:int => { fn t:int => { z*t }}) (7) (7);;

// 49
(fn z:int,t:int => { z*t }) (7) (7);;

// 7
let x = 1;
let f = fn y:int => { x + y };
(
let x = 4;
(x + f (2))
)
;;

// 5
let x=1 ;			 
let f = fn y:int => { y+x } ;
let g = fn x:int => { x+f(x) };
g(2) 
;;

/* fails */
let f = fn g:int->bool,z:int => { g (z) };
f (fn z:int => { z*2 }) (7)
;;

// 14
let f = fn g:int->int,z:int => { g (z) };
f (fn z:int => { z*2 }) (7)
;;

// 
let x:int = 2; x;;

/* fails */
let x:bool = 2; x;;

// 14
let x=1 ;			 
let f = fn y:int => {
           let k = x*2;
  	   y+x*k
        };
let g = fn x:int, u:int->int =>
           { u(x) + f(x) };
g  (f(3)) (f) 
;;

// 199
let comp = fn f:int->int, g:int->int => 
        { fn x:int =>
        { f (g (x)) }};
let inc = fn x:int => { x + 1};
let dup = fn x:int => { 2 * x};
let c2 = comp (inc) (dup);
c2 (99)
;;

// 120
let fact:int->int =
    fn n:int => {
        if (n==0) { 1 }
        else { n * (fact (n-1))}
    };
fact (5);;

// 120
let fact:int->int = (
    let one = 1;
    fn n:int => {
        if (n==0) { one }
        else { n * (fact (n-1))}
    });
fact (5);;

// 3628800
let reduce: (int -> int -> int) -> int -> int -> int = 
fn g:int->int->int, b:int, k:int => {
    if (k == 0) { b }
    else {
         g (k) (reduce (g) (b) (k-1) )
      }
  };
let fact = reduce (fn n:int, p:int => { n*p }) (1) ;
fact (10)
;;

// 5051
let reduce: (int -> int -> int) -> int -> int -> int = 
fn g:int->int->int, b:int, k:int  => {
    if (k == 0) { b }
    else {
         g (k) (reduce (g) (b) (k-1) )
      }
  };
let tri = reduce (fn n:int, p:int => { n+p }) (1) ;
tri (100)
;;

// prints all intermediate sums
let reduce: (int -> int -> int) -> int -> int -> int = 
fn g:int->int->int, b:int, k:int  => {
    if (k == 0) { b }
    else {
         g (k) (reduce (g) (b) (k-1) )
      }
  };
let tri = reduce (fn n:int, p:int => { println (n+p) }) (1) ;
tri (100)
;;

// 1
let k = -1;
let om: (int -> int)->int = fn f:int->int => { f (k)};
om (fn k:int => {k*k})
;;


