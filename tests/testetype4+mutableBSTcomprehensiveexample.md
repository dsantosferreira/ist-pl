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
let reduce: redtype = 
fn g:int2op, b:int, k:int  => {
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
let sigfpe:ref<int->int> = box ( fn x:int => {x} );
let setfpe = fn h:int->int => { sigfpe := h };
let div = fn n:int,m:int => {
      if (m==0) { (!sigfpe) (n) }
        else { n / m}
};
setfpe (fn v:int => { -1 });
div (2) (0)
;;

/* higher-order store */
type i2i = int->int;
type refi2i = ref<i2i>;
let sigfpe:refi2i = box ( fn x:int => {x} );
let setfpe = fn h:i2i => { sigfpe := h };
let div = fn n:int,m:int => {
      if (m==0) { (!sigfpe) (n) }
        else { n / m}
};
setfpe (fn v:int => { -1 });
div (2) (0)
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
let x:money = 2;
x+1;;

/* landin's knot, encoding recursion through state */

type knott = ref<int->int>;
let knot:knott = box (fn x:int => {x});
let fact = fn n:int => {
      if (n==0) { 1}
        else { n * ((!knot)( n - 1 ))}};
knot := fact;
fact (6)
;;

/* some functions to encode mutable pairs */
/* now w / type defs */

type Iaccessor = ref<int> -> ref<int> -> int;
type Igettype = (Iaccessor -> int)->int;
type Isettype = (Iaccessor -> int) -> int -> int;

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
;;

let inc = fn r:ref<int>,z:int->int => { r := z(!r + 1); !r};
let age = box (1);
inc (age) (fn x:int => { x + 1 })
;;

/* struct(s) */

{};;

{ #fst=1, #snd = 2};;

{ #fst=1, #snd = 2}.#fst;;

{ #fst=1, #snd = 2}.#snd;;

/* fails */
{ #fst=1, #snd = 2}.#foo;;

type Person = struct{#name:int, #age: int};
let p0 = {#name = 1, #age = 2};
p0.#name + p0.#age;;

type Person = struct{ #name:int, #age: int };
let p0:Person = {#name = 1, #age = 2};
p0.#name + p0.#age;;

/* subtyping -- succeeds */
type Person = struct{ #name:int, #age: int };
let p0:Person = {#name = 1, #age = 2, #more = 42};
p0.#name + p0.#age;;

/* subtyping -- fails */
type Person = struct{ #name:int, #age: int };
let p0:Person = {#name = 1};
p0.#name + p0.#age;;

/* mutable structs, redux */

type MPair = struct { #fst: ref<int>, #snd:ref<int>};
let p1 = { #fst = box(1), #snd = box(2)};
p1.#fst := !(p1.#snd);
p1.#snd := !(p1.#snd) + 1;
!(p1.#fst) + !(p1.#snd)
;;

();;

/* object encodings, closure encapsulated state and interface types */

type ICounter = struct { #inc: () -> int, #get : () -> int };
let c:int -> ICounter =
    fn n:int => { let v = box(n);
                { #inc = fn _:() => { v := !v + 1 }, 
                  #get = fn _:() => { !v } }};
let cv = c(0);
cv.#inc(());
cv.#inc(());
cv.#get(());;

/* syntactic sugar for M (()) on function application, M () expands to M (()) */

type ICounter = struct { #inc: () -> int, #get : () -> int };
let newcounter:int -> ICounter =
    fn n:int =>
        { let v = box(n);
            { 
                #inc = fn _:() => { v := !v + 1 }, 
                #get = fn _:() => { !v }
            }
        };
let c0 = newcounter(0);
    c0.#inc(); 
    c0.#inc();
    c0.#get()
;;

/* subtyping */
type ICounter = struct { #inc: () -> int, #get : () -> int };
type IIncView = struct { #inc: () -> int };
let newcounter:int -> ICounter =
    fn n:int =>
        { let v = box(n);
            { 
                #inc = fn _:() => { v := !v + 1 }, 
                #get = fn _:() => { !v }
            }
        };
let c0 = newcounter(0);
let client = fn r: IIncView  =>
            { r.#inc(); r.#inc(); r.#inc() };
    client (c0);
    c0.#get()
;;

/* union types */

type Opt = union { #none:(), #some:int};
let l0:Opt = #none(());
l0;;

type Opt = union { #none:(), #some:int};
let l0:Opt = #some(2);
l0;;

type Opt = union { #none:(), #some:int};
let l0:Opt = #none(());
match l0 {
    #none(_) -> 1
|   #some(i) -> i    
};;

type Opt = union { #none:(), #some:int};
let l0:Opt = #some(42);
match l0 {
    #none(_) -> 1
|   #some(i) -> i    
};;

type Opt = union { #none:(), #some:int};
let l0:Opt = #some(42);
match l0 {
    #none(_) -> 1
|   #some(i) -> i  
|   #never(_) -> true * false   /* dead code */
};;


/* fails */
type Opt = union { #none:(), #some:int};
let l0:Opt = #some(42);
match l0 {
    #none(_) -> 1
|   #some(i) -> (i && true)  
};;

/* fails */
type Opt = union { #none:(), #some:int};
let l0:Opt = #some(42);
match l0 {
    #none(_) -> 1
|   #some(i) -> (i > 2)  
};;

/* fails */
type Opt = union { #none:(), #some:int};
let l0:Opt = #some(42);
match l0 {
    #none(_) -> 1
};;

/* fails */
type Opt = union { #none:(), #some:int};
let l0:Opt = #none(());
match l0 {
    #some(x) -> x + 1
};;

type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let r1 = #rect({ #h = 10, #v = 20 });
match r1 {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#v
}
;;

/* fails */
type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let r1:Blob = #rect({ #h = 10, #v = 20 });
match r1 {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#v
}
;;

type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3142;
let area = fn b:Blob => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1 = #rect({ #h = 10, #w = 20 });
area(r1);;

type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3142;
let area = fn b:Blob => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1:Blob = #rect({ #h = 10, #w = 20 });
area(r1);;

/* fails */
type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3142;
let area = fn b:union{ #circle: Circle } => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1:Blob = #rect({ #h = 10, #w = 20 });
area(r1);;

/* fails */
type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3142;
let area = fn b:union{ #rect: Rectangle } => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1:Blob = #rect({ #h = 10, #w = 20 });
area(r1);;

type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3142;
let area = fn b:union{ #rect: Rectangle } => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1 = #rect({ #h = 10, #w = 20 });
area(r1);;

type Circle = struct { #cx:int, #cy:int, #rad:int };
type Rectangle = struct { #h:int, #w:int };
type Blob = union { #circle: Circle, #rect: Rectangle };
let pi = 3; /*3.14*/
let area = fn b:Blob => {
  match b {
        #circle(c) -> let r = pi*c.#rad; (r*r)/2
    |   #rect(r) -> r.#h * r.#w
    }
};
let r1 = #circle({ #cx=0, #cy=0, #rad=2 });
area(r1);;

type NODE = struct { #val:int, #next:LIST };
type LIST = union { #nil: (), #cons: NODE };
let l0:LIST = #nil (()) ;
let l1:LIST = #cons ( { #val=2, #next=l0}) ;
let l2:LIST = #cons ( { #val=3, #next=l1}) ;
l1;;

type NODE = struct {
        #val:int,
        #next:LIST
};
type LIST = union {
        #nil: (),
        #cons: NODE
};
let l0:LIST = #nil (()) ;
let l1:LIST = #cons ( { #val=2, #next=l0}) ;
let l2:LIST = #cons ( { #val=3, #next=l1}) ;
let plist:LIST->() = fn
    l:LIST => { 
        match l {
            #nil(_) -> println (-1000); ()
        |   #cons(n) -> 
                print (n.#val);
                plist (n.#next)
        }
    }
;
plist (l2);;

type NODE = struct {
        #val:int,
        #next:LIST
};

type LIST = union {
        #nil: (),
        #cons: NODE
};

let cons: int -> LIST -> LIST =
    fn v:int, l:LIST =>
        {#cons ( {#val=v, #next=l})};

let concat:LIST->LIST->LIST =
    fn a:LIST, b:LIST => { 
        match a {
            #nil(_) -> b
        |   #cons(n) -> 
                cons (n.#val) (concat (n.#next) (b))
        }
    };

let genlist: int -> LIST =
    fn n:int => {
        if (n==0) { #nil(()) }
        else {
            cons (n) (genlist (n-1))
        }
    };

let print_list:LIST->() = fn
    l:LIST => { 
        match l {
            #nil(_) -> ()
        |   #cons(n) -> 
                println (n.#val);
                print_list (n.#next)
        }
    }
;

let l0 = genlist (100);
let la = concat (l0) (l0);
print_list (la);;

/* mutable binary search tree  */

type TNODE = struct {
        #val:int,
        #left:ref<BTREE>,
        #right:ref<BTREE>
};

type BTREE = union {
        #nil: (),
        #node: TNODE
};

type RTREE = ref<BTREE>;

let mknil: BTREE = #nil(());

let mknode:int->BTREE =
    fn n:int => {
        #node({#val = n,
               #left = box(mknil), 
               #right = box(mknil)})
    };

let insert: RTREE->int->() = 
    fn t:RTREE, n:int =>
    {
        match !t {
            #nil(_) -> 
                t := mknode (n); ()
        |   #node(c) ->
            if (n<c.#val)
                { insert (c.#left) (n) }
                else { insert (c.#right) (n) }
        }
    };

let inorder: RTREE->() = 
    fn t:RTREE =>
    {
        match !t {
            #nil(_) ->  ()
        |   #node(c) ->
            inorder (c.#left);
            println(c.#val);
            inorder (c.#right)
        }
    };

let tree:RTREE = box(mknil);
insert (tree) (5);
insert (tree) (3);
insert (tree) (10);
insert (tree) (8);
insert (tree) (7);
insert (tree) (2);
insert (tree) (9);
inorder (tree)
;;

/* mutable binary search tree with iterator */

type TNODE = struct {
        #val:int,
        #left:ref<BTREE>,
        #right:ref<BTREE>
};

type BTREE = union {
        #nil: (),
        #node: TNODE
};

type RTREE = ref<BTREE>;

let mknil: BTREE = #nil(());

let mknode:int->BTREE =
    fn n:int => {
        #node({#val = n,
               #left = box(mknil), 
               #right = box(mknil)})
    };

let insert: RTREE->int->() = 
    fn t:RTREE, n:int =>
    {
        match !t {
            #nil(_) -> 
                t := mknode (n); ()
        |   #node(c) ->
            if (n<c.#val)
                { insert (c.#left) (n) }
                else { insert (c.#right) (n) }
        }
    };

let treeiterator: RTREE->(int->())->() = 
    fn t:RTREE,f:int->() =>
    {
        match !t {
            #nil(_) ->  ()
        |   #node(c) ->
            treeiterator (c.#left) (f);
            f (c.#val);
            treeiterator (c.#right) (f)
        }
    };

let tree:RTREE = box(mknil);
insert (tree) (5);
insert (tree) (3);
insert (tree) (10);
insert (tree) (8);
insert (tree) (7);
insert (tree) (2);
insert (tree) (9);
treeiterator (tree) (fn v:int => { println(v); ()} )
;;








