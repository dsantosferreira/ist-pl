// Tests with static lists
// 1::2::3::4::nil
let concat:list<int> -> list<int> -> list<int> = fn l:list<int>, r:list<int> => {
    match l {
        nil -> r |
        x::t -> x::(concat (t) (r))
    }
};
concat (1::2::nil) (3::4::nil);;

// 3::2::1::nil
let concat:list<int> -> list<int> -> list<int> = fn l:list<int>, r:list<int> => {
    match l {
        nil -> r |
        x::t -> x::(concat (t) (r))
    }
};
let reverse:list<int> -> list<int> = fn l:list<int> => {
    match l {
        nil -> nil |
        x::t -> concat (reverse (t)) (x::nil)
    }
};
reverse (1::2::3::nil);;

// 15
let reduce:list<int> -> (int -> int -> int) -> int = fn l:list<int>, op:int->int->int => {
    match l {
        nil -> 0
        |
        h::t -> op (h) (reduce (t) (op))
    }
};
let mkl:int -> list<int> = fn n:int =>
{
    if (n==0) {
        nil
    } else {
        n::( mkl(n-1))
    }
};
reduce ( mkl (5) ) ((fn x:int, y:int => {x + y})) ;;

// 5
let reduce:list<int> -> (int -> int -> int) -> int = fn l:list<int>, op:int -> int -> int => {
    match l {
        nil -> 0
        |
        h::t -> op (h) (reduce (t) (op))
    }
};
let mkl:int -> list<int> = fn n:int =>
{
    if (n==0) {
        nil
    } else {
        n::( mkl(n-1))
    }
};
reduce ( mkl (5) ) ((fn x:int, y:int => {if x >= y {x} else {y}}));;

// 19
let sum:list<int> -> int = fn l:list<int> => {
    match l {
        nil -> 0 |
        x::t -> x + sum (t)
    }
};
let sublistsum:list<list<int>> -> int = fn l:list<list<int>> => {
    match l {
        nil -> 0 |
        x::t -> sum(x) + sublistsum(t)
    }
};
sublistsum((1::2::3::nil)::(1::2::3::nil)::(1::2::4::nil)::nil);;

// Dynamic typing tests

// Variable already bound
let a = 1; let a = 2; a;;

// Usage of undeclared variable
let a = 1; b;;

// Usage of variable out of scope
let f = fn x:int => {
    let a = x;
    a
};
f(3);
a;;

// List operator is more binding than arithmetic/logical operators. Gives error because tries to sum 1::2 with 3
let a = 1::2+3; a;;

// Expected two boolean values in AND operation
let v = 1 && 1; v;;
let v = true && 2; v;;

// Expected two boolean values in OR operation
let v = 1 || 1; v;;
let v = true || 2; v;;

// Expected boolean value in negation operation
let v = ~1; v;;

// Expected reference in left hand side of assignment operation
let a = box(0); !a := 1;;
let a = box(0); 1 := 1;;

// Expected reference in dereference operation
let a = 0; !a;;

// Expected either two integers or two boolean values in ~= operation
let a = 1 != true; a;;
let a = true != 1; a;;
let a = 1::nil != true; a;;

// Expected two integers in division operation
let a = 1 / true; a;;
let a = true / 1; a;;

// Expected two integers in multiplication operation
let a = 1 * true; a;;
let a = true * 1; a;;

// Expected two integers in addition operation
let a = 1 + true; a;;
let a = true + 1; a;;

// Expected two integers in subtraction operation
let a = 1 - true; a;;
let a = true - 1; a;;

// Expected either two integers or two booleans in equality operation
let a = 1 == true; a;;
let a = true == 1; a;;
let a = 1::nil == true; a;;

// Expected closure in left hand side of application
let a = 1; a(1);;

// Expected two integers in >= operation
let a = 1 >= true; a;;
let a = true >= 1; a;;

// Expected two integers in > operation
let a = 1 > true; a;;
let a = true > 1; a;;

// Expected two integers in < operation
let a = 1 < true; a;;
let a = true < 1; a;;

// Expected two integers in <= operation
let a = 1 <= true; a;;
let a = true <= 1; a;;

// Expected boolean value in if statement condition
let a = 1; if a {1} else {2};;

// Expected boolean value in while statement condition
let a = 1; while a {1};;

// Expected either a list or nil in match operator
let a = 1; match a {nil -> nil | x::t -> x};;

// More type checking tests

// Branches must have a common super type
// Fails
if true {1} else {false};;

// Succeeds
if true {1} else {1};;

// Succeeds
if true {{#x=1, #y=2}} else {{#x = 13}};;

// Fails
if true {{#x=1, #y=2}} else {{#x = true}};;

// Succeeds
type BigStruct = struct {#x:int, #y:int, #z:int};
type SmallStruct = struct {#x:int, #y:int};
(
let f = fn t:bool => {
    if t {{#x = 1, #y = 2, #z = 3}} else {{#x = 1, #y = 2}}
};

let a:SmallStruct = f(false);
a
);;

// Succeeds
type BigStruct = struct {#x:int, #y:int, #z:int};
type SmallStruct = struct {#x:int, #y:int};
(
let f = fn t:bool => {
if t {{#x = 1, #y = 2, #z = 3}} else {{#x = 1, #y = 2}}
};

let a:SmallStruct = f(true);
a
);;

// Fails
type BigStruct = struct {#x:int, #y:int, #z:int};
type SmallStruct = struct {#x:int, #y:int};
(
let f = fn t:bool => {
if t {{#x = 1, #y = 2, #z = 3}} else {{#x = 1, #y = 2}}
};

let a:BigStruct = f(true);
a
);;


// Union Match branches must have common super type

