
/* tests for L1 step 1 */

// 2
let x = 1; 
(x + 1);;

// 10
(let x = 1; (x + 1)) * (let x = 2; (x + 3));;

// 3
let x = 1;
let y = 2; 
(x + y);;

// 72
let x = 2;
let z = x+2;
let k = (let x = z+2; x*x);
k+k;;

// true
let y = 1;
let b = (y > 0) && (y <= 20);
let z = (let z = 2*y;  z*z);
b || ~ (z < 0);;

/* tests for L1 step 2 */

// 4
let x = 1;
let f = fn y:int => { x + y };
(x + f (2));;

// 5
let x=1;
let f = fn y:int => { y+x };
let g = fn x:int => { x+f(x) };
g(2);;

// 14
let f = fn g:int->int,z:int => { g (z) };
f (fn z:int => { z*2 }) (7);;

// 49
(fn z:int => { fn t:int => { z*t }}) (7) (7);;


// 49
(fn z:int,t:int => { z*t }) (7) (7);;

// 14
let x=1;
let f = fn y:int => { let k = x*2; y+x*k };
let g = fn x:int, u:int->int => { u(x) + f(x) };
g (f(3)) (f);;

// 199
let comp = fn f:int->int, g:int->int => { fn x:int => { f (g (x)) }};
let inc = fn x:int => { x + 1};
let dup = fn x:int => { 2 * x};
let c2 = comp (inc) (dup);
c2 (99);;

// 6
let f = fn l:list<int> => {match l { nil -> 0 | h::t -> h + f(t)} }; f(1::2::3::nil);;

let fibo = fn a, b => { a:?fibo(b)(a+b) };
let fibogen = fibo(0)(1);
let count = box(41);
let lv = box(fibogen);
while *count ~= 0 {
    match *lv {
        nil => println(0)
        | v::tail => println(v); lv := tail
    };
    count := *count - 1
};;