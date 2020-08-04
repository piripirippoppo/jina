open SmallCTypes
module L = List
module S = String
module R = Str

let re = [ 
	(Str.regexp "\\bwhile\\b" , fun _ -> [Tok_While]);
	(Str.regexp "-?[0-9]+" , fun x -> [Tok_Int (int_of_string x)]) ; 
	(Str.regexp "\\btrue\\b" , fun x -> [Tok_Bool (bool_of_string x)]);
	(Str.regexp "\\bfalse\\b" , fun x -> [Tok_Bool (bool_of_string x)]);
	(Str.regexp "-" , fun _ -> [Tok_Sub]) ;
	(Str.regexp ";" , fun _ -> [Tok_Semi]) ;
  	(Str.regexp ")" , fun _ -> [Tok_RParen]) ; 
  	(Str.regexp "}" , fun _ -> [Tok_RBrace]) ; 
	(Str.regexp "\\bprintf\\b" , fun _ -> [Tok_Print]) ;
  	(Str.regexp "\\^" , fun _ -> [Tok_Pow]) ;
  	(Str.regexp "\\+" , fun _ -> [Tok_Add]) ; 
	(Str.regexp "||" , fun _ -> [Tok_Or]) ;
  	(Str.regexp "!=" , fun _ -> [Tok_NotEqual]) ;
	(Str.regexp "!" , fun _ -> [Tok_Not]) ;
  	(Str.regexp "\\*" , fun _ -> [Tok_Mult]) ; 
  	(Str.regexp "\\bmain\\b" , fun _ -> [Tok_Main]) ;
  	(Str.regexp "<=" , fun _ -> [Tok_LessEqual]) ;
  	(Str.regexp "<" , fun _ -> [Tok_Less]) ;
	(Str.regexp "(" , fun _ -> [Tok_LParen]) ; 
  	(Str.regexp "{" , fun _ -> [Tok_LBrace]) ;
  	(Str.regexp "if" , fun _ -> [Tok_If]) ;
  	(Str.regexp ">=" , fun _ -> [Tok_GreaterEqual]) ;
  	(Str.regexp ">" , fun _ -> [Tok_Greater]) ;
  	(Str.regexp "==" , fun _ -> [Tok_Equal]) ;
  	(Str.regexp "\\belse\\b" , fun _ -> [Tok_Else]) ;
  	(Str.regexp "\\/" , fun _ -> [Tok_Div]) ;
  	(Str.regexp "=" , fun _ -> [Tok_Assign]) ;
  	(Str.regexp "&&" , fun _ -> [Tok_And]) ;
        (Str.regexp "\n" , fun _ -> []) ;
        (Str.regexp "\t" , fun _ -> []) ;
        (Str.regexp " " , fun _ -> []) ;
        (Str.regexp "\r" , fun _ -> []) ;
  	(Str.regexp "\\bint\\b" , fun _ -> [Tok_Int_Type]) ;
  	(Str.regexp "\\bbool\\b" , fun _ -> [Tok_Bool_Type]) ;
  	(Str.regexp "\\Z" , fun _ -> [EOF]) ;
  	(Str.regexp "[a-zA-Z][a-zA-Z0-9]*" , fun x -> [Tok_ID x])
] 

(* Given source code returns a token list. *) 
let rec tokenize (s : string) : token list = 
lexer' s 0 
	 
(* Helper for lexer takes in a position offset. *) 
and lexer' (s : string) (pos : int) : token list = 
	if pos >= String.length s then [EOF] 
	else		
		let (_, f) = List.find (fun (re, _) -> Str.string_match re s pos) re in 
	let s' = Str.matched_string s in 
	(f s') @ (lexer' s (pos + (String.length s')));;
