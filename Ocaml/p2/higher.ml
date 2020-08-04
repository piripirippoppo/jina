open Funs

(********************************)
(* Part 1: High Order Functions *)
(********************************)

let count_occ lst target =
fold (fun a h -> if h = target then a+1 else a) 0 lst;;

let uniq lst =
fold_right (fun h a -> if count_occ a h = 0 then h::a else a) lst [];;

let assoc_list lst =
fold_right (fun h a -> (h, (count_occ lst h))::a) (uniq lst) [];;

let flatmap f lst =
fold_right (fun h a -> (f h)@a) lst [];;

let ap fns args =
fold_right (fun fn_h fn_a ->
		(fold_right (fun arg_h arg_a ->
				 (fn_h arg_h)::arg_a) args [])@fn_a) fns [];;