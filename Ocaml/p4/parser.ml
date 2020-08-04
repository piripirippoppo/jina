open SmallCTypes
open Utils

type stmt_result = token list * stmt
type expr_result = token list * expr

(* Provided helper function - takes a token list and an exprected token.
 * Handles error cases and returns the tail of the list *)
let match_token (toks : token list) (tok : token) : token list =
  match toks with
  | [] -> raise (InvalidInputException(string_of_token tok))
  | h::t when h = tok -> t
  | h::_ -> raise (InvalidInputException(
      Printf.sprintf "Expected %s from input %s, got %s"
        (string_of_token tok)
        (string_of_list string_of_token toks)
        (string_of_token h)))

  let lookahead (toks: token list) : token =
  match toks with
  | [] -> raise (InvalidInputException "no tokens")
  | h::t -> h

let rec parse_expr (toks: token list) : expr_result =
parse_OrExpr toks

(*OrExpr -> OrExpr || OrExpr | AndExpr*)
and parse_OrExpr (toks: token list) : token list * expr =
let (andList, andExpr) = parse_AndExpr toks in
match andList with
| Tok_Or::t -> let (a, b) = parse_OrExpr t in
		       (a, Or (andExpr, b))
| _ -> (andList, andExpr)

(*AndExpr -> AndExpr && AndExpr | EqualityExpr*)
and parse_AndExpr (toks: token list) : token list * expr =
let (euqalityList, equalityExpr) = parse_EqualityExpr toks in
match euqalityList with
| Tok_And::t -> let (a, b) = parse_AndExpr t in
			 	(a, And (equalityExpr, b))
| _ -> (euqalityList, equalityExpr)

(*EqualityExpr -> EqualityExpr EqualityOperator EqualityExpr | RelationalExpr
        EqualityOperator -> == | !=*)
and parse_EqualityExpr (toks: token list) : token list * expr =
let (relationList, relationalExpr) = parse_RelationExpr toks in
match relationList with
| Tok_Equal::t -> let (a, b) = parse_EqualityExpr t in
			   	  (a, Equal (relationalExpr, b))
| Tok_NotEqual::t -> let (a, b) = parse_EqualityExpr t in
			   		 (a, NotEqual (relationalExpr, b))
| _ -> (relationList, relationalExpr)

(*RelationalExpr -> RelationalExpr RelationalOperator RelationalExpr | AdditiveExpr
        RelationalOperator -> < | > | <= | >=*)
and parse_RelationExpr (toks: token list) : token list * expr =
let (additiveList, additiveExpr) = parse_AdditiveExpr toks in
match additiveList with
| Tok_Less::t -> let (a, b) = parse_RelationExpr t in
			  	 (a, Less (additiveExpr, b))
| Tok_LessEqual::t -> let (c, d) = parse_RelationExpr t in
				   	  (c, LessEqual (additiveExpr, d))
| Tok_Greater::t -> let (e, f) = parse_RelationExpr t in
				 	(e, Greater (additiveExpr, f))
| Tok_GreaterEqual::t -> let (g, h) = parse_RelationExpr t in
					  (g, GreaterEqual (additiveExpr, h))
| _ -> (additiveList, additiveExpr)

(*AdditiveExpr -> AdditiveExpr AdditiveOperator AdditiveExpr | MultiplicativeExpr
        AdditiveOperator -> + | -*)
and parse_AdditiveExpr (toks: token list) : token list * expr =
let (multiplicativeList, multiplicativeExpr) = parse_MuliplicativeExpr toks in
match multiplicativeList with
| Tok_Add::t -> let (a, b) = parse_AdditiveExpr t in
			 	(a, Add (multiplicativeExpr, b))
| Tok_Sub::t -> let (c, d) = parse_AdditiveExpr t in
			 	(c, Sub (multiplicativeExpr, d))
| _ -> (multiplicativeList, multiplicativeExpr)

