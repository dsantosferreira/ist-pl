
/* tests for L1 step 1 */

// 2
let x = 1; (x + 1);;

// 10
(let x = 1; (x + 1)) * (let x = 2; (x + 3));;

// 3
let x = 1; let y = 2; (x + y);;

// 72
let x = 2; let z = x+2; let k = (let x = z+2; x*x); k+k;;

// true
let y = 1; let b = (y > 0) && (y <= 20); let z = (let z = 2*y;  z*z); b || ~ (z < 0);;

/* tests for L1 step 2 */

// 4
let x = 1; let f = fn y => { x + y }; (x + f (2));;

// 5
let x=1 ; let f = fn y => { y+x } ; let g = fn x => { x+f(x) }; g(2);;

// 14
let f = fn g,z => { g (z) }; f (fn z => { z*2 }) (7);;

// 49
(fn z => { fn t => { z*t }}) (7) (7);;


// 49
(fn z,t => { z*t }) (7) (7);;

// 14
let x=1 ; let f = fn y => { let k = x*2; y+x*k }; let g = fn x, u => { u(x) + f(x) }; g (f(3)) (f);;

// 199
let comp = fn f, f => { fn x => { f (g (x)) }}; let inc = fn x => { x + 1}; let dup = fn x => { 2 * x}; let c2 = comp (inc) (dup); c2 (99);;

// 2
let a = 1; let b = 2; a; b;

// 0
let a = 1; let b = 2; if a <= b {0} else {1};;

// 1
let a = 1; let b = 2; if a > b {0} else {1};;

// 2
let f = fn x, y => {if (x == 1) {f(2)(2)} else {0}; x + y}; f(1)(1);;

// false
let a = false; while a {0}; a;;

// 5\n4\n3\n2\n1\n0
let a = box(5); while *a ~= 0 {println(*a); a := *a - 1}; *a;;

// true1
let a = true; let b = 1; print(a); b;;

// true\n1
let a = true; let b = 1; println(a); b;;

// 6
let fact = fn n => { let c = box (n); let f = box (1); while (*c > 0) { f := *f * *c; c := *c - 1 }; *f }; fact (3);;

// 3
let c = box(0); let tick = fn inc => { let v = *c; c := *c + inc; v }; tick(1); tick(1); tick(1); tick(1);;

// 5
let a = box(0); let f = fn b => {let c = b; a := c}; f(5); *a;;

// 5
let a = box(0); let f = fn b => {let c = box(b); a := c}; f(5); **a;;

// 10
let a = box(0); let f = fn b => {let c = box(b); a := c}; f(5); *a := 10; **a;;

// 6
let f = fn a, b, c => {a + b + c}; let g = f(1); let h = g(2); h(3);;