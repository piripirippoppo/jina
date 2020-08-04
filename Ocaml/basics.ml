(******************************)
(* Part 1: Non-List Functions *)
(******************************)

let pyth a b c =
if a > 0 && b > 0 && c > 0 then
begin
if (a*a) + (b*b) = (c*c) then true else false
end
else false;;

let rec gcd a b =
if a = 0 && b = 0 then 0
else if a = 0 then b
else if b = 0 then a
else
if a >= b then gcd b (a mod b)
else gcd a (b mod a);;

let reduced_form numer denom =
(numer / (gcd numer denom), denom / (gcd numer denom));;

let rec cubes n = if n <= 0 then 0 else n * n * n + cubes (n - 1);;

let rec ack m n =
if m = 0 then n + 1
else if m > 0 && n = 0 then (ack (m - 1) 1)
else (ack (m - 1) (ack m (n - 1)));;

(*********************************)
(* Part 2: List Functions *)
(*********************************)

let max_first_three lst =
match lst with
| [] -> -1
| [h] -> h
| h::h1::h2::_ -> max (max h h1) h2
| h::h1::[] -> max h h1;;

(*'a list -> 'a -> int*)
let count_occ lst target =
let rec count_occ_aux lst target count =
match lst with
| [] -> 0
| h::t ->
if h = target then count_occ_aux t target count + 1
else count_occ_aux t target count
in count_occ_aux lst target 0;;

(*int list -> int list*)
let uniq lst =
let rec uniq_aux x xlst = (*aux fun to detect duplicate*)
match xlst with (*if true/false case *)
| [] -> true
| h::t ->
if h = x then false (*duplicate found*)
else uniq_aux x t (*no duplicate*)
in (*from uniq_aux*)

let rec iter add_lst = (*this function is for add/skip*)
match add_lst with
| [] -> []
| h::t ->
let left_list = iter t in
if uniq_aux h left_list then h::left_list else left_list
in (*from iter*)
iter lst;;

(*int list -> (int * int) list*)
let rec assoc_list_aux x a =
match a with
| [] -> 0
| h::t ->
if h = x then 1 + assoc_list_aux x t else assoc_list_aux x t;;

(*'a -> 'a list -> 'a list*)
let rec remove x a =
match a with
| [] -> []
| h::t -> if h = x then remove x t else h::remove x t;;

let rec assoc_list lst =
match lst with
| [] -> []
| h::t -> (h, (assoc_list_aux h lst))::assoc_list (remove h t);;

let rec zip lst1 lst2 =
match lst1 with
| [] -> []
| h1::t1 -> (
match lst2 with
| [] -> []
| h2::t2 -> (h1,h2)::(zip t1 t2));;

(****************)
(* Part 3: Sets *)
(****************)

let rec elem x a =
match a with
| [] -> false
| h::t -> if h = x then true else elem x t;;

let rec insert x a =
match a with
| [] -> x :: []
| h::t -> if h = x then h::t else h::insert x t;;

(*'a list -> 'a list -> bool*)
let rec eq a b =
let len1 = List.length a in
let len2 = List.length b in
match a, b with
| [], [] -> true
| [], _
| _, [] -> false
| h1::t1, _ ->
if len1 = len2 then
elem h1 b else false;;

(*'a list -> 'a list -> 'a list*)
let rec union a b =
match a with
| [] -> b
| h::t -> if elem h b then union t b else union t (h::b);;

(*'a list -> 'a list -> 'a list*)
let rec intersection a b =
match a with
| [] -> []
| h::t -> if (elem h b) then (h::(intersection t b)) else (intersection t b);;

(*'a list -> 'a list -> bool*)
let rec subset a b =
if intersection a b = a && union a b = b then true else false;;

let rec append a b =
match a with
| [] -> b
| h::t -> insert h (append t b);;

(*'a list -> 'b list -> ('a * 'b) list*)
let rec product_help x b =
match b with
| [] -> []
| h::t -> (x, h)::(product_help x t);;

let rec product a b =
match a with
| [] -> []
| h::t -> append (product_help h b) (product t b);;
