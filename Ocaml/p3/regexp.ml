open List
open Nfa

(*********)
(* Types *)
(*********)

type regexp_t =
  | Empty_String
  | Char of char
  | Union of regexp_t * regexp_t
  | Concat of regexp_t * regexp_t
  | Star of regexp_t

(***********)
(* Utility *)
(***********)

let fresh =
  let cntr = ref 0 in
  fun () -> cntr := !cntr + 1 ; !cntr

(*******************************)
(* Part 3: Regular Expressions *)
(*******************************)
(*regexp_t -> nfa_t*)
let regexp_to_nfa re =
let rec helper re n =
let x = fresh() in
let y = fresh() in
match re with
| Empty_String ->
{ qs = [x];
  sigma = [];
  delta = [];
  q0 = x;
  fs = [x];}
| Char c ->
{ qs = [x; y];
  sigma = [c];
  delta = [(x, Some c, y)];
  q0 = x;
  fs = [y];}
| Union (v, w) ->
let a = fresh() in
let b = fresh() in
let n1 = helper v n in
let n1_p = a + (List.length n1.qs) in
let n2 = helper w n1_p in
let n2_p = n1_p + (List.length n2.qs) in
{ qs = [n2_p; n2_p + 1]@(Sets.union n1.qs n2.qs);
  sigma = (Sets.union n1.sigma n2.sigma);
  delta = (Sets.union n1.delta n2.delta)@[(n2_p, None, n1.q0)]@
  [(n2_p, None, n2.q0)]@[((List.hd n1.fs), None, n2_p + 1)]@
  [((List.hd n2.fs), None, n2_p + 1)];
  q0 = n2_p;
  fs = [n2_p + 1];}
| Concat(v, w) ->
let n1 = helper v n in
let n1_p = n + (List.length n1.qs) in
let n2 = helper w n1_p in
{ qs = (Sets.union n1.qs n2.qs);
  sigma = (Sets.union n1.sigma n2.sigma);
  delta = [((List.hd n1.fs), None, n2.q0)]@(Sets.union n1.delta n2.delta);
  q0 = n1.q0;
  fs = n2.fs;}
| Star s ->
let sp = helper s n in
{ qs = sp.qs;
  sigma = sp.sigma;
  delta = append ([(sp.q0, None, (List.hd sp.fs))]@
  [((List.hd sp.fs), None, sp.q0)]) sp.delta;
  q0 = sp.q0;
  fs = sp.fs;} in
helper re 0;;

(*string -> regexp*)
let rec regexp_to_string r =
match r with
| Empty_String -> "E"
| Char c -> String.make 1 c
| Union (h,t) -> "("^(regexp_to_string h)^")"^"|"^"("^(regexp_to_string t)^")"
| Concat (h,t) -> "("^(regexp_to_string h)^")"^"("^(regexp_to_string t)^")"
| Star s -> "("^(regexp_to_string s)^")"^"*";;

(*****************************************************************)
(* Below this point is parser code that YOU DO NOT NEED TO TOUCH *)
(*****************************************************************)

exception IllegalExpression of string

(* Scanner *)
type token =
  | Tok_Char of char
  | Tok_Epsilon
  | Tok_Union
  | Tok_Star
  | Tok_LParen
  | Tok_RParen
  | Tok_END

let tokenize str =
  let re_var = Str.regexp "[a-z]" in
  let re_epsilon = Str.regexp "E" in
  let re_union = Str.regexp "|" in
  let re_star = Str.regexp "*" in
  let re_lparen = Str.regexp "(" in
  let re_rparen = Str.regexp ")" in
  let rec tok pos s =
    if pos >= String.length s then
      [Tok_END]
    else begin
      if (Str.string_match re_var s pos) then
        let token = Str.matched_string s in
        (Tok_Char token.[0])::(tok (pos+1) s)
      else if (Str.string_match re_epsilon s pos) then
        Tok_Epsilon::(tok (pos+1) s)
      else if (Str.string_match re_union s pos) then
        Tok_Union::(tok (pos+1) s)
      else if (Str.string_match re_star s pos) then
        Tok_Star::(tok (pos+1) s)
      else if (Str.string_match re_lparen s pos) then
        Tok_LParen::(tok (pos+1) s)
      else if (Str.string_match re_rparen s pos) then
        Tok_RParen::(tok (pos+1) s)
      else
        raise (IllegalExpression("tokenize: " ^ s))
    end
  in
  tok 0 str

let tok_to_str t = ( match t with
      Tok_Char v -> (Char.escaped v)
    | Tok_Epsilon -> "E"
    | Tok_Union -> "|"
    | Tok_Star ->  "*"
    | Tok_LParen -> "("
    | Tok_RParen -> ")"
    | Tok_END -> "END"
  )

(*
   S -> A Tok_Union S | A
   A -> B A | B
   B -> C Tok_Star | C
   C -> Tok_Char | Tok_Epsilon | Tok_LParen S Tok_RParen

   FIRST(S) = Tok_Char | Tok_Epsilon | Tok_LParen
   FIRST(A) = Tok_Char | Tok_Epsilon | Tok_LParen
   FIRST(B) = Tok_Char | Tok_Epsilon | Tok_LParen
   FIRST(C) = Tok_Char | Tok_Epsilon | Tok_LParen
 *)

let parse_regexp (l : token list) =
  let lookahead tok_list = match tok_list with
      [] -> raise (IllegalExpression "lookahead")
    | (h::t) -> (h,t)
  in

  let rec parse_S l =
    let (a1,l1) = parse_A l in
    let (t,n) = lookahead l1 in
    match t with
      Tok_Union -> (
        let (a2,l2) = (parse_S n) in
        (Union (a1,a2),l2)
      )
    | _ -> (a1,l1)

  and parse_A l =
    let (a1,l1) = parse_B l in
    let (t,n) = lookahead l1 in
    match t with
      Tok_Char c ->
      let (a2,l2) = (parse_A l1) in (Concat (a1,a2),l2)
    | Tok_Epsilon ->
      let (a2,l2) = (parse_A l1) in (Concat (a1,a2),l2)
    | Tok_LParen ->
      let (a2,l2) = (parse_A l1) in (Concat (a1,a2),l2)
    | _ -> (a1,l1)

  and parse_B l =
    let (a1,l1) = parse_C l in
    let (t,n) = lookahead l1 in
    match t with
      Tok_Star -> (Star a1,n)
    | _ -> (a1,l1)

  and parse_C l =
    let (t,n) = lookahead l in
    match t with
      Tok_Char c -> (Char c, n)
    | Tok_Epsilon -> (Empty_String, n)
    | Tok_LParen ->
      let (a1,l1) = parse_S n in
      let (t2,n2) = lookahead l1 in
      if (t2 = Tok_RParen) then
        (a1,n2)
      else
        raise (IllegalExpression "parse_C 1")
    | _ -> raise (IllegalExpression "parse_C 2")
  in
  let (rxp, toks) = parse_S l in
  match toks with
  | [Tok_END] -> rxp
  | _ -> raise (IllegalExpression "parse didn't consume all tokens")

let string_to_regexp str = parse_regexp @@ tokenize str

let string_to_nfa str = regexp_to_nfa @@ string_to_regexp str
