open List
open Sets

(*********)
(* Types *)
(*********)

type ('q, 's) transition = 'q * 's option * 'q
type ('q, 's) nfa_t = {
  qs : 'q list;
  sigma : 's list;
  delta : ('q, 's) transition list;
  q0 : 'q;
  fs : 'q list;
}

(***********)
(* Utility *)
(***********)

let explode s =
  let rec exp i l =
    if i < 0 then l else exp (i - 1) (s.[i] :: l) in
exp (String.length s - 1) []

(****************)
(* Part 1: NFAs *)
(****************)
(* fold_left -> func acc list *)
let rec move_2 delta hd s =
List.fold_left (fun a (entry, trans, finish) ->
if (entry = hd && trans = s) then Sets.union [finish] a else a) [] delta;;

let rec move (m: ('q, 's) nfa_t) (qs: 'q list) (s: 's option): ('q list) =
match qs with (*recursion for list*)
| [] -> []
| h::t -> Sets.union (move_2 m.delta h s) (move m t s);;

let rec e_closure (m:('q, 's) nfa_t) (qs: 'q list): ('q list) =
let epsilon = Sets.union (move m qs None) qs in
if (eq epsilon qs) then epsilon else (e_closure m epsilon);;

let accept (m: ('q, char) nfa_t) (str: string): (bool) =
let rec accept m stat str =
match str with
| [] ->
if (Sets.intersection (e_closure m stat) m.fs != []) then true else false
| h::t -> if (eq stat []) then false else
accept m (e_closure m (move m stat (Some h))) t in
accept m (e_closure m [m.q0]) (explode str);;

(*******************************)
(* Part 2: Subset Construction *)
(*******************************)

let new_states (m:('q, 's) nfa_t) (qs: 'q list) =
List.map (fun h -> e_closure m (move m qs (Some h))) m.sigma;;

let new_trans (m: ('q, 's) nfa_t) (qs: 'q list) =
List.fold_left (fun acc h ->
(qs, Some h, (e_closure m (move m qs (Some h))))::acc) [] m.sigma;;

let new_finals (m:('q, 's) nfa_t) (qs: 'q list) =
List.fold_left (fun acc h -> if Sets.elem h m.fs then [qs] else acc) [] qs;;

(*('q, 's) nfa_t -> ('q list, 's) nfa_t -> 'q list list -> ('q list, 's) nfa_t*)
let rec nfa_to_dfa_step m dfa wrk =
match wrk with
| [] -> dfa
| h::t -> let new_list = new_states m h in

List.fold_left(fun acc hd ->
let acc =
{qs = Sets.union acc.qs [h];
sigma = m.sigma;
delta = Sets.union acc.delta (new_trans m h);
q0 = e_closure m [m.q0];
fs = Sets.union acc.fs (new_finals m h)} in
if elem (e_closure m hd) acc.qs then acc
else nfa_to_dfa_step m acc [e_closure m hd]) dfa new_list;;

let nfa_to_dfa (m: ('q, 's) nfa_t): (('q list, 's) nfa_t) =
let dfa =
{ qs = [];
  sigma = m.sigma;
  delta = [];
  q0 = e_closure m [m.q0];
  fs = []
} in
nfa_to_dfa_step m dfa [e_closure m [m.q0]];;
