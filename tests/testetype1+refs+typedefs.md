let x = 1;
let y = true;
(x+2*x);;

let x = 1;
let y = true;
(x>3 || y);;

/* fails */
let x = 1;
let y = true;
(x>3 && x>y);;

let x = 1;
let y = (x > 2);
(x>3 && y);;

let x = 1;
let y = (x > 2);
(x>5/2 && y);;

let x = 1;
let y = (x > 2);
(2*x>5/2 && y);;

/* fails */
let x = 1;
let y = (x > 2);
-(2*x>5/2 && y);;


let x = 1;
let y = (x > 2);
~(2*x>5/2 && y);;

let x = 1;
let y = (x > 2);
if (y) { 1 } else { 2 };;

/* fails */
let x = 1;
let y = (x > 2);
if (y) { 1 } else { x > 1 };;

let x = 1;
let y = (x > 2);
if (y) { x != 1 } else { y };;

(let x = 1; (x + 1)) * (let x = 2; (x + 3));;

let x = 2;
let z = x+2;
let k = (let x = z+2; x*x);
k+k;;

let y = 1;
let b = (y > 0) && (y <= 20);
let z = (let z = 2*y;  z*z);
b || ~ (z < 0)
;;

(fn z:int => { fn t:int => { z*t }}) (7) (7);;

(fn z:int,t:int => { z*t }) (7) (7);;

let x = 1;
let f = fn y:int => { x + y };
(
let x = 4;
(x + f (2))
)
;;

let x=1 ;			 
let f = fn y:int => { y+x } ;
let g = fn x:int => { x+f(x) };
g(2) 
;;

/* fails */
let f = fn g:int->bool,z:int => { g (z) };
f (fn z:int => { z*2 }) (7)
;;

let f = fn g:int->int,z:int => { g (z) };
f (fn z:int => { z*2 }) (7)
;;

let x:int = 2; x;;

/* fails */
let x:bool = 2; x;;

let x=1 ;			 
let f = fn y:int => {
           let k = x*2;
  	   y+x*k
        };
let g = fn x:int, u:int->int =>
           { u(x) + f(x) };
g  (f(3)) (f) 
;;

let comp = fn f:int->int, g:int->int => 
        { fn x:int =>
        { f (g (x)) }};
let inc = fn x:int => { x + 1};
let dup = fn x:int => { 2 * x};
let c2 = comp (inc) (dup);
c2 (99)
;;

let fact:int->int =
    fn n:int => {
        if (n==0) { 1 }
        else { n * (fact (n-1))}
    };
fact (5);;


let fact:int->int = (
    let one = 1;
    fn n:int => {
        if (n==0) { one }
        else { n * (fact (n-1))}
    });
fact (5);;

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

let k = -1;
let om: (int -> int)->int = fn f:int->int => { f (k)};
om (fn k:int => {k*k})
;;

/* type defs */

type int2op = (int -> int -> int) ;
type redtype = int2op -> int -> int -> int;
(
let reduce: redtype =
fn g:int2op, b:int, k:int  => {
if (k == 0) { b }
else {
g (k) (reduce (g) (b) (k-1) )
}
};
let tri = reduce (fn n:int, p:int => { println (n+p) }) (1) ;
tri (100)
);;


let k = -1;
let om: (int -> int)->int = fn f:int->int => { f (k)};
om (fn k:int => {k*k})
;;


/* simple imperative programs */
let c = 0;
let L = 1000;
let m = box(L);
let S = box(c);
(
while (!m>0) {
    m := !m - 1;
    S := !S + !m
};
!S
)
;;

/* higher-order store */
type i2i = int->int;
(
let sigfpe:ref<int->int> = box ( fn x:int => {x} );
let setfpe = fn h:int->int => { sigfpe := h };
let div = fn n:int,m:int => {
if (m==0) { (!sigfpe) (n) }
else { n / m}
};
setfpe (fn v:int => { -1 });
div (2) (0)
)
;;

/* higher-order store */
type i2i = int->int;
type refi2i = ref<i2i>;
(
let sigfpe:refi2i = box ( fn x:int => {x} );
let setfpe = fn h:i2i => { sigfpe := h };
let div = fn n:int,m:int => {
if (m==0) { (!sigfpe) (n) }
else { n / m}
};
setfpe (fn v:int => { -1 });
div (2) (0)
)
;;


/* landin's knot, encoding recursion through state */
let knot = box (fn x:int => {x});
let fact = fn n:int => {
      if (n==0) { 1}
        else { n * ((!knot)( n - 1 ))}};
knot := fact;
fact (6)
;;


type money = int;
(
let x:money = 2;
x+1
);;


/* landin's knot, encoding recursion through state */

type knott = ref<int->int>;
(
let knot:knott = box (fn x:int => {x});
let fact = fn n:int => {
if (n==0) { 1}
else { n * ((!knot)( n - 1 ))}};
knot := fact;
fact (6)
)
;;

/* some functions to encode mutable pairs */
/* now w / type defs */

type Iaccessor = ref<int> -> ref<int> -> int;
type Igettype = (Iaccessor -> int)->int;
type Isettype = (Iaccessor -> int) -> int -> int;

(
let mkpair =
fn a:int,b:int => {
let l = box(a);
let r = box(b);
fn f:Iaccessor => { f (l) (r) }
};
let getfst:Igettype =
fn p:Iaccessor->int =>
{ p (fn a:ref<int>,b:ref<int> => { !a }) };

let getsnd:Igettype =
fn p:Iaccessor->int =>
{ p (fn a:ref<int>,b:ref<int> => { !b })};

let setfst:Isettype  =
fn p:Iaccessor->int,v:int =>
{ p (fn a:ref<int>,b:ref<int> => { a := v })};

let setsnd:Isettype =
fn p:Iaccessor->int,v:int =>
{ p (fn a:ref<int>,b:ref<int> => { b := v })};

let x = mkpair (1) (2);
setfst (x) (10);
setsnd (x) (20);
(getfst (x)) + (getsnd (x))
)
;;


let inc = fn r:ref<int>,z:int->int => { r := z(!r + 1); !r};
let age = box (1);
inc (age) (fn x:int => { x + 1 })
;;
