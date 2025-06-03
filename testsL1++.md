// Strict Lists

// 1\n2::nil\n2::nil
let l = 1::2::nil;
match l {
    nil -> println(nil)
|   x::y -> println(x); println(y)
};;

// 3
let len = fn l:list<int> =>
    {   
        match l {
        nil -> 0
      | x::y -> 1 + len (y)
    }};
let l0 = 1::2::3::nil;
len (l0);;

// 100:99:88...::1::nil\n(prints intermédios)\n100
let mkl = fn n =>
{
    if (n==0) {
        nil
    } else {
        n::( mkl(n-1))
    }
};
let len = fn l =>
    {   println (l);
        match l {
        nil -> 0
      | x::y -> 1 + len (y)
    }};
let l100 = mkl (100);
println l100;
len(l100)
;;

// (print intermédios)\n 5050
let mkl = fn n =>
{
    if (n==0) {
        nil
    } else {
        n::( mkl(n-1))
    }
};
let suml = fn l =>
    {   
        match l {
        nil -> 0
      | x::y -> x + suml (y)
    }};
let l100 = mkl (100);
println l100;
suml(l100)
;;

// Lazy Lists

// 1\nlazylist@(...)\nlazylist@(...)
let l = 1:?2:?nil;
match l {
    nil -> println(nil)
|   x::y -> println(x); println(y)
};;

// 1\n2::nil\n2::nil
let l = 1:?2::nil;
match l {
    nil -> println(nil)
|   x::y -> println(x); println(y)
};;

// 11::10::(...)::3::2::0
let add1 = fn l => {
    match l {
        nil -> 0
        |
        h::t -> (h+1)::(add1 (t))
    }
};
let mkl = fn n =>
{
    if (n==0) {
        nil
    } else {
        n::( mkl(n-1))
    }
};
add1 ( mkl (10) ) ;;

// Mesma coisa que o de cima mas começa em 101
let add1 = fn l => {
    match l {
        nil -> 0
        |
        h::t -> (h+1)::(add1 (t))
    }
};
let mkll = fn n =>
{
    if (n==0) {
        nil
    } else {
        n:?( mkll(n-1))
    }
};
let ll100 = mkll (100);
add1 ( ll100 ) ;;

// 0\n1\n2(...)19\n0\n0
let intsfm = fn n => {
    n:? (intsfm (n+1))
};
let pfst = fn l,n => {
    if (n==0) {
        println(n)
    } else {
        match l {
            nil -> 0
            |
            h::t -> println(h); pfst (t) (n-1)
        }
    }
};
let l = intsfm (0);
pfst (l) (20);;

// 514229
let fibo = fn a, b => { a :? (fibo (b) (a+b)) };
let fibogen = fibo (0) (1);
let count = box ( 30 ) ;
let lv = box( fibogen );
while (!count ~= 0) {
     match (!lv) {
        nil -> println (0)
      | v :: tail -> println (v); lv := tail
     };
     count := !count - 1
};;

// Dá print dos primeiros 100 números pares
let intsfm = fn n => {
    n:? (intsfm (n+1))
};
let filter = fn l, g => {
    match l {
        nil -> nil
    |
        h::t -> if (g(h)) {
            h :? (filter (t) (g))
        } else { filter (t) (g)}
    }
};
let pfst = fn l,n => {
    if (n==0) {
        println(n)
    } else {
        match l {
            nil -> 0
            |
            h::t -> println(h); pfst (t) (n-1)
        }
    }
};
let l = intsfm (0);
let even = fn n => { (n/2)*2 == n };
let m = filter (l) (even);
pfst (m) (100);;
