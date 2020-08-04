open Funs

(***********************)
(* Part 2: Integer BST *)
(***********************)

type int_tree =
  | IntLeaf
  | IntNode of int * int_tree * int_tree

let empty_int_tree = IntLeaf

let rec int_insert x t =
  match t with
  | IntLeaf -> IntNode(x, IntLeaf, IntLeaf)
  | IntNode (y, l, r) when x > y -> IntNode (y, l, int_insert x r)
  | IntNode (y, l, r) when x = y -> t
  | IntNode (y, l, r) -> IntNode (y, int_insert x l, r)

let rec int_mem x t =
  match t with
  | IntLeaf -> false
  | IntNode (y, l, r) when x > y -> int_mem x r
  | IntNode (y, l, r) when x = y -> true
  | IntNode (y, l, r) -> int_mem x l

(* Implement the functions below. *)
(* int_tree -> int*)
let rec int_size t =
match t with
| IntLeaf -> 0
| IntNode (n, l ,r) -> 1 + int_size l + int_size r;;

(* int_tree -> int*)
let rec int_max t =
match t with
| IntLeaf -> invalid_arg "int_max"
| IntNode (n, l, r) when r = IntLeaf -> n
| IntNode (n, l, r) -> int_max r

(*int_tree -> int -> int -> int*)
let rec int_common t x y =
match t with
| IntLeaf -> invalid_arg "int_common"
| IntNode (v, l, r)
when int_mem y t = false || int_mem x t = false -> invalid_arg "int_common"
| IntNode (v, l, r) when v > y && v > x-> int_common l x y
| IntNode (v, l, r) when v < y && v < x-> int_common r x y
| IntNode (v, l, r) -> v;;

let rec int_insert_all lst t =
fold(fun t h -> int_insert h t) t lst;;

(***************************)
(* Part 3: Polymorphic BST *)
(***************************)

let int_comp x y = if x < y then -1 else if x > y then 1 else 0;;

type 'a atree =
    Leaf
  | Node of 'a * 'a atree * 'a atree
type 'a compfn = 'a -> 'a -> int
type 'a ptree = 'a compfn * 'a atree

let empty_ptree f : 'a ptree = (f,Leaf)

(* Implement the functions below. *)

(*'a -> 'a ptree -> 'a ptree*)
let pinsert x t =
match t with
(f, tree) -> let rec insert_BST x tree =
match tree with
Leaf -> Node(x, Leaf, Leaf)
| Node (y, l, r) when (f x y) > 0 -> Node(y, l, insert_BST x r)
| Node (y, l, r) when (f x y) = 0 -> tree
| Node (y, l, r) -> Node(y, insert_BST x l, r) in
(f, insert_BST x tree);;

(*'a -> 'a ptree -> bool*)
let pmem x t =
match t with
(f, tree) -> let rec pmem_BST x tree =
match tree with
| Leaf -> false
| Node (y, l, r) when (f x y) > 0 -> pmem_BST x r
| Node (y, l, r) when (f x y) = 0 -> true
| Node (y, l, r) -> pmem_BST x l in
pmem_BST x tree;;

(*'a list -> 'a ptree -> 'a ptree*)
let pinsert_all lst t =
fold(fun t h -> pinsert h t) t lst;;

(*'a ptree -> 'a list*)
let rec p_as_list t =
match t with
(f, tree) -> let rec p_as_list tree =
match tree with
| Leaf -> []
| Node (y, l, r) -> (p_as_list l)@(y::p_as_list r) in
p_as_list tree;;

(*('a -> 'a) -> 'a ptree -> 'a ptree*)
let pmap f t =
match t with
 (fn, tree) ->
let lst2 = map f (p_as_list t) in
pinsert_all lst2 (fn, Leaf);;

(*******************************)
(* Part 4: Shapes with Records *)
(*******************************)

type pt = { x: int; y: int }
type shape =
    Circ of { radius: float; center: pt }
  | Rect of { width: float; height: float; upper: pt }

(*shape -> float*)
let area s =
match s with
| Circ c -> c.radius *. c.radius *. 3.14
| Rect r -> r.width *. r.height;;

(*(shape -> bool) -> shape list -> shape list*)
let filter f lst =
fold (fun acc hd -> if (f hd) = true then hd::acc else acc) [] lst;;

(*float -> shape list -> (shape list * shape list)*)
let partition thresh lst =
let split sp (front, back) =
if thresh > (area sp) then (sp::front, back) else (front, sp::back) in
fold_right split lst ([],[]);;

(*shape list -> shape list*)
let rec qs lst =
match lst with
| [] -> []
| pivot::rest ->
let left, right = partition (area pivot) rest in
qs left@(pivot::qs right);;