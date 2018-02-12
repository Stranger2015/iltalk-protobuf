%ok, few query examples before being back to work:
%starting with something easy:

query(1, [A]) :-
     A = 1.
query(2, [] ) :-
    true.
query(3, [A,B,C]) :-
    A = B,
    B = C.
query(4, [A,B,C]) :-
    A = B,
    B = C,
    C = 1.
query(5, []) :-
    false.
query(6, [X]) :-
    member(X, [1,2,3]).