(*MultiplicativeExpr -> MultiplicativeExpr MultiplicativeOperator MultiplicativeExpr | PowerExpr
        MultiplicativeOperator -> * | /*)
and parse_MuliplicativeExpr (toks: token list) : token list * expr =
let (powerList, powerExpr) = parse_PowerExpr toks in
match powerList with
| Tok_Mult::t -> let (a, b) = parse_MuliplicativeExpr t in
			  	 (a, Mult (powerExpr, b))
| Tok_Div::t -> let (c, d) = parse_MuliplicativeExpr t in
			 	(c, Div (powerExpr, d))
| _ -> (powerList, powerExpr)

(*PowerExpr -> PowerExpr ^ PowerExpr | UnaryExpr*)
and parse_PowerExpr (toks: token list) : token list * expr =
let (unaryList, unaryExpr) = parse_UnaryExpr toks in
match unaryList with
| Tok_Pow::t -> let (a, b) = parse_PowerExpr t in
			 	(a, Pow (unaryExpr, b))
| _ -> (unaryList, unaryExpr)

(*UnaryExpr -> ! UnaryExpr | PrimaryExpr*)
and parse_UnaryExpr (toks: token list) : token list * expr =
let (primaryList, primaryExpr) = parse_PrimaryExpr toks in
match primaryList with
| Tok_Not::t -> let (a, b) = parse_UnaryExpr t in
			 	(a, Not b)
| _ -> (primaryList, primaryExpr)

(*PrimaryExpr -> Tok_Int | Tok_Bool | Tok_ID | ( Expr )*)
and parse_PrimaryExpr (toks: token list) : token list * expr =
match toks with
| (Tok_Int i)::t -> (t, Int i)
| (Tok_Bool b)::t -> (t, Bool b)
| (Tok_ID s)::t -> (t, ID s)
| Tok_LParen::t -> let (a, b) = parse_expr t in
				(match_token a Tok_RParen, b)
| _ -> raise (InvalidInputException "parse failed")

(*Stmt -> StmtOptions Stmt | StmtOptions
	DeclareStmt | AssignStmt | PrintStmt | IfStmt | WhileStmt*)
let rec parse_stmt (toks: token list) : stmt_result =
match toks with
(*Declare of data type * string
	->  BasicType ID ; (int | bool) *)
| Tok_Int_Type::t -> (
                match t with
                | (Tok_ID x)::t1 ->
		let a = match_token t1 Tok_Semi in
		let (b, c) = parse_stmt a in
		(b, Seq (Declare(Int_Type, x), c))
		| _ -> raise (InvalidInputException "int_Type failed")
		)
| Tok_Bool_Type::t -> (
                match t with
                | (Tok_ID x)::t1 ->
		let a = match_token t1 Tok_Semi in
		let (b, c) = parse_stmt a in
		(b, Seq (Declare(Bool_Type, x), c))
		| _ -> raise (InvalidInputException "bool_Type failed")
		)
(*Assign of string * expr
 	-> ID = Expr ;*)
| (Tok_ID x)::t -> (
				let t' = match_token t Tok_Assign in
				let (a, b) = parse_expr t' in
			  	let c = match_token a Tok_Semi in
			  	let (d, e) = parse_stmt c in
			  	(d, Seq (Assign (x, b), e)))

(*Print of expr
	-> printf ( Expr ) ;*)
| Tok_Print::t -> (
				let t' = match_token t Tok_LParen in
				let (a, b) = parse_expr t' in
			        let c = match_token a Tok_RParen in
			        let d = match_token c Tok_Semi in
			        let (e, f) = parse_stmt d in
				(e, Seq(Print (b), f)))

(*If of expr * stmt * stmt
	if ( Expr ) { Stmt } ElseBranch *)
| Tok_If::t -> (
				let t' = match_token t Tok_LParen in
				let (a, b) = parse_expr t' in
				let c = match_token a Tok_RParen in
				let d = match_token c Tok_LBrace in
				let (e, f) = parse_stmt d in
				let g = match_token e Tok_RBrace in
		match g with
		| Tok_Else::t1 -> (
				let t1' = match_token t1 Tok_LBrace in
				let (h, i) = parse_stmt t1' in
				let j = match_token h Tok_RBrace in
				let (k, l) = parse_stmt j in
				(k, Seq (If (b, f, i), l)))
		| _ -> let (m, n) = parse_stmt g in
                         (m, Seq (If (b, f, NoOp), n)))

(*While of expr * stmt
	-> while ( Expr ) { Stmt } *)
| Tok_While::t -> (
				let t1 = match_token t Tok_LParen in
				let (a, b) = parse_expr t1 in
				let c = match_token a Tok_RParen in
				let e = match_token c Tok_LBrace in
				let (f, g) = parse_stmt e in
				let h = match_token f Tok_RBrace in
				let (i, j) = parse_stmt h in
				(i, Seq (While (b, g), j)))
| Tok_RBrace::t -> (toks, NoOp)
| EOF::t -> (toks, NoOp)
| _ -> raise (InvalidInputException "parse_stmt failed")

let parse_main (toks: token list): stmt =
match toks with
| Tok_Int_Type::Tok_Main::Tok_LParen::Tok_RParen::Tok_LBrace::t ->
				let (e, f) = parse_stmt t in
				let g = match_token e Tok_RBrace in
				let h = match_token g EOF in f
| _ -> raise (InvalidInputException "parse_main failed")
